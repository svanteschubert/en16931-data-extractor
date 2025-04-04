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
import de.prototypefund.en16931.type.TypeStatistic;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
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

public class OdtTableDataExtraction {

    private static final Logger LOG = LoggerFactory.getLogger(OdtTableDataExtraction.class);

    // XML tables
    private static final int WG3_XML_NORMATIVE_TABLE_SIZE = 11;
    private static final int WG3_XML_INFORMATIVE_TABLE_SIZE = 8;
    // EDIFACT tables
    private static final int WG3_EDIFACT_NORMATIVE_TABLE_SIZE = 10;
    private static final int WG3_EDIFACT_INFORMATIVE_TABLE_SIZE = 9;
    private static final String ODT_SUFFIX = ".odt";
    private static final String WORKING_DIRECTORY = "user.dir";

    private final SemanticHeading[] WG3_SEMANTIC_TABLE_HEADINGS = NodeSemantic.SemanticHeading.values();
    private final SyntaxHeading[] WG3_SYNTAX_TABLE_HEADINGS = NodeSyntax.SyntaxHeading.values();

    private OdfTextDocument odtDoc;
    private String mTableId = null;
    private Boolean mIsXML;
    private Boolean mIsUBL;

    // used by NodeSemantics to collect info on Semantic ID problem using different
    // hyphens
    static List<String> mMultiHyphenDiff = null;
    static List<String> mMultiHyphenSame = null;
    // following variables used for dynamic directory naming of output
    // syntax-binding dirs
    static int mWG3_SyntaxBindingCounter = 0;
    static String mWG3_SyntaxBindingLastFileName = "";

    /**
     * @param odtFileName the file name of the specification or a directory
     *                    where specifications are any descendant documents!
     * @throws java.lang.Exception
     */
    public void collectSpecData(String odtFileName) throws Exception {
        String absPath = resolveAbsolutePath(odtFileName);
        collectSpecData(new File(absPath));
    }

    private String resolveAbsolutePath(String odtFileName) throws FileNotFoundException {
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
        // TypeStatistic.allDocuments();
        return absPath;
    }

    /**
     * If file is a directory searches within children and provides all found
     * documents that have an '.odt' suffix to the data extractor.
     */
    private void collectSpecData(File f) throws Exception {
        String absPath = f.getAbsolutePath();
        if (f.isDirectory()) {
            LOG.debug("Extracting data from directory: {}", absPath);
            for (String childPath : f.list()) {
                collectSpecData(new File(absPath + File.separator + childPath));
            }
        } else {
            if (absPath.endsWith(ODT_SUFFIX)) {
                LOG.debug("Extracting data from file: {}", absPath);
                extractData(f);
            } else {
                LOG.debug("As without file suffix '.odt' ignoring: {}", absPath);
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
                + "********* Specification document: '{}'\n"
                + "*********\n\n", odtFileName);

        // traverse top level user objects
        OfficeTextElement root = odtDoc.getContentRoot();
        NodeList topChildren = root.getChildNodes();

        String tableTitle = null;
        Boolean hasPrecedingHeading = Boolean.FALSE;
        for (int i = 0; i < topChildren.getLength(); i++) {
            // NOTE: Get all Table, where a heading is in front of the table is the title of the table!
            Node child = topChildren.item(i);
            if (child instanceof TextHElement) {
                tableTitle = ((TextHElement) child).getTextContent();
                hasPrecedingHeading = Boolean.TRUE;

                // German version uses paragraphs instead of headings for informal tables
            } else if (child instanceof TextPElement) {
                tableTitle = ((TextPElement) child).getTextContent();
            } else if (child instanceof TableTableElement) {
                if (hasPrecedingHeading || (tableTitle != null && tableTitle.contains("Mapping"))) {
                    extractDataFromTable((TableTableElement) child, odtFileName, odtFilePath, tableTitle);
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
                + "********* Specification document: '{}'\n"
                + "****************************************************************\n\n", odtFileName);
    }

    private void extractDataFromTable(TableTableElement tableElement, String fileName, String outputPath,
            String title) {
        LOG.debug("Table Heading: '{}'", title);
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
        LOG.debug("ColumnCount is '{}'!", columnCount);

        if (table.getHeaderRowCount() != 1) {
            LOG.debug("Ignoring '{}' as no header row was found!", title);
            return;
        }

        if (!isValidTableSize(columnCount)) {
            return;
        }

        mIsXML = (columnCount == WG3_XML_NORMATIVE_TABLE_SIZE || columnCount == WG3_XML_INFORMATIVE_TABLE_SIZE);
        if (mIsXML) {
            mIsUBL = title.contains("UBL");
        } else {
            mIsUBL = Boolean.FALSE;
        }

        assert table.getHeaderColumnCount() == 0;
        OdfTableRow headerRow = table.getRowByIndex(0);
        OdfTableCell firstHeaderCell = headerRow.getCellByIndex(0);
        // final test: first cell conent of header row have to be correct!

        if (!isCorrectHeaderCell(columnCount, firstHeaderCell)) {
            LOG.error("ERROR: WRONG TABLE: '{}' + IS NOT A TABLE FOR DATA EXTRACTION!", mTableId);
            return;
        }

        LOG.info(
                "\n--------------------------------------------------------------------------------------------------------\n");
        LOG.info("Table Heading:\n\t{}\n", title);
        LOG.info("--------------------------------------------------------------------------------------------------------\n\n");
        // *********
        // CONTENT ROWS
        // *********
        processTableRows(table, fileName, outputPath, title, columnCount);
    }

    private boolean isValidTableSize(int columnCount) {
        return columnCount == WG3_XML_NORMATIVE_TABLE_SIZE
                || columnCount == WG3_XML_INFORMATIVE_TABLE_SIZE
                || columnCount == WG3_EDIFACT_NORMATIVE_TABLE_SIZE
                || columnCount == WG3_EDIFACT_INFORMATIVE_TABLE_SIZE;
    }

    private boolean isCorrectHeaderCell(int columnCount, OdfTableCell firstHeaderCell) {
        String cellContent = getCellContent(firstHeaderCell);
        return ((columnCount == WG3_XML_NORMATIVE_TABLE_SIZE || columnCount == WG3_EDIFACT_NORMATIVE_TABLE_SIZE)
                && cellContent.equals(WG3_SEMANTIC_TABLE_HEADINGS[0].getLabel()))
                || ((columnCount == WG3_XML_INFORMATIVE_TABLE_SIZE || columnCount == WG3_EDIFACT_INFORMATIVE_TABLE_SIZE)
                        && cellContent.equals(WG3_SYNTAX_TABLE_HEADINGS[0].getLabel()));
    }

    private void processTableRows(OdfTable table, String fileName, String outputPath, String title, int columnCount) {
        int rowCount = table.getRowCount();
        NodeSemantic semanticNode = null;
        NodeSyntax syntaxNode = null;
        // only required for the informative cell to remember the two Syntax contents,
        // until Semantic was created
        String informativeTable_CellContentOne = null;
        String informativeTable_CellContentTwo = null;
        String informativeTable_CellContentThree = null;
        int syntax_header_length = 2; // the default, only EDIFACT has 3 columns

        for (int r = 1; r < rowCount; r++) {
            OdfTableRow tr = table.getRowByIndex(r);
            LOG.debug("\n**** NEW ROW ****");
            int cellCount = tr.getCellCount();
            boolean isNewSemantic = Boolean.FALSE;
            syntaxNode = null;

            for (int c = 0; c < cellCount; c++) {
                OdfTableCell tc = tr.getCellByIndex(c);
                String cellContent = getCellContent(tc);
                LOG.debug("**** NEW CELL ****");
                LOG.debug(": {}", cellContent);
                // First Part of Heading - Semantics
                if (isNormativeTable(columnCount)) {
                    if (c < WG3_SEMANTIC_TABLE_HEADINGS.length) {
                        // For each Row:
                        if (c == 0) {
                            if (!cellContent.replaceAll(LEADING_TRAILING_WHITESPACES, "").isEmpty()) {
                                String finalCellContent = NodeSemantic.unifyID(cellContent);
                                semanticNode = new NodeSemantic(finalCellContent, mTableId);
                                isNewSemantic = Boolean.TRUE;
                            } else {
                                LOG.debug("IS EMPTY!!!");
                            }
                        }
                        if (isNewSemantic) {
                            mapSemantic(cellContent, c, semanticNode);
                        }
                    } else { // Second Part of Row - Syntax
                        syntaxNode = createSyntaxNode(cellContent, semanticNode, c, columnCount);
                        if (syntaxNode != null) {
                            mapSyntax(cellContent, syntaxNode, getSyntaxHeading(c, columnCount));
                        }
                    }
                } else { // informative table
                    // in case of EDIFACT 3-4 there are 3 columns instead of 2
                    if (columnCount == WG3_EDIFACT_INFORMATIVE_TABLE_SIZE) {
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
                    } else {
                        // Second Part of Row - Semantic Model
                        if (c == syntax_header_length) {
                            cellContent = cellContent.replaceAll(LEADING_TRAILING_WHITESPACES, "");
                            if (!cellContent.isEmpty()) {
                                String unifiedId = NodeSemantic.unifyID(cellContent);
                                if (!NodeSemantic.allSemanticNodes.containsKey(unifiedId)) {
                                    semanticNode = new NodeSemantic(unifiedId, mTableId);
                                    NodeSemantic.allSemanticNodes.put(unifiedId, semanticNode);
                                    isNewSemantic = true;
                                } else {
                                    semanticNode = NodeSemantic.allSemanticNodes.get(unifiedId);
                                }
                            }else {
                                break;
                            }
                        }
                        if (isNewSemantic) {
                            mapSemantic(cellContent, c - syntax_header_length, semanticNode);
                        }
                        if (c == cellCount - 1) {
                            syntaxNode = createSyntaxNode(informativeTable_CellContentOne, semanticNode, 0,
                                    columnCount);
                            mapSyntax(informativeTable_CellContentTwo, syntaxNode, WG3_SYNTAX_TABLE_HEADINGS[2]);
                            if (!mIsXML) {
                                mapSyntax(informativeTable_CellContentThree, syntaxNode, WG3_SYNTAX_TABLE_HEADINGS[3]);
                            }
                        }
                    }
                }
            }
        }
        if (semanticNode != null) {
            postProcessTable(semanticNode, fileName, outputPath, title, columnCount);
        }
    }

    private boolean isNormativeTable(int columnCount) {
        return columnCount == WG3_XML_NORMATIVE_TABLE_SIZE || columnCount == WG3_EDIFACT_NORMATIVE_TABLE_SIZE;
    }

    private NodeSyntax createSyntaxNode(String cellContent, NodeSemantic semanticNode, int c, int columnCount) {
        NodeSyntax syntaxNode = null;
        int i = c - WG3_SEMANTIC_TABLE_HEADINGS.length;
        if (i == 0) {
            if (mIsXML) {
                syntaxNode = mIsUBL ? new NodeUblXml(cellContent, semanticNode)
                        : new NodeXml(cellContent, semanticNode);
            } else {
                syntaxNode = new NodeEdifact(cellContent, semanticNode);
            }
        }
        return syntaxNode;
    }

    private SyntaxHeading getSyntaxHeading(int c, int columnCount) {
        int i = c - WG3_SEMANTIC_TABLE_HEADINGS.length;
        if (columnCount == WG3_EDIFACT_NORMATIVE_TABLE_SIZE) {
            return i < 2 ? WG3_SYNTAX_TABLE_HEADINGS[i + 1] : WG3_SYNTAX_TABLE_HEADINGS[i + 2];
        } else {
            return i < 3 ? WG3_SYNTAX_TABLE_HEADINGS[i] : WG3_SYNTAX_TABLE_HEADINGS[i + 1];
        }
    }

    private void postProcessTable(NodeSemantic semanticNode, String fileName, String outputPath, String title,
            int columnCount) {
        semanticNode.validateCardinalityMismatches();
        semanticNode.showSemanticIDAnomalies();

        boolean isNormative = isNormativeTable(columnCount);
        semanticNode.createXMLFile(fileName, outputPath, title, isNormative);
        semanticNode.createSubXMLFile(fileName, outputPath, title, isNormative);
        semanticNode.createSemanticXMLFile(fileName.replace(".xml", ".json"), outputPath, title, isNormative);
        semanticNode.createSemanticJSONFile(fileName, outputPath, title, isNormative);

        TypeStatistic.table(title, mIsXML, mIsUBL);
        clearAll();
    }

    private String mapSemantic(String cellContent, int c, NodeSemantic semanticNode) {
        SemanticHeading columnType_Semantic = WG3_SEMANTIC_TABLE_HEADINGS[c];
        String label = columnType_Semantic.getLabel();
        if (!cellContent.isEmpty()) {
            switch (columnType_Semantic) {
                case ID:
                    break; // already prior set
                case LEVEL:
                    semanticNode.setLevel(parseLevel(cellContent));
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


    /**
     * Parses cell content that may contain either an integer as a string
     * or a sequence of '+' characters where the count represents the integer value.
     *
     * @param cellContent The string to parse
     * @return The parsed integer value
     * @throws NumberFormatException if the string is neither a valid integer nor a sequence of '+' characters
     */
    public static int parseLevel(String cellContent) {
        if (cellContent == null || cellContent.isEmpty()) {
            throw new NumberFormatException("Input string is null or empty");
        }

        // Check if the string contains only '+' characters
        if (cellContent.matches("\\++")) {
            // Count the number of '+' characters
            return cellContent.length();
        } else {
            int foo =-1;

            try{
            // Try to parse as a regular integer
            foo = Integer.parseInt(cellContent);

            }catch(NumberFormatException e){
                System.out.println("AWAIT!");
            }
            return foo;
        }
    }


    private String mapSyntax(String cellContent, NodeSyntax syntaxNode, SyntaxHeading columnType) {
        String label = columnType.getLabel();
        if (cellContent != null) {
            cellContent = cellContent.replaceAll(LEADING_TRAILING_WHITESPACES, "");
            if (!cellContent.isEmpty()) {
                switch (columnType) {
                    case TYPE: // only for XML
                        if(syntaxNode instanceof NodeXml){
                            ((NodeXml) syntaxNode).setType(cellContent);
                        }
                        break;
                    case CARD:
                        if (mIsXML) {
                            if(syntaxNode instanceof NodeXml){
                                ((NodeXml) syntaxNode).setCardinalityXml(cellContent);
                            }
                        } else {
                            if(syntaxNode instanceof NodeEdifact){
                                ((NodeEdifact) syntaxNode).setCardinalityEdifact(cellContent);
                            }
                        }
                        break;
                    case NAME: // only for EDIFACT
                        if(syntaxNode instanceof NodeEdifact){
                            ((NodeEdifact) syntaxNode).setName(cellContent);
                        }
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
            java.util.logging.Logger.getLogger(OdtTableDataExtraction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(OdtTableDataExtraction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return file.getAbsolutePath();
    }
}
