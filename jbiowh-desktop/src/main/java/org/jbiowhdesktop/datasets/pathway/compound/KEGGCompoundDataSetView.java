package org.jbiowhdesktop.datasets.pathway.compound;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.compound.KEGGCompound;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.compound.KEGGCompoundDBLink;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.compound.KEGGCompoundName;

/**
 * This Class handled the KEGGCompound View
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since May 11, 2012
 */
public class KEGGCompoundDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public KEGGCompoundDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        KEGGCompound compound = (KEGGCompound) dataSetObject;

        basicData.add("Comment: " + compound.getComment() + "\n");
        basicData.add("Mass: " + compound.getMass() + "\n");
        basicData.add("Remark: " + compound.getRemark() + "\n");
        basicData.add("Formula: " + compound.getFormula() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        ArrayList string = new ArrayList();
        KEGGCompound compound = (KEGGCompound) dataSetObject;

        if (!compound.getkEGGCompoundName().isEmpty()) {
            string.add("Names");
        }
        if (!compound.getkEGGCompoundDBLink().isEmpty()) {
            string.add("DB Links");
        }
        if (!compound.getkEGGEnzymeAsCofactor().isEmpty()) {
            string.add("Enzyme As Cofactor");
        }
        if (!compound.getkEGGEnzymeAsEffector().isEmpty()) {
            string.add("Enzyme As Effector");
        }
        if (!compound.getkEGGEnzymeAsInhibitor().isEmpty()) {
            string.add("Enzyme As Inhibitor");
        }
        if (!compound.getkEGGPathways().isEmpty()) {
            string.add("Pathways");
        }
        if (!compound.getkEGGReactionAsProduct().isEmpty()) {
            string.add("Reaction As Product");
        }
        if (!compound.getkEGGReactionAsSubstrate().isEmpty()) {
            string.add("Reaction As Substrate");
        }
        if (!compound.getDrugBanks().isEmpty()) {
            string.add("DrugBanks");
        }

        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        KEGGCompound compound = (KEGGCompound) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "DrugBanks":
                    getDrugBankLink(findOrShow, compound.getDrugBanks());
                    break;
                case "Reaction As Product":
                    getKEGGReactionLink(findOrShow, compound.getkEGGReactionAsProduct());
                    break;
                case "Reaction As Substrate":
                    getKEGGReactionLink(findOrShow, compound.getkEGGReactionAsSubstrate());
                    break;
                case "Pathways":
                    getKEGGPathwayLink(findOrShow, compound.getkEGGPathways());
                    break;
                case "Enzyme As Cofactor":
                    getKEGGEnzymeLink(findOrShow, compound.getkEGGEnzymeAsCofactor());
                    break;
                case "Enzyme As Effector":
                    getKEGGEnzymeLink(findOrShow, compound.getkEGGEnzymeAsEffector());
                    break;
                case "Enzyme As Inhibitor":
                    getKEGGEnzymeLink(findOrShow, compound.getkEGGEnzymeAsInhibitor());
                    break;
                case "Names":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGCompoundName dbts : compound.getkEGGCompoundName()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getName());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "DB Links":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGCompoundDBLink dbts : compound.getkEGGCompoundDBLink().values()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getKEGGCompoundDBLinkPK().getDb());
                            list.add(dbts.getKEGGCompoundDBLinkPK().getId());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
            }
        }
    }
}
