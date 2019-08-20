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
 * This enum contains all 10 data types of the UBL 3-2 part
 * within the EN 16931 specification.
 *
 * As described in table "Table 1 — UBL data types" of EN16931-3-2.
 */
public enum UblDataType implements Type {

    /**
     * UBL Data Types<code>
     * — A = Amount
     * — B = BinaryObject
     * — C = Code
     * — D = Date
     * — I = Identifier
     * — N = Name
     * — Nb = Numeric
     * — P = Percent
     * — Q = Quantity
     * — T = Text
     * </code>
     */
    AMOUNT("A"),
    BINARYOBJECT("B"),
    CODE("C"),
    DATE("D"),
    ID("I"),
    NAME("N"),
    NUMERIC("Nb"),
    PERCENT("P"),
    QUANTITY("Q"),
    TEXT("T");

    private static final Map<String, UblDataType> mTypeMap = new HashMap<String, UblDataType>();

    static {
        for (UblDataType c : values()) {
            mTypeMap.put(c.getValue(), c);
        }
    }

    public static UblDataType getByValue(String value, String ublID) {
        updateStatistic(value);
        UblDataType d = mTypeMap.get(value);
        if (d == null) {
           LoggerFactory.getLogger(SyntaxType.class.getName()).error("The UBL XML data type being abbreviated '" + value + "' does not exist! Found in UBL object with ID '" + ublID + "'!\n");
        }
        return d;
    }

    private final String mDataType;

    /**
     * @return the DataType
     */
    @Override
    public String getValue() {
        return mDataType;
    }

    UblDataType(String dataType) {
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
