package org.jbiowhdesktop.component.panel.sql.syntax;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class handled the SQL Arithmetic Operators
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since May 21, 2012
 */
public class SQLFunctionsAndOperators {

    private final String EQUAL = "=";
    private final String NOTEQUAL = "!=";
    private final String NOTEQUAL1 = "<>";
    private final String GREATERTHANOREQUAL = ">=";
    private final String GREATERTHAN = ">";
    private final String LESSTHANOREQUAL = "<=";
    private final String LESSTHAN = "<";
    private final String LIKE = "LIKE";
    private final String IS = "IS";
    private final String NOT = "NOT";
    private final String NOT1 = "!";
    private final String NULL = "NULL";
    private final String USED = "USED";
    private final String LENGTH = "LENGTH";
    private final String LOCATE = "LOCATE";
    private final String AND = "AND";
    private final String AND1 = "&&";
    private final String OR = "OR";
    private final String OR1 = "||";
    private final String BETWEEN = "BETWEEN";
    private final String COUNT = "COUNT";
    private final String NULLSAFEEQUAL = "NULL-safe equal";
    private final String IN = "IN";
    private final String IF = "IF";
    private final String ON = "ON";
    private final String MAX = "MAX";
    private final String MIN = "MIN";
    private final String STRCMP = "STRCMP";
    private final String SUBSTR = "SUBSTR";
    private final String SUBSTRING = "SUBSTRING";
    private final String TRIM = "TRIM";
    private static SQLFunctionsAndOperators singleton;
    private static List funcAndOper;

    private SQLFunctionsAndOperators() {
    }

    /**
     * Return a SQLFunctionsAndOperators
     *
     * @return a SQLFunctionsAndOperators
     */
    public static synchronized SQLFunctionsAndOperators getInstance() {
        if (singleton == null) {
            singleton = new SQLFunctionsAndOperators();
            singleton.fillList();
        }
        return singleton;
    }
    
    private void fillList(){
        funcAndOper = new ArrayList();
        funcAndOper.add(EQUAL);
        funcAndOper.add(NOTEQUAL);
        funcAndOper.add(NOTEQUAL1);
        funcAndOper.add(GREATERTHANOREQUAL);
        funcAndOper.add(GREATERTHAN);
        funcAndOper.add(LESSTHANOREQUAL);
        funcAndOper.add(LESSTHAN);
        funcAndOper.add(LIKE);
        funcAndOper.add(IS);
        funcAndOper.add(NOT);
        funcAndOper.add(NOT1);
        funcAndOper.add(NULL);
        funcAndOper.add(USED);
        funcAndOper.add(LENGTH);
        funcAndOper.add(LOCATE);
        funcAndOper.add(AND);
        funcAndOper.add(AND1);
        funcAndOper.add(OR);
        funcAndOper.add(OR1);
        funcAndOper.add(BETWEEN);
        funcAndOper.add(COUNT);
        funcAndOper.add(NULLSAFEEQUAL);
        funcAndOper.add(IN);
        funcAndOper.add(IF);
        funcAndOper.add(ON);
        funcAndOper.add(MAX);
        funcAndOper.add(MIN);
        funcAndOper.add(STRCMP);
        funcAndOper.add(SUBSTR);
        funcAndOper.add(SUBSTRING);
        funcAndOper.add(TRIM);
    }

    /**
     * Get the SQL Functions and Operators
     *
     * @return a List object with the SQL Functions and Operators
     */
    public List getSQLFunctionsAndOperators() {
        return funcAndOper;
    }
}
