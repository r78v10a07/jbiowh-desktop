package org.jbiowhdesktop.datasets.protgroup.pirsf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.protgroup.pirsf.entities.Pirsf;

/**
 * This class shows the Pirsf List
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Dec 12, 2013
 */
public class PirsfListView extends AbstractListView<Pirsf> {

    /**
     * Create the Pirsf List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane)
     */
    public PirsfListView(Collection<Pirsf> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(Pirsf data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getpIRSFnumber());
        column.add(data.getName());
        column.add(data.getCurationStatus());
        column.add(data.getParent());
        return column;
    }

    @Override
    public Pirsf getCollectionElementFromTableRow(int row) {
        for (Pirsf data : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(data.getpIRSFnumber())) {
                return data;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Id", "Name", "Status", "Parent"};
    }

}
