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
 * This enum defines the mismatch between cardinalities. Exposing discrepancies.
 */
public enum MisMatchCardinality implements MisMatch {

    /**
     * Cardinality MisMatches
     */
    CARDINALITY_MANDATORY("CAR-1"), // Hierarchical order many to one
    CARDINALITY_OPTIONAL("CAR-2"), // element on lower level
    CARDINALITY_MULTIPLE("CAR-3"), // different grouping
    CARDINALITY_SINGLE("CAR-4"), // less detail
    CARDINALITY_ELEMENT_MANDATORY("CAR-5"); // higher detail
    private static final Map<String, MisMatchCardinality> mMatchMap = new HashMap<String, MisMatchCardinality>();

    static {
        for (MisMatchCardinality c : values()) {
            mMatchMap.put(c.getValue(), c);
        }
    }

    public static MisMatchCardinality getByValue(String value, String semanticID) {
        updateStatistic(value);
        MisMatchCardinality c = mMatchMap.get(value);
        if (c == null) {
            LoggerFactory.getLogger(MisMatchCardinality.class.getName()).error("There is no cardinality mismatch for '" + value + "' used in Semantic object with ID '" + semanticID + "'!\n");
        }
        return c;
    }

    private final String mMatch;

    /**
     * @return the match
     */
    @Override
    public String getValue() {
        return mMatch;
    }

    MisMatchCardinality(String match) {
        this.mMatch = match;
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
}

