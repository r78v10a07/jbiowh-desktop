package org.jbiowhdesktop.datasets.pathway.compound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.compound.KEGGCompound;

/**
 * This Class show the KEGG Compound List view
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since Feb 14, 2012
 */
public class KEGGCompoundListView extends AbstractListView<KEGGCompound> {

    /**
     * Create the KEGGCompound List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane) 
     */
    public KEGGCompoundListView(Collection<KEGGCompound> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(KEGGCompound data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getEntry());
        column.add(data.getFormula());

        return column;
    }

    @Override
    public KEGGCompound getCollectionElementFromTableRow(int row) {
        for (KEGGCompound kEGGCompound : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(kEGGCompound.getEntry())) {
                return kEGGCompound;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Id", "Formula"};
    }
}
