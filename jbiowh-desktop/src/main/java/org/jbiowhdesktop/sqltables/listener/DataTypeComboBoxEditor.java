package org.jbiowhdesktop.sqltables.listener;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

/**
 * This Class is
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since Aug 13, 2012
 */
public class DataTypeComboBoxEditor implements ComboBoxEditor {

    private final String var[] = {"VARCHAR", "DECIMAL", "VARBINARY", "ENUM", "SET"};
    final protected JTextField text = new JTextField();
    transient protected EventListenerList listeners = new EventListenerList();

    public DataTypeComboBoxEditor(Dimension preferredSize) {
        text.setPreferredSize(preferredSize);
    }

    @Override
    public Component getEditorComponent() {
        return text;
    }

    @Override
    public void setItem(Object anObject) {
        if (anObject instanceof String) {
            String item = ((String) anObject).toUpperCase();
            if (Arrays.asList(var).contains((String) anObject)) {
                item = ((String) anObject).toUpperCase() + "()";
            }
            text.setText(item);
        }
    }

    @Override
    public Object getItem() {
        return text.getText();
    }

    @Override
    public void selectAll() {
        //Ignore
    }

    @Override
    public void addActionListener(ActionListener l) {
        listeners.add(ActionListener.class, l);
    }

    @Override
    public void removeActionListener(ActionListener l) {
        listeners.remove(ActionListener.class, l);
    }
}
