package org.jbiowhdesktop.datasets.drug;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBank;

/**
 * This Class show the DrugBank list
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since Feb 14, 2012
 */
public class DrugBankListView extends AbstractListView<DrugBank> {

    /**
     * Create the DrugBankListView object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane)
     */
    public DrugBankListView(Collection<DrugBank> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(DrugBank data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getId());
        column.add(data.getName());
        return column;
    }

    @Override
    public DrugBank getCollectionElementFromTableRow(int row) {
        for (DrugBank drugBank : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(drugBank.getId())) {
                return drugBank;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Id", "Name"};
    }
}
