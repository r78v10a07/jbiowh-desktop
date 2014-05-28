package org.jbiowhdesktop.actions.jbiowh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhdbms.dbms.JBioWHDBMSSingleton;
import org.jbiowhdesktop.JBioWH;
import org.jbiowhdesktop.component.panel.result.ResultPanelFactory;
import org.jbiowhpersistence.utils.entitymanager.JBioWHPersistence;

/**
 * This class is the close action for the main window
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2013-09-06 14:21:09 +0200
 * (Fri, 06 Sep 2013) $ $LastChangedRevision: 664 $
 *
 * @since Sep 25, 2012
 */
public class CloseActionListener implements ActionListener {

    private JBioWH jbiowh;

    /**
     * Creates the close action for the main window
     *
     * @param jbiowh the main window object
     */
    public CloseActionListener(JBioWH jbiowh) {
        this.jbiowh = jbiowh;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (JBioWHPersistence.getInstance().getWhdbmsFactory() != null) {
            try {
                ResultPanelFactory.getInstance().getsQLTableViewPanel().removeNode(JBioWHPersistence.getInstance().getMainURLParsed());
                JBioWHPersistence.getInstance().closeSchema(false);
                JBioWHDBMSSingleton.getInstance().closeConnection();
                if (JBioWHDBMSSingleton.getInstance().getWhdbmsFactory() == null || !JBioWHDBMSSingleton.getInstance().getWhdbmsFactory().isConnOpen()) {
                    jbiowh.getjMOpenSQLScript().setEnabled(false);
                    jbiowh.getjMOpen().setEnabled(true);
                    jbiowh.getjMClose().setEnabled(false);
                    jbiowh.getjBOpen().setVisible(true);
                    jbiowh.getjBClose().setVisible(false);
                    jbiowh.getjBSearch().setVisible(false);
                    jbiowh.getjMTabs().setEnabled(false);
                    jbiowh.getjMSQL().setEnabled(false);
                    jbiowh.getjMEdit().setEnabled(false);
                    jbiowh.getjMMSTool().setEnabled(false);
                    jbiowh.getjTabbedPane2().removeAll();
                }
            } catch (SQLException ex) {
                VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
                VerbLogger.getInstance().log(this.getClass(), "\tSQL: Exception code for this SQLException object: " + ex.getErrorCode());
                VerbLogger.getInstance().log(this.getClass(), "\tSQL: The SQLState for this object: " + ex.getSQLState());
                VerbLogger.getInstance().log(this.getClass(), "\tSQL: Exception code for this object: " + ex.getLocalizedMessage());
                VerbLogger.getInstance().log(this.getClass(), "\tSQL: Exception code for this object: " + ex.getMessage());
                VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
                int type = JOptionPane.ERROR_MESSAGE;
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", type);
            }
        }
    }
}
