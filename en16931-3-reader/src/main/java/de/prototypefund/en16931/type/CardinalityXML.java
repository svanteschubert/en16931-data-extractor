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
import org.slf4j.LoggerFactory;

/**
 * This enum describes the CardinalityXML type being used in EN16931
 */
public enum CardinalityXML {

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
    private static final Map<String, CardinalityXML> mCardMap = new HashMap<String, CardinalityXML>();

    static {
        for (CardinalityXML c : values()) {
            mCardMap.put(c.getValue(), c);
        }
    }

    public static CardinalityXML getByValue(String value) {
        CardinalityXML c = mCardMap.get(value);
        if (c == null) {
            LoggerFactory.getLogger(CardinalityXML.class.getName()).error("There is no cardinality for '" + value + "'!\n");
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

    CardinalityXML(String cardinality) {
        this.mCardinality = cardinality;
    }
}
