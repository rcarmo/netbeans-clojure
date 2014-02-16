/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clojure.lexer;

import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.LanguagePath;
import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.LanguageEmbedding;
import org.netbeans.spi.lexer.LanguageProvider;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = LanguageProvider.class)
public class HTMLEmbeddingLanguageProvider extends LanguageProvider {

    private Language embeddedLanguage;

    @Override
    public Language<?> findLanguage(String mimeType) {
	return null;
    }

    @Override
    public LanguageEmbedding<?> findLanguageEmbedding(Token<?> token, LanguagePath languagePath, InputAttributes inputAttributes) {
	initLanguage();
	if (11 == token.id().ordinal()) { //11 is the token ordinal for comments
	    return LanguageEmbedding.create(embeddedLanguage, 0, 0);
	}
	return null;
    }

    private void initLanguage() {
	embeddedLanguage = MimeLookup.getLookup("text/html").lookup(Language.class);
	if (embeddedLanguage == null) {
	    throw new NullPointerException("Can't find language for embedding");
	}
    }

}
