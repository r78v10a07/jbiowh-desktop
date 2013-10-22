package org.jbiowhdesktop.datasets.pathway.glycan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.glycan.KEGGGlycan;

/**
 * This Class show the KEGG Glycan List view
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since Feb 14, 2012
 */
public class KEGGGlycanListView extends AbstractListView<KEGGGlycan> {

    /**
     * Create the KEGGGlycan List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane) 
     */
    public KEGGGlycanListView(Collection<KEGGGlycan> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(KEGGGlycan data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getEntry());

        return column;
    }

    @Override
    public KEGGGlycan getCollectionElementFromTableRow(int row) {
        for (KEGGGlycan kEGGGlycan : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(kEGGGlycan.getEntry())) {
                return kEGGGlycan;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Entry"};
    }
}
