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
public class Statistics {

    private static Logger LOG = LoggerFactory.getLogger(Statistics.class.getName());
    // one global map for each data type
    private static SortedMap<String, Integer> mSemanticDataTypes = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mMismatchCardinality = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mMismatchDataTypes = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mMismatchSemantic = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mMismatchStructural = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mTypesOfXML = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mCardinalitesXML = new TreeMap<String, Integer>();
    private static SortedMap<String, Integer> mCardinalitesEDIFACT = new TreeMap<String, Integer>();


   /** Logs for each data type its occurrences and resets the counter.
    Usually being called after parsing a table. */
    static public void table() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n\n**** TABLE DATATYPE USAGE STATISTIC:\n");
        sb.append("----------------------------------------------------\n");

        SortedMap<String, Integer> m = DataType.getStatistic();
        log(sb.append("* Semantic Data Type"), m);
        if(m != null){
            mSemanticDataTypes.putAll(m);
            m.clear();
        }

        m = MisMatchCardinality.getStatistic();
        log(sb.append("* Mismatch Cardinality"), m);
        if(m != null){
            mMismatchCardinality.putAll(m);
            m.clear();
        }

        m = MisMatchDatatype.getStatistic();
        log(sb.append("* Mismatch Data Type"), MisMatchDatatype.getStatistic());
        if(m != null){
            mMismatchDataTypes.putAll(m);
            m.clear();
        }

        m = MisMatchSemantic.getStatistic();
        log(sb.append("* Mismatch Semantic"), MisMatchSemantic.getStatistic());
        if(m != null){
            mMismatchSemantic.putAll(m);
            m.clear();
        }

        m = MisMatchStructural.getStatistic();
        log(sb.append("* Mismatch Structural"), m);
        if(m != null){
            mMismatchStructural.putAll(m);
            m.clear();
        }

        m = Type.getStatistic();
        log(sb.append("* Types of XML"), m);
        if(m != null){
            mTypesOfXML.putAll(m);
            m.clear();
        }

        m = CardinalityXML.getStatistic();
        log(sb.append("* Cardinalities used by XML"), m);
        if(m != null){
            mCardinalitesXML.putAll(m);
            m.clear();
        }

        m = CardinalityEdifact.getStatistic();
        log(sb.append("* Cardinalities used by EDIFACT"), m);
        if(m != null){
            mCardinalitesEDIFACT.putAll(m);
            m.clear();
        }

        sb.append("----------------------------------------------------\n");
        sb.append("****");
        LOG.info(sb.toString());
    }

    /** Logs for each data type its occurrences during the extraction run.
     Best called after a test of various documents.*/
    static public void global() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n\n**** GLOABL DATATYPEUSAGE STATISTIC:\n");
        sb.append("****************************************************\n");
        log(sb.append("* Semantic Data Type"), mSemanticDataTypes);
        log(sb.append("* Mismatch Cardinality"), mMismatchCardinality);
        log(sb.append("* Mismatch Data Type"), mMismatchDataTypes);
        log(sb.append("* Mismatch Semantic"), mMismatchSemantic);
        log(sb.append("* Mismatch Structural"), mMismatchStructural);
        log(sb.append("* Types of XML"), mTypesOfXML);
        log(sb.append("* Cardinalities used by XML"), mCardinalitesXML);
        log(sb.append("* Cardinalities used by EDIFACT"), mCardinalitesEDIFACT);
        sb.append("****************************************************\n");
        sb.append("****");
        LOG.info(sb.toString());
    }

    static private void log(StringBuilder sb, SortedMap<String, Integer> statistic) {
        sb.append(": ");
        if (statistic != null) {
            Set<String> keys = statistic.keySet();
            for (String key : keys) {
                sb.append("\n\t");
                sb.append(key).append(" : ").append(statistic.get(key));
            }
        } else {
            sb.append("\n\tNONE");
        }
        sb.append("\n\n");
    }
}
