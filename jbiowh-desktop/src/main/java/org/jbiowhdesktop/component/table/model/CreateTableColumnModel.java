package org.jbiowhdesktop.component.table.model;

import org.jbiowhdbms.dbms.sql.table.SQLTable;
import org.jbiowhdbms.dbms.sql.table.SQLTableColumns;
import org.jbiowhdbms.dbms.sql.table.SQLTableIndex;
import org.jbiowhdbms.dbms.sql.table.SQLTableIndexColumn;
import org.jbiowhdbms.dbms.sql.table.SQLTableIndexes;

/**
 * This Class is the table model to create a table index column
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-08-31 15:46:36 +0200
 * (Fri, 31 Aug 2012) $ $LastChangedRevision: 231 $
 *
 * @since Aug 9, 2012
 */
public class CreateTableColumnModel extends EditCellTableModel {

    private String indexName;
    private SQLTableIndex index;
    private SQLTableColumns columns;
    private SQLTableIndexes indexes;

    /**
     * Create a table model for the SQL create table index column table
     *
     * @param columnNames the column's names for the table
     * @param contents the SQL table
     * @param types the class types for each column
     * @param edit the default edit array for each column
     */
    public CreateTableColumnModel(String[] columnNames, SQLTable contents, Class[] types, boolean[] edit) {
        super(columnNames, types, edit);
        index = null;
        columns = contents.getColumns();
        indexes = contents.getIndexes();
    }

    /**
     * Add an extra row of data to table model
     *
     * @param name the column name
     * @param order the column order
     */
    public void addRow(String name, String order) {
        if (indexName != null && !indexName.equals("PRIMARY")
                && index != null
                && getRowCount() < columns.getColumnCount()) {
            index.addColumn(name, order);
            addEditCell();
            fireTableDataChanged();
        }
    }

    @Override
    public void rmRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < getRowCount()) {
            index.rmIndexColumn(rowIndex);
            getEditCell().remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    @Override
    public int getRowCount() {
        if (indexName != null && index != null) {
            return index.getColumns().size();
        }
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (indexName != null && index != null
                && rowIndex >= 0 && rowIndex < getRowCount()
                && columnIndex >= 0 && columnIndex < getColumnCount()) {
            SQLTableIndexColumn col = index.getIndexColumn(rowIndex);
            if (col != null) {
                switch (columnIndex) {
                    case 0:
                        return col.getName();
                    case 1:
                        return col.getOrder();
                    case 2:
                        return col.getLength();
                }
            }
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (indexName != null && index != null
                && rowIndex >= 0 && rowIndex < getRowCount()
                && columnIndex >= 0 && columnIndex < getColumnCount()) {
            SQLTableIndexColumn col = index.getIndexColumn(rowIndex);
            String key = index.getIndexColumnKey(col);
            if (col != null && key != null) {
                switch (columnIndex) {
                    case 0:
                        col.setName((String) value);
                        break;
                    case 1:
                        col.setOrder((String) value);
                        break;
                    case 2:
                        col.setLength((String) value);
                        break;
                }
                if (!key.equals(col.getName()) && col.getName() != null && !col.getName().isEmpty()) {
                    index.remove(key);
                    index.put(col.getName(), col);
                }
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    /**
     * Get the active index's name showed on the Index Column table
     *
     * @return the active index's name showed on the Index Column table
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * Set the active index's name showed on the Index Column table
     *
     * @param indexName the active index's name showed on the Index Column table
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
        if (indexName != null) {
            index = indexes.getIndex(indexName);
            if (index != null) {
                createEdit(index.getIndexColumnCount());
            }
        } else {
            index = null;
        }
        fireTableDataChanged();
    }

    /**
     * Return the index's handler
     *
     * @return the index's handler
     */
    public SQLTableIndexes getIndexes() {
        return indexes;
    }

    /**
     * Return the column's handler
     *
     * @return the column's handler
     */
    public SQLTableColumns getColumns() {
        return columns;
    }
}
