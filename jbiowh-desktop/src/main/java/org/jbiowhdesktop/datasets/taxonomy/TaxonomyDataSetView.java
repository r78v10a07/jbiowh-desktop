package org.jbiowhdesktop.datasets.taxonomy;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.taxonomy.entities.Taxonomy;
import org.jbiowhpersistence.datasets.taxonomy.entities.TaxonomyPMID;
import org.jbiowhpersistence.datasets.taxonomy.entities.TaxonomySynonym;
import org.jbiowhpersistence.datasets.taxonomy.entities.TaxonomyUnParseCitation;

/**
 * This Class handled the Taxonomy View
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-10-03 22:11:05 +0200
 * (Wed, 03 Oct 2012) $ $LastChangedRevision: 270 $
 *
 * @since Mar 26, 2012
 */
public class TaxonomyDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public TaxonomyDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        Taxonomy tax = (Taxonomy) dataSetObject;

        basicData.add("TaxId: " + tax.getTaxId() + "\n");
        basicData.add("Parent TaxId: " + tax.getParentTaxId() + "\n");
        basicData.add("Rank: " + tax.getRank() + "\n");
        basicData.add("EMBLCode: " + tax.geteMBLCode() + "\n");
        basicData.add("Division: " + tax.getDivision().getName() + "\n");
        basicData.add("Inherited Division: " + tax.getInheritedDivision() + "\n");
        basicData.add("Gencode: " + tax.getGencode().getName() + "\n");
        basicData.add("Inherited Gencode: " + tax.getInheritedGencode() + "\n");
        basicData.add("MC Gencode: " + tax.getMcgencode().getName() + "\n");
        basicData.add("Inherited MC Gencode: " + tax.getInheritedMCGencode() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        ArrayList string = new ArrayList();
        Taxonomy tax = (Taxonomy) dataSetObject;

        if (!tax.getSynonym().isEmpty()) {
            string.add("Synonym");
        }
        if (!tax.getPmid().isEmpty()) {
            string.add("PMID");
        }
        if (!tax.getUnparse().isEmpty()) {
            string.add("Un Parsed Citations");
        }

        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        Taxonomy tax = (Taxonomy) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Synonym":
                    if (findOrShow) {
                        data.clear();
                        for (TaxonomySynonym dbts : tax.getSynonym()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getSynonym());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "PMID":
                    if (findOrShow) {
                        data.clear();
                        for (TaxonomyPMID dbts : tax.getPmid()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getPmid());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Un Parsed Citations":
                    if (findOrShow) {
                        data.clear();
                        for (TaxonomyUnParseCitation dbts : tax.getUnparse()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getCitId());
                            list.add(dbts.getCitKey());
                            list.add(dbts.getText());
                            list.add(dbts.getUrl());
                            data.add(list);
                        }
                        setjTViewColumn(data, 4);
                    }
                    break;
            }
        }
    }
}
