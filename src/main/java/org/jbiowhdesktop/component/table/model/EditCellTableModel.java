package org.jbiowhdesktop.component.table.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * This Class is general editable table model
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since Aug 8, 2012
 */
public abstract class EditCellTableModel extends AbstractTableModel {

    private String[] columnNames;
    private Class[] types;
    private List<List<Boolean>> editCell;
    private boolean[] edit;

    /**
     * Create a table model for the SQL create table process
     *
     * @param columnNames the column's names for the table
     * @param types the class types for each column
     * @param edit the default edit array for each column
     */
    public EditCellTableModel(String[] columnNames, Class[] types, boolean[] edit) {
        this.columnNames = columnNames;
        this.types = types;
        this.edit = edit;
        editCell = new ArrayList<>();
    }

    /**
     * Remove a row
     *
     * @param rowIndex the row index
     */
    public abstract void rmRow(int rowIndex);

    @Override
    public int getColumnCount() {
        if (columnNames == null) {
            return 0;
        }
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnNames == null) {
            return new String();
        }
        if (columnIndex >= 0 && columnIndex < columnNames.length) {
            return columnNames[columnIndex];
        }
        return new String();
    }

    /**
     * Set the column's name
     *
     * @param columnNames the column's name
     */
    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    @Override
    public Class getColumnClass(int c) {
        if (c >= 0 && c < types.length) {
            return types[c];
        }
        return java.lang.Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (rowIndex >= 0 && rowIndex < getRowCount()
                && columnIndex >= 0 && columnIndex < getColumnCount()) {
            if (editCell.isEmpty()) {
                return edit[columnIndex];
            } else {
                List<Boolean> ed = editCell.get(rowIndex);
                if (ed != null && ed.size() > columnIndex) {
                    return ed.get(columnIndex);
                }
            }
        }
        return false;
    }

    /**
     * Set the editable field for the cell
     *
     * @param edit true if the cell is editable
     * @param rowIndex the row index
     * @param columnIndex the column index
     */
    public void setCellEditable(boolean edit, int rowIndex, int columnIndex) {
        if (!editCell.isEmpty()
                && rowIndex >= 0 && rowIndex < getRowCount()
                && columnIndex >= 0 && columnIndex < getColumnCount()) {
            List<Boolean> colValues = editCell.get(rowIndex);
            if (colValues != null) {
                colValues.set(columnIndex, edit);
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        } else if (rowIndex == -1 && columnIndex == -1) {
            for (int row = 0; row < getRowCount(); row++) {
                for (int col = 0; col < getColumnCount(); col++) {
                    setCellEditable(edit, row, col);
                }
            }
        }
    }

    /**
     * Set the editable field for the cell
     *
     * @param edit true if the cell is editable
     * @param rowIndex an array with the rows to modified
     * @param columnIndex an array with the columns to modified
     */
    public void setCellEditable(boolean edit, int[] rowIndex, int[] columnIndex) {
        for (int column : columnIndex) {
            for (int row : rowIndex) {
                setCellEditable(edit, row, column);
            }
        }
    }

    /**
     * Add a edit row
     */
    public void addEditCell() {
        List<Boolean> ed = new ArrayList<>();
        for (Boolean e : edit) {
            ed.add(e);
        }
        editCell.add(ed);
    }

    /**
     * Return the edit matrix for all the cells
     *
     * @return the edit matrix for all the cells
     */
    protected List<List<Boolean>> getEditCell() {
        return editCell;
    }

    /**
     * Set the edit matrix for all the cells
     *
     * @param editCell the edit matrix for all the cells
     */
    public void setEditCell(List<List<Boolean>> editCell) {
        this.editCell = editCell;
    }

    /**
     * Return the default edit array for each column
     *
     * @return the default edit array for each column
     */
    protected boolean[] getEdit() {
        return edit;
    }

    /**
     * Set the default edit array for each column
     *
     * @param edit the default edit array for each column
     */
    public void setEdit(boolean[] edit) {
        this.edit = edit;
    }

    /**
     * Creates the edit list for a number of rows
     *
     * @param rowNumber the number of rows
     */
    protected void createEdit(int rowNumber) {
        editCell = new ArrayList<>();
        for (int i = 0; i < rowNumber; i++) {
            List<Boolean> ed = new ArrayList<>();
            ed.add(false);
            for (int j = 1; j < edit.length; j++) {
                ed.add(edit[j]);
            }
            editCell.add(ed);
        }
    }

    /**
     * Return the class types for each column
     *
     * @return the class types for each column
     */
    public Class[] getTypes() {
        return types;
    }

    /**
     * Set the class types for each column
     *
     * @param types the class types for each column
     */
    public void setTypes(Class[] types) {
        this.types = types;
    }
}
