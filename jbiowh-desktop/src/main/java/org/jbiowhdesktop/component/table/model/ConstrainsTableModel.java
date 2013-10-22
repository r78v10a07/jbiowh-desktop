package org.jbiowhdesktop.component.table.model;

import java.util.Collection;
import java.util.List;
import org.jbiowhcore.utility.constrains.JPLConstrains;

/**
 * This class is the Table Model based on Constrains
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Mar 13, 2013
 */
public class ConstrainsTableModel extends EditCellTableModel {

    private JPLConstrains constrains;

    public ConstrainsTableModel(JPLConstrains constrains, String[] columnNames, Class[] types, boolean[] edit) {
        super(columnNames, types, edit);
        this.constrains = constrains;
    }

    public JPLConstrains getConstrains() {
        return constrains;
    }

    public void setConstrains(JPLConstrains constrains) {
        this.constrains = constrains;
    }

    /**
     * Add an extra row of data to table model
     *
     * @param content a list of data objects
     */
    public void addRow(List<Object> content) {
        if (content.size() != 3) {
            throw new IndexOutOfBoundsException("To add a Constrains the list should have 3 elements");
        }
        constrains.getConstrains().add(content.get(0));
        constrains.getOperation().add((String) content.get(1));
        constrains.getExtOperation().add((String) content.get(2));
        System.out.println(constrains);
        fireTableDataChanged();
    }
    
    public Object getDataAt(int rowIndex){
        if (rowIndex >= 0 && rowIndex < getRowCount()) {
            return constrains.getConstrains().get(rowIndex);
        }
        return null;
    }

    @Override
    public void rmRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < getRowCount()) {
            constrains.getConstrains().remove(rowIndex);
            constrains.getOperation().remove(rowIndex);
            constrains.getExtOperation().remove(rowIndex);
            fireTableDataChanged();
        }
    }

    @Override
    public int getRowCount() {
        return constrains.getConstrains().size();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex >= 0 && rowIndex < getRowCount()
                && columnIndex >= 1 && columnIndex < getColumnCount()) {
            switch (columnIndex) {
                case 1:
                    constrains.getOperation().set(rowIndex, (String) aValue);
                case 2:
                    constrains.getExtOperation().set(rowIndex, (String) aValue);
            }
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        if (rowIndex >= 0 && rowIndex < getRowCount()
                && columnIndex >= 0 && columnIndex < getColumnCount()) {

            String type = "Constrain";
            int size = 1;
            Object data = constrains.getConstrains().get(rowIndex);

            if (data instanceof Collection) {
                if (!((Collection) data).isEmpty()) {
                    type = ((Collection) data).iterator().next().getClass().getName();
                    type = type.substring(type.lastIndexOf(".") + 1);
                    size = ((Collection) data).size();
                } else {
                    type = "Empty";
                    size = 1;
                }
            }
            switch (columnIndex) {
                case 0:
                    if (size == 1) {
                        return type;
                    } else if (size > 1) {
                        return "Multiples " + type;
                    }
                case 1:
                    return constrains.getOperation().get(rowIndex);
                case 2:
                    return constrains.getExtOperation().get(rowIndex);

            }
        }
        return result;
    }    
}
