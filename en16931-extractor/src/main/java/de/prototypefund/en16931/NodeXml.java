
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
package de.prototypefund.en16931;

import static de.prototypefund.en16931.NodeSemantic.LEADING_TRAILING_WHITESPACES;
import de.prototypefund.en16931.type.CardinalityXml;
import de.prototypefund.en16931.type.SyntaxType;
import de.prototypefund.en16931.type.Type;

/**
 * Represents an XML syntax node
 */
public class NodeXml extends NodeSyntax {

    protected Type mType; // not used for EDIFACT
    private CardinalityXml mCardinality;

    public NodeXml(String path, NodeSemantic semanticParent) {
        super(path, semanticParent, Boolean.TRUE);
    }

    public Type getType() {
        return mType;
    }

    public void setType(String type) {
        mType = SyntaxType.getByValue(type.replaceAll(LEADING_TRAILING_WHITESPACES, ""), this.getSemanticNode().getId());
    }

    public String getCardinality() {
        String c = null;
        if (mCardinality != null) {
            c = mCardinality.getValue();
        }
        return c;
    }

    public CardinalityXml getCardinalityXml() {
        return mCardinality;
    }

    public void setCardinalityXml(String cardinality) {
        mCardinality = CardinalityXml.getByValue(cardinality.replaceAll(LEADING_TRAILING_WHITESPACES, ""), this.getSemanticNode().getId());
    }

    protected StringBuilder addSpecificString(StringBuilder syntax) {
        if (getType() != null) {
            syntax.append(" type=\"" + getType() + "\"");
        }
        return syntax;
    }
}
