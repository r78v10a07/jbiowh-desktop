package org.jbiowhdesktop.component.table.model.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class is the ListTableModel2 data
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since Feb 13, 2012
 */
public class ExtendedModelData {

    private List displayed;
    private List data;

    /**
     * Create the extended data object
     */
    public ExtendedModelData() {
        displayed = new ArrayList();
        data = new ArrayList();
    }

    /**
     * Add a data
     *
     * @param displayed the data to be displayed on the table
     * @param data the original data
     */
    public void add(Object displayed, Object data) {
        this.displayed.add(displayed);
        this.data.add(data);
    }

    /**
     * Get the data list
     *
     * @return the data list
     */
    public List getData() {
        return data;
    }

    /**
     * Set the data list
     *
     * @param data the data list
     */
    public void setData(List data) {
        this.data = data;
    }

    /**
     * Get the displayed list
     *
     * @return the displayed list
     */
    public List getDisplayed() {
        return displayed;
    }

    /**
     * Set the displayed list
     *
     * @param displayed the displayed list
     */
    public void setDisplayed(List displayed) {
        this.displayed = displayed;
    }
}
