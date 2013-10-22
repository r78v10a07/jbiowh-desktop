package org.jbiowhdesktop.component.popup;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.jbiowhdbms.dbms.JBioWHDBMS;
import org.jbiowhdbms.dbms.WHDBMSFactory;
import org.jbiowhdesktop.component.panel.result.ResultPanelFactory;
import org.jbiowhdesktop.component.panel.sql.SQLBrowsePanel;
import org.jbiowhdesktop.component.panel.sql.SQLEditorPanel;
import org.jbiowhdesktop.sqltables.CreateTable;
import org.jbiowhpersistence.utils.entitymanager.JBioWHPersistence;

/**
 * This Class is the SQLTablesTab panel popup
 *
 * $Author: r78v10a07@gmail.com $ 
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $ 
 * $LastChangedRevision: 270 $
 * @since Jul 27, 2012
 */
public class PopupSQLSchema extends BasicPopup {

    private TreePath currentSelection = null;

    /**
     * Creates the SQLTables popup
     *
     * @param parentComponent the parent component for this popup
     */
    public PopupSQLSchema(JComponent parentComponent) {
        super(parentComponent);
        addItems();
    }

    private void addItems() {
        currentSelection = ResultPanelFactory.getInstance().getsQLTableViewPanel().getjTreeResult().getSelectionPath();
        if (currentSelection != null) {
            switch (currentSelection.getPathCount()) {
                case 2:
                    // Popup Menu for the Relational Schema
                    if (JBioWHPersistence.getInstance().getWhdbmsFactory() != null) {
                        if (!JBioWHPersistence.getInstance().getMainURLParsed().equals((String) currentSelection.getLastPathComponent().toString())) {
                            add(createJMenuItem("Set as main schema", 0, 0));
                        }
                    }
                    add(createJMenuItem("Create Table", 0, 0));
                    addSeparator();
                    add(createJMenuItem("Close schema", 0, 0));
                    break;
                case 3:
                    // Popup Menu for the Tables
                    add(createJMenuItem("Select Table - Limit 1000", 0, 0));
                    addSeparator();
                    addSendToSQLEditor();
                    add(createJMenuItem("Remove Table", 0, 0));
                    break;
                case 4:
                    // Popup Menu for the Columns
                    addSendToSQLEditor();
                    break;
            }
        }
    }

    private void addSendToSQLEditor() {
        if (parentComponent instanceof JTabbedPane) {
            JMenu selectedAsConstrains = null;
            for (int i = 0; i < ((JTabbedPane) parentComponent).getTabCount(); i++) {
                if (((JTabbedPane) parentComponent).getComponentAt(i) instanceof SQLEditorPanel) {
                    if (selectedAsConstrains == null) {
                        selectedAsConstrains = createJMenu("Send to SQL Editor");
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
            if (currentSelection != null) {
                WHDBMSFactory factory;
                DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) currentNode.getParent();
                switch (jMenuItem.getText()) {
                    case "Select Table - Limit 1000":
                        if (parentComponent instanceof JTabbedPane) {
                            try {
                                factory = JBioWHDBMS.getInstance().getWhdbmsFactory(((String) parent.getUserObject()));
                                if (factory != null) {
                                    SQLBrowsePanel browsePanel = new SQLBrowsePanel(
                                            (String) currentNode.getUserObject(),
                                            factory.executeSingleSQLSelect("select * from " + ((String) currentNode.getUserObject()), 0, 1000),
                                            parentComponent);
                                    browsePanel.setVisible();
                                }
                            } catch (SQLException ex) {
                                int type = JOptionPane.ERROR_MESSAGE;
                                JOptionPane.showMessageDialog(parentComponent, ex.getMessage(), "SQL Error", type);
                            }
                        }
                        break;
                    case "Set as main schema":
                        JBioWHDBMS.getInstance().setMainURL(((String) currentNode.getUserObject()));
                        JBioWHPersistence.getInstance().setAsMainSchema(((String) currentNode.getUserObject()), false);
                        break;
                    case "Close schema":
                        try {
                            //Hide the close button on the main windows if there is not other connection open
                            ResultPanelFactory.getInstance().getsQLTableViewPanel().removeNode(((String) currentNode.getUserObject()));
                            JBioWHPersistence.getInstance().closeSchema(((String) currentNode.getUserObject()), true);
                            JBioWHDBMS.getInstance().closeConnection(((String) currentNode.getUserObject()));
                        } catch (SQLException ex) {
                            int type = JOptionPane.ERROR_MESSAGE;
                            JOptionPane.showMessageDialog(parentComponent, ex.getMessage(), "SQL Error", type);
                        }
                        break;
                    case "Create Table":
                        factory = JBioWHDBMS.getInstance().getWhdbmsFactory(((String) currentNode.getUserObject()));
                        if (factory != null) {
                            CreateTable table = new CreateTable(new JFrame(), true, factory);
                            table.setLocationRelativeTo(this);
                            table.setVisible(true);
                            if (!table.isCancel()) {
                                try {
                                    factory.executeUpdate(table.getSqlFormat());
                                    ResultPanelFactory.getInstance().getsQLTableViewPanel().addNewTables(factory);
                                } catch (SQLException ex) {
                                    int type = JOptionPane.ERROR_MESSAGE;
                                    JOptionPane.showMessageDialog(parentComponent, ex.getMessage(), "SQL Error", type);
                                }
                            }
                        }
                        break;
                    case "Remove Table":
                        try {
                            factory = JBioWHDBMS.getInstance().getWhdbmsFactory(((String) parent.getUserObject()));
                            if (factory != null) {
                                factory.dropTable(((String) currentNode.getUserObject()));
                                ResultPanelFactory.getInstance().getsQLTableViewPanel().removeCurrentNode();
                            }

                        } catch (SQLException ex) {
                            int type = JOptionPane.ERROR_MESSAGE;
                            JOptionPane.showMessageDialog(parentComponent, ex.getMessage(), "SQL Error", type);
                        }
                        break;
                    default:
                        if (parentComponent instanceof JTabbedPane) {
                            JTabbedPane jTabbedPane2 = (JTabbedPane) parentComponent;
                            for (int i = 0; i < jTabbedPane2.getTabCount(); i++) {
                                if (jTabbedPane2.getTitleAt(i).equals(jMenuItem.getText())) {
                                    SQLEditorPanel editor = (SQLEditorPanel) ((JTabbedPane) parentComponent).getComponentAt(i);
                                    editor.insertOnCaretPosition(((String) currentNode.getUserObject()));
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
