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
 * This enum describes CII XML &amp; EDIFACT syntax types as used in the syntax binding tables.
 */
public enum SyntaxType implements Type {

    /**
     * All Types * <code>
     * — G = Aggregat
     * — C = Verbund
     * — E = Element
     * — A = Attribut
     * — S = Segment
     * </code>
     */
    AGGREGATE("G"),
    COLLECTION("C"),
    ELEMENT("E"),
    ATTRIBUTE("A"),
    SEGMENT("S");

    private static final Map<String, SyntaxType> mTypeMap = new HashMap<String, SyntaxType>();

    static {
        for (SyntaxType c : values()) {
            mTypeMap.put(c.getValue(), c);
        }
    }

    public static SyntaxType getByValue(String value, String semanticID) {
        updateStatistic(value);
        SyntaxType t = mTypeMap.get(value);

        if (t == null) {
            LoggerFactory.getLogger(SyntaxType.class.getName()).error("ERROR: It do not exist a syntax type of '" + value + "'. Found in Semantic object with ID '" + semanticID + "'!\n");
        }
        return t;
    }

    private final String mType;

    /**
     * @return the xmlType
     */
    public String getValue() {
        return mType;
    }

    SyntaxType(String type) {
        this.mType = type;
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

