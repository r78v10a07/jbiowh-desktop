package org.jbiowhdesktop.datasets.protein;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhdesktop.datasets.EntityParserViewProxy;
import org.jbiowhpersistence.datasets.gene.genome.entities.GenePTT;
import org.jbiowhpersistence.datasets.protclust.entities.UniRefEntry;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.datasets.protein.entities.ProteinAccessionNumber;
import org.jbiowhpersistence.datasets.protein.entities.ProteinComment;
import org.jbiowhpersistence.datasets.protein.entities.ProteinDBReference;
import org.jbiowhpersistence.datasets.protein.entities.ProteinKeyword;
import org.jbiowhpersistence.datasets.protein.entities.ProteinLongName;
import org.jbiowhpersistence.datasets.protgroup.pirsf.entities.PirsfhasProtein;
import org.jbiowhpersistence.datasets.taxonomy.entities.Taxonomy;

/**
 * This Class handled the Protein View
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-12-04 11:38:29 +0100
 * (Tue, 04 Dec 2012) $ $LastChangedRevision: 591 $
 *
 * @since Apr 18, 2012
 */
public class ProteinDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public ProteinDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        StringBuilder seqShort = new StringBuilder();
        Protein protein = (Protein) dataSetObject;

        if (protein.getTaxonomy() != null) {
            basicData.add("Taxonomy: " + protein.getTaxonomy().getTaxonomySynonym() + "\n");
        }
        if (protein.getTaxonomyHost() != null) {
            for (Taxonomy tax : protein.getTaxonomyHost()) {
                basicData.add("\tTaxonomy Host: " + tax.getTaxonomySynonym() + "\n");
            }
        }
        basicData.add("Version: " + protein.getVersion() + "\n");
        basicData.add("Modified: " + protein.getModified() + "\n");
        basicData.add("Created: " + protein.getCreated() + "\n");
        basicData.add("DataSet: " + protein.getDataSetIn() + "\n");
        basicData.add("Existence: " + protein.getExistence() + "\n");
        basicData.add("SeqLength: " + protein.getSeqLength() + "\n");
        basicData.add("Mass: " + protein.getMass() + "\n");
        basicData.add("Precursor: " + protein.getPrecursor() + "\n");
        basicData.add("Fragment: " + protein.getFragment() + "\n");

        for (int i = 0; i < protein.getSeq().length(); i += 80) {
            if (protein.getSeq().length() < i + 80) {
                seqShort.append(protein.getSeq().substring(i, protein.getSeq().length())).append("\n");
            } else {
                seqShort.append(protein.getSeq().substring(i, i + 80)).append("\n");
            }
        }
        basicData.add("Seq:\n" + seqShort.toString() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        ArrayList string = new ArrayList();
        Protein protein = (Protein) dataSetObject;

        if (protein.getProteinAccessionNumber() != null && !protein.getProteinAccessionNumber().isEmpty()) {
            string.add("Accession Number");
        }
        if (protein.getProteinLongName() != null && !protein.getProteinLongName().isEmpty()) {
            string.add("Name");
        }
        if (protein.getProteinComment() != null && !protein.getProteinComment().isEmpty()) {
            string.add("Comment");
        }
        if (protein.getProteinKeyword() != null && !protein.getProteinKeyword().isEmpty()) {
            string.add("Keyword");
        }
        if (protein.getProteinDBReference() != null && !protein.getProteinDBReference().isEmpty()) {
            string.add("DBReference");
        }
        if (protein.getOntology() != null && !protein.getOntology().isEmpty()) {
            string.add("Ontology");
        }
        if (protein.getGeneInfo() != null && !protein.getGeneInfo().isEmpty()) {
            string.add("Gene");
        }
        if (protein.getGenePTT() != null) {
            string.add("GenePTT");
        }
        if (protein.getDrugBank() != null && !protein.getDrugBank().isEmpty()) {
            string.add("DrugBank");
        }
        if (protein.getDrugBankAsEnzyme() != null && !protein.getDrugBankAsEnzyme().isEmpty()) {
            string.add("DrugBank As Enzyme");
        }
        if (protein.getDrugBankAsCarriers() != null && !protein.getDrugBankAsCarriers().isEmpty()) {
            string.add("DrugBank As Carrier");
        }
        if (protein.getDrugBankAsTransporters() != null && !protein.getDrugBankAsTransporters().isEmpty()) {
            string.add("DrugBank As Transporter");
        }
        if (protein.getUniRefEntry() != null && !protein.getUniRefEntry().isEmpty()) {
            string.add("UniRef");
        }
        if (protein.getkEGGEnzymes() != null && !protein.getkEGGEnzymes().isEmpty()) {
            string.add("Enzymes");
        }
        if (protein.getkEGGPathways() != null && !protein.getkEGGPathways().isEmpty()) {
            string.add("Pathways");
        }
        if (protein.getpIRSFhasProtein() != null && !protein.getpIRSFhasProtein().isEmpty()) {
            string.add("PIRSF");
        }

        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        Protein protein = (Protein) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Enzymes":
                    getKEGGEnzymeLink(findOrShow, protein.getkEGGEnzymes());
                    break;
                case "Pathways":
                    getKEGGPathwayLink(findOrShow, protein.getkEGGPathways());
                    break;
                case "GenePTT":
                    if (findOrShow) {
                        data.clear();
                        GenePTT dbts = protein.getGenePTT();
                        ArrayList<Object> list = new ArrayList<>();
                        list.add(dbts.getProteinGi());
                        list.add(dbts.getCog());
                        list.add(dbts.getLocation());
                        list.add(dbts.getStrand());
                        list.add(dbts.getProduct());
                        data.add(list);

                        setjTViewColumn(data, 5);
                    } else {
                        GenePTT dbts = protein.getGenePTT();
                        if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getProteinGi())) {
                            EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts);
                            viewProxy.setVisible();
                        }
                    }
                    break;
                case "Accession Number":
                    if (findOrShow) {
                        data.clear();
                        for (ProteinAccessionNumber dbts : protein.getProteinAccessionNumber()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getAccessionNumber());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Name":
                    if (findOrShow) {
                        data.clear();
                        for (ProteinLongName dbts : protein.getProteinLongName()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getName());
                            list.add(dbts.getProteinNameDef());
                            list.add(dbts.getType());
                            list.add(dbts.isComponent());
                            list.add(dbts.isDomain());
                            list.add(dbts.getEvidence());
                            list.add(dbts.getStatus());
                            data.add(list);
                        }
                        setjTViewColumn(data, 7);
                    }
                    break;
                case "Comment":
                    if (findOrShow) {
                        data.clear();
                        for (ProteinComment dbts : protein.getProteinComment()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getType());
                            list.add(dbts.getText());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Keyword":
                    if (findOrShow) {
                        data.clear();
                        for (ProteinKeyword dbts : protein.getProteinKeyword()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getKeyword());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "DBReference":
                    if (findOrShow) {
                        data.clear();
                        for (ProteinDBReference dbts : protein.getProteinDBReference()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getType());
                            list.add(dbts.getId());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Ontology":
                    getOntologyLink(findOrShow, protein.getOntology());
                    break;
                case "Gene":
                    getGeneLink(findOrShow, protein.getGeneInfo());
                    break;
                case "DrugBank":
                    getDrugBankLink(findOrShow, protein.getDrugBank());
                    break;
                case "DrugBank As Enzyme":
                    getDrugBankLink(findOrShow, protein.getDrugBankAsEnzyme());
                    break;
                case "DrugBank As Carrier":
                    getDrugBankLink(findOrShow, protein.getDrugBankAsCarriers());
                    break;
                case "DrugBank As Transporter":
                    getDrugBankLink(findOrShow, protein.getDrugBankAsTransporters());
                    break;
                case "UniRef":
                    if (findOrShow) {
                        data.clear();
                        for (UniRefEntry dbts : protein.getUniRefEntry()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getId());
                            list.add(dbts.getName());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    } else {
                        if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                            for (UniRefEntry dbts : protein.getUniRefEntry()) {
                                if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getId())) {
                                    EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts);
                                    viewProxy.setVisible();
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case "PIRSF":
                    if (findOrShow) {
                        data.clear();
                        if (protein.getpIRSFhasProtein() != null) {
                            for (PirsfhasProtein dbts : protein.getpIRSFhasProtein().values()) {
                                if (dbts.getProtein() != null) {
                                    ArrayList<Object> list = new ArrayList<>();
                                    list.add(dbts.getPirsf().getpIRSFnumber());
                                    list.add(dbts.getPirsf().getName());
                                    list.add(dbts.getPirsf().getCurationStatus());
                                    list.add(dbts.getPirsf().getParent());
                                    data.add(list);
                                }
                            }
                        }
                        setjTViewColumn(data, 3);
                    } else {
                        if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                            if (protein.getpIRSFhasProtein() != null) {
                                for (PirsfhasProtein dbts : protein.getpIRSFhasProtein().values()) {
                                    if (dbts.getProtein() != null) {
                                        if (dbts.getPirsf() != null) {
                                            if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getPirsf().getpIRSFnumber())) {
                                                EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts.getPirsf());
                                                viewProxy.setVisible();
                                                break;
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
