package org.jbiowhdesktop.datasets.gene.gene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;

/**
 * This Class show the GeneInfo List
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2013-05-29 11:24:54 +0200 (Wed, 29 May 2013) $
 * $LastChangedRevision: 591 $
 * @since Feb 14, 2012
 */
public class GeneInfoListView extends AbstractListView<GeneInfo>{

    /**
     * Create the GeneInfo List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane) 
     */
    public GeneInfoListView(Collection<GeneInfo> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(GeneInfo data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getGeneID());
        column.add(data.getSymbol());
        column.add(data.getLocusTag());
        column.add(data.getTaxonomy().getTaxonomySynonym());

        return column;
    }

    @Override
    public GeneInfo getCollectionElementFromTableRow(int row) {
        for (GeneInfo geneInfo : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(geneInfo.getGeneID())) {
                return geneInfo;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Id", "Symbol", "LocusTag", "Organism"};
    }
}
