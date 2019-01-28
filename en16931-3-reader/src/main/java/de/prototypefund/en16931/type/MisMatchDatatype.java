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
public enum MisMatchDatatype implements MisMatch {

    /**
     * Semantic MisMatches
     */
    DATATYPE_SMALLER("SYN-1"),
    DATATYPE_WIDER("SYN-2"),
    DATATYPE_NO_MATCH("SYN-3"),;
    private static final Map<String, MisMatchDatatype> mMatchMap = new HashMap<String, MisMatchDatatype>();

    static {
        for (MisMatchDatatype c : values()) {
            mMatchMap.put(c.getValue(), c);
        }
    }

    public static MisMatchDatatype getByValue(String value, String semanticID) {
        updateStatistic(value);
        MisMatchDatatype d = mMatchMap.get(value);
        if (d == null) {
            LoggerFactory.getLogger(MisMatchDatatype.class.getName()).error("There is no datatype mismatch for '" + value + "' used in Semantic object with ID '" + semanticID + "'!\n");
        }
        return d;
    }

    private final String mMatch;

    /**
     * @return the match
     */
    @Override
    public String getValue() {
        return mMatch;
    }

    MisMatchDatatype(String match) {
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

