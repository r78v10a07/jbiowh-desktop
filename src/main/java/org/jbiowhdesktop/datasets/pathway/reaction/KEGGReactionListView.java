package org.jbiowhdesktop.datasets.pathway.reaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhdesktop.component.table.model.ListTableModel;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.reaction.KEGGReaction;

/**
 * This Class shows the KEGG Reaction List view 
 * 
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since Feb 14, 2012
 */
public class KEGGReactionListView extends AbstractListView<KEGGReaction>{

    /**
     * Create the KEGGReaction List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane) 
     */
    public KEGGReactionListView(Collection<KEGGReaction> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(KEGGReaction data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getEntry());
        column.add(data.getDefinition());

        return column;
    }

    @Override
    public KEGGReaction getCollectionElementFromTableRow(int row) {
        for (KEGGReaction kEGGReaction : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(kEGGReaction.getEntry())){
                return kEGGReaction;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Entry", "Equation"};
    }

    @Override
    public void setTableDimensions() {
        ListTableModel model = (ListTableModel) getjTable().getModel();
        getjTable().getColumnModel().getColumn(0).setMinWidth(30);
        getjTable().getColumnModel().getColumn(0).setPreferredWidth(30);
        getjTable().getColumnModel().getColumn(0).setMaxWidth(30);
        if (model.getColumnCount() > 2) {
            getjTable().getColumnModel().getColumn(1).setMinWidth(80);
            getjTable().getColumnModel().getColumn(1).setPreferredWidth(80);
            getjTable().getColumnModel().getColumn(1).setMaxWidth(80);
        }
    }
}
