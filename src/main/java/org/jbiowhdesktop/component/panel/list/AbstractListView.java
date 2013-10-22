package org.jbiowhdesktop.component.panel.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.jbiowhdesktop.actions.tabbedpanel.TabbedPanelCloseActionListener;
import org.jbiowhdesktop.component.panel.tabbedpanel.ClosePanel;
import org.jbiowhdesktop.component.popup.PopupListView;
import org.jbiowhdesktop.component.table.model.ListTableModel;

/**
 * This JPanel is the List View
 *
 * @param <T> entity class to be visualized 
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2013-03-19 09:38:47 +0100 (Tue, 19 Mar 2013) $
 * $LastChangedRevision: 396 $
 * @since Feb 11, 2012
 */
public abstract class AbstractListView<T extends Object> extends javax.swing.JPanel {

    /**
     * Creates new form AbstractListView
     *
     * @param collection the entitly collection to be visualized
     * @param parentComponent the parent JComponent
     */
    public AbstractListView(Collection<T> collection, JComponent parentComponent) {
        this.collection = collection;
        this.parentComponent = parentComponent;
        uniqueKey = UUID.randomUUID();
        initComponents();
    }

    /**
     * Return the field to be visualized from the collection
     *
     * @param data The class <T> to extract the field to be visualized
     * @return a List<Object> with the selected fields
     */
    public abstract List<Object> getCollectionColumns(T data);

    /**
     * Get the collection element from the table row
     *
     * @param row The table row
     * @return a <T> object
     */
    public abstract T getCollectionElementFromTableRow(int row);

    /**
     * Create the Table Header
     *
     * @return a String array with the column header names
     */
    public abstract String[] createJTableHeader();

    /**
     * Create the Class array to build the Table
     *
     * @return a Class array with the column class types
     */
    public Class[] createClass() {
        int length = createJTableHeader().length;
        Class[] classes = new Class[length];
        classes[0] = java.lang.Boolean.class;
        for (int i = 1; i < length; i++) {
            classes[i] = java.lang.Object.class;
        }
        return classes;
    }

    /**
     * Create the Edit array
     *
     * @return a Boolean array with the editable column
     */
    public boolean[] createCanEdit() {
        int length = createJTableHeader().length;
        boolean[] canEdit = new boolean[length];
        canEdit[0] = true;
        for (int i = 1; i < length; i++) {
            canEdit[i] = false;
        }
        return canEdit;
    }

    /**
     * Set the table dimensions
     */
    public void setTableDimensions() {
        ListTableModel model = (ListTableModel) jTable.getModel();
        getjTable().getColumnModel().getColumn(0).setMinWidth(30);
        getjTable().getColumnModel().getColumn(0).setPreferredWidth(30);
        getjTable().getColumnModel().getColumn(0).setMaxWidth(30);
        if (model.getColumnCount() > 2) {
            getjTable().getColumnModel().getColumn(1).setMinWidth(120);
            getjTable().getColumnModel().getColumn(1).setPreferredWidth(120);
            getjTable().getColumnModel().getColumn(1).setMaxWidth(120);
        }
    }

    /**
     * Perform the search over the collection selected field
     *
     * @param s The string to be searched
     * @param data The <T> object to perform the search
     * @return True is the collection fields contains the string s
     */
    public boolean collectionFieldContains(String s, T data) {
        for (Object d : getCollectionColumns(data)) {
            if (d instanceof String) {
                if (((String) d).toUpperCase().contains(s.toUpperCase())) {
                    return true;
                }
            } else if (d instanceof Long) {
                if (((Long) d).toString().contains(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the list with the selected elements
     *
     * @return a Collection<T> with the selected elements
     */
    public Collection<T> getSelectedElements() {
        Collection<T> result = new ArrayList<>();

        ListTableModel model = (ListTableModel) getjTable().getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((Boolean) model.getValueAt(i, 0) == true) {
                result.add(getCollectionElementFromTableRow(i));
            }
        }

        return result;
    }

    /**
     * Return the unique Id for this object
     *
     * @return the unique Id for this object
     */
    public UUID getUniqueKey() {
        return uniqueKey;
    }

    /**
     * Get the collection
     *
     * @return a Collection<T> object
     */
    public Collection<T> getCollection() {
        return collection;
    }

    /**
     * Set the collection
     *
     * @param collection a Collection<T> object
     */
    public void setCollection(Collection<T> collection) {
        this.collection = collection;
    }

    /**
     * Get the Table
     *
     * @return a Table object
     */
    public JTable getjTable() {
        return jTable;
    }

    /**
     * Get the found label
     *
     * @return a JLabel object
     */
    public JLabel getjLFound() {
        return jLFound;
    }

    /**
     * Show the AbstractListView panel
     */
    public void setVisible() {
        if (parentComponent instanceof JTabbedPane) {
            ListTableModel model = (ListTableModel) jTable.getModel();
            if (model.getRowCount() > 0) {
                ((JTabbedPane) parentComponent).addTab("List", this);
                ((JTabbedPane) parentComponent).setTabComponentAt(((JTabbedPane) parentComponent).getTabCount() - 1,
                        new ClosePanel((JTabbedPane) parentComponent,
                        "List", uniqueKey, new TabbedPanelCloseActionListener(parentComponent)));
                ((JTabbedPane) parentComponent).setSelectedIndex(((JTabbedPane) parentComponent).getTabCount() - 1);
            }
        }
    }

    private List<List<Object>> createTableContents() {
        ArrayList<List<Object>> row = new ArrayList<>();
        for (T content : getCollection()) {
            List<Object> column = getCollectionColumns(content);
            row.add(column);
        }
        return row;
    }

    private void showPopop(java.awt.event.MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            (new PopupListView(parentComponent)).show(evt.getComponent(), evt.getX(), evt.getY());
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

        jLFilter = new javax.swing.JLabel();
        jTFilter = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable =         new javax.swing.JTable() {

            @Override
            public String getToolTipText(java.awt.event.MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                int realColumnIndex = convertColumnIndexToModel(colIndex);

                if (realColumnIndex != 0) {
                    TableModel model = getModel();

                    Object tipTemp = model.getValueAt(rowIndex, colIndex);
                    if (tipTemp != null) {
                        if (tipTemp instanceof String) {
                            tip = (String) tipTemp;
                        } else {
                            tip = tipTemp.toString();
                        }
                    }

                } else {
                    tip = super.getToolTipText(e);
                }
                return tip;
            }
        };
        jCheckAll = new javax.swing.JCheckBox();
        jLFound = new javax.swing.JLabel();

        jLFilter.setText("Filter:");
        jLFilter.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jTFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFilterKeyReleased(evt);
            }
        });

        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jTable.setModel(new ListTableModel(createJTableHeader(),
            createTableContents(), createClass(), createCanEdit()));
    jTable.setFillsViewportHeight(true);
    jTable.setGridColor(new java.awt.Color(240, 240, 240));
    jTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    setTableDimensions();
    jTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            jTableMousePressed(evt);
        }
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            jTableMouseReleased(evt);
        }
    });
    jScrollPane1.setViewportView(jTable);

    jCheckAll.setText("Check All");
    jCheckAll.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
    jCheckAll.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jCheckAllMouseClicked(evt);
        }
    });

    jLFound.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLFound.setText("Found " + collection.size() + " elements");
    jLFound.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jCheckAll)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLFound, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLFilter)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTFilter)))
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLFilter)
                .addComponent(jTFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jCheckAll)
                .addComponent(jLFound))
            .addContainerGap())
    );
    }// </editor-fold>//GEN-END:initComponents

    private void jTFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFilterKeyReleased
        ListTableModel model = (ListTableModel) jTable.getModel();
        ArrayList<List<Object>> objects = new ArrayList<>();
        for (T data : collection) {
            if (collectionFieldContains(jTFilter.getText(), data)) {
                objects.add(getCollectionColumns(data));
            }
        }
        jLFound.setText("Found " + objects.size() + " elements");
        model.setContents(objects);
    }//GEN-LAST:event_jTFilterKeyReleased

    private void jCheckAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckAllMouseClicked
        ListTableModel model = (ListTableModel) jTable.getModel();
        if (jCheckAll.isSelected()) {
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(true, i, 0);
            }
        } else {
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(false, i, 0);
            }
        }
    }//GEN-LAST:event_jCheckAllMouseClicked

    private void jTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMousePressed
        showPopop(evt);
    }//GEN-LAST:event_jTableMousePressed

    private void jTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseReleased
        showPopop(evt);
    }//GEN-LAST:event_jTableMouseReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckAll;
    private javax.swing.JLabel jLFilter;
    private javax.swing.JLabel jLFound;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFilter;
    private javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables
    private Collection<T> collection;
    protected JComponent parentComponent;
    private UUID uniqueKey;
}
