package org.jbiowhdesktop.datasets.gene.genome;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.gene.genome.entities.GenePTT;

/**
 * This Class handled the Chromosome View
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-11-08 14:37:19 +0100
 * (Thu, 08 Nov 2012) $ $LastChangedRevision: 591 $
 * @since Jul 23, 2012
 */
public class GenePTTDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public GenePTTDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        GenePTT gene = (GenePTT) dataSetObject;

        basicData.add("ProteinGi: " + gene.getProteinGi() + "\n");
        basicData.add("Symbol: " + gene.getGeneSymbol() + "\n");
        basicData.add("pFrom: " + gene.getPFrom() + "\n");
        basicData.add("pTo: " + gene.getPTo() + "\n");
        basicData.add("Strand: " + gene.getStrand() + "\n");
        basicData.add("Length: " + gene.getPLength() + "\n");
        basicData.add("LocusTag: " + gene.getGeneLocusTag() + "\n");
        basicData.add("Code: " + gene.getCode() + "\n");
        basicData.add("COG: " + gene.getCog() + "\n");
        basicData.add("Product: " + gene.getProduct() + "\n");
        basicData.add("PTTFile: " + gene.getPTTFile() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        ArrayList string = new ArrayList();
        GenePTT genePTT = (GenePTT) dataSetObject;

        if (genePTT.getGeneInfo() != null) {
            string.add("Gene");
        }
        if (genePTT.getProtein() != null) {
            string.add("Protein");
        }
        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        GenePTT gene = (GenePTT) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Gene":
                    getGeneLink(findOrShow, gene.getGeneInfo());
                    break;
                case "Protein":
                    getProteinLink(findOrShow, gene.getProtein());
                    break;
            }
        }
    }
}
