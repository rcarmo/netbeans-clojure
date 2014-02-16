package org.netbeans.modules.projecttype;

import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

public class ClojureProject implements Project {
    private final FileObject dir;
    public ClojureProject(FileObject dir) {
        this.dir = dir;
    }
    @Override
    public FileObject getProjectDirectory() {
        return dir;
    }
    private Lookup lkp;
    @Override
    public Lookup getLookup() {
        if (lkp == null) {
            lkp = Lookups.fixed(new Object[]{
                new ClojureProjectInformation(),
                new ClojureProjectLogicalView(this),
                new ClojureActionProvider(),
            });
        }
        return lkp;
    }
    class ClojureProjectInformation implements ProjectInformation {
        @StaticResource()
        public static final String CUSTOMER_ICON = "org/clojure/filetype/clojure-icon.png";
        @Override
        public Icon getIcon() {
            return new ImageIcon(ImageUtilities.loadImage(CUSTOMER_ICON));
        }
        @Override
        public String getName() {
            return getProjectDirectory().getName();
        }
        @Override
        public String getDisplayName() {
            return getName();
        }
        @Override
        public void addPropertyChangeListener(PropertyChangeListener pcl) {
        }
        @Override
        public void removePropertyChangeListener(PropertyChangeListener pcl) {
        }
        @Override
        public Project getProject() {
            return ClojureProject.this;
        }
    }
}
