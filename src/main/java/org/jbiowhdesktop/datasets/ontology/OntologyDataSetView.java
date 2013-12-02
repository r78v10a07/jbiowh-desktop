package org.jbiowhdesktop.datasets.ontology;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhdesktop.datasets.EntityParserViewProxy;
import org.jbiowhpersistence.datasets.ontology.entities.Ontology;
import org.jbiowhpersistence.datasets.ontology.entities.OntologyAlternativeId;
import org.jbiowhpersistence.datasets.ontology.entities.OntologyIsA;
import org.jbiowhpersistence.datasets.ontology.entities.OntologyPMID;
import org.jbiowhpersistence.datasets.ontology.entities.OntologyRelation;
import org.jbiowhpersistence.datasets.ontology.entities.OntologySubset;
import org.jbiowhpersistence.datasets.ontology.entities.OntologyXRef;
import org.jbiowhpersistence.datasets.ontology.entities.OntologyhasOntologySynonym;

/**
 * This Class handled the Ontology View
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-10-03 22:11:05 +0200
 * (Wed, 03 Oct 2012) $ $LastChangedRevision: 270 $
 *
 * @since Mar 27, 2012
 */
public class OntologyDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public OntologyDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        Ontology ont = (Ontology) dataSetObject;

        basicData.add("Name: " + ont.getName() + "\n");
        basicData.add("Name space: " + ont.getNameSpace() + "\n\n");
        basicData.add("Definition: " + ont.getDef() + "\n\n");
        basicData.add("Comment: " + ont.getComment() + "\n\n");
        if (ont.isIsObsolete()) {
            basicData.add("Is Obsolete" + "\n");
        }

        return basicData;
    }

    @Override
    public List getLinks() {
        ArrayList string = new ArrayList();
        Ontology ont = (Ontology) dataSetObject;

        if (!ont.getOntologyAlternativeId().isEmpty()) {
            string.add("Alternative Id");
        }
        if (!ont.getOntologyIsA().isEmpty()) {
            string.add("IsA");
        }
        if (!ont.getOntologyPMID().isEmpty()) {
            string.add("PMID");
        }
        if (!ont.getOntologyRelation().isEmpty()) {
            string.add("Relation");
        }
        if (!ont.getOntologySubset().isEmpty()) {
            string.add("Subset");
        }
        if (!ont.getOntologyhasOntologySynonym().isEmpty()) {
            string.add("Synonym");
        }
        if (!ont.getOntologyXRef().isEmpty()) {
            string.add("XRef");
        }

        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        Ontology ont = (Ontology) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Alternative Id":
                    if (findOrShow) {
                        data.clear();
                        for (OntologyAlternativeId dbts : ont.getOntologyAlternativeId()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getAltId());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "IsA":
                    if (findOrShow) {
                        data.clear();
                        for (OntologyIsA dbts : ont.getOntologyIsA()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getIsAOntology().getId());
                            list.add(dbts.getIsAOntology().getName());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    } else {
                        if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()
                                && jTLinks.getSelectedColumn() >= 0 && jTLinks.getSelectedRow() < jTLinks.getColumnCount()) {
                            for (OntologyIsA dbts : ont.getOntologyIsA()) {
                                if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getIsAOntology().getId())) {
                                    EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts.getIsAOntology());
                                    viewProxy.setVisible();
                                }
                            }
                        }
                    }
                    break;
                case "PMID":
                    if (findOrShow) {
                        data.clear();
                        for (OntologyPMID dbts : ont.getOntologyPMID()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getPmid());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Relation":
                    if (findOrShow) {
                        data.clear();
                        for (OntologyRelation dbts : ont.getOntologyRelation()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getType());
                            list.add(dbts.getOtherOntology().getId());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    } else {
                        if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()
                                && jTLinks.getSelectedColumn() >= 0 && jTLinks.getSelectedRow() < jTLinks.getColumnCount()) {
                            for (OntologyRelation dbts : ont.getOntologyRelation()) {
                                if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 1).equals(dbts.getOtherOntology().getId())) {
                                    EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts.getOtherOntology());
                                    viewProxy.setVisible();
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case "Subset":
                    if (findOrShow) {
                        data.clear();
                        for (OntologySubset dbts : ont.getOntologySubset()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getName());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Synonym":
                    if (findOrShow) {
                        data.clear();
                        for (OntologyhasOntologySynonym dbts : ont.getOntologyhasOntologySynonym().values()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getOntologySynonym().getSynonym());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "XRef":
                    if (findOrShow) {
                        data.clear();
                        for (OntologyXRef dbts : ont.getOntologyXRef()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getDBName());
                            list.add(dbts.getType());
                            list.add(dbts.getAcc());
                            list.add(dbts.getName());
                            data.add(list);
                        }
                        setjTViewColumn(data, 4);
                    }
                    break;
            }
        }
    }
}
