package org.jbiowhdesktop.datasets.pathway;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.pathway.KEGGPathway;

/**
 * This Class handled the KEGGPathway View
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since May 10, 2012
 */
public class KEGGPathwayDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public KEGGPathwayDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        KEGGPathway pathway = (KEGGPathway) dataSetObject;

        if (pathway.getTaxonomy() != null) {
            basicData.add("Taxonomy: " + pathway.getTaxonomy().getTaxonomySynonym() + "\n");
        }
        basicData.add("Org: " + pathway.getOrg() + "\n");
        basicData.add("Number: " + pathway.getNumber() + "\n");
        basicData.add("Title: " + pathway.getTitle() + "\n");
        basicData.add("Image: " + pathway.getImage() + "\n");
        basicData.add("Link: " + pathway.getLink() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        KEGGPathway path = (KEGGPathway) dataSetObject;
        ArrayList string = new ArrayList();
        if (!path.getkEGGEnzymes().isEmpty()) {
            string.add("Enzymes");
        }
        if (!path.getkEGGCompounds().isEmpty()) {
            string.add("Compounds");
        }
        if (!path.getkEGGGenes().isEmpty()) {
            string.add("KEGG Genes");
        }
        if (!path.getkEGGReactions().isEmpty()) {
            string.add("Reactions");
        }
        if (!path.getProtein().isEmpty()) {
            string.add("Proteins");
        }
        if (!path.getGeneInfo().isEmpty()) {
            string.add("Genes");
        }
        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        KEGGPathway path = (KEGGPathway) dataSetObject;
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Reactions":
                    getKEGGReactionLink(findOrShow, path.getkEGGReactions());
                    break;
                case "Compounds":
                    getKEGGCompoundLink(findOrShow, path.getkEGGCompounds());
                    break;
                case "Enzymes":
                    getKEGGEnzymeLink(findOrShow, path.getkEGGEnzymes());
                    break;
                case "KEGG Genes":
                    getKEGGGeneLink(findOrShow, path.getkEGGGenes());
                    break;
                case "Proteins":
                    getProteinLink(findOrShow, path.getProtein());
                    break;
                case "Genes":
                    getGeneLink(findOrShow, path.getGeneInfo());
                    break;
            }
        }
    }
}
