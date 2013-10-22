package org.jbiowhdesktop.component.panel.result;

import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Set;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.jbiowhdesktop.component.panel.TreeViewPanel;
import org.jbiowhdesktop.component.popup.PopupResultTab;
import org.jbiowhdesktop.datasets.EntityParserViewProxy;
import org.jbiowhdesktop.utils.SavedResults;
import org.jbiowhpersistence.utils.entitymanager.EntityParserFieldProxy;

/**
 * This Class handled the Result Tab
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since Jul 27, 2012
 */
public class ResultPanel extends TreeViewPanel {

    private JTabbedPane listPane;
    //private PopupResultTab popupResultTab;

    public ResultPanel(JTabbedPane listPane) {
        super("RootNode");
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
        DefaultMutableTreeNode treeNode = addObject(null, child, true);

        Set<String> key = SavedResults.getInstance().listResult((String) child);
        for (String k : key) {
            addObject(treeNode, k, true);
        }

        return treeNode;
    }

    @Override
    public void showPopop(MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            (new PopupResultTab(listPane)).show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }

    @Override
    public void doubleMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            TreePath currentSelection = jTreeResult.getSelectionPath();
            if (currentSelection != null) {
                DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) (currentNode.getParent());
                if (currentNode.getChildCount() == 0) {
                    if (SavedResults.getInstance().getResult().get((String) parent.getUserObject()) instanceof Collection) {
                        for (Object nodeObject : (Collection) SavedResults.getInstance().getResult().get((String) parent.getUserObject())) {
                            if (EntityParserFieldProxy.getInstance().getId(nodeObject).equals(currentNode.getUserObject())) {
                                EntityParserViewProxy viewProxy = new EntityParserViewProxy(listPane, nodeObject);
                                viewProxy.setVisible();
                                break;
                            }
                        }
                    } else {
                        EntityParserViewProxy viewProxy = new EntityParserViewProxy(listPane, SavedResults.getInstance().getResult().get((String) currentNode.getUserObject()));
                        viewProxy.setVisible();
                    }
                }
                evt.consume();
            }

        }
    }
}
