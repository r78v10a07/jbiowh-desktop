package org.jbiowhdesktop.actions.jbiowh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhdbms.dbms.JBioWHDBMSSingleton;
import org.jbiowhdbms.dbms.JBioWHDBMS;
import org.jbiowhdesktop.JBioWH;
import org.jbiowhdesktop.OpenWHDB;
import org.jbiowhdesktop.component.panel.result.ResultPanelFactory;

/**
 * This class is the open action for the main window
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2013-09-06 14:21:09 +0200
 * (Fri, 06 Sep 2013) $ $LastChangedRevision: 664 $
 *
 * @since Sep 25, 2012
 */
public class OpenActionListener implements ActionListener {

    private JBioWH jbiowh;
    private boolean createSchema;
    private boolean isMain;

    /**
     * Create the open action for the main window
     *
     * @param jbiowh the main window object
     * @param createSchema true to create the schema on the DBMS
     */
    public OpenActionListener(JBioWH jbiowh, boolean createSchema) {
        this.jbiowh = jbiowh;
        this.createSchema = createSchema;
        isMain = true;
    }

    /**
     * Create the open action for the main window
     *
     * @param jbiowh the main window object
     * @param createSchema true to create the schema on the DBMS
     * @param isMain
     */
    public OpenActionListener(JBioWH jbiowh, boolean createSchema, boolean isMain) {
        this.jbiowh = jbiowh;
        this.createSchema = createSchema;
        this.isMain = isMain;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        OpenWHDB open = new OpenWHDB(jbiowh, true, createSchema);
        open.toFront();
        open.setLocationRelativeTo(jbiowh);
        open.setVisible(true);
        if (!open.isCanceled()) {
            JBioWHDBMS factory = JBioWHDBMSSingleton.getInstance().getWhdbmsFactory(open.getUrl());
            if (factory != null) {
                try {
                    if (factory.isConnOpen()) {
                        jbiowh.getjMOpenSQLScript().setEnabled(true);
                        jbiowh.getjMClose().setEnabled(true);
                        jbiowh.getjBClose().setVisible(true);
                        jbiowh.getjBSearch().setVisible(true);
                        jbiowh.getjMTabs().setEnabled(true);
                        jbiowh.getjMSQL().setEnabled(true);
                        jbiowh.getjMEdit().setEnabled(true);
                        jbiowh.getjMMSTool().setEnabled(true);
                        if (!createSchema) {
                            ResultPanelFactory.getInstance().getsQLTableViewPanel().addObject(factory.getMainURLParsed(), factory);
                            ResultPanelFactory.getInstance().getsQLTableViewPanel().collapsePathFromLevel(2);
                            if (open.isMain()) {
                                jbiowh.viewHomeTabbedPanel(factory);
                            }
                        }
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
            } else {
                System.out.println("Is null");
            }
        }
    }
}
