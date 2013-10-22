package org.jbiowhdesktop.datasets.pathway.enzyme;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.enzyme.KEGGEnzyme;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.enzyme.KEGGEnzymeClass;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.enzyme.KEGGEnzymeDBLink;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.enzyme.KEGGEnzymeName;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.enzyme.KEGGEnzymeOrthology;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.enzyme.KEGGEnzymeSysName;

/**
 * This Class handled the KEGGEnzyme View
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since May 14, 2012
 */
public class KEGGEnzymeDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public KEGGEnzymeDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        KEGGEnzyme enzyme = (KEGGEnzyme) dataSetObject;

        basicData.add("Comment: " + enzyme.getComment() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        KEGGEnzyme enzyme = (KEGGEnzyme) dataSetObject;
        ArrayList string = new ArrayList();
        if (!enzyme.getkEGGEnzymeName().isEmpty()) {
            string.add("Names");
        }
        if (!enzyme.getkEGGEnzymeClass().isEmpty()) {
            string.add("Classes");
        }
        if (!enzyme.getkEGGEnzymeDBLink().isEmpty()) {
            string.add("DB Links");
        }
        if (!enzyme.getkEGGEnzymeSysName().isEmpty()) {
            string.add("Sys Names");
        }
        if (!enzyme.getkEGGEnzymeOrthology().isEmpty()) {
            string.add("Orthologies");
        }
        if (!enzyme.getkEGGCompoundAsCofactor().isEmpty()) {
            string.add("Compound As Cofactor");
        }
        if (!enzyme.getkEGGCompoundAsEffector().isEmpty()) {
            string.add("Compound As Effector");
        }
        if (!enzyme.getkEGGCompoundAsInhibitor().isEmpty()) {
            string.add("Compound As Inhibitor");
        }
        if (!enzyme.getkEGGPathways().isEmpty()) {
            string.add("Pathways");
        }
        if (!enzyme.getkEGGReaction().isEmpty()) {
            string.add("Reactions");
        }
        if (!enzyme.getProtein().isEmpty()) {
            string.add("Proteins");
        }
        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        KEGGEnzyme enzyme = (KEGGEnzyme) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Proteins":
                    getProteinLink(findOrShow, enzyme.getProtein());
                    break;
                case "Reactions":
                    getKEGGReactionLink(findOrShow, enzyme.getkEGGReaction());
                    break;
                case "Pathways":
                    getKEGGPathwayLink(findOrShow, enzyme.getkEGGPathways());
                    break;
                case "Compound As Cofactor":
                    getKEGGCompoundLink(findOrShow, enzyme.getkEGGCompoundAsCofactor());
                    break;
                case "Compound As Effector":
                    getKEGGCompoundLink(findOrShow, enzyme.getkEGGCompoundAsEffector());
                    break;
                case "Compound As Inhibitor":
                    getKEGGCompoundLink(findOrShow, enzyme.getkEGGCompoundAsInhibitor());
                    break;
                case "Orthologies":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGEnzymeOrthology dbts : enzyme.getkEGGEnzymeOrthology().values()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getKEGGEnzymeOrthologyPK().getEntry());
                            list.add(dbts.getName());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Sys Names":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGEnzymeSysName dbts : enzyme.getkEGGEnzymeSysName()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getSySName());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "DB Links":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGEnzymeDBLink dbts : enzyme.getkEGGEnzymeDBLink().values()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getKEGGEnzymeDBLinkPK().getId());
                            list.add(dbts.getKEGGEnzymeDBLinkPK().getDb());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Names":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGEnzymeName dbts : enzyme.getkEGGEnzymeName()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getName());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Classes":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGEnzymeClass dbts : enzyme.getkEGGEnzymeClass()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getClass1());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
            }
        }
    }
}
