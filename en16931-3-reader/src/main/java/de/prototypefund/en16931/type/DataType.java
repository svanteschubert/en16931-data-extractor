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
public enum DataType {

    /**
     * Data Types      <code>
     * — A = Betrag
     * — B = Binärobjekt
     * — C = Code
     * — D = Datum
     * — I = Kennung
     * — M = numerisch
     * — N = normalisierter String
     * — P = Prozentsatz
     * — Q = Menge
     * — S = String
     * — T = Text
     * — U = Einheitspreisbetrag
     * </code>
     */
    AMOUNT("A"),
    BINARYOBJECT("B"),
    CODE("C"),
    DATE("D"),
    ID("I"),
    NUMERIC("M"),
    NORMALISED_STRING("N"),
    PERCENT("P"),
    QUANTITY("Q"),
    STRING("S"),
    TEXT("T"),
    UNIT_PRICE_AMMOUNT("U");

    private static final Map<String, DataType> mTypeMap = new HashMap<String, DataType>();

    static {
        for (DataType c : values()) {
            mTypeMap.put(c.getValue(), c);
        }
    }

    public static DataType getByValue(String value, String semanticID) {
        updateStatistic(value);
        DataType d = mTypeMap.get(value);
        if (d == null) {
           LoggerFactory.getLogger(Type.class.getName()).error("A data type being abbreviated '" + value + "' does not exist! Found in Semantic object with ID '" + semanticID + "'!\n");
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

    DataType(String dataType) {
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
            statistic.clear();;
        }
    }
}
