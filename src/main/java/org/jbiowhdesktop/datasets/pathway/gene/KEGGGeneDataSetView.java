package org.jbiowhdesktop.datasets.pathway.gene;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.gene.KEGGGene;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.gene.KEGGGeneDBLink;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.gene.KEGGGeneDisease;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.gene.KEGGGeneDrugTarget;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.gene.KEGGGeneName;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.gene.KEGGGeneOrthology;

/**
 * This Class handled the KEGGGene View
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since May 14, 2012
 */
public class KEGGGeneDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public KEGGGeneDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        KEGGGene gene = (KEGGGene) dataSetObject;

        basicData.add("Definition: " + gene.getDefinition() + "\n");
        basicData.add("Position Start: " + gene.getPositionStart() + "\n");
        basicData.add("Position End: " + gene.getPositionEnd() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        KEGGGene gene = (KEGGGene) dataSetObject;
        ArrayList string = new ArrayList();

        if (!gene.getkEGGGeneName().isEmpty()) {
            string.add("Names");
        }
        if (!gene.getkEGGGeneDBLink().isEmpty()) {
            string.add("DB Links");
        }
        if (!gene.getkEGGGeneOrthology().isEmpty()) {
            string.add("Orthology");
        }
        if (!gene.getkEGGGeneDisease().isEmpty()) {
            string.add("Diseases");
        }
        if (!gene.getkEGGGeneDrugTarget().isEmpty()) {
            string.add("Drug Target");
        }
        if (!gene.getkEGGPathways().isEmpty()) {
            string.add("Pathways");
        }
        if (!gene.getGeneInfos().isEmpty()) {
            string.add("Gene");
        }

        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        KEGGGene gene = (KEGGGene) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Orthology":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGGeneOrthology dbts : gene.getkEGGGeneOrthology().values()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getKEGGGeneOrthologyPK().getEntry());
                            list.add(dbts.getName());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Drug Target":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGGeneDrugTarget dbts : gene.getkEGGGeneDrugTarget().values()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getKEGGGeneDrugTargetPK().getName());
                            list.add(dbts.getEntries());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Pathways":
                    getKEGGPathwayLink(findOrShow, gene.getkEGGPathways());
                    break;
                case "Gene":
                    getGeneLink(findOrShow, gene.getGeneInfos());
                    break;
                case "Diseases":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGGeneDisease dbts : gene.getkEGGGeneDisease()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getDisease());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Names":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGGeneName dbts : gene.getkEGGGeneName()) {
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
                        for (KEGGGeneDBLink dbts : gene.getkEGGGeneDBLink().values()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getKEGGGeneDBLinkPK().getId());
                            list.add(dbts.getKEGGGeneDBLinkPK().getDb());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
            }
        }
    }
}
