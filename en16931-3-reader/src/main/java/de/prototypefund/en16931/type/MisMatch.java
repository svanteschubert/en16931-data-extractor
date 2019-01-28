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

import org.slf4j.LoggerFactory;

/**
 * Mismatch on either semantic, structure or cardinality level. Exposing
 * discrepancies.
 */
public interface MisMatch {

    /**
     * @return the MisMatch value
     */
    String getValue();

    /**
     * Creates a single mismatch from its EN16931 value, e.g. "STR-1"
     */
    public static MisMatch createMisMatch(String match) {
        MisMatch misMatch = null;
        if (match != null && !match.isEmpty()) {
            String ID = match.substring(0, 3);
            switch (ID) {
                case "CAR":
                    misMatch = MisMatchCardinality.getByValue(match);
                    break;
                case "SEM":
                    misMatch = MisMatchSemantic.getByValue(match);
                    break;
                case "STR":
                    misMatch = MisMatchStructural.getByValue(match);
                    break;
                case "SYN":
                    misMatch = MisMatchDatatype.getByValue(match);
                    break;
                default:
                    LoggerFactory.getLogger(MisMatch.class.getName()).error("There is no mismatch for '" + match + "'!\n");
            }
        }
        return misMatch;
    }

}
