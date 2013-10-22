package org.jbiowhdesktop.datasets;

import java.util.Collection;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhcore.utility.constrains.JPLConstrains;
import org.jbiowhdesktop.component.panel.ConstrainPanel;
import org.jbiowhdesktop.datasets.disease.OMIMListView;
import org.jbiowhdesktop.datasets.domain.pfam.PFamListView;
import org.jbiowhdesktop.datasets.drug.DrugBankListView;
import org.jbiowhdesktop.datasets.gene.gene.GeneInfoListView;
import org.jbiowhdesktop.datasets.gene.genebank.GeneBankListView;
import org.jbiowhdesktop.datasets.gene.genome.GenePTTListView;
import org.jbiowhdesktop.datasets.ontology.OntologyListView;
import org.jbiowhdesktop.datasets.pathway.KEGGPathwayListView;
import org.jbiowhdesktop.datasets.pathway.compound.KEGGCompoundListView;
import org.jbiowhdesktop.datasets.pathway.enzyme.KEGGEnzymeListView;
import org.jbiowhdesktop.datasets.pathway.gene.KEGGGeneListView;
import org.jbiowhdesktop.datasets.pathway.glycan.KEGGGlycanListView;
import org.jbiowhdesktop.datasets.pathway.reaction.KEGGReactionListView;
import org.jbiowhdesktop.datasets.protein.ProteinListView;
import org.jbiowhdesktop.datasets.protfam.UniRefEntryListView;
import org.jbiowhdesktop.datasets.taxonomy.TaxonomyListView;
import org.jbiowhpersistence.datasets.disease.omim.entities.OMIM;
import org.jbiowhpersistence.datasets.domain.pfam.entities.PfamAbioWH;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBank;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.datasets.gene.genebank.entities.GeneBank;
import org.jbiowhpersistence.datasets.gene.genome.entities.GenePTT;
import org.jbiowhpersistence.datasets.ontology.entities.Ontology;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.compound.KEGGCompound;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.enzyme.KEGGEnzyme;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.gene.KEGGGene;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.glycan.KEGGGlycan;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.pathway.KEGGPathway;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.reaction.KEGGReaction;
import org.jbiowhpersistence.datasets.protclust.entities.UniRefEntry;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.datasets.taxonomy.entities.Taxonomy;

/**
 * This Class is the List View Factory
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2013-03-19 09:38:47 +0100
 * (Tue, 19 Mar 2013) $ $LastChangedRevision: 591 $
 * @since Feb 13, 2012
 */
public class ListViewProxy {

    private static ListViewProxy singleton;

    private ListViewProxy() {
    }

    /**
     * Return a ListViewProxy
     *
     * @return a ListViewProxy
     */
    public static synchronized ListViewProxy getInstance() {
        if (singleton == null) {
            singleton = new ListViewProxy();
        }
        return singleton;
    }

    /**
     * Creates the ListView object to show the results
     *
     * @param object the collection with the results objects
     * @param parentComponent the parent JComponent
     */
    public void setVisible(Object object, JComponent parentComponent) {
        if (object == null) {
            int type = JOptionPane.INFORMATION_MESSAGE;
            JOptionPane.showMessageDialog(parentComponent, "Found 0 elements", "Empty search", type);
            return;
        }
        VerbLogger.getInstance().log(this.getClass(), "\tCollection Class: " + object.getClass().getSimpleName());
        if (object instanceof Collection) {
            Collection collection = (Collection) object;
            if (!collection.isEmpty()) {
                Object element = collection.iterator().next();
                if (element instanceof Taxonomy) {
                    TaxonomyListView list = new TaxonomyListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof Ontology) {
                    OntologyListView list = new OntologyListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof DrugBank) {
                    DrugBankListView list = new DrugBankListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof GeneInfo) {
                    GeneInfoListView list = new GeneInfoListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof GenePTT) {
                    GenePTTListView list = new GenePTTListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof KEGGCompound) {
                    KEGGCompoundListView list = new KEGGCompoundListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof KEGGEnzyme) {
                    KEGGEnzymeListView list = new KEGGEnzymeListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof KEGGGene) {
                    KEGGGeneListView list = new KEGGGeneListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof KEGGGlycan) {
                    KEGGGlycanListView list = new KEGGGlycanListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof KEGGReaction) {
                    KEGGReactionListView list = new KEGGReactionListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof KEGGPathway) {
                    KEGGPathwayListView list = new KEGGPathwayListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof Protein) {
                    ProteinListView list = new ProteinListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof UniRefEntry) {
                    UniRefEntryListView list = new UniRefEntryListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof OMIM) {
                    OMIMListView list = new OMIMListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof PfamAbioWH) {
                    PFamListView list = new PFamListView(collection, parentComponent);
                    list.setVisible();
                } else if (element instanceof GeneBank) {
                    GeneBankListView list = new GeneBankListView(collection, parentComponent);
                    list.setVisible();
                }
            } else {
                int type = JOptionPane.INFORMATION_MESSAGE;
                JOptionPane.showMessageDialog(parentComponent, "Found 0 elements", "Empty search", type);
            }
        } else if (object instanceof JPLConstrains) {
            ConstrainPanel constrainPanel = new ConstrainPanel((JPLConstrains) object, parentComponent);
            constrainPanel.setVisible();
        } else {
            int type = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog(parentComponent, "I can't visualize this data type", "Unknown data type", type);
        }
    }
}
