package org.jbiowhdesktop.component.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * This Class is the basic Popup
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-08-31 15:46:36 +0200
 * (Fri, 31 Aug 2012) $ $LastChangedRevision: 231 $
 *
 * @since Feb 15, 2012
 */
public abstract class BasicPopup extends JPopupMenu implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * The parent component for this popup
     */
    protected JComponent parentComponent;

    /**
     * Create the basic popup
     *
     * @param parentComponent the parent component for this popup
     */
    public BasicPopup(JComponent parentComponent) {
        this.parentComponent = parentComponent;
    }

    /**
     * Create a JMenuItem on this popup
     *
     * @param name the item's name
     * @param keyStroke the KeyStroke which will serve as an accelerator
     * @return a JMenuItem object
     */
    protected JMenuItem createJMenuItem(String name, int keyCode, int modifiers) {
        JMenuItem jMenuItem = new JMenuItem();
        jMenuItem.setText(name);
        if (keyCode != 0) {
            jMenuItem.setAccelerator(KeyStroke.getKeyStroke(keyCode, modifiers));
        }
        jMenuItem.addActionListener(this);
        return jMenuItem;
    }

    /**
     * Create a JMenu on this popup
     *
     * @param name the item's name
     * @return a JMenu object
     */
    protected JMenu createJMenu(String name) {
        JMenu jMenu = new JMenu(name);
        jMenu.setText(name);
        jMenu.addActionListener(this);
        return jMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
