package org.jbiowhdesktop.datasets.pathway.enzyme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.enzyme.KEGGEnzyme;

/**
 * This Class show the KEGG Enzyme List view
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since Feb 14, 2012
 */
public class KEGGEnzymeListView extends AbstractListView<KEGGEnzyme> {

    /**
     * Create the KEGGEnzyme List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane)
     */
    public KEGGEnzymeListView(Collection<KEGGEnzyme> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(KEGGEnzyme data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getEntry());
        column.add(data.getkEGGEnzymeNameDirectly());
        return column;
    }

    @Override
    public KEGGEnzyme getCollectionElementFromTableRow(int row) {
        for (KEGGEnzyme kEGGEnzyme : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(kEGGEnzyme.getEntry())) {
                return kEGGEnzyme;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Entry", "Name"};
    }
}
