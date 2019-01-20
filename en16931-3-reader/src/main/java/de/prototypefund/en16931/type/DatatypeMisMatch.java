/*
 * Copyright 2018 The Apache Software Foundation.
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

/**
 * This enum defines the mismatch between of semantic. Exposing
 * discrepancies.
 */
public enum DatatypeMisMatch implements MisMatch {

    /**
     * Semantic MisMatches
     */
    DATATYPE_SMALLER("SYN-1"),
    DATATYPE_WIDER("SYN-2"),
    DATATYPE_NO_MATCH("SYN-3"),;
    private static final Map<String, DatatypeMisMatch> mMatchMap = new HashMap<String, DatatypeMisMatch>();

    static {
        for (DatatypeMisMatch c : values()) {
            mMatchMap.put(c.getValue(), c);
        }
    }

    public static DatatypeMisMatch getByValue(String value) {
        return mMatchMap.get(value);
    }


    private final String mMatch;

    /**
     * @return the match
     */
    @Override
    public String getValue() {
        return mMatch;
    }

    DatatypeMisMatch(String match) {
        this.mMatch = match;
    }
}
