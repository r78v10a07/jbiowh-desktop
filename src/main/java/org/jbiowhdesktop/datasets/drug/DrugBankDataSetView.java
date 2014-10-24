package org.jbiowhdesktop.datasets.drug;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhdesktop.datasets.EntityParserViewProxy;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBank;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankAHFSCode;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankATCCode;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankAffectedOrganism;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankBrand;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankCalculatedProperty;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankCategory;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankDosage;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankDrugInteraction;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankExperimentalProperty;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankExternalIdentifier;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankExternalLink;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankFoodInteraction;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankGeneralRef;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankGroup;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankManufacturer;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankMixture;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankPackager;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankPatent;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankPrice;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankSecondAccessionNumber;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankSynonym;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankTaxonomy;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankTaxonomySubstructure;
import org.jbiowhpersistence.datasets.drug.drugbank.search.SearchDrugBank;

/**
 * This Class handled the DrugBank View
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2013-03-19 09:38:47 +0100
 * (Tue, 19 Mar 2013) $ $LastChangedRevision: 396 $
 *
 * @since Apr 20, 2012
 */
public class DrugBankDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public DrugBankDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList<>();
        DrugBank drug = (DrugBank) dataSetObject;

        basicData.add("Name: " + drug.getName() + "\n");
        basicData.add("CAS: " + drug.getcASNumber() + "\n");
        basicData.add("Updated: " + drug.getUpdated() + "\n");
        basicData.add("Created: " + drug.getCreated() + "\n\n");
        basicData.add("Description: " + drug.getDescription() + "\n");
        basicData.add("Indication: " + drug.getIndication() + "\n");
        basicData.add("Pharmacology: " + drug.getPharmacology() + "\n");
        basicData.add("Mechanism Of Action: " + drug.getMechanismOfAction() + "\n");
        basicData.add("Toxicity: " + drug.getToxicity() + "\n");
        basicData.add("Absorption: " + drug.getAbsorption() + "\n");
        basicData.add("HalfLife: " + drug.getHalfLife() + "\n");
        basicData.add("ProteinBinding: " + drug.getProteinBinding() + "\n");
        basicData.add("Route Of Elimination: " + drug.getRouteOfElimination() + "\n");
        basicData.add("Volume Of Distribution: " + drug.getVolumeOfDistribution() + "\n");
        basicData.add("Clearance: " + drug.getClearance() + "\n");
        basicData.add("Type: " + drug.getType() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        List string = new ArrayList();
        DrugBank drug = (DrugBank) dataSetObject;

        if (!drug.getDrugBankSecondAccessionNumber().isEmpty()) {
            string.add("Second Accession Number");
        }
        if (!drug.getDrugBankSynonym().isEmpty()) {
            string.add("Synonym");
        }
        if (!drug.getDrugBankBrand().isEmpty()) {
            string.add("Brands");
        }
        if (!drug.getDrugBankMixture().isEmpty()) {
            string.add("Mixtures");
        }
        if (!drug.getDrugBankGeneralRef().isEmpty()) {
            string.add("General References");
        }
        if (!drug.getDrugBankPackager().isEmpty()) {
            string.add("Packagers");
        }
        if (!drug.getDrugBankManufacturer().isEmpty()) {
            string.add("Manofactures");
        }
        if (!drug.getDrugBankGroup().isEmpty()) {
            string.add("Group");
        }
        if (!drug.getDrugBankTaxonomy().isEmpty()) {
            string.add("Taxonomy");
        }
        if (!drug.getDrugBankTaxonomySubstructure().isEmpty()) {
            string.add("Taxonomy Substructure");
        }
        if (!drug.getDrugBankPrice().isEmpty()) {
            string.add("Prices");
        }
        if (!drug.getDrugBankFoodInteraction().isEmpty()) {
            string.add("Food Interactions");
        }
        if (!drug.getDrugBankCategory().isEmpty()) {
            string.add("Categories");
        }
        if (!drug.getDrugBankDrugInteraction().isEmpty()) {
            string.add("Drug Interactions");
        }
        if (!drug.getDrugBankDosage().isEmpty()) {
            string.add("Dosages");
        }
        if (!drug.getDrugBankATCCode().isEmpty()) {
            string.add("ATCCodes");
        }
        if (!drug.getDrugBankAHFSCode().isEmpty()) {
            string.add("AHFSCodes");
        }
        if (!drug.getDrugBankAffectedOrganism().isEmpty()) {
            string.add("Affected Organisms");
        }
        if (!drug.getDrugBankPatent().isEmpty()) {
            string.add("Patents");
        }
        if (!drug.getDrugBankExternalIdentifier().isEmpty()) {
            string.add("External Identifiers");
        }
        if (!drug.getDrugBankExternalLink().isEmpty()) {
            string.add("External Links");
        }
        if (!drug.getDrugBankCalculatedProperty().isEmpty()) {
            string.add("Calculated Properties");
        }
        if (!drug.getDrugBankExperimentalProperty().isEmpty()) {
            string.add("Experimental Properties");
        }
        if (!drug.getProtein().isEmpty()) {
            string.add("Protein");
        }
        if (!drug.getProteinAsEnzyme().isEmpty()) {
            string.add("Protein As Enzyme");
        }
        if (!drug.getProteinAsCarriers().isEmpty()) {
            string.add("Protein As Carrier");
        }
        if (!drug.getProteinAsTransporters().isEmpty()) {
            string.add("Protein As Transporter");
        }
        if (!drug.getkEGGCompounds().isEmpty()) {
            string.add("KEGGCompound");
        }

        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        DrugBank drug = (DrugBank) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "KEGGCompound":
                    getKEGGCompoundLink(findOrShow, drug.getkEGGCompounds());
                    break;
                case "Protein":
                    getProteinLink(findOrShow, drug.getProtein());
                    break;
                case "Protein As Enzyme":
                    getProteinLink(findOrShow, drug.getProteinAsEnzyme());
                    break;
                case "Protein As Carrier":
                    getProteinLink(findOrShow, drug.getProteinAsCarriers());
                    break;
                case "Protein As Transporter":
                    getProteinLink(findOrShow, drug.getProteinAsTransporters());
                    break;
                case "Experimental Properties":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankExperimentalProperty dbts : drug.getDrugBankExperimentalProperty()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getKind());
                            list.add(dbts.getValue());
                            list.add(dbts.getSource());
                            data.add(list);
                        }
                        setjTViewColumn(data, 3);
                    }
                    break;
                case "Calculated Properties":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankCalculatedProperty dbts : drug.getDrugBankCalculatedProperty()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getKind());
                            list.add(dbts.getValue());
                            list.add(dbts.getSource());
                            data.add(list);
                        }
                        setjTViewColumn(data, 3);
                    }
                    break;
                case "External Links":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankExternalLink dbts : drug.getDrugBankExternalLink()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getResource());
                            list.add(dbts.getUrl());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "External Identifiers":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankExternalIdentifier dbts : drug.getDrugBankExternalIdentifier()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getIdentifier());
                            list.add(dbts.getResource());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Patents":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankPatent dbts : drug.getDrugBankPatent()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getNumber());
                            list.add(dbts.getCountry());
                            list.add(dbts.getApproved());
                            list.add(dbts.getExpires());
                            data.add(list);
                        }
                        setjTViewColumn(data, 4);
                    }
                    break;
                case "Affected Organisms":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankAffectedOrganism dbts : drug.getDrugBankAffectedOrganism()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getAffectedOrganisms());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "AHFSCodes":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankAHFSCode dbts : drug.getDrugBankAHFSCode()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getaHFSCodes());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "ATCCodes":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankATCCode dbts : drug.getDrugBankATCCode()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getaTCCode());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Dosages":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankDosage dbts : drug.getDrugBankDosage()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getForm());
                            list.add(dbts.getRoute());
                            list.add(dbts.getStrength());
                            data.add(list);
                        }
                        setjTViewColumn(data, 3);
                    }
                    break;
                case "Drug Interactions":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankDrugInteraction dbts : drug.getDrugBankDrugInteraction()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getDrug());
                            list.add(dbts.getDescription());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    } else {
                        if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                            for (DrugBankDrugInteraction dbts : drug.getDrugBankDrugInteraction()) {
                                if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getDrug())) {
                                    try {
                                        SearchDrugBank drugSearch = new SearchDrugBank();
                                        String nameFormat = "DB";
                                        String dname = (new Long(dbts.getDrug())).toString();
                                        for (int i = 0; i < 5 - dname.length(); i++) {
                                            nameFormat = nameFormat + "0";
                                        }
                                        nameFormat = nameFormat + dname;
                                        System.out.println("name: " + nameFormat);
                                        List<DrugBank> d = drugSearch.search(nameFormat, null);
                                        EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, d);
                                        viewProxy.setVisible();
                                        break;
                                    } catch (SQLException ex) {
                                        VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "Categories":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankCategory dbts : drug.getDrugBankCategory()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getCategory());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Food Interactions":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankFoodInteraction dbts : drug.getDrugBankFoodInteraction()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getFoodInteractions());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Prices":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankPrice dbts : drug.getDrugBankPrice()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getDescription());
                            list.add(dbts.getCost());
                            list.add(dbts.getCurrency());
                            list.add(dbts.getUnit());
                            data.add(list);
                        }
                        setjTViewColumn(data, 4);
                    }
                    break;
                case "Manofactures":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankManufacturer dbts : drug.getDrugBankManufacturer()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.isGeneric());
                            list.add(dbts.getManufacturer());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Taxonomy Substructure":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankTaxonomySubstructure dbts : drug.getDrugBankTaxonomySubstructure()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getClass1());
                            list.add(dbts.getSubstructure());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Taxonomy":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankTaxonomy dbts : drug.getDrugBankTaxonomy()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getKingdom());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Group":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankGroup dbts : drug.getDrugBankGroup()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getGroupName());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Packager":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankPackager dbts : drug.getDrugBankPackager()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getName());
                            list.add(dbts.getUrl());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "General References":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankGeneralRef dbts : drug.getDrugBankGeneralRef()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getCite());
                            list.add(dbts.getLink());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Mixtures":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankMixture dbts : drug.getDrugBankMixture()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getName());
                            list.add(dbts.getIngredients());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Second Accession Number":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankSecondAccessionNumber dbts : drug.getDrugBankSecondAccessionNumber()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getAccessionNumber());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Synonym":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankSynonym dbts : drug.getDrugBankSynonym()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getSynonym());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Brands":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankBrand dbts : drug.getDrugBankBrand()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getBrand());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
            }
        }
    }
}
