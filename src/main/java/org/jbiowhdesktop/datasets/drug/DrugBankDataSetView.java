package org.jbiowhdesktop.datasets.drug;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhdesktop.datasets.EntityParserViewProxy;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBank;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankAHFSCodes;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankATCCodes;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankAffectedOrganisms;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankBrands;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankCalculatedProperties;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankCategories;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankDosages;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankDrugInteractions;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankExperimentalProperties;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankExternalIdentifiers;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankExternalLinks;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankFoodInteractions;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankGeneralRef;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankGroup;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankManufacturers;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankMixtures;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankPackagers;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankPatents;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankPrices;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankSecondAccessionNumbers;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankSynonyms;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankTaxonomy;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBankTaxonomySubstructures;
import org.jbiowhpersistence.datasets.drug.drugbank.search.SearchDrugBank;

/**
 * This Class handled the DrugBank View
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2013-03-19 09:38:47 +0100 (Tue, 19 Mar 2013) $
 * $LastChangedRevision: 396 $
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
        basicData.add("CAS: " + drug.getCASNumber() + "\n");
        basicData.add("Version: " + drug.getVersion() + "\n");
        basicData.add("Updated: " + drug.getUpdated() + "\n");
        basicData.add("Created: " + drug.getCreated() + "\n\n");
        basicData.add("Description: " + drug.getDescription() + "\n");
        basicData.add("Indication: " + drug.getIndication() + "\n");
        basicData.add("Pharmacology: " + drug.getPharmacology() + "\n");
        basicData.add("Mechanism Of Action: " + drug.getMechanismOfAction() + "\n");
        basicData.add("Toxicity: " + drug.getToxicity() + "\n");
        basicData.add("Biotransformation: " + drug.getBiotransformation() + "\n");
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

        if (!drug.getDrugBankSecondAccessionNumbers().isEmpty()) {
            string.add("Second Accession Number");
        }
        if (!drug.getDrugBankSynonyms().isEmpty()) {
            string.add("Synonym");
        }
        if (!drug.getDrugBankBrands().isEmpty()) {
            string.add("Brands");
        }
        if (!drug.getDrugBankMixtures().isEmpty()) {
            string.add("Mixtures");
        }
        if (!drug.getDrugBankGeneralRef().isEmpty()) {
            string.add("General References");
        }
        if (!drug.getDrugBankPackagers().isEmpty()) {
            string.add("Packagers");
        }
        if (!drug.getDrugBankManufacturers().isEmpty()) {
            string.add("Manofactures");
        }
        if (!drug.getDrugBankGroup().isEmpty()) {
            string.add("Group");
        }
        if (!drug.getDrugBankTaxonomy().isEmpty()) {
            string.add("Taxonomy");
        }
        if (!drug.getDrugBankTaxonomySubstructures().isEmpty()) {
            string.add("Taxonomy Substructure");
        }
        if (!drug.getDrugBankPrices().isEmpty()) {
            string.add("Prices");
        }
        if (!drug.getDrugBankFoodInteractions().isEmpty()) {
            string.add("Food Interactions");
        }
        if (!drug.getDrugBankCategories().isEmpty()) {
            string.add("Categories");
        }
        if (!drug.getDrugBankDrugInteractions().isEmpty()) {
            string.add("Drug Interactions");
        }
        if (!drug.getDrugBankDosages().isEmpty()) {
            string.add("Dosages");
        }
        if (!drug.getDrugBankATCCodes().isEmpty()) {
            string.add("ATCCodes");
        }
        if (!drug.getDrugBankAHFSCodes().isEmpty()) {
            string.add("AHFSCodes");
        }
        if (!drug.getDrugBankAffectedOrganisms().isEmpty()) {
            string.add("Affected Organisms");
        }
        if (!drug.getDrugBankPatents().isEmpty()) {
            string.add("Patents");
        }
        if (!drug.getDrugBankExternalIdentifiers().isEmpty()) {
            string.add("External Identifiers");
        }
        if (!drug.getDrugBankExternalLinks().isEmpty()) {
            string.add("External Links");
        }
        if (!drug.getDrugBankCalculatedProperties().isEmpty()) {
            string.add("Calculated Properties");
        }
        if (!drug.getDrugBankExperimentalProperties().isEmpty()) {
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
                        for (DrugBankExperimentalProperties dbts : drug.getDrugBankExperimentalProperties()) {
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
                        for (DrugBankCalculatedProperties dbts : drug.getDrugBankCalculatedProperties()) {
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
                        for (DrugBankExternalLinks dbts : drug.getDrugBankExternalLinks()) {
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
                        for (DrugBankExternalIdentifiers dbts : drug.getDrugBankExternalIdentifiers()) {
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
                        for (DrugBankPatents dbts : drug.getDrugBankPatents()) {
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
                        for (DrugBankAffectedOrganisms dbts : drug.getDrugBankAffectedOrganisms()) {
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
                        for (DrugBankAHFSCodes dbts : drug.getDrugBankAHFSCodes()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getAHFSCodes());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "ATCCodes":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankATCCodes dbts : drug.getDrugBankATCCodes()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getATCCode());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Dosages":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankDosages dbts : drug.getDrugBankDosages()) {
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
                        for (DrugBankDrugInteractions dbts : drug.getDrugBankDrugInteractions().values()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getDrugBankDrugInteractionsPK().getDrug());
                            list.add(dbts.getDescription());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    } else {
                        if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                            for (DrugBankDrugInteractions dbts : drug.getDrugBankDrugInteractions().values()) {
                                if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getDrugBankDrugInteractionsPK().getDrug())) {
                                    try {
                                        SearchDrugBank drugSearch = new SearchDrugBank();
                                        String nameFormat = "DB";
                                        String dname = (new Long(dbts.getDrugBankDrugInteractionsPK().getDrug())).toString();
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
                        for (DrugBankCategories dbts : drug.getDrugBankCategories()) {
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
                        for (DrugBankFoodInteractions dbts : drug.getDrugBankFoodInteractions()) {
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
                        for (DrugBankPrices dbts : drug.getDrugBankPrices()) {
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
                        for (DrugBankManufacturers dbts : drug.getDrugBankManufacturers()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getGeneric());
                            list.add(dbts.getManufacturer());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Taxonomy Substructure":
                    if (findOrShow) {
                        data.clear();
                        for (DrugBankTaxonomySubstructures dbts : drug.getDrugBankTaxonomySubstructures()) {
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
                        for (DrugBankPackagers dbts : drug.getDrugBankPackagers()) {
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
                        for (DrugBankMixtures dbts : drug.getDrugBankMixtures()) {
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
                        for (DrugBankSecondAccessionNumbers dbts : drug.getDrugBankSecondAccessionNumbers()) {
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
                        for (DrugBankSynonyms dbts : drug.getDrugBankSynonyms()) {
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
                        for (DrugBankBrands dbts : drug.getDrugBankBrands()) {
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
