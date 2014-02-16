/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clojure.filetype;

import java.io.IOException;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@DataObject.Registration(iconBase = "org/clojure/filetype/clojure-icon.png", mimeType = "text/x-clojure")
public class ClojureDataObject extends MultiDataObject {

    public ClojureDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
	super(pf, loader);
	registerEditor("text/x-clojure", true);
    }

    @Override
    protected int associateLookup() {
	return 1;
    }

    @MultiViewElement.Registration(displayName = "#LBL_Clojure_EDITOR",
//    iconBase = "SET/PATH/TO/ICON/HERE",
    mimeType = "text/x-clojure",
    persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
    preferredID = "Clojure",
    position = 1000)
    @Messages("LBL_Clojure_EDITOR=Source")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
	return new MultiViewEditorElement(lkp);
    }
    
}
