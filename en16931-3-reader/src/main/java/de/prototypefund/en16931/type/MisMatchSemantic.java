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
public enum MisMatchSemantic implements MisMatch {

    /**
     * Semantic MisMatches
     */
    SEMANTIC_SMALLER("SEM-1"),
    SEMANTIC_WIDER("SEM-2"),
    SEMANTIC_OVERLAP("SEM-3"),
    SEMANTIC_NO_MATCH("SEM-4"),;
    private static final Map<String, MisMatchSemantic> mMatchMap = new HashMap<String, MisMatchSemantic>();

    static {
        for (MisMatchSemantic c : values()) {
            mMatchMap.put(c.getValue(), c);
        }
    }

    public static MisMatchSemantic getByValue(String value, String semanticID) {
        updateStatistic(value);
        MisMatchSemantic s = mMatchMap.get(value);
        if (s == null) {
            LoggerFactory.getLogger(MisMatchSemantic.class.getName()).error("There is no semantic mismatch for '" + value + "'. Found in Semantic object with ID '" + semanticID + "'!\n");
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

    MisMatchSemantic(String match) {
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
