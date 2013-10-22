package org.jbiowhdesktop.datasets.gene.genome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.gene.genome.entities.GenePTT;

/**
 * This Class show the GenePTT List
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2013-05-29 11:24:54 +0200 (Wed, 29 May 2013) $
 * $LastChangedRevision: 591 $
 * @since Jul 23, 2012
 */
public class GenePTTListView extends AbstractListView<GenePTT> {

    /**
     * Create the GenePTT List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane)
     */
    public GenePTTListView(Collection<GenePTT> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(GenePTT data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getProteinGi());
        column.add(data.getGeneSymbol());
        column.add(data.getCog());
        column.add(data.getLocation());
        column.add(data.getStrand());

        return column;
    }

    @Override
    public GenePTT getCollectionElementFromTableRow(int row) {
        for (GenePTT genePTT : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(genePTT.getProteinGi())) {
                return genePTT;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "ProteinGi", "Gene", "COG", "Location", "Strand"};
    }
}
