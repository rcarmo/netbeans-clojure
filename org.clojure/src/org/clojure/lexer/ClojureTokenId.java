/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clojure.lexer;

import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenId;

/**
 *
 * @author geertjan
 */
public class ClojureTokenId implements TokenId {

    private final String name;
    private final String primaryCategory;
    private final int id;

    ClojureTokenId(
	    String name,
	    String primaryCategory,
	    int id) {
	this.name = name;
	this.primaryCategory = primaryCategory;
	this.id = id;
    }

    public static Language<ClojureTokenId> getLanguage() {
	return new ClojureLanguageHierarchy().language();
    }

    @Override
    public String primaryCategory() {
	return primaryCategory;
    }

    @Override
    public int ordinal() {
	return id;
    }

    @Override
    public String name() {
	return name;
    }
}