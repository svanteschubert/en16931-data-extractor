/*
 * Copyright 2019 Svante Schubert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.prototypefund.en16931.type;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.slf4j.LoggerFactory;

/**
 *
 * This enum contains all possible data types of the semantic part of the table
 * within the EN 16931 specification.
 */
public enum SemanticDataType implements Type {

    /**
     * Data Types      <code>
     * — A = Amount
     * — B = Binary Object
     * — C = Code
     * — D = Date
     * — I = Identifier
     * — M = Numeric
     * — O = Document Reference Identitifier
     * — P = Percent
     * — Q = Quantity
     * — S = Attribute
     * — T = Text
     * — U = Unit Price Amount
     * </code>
     */
    AMOUNT("A"),
    BINARYOBJECT("B"),
    CODE("C"),
    DATE("D"),
    ID("I"),
    NUMERIC("M"),
    DOCUMENT_REFERENCE_IDENTIFIER("O"),
    PERCENT("P"),
    QUANTITY("Q"),
    ATTRIBUTE("S"),
    TEXT("T"),
    UNIT_PRICE_AMOUNT("U");

    private static final Map<String, SemanticDataType> mTypeMap = new HashMap<String, SemanticDataType>();

    static {
        for (SemanticDataType c : values()) {
            mTypeMap.put(c.getValue(), c);
        }
    }

    public static SemanticDataType getByValue(String value, String semanticID) {
        updateStatistic(value);
        SemanticDataType d = mTypeMap.get(value);
        if (d == null) {
           LoggerFactory.getLogger(SemanticDataType.class.getName()).error("ERROR: The semantic data type being abbreviated '" + value + "' does not exist! Found in Semantic object with ID '" + semanticID + "'!\n");
        }
        return d;
    }

    private final String mDataType;

    /**
     * @return the DataType
     */
    public String getValue() {
        return mDataType;
    }

    SemanticDataType(String dataType) {
        this.mDataType = dataType;
    }

    private static SortedMap<String, Integer> statistic = null;

    static private void updateStatistic(String value){
        if(statistic == null){
           statistic = new TreeMap<String, Integer>();
        }
        Integer occurances = statistic.get(value);
        if(occurances == null){
            occurances = 1;
        }else{
            occurances += 1;
        }
        statistic.put(value, occurances);
    }

    static SortedMap<String, Integer> getStatistic(){
        return statistic;
    }

    static void clearStatistic(){
        if(statistic != null){
            statistic.clear();
        }
    }
}
