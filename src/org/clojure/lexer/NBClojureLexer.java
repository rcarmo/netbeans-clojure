/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clojure.lexer;

import org.clojure.ClojureLexer;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 * @author geertjan
 */
class NBClojureLexer implements Lexer<ClojureTokenId> {

    private final LexerRestartInfo<ClojureTokenId> info;
    private final ClojureLexer clojureLexer;

    public NBClojureLexer(LexerRestartInfo<ClojureTokenId> info) {
	this.info = info;
	AntlrCharStream charStream = new AntlrCharStream(info.input(), "Clojure");
	clojureLexer = new ClojureLexer(charStream);
    }

    @Override
    public org.netbeans.api.lexer.Token<ClojureTokenId> nextToken() {
	org.antlr.runtime.Token token = clojureLexer.nextToken();
	org.netbeans.api.lexer.Token<ClojureTokenId> resultToken = null;
	if (token.getType() != ClojureLexer.EOF) {
	    ClojureTokenId tokenId = ClojureLanguageHierarchy.getToken(token.getType());
	    resultToken = info.tokenFactory().createToken(tokenId);
	}
	return resultToken;
    }

    @Override
    public Object state() {
	return null;
    }

    @Override
    public void release() {
    }
}