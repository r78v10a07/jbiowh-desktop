package org.jbiowhdesktop.sqltables.listener;

import javax.swing.JComboBox;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.jbiowhdbms.dbms.sql.table.SQLTableColumn;
import org.jbiowhdbms.dbms.sql.table.SQLTableIndex;
import org.jbiowhdesktop.component.table.model.CreateTableColumnModel;

/**
 * This Class is the Table ModeListener fro the Index Column tables
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since Aug 2, 2012
 */
public class CreateTableColumnListener implements TableModelListener {

    private javax.swing.JComboBox jTColumnsIndex;

    public CreateTableColumnListener(JComboBox jTColumnsIndex) {
        this.jTColumnsIndex = jTColumnsIndex;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getSource() instanceof CreateTableColumnModel) {
            CreateTableColumnModel model = (CreateTableColumnModel) e.getSource();
            if (e.getFirstRow() != -1) {
                if (e.getColumn() == 0 && model.isCellEditable(e.getFirstRow(), 0)) {
                    String name = (String) model.getValueAt(e.getFirstRow(), 0);
                    if (name != null && !name.isEmpty()) {
                        upDateColumnsIndex(model);
                        model.setCellEditable(false, e.getFirstRow(), 0);
                    }
                }
            }
        }
    }

    private void upDateColumnsIndex(CreateTableColumnModel model) {

        jTColumnsIndex.removeAllItems();
        if (model.getIndexName() != null) {
            SQLTableIndex ind = model.getIndexes().getIndex(model.getIndexName());
            if (ind != null) {
                for (SQLTableColumn col : model.getColumns().getColumnSet()) {
                    boolean flags = false;
                    for (String name : ind.getColumns().keySet()) {
                        if (col.getName().equals(name)) {
                            flags = true;
                        }
                    }
                    if (!flags) {
                        jTColumnsIndex.addItem(col.getName());
                    }
                }
            }
        }
    }
}
