package org.jbiowhdesktop.component.popup;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Set;
import javax.swing.*;
import org.jbiowhdbms.dbms.JBioWHDBMSSingleton;
import org.jbiowhdbms.dbms.JBioWHDBMS;
import org.jbiowhdesktop.component.dialog.progress.ProgressDialog;
import org.jbiowhdesktop.component.graph.EntityRelationshipDialog;
import org.jbiowhdesktop.component.panel.BasicConstrain;
import org.jbiowhdesktop.component.panel.ConstrainPanel;
import org.jbiowhdesktop.component.panel.SearchPanel;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhdesktop.component.panel.result.ResultPanelFactory;
import org.jbiowhdesktop.datasets.EntityParserViewProxy;
import org.jbiowhdesktop.save.SaveToFile;
import org.jbiowhdesktop.utils.SavedResults;
import org.jbiowhpersistence.utils.entitymanager.JBioWHPersistence;
import org.jbiowhpersistence.utils.transfer.TransferData;

/**
 * This Class is the List View's Popup Menu
 *
 * $Author: r78v10a07@gmail.com $ 
 * $LastChangedDate: 2013-03-19 09:38:47 +0100 (Tue, 19 Mar 2013) $ 
 * $LastChangedRevision: 396 $
 * @since Feb 13, 2012
 */
public class PopupListView extends BasicPopup {

    /**
     * Create the PopupListView object
     *
     * @param parentComponent the parent component for this popup
     */
    public PopupListView(JComponent parentComponent) {
        super(parentComponent);
        addItems();
    }

    private void addItems() {
        add(createJMenuItem("View", 0, 0));
        add(new javax.swing.JSeparator());
        add(createJMenuItem("Save Selected", KeyEvent.VK_S, InputEvent.CTRL_MASK));
        add(createJMenuItem("Save Selected to File", KeyEvent.VK_F, InputEvent.CTRL_MASK));
        add(new javax.swing.JSeparator());
        add(createJMenuItem("Add to New Search", 0, 0));
        add(createJMenuItem("Add to New Constrain", 0, 0));
        add(new javax.swing.JSeparator());
        if (parentComponent instanceof JTabbedPane) {
            JMenu selectedAsConstrainsOnSearch = null;
            for (int i = 0; i < ((JTabbedPane) parentComponent).getTabCount(); i++) {
                if (((JTabbedPane) parentComponent).getComponentAt(i) instanceof SearchPanel
                        || ((JTabbedPane) parentComponent).getComponentAt(i) instanceof ConstrainPanel) {
                    if (selectedAsConstrainsOnSearch == null) {
                        selectedAsConstrainsOnSearch = createJMenu("Add selected as constrains on");
                    }
                    selectedAsConstrainsOnSearch.add(createJMenuItem(((JTabbedPane) parentComponent).getTitleAt(i) + i, 0, 0));
                }
            }
            if (selectedAsConstrainsOnSearch != null) {
                add(selectedAsConstrainsOnSearch);
                add(new javax.swing.JSeparator());
            }
        }
        if (JBioWHPersistence.getInstance().getSchemas() != null
                && JBioWHPersistence.getInstance().getSchemas().size() > 1) {
            JMenu insertToOtherSchema = createJMenu("Insert selected to");
            for (String key : JBioWHPersistence.getInstance().getSchemas().keySet()) {
                JBioWHDBMS factory = JBioWHDBMSSingleton.getInstance().getWhdbmsFactory(key);
                if (!factory.equals(JBioWHPersistence.getInstance().getWhdbmsFactory())) {
                    insertToOtherSchema.add(createJMenuItem(factory.getUrl(), 0, 0));
                }
            }
            if (insertToOtherSchema != null) {
                add(insertToOtherSchema);
                add(new javax.swing.JSeparator());
            }
        }

        add(createJMenuItem("Copy", KeyEvent.VK_C, InputEvent.CTRL_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JMenuItem) {
            JMenuItem jMenuItem = (JMenuItem) source;
            if (this.getInvoker() instanceof JTable) {
                JTable table = (JTable) this.getInvoker();
                Container o = table.getParent();
                switch (jMenuItem.getText()) {
                    case "View":
                        while (o != null) {
                            if (o instanceof AbstractListView) {
                                if (table.getSelectedColumn() != 0) {
                                    if ((table.getSelectedRow() >= 0 && table.getSelectedRow() < table.getRowCount())
                                            && (table.getSelectedColumn() >= 0 && table.getSelectedColumn() < table.getColumnCount())) {
                                        EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, ((AbstractListView) o).getCollectionElementFromTableRow(table.getSelectedRow()));
                                        viewProxy.setVisible();
                                    }
                                }
                                break;
                            }
                            o = o.getParent();
                        }
                        break;
                    case "Copy":
                        if (table.getSelectedColumn() != 0) {
                            if ((table.getSelectedRow() >= 0 && table.getSelectedRow() < table.getRowCount())
                                    && (table.getSelectedColumn() >= 0 && table.getSelectedColumn() < table.getColumnCount())) {
                                JTextField text = new JTextField();
                                text.setText(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString());
                                text.copy();
                            }
                        }

                        break;
                    case "Save Selected":
                        while (o != null) {
                            if (o instanceof AbstractListView) {
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
                                break;
                            }
                            o = o.getParent();
                        }
                        break;
                    case "Save Selected to File":
                        while (o != null) {
                            if (o instanceof AbstractListView) {
                                if (parentComponent instanceof JTabbedPane) {
                                    SaveToFile saveToFile = new SaveToFile(((JTabbedPane) parentComponent).getSelectedComponent());
                                    saveToFile.save();
                                }
                                break;
                            }
                            o = o.getParent();
                        }
                        break;
                    case "Add to New Search":
                        while (o != null) {
                            if (o instanceof AbstractListView) {
                                SearchPanel searchPanel = new SearchPanel(parentComponent);
                                searchPanel.addConstrains(((AbstractListView) o).getSelectedElements());
                                searchPanel.setVisible();
                                break;
                            }
                            o = o.getParent();
                        }
                        break;
                    case "Add to New Constrain":
                        while (o != null) {
                            if (o instanceof AbstractListView) {
                                ConstrainPanel constrainPanel = new ConstrainPanel(parentComponent);
                                constrainPanel.addConstrains(((AbstractListView) o).getSelectedElements());
                                constrainPanel.setVisible();
                                break;
                            }
                            o = o.getParent();
                        }
                        break;
                    default:
                        while (o != null) {
                            if (o instanceof AbstractListView) {
                                JTabbedPane jTabbedPane2 = (JTabbedPane) parentComponent;
                                if (jMenuItem.getText().startsWith("jdbc:")) {
                                    Collection objects = ((AbstractListView) o).getSelectedElements();
                                    if (objects != null && !objects.isEmpty()) {
                                        try {
                                            Object element = objects.iterator().next();
                                            EntityRelationshipDialog dialog = new EntityRelationshipDialog(new JFrame(), true);
                                            dialog.selectCell(element.getClass().getSimpleName());
                                            dialog.setLocationRelativeTo(parentComponent);
                                            dialog.setVisible(true);
                                            Task task = new Task(jTabbedPane2, jMenuItem.getText(), objects);
                                            task.execute();
                                        } catch (Exception ex) {
                                            int type = JOptionPane.ERROR_MESSAGE;
                                            JOptionPane.showMessageDialog(jTabbedPane2, ex.getMessage(), "Exception", type);
                                        }
                                    }
                                } else {
                                    for (int i = 0; i < jTabbedPane2.getTabCount(); i++) {
                                        if ((jTabbedPane2.getTitleAt(i) + i).equals(jMenuItem.getText())) {
                                            if (jTabbedPane2.getComponentAt(i) instanceof BasicConstrain) {
                                                ((BasicConstrain) jTabbedPane2.getComponentAt(i)).addConstrains(((AbstractListView) o).getSelectedElements());
                                            }
                                            jTabbedPane2.setSelectedIndex(i);
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                            o = o.getParent();
                        }

                        break;
                }
            }
        }
    }

    private class Task extends SwingWorker<Void, Void> {

        private JTabbedPane jTabbedPane2;
        private String url;
        private Collection objects;

        public Task(JTabbedPane jTabbedPane2, String url, Collection objects) {
            this.jTabbedPane2 = jTabbedPane2;
            this.url = url;
            this.objects = objects;
        }

        @Override
        protected Void doInBackground() throws Exception {
            ProgressDialog dialogView = new ProgressDialog(null, false);
            dialogView.setLocationRelativeTo(jTabbedPane2);
            dialogView.getjProgressBar().setIndeterminate(true);
            dialogView.getjProgressBar().repaint();
            dialogView.getjLProgress().setText("Inserting data to: " + url);
            dialogView.getjLProgress().repaint();
            dialogView.setVisible(true);
            TransferData.getInstance().startTransfer(JBioWHPersistence.getInstance().getSchema(url), objects);
            dialogView.dispose();
            return null;
        }
    }
}
