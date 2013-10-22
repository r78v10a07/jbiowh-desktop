package org.jbiowhdesktop.datasets.taxonomy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.taxonomy.entities.Taxonomy;

/**
 * This Class show the Taxonomy List
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since Feb 13, 2012
 */
public class TaxonomyListView extends AbstractListView<Taxonomy> {

    /**
     * Create the Taxonomy List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane) 
     */
    public TaxonomyListView(Collection<Taxonomy> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(Taxonomy data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getTaxId());
        column.add(data.getTaxonomySynonym());

        return column;
    }

    @Override
    public Taxonomy getCollectionElementFromTableRow(int row) {
        for (Taxonomy taxonomy : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(taxonomy.getTaxId())) {
                return taxonomy;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Id", "Synonym"};
    }
}
