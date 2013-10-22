package org.jbiowhdesktop.component.popup;

import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.jbiowhdesktop.component.table.model.CreateTableIndexModel;
import org.jbiowhdesktop.component.table.model.EditCellTableModel;

/**
 * This Class is the Create table dialog popup
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since Aug 3, 2012
 */
public class PopupCreateTable extends BasicPopup {

    private JTable table;

    /**
     * Create the popup on the CreateTable Dialog
     *
     * @param jTColumns the column's table
     * @param jTIndex the index's table
     * @param jTIndexColumn the index column's table
     * @param indexes the index map
     * @param parentComponent the parent component for this popup
     */
    public PopupCreateTable(JComponent parentComponent, JTable table) {
        super(parentComponent);
        this.table = table;
        addItems();
    }

    private void addItems() {
        TableModel model = table.getModel();
        Object obj = model.getValueAt(table.getSelectedRow(), 0);
        if (obj != null) {
            if (table.getModel() instanceof CreateTableIndexModel) {
                if (!obj.equals("PRIMARY")) {
                    add(createJMenuItem("Remove", 0, 0));
                }
            } else {
                add(createJMenuItem("Remove", 0, 0));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JMenuItem) {
            EditCellTableModel model = (EditCellTableModel) table.getModel();
            model.rmRow(table.getSelectedRow());
        }
    }
}
