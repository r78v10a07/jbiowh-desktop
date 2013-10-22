package org.jbiowhdesktop.datasets.protfam;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.datasets.protclust.entities.UniRefEntry;
import org.jbiowhpersistence.datasets.protclust.entities.UniRefEntryProperty;

/**
 * This Class handled the ProtFam View
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-11-08 14:37:19 +0100 (Thu, 08 Nov 2012) $
 * $LastChangedRevision: 322 $
 * @since Apr 20, 2012
 */
public class ProtFamDataSetView extends AbstractDataSetView {

    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public ProtFamDataSetView(JComponent parentComponent, Object dataSetObject) {
        super(parentComponent, dataSetObject);
    }

    @Override
    public List getBasicData() {
        List<String> basicData = new ArrayList();
        UniRefEntry entry = (UniRefEntry) dataSetObject;


        basicData.add("Name: " + entry.getName() + "\n");
        if (entry.getTaxonomy() != null) {
            basicData.add("Taxonomy: " + entry.getTaxonomy().getTaxonomySynonym() + "\n");
        }
        basicData.add("Updated: " + entry.getUpdated() + "\n");
        if (entry.getUniRefEntryProperty() != null) {
            if (!entry.getUniRefEntryProperty().isEmpty()) {
                basicData.add("Properties: \n");
                for (UniRefEntryProperty property : entry.getUniRefEntryProperty()) {
                    basicData.add("Type: " + property.getType() + "\n");
                    basicData.add("Value: " + property.getValue() + "\n");
                }
            }
        }
        return basicData;
    }

    @Override
    public List getLinks() {
        ArrayList string = new ArrayList();
        UniRefEntry entry = (UniRefEntry) dataSetObject;

        if (!entry.getProtein().isEmpty()) {
            string.add("Protein");
        }

        return string;
    }

    @Override
    public void jTLinkAction(boolean findOrShow) {
        UniRefEntry entry = (UniRefEntry) dataSetObject;
        if (jCLinks.getSelectedItem() != null) {
            switch (jCLinks.getSelectedItem().toString()) {
                case "Protein":
                    getProteinLink(findOrShow, entry.getProtein());
                    break;
            }
        }
    }
}
