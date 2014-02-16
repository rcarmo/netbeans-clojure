package org.netbeans.modules.projecttemplates;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

//@TemplateRegistration(
//        folder = "Project/Clojure",
//        iconBase = "org/clojure/filetype/clojure-icon.png",
//        displayName = "Clojure",
//        description = "clojureProject.html")
//@Messages("ClojureProjectWizardIterator_displayName=Clojure")
public final class ClojureProjectWizardIterator implements WizardDescriptor.ProgressInstantiatingIterator<WizardDescriptor> {

    private int index;

    private WizardDescriptor wizard;
    private List<WizardDescriptor.Panel<WizardDescriptor>> panels;

    public static ClojureProjectWizardIterator create(FileObject template) {
        return new ClojureProjectWizardIterator(template);
    }
    private FileObject template;
    
    private ClojureProjectWizardIterator(FileObject template) {
        this.template = template;
    }
    
    private List<WizardDescriptor.Panel<WizardDescriptor>> getPanels() {
        if (panels == null) {
            panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
            panels.add(new ClojureProjectWizardPanel1());
            String[] steps = createSteps();
            for (int i = 0; i < panels.size(); i++) {
                Component c = panels.get(i).getComponent();
                if (steps[i] == null) {
                    // Default step name to component name of panel. Mainly
                    // useful for getting the name of the target chooser to
                    // appear in the list of steps.
                    steps[i] = c.getName();
                }
                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                    jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
                }
            }
        }
        return panels;
    }

    @Override
    public Set instantiate(ProgressHandle h) throws IOException {
        String projName = wizard.getProperty("projName").toString();
        String projDir = wizard.getProperty("projDir").toString();
        Set<Object> results = new HashSet<Object>();
        File file = new File(projDir);
        FileObject dest = FileUtil.toFileObject(FileUtil.normalizeFile(file));
//        Callable<Process> processCallable = new Callable<Process>() {
//            @Override
//            public Process call() throws IOException {
//                return new ProcessBuilder("/usr/bin/lein").start();
//            }
//        };
//        ExternalProcessBuilder processBuilder = new ExternalProcessBuilder("/usr/local/bin/lein").
//                addArgument("new").
//                addArgument("app").
//                addArgument(projName);
//        ExecutionDescriptor descriptor = new ExecutionDescriptor()
//                .frontWindow(true).
//                controllable(true);
//        ExecutionService service = ExecutionService.newService(
//                processBuilder,
//                descriptor, 
//                "lein command");
//
//        service.run();        
//        return task.get();
        ProjectCreator gen = new ProjectCreator(dest);
        GeneratedProject proj = gen.createProject(h, projName, template, new HashMap<String, String>());
        results.add(proj.projectDir);
        return results;
    }

    @Override
    public Set instantiate() throws IOException {
        throw new UnsupportedOperationException("Not supported."); //NOI18N
    }
    
    @Override
    public void initialize(WizardDescriptor wizard) {
        this.wizard = wizard;
    }

    @Override
    public void uninitialize(WizardDescriptor wizard) {
        panels = null;
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> current() {
        return getPanels().get(index);
    }

    @Override
    public String name() {
        return index + 1 + ". from " + getPanels().size();
    }

    @Override
    public boolean hasNext() {
        return index < getPanels().size() - 1;
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
    }

    @Override
    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        index--;
    }

    // If nothing unusual changes in the middle of the wizard, simply:
    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }
    // If something changes dynamically (besides moving between panels), e.g.
    // the number of panels changes in response to user input, then use
    // ChangeSupport to implement add/removeChangeListener and call fireChange
    // when needed

    // You could safely ignore this method. Is is here to keep steps which were
    // there before this wizard was instantiated. It should be better handled
    // by NetBeans Wizard API itself rather than needed to be implemented by a
    // client code.
    private String[] createSteps() {
        String[] beforeSteps = (String[]) wizard.getProperty("WizardPanel_contentData");
        assert beforeSteps != null : "This wizard may only be used embedded in the template wizard";
        String[] res = new String[(beforeSteps.length - 1) + panels.size()];
        for (int i = 0; i < res.length; i++) {
            if (i < (beforeSteps.length - 1)) {
                res[i] = beforeSteps[i];
            } else {
                res[i] = panels.get(i - beforeSteps.length + 1).getComponent().getName();
            }
        }
        return res;
    }

}
