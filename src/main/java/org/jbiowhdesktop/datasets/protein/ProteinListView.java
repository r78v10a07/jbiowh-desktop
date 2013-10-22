package org.jbiowhdesktop.datasets.protein;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.datasets.protein.entities.ProteinName;

/**
 * This Class shows the Protein List
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-10-03 22:11:05 +0200
 * (Wed, 03 Oct 2012) $ $LastChangedRevision: 270 $
 *
 * @since Feb 14, 2012
 */
public class ProteinListView extends AbstractListView<Protein> {

    /**
     * Create the Protein List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane)
     */
    public ProteinListView(Collection<Protein> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(Protein protein) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(protein.getProteinNameDirected());
        column.add(protein.getProteinFirstAccessionNumber());
        return column;
    }

    @Override
    public Protein getCollectionElementFromTableRow(int row) {
        for (Protein protein : getCollection()) {
            if (protein.getProteinName() != null) {
                for (ProteinName proteinName : protein.getProteinName().values()) {
                    if (getjTable().getValueAt(row, 1).equals(proteinName.getProteinNamePK().getName())) {
                        return protein;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Name", "AccessionNumber"};
    }
}
