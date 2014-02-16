/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clojure.lexer;

import java.util.*;
import org.clojure.ClojureLexer;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 * @author geertjan
 *
 * EOF=-1; AMPERSAND=4; APOSTROPHE=5; BACKSLASH=6; BOOLEAN=7; CHARACTER=8;
 * CIRCUMFLEX=9; CLOSE_PAREN=10; COMMENT=11; COMMERCIAL_AT=12;
 * EscapeSequence=13; HEXDIGIT=14; KEYWORD=15; LAMBDA_ARG=16;
 * LEFT_CURLY_BRACKET=17; LEFT_SQUARE_BRACKET=18; METADATA_TYPEHINT=19; NAME=20;
 * NIL=21; NUMBER=22; NUMBER_SIGN=23; OPEN_PAREN=24; OctalEscape=25;
 * REGEX_LITERAL=26; RIGHT_CURLY_BRACKET=27; RIGHT_SQUARE_BRACKET=28; SPACE=29;
 * SPECIAL_FORM=30; STRING=31; SYMBOL=32; SYMBOL_HEAD=33; SYMBOL_REST=34;
 * SYNTAX_QUOTE=35; UNQUOTE=36; UNQUOTE_SPLICING=37; UnicodeEscape=38;
 *
 */
public class ClojureLanguageHierarchy extends LanguageHierarchy<ClojureTokenId> {

    private static List<ClojureTokenId> tokens;
    private static Map<Integer, ClojureTokenId> idToToken;

    private static void init() {
	tokens = Arrays.<ClojureTokenId>asList(new ClojureTokenId[]{
		    new ClojureTokenId("AMPERSAND", "symbol", 4),
		    new ClojureTokenId("APOSTROPHE", "symbol", 5),
		    new ClojureTokenId("BACKSLASH", "symbol", 6),
		    new ClojureTokenId("BOOLEAN", "symbol", 7),
		    new ClojureTokenId("CHARACTER", "character", 8),
		    new ClojureTokenId("CIRCUMFLEX", "symbol", 9),
		    new ClojureTokenId("CLOSE_PAREN", "around", 10),
		    new ClojureTokenId("COMMENT", "comment", 11),
		    new ClojureTokenId("COMMERCIAL_AT", "symbol", 12),
		    new ClojureTokenId("EscapeSequence", "symbol", 13),
		    new ClojureTokenId("HEXDIGIT", "symbol", 14),
		    new ClojureTokenId("KEYWORD", "keyword", 15),
		    new ClojureTokenId("LAMBDA_ARG", "symbol", 16),
		    new ClojureTokenId("LEFT_CURLY_BRACKET", "around", 17),
		    new ClojureTokenId("LEFT_SQUARE_BRACKET", "around", 18),
		    new ClojureTokenId("METADATA_TYPEHINT", "symbol", 19),
		    new ClojureTokenId("NAME", "character", 20),
		    new ClojureTokenId("NIL", "symbol", 21),
		    new ClojureTokenId("NUMBER", "symbol", 22),
		    new ClojureTokenId("NUMBER_SIGN", "symbol", 23),
		    new ClojureTokenId("OPEN_PAREN", "around", 24),
		    new ClojureTokenId("OctalEscape", "symbol", 25),
		    new ClojureTokenId("REGEX_LITERAL", "symbol", 26),
		    new ClojureTokenId("RIGHT_CURLY_BRACKET", "around", 27),
		    new ClojureTokenId("RIGHT_SQUARE_BRACKET", "around", 28),
		    new ClojureTokenId("SPACE", "symbol", 29),
		    new ClojureTokenId("SPECIAL_FORM", "keyword", 30),
		    new ClojureTokenId("STRING", "character", 31),
		    new ClojureTokenId("SYMBOL", "symbol1", 32),
		    new ClojureTokenId("SYMBOL_HEAD", "symbol2", 33),
		    new ClojureTokenId("SYMBOL_REST", "symbol3", 34),
		    new ClojureTokenId("SYNTAX_QUOTE", "symbol4", 35),
		    new ClojureTokenId("UNQUOTE", "symbol", 36),
		    new ClojureTokenId("UNQUOTE_SPLICING", "symbol", 37),
		    new ClojureTokenId("UnicodeEscape", "symbol", 38),
		});
	idToToken = new HashMap<Integer, ClojureTokenId>();
	for (ClojureTokenId token : tokens) {
	    idToToken.put(token.ordinal(), token);
	}
    }

    static synchronized ClojureTokenId getToken(int id) {
	if (idToToken == null) {
	    init();
	}
	return idToToken.get(id);
    }

    @Override
    protected synchronized Collection<ClojureTokenId> createTokenIds() {
	if (tokens == null) {
	    init();
	}
	return tokens;
    }

    @Override
    protected synchronized Lexer<ClojureTokenId> createLexer(LexerRestartInfo<ClojureTokenId> info) {
	return new NBClojureLexer(info);
    }

    @Override
    protected String mimeType() {
	return "text/x-clojure";
    }
}
