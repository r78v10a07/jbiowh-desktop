package org.jbiowhdesktop.datasets.pathway.reaction;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.reaction.KEGGReaction;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.reaction.KEGGReactionName;

/**
 * This Class handled the KEGGReaction View
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since May 11, 2012
 */
public class KEGGReactionDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public KEGGReactionDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        KEGGReaction reaction = (KEGGReaction) dataSetObject;

        basicData.add("Comment: " + reaction.getComment() + "\n");
        basicData.add("Definition: " + reaction.getDefinition() + "\n");
        basicData.add("Equation: " + reaction.getEquation() + "\n");
        basicData.add("Remark: " + reaction.getRemark() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        ArrayList string = new ArrayList();
        KEGGReaction reaction = (KEGGReaction) dataSetObject;

        if (!reaction.getkEGGReactionName().isEmpty()) {
            string.add("Names");
        }
        if (!reaction.getkEGGCompoundAsProduct().isEmpty()) {
            string.add("Compound As Product");
        }
        if (!reaction.getkEGGCompoundAsSubstrate().isEmpty()) {
            string.add("Compound As Substrate");
        }
        if (!reaction.getkEGGEnzyme().isEmpty()) {
            string.add("Enzymes");
        }
        if (!reaction.getkEGGPathways().isEmpty()) {
            string.add("Pathways");
        }
        if (!reaction.getkEGGGlycanAsProduct().isEmpty()) {
            string.add("Glycan As Product");
        }
        if (!reaction.getkEGGGlycanAsSubstrate().isEmpty()) {
            string.add("Glycan As Substrate");
        }

        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        KEGGReaction reaction = (KEGGReaction) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Glycan As Product":
                    getKEGGGlycanLink(findOrShow, reaction.getkEGGGlycanAsProduct());
                    break;
                case "Glycan As Substrate":
                    getKEGGGlycanLink(findOrShow, reaction.getkEGGGlycanAsSubstrate());
                    break;
                case "Compound As Product":
                    getKEGGCompoundLink(findOrShow, reaction.getkEGGCompoundAsProduct());
                    break;
                case "Compound As Substrate":
                    getKEGGCompoundLink(findOrShow, reaction.getkEGGCompoundAsSubstrate());
                    break;
                case "Pathways":
                    getKEGGPathwayLink(findOrShow, reaction.getkEGGPathways());
                    break;
                case "Enzymes":
                    getKEGGEnzymeLink(findOrShow, reaction.getkEGGEnzyme());
                    break;
                case "Names":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGReactionName dbts : reaction.getkEGGReactionName()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getName());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
            }
        }
    }
}
