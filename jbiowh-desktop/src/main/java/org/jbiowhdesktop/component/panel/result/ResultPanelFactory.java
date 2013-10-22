package org.jbiowhdesktop.component.panel.result;

import org.jbiowhdesktop.component.panel.sql.SQLTableViewPanel;

/**
 * This Class is the ResultPanel Factory
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since Feb 15, 2012
 */
public class ResultPanelFactory {

    private ResultPanel resultPanel;
    private SQLTableViewPanel sQLTableViewPanel;
    private static ResultPanelFactory singleton;

    private ResultPanelFactory() {
    }

    /**
     * Return a ResultPanelFactory
     *
     * @return a ResultPanelFactory
     */
    public static synchronized ResultPanelFactory getInstance() {
        if (singleton == null) {
            singleton = new ResultPanelFactory();
        }
        return singleton;
    }

    /**
     * Get the Result Panel
     *
     * @return a ResultPanel object
     */
    public ResultPanel getResultPanel() {
        return resultPanel;
    }

    /**
     * Set the ResultPanel
     *
     * @param resultPanel the ResultPanel
     */
    public void setResultPanel(ResultPanel resultPanel) {
        this.resultPanel = resultPanel;
    }

    /**
     * Get the SQL Table view panel
     *
     * @return a SQL Table view panel
     */
    public SQLTableViewPanel getsQLTableViewPanel() {
        return sQLTableViewPanel;
    }

    /**
     * Set the SQL Table view panel
     *
     * @param sQLTableViewPanel the SQL Table view panel
     */
    public void setsQLTableViewPanel(SQLTableViewPanel sQLTableViewPanel) {
        this.sQLTableViewPanel = sQLTableViewPanel;
    }
}
