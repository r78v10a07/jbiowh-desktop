package org.jbiowhdesktop.component.popup;

import java.awt.event.ActionEvent;
import java.util.Collection;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.jbiowhcore.utility.constrains.JPLConstrains;
import org.jbiowhdesktop.component.panel.BasicConstrain;
import org.jbiowhdesktop.component.panel.ConstrainPanel;
import org.jbiowhdesktop.component.panel.SearchPanel;
import org.jbiowhdesktop.component.panel.result.ResultPanelFactory;
import org.jbiowhdesktop.datasets.EntityParserViewProxy;
import org.jbiowhdesktop.datasets.ListViewProxy;
import org.jbiowhdesktop.save.SaveToFile;
import org.jbiowhdesktop.utils.SavedResults;
import org.jbiowhpersistence.utils.entitymanager.EntityParserFieldProxy;

/**
 * This Class is the ResultTab panel popup
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since Feb 15, 2012
 */
public class PopupResultTab extends BasicPopup {

    /**
     * Create the PopupListView object
     *
     * @param parentComponent the parent component for this popup
     */
    public PopupResultTab(JComponent parentComponent) {
        super(parentComponent);
        addItems();
    }

    private void addItems() {
        add(createJMenuItem("View", 0, 0));
        add(createJMenuItem("Remove", 0, 0));
        add(new javax.swing.JSeparator());
        add(createJMenuItem("Save Selected to File", 0, 0));
        add(new javax.swing.JSeparator());
        add(createJMenuItem("Add to New Search", 0, 0));
        add(createJMenuItem("Add to New Constrain", 0, 0));
        add(new javax.swing.JSeparator());
        if (parentComponent instanceof JTabbedPane) {
            JMenu selectedAsConstrains = null;
            for (int i = 0; i < ((JTabbedPane) parentComponent).getTabCount(); i++) {
                if (((JTabbedPane) parentComponent).getComponentAt(i) instanceof SearchPanel
                        || ((JTabbedPane) parentComponent).getComponentAt(i) instanceof ConstrainPanel) {
                    if (selectedAsConstrains == null) {
                        selectedAsConstrains = createJMenu("Add selected as constrains on");
                    }
                    selectedAsConstrains.add(createJMenuItem(((JTabbedPane) parentComponent).getTitleAt(i), 0, 0));
                }
            }
            if (selectedAsConstrains != null) {
                add(selectedAsConstrains);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JMenuItem) {
            JMenuItem jMenuItem = (JMenuItem) source;
            TreePath currentSelection = ResultPanelFactory.getInstance().getResultPanel().getjTreeResult().getSelectionPath();
            if (currentSelection != null) {
                DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) (currentNode.getParent());
                switch (jMenuItem.getText()) {
                    case "Remove":
                        if (currentNode.getChildCount() != 0 || parent.isRoot()) {
                            ResultPanelFactory.getInstance().getResultPanel().removeCurrentNode();
                            ResultPanelFactory.getInstance().getResultPanel().getNodes().remove((String) currentNode.getUserObject());
                            SavedResults.getInstance().getResult().remove((String) currentNode.getUserObject());
                        } else {
                            if (parent.getChildCount() == 1) {
                                ResultPanelFactory.getInstance().getResultPanel().getNodes().remove((String) parent.getUserObject());
                                SavedResults.getInstance().getResult().remove((String) parent.getUserObject());
                                DefaultTreeModel model = (DefaultTreeModel) ResultPanelFactory.getInstance().getResultPanel().getjTreeResult().getModel();
                                model.removeNodeFromParent(parent);
                            } else {
                                SavedResults.getInstance().removeIntResult((String) parent.getUserObject(), (String) currentNode.getUserObject());
                                ResultPanelFactory.getInstance().getResultPanel().removeCurrentNode();
                            }
                        }
                        break;
                    case "View":
                        if (currentNode.getChildCount() != 0
                                || SavedResults.getInstance().getResult().get((String) currentNode.getUserObject()) instanceof JPLConstrains) {
                            ListViewProxy.getInstance().setVisible(SavedResults.getInstance().getResult().get((String) currentNode.getUserObject()), parentComponent);
                        } else {
                            if (SavedResults.getInstance().getResult().get((String) parent.getUserObject()) instanceof Collection) {
                                for (Object nodeObject : (Collection) SavedResults.getInstance().getResult().get((String) parent.getUserObject())) {
                                    if (EntityParserFieldProxy.getInstance().getId(nodeObject).equals(currentNode.getUserObject())) {
                                        EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, nodeObject);
                                        viewProxy.setVisible();
                                        break;
                                    }
                                }
                            } else {
                                EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, SavedResults.getInstance().getResult().get((String) currentNode.getUserObject()));
                                viewProxy.setVisible();
                            }
                        }
                        break;
                    case "Save Selected to File":
                        if (parentComponent instanceof JTabbedPane) {
                            SaveToFile saveToFile;
                            if (currentNode.getChildCount() != 0 || parent.isRoot()) {
                                saveToFile = new SaveToFile(parentComponent, SavedResults.getInstance().getResult(((String) currentNode.getUserObject())));
                            } else {
                                saveToFile = new SaveToFile(parentComponent, SavedResults.getInstance().getIntResult((String) parent.getUserObject(), (String) currentNode.getUserObject()));
                            }
                            saveToFile.save();
                        }
                        break;
                    case "Add to New Search":
                        if (parentComponent instanceof JTabbedPane) {
                            SearchPanel searchPanel = new SearchPanel(parentComponent);
                            if (currentNode.getChildCount() != 0 || parent.isRoot()) {
                                searchPanel.addConstrains(SavedResults.getInstance().getResult(((String) currentNode.getUserObject())));
                            } else {
                                searchPanel.addConstrains(SavedResults.getInstance().getIntResult((String) parent.getUserObject(), (String) currentNode.getUserObject()));
                            }
                            searchPanel.setVisible();
                        }
                        break;
                    case "Add to New Constrain":
                        if (parentComponent instanceof JTabbedPane) {
                            ConstrainPanel constrainPanel = new ConstrainPanel(parentComponent);
                            if (currentNode.getChildCount() != 0 || parent.isRoot()) {
                                constrainPanel.addConstrains(SavedResults.getInstance().getResult().get((String) currentNode.getUserObject()));
                            } else {
                                constrainPanel.addConstrains(SavedResults.getInstance().getIntResult((String) parent.getUserObject(), (String) currentNode.getUserObject()));
                            }
                            constrainPanel.setVisible();
                        }
                        break;
                    default:
                        if (parentComponent instanceof JTabbedPane) {
                            JTabbedPane jTabbedPane2 = (JTabbedPane) parentComponent;
                            for (int i = 0; i < jTabbedPane2.getTabCount(); i++) {
                                if (jTabbedPane2.getTitleAt(i).equals(jMenuItem.getText())) {
                                    if (currentNode.getChildCount() != 0 || parent.isRoot()) {
                                        ((BasicConstrain) jTabbedPane2.getComponentAt(i)).addConstrains(SavedResults.getInstance().getResult().get((String) currentNode.getUserObject()));
                                    } else {
                                        ((BasicConstrain) jTabbedPane2.getComponentAt(i)).addConstrains(SavedResults.getInstance().getIntResult((String) parent.getUserObject(), (String) currentNode.getUserObject()));
                                    }
                                    jTabbedPane2.setSelectedIndex(i);
                                    break;
                                }
                            }
                        }
                        break;
                }
            }
        }
    }
}
