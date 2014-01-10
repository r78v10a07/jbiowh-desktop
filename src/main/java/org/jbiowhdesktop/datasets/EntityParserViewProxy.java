package org.jbiowhdesktop.datasets;

import java.util.Collection;
import javax.swing.JComponent;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhdesktop.datasets.disease.OMIMDataSetView;
import org.jbiowhdesktop.datasets.domain.pfam.PFamDataSetView;
import org.jbiowhdesktop.datasets.drug.DrugBankDataSetView;
import org.jbiowhdesktop.datasets.gene.gene.GeneDataSetView;
import org.jbiowhdesktop.datasets.gene.genebank.GeneBankDataSetView;
import org.jbiowhdesktop.datasets.gene.genome.GenePTTDataSetView;
import org.jbiowhdesktop.datasets.ontology.OntologyDataSetView;
import org.jbiowhdesktop.datasets.pathway.KEGGPathwayDataSetView;
import org.jbiowhdesktop.datasets.pathway.compound.KEGGCompoundDataSetView;
import org.jbiowhdesktop.datasets.pathway.enzyme.KEGGEnzymeDataSetView;
import org.jbiowhdesktop.datasets.pathway.gene.KEGGGeneDataSetView;
import org.jbiowhdesktop.datasets.pathway.glycan.KEGGGlycanDataSetView;
import org.jbiowhdesktop.datasets.pathway.reaction.KEGGReactionDataSetView;
import org.jbiowhdesktop.datasets.protein.ProteinDataSetView;
import org.jbiowhdesktop.datasets.protfam.ProtFamDataSetView;
import org.jbiowhdesktop.datasets.protgroup.cog.COGDataSetView;
import org.jbiowhdesktop.datasets.protgroup.orthoxml.OrthoXMLDataSetView;
import org.jbiowhdesktop.datasets.protgroup.pirsf.PirsfDataSetView;
import org.jbiowhdesktop.datasets.taxonomy.TaxonomyDataSetView;
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
import org.jbiowhpersistence.datasets.protgroup.cog.entities.COGOrthologousGroup;
import org.jbiowhpersistence.datasets.protgroup.orthoxml.entities.OrthoXMLGroup;
import org.jbiowhpersistence.datasets.protgroup.pirsf.entities.Pirsf;
import org.jbiowhpersistence.datasets.taxonomy.entities.Taxonomy;

/**
 * This Class is to visualize the correct Dataset Entity
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2013-05-29 11:24:54 +0200
 * (Wed, 29 May 2013) $ $LastChangedRevision: 591 $
 *
 * @since Mar 26, 2012
 */
public class EntityParserViewProxy {

    private AbstractDataSetView view;

    public EntityParserViewProxy(JComponent parentComponent, Object dataSetObject) {
        view = getDataSetView(parentComponent, dataSetObject);
    }

    /**
     * Set the panel visible
     */
    public void setVisible() {
        if (view != null) {
            view.setVisible();
        }
    }

    private AbstractDataSetView getDataSetView(JComponent parentComponent, Object dataSetObject) throws
            NullPointerException {
        if (dataSetObject != null) {
            VerbLogger.getInstance().log(this.getClass(), "\tEntityParserViewProxy Object Class: " + dataSetObject.getClass().getSimpleName());
            if (dataSetObject instanceof Collection) {
                if (((Collection) dataSetObject).size() == 1) {
                    dataSetObject = ((Collection) dataSetObject).iterator().next();
                }
            }
            if (dataSetObject instanceof Taxonomy) {
                return new TaxonomyDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof Ontology) {
                return new OntologyDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof GeneInfo) {
                return new GeneDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof GenePTT) {
                return new GenePTTDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof Protein) {
                return new ProteinDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof DrugBank) {
                return new DrugBankDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof KEGGCompound) {
                return new KEGGCompoundDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof KEGGEnzyme) {
                return new KEGGEnzymeDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof KEGGGene) {
                return new KEGGGeneDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof KEGGGlycan) {
                return new KEGGGlycanDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof KEGGReaction) {
                return new KEGGReactionDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof KEGGPathway) {
                return new KEGGPathwayDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof UniRefEntry) {
                return new ProtFamDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof OMIM) {
                return new OMIMDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof PfamAbioWH) {
                return new PFamDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof GeneBank) {
                return new GeneBankDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof Pirsf) {
                return new PirsfDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof COGOrthologousGroup) {
                return new COGDataSetView(parentComponent, dataSetObject);
            } else if (dataSetObject instanceof OrthoXMLGroup) {
                return new OrthoXMLDataSetView(parentComponent, dataSetObject);
            }
            throw new NullPointerException("\tThe EntityParserViewProxy Object Class: " + dataSetObject.getClass().getSimpleName() + " does not have a DataSetView extends");
        } else {
            return null;
        }
    }
}
