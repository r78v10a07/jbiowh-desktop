package org.jbiowhdesktop.component.popup;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Set;
import javax.swing.*;
import org.jbiowhdesktop.component.panel.ConstrainPanel;
import org.jbiowhdesktop.component.panel.SearchPanel;
import org.jbiowhdesktop.component.panel.result.ResultPanelFactory;
import org.jbiowhdesktop.component.table.model.ConstrainsTableModel;
import org.jbiowhdesktop.datasets.ListViewProxy;
import org.jbiowhdesktop.utils.SavedResults;

/**
 * This Class is the Constrains Panel Popup Menu
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2013-03-19 09:38:47 +0100 (Tue, 19 Mar 2013) $
 * $LastChangedRevision: 396 $
 * @since Feb 24, 2012
 */
public class PopupConstrainPanel extends BasicPopup {

    public PopupConstrainPanel(JComponent parentComponent) {
        super(parentComponent);
        addItems();
    }

    private void addItems() {
        add(createJMenuItem("View", 0, 0));
        add(createJMenuItem("Remove", 0, 0));
        add(new javax.swing.JSeparator());
        add(createJMenuItem("Save Constrain", KeyEvent.VK_S, InputEvent.CTRL_MASK));
        add(createJMenuItem("Add to New Search", 0, 0));
        add(new javax.swing.JSeparator());
        if (parentComponent instanceof JTabbedPane) {
            JMenu selectedAsConstrainsOnSearch = null;
            for (int i = 0; i < ((JTabbedPane) parentComponent).getTabCount(); i++) {
                if (((JTabbedPane) parentComponent).getComponentAt(i) instanceof SearchPanel) {
                    if (selectedAsConstrainsOnSearch == null) {
                        selectedAsConstrainsOnSearch = createJMenu("Add selected as constrains on");
                    }
                    selectedAsConstrainsOnSearch.add(createJMenuItem(((JTabbedPane) parentComponent).getTitleAt(i), 0, 0));
                }
            }
            if (selectedAsConstrainsOnSearch != null) {
                add(selectedAsConstrainsOnSearch);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JMenuItem) {
            JMenuItem jMenuItem = (JMenuItem) source;
            if (this.getInvoker() instanceof JTable) {
                JTable table = (JTable) this.getInvoker();
                ConstrainsTableModel model = (ConstrainsTableModel) table.getModel();
                Container o = table.getParent();
                switch (jMenuItem.getText()) {
                    case "Remove":
                        if (table.getSelectedRow() >= 0 && table.getSelectedRow() < table.getRowCount()) {
                            model.rmRow(table.getSelectedRow());
                        }
                        break;
                    case "View":
                        if (table.getSelectedRow() >= 0 && table.getSelectedRow() < table.getRowCount()) {
                            ListViewProxy.getInstance().setVisible(model.getDataAt(table.getSelectedRow()), parentComponent);
                        }
                        break;
                    case "Save Constrain":
                        while (o != null) {
                            if (o instanceof ConstrainPanel) {
                                SavedResults.getInstance().addResult(((ConstrainPanel) o).getConstrains());
                                if (SavedResults.getInstance().getResult() != null) {
                                    Set<String> key = SavedResults.getInstance().getResult().keySet();
                                    for (String a : key) {
                                        if (!ResultPanelFactory.getInstance().getResultPanel().getNodes().contains(a)) {
                                            ResultPanelFactory.getInstance().getResultPanel().addObject(null, a, true);
                                        }
                                    }
                                }
                                break;
                            }
                            o = o.getParent();
                        }
                        break;
                    case "Add to New Search":
                        while (o != null) {
                            if (o instanceof ConstrainPanel) {
                                SearchPanel searchPanel = new SearchPanel(parentComponent);
                                searchPanel.addConstrains(((ConstrainPanel) o).getConstrains());
                                searchPanel.setVisible();
                            }
                            o = o.getParent();
                        }
                        break;
                    default:
                        while (o != null) {
                            if (o instanceof ConstrainPanel) {
                                JTabbedPane jTabbedPane2 = (JTabbedPane) parentComponent;
                                for (int i = 0; i < jTabbedPane2.getTabCount(); i++) {
                                    if (jTabbedPane2.getTitleAt(i).equals(jMenuItem.getText())) {
                                        if (jTabbedPane2.getComponentAt(i) instanceof SearchPanel) {
                                            ((SearchPanel) jTabbedPane2.getComponentAt(i)).addConstrains(((ConstrainPanel) o).getConstrains());
                                        }
                                        jTabbedPane2.setSelectedIndex(i);
                                        break;
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
}
