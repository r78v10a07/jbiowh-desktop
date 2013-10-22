package org.jbiowhdesktop.datasets.gene.genebank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.gene.genebank.entities.GeneBank;

/**
 * This class show the GeneBank List
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2013-05-29 11:24:54 +0200 (Wed, 29 May 2013) $ $LastChangedRevision: 591 $
 *
 * @since May 9, 2013
 */
public class GeneBankListView extends AbstractListView<GeneBank> {

    /**
     * Create the GeneBank List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane)
     */
    public GeneBankListView(Collection<GeneBank> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(GeneBank data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getGi());
        column.add(data.getLocusName());
        column.add(data.getLocation());
        column.add(data.getTaxonomy().getTaxonomySynonym());

        return column;
    }

    @Override
    public GeneBank getCollectionElementFromTableRow(int row) {
        for (GeneBank geneBank : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(geneBank.getGi())) {
                return geneBank;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Gi", "LocusName", "Location", "Organism"};
    }
}
