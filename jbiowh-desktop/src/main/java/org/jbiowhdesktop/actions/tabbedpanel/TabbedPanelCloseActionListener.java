package org.jbiowhdesktop.actions.tabbedpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * This Class is handled the ActionListernet from close the TabbedPanel
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-08-31 15:46:36 +0200
 * (Fri, 31 Aug 2012) $ $LastChangedRevision: 270 $
 *
 * @since Feb 10, 2012
 */
public class TabbedPanelCloseActionListener implements ActionListener {

    private JTabbedPane jTabbedPane2;

    public TabbedPanelCloseActionListener(JComponent jTabbedPane2) {
        if (jTabbedPane2 instanceof JTabbedPane) {
            this.jTabbedPane2 = (JTabbedPane) jTabbedPane2;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        String s1 = btn.getActionCommand();
        for (int i = 0; i < jTabbedPane2.getTabCount(); i++) {
            boolean aFlag = false;
            JPanel pnl = (JPanel) jTabbedPane2.getTabComponentAt(i);
            for (int j = 0; j < pnl.getComponentCount(); j++) {
                if (pnl.getComponent(j) instanceof JButton) {
                    btn = (JButton) pnl.getComponent(j);
                    String s2 = btn.getActionCommand();
                    if (s1.equals(s2)) {
                        jTabbedPane2.removeTabAt(i);
                        aFlag = true;
                        break;
                    }
                }
            }
            if (aFlag) {
                break;
            }
        }
    }
}
