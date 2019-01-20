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
public enum SemanticMisMatch implements MisMatch {

    /**
     * Semantic MisMatches
     */
    SEMANTIC_SMALLER("SEM-1"),
    SEMANTIC_WIDER("SEM-2"),
    SEMANTIC_OVERLAP("SEM-3"),
    SEMANTIC_NO_MATCH("SEM-4"),;
    private static final Map<String, SemanticMisMatch> mMatchMap = new HashMap<String, SemanticMisMatch>();

    static {
        for (SemanticMisMatch c : values()) {
            mMatchMap.put(c.getValue(), c);
        }
    }

    public static SemanticMisMatch getByValue(String value) {
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

    SemanticMisMatch(String match) {
        this.mMatch = match;
    }
}
