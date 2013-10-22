package org.jbiowhdesktop.component.table.model;

import java.util.ArrayList;
import java.util.List;
import org.jbiowhdbms.dbms.sql.table.SQLTable;
import org.jbiowhdbms.dbms.sql.table.SQLTableIndex;
import org.jbiowhdbms.dbms.sql.table.SQLTableIndexes;

/**
 * This Class is the table model to create a table index
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since Aug 8, 2012
 */
public class CreateTableIndexModel extends EditCellTableModel {

    private SQLTableIndexes indexes;

    /**
     * Create a table model for the SQL create table index table
     *
     * @param columnNames the column's names for the table
     * @param contents the SQL table
     * @param types the class types for each column
     * @param edit the default edit array for each column
     */
    public CreateTableIndexModel(String[] columnNames, SQLTable contents, Class[] types, boolean[] edit) {
        super(columnNames, types, edit);
        indexes = contents.getIndexes();
    }

    /**
     * Add an extra row of data to table model
     *
     * @param name the index name
     * @param type the index type
     */
    public void addRow(String name, String type) {
        System.out.println("INDEX Row count: " + getRowCount());
        indexes.addIndex(name, type);
        List<Boolean> ed = new ArrayList<>();
        for (Boolean e : getEdit()) {
            ed.add(e);
        }
        getEditCell().add(ed);
        fireTableDataChanged();
    }

    @Override
    public void rmRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < getRowCount()) {
            indexes.rmIndex(rowIndex);
            getEditCell().remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    @Override
    public int getRowCount() {
        return indexes.getIndexCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        if (!indexes.isEmpty()
                && rowIndex >= 0 && rowIndex < getRowCount()
                && columnIndex >= 0 && columnIndex < getColumnCount()) {
            SQLTableIndex ind = indexes.getIndex(rowIndex);
            if (ind != null) {
                switch (columnIndex) {
                    case 0:
                        return ind.getName();
                    case 1:
                        return ind.getType();
                }
            }
        }
        return result;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (!indexes.isEmpty()
                && rowIndex >= 0 && rowIndex < getRowCount()
                && columnIndex >= 0 && columnIndex < getColumnCount()) {
            SQLTableIndex ind = indexes.getIndex(rowIndex);
            if (ind != null) {
                String key = indexes.getIndexKey(ind);
                switch (columnIndex) {
                    case 0:
                        if (!indexes.containsIndexName((String) value)) {
                            ind.setName((String) value);
                        } else {
                            ind.setName("");
                        }
                        break;
                    case 1:
                        ind.setType((String) value);
                        break;
                }
                if (!key.equals(ind.getName()) && ind.getName() != null && !ind.getName().isEmpty()) {
                    indexes.remove(key);
                    indexes.put(ind.getName(), ind);
                }
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    /**
     * Return the SQL index handler
     * @return the SQL index handler
     */
    public SQLTableIndexes getIndexes() {
        return indexes;
    }
}
