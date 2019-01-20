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
public enum StructuralMisMatch implements MisMatch {

    /**
     * Structural MisMatches
     */
    STRUCTURAL_MANY_TO_ONE("STR-1"), // Hierarchical order many to one
    STRUCTURAL_ON_LOWER_LEVEL("STR-2"), // element on lower level
    STRUCTURAL_DIFFERENT_GROUPING("STR-3"), // different grouping
    STRUCTURAL_LESS_DETAIL("STR-4"), // less detail
    STRUCTURAL_HIGHER_DETAIL("STR-5"); // higher detail
    private static final Map<String, StructuralMisMatch> mMatchMap = new HashMap<String, StructuralMisMatch>();

    static {
        for (StructuralMisMatch c : values()) {
            mMatchMap.put(c.getValue(), c);
        }
    }

    public static StructuralMisMatch getByValue(String value) {
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

    StructuralMisMatch(String match) {
        this.mMatch = match;
    }
}
