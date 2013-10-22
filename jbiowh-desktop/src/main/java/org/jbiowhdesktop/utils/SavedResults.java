package org.jbiowhdesktop.utils;

import java.util.*;
import org.jbiowhcore.utility.constrains.JPLConstrains;
import org.jbiowhdesktop.component.panel.AbstractDataSetView;
import org.jbiowhpersistence.utils.entitymanager.EntityParserFieldProxy;

/**
 * This Class is storage the previously saved results
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-10-03 22:11:05 +0200 (Wed, 03 Oct 2012) $
 * $LastChangedRevision: 270 $
 * @since Feb 15, 2012
 */
public class SavedResults {

    private HashMap<String, Object> result = null;
    private static SavedResults singleton;

    private SavedResults() {
    }

    /**
     * Return a SavedResults
     *
     * @return a SavedResults
     */
    public static synchronized SavedResults getInstance() {
        if (singleton == null) {
            singleton = new SavedResults();
        }
        return singleton;
    }

    /**
     * Add a result Object to the saved result list
     *
     * @param object a result Object to be saved
     */
    public void addResult(Object object) {
        if (result == null) {
            result = new HashMap<>();
        }
        if (object instanceof Collection) {
            if (!((Collection) object).isEmpty()) {
                if (((Collection) object).size() > 1) {
                    String className = ((Collection) object).iterator().next().getClass().getSimpleName();
                    result.put(getKeyNumber(className), object);
                } else {
                    result.put(EntityParserFieldProxy.getInstance().getId(((Collection) object).iterator().next()), object);
                }
            }
        } else if (object instanceof JPLConstrains) {
            result.put(getKeyNumber("Constrain"), object);
        } else if (object instanceof AbstractDataSetView) {
            Collection data = new ArrayList();
            AbstractDataSetView view = (AbstractDataSetView) object;
            data.add(view.getDataSetObject());
            result.put(EntityParserFieldProxy.getInstance().getId(view.getDataSetObject()), data);
        }
    }

    /**
     * Return a set with the field to be visualized by dataset
     *
     * @param key the key in the result map
     * @return a String set
     */
    public Set<String> listResult(String key) {
        Set<String> keys = new TreeSet();

        Object col = result.get(key);
        if (col instanceof Collection) {
            for (Object data : (Collection) col) {
                keys.add(EntityParserFieldProxy.getInstance().getId(data));
            }
        } else if (col instanceof JPLConstrains) {
            for (int i = 0; i < ((JPLConstrains) col).getConstrains().size(); i++) {
                keys.add(((JPLConstrains) col).getConstrainObject(i).getClass().getSimpleName());
            }
        } else {
            keys.add(key);
        }

        return keys;
    }

    /**
     * Remove the internal result into the key with id
     *
     * @param key the key in the result map
     * @param field the field visualized
     */
    public void removeIntResult(String key, String field) {
        Object col = result.get(key);
        if (col instanceof Collection) {
            for (Object data : (Collection) col) {
                if (EntityParserFieldProxy.getInstance().isId(data, field)) {
                    ((Collection) col).remove(data);
                    break;
                }
            }
        }
    }

    /**
     * Get internal result
     *
     * @param key the key in the result map
     * @param field the field visualized
     * @return a Collection Object
     */
    public Collection getIntResult(String key, String field) {
        Collection newCol = new ArrayList();
        Object col = result.get(key);
        if (col instanceof Collection) {
            for (Object data : (Collection) col) {
                if (EntityParserFieldProxy.getInstance().isId(data, field)) {
                    newCol.add(data);
                    return newCol;
                }
            }
        } else {
            if (EntityParserFieldProxy.getInstance().isId(col, field)) {
                newCol.add(col);
                return newCol;
            }
        }
        return null;
    }

    /**
     * Get the result map
     *
     * @return the result map
     */
    public HashMap<String, Object> getResult() {
        return result;
    }

    /**
     * Get the result object at index index
     *
     * @param key the object's key in the result map
     * @return the result object
     */
    public Object getResult(String key) {
        return result.get(key);
    }

    private String getKeyNumber(String key) {
        int j = 1;
        for (String a : result.keySet()) {
            if (a.startsWith(key)) {
                int n = new Integer(a.substring(a.lastIndexOf(" ") + 1));
                if (j <= n) {
                    j = n + 1;
                }
            }
        }
        return key + " " + j;
    }
}
