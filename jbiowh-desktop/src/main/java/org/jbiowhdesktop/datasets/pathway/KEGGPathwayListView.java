package org.jbiowhdesktop.datasets.pathway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.pathway.KEGGPathway;

/**
 * This Class shows the KEGGPathway List
 * 
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since Feb 14, 2012
 */
public class KEGGPathwayListView extends AbstractListView<KEGGPathway>{

    /**
     * Create the KEGGPathway List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane) 
     */
    public KEGGPathwayListView(Collection<KEGGPathway> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(KEGGPathway data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getEntry());
        column.add(data.getTitle());

        return column;
    }

    @Override
    public KEGGPathway getCollectionElementFromTableRow(int row) {
        for (KEGGPathway kEGGPathway : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(kEGGPathway.getEntry())){
                return kEGGPathway;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Id", "Title"};
    }
}
