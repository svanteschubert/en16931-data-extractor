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
 * This enum defines the mismatch between of semantic. Exposing discrepancies.
 */
public enum MisMatchStructural implements MisMatch {

    /**
     * Structural MisMatches
     */
    STRUCTURAL_MANY_TO_ONE("STR-1"), // Hierarchical order many to one
    STRUCTURAL_ON_LOWER_LEVEL("STR-2"), // element on lower level
    STRUCTURAL_DIFFERENT_GROUPING("STR-3"), // different grouping
    STRUCTURAL_LESS_DETAIL("STR-4"), // less detail
    STRUCTURAL_HIGHER_DETAIL("STR-5"); // higher detail// higher detail
    private static final Map<String, MisMatchStructural> mMatchMap = new HashMap<String, MisMatchStructural>();

    static {
        for (MisMatchStructural c : values()) {
            mMatchMap.put(c.getValue(), c);
        }
    }

    public static MisMatchStructural getByValue(String value, String semanticID) {
        updateStatistic(value);
        MisMatchStructural s = mMatchMap.get(value);

        if (s == null) {
            LoggerFactory.getLogger(MisMatchSemantic.class.getName()).error("ERROR: There is no structural mismatch for '" + value + "'. Found in Semantic object with ID '" + semanticID + "'!\n");
        }
        return s;
    }

    private final String mMatch;

    /**
     * @return the match
     */
    @Override
    public String getValue() {
        return mMatch;
    }

    MisMatchStructural(String match) {
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

    static void clearStatistic(){
        if(statistic != null){
            statistic.clear();
        }
    }
}
