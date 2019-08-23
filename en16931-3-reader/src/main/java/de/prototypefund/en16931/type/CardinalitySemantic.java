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
 * This enum describes the CardinalitySemantic type being used in EN16931,
 * which is the same as for XML, but has it is own class for its statistic.
 */
public enum CardinalitySemantic {

    /**
     * Cardinalities
     */
    ONE2ONE("1..1"),
    ZERO2ONE("0..1"),
    ONE2ZERO("1..0"),
    ZERO2MANY("0..n"),
    MANY2ZERO("n..0"),
    ONE2MANY("1..n"),
    MANY2ONE("n..1"),
    MANY2MANY("n..n");
    private static final Map<String, CardinalitySemantic> mCardMap = new HashMap<String, CardinalitySemantic>();

    static {
        for (CardinalitySemantic c : values()) {
            mCardMap.put(c.getValue(), c);
        }
    }

    public static CardinalitySemantic getByValue(String value, String semanticID) {
        updateStatistic(value);
        CardinalitySemantic c = mCardMap.get(value);
        if (c == null) {
            LoggerFactory.getLogger(CardinalitySemantic.class.getName()).error("ERROR: There is no cardinality for '" + value + "'. Found in Semantic object with ID '" + semanticID + "'!\n");
        }
        return c;
    }

    private final String mCardinality;

    /**
     * @return the cardinality
     */
    public String getValue() {
        return mCardinality;
    }

    CardinalitySemantic(String cardinality) {
        this.mCardinality = cardinality;
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

