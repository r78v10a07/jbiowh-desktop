package org.jbiowhdesktop.datasets.protfam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.protclust.entities.UniRefEntry;

/**
 * This Class shows the UniRefEntry List
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-11-08 14:37:19 +0100 (Thu, 08 Nov 2012) $
 * $LastChangedRevision: 322 $
 * @since Feb 14, 2012
 */
public class UniRefEntryListView extends AbstractListView<UniRefEntry> {

    /**
     * Create the UniRefEntry List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane) 
     */
    public UniRefEntryListView(Collection<UniRefEntry> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(UniRefEntry data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getId());
        column.add(data.getName());

        return column;
    }

    @Override
    public UniRefEntry getCollectionElementFromTableRow(int row) {
        for (UniRefEntry uniRefEntry : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(uniRefEntry.getId())) {
                return uniRefEntry;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "ID", "Name", "Taxonomy"};
    }
}
