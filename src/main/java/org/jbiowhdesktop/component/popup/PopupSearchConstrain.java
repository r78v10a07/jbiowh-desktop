package org.jbiowhdesktop.component.popup;

import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import org.jbiowhdesktop.component.table.model.ConstrainsTableModel;
import org.jbiowhdesktop.datasets.ListViewProxy;

/**
 * This Class is the Constrains Table Popup Menu on SearchPanel
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2013-09-06 14:21:09 +0200 (Fri, 06 Sep 2013) $
 * $LastChangedRevision: 664 $
 * @since Feb 25, 2012
 */
public class PopupSearchConstrain extends BasicPopup {

    public PopupSearchConstrain(JComponent parentComponent) {
        super(parentComponent);
        addItems();
    }

    private void addItems() {
        add(createJMenuItem("View", 0, 0));
        add(createJMenuItem("Remove", 0, 0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JMenuItem) {
            JMenuItem jMenuItem = (JMenuItem) source;
            if (this.getInvoker() instanceof JTable) {
                JTable table = (JTable) this.getInvoker();
                ConstrainsTableModel model = (ConstrainsTableModel) table.getModel();
                switch (jMenuItem.getText()) {
                    case "Remove":
                        if (table.getSelectedRow() >= 0 && table.getSelectedRow() < table.getRowCount()) {
                            model.rmRow(table.getSelectedRow());
                        }
                        break;
                    case "View":
                        if (table.getSelectedRow() >= 0 && table.getSelectedRow() < table.getRowCount()) {
                            ListViewProxy.getInstance().setVisible(model.getDataAt(table.getSelectedRow()), parentComponent);
                        }
                        break;
                }
            }
        }
    }
}
