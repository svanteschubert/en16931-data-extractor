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

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * This class creates a statistic from the counting of the types during an
 * extraction run, e.g. test.
 */
public class TypeStatistic {

    private static Logger LOG = LoggerFactory.getLogger(TypeStatistic.class.getName());
    // one global map for each data type
    private static SortedMap<String, Integer> mSemanticDataTypes = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mMismatchCardinality = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mMismatchDataTypes = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mMismatchSemantic = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mMismatchStructural = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mTypesOfXml = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mTypesOfUblXml = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mCardinalites = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mCardinalitesSemantic = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mCardinalitesEdifact = new TreeMap<String, Integer>();

    /**
     * Logs for each data type its occurrences and resets the counter. Usually
     * being called after parsing a table.
     */
    static public void table(String tableTitle, Boolean isXML, Boolean isUBL) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--------------------------------------------------------------------------------------------------------\n");
        sb.append("TABLE DATATYPE STATISTIC:\n\t").append(tableTitle).append("\n");
        sb.append("--------------------------------------------------------------------------------------------------------\n");

        SortedMap<String, Integer> m = SemanticDataType.getStatistic();
        log(sb.append("* Semantic Data Type"), m);
        if (m != null) {
            mSemanticDataTypes.putAll(m);
            m.clear();
            SemanticDataType.clearStatistic();
        }

        m = CardinalitySemantic.getStatistic();
        log(sb.append("* Cardinalities used by Semantic"), m);
        if (m != null) {
            mCardinalitesSemantic.putAll(m);
            m.clear();
            CardinalitySemantic.clearStatistic();
        }

        m = MisMatchCardinality.getStatistic();
        log(sb.append("* Mismatch Cardinal."), m);
        if (m != null) {
            mMismatchCardinality.putAll(m);
            m.clear();
            MisMatchCardinality.clearStatistic();
        }

        m = MisMatchDatatype.getStatistic();
        log(sb.append("* Mismatch Data Type"), MisMatchDatatype.getStatistic());
        if (m != null) {
            mMismatchDataTypes.putAll(m);
            m.clear();
            MisMatchDatatype.clearStatistic();
        }

        m = MisMatchSemantic.getStatistic();
        log(sb.append("* Mismatch Semantic"), MisMatchSemantic.getStatistic());
        if (m != null) {
            mMismatchSemantic.putAll(m);
            m.clear();
            MisMatchSemantic.clearStatistic();
        }

        m = MisMatchStructural.getStatistic();
        log(sb.append("* Mismatch Structural"), m);
        if (m != null) {
            mMismatchStructural.putAll(m);
            m.clear();
            MisMatchStructural.clearStatistic();
        }

        m = SyntaxType.getStatistic();

        if (isUBL) {
            m = UblDataType.getStatistic();
            log(sb.append("* Types of UBL XML"), m);
            if (m != null) {
                mTypesOfUblXml.putAll(m);
                m.clear();
                UblDataType.clearStatistic();
            }
        } else {
            if (isXML) {
                log(sb.append("* Types of CII XML Syntax"), m);
            } else {
                log(sb.append("* Types of EDIFACT Syntax"), m);
            }
            if (m != null) {
                mTypesOfXml.putAll(m);
                m.clear();
                SyntaxType.clearStatistic();
            }
        }

        m = CardinalityXml.getStatistic();
        if (isUBL) {
            log(sb.append("* Cardinalities used by UBL XML"), m);
        } else {
            log(sb.append("* Cardinalities used by CII XML"), m);
        }
        if (m != null) {
            mCardinalites.putAll(m);

            m.clear();
            CardinalityXml.clearStatistic();
        }
        if (!isXML) {
            m = CardinalityEdifact.getStatistic();
            log(sb.append("* Cardinalities used by EDIFACT"), m);
            if (m != null) {
                mCardinalitesEdifact.putAll(m);
                m.clear();
                CardinalityEdifact.clearStatistic();
            }
        }
        LOG.info(sb.toString());
        sb.append("\n----------------------------------------------------\n");
    }

    /**
     * Logs for each data type its occurrences during the extraction run. Best
     * called after a test of various documents.
     */
    static public void allDocuments() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n+++ GLOABL DATATYPEUSAGE STATISTIC:\n");
        sb.append("++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        log(sb.append("* Semantic Data Type"), mSemanticDataTypes);
        log(sb.append("* Mismatch Cardinal."), mMismatchCardinality);
        log(sb.append("* Mismatch Data Type"), mMismatchDataTypes);
        log(sb.append("* Mismatch Semantic"), mMismatchSemantic);
        log(sb.append("* Mismatch Structural"), mMismatchStructural);
        log(sb.append("* Types of XML"), mTypesOfXml);
        log(sb.append("* Cardinalities used by Semantic and XML"), mCardinalites);
        log(sb.append("* Cardinalities used by EDIFACT"), mCardinalitesEdifact);
        sb.append("++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        LOG.info(sb.toString());
    }

    static private void log(StringBuilder sb, SortedMap<String, Integer> statistic) {
        sb.append(": ");
        if (statistic != null && !statistic.isEmpty()) {
            Set<String> keys = statistic.keySet();
            int total = 0;
            int current = 0;
            for (String key : keys) {
                sb.append("\n\t");
                current = statistic.get(key);
                sb.append(key).append(" : ").append(current);
                total += current;
            }
            sb.append("\n\t--------");
            sb.append("\n\tTOTAL: " + total);
        } else {
            sb.append("\tNONE");
        }
        sb.append("\n\n");
    }
}
