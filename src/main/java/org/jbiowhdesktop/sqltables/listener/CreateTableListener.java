package org.jbiowhdesktop.sqltables.listener;

import javax.swing.JTabbedPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.jbiowhdbms.dbms.sql.table.SQLTableIndex;
import org.jbiowhdesktop.component.table.model.CreateTableIndexModel;
import org.jbiowhdesktop.component.table.model.CreateTableModel;

/**
 * This Class is the Table ModeListener fro the Column tables
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since Aug 1, 2012
 */
public class CreateTableListener implements TableModelListener {

    private javax.swing.JTabbedPane jTabbedPane1;
    private CreateTableIndexModel modelIndex;

    public CreateTableListener(JTabbedPane jTabbedPane1, CreateTableIndexModel modelIndex) {
        this.jTabbedPane1 = jTabbedPane1;
        this.modelIndex = modelIndex;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getSource() instanceof CreateTableModel) {
            CreateTableModel model = (CreateTableModel) e.getSource();

            if (model.getRowCount() != 0) {
                jTabbedPane1.setEnabledAt(1, true);
            } else {
                jTabbedPane1.setEnabledAt(1, false);
            }

            if (e.getFirstRow() != -1) {
                SQLTableIndex index;
                switch (e.getColumn()) {
                    case 0:
                        String name = (String) model.getValueAt(e.getFirstRow(), 0);
                        if (name != null) {
                            if (!name.isEmpty()) {
                                model.setCellEditable(true, new int[]{e.getFirstRow()}, new int[]{1, 2, 3, 4, 5, 6});
                            } else {
                                model.setCellEditable(false, new int[]{e.getFirstRow()}, new int[]{1, 2, 3, 4, 5, 6});
                            }
                        }
                        break;
                    case 2:
                        index = model.getContents().createPrimaryKey();
                        if (index != null) {
                            if (!modelIndex.getIndexes().containsIndexName(index.getName())) {
                                modelIndex.addEditCell();
                            }
                            modelIndex.getIndexes().put(index.getName(), index);
                        } else {
                            if (modelIndex.getIndexes().getIndex("PRIMARY") != null) {
                                modelIndex.rmRow(modelIndex.getIndexes().getIndex("PRIMARY").getNumber());
                            }
                        }
                        model.fireTableDataChanged();
                        modelIndex.fireTableDataChanged();
                    case 4:
                        index = model.getContents().createUniqueKey();
                        if (index != null) {
                            if (!modelIndex.getIndexes().containsIndexName(index.getName())) {
                                modelIndex.addEditCell();
                            }
                            modelIndex.getIndexes().put(index.getName(), index);
                        } else {
                            if (modelIndex.getIndexes().getIndex("pk_UNIQUE") != null) {
                                modelIndex.rmRow(modelIndex.getIndexes().getIndex("pk_UNIQUE").getNumber());
                            }
                        }
                        model.fireTableDataChanged();
                        modelIndex.fireTableDataChanged();
                }
            }
        }
    }
}
