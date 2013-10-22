package org.jbiowhdesktop.datasets.gene.genebank;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.gene.genebank.entities.GeneBank;
import org.jbiowhpersistence.datasets.gene.genebank.entities.GeneBankAccession;
import org.jbiowhpersistence.datasets.gene.genebank.entities.GeneBankCDS;
import org.jbiowhpersistence.datasets.gene.genebank.entities.GeneBankFeatures;

/**
 * This class handled the GeneBank View
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2013-07-26 09:54:20 +0200 (Fri, 26 Jul 2013) $ $LastChangedRevision: 638 $
 *
 * @since May 9, 2013
 */
public class GeneBankDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public GeneBankDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        GeneBank gene = (GeneBank) dataSetObject;

        basicData.add("LocusName: " + gene.getLocusName() + "\n");
        basicData.add("Taxonomy: " + gene.getTaxonomy().getTaxonomySynonym() + "\n");
        basicData.add("Seq Lengh: " + gene.getSeqLengh() + "\n");
        basicData.add("Mol Type: " + gene.getMolType() + "\n");
        basicData.add("Division: " + gene.getDivision() + "\n");
        basicData.add("Mod date: " + (new SimpleDateFormat("yyyy-MM-dd")).format(gene.getModDate()) + "\n");
        basicData.add("Definition: " + gene.getDefinition() + "\n");
        basicData.add("Version: " + gene.getVersion() + "\n");
        basicData.add("TaxId: " + gene.getTaxId() + "\n");
        basicData.add("Source: " + gene.getSource() + "\n");
        basicData.add("Organism: " + gene.getOrganism() + "\n");
        basicData.add("Location: " + gene.getLocation());

        return basicData;
    }

    @Override
    public List getLinks() {
        ArrayList string = new ArrayList();
        GeneBank gene = (GeneBank) dataSetObject;

        if (!gene.getGeneBankAccessions().isEmpty()) {
            string.add("Accessions");
        }
        if (!gene.getGeneBankFeatureses().isEmpty()) {
            string.add("Features");
        }
        if (!gene.getGeneBankCDSs().isEmpty()) {
            string.add("CDS");
        }

        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        GeneBank gene = (GeneBank) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Accessions":
                    if (findOrShow) {
                        data.clear();
                        for (GeneBankAccession dbts : gene.getGeneBankAccessions()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getAccession());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Features":
                    if (findOrShow) {
                        data.clear();
                        for (GeneBankFeatures dbts : gene.getGeneBankFeatureses()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getKeyName());
                            list.add(dbts.getLocation());
                            if (dbts.getProduct() != null) {
                                list.add(dbts.getProduct());
                            } else {
                                list.add("");
                            }
                            if (dbts.getGene() != null) {
                                list.add(dbts.getGene());
                            } else {
                                list.add("");
                            }
                            data.add(list);
                        }
                        setjTViewColumn(data, 4);
                    }
                    break;
                case "CDS":
                    if (findOrShow) {
                        data.clear();
                        for (GeneBankCDS dbts : gene.getGeneBankCDSs()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getProteinGi());
                            list.add(dbts.getLocation());
                            if (dbts.getProduct() != null) {
                                list.add(dbts.getProduct());
                            } else {
                                list.add("");
                            }
                            if (dbts.getProteinId() != null) {
                                list.add(dbts.getProteinId());
                            } else {
                                list.add("");
                            }
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
            }
        }
    }
}
