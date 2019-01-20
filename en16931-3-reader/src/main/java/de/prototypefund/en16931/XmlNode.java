
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

import static de.prototypefund.en16931.SemanticNode.LEADING_TRAILING_WHITESPACES;
import de.prototypefund.en16931.type.Cardinality;
import de.prototypefund.en16931.type.MisMatch;
import de.prototypefund.en16931.type.XmlType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents either a Business Group (BG) or Business Term (BT) from the
 * eInvoice EN16931 specification.
 */
public class XmlNode {

    private static final Logger LOG = LoggerFactory.getLogger(XmlNode.class);
    private SemanticNode mSemanticParent;
    static TreeMap<String, XmlNode> allXmlNodes = null;
    static Map<String, List<XmlNode>> duplicateXPathList = null;
    private XmlType mXmlType;
    private String mXpath;
    private Cardinality mCardinality;
    private MisMatch[] mMisMatches;
    private String mRules;
    Boolean mERROR_FixAlreadyTaken = Boolean.FALSE;
    Boolean mERROR_FixUnavailable = Boolean.FALSE;

    public XmlNode(String xpath, SemanticNode semanticParent) {
        try {

            assert semanticParent != null;
            xpath = xpath.replaceAll(LEADING_TRAILING_WHITESPACES, "");
            mSemanticParent = semanticParent;
            mSemanticParent.addXMLRepresentation(this);
            if (allXmlNodes == null) {
                allXmlNodes = new TreeMap<String, XmlNode>();
            }
            String duplicateXPath = xpath + "_1";
            List<XmlNode> duplicates = null;
            if (!allXmlNodes.containsKey(duplicateXPath)) {
                if(!allXmlNodes.containsKey(xpath)){
                    allXmlNodes.put(xpath, this);
                    mXpath = xpath;
                }else{ // collison remove the previous xpath and add DUPLICATE_SUFFIX
                    XmlNode firstNode = allXmlNodes.remove(xpath);
                    firstNode.mXpath = duplicateXPath;
                    allXmlNodes.put(duplicateXPath, firstNode);
                    allXmlNodes.put(xpath + "_2", this);
                    mXpath = xpath + "_2";
                    // add all duplicates in a List for later error processing
                    duplicates = new ArrayList<XmlNode>(2);
                    duplicates.add(firstNode);
                    duplicates.add(this);
                    if(duplicateXPathList == null){
                        duplicateXPathList = new HashMap<String, List<XmlNode>>();
                    }
                    duplicateXPathList.put(xpath, duplicates);
                }
            } else { // if the xpatch was already MORE THAN ONCE as ID before
                String xpathDuplicateId = null;
                for(int i = 3;i<allXmlNodes.size();i++){
                    xpathDuplicateId = xpath + "_" + i;
                    if(!allXmlNodes.containsKey(xpathDuplicateId)){
                        allXmlNodes.put(xpathDuplicateId, this);
                        mXpath = xpathDuplicateId;
                        break;
                    }
                }
                // add all duplicates in a List for later error processing
                duplicates = duplicateXPathList.get(xpath);
                duplicates.add(this);
                duplicateXPathList.put(xpath, duplicates);
//                Map<String, String> fixes = mAllFixes.get(tableId);
//                if (fixes.containsKey(xpath)) { // but a fix is available
//                    String fix = fixes.get(xpath);
//                    if (!allXmlNodes.containsKey(fix)) {
//                        allXmlNodes.put(fix, this); // apply fix
//                    } else {
//                        mWARNING_FixAlreadyTaken = Boolean.TRUE;
//                        SpecificationFixes.hasError = Boolean.TRUE;
//                        LOG.error("\n\nERROR: Fix: '" + fix + "', already taken for xpath '" + xpath + "'\n");
//                        xpath = SpecificationFixes.getAlternativeID(xpath);
//                        allXmlNodes.put(xpath, this);
//                    }
//                } else {
//                    mWARNING_FixUnavailable = Boolean.TRUE;
//                    SpecificationFixes.hasError = Boolean.TRUE;
//                    XmlNode x = allXmlNodes.get(xpath);
//                    SemanticNode s = x.getSemanticNode();
//                    LOG.error("\n\nERROR: Existing XPath: '" + xpath + "'\n");
//                    LOG.error("       Related semanticNode ID: '" + s.getId() + "'\n");
//                    LOG.error("       Related XML Rules: '" + x.getRules() + "'\n");
//                    xpath = SpecificationFixes.getAlternativeID(xpath);
//                    allXmlNodes.put(xpath, this);
//                }

            }
        } catch (Throwable t) {
            LOG.error("Problem in XmlNode creation!", t);
        }
    }

    public SemanticNode getSemanticNode() {
        return mSemanticParent;
    }

    public XmlType getType() {
        return mXmlType;
    }

    public void setType(String type) {
        mXmlType = XmlType.getByValue(type.replaceAll(LEADING_TRAILING_WHITESPACES, ""));
    }

    public Cardinality getCardinality() {
        return mCardinality;
    }

    public void setCardinality(String cardinality) {
        mCardinality = Cardinality.getByValue(cardinality.replaceAll(LEADING_TRAILING_WHITESPACES, ""));
    }

    public MisMatch[] getMisMatches() {
        return mMisMatches;
    }

    public void setMisMatch(String match) {
        try {
            if (match.contains(",")) {
                StringTokenizer st = new StringTokenizer(match, ",");
                int count = st.countTokens();
                if (mMisMatches == null) {
                    mMisMatches = new MisMatch[count];
                }
                for (int i = 0; st.hasMoreElements(); i++) {
                    mMisMatches[i] = MisMatch.createMisMatch(st.nextToken().replaceAll(LEADING_TRAILING_WHITESPACES, ""));
                }
            } else {
                if (mMisMatches == null) {
                    mMisMatches = new MisMatch[1];
                }
                mMisMatches[0] = MisMatch.createMisMatch(match.replaceAll(LEADING_TRAILING_WHITESPACES, ""));
            }
        } catch (Throwable e) {
            LoggerFactory.getLogger(XmlNode.class.getName()).error(e.getMessage(), e);
        }
    }

    public String getRules() {
        return mRules;
    }

    public void setRules(String rules) {
        mRules = rules;
    }

    public void setXPath(String xpath) {
        mXpath = xpath;
    }

    public String getXPath() {
        return mXpath;
    }

    /**
     * This enum contains all table header row label of the semantic object
     */
    public static enum XmlHeading {

        /**
         * Path within XML grammar
         */
        PATH("Path"),
        /**
         * Typ of XML — A = Attribut — C = Verbund — E = Element — G = Aggregat
         * — S = Segment
         */
        TYPE("Type"),
        /**
         * Cardinality: Also known as multiplicity is used to indicate if an
         * information element (or group of information elements) is mandatory
         * or conditional, and if it is repeatable. The cardinality shall always
         * be analysed in the context of where the information element is used.
         * Example: the Payee Name is mandatory in the core invoice model, but
         * only when a Payee is stated and is relevant.
         */
        CARD("Card."),
        /**
         * Level of Coverage (see EN 16931-1 Table 4.5)
         */
        MATCH("Match"),
        /**
         * Remarks on Relationship
         */
        RULES("Rules");

        private final String mLabel;

        /**
         * @return the label of the header row of a semantic object.
         */
        public String getLabel() {
            return mLabel;
        }

        XmlHeading(String label) {
            this.mLabel = label;
        }
    }

    @Override
    public String toString() {
        StringBuilder xml = new StringBuilder();
        try {
            xml.append("\t\t<xml path=\"" + mXpath + "\"");
            if (mXmlType != null) {
                xml.append(" type=\"" + mXmlType + "\"");
            }
            if (mCardinality != null) {
                xml.append(" card=\"" + mCardinality + "\"");
            }
            if (mMisMatches != null && mMisMatches.length > 0) {
                xml.append(" mismatches=\"");
                for (int i = 0; mMisMatches.length > i; i++) {
                    if(mMisMatches[i] == null){
                        LOG.info("INVALID CONTENT: See 'Match' column of Semantic ID '" + this.getSemanticNode().getId() + "'!\n");
                        xml.append("ERROR!\"");
                        break;
                    }
                    xml.append(mMisMatches[i].getValue());
                    if (mMisMatches.length > i + 1) {
                        xml.append(" ");
                    }
                }
                xml.append("\"");
            }
            if (mRules != null && !mRules.isEmpty()) {
                xml.append(">");
                xml.append(mRules);
                xml.append("</xml>");
            } else {
                xml.append("/>");
            }
        } catch (Throwable e) {
            LoggerFactory.getLogger(XmlNode.class.getName()).error(e.getMessage(), e);
        }
        return xml.toString();
    }

    public String toSubString() {
        StringBuilder xml = new StringBuilder();
        try {
            xml.append("\t\t<xml path=\"" + mXpath + "\"");

            if (mCardinality != null) {
                xml.append(" card=\"" + mCardinality + "\"");
            }
            xml.append("/>");

        } catch (Throwable e) {
            LoggerFactory.getLogger(XmlNode.class.getName()).error(e.getMessage(), e);
        }
        return xml.toString();
    }
}
