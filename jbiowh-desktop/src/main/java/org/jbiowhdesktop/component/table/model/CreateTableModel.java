package org.jbiowhdesktop.component.table.model;

import org.jbiowhdbms.dbms.sql.table.SQLTable;
import org.jbiowhdbms.dbms.sql.table.SQLTableColumn;

/**
 * This Class is the table model to create a table
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since Aug 7, 2012
 */
public class CreateTableModel extends EditCellTableModel {

    private SQLTable contents;

    /**
     * Create a table model for the SQL create table process
     *
     * @param columnNames the column's names for the table
     * @param contents the SQL table
     * @param types the class types for each column
     * @param edit the default edit array for each column
     */
    public CreateTableModel(String[] columnNames, SQLTable contents, Class[] types, boolean[] edit) {
        super(columnNames, types, edit);
        this.contents = contents;
    }

    /**
     * Add an extra row of data to table model
     *
     * @param name the column's name
     * @param type the column's type
     * @param PK true if the column is PK
     * @param NN true if the column is NN
     * @param UQ true if the column is UQ
     * @param AI true if the column is AI
     * @param def the default value for the column
     */
    public void addRow(String name, String type, boolean PK, boolean NN, boolean UQ, boolean AI, Object def) {
        contents.getColumns().addColumn(name, type, PK, NN, UQ, AI, def);
        addEditCell();
        fireTableDataChanged();
    }

    @Override
    public void rmRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < getRowCount()) {
            contents.getColumns().rmColumn(rowIndex);
            getEditCell().remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    @Override
    public int getRowCount() {
        return contents.getColumns().getColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        if (!contents.getColumns().isEmpty()
                && rowIndex >= 0 && rowIndex < getRowCount()
                && columnIndex >= 0 && columnIndex < getColumnCount()) {
            SQLTableColumn col = contents.getColumns().getColumn(rowIndex);
            if (col != null) {
                switch (columnIndex) {
                    case 0:
                        return col.getName();
                    case 1:
                        return col.getType();
                    case 2:
                        return col.isPK();
                    case 3:
                        return col.isNN();
                    case 4:
                        return col.isUQ();
                    case 5:
                        return col.isAI();
                    case 6:
                        return col.getDef();
                }
            }
        }
        return result;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (!contents.getColumns().isEmpty()
                && rowIndex >= 0 && rowIndex < getRowCount()
                && columnIndex >= 0 && columnIndex < getColumnCount()) {
            SQLTableColumn col = contents.getColumns().getColumn(rowIndex);
            if (col != null) {
                String key = contents.getColumns().getColumnKey(col);
                switch (columnIndex) {
                    case 0:
                        if (!contents.getColumns().containsColumnName((String) value)) {
                            col.setName((String) value);
                        } else {
                            col.setName(null);
                        }
                        break;
                    case 1:
                        col.setType((String) value);
                        break;
                    case 2:
                        col.setPK((boolean) value);
                        break;
                    case 3:
                        col.setNN((boolean) value);
                        break;
                    case 4:
                        col.setUQ((boolean) value);
                        break;
                    case 5:
                        col.setAI((boolean) value);
                        break;
                    case 6:
                        if (value instanceof String) {
                            if (((String) value).matches("\\d+")) {
                                col.setDef(Integer.parseInt((String) value));
                            } else if (((String) value).matches("\\d*\\.\\d*")) {
                                col.setDef(Float.parseFloat((String) value));
                            } else if (!((String) value).isEmpty()) {
                                col.setDef(value);
                            } else {
                                col.setDef(null);
                            }
                        }
                        break;
                }
                if (!key.equals(col.getName()) && col.getName() != null && !col.getName().isEmpty()) {
                    contents.getColumns().remove(key);
                    contents.getColumns().put(col.getName(), col);
                }
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    /**
     * Return the SQLTable object
     *
     * @return the SQLTable object
     */
    public SQLTable getContents() {
        return contents;
    }
}
