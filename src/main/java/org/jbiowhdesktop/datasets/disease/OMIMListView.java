package org.jbiowhdesktop.datasets.disease;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.disease.omim.entities.OMIM;

/**
 * This Class show the OMIM list
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-10-03 22:11:05 +0200
 * (Wed, 03 Oct 2012) $ $LastChangedRevision: 270 $
 *
 * @since Jul 25, 2012
 */
public class OMIMListView extends AbstractListView<OMIM> {

    /**
     * Create the OMIMListView object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane)
     */
    public OMIMListView(Collection<OMIM> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(OMIM data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getOmimId());
        if (!data.getOmimTIs().isEmpty()) {
            column.add(data.getOmimTIs().values().iterator().next().getOMIMTIPK().getTi());
        } else {
            column.add("");
        }
        return column;
    }

    @Override
    public OMIM getCollectionElementFromTableRow(int row) {
        for (OMIM omim : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(omim.getOmimId())) {
                return omim;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Id", "Title"};
    }
}
