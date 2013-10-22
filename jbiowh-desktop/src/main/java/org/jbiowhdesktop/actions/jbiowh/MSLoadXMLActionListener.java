package org.jbiowhdesktop.actions.jbiowh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jbiowhdesktop.JBioWH;
import org.jbiowhdesktop.component.dialog.LoadMSXMLDialog;

/**
 * This class is the MS Menu ActionListener
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Sep 5, 2013
 */
public class MSLoadXMLActionListener implements ActionListener {

    private JBioWH jbiowh;

    /**
     * Creates a ActionListener for the MS Menu
     *
     * @param jbiowh the main JBioWH windows
     */
    public MSLoadXMLActionListener(JBioWH jbiowh) {
        this.jbiowh = jbiowh;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LoadMSXMLDialog dialog = new LoadMSXMLDialog(jbiowh, true);
        dialog.setLocationRelativeTo(jbiowh);
        dialog.setVisible(true);
    }
}
