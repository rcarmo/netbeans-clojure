package org.netbeans.modules.projecttype;

import org.netbeans.spi.project.ActionProvider;
import org.openide.util.Lookup;

public class ClojureActionProvider implements ActionProvider {

    @Override
    public String[] getSupportedActions() {
        return new String[]{
            ActionProvider.COMMAND_RUN,
            ActionProvider.COMMAND_BUILD,
            ActionProvider.COMMAND_CLEAN,
            ActionProvider.COMMAND_COMPILE_SINGLE
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
