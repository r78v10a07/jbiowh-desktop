package org.jbiowhdesktop.component.popup;

import java.awt.event.ActionEvent;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import org.jbiowhdesktop.component.panel.sql.SQLEditorPanel;

/**
 * This Class is
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since Jun 26, 2012
 */
public class PopupSQLEditorTables extends BasicPopup {

    public PopupSQLEditorTables(JComponent parentComponent, Set<String> tables) {
        super(parentComponent);
        addItems(tables);
    }

    private void addItems(Set<String> tables) {
        for (String table : tables) {
            add(createJMenuItem(table, 0, 0));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JMenuItem) {
            JMenuItem jMenuItem = (JMenuItem) source;
            if (parentComponent instanceof SQLEditorPanel) {
                ((SQLEditorPanel) parentComponent).insertAutoComplete(jMenuItem.getText());
            }
        }
    }
}
