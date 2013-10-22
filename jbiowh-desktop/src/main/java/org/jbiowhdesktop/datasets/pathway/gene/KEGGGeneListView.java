package org.jbiowhdesktop.datasets.pathway.gene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.gene.KEGGGene;

/**
 * This Class shows the KEGG Gene List view
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since Feb 14, 2012
 */
public class KEGGGeneListView extends AbstractListView<KEGGGene> {

    /**
     * Create the KEGGGeneList List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane)
     */
    public KEGGGeneListView(Collection<KEGGGene> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(KEGGGene data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getEntry());
        column.add(data.getkEGGGeneNameDirectly());
        return column;
    }

    @Override
    public KEGGGene getCollectionElementFromTableRow(int row) {
        for (KEGGGene kEGGGene : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(kEGGGene.getEntry())) {
                return kEGGGene;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Entry", "Name"};
    }
}
