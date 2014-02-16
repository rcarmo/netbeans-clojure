/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clojure.lexer;

import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;

@LanguageRegistration(mimeType = "text/x-clojure")
public class ClojureLanguage extends DefaultLanguageConfig {

    @Override
    public Language getLexerLanguage() {
        return ClojureTokenId.getLanguage();
    }

    @Override
    public String getDisplayName() {
        return "Clojure";
    }

}