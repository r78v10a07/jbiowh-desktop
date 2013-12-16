package org.jbiowhdesktop.datasets.protgroup.pirsf;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhdesktop.datasets.EntityParserViewProxy;
import org.jbiowhpersistence.datasets.protein.entities.ProteinName;
import org.jbiowhpersistence.datasets.protgroup.pirsf.entities.Pirsf;
import org.jbiowhpersistence.datasets.protgroup.pirsf.entities.PirsfProtein;
import org.jbiowhpersistence.datasets.protgroup.pirsf.entities.PirsfhasProtein;

/**
 * This class handled the Pirsf View
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Dec 12, 2013
 */
public class PirsfDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public PirsfDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        Pirsf object = (Pirsf) dataSetObject;

        basicData.add("Name: " + object.getName() + "\n");
        basicData.add("Curation Status: " + object.getCurationStatus() + "\n");
        basicData.add("Parent: " + object.getParent() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        ArrayList string = new ArrayList();
        Pirsf object = (Pirsf) dataSetObject;

        if (object.getpIRSFProtein() != null && !object.getpIRSFProtein().isEmpty()) {
            string.add("PIRSF Protein");
        }
        if (object.getpIRSFhasProtein() != null && !object.getpIRSFhasProtein().isEmpty()) {
            string.add("Protein");
        }
        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        Pirsf pirsf = (Pirsf) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "PIRSF Protein":
                    if (findOrShow) {
                        for (PirsfProtein dbts : pirsf.getpIRSFProtein()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getAccessionNumber());
                            list.add(dbts.getStatus());
                            list.add(dbts.getSeed());
                            data.add(list);
                        }
                        setjTViewColumn(data, 3);
                    }
                    break;
                case "Protein":
                    if (findOrShow) {
                        data.clear();
                        if (pirsf.getpIRSFhasProtein() != null) {
                            for (PirsfhasProtein dbts : pirsf.getpIRSFhasProtein().values()) {
                                if (dbts.getProtein() != null) {
                                    ArrayList<Object> list = new ArrayList<>();
                                    list.add(dbts.getProtein().getProteinNameDirected());
                                    list.add(dbts.getProtein().getProteinLongNameDirected());
                                    if (dbts.getProtein().getTaxonomy() != null) {
                                        list.add(dbts.getProtein().getTaxonomy().getTaxonomySynonym());
                                    } else {
                                        list.add("");
                                    }
                                    data.add(list);
                                }
                            }
                        }
                        setjTViewColumn(data, 3);
                    } else {
                        if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                            if (pirsf.getpIRSFhasProtein() != null) {
                                for (PirsfhasProtein dbts : pirsf.getpIRSFhasProtein().values()) {
                                    if (dbts.getProtein() != null) {
                                        if (dbts.getProtein().getProteinName() != null) {
                                            for (ProteinName proteinName : dbts.getProtein().getProteinName()) {
                                                if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(proteinName.getName())) {
                                                    EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts.getProtein());
                                                    viewProxy.setVisible();
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
        }
    }

}
