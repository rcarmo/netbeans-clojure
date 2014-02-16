package org.clojure.filetype;

import javax.swing.text.Document;
import org.clojure.lexer.ClojureTokenId;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProvider;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.Utilities;

@MimeRegistration(mimeType = "text/x-clojure", service = HyperlinkProvider.class)
public class MySpecialHyperlinkProvider implements HyperlinkProvider {

    private int targetStart;
    private int targetEnd;

    String methodName;
    String namespaceName;

    @Override
    public boolean isHyperlinkPoint(Document doc, int offset) {
        return verifyState(doc, offset);
    }

    public boolean verifyState(Document doc, int offset) {
        TokenHierarchy hi = TokenHierarchy.get(doc);
        TokenSequence<ClojureTokenId> ts = hi.tokenSequence();
        ts.move(offset);
        ts.moveNext();
        Token<ClojureTokenId> tok = ts.token();
        int newOffset = ts.offset();
        String matcherText = tok.text().toString();
        if (matcherText.contains("defn")) {
            targetStart = newOffset;
            targetEnd = targetStart + tok.text().length();
            ts.moveNext();
            ts.moveNext();
            Token<ClojureTokenId> nexttok = ts.token();
            methodName = nexttok.text().toString();
            //now getting the nsame space
            ts.moveStart();
            while (ts.moveNext()) {
                Token t = ts.token();
                matcherText = t.text().toString();
                if (matcherText.contains("ns")) {
                    ts.moveNext();
                    ts.moveNext();
                    Token<ClojureTokenId> nstok = ts.token();
                    namespaceName = nstok.text().toString();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int[] getHyperlinkSpan(Document document, int offset) {
        if (verifyState(document, offset)) {
            return new int[]{targetStart, targetEnd};
        } else {
            return null;
        }
    }

    @Override
    public void performClickAction(Document document, int offset) {
        if (verifyState(document, offset)) {
            Project owner = FileOwnerQuery.getOwner(Utilities.actionsGlobalContext().lookup(DataObject.class).getPrimaryFile());
            ExternalProcessBuilder processBuilder = new ExternalProcessBuilder("/usr/bin/lein").
                    addArgument("run").
                    addArgument("-m").
                    addArgument(namespaceName + "/" + methodName).
                    workingDirectory(FileUtil.toFile(owner.getProjectDirectory()));
            ExecutionDescriptor descriptor = new ExecutionDescriptor().
                    frontWindow(true).
                    showProgress(true).
                    controllable(true);
            ExecutionService service = ExecutionService.newService(
                    processBuilder,
                    descriptor,
                    "run " + methodName);
            service.run();
        }
    }

}
