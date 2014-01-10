package org.jbiowhdesktop.datasets.protgroup.orthoxml;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.protgroup.orthoxml.entities.OrthoXMLGroup;
import org.jbiowhpersistence.datasets.protgroup.orthoxml.entities.OrthoXMLGroupGeneRef;

/**
 * This class handled the OrthoXML View
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Jan 9, 2014
 */
public class OrthoXMLDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public OrthoXMLDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        OrthoXMLGroup object = (OrthoXMLGroup) dataSetObject;

        basicData.add("Id: " + object.getId() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        ArrayList string = new ArrayList();
        OrthoXMLGroup object = (OrthoXMLGroup) dataSetObject;

        if (object.getOrthoXMLGroupGeneRef() != null && !object.getOrthoXMLGroupGeneRef().isEmpty()) {
            string.add("Members");
        }
        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        OrthoXMLGroup object = (OrthoXMLGroup) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Members":
                    if (findOrShow) {
                        data.clear();
                        for (OrthoXMLGroupGeneRef dbts : object.getOrthoXMLGroupGeneRef()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getId());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
            }
        }
    }

}
