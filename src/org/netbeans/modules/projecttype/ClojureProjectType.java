package org.netbeans.modules.projecttype;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = ProjectFactory.class)
public class ClojureProjectType implements ProjectFactory {
    public static final String PROJECT_FILE = "project.clj";
    @Override
    public boolean isProject(FileObject projectDirectory) {
        return projectDirectory.getFileObject(PROJECT_FILE) != null;
    }
    @Override
    public Project loadProject(FileObject dir, ProjectState state) throws IOException {
        return isProject(dir) ? new ClojureProject(dir) : null;
    }
    @Override
    public void saveProject(final Project project) throws IOException, ClassCastException {
    }
}
