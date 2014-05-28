package org.jbiowhdesktop.component.panel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhcore.utility.constrains.JPLConstrains;
import org.jbiowhdesktop.actions.tabbedpanel.TabbedPanelCloseActionListener;
import org.jbiowhdesktop.component.dialog.progress.ProgressDialog;
import org.jbiowhdesktop.component.file.FileChooser;
import org.jbiowhdesktop.component.panel.tabbedpanel.ClosePanel;
import org.jbiowhdesktop.component.popup.PopupSearchConstrain;
import org.jbiowhdesktop.component.table.model.ConstrainsTableModel;
import org.jbiowhdesktop.component.table.model.ListTableModel;
import org.jbiowhdesktop.datasets.ListViewProxy;
import org.jbiowhpersistence.datasets.DataSetPersistence;
import org.jbiowhpersistence.datasets.disease.omim.search.SearchOMIM;
import org.jbiowhpersistence.datasets.domain.pfam.search.SearchPFam;
import org.jbiowhpersistence.datasets.drug.drugbank.search.SearchDrugBank;
import org.jbiowhpersistence.datasets.gene.gene.search.SearchGeneInfo;
import org.jbiowhpersistence.datasets.gene.genebank.search.SearchGeneBank;
import org.jbiowhpersistence.datasets.gene.genome.search.SearchGenePTT;
import org.jbiowhpersistence.datasets.ontology.search.SearchOntology;
import org.jbiowhpersistence.datasets.pathway.kegg.search.SearchKEGGCompound;
import org.jbiowhpersistence.datasets.pathway.kegg.search.SearchKEGGEnzyme;
import org.jbiowhpersistence.datasets.pathway.kegg.search.SearchKEGGGene;
import org.jbiowhpersistence.datasets.pathway.kegg.search.SearchKEGGGlycan;
import org.jbiowhpersistence.datasets.pathway.kegg.search.SearchKEGGPathway;
import org.jbiowhpersistence.datasets.pathway.kegg.search.SearchKEGGReaction;
import org.jbiowhpersistence.datasets.protclust.search.SearchUniRef;
import org.jbiowhpersistence.datasets.protein.search.SearchProtein;
import org.jbiowhpersistence.datasets.protgroup.cog.search.SearchCOG;
import org.jbiowhpersistence.datasets.protgroup.orthoxml.search.SearchOrthoXML;
import org.jbiowhpersistence.datasets.protgroup.pirsf.search.SearchPirsf;
import org.jbiowhpersistence.datasets.taxonomy.search.SearchTaxonomy;
import org.jbiowhpersistence.utils.entitymanager.EntityParserFieldProxy;
import org.jbiowhpersistence.utils.search.SearchFactory;

/**
 * This JPanel is the Search interface
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-12-04 11:38:29 +0100
 * (Tue, 04 Dec 2012) $ $LastChangedRevision: 591 $
 *
 * @since Feb 10, 2012
 */
public class SearchPanel extends BasicConstrain {

    /**
     * Creates new form SearchPanel
     *
     * @param parentComponent
     */
    public SearchPanel(JComponent parentComponent) {
        this.parentComponent = parentComponent;
        uniqueKey = UUID.randomUUID();
        initComponents();
    }

    public UUID getUniqueKey() {
        return uniqueKey;
    }

    public JComboBox getjCDataSet() {
        return jCDataSet;
    }

    public void setVisible() {
        if (parentComponent instanceof JTabbedPane) {
            ((JTabbedPane) parentComponent).addTab("Search", this);
            ((JTabbedPane) parentComponent).setTabComponentAt(((JTabbedPane) parentComponent).getTabCount() - 1,
                    new ClosePanel((JTabbedPane) parentComponent,
                            "Search", uniqueKey, new TabbedPanelCloseActionListener(parentComponent)));
            ((JTabbedPane) parentComponent).setSelectedIndex(((JTabbedPane) parentComponent).getTabCount() - 1);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCDataSet = new javax.swing.JComboBox();
        jCField = new javax.swing.JComboBox();
        jCExternalOperation = new javax.swing.JComboBox();
        jCConstOperation = new javax.swing.JComboBox();
        jTSearch = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLConstrains = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTConstrain =         new javax.swing.JTable() {

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
                    if (tipTemp != null) {
                        if (tipTemp instanceof String) {
                            tip = (String) tipTemp;
                        } else {
                            tip = tipTemp.toString();
                        }
                    }

                } else {
                    tip = super.getToolTipText(e);
                }
                return tip;
            }
        };
        jBSearch2 = new javax.swing.JButton();
        jLParm = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTParameters = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jBAddField = new javax.swing.JButton();
        jBRemField = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jBLoadFile = new javax.swing.JButton();

        setFocusable(false);

        jCDataSet.setModel(new javax.swing.DefaultComboBoxModel(getDataSets()));
        jCField.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCFieldActionPerformed(evt);
            }
        });

        jCConstOperation.addItem("IN");
        jCConstOperation.addItem("NOT IN");

        jCExternalOperation.addItem("AND");
        jCExternalOperation.addItem("OR");
        jCDataSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCDataSetActionPerformed(evt);
            }
        });

        jTSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTSearchActionPerformed(evt);
            }
        });

        jLConstrains.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLConstrains.setText("Constrains");

        jTConstrain.setModel(new org.jbiowhdesktop.component.table.model.ConstrainsTableModel(
            new JPLConstrains(),
            new String[] {"ConstrainsType", "Operation", "External Operation"},
            new Class[]{java.lang.Object.class, java.lang.Object.class, java.lang.Object.class},
            new boolean[]{false, true, true}));
    jTConstrain.setFillsViewportHeight(true);
    jTConstrain.setGridColor(new java.awt.Color(240, 240, 240));
    TableColumn operationColumn = jTConstrain.getColumnModel().getColumn(1);
    operationColumn.setCellEditor(new DefaultCellEditor(jCConstOperation));
    DefaultTableCellRenderer rendererConstrain = new DefaultTableCellRenderer();
    rendererConstrain.setToolTipText("Click for combo box");
    operationColumn.setCellRenderer(rendererConstrain);

    TableColumn logIntOperatorColumn = jTConstrain.getColumnModel().getColumn(2);
    logIntOperatorColumn.setCellEditor(new DefaultCellEditor(jCExternalOperation));
    logIntOperatorColumn.setCellRenderer(rendererConstrain);
    jTConstrains = jTConstrain;
    jTConstrain.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            jTConstrainMouseReleased(evt);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            jTConstrainMousePressed(evt);
        }
    });
    jScrollPane1.setViewportView(jTConstrain);

    jBSearch2.setText("Search");
    jBSearch2.setToolTipText("Perform the search");
    jBSearch2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jBSearch2ActionPerformed(evt);
        }
    });

    jLParm.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLParm.setText("Other parmeters");

    jTParameters.setModel(new org.jbiowhdesktop.component.table.model.ListTableModel( new String[] {"External Operation","Field","Operation", "User Search Parameter"}, new ArrayList<List<Object>>(),
        new Class[]{java.lang.Object.class, java.lang.Object.class,java.lang.Object.class, java.lang.Object.class},
        new boolean[]{true, true,true, true}));
jTParameters.setFillsViewportHeight(true);
jTParameters.setGridColor(new java.awt.Color(240, 240, 240));
TableColumn fieldColumn = jTParameters.getColumnModel().getColumn(0);
fieldColumn.setCellEditor(new DefaultCellEditor(jCExternalOperation));
DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
renderer.setToolTipText("Click for combo box");
fieldColumn.setCellRenderer(renderer);

fieldColumn = jTParameters.getColumnModel().getColumn(1);
fieldColumn.setCellEditor(new DefaultCellEditor(jCField));
renderer = new DefaultTableCellRenderer();
renderer.setToolTipText("Click for combo box");
fieldColumn.setCellRenderer(renderer);

jTParameters.getColumnModel().getColumn(0).setMinWidth(60);
jTParameters.getColumnModel().getColumn(0).setPreferredWidth(60);
jTParameters.getColumnModel().getColumn(0).setMaxWidth(60);
jTParameters.getColumnModel().getColumn(1).setMinWidth(100);
jTParameters.getColumnModel().getColumn(1).setPreferredWidth(100);
jTParameters.getColumnModel().getColumn(1).setMaxWidth(100);
jTParameters.getColumnModel().getColumn(2).setMinWidth(60);
jTParameters.getColumnModel().getColumn(2).setPreferredWidth(60);
jTParameters.getColumnModel().getColumn(2).setMaxWidth(60);
jScrollPane2.setViewportView(jTParameters);

jBAddField.setText("Add");
jBAddField.setToolTipText("Add other parameters to the search");
jBAddField.setMaximumSize(new java.awt.Dimension(71, 23));
jBAddField.setMinimumSize(new java.awt.Dimension(71, 23));
jBAddField.setPreferredSize(new java.awt.Dimension(71, 23));
jBAddField.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        jBAddFieldActionPerformed(evt);
    }
    });

    jBRemField.setText("Remove");
    jBRemField.setToolTipText("Remove selected parameter");
    jBRemField.setEnabled(false);
    jBRemField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jBRemFieldActionPerformed(evt);
        }
    });

    jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
    jSeparator3.setAlignmentX(0.0F);

    jBLoadFile.setText("Load a File");
    jBLoadFile.setToolTipText("Load a file to perform the search");
    jBLoadFile.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jBLoadFileActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jSeparator1)
        .addComponent(jSeparator2)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jBSearch2))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jCDataSet, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTSearch))
                .addComponent(jScrollPane2)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLConstrains)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLParm)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jBAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jBRemField)
                    .addGap(139, 139, 139)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBLoadFile)))
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jCDataSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLParm)
                        .addComponent(jBAddField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBRemField))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jBLoadFile))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLConstrains)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jBSearch2)
            .addContainerGap())
    );
    }// </editor-fold>//GEN-END:initComponents

    private class Task extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            ProgressDialog dialogView = new ProgressDialog(null, false);
            dialogView.setLocationRelativeTo(parentComponent);
            dialogView.getjProgressBar().setIndeterminate(true);
            dialogView.getjProgressBar().repaint();
            dialogView.getjLProgress().setText("Searching ...");
            dialogView.getjLProgress().repaint();
            dialogView.setVisible(true);

            List result;
            try {
                if (jTSearch.isVisible()) {
                    System.out.println("task");
                    result = searchFactory.search(jTSearch.getText(), createConstrains());
                } else {
                    ListTableModel model = (ListTableModel) jTParameters.getModel();
                    result = searchFactory.search(model.getContents(), createConstrains());
                }
                ListViewProxy.getInstance().setVisible(result, parentComponent);
            } catch (SQLException ex) {
                int type = JOptionPane.ERROR_MESSAGE;
                JOptionPane.showMessageDialog(parentComponent, ex.getMessage(), "Constrain error", type);
            }

            dialogView.dispose();
            return null;
        }
    }

    private void jBSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSearch2ActionPerformed
        if (searchFactory != null) {
            Task task = new Task();
            task.execute();
        } else {
            int type = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog(parentComponent, "The selected DataSet is not ready yet", "DataSet error", type);
        }
    }//GEN-LAST:event_jBSearch2ActionPerformed

    private void jCDataSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCDataSetActionPerformed
        searchFactory = createSearchFactory(jCDataSet.getSelectedItem().toString());
    }//GEN-LAST:event_jCDataSetActionPerformed

    private void jBAddFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddFieldActionPerformed
        jTSearch.setVisible(false);
        jTSearch.setText(null);
        jBRemField.setEnabled(true);
        ListTableModel model = (ListTableModel) jTParameters.getModel();
        List row = new ArrayList();

        if (model.getRowCount() == 0) {
            row.add("");
        } else {
            row.add("AND");
        }

        String key = (String) searchFactory.getFieldsSet().toArray()[0];
        JComboBox jCOperation = getOperation(key);
        row.add(key);
        row.add("=");
        row.add("");

        TableColumn fieldColumn = jTParameters.getColumnModel().getColumn(2);
        fieldColumn.setCellEditor(new DefaultCellEditor(jCOperation));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        fieldColumn.setCellRenderer(renderer);

        model.addRow(row);
    }//GEN-LAST:event_jBAddFieldActionPerformed

    private void jBRemFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemFieldActionPerformed
        ListTableModel model = (ListTableModel) jTParameters.getModel();
        if (jTParameters.getSelectedRowCount() != 0) {
            for (int i = jTParameters.getSelectedRowCount() - 1; i >= 0; i--) {
                model.getContents().remove(jTParameters.getSelectedRows()[i]);
            }
            model.fireTableDataChanged();
            if (model.getRowCount() == 0) {
                jBRemField.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jBRemFieldActionPerformed

    private void showPopop(java.awt.event.MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            popupConstain = new PopupSearchConstrain(parentComponent);
            popupConstain.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }

    private void jTConstrainMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTConstrainMousePressed
        showPopop(evt);
    }//GEN-LAST:event_jTConstrainMousePressed

    private void jTConstrainMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTConstrainMouseReleased
        showPopop(evt);
    }//GEN-LAST:event_jTConstrainMouseReleased

    private void jTSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTSearchActionPerformed
        jBSearch2ActionPerformed(evt);
    }//GEN-LAST:event_jTSearchActionPerformed

    private void jBLoadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLoadFileActionPerformed
        List<FileNameExtensionFilter> extensions = EntityParserFieldProxy.getInstance().getFileExtensions(searchFactory);
        FileChooser fileChooser = new FileChooser(new JFrame(), true, extensions);
        fileChooser.getjFileChooser1().setAcceptAllFileFilterUsed(false);
        fileChooser.getjFileChooser1().setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
        fileChooser.setLocationRelativeTo(parentComponent);
        fileChooser.setVisible(true);
        if (!fileChooser.isCancelled()) {
            try {
                jTSearch.setVisible(false);
                jTSearch.setText(null);
                jBRemField.setEnabled(true);
                String line;
                ListTableModel model = (ListTableModel) jTParameters.getModel();
                try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getjFileChooser1().getSelectedFile()))) {
                    while ((line = reader.readLine()) != null) {
                        List row = new ArrayList();

                        if (!fileChooser.getjFileChooser1().getSelectedFile().getName().endsWith("fasta")) {
                            if (model.getRowCount() == 0) {
                                row.add("");
                            } else {
                                row.add("OR");
                            }

                            JComboBox jCOperation = getOperation(searchFactory.getMainField());
                            row.add(searchFactory.getMainField());
                            row.add("=");
                            row.add(line.split("\t| ")[0]);

                            TableColumn fieldColumn = jTParameters.getColumnModel().getColumn(2);
                            fieldColumn.setCellEditor(new DefaultCellEditor(jCOperation));
                            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                            renderer.setToolTipText("Click for combo box");
                            fieldColumn.setCellRenderer(renderer);

                            model.addRow(row);
                        } else {
                            if (line.startsWith(">")) {
                                if (model.getRowCount() == 0) {
                                    row.add("");
                                } else {
                                    row.add("OR");
                                }

                                JComboBox jCOperation = getOperation(searchFactory.getMainField());
                                row.add(searchFactory.getMainField());
                                row.add("=");
                                row.add(line.split("\t| ")[0].substring(1));

                                TableColumn fieldColumn = jTParameters.getColumnModel().getColumn(2);
                                fieldColumn.setCellEditor(new DefaultCellEditor(jCOperation));
                                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                                renderer.setToolTipText("Click for combo box");
                                fieldColumn.setCellRenderer(renderer);

                                model.addRow(row);
                            }
                        }

                    }
                }
            } catch (IOException ex) {
                VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
                VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
                VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
                int type = JOptionPane.ERROR_MESSAGE;
                JOptionPane.showMessageDialog(parentComponent, ex.getMessage(), "File Error", type);
            }
        }
    }//GEN-LAST:event_jBLoadFileActionPerformed

    private SearchFactory createSearchFactory(String dataset) {
        switch (dataset) {
            case "Taxonomy":
                searchFactory = new SearchTaxonomy();
                setConstrainsVisible(false);
                break;
            case "Ontology":
                searchFactory = new SearchOntology();
                setConstrainsVisible(false);
                break;
            case "Gene":
                searchFactory = new SearchGeneInfo();
                setConstrainsVisible(true);
                break;
            case "GenePTT":
                searchFactory = new SearchGenePTT();
                setConstrainsVisible(true);
                break;
            case "Protein":
                searchFactory = new SearchProtein();
                setConstrainsVisible(true);
                break;
            case "ProtFam":
                searchFactory = new SearchUniRef();
                setConstrainsVisible(true);
                break;
            case "Drug":
                searchFactory = new SearchDrugBank();
                setConstrainsVisible(true);
                break;
            case "Pathway":
                searchFactory = new SearchKEGGPathway();
                setConstrainsVisible(true);
                break;
            case " -- KEGG Compound":
                searchFactory = new SearchKEGGCompound();
                setConstrainsVisible(true);
                break;
            case " -- KEGG Enzyme":
                searchFactory = new SearchKEGGEnzyme();
                setConstrainsVisible(true);
                break;
            case " -- KEGG Glycan":
                searchFactory = new SearchKEGGGlycan();
                setConstrainsVisible(true);
                break;
            case " -- KEGG Gene":
                searchFactory = new SearchKEGGGene();
                setConstrainsVisible(true);
                break;
            case " -- KEGG Reaction":
                searchFactory = new SearchKEGGReaction();
                setConstrainsVisible(true);
                break;
            case "OMIM":
                searchFactory = new SearchOMIM();
                setConstrainsVisible(true);
                break;
            case "PFAM":
                searchFactory = new SearchPFam();
                setConstrainsVisible(true);
                break;
            case "GeneBank":
                searchFactory = new SearchGeneBank();
                setConstrainsVisible(true);
                break;
            case "PIRSF":
                searchFactory = new SearchPirsf();
                setConstrainsVisible(true);
                break;
            case "COG":
                searchFactory = new SearchCOG();
                setConstrainsVisible(true);
                break;
            case "EggNOG":
                searchFactory = new SearchCOG();
                setConstrainsVisible(true);
                break;
            case "OrthoXML":
                searchFactory = new SearchOrthoXML();
                setConstrainsVisible(true);
                break;
            default:
                searchFactory = null;
                break;

        }
        if (searchFactory != null) {
            fillChoice(searchFactory.getFields());
        }

        return searchFactory;
    }

    private void setConstrainsVisible(boolean flags) {
        jTSearch.setVisible(true);
        jTSearch.setText(null);
        jScrollPane1.setVisible(flags);
        jLConstrains.setVisible(flags);
    }

    private void jCFieldActionPerformed(java.awt.event.ActionEvent evt) {
        if (jCField.getSelectedItem() != null) {
            ListTableModel model = (ListTableModel) jTParameters.getModel();
            if (jTParameters.getSelectedRow() >= 0 && jTParameters.getSelectedRow() < jTParameters.getRowCount()) {
                model.setValueAt("=", jTParameters.getSelectedRow(), 2);
            }

            String key = (String) jCField.getSelectedItem();
            JComboBox jCOperation = getOperation(key);

            TableColumn fieldColumn = jTParameters.getColumnModel().getColumn(2);
            fieldColumn.setCellEditor(new DefaultCellEditor(jCOperation));
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setToolTipText("Click for combo box");
            fieldColumn.setCellRenderer(renderer);
        }
    }

    private JComboBox getOperation(String key) {
        JComboBox jCOperation = new javax.swing.JComboBox();
        if (searchFactory.getFields().get(key).equals(Long.class)) {
            jCOperation.addItem("=");
            jCOperation.addItem("!=");
            jCOperation.addItem(">");
            jCOperation.addItem("<");
            jCOperation.addItem(">=");
            jCOperation.addItem("<=");
        } else {
            jCOperation.addItem("=");
            jCOperation.addItem("!=");
            jCOperation.addItem("like");
            jCOperation.addItem("not like");
        }
        return jCOperation;
    }

    private Object[] getDataSets() {
        List<String> result = new ArrayList();
        List<List<Object>> dataset = DataSetPersistence.getInstance().loadDataSet();

        if (!dataset.isEmpty()) {
            for (List<Object> list : dataset) {
                String data = ((String) list.get(5)).replace("Loader", "");
                if (!result.contains(data)) {
                    result.add(data);
                    if (data.equals("Pathway")) {
                        result.add(" -- KEGG Enzyme");
                        result.add(" -- KEGG Compound");
                        result.add(" -- KEGG Glycan");
                        result.add(" -- KEGG Gene");
                        result.add(" -- KEGG Reaction");
                    }
                }
            }
            searchFactory = createSearchFactory(result.get(0));
        }

        return result.toArray();
    }

    public void fillChoice(HashMap<String, Class> items) {
        if (jTParameters.getModel() instanceof ListTableModel) {
            ListTableModel model = ((ListTableModel) jTParameters.getModel());
            model.setContents(new ArrayList<List<Object>>());
        }
        jCField.removeAllItems();
        for (String item : items.keySet()) {
            jCField.addItem(item);
        }
    }

    private JPLConstrains createConstrains() {
        ConstrainsTableModel model = (ConstrainsTableModel) jTConstrain.getModel();
        if (model.getRowCount() != 0) {
            return model.getConstrains();
        }

        return null;
    }

    public JTextField getjTSearch() {
        return jTSearch;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public JLabel getjLConstrains() {
        return jLConstrains;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAddField;
    private javax.swing.JButton jBLoadFile;
    private javax.swing.JButton jBRemField;
    private javax.swing.JButton jBSearch2;
    private javax.swing.JComboBox jCDataSet;
    private javax.swing.JLabel jLConstrains;
    private javax.swing.JLabel jLParm;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTConstrain;
    private javax.swing.JTable jTParameters;
    private javax.swing.JTextField jTSearch;
    // End of variables declaration//GEN-END:variables
    private JComponent parentComponent;
    private SearchFactory searchFactory;
    private javax.swing.JComboBox jCField;
    private javax.swing.JComboBox jCExternalOperation;
    private javax.swing.JComboBox jCConstOperation;
    private PopupSearchConstrain popupConstain;
    private UUID uniqueKey;
}
