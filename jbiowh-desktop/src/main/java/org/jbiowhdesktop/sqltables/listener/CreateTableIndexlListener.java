package org.jbiowhdesktop.sqltables.listener;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.jbiowhdesktop.component.table.model.CreateTableColumnModel;
import org.jbiowhdesktop.component.table.model.CreateTableIndexModel;

/**
 * This Class is the Table ModeListener fro the Index tables
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since Aug 2, 2012
 */
public class CreateTableIndexlListener implements TableModelListener {

    private CreateTableColumnModel indexColumnModel;

    public CreateTableIndexlListener(CreateTableColumnModel indexColumnModel) {
        this.indexColumnModel = indexColumnModel;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getSource() instanceof CreateTableIndexModel) {
            CreateTableIndexModel model = (CreateTableIndexModel) e.getSource();

            if (e.getFirstRow() != -1) {
                switch (e.getColumn()) {
                    case 0:
                        String name = (String) model.getValueAt(e.getFirstRow(), 0);
                        if (name != null) {
                            if (!name.isEmpty() && (!name.equals("PRIMARY") && !name.equals("pk_UNIQUE") )) {
                                model.setCellEditable(true, e.getFirstRow(), 1);
                            } else {
                                model.setCellEditable(false, e.getFirstRow(), 1);
                            }
                            indexColumnModel.setIndexName(name);
                        }
                        break;
                    case 1:
                        break;
                }
            }
        }
    }
}
