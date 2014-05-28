package org.jbiowhdesktop.component.panel.sql;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.jbiowhdbms.dbms.JBioWHDBMSSingleton;
import org.jbiowhdbms.dbms.JBioWHDBMS;
import org.jbiowhdesktop.component.panel.TreeViewPanel;
import org.jbiowhdesktop.component.popup.PopupSQLSchema;
import org.jbiowhpersistence.utils.entitymanager.JBioWHPersistence;

/**
 * This Class is the SQL Table View panel
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since Jul 27, 2012
 */
public class SQLTableViewPanel extends TreeViewPanel {

    private JTabbedPane listPane;

    /**
     *
     * @param listPane
     */
    public SQLTableViewPanel(JTabbedPane listPane) {
        super("Relational Schema");
        this.listPane = listPane;
    }

    /**
     * Add child to the currently selected node.
     *
     * @param child the node the be added
     * @return a DefaultMutableTreeNode object
     */
    @Override
    public DefaultMutableTreeNode addObject(Object child) {
        JBioWHDBMS factory = JBioWHDBMSSingleton.getInstance().getWhdbmsFactory();
        DefaultMutableTreeNode treeNode = addObject(null, child, true);

        Map<String, List<String>> tables = factory.getTablesOnSchema();
        for (String table : tables.keySet()) {
            DefaultMutableTreeNode tableNode = addObject(treeNode, table, true);
            for (String columns : tables.get(table)) {
                addObject(tableNode, columns, true);
            }
        }
        return treeNode;
    }

    /**
     * Add child to the currently selected node.
     *
     * @param child the node the be added
     * @param factory the JBioWHPersistenceFactory with the connection
     * @return a DefaultMutableTreeNode object
     */
    public DefaultMutableTreeNode addObject(Object child, JBioWHDBMS factory) {
        DefaultMutableTreeNode treeNode = addObject(null, child, true);

        Map<String, List<String>> tables = factory.getTablesOnSchema();
        for (String table : tables.keySet()) {
            DefaultMutableTreeNode tableNode = addObject(treeNode, table, true);
            for (String columns : tables.get(table)) {
                addObject(tableNode, columns, true);
            }
        }
        return treeNode;
    }

    /**
     * Add new tables to the selected factory
     *
     * @param factory the JBioWHPersistenceFactory with the connection
     * @return a DefaultMutableTreeNode object
     */
    public DefaultMutableTreeNode addNewTables(JBioWHDBMS factory) {
        TreePath schema = findNode(factory.getUrl().substring(factory.getUrl().indexOf("//") + 2));
        if (schema != null) {
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) schema.getLastPathComponent();
            Map<String, List<String>> tables = factory.getTablesOnSchema();
            for (String table : tables.keySet()) {
                if (findNode(table, treeNode) == null) {
                    DefaultMutableTreeNode tableNode = addObject(treeNode, table, true);
                    for (String columns : tables.get(table)) {
                        addObject(tableNode, columns, true);
                    }
                }
            }
            return treeNode;
        }

        return null;
    }

    @Override
    public void showPopop(MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            (new PopupSQLSchema(listPane)).show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }

    @Override
    public void doubleMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            TreePath currentSelection = jTreeResult.getSelectionPath();
            if (currentSelection != null) {
                DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
                if (currentSelection.getPathCount() == 4) {
                    if (((JComponent) listPane.getSelectedComponent()) instanceof SQLEditorPanel) {
                        SQLEditorPanel editor = (SQLEditorPanel) listPane.getSelectedComponent();
                        editor.insertOnCaretPosition(((String) currentNode.getUserObject()));
                    }
                }
                evt.consume();
            }
        }
    }
}
