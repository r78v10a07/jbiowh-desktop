package org.jbiowhdesktop.component.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.TreeCellRenderer;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhpersistence.utils.entitymanager.JBioWHPersistence;

/**
 * This Class is the renderer for the Schema Tree
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2013-09-06 14:21:09 +0200
 * (Fri, 06 Sep 2013) $ $LastChangedRevision: 664 $
 *
 * @since Jul 30, 2012
 */
public class TreeSchemaRenderer extends JPanel implements TreeCellRenderer {

    private JLabel icon;
    private TreeTextArea text;

    public TreeSchemaRenderer() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        icon = new JLabel() {
            @Override
            public void setBackground(Color color) {
                if (color instanceof ColorUIResource) {
                    color = null;
                }
                super.setBackground(color);
            }
        };
        add(icon);
        add(Box.createHorizontalStrut(4));
        add(text = new TreeTextArea());
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean isSelected, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
        String stringValue = tree.convertValueToText(value, isSelected,
                expanded, leaf, row, hasFocus);
        setEnabled(tree.isEnabled());
        text.setText(stringValue);
        text.setSelect(isSelected);
        text.setFocus(hasFocus);
        if (leaf) {
            icon.setIcon(UIManager.getIcon("Tree.leafIcon"));
        } else if (expanded) {
            icon.setIcon(UIManager.getIcon("Tree.openIcon"));
        } else {
            icon.setIcon(UIManager.getIcon("Tree.closedIcon"));
        }
        if (JBioWHPersistence.getInstance().getWhdbmsFactory() != null) {
            if (JBioWHPersistence.getInstance().getMainURLParsed() != null && JBioWHPersistence.getInstance().getMainURLParsed().equals(stringValue)) {
                text.setFont(text.getFont().deriveFont(Font.BOLD));
            } else {
                text.setFont(text.getFont().deriveFont(Font.PLAIN));
            }
        }
        return this;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension iconD = icon.getPreferredSize();
        Dimension textD = text.getPreferredSize();
        int height = iconD.height < textD.height ? textD.height : iconD.height;
        return new Dimension(iconD.width + textD.width, height);
    }

    @Override
    public void setBackground(Color color) {
        if (color instanceof ColorUIResource) {
            color = null;
        }
        super.setBackground(color);
    }

    class TreeTextArea extends JTextArea {

        Dimension preferredSize;

        TreeTextArea() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public void setBackground(Color color) {
            if (color instanceof ColorUIResource) {
                color = null;
            }
            super.setBackground(color);
        }

        @Override
        public void setPreferredSize(Dimension d) {
            if (d != null) {
                preferredSize = d;
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return preferredSize;
        }

        @Override
        public void setText(String str) {
            FontMetrics fm = text.getFontMetrics(text.getFont());
            BufferedReader br = new BufferedReader(new StringReader(str));
            String line;
            int maxWidth = 0, lines = 0;
            try {
                while ((line = br.readLine()) != null) {
                    int width = SwingUtilities.computeStringWidth(fm, line);
                    if (maxWidth < width) {
                        maxWidth = width;
                    }
                    lines++;
                }
            } catch (IOException ex) {
                VerbLogger.getInstance().setLevel(VerbLogger.getInstance().ERROR);
                VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
                VerbLogger.getInstance().setLevel(VerbLogger.getInstance().getInitialLevel());
            }
            lines = (lines < 1) ? 1 : lines;
            int height = fm.getHeight() * lines;
            setPreferredSize(new Dimension(maxWidth + 6, height));
            super.setText(str);
        }

        void setSelect(boolean isSelected) {
            Color bColor;
            if (isSelected) {
                bColor = UIManager.getColor("Tree.selectionBackground");
            } else {
                bColor = UIManager.getColor("Tree.textBackground");
            }
            super.setBackground(bColor);
        }

        void setFocus(boolean hasFocus) {
            if (hasFocus) {
                Color lineColor = UIManager
                        .getColor("Tree.selectionBorderColor");
                setBorder(BorderFactory.createLineBorder(lineColor));
            } else {
                setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            }
        }
    }
}
