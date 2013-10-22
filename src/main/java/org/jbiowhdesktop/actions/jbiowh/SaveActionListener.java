package org.jbiowhdesktop.actions.jbiowh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhdesktop.JBioWH;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhdesktop.component.panel.ConstrainPanel;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhdesktop.component.panel.result.ResultPanelFactory;
import org.jbiowhdesktop.utils.SavedResults;

/**
 * This class launch the save action on the main window
 *
 * $Author: r78v10a07@gmail.com $ 
 * $LastChangedDate: 2013-03-19 09:38:47 +0100 (Tue, 19 Mar 2013) $ 
 * $LastChangedRevision: 396 $
 * @since Sep 25, 2012
 */
public class SaveActionListener implements ActionListener {

    private JBioWH jbiowh;

    /**
     * Launch the save action on the main window
     *
     * @param jbiowh the main window object
     */
    public SaveActionListener(JBioWH jbiowh) {
        this.jbiowh = jbiowh;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        VerbLogger.getInstance().log(this.getClass(), "\tJBioWH Saving Object Class: " + jbiowh.getjTabbedPane2().getSelectedComponent().getClass().getSimpleName());
        if (jbiowh.getjTabbedPane2().getSelectedComponent() instanceof AbstractListView) {
            AbstractListView o = (AbstractListView) jbiowh.getjTabbedPane2().getSelectedComponent();
            int size = ((AbstractListView) o).getSelectedElements().size();
            SavedResults.getInstance().addResult(((AbstractListView) o).getSelectedElements());
            if (SavedResults.getInstance().getResult() != null) {
                Set<String> key = SavedResults.getInstance().getResult().keySet();
                for (String a : key) {
                    if (!ResultPanelFactory.getInstance().getResultPanel().getNodes().contains(a)) {
                        if (size > 1) {
                            ResultPanelFactory.getInstance().getResultPanel().addObject(a);
                        } else {
                            ResultPanelFactory.getInstance().getResultPanel().addObject(null, a, true);
                        }
                    }
                }
            }
        } else if (jbiowh.getjTabbedPane2().getSelectedComponent() instanceof ConstrainPanel) {
            ConstrainPanel o = (ConstrainPanel) jbiowh.getjTabbedPane2().getSelectedComponent();
            SavedResults.getInstance().addResult(((ConstrainPanel) o).getConstrains());
            if (SavedResults.getInstance().getResult() != null) {
                Set<String> key = SavedResults.getInstance().getResult().keySet();
                for (String a : key) {
                    if (!ResultPanelFactory.getInstance().getResultPanel().getNodes().contains(a)) {
                        ResultPanelFactory.getInstance().getResultPanel().addObject(null, a, true);
                    }
                }
            }

        } else if (jbiowh.getjTabbedPane2().getSelectedComponent() instanceof AbstractDataSetView) {
            AbstractDataSetView o = (AbstractDataSetView) jbiowh.getjTabbedPane2().getSelectedComponent();
            SavedResults.getInstance().addResult(o);
            if (SavedResults.getInstance().getResult() != null) {
                Set<String> key = SavedResults.getInstance().getResult().keySet();
                for (String a : key) {
                    if (!ResultPanelFactory.getInstance().getResultPanel().getNodes().contains(a)) {
                        ResultPanelFactory.getInstance().getResultPanel().addObject(null, a, true);
                    }
                }
            }
        }
    }
}
