package org.jbiowhdesktop.datasets.protgroup.cog;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.protgroup.cog.entities.COGFuncClass;
import org.jbiowhpersistence.datasets.protgroup.cog.entities.COGMember;
import org.jbiowhpersistence.datasets.protgroup.cog.entities.COGOrthologousGroup;

/**
 * This class handled the COG View
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Jan 9, 2014
 */
public class COGDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public COGDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        COGOrthologousGroup object = (COGOrthologousGroup) dataSetObject;

        basicData.add("Id: " + object.getId() + "\n");
        basicData.add("Function: " + object.getGroupFunction() + "\n");
        if (object.getCogFuncClass() != null && !object.getCogFuncClass().isEmpty()) {
            basicData.add("Class: \n");
            for (COGFuncClass c : object.getCogFuncClass()) {
                basicData.add("\t" + c.getName() + "\n");
            }
        }

        return basicData;
    }

    @Override
    public List getLinks() {
        ArrayList string = new ArrayList();
        COGOrthologousGroup object = (COGOrthologousGroup) dataSetObject;

        if (object.getCogMember() != null && !object.getCogMember().isEmpty()) {
            string.add("COG Members");
        }
        if (object.getGeneInfo() != null && !object.getGeneInfo().isEmpty()) {
            string.add("Gene");
        }
        if (object.getProtein() != null && !object.getProtein().isEmpty()) {
            string.add("Protein");
        }
        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        COGOrthologousGroup object = (COGOrthologousGroup) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "COG Members":
                    if (findOrShow) {
                        data.clear();
                        for (COGMember dbts : object.getCogMember()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getOrganism());
                            list.add(dbts.getId());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Gene":
                    getGeneLink(findOrShow, object.getGeneInfo());
                    break;
                case "Protein":
                    getProteinLink(findOrShow, object.getProtein());
                    break;
            }
        }
    }
}
