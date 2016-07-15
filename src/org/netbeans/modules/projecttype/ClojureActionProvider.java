package org.netbeans.modules.projecttype;

import org.netbeans.spi.project.ActionProvider;
import org.openide.util.Lookup;

public class ClojureActionProvider implements ActionProvider {

    public static final String COMMAND_DEPS = "deps";
    public static final String COMMAND_HELP = "help";

    @Override
    public String[] getSupportedActions() {
        return new String[]{
            ActionProvider.COMMAND_RUN,
            COMMAND_DEPS,
            ActionProvider.COMMAND_BUILD,
            ActionProvider.COMMAND_CLEAN,
            COMMAND_HELP
        };
    }

    @Override
    public void invokeAction(String string, Lookup lookup) throws IllegalArgumentException {
    }

    @Override
    public boolean isActionEnabled(String string, Lookup lookup) throws IllegalArgumentException {
        return true;
    }

}
