
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
import de.prototypefund.en16931.type.MisMatch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a syntax node representing the Semantic in the file format. In
 * general, XML nodes, but might be EDIFACT as well.
 */
public abstract class NodeSyntax {

    private static final Logger LOG = LoggerFactory.getLogger(NodeSyntax.class);
    protected NodeSemantic mSemanticParent;
    static TreeMap<String, NodeSyntax> allSyntaxNodes = null;
    static Map<String, List<NodeSyntax>> duplicatePathList = null;
    protected String mPath; // usually the XPath
    protected MisMatch[] mMisMatches;
    protected String mRules;
    protected Boolean mIsXml;
    Boolean mERROR_FixAlreadyTaken = Boolean.FALSE;
    Boolean mERROR_FixUnavailable = Boolean.FALSE;

    public NodeSyntax(String path, NodeSemantic semanticParent, Boolean isXml) {
        try {
            assert semanticParent != null;
            mIsXml = isXml;
            path = path.replaceAll(LEADING_TRAILING_WHITESPACES, "");
            mSemanticParent = semanticParent;
            mSemanticParent.addSyntaxRepresentation(this);
            if (allSyntaxNodes == null) {
                allSyntaxNodes = new TreeMap<String, NodeSyntax>();
            }
            String duplicatePath = path + "_1";
            List<NodeSyntax> duplicates = null;
            if (!allSyntaxNodes.containsKey(duplicatePath)) {
                if (!allSyntaxNodes.containsKey(path)) {
                    allSyntaxNodes.put(path, this);
                    mPath = path;
                } else { // collison remove the previous path and add DUPLICATE_SUFFIX
                    NodeSyntax firstNode = allSyntaxNodes.remove(path);
                    firstNode.mPath = duplicatePath;
                    allSyntaxNodes.put(duplicatePath, firstNode);
                    allSyntaxNodes.put(path + "_2", this);
                    mPath = path + "_2";
                    // add all duplicates in a List for later error processing
                    duplicates = new ArrayList<NodeSyntax>(2);
                    duplicates.add(firstNode);
                    duplicates.add(this);
                    if (duplicatePathList == null) {
                        duplicatePathList = new HashMap<String, List<NodeSyntax>>();
                    }
                    duplicatePathList.put(path, duplicates);
                }
            } else { // if the xpatch was already MORE THAN ONCE as ID before
                String pathDuplicateId = null;
                for (int i = 3; i < allSyntaxNodes.size(); i++) {
                    pathDuplicateId = path + "_" + i;
                    if (!allSyntaxNodes.containsKey(pathDuplicateId)) {
                        allSyntaxNodes.put(pathDuplicateId, this);
                        mPath = pathDuplicateId;
                        break;
                    }
                }
                // add all duplicates in a List for later error processing
                duplicates = duplicatePathList.get(path);
                duplicates.add(this);
                duplicatePathList.put(path, duplicates);
//                Map<String, String> fixes = mAllFixes.get(tableId);
//                if (fixes.containsKey(path)) { // but a fix is available
//                    String fix = fixes.get(path);
//                    if (!allSyntaxNodes.containsKey(fix)) {
//                        allSyntaxNodes.put(fix, this); // apply fix
//                    } else {
//                        mWARNING_FixAlreadyTaken = Boolean.TRUE;
//                        SpecificationFixes.hasError = Boolean.TRUE;
//                        LOG.error("\n\nERROR: Fix: '" + fix + "', already taken for path '" + path + "'\n");
//                        path = SpecificationFixes.getAlternativeID(path);
//                        allSyntaxNodes.put(path, this);
//                    }
//                } else {
//                    mWARNING_FixUnavailable = Boolean.TRUE;
//                    SpecificationFixes.hasError = Boolean.TRUE;
//                    NodeSyntax x = allSyntaxNodes.get(path);
//                    NodeSemantic s = x.getSemanticNode();
//                    LOG.error("\n\nERROR: Existing Path: '" + path + "'\n");
//                    LOG.error("       Related semanticNode ID: '" + s.getId() + "'\n");
//                    LOG.error("       Related Syntax Rules: '" + x.getRules() + "'\n");
//                    path = SpecificationFixes.getAlternativeID(path);
//                    allSyntaxNodes.put(path, this);
//                }

            }
        } catch (Throwable t) {
            LOG.error("Problem in SyntaxNode creation!", t);
        }
    }

    abstract public String getCardinality();

    public NodeSemantic getSemanticNode() {
        return mSemanticParent;
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
            LoggerFactory.getLogger(NodeSyntax.class.getName()).error(e.getMessage(), e);
        }
    }

    public String getRules() {
        return mRules;
    }

    public void setRules(String rules) {
        mRules = rules;
    }

    public void setPath(String xpath) {
        mPath = xpath;
    }

    public String getPath() {
        return mPath;
    }

    /**
     * This enum contains all table header row label of all syntax
     */
    public static enum SyntaxHeading {

        /**
         * Path within the syntax file (e.g. XPath within XML file)
         */
        PATH("Path"),
        /**
         * Only used at en16931-3-2 and en16931-3-3 Typ — A = Attribut — C =
         * Verbund — E = Element — G = Aggregat — S = Segment
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
         * Only used at en16931-3-4 (EDIFACT) informative table heading
         */
        NAME("Name"),
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

        SyntaxHeading(String label) {
            this.mLabel = label;
        }
    }

    abstract protected StringBuilder addSpecificString(StringBuilder syntax);

    @Override
    public String toString() {
        StringBuilder syntax = new StringBuilder();
        try {
            if (mIsXml) {
                syntax.append("\t\t<xml path=\"" + mPath + "\"");
            } else {
                syntax.append("\t\t<edifact path=\"" + mPath + "\"");
            }

            addSpecificString(syntax);

            if (getCardinality() != null) {
                syntax.append(" card=\"" + getCardinality() + "\"");
            }
            if (mMisMatches != null && mMisMatches.length > 0) {
                syntax.append(" mismatches=\"");
                for (int i = 0; mMisMatches.length > i; i++) {
                    if (mMisMatches[i] == null) {
                        LOG.info("INVALID CONTENT: See 'Match' column of Semantic ID '" + this.getSemanticNode().getId() + "'!\n");
                        syntax.append("ERROR!\"");
                        break;
                    }
                    syntax.append(mMisMatches[i].getValue());
                    if (mMisMatches.length > i + 1) {
                        syntax.append(" ");
                    }
                }
                syntax.append("\"");
            }
            if (mRules != null && !mRules.isEmpty()) {
                syntax.append(">");
                syntax.append(mRules);
                if (mIsXml) {
                    syntax.append("</xml>");
                } else {
                    syntax.append("</edifact>");
                }
            } else {
                syntax.append("/>");
            }
        } catch (Throwable e) {
            LoggerFactory.getLogger(NodeXml.class.getName()).error(e.getMessage(), e);
        }
        return syntax.toString();
    }

    public String toSubString() {
        StringBuilder syntax = new StringBuilder();
        try {
            if (mIsXml) {
                syntax.append("\t\t<xml path=\"" + mPath + "\"");
            } else {
                syntax.append("\t\t<edifact path=\"" + mPath + "\"");
            }

            if (getCardinality() != null) {
                syntax.append(" card=\"" + getCardinality() + "\"");
            }
            syntax.append("/>");

        } catch (Throwable e) {
            LoggerFactory.getLogger(NodeXml.class.getName()).error(e.getMessage(), e);
        }
        return syntax.toString();
    }
}
