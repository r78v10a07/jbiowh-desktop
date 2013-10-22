package org.jbiowhdesktop.component.panel;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.jbiowhdesktop.component.tree.TreeSchemaRenderer;

/**
 * This JPanel handled the Tree view
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-08-31 15:46:36 +0200
 * (Fri, 31 Aug 2012) $ $LastChangedRevision: 270 $
 *
 * @since Jul 27, 2012
 */
public abstract class TreeViewPanel extends javax.swing.JPanel {

    /**
     * Creates new form TreeViewPanel
     *
     * @param rootNodeName the tree root name
     */
    public TreeViewPanel(String rootNodeName) {
        nodes = new TreeSet<>();
        rootNode = new DefaultMutableTreeNode(rootNodeName);
        initComponents();
    }

    /**
     * Show the popup in the tree view
     *
     * @param evt a mouse event
     */
    public abstract void showPopop(java.awt.event.MouseEvent evt);

    /**
     * Provides the Mouse Click action
     *
     * @param evt the mouse event
     */
    public abstract void doubleMouseClicked(java.awt.event.MouseEvent evt);

    /**
     * Get the nodes list
     *
     * @return a object set
     */
    public Set<Object> getNodes() {
        return nodes;
    }

    /**
     * Get the TreeResult
     *
     * @return a JTree object
     */
    public JTree getjTreeResult() {
        return jTreeResult;
    }

    /**
     * Remove all nodes except the root node.
     */
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    /**
     * Remove the currently selected node.
     */
    public void removeCurrentNode() {
        TreePath currentSelection = jTreeResult.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
            }
        }
    }

    /**
     * Remove from node.
     *
     * @param currentSelection the tree path to remove nodes
     */
    public void removeNode(TreePath currentSelection) {
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
            }
        }
    }

    /**
     * Remove the node with the name
     *
     * @param name the name of the node to be removed
     */
    public void removeNode(String name) {
        removeNode(findNode(name));
    }

    /**
     * Add child to the currently selected node.
     *
     * @param child the node the be added
     * @return a DefaultMutableTreeNode object
     */
    public abstract DefaultMutableTreeNode addObject(Object child);

    /**
     * Find the node with name name
     *
     * @param name the node name
     * @return the TreePath for the node, null if the node is not present
     */
    public TreePath findNode(String name) {
        if (name != null) {
            TreeNode root = (TreeNode) jTreeResult.getModel().getRoot();
            if (root.getChildCount() >= 0) {
                for (Enumeration c = root.children(); c.hasMoreElements();) {
                    TreePath path = getPath((TreeNode) c.nextElement());
                    if (name.equals(path.getLastPathComponent().toString())) {
                        return path;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Find the node with name name starting from the passed node
     *
     * @param name the node name
     * @param node the stating node to search
     * @return the TreePath for the node, null if the node is not present
     */
    public TreePath findNode(String name, TreeNode node) {
        if (node.getChildCount() >= 0) {
            for (Enumeration c = node.children(); c.hasMoreElements();) {
                TreePath path = getPath((TreeNode) c.nextElement());
                if (name.equals(path.getLastPathComponent().toString())) {
                    return path;
                }
            }
        }
        return null;
    }

    /**
     * Collapse the tree from the level
     *
     * @param level the levl to collapse the tree
     */
    public void collapsePathFromLevel(int level) {
        TreeNode root = (TreeNode) jTreeResult.getModel().getRoot();
        collapsePathFromLevel(root, level);
        jTreeResult.scrollRowToVisible(1);
    }

    private void collapsePathFromLevel(TreeNode treeNode, int level) {
        TreePath path = getPath(treeNode);
        if (treeNode.getChildCount() >= 0) {
            for (Enumeration c = treeNode.children(); c.hasMoreElements();) {
                TreeNode t = (TreeNode) c.nextElement();
                collapsePathFromLevel(t, level);
            }
        }
        if (path.getPathCount() >= level) {
            jTreeResult.collapsePath(path);
        }
    }

    private TreePath getPath(TreeNode treeNode) {
        List<Object> nodesList = new ArrayList<>();
        if (treeNode != null) {
            nodesList.add(treeNode);
            treeNode = treeNode.getParent();
            while (treeNode != null) {
                nodesList.add(0, treeNode);
                treeNode = treeNode.getParent();
            }
        }
        return nodesList.isEmpty() ? null : new TreePath(nodesList.toArray());
    }

    /**
     * Add child to the currently selected node.
     *
     * @param parent the parent node
     * @param child the node the be added
     * @return a DefaultMutableTreeNode object
     */
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
        return addObject(parent, child, false);
    }

    /**
     * Add child to the currently selected node.
     *
     * @param parent the parent node
     * @param child the node the be added
     * @param shouldBeVisible true is node is visible
     * @return a DefaultMutableTreeNode object
     */
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
            Object child,
            boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode
                = new DefaultMutableTreeNode(child);

        nodes.add(child);

        if (parent == null) {
            parent = rootNode;
        }

        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        treeModel.insertNodeInto(childNode, parent,
                parent.getChildCount());

        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            jTreeResult.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;

    }

    class MyTreeModelListener implements TreeModelListener {

        @Override
        public void treeNodesChanged(TreeModelEvent e) {
            DefaultMutableTreeNode node;
            node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());

            /*
             * If the event lists children, then the changed node is the child
             * of the node we've already gotten. Otherwise, the changed node and
             * the specified node are the same.
             */
            int index = e.getChildIndices()[0];
            //node = (DefaultMutableTreeNode) (node.getChildAt(index));
            node.getChildAt(index);
        }

        @Override
        public void treeNodesInserted(TreeModelEvent e) {
        }

        @Override
        public void treeNodesRemoved(TreeModelEvent e) {
        }

        @Override
        public void treeStructureChanged(TreeModelEvent e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new MyTreeModelListener());
        jTreeResult = new javax.swing.JTree(treeModel);

        jTreeResult.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTreeResult.setAutoscrolls(true);
        jTreeResult.setCellRenderer(new TreeSchemaRenderer());
        jTreeResult.setRootVisible(false);
        jTreeResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTreeResultMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTreeResultMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTreeResultMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeResult);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTreeResultMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeResultMousePressed
        showPopop(evt);
    }//GEN-LAST:event_jTreeResultMousePressed

    private void jTreeResultMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeResultMouseReleased
        showPopop(evt);
    }//GEN-LAST:event_jTreeResultMouseReleased

    private void jTreeResultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeResultMouseClicked
        doubleMouseClicked(evt);
    }//GEN-LAST:event_jTreeResultMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JTree jTreeResult;
    // End of variables declaration//GEN-END:variables
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private Set<Object> nodes;
}
