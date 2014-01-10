package org.jbiowhdesktop.datasets.protgroup.orthoxml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.datasets.protgroup.orthoxml.entities.OrthoXMLGroup;

/**
 * This class shows the COG List
 *
 * $Author$ $LastChangedDate$ $LastChangedRevision$
 *
 * @since Jan 9, 2014
 */
public class OrthoXMLListView extends AbstractListView<OrthoXMLGroup> {

    /**
     * Create the OrthoXML List object
     *
     * @param collection The collection with the entity <T> to be visualized
     * @param parentComponent the parent JComponent (Should be a JTabbedPane)
     */
    public OrthoXMLListView(Collection<OrthoXMLGroup> collection, JComponent parentComponent) {
        super(collection, parentComponent);
    }

    @Override
    public List<Object> getCollectionColumns(OrthoXMLGroup data) {
        ArrayList<Object> column = new ArrayList<>();
        column.add(false);
        column.add(data.getId());
        return column;
    }

    @Override
    public OrthoXMLGroup getCollectionElementFromTableRow(int row) {
        for (OrthoXMLGroup data : getCollection()) {
            if (getjTable().getValueAt(row, 1).equals(data.getId())) {
                return data;
            }
        }
        return null;
    }

    @Override
    public String[] createJTableHeader() {
        return new String[]{"", "Id"};
    }

}
