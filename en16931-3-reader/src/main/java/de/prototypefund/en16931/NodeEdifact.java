
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
package de.prototypefund.en16931;

import static de.prototypefund.en16931.NodeSemantic.LEADING_TRAILING_WHITESPACES;
import de.prototypefund.en16931.type.CardinalityEdifact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an XML syntax node
 */
public class NodeEdifact extends NodeSyntax {

    private static final Logger LOG = LoggerFactory.getLogger(NodeEdifact.class);
    private CardinalityEdifact mCardinality;
    private String mName; // only used by 16931-3-4 EDIFCAT informative table heading

    public NodeEdifact(String path, NodeSemantic semanticParent) {
        super(path, semanticParent, Boolean.FALSE);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCardinality() {
        String c = null;
        if (mCardinality != null) {
            c = mCardinality.getValue();
        }
        return c;
    }

    public CardinalityEdifact getCardinalityEdifact() {
        return mCardinality;
    }

    public void setCardinalityEdifact(String cardinality) {
        mCardinality = CardinalityEdifact.getByValue(cardinality.replaceAll(LEADING_TRAILING_WHITESPACES, ""));
    }

    protected StringBuilder addSpecificString(StringBuilder syntax) {
        if (getName() != null) {
            syntax.append(" name=\"" + getName() + "\"");
        }
        return syntax;
    }
}
