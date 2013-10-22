package org.jbiowhdesktop.component.table.model;

import java.util.ArrayList;
import java.util.List;
import org.jbiowhdesktop.component.table.model.data.ExtendedModelData;

/**
 * This Class is the Table Model based on List
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-08-31 15:46:36 +0200
 * (Fri, 31 Aug 2012) $ $LastChangedRevision: 231 $
 *
 * @since Feb 13, 2012
 */
public class ExtendedListTableModel extends EditCellTableModel {

    private List<ExtendedModelData> contents;

    /**
     * Create the ExtendedListTable model for complex tables
     *
     * @param columnNames the column names
     * @param contents the objects to be showed
     * @param types the objects's class types
     * @param canEdit true if the objects can be edited
     */
    public ExtendedListTableModel(String[] columnNames, List<List<Object>> contents, Class[] types, boolean[] canEdit) {
        super(columnNames, types, canEdit);
        createContents(contents);
    }

    /**
     * Add an extra row of data to table model
     *
     * @param content a list of data objects
     */
    public void addRow(List<Object> content) {
        ExtendedModelData data = new ExtendedModelData();
        for (Object o : content) {
            if (o instanceof List) {
                List l = (List) o;
                data.add(l.get(0), l.get(1));
            } else {
                data.add(o, o);
            }
        }
        this.contents.add(data);
        fireTableDataChanged();
    }

    @Override
    public void rmRow(int rowIndex) {
        contents.remove(rowIndex);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return contents.size();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (!contents.isEmpty() && rowIndex >= 0 && columnIndex >= 0) {
            ExtendedModelData colValues = contents.get(rowIndex);
            if (colValues != null) {
                if (colValues.getDisplayed().get(columnIndex).equals(
                        colValues.getData().get(columnIndex))) {
                    colValues.getData().set(columnIndex, aValue);
                }
                colValues.getDisplayed().set(columnIndex, aValue);

                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;

        if (!contents.isEmpty() && rowIndex >= 0 && columnIndex >= 0) {
            ExtendedModelData colValues = contents.get(rowIndex);
            if (colValues != null && getColumnCount() > columnIndex) {
                result = colValues.getDisplayed().get(columnIndex);
            }
        }

        return result;
    }

    /**
     * Get the data at selected position
     *
     * @param rowIndex row number
     * @param columnIndex column number
     * @return a data Object
     */
    public Object getDataAt(int rowIndex, int columnIndex) {
        Object result = null;

        if (!contents.isEmpty() && rowIndex >= 0 && columnIndex >= 0) {
            ExtendedModelData colValues = contents.get(rowIndex);
            if (colValues != null && getColumnCount() > columnIndex) {
                result = colValues.getData().get(columnIndex);
            }
        }

        return result;
    }

    /**
     * Get the ExtendedModelData data in the column in a List form
     *
     * @param columnIndex the column that should be add to the data
     * @return a List object
     */
    public List getContentsData(int columnIndex) {
        List contentsData = new ArrayList();

        for (ExtendedModelData data : contents) {
            contentsData.add(data.getData().get(columnIndex));
        }
        return contentsData;
    }

    /**
     * Get the extended data
     *
     * @return a ExtendedModelData list
     */
    public List<ExtendedModelData> getContents() {
        return contents;
    }

    private void createContents(List<List<Object>> contents) {
        this.contents = new ArrayList<>();

        for (List m : contents) {
            addRow(m);
        }
    }
}
