package org.jbiowhdesktop.component.panel.sql;

import java.awt.Event;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyledDocument;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhdbms.dbms.JBioWHDBMS;
import org.jbiowhdesktop.actions.tabbedpanel.TabbedPanelCloseActionListener;
import org.jbiowhdesktop.component.panel.sql.syntax.SQLDocumentFilter;
import org.jbiowhdesktop.component.panel.tabbedpanel.ClosePanel;
import org.jbiowhdesktop.component.popup.MenuScroller;
import org.jbiowhdesktop.component.popup.PopupSQLEditor;
import org.jbiowhdesktop.component.popup.PopupSQLEditorTables;
import org.jbiowhpersistence.datasets.dataset.DataSetsTables;

/**
 * This JPanel is
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-08-31 15:46:36 +0200
 * (Fri, 31 Aug 2012) $ $LastChangedRevision: 396 $
 *
 * @since May 21, 2012
 */
public class SQLEditorPanel extends javax.swing.JPanel {

    /**
     * Creates new form SQLEditorPanel
     *
     * @param parentComponent the Frame from which the dialog is displayed
     */
    public SQLEditorPanel(JComponent parentComponent, JBioWHDBMS wHDBMSFactory) {
        this.parentComponent = parentComponent;
        this.wHDBMSFactory = wHDBMSFactory;
        uniqueKey = UUID.randomUUID();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        initComponents();
    }

    /**
     * Return the unique Id for this object
     *
     * @return the unique Id for this object
     */
    public UUID getUniqueKey() {
        return uniqueKey;
    }

    /**
     * Set the SQLEditorPanel visible
     */
    public void setVisible() {
        if (parentComponent instanceof JTabbedPane) {
            ((JTabbedPane) parentComponent).addTab("SQLEditor", this);
            ((JTabbedPane) parentComponent).setTabComponentAt(((JTabbedPane) parentComponent).getTabCount() - 1,
                    new ClosePanel((JTabbedPane) parentComponent,
                            "SQLEditor", uniqueKey, new TabbedPanelCloseActionListener(parentComponent)));
            ((JTabbedPane) parentComponent).setSelectedIndex(((JTabbedPane) parentComponent).getTabCount() - 1);
        }
    }

    /**
     * Open the file f on the SQL Editor
     *
     * @param f the file to be opened
     * @throws IOException
     */
    public void openSQLFile(File f) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            openSQLFile(reader);
        }
    }

    /**
     * Open the BufferedReader reader on the SQL Editor
     *
     * @param reader the file to be opened
     * @throws IOException
     */
    public void openSQLFile(BufferedReader reader) throws IOException {
        String line;
        StringBuilder buffer = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            doc.insertString(0, buffer.toString(), null);
        } catch (BadLocationException ex) {
            VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
            VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
            VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
        }
    }

    /**
     * Get the current SQL line
     *
     * @return a SQL sentence
     */
    public String getCurrentSQLLine() {
        if (jSQLEditor.getText().isEmpty()) {
            return jSQLEditor.getText();
        }

        int carretPositionToFront = jSQLEditor.getCaretPosition();
        int carretPositionToRear = jSQLEditor.getCaretPosition();

        if (carretPositionToFront == jSQLEditor.getText().length()) {
            carretPositionToFront--;
        }
        if (carretPositionToRear == jSQLEditor.getText().length()) {
            carretPositionToRear--;
        }

        if (jSQLEditor.getText().charAt(carretPositionToFront) == ';') {
            carretPositionToFront--;
        }

        while (carretPositionToFront > 0) {
            if (jSQLEditor.getText().charAt(carretPositionToFront) != ';') {
                carretPositionToFront--;
            } else {
                if (carretPositionToFront != 0) {
                    carretPositionToFront++;
                }
                break;
            }
        }

        while (carretPositionToRear < jSQLEditor.getText().length()) {
            if (jSQLEditor.getText().charAt(carretPositionToRear) != ';') {
                carretPositionToRear++;
            } else {
                carretPositionToRear++;
                break;
            }
        }
        return jSQLEditor.getText().substring(carretPositionToFront, carretPositionToRear).replaceAll("[;\n]", " ").trim();
    }

    /**
     * Get the SQL sentences under selection
     *
     * @return a list of SQL sentences
     */
    public List<String> getSQLSelection() {
        List<String> sentences = new ArrayList<>();
        if (jSQLEditor.getSelectedText() != null) {
            StringBuilder text = new StringBuilder();
            for (String sentence : jSQLEditor.getSelectedText().split("\n")) {
                if (!sentence.startsWith("--")) {
                    text.append(sentence).append("\n");
                }
            }
            for (String sentence : text.toString().split(";")) {
                sentences.add(sentence.replace("\n", " ").trim());
            }
        }
        return sentences;
    }

    /**
     * Get all SQL sentences
     *
     * @return a list of SQL sentences
     */
    public List<String> getAllSQL() {
        List<String> sentences = new ArrayList<>();
        if (!jSQLEditor.getText().isEmpty()) {
            StringBuilder text = new StringBuilder();
            for (String sentence : jSQLEditor.getText().split("\n")) {
                if (!sentence.startsWith("--")) {
                    text.append(sentence).append("\n");
                }
            }
            for (String sentence : text.toString().split(";")) {
                String sql = sentence.replace("\n", " ").trim();
                if (!sql.isEmpty()) {
                    sentences.add(sql);
                }
            }
        }
        return sentences;
    }

    /**
     * Get the Redo Action
     *
     * @return a RedoAction object
     */
    public RedoAction getRedoAction() {
        return redoAction;
    }

    /**
     * Get the Undo Action
     *
     * @return a UndoAction action
     */
    public UndoAction getUndoAction() {
        return undoAction;
    }

    /**
     * Get the SQL editor pane
     *
     * @return a JTextPane object
     */
    public JTextPane getjSQLEditor() {
        return jSQLEditor;
    }

    /**
     * The Document listener for the Undo and Redo process
     */
    protected class MyUndoableEditListener
            implements UndoableEditListener {

        @Override
        public void undoableEditHappened(UndoableEditEvent e) {
            undo.addEdit(e.getEdit());
            undoAction.updateUndoState();
            redoAction.updateRedoState();
        }
    }

    /**
     * The UndoAction Class definition
     */
    public class UndoAction extends AbstractAction {

        /**
         * Create the Undo Action
         */
        public UndoAction() {
            super("Undo");
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                VerbLogger.getInstance().log(UndoAction.class, "Unable to undo: " + ex);
            }
            updateUndoState();
            redoAction.updateRedoState();
        }

        /**
         * Get the Undo state
         */
        protected void updateUndoState() {
            if (undo.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, undo.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
            }
        }
    }

    /**
     * The RedoAction Class definition
     */
    public class RedoAction extends AbstractAction {

        /**
         * Create the Redo Action
         */
        public RedoAction() {
            super("Redo");
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undo.redo();
            } catch (CannotRedoException ex) {
                VerbLogger.getInstance().log(RedoAction.class, "Unable to redo: " + ex);
            }
            updateRedoState();
            undoAction.updateUndoState();
        }

        /**
         * Get the Redo state
         */
        protected void updateRedoState() {
            if (undo.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, undo.getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }
    }

    /**
     * Insert the string on the caret position
     *
     * @param toInsert the string to be inserted
     */
    public void insertOnCaretPosition(String toInsert) {
        try {
            jSQLEditor.getDocument().insertString(jSQLEditor.getCaretPosition(), toInsert, null);
        } catch (BadLocationException ex) {
            VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
            VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
            VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
        }
    }

    /**
     * Insert into the Document the auto completed word selected in the popup
     *
     * @param toInsert the word to be inserted
     */
    public void insertAutoComplete(String toInsert) {
        try {
            if (jSQLEditor.getText(jSQLEditor.getCaretPosition() - 1, 1).equals(" ")) {
                jSQLEditor.getDocument().insertString(jSQLEditor.getCaretPosition(), toInsert, null);
            } else {
                int position = jSQLEditor.getCaretPosition() - 1;
                while (!jSQLEditor.getText(position, 1).equals(" ")) {
                    position--;
                }
                position++;
                jSQLEditor.getDocument().remove(position, jSQLEditor.getCaretPosition() - position);
                jSQLEditor.getDocument().insertString(position, toInsert, null);
            }
        } catch (BadLocationException ex) {
            VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
            VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
            VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
        }
    }

    private void showPopup(java.awt.event.MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            (new PopupSQLEditor(parentComponent)).show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }

    private Set<String> getAutoCompleteSet() {
        Set<String> completSet = new TreeSet<>();
        try {
            if (jSQLEditor.getText(jSQLEditor.getCaretPosition() - 1, 1).equals(" ")) {
                completSet.addAll(DataSetsTables.getInstance().getTables());
            } else {
                int position = jSQLEditor.getCaretPosition() - 1;
                while (!jSQLEditor.getText(position, 1).equals(" ")) {
                    position--;
                }
                position++;
                String toSearch = jSQLEditor.getText(position, jSQLEditor.getCaretPosition() - position);
                for (String d : DataSetsTables.getInstance().getTables()) {
                    if (d.toUpperCase().startsWith(toSearch.toUpperCase())) {
                        completSet.add(d);
                    }
                }

            }
        } catch (BadLocationException ex) {
            VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
            VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
            VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
        }
        return completSet;
    }

    public AbstractDocument getDoc() {
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jSQLEditor = new javax.swing.JTextPane();
        StyledDocument styledDoc = jSQLEditor.getStyledDocument();
        if (styledDoc instanceof AbstractDocument) {
            doc = (AbstractDocument) styledDoc;
            doc.setDocumentFilter(new SQLDocumentFilter(wHDBMSFactory));
            doc.addUndoableEditListener(new MyUndoableEditListener());
        }

        InputMap inputMap = jSQLEditor.getInputMap();

        //Ctrl-b to go backward one character
        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.backwardAction);

        //Ctrl-f to go forward one character
        key = KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.forwardAction);

        //Ctrl-p to go up one line
        key = KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.upAction);

        //Ctrl-n to go down one line
        key = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.downAction);

        jSQLEditor.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jSQLEditor.setPreferredSize(new java.awt.Dimension(836, 600));
        jSQLEditor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jSQLEditorMouseReleased(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jSQLEditorMousePressed(evt);
            }
        });
        jSQLEditor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSQLEditorKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jSQLEditor);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jSQLEditorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSQLEditorMousePressed
        showPopup(evt);
    }//GEN-LAST:event_jSQLEditorMousePressed

    private void jSQLEditorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSQLEditorMouseReleased
        showPopup(evt);
    }//GEN-LAST:event_jSQLEditorMouseReleased

    private void jSQLEditorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSQLEditorKeyPressed
        if (evt.getKeyChar() == ' '
                && evt.getModifiers() == InputEvent.CTRL_MASK) {
            try {
                Set<String> items = getAutoCompleteSet();

                if (!items.isEmpty()) {
                    Rectangle coord = jSQLEditor.modelToView(jSQLEditor.getCaretPosition());
                    PopupSQLEditorTables compl = new PopupSQLEditorTables(this, items);
                    MenuScroller.setScrollerFor(compl, 20);
                    compl.show(this, coord.x, coord.y);
                }
            } catch (BadLocationException ex) {
                VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
                VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
                VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
            }
        }
    }//GEN-LAST:event_jSQLEditorKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane jSQLEditor;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
    private AbstractDocument doc;
    private JComponent parentComponent;
    private UndoAction undoAction;
    private RedoAction redoAction;
    private UndoManager undo = new UndoManager();
    private JBioWHDBMS wHDBMSFactory;
    private UUID uniqueKey;
}
