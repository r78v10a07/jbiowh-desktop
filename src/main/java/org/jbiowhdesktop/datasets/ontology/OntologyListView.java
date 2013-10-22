package org.jbiowhdesktop.datasets.ontology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.ontology.entities.Ontology;

/**
 * This Class is show the Ontology list
 * 
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since Feb 14, 2012
 */
public class OntologyListView extends AbstractListView<Ontology>{

    /**
     * Create the Ontology List object
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane) 
     */
    public OntologyListView(Collection<Ontology> collection, JComponent parentComponent) {
        super(collection,parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(Ontology data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getId());
        column.add(data.getName());

        return column;
    }

    @Override
    public Ontology getCollectionElementFromTableRow(int row) {
        for (Ontology ontology : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(ontology.getId())) {
                return ontology;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Id", "Name"};
    }
}
