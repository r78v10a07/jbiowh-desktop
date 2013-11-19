package org.jbiowhdesktop.datasets.gene.gene;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhdesktop.datasets.EntityParserViewProxy;
import org.jbiowhpersistence.datasets.disease.omim.entities.OMIM;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2Ensembl;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2GenomicNucleotide;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2PMID;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2ProteinAccession;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2RNANucleotide;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2STS;
import org.jbiowhpersistence.datasets.gene.gene.entities.Gene2UniGene;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneGroup;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfoDBXrefs;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfoSynonyms;
import org.jbiowhpersistence.datasets.gene.genome.entities.GenePTT;

/**
 * This Class handled the Gene View
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2013-05-29 11:24:54 +0200
 * (Wed, 29 May 2013) $ $LastChangedRevision: 591 $
 *
 * @since Apr 18, 2012
 */
public class GeneDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public GeneDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        GeneInfo gene = (GeneInfo) dataSetObject;

        basicData.add("Symbol: " + gene.getSymbol() + "\n");
        basicData.add("LocusTag: " + gene.getLocusTag() + "\n");
        basicData.add("Taxonomy: " + gene.getTaxonomy().getTaxonomySynonym() + "\n");
        basicData.add("Chromosome: " + gene.getChromosome() + "\n");
        basicData.add("Map Location: " + gene.getMapLocation() + "\n");
        basicData.add("Type Of Gene: " + gene.getTypeOfGene() + "\n");
        basicData.add("Symbol From Nomenclature: " + gene.getSymbolFromNomenclature() + "\n");
        basicData.add("Nomenclature Status: " + gene.getNomenclatureStatus() + "\n");
        basicData.add("Other Designations: " + gene.getOtherDesignations() + "\n");
        basicData.add("Modification Date: " + gene.getModificationDate() + "\n");
        basicData.add("Description: " + gene.getDescription() + "\n");

        return basicData;
    }

    @Override
    public List getLinks() {
        ArrayList string = new ArrayList();
        GeneInfo gene = (GeneInfo) dataSetObject;

        if (!gene.getGene2ProteinAccessions().isEmpty()) {
            string.add("ProteinAccessions");
        }
        if (!gene.getGene2RNANucleotides().isEmpty()) {
            string.add("RNANucleotides");
        }
        if (!gene.getGene2GenomicNucleotides().isEmpty()) {
            string.add("GenomicNucleotides");
        }
        if (gene.getGenePTT() != null) {
            string.add("GenePTT");
        }
        if (!gene.getGeneInfoDBXrefs().isEmpty()) {
            string.add("DBXrefs");
        }
        if (!gene.getGeneInfoSynonyms().isEmpty()) {
            string.add("Synonyms");
        }
        if (!gene.getGene2Ensembl().isEmpty()) {
            string.add("Gene2Ensembl");
        }
        if (!gene.getGene2PMID().isEmpty()) {
            string.add("PMID");
        }
        if (!gene.getGeneGroup().isEmpty()) {
            string.add("GeneGroup");
        }
        if (!gene.getGene2STS().isEmpty()) {
            string.add("STS");
        }
        if (!gene.getGene2UniGene().isEmpty()) {
            string.add("UniGene");
        }
        if (!gene.getOntology().isEmpty()) {
            string.add("Ontology");
        }
        if (!gene.getProtein().isEmpty()) {
            string.add("Protein");
        }
        if (!gene.getkEGGGenes().isEmpty()) {
            string.add("KEGG Genes");
        }
        if (!gene.getkEGGPathways().isEmpty()) {
            string.add("KEGG Pathways");
        }
        if (!gene.getOmim().isEmpty()) {
            string.add("OMIM");
        }

        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        GeneInfo gene = (GeneInfo) dataSetObject;
        ArrayList<List<Object>> data = new ArrayList<>();
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "ProteinAccessions":
                    if (findOrShow) {
                        data.clear();
                        for (Gene2ProteinAccession dbts : gene.getGene2ProteinAccessions()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getProteinAccession() + "." + dbts.getProteinAccessionVersion());
                            list.add(dbts.getProteinGi());

                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "RNANucleotides":
                    if (findOrShow) {
                        data.clear();
                        for (Gene2RNANucleotide dbts : gene.getGene2RNANucleotides()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getRNANucleotideAccession() + "." + dbts.getRNANucleotideAccessionVersion());
                            list.add(dbts.getRNANucleotideGi());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "GenomicNucleotides":
                    if (findOrShow) {
                        data.clear();
                        for (Gene2GenomicNucleotide dbts : gene.getGene2GenomicNucleotides()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getGenomicNucleotideAccession() + "." + dbts.getGenomicNucleotideAccessionVersion());
                            list.add(dbts.getGenomicNucleotideGi());
                            list.add(dbts.getStartPositionOnTheGenomicAccession());
                            list.add(dbts.getEndPositionOnTheGenomicAccession());
                            list.add(dbts.getOrientation());
                            list.add(dbts.getAssembly());
                            data.add(list);
                        }
                        setjTViewColumn(data, 6);
                    }
                    break;
                case "GenePTT":
                    if (findOrShow) {
                        data.clear();
                        GenePTT dbts = gene.getGenePTT();
                        ArrayList<Object> list = new ArrayList<>();
                        list.add(dbts.getProteinGi());
                        list.add(dbts.getCog());
                        list.add(dbts.getLocation());
                        list.add(dbts.getStrand());
                        list.add(dbts.getProduct());
                        data.add(list);

                        setjTViewColumn(data, 5);
                    } else {
                        GenePTT dbts = gene.getGenePTT();
                        if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getProteinGi())) {
                            EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts);
                            viewProxy.setVisible();
                        }
                    }
                    break;
                case "DBXrefs":
                    if (findOrShow) {
                        data.clear();
                        for (GeneInfoDBXrefs dbts : gene.getGeneInfoDBXrefs()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getdBName());
                            list.add(dbts.getId());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "Synonyms":
                    if (findOrShow) {
                        data.clear();
                        for (GeneInfoSynonyms dbts : gene.getGeneInfoSynonyms()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getSynonyms());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "Gene2Ensembl":
                    if (findOrShow) {
                        data.clear();
                        for (Gene2Ensembl dbts : gene.getGene2Ensembl()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getEnsemblGeneIdentifier());
                            list.add(dbts.getRNANucleotideAccession());
                            list.add(dbts.getEnsemblRNAIdentifier());
                            list.add(dbts.getProteinAccession());
                            list.add(dbts.getEnsemblProteinIdentifier());
                            data.add(list);
                        }
                        setjTViewColumn(data, 5);
                    }
                    break;
                case "PMID":
                    if (findOrShow) {
                        data.clear();
                        for (Gene2PMID dbts : gene.getGene2PMID()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getPmid());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "GeneGroup":
                    if (findOrShow) {
                        data.clear();
                        for (GeneGroup dbts : gene.getGeneGroup()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getOtherGeneInfoWID());
                            list.add(dbts.getRelationship());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    }
                    break;
                case "STS":
                    if (findOrShow) {
                        data.clear();
                        for (Gene2STS dbts : gene.getGene2STS()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getUniSTSID());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "UniGene":
                    if (findOrShow) {
                        data.clear();
                        for (Gene2UniGene dbts : gene.getGene2UniGene()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(dbts.getUniGene());
                            data.add(list);
                        }
                        setjTViewColumn(data, 1);
                    }
                    break;
                case "OMIM":
                    if (findOrShow) {
                        data.clear();
                        for (OMIM omim : gene.getOmim()) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(omim.getOmimId());
                            list.add(omim.getTx());
                            data.add(list);
                        }
                        setjTViewColumn(data, 2);
                    } else {
                        for (OMIM omim : gene.getOmim()) {
                            if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(omim.getOmimId())) {
                                EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, omim);
                                viewProxy.setVisible();
                            }
                        }
                    }
                    break;
                case "Ontology":
                    getOntologyLink(findOrShow, gene.getOntology());
                    break;
                case "Protein":
                    getProteinLink(findOrShow, gene.getProtein());
                    break;
                case "KEGG Genes":
                    getKEGGGeneLink(findOrShow, gene.getkEGGGenes());
                    break;
                case "KEGG Pathways":
                    getKEGGPathwayLink(findOrShow, gene.getkEGGPathways());
                    break;
            }
        }
    }
}
