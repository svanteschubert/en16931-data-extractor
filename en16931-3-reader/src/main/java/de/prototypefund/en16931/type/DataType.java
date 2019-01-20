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
 *
 * This enum contains all possible data types of the semantic part of the table within the EN 16931 specification.
 */
public enum DataType {

    /**
     * Data Types
     * <code>
     * — A = Betrag
     * — B = Binärobjekt
     * — C = Code
     * — D = Datum
     * — I = Kennung
     * — M = numerisch
     * — N = normalisierter String
     * — P = Prozentsatz
     * — Q = Menge
     * — S = String
     * — T = Text
     * — U = Einheitspreisbetrag
     * </code>
     */
    AMOUNT("A"),
    BINARYOBJECT("B"),
    CODE("C"),
    DATE("D"),
    ID("I"),
    NUMERIC("M"),
    NORMALISED_STRING("N"),
    PERCENT("P"),
    QUANTITY("Q"),
    STRING("S"),
    TEXT("T"),
    UNIT_PRICE_AMMOUNT("U");

    private static final Map<String, DataType> mTypeMap = new HashMap<String, DataType>();

    static {
        for (DataType c : values()) {
            mTypeMap.put(c.getValue(), c);
        }
    }

    public static DataType getByValue(String value) {
        return mTypeMap.get(value);
    }

    private final String mDataType;

    /**
     * @return the DataType
     */
    public String getValue() {
        return mDataType;
    }

    DataType(String dataType) {
        this.mDataType = dataType;
    }
}
