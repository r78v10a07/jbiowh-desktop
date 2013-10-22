package org.jbiowhdesktop.component.popup;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.text.DefaultEditorKit;
import org.jbiowhdesktop.component.panel.sql.SQLEditorPanel;

/**
 * This Class is the SQL Editor popup
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since May 28, 2012
 */
public class PopupSQLEditor extends BasicPopup {

    public PopupSQLEditor(JComponent parentComponent) {
        super(parentComponent);
        addItems();
    }

    private void addItems() {
        add(createJMenuItem("Undo", KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        add(createJMenuItem("Redo", KeyEvent.VK_Z, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
        add(new javax.swing.JSeparator());
        add(createJMenuItem("Cut", KeyEvent.VK_X, InputEvent.CTRL_MASK));
        add(createJMenuItem("Copy", KeyEvent.VK_C, InputEvent.CTRL_MASK));
        add(createJMenuItem("Paste", KeyEvent.VK_V, InputEvent.CTRL_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JMenuItem) {
            JMenuItem jMenuItem = (JMenuItem) source;
            if (this.getInvoker() instanceof JTextPane) {
                JTextPane textPane = (JTextPane) this.getInvoker();
                Container o = textPane.getParent();
                while (o != null) {
                    if (o instanceof SQLEditorPanel) {
                        SQLEditorPanel sqlEditor = (SQLEditorPanel) o;
                        switch (jMenuItem.getText()) {
                            case "Undo":
                                sqlEditor.getUndoAction().actionPerformed(e);
                                break;
                            case "Redo":
                                sqlEditor.getRedoAction().actionPerformed(e);
                                break;
                            case "Cut":
                                for (Action a : sqlEditor.getjSQLEditor().getActions()) {
                                    if (a.getValue(Action.NAME).equals(DefaultEditorKit.cutAction)) {
                                        a.actionPerformed(e);
                                        break;
                                    }
                                }
                                break;
                            case "Copy":
                                for (Action a : sqlEditor.getjSQLEditor().getActions()) {
                                    if (a.getValue(Action.NAME).equals(DefaultEditorKit.copyAction)) {
                                        a.actionPerformed(e);
                                        break;
                                    }
                                }
                                break;
                            case "Paste":
                                for (Action a : sqlEditor.getjSQLEditor().getActions()) {
                                    if (a.getValue(Action.NAME).equals(DefaultEditorKit.pasteAction)) {
                                        a.actionPerformed(e);
                                        break;
                                    }
                                }
                                break;
                        }
                        break;
                    }
                    o = o.getParent();
                }
            }
        }
    }
}
