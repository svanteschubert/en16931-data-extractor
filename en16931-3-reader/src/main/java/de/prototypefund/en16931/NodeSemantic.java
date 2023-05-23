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

import static de.prototypefund.en16931.OdtTableExtraction.mMultiHyphenDiff;
import static de.prototypefund.en16931.OdtTableExtraction.mMultiHyphenSame;
import static de.prototypefund.en16931.OdtTableExtraction.mSyntaxBindingCounter;
import static de.prototypefund.en16931.OdtTableExtraction.mSyntaxBindingLastFileName;
import de.prototypefund.en16931.type.CardinalitySemantic;
import de.prototypefund.en16931.type.MisMatch;
import de.prototypefund.en16931.type.NumberAwareStringComparator;
import de.prototypefund.en16931.type.SemanticDataType;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents either a Business Group (BG) or Business Term (BT) from the
 * eInvoice EN16931 specification.
 */
public class NodeSemantic {

    private static final Logger LOG = LoggerFactory.getLogger(NodeSemantic.class);
    private static final String BUSINESS_GROUP_PREFIX = "BG-";
    private static final String BUSINESS_TERM_PREFIX = "BT-";
    private static final String ODT_SUFFIX = ".odt";
    static final String LEADING_TRAILING_WHITESPACES = "(^\\h*)|(\\h*$)";
    static final private String INVALID_FILE_CHARACTERS = "[\\\\/:*?\"<>|]";
    private Boolean isBusinessGroup = null;
    static TreeMap<String, NodeSemantic> allSemanticNodes = null;
    List<NodeSyntax> syntaxRepresentations = null;
    private String mID = null;
    private String mBusinessTerm = null;
    private CardinalitySemantic mCardinality = null;
    private Integer mLevel = null;
    private SemanticDataType mDataType = null;
    private String mDescription = null;
    private String mTableId = null;
    private static final String mERROR_ID = "ID ALREADY TAKEN";
    Boolean mWARNING_FixAlreadyTaken = Boolean.FALSE;
    Boolean mWARNING_FixUnavailable = Boolean.FALSE;
    // Following properites are for naming the new output directories for the various output files
    private static String FILE_SUFFIX__SUBSET = "_SUBSET";
    private static String FILE_SUFFIX__SEMANTIC = "_SEMANTIC";
    private static String SAME_SEMANTIC_DIR_NAME = "_SAME_SEMANTIC";
    private static String SAME_BINDING_PREFIX = "_SAME_BINDING";
    private static String BINDING_TYPE_INVOICE = "_INV__";
    private static String BINDING_TYPE_CREDIT_NOTE = "_CN__";
    private static final Integer CAR_1 = 1;
    private static final Integer CAR_2 = 2;
    private static final Integer CAR_3 = 3;
    private static final Integer CAR_4 = 4;

    public NodeSemantic(String id, String tableId) {
        try {
            mTableId = tableId;
            // check if ID is empty
            if (!id.replaceAll(LEADING_TRAILING_WHITESPACES, "").isEmpty()) {
                if (id.startsWith(BUSINESS_TERM_PREFIX)) {
                    isBusinessGroup = Boolean.FALSE;
                    testID(id);
                } else if (id.startsWith(BUSINESS_GROUP_PREFIX)) {
                    isBusinessGroup = Boolean.TRUE;
                    testID(id);
                } else {
                    LOG.error("ERROR: ID of Semantic object have to start, either with 'BT-' or 'BG-'! The ID was '" + id + "'!");
                }
                if (id.contains("–")) {
                    if (mMultiHyphenDiff == null) {
                        mMultiHyphenDiff = new ArrayList<>();
                    }
                    mMultiHyphenDiff.add(id);
                    id = id.replace("–", "-"); // fixing hyphen problem so all ID have similar structure
                } else if (!id.contains("–") && id.contains("-")) {
                    int count = countChar(id, '-');
                    if (count > 1) {
                        if (mMultiHyphenSame == null) {
                            mMultiHyphenSame = new ArrayList();
                        }
                        mMultiHyphenSame.add(id);

                    }
                }
            } else {
                LOG.error("ERROR: ID of semantic object shall not be empty!");
            }
            if (allSemanticNodes == null) {
                allSemanticNodes = new TreeMap<>(new NumberAwareStringComparator());
            }
            // new semantic ID
            if (!allSemanticNodes.containsKey(id)) {
                allSemanticNodes.put(id, this);
                mID = id;
            } else { // duplicate sematic ID
                NodeSemantic s = allSemanticNodes.get(id);
                mWARNING_FixUnavailable = Boolean.TRUE;
                LOG.error("ERROR: Duplicated SemanticNode ID: " + s.getId() + "\n");
                LOG.info("\t\twithin table: '" + mTableId + "'\n");
                LOG.info("\t\twith business Term: '" + s.getBusinessTerm() + "'\n");
                if (s.mDescription != null) {
                    LOG.info("\t\twith description: '" + s.mDescription + "'\n");
                }
                for (NodeSyntax x : s.syntaxRepresentations) {
                    LOG.info("\t\t\tSyntax child: '" + x.getPath() + "'\n");
                    if (x.getRules() != null) {
                        LOG.info("\t\t\tRules: '" + x.getRules() + "'\n");
                    }
                }
                LOG.info("\n");
                allSemanticNodes.put(id, this);
            }
        } catch (Throwable t) {
            try {
                ByteArrayOutputStream out1 = new ByteArrayOutputStream();
                PrintStream out2 = new PrintStream(out1);
                t.printStackTrace(out2);
                String message = out1.toString("UTF8");
                LoggerFactory.getLogger(NodeSemantic.class.getName()).error("ERROR: " + message, t);
                out1.close();
                out2.close();
            } catch (IOException ex) {
                LoggerFactory.getLogger(NodeSemantic.class.getName()).error(null, ex);
            }
        }
    }

    private void testID(String id) {
        String numberCandidate = id.substring(BUSINESS_TERM_PREFIX.length(), id.length());
        numberCandidate = numberCandidate.replace("-", "1").replace("–", "2"); // 16931-3-4 uses two different hyphen in its ID "BT-18–1"
        try {
            Integer.parseInt(numberCandidate);
        } catch (NumberFormatException e) {
            LOG.error("ERROR: Semantic ID is not as as usual. Expected is a 'BT-' or 'BG-' with numbers and further '-', but the ID was '" + id + "'!");
        }
    }

    /**
     * Adds an syntax representation node of this semantic node
     */
    public void addSyntaxRepresentation(NodeSyntax node) {
        if (syntaxRepresentations == null) {
            syntaxRepresentations = new ArrayList<NodeSyntax>(1);
        }
        syntaxRepresentations.add(node);
    }

    public Boolean isBusinessGroup() {
        return isBusinessGroup;
    }

    public String getId() {
        return mID;
    }

    public void setBusinessTerm(String bt) {
        String testString = bt.replaceAll("\\s+", " ").replaceAll(LEADING_TRAILING_WHITESPACES, "");
        if (!testString.equals(bt)) {
            LOG.warn("WARNING: " + getId() + " 'BT description' has whitespace problems:"
                    + "\n\tWith visible whitespace (space = . and Java abbreviations \\t,\\r,\\f,\\n):\n\t\t"
                    + "\"" + bt.replaceAll(" ", ".").replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t").replaceAll("\r", "\\\\r").replaceAll("\f", "\\\\f") + "\"\n");
            LOG.warn("\tinstead of:\n\t\t\"" + testString + "\"\n\n");
        }
        mBusinessTerm = testString;
    }

    public String getBusinessTerm() {
        return mBusinessTerm;
    }

    public void setCardinality(String c) {
        mCardinality = CardinalitySemantic.getByValue(c, this.mID);
    }

    public CardinalitySemantic getCardinality() {
        return mCardinality;
    }

    public void setDataType(String dt) {
        mDataType = SemanticDataType.getByValue(dt, this.mID);
    }

    public SemanticDataType getDataType() {
        return mDataType;
    }

    public void setDescription(String d) {
        String testString = d.replaceAll("\\s+", " ").replaceAll(LEADING_TRAILING_WHITESPACES, "");
        if (!testString.equals(d)) {
            LOG.warn("WARNING: " + getId() + " 'description' has whitespace problems:"
                    + "\n\tWith visible whitespace (space = . and Java abbreviations \\t,\\r,\\f,\\n):\n\t\t"
                    + "\"" + d.replaceAll(" ", ".").replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t").replaceAll("\r", "\\\\r").replaceAll("\f", "\\\\f") + "\"\n");
            LOG.warn("\tinstead of:\n\t\t\"" + testString + "\"\n\n");
        }
        mDescription = testString;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setLevel(Integer l) {
        mLevel = l;
    }

    public Integer getLevel() {
        return mLevel;
    }

    /**
     * @return the ID of the current table, to access existing bugfixes
     */
    String getTableId() {
        return mTableId;
    }

    @Override
    public String toString() {
        StringBuilder xml = new StringBuilder();
        try {
            xml.append("\t<semantic id=\"" + mID + "\"");
            if (mLevel != null) {
                xml.append(" level=\"" + mLevel + "\"");
            }
            if (mCardinality != null) {
                xml.append(" card=\"" + mCardinality.getValue() + "\"");
            }
            if (mBusinessTerm != null) {
                xml.append(" bt=\"" + mBusinessTerm + "\"");
            }
            if (mDescription != null) {
                xml.append(" desc=\"" + mDescription + "\"");
            }
            if (mDataType != null) {
                xml.append(" datatype=\"" + mDataType + "\"");
            }
            if (syntaxRepresentations != null && !syntaxRepresentations.isEmpty()) {
                xml.append(">\n");
                for (NodeSyntax xnode : syntaxRepresentations) {
                    xml.append(xnode.toString());
                    xml.append("\n");
                }
                xml.append("\t</semantic>");
            } else { // empty element due as no XML children
                xml.append("/>");
            }
        } catch (Throwable t) {
            t.toString();
        }
        return xml.toString();
    }

    public String toSubString(boolean onlySemantic) {
        StringBuilder xml = new StringBuilder();
        try {
            xml.append("\t<semantic id=\"" + mID + "\"");
            if (mLevel != null) {
                xml.append(" level=\"" + mLevel + "\"");
            }
            if (mCardinality != null) {
                xml.append(" card=\"" + mCardinality.getValue() + "\"");
            }
            if (mBusinessTerm != null) {
                xml.append(" bt=\"" + mBusinessTerm + "\"");
            }
            if (mDescription != null) {
                xml.append(" desc=\"" + mDescription + "\"");
            }
            if (mDataType != null) {
                xml.append(" datatype=\"" + mDataType + "\"");
            }
            if (!onlySemantic && syntaxRepresentations != null && !syntaxRepresentations.isEmpty()) {
                xml.append(">\n");
                for (NodeSyntax xnode : syntaxRepresentations) {
                    xml.append(xnode.toSubString());
                    xml.append("\n");
                }
                xml.append("\t</semantic>");
            } else { // empty element due as no XML children
                xml.append("/>");
            }
        } catch (Throwable t) {
            t.toString();
        }
        return xml.toString();
    }

    private String toJSONSubString(boolean onlySemantic) {
        StringBuilder xml = new StringBuilder();
        try {
            xml.append("\t{ \"id\":\"" + mID + "\",");
            if (mLevel != null) {
                xml.append(" \"level\":\"" + mLevel + "\",");
            }
            if (mCardinality != null) {
                xml.append(" \"card\":\"" + mCardinality.getValue() + "\",");
            }
            if (mBusinessTerm != null) {
                xml.append(" \"bt\":\"" + mBusinessTerm + "\",");
            }
            if (mDescription != null) {
                xml.append(" \"desc\":\"" + mDescription + "\",");
            }
            if (mDataType != null) {
                xml.append(" \"datatype\":\"" + mDataType + "\"");
            }
            xml = deleteFinalChar(xml, ',');
            xml.append("}");
        } catch (Throwable t) {
            t.toString();
        }
        return xml.toString();
    }

    private StringBuilder deleteFinalChar(StringBuilder xml, char finalChar){
        int length = xml.length();
        if(xml.charAt(length - 1) == finalChar){
            xml.deleteCharAt(length - 1);
        }
        return xml;
    }

    public void createSemanticXMLFile(String fileName, String outputPath, String title, Boolean isNormative) {
        createXMLFileVariants(fileName, outputPath, title, Boolean.FALSE, Boolean.TRUE, isNormative);
    }

    public void createSemanticJSONFile(String fileName, String outputPath, String title, Boolean isNormative) {
        createJSONFileVariants(fileName, outputPath, title, Boolean.FALSE, Boolean.TRUE, isNormative);
    }

    public void createSubXMLFile(String fileName, String outputPath, String title, Boolean isNormative) {
        createXMLFileVariants(fileName, outputPath, title, Boolean.TRUE, Boolean.FALSE, isNormative);
    }

    public void createXMLFile(String fileName, String outputPath, String title, Boolean isNormative) {
        createXMLFileVariants(fileName, outputPath, title, Boolean.FALSE, Boolean.FALSE, isNormative);
    }

    private void createXMLFileVariants(String fileName, String outputPath, String title, Boolean isSubFile, Boolean onlySemantic, Boolean isNormative) {
        try {
            if (fileName.endsWith(ODT_SUFFIX)) {
                fileName = fileName.substring(0, fileName.length() - ODT_SUFFIX.length());
            }
            StringBuilder xml_Suffix = new StringBuilder();
            Collection<NodeSemantic> semanticNodes = this.allSemanticNodes.values();
            int xmlCount = 0;
            for (NodeSemantic s : semanticNodes) {
                if (s != null) {
                    if (isSubFile || onlySemantic) {
                        xml_Suffix.append(s.toSubString(onlySemantic)).append("\n");
                    } else {
                        xml_Suffix.append(s.toString()).append("\n");
                    }
                } else {
                    LOG.error("ERROR: DATA MODEL IS EMPTY!!");
                }
                if (s.syntaxRepresentations != null) {
                    xmlCount += s.syntaxRepresentations.size();
                }
            }
            xml_Suffix.append("</semantics>");
            StringBuilder xml_Prefix = new StringBuilder();
            int semanticCount = semanticNodes.size();
            xml_Prefix.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<semantics semantics=\"" + semanticCount + "\" xml=\"" + xmlCount + "\" file=\"" + fileName + "\" table=\"" + title + "\">\n");
            // XML files are saved in at least different folder (same semantic data-set, same informative & normative-subset, full table dataset)
            String outputFilePath = getOutputFilePath(fileName, outputPath, title, isSubFile, onlySemantic, isNormative);
            FileHelper.saveStringToFile(new File(outputFilePath), xml_Prefix.append(xml_Suffix).toString());
            LOG.info("\nSaving extracted syntax binding into file:\n\t" + outputFilePath + "\n");
        } catch (Throwable e) {
            LoggerFactory.getLogger(NodeSemantic.class.getName()).error("ERROR: " + e.getMessage(), e);
        }
    }

    private void createJSONFileVariants(String fileName, String outputPath, String title, Boolean isSubFile, Boolean onlySemantic, Boolean isNormative) {
        try {
            if (fileName.endsWith(ODT_SUFFIX)) {
                fileName = fileName.substring(0, fileName.length() - ODT_SUFFIX.length());
            }
            StringBuilder xml_Suffix = new StringBuilder();
            Collection<NodeSemantic> semanticNodes = this.allSemanticNodes.values();
            int xmlCount = 0;
            for (NodeSemantic s : semanticNodes) {
                if (s != null && (isSubFile || onlySemantic)) {
                    xml_Suffix.append(s.toJSONSubString(onlySemantic)).append(",");
                } else {
                    LOG.error("ERROR: DATA MODEL IS EMPTY!!");
                }
                if (s.syntaxRepresentations != null) {
                    xmlCount += s.syntaxRepresentations.size();
                }
            }
            xml_Suffix = deleteFinalChar(xml_Suffix, ',');
            xml_Suffix.append("\n]}");
            StringBuilder xml_Prefix = new StringBuilder();
            int semanticCount = semanticNodes.size();
            xml_Prefix.append("{ \"semanticCount\":\"" + semanticCount + "\", " + " \"file\":\"" + fileName + "\", \"table\":\"" + title + "\", " + "\"semantics\":[\n");
            // XML files are saved in at least different folder (same semantic data-set, same informative & normative-subset, full table dataset)
            String outputFilePath = getOutputFilePath(fileName, outputPath, title, isSubFile, onlySemantic, isNormative, ".json");
            FileHelper.saveStringToFile(new File(outputFilePath), xml_Prefix.append(xml_Suffix).toString());
            LOG.info("\nSaving extracted syntax binding into file:\n\t" + outputFilePath + "\n");
        } catch (Throwable e) {
            LoggerFactory.getLogger(NodeSemantic.class.getName()).error("ERROR: " + e.getMessage(), e);
        }
    }

    /**
     * XML files are saved in at least three different folder (same semantic
     * data-set, same informative & normative-subset, full table dataset) In the
     * case of UBL there are even two same informative & normative-subsets.
     */
    private String getOutputFilePath(String fileName, String outputPath, String title, Boolean isSubFile, Boolean onlySemantic, Boolean isNormative) {
        return getOutputFilePath(fileName, outputPath, title, isSubFile, onlySemantic, isNormative, ".xml");
    }

    /**
     * XML files are saved in at least three different folder (same semantic
     * data-set, same informative & normative-subset, full table dataset) In the
     * case of UBL there are even two same informative & normative-subsets.
     */
    private String getOutputFilePath(String fileName, String outputPath, String title, Boolean isSubFile, Boolean onlySemantic, Boolean isNormative, String outputFileSuffix) {
        String outputFilePath = null;
        if (isSubFile) {
            if (isNormative) {
                title += FILE_SUFFIX__SUBSET;
            }
            String bindingDirName = getBindingDirName(fileName);
            File newDir = new File(outputPath + File.separator + bindingDirName);
            newDir.mkdir();
            outputFilePath = newDir.getAbsolutePath() + File.separator + title.replaceAll(INVALID_FILE_CHARACTERS, "_") + outputFileSuffix;
        } else if (onlySemantic) {
            title += FILE_SUFFIX__SEMANTIC;
            File newDir = new File(outputPath + File.separator + SAME_SEMANTIC_DIR_NAME);
            newDir.mkdir();
            outputFilePath = newDir.getAbsolutePath() + File.separator + fileName + "_" + title.replaceAll(INVALID_FILE_CHARACTERS, "_") + outputFileSuffix;
        } else {
            // informative data needs to be saved twice (root and subdirectory for comparison)
            File newDir = new File(outputPath + File.separator + fileName);
            newDir.mkdir();
            outputFilePath = newDir.getAbsolutePath() + File.separator + title.replaceAll(INVALID_FILE_CHARACTERS, "_") + outputFileSuffix;
        }
        return outputFilePath;
    }

    private String getBindingDirName(String fileName) {
        String enType = BINDING_TYPE_INVOICE;
        if (!fileName.equals(mSyntaxBindingLastFileName)) {
            mSyntaxBindingLastFileName = fileName;
            mSyntaxBindingCounter = 1;
        } else {
            mSyntaxBindingCounter++;
            if (mSyntaxBindingCounter > 2) {
                enType = BINDING_TYPE_CREDIT_NOTE;
            }
        }
        return SAME_BINDING_PREFIX + enType + fileName;
    }

    /**
     * Compares the resulting mismatch from the given cardinalites of semantic &
     * syntax, with the given mismatches!
     */
    static public void validateCardinalityMismatches() {

        System.out.println("\n");
        System.out.println("***************************");
        System.out.println("* Cardinality Mismatches  *");
        System.out.println("***************************");
        StringBuilder sb = new StringBuilder();
        // for loop as functional operations
        for (NodeSemantic s : allSemanticNodes.values()) {
            sb.append("\nSemantic ID: " + s.mID + "\n");
            sb.append("Semantic Term: " + s.mBusinessTerm + "\n");
            if (s.syntaxRepresentations != null) {
                getCardinaltiyMismatches(sb, s);
            } else {
                sb.append("WARNING: Semantic without XML representation!!!\n");
                System.out.print(sb.toString());
            }
            sb = sb.delete(0, sb.length());
        }
        System.out.println("\n\n");
    }

    static private void getCardinaltiyMismatches(StringBuilder sb, NodeSemantic s) {
        Boolean hasError = Boolean.FALSE;
        String cSem = s.getCardinality().getValue();

        for (NodeSyntax x : s.syntaxRepresentations) {
            if (x != null) {
                String cSyn = x.getCardinality();
                MisMatch[] mm = x.getMisMatches();
                sb.append("XML path: " + x.mPath + "\n");
                // evaluate the cardinality mismatch by comparing semantic & syntax cardinality
                // 1) Give errors, if cardinality is missing!
                if (cSem != null) {
                    sb.append("Cardinality-Semantic: " + cSem + "\n");
                } else {
                    hasError = Boolean.TRUE;
                    sb.append("ERROR: Semantic Cardinality have to be set!\n");
                }
                if (cSyn != null) {
                    sb.append("Cardinality-Syntax:   " + cSyn + "\n");
                } else {
                    // there has to be a cardinality, unless it is an attribute (unique)
                    if (!x.getPath().contains("@")) {
                        hasError = Boolean.TRUE;
                        sb.append("ERROR: XML Cardinality have to be set!\n");
                    } else {
                        sb.append("Using an XML attribute!\n");
                    }
                }

                // 2) Gather existing cardinality
                Set<Integer> cm = null;
                if (mm != null) {
                    for (MisMatch m : mm) {
                        if (m != null) {
                            //System.out.println("Mismatch m: " + m.getValue());
                            if (m.getValue().startsWith("CAR-")) {
                                if (cm == null) {
                                    cm = new HashSet<Integer>();
                                }
                                Integer cmLevel = getCardinality(Integer.parseInt(m.getValue().substring(4)));
                                if (cm.contains(cmLevel)) {
                                    hasError = Boolean.TRUE;
                                    sb.append("ERROR: Cardinality level " + cmLevel + " is multiple times present!\n");
                                }
                                cm.add(cmLevel);
                                // Potential information loss during roundtrip from syntax to semantic and back (just info)
//                                if (cmLevel.equals(CAR_2) || cmLevel.equals(CAR_4)) {
//                                    hasError = Boolean.TRUE;
//                                    sb.append("\nWARNING: Potential information loss due to limited XML: CAR-" + cmLevel + "!");
//                                }
//                                if (cmLevel.equals(CAR_1) || cmLevel.equals(CAR_3)) {
//                                    hasError = Boolean.TRUE;
//                                    sb.append("\nWARNING: Potential information loss due to limited EU semantic: CAR-" + cmLevel + "!");
//                                }
                            }
                        } else {
                            hasError = Boolean.TRUE;
                            sb.append("ERROR: Mismatch should be set!\n");
                        }
                    }
                }

                // 3) Test cardinality
                if (cSem != null && cSyn != null) {
                    if (!cSyn.equals(cSem)) {
                        if (cSem.charAt(0) != cSyn.charAt(0)) {
                            // CAR-1
                            if (cSem.charAt(0) == '0' && cSyn.charAt(0) == '1') {
                                if (cm != null) {
                                    if (!cm.contains(CAR_1)) {
                                        hasError = Boolean.TRUE;
                                        sb.append("ERROR: CAR-1 is missing!\n");
                                    } else {
                                        cm.remove(CAR_1);
                                    }
                                }
                            } // CAR-2
                            else if (cSem.charAt(0) == '1' && cSyn.charAt(0) == '0') {
                                if (cm != null) {
                                    if (!cm.contains(CAR_2)) {
                                        hasError = Boolean.TRUE;
                                        sb.append("ERROR: CAR-2 is missing!\n");
                                    } else {
                                        cm.remove(CAR_2);
                                    }
                                }
                            }
                        }
                        if (cSem.charAt(3) != cSyn.charAt(3)) {
                            // CAR-3
                            if (cSem.charAt(3) == '1' && cSyn.charAt(3) == 'n') {
                                if (cm != null) {
                                    if (!cm.contains(CAR_3)) {
                                        hasError = Boolean.TRUE;
                                        sb.append("ERROR: CAR-3 is missing!\n");
                                    } else {
                                        cm.remove(CAR_3);
                                    }
                                }
                            } // CAR-4
                            else if (cSem.charAt(3) == 'n' && cSyn.charAt(3) == '1') {
                                if (cm != null) {
                                    if (!cm.contains(CAR_4)) {
                                        hasError = Boolean.TRUE;
                                        sb.append("ERROR: CAR-4 is missing!\n");
                                    } else {
                                        cm.remove(CAR_4);
                                    }
                                }
                            }
                        }
                        if (cm != null && cm.size() > 0) {
                            for (Integer i : cm) {
                                hasError = Boolean.TRUE;
                                sb.append("ERROR: CAR-" + i + " is misplaced!\n");
                            }
                        }
                    }
                }
                cm = null;
            } // if
        } // for
        if (hasError) {
            System.out.print(sb.toString());
            hasError = Boolean.FALSE;
        }
    }


    static private Integer getCardinality(Integer i) {
        Integer cardinality = null;
        switch (i) {
            case 1:
                cardinality = CAR_1;
                break;
            case 2:
                cardinality = CAR_2;
                break;
            case 3:
                cardinality = CAR_3;
                break;
            case 4:
                cardinality = CAR_4;
                break;
        }
        return cardinality;
    }

    static public void showSemanticIDAnomalies() {
        if (mMultiHyphenDiff != null) {
            LOG.warn("WARNING: Semantic IDs are using different hyphen characters:\n");
            LOG.warn("\t\tThe unicode character hyphen-minus is shown as '*', the control-character 'START OF GUARDED AREA' as '+':\n\t\t");
            for (String id : mMultiHyphenDiff) {
                LOG.info(id.replace("-", "*").replace("–", "+") + ", ");
            }
            if (mMultiHyphenSame != null) {
                LOG.info("\n\t\tIn addition, the following semantic IDs were using two identical hyphen:\n");
                for (String id : mMultiHyphenSame) {
                    LOG.info(id + ", ");
                }
                mMultiHyphenSame.clear();
                mMultiHyphenSame = null;
            } else {
                LOG.info("\n\t\tNo Semantic ID was using two identical hyphen!\n\n");
            }
            mMultiHyphenDiff.clear();
            mMultiHyphenDiff = null;
        } else {
            LOG.info("INFO: Semantic IDs are using correct hyphens.\n\n");
        }
    }

    private Set<List<NodeSemantic>> findDuplicates() {
        Set<List<NodeSemantic>> duplicates = new HashSet<>();
        // 1) XPath - collecting BG in List
        for (List<NodeSyntax> l : NodeSyntax.duplicatePathList.values()) {
            List<NodeSemantic> duplicateList = new ArrayList<NodeSemantic>();
            for (NodeSyntax x : l) {
                duplicateList.add(x.getSemanticNode());
            }
            // remove duplicate NodeSemantic lists by adding to Set
            duplicates.add(duplicateList);
        }
        return duplicates;
    }

    void logDuplicateXPathErors() {
        Set<List<NodeSemantic>> duplicates = this.findDuplicates();
        StringBuilder sb = new StringBuilder();
        for (List<NodeSemantic> l : duplicates) {
            sb.append("The following group of Semantic Entities have the same Syntax Nodes:\n");
            for (NodeSemantic s : l) {
                sb.append(s.toString() + "\n");
            }
            sb.append("\n\n");
        }
        LOG.error("\n\nERROR: There are " + duplicates.size() + " duplications of syntax within semantic nodes!\n" + sb.toString());
    }
// 2DO Later Svante - CURRENTLY NO WARNING IF XML WAS USED IN MULTIPLE SEMANTICS
//    Set<List<SemanticNode>> findDuplicates() {
//        Set<List<SemanticNode>> duplicates = new HashSet<>();
//
//        StringBuilder duplicateIdList = new StringBuilder();
//        // 1) XPath - collecting BG in List
//        for (List<SyntaxNode> l : NodeSyntax.duplicatePathList.values()) {
//            Set<String> duplicateLists = new HashSet<String>();
//            for (NodeSyntax x : l) {
//                // 2) SG Liste UNIQUE als STRING im SET zu machen
//
//                duplicateIdList.append(x.getSemanticNode().getId()).append(" ");
//            }
//            duplicateLists.add(duplicateIdList.toString());
//        }
//
//        Set<String> duplicateLists = new HashSet<String>();
//
//        // 3) Alle Keys ausgeben als Fehlermeldung
//        return duplicates;
//    }

    /**
     * This enum contains all table header row label of the semantic object
     */
    public static enum SemanticHeading {

        /**
         * An identifier for the information element (BT - Business Term) and
         * group of information elements (BG - Business terms Group). The
         * identifiers are not necessarily consecutive or in sequence.
         */
        ID("ID"),
        /**
         * Indicates on which level in the model the information element occurs.
         */
        LEVEL("Level."),
        /**
         * Cardinality: Also known as multiplicity is used to indicate if an
         * information element (or group of information elements) is mandatory
         * or conditional, and if it is repeatable. The cardinality shall always
         * be analysed in the context of where the information element is used.
         * Example: the Payee Name is mandatory in the core invoice model, but
         * only when a Payee is stated and is relevant.
         */
        CARD_S("Card."),
        /**
         * Business Term: The name of the information element used in the core
         * invoice model or the name of a coherent group of related information
         * elements, provided to give logical meaning.
         */
        BT("BT"),
        /**
         * A description of the semantic meaning of the information element
         */
        DESC("Desc."),
        /**
         * The data format that applies to the information element (see EN
         * 16931-1 - 6.5).
         */
        DT("DT");

        private final String mLabel;

        /**
         * @return the label of the header row of a semantic object.
         */
        public String getLabel() {
            return mLabel;
        }

        SemanticHeading(String label) {
            this.mLabel = label;
        }
    }

    private int countChar(String str, char c) {
        int count = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }
}
