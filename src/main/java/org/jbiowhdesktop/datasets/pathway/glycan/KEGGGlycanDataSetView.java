package org.jbiowhdesktop.datasets.pathway.glycan;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.glycan.KEGGGlycan;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.glycan.KEGGGlycanClass;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.glycan.KEGGGlycanDBLink;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.glycan.KEGGGlycanName;

/**
 * This Class handled the KEGGGlycan View
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since May 14, 2012
 */
public class KEGGGlycanDataSetView extends AbstractDataSetView {

    public KEGGGlycanDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        KEGGGlycan glycan = (KEGGGlycan) dataSetObject;

        basicData.add("Composition: " + glycan.getComposition() + "\n");
        basicData.add("Comment: " + glycan.getComment() + "\n");
        basicData.add("Mass: " + glycan.getMass() + "\n");
        basicData.add("Remark: " + glycan.getRemark() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        KEGGGlycan glycan = (KEGGGlycan) dataSetObject;
        ArrayList string = new ArrayList();
        if (!glycan.getkEGGGlycanName().isEmpty()) {
            string.add("Names");
        }
        if (!glycan.getkEGGGlycanClass().isEmpty()) {
            string.add("Classes");
        }
        if (!glycan.getkEGGGlycanDBLink().isEmpty()) {
            string.add("DB Links");
        }
        if (!glycan.getkEGGReactionAsProduct().isEmpty()) {
            string.add("Reaction As Product");
        }
        if (!glycan.getkEGGReactionAsSubstrate().isEmpty()) {
            string.add("Reaction As Substrate");
        }

        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        KEGGGlycan glycan = (KEGGGlycan) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Reaction As Product":
                    getKEGGReactionLink(findOrShow, glycan.getkEGGReactionAsProduct());
                    break;
                case "Reaction As Substrate":
                    getKEGGReactionLink(findOrShow, glycan.getkEGGReactionAsSubstrate());
                    break;
                case "DB Links":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGGlycanDBLink dbts : glycan.getkEGGGlycanDBLink().values()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getKEGGGlycanDBLinkPK().getId());
                            list.add(dbts.getKEGGGlycanDBLinkPK().getDb());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Classes":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGGlycanClass dbts : glycan.getkEGGGlycanClass()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getClass1());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Names":
                    if (findOrShow) {
                        data.clear();
                        for (KEGGGlycanName dbts : glycan.getkEGGGlycanName()) {
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
