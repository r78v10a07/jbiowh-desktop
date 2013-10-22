package org.jbiowhdesktop.save;

import java.awt.Component;
import java.io.*;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jbiowhdesktop.component.file.FileChooser;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhdesktop.component.panel.list.AbstractListView;
import org.jbiowhpersistence.utils.entitymanager.EntityParserFieldProxy;

/**
 * This Class is save a result to a file
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2013-03-19 09:38:47 +0100 (Tue, 19 Mar 2013) $
 * $LastChangedRevision: 396 $
 * @since Jul 4, 2012
 */
public class SaveToFile {

    private Object objectToSave;
    private Component parent;

    /**
     * Creates a SaveToFile object to save the objects inside the component
     *
     * @param objectToSave the component which include the objects to be saved
     */
    public SaveToFile(Component objectToSave) {
        this.objectToSave = objectToSave;
        this.parent = objectToSave;
    }
    
    /**
     * Creates a SaveToFile object to save the objects inside the component
     *
     * @param parent
     * @param objectToSave the component which include the objects to be saved
     */
    public SaveToFile(Component parent, Object objectToSave) {
        this.parent = parent;
        this.objectToSave = objectToSave;
    }

    public void save() {
        if (objectToSave instanceof AbstractListView) {
            printToFile(((AbstractListView) objectToSave).getSelectedElements());
        } else if (objectToSave instanceof AbstractDataSetView) {
            printToFile(((AbstractDataSetView) objectToSave).getDataSetObject());
        } else {
           printToFile(objectToSave); 
        }
    }

    private void printToFile(Object data) {
        try {
            List<FileNameExtensionFilter> extensions = EntityParserFieldProxy.getInstance().getFileExtensions(data);
            FileChooser fileChooser = new FileChooser(new JFrame(), true, extensions);
            fileChooser.getjFileChooser1().setAcceptAllFileFilterUsed(false);
            fileChooser.getjFileChooser1().setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
            fileChooser.setLocationRelativeTo(parent);
            fileChooser.setVisible(true);
            if (!fileChooser.isCancelled()) {
                File fileName = fileChooser.getjFileChooser1().getSelectedFile();
                FileFilter selectedExt = fileChooser.getjFileChooser1().getFileFilter();
                if (!selectedExt.accept(fileName)) {
                    for (FileNameExtensionFilter ext : extensions) {
                        if (ext.equals(selectedExt)) {
                            fileName = new File(fileName.getAbsolutePath() + "." + ext.getExtensions()[0]);
                        }
                    }
                }
                try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
                    List print = EntityParserFieldProxy.getInstance().getListToPrint(data, fileName.getName());
                    if (!print.isEmpty()) {
                        if (print.get(0) instanceof String[]) {
                            for (String[] p : (List<String[]>) print) {
                                for (int i = 0; i < p.length; i++) {
                                    if (i < p.length - 1) {
                                        writer.print(p[i] + "\t");
                                    } else {
                                        writer.print(p[i] + "\n");
                                    }
                                }
                            }
                        } else if (print.get(0) instanceof String) {
                            for (String p : (List<String>) print) {
                                writer.print(p);
                            }
                        }
                    }

                }
            }
        } catch (IOException ex) {
            int type = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", type);
        }
    }
}
