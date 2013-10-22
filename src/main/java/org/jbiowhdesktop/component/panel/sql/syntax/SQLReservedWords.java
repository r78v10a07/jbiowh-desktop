package org.jbiowhdesktop.component.panel.sql.syntax;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class handled the SQL reserved words
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since May 21, 2012
 */
public class SQLReservedWords {

    private final String SELECT = "SELECT";
    private final String FROM = "FROM";
    private final String WHERE = "WHERE";
    private final String INNER = "INNER";
    private final String JOIN = "JOIN";
    private final String LEFT = "LEFT";
    private final String UNION = "UNION";
    private final String DISTINCT = "DISTINCT";
    private final String GROUP = "GROUP";
    private final String BY = "BY";
    private final String ASC = "ASC";
    private final String DESC = "DESC";
    private final String HAVING = "HAVING";
    private final String AS = "AS";
    private final String ORDER = "ORDER";
    private final String LIMIT = "LIMIT";
    private final String OFFSET = "OFFSET";
    private final String SHOW = "SHOW";
    private final String WARNINGS = "WARNINGS";
    private final String CREATE = "CREATE";
    private final String TABLE = "TABLE";
    private final String EXISTS = "EXISTS";
    private final String DROP = "DROP";
    private final String INDEX = "INDEX";
    private final String PRIMARY = "PRIMARY";
    private final String KEY = "KEY";
    private static SQLReservedWords singleton;
    private static List reservedWords;

    private SQLReservedWords() {
    }

    /**
     * Return a SQLReservedWords
     *
     * @return a SQLReservedWords
     */
    public static synchronized SQLReservedWords getInstance() {
        if (singleton == null) {
            singleton = new SQLReservedWords();            
            singleton.fillList();
        }
        return singleton;
    }
    
    private void fillList(){
        reservedWords = new ArrayList();
        reservedWords.add(SELECT);
        reservedWords.add(FROM);
        reservedWords.add(WHERE);
        reservedWords.add(INNER);
        reservedWords.add(JOIN);
        reservedWords.add(LEFT);
        reservedWords.add(UNION);
        reservedWords.add(DISTINCT);
        reservedWords.add(GROUP);
        reservedWords.add(BY);
        reservedWords.add(ASC);
        reservedWords.add(DESC);
        reservedWords.add(HAVING);
        reservedWords.add(AS);
        reservedWords.add(ORDER);
        reservedWords.add(LIMIT);
        reservedWords.add(OFFSET);
        reservedWords.add(SHOW);
        reservedWords.add(WARNINGS);
        reservedWords.add(CREATE);
        reservedWords.add(TABLE);
        reservedWords.add(EXISTS);
        reservedWords.add(DROP);
        reservedWords.add(INDEX);
        reservedWords.add(PRIMARY);
        reservedWords.add(KEY);
    }

    /**
     * Get the SQL Reserved Words
     *
     * @return a list object with the SQL Reserved Words
     */
    public List getSQLReservedWords() {
        return reservedWords;
    }
}
