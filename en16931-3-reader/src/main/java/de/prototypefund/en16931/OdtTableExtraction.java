/** **********************************************************************
 *
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 *********************************************************************** */
package de.prototypefund.en16931;

import static de.prototypefund.en16931.SemanticNode.LEADING_TRAILING_WHITESPACES;
import de.prototypefund.en16931.SemanticNode.SemanticHeading;
import de.prototypefund.en16931.XmlNode.XmlHeading;
import de.prototypefund.utils.ResourceUtilities;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collection;
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
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextParagraph;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OdtTableExtraction {

    private static final Logger LOG;


    // To ease the comparison of logs, there is a simplification of logging to one line without a date:
    static {
        // " [Mo Okt 15 21:39:48 MESZ 2018]"
        // "%4$s: %5$s [%1$tc]%n"
        // System.setProperty("java.util.logging.SimpleFormatter.format","%4$s: %5$s [%1$tc]%n");
        // ": Mon Oct 15 22:01:36 CEST 2018SEVERE: "
        // see https://docs.oracle.com/javase/7/docs/api/index.html?java/util/logging/SimpleFormatter.html
        // System.setProperty("java.util.logging.SimpleFormatter.format","%4$s: %5$s: %1$s");
        // System.setProperty("java.util.logging.SimpleFormatter.format","%p : %m");
        LOG = LoggerFactory.getLogger(OdtTableExtraction.class);
    }

    OdfTextDocument odtDoc;
    OdfTable odtTable;
    SemanticHeading[] SEMANTIC_TABLE_HEADINGS = SemanticNode.SemanticHeading.values();
    XmlHeading[] SYNTAX_TABLE_HEADINGS_FULL = XmlNode.XmlHeading.values();
    private String mTableId = null;
    private int NORMATIVE_TABLE_SIZE = 11;
    private int INFORMATIVE_TABLE_SIZE = 8;
    private static final String ODT_SUFFIX = ".odt";
    private static final String WORKING_DIRECTORY = "user.dir";

    /** @param odtFileName the file name of the specification or a directory where specifications are any descendant documents! */
    public void collectSpecData(String odtFileName) throws Exception {
        System.out.println("odtFileName" + odtFileName);
        String absPath = null;
        try{
            absPath = ResourceUtilities.getAbsolutePath(odtFileName);
        }catch(FileNotFoundException e){
            // expected if file is not in Java class path
        }
        System.out.println("absPath1" + absPath);
        if(absPath == null){
            absPath = System.getProperty(WORKING_DIRECTORY);
        }
        System.out.println("absPath2" + absPath);
        collectSpecData(new File(absPath));
    }


    /** If file is a directory searches within children and provides all found documents that have an '.odt' suffix to the data extractor. */
    private void collectSpecData(File f) throws Exception{
        if(f.isDirectory()){
            String absPath = f.getAbsolutePath();
            LOG.info("Extracting data from directory: " + absPath);
            for(String childPath : f.list()){
                collectSpecData(new File(absPath + File.separatorChar + childPath));
            }
        }else{
            String absPath = f.getAbsolutePath();
            if(absPath.endsWith(ODT_SUFFIX)){
                LOG.info("Extracting data from file: " + absPath);
                extractData(f);
            }else{
                LOG.info("As without file suffix '.odt' ignoring: " + absPath);
            }
        }
    }



    /** @param odtFile <code>File</code> representing the en16931 specification  */
    private void extractData(File odtFile) throws Exception {
        odtDoc = OdfTextDocument.loadDocument(odtFile);
        String absPath = odtFile.getAbsolutePath();
        String odtFileName = absPath.substring(absPath.lastIndexOf(File.separatorChar) + 1);
        String odtFilePath = absPath.substring(0, absPath.lastIndexOf(File.separatorChar) + 1);
        LOG.info("\n*** Specification document: '" + odtFileName + "'\n\n\n");;
        // traverse top level user objects
        OfficeTextElement root = odtDoc.getContentRoot();
        NodeList topChildren = root.getChildNodes();

        int i = 0;
        Node child = null;
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
            }else if (child instanceof TextPElement) {

                tableTitle = null;
                TextPElement p = ((TextPElement) child);
                tableTitle = p.getTextContent();

            }else if (child instanceof TableTableElement) {
                if(hasPrecedingHeading || tableTitle != null && tableTitle.contains("Mapping")){
                    extractTableData(((TableTableElement) child), odtFileName, odtFilePath, tableTitle);
                }
                hasPrecedingHeading = Boolean.FALSE;
                tableTitle = null;

                // there shall be no other element between the preceding heading/paragraph (title) and the table
            } else if (child instanceof OdfElement){
                hasPrecedingHeading = Boolean.FALSE;
                tableTitle = null;
            }
        }
    }

    private void extractTableData(TableTableElement tableElement, String fileName, String outputPath, String title) {
        LOG.info("\n\nTable Heading: '" + title + "'\n");
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
        LOG.info("ColumnCount is '" + columnCount + "!'\n");

        if (table.getHeaderRowCount() != 1) {
            LOG.info("Ignoring '" + title + " as no header row was found!'\n");

        } else if (columnCount == INFORMATIVE_TABLE_SIZE || columnCount == NORMATIVE_TABLE_SIZE) {
            assert table.getHeaderColumnCount() == 0;
            OdfTableRow tr = table.getRowByIndex(0);
            int cellCount = tr.getCellCount();
            for (int i = 0; i < cellCount; i++) {
                OdfTableCell tc = tr.getCellByIndex(i);

                if (i == 0) {
                    LOG.debug("Header: '" + getCellContent(tc) + "'");
                    if (columnCount == NORMATIVE_TABLE_SIZE) {
                        LOG.debug("label: '" + SEMANTIC_TABLE_HEADINGS[0].getLabel() + "'");
                    } else {
                        LOG.debug("label: '" + SYNTAX_TABLE_HEADINGS_FULL[0].getLabel() + "'");
                    }
                    if (!((columnCount == NORMATIVE_TABLE_SIZE && getCellContent(tc).equals(SEMANTIC_TABLE_HEADINGS[0].getLabel()))
                            || (columnCount == INFORMATIVE_TABLE_SIZE && getCellContent(tc).equals(SYNTAX_TABLE_HEADINGS_FULL[0].getLabel())))) {
                        i = cellCount; // end all loops
                        LOG.error("WRONG TABLE: THIS IS NOT A TABLE FOR DATA EXTRACTION!!!");
                        break;
                    }
                }
            }
            //*********
            // CONTENT ROWS
            //*********
            int rowCount = table.getRowCount();
            SemanticNode semanticNode = null;
            XmlNode xmlNode = null;
            // only required for the informative cell to remember the two Syntax contents, until Semantic was created
            String informativeTable_CellContentOne = null;
            String informativeTable_CellContentTwo = null;
            for (int r = 1; r < rowCount; r++) {
                tr = table.getRowByIndex(r);
                LOG.debug("\n**** NEW ROW ****");
                cellCount = tr.getCellCount();

                xmlNode = null;
                for (int c = 0; c < cellCount; c++) {
                    OdfTableCell tc = tr.getCellByIndex(c);
                    String cellContent = getCellContent(tc);
                    String label = null;
                    LOG.debug("**** NEW CELL ****");
                    LOG.debug(label + ": " + cellContent);

                    // First Part of Heading - Semantics
                    if (columnCount == NORMATIVE_TABLE_SIZE) {
                        if (c < SEMANTIC_TABLE_HEADINGS.length) {
                            // For each Row:
                            if (c == 0) {
                                if (cellContent.replaceAll(LEADING_TRAILING_WHITESPACES, "").isEmpty()) {
                                    LOG.debug("IS EMPTY!!!");
                                } else {
                                    // errorhandling of previous node
                                    if (SpecificationFixes.hasError) {
                                        SpecificationFixes.flushErrors(semanticNode);
                                    }
                                    semanticNode = new SemanticNode(cellContent, mTableId);
                                }
                            }
                            label = mapSemantic(cellContent, c, semanticNode);

                            // Second Part of Row - XML Syntax
                        } else {
                            // find the according type to this column from the header
                            XmlHeading columnType = SYNTAX_TABLE_HEADINGS_FULL[c - SEMANTIC_TABLE_HEADINGS.length];
                            if ((c - SEMANTIC_TABLE_HEADINGS.length) == 0) {
                                xmlNode = new XmlNode(cellContent, semanticNode);
                            }
                            label = mapSyntax(cellContent, xmlNode, columnType);
                        }
                    } else {
                        if (c < 2) {
                            // For each Row:

                            // find the according type to this column from the header
                            if (c == 0) {
                                // this time the syntax mapping has 3 columns less..
                                informativeTable_CellContentOne = cellContent;
                            }
                            if (c == 1) {
                                informativeTable_CellContentTwo = cellContent;
                            }
                       // Second Part of Row - Semantic Model
                        } else {
                            if (c == 2) {
                                cellContent = cellContent.replaceAll(LEADING_TRAILING_WHITESPACES, "");
                                // same semantic node, if there is NO ID or the previous ID
                                if (cellContent.isEmpty()){ // semantic ID is empty
                                    break; // just boilerplate
                                }else { // semantic ID exist (might be not as the one before)
                                    // in the informative table the IDs are defined not adjacent
                                    semanticNode = SemanticNode.allSemanticNodes.get(cellContent);
                                    if(semanticNode == null){
                                        semanticNode = new SemanticNode(cellContent, mTableId);
                                    }
                                }
                            } else {
                                label = mapSemantic(cellContent, c - 2, semanticNode);
                            }
                            // Finally after all semantics have been added, add the syntax that was remembered from the start of the informative table
                            if (c == cellCount - 1) {
                                xmlNode = new XmlNode(informativeTable_CellContentOne, semanticNode);
                                mapSyntax(informativeTable_CellContentTwo, xmlNode, SYNTAX_TABLE_HEADINGS_FULL[2]);
                            }
                        }
                    }
                }
            }
            // dump the table model into an XML file
            semanticNode.createXMLFile(fileName, outputPath, title);
            if(columnCount == NORMATIVE_TABLE_SIZE){
                semanticNode.createSubXMLFile(fileName, outputPath, title);
            }
            // log all duplicated XML nodes
//2DO            semanticNode.logDuplicateXPathErrors();
            clearAll();
        }
    }

    private String mapSemantic(String cellContent, int c, SemanticNode semanticNode) {
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

    private String mapSyntax(String cellContent, XmlNode xmlNode, XmlHeading columnType) {
        String label = columnType.getLabel();
        cellContent = cellContent.replaceAll(LEADING_TRAILING_WHITESPACES, "");
        if (!cellContent.isEmpty()) {
            switch (columnType) {
                case TYPE:
                    xmlNode.setType(cellContent);
                    break;
                case CARD:
                    xmlNode.setCardinality(cellContent);
                    break;
                case MATCH:
                    xmlNode.setMisMatch(cellContent);
                    break;
                case RULES:
                    xmlNode.setRules(cellContent);
                    break;
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
            if (node instanceof OdfTextParagraph) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                OdfTextParagraph p = ((OdfTextParagraph) node);
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
            if(SemanticNode.allSemanticNodes != null){
                Collection<SemanticNode> semanticNodes = SemanticNode.allSemanticNodes.values();
                for (SemanticNode s : semanticNodes) {
                    if(s.xmlRepresentations != null){
                        s.xmlRepresentations.clear();
                    }
                }
                SemanticNode.allSemanticNodes.clear();
            }
            if(XmlNode.allXmlNodes != null){
                XmlNode.allXmlNodes.clear();
            }
            if(XmlNode.duplicateXPathList != null){
                XmlNode.duplicateXPathList.clear();
            }



        } catch (Throwable e) {
            LoggerFactory.getLogger(SemanticNode.class.getName()).error(e.getMessage(), e);
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
