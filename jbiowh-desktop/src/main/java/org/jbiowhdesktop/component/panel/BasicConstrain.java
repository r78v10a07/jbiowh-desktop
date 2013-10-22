package org.jbiowhdesktop.component.panel;

import java.util.ArrayList;
import java.util.List;
import org.jbiowhdesktop.component.table.model.ConstrainsTableModel;

/**
 * This Class provides the basic constrain functions
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2013-03-19 09:38:47 +0100 (Tue, 19 Mar 2013) $
 * $LastChangedRevision: 396 $
 * @since Feb 25, 2012
 */
public abstract class BasicConstrain extends javax.swing.JPanel {

    /**
     * This table shows the Constrains
     */
    protected javax.swing.JTable jTConstrains;

    /**
     * Add a Constrain object to the Constrain Table
     * @param data the Constrain object to add 
     */
    public void addConstrains(Object data) {        
        if (data == null){
            return;
        }
        ConstrainsTableModel model = (ConstrainsTableModel) jTConstrains.getModel();
        
        List list = new ArrayList();
        list.add(data);
        list.add("IN");
        list.add("AND");

        model.addRow(list);
    }
}
