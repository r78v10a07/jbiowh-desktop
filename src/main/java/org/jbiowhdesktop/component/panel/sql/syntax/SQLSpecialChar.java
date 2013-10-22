package org.jbiowhdesktop.component.panel.sql.syntax;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class handled the SQL special characters parenthesis
 *
 * $Author: r78v10a07@gmail.com $ $LastChangedDate: 2012-08-31 15:46:36 +0200
 * (Fri, 31 Aug 2012) $ $LastChangedRevision: 231 $
 *
 * @since May 23, 2012
 */
public class SQLSpecialChar {

    private final Character SPACE = ' ';
    private final Character ENDOFLINE = '\n';
    private final Character DOT = '.';
    private final Character COMA = ',';
    private final Character COLN = ';';
    private final Character OPENPARENTHESIS = '(';
    private final Character CLOSEPARENTHESIS = ')';
    private final Character EQUAL = '=';
    private final Character NOTEQUAL = '!';
    private final Character LESSTHAN = '<';
    private final Character GREATERTHAN = '>';
    private final Character AND = '&';
    private final Character OR = '|';
    private static SQLSpecialChar singleton;
    private static List specialChar;

    private SQLSpecialChar() {
    }

    /**
     * Return a SQLSpecialChar
     *
     * @return a SQLSpecialChar
     */
    public static synchronized SQLSpecialChar getInstance() {
        if (singleton == null) {
            singleton = new SQLSpecialChar();
            singleton.fillList();
        }
        return singleton;
    }

    private void fillList() {
        specialChar = new ArrayList();
        specialChar.add(SPACE);
        specialChar.add(ENDOFLINE);
        specialChar.add(DOT);
        specialChar.add(COMA);
        specialChar.add(COLN);
        specialChar.add(OPENPARENTHESIS);
        specialChar.add(CLOSEPARENTHESIS);
        specialChar.add(EQUAL);
        specialChar.add(NOTEQUAL);
        specialChar.add(LESSTHAN);
        specialChar.add(GREATERTHAN);
        specialChar.add(AND);
        specialChar.add(OR);
    }

    /**
     * Get the SQL special characters
     *
     * @return a List object with the SQL special characters
     */
    public List getSQLSpecialChars() {
        return specialChar;
    }
}
