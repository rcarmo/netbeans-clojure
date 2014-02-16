/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clojure.lexer;

import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.bracesmatching.BracesMatcher;
import org.netbeans.spi.editor.bracesmatching.BracesMatcherFactory;
import org.netbeans.spi.editor.bracesmatching.MatcherContext;
import org.netbeans.spi.editor.bracesmatching.support.BracesMatcherSupport;

@MimeRegistration(mimeType="text/x-clojure", service=BracesMatcherFactory.class)
public class ClojureBraceMatcher implements BracesMatcherFactory {

    @Override
    public BracesMatcher createMatcher(MatcherContext context) {
	return BracesMatcherSupport.defaultMatcher(context, -1, -1);
    }

}