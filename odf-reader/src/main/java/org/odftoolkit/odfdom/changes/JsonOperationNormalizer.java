/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANT
IES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.odftoolkit.odfdom.changes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import static org.odftoolkit.odfdom.changes.OperationConstants.OPK_END;
import static org.odftoolkit.odfdom.changes.OperationConstants.OPK_NAME;
import static org.odftoolkit.odfdom.changes.OperationConstants.OPK_OPERATIONS;
import static org.odftoolkit.odfdom.changes.OperationConstants.OPK_START;
import static org.odftoolkit.odfdom.changes.OperationConstants.OPK_STYLE_ID;
import static org.odftoolkit.odfdom.changes.OperationConstants.OPK_TYPE;
import static org.odftoolkit.odfdom.changes.OperationConstants.OP_STYLE;

/**
 * Normalizes the content of JSONObject to make it comparable.
 *
 * @author svante.schubertATgmail.com
 */
public class JsonOperationNormalizer {

    // If in an operation a map contains one of the following keys, they will be added at the beginning for better readability!
    private static final String[] SORTING_SEQUENCE_OF_KEYS = {OPK_NAME, OPK_START, OPK_END, OPK_TYPE, OPK_STYLE_ID};
    private static final Logger LOG = Logger.getLogger(JsonOperationNormalizer.class.getName());

    /**
     * Make a JSON text of this JSONObject. For compactness, no whitespace is
     * added. If this would not result in a syntactically correct JSON text,
     * then null will be returned instead.
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     *
     * @return a printable, displayable, portable, transmittable representation
     * of the object, beginning with <code>{</code>&nbsp;<small>(left
     * brace)</small> and ending with <code>}</code>&nbsp;<small>(right
     * brace)</small>.
     */
    public static String asString(JSONObject jsonObject) {
        StringBuilder sb = new StringBuilder("{");
        Iterator<String> keys = getSortedIterator(jsonObject);
        while (keys.hasNext()) {
            String key = keys.next();
            if (key.equals(OPK_OPERATIONS)) {
                sb.append("\"" + OPK_OPERATIONS + "\":[\n");
                try {
                    JSONArray ops = jsonObject.getJSONArray(OPK_OPERATIONS);
                    sb.append(normalizeOperations(ops));
                } catch (JSONException ex) {
                    Logger.getLogger(JsonOperationNormalizer.class.getName()).log(Level.SEVERE, null, ex);
                }
                sb.append("\n]");
            } else {
                try {
                    if (sb.length() > 1) {
                        sb.append(',');
                    }
                    sb.append('"');
                    sb.append(key);
                    sb.append('"');
                    sb.append(':');
                    Object value = jsonObject.get(key);
                    // Encodingproblem - unescaping and escaping provides problems with either slash (e.g. http://) and/or backslash (e.g. c:\path\)
                    // value = StringEscapeUtils.unescapeJson(value.toString());
                    appendValueAsString(value, sb);
                } catch (JSONException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * Make a JSON text of this JSONArray. For compactness, no unnecessary
     * whitespace is added. If it is not possible to produce a syntactically
     * correct JSON text then null will be returned instead. This could occur if
     * the array contains an invalid number.
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     *
     * @return a printable, displayable, transmittable representation of the
     * array.
     */
    static String asString(JSONArray array) {
        try {
            return '[' + normalizeOperations(array) + ']';
        } catch (Exception e) {
            LOG.severe(e.getMessage());
        }
        return null;
    }

    private static Iterator<String> getSortedIterator(JSONObject jsonObject) {
        // have to duplicate set, as the original set is read-only
        Set<String> keySet = new HashSet<String>(jsonObject.keySet());
        List<String> firstListedKeys = null;
        for (String SORTING_SEQUENCE_OF_KEYS1 : SORTING_SEQUENCE_OF_KEYS) {
            // do not sort 'name' property
            if (keySet.contains(SORTING_SEQUENCE_OF_KEYS1)) {
                if (firstListedKeys == null) {
                    firstListedKeys = new ArrayList<String>(3);
                }
                firstListedKeys.add(SORTING_SEQUENCE_OF_KEYS1);
                keySet.remove(SORTING_SEQUENCE_OF_KEYS1);
            }
        }
        List<String> list = new ArrayList<String>(keySet);
        // sort the remaining keys
        Collections.sort(list);

        // if there are any priority keys
        if (firstListedKeys != null) {
            // add them at the beginning of the operation list (being a JSONObject)
            for (int j = 0; j < firstListedKeys.size(); j++) {
                list.add(j, firstListedKeys.get(j));
            }
        }
        return list.iterator();
    }

    /**
     * Make a JSON text of an Object value. If the object has an
     * value.toJSONString() method, then that method will be used to produce the
     * JSON text. The method is required to produce a strictly conforming text.
     * If the object does not contain a toJSONString method (which is the most
     * common case), then a text will be produced by the rules.
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     *
     * @param value The value to be serialized.
     * @return a printable, displayable, transmittable representation of the
     * object, beginning with <code>{</code>&nbsp;<small>(left brace)</small>
     * and ending with <code>}</code>&nbsp;<small>(right brace)</small>.
     * @throws JSONException If the value is or contains an invalid number.
     */
    private static void appendValueAsString(Object value, StringBuilder sb) {
        if (value instanceof JSONObject) {
            sb.append(JsonOperationNormalizer.asString((JSONObject) value));
        } else if (value instanceof String) {
            sb.append('"');
            // Encodingproblem - sb.append(StringEscapeUtils.escapeJson((String) value));
            sb.append((String) value);
            sb.append('"');
        } else if (value instanceof JSONArray) {
            sb.append(asString((JSONArray) value));
        } else if (value instanceof JSONString) {
            sb.append('"');
            sb.append(((JSONString) value).toJSONString());
            sb.append('"');
        } else if (value instanceof Number) {
            sb.append(numberToString((Number) value));
        } else if (value instanceof Boolean) {
            sb.append(value.toString());
        } else if (value == null || value == JSONObject.NULL) {
            sb.append("null");
        } else {
            sb.append(value.toString());
        }
    }

    /**
     * Produce a string from a Number.
     *
     * @param n A Number
     * @return A String.
     * @throws JSONException If n is a non-finite number.
     */
    private static String numberToString(Number n) {
        if (n == null) {
            throw new RuntimeException("Null pointer");
        }
        testValidity(n);

        // Shave off trailing zeros and decimal point, if possible.
        String s = n.toString();
        if (s.indexOf('.') > 0 && s.indexOf('e') < 0 && s.indexOf('E') < 0) {
            while (s.endsWith("0")) {
                s = s.substring(0, s.length() - 1);
            }
            if (s.endsWith(".")) {
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }

    /**
     * Throw an exception if the object is an NaN or infinite number.
     *
     * @param o The object to test.
     * @throws JSONException If o is a non-finite number.
     */
    private static void testValidity(Object o) {
        if (o != null) {
            if (o instanceof Double) {
                if (((Double) o).isInfinite() || ((Double) o).isNaN()) {
                    throw new RuntimeException("JSON does not allow non-finite numbers");
                }
            } else if (o instanceof Float) {
                if (((Float) o).isInfinite() || ((Float) o).isNaN()) {
                    throw new RuntimeException("JSON does not allow non-finite numbers.");
                }
            } else if (o instanceof BigDecimal || o instanceof BigInteger) {
                // ok
                return;
            }
        }
    }

    /**
     * Make a string from the contents of this JSONArray.
     *
     * Currently only adjacent style operations are being sorted.
     *
     * Warning: This method assumes that the data structure is acyclical.
     *
     * @throws JSONException If the array contains an invalid number.
     */
    private static String normalizeOperations(JSONArray array) {
        int len = array.length();
        StringBuilder sb = new StringBuilder(len);

        // we will go through all elements of the array
        boolean opsCollected = false;
        SortedSet<JSONObject> sameTypOperations = null;
        for (int i = 0; i < len; i += 1) {
            if (i > 0 && !opsCollected) {
                sb.append(',');
            }
            String lastOperationName = null;
            try {
                Object o = array.get(i);
                if (o instanceof JSONObject) {
                    JSONObject operation = (JSONObject) o;
                    if (operation.has(OPK_NAME)) {
                        String newOperationName = operation.getString(OPK_NAME);
                        if (lastOperationName == null) {
                            lastOperationName = newOperationName;
                        }
                        if (newOperationName.equals(OP_STYLE) && lastOperationName.equals(newOperationName)) {
                            // ADD EQUAL OPERATIONS
                            if (sameTypOperations == null) {
                                sameTypOperations = new TreeSet<>(new OperationSorter());
                            }
                            sameTypOperations.add(operation);
                            opsCollected = true;
                        } else {
                            if (opsCollected) {
                                // FLUSH SORTED OPERATIONS
                                for (JSONObject sortedOperation : sameTypOperations) {
                                    appendValueAsString(sortedOperation, sb);
                                    sb.append(',');
                                }
                                sameTypOperations.clear();
                                opsCollected = false;
                            }
                            appendValueAsString(operation, sb);
                            lastOperationName = null;
                        }
                    } else {
                        appendValueAsString(operation, sb);
                        lastOperationName = null;
                    }
                } else {
                    appendValueAsString(o, sb);
                }

            } catch (JSONException ex) {
                sb.append("null");
                ex.printStackTrace();
            }
        }
        if (opsCollected) {
            // FLUSH SORTED OPERATIONS
            for (Iterator<JSONObject> iter = sameTypOperations.iterator(); iter.hasNext();) {
                appendValueAsString(iter.next(), sb);
                if (iter.hasNext()) {
                    sb.append(',');
                }
            }
        }
        return sb.toString();
    }

    static class OperationSorter implements Comparator<JSONObject> {

        private static Collator mCollator = null;

        public int compare(JSONObject op1, JSONObject op2) {
            int returnValue = 0;
            if (op1 instanceof JSONObject && op2 instanceof JSONObject) {
                try {
                    String op1Name = op1.getString(OPK_NAME);
                    String op2Name = op2.getString(OPK_NAME);
                    if (op1Name.equals(OP_STYLE) && op1Name.equals(op2Name)) {
                        String uniqueStyleName1 = op1.getString("type") + op1.getString(OPK_STYLE_ID);
                        String uniqueStyleName2 = op2.getString("type") + op2.getString(OPK_STYLE_ID);
                        if (mCollator == null) {
                            mCollator = Collator.getInstance(Locale.US);
                        }
                        return mCollator.compare(uniqueStyleName1, uniqueStyleName2);
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(JsonOperationNormalizer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return returnValue;
        }
    }
}
