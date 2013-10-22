package org.jbiowhdesktop.component.table.model;

import java.util.List;

/**
 * This class is the list Table Model
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-08-31 15:46:36 +0200
 * (Fri, 31 Aug 2012) $ $LastChangedRevision: 231 $
 *
 * @since Nov 29, 2011
 */
public class ListTableModel extends EditCellTableModel {

    private List<List<Object>> contents;

    /**
     * Create a list table model
     *
     * @param columnNames the column's names for the table
     * @param datasetList the objects to be showed and handled by the model
     * @param types the class types for each column
     * @param canEdit the default edit array for each column
     */
    public ListTableModel(String[] columnNames, List<List<Object>> datasetList, Class[] types, boolean[] canEdit) {
        super(columnNames, types, canEdit);
        this.contents = datasetList;
    }

    /**
     * Add an extra row of data to table model
     *
     * @param content a list of data objects
     */
    public void addRow(List<Object> content) {
        contents.add(content);
        fireTableDataChanged();
    }

    /**
     * Remove the row of data to table model
     *
     * @param rowIndex the row number
     */
    @Override
    public void rmRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < getRowCount()) {
            contents.remove(rowIndex);
            fireTableDataChanged();
        }
    }

    @Override
    public int getRowCount() {
        return contents.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;

        if (!contents.isEmpty()
                && rowIndex >= 0 && rowIndex < getRowCount()
                && columnIndex >= 0 && columnIndex < getColumnCount()) {
            List<Object> colValues = contents.get(rowIndex);
            if (colValues != null && colValues.size() > columnIndex) {
                result = colValues.get(columnIndex);
            }
        }
        return result;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (!contents.isEmpty()
                && rowIndex >= 0 && rowIndex < getRowCount()
                && columnIndex >= 0 && columnIndex < getColumnCount()) {
            List<Object> colValues = contents.get(rowIndex);
            if (colValues != null) {
                colValues.set(columnIndex, value);
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }
    }

    /**
     * Return the data object
     *
     * @return the data object
     */
    public List<List<Object>> getContents() {
        return contents;
    }

    /**
     * Set the data object
     *
     * @param contents the data object
     */
    public void setContents(List<List<Object>> contents) {
        this.contents = contents;
        fireTableDataChanged();
    }
}
