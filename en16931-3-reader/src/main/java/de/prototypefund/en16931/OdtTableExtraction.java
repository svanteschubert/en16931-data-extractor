/** **********************************************************************
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
 *
 *********************************************************************** */
package de.prototypefund.en16931;

import static de.prototypefund.en16931.NodeSemantic.LEADING_TRAILING_WHITESPACES;
import de.prototypefund.en16931.NodeSemantic.SemanticHeading;
import de.prototypefund.en16931.NodeSyntax.SyntaxHeading;
import static de.prototypefund.en16931.NodeSyntax.SyntaxHeading.CARD;
import static de.prototypefund.en16931.NodeSyntax.SyntaxHeading.MATCH;
import static de.prototypefund.en16931.NodeSyntax.SyntaxHeading.NAME;
import static de.prototypefund.en16931.NodeSyntax.SyntaxHeading.RULES;
import de.prototypefund.en16931.type.TypeStatistic;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.odftoolkit.odfdom.doc.table.OdfTableRow;
import org.odftoolkit.odfdom.dom.element.office.OfficeTextElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElementBase;
import org.odftoolkit.odfdom.dom.element.table.TableTableElement;
import org.odftoolkit.odfdom.dom.element.text.TextHElement;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.odftoolkit.odfdom.dom.element.text.TextParagraphElementBase;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OdtTableExtraction {

    private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(OdtTableExtraction.class);
    }
    OdfTextDocument odtDoc;
    OdfTable odtTable;
    SemanticHeading[] SEMANTIC_TABLE_HEADINGS = NodeSemantic.SemanticHeading.values();
    SyntaxHeading[] SYNTAX_TABLE_HEADINGS = NodeSyntax.SyntaxHeading.values();
    private String mTableId = null;
    // XML tables
    private static final int NORMATIVE_TABLE_SIZE = 11;
    private static final int INFORMATIVE_TABLE_SIZE = 8;
    // EDIFACT tables
    private static final int NORMATIVE_EDIFACT_TABLE_SIZE = 10;
    private static final int INFORMATIVE_EDIFACT_TABLE_SIZE = 9;
    private static final String ODT_SUFFIX = ".odt";
    private static final String WORKING_DIRECTORY = "user.dir";
    private static Boolean mIsXML;
    private static Boolean mIsUBL;
    // used by NodeSemantics to collect info on Semantic ID problem using different hyphens
    static List<String> mMultiHyphenDiff = null;
    static List<String> mMultiHyphenSame = null;
    /**
     * @param odtFileName the file name of the specification or a directory
     * where specifications are any descendant documents!
     * @throws java.lang.Exception
     */
    public void collectSpecData(String odtFileName) throws Exception {
        String absPath = null;
        try {
            absPath = FileHelper.getAbsolutePath(odtFileName);
        } catch (FileNotFoundException e) {
            // expected if file is not in Java classpath
        }
        if (absPath == null) {
            absPath = System.getProperty(WORKING_DIRECTORY);
            if (!absPath.endsWith(File.separator)) {
                absPath += File.separator;
            }
            absPath += odtFileName;
        }
        collectSpecData(new File(absPath));
    }

    /**
     * If file is a directory searches within children and provides all found
     * documents that have an '.odt' suffix to the data extractor.
     */
    private void collectSpecData(File f) throws Exception {
        String absPath = f.getAbsolutePath();
        if (f.isDirectory()) {
            LOG.debug("Extracting data from directory: " + absPath + "\n");
            for (String childPath : f.list()) {
                collectSpecData(new File(absPath + File.separator + childPath));
            }
        } else {
            if (absPath.endsWith(ODT_SUFFIX)) {
                LOG.debug("Extracting data from file: " + absPath + "\n");
                extractData(f);
            } else {
                LOG.debug("As without file suffix '.odt' ignoring: " + absPath + "\n");
            }
        }
    }

    /**
     * @param odtFile <code>File</code> representing the EN16931 specification
     */
    private void extractData(File odtFile) throws Exception {
        odtDoc = OdfTextDocument.loadDocument(odtFile);
        String absPath = odtFile.getAbsolutePath();
        String odtFileName = absPath.substring(absPath.lastIndexOf(File.separatorChar) + 1);
        String odtFilePath = absPath.substring(0, absPath.lastIndexOf(File.separatorChar) + 1);
        LOG.info("****************************************************************\n"
                + "********* Specification document: '" + odtFileName + "'\n"
                + "*********\n\n");
        // traverse top level user objects
        OfficeTextElement root = odtDoc.getContentRoot();
        NodeList topChildren = root.getChildNodes();

        int i = 0;
        Node child;
        String tableTitle = null;
        Boolean hasPrecedingHeading = Boolean.FALSE;
        for (i = 0; i < topChildren.getLength(); i++) {
            // NOTE: Get all Table, where a heading is in front of the table is the title of the table!
            child = topChildren.item(i);
            if (child instanceof TextHElement) {
                tableTitle = null;
                TextHElement h = ((TextHElement) child);
                tableTitle = h.getTextContent();
                hasPrecedingHeading = Boolean.TRUE;

                // German version uses paragraphs instead of headings for informal tables
            } else if (child instanceof TextPElement) {

                tableTitle = null;
                TextPElement p = ((TextPElement) child);
                tableTitle = p.getTextContent();

            } else if (child instanceof TableTableElement) {
                if (hasPrecedingHeading || tableTitle != null && tableTitle.contains("Mapping")) {
                    extractDataFromTable(((TableTableElement) child), odtFileName, odtFilePath, tableTitle);
                }
                hasPrecedingHeading = Boolean.FALSE;
                tableTitle = null;

                // there shall be no other element between the preceding heading/paragraph (title) and the table
            } else if (child instanceof OdfElement) {
                hasPrecedingHeading = Boolean.FALSE;
                tableTitle = null;
            }
        }
        LOG.info("\n*********"
                + "********* Specification document: '" + odtFileName + "'\n"
                + "****************************************************************\n");
    }

    private void extractDataFromTable(TableTableElement tableElement, String fileName, String outputPath, String title) {
        LOG.debug("Table Heading: '" + title + "'\n");
        mTableId = title;
        OdfTable table = OdfTable.getInstance(tableElement);

        //*********
        // HEADER ROW
        //*********
        /*  The typical ODF table row with the first cell looks like:
            <table:table-row table:style-name="TableRow326">
                <table:table-cell table:style-name="TableCell327">
                    <text:p text:style-name="P328">BT-1</text:p>
                </table:table-cell>
         */
        int columnCount = table.getColumnCount();
        LOG.debug("ColumnCount is '" + columnCount + "'!\n");

        if (table.getHeaderRowCount() != 1) {
            LOG.debug("Ignoring '" + title + "' as no header row was found!\n");

        } else if (columnCount == NORMATIVE_TABLE_SIZE || columnCount == INFORMATIVE_TABLE_SIZE || columnCount == NORMATIVE_EDIFACT_TABLE_SIZE || columnCount == INFORMATIVE_EDIFACT_TABLE_SIZE) {
            mIsXML = (columnCount == NORMATIVE_TABLE_SIZE || columnCount == INFORMATIVE_TABLE_SIZE);
            if (mIsXML) {
                if (title.contains("UBL")) {
                    mIsUBL = Boolean.TRUE;
                } else {
                    mIsUBL = Boolean.FALSE;
                }
            }
            assert table.getHeaderColumnCount() == 0;
            OdfTableRow tr = table.getRowByIndex(0);
            OdfTableCell tc = tr.getCellByIndex(0);
            // final test: first cell conent of header row have to be correct!
            if (!(((columnCount == NORMATIVE_TABLE_SIZE || columnCount == NORMATIVE_EDIFACT_TABLE_SIZE) && getCellContent(tc).equals(SEMANTIC_TABLE_HEADINGS[0].getLabel()))
                    || ((columnCount == INFORMATIVE_TABLE_SIZE || columnCount == INFORMATIVE_EDIFACT_TABLE_SIZE) && getCellContent(tc).equals(SYNTAX_TABLE_HEADINGS[0].getLabel())))) {
                LOG.error("ERROR: WRONG TABLE: '" + mTableId + "' + IS NOT A TABLE FOR DATA EXTRACTION!");
            } else {
                LOG.info("Table Heading: '" + title + "'\n\n");
                //*********
                // CONTENT ROWS
                //*********
                int rowCount = table.getRowCount();
                NodeSemantic semanticNode = null;
                NodeSyntax syntaxNode = null;
                // only required for the informative cell to remember the two Syntax contents, until Semantic was created
                String informativeTable_CellContentOne = null;
                String informativeTable_CellContentTwo = null;
                String informativeTable_CellContentThree = null;
                int syntax_header_length = 2; // the default, only EDIFACT has 3 columns
                for (int r = 1; r < rowCount; r++) {
                    tr = table.getRowByIndex(r);
                    LOG.debug("\n**** NEW ROW ****");
                    int cellCount = tr.getCellCount();

                    syntaxNode = null;
                    for (int c = 0; c < cellCount; c++) {
                        tc = tr.getCellByIndex(c);
                        String cellContent = getCellContent(tc);
                        String label = null;
                        LOG.debug("**** NEW CELL ****");
                        LOG.debug(label + ": " + cellContent);

                        // First Part of Heading - Semantics
                        if (columnCount == NORMATIVE_TABLE_SIZE || columnCount == NORMATIVE_EDIFACT_TABLE_SIZE) {
                            if (c < SEMANTIC_TABLE_HEADINGS.length) {
                                // For each Row:
                                if (c == 0) {
                                    if (cellContent.replaceAll(LEADING_TRAILING_WHITESPACES, "").isEmpty()) {
                                        LOG.debug("IS EMPTY!!!");
                                    } else {
// duplicated output - errors are no longer being hidden
//                                        // errorhandling of previous node
//                                        if (SpecificationFixes.hasError) {
//                                            SpecificationFixes.flushErrors(semanticNode);
//                                        }
                                        semanticNode = new NodeSemantic(cellContent, mTableId);
                                    }
                                }
                                label = mapSemantic(cellContent, c, semanticNode);

                                // Second Part of Row - Syntax
                            } else {
                                SyntaxHeading columnType = null;
                                int i = c - SEMANTIC_TABLE_HEADINGS.length;
                                if (i == 0) {
                                    if (mIsXML) {
                                        if (mIsUBL) {
                                            syntaxNode = new NodeUblXml(cellContent, semanticNode);
                                        } else {
                                            syntaxNode = new NodeXml(cellContent, semanticNode);
                                        }
                                    } else {
                                        syntaxNode = new NodeEdifact(cellContent, semanticNode);
                                    }
                                } else {
                                    if (columnCount == NORMATIVE_EDIFACT_TABLE_SIZE) {
                                        // we have to omit the syntax not available for EDIFACT, i.e. "Type"
                                        if (i < 2) { // if it before "Name"
                                            columnType = SYNTAX_TABLE_HEADINGS[i + 1]; // always skip "Type"
                                        } else {
                                            columnType = SYNTAX_TABLE_HEADINGS[i + 2]; // skip "Type" and "Name"
                                        }
                                    } else { // normative XML
                                        // we have to omit the syntax not available for XML, i.e. "Name"
                                        if (i < 3) {
                                            columnType = SYNTAX_TABLE_HEADINGS[i];
                                        } else {
                                            columnType = SYNTAX_TABLE_HEADINGS[i + 1];
                                        }
                                    }
                                    label = mapSyntax(cellContent, syntaxNode, columnType);
                                }
                            }
                        } else { // informative table
                            // in case of EDIFACT 3-4 there are 3 columns instead of 2
                            if (columnCount == INFORMATIVE_EDIFACT_TABLE_SIZE) {
                                syntax_header_length = 3;
                            }

                            if (c < syntax_header_length) {
                                // For each Row:

                                // find the according type to this column from the header
                                if (c == 0) {
                                    // this time the syntax mapping has 3 columns less..
                                    informativeTable_CellContentOne = cellContent;
                                } else if (c == 1) {
                                    informativeTable_CellContentTwo = cellContent;
                                } else if (c == 3) { // in case of EDIFACT part 3-4
                                    informativeTable_CellContentThree = cellContent;
                                }
                                // Second Part of Row - Semantic Model
                            } else {
                                if (c == syntax_header_length) {
                                    cellContent = cellContent.replaceAll(LEADING_TRAILING_WHITESPACES, "");
                                    // same semantic node, if there is NO ID or the previous ID
                                    if (cellContent.isEmpty()) { // semantic ID is empty
                                        break; // just boilerplate
                                    } else { // semantic ID exist (might be not as the one before)
                                        // in the informative table the IDs are defined not adjacent
                                        semanticNode = NodeSemantic.allSemanticNodes.get(cellContent);
                                        if (semanticNode == null) {
                                            semanticNode = new NodeSemantic(cellContent, mTableId);
                                        }
                                    }
                                } else {
                                    label = mapSemantic(cellContent, c - syntax_header_length, semanticNode);
                                }
                                // Finally after all semantics have been added, add the syntax that was remembered from the start of the informative table
                                if (c == cellCount - 1) {
                                    if (mIsXML) {
                                        syntaxNode = new NodeXml(informativeTable_CellContentOne, semanticNode);
                                    } else {
                                        syntaxNode = new NodeEdifact(informativeTable_CellContentOne, semanticNode);
                                    }

                                    label = mapSyntax(informativeTable_CellContentTwo, syntaxNode, SYNTAX_TABLE_HEADINGS[2]);
                                    if (!mIsXML) {
                                        label = mapSyntax(informativeTable_CellContentThree, syntaxNode, SYNTAX_TABLE_HEADINGS[3]);
                                    }
                                }
                            }
                        }
                    }
                }
                // anomalies of each semantic ID had been collected, to show once all..
                semanticNode.showSemanticIDAnomalies();
                semanticNode.createXMLFile(fileName, outputPath, title);
                // dump the table model into an XML file
                semanticNode.createXMLFile(fileName, outputPath, title);
                if (columnCount == NORMATIVE_TABLE_SIZE) {
                    semanticNode.createSubXMLFile(fileName, outputPath, title);
                }
                if (columnCount == NORMATIVE_EDIFACT_TABLE_SIZE) {
                    semanticNode.createSubXMLFile(fileName, outputPath, title);
                }
                // log all duplicated XML nodes
//2DO            semanticNode.logDuplicateXPathErrors();
                TypeStatistic.table(title, mIsXML, mIsUBL);
                clearAll();
            }
        }
    }

    private String mapSemantic(String cellContent, int c, NodeSemantic semanticNode) {
        SemanticHeading columnType_Semantic = SEMANTIC_TABLE_HEADINGS[c];
        String label = columnType_Semantic.getLabel();
        if (!cellContent.isEmpty()) {
            switch (columnType_Semantic) {
                case ID:
                    break; // already prior set
                case LEVEL:
                    semanticNode.setLevel(Integer.parseInt(cellContent));
                    break;
                case CARD_S:
                    semanticNode.setCardinality(cellContent);
                    break;
                case BT:
                    semanticNode.setBusinessTerm(cellContent);
                    break;
                case DESC:
                    semanticNode.setDescription(cellContent);
                    break;
                case DT:
                    semanticNode.setDataType(cellContent);
                    break;
            }
        }
        return label;
    }

    private String mapSyntax(String cellContent, NodeSyntax syntaxNode, SyntaxHeading columnType) {
        String label = columnType.getLabel();
        if (cellContent != null) {
            cellContent = cellContent.replaceAll(LEADING_TRAILING_WHITESPACES, "");
            if (!cellContent.isEmpty()) {
                switch (columnType) {
                    case TYPE: // only for XML
                        ((NodeXml) syntaxNode).setType(cellContent);
                        break;
                    case CARD:
                        if (mIsXML) {
                            ((NodeXml) syntaxNode).setCardinalityXml(cellContent);
                        } else {
                            ((NodeEdifact) syntaxNode).setCardinalityEdifact(cellContent);
                        }
                        break;
                    case NAME: // only for EDIFACT
                        ((NodeEdifact) syntaxNode).setName(cellContent);
                        break;
                    case MATCH:
                        syntaxNode.setMisMatch(cellContent);
                        break;
                    case RULES:
                        syntaxNode.setRules(cellContent);
                        break;
                }
            }
        }
        return label;
    }

    private String getCellContent(OdfTableCell tc) {
        String content = null;
        TableTableCellElementBase c = tc.getOdfElement();
        StringBuilder sb = null;
        Node node = c.getFirstChild();
        while (node != null) {
            if (node instanceof TextParagraphElementBase) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                TextParagraphElementBase p = ((TextParagraphElementBase) node);
                sb.append(p.getTextContent());
            }
            node = node.getNextSibling();
        }
        if (sb != null) {
            content = sb.toString();
        }
        return content.replaceAll(LEADING_TRAILING_WHITESPACES, "");
    }

    static void clearAll() {
        try {
            if (NodeSemantic.allSemanticNodes != null) {
                Collection<NodeSemantic> semanticNodes = NodeSemantic.allSemanticNodes.values();
                for (NodeSemantic s : semanticNodes) {
                    if (s.syntaxRepresentations != null) {
                        s.syntaxRepresentations.clear();
                    }
                }
                NodeSemantic.allSemanticNodes.clear();
            }
            if (NodeSyntax.allSyntaxNodes != null) {
                NodeSyntax.allSyntaxNodes.clear();
            }
            if (NodeSyntax.duplicatePathList != null) {
                NodeSyntax.duplicatePathList.clear();
            }
        } catch (Throwable e) {
            LoggerFactory.getLogger(NodeSemantic.class.getName()).error("ERROR: " + e.getMessage(), e);
        }
    }

    /*
	 * @param file the file to be saved, when creating a test file, you might use <code>newTestOutputFile(String relativeFilePath)</code>.
	 * @param inputData the data to be written into the file
     * @return absolute file path of new output file
     */
    static String saveStringToFile(File file, String data) {
        return saveStringToFile(file, Charset.forName("UTF-8"), data);
    }

    /**
     * @param file the file to be saved, when creating a test file, you might
     * use <code>newTestOutputFile(String relativeFilePath)</code>.
     * @param charset the character encoding
     * @param inputData the data to be written into the file
     * @return absolute file path of new output file
     */
    static String saveStringToFile(File file, Charset charset, String inputData) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
            //out = new BufferedWriter(new FileWriter(file));
            out.write(inputData);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(OdtTableExtraction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(OdtTableExtraction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return file.getAbsolutePath();
    }
}
