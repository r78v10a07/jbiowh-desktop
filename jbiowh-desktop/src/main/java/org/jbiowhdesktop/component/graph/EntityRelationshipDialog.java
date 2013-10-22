package org.jbiowhdesktop.component.graph;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.graph.JGraphSimpleLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhpersistence.utils.jparelationship.JpaEntitiesSelected;
import org.jbiowhpersistence.utils.jparelationship.JpaRelationship;
import org.jgraph.JGraph;
import org.jgraph.graph.CellMapper;
import org.jgraph.graph.CellView;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;

/**
 * This Class show a graph with the JBioWH entities relationship
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2013-03-19 09:38:47 +0100 (Tue, 19 Mar 2013) $
 * $LastChangedRevision: 396 $ 
 * @since Sep 24, 2012
 */
public class EntityRelationshipDialog extends javax.swing.JDialog {

    private Color COLOR_DEFAULT = new Color(74, 220, 72);
    private Color COLOR_SELECTED = new Color(92, 58, 190);
    private JGraph jgraph;
    private JGraphModelAdapter m_jgAdapter;

    /**
     * Creates new form EntityRelationshipDialog
     *
     * @param parent the Frame from which the dialog is displayed
     * @param modal specifies whether dialog blocks user input to other
     * top-level windows when shown. If true, the modality type property is set
     * to DEFAULT_MODALITY_TYPE, otherwise the dialog is modeless.
     */
    public EntityRelationshipDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            m_jgAdapter = new JGraphModelAdapter(JpaRelationship.getInstance().getGraph());
            jgraph = new JGraph(m_jgAdapter);
            jgraph.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    jGraphMouseClicked(e);
                }
            });

            for (Object cell : m_jgAdapter.getRoots()) {
                CellMapper mapper = jgraph.getGraphLayoutCache();
                CellView c = mapper.getMapping(cell, false);
                if (c != null) {
                    Map m = c.getAllAttributes();
                    if (JpaEntitiesSelected.getInstance().getLoadedByDefault().contains(cell.toString())) {                        
                        GraphConstants.setBackground(m, COLOR_SELECTED);
                    } else {
                        GraphConstants.setBackground(m, COLOR_DEFAULT);
                    }
                }

            }
            JGraphSimpleLayout layout = new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE, 10, 8);
            JGraphFacade graphFacade = new JGraphFacade(jgraph);
            graphFacade.setCircleRadiusFactor(0.6);
            layout.run(graphFacade);
            Map nestedMap = graphFacade.createNestedMap(true, true);
            jgraph.getGraphLayoutCache().edit(nestedMap);
            jgraph.setEditable(false);
            jgraph.setDragEnabled(false);
            jgraph.setAutoResizeGraph(true);
            initComponents();
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
            VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
            VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
        }
    }

    private void jGraphMouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1 && !e.isConsumed()) {
            int x = e.getX(), y = e.getY();
            Object cell = jgraph.getFirstCellForLocation(x, y);
            if (cell != null) {
                if (!JpaEntitiesSelected.getInstance().getLoadedByDefault().contains(cell.toString())) {
                    CellMapper mapper = jgraph.getGraphLayoutCache();
                    CellView c = mapper.getMapping(cell, false);
                    Map m = c.getAllAttributes();
                    try {
                        if (GraphConstants.getBackground(m) == COLOR_SELECTED) {
                            GraphConstants.setBackground(m, COLOR_DEFAULT);
                            JpaEntitiesSelected.getInstance().rmEntity(jgraph.getModel().getValue(cell));
                        } else {
                            GraphConstants.setBackground(m, COLOR_SELECTED);
                            JpaEntitiesSelected.getInstance().addEntity(jgraph.getModel().getValue(cell));
                        }
                    } catch (ClassNotFoundException | NoSuchFieldException ex) {
                        VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
                        VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
                        VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
                    }
                    c.changeAttributes(jgraph.getGraphLayoutCache(), m);
                    jgraph.updateUI();
                }
            }
            e.consume();
        }
    }

    public void selectCell(String cellName) {
        for (Object cell : m_jgAdapter.getRoots()) {
            CellView c = jgraph.getGraphLayoutCache().getMapping(cell, false);
            if (c != null) {                
                Map m = c.getAllAttributes();
                if (!JpaEntitiesSelected.getInstance().getLoadedByDefault().contains(cell.toString())
                        && cellName.equals(cell.toString())) {
                    try {
                        GraphConstants.setBackground(m, COLOR_SELECTED);
                        JpaEntitiesSelected.getInstance().addEntity(jgraph.getModel().getValue(cell));
                        c.changeAttributes(jgraph.getGraphLayoutCache(), m);
                    } catch (ClassNotFoundException | NoSuchFieldException ex) {
                        VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
                        VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
                        VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
                    }
                }
            }
        }
    }

    public void unSelectCell(String cellName) {
        for (Object cell : m_jgAdapter.getRoots()) {
            CellView c = jgraph.getGraphLayoutCache().getMapping(cell, false);
            if (c != null) {
                Map m = c.getAllAttributes();
                if (!JpaEntitiesSelected.getInstance().getLoadedByDefault().contains(cell.toString())
                        && cellName.equals(cell.toString())) {
                    try {
                        GraphConstants.setBackground(m, COLOR_DEFAULT);
                        JpaEntitiesSelected.getInstance().rmEntity(jgraph.getModel().getValue(cell));
                        c.changeAttributes(jgraph.getGraphLayoutCache(), m);
                    } catch (ClassNotFoundException | NoSuchFieldException ex) {
                        VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
                        VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
                        VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
                    }
                }
            }
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

        jScrollPane1 = new javax.swing.JScrollPane(jgraph);
        jBDone = new javax.swing.JButton();
        jBSelectAll = new javax.swing.JButton();
        jBUnSelectAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Entities Relationship");

        jScrollPane1.setMaximumSize(new java.awt.Dimension(785, 740));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(785, 740));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(785, 740));

        jBDone.setText("Done");
        jBDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDoneActionPerformed(evt);
            }
        });

        jBSelectAll.setText("Select All");
        jBSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSelectAllActionPerformed(evt);
            }
        });

        jBUnSelectAll.setText("Unselect All");
        jBUnSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUnSelectAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBSelectAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBUnSelectAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBDone)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBDone)
                    .addComponent(jBSelectAll)
                    .addComponent(jBUnSelectAll))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDoneActionPerformed
        dispose();
    }//GEN-LAST:event_jBDoneActionPerformed

    private void jBSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSelectAllActionPerformed
        for (Object cell : m_jgAdapter.getRoots()) {
            CellMapper mapper = jgraph.getGraphLayoutCache();
            CellView c = mapper.getMapping(cell, false);
            if (c != null) {
                Map m = c.getAllAttributes();
                if (!JpaEntitiesSelected.getInstance().getLoadedByDefault().contains(cell.toString())) {
                    try {                                                
                        GraphConstants.setBackground(m, COLOR_SELECTED);
                        JpaEntitiesSelected.getInstance().addEntity(jgraph.getModel().getValue(cell));
                        c.changeAttributes(jgraph.getGraphLayoutCache(), m);
                    } catch (ClassNotFoundException | NoSuchFieldException ex) {
                        VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
                        VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
                        VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
                    }
                }
            }
        }
        jgraph.updateUI();
    }//GEN-LAST:event_jBSelectAllActionPerformed

    private void jBUnSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUnSelectAllActionPerformed
        for (Object cell : m_jgAdapter.getRoots()) {
            CellMapper mapper = jgraph.getGraphLayoutCache();
            CellView c = mapper.getMapping(cell, false);
            if (c != null) {
                Map m = c.getAllAttributes();                
                if (!JpaEntitiesSelected.getInstance().getLoadedByDefault().contains(cell.toString())) {
                    try {
                        GraphConstants.setBackground(m, COLOR_DEFAULT);
                        JpaEntitiesSelected.getInstance().rmEntity(jgraph.getModel().getValue(cell));
                        c.changeAttributes(jgraph.getGraphLayoutCache(), m);
                    } catch (ClassNotFoundException | NoSuchFieldException ex) {
                        VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
                        VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
                        VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
                    }
                }
            }
        }
        jgraph.updateUI();
    }//GEN-LAST:event_jBUnSelectAllActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBDone;
    private javax.swing.JButton jBSelectAll;
    private javax.swing.JButton jBUnSelectAll;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
