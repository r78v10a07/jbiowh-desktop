package org.jbiowhdesktop.datasets.protgroup.cog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.protgroup.cog.entities.COGOrthologousGroup;

/**
 * This class shows the COG List
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Jan 9, 2014
 */
public class COGListView extends AbstractListView<COGOrthologousGroup> {

    /**
     * Create the Pirsf List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane)
     */
    public COGListView(Collection<COGOrthologousGroup> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(COGOrthologousGroup data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getId());
        column.add(data.getGroupFunction());
        return column;
    }

    @Override
    public COGOrthologousGroup getCollectionElementFromTableRow(int row) {
        for (COGOrthologousGroup data : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(data.getId())) {
                return data;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Id", "Function"};
    }

}
