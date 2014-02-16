package org.clojure.filetype;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.clojure.lexer.ClojureTokenId;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.openide.cookies.EditorCookie;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.CookieAction;

@ActionID(
        category = "Build",
        id = "org.clojure.filetype.RunClojureMethodAction"
)
@ActionRegistration(
        lazy = false,
        displayName = "#CTL_RunClojureMethodAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/BuildProject", position = 15),
    @ActionReference(path = "Editors/text/x-clojure/Popup", position = 15)
})
@Messages("CTL_RunClojureMethodAction=Run Clojure Method")
public final class RunClojureMethodAction extends CookieAction {

    public RunClojureMethodAction() {
        setEnabled(false);
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
        JTextComponent jtc = EditorRegistry.lastFocusedComponent();
        if (jtc != null) {
            return jtc.getSelectedText() != null && jtc.getSelectedText().contains("defn");
        } else {
            return false;
        }
    }

    @Override
    protected void performAction(Node[] activatedNodes) {
        JTextComponent jtc = EditorRegistry.lastFocusedComponent();
        int dot = jtc.getCaret().getDot();
        Document doc;
        try {
            doc = activatedNodes[0].getLookup().lookup(EditorCookie.class).openDocument();
            TokenHierarchy hi = TokenHierarchy.get(doc);
            TokenSequence<ClojureTokenId> ts = hi.tokenSequence(ClojureTokenId.getLanguage());
            if (ts != null) {
                ts.move(dot);
                ts.moveNext();
                Token<ClojureTokenId> tok = ts.token();
                JOptionPane.showMessageDialog(null, tok.text() + " / " + tok.id());
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

//        JTextComponent jtc = EditorRegistry.lastFocusedComponent();
//        JOptionPane.showMessageDialog(null, jtc.getSelectedText());
    }

//    private RunClojureMethodAction(Lookup lkp) {
//        super("Run Clojure Method");
//        this.lkp = lkp;
//        result = lkp.lookupResult(EditorCookie.class);
//        result.addLookupListener(
//                WeakListeners.create(LookupListener.class, this, result));
//    }
//    @Override
//    public void actionPerformed(ActionEvent ev) {
//        if (result.allInstances().size() == 1) {
//            EditorCookie context = result.allInstances().iterator().next();
//            try {
//                StyledDocument doc = context.openDocument();
//                JTextComponent jtc = EditorRegistry.lastFocusedComponent();
//                int dot = jtc.getCaret().getDot();
//                String selectedText = jtc.getSelectedText();
//                JOptionPane.showMessageDialog(null, dot);
//            } catch (IOException ex) {
//                Exceptions.printStackTrace(ex);
//            }
//        }
//    }
//
//    @Override
//    public void resultChanged(LookupEvent le) {
//
//        if (result.allInstances().size() == 1) {
//            EditorCookie context = result.allInstances().iterator().next();
//            try {
//                StyledDocument doc = context.openDocument();
//                final JTextComponent jtc = EditorRegistry.lastFocusedComponent();
//                jtc.addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mouseClicked(MouseEvent e) {
//                        String selectedText = jtc.getSelectedText();
//                        JOptionPane.showMessageDialog(null, selectedText);
//                    }
//
////                    @Override
////                    public void mousePressed(MouseEvent e) {
////                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
////                    }
//                });
////                int dot = jtc.getCaret().getDot();
////                String selectedText = jtc.getSelectedText();
////                JOptionPane.showMessageDialog(null, dot);
//            } catch (IOException ex) {
//                Exceptions.printStackTrace(ex);
//            }
//        }
//
////        super.setEnabled(result.allInstances().size() > 0);
//    }
    @Override
    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    @Override
    protected Class<?>[] cookieClasses() {
        return new Class[]{EditorCookie.class};
    }

    @Override
    public String getName() {
        return Bundle.CTL_RunClojureMethodAction();
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

}
