package org.jbiowhdesktop.component.panel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.table.TableModel;
import javax.swing.text.*;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhdesktop.actions.tabbedpanel.TabbedPanelCloseActionListener;
import org.jbiowhdesktop.component.panel.tabbedpanel.ClosePanel;
import org.jbiowhdesktop.component.table.model.ListTableModel;
import org.jbiowhdesktop.datasets.EntityParserViewProxy;
import org.jbiowhpersistence.datasets.drug.drugbank.entities.DrugBank;
import org.jbiowhpersistence.datasets.gene.gene.entities.GeneInfo;
import org.jbiowhpersistence.datasets.ontology.entities.Ontology;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.compound.KEGGCompound;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.enzyme.KEGGEnzyme;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.gene.KEGGGene;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.glycan.KEGGGlycan;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.pathway.KEGGPathway;
import org.jbiowhpersistence.datasets.pathway.kegg.entities.reaction.KEGGReaction;
import org.jbiowhpersistence.datasets.protein.entities.Protein;
import org.jbiowhpersistence.datasets.protein.entities.ProteinName;
import org.jbiowhpersistence.utils.entitymanager.EntityParserFieldProxy;

/**
 * This JPanel is the abstract dataset entity type view
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-11-08 14:37:19 +0100
 * (Thu, 08 Nov 2012) $ $LastChangedRevision: 591 $
 *
 * @since Mar 26, 2012
 */
public abstract class AbstractDataSetView extends javax.swing.JPanel {

    //TODO Agregar un search como en el list para buscar en elos resultados de la tabla de abajo
    /**
     * Creates new form AbstractDataSetView
     *
     * @param parentComponent the parent JComponent
     * @param dataSetObject the dataset entity type
     */
    public AbstractDataSetView(JComponent parentComponent, Object dataSetObject) {
        this.parentComponent = parentComponent;
        this.dataSetObject = dataSetObject;
        uniqueKey = UUID.randomUUID();
        initComponents();
    }

    /**
     * Get the basic data to show
     *
     * @return a String object
     */
    public abstract List getBasicData();

    /**
     * Get the basic data to show
     *
     * @return a String object
     */
    public abstract List getLinks();

    /**
     * Set the JComboBox action and the Table mouse click action
     *
     * @param findOrShow false for JComboBox action
     */
    public abstract void jTLinkAction(boolean findOrShow);

    /**
     * Set the panel visible
     */
    public void setVisible() {
        if (parentComponent instanceof JTabbedPane) {
            ((JTabbedPane) parentComponent).addTab(EntityParserFieldProxy.getInstance().getId(dataSetObject), this);
            ((JTabbedPane) parentComponent).setTabComponentAt(((JTabbedPane) parentComponent).getTabCount() - 1,
                    new ClosePanel((JTabbedPane) parentComponent,
                            EntityParserFieldProxy.getInstance().getId(dataSetObject), uniqueKey, new TabbedPanelCloseActionListener(parentComponent)));
            ((JTabbedPane) parentComponent).setSelectedIndex(((JTabbedPane) parentComponent).getTabCount() - 1);
        }
    }

    /**
     * Get the unique UUID for this object
     *
     * @return a UUID Object
     */
    public UUID getUniqueKey() {
        return uniqueKey;
    }

    /**
     * Get the DataSet Object View
     *
     * @return a Object
     */
    public Object getDataSetObject() {
        return dataSetObject;
    }

    /**
     * Set the Link Table format
     *
     * @param data the data to be visualized
     * @param column the number of columns
     */
    protected void setjTViewColumn(List<List<Object>> data, int column) {
        ListTableModel model = (ListTableModel) jTLinks.getModel();
        if (model.getColumnCount() != column) {
            String[] names = new String[column];
            boolean[] canEdit = new boolean[column];
            Class[] type = new Class[column];
            for (int i = 0; i < column; i++) {
                names[i] = "";
                canEdit[i] = false;
                type[i] = java.lang.Object.class;
            }
            model.setColumnNames(names);
            model.setEdit(canEdit);
            model.setTypes(type);
            model.fireTableStructureChanged();
        }
        model.setContents(data);
        if (column > 1) {
            jTLinks.getColumnModel().getColumn(0).setMinWidth(140);
            jTLinks.getColumnModel().getColumn(0).setPreferredWidth(140);
            jTLinks.getColumnModel().getColumn(0).setMaxWidth(140);
        }
    }

    /**
     * Get the Ontology action
     *
     * @param findOrShow false for JComboBox action
     * @param ontology the Ontology collection
     */
    protected void getOntologyLink(boolean findOrShow, Collection<Ontology> ontology) {
        ArrayList<List<Object>> data = new ArrayList<>();
        if (findOrShow) {
            data.clear();
            for (Ontology ont : ontology) {
                ArrayList<Object> list = new ArrayList<>();
                list.add(ont.getId());
                list.add(ont.getName());
                data.add(list);
            }
            setjTViewColumn(data, 2);
        } else {
            if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                for (Ontology dbts : ontology) {
                    if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getId())) {
                        EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts);
                        viewProxy.setVisible();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Get the Gene action
     *
     * @param findOrShow false for JComboBox action
     * @param gene the Gene collection
     */
    protected void getGeneLink(boolean findOrShow, Object gene) {
        ArrayList<List<Object>> data = new ArrayList<>();
        if (findOrShow) {
            data.clear();
            Collection<GeneInfo> g = null;
            if (gene instanceof Collection) {
                g = (Collection) gene;
            } else if (gene instanceof GeneInfo) {
                g = new ArrayList();
                g.add((GeneInfo) gene);
            }
            if (g != null) {
                for (GeneInfo g1 : (Collection<GeneInfo>) g) {
                    ArrayList<Object> list = new ArrayList<>();
                    list.add(g1.getGeneID());
                    list.add(g1.getSymbol());
                    if (g1.getTaxonomy() != null) {
                        list.add(g1.getTaxonomy().getTaxonomySynonym());
                    } else {
                        list.add("");
                    }
                    data.add(list);
                }
            }
            setjTViewColumn(data, 3);
        } else {
            if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                Collection<GeneInfo> g = null;
                if (gene instanceof Collection) {
                    g = (Collection) gene;
                } else if (gene instanceof GeneInfo) {
                    g = new ArrayList();
                    g.add((GeneInfo) gene);
                }
                if (g != null) {
                    for (GeneInfo dbts : (Collection<GeneInfo>) g) {
                        if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getGeneID())) {
                            EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts);
                            viewProxy.setVisible();
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the Protein action
     *
     * @param findOrShow false for JComboBox action
     * @param proteinObject
     */
    protected void getProteinLink(boolean findOrShow, Object proteinObject) {
        ArrayList<List<Object>> data = new ArrayList<>();
        if (findOrShow) {
            data.clear();
            Collection<Protein> protein = null;
            if (proteinObject instanceof Collection) {
                protein = (Collection) proteinObject;
            } else if (proteinObject instanceof Protein) {
                protein = new ArrayList();
                protein.add((Protein) proteinObject);
            }
            if (protein != null) {
                for (Protein dbts : protein) {
                    ArrayList<Object> list = new ArrayList<>();
                    list.add(dbts.getProteinNameDirected());
                    list.add(dbts.getProteinLongNameDirected());
                    if (dbts.getTaxonomy() != null) {
                        list.add(dbts.getTaxonomy().getTaxonomySynonym());
                    } else {
                        list.add("");
                    }
                    data.add(list);
                }
            }
            setjTViewColumn(data, 3);
        } else {
            if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                Collection<Protein> protein = null;
                if (proteinObject instanceof Collection) {
                    protein = (Collection) proteinObject;
                } else if (proteinObject instanceof Protein) {
                    protein = new ArrayList();
                    protein.add((Protein) proteinObject);
                }
                if (protein != null) {
                    for (Protein dbts : protein) {
                        if (dbts.getProteinName() != null) {
                            for (ProteinName proteinName : dbts.getProteinName()) {
                                if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(proteinName.getName())) {
                                    EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts);
                                    viewProxy.setVisible();
                                    break;
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * Get the KEGGCompound action
     *
     * @param findOrShow false for JComboBox action
     * @param kEGGCompound the KEGGCompound collection
     */
    protected void getKEGGCompoundLink(boolean findOrShow, Collection<KEGGCompound> kEGGCompound) {
        ArrayList<List<Object>> data = new ArrayList<>();
        if (findOrShow) {
            data.clear();
            for (KEGGCompound g : kEGGCompound) {
                ArrayList<Object> list = new ArrayList<>();
                list.add(g.getEntry());
                list.add(g.getFormula());
                list.add(g.getComment());
                data.add(list);
            }
            setjTViewColumn(data, 3);
        } else {
            if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                for (KEGGCompound dbts : kEGGCompound) {
                    if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getEntry())) {
                        EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts);
                        viewProxy.setVisible();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Get the Enzyme action
     *
     * @param findOrShow false for JComboBox action
     * @param kEGGEnzyme the KEGGEnzyme collection
     */
    protected void getKEGGEnzymeLink(boolean findOrShow, Collection<KEGGEnzyme> kEGGEnzyme) {
        ArrayList<List<Object>> data = new ArrayList<>();
        if (findOrShow) {
            data.clear();
            for (KEGGEnzyme dbts : kEGGEnzyme) {
                ArrayList<Object> list = new ArrayList<>();
                list.add(dbts.getEntry());
                list.add(dbts.getComment());
                data.add(list);
            }
            setjTViewColumn(data, 2);
        } else {
            if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                for (KEGGEnzyme dbts : kEGGEnzyme) {
                    if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getEntry())) {
                        EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts);
                        viewProxy.setVisible();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Get the KEGGGene action
     *
     * @param findOrShow false for JComboBox action
     * @param kEGGGene the KEGGGene collection
     */
    protected void getKEGGGeneLink(boolean findOrShow, Collection<KEGGGene> kEGGGene) {
        ArrayList<List<Object>> data = new ArrayList<>();
        if (findOrShow) {
            data.clear();
            for (KEGGGene dbts : kEGGGene) {
                ArrayList<Object> list = new ArrayList<>();
                list.add(dbts.getEntry());
                list.add(dbts.getDefinition());
                data.add(list);
            }
            setjTViewColumn(data, 2);
        } else {
            if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                for (KEGGGene dbts : kEGGGene) {
                    if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getEntry())) {
                        EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts);
                        viewProxy.setVisible();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Get the KEGGReaction action
     *
     * @param findOrShow false for JComboBox action
     * @param kEGGReaction the KEGGReaction collection
     */
    protected void getKEGGReactionLink(boolean findOrShow, Collection<KEGGReaction> kEGGReaction) {
        ArrayList<List<Object>> data = new ArrayList<>();
        if (findOrShow) {
            data.clear();
            for (KEGGReaction dbts : kEGGReaction) {
                ArrayList<Object> list = new ArrayList<>();
                list.add(dbts.getEntry());
                list.add(dbts.getEquation());
                list.add(dbts.getDefinition());
                data.add(list);
            }
            setjTViewColumn(data, 3);
        } else {
            if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                for (KEGGReaction dbts : kEGGReaction) {
                    if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getEntry())) {
                        EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts);
                        viewProxy.setVisible();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Get the KEGGPathway action
     *
     * @param findOrShow false for JComboBox action
     * @param kEGGPathway the KEGGPathway collection
     */
    protected void getKEGGPathwayLink(boolean findOrShow, Collection<KEGGPathway> kEGGPathway) {
        ArrayList<List<Object>> data = new ArrayList<>();
        if (findOrShow) {
            data.clear();
            for (KEGGPathway dbts : kEGGPathway) {
                ArrayList<Object> list = new ArrayList<>();
                list.add(dbts.getEntry());
                list.add(dbts.getTitle());
                data.add(list);
            }
            setjTViewColumn(data, 2);
        } else {
            if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                for (KEGGPathway dbts : kEGGPathway) {
                    if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getEntry())) {
                        EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts);
                        viewProxy.setVisible();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Get the KEGGGlycan action
     *
     * @param findOrShow false for JComboBox action
     * @param kEGGGlycan the KEGGGlycan collection
     */
    protected void getKEGGGlycanLink(boolean findOrShow, Collection<KEGGGlycan> kEGGGlycan) {
        ArrayList<List<Object>> data = new ArrayList<>();
        if (findOrShow) {
            data.clear();
            for (KEGGGlycan dbts : kEGGGlycan) {
                ArrayList<Object> list = new ArrayList<>();
                list.add(dbts.getEntry());
                data.add(list);
            }
            setjTViewColumn(data, 1);
        } else {
            if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                for (KEGGGlycan dbts : kEGGGlycan) {
                    if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getEntry())) {
                        EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts);
                        viewProxy.setVisible();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Get the DrugBank action
     *
     * @param findOrShow false for JComboBox action
     * @param drugBank the DrugBank collection
     */
    protected void getDrugBankLink(boolean findOrShow, Collection<DrugBank> drugBank) {
        ArrayList<List<Object>> data = new ArrayList<>();
        if (findOrShow) {
            data.clear();
            for (DrugBank dbts : drugBank) {
                ArrayList<Object> list = new ArrayList<>();
                list.add(dbts.getId());
                list.add(dbts.getCASNumber());
                list.add(dbts.getName());
                data.add(list);
            }
            setjTViewColumn(data, 3);
        } else {
            if (jTLinks.getSelectedRow() >= 0 && jTLinks.getSelectedRow() < jTLinks.getRowCount()) {
                for (DrugBank dbts : drugBank) {
                    if (jTLinks.getValueAt(jTLinks.getSelectedRow(), 0).equals(dbts.getId())) {
                        EntityParserViewProxy viewProxy = new EntityParserViewProxy(parentComponent, dbts);
                        viewProxy.setVisible();
                        break;
                    }
                }
            }
        }
    }

    private DefaultStyledDocument getStyledDocument(List<String> textList) {
        StyleContext sc = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        final Style blStyle = sc.addStyle("BoldStyle", null);

        StyleConstants.setBold(blStyle, true);
        StyleConstants.setItalic(blStyle, true);
        StyleConstants.setUnderline(blStyle, true);

        try {
            for (String text : textList) {
                int size = doc.getLength();
                doc.insertString(size, text, null);
                if (text.contains(":") && !text.matches("^\\s")) {
                    doc.setCharacterAttributes(size, text.indexOf(":"), blStyle, false);
                }
            }

        } catch (BadLocationException ex) {
            VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
        }
        return doc;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLMainId = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jCLinks = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTLinks =         new javax.swing.JTable() {

            @Override
            public String getToolTipText(java.awt.event.MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                int realColumnIndex = convertColumnIndexToModel(colIndex);

                if (realColumnIndex >= 0) {
                    TableModel model = getModel();

                    Object tipTemp = model.getValueAt(rowIndex, colIndex);
                    if (tipTemp instanceof String) {
                        tip = (String) tipTemp;
                    } else if (tipTemp instanceof Long) {
                        tip = ((Long) tipTemp).toString();
                    }

                } else {
                    tip = super.getToolTipText(e);
                }
                return tip;
            }
        };
        jScrollPane3 = new javax.swing.JScrollPane();
        jTBasicData = new JTextPane(getStyledDocument(getBasicData()));

        jLMainId.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLMainId.setText(EntityParserFieldProxy.getInstance().getId(dataSetObject));

        jCLinks.setModel(new javax.swing.DefaultComboBoxModel(getLinks().toArray()));
        if (jCLinks.getItemCount() > 0) {
            jCLinks.setSelectedIndex(0);
        }
        jCLinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCLinksActionPerformed(evt);
            }
        });

        jTLinks.setModel(new ListTableModel(new String[]{""}, new ArrayList(),
            new Class[]{java.lang.Object.class},
            new boolean[]{false}));
    jTLinks.setFillsViewportHeight(true);
    jTLinks.setGridColor(new java.awt.Color(240, 240, 240));
    jTLinks.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    jTLinkAction(true);
    jTLinks.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jTLinksMouseClicked(evt);
        }
    });
    jScrollPane2.setViewportView(jTLinks);

    jTBasicData.setEditable(false);
    jScrollPane3.setViewportView(jTBasicData);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jSeparator1)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLMainId, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCLinks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(jScrollPane3))
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLMainId, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jCLinks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
            .addContainerGap())
    );
    }// </editor-fold>//GEN-END:initComponents

    private void jTLinksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTLinksMouseClicked
        jTLinkAction(false);
    }//GEN-LAST:event_jTLinksMouseClicked

    private void jCLinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCLinksActionPerformed
        jTLinkAction(true);
    }//GEN-LAST:event_jCLinksActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JComboBox jCLinks;
    private javax.swing.JLabel jLMainId;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextPane jTBasicData;
    protected javax.swing.JTable jTLinks;
    // End of variables declaration//GEN-END:variables
    /**
     * The parent JComponent
     */
    protected JComponent parentComponent;
    /**
     * The dataset entity type
     */
    protected Object dataSetObject;
    /**
     * The GUI component Id
     */
    protected UUID uniqueKey;
}
