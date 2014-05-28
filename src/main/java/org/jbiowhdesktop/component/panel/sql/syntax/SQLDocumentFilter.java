package org.jbiowhdesktop.component.panel.sql.syntax;

import java.awt.Color;
import java.util.Arrays;
import javax.swing.text.*;
import org.jbiowhcore.logger.VerbLogger;
import org.jbiowhdbms.dbms.JBioWHDBMS;

/**
 * This Class is the document filter to put color in the editor
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2013-03-19 09:38:47 +0100
 * (Tue, 19 Mar 2013) $ $LastChangedRevision: 396 $
 *
 * @since May 25, 2012
 */
public class SQLDocumentFilter extends DocumentFilter {

    private StyleContext sc;
    private final Style sqlStyle;
    private final Style sqlDefaultStyle;
    private final Style sqlFunctionStyle;
    private final Style sqlOperatorStyle;
    private final Style sqlStringStyle;
    private JBioWHDBMS wHDBMSFactory;

    public SQLDocumentFilter(JBioWHDBMS wHDBMSFactory) {
        this.wHDBMSFactory = wHDBMSFactory;
        sc = new StyleContext();
        sqlStyle = sc.addStyle("SQLStyle", null);
        StyleConstants.setBold(sqlStyle, true);
        StyleConstants.setForeground(sqlStyle, Color.GREEN);

        sqlDefaultStyle = sc.addStyle("DefaultStyle", null);
        StyleConstants.setBold(sqlDefaultStyle, false);
        StyleConstants.setForeground(sqlDefaultStyle, Color.BLACK);

        sqlFunctionStyle = sc.addStyle("FunctionStyle", null);
        StyleConstants.setBold(sqlFunctionStyle, false);
        StyleConstants.setForeground(sqlFunctionStyle, Color.BLUE);

        sqlOperatorStyle = sc.addStyle("OperatorStyle", null);
        StyleConstants.setBold(sqlOperatorStyle, true);
        StyleConstants.setForeground(sqlOperatorStyle, Color.BLACK);

        sqlStringStyle = sc.addStyle("StringStyle", null);
        StyleConstants.setBold(sqlStringStyle, false);
        StyleConstants.setForeground(sqlStringStyle, Color.RED);
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        super.insertString(fb, offset, string, attr);
        filterDoc(fb, offset);        
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
        filterDoc(fb, offset);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
        super.replace(fb, offset, length, string, attrs);
        filterDoc(fb, offset);        
    }

    private void filterDoc(FilterBypass fb, int offset) {
        try {
            DefaultStyledDocument doc = (DefaultStyledDocument) fb.getDocument();
            String docText = doc.getText(0, doc.getLength());
            int index = offset - 1;
            while (index >= 0) {                
                if (SQLSpecialChar.getInstance().getSQLSpecialChars().contains(docText.charAt(index)) || index == 0) {
                    if (SQLReservedWords.getInstance().getSQLReservedWords().contains(docText.substring(index,offset).toUpperCase().trim())
                            || wHDBMSFactory.getDBMSDataTypes().contains(docText.substring(index, offset).toUpperCase().trim())
                            || (Arrays.asList(wHDBMSFactory.getDBMSIndexTypes())).contains(docText.substring(index, offset).toUpperCase().trim())) {
                        doc.setCharacterAttributes(index, offset - index, sqlStyle, false);
                        break;
                    } else if (SQLFunctionsAndOperators.getInstance().getSQLFunctionsAndOperators().contains(docText.substring(index, offset).toUpperCase().trim())) {
                        doc.setCharacterAttributes(index, offset - index, sqlFunctionStyle, false);
                        break;
                    } else if (docText.substring(index, offset).trim().startsWith("'")
                            && docText.substring(index, offset).endsWith("'")) {
                        doc.setCharacterAttributes(index, offset - index, sqlStringStyle, false);
                        break;
                    } else {
                        doc.setCharacterAttributes(index, offset - index, sqlDefaultStyle, false);
                    }
                    //doc.setCharacterAttributes(index, 1, sqlOperatorStyle, false);
                    break;
                }                
                index--;
            }

        } catch (BadLocationException ex) {
            VerbLogger.getInstance().log(this.getClass(), ex.getMessage());
        }
    }
}
