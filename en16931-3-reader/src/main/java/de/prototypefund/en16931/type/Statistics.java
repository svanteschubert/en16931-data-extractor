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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * This class creates a statistic from the counting of the types during an
 * extraction run, e.g. test.
 */
public class Statistics {

    private static Logger LOG = LoggerFactory.getLogger(Statistics.class.getName());

    /** Logs for each data type its occurrences.*/
    static public void log() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n\n**** DATA USAGE STATISTIC:\n");
        sb.append("****************************************************\n");
        log(sb.append("* Semantic Data Type"), DataType.getStatistic());
        log(sb.append("* Mismatch Cardinality"), MisMatchCardinality.getStatistic());
        log(sb.append("* Mismatch Data Type"), MisMatchDatatype.getStatistic());
        log(sb.append("* Mismatch Semantic"), MisMatchSemantic.getStatistic());
        log(sb.append("* Mismatch Structural"), MisMatchStructural.getStatistic());
        log(sb.append("* Types of XML"), Type.getStatistic());
        log(sb.append("* Cardinalities used by XML"), CardinalityXML.getStatistic());
        log(sb.append("* Cardinalities used by EDIFACT"), CardinalityEdifact.getStatistic());
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
