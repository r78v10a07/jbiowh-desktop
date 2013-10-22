package org.jbiowhdesktop.datasets.disease;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.disease.omim.entities.OMIM;
import org.jbiowhpersistence.datasets.disease.omim.entities.OMIMTI;

/**
 * This Class handled the OMIM View
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since Jul 25, 2012
 */
public class OMIMDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public OMIMDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        OMIM omim = (OMIM) dataSetObject;

        basicData.add("Name: " + omim.getOmimId() + "\n");
        if (!omim.getOmimTIs().isEmpty()) {
            basicData.add("Title: \n");
            for (OMIMTI ti : omim.getOmimTIs().values()) {
                basicData.add("\t" + ti.getOMIMTIPK().getTi() + "\n");
            }
        }
        basicData.add("TEXT: " + omim.getTx() + "\n");
        return basicData;
    }

    @Override
    public List getLinks() {
        List string = new ArrayList();
        OMIM omim = (OMIM) dataSetObject;

        if (!omim.getGeneInfos().isEmpty()) {
            string.add("Gene");
        }

        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        OMIM omim = (OMIM) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Gene":
                    getGeneLink(findOrShow, omim.getGeneInfos());
                    break;
            }
        }
    }
}
