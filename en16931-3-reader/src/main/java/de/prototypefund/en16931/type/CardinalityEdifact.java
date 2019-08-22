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
 * This enum describes the Cardinality type being used in EN16931-3-4
 */
public enum CardinalityEdifact {

    /**
     * Cardinalities
     */
    ONE2ONE("1..1"),
    ZERO2ONE("0..1"),
    ONE2ZERO("1..0"),
    ZERO2MANY("0..n"),
    ZERO2TWO("0..2"),
    ZERO2FIVE("0..5"),
    ONE2TEN("1..10"),
    ZERO2TEN("0..10"),
    ONE2_25("1..25"),
    ZERO2_25("0..25"),
    ZERO2_30("0..30"),
    ZERO2_35("0..35"),
    ONE2_99("1..99"),
    ZERO2_99("0..99"),
    ZERO2_9999("0..9999"),
    ZERO2_99999("0..99999"),
    ONE2_9999999("1..9999999"),
    ZERO2_9999999("0..9999999"),
    MANY2ZERO("n..0"),
    ONE2MANY("1..n"),
    MANY2ONE("n..1"),
    MANY2MANY("n..n");
    private static final Map<String, CardinalityEdifact> mCardMap = new HashMap<String, CardinalityEdifact>();

    static {
        for (CardinalityEdifact c : values()) {
            mCardMap.put(c.getValue(), c);
        }
    }

    public static CardinalityEdifact getByValue(String value, String semanticID) {
        updateStatistic(value);
        CardinalityEdifact ce = mCardMap.get(value);
        if (ce == null) {
            LoggerFactory.getLogger(CardinalityEdifact.class.getName()).error("ERROR: There is no cardinality for '" + value + "'. Found in Semantic object with ID '" + semanticID + "'!\n");
        }
        return ce;
    }

    private final String mCardinality;

    /**
     * @return the cardinality
     */
    public String getValue() {
        return mCardinality;
    }

    CardinalityEdifact(String cardinality) {
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
