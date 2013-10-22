package org.jbiowhdesktop.datasets.domain.pfam;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.domain.pfam.entities.PfamADatabaseLinks;
import org.jbiowhpersistence.datasets.domain.pfam.entities.PfamAbioWH;
import org.jbiowhpersistence.datasets.domain.pfam.entities.PfamAhasPfamLiteratureReferences;
import org.jbiowhpersistence.datasets.domain.pfam.entities.PfamArchitecture;
import org.jbiowhpersistence.datasets.domain.pfam.entities.PfamClans;
import org.jbiowhpersistence.datasets.domain.pfam.entities.PfamProteomeRegions;

/**
 * This class handled the PFam View
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-12-04 11:38:52 +0100
 * (Tue, 04 Dec 2012) $ $LastChangedRevision: 377 $
 * @since Nov 27, 2012
 */
public class PFamDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public PFamDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        PfamAbioWH pfam = (PfamAbioWH) dataSetObject;

        basicData.add("Id: " + pfam.getPfamAid() + "\n");
        basicData.add("Previous Id: " + pfam.getPreviousId() + "\n");
        basicData.add("Description: " + pfam.getDescription() + "\n");
        basicData.add("Author(): " + pfam.getAuthor() + "\n");
        basicData.add("Deposited By: " + pfam.getDepositedBy() + "\n");
        basicData.add("Seed Source: " + pfam.getSeedSource() + "\n");
        basicData.add("Comment: " + pfam.getComment() + "\n");
        basicData.add("Type: " + pfam.getType() + "\n");
        basicData.add("Sequence GA: " + pfam.getSequenceGA() + "\n");
        basicData.add("Domain GA: " + pfam.getDomainGA() + "\n");
        basicData.add("Sequence TC: " + pfam.getSequenceTC() + "\n");
        basicData.add("Domain TC: " + pfam.getDomainTC() + "\n");
        basicData.add("Sequence NC: " + pfam.getSequenceNC() + "\n");
        basicData.add("Domain NC: " + pfam.getDomainNC() + "\n");
        basicData.add("Build Method: " + pfam.getBuildMethod() + "\n");
        basicData.add("Model Length: " + pfam.getModelLength() + "\n");
        basicData.add("Search Method: " + pfam.getSearchMethod() + "\n");
        basicData.add("Msv Lambda: " + pfam.getMsvLambda() + "\n");
        basicData.add("Msv Mu: " + pfam.getMsvMu() + "\n");
        basicData.add("Viterbi Lambda: " + pfam.getViterbiLambda() + "\n");
        basicData.add("Viterbi Mu: " + pfam.getViterbiMu() + "\n");
        basicData.add("Forward Lambda: " + pfam.getForwardLambda() + "\n");
        basicData.add("Forward Tau: " + pfam.getForwardTau() + "\n");
        basicData.add("Num Seed: " + pfam.getNumSeed() + "\n");
        basicData.add("Num Full: " + pfam.getNumFull() + "\n");
        basicData.add("Updated: " + pfam.getUpdated() + "\n");
        basicData.add("Created: " + pfam.getCreated() + "\n");
        basicData.add("Version: " + pfam.getVersion() + "\n");
        basicData.add("Number Archs: " + pfam.getNumberArchs() + "\n");
        basicData.add("Number Species: " + pfam.getNumberSpecies() + "\n");
        basicData.add("Number Structures: " + pfam.getNumberStructures() + "\n");
        basicData.add("Number Ncbi: " + pfam.getNumberNcbi() + "\n");
        basicData.add("Number Meta: " + pfam.getNumberMeta() + "\n");
        basicData.add("Average Length: " + pfam.getAverageLength() + "\n");
        basicData.add("Percentage Id: " + pfam.getPercentageId() + "\n");
        basicData.add("Average Coverage: " + pfam.getAverageCoverage() + "\n");
        basicData.add("Change Status: " + pfam.getChangeStatus() + "\n");
        basicData.add("Seed Consensus: " + pfam.getSeedConsensus() + "\n");
        basicData.add("Full Consensus: " + pfam.getFullConsensus() + "\n");
        basicData.add("Number Shuffled Hits: " + pfam.getNumberShuffledHits() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        ArrayList string = new ArrayList();
        PfamAbioWH pfam = (PfamAbioWH) dataSetObject;

        if (!pfam.getPfamClanses().isEmpty()) {
            string.add("Clans");
        }

        if (!pfam.getPfamADatabaseLinkses().isEmpty()) {
            string.add("DB Links");
        }
        
        if (!pfam.getPfamAhasPfamLiteratureReferences().isEmpty()) {
            string.add("Literature References");
        }
        
        if (!pfam.getPfamArchitectures().isEmpty()) {
            string.add("Architectures");
        }
        
        if (!pfam.getPfamProteomeRegions().isEmpty()) {
            string.add("Proteome Regions");
        }
        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        PfamAbioWH pfam = (PfamAbioWH) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Proteome Regions":
                    if (findOrShow) {
                        data.clear();
                        for (PfamProteomeRegions dbts : pfam.getPfamProteomeRegions().values()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getPfamCompleteProteomes().getSpecies());
                            list.add(dbts.getPfamCompleteProteomes().getGrouping());
                            list.add(dbts.getCount());
                            data.add(list);
                        }
                        setjTViewColumn(data, 3);
                    }
                    break;
                case "Architectures":
                    if (findOrShow) {
                        data.clear();
                        for (PfamArchitecture dbts : pfam.getPfamArchitectures()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getArchitectureAcc());
                            list.add(dbts.getNoSeqs());
                            list.add(dbts.getTypeExample());
                            data.add(list);
                        }
                        setjTViewColumn(data, 3);
                    }
                    break;
                case "Literature References":
                    if (findOrShow) {
                        data.clear();
                        for (PfamAhasPfamLiteratureReferences dbts : pfam.getPfamAhasPfamLiteratureReferences().values()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getPfamLiteratureReferences().getJournal());
                            list.add(dbts.getPfamLiteratureReferences().getTitle());
                            list.add(dbts.getPfamLiteratureReferences().getPmid());
                            list.add(dbts.getComment());
                            list.add(dbts.getPfamAhasPfamLiteratureReferencesPK().getOrderAdded());
                            data.add(list);
                        }
                        setjTViewColumn(data, 5);
                    }
                    break;
                case "Clans":
                    if (findOrShow) {
                        data.clear();
                        for (PfamClans dbts : pfam.getPfamClanses()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getClanAcc());
                            list.add(dbts.getClanId());
                            list.add(dbts.getClanComment());
                            list.add(dbts.getClanDescription());
                            data.add(list);
                        }
                        setjTViewColumn(data, 4);
                    }
                    break;
                case "DB Links":
                    if (findOrShow) {
                        data.clear();
                        for (PfamADatabaseLinks dbts : pfam.getPfamADatabaseLinkses()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getDbId());
                            list.add(dbts.getComment());
                            list.add(dbts.getDbLink());
                            list.add(dbts.getOtherParams());
                            data.add(list);
                        }
                        setjTViewColumn(data, 4);
                    }
                    break;
            }
        }
    }
}
