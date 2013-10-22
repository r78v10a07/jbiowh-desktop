package org.jbiowhdesktop.datasets.domain.pfam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.domain.pfam.entities.PfamAbioWH;

/**
 * This class shows the Pfam List
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-12-27 15:38:46 +0100 (Thu, 27 Dec 2012) $
 * $LastChangedRevision: 377 $
 * @since Nov 27, 2012
 */
public class PFamListView extends AbstractListView<PfamAbioWH> {

    /**
     * Create the Pfam List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane)
     */
    public PFamListView(Collection<PfamAbioWH> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(PfamAbioWH data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getPfamAacc());
        column.add(data.getPfamAid());
        return column;
    }

    @Override
    public PfamAbioWH getCollectionElementFromTableRow(int row) {
        for (PfamAbioWH pfam : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(pfam.getPfamAacc())) {
                return pfam;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "ACC", "ID"};
    }
}
