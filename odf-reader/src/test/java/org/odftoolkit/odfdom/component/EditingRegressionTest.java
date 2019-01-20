/*
 * Copyright 2012 The Apache Software Foundation.
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
package org.odftoolkit.odfdom.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.odftoolkit.odfdom.component.OperationConstants.DEBUG_OPERATIONS;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.odftoolkit.odfdom.utils.ResourceUtilities;

/**
 * Loads a document with tables and gathers its operations. Gathered operations
 * will be applied to an empty text document. The changed text document will be
 * saved and reloaded. New gathered operations will be compared with the
 * original ones, expected to be identical!
 *
 * @author svanteschubert
 */
public class EditingRegressionTest extends RoundtripTestHelper {

    private static final Logger LOG = Logger.getLogger(EditingRegressionTest.class.getName());

    public EditingRegressionTest() {
    }
    private static final String OUTPUT_DIRECTORY = "regression-tests" + File.separatorChar;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Creating the output directory for the tests
        File outputDir = ResourceUtilities.newTestOutputFile(OUTPUT_DIRECTORY);
        outputDir.mkdir();
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Inserting a formula
     */
    public void insertFormulaTest() {
        final String SOURCE_FILE_NAME_TRUNC = "attributes";

        String firstEditOperations = "["
            + "{\"start\":[0,4],\"contents\":[[{\"value\":\"=TODAY()\"}]],\"opl\":1,\"name\":\"setCellContents\",\"sheet\":0,\"osn\":38},"
            + "{\"start\":4,\"attrs\":{\"row\":{\"customHeight\":false,\"height\":550,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":39}"
            + "]";

        List<String> editOperations = new ArrayList();
        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".ods", getTestMethodName(), editOperations);
        // TODO: Performance Issues during loading!
//      super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Inserting and Deleting rows and columns
     */
    public void insertDeletRowsAndColumnsTest() {
        final String SOURCE_FILE_NAME_TRUNC = "simple-styled";

        String firstEditOperations = "["
            + "{\"start\":[5,15],\"contents\":[[{\"value\":\"test\"}]],\"opl\":1,\"name\":\"setCellContents\",\"sheet\":0,\"osn\":46},"
            + "{\"start\":15],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":166,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":47},"
            + "{\"start\":[1,4],\"opl\":1,\"name\":\"fillCellRange\",\"value\":null,\"sheet\":0,\"end\":[2,6],\"osn\":48},"
            + "{\"start\":[4],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":1508,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":49},"
            + "{\"start\":[5],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":2508,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":50},"
            + "{\"start\":[6],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":3508,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":51},"
            + "{\"start\":[4],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":4508,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":52},"
            + "{\"start\":[5],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":5508,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":53},"
            + "{\"start\":[6],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":6508,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":54},"
            + "{\"start\":[9],\"end\":[13],\"opl\":1,\"name\":\"insertRows\",\"sheet\":0,\"osn\":55},"
            + "{\"start\":[2],\"end\":[3],\"opl\":1,\"name\":\"deleteRows\",\"sheet\":0,\"osn\":56},"
            + "{\"start\":[2],\"end\":[5],\"opl\":1,\"name\":\"insertColumns\",\"sheet\":0,\"osn\":57},"
            + "{\"start\":[0],\"end\":[5],\"opl\":1,\"name\":\"insertColumns\",\"sheet\":0,\"osn\":57},"
            + "{\"start\":[13],\"opl\":1,\"name\":\"insertColumns\",\"sheet\":0,\"osn\":57},"
            + "{\"start\":[0],\"end\":[4],\"name\":\"deleteColumns\",\"sheet\":0,\"osn\":58},"
            + "{\"start\":[7],\"name\":\"deleteColumns\",\"sheet\":0,\"osn\":58}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Create content within a spreadsheet
     */
    public void createSpreadsheetContentTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        String firstEditOperations = "["
            + "{\"name\":\"setDocumentAttributes\",\"attrs\":{\"document\":{\"defaultTabStop\":1270}}},\n"
            + "{\"name\":\"insertStyleSheet\",\"type\":\"cell\",\"styleId\":\"default_cell_style\",\"attrs\":{\"character\":{\"fontName\":\"Liberation Sans\",\"fontNameAsian\":\"DejaVu Sans\",\"fontNameComplex\":\"DejaVu Sans\",\"language\":\"en-US\"}},\"default\":true,\"hidden\":true,\"styleName\":\"Default Cell Style\"},\n"
            + "{\"name\":\"insertStyleSheet\",\"type\":\"cell\",\"styleId\":\"Default\",\"attrs\":{\"character\":{\"fontNameComplex\":\"FreeSans\"}},\"parent\":\"default_cell_style\"},\n"
            + "{\"name\":\"insertStyleSheet\",\"type\":\"cell\",\"styleId\":\"Heading\",\"attrs\":{\"character\":{\"bold\":true,\"fontSize\":16,\"italic\":true},\"paragraph\":{\"alignment\":\"center\"}},\"parent\":\"Default\"},\n"
            + "{\"name\":\"insertStyleSheet\",\"type\":\"cell\",\"styleId\":\"Heading1\",\"attrs\":{},\"parent\":\"Heading\"},\n"
            + "{\"name\":\"insertStyleSheet\",\"type\":\"cell\",\"styleId\":\"Result\",\"attrs\":{\"character\":{\"bold\":true,\"italic\":true,\"underline\":true}},\"parent\":\"Default\"},\n"
            + "{\"name\":\"insertStyleSheet\",\"type\":\"cell\",\"styleId\":\"Result2\",\"attrs\":{},\"parent\":\"Result\"},\n"
            + "{\"name\":\"setDocumentAttributes\",\"attrs\":{\"document\":{\"fileFormat\":\"odf\"}}},\n"
            + "{\"name\":\"insertFontDescription\",\"attrs\":{\"family\":\"'Liberation Sans'\",\"familyGeneric\":\"swiss\",\"pitch\":\"variable\"},\"fontName\":\"Liberation Sans\"},\n"
            + "{\"name\":\"insertFontDescription\",\"attrs\":{\"family\":\"'DejaVu Sans'\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"fontName\":\"DejaVu Sans\"},\n"
            + "{\"name\":\"insertFontDescription\",\"attrs\":{\"family\":\"FreeSans\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"fontName\":\"FreeSans\"},\n"
            + "{\"name\":\"setDocumentAttributes\",\"attrs\":{\"document\":{\"fileFormat\":\"odf\"}}},\n"
            + "{\"name\":\"insertSheet\",\"attrs\":{\"sheet\":{\"visible\":true}},\"sheet\":0,\"sheetName\":\"Sheet1\"},\n"
            + "{\"name\":\"setColumnAttributes\",\"start\":[0],\"end\":3,\"attrs\":{\"character\":{\"fontNameComplex\":\"FreeSans\"},\"column\":{\"width\":2258}},\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":[0],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":1],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setCellContents\",\"start\":[1,1],\"contents\":[[{\"attrs\":{\"cell\":{\"value\":\"1\"}},\"value\":\"1\"},{\"attrs\":{\"cell\":{\"value\":\"2\"}},\"value\":\"2\"},{\"attrs\":{\"cell\":{\"value\":\"3\"}},\"value\":\"3\"}]],\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":[2],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setCellContents\",\"start\":[1,2],\"contents\":[[{\"attrs\":{\"cell\":{\"value\":\"2\"}},\"value\":\"2\"},{\"attrs\":{\"cell\":{\"value\":\"3\"}},\"value\":\"3\"},{\"attrs\":{\"cell\":{\"value\":\"4\"}},\"value\":\"4\"}]],\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":[3],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setCellContents\",\"start\":[1,3],\"contents\":[[{\"attrs\":{\"cell\":{\"value\":\"3\"}},\"value\":\"3\"},{\"attrs\":{\"cell\":{\"value\":\"4\"}},\"value\":\"4\"},{\"attrs\":{\"cell\":{\"value\":\"5\"}},\"value\":\"5\"}]],\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":[4],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setCellContents\",\"start\":[1,4],\"contents\":[[{\"attrs\":{\"cell\":{\"value\":\"4\"}},\"value\":\"4\"},{\"attrs\":{\"cell\":{\"value\":\"5\"}},\"value\":\"5\"},{\"attrs\":{\"cell\":{\"value\":\"6\"}},\"value\":\"6\"}]],\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":[5],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setCellContents\",\"start\":[1,5],\"contents\":[[{\"attrs\":{\"cell\":{\"value\":\"5\"}},\"value\":\"5\"},{\"attrs\":{\"cell\":{\"value\":\"6\"}},\"value\":\"6\"},{\"attrs\":{\"cell\":{\"value\":\"7\"}},\"value\":\"7\"}]],\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":[6],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setCellContents\",\"start\":[1,6],\"contents\":[[{\"attrs\":{\"cell\":{\"value\":\"6\"}},\"value\":\"6\"},{\"attrs\":{\"cell\":{\"value\":\"7\"}},\"value\":\"7\"},{\"attrs\":{\"cell\":{\"value\":\"8\"}},\"value\":\"8\"}]],\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":[7],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setCellContents\",\"start\":[1,7],\"contents\":[[{\"attrs\":{\"cell\":{\"value\":\"7\"}},\"value\":\"7\"},{\"attrs\":{\"cell\":{\"value\":\"8\"}},\"value\":\"8\"},{\"attrs\":{\"cell\":{\"value\":\"9\"}},\"value\":\"9\"}]],\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":[8],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setCellContents\",\"start\":[1,8],\"contents\":[[{\"attrs\":{\"cell\":{\"value\":\"8\"}},\"value\":\"8\"},{\"attrs\":{\"cell\":{\"value\":\"9\"}},\"value\":\"9\"},{\"attrs\":{\"cell\":{\"value\":\"10\"}},\"value\":\"10\"}]],\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":[9],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setCellContents\",\"start\":[1,9],\"contents\":[[{\"attrs\":{\"cell\":{\"value\":\"9\"}},\"value\":\"9\"},{\"attrs\":{\"cell\":{\"value\":\"10\"}},\"value\":\"10\"},{\"attrs\":{\"cell\":{\"value\":\"11\"}},\"value\":\"11\"}]],\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":[10],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setCellContents\",\"start\":[1,10],\"contents\":[[{\"attrs\":{\"cell\":{\"value\":\"10\"}},\"value\":\"10\"},{\"attrs\":{\"cell\":{\"value\":\"11\"}},\"value\":\"11\"},{\"attrs\":{\"cell\":{\"value\":\"12\"}},\"value\":\"12\"}]],\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":[11],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setCellContents\",\"start\":[1,11],\"contents\":[[{\"attrs\":{\"cell\":{\"value\":\"11\"}},\"value\":\"11\"},{\"attrs\":{\"cell\":{\"value\":\"12\"}},\"value\":\"12\"},{\"attrs\":{\"cell\":{\"value\":\"13\"}},\"value\":\"13\"}]],\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":[12],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setCellContents\",\"start\":[1,12],\"contents\":[[{\"attrs\":{\"cell\":{\"value\":\"12\"}},\"value\":\"12\"},{\"attrs\":{\"cell\":{\"value\":\"13\"}},\"value\":\"13\"},{\"attrs\":{\"cell\":{\"value\":\"14\"}},\"value\":\"14\"}]],\"sheet\":0},\n"
            + "{\"name\":\"setRowAttributes\",\"start\":[13],\"attrs\":{\"row\":{\"customHeight\":false,\"height\":427}},\"sheet\":0},\n"
            + "{\"name\":\"setCellContents\",\"start\":[1,13],\"contents\":[[{\"attrs\":{\"cell\":{\"value\":\"13\"}},\"value\":\"13\"},{\"attrs\":{\"cell\":{\"value\":\"14\"}},\"value\":\"14\"},{\"attrs\":{\"cell\":{\"value\":\"15\"}},\"value\":\"15\"}]],\"sheet\":0}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Deleting content range.
     */
    public void deleteRangeTest() {
        final String SOURCE_FILE_NAME_TRUNC = "simple";

        String firstEditOperations = "["
            + "{\"start\":[1,4],\"opl\":1,\"name\":\"fillCellRange\",\"value\":null,\"sheet\":0,\"end\":[2,6],\"osn\":47},"
            + "{\"start\":4,\"attrs\":{\"row\":{\"customHeight\":false,\"height\":508,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":48},"
            + "{\"start\":5,\"attrs\":{\"row\":{\"customHeight\":false,\"height\":508,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":49},"
            + "{\"start\":6,\"attrs\":{\"row\":{\"customHeight\":false,\"height\":508,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":50},"
            + "{\"start\":4,\"attrs\":{\"row\":{\"customHeight\":false,\"height\":508,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":51},"
            + "{\"start\":5,\"attrs\":{\"row\":{\"customHeight\":false,\"height\":508,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":52},"
            + "{\"start\":6,\"attrs\":{\"row\":{\"customHeight\":false,\"height\":508,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":53}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Adding a table with two rows, deleting the first
     */
    public void rowDeletionTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        String firstEditOperations = "["
            + "{\"styleId\":\"TableGrid\",\"styleName\":\"Table Grid\",\"attrs\":{\"wholeTable\":{\"paragraph\":{\"lineHeight\":{\"value\":100,\"type\":\"percent\"}},\"table\":{\"borderInsideVert\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderInsideHor\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderBottom\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderLeft\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderRight\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingBottom\":0,\"paddingLeft\":190,\"paddingRight\":190}}},\"name\":\"insertStyleSheet\",\"opl\":1,\"uiPriority\":59,\"type\":\"table\",\"osn\":16},"
            + "{\"start\":[1],\"attrs\":{\"styleId\":\"TableGrid\",\"table\":{\"tableGrid\":[1000],\"width\":\"auto\",\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":17},"
            + "{\"count\":2,\"start\":[1,0],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":true,\"osn\":18},"
            + "{\"start\":[1,0],\"name\":\"delete\",\"opl\":1,\"osn\":19}"
            + "]";
        List<String> editOperations = new ArrayList();
        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Adding content within repeated rows/cells.
     */
    public void splitRepeatedRowsCellsTest() {
        final String SOURCE_FILE_NAME_TRUNC = "simple";

        String firstEditOperations = "["
            + "{\"start\":[5,20],\"contents\":[[{\"attrs\":{\"cell\":{\"numberFormat\":{\"id\":0,\"code\":\"\"}}},\"value\":31}]],\"opl\":1,\"name\":\"setCellContents\",\"sheet\":0,\"osn\":47},"
            + "{\"start\":20,\"attrs\":{\"row\":{\"customHeight\":false,\"height\":466,\"visible\":true}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":48}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * ODT with table -copy&paste a table: after saving=> inserted table is lost
     */
    public void copyAndPasteTableTest() {
        final String SOURCE_FILE_NAME_TRUNC = "simple_table";
        String firstEditOperations = "["
            + "{\"text\":\"Text\",\"start\":[3,0],\"opl\":4,\"name\":\"insertText\",\"osn\":57},"
            + "{\"start\":[3,4],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":61},"
            + "{\"start\":[4,0],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":62},"
            + "{\"start\":[4],\"attrs\":{\"styleId\":\"Standard\",\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"nextStyleId\":null,\"listStyleId\":null,\"borderBottom\":null,\"listLevel\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"tabStops\":null,\"marginBottom\":null,\"listLabelHidden\":null,\"listStartValue\":null,\"indentRight\":null,\"indentLeft\":null,\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":63},"
            + "{\"text\":\"Tabelle odt\",\"start\":[4,0],\"attrs\":{\"styleId\":null,\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"opl\":1,\"name\":\"insertText\",\"osn\":64},"
            + "{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\",\"osn\":65},"
            + "{\"start\":[5],\"attrs\":{\"styleId\":\"Standard\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":66},"
            + "{\"start\":[6],\"attrs\":{\"table\":{\"tableGrid\":[16383,16383,16383,16383]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":67},"
            + "{\"start\":[6,0],\"name\":\"insertRows\",\"opl\":1,\"osn\":68},"
            + "{\"count\":1,\"start\":[6,0,0],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":69},"
            + "{\"start\":[6,0,0,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":70},"
            + "{\"count\":1,\"start\":[6,0,1],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":71},"
            + "{\"start\":[6,0,1,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":72},"
            + "{\"count\":1,\"start\":[6,0,2],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":73},"
            + "{\"start\":[6,0,2,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":74},"
            + "{\"count\":1,\"start\":[6,0,3],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2,\"space\":97},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2,\"space\":97},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2,\"space\":97},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2,\"space\":97},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":75},"
            + "{\"start\":[6,0,3,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":76},"
            + "{\"start\":[6,1],\"name\":\"insertRows\",\"opl\":1,\"osn\":77},"
            + "{\"count\":1,\"start\":[6,1,0],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":78},"
            + "{\"start\":[6,1,0,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":79},"
            + "{\"count\":1,\"start\":[6,1,1],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":80},"
            + "{\"start\":[6,1,1,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":81},"
            + "{\"count\":1,\"start\":[6,1,2],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":82},"
            + "{\"start\":[6,1,2,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":83},"
            + "{\"count\":1,\"start\":[6,1,3],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":84},"
            + "{\"start\":[6,1,3,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":85},"
            + "{\"start\":[6,2],\"name\":\"insertRows\",\"opl\":1,\"osn\":86},"
            + "{\"count\":1,\"start\":[6,2,0],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":87},"
            + "{\"start\":[6,2,0,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":88},"
            + "{\"count\":1,\"start\":[6,2,1],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":89},"
            + "{\"start\":[6,2,1,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":90},"
            + "{\"count\":1,\"start\":[6,2,2],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":91},"
            + "{\"start\":[6,2,2,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":92},"
            + "{\"count\":1,\"start\":[6,2,3],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":93},"
            + "{\"start\":[6,2,3,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":94},"
            + "{\"start\":[6,3],\"name\":\"insertRows\",\"opl\":1,\"osn\":95},"
            + "{\"count\":1,\"start\":[6,3,0],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":96},"
            + "{\"start\":[6,3,0,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":97},"
            + "{\"count\":1,\"start\":[6,3,1],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":98},"
            + "{\"start\":[6,3,1,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":99},"
            + "{\"count\":1,\"start\":[6,3,2],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":100},"
            + "{\"start\":[6,3,2,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":101},"
            + "{\"count\":1,\"start\":[6,3,3],\"attrs\":{\"cell\":{\"paddingTop\":97,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":97,\"paddingLeft\":97,\"paddingRight\":97}},\"name\":\"insertCells\",\"opl\":1,\"osn\":102},"
            + "{\"start\":[6,3,3,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":103},"
            + "{\"text\":\"Text\",\"start\":[7,0],\"attrs\":{\"styleId\":null,\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"opl\":1,\"name\":\"insertText\",\"osn\":104}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * ODT - Table cell component not found, cell content is lost
     */
    public void addingRepeatedRowsForTextTableTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"styleId\":\"TableGrid\",\"styleName\":\"Table Grid\",\"attrs\":{\"wholeTable\":{\"paragraph\":{\"lineHeight\":{\"value\":100,\"type\":\"percent\"}},\"table\":{\"borderInsideVert\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderInsideHor\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderBottom\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderLeft\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderRight\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingBottom\":0,\"paddingLeft\":190,\"paddingRight\":190}}},\"name\":\"insertStyleSheet\",\"opl\":1,\"uiPriority\":59,\"type\":\"table\",\"osn\":16},"
            + "{\"start\":[1],\"attrs\":{\"styleId\":\"TableGrid\",\"table\":{\"tableGrid\":[1000,1000,1000],\"width\":\"auto\",\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":17},"
            + "{\"count\":2,\"start\":[1,0],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":true,\"osn\":18},"
            + "{\"start\":[1,0,1,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":19},"
            + "{\"start\":[1,0,1,0],\"attrs\":{\"character\":{\"color\":{\"value\":\"accent2\",\"fallbackValue\":\"c0504d\",\"type\":\"scheme\"}}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":20},"
            + "{\"start\":[1,0,1,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"text2\",\"fallbackValue\":\"558ed5\",\"type\":\"scheme\",\"transformations\":[{\"value\":60000,\"type\":\"tint\"}]}}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":21},"
            + "{\"text\":\"svante\",\"start\":[1,0,1,0,0],\"opl\":5,\"name\":\"insertText\",\"osn\":22}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Webeditor Clipboard Docx to ODT: => ERROR:
     * nullorg.json.JSONException: JSONObject["fallbackValue"] not found
     */
    public void copyPasteOOXMLTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"styleId\":\"TableGrid\",\"styleName\":\"Table Grid\",\"attrs\":{\"wholeTable\":{\"paragraph\":{\"lineHeight\":{\"value\":100,\"type\":\"percent\"}},\"table\":{\"borderInsideVert\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderInsideHor\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderBottom\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderLeft\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderRight\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingBottom\":0,\"paddingLeft\":190,\"paddingRight\":190}}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":null,\"uiPriority\":59,\"type\":\"table\",\"osn\":16},"
            + "{\"start\":[0,0],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":17},"
            + "{\"start\":[0],\"attrs\":{\"styleId\":\"Normal\",\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"nextStyleId\":null,\"listStyleId\":null,\"borderBottom\":null,\"listLevel\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"tabStops\":null,\"marginBottom\":null,\"listLabelHidden\":null,\"listStartValue\":null,\"indentRight\":null,\"indentLeft\":null,\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":18},"
            + "{\"text\":\"text\",\"start\":[0,0],\"attrs\":{\"styleId\":null,\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"opl\":1,\"name\":\"insertText\",\"osn\":19},"
            + "{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":\"Normal\",\"uiPriority\":34,\"type\":\"paragraph\",\"osn\":20},"
            + "{\"start\":[1],\"attrs\":{\"styleId\":\"TableGrid\",\"table\":{\"width\":16510,\"tableGrid\":[499,499],\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":21},"
            + "{\"start\":[1,0],\"name\":\"insertRows\",\"opl\":1,\"osn\":22},"
            + "{\"count\":1,\"start\":[1,0,0],\"attrs\":{\"cell\":{\"fillColor\":{\"value\":\"text2\",\"type\":\"scheme\",\"transformations\":[{\"value\":60000,\"type\":\"tint\"}]}}},\"name\":\"insertCells\",\"opl\":1,\"osn\":23},"
            + "{\"start\":[1,0,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":24},"
            + "{\"text\":\"sdgasjdajsdgajsdgjas\",\"start\":[1,0,0,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":25},"
            + "{\"start\":[1,0,0,0,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,0,19],\"osn\":26},"
            + "{\"start\":[1,0,0,1],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":27},"
            + "{\"text\":\"asdasdasgdjsad\",\"start\":[1,0,0,1,0],\"opl\":1,\"name\":\"insertText\",\"osn\":28},"
            + "{\"start\":[1,0,0,1,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,1,13],\"osn\":29},"
            + "{\"start\":[1,0,0,2],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":30},"
            + "{\"text\":\"asdasgd\",\"start\":[1,0,0,2,0],\"opl\":1,\"name\":\"insertText\",\"osn\":31},"
            + "{\"start\":[1,0,0,2,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,2,6],\"osn\":32},"
            + "{\"start\":[1,0,0,3],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":33},"
            + "{\"text\":\"xkhvkx\",\"start\":[1,0,0,3,0],\"opl\":1,\"name\":\"insertText\",\"osn\":34},"
            + "{\"start\":[1,0,0,3,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,3,5],\"osn\":35},"
            + "{\"start\":[1,0,0,4],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":1},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":36},"
            + "{\"text\":\"sydkasdk\",\"start\":[1,0,0,4,0],\"opl\":1,\"name\":\"insertText\",\"osn\":37},"
            + "{\"start\":[1,0,0,4,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,4,7],\"osn\":38},"
            + "{\"start\":[1,0,0,5],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":2},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":39},"
            + "{\"text\":\"asfk\",\"start\":[1,0,0,5,0],\"opl\":1,\"name\":\"insertText\",\"osn\":40},"
            + "{\"start\":[1,0,0,5,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,5,3],\"osn\":41},"
            + "{\"start\":[1,0,0,6],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":3},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":42},"
            + "{\"text\":\"sdkfsöd\",\"start\":[1,0,0,6,0],\"opl\":1,\"name\":\"insertText\",\"osn\":43},"
            + "{\"start\":[1,0,0,6,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,6,6],\"osn\":44},"
            + "{\"start\":[1,0,0,7],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":45},"
            + "{\"text\":\"sdf\",\"start\":[1,0,0,7,0],\"opl\":1,\"name\":\"insertText\",\"osn\":46},"
            + "{\"start\":[1,0,0,7,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,7,2],\"osn\":47},"
            + "{\"start\":[1,0,0,8],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":48},"
            + "{\"text\":\"asd\",\"start\":[1,0,0,8,0],\"opl\":1,\"name\":\"insertText\",\"osn\":49},"
            + "{\"start\":[1,0,0,8,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,8,2],\"osn\":50},"
            + "{\"start\":[1,0,0,9],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":51},"
            + "{\"text\":\"asd\",\"start\":[1,0,0,9,0],\"opl\":1,\"name\":\"insertText\",\"osn\":52},"
            + "{\"start\":[1,0,0,9,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,9,2],\"osn\":53},"
            + "{\"start\":[1,0,0,10],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":54},"
            + "{\"text\":\"asd\",\"start\":[1,0,0,10,0],\"opl\":1,\"name\":\"insertText\",\"osn\":55},"
            + "{\"start\":[1,0,0,10,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,10,2],\"osn\":56},"
            + "{\"start\":[1,0,0,11],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":57},"
            + "{\"text\":\"asd\",\"start\":[1,0,0,11,0],\"opl\":1,\"name\":\"insertText\",\"osn\":58},"
            + "{\"start\":[1,0,0,11,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,11,2],\"osn\":59},"
            + "{\"start\":[1,0,0,12],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":60},"
            + "{\"text\":\"ad\",\"start\":[1,0,0,12,0],\"opl\":1,\"name\":\"insertText\",\"osn\":61},"
            + "{\"start\":[1,0,0,12,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,12,1],\"osn\":62},"
            + "{\"start\":[1,0,0,13],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":63},"
            + "{\"text\":\"sad\",\"start\":[1,0,0,13,0],\"opl\":1,\"name\":\"insertText\",\"osn\":64},"
            + "{\"start\":[1,0,0,13,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,13,2],\"osn\":65},"
            + "{\"start\":[1,0,0,14],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":66},"
            + "{\"text\":\"sadd\",\"start\":[1,0,0,14,0],\"opl\":1,\"name\":\"insertText\",\"osn\":67},"
            + "{\"start\":[1,0,0,14,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,0,14,3],\"osn\":68},"
            + "{\"count\":1,\"start\":[1,0,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":69},"
            + "{\"start\":[1,0,1,0],\"attrs\":{\"styleId\":\"Normal\",\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":70},"
            + "{\"start\":[1,1],\"name\":\"insertRows\",\"opl\":1,\"osn\":71},"
            + "{\"count\":1,\"start\":[1,1,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":72},"
            + "{\"start\":[1,1,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":73},"
            + "{\"count\":1,\"start\":[1,1,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":74},"
            + "{\"start\":[1,1,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":75},"
            + "{\"start\":[1,2],\"name\":\"insertRows\",\"opl\":1,\"osn\":76},"
            + "{\"count\":1,\"start\":[1,2,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":77},"
            + "{\"start\":[1,2,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":78},"
            + "{\"count\":1,\"start\":[1,2,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":79},"
            + "{\"start\":[1,2,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":80},"
            + "{\"start\":[1,3],\"name\":\"insertRows\",\"opl\":1,\"osn\":81},"
            + "{\"count\":1,\"start\":[1,3,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":82},"
            + "{\"start\":[1,3,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":83},"
            + "{\"count\":1,\"start\":[1,3,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":84},"
            + "{\"start\":[1,3,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":85},"
            + "{\"start\":[1,4],\"name\":\"insertRows\",\"opl\":1,\"osn\":86},"
            + "{\"count\":1,\"start\":[1,4,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":87},"
            + "{\"start\":[1,4,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":88},"
            + "{\"count\":1,\"start\":[1,4,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":89},"
            + "{\"start\":[1,4,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":90},"
            + "{\"start\":[1,5],\"name\":\"insertRows\",\"opl\":1,\"osn\":91},"
            + "{\"count\":1,\"start\":[1,5,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":92},"
            + "{\"start\":[1,5,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":93},"
            + "{\"count\":1,\"start\":[1,5,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":94},"
            + "{\"start\":[1,5,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":95},"
            + "{\"start\":[1,6],\"name\":\"insertRows\",\"opl\":1,\"osn\":96},"
            + "{\"count\":1,\"start\":[1,6,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":97},"
            + "{\"start\":[1,6,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":98},"
            + "{\"count\":1,\"start\":[1,6,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":99},"
            + "{\"start\":[1,6,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":100},"
            + "{\"start\":[1,7],\"name\":\"insertRows\",\"opl\":1,\"osn\":101},"
            + "{\"count\":1,\"start\":[1,7,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":102},"
            + "{\"start\":[1,7,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":103},"
            + "{\"count\":1,\"start\":[1,7,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":104},"
            + "{\"start\":[1,7,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":105},"
            + "{\"start\":[2],\"attrs\":{\"styleId\":\"Normal\",\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":106},"
            + "{\"text\":\"asdasdtext\",\"start\":[2,0],\"opl\":1,\"name\":\"insertText\",\"osn\":107},"
            + "{\"start\":[3],\"attrs\":{\"styleId\":\"Normal\",\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":108},"
            + "{\"text\":\"asdadasd\",\"start\":[3,0],\"opl\":1,\"name\":\"insertText\",\"osn\":109},"
            + "{\"start\":[3,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[3,7],\"osn\":110},"
            + "{\"start\":[4],\"attrs\":{\"styleId\":\"Normal\",\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":111},"
            + "{\"text\":\"asd\",\"start\":[4,0],\"opl\":1,\"name\":\"insertText\",\"osn\":112},"
            + "{\"start\":[4,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[4,2],\"osn\":113},"
            + "{\"start\":[5],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":114},"
            + "{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L1\",\"osn\":115},"
            + "{\"start\":[6],\"attrs\":{\"styleId\":\"Normal\",\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":116},"
            + "{\"start\":[7],\"attrs\":{\"styleId\":\"Normal\",\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":117},"
            + "{\"start\":[8],\"attrs\":{\"styleId\":\"Normal\",\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":118},"
            + "{\"text\":\"wqe\",\"start\":[8,0],\"opl\":1,\"name\":\"insertText\",\"osn\":119},"
            + "{\"start\":[8,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[8,2],\"osn\":120},"
            + "{\"start\":[9],\"attrs\":{\"styleId\":\"TableGrid\",\"table\":{\"width\":16510,\"tableGrid\":[998,998],\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":121},"
            + "{\"start\":[9,0],\"name\":\"insertRows\",\"opl\":1,\"osn\":122},"
            + "{\"count\":1,\"start\":[9,0,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":123},"
            + "{\"start\":[9,0,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":124},"
            + "{\"text\":\"sdgasjdajsdgajsdgjas\",\"start\":[9,0,0,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":125},"
            + "{\"start\":[9,0,0,0,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[9,0,0,0,19],\"osn\":126},"
            + "{\"start\":[9,0,0,1],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":127},"
            + "{\"text\":\"asdasdasgdjsad\",\"start\":[9,0,0,1,0],\"opl\":1,\"name\":\"insertText\",\"osn\":128},"
            + "{\"start\":[9,0,0,1,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[9,0,0,1,13],\"osn\":129},"
            + "{\"start\":[9,0,0,2],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":130},"
            + "{\"text\":\"asdasd\",\"start\":[9,0,0,2,0],\"opl\":1,\"name\":\"insertText\",\"osn\":131},"
            + "{\"start\":[9,0,0,2,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[9,0,0,2,5],\"osn\":132},"
            + "{\"start\":[9,0,0,3],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":133},"
            + "{\"text\":\"asdasgd\",\"start\":[9,0,0,3,0],\"opl\":1,\"name\":\"insertText\",\"osn\":134},"
            + "{\"start\":[9,0,0,3,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[9,0,0,3,6],\"osn\":135},"
            + "{\"start\":[9,0,0,4],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":136},"
            + "{\"text\":\"asdadasd\",\"start\":[9,0,0,4,0],\"opl\":1,\"name\":\"insertText\",\"osn\":137},"
            + "{\"start\":[9,0,0,5],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":138},"
            + "{\"text\":\"asd\",\"start\":[9,0,0,5,0],\"opl\":1,\"name\":\"insertText\",\"osn\":139},"
            + "{\"count\":1,\"start\":[9,0,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":140},"
            + "{\"start\":[9,0,1,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":141},"
            + "{\"start\":[9,1],\"name\":\"insertRows\",\"opl\":1,\"osn\":142},"
            + "{\"count\":1,\"start\":[9,1,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":143},"
            + "{\"start\":[9,1,0,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":144},"
            + "{\"count\":1,\"start\":[9,1,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":145},"
            + "{\"start\":[9,1,1,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":146},"
            + "{\"start\":[10],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":147},"
            + "{\"text\":\"qweqwe\",\"start\":[10,0],\"opl\":1,\"name\":\"insertText\",\"osn\":148},"
            + "{\"start\":[11],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":149},"
            + "{\"text\":\"sdgasjdajsdgajsdgjas\",\"start\":[11,0],\"opl\":1,\"name\":\"insertText\",\"osn\":150},"
            + "{\"start\":[11,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[11,19],\"osn\":151},"
            + "{\"start\":[12],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":152},"
            + "{\"text\":\"asdasdasgdjsad\",\"start\":[12,0],\"opl\":1,\"name\":\"insertText\",\"osn\":153},"
            + "{\"start\":[12,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[12,13],\"osn\":154},"
            + "{\"start\":[13],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":155},"
            + "{\"text\":\"asdasgd\",\"start\":[13,0],\"opl\":1,\"name\":\"insertText\",\"osn\":156},"
            + "{\"start\":[13,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[13,6],\"osn\":157},"
            + "{\"start\":[14],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":158},"
            + "{\"text\":\"xkhvkx\",\"start\":[14,0],\"opl\":1,\"name\":\"insertText\",\"osn\":159},"
            + "{\"start\":[14,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[14,5],\"osn\":160},"
            + "{\"start\":[15],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":1},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":161},"
            + "{\"text\":\"sydkasdk\",\"start\":[15,0],\"opl\":1,\"name\":\"insertText\",\"osn\":162},"
            + "{\"start\":[15,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[15,7],\"osn\":163},"
            + "{\"start\":[16],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":2},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":164},"
            + "{\"text\":\"asfk\",\"start\":[16,0],\"opl\":1,\"name\":\"insertText\",\"osn\":165},"
            + "{\"start\":[16,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[16,3],\"osn\":166},"
            + "{\"start\":[17],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":3},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":167},"
            + "{\"text\":\"sdkfsöd\",\"start\":[17,0],\"opl\":1,\"name\":\"insertText\",\"osn\":168},"
            + "{\"start\":[17,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[17,6],\"osn\":169},"
            + "{\"start\":[18],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":170},"
            + "{\"text\":\"sdf\",\"start\":[18,0],\"opl\":1,\"name\":\"insertText\",\"osn\":171},"
            + "{\"start\":[18,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[18,2],\"osn\":172},"
            + "{\"start\":[19],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":173},"
            + "{\"text\":\"asd\",\"start\":[19,0],\"opl\":1,\"name\":\"insertText\",\"osn\":174},"
            + "{\"start\":[19,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[19,2],\"osn\":175},"
            + "{\"start\":[20],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":176},"
            + "{\"text\":\"asd\",\"start\":[20,0],\"opl\":1,\"name\":\"insertText\",\"osn\":177},"
            + "{\"start\":[20,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[20,2],\"osn\":178},"
            + "{\"start\":[21],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":179},"
            + "{\"text\":\"asd\",\"start\":[21,0],\"opl\":1,\"name\":\"insertText\",\"osn\":180},"
            + "{\"start\":[21,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[21,2],\"osn\":181},"
            + "{\"start\":[22],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":182},"
            + "{\"text\":\"asd\",\"start\":[22,0],\"opl\":1,\"name\":\"insertText\",\"osn\":183},"
            + "{\"start\":[22,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[22,2],\"osn\":184},"
            + "{\"start\":[23],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":185},"
            + "{\"text\":\"ad\",\"start\":[23,0],\"opl\":1,\"name\":\"insertText\",\"osn\":186},"
            + "{\"start\":[23,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[23,1],\"osn\":187},"
            + "{\"start\":[24],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":188},"
            + "{\"text\":\"sad\",\"start\":[24,0],\"opl\":1,\"name\":\"insertText\",\"osn\":189},"
            + "{\"start\":[24,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[24,2],\"osn\":190},"
            + "{\"start\":[25],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":191},"
            + "{\"text\":\"sadd\",\"start\":[25,0],\"opl\":1,\"name\":\"insertText\",\"osn\":192},"
            + "{\"start\":[25,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[25,3],\"osn\":193},"
            + "{\"start\":[26],\"attrs\":{\"styleId\":\"Normal\",\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":194},"
            + "{\"start\":[27],\"attrs\":{\"styleId\":\"Normal\",\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":195},"
            + "{\"start\":[28],\"attrs\":{\"styleId\":\"Normal\",\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":196},"
            + "{\"text\":\"text\",\"start\":[28,0],\"opl\":1,\"name\":\"insertText\",\"osn\":197},"
            + "{\"start\":[28,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[28,3],\"osn\":198},"
            + "{\"start\":[29],\"attrs\":{\"styleId\":\"TableGrid\",\"table\":{\"width\":16510,\"tableGrid\":[499,499],\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":199},"
            + "{\"start\":[29,0],\"name\":\"insertRows\",\"opl\":1,\"osn\":200},"
            + "{\"count\":1,\"start\":[29,0,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":201},"
            + "{\"start\":[29,0,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":202},"
            + "{\"text\":\"sdgasjdajsdgajsdgjas\",\"start\":[29,0,0,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":203},"
            + "{\"start\":[29,0,0,1],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":204},"
            + "{\"text\":\"asdasdasgdjsad\",\"start\":[29,0,0,1,0],\"opl\":1,\"name\":\"insertText\",\"osn\":205},"
            + "{\"start\":[29,0,0,1,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,1,13],\"osn\":206},"
            + "{\"start\":[29,0,0,2],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":207},"
            + "{\"text\":\"asdasgd\",\"start\":[29,0,0,2,0],\"opl\":1,\"name\":\"insertText\",\"osn\":208},"
            + "{\"start\":[29,0,0,2,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,2,6],\"osn\":209},"
            + "{\"start\":[29,0,0,3],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":210},"
            + "{\"text\":\"xkhvkx\",\"start\":[29,0,0,3,0],\"opl\":1,\"name\":\"insertText\",\"osn\":211},"
            + "{\"start\":[29,0,0,3,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,3,5],\"osn\":212},"
            + "{\"start\":[29,0,0,4],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":1},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":213},"
            + "{\"text\":\"sydkasdk\",\"start\":[29,0,0,4,0],\"opl\":1,\"name\":\"insertText\",\"osn\":214},"
            + "{\"start\":[29,0,0,4,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,4,7],\"osn\":215},"
            + "{\"start\":[29,0,0,5],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":2},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":216},"
            + "{\"text\":\"asfk\",\"start\":[29,0,0,5,0],\"opl\":1,\"name\":\"insertText\",\"osn\":217},"
            + "{\"start\":[29,0,0,5,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,5,3],\"osn\":218},"
            + "{\"start\":[29,0,0,6],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":3},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":219},"
            + "{\"text\":\"sdkfsöd\",\"start\":[29,0,0,6,0],\"opl\":1,\"name\":\"insertText\",\"osn\":220},"
            + "{\"start\":[29,0,0,6,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,6,6],\"osn\":221},"
            + "{\"start\":[29,0,0,7],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":222},"
            + "{\"text\":\"sdf\",\"start\":[29,0,0,7,0],\"opl\":1,\"name\":\"insertText\",\"osn\":223},"
            + "{\"start\":[29,0,0,7,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,7,2],\"osn\":224},"
            + "{\"start\":[29,0,0,8],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":225},"
            + "{\"text\":\"asd\",\"start\":[29,0,0,8,0],\"opl\":1,\"name\":\"insertText\",\"osn\":226},"
            + "{\"start\":[29,0,0,8,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,8,2],\"osn\":227},"
            + "{\"start\":[29,0,0,9],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":228},"
            + "{\"text\":\"asd\",\"start\":[29,0,0,9,0],\"opl\":1,\"name\":\"insertText\",\"osn\":229},"
            + "{\"start\":[29,0,0,9,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,9,2],\"osn\":230},"
            + "{\"start\":[29,0,0,10],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":231},"
            + "{\"text\":\"asd\",\"start\":[29,0,0,10,0],\"opl\":1,\"name\":\"insertText\",\"osn\":232},"
            + "{\"start\":[29,0,0,10,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,10,2],\"osn\":233},"
            + "{\"start\":[29,0,0,11],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":234},"
            + "{\"text\":\"asd\",\"start\":[29,0,0,11,0],\"opl\":1,\"name\":\"insertText\",\"osn\":235},"
            + "{\"start\":[29,0,0,11,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,11,2],\"osn\":236},"
            + "{\"start\":[29,0,0,12],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":237},"
            + "{\"text\":\"ad\",\"start\":[29,0,0,12,0],\"opl\":1,\"name\":\"insertText\",\"osn\":238},"
            + "{\"start\":[29,0,0,12,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,12,1],\"osn\":239},"
            + "{\"start\":[29,0,0,13],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":240},"
            + "{\"text\":\"sad\",\"start\":[29,0,0,13,0],\"opl\":1,\"name\":\"insertText\",\"osn\":241},"
            + "{\"start\":[29,0,0,13,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,13,2],\"osn\":242},"
            + "{\"start\":[29,0,0,14],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":4},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":243},"
            + "{\"text\":\"sadd\",\"start\":[29,0,0,14,0],\"opl\":1,\"name\":\"insertText\",\"osn\":244},"
            + "{\"start\":[29,0,0,14,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[29,0,0,14,3],\"osn\":245},"
            + "{\"count\":1,\"start\":[29,0,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":246},"
            + "{\"start\":[29,0,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":247},"
            + "{\"start\":[29,1],\"name\":\"insertRows\",\"opl\":1,\"osn\":248},"
            + "{\"count\":1,\"start\":[29,1,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":249},"
            + "{\"start\":[29,1,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":250},"
            + "{\"count\":1,\"start\":[29,1,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":251},"
            + "{\"start\":[29,1,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":252},"
            + "{\"start\":[29,2],\"name\":\"insertRows\",\"opl\":1,\"osn\":253},"
            + "{\"count\":1,\"start\":[29,2,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":254},"
            + "{\"start\":[29,2,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":255},"
            + "{\"count\":1,\"start\":[29,2,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":256},"
            + "{\"start\":[29,2,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":257},"
            + "{\"start\":[29,3],\"name\":\"insertRows\",\"opl\":1,\"osn\":258},"
            + "{\"count\":1,\"start\":[29,3,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":259},"
            + "{\"start\":[29,3,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":260},"
            + "{\"count\":1,\"start\":[29,3,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":261},"
            + "{\"start\":[29,3,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":262},"
            + "{\"start\":[29,4],\"name\":\"insertRows\",\"opl\":1,\"osn\":263},"
            + "{\"count\":1,\"start\":[29,4,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":264},"
            + "{\"start\":[29,4,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":265},"
            + "{\"count\":1,\"start\":[29,4,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":266},"
            + "{\"start\":[29,4,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":267},"
            + "{\"start\":[29,5],\"name\":\"insertRows\",\"opl\":1,\"osn\":268},"
            + "{\"count\":1,\"start\":[29,5,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":269},"
            + "{\"start\":[29,5,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":270},"
            + "{\"count\":1,\"start\":[29,5,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":271},"
            + "{\"start\":[29,5,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":272},"
            + "{\"start\":[29,6],\"name\":\"insertRows\",\"opl\":1,\"osn\":273},"
            + "{\"count\":1,\"start\":[29,6,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":274},"
            + "{\"start\":[29,6,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":275},"
            + "{\"count\":1,\"start\":[29,6,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":276},"
            + "{\"start\":[29,6,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":277},"
            + "{\"start\":[29,7],\"name\":\"insertRows\",\"opl\":1,\"osn\":278},"
            + "{\"count\":1,\"start\":[29,7,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":279},"
            + "{\"start\":[29,7,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":280},"
            + "{\"count\":1,\"start\":[29,7,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":281},"
            + "{\"start\":[29,7,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0},\"character\":{\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":282},"
            + "{\"text\":\"text\",\"start\":[30,0],\"attrs\":{\"styleId\":null,\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"opl\":1,\"name\":\"insertText\",\"osn\":283}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     ODT - Hyperlink styles not set in ODF by LO 4.0.4.2
     */
    public void hyperlinkStyleTest() {
        /**
         * LO/AOO/Calligra are applying to Hyperlinks the "Internet_20_link"
         * style, without writing out the dependency into XML. Therefore
         * whenever a Hyperlink existists without character style properties,
         * the reference will be set.
         */
        final String SOURCE_FILE_NAME_TRUNC = "Hyperlink-AOO401";
        String firstEditOperations = "["
            + "]";
        List<String> editOperations = new ArrayList();
        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    public void listSplitWithinCellTest() {
        final String SOURCE_FILE_NAME_TRUNC = "listsInTable";
        String firstEditOperations = "["
            + "{\"text\":\"1\",\"start\":[0,0,2,0,0],\"name\":\"insertText\"},"
            + "{\"start\":[0,0,2,0,1],\"name\":\"splitParagraph\"},"
            + "{\"text\":\"2\",\"start\":[0,0,2,1,0],\"name\":\"insertText\"},"
            + "{\"start\":[0,0,2,1,1],\"name\":\"splitParagraph\"},"
            /*
             <text:list text:continue-numbering="true" text:style-name="L1">
             <text:list-item>
             <text:p text:style-name="aaaf1f7">1</text:p>
             </text:list-item>
             <text:list-item>
             <text:p text:style-name="aaaf1f7">2</text:p>
             </text:list-item>
             <text:list-item>
             <text:p text:style-name="aaaf1f7"/>
             </text:list-item>
             </text:list>
             */
            + "{\"start\":[0,0,2,1],\"attrs\":{\"styleId\":null,\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"nextStyleId\":null,\"listStyleId\":null,\"borderBottom\":null,\"listLevel\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"tabStops\":null,\"marginBottom\":null,\"listLabelHidden\":null,\"listStartValue\":null,\"indentRight\":null,\"indentLeft\":null,\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\"},"
            /*
             <text:list text:continue-numbering="true" text:style-name="L1">
             <text:list-item>
             <text:p text:style-name="aaaf1f7">1</text:p>
             </text:list-item>
             </text:list>
             <text:p text:style-name="a81c9de">2</text:p>
             <text:list text:continue-numbering="true" text:style-name="L1">
             <text:list-item>
             <text:p text:style-name="aaaf1f7"/>
             </text:list-item>
             </text:list>
             */
            + "{\"text\":\"a\",\"start\":[0,0,2,2,0],\"name\":\"insertText\"},"
            /*
             * WRONG:
             <text:list text:continue-numbering="true" text:style-name="L1">
             <text:list-item>
             <text:p text:style-name="aaaf1f7">1</text:p>
             </text:list-item>
             </text:list>
             <text:p text:style-name="a81c9de">a2</text:p>
             <text:list text:continue-numbering="true" text:style-name="L1">
             <text:list-item>
             <text:p text:style-name="aaaf1f7"/>
             </text:list-item>
             </text:list>

             *
             *
             * RIGHT:
             <text:list text:continue-numbering="true" text:style-name="L1">
             <text:list-item>
             <text:p text:style-name="aaaf1f7">1</text:p>
             </text:list-item>
             </text:list>
             <text:p text:style-name="a81c9de">2</text:p>
             <text:list text:continue-numbering="true" text:style-name="L1">
             <text:list-item>
             <text:p text:style-name="aaaf1f7">a</text:p>
             </text:list-item>
             </text:list>
             */
            + "]";

        List<String> editOperations = new ArrayList();
        editOperations.add(firstEditOperations);
        //super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Deleting, editing & format a spreadsheet with existing hyperlinks
     */
    public void styleSheetFormatTest() {
        final String SOURCE_FILE_NAME_TRUNC = "Calc_Excel_Funktionen_Pootle";
        String firstEditOperations = "["
            + "{\"start\":[1,3],\"opl\":1,\"name\":\"fillCellRange\",\"value\":null,\"sheet\":0,\"osn\":477},"
            + "{\"start\":[1,2],\"attrs\":{\"character\":{\"underline\":true}},\"opl\":1,\"name\":\"fillCellRange\",\"sheet\":0,\"osn\":478},"
            + "{\"start\":[1,2],\"attrs\":{\"character\":{\"italic\":true}},\"opl\":1,\"name\":\"fillCellRange\",\"sheet\":0,\"osn\":479},"
            + "{\"start\":[1,1],\"contents\":[[{\"value\":\"A0BC\"}]],\"opl\":1,\"name\":\"setCellContents\",\"sheet\":0,\"osn\":480},"
            + "{\"start\":[1,4],\"attrs\":{\"character\":{\"fontSize\":24}},\"opl\":1,\"name\":\"fillCellRange\",\"sheet\":0,\"osn\":481},"
            + "{\"start\":[1,4],\"attrs\":{\"character\":{\"fontName\":\"Andale Mono\"}},\"opl\":1,\"name\":\"fillCellRange\",\"sheet\":0,\"osn\":482},"
            + "{\"start\":[1,5],\"opl\":1,\"name\":\"fillCellRange\",\"value\":null,\"attrs\":{\"character\":{\"url\":\"http://www.heise.de\"}},\"sheet\":0,\"osn\":37},"
            + "{\"start\":[1,5],\"contents\":[[{\"value\":\"HEISE\"}]],\"opl\":1,\"name\":\"setCellContents\",\"sheet\":0,\"osn\":36}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     ODT - Copy&Paste a table: after re-edit, the borderstyle is lost
     */
    public void copyPasteTableTest() {
        final String SOURCE_FILE_NAME_TRUNC = "listsInTable";
        String firstEditOperations = "["
            + "{\"styleId\":\"TableGrid\",\"styleName\":\"Table Grid\",\"attrs\":{\"wholeTable\":{\"paragraph\":{\"lineHeight\":{\"value\":100,\"type\":\"percent\"}},\"table\":{\"borderInsideVert\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderInsideHor\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderBottom\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderLeft\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderRight\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingBottom\":0,\"paddingLeft\":190,\"paddingRight\":190}}},\"name\":\"insertStyleSheet\",\"opl\":1,\"uiPriority\":59,\"type\":\"table\",\"osn\":16},"
            + "{\"start\":[1],\"attrs\":{\"styleId\":\"TableGrid\",\"table\":{\"tableGrid\":[1000,1000],\"width\":\"auto\",\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":17},"
            + "{\"count\":1,\"start\":[1,0],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":true,\"osn\":18},"
            + "{\"start\":[2],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":19},"
            + "{\"text\":\"Zweite Tabelle\",\"start\":[2,0],\"opl\":14,\"name\":\"insertText\",\"osn\":20},"
            + "{\"start\":[2,14],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":34},"
            + "{\"start\":[3,0],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":35},"
            + "{\"start\":[4],\"attrs\":{\"styleId\":\"TableGrid\",\"table\":{\"tableGrid\":[1000,1000],\"width\":\"auto\",\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":36},"
            + "{\"count\":1,\"start\":[4,0],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":false,\"osn\":37},"
            + "{\"count\":1,\"start\":[4,0,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":38},"
            + "{\"count\":1,\"start\":[4,0,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":39}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Inserting, Renaming and Moving Spreadsheets
     */
    public void styleSheetAdoptionTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"opl\":1,\"name\":\"setSheetName\",\"sheetName\":\"FORMER_FIRST\",\"sheet\":0,\"osn\":477},"
            + "{\"opl\":1,\"name\":\"insertSheet\",\"sheetName\":\"NEW\",\"sheet\":1,\"osn\":478},"
            + "{\"opl\":1,\"name\":\"setSheetName\",\"sheetName\":\"NEW_RENAMED\",\"sheet\":1,\"osn\":479},"
            + "{\"opl\":1,\"name\":\"setSheetName\",\"sheetName\":\"NEW_RENAMED_SECON\",\"sheet\":1,\"osn\":480},"
            + "{\"opl\":1,\"name\":\"insertSheet\",\"sheetName\":\"Sheet2\",\"sheet\":2,\"osn\":481},"
            + "{\"opl\":1,\"name\":\"setSheetName\",\"sheetName\":\"THIRD_NOW_FIRST\",\"sheet\":2,\"osn\":482},"
            + "{\"opl\":1,\"name\":\"moveSheet\",\"to\":\"0\",\"sheet\":2,\"osn\":483},"
            + "{\"opl\":1,\"name\":\"deleteSheet\",\"sheet\":1,\"osn\":484},"
            + "{\"opl\":1,\"name\":\"setSheetAttributes\",\"sheet\":1,\"osn\":479,\"attrs\":{\"sheet\":{\"visible\":false}}}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * ODT - Default Styles are not being round-tripped
     */
    public void styleSheetInsertionTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"name\":\"insertStyleSheet\",\"type\":\"cell\",\"styleId\":\"default_cell_style\",\"attrs\":{\"character\":{\"fontName\":\"Arial\",\"fontNameAsian\":\"Arial Unicode MS\",\"fontNameComplex\":\"Tahoma\",\"language\":\"de-DE\"}},\"default\":true,\"hidden\":true,\"styleName\":\"Default Cell Style\"},"
            + "{\"name\":\"insertStyleSheet\",\"type\":\"cell\",\"styleId\":\"Default\",\"attrs\":{\"character\":{\"fontNameAsian\":\"Microsoft YaHei\",\"fontNameComplex\":\"Mangal\"}},\"parent\":\"default_cell_style\"}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * ODT - Embedding a table within the list of another table
     */
    public void deleteListPropertiesTest() {
        final String SOURCE_FILE_NAME_TRUNC = "listsInTable";
        String firstEditOperations = "["
            + "{\"text\":\"1\",\"start\":[0,0,2,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":70},"
            + "{\"start\":[0,0,2,0,1],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":71},"
            + "{\"text\":\"2\",\"start\":[0,0,2,1,0],\"opl\":1,\"name\":\"insertText\",\"osn\":72},"
            + "{\"start\":[0,0,2,1,1],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":73},"
            + "{\"text\":\"3\",\"start\":[0,0,2,2,0],\"opl\":1,\"name\":\"insertText\",\"osn\":74},"
            + "{\"start\":[0,0,2,2,1],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":75},"
            + "{\"start\":[0,0,2,1,1],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":76},"
            + "{\"start\":[0,0,2,1],\"attrs\":{\"styleId\":null,\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"nextStyleId\":null,\"listStyleId\":null,\"borderBottom\":null,\"listLevel\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"tabStops\":null,\"marginBottom\":null,\"listLabelHidden\":null,\"listStartValue\":null,\"indentRight\":null,\"indentLeft\":null,\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":77},"
            + "{\"text\":\"Notfallkontakt:\",\"start\":[0,0,2,1,1],\"attrs\":{\"styleId\":null,\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"opl\":1,\"name\":\"insertText\",\"osn\":78},"
            + "{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"marginLeft\":1270,\"nextStyleId\":\"ListParagraph\"}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":\"Standard\",\"uiPriority\":0,\"type\":\"paragraph\",\"osn\":79},"
            + "{\"start\":[0,0,2,2],\"attrs\":{\"styleId\":\"FieldTripLetterTable\",\"table\":{\"tableGrid\":[1582,14418]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":80},"
            + "{\"start\":[0,0,2,2,0],\"attrs\":{\"row\":{\"height\":793}},\"name\":\"insertRows\",\"opl\":1,\"osn\":81},"
            + "{\"count\":1,\"start\":[0,0,2,2,0,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":82},"
            + "{\"start\":[0,0,2,2,0,0,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":83},"
            + "{\"text\":\"Name:\",\"start\":[0,0,2,2,0,0,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":84},"
            + "{\"count\":1,\"start\":[0,0,2,2,0,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":85},"
            + "{\"start\":[0,0,2,2,0,1,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":86},"
            + "{\"start\":[0,0,2,2,1],\"attrs\":{\"row\":{\"height\":793}},\"name\":\"insertRows\",\"opl\":1,\"osn\":87},"
            + "{\"count\":1,\"start\":[0,0,2,2,1,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":88},"
            + "{\"start\":[0,0,2,2,1,0,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":89},"
            + "{\"text\":\"Telefon:\",\"start\":[0,0,2,2,1,0,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":90},"
            + "{\"count\":1,\"start\":[0,0,2,2,1,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":91},"
            + "{\"start\":[0,0,2,2,1,1,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":92},"
            + "{\"start\":[0,0,2,3],\"attrs\":{\"paragraph\":{\"marginTop\":1270}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":93},"
            + "{\"text\":\"Mein Kind darf im Notfall medizinisch betreut werden.\",\"start\":[0,0,2,3,0],\"opl\":1,\"name\":\"insertText\",\"osn\":94},"
            + "{\"start\":[0,0,2,4],\"attrs\":{\"table\":{\"tableGrid\":[7679,640,7679]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":95},"
            + "{\"start\":[0,0,2,4,0],\"name\":\"insertRows\",\"opl\":1,\"osn\":96},"
            + "{\"count\":1,\"start\":[0,0,2,4,0,0],\"attrs\":{\"cell\":{\"borderBottom\":{\"style\":\"single\",\"width\":18}}},\"name\":\"insertCells\",\"opl\":1,\"osn\":97},"
            + "{\"start\":[0,0,2,4,0,0,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":98},"
            + "{\"count\":1,\"start\":[0,0,2,4,0,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":99},"
            + "{\"start\":[0,0,2,4,0,1,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":100},"
            + "{\"count\":1,\"start\":[0,0,2,4,0,2],\"attrs\":{\"cell\":{\"borderBottom\":{\"style\":\"single\",\"width\":18}}},\"name\":\"insertCells\",\"opl\":1,\"osn\":101},"
            + "{\"start\":[0,0,2,4,0,2,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":102},"
            + "{\"start\":[0,0,2,4,1],\"attrs\":{\"row\":{\"height\":0}},\"name\":\"insertRows\",\"opl\":1,\"osn\":103},"
            + "{\"count\":1,\"start\":[0,0,2,4,1,0],\"attrs\":{\"cell\":{\"borderTop\":{\"style\":\"single\",\"width\":18}}},\"name\":\"insertCells\",\"opl\":1,\"osn\":104},"
            + "{\"start\":[0,0,2,4,1,0,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":105},"
            + "{\"text\":\"Unterschrift der Eltern/eines Erziehungsberechtigten\",\"start\":[0,0,2,4,1,0,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":106},"
            + "{\"count\":1,\"start\":[0,0,2,4,1,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":107},"
            + "{\"start\":[0,0,2,4,1,1,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":108},"
            + "{\"count\":1,\"start\":[0,0,2,4,1,2],\"attrs\":{\"cell\":{\"borderTop\":{\"style\":\"single\",\"width\":18}}},\"name\":\"insertCells\",\"opl\":1,\"osn\":109},"
            + "{\"start\":[0,0,2,4,1,2,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":110},"
            + "{\"text\":\"Datum\",\"start\":[0,0,2,4,1,2,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":111},"
            + "{\"start\":[0,0,2,5],\"attrs\":{\"styleId\":\"Standard\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":112},"
            + "{\"text\":\"a\",\"start\":[0,0,2,6,0],\"attrs\":{\"styleId\":null,\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"opl\":1,\"name\":\"insertText\",\"osn\":113},"
            + "{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":8890,\"numberFormat\":\"lowerRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":10160,\"numberFormat\":\"lowerRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":7620,\"numberFormat\":\"lowerRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"lowerRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"lowerRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"lowerRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"lowerRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L30031\",\"osn\":114}"
            + "]";
        List<String> editOperations = new ArrayList();
        editOperations.add(firstEditOperations);
        //super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * ODT - Embedding a table within the list of another table
     */
    public void insertNestedTableTest() {
        final String SOURCE_FILE_NAME_TRUNC = "crazyTable";
        String firstEditOperations = "["
            + "{\"start\":[0,0,2,1,0],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":211},"
            + "{\"start\":[0,0,2,1],\"attrs\":{\"styleId\":null,\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"nextStyleId\":null,\"listStyleId\":null,\"borderBottom\":null,\"listLevel\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"tabStops\":null,\"marginBottom\":null,\"listLabelHidden\":null,\"listStartValue\":null,\"indentRight\":null,\"indentLeft\":null,\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":212},"
            + "{\"text\":\"Notfallkontakt:\",\"start\":[0,0,2,1,0],\"attrs\":{\"styleId\":null,\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"opl\":1,\"name\":\"insertText\",\"osn\":213},"
            + "{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"marginLeft\":1270,\"nextStyleId\":\"ListParagraph\"}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":\"Standard\",\"uiPriority\":0,\"type\":\"paragraph\",\"osn\":214},"
            + "{\"start\":[0,0,2,2],\"attrs\":{\"styleId\":\"FieldTripLetterTable\",\"table\":{\"tableGrid\":[1582,14418]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":215},"
            + "{\"start\":[0,0,2,2,0],\"attrs\":{\"row\":{\"height\":793}},\"name\":\"insertRows\",\"opl\":1,\"osn\":216},"
            + "{\"count\":1,\"start\":[0,0,2,2,0,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":217},"
            + "{\"start\":[0,0,2,2,0,0,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":218},"
            + "{\"text\":\"Name:\",\"start\":[0,0,2,2,0,0,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":219},"
            + "{\"count\":1,\"start\":[0,0,2,2,0,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":220},"
            + "{\"start\":[0,0,2,2,0,1,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":221},"
            + "{\"start\":[0,0,2,2,1],\"attrs\":{\"row\":{\"height\":793}},\"name\":\"insertRows\",\"opl\":1,\"osn\":222},"
            + "{\"count\":1,\"start\":[0,0,2,2,1,0],\"name\":\"insertCells\",\"opl\":1,\"osn\":223},"
            + "{\"start\":[0,0,2,2,1,0,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":224},"
            + "{\"text\":\"Telefon:\",\"start\":[0,0,2,2,1,0,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":225},"
            + "{\"count\":1,\"start\":[0,0,2,2,1,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":226},"
            + "{\"start\":[0,0,2,2,1,1,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":227},"
            + "{\"start\":[0,0,2,3],\"attrs\":{\"paragraph\":{\"marginTop\":1270}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":228},"
            + "{\"text\":\"Mein Kind darf im Notfall medizinisch betreut werden.\",\"start\":[0,0,2,3,0],\"opl\":1,\"name\":\"insertText\",\"osn\":229},"
            + "{\"start\":[0,0,2,4],\"attrs\":{\"table\":{\"tableGrid\":[7679,640,7679]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":230},"
            + "{\"start\":[0,0,2,4,0],\"name\":\"insertRows\",\"opl\":1,\"osn\":231},"
            + "{\"count\":1,\"start\":[0,0,2,4,0,0],\"attrs\":{\"cell\":{\"borderBottom\":{\"style\":\"single\",\"width\":18}}},\"name\":\"insertCells\",\"opl\":1,\"osn\":232},"
            + "{\"start\":[0,0,2,4,0,0,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":233},"
            + "{\"count\":1,\"start\":[0,0,2,4,0,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":234},"
            + "{\"start\":[0,0,2,4,0,1,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":235},"
            + "{\"count\":1,\"start\":[0,0,2,4,0,2],\"attrs\":{\"cell\":{\"borderBottom\":{\"style\":\"single\",\"width\":18}}},\"name\":\"insertCells\",\"opl\":1,\"osn\":236},"
            + "{\"start\":[0,0,2,4,0,2,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":237},"
            + "{\"start\":[0,0,2,4,1],\"attrs\":{\"row\":{\"height\":0}},\"name\":\"insertRows\",\"opl\":1,\"osn\":238},"
            + "{\"count\":1,\"start\":[0,0,2,4,1,0],\"attrs\":{\"cell\":{\"borderTop\":{\"style\":\"single\",\"width\":18}}},\"name\":\"insertCells\",\"opl\":1,\"osn\":239},"
            + "{\"start\":[0,0,2,4,1,0,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":240},"
            + "{\"text\":\"Unterschrift der Eltern/eines Erziehungsberechtigten\",\"start\":[0,0,2,4,1,0,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":241},"
            + "{\"count\":1,\"start\":[0,0,2,4,1,1],\"name\":\"insertCells\",\"opl\":1,\"osn\":242},"
            + "{\"start\":[0,0,2,4,1,1,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":243},"
            + "{\"count\":1,\"start\":[0,0,2,4,1,2],\"attrs\":{\"cell\":{\"borderTop\":{\"style\":\"single\",\"width\":18}}},\"name\":\"insertCells\",\"opl\":1,\"osn\":244},"
            + "{\"start\":[0,0,2,4,1,2,0],\"name\":\"insertParagraph\",\"opl\":1,\"osn\":245},"
            + "{\"text\":\"Datum\",\"start\":[0,0,2,4,1,2,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":246}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    /**
     * ODT - Downloading odt: values are not shown in LO
     */
    @Test
    public void changeTableContentTest() {
        final String SOURCE_FILE_NAME_TRUNC = "repeatedColumns_AO401";
        String firstEditOperations = "["
            + "{\"text\":\"7\",\"start\":[0,0,0,0,0],\"name\":\"insertText\"}"
            + ",{\"start\":[0,0,8,0,0],\"name\":\"delete\",\"end\":[0,0,8,0,1]}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     ODT - Table: After saving the row sequences are mixed up
     */
    public void insertTableRowTest() {
        final String SOURCE_FILE_NAME_TRUNC = "repeatedColumns_AO401";
        String firstEditOperations = "["
            + "{\"count\":1,\"start\":[0,2],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":false,\"referenceRow\":1,\"osn\":74},"
            + "{\"start\":[0,2,0,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":75},"
            + "{\"start\":[0,2,1,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":76},"
            + "{\"start\":[0,2,2,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":77},"
            + "{\"start\":[0,2,3,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":78},"
            + "{\"start\":[0,2,4,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":79},"
            + "{\"start\":[0,2,5,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":80},"
            + "{\"start\":[0,2,6,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":81},"
            + "{\"start\":[0,2,7,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":82},"
            + "{\"start\":[0,2,8,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":83},"
            + "{\"count\":1,\"start\":[0,3],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":false,\"referenceRow\":2,\"osn\":84},"
            + "{\"start\":[0,3,0,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":85},"
            + "{\"start\":[0,3,1,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":86},"
            + "{\"start\":[0,3,2,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":87},"
            + "{\"start\":[0,3,3,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":88},"
            + "{\"start\":[0,3,4,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":89},"
            + "{\"start\":[0,3,5,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":90},"
            + "{\"start\":[0,3,6,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":91},"
            + "{\"start\":[0,3,7,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":92},"
            + "{\"start\":[0,3,8,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":93},"
            + "{\"count\":1,\"start\":[0,4],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":false,\"referenceRow\":3,\"osn\":94},"
            + "{\"start\":[0,4,0,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":95},"
            + "{\"start\":[0,4,1,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":96},"
            + "{\"start\":[0,4,2,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":97},"
            + "{\"start\":[0,4,3,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":98},"
            + "{\"start\":[0,4,4,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":99},"
            + "{\"start\":[0,4,5,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":100},"
            + "{\"start\":[0,4,6,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":101},"
            + "{\"start\":[0,4,7,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":102},"
            + "{\"start\":[0,4,8,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":103},"
            + "{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"defaultlist\":\"numbering\",\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L1\",\"osn\":104},"
            + "{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\",\"osn\":105},"
            + "{\"start\":[0,2,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":106},"
            + "{\"text\":\"a\",\"start\":[0,2,0,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":107},"
            + "{\"count\":1,\"start\":[0,3],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":false,\"referenceRow\":2,\"osn\":108},"
            + "{\"start\":[0,3,0,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":109},"
            + "{\"start\":[0,3,1,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":110},"
            + "{\"start\":[0,3,2,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":111},"
            + "{\"start\":[0,3,3,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":112},"
            + "{\"start\":[0,3,4,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":113},"
            + "{\"start\":[0,3,5,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":114},"
            + "{\"start\":[0,3,6,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":115},"
            + "{\"start\":[0,3,7,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":116},"
            + "{\"start\":[0,3,8,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":117}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Change table width (ODT): after downloading/editing this odt the small column is lost
     */
    public void setTableColumnWidthTest() {
        final String SOURCE_FILE_NAME_TRUNC = "TableWidth";
        String firstEditOperations = "["
            //+ "{\"start\":[1],\"attrs\":{\"table\":{\"tableGrid\":[1462,1462,68],\"width\":10615}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":32}"
            + "{\"start\":[1],\"attrs\":{\"table\":{\"tableGrid\":[1390,1390,214],\"width\":12224}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":32}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Insert Row and Column should not show content (no longer
     * cloning XML attribute
     *
     * @office:value)
     */
    public void editComplexTableTest() {
        final String SOURCE_FILE_NAME_TRUNC = "repeatedColumns_AO401";
        String firstEditOperations = "["
            + "{\"insertMode\":\"behind\",\"start\":[0],\"name\":\"insertColumn\",\"tableGrid\":[6553,6553,6553,6553,6553,6553,6553,6553,6553,6558],\"opl\":1,\"gridPosition\":0,\"osn\":74}"
            + ",{\"start\":[0],\"attrs\":{\"table\":{\"tableGrid\":[6553,6553,6553,6553,6553,6553,6553,6553,6553,6558]}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":75}"
            + ",{\"start\":[0,0,1,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":76}"
            + ",{\"start\":[0,1,1,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":77}"
            + ",{\"insertMode\":\"behind\",\"start\":[0],\"name\":\"insertColumn\",\"tableGrid\":[5963,5963,5963,5963,5963,5963,5963,5963,5963,5963,5968],\"opl\":1,\"gridPosition\":4,\"osn\":78}"
            + ",{\"start\":[0],\"attrs\":{\"table\":{\"tableGrid\":[5963,5963,5963,5963,5963,5963,5963,5963,5963,5963,5968]}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":79}"
            + ",{\"start\":[0,0,5,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":80}"
            + ",{\"start\":[0,1,5,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":81}"
            + ",{\"insertMode\":\"behind\",\"start\":[0],\"name\":\"insertColumn\",\"tableGrid\":[5486,5486,5486,5486,5486,5486,5486,5486,5486,5486,5486,5491],\"opl\":1,\"gridPosition\":8,\"osn\":82}"
            + ",{\"start\":[0],\"attrs\":{\"table\":{\"tableGrid\":[5486,5486,5486,5486,5486,5486,5486,5486,5486,5486,5486,5491]}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":83}"
            + ",{\"start\":[0,0,9,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":84}"
            + ",{\"start\":[0,1,9,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":85}"
            + ",{\"insertMode\":\"behind\",\"start\":[0],\"name\":\"insertColumn\",\"tableGrid\":[5047,5047,5047,5047,5047,5047,5047,5047,5047,5047,5047,5047,5052],\"opl\":1,\"gridPosition\":7,\"osn\":86}"
            + ",{\"start\":[0],\"attrs\":{\"table\":{\"tableGrid\":[5047,5047,5047,5047,5047,5047,5047,5047,5047,5047,5047,5047,5052]}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":87}"
            + ",{\"start\":[0,0,8,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":88}"
            + ",{\"start\":[0,1,8,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":89}"
            + ",{\"insertMode\":\"behind\",\"start\":[0],\"name\":\"insertColumn\",\"tableGrid\":[4694,4694,4694,4694,4694,4694,4694,4694,4694,4694,4694,4694,4694,4698],\"opl\":1,\"gridPosition\":9,\"osn\":90}"
            + ",{\"start\":[0],\"attrs\":{\"table\":{\"tableGrid\":[4694,4694,4694,4694,4694,4694,4694,4694,4694,4694,4694,4694,4694,4698]}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":91}"
            + ",{\"start\":[0,0,10,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":92}"
            + ",{\"start\":[0,1,10,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":93}"
            + ",{\"insertMode\":\"behind\",\"start\":[0],\"name\":\"insertColumn\",\"tableGrid\":[4365,4365,4365,4365,4365,4365,4365,4365,4365,4365,4365,4365,4365,4369,4369],\"opl\":1,\"gridPosition\":13,\"osn\":94}"
            + ",{\"start\":[0],\"attrs\":{\"table\":{\"tableGrid\":[4365,4365,4365,4365,4365,4365,4365,4365,4365,4365,4365,4365,4365,4369,4369]}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":95}"
            + ",{\"start\":[0,0,14,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":96}"
            + ",{\"start\":[0,1,14,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":97}"
            + ",{\"count\":1,\"start\":[0,2],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":false,\"referenceRow\":1,\"osn\":98}"
            + ",{\"start\":[0,2,0,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":99}"
            + ",{\"start\":[0,2,1,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":100}"
            + ",{\"start\":[0,2,2,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":101}"
            + ",{\"start\":[0,2,3,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":102}"
            + ",{\"start\":[0,2,4,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":103}"
            + ",{\"start\":[0,2,5,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":104}"
            + ",{\"start\":[0,2,6,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":105}"
            + ",{\"start\":[0,2,7,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":106}"
            + ",{\"start\":[0,2,8,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":107}"
            + ",{\"start\":[0,2,9,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":108}"
            + ",{\"start\":[0,2,10,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":109}"
            + ",{\"start\":[0,2,11,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":110}"
            + ",{\"start\":[0,2,12,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":111}"
            + ",{\"start\":[0,2,13,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":112}"
            + ",{\"start\":[0,2,14,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":113}"
            + ",{\"count\":1,\"start\":[0,1],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":false,\"referenceRow\":0,\"osn\":114}"
            + ",{\"start\":[0,1,0,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":115}"
            + ",{\"start\":[0,1,1,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":116}"
            + ",{\"start\":[0,1,2,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":117}"
            + ",{\"start\":[0,1,3,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":118}"
            + ",{\"start\":[0,1,4,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":119}"
            + ",{\"start\":[0,1,5,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":120}"
            + ",{\"start\":[0,1,6,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":121}"
            + ",{\"start\":[0,1,7,0],\"attrs\":{\"styleId\":\"Table_20_Contents\"},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":122}"
            + ",{\"start\":[0,1,8,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":123}"
            + ",{\"start\":[0,1,9,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":124}"
            + ",{\"start\":[0,1,10,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":125}"
            + ",{\"start\":[0,1,11,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":126}"
            + ",{\"start\":[0,1,12,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":127}"
            + ",{\"start\":[0,1,13,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":128}"
            + ",{\"start\":[0,1,14,0],\"attrs\":{\"styleId\":\"Table_20_Contents\",\"character\":{\"fillColor\":{\"value\":\"00cc33\",\"type\":\"rgb\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":129}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    public void complexTableIndexTest() {
        final String SOURCE_FILE_NAME_TRUNC = "tableComplex_DOC_LO41";
        String firstEditOperations = "["
            + "{\"text\":\" WHAT\",\"start\":[9,4,0,0,16],\"opl\":5,\"name\":\"insertText\",\"osn\":677}"
            + ",{\"text\":\"x\",\"start\":[9,20,1,0,1],\"opl\":1,\"name\":\"insertText\",\"osn\":682}"
            + ",{\"text\":\"d\",\"start\":[9,30,0,0,22],\"opl\":1,\"name\":\"insertText\",\"osn\":683}"
            + ",{\"text\":\"dd\",\"start\":[9,25,0,0,0],\"opl\":2,\"name\":\"insertText\",\"osn\":684}"
            + ",{\"insertMode\":\"behind\",\"start\":[9],\"name\":\"insertColumn\",\"tableGrid\":[35,35,7,7,7,7],\"opl\":1,\"gridPosition\":0,\"osn\":686}"
            + ",{\"start\":[9],\"attrs\":{\"table\":{\"tableGrid\":[35,35,7,7,7,7]}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":687}"
            + ",{\"text\":\"d\",\"start\":[9,2,2,0,3],\"opl\":1,\"name\":\"insertText\",\"osn\":688}"
            + ",{\"text\":\" \",\"start\":[9,4,0,0,21],\"opl\":1,\"name\":\"insertText\",\"osn\":689}"
            + ",{\"insertMode\":\"behind\",\"start\":[9],\"name\":\"insertColumn\",\"tableGrid\":[26,26,26,5,5,5,5],\"opl\":1,\"gridPosition\":0,\"osn\":690}"
            + ",{\"start\":[9],\"attrs\":{\"table\":{\"tableGrid\":[26,26,26,5,5,5,5]}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":691}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Table 1*1 - add column: => java.lang.NullPointerException
     */
    public void insertColumnTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"styleId\":\"TableGrid\",\"styleName\":\"Table Grid\",\"attrs\":{\"wholeTable\":{\"paragraph\":{\"lineHeight\":{\"value\":100,\"type\":\"percent\"}},\"table\":{\"borderInsideVert\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderInsideHor\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderBottom\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderLeft\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderRight\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingBottom\":0,\"paddingLeft\":190,\"paddingRight\":190}}},\"name\":\"insertStyleSheet\",\"opl\":1,\"uiPriority\":59,\"type\":\"table\",\"osn\":16}"
            + ",{\"start\":[1],\"attrs\":{\"styleId\":\"TableGrid\",\"table\":{\"tableGrid\":[1000],\"width\":\"auto\",\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":17}"
            + ",{\"count\":1,\"start\":[1,0],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":true,\"osn\":18}"
            + ",{\"insertMode\":\"behind\",\"start\":[1],\"name\":\"insertColumn\",\"tableGrid\":[500,500],\"opl\":1,\"gridPosition\":0,\"osn\":19}"
            + ",{\"start\":[1],\"attrs\":{\"table\":{\"tableGrid\":[500,500]}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":20}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Enhance Line Height by adding leading ("Bleistreifen" from former print)
     * - the space between lines
     */
    public void removeLineHeightLeadingTest() {
        final String SOURCE_FILE_NAME_TRUNC = "lineHeight_AO4-0-1";
        String firstEditOperations = "["
            + "{\"start\":[5],\"attrs\":{\"paragraph\":{\"lineHeight\":null}},\"name\":\"setAttributes\"}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Removing one style property, removes all style properties.
     */
    public void lostBackground() {
        final String SOURCE_FILE_NAME_TRUNC = "lostBackground";
        String firstEditOperations = "["
            + "{\"start\":[11,0],\"attrs\":{\"character\":{\"fontSize\":null}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[11,3],\"osn\":102}"
            + ",{\"start\":[10,0],\"attrs\":{\"character\":{\"fontSize\":null}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[10,3],\"osn\":103}"
            + ",{\"start\":[8,1,0,0,0],\"attrs\":{\"character\":{\"fontSize\":null}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[8,1,0,0,11],\"osn\":104}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Text inserted after a hyperlink always becomes an Hyperlink
     */
    public void hyperlinkExtensionTest() {
        final String SOURCE_FILE_NAME_TRUNC = "hyperlinkSpacesNoUnderline";
        String firstEditOperations = "["
            + "{\"text\":\"NoLink!\",\"start\":[5,37],\"attrs\":{\"styleId\":null,\"character\":{\"url\":null}},\"opl\":6,\"name\":\"insertText\",\"osn\":48}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * ODF space XML element that contains more than two spaces and was not
     * completely deleted, returned the removed spaces as remaining element
     */
    public void spaceElementSplitTest() {
        final String SOURCE_FILE_NAME_TRUNC = "hyperlinkSpacesNoUnderline";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[5,34],\"name\":\"delete\",\"opl\":1,\"end\":[5,34],\"osn\":48}"
            + ",{\"start\":[5,33],\"name\":\"delete\",\"opl\":1,\"end\":[5,33],\"osn\":49}"
            + ",{\"start\":[5,32],\"name\":\"delete\",\"opl\":1,\"end\":[5,32],\"osn\":50}"
            + ",{\"start\":[5,31],\"name\":\"delete\",\"opl\":1,\"end\":[5,31],\"osn\":51}"
            + ",{\"start\":[5,30],\"name\":\"delete\",\"opl\":1,\"end\":[5,30],\"osn\":52}"
            + ",{\"start\":[5,29],\"name\":\"delete\",\"opl\":1,\"end\":[5,29],\"osn\":53}"
            + ",{\"start\":[5,28],\"name\":\"delete\",\"opl\":1,\"end\":[5,28],\"osn\":54}"
            + ",{\"start\":[5,27],\"name\":\"delete\",\"opl\":1,\"end\":[5,27],\"osn\":55}"
            + ",{\"start\":[5,26],\"name\":\"delete\",\"opl\":1,\"end\":[5,26],\"osn\":56}"
            + ",{\"start\":[5,25],\"name\":\"delete\",\"opl\":1,\"end\":[5,25],\"osn\":57}"
            + ",{\"start\":[5,24],\"name\":\"delete\",\"opl\":1,\"end\":[5,24],\"osn\":58}"
            + ",{\"start\":[5,23],\"name\":\"delete\",\"opl\":1,\"end\":[5,23],\"osn\":59}"
            + ",{\"start\":[5,22],\"name\":\"delete\",\"opl\":1,\"end\":[5,22],\"osn\":60}"
            + ",{\"start\":[5,21],\"name\":\"delete\",\"opl\":1,\"end\":[5,21],\"osn\":61}"
            + ",{\"start\":[5,20],\"name\":\"delete\",\"opl\":1,\"end\":[5,20],\"osn\":62}"
            + ",{\"start\":[5,19],\"name\":\"delete\",\"opl\":1,\"end\":[5,19],\"osn\":63}"
            + ",{\"start\":[5,18],\"name\":\"delete\",\"opl\":1,\"end\":[5,18],\"osn\":64}"
            + ",{\"start\":[5,17],\"name\":\"delete\",\"opl\":1,\"end\":[5,17],\"osn\":65}"
            + ",{\"start\":[5,16],\"name\":\"delete\",\"opl\":1,\"end\":[5,16],\"osn\":66}"
            + ",{\"start\":[5,15],\"name\":\"delete\",\"opl\":1,\"end\":[5,15],\"osn\":67}"
            + ",{\"start\":[5,14],\"name\":\"delete\",\"opl\":1,\"end\":[5,14],\"osn\":68}"
            + ",{\"start\":[5,13],\"name\":\"delete\",\"opl\":1,\"end\":[5,13],\"osn\":69}"
            + ",{\"start\":[5,12],\"name\":\"delete\",\"opl\":1,\"end\":[5,12],\"osn\":70}"
            + ",{\"start\":[5,11],\"name\":\"delete\",\"opl\":1,\"end\":[5,11],\"osn\":71}"
            + ",{\"start\":[5,10],\"name\":\"delete\",\"opl\":1,\"end\":[5,10],\"osn\":72}"
            + ",{\"start\":[5,9],\"name\":\"delete\",\"opl\":1,\"end\":[5,9],\"osn\":73}"
            + ",{\"start\":[5,8],\"name\":\"delete\",\"opl\":1,\"end\":[5,8],\"osn\":74}"
            + ",{\"start\":[5,7],\"name\":\"delete\",\"opl\":1,\"end\":[5,7],\"osn\":75}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODF space XML element that contains more than two spaces and was not
     * completely deleted, returned the removed spaces as remaining element
     */
    public void hyperlinkUnderscoreRemovalTest() {
        final String SOURCE_FILE_NAME_TRUNC = "hyperlinkSpaces";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[5,7],\"attrs\":{\"character\":{\"underline\":false}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[5,34],\"osn\":48}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /*
     * ODT - Roundtrip - numbered list: gap between preview / editor
     */
    public void listRoundtripTest() {
        final String SOURCE_FILE_NAME_TRUNC = "ListRoundtrip";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"svante\",\"start\":[6,0],\"opl\":5,\"name\":\"insertText\",\"osn\":90},"
            + "{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"}"
            + ",\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"defaultlist\":\"numbering\",\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"}"
            + ",\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"}"
            + ",\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}}"
            + ",\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L4\",\"osn\":95}"
            + ",{\"start\":[6],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L4\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":96}"
            + ",{\"start\":[6,5],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":97}"
            + ",{\"start\":[7],\"attrs\":{\"paragraph\":{\"listLevel\":1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":98}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * Foreign ODF elements could not be cloned. Throwable are now caught and
     * rethrown as filterexception to be found by Admin logging -- adding
     * regression test references
     */
    public void foreignElementSplitTest() {
        final String SOURCE_FILE_NAME_TRUNC = "foreignElementSplit";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[1,9,1,1,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":495}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * Preceding nested paragraph in paragraph within unknown
     * content parent was erroneously counted as component
     */
    public void insertCharacterBehindLineShapeWithParagraphTest() {
        final String SOURCE_FILE_NAME_TRUNC = "sample";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"123456789\",\"start\":[4,22],\"opl\":9,\"name\":\"insertText\",\"osn\":85}"
            + "]";

        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT - Hyperlinks over full unformatted text looses first
     * character.
     */
    public void anchorOverFullUnformattedTextTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"eins  zwei\",\"start\":[0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":16},{\"styleId\":\"Hyperlink\",\"styleName\":\"Hyperlink\",\"attrs\":{\"character\":{\"color\":{\"value\":\"hyperlink\",\"fallbackValue\":\"0080C0\",\"type\":\"scheme\"},\"underline\":true}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":null,\"uiPriority\":99,\"type\":\"character\",\"osn\":17},{\"start\":[0,0],\"attrs\":{\"styleId\":\"Hyperlink\",\"character\":{\"url\":\"http://www.heise.de\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0,10],\"osn\":18}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT - Hyperlinks with spaces >1: Hyperlink/First letter is lost after reediting
     */
    public void anchorNoneBreakableTest() {
        final String SOURCE_FILE_NAME_TRUNC = "liste2";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[0,0],\"attrs\":{\"styleId\":\"Hyperlink\",\"character\":{\"url\":\"http://www.heise.de\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0,11],\"osn\":19}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT - Remove attribute does not works
     */
    public void removeUnderlineTest() {
        final String SOURCE_FILE_NAME_TRUNC = "UNDERLINE";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[0,0],\"attrs\":{\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0,8],\"osn\":18}"
            + "]";

        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT - Remove attribute does not works
     */
    public void removeUnderlineBoldAddColorTest() {
        final String SOURCE_FILE_NAME_TRUNC = "UNDERLINE";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[0,0],\"attrs\":{\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0,8],\"osn\":18}"
            + "]";

        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODF toolkit: hyperlink is not properly overwritten with new
     */
    public void hyperlinkOverridenByHyperlinkTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"Empty Document\",\"start\":[0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":21}"
            + ",{\"start\":[0,2],\"attrs\":{\"styleId\":\"Hyperlink\",\"character\":{\"url\":\"http://www.heise.de\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0,8],\"osn\":22}"
            + ",{\"start\":[0,1],\"attrs\":{\"styleId\":\"Hyperlink\",\"character\":{\"url\":\"http://www.sueddeutsche.de\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0,9],\"osn\":23}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * Split Paragraph at end of hyperlink clones hyperlink
     */
    public void listHyperlinkTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"link\",\"start\":[0,0],\"opl\":4,\"name\":\"insertText\",\"osn\":16}"
            + ",{\"styleId\":\"Hyperlink\",\"styleName\":\"Hyperlink\",\"attrs\":{\"character\":{\"color\":{\"value\":\"hyperlink\",\"fallbackValue\":\"0080C0\",\"type\":\"scheme\"},\"underline\":true}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":null,\"uiPriority\":99,\"type\":\"character\",\"osn\":20}"
            + ",{\"start\":[0,2],\"attrs\":{\"styleId\":\"Hyperlink\",\"character\":{\"url\":\"http://www.heise.de\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0,2],\"osn\":21}"
            + ",{\"start\":[0,4],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":22}"
            + ",{\"text\":\"link2\",\"start\":[1,0],\"opl\":5,\"name\":\"insertText\",\"osn\":23}"
            + ",{\"start\":[1,0],\"attrs\":{\"styleId\":\"Hyperlink\",\"character\":{\"url\":\"http://www.spiegel.de\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,4],\"osn\":28}"
            + ",{\"start\":[1,5],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":29}"
            + ",{\"text\":\"link3\",\"start\":[2,0],\"attrs\":{\"styleId\":null,\"character\":{\"url\":null}},\"opl\":5,\"name\":\"insertText\",\"osn\":30}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT - mixed list (numbered/ bullet list): numbering is
     * different in preview /editor
     */
    public void listLevelContinousTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"1. eins\",\"start\":[0,0],\"opl\":9,\"name\":\"insertText\",\"osn\":16}"
            + ",{\"start\":[0,7],\"name\":\"insertTab\",\"opl\":1,\"osn\":25}"
            + ",{\"start\":[0,7],\"name\":\"delete\",\"opl\":1,\"end\":[0,7],\"osn\":26}"
            + ",{\"start\":[0,7],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":27}"
            + ",{\"start\":[0,0],\"name\":\"delete\",\"opl\":1,\"end\":[0,2],\"osn\":28}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L1\",\"osn\":29}"
            + ",{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\",\"osn\":30}"
            + ",{\"start\":[0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":31}"
            + ",{\"start\":[1],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":32}"
            + ",{\"text\":\"zwei\",\"start\":[1,0],\"opl\":4,\"name\":\"insertText\",\"osn\":33}"
            + ",{\"start\":[1,4],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":37}"
            + ",{\"text\":\"drei\",\"start\":[2,0],\"opl\":4,\"name\":\"insertText\",\"osn\":38}"
            + ",{\"start\":[2,4],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":42}"
            + ",{\"text\":\"vier\",\"start\":[3,0],\"opl\":4,\"name\":\"insertText\",\"osn\":43}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"■\",\"indentLeft\":11430,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel6\":{\"listStartValue\":1,\"fontName\":\"Symbol\",\"levelText\":\"\",\"indentLeft\":8890,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel7\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"○\",\"indentLeft\":10160,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"defaultlist\":\"bullet\",\"listLevel5\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"■\",\"indentLeft\":7620,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel4\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"○\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel3\":{\"listStartValue\":1,\"fontName\":\"Symbol\",\"levelText\":\"\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel2\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"■\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel1\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"○\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel0\":{\"listStartValue\":1,\"fontName\":\"Symbol\",\"levelText\":\"\",\"indentLeft\":1270,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L2\",\"osn\":47}"
            + ",{\"start\":[1],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":48}"
            + ",{\"start\":[2],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":49}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * Big table size limit - ODT -Edit: check for table limitation is missing
     */
    public void listAnomalityTest() {
        final String SOURCE_FILE_NAME_TRUNC = "BigTable";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "[" + "]";

        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT numbered Lists after saving: Labels and the sublist
     * entries are wrong
     */
    public void listLevelComplexTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"attrs\":{\"document\":{\"defaultTabStop\":1251}},\"name\":\"setDocumentAttributes\"},{\"styleName\":\"Default Paragraph Style\",\"styleId\":\"default_paragraph_style\",\"default\":true,\"hidden\":true,\"attrs\":{\"character\":{\"fontSizeAsian\":12,\"fontName\":\"Times New Roman\",\"fontNameComplex\":\"Mangal\",\"language\":\"de-DE\",\"fontSize\":12,\"fontNameAsian\":\"SimSun\"}},\"name\":\"insertStyleSheet\",\"type\":\"paragraph\"},{\"styleId\":\"Standard\",\"attrs\":{},\"name\":\"insertStyleSheet\",\"parent\":\"default_paragraph_style\",\"type\":\"paragraph\"},{\"styleId\":\"Caption\",\"attrs\":{\"paragraph\":{\"marginBottom\":212,\"marginTop\":212},\"character\":{\"fontSizeAsian\":12,\"italicComplex\":true,\"fontNameComplex\":\"Mangal1\",\"italicAsian\":true,\"italic\":true,\"fontSize\":12}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},{\"styleId\":\"Heading\",\"attrs\":{\"paragraph\":{\"nextStyleId\":\"Text_20_body\",\"marginTop\":423,\"marginBottom\":212},\"character\":{\"fontSizeAsian\":14,\"fontNameComplex\":\"Mangal\",\"fontName\":\"Arial\",\"fontSize\":14,\"fontNameAsian\":\"Microsoft YaHei\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},{\"styleId\":\"Index\",\"attrs\":{\"character\":{\"fontNameComplex\":\"Mangal1\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},{\"styleName\":\"List Paragraph\",\"styleId\":\"ListParagraph\",\"attrs\":{\"paragraph\":{\"nextStyleId\":\"ListParagraph\",\"indentLeft\":1270,\"marginLeft\":1270}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},{\"styleName\":\"Text body\",\"styleId\":\"Text_20_body\",\"attrs\":{\"paragraph\":{\"marginBottom\":212,\"marginTop\":0}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},{\"styleId\":\"List\",\"attrs\":{\"character\":{\"fontNameComplex\":\"Mangal1\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Text_20_body\",\"type\":\"paragraph\"},{\"attrs\":{\"page\":{\"printOrientation\":\"portrait\",\"marginBottom\":2000,\"indentRight\":2000,\"height\":29700,\"indentLeft\":2000,\"marginTop\":2000,\"marginLeft\":2000,\"numberFormat\":\"1\",\"width\":21001,\"marginRight\":2000}},\"name\":\"setDocumentAttributes\"},{\"fontName\":\"Mangal1\",\"attrs\":{\"family\":\"Mangal\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"Times New Roman\",\"attrs\":{\"family\":\"'Times New Roman'\",\"familyGeneric\":\"roman\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"Arial\",\"attrs\":{\"family\":\"Arial\",\"familyGeneric\":\"swiss\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"Mangal\",\"attrs\":{\"family\":\"Mangal\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"Microsoft YaHei\",\"attrs\":{\"family\":\"'Microsoft YaHei'\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"SimSun\",\"attrs\":{\"family\":\"SimSun\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":\"1\",\"levelText\":\"%9.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel6\":{\"listStartValue\":\"1\",\"levelText\":\"%7.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":\"1\",\"levelText\":\"%8.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":\"1\",\"levelText\":\"%6.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":\"1\",\"levelText\":\"%5.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":\"1\",\"levelText\":\"%4.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":\"1\",\"levelText\":\"%3.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":\"1\",\"levelText\":\"%2.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":\"1\",\"levelText\":\"%1.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"listStyleId\":\"L30001\"},{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30001\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},{\"text\":\"eins\",\"start\":[0,0],\"name\":\"insertText\"},{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30001\",\"listLevel\":1}},\"name\":\"insertParagraph\"},{\"text\":\"zwei\",\"start\":[1,0],\"name\":\"insertText\"},{\"start\":[2],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30001\",\"listLevel\":2}},\"name\":\"insertParagraph\"},{\"text\":\"drei\",\"start\":[2,0],\"name\":\"insertText\"},{\"listDefinition\":{\"listLevel8\":{\"levelText\":\" \",\"labelFollowedBy\":\"listtab\",\"indentLeft\":11430,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel6\":{\"levelText\":\" \",\"labelFollowedBy\":\"listtab\",\"indentLeft\":8890,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"levelText\":\" \",\"labelFollowedBy\":\"listtab\",\"indentLeft\":10160,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"levelText\":\" \",\"labelFollowedBy\":\"listtab\",\"indentLeft\":7620,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"levelText\":\" \",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"levelText\":\" \",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"levelText\":\" \",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"levelText\":\" \",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"levelText\":\" \",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"listStyleId\":\"L20014\"},{\"start\":[3],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L20014\",\"listLevel\":3}},\"name\":\"insertParagraph\"},{\"text\":\"vier\",\"start\":[3,0],\"name\":\"insertText\"},{\"start\":[4],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L20014\",\"listLevel\":3}},\"name\":\"insertParagraph\"},{\"text\":\"fünf\",\"start\":[4,0],\"name\":\"insertText\"},{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L20014\",\"listLevel\":3}},\"name\":\"insertParagraph\"},{\"text\":\"sechs\",\"start\":[5,0],\"name\":\"insertText\"},{\"listDefinition\":{\"listLevel8\":{\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":11430,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel6\":{\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":8890,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":10160,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":7620,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"listStyleId\":\"L20001\"},{\"start\":[6],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L20001\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},{\"text\":\"bullet\",\"start\":[6,0],\"name\":\"insertText\"},{\"start\":[7],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L20001\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},{\"text\":\"bullet2\",\"start\":[7,0],\"name\":\"insertText\"},{\"start\":[8],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L20001\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},{\"text\":\"bullet3\",\"start\":[8,0],\"name\":\"insertText\"},"
            + "{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":2}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":37},{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":3}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":38},{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":2}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":39},{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":3}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":40},{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":2}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":41},{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":42},{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":43},{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":44},{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":2}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":45},{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":3}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":46},{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":2}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":47},{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":48},{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":2}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":49},{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":3}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":50},{\"start\":[2],\"name\":\"mergeParagraph\",\"opl\":1,\"osn\":51},{\"start\":[2,4],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":52},{\"start\":[5],\"attrs\":{\"paragraph\":{\"listLevel\":2}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":53},{\"start\":[5],\"attrs\":{\"paragraph\":{\"listLevel\":1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":54},{\"start\":[5],\"attrs\":{\"paragraph\":{\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":55},{\"start\":[5],\"attrs\":{\"paragraph\":{\"listLevel\":-1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":56},{\"start\":[5],\"attrs\":{\"paragraph\":{\"listStyleId\":null}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":57},{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"Standard\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":58},{\"start\":[4],\"name\":\"mergeParagraph\",\"opl\":1,\"osn\":59},{\"start\":[4,3],\"name\":\"delete\",\"opl\":1,\"end\":[4,3],\"osn\":60},{\"start\":[4,3],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":61},{\"start\":[4],\"attrs\":{\"paragraph\":{\"listLevel\":2}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":62},{\"start\":[4],\"attrs\":{\"paragraph\":{\"listLevel\":1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":63},{\"start\":[4],\"attrs\":{\"paragraph\":{\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":64},{\"start\":[4],\"attrs\":{\"paragraph\":{\"listLevel\":-1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":65},{\"start\":[4],\"attrs\":{\"paragraph\":{\"listStyleId\":null}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":66},{\"start\":[4],\"attrs\":{\"paragraph\":{\"style\":\"Standard\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":67},{\"start\":[3],\"name\":\"mergeParagraph\",\"opl\":1,\"osn\":68},{\"start\":[3,3],\"name\":\"delete\",\"opl\":1,\"end\":[3,3],\"osn\":69},{\"text\":\"r\",\"start\":[3,3],\"opl\":1,\"name\":\"insertText\",\"osn\":70},{\"start\":[3,4],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":71},{\"text\":\"f\",\"start\":[4,3],\"opl\":1,\"name\":\"insertText\",\"osn\":72},{\"start\":[4],\"name\":\"mergeParagraph\",\"opl\":1,\"osn\":73},{\"start\":[4,4],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":74},"
            + "{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":\"1\",\"levelText\":\"%9.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listIdentifier\":\"L30001\",\"listLevel6\":{\"listStartValue\":\"1\",\"levelText\":\"%7.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevels\":[{\"listStartValue\":\"1\",\"levelText\":\"%1.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":\"1\",\"levelText\":\"%2.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":\"1\",\"levelText\":\"%3.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":\"1\",\"levelText\":\"%4.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":\"1\",\"levelText\":\"%5.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":\"1\",\"levelText\":\"%6.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":\"1\",\"levelText\":\"%7.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":\"1\",\"levelText\":\"%8.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":\"1\",\"levelText\":\"%9.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}],\"listLevel7\":{\"listStartValue\":\"1\",\"levelText\":\"%8.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":\"1\",\"levelText\":\"%6.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":\"1\",\"levelText\":\"%5.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":\"1\",\"levelText\":\"%4.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"(%3)\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":\"1\",\"levelText\":\"%2.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":\"1\",\"levelText\":\"%1.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L4\",\"osn\":75}"
            + ",{\"start\":[2],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L4\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":76}"
            + ",{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L4\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":77}"
            + ",{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L4\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":78}"
            + ",{\"start\":[4],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L4\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":79}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L4\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":80}"
            + ",{\"start\":[3],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L4\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":81}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT numbered Lists after saving: Labels and the sublist
     * entries are wrong
     */
    public void listMultipleSimularChangeTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"1.\",\"start\":[0,0],\"opl\":2,\"name\":\"insertText\",\"osn\":16}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"defaultlist\":\"numbering\",\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L1\",\"osn\":18}"
            + ",{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\",\"osn\":19}"
            + ",{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":20}"
            + ",{\"start\":[0,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":21}"
            + ",{\"start\":[1],\"attrs\":{\"paragraph\":{\"listLevel\":1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":22}"
            + ",{\"text\":\"a.\",\"start\":[1,0],\"opl\":2,\"name\":\"insertText\",\"osn\":23}"
            + ",{\"start\":[1,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":25}"
            + ",{\"text\":\"b.\",\"start\":[2,0],\"opl\":2,\"name\":\"insertText\",\"osn\":26}"
            + ",{\"start\":[2,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":28}"
            + ",{\"text\":\"c.\",\"start\":[3,0],\"opl\":2,\"name\":\"insertText\",\"osn\":29}"
            + ",{\"start\":[3,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":31}"
            + ",{\"text\":\"d.\",\"start\":[4,0],\"opl\":2,\"name\":\"insertText\",\"osn\":32}"
            + ",{\"start\":[4,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":34}"
            + ",{\"text\":\"2.\",\"start\":[5,0],\"opl\":2,\"name\":\"insertText\",\"osn\":35}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":39}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listIdentifier\":\"L1\",\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevels\":[{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"}],\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2)\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L2\",\"osn\":40}"
            + ",{\"start\":[2],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":41}"
            + ",{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":42}"
            + ",{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":43}"
            + ",{\"start\":[4],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":44}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":45}"
            + ",{\"start\":[3],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":46}"
            + "]";
        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    /**
     *
     * FIXED: 1) Changing an automatic styles, requires to rename it, if it is
     * being used by others! FIXME: 2) outlineLevel:0 check, copy all attributes
     * and descendent from text:p to text:h and vice versa..
     */
    public void changeParagraphToHeaderTest() {
        final String SOURCE_FILE_NAME_TRUNC = "field";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[1,0],\"name\":\"splitParagraph\"},"
            + "{\"text\":\"Level 1 Heading!\",\"start\":[2,0],\"name\":\"insertText\"},"
            + "{\"styleName\":\"heading 1\",\"styleId\":\"heading1\",\"attrs\":{\"paragraph\":{\"outlineLevel\":0,\"nextStyleId\":\"Standard\"},\"character\":{\"bold\":true,\"color\":{\"value\":\"accent1\",\"fallbackValue\":\"376092\",\"transformations\":[{\"value\":74902,\"type\":\"shade\"}],\"type\":\"scheme\"},\"fontSize\":14}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"uiPriority\":9,\"type\":\"paragraph\"},"
            + "{\"start\":[2],\"attrs\":{\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"nextStyleId\":null,\"listStyleId\":null,\"borderBottom\":null,\"listLevel\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"showListLabel\":null,\"marginBottom\":null,\"tabStops\":null,\"listStartValue\":null,\"indentRight\":null,\"style\":\"heading1\",\"indentLeft\":null,\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"strike\":null,\"bold\":null,\"fontName\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"language\":null,\"italic\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\"},"
            + "{\"start\":[2,16],\"name\":\"splitParagraph\"},"
            + "{\"start\":[2,16],\"name\":\"splitParagraph\"},"
            + "{\"text\":\"No Heading!\",\"start\":[3,0],\"name\":\"insertText\"},"
            + "{\"text\":\"Heading by Parent!\",\"start\":[4,0],\"name\":\"insertText\"},"
            + "{\"styleName\":\"No Heading\",\"styleId\":\"noheading\",\"attrs\":{\"paragraph\":{\"nextStyleId\":\"Standard\"},\"character\":{\"bold\":true,\"color\":{\"value\":\"accent1\",\"fallbackValue\":\"376092\",\"transformations\":[{\"value\":74902,\"type\":\"shade\"}],\"type\":\"scheme\"},\"fontSize\":14}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"uiPriority\":9,\"type\":\"paragraph\"},"
            + "{\"start\":[3],\"attrs\":{\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"nextStyleId\":null,\"listStyleId\":null,\"borderBottom\":null,\"listLevel\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"showListLabel\":null,\"marginBottom\":null,\"tabStops\":null,\"listStartValue\":null,\"indentRight\":null,\"style\":\"noheading\",\"indentLeft\":null,\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"strike\":null,\"bold\":null,\"fontName\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"language\":null,\"italic\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\"},"
            + "{\"styleName\":\"No Heading But Parent\",\"styleId\":\"noheadingbutParent\",\"attrs\":{\"paragraph\":{\"nextStyleId\":\"Standard\"},\"character\":{\"bold\":true,\"color\":{\"value\":\"accent1\",\"fallbackValue\":\"376092\",\"transformations\":[{\"value\":74902,\"type\":\"shade\"}],\"type\":\"scheme\"},\"fontSize\":14}},\"name\":\"insertStyleSheet\",\"parent\":\"headingParent\",\"uiPriority\":9,\"type\":\"paragraph\"},"
            + "{\"styleName\":\"Heading Parent\",\"styleId\":\"headingParent\",\"attrs\":{\"paragraph\":{\"outlineLevel\":3,\"nextStyleId\":\"Standard\"},\"character\":{\"bold\":true,\"color\":{\"value\":\"accent1\",\"fallbackValue\":\"376092\",\"transformations\":[{\"value\":74902,\"type\":\"shade\"}],\"type\":\"scheme\"},\"fontSize\":14}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"uiPriority\":9,\"type\":\"paragraph\"},"
            + "{\"start\":[4],\"attrs\":{\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"nextStyleId\":null,\"listStyleId\":null,\"borderBottom\":null,\"listLevel\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"showListLabel\":null,\"marginBottom\":null,\"tabStops\":null,\"listStartValue\":null,\"indentRight\":null,\"style\":\"headingParent\",\"indentLeft\":null,\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"strike\":null,\"bold\":null,\"fontName\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"language\":null,\"italic\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\"},"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * List items across the document with the same style do not
     * influence each other in ODF
     */
    public void listWithContinuousNumberingTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9)\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7)\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8)\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6)\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5)\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4)\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3)\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2)\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1)\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L30002\",\"osn\":16}"
            + ",{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\",\"osn\":17}"
            + ",{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30002\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":18}"
            + ",{\"text\":\"1)\",\"start\":[0,0],\"opl\":2,\"name\":\"insertText\",\"osn\":19}"
            + ",{\"start\":[0,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":21}"
            + ",{\"start\":[1],\"attrs\":{\"paragraph\":{\"listLevel\":1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":22}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9)\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listIdentifier\":\"L30002\",\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7)\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevels\":[{\"listStartValue\":1,\"levelText\":\"%1)\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%2)\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%3)\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%4)\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%5)\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%6)\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%7)\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%8)\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%9)\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}],\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8)\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6)\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5)\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4)\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3)\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2)\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1)\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L2\",\"osn\":23}"
            + ",{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":24}"
            + ",{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":25}"
            + ",{\"start\":[1],\"attrs\":{\"paragraph\":{\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":26}"
            + ",{\"start\":[1],\"attrs\":{\"paragraph\":{\"listLevel\":1}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1],\"osn\":27}"
            + ",{\"text\":\"a)\",\"start\":[1,0],\"opl\":2,\"name\":\"insertText\",\"osn\":28}"
            + ",{\"start\":[1,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":30}"
            + ",{\"text\":\"b)\",\"start\":[2,0],\"opl\":2,\"name\":\"insertText\",\"osn\":31}"
            + ",{\"start\":[2,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":33}"
            + ",{\"text\":\"c)\",\"start\":[3,0],\"opl\":2,\"name\":\"insertText\",\"osn\":34}"
            + ",{\"start\":[3,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":36}"
            + ",{\"start\":[4],\"attrs\":{\"paragraph\":{\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":37}"
            + ",{\"text\":\"2)\",\"start\":[4,0],\"opl\":2,\"name\":\"insertText\",\"osn\":38}"
            + "]";

        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT - Reedit odt with List entries: list entries are
     * displayed on the wrong position
     */
    public void listMixedFirstLevelFullTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"1.\",\"start\":[0,0],\"opl\":2,\"name\":\"insertText\",\"osn\":16}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L30001\",\"osn\":18}"
            + ",{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\",\"osn\":19}"
            + ",{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30001\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":20}"
            + ",{\"start\":[0,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":21}"
            + ",{\"text\":\"2.\",\"start\":[1,0],\"opl\":2,\"name\":\"insertText\",\"osn\":22}"
            + ",{\"start\":[1,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":24}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listIdentifier\":\"L30001\",\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevels\":[{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}],\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L2\",\"osn\":25}"
            + ",{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":26}"
            + ",{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":27}"
            + ",{\"start\":[2],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":28}"
            + ",{\"start\":[2],\"attrs\":{\"paragraph\":{\"listStyleId\":\"L30001\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[2],\"osn\":29}"
            + ",{\"start\":[0],\"attrs\":{\"paragraph\":{\"listStyleId\":\"L30001\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0],\"osn\":30}"
            + ",{\"start\":[1],\"attrs\":{\"paragraph\":{\"listStyleId\":\"L30001\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1],\"osn\":31}"
            + ",{\"name\":\"deleteListStyle\",\"opl\":1,\"listStyleId\":\"L2\",\"osn\":32}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L30011\",\"osn\":35}"
            + ",{\"start\":[2],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30011\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":36}"
            + ",{\"start\":[2,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":37}"
            + ",{\"text\":\"b.\",\"start\":[3,0],\"opl\":2,\"name\":\"insertText\",\"osn\":38}"
            + ",{\"start\":[3,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":40}"
            + ",{\"text\":\"1.\",\"start\":[4,0],\"opl\":2,\"name\":\"insertText\",\"osn\":41}"
            + ",{\"start\":[4],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30001\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":43}"
            + ",{\"start\":[4,0],\"name\":\"delete\",\"opl\":1,\"end\":[4,0],\"osn\":44}"
            + ",{\"text\":\"3\",\"start\":[4,0],\"opl\":1,\"name\":\"insertText\",\"osn\":45}"
            + ",{\"start\":[4,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":46}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listIdentifier\":\"L30001\",\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevels\":[{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}],\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1)\",\"indentLeft\":1270,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L3\",\"osn\":47}"
            + ",{\"start\":[4],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L3\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":48}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L3\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":49}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"listStyleId\":\"L30001\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[5],\"osn\":50}"
            + ",{\"start\":[4],\"attrs\":{\"paragraph\":{\"listStyleId\":\"L30001\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[4],\"osn\":51}"
            + ",{\"name\":\"deleteListStyle\",\"opl\":1,\"listStyleId\":\"L3\",\"osn\":52}"
            + ",{\"text\":\"4\",\"start\":[5,0],\"opl\":1,\"name\":\"insertText\",\"osn\":53}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":null,\"listLevel\":-1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":54}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30001\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[5],\"osn\":55}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"■\",\"indentLeft\":11430,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel6\":{\"listStartValue\":1,\"fontName\":\"Symbol\",\"levelText\":\"\",\"indentLeft\":8890,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel7\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"○\",\"indentLeft\":10160,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"defaultlist\":\"bullet\",\"listLevel5\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"■\",\"indentLeft\":7620,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel4\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"○\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel3\":{\"listStartValue\":1,\"fontName\":\"Symbol\",\"levelText\":\"\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel2\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"■\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel1\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"○\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel0\":{\"listStartValue\":1,\"fontName\":\"Symbol\",\"levelText\":\"\",\"indentLeft\":1270,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L3\",\"osn\":56}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L3\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":57}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"defaultlist\":\"numbering\",\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L4\",\"osn\":58}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L4\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":59}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listIdentifier\":\"L4\",\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevels\":[{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"}],\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L5\",\"osn\":60}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L5\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":61}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":null,\"listLevel\":-1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":62}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L4\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":63}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9)\",\"indentLeft\":11430,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7)\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8)\",\"indentLeft\":10160,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6)\",\"indentLeft\":7620,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5)\",\"indentLeft\":6350,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4)\",\"indentLeft\":5080,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3)\",\"indentLeft\":3810,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2)\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1)\",\"indentLeft\":1270,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L30012\",\"osn\":64}"
            + ",{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30012\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":65}"
            + ",{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30012\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":66}"
            + ",{\"start\":[4],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30012\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":67}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30012\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":68}"
            + "]";

        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT - Reedit odt with List entries: list entries are
     * displayed on the wrong position
     */
    public void listMixedFirstLevelTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"1.\",\"start\":[0,0],\"opl\":2,\"name\":\"insertText\",\"osn\":16}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + "\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,"
            + "\"listStyleId\":\"L30001\",\"osn\":18}"
            + ",{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\",\"osn\":19}"
            + ",{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30001\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":20}"
            + ",{\"start\":[0,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":21}"
            + ",{\"text\":\"2.\",\"start\":[1,0],\"opl\":2,\"name\":\"insertText\",\"osn\":22}"
            + ",{\"start\":[1,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":24}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listIdentifier\":\"L30001\",\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevels\":[{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}],"
            + "\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + "\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L2\",\"osn\":25}"
            + ",{\"text\":\"a.\",\"start\":[2,0],\"opl\":2,\"name\":\"insertText\",\"osn\":33}"
            + ",{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":26}"
            + ",{\"text\":\"b.\",\"start\":[2,0],\"opl\":2,\"name\":\"insertText\",\"osn\":33}"
            + "]";

        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);

        //super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT - Reedit odt with List entries: list entries are
     * displayed on the wrong position
     */
    public void list6LevelTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L30001\",\"osn\":16}"
            + ",{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\",\"osn\":17}"
            + ",{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L30001\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":18}"
            + ",{\"text\":\"eins\",\"start\":[0,0],\"opl\":4,\"name\":\"insertText\",\"osn\":19}"
            + ",{\"start\":[0,4],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":23}"
            + ",{\"start\":[1],\"attrs\":{\"paragraph\":{\"listLevel\":1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":24}"
            + ",{\"text\":\"zwei\",\"start\":[1,0],\"opl\":4,\"name\":\"insertText\",\"osn\":31}"
            + ",{\"start\":[1,4],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":35}"
            + ",{\"start\":[2],\"attrs\":{\"paragraph\":{\"listLevel\":2}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":36}"
            + ",{\"text\":\"drei\",\"start\":[2,0],\"opl\":4,\"name\":\"insertText\",\"osn\":37}"
            + ",{\"start\":[2,4],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":41}"
            + ",{\"start\":[3],\"attrs\":{\"paragraph\":{\"listLevel\":3}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":42}"
            + ",{\"text\":\"vier\",\"start\":[3,0],\"opl\":4,\"name\":\"insertText\",\"osn\":43}"
            + ",{\"start\":[3,4],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":47}"
            + ",{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listIdentifier\":\"L30001\",\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevels\":[{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}],\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4)\",\"indentLeft\":5080,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L2\",\"osn\":48}"
            + ",{\"start\":[3],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":49}"
            + ",{\"start\":[2],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":50}"
            + ",{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":51}"
            + ",{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":52}"
            + ",{\"start\":[4],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":53}"
            + ",{\"start\":[4],\"attrs\":{\"paragraph\":{\"listLevel\":4}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":54}"
            + ",{\"text\":\"fuenf\",\"start\":[4,0],\"opl\":5,\"name\":\"insertText\",\"osn\":55}"
            + ",{\"start\":[4,5],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":60}"
            + ",{\"start\":[5],\"attrs\":{\"paragraph\":{\"listLevel\":5}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":61}"
            + ",{\"text\":\"sechs\",\"start\":[5,0],\"opl\":5,\"name\":\"insertText\",\"osn\":62}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * Create a complex bullet list!
     *
     */
    public void bulletListTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"attrs\":{\"document\":{\"defaultTabStop\":1251}},\"name\":\"setDocumentAttributes\"},"
            + "{\"styleId\":\"default_paragraph_style\",\"default\":true,\"hidden\":true,\"attrs\":{\"character\":{\"fontSizeAsian\":12,\"fontName\":\"Times New Roman\",\"fontNameComplex\":\"Mangal\",\"language\":\"de-DE\",\"fontSize\":12,\"fontNameAsian\":\"SimSun\"}},\"name\":\"insertStyleSheet\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"Standard\",\"attrs\":{},\"name\":\"insertStyleSheet\",\"parent\":\"default_paragraph_style\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"Caption\",\"attrs\":{\"paragraph\":{\"marginBottom\":212,\"marginTop\":212},\"character\":{\"fontSizeAsian\":12,\"italicComplex\":true,\"fontNameComplex\":\"Mangal1\",\"italicAsian\":true,\"italic\":true,\"fontSize\":12}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"Heading\",\"attrs\":{\"paragraph\":{\"nextStyleId\":\"Text_20_body\",\"marginTop\":423,\"marginBottom\":212},\"character\":{\"fontSizeAsian\":14,\"fontNameComplex\":\"Mangal\",\"fontName\":\"Arial\",\"fontSize\":14,\"fontNameAsian\":\"Microsoft YaHei\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"Index\",\"attrs\":{\"character\":{\"fontNameComplex\":\"Mangal1\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleName\":\"List Paragraph\",\"styleId\":\"ListParagraph\",\"attrs\":{\"paragraph\":{\"nextStyleId\":\"ListParagraph\",\"indentLeft\":1270,\"marginLeft\":1270}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleName\":\"Text body\",\"styleId\":\"Text_20_body\",\"attrs\":{\"paragraph\":{\"marginBottom\":212,\"marginTop\":0}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"List\",\"attrs\":{\"character\":{\"fontNameComplex\":\"Mangal1\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Text_20_body\",\"type\":\"paragraph\"},"
            + "{\"attrs\":{\"page\":{\"printOrientation\":\"portrait\",\"marginBottom\":2000,\"indentRight\":2000,\"height\":29700,\"indentLeft\":2000,\"marginTop\":2000,\"marginLeft\":2000,\"numberFormat\":\"1\",\"width\":21001,\"marginRight\":2000}},\"name\":\"setDocumentAttributes\"},"
            + "{\"fontName\":\"Mangal1\",\"attrs\":{\"family\":\"Mangal\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"Times New Roman\",\"attrs\":{\"family\":\"'Times New Roman'\",\"familyGeneric\":\"roman\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"Arial\",\"attrs\":{\"family\":\"Arial\",\"familyGeneric\":\"swiss\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"Mangal\",\"attrs\":{\"family\":\"Mangal\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"Microsoft YaHei\",\"attrs\":{\"family\":\"'Microsoft YaHei'\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"SimSun\",\"attrs\":{\"family\":\"SimSun\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":\"1\",\"levelText\":\"%9\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":11430,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + " \"listLevel6\":{\"listStartValue\":\"1\",\"levelText\":\"%7\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":8890,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + " \"listLevel7\":{\"listStartValue\":\"1\",\"levelText\":\"%8\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":10160,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + " \"listLevel5\":{\"listStartValue\":\"1\",\"levelText\":\"%6\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel4\":{\"listStartValue\":\"1\",\"levelText\":\"%5\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":\"1\",\"levelText\":\"%4\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":\"1\",\"levelText\":\"%3\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + "\"listLevel1\":{\"listStartValue\":\"1\",\"levelText\":\"%2\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + "\"listLevel0\":{\"listStartValue\":\"1\",\"levelText\":\"(%1\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"lowerRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"listStyleId\":\"L2\"},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"1)\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"(i)\",\"start\":[1,0],\"name\":\"insertText\"},"
            + "{\"start\":[2],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"(ii)\",\"start\":[2,0],\"name\":\"insertText\"},"
            + "{\"start\":[3],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"(iii)\",\"start\":[3,0],\"name\":\"insertText\"},"
            + "{\"start\":[4],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"2)\",\"start\":[4,0],\"name\":\"insertText\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT: Changing bullet for one list item changes whole bullet
     */
    public void changeListStyleOfFirstItemTest() {
        final String SOURCE_FILE_NAME_TRUNC = "simple bullet list";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"listDefinition\":{\"listLevel8\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":6350,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listIdentifier\":\"L1\",\"listLevel6\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5080,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevels\":[{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":1270,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":1905,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":2540,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3175,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3175,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3810,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":4445,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":4445,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5080,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5715,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5715,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":6350,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635}],\"listLevel7\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5715,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5715,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel5\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":4445,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":4445,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel4\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3810,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel3\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3175,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3175,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel2\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":2540,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel1\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":1905,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L3\",\"osn\":30},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L3\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":31},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L3\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":32},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L1\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1],\"osn\":33},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L1\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0],\"osn\":34},"
            + "{\"name\":\"deleteListStyle\",\"opl\":1,\"listStyleId\":\"L3\",\"osn\":35},"
            + "{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listIdentifier\":\"L2\",\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevels\":[{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + "{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + "{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},"
            + "{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + "{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + "{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},"
            + "{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + "{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + "{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"}],\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1)\",\"indentLeft\":1270,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L3\",\"osn\":36},"
            + "{\"start\":[2],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L3\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":37},"
            + "{\"listDefinition\":{\"listLevel8\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":6350,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listIdentifier\":\"L1\",\"listLevel6\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5080,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevels\":[{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":1270,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":1905,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":2540,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3175,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3175,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3810,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":4445,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":4445,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5080,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5715,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5715,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":6350,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635}],\"listLevel7\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5715,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5715,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel5\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":4445,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":4445,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel4\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3810,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel3\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3175,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3175,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel2\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":2540,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel1\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":1905,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1)\",\"indentLeft\":1270,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L4\",\"osn\":38},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L4\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":39},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L4\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":40},"
            + "{\"listDefinition\":{\"listLevel8\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":6350,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listIdentifier\":\"L4\",\"listLevel6\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5080,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevels\":[{\"listStartValue\":1,\"levelText\":\"%1)\",\"indentLeft\":1270,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":1905,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":2540,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3175,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3175,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3810,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":4445,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":4445,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5080,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5715,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5715,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},"
            + "{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":6350,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635}],\"listLevel7\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5715,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5715,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel5\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":4445,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":4445,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel4\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3810,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel3\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3175,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3175,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel2\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":2540,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel1\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":1905,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1)\",\"indentLeft\":1270,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L5\",\"osn\":41},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L5\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":42},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L5\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":43}"
            + "]";

        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT - Numbered list: After saving the numbering list contains
     * wrong labels
     */
    public void editSubListTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"a\",\"start\":[0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":16},"
            + "{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"■\",\"indentLeft\":11430,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel6\":{\"listStartValue\":1,\"fontName\":\"Symbol\",\"levelText\":\"\",\"indentLeft\":8890,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel7\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"○\",\"indentLeft\":10160,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"defaultlist\":\"bullet\",\"listLevel5\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"■\",\"indentLeft\":7620,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel4\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"○\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel3\":{\"listStartValue\":1,\"fontName\":\"Symbol\",\"levelText\":\"\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel2\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"■\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel1\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"○\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel0\":{\"listStartValue\":1,\"fontName\":\"Symbol\",\"levelText\":\"\",\"indentLeft\":1270,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L1\",\"osn\":17},"
            + "{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\",\"osn\":18},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":19},"
            + "{\"start\":[0,1],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":20},"
            + "{\"text\":\"b\",\"start\":[1,0],\"opl\":1,\"name\":\"insertText\",\"osn\":21},"
            + "{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"defaultlist\":\"numbering\",\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L2\",\"osn\":22},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":23},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"listLevel\":1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":24},"
            + "{\"start\":[1,1],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":25},"
            + "{\"text\":\"b\",\"start\":[2,0],\"opl\":1,\"name\":\"insertText\",\"osn\":26},"
            + "{\"start\":[2,1],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":27},"
            + "{\"text\":\"c\",\"start\":[3,0],\"opl\":1,\"name\":\"insertText\",\"osn\":28},"
            + "{\"start\":[3,1],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":29},"
            + "{\"text\":\"d\",\"start\":[4,0],\"opl\":1,\"name\":\"insertText\",\"osn\":30},"
            + "{\"start\":[2],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":31},"
            + "{\"start\":[2],\"attrs\":{\"paragraph\":{\"listLevel\":1}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":32}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT - Numbered list: After saving the numbering list contains
     * wrong labels
     */
    public void listStyleChangeTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"A\",\"start\":[0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":16},"
            + "{\"listDefinition\":{\""
            + "listLevel8\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"■\",\"indentLeft\":11430,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\""
            + "listLevel6\":{\"listStartValue\":1,\"fontName\":\"Symbol\",\"levelText\":\"\",\"indentLeft\":8890,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\""
            + "listLevel7\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"○\",\"indentLeft\":10160,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"defaultlist\":\"bullet\",\""
            + "listLevel5\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"■\",\"indentLeft\":7620,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\""
            + "listLevel4\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"○\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\""
            + "listLevel3\":{\"listStartValue\":1,\"fontName\":\"Symbol\",\"levelText\":\"\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\""
            + "listLevel2\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"■\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\""
            + "listLevel1\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"○\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\""
            + "listLevel0\":{\"listStartValue\":1,\"fontName\":\"Symbol\",\"levelText\":\"\",\"indentLeft\":1270,\"numberFormat\":\"bullet\",\"textAlign\":\"left\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L1\",\"osn\":17},"
            + "{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"opl\":1,\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\",\"osn\":18},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":19},"
            + "{\"start\":[0,1],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":20},"
            + "{\"text\":\"B\",\"start\":[1,0],\"opl\":1,\"name\":\"insertText\",\"osn\":21},"
            + "{\"listDefinition\":{\""
            + "listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\""
            + "listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\""
            + "listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"defaultlist\":\"numbering\",\""
            + "listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\""
            + "listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\""
            + "listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\""
            + "listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\""
            + "listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\""
            + "listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"opl\":1,\"listStyleId\":\"L2\",\"osn\":22},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":23}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    /**
     * ODT: inserting table after image doubles image
     */
    @Test
    public void insertTableBehindImage() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[0,0],\"attrs\":{\"drawing\":{\"marginBottom\":317,\"height\":16933,\"imageUrl\":\"http://no-real-server/skyline.png\",\"marginTop\":317,\"marginLeft\":317,\"width\":12700,\"marginRight\":317}},\"name\":\"insertDrawing\",\"type\":\"image\"},"
            + "{\"start\":[0,1],\"name\":\"splitParagraph\"},"
            + "{\"styleId\":\"TableGrid\",\"styleName\":\"Table Grid\",\"attrs\":{\"wholeTable\":{\"paragraph\":{\"lineHeight\":{\"value\":100,\"type\":\"percent\"}},\"table\":{\"borderInsideVert\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderInsideHor\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderBottom\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderLeft\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderRight\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingBottom\":0,\"paddingLeft\":190,\"paddingRight\":190}}},\"name\":\"insertStyleSheet\",\"uiPriority\":59,\"type\":\"table\"},"
            + "{\"start\":[2],\"attrs\":{\"table\":{\"style\":\"TableGrid\",\"tableGrid\":[1000,1000,1000],\"width\":\"auto\",\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]}},\"name\":\"insertTable\"},{\"count\":3,\"start\":[2,0],\"name\":\"insertRows\",\"insertDefaultCells\":true}"
            + "]";

        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT: Bullets in bullet list not saved
     */
    public void splitListParagraph2() {
        final String SOURCE_FILE_NAME_TRUNC = "simple bullet list 1_pre OX";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[2,8],\"name\":\"splitParagraph\"},"
            + "{\"text\":\"s\",\"start\":[3,0],\"name\":\"insertText\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT - splitParagraph does only split the paragraph and not
     * create a new list item
     */
    public void splitListParagraph() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"ab\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"defaultlist\":\"numbering\",\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"indentFirstLine\":-635,\"textAlign\":\"right\"},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"listStyleId\":\"L1\"},"
            + "{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\"},{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"setAttributes\"},"
            + "{\"start\":[0,1],\"name\":\"splitParagraph\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT - bulletlist: placed text behind a bulletlist is lost,
     * after closing the odt
     *
     * The deletion of one of two paragraphs within a list, resulted into the
     * deletion of both, as the routine believed the second was empty after
     * deletion within.
     */
    public void textAfterListTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations1 = "["
            + "{\"text\":\"a\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"start\":[0,1],\"name\":\"splitParagraph\"},"
            + "{\"listDefinition\":{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerRoman\",\"textAlign\":\"right\",\"indentFirstLine\":-635},\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"defaultlist\":\"numbering\",\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"upperLetter\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"decimal\",\"textAlign\":\"right\",\"indentFirstLine\":-635},\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"%5.\",\"indentLeft\":6350,\"numberFormat\":\"upperRoman\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerRoman\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"upperLetter\",\"textAlign\":\"right\",\"indentFirstLine\":-635},\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"textAlign\":\"left\",\"indentFirstLine\":-635},\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"%1.\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"textAlign\":\"left\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"listStyleId\":\"L1\"},{\"styleName\":\"List Paragraph\",\"styleId\":\"ListParagraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\"},{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listStyleId\":\"L1\",\"listLevel\":0}},\"name\":\"setAttributes\"},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":null,\"listLevel\":-1}},\"name\":\"setAttributes\"},"
            + "{\"text\":\"b\",\"start\":[1,0],\"name\":\"insertText\"}"
            + "]";

        editOperations.add(firstEditOperations1);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * This test if the debug output does work: 1) Applies operations to a test
     * document 2) Checks if operation and original document exist 3) Applies
     * again operation to the test document 4) Checks if two operation files and
     * same original document as previously exist
     */
    public void debugOperationTest2() {
        final String SOURCE_FILE_NAME_TRUNC = "feature_bullets_numbering";

        List<String> editOperations = new ArrayList();
        String firstEditOperations1 = "["
            + "{\"name\":\"insertText\",\"start\":[0,10],\"text\":\"Svante & \"},"
            + "]";
        editOperations.add(firstEditOperations1);
        String savedDocumentPath = super.roundtripRegressionWithResourcesTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations, null, true);
        OdfPackage pkg = null;
        try {
            // return the absolute path to the test directory
            savedDocumentPath = ResourceUtilities.getTestOutputFolder() + savedDocumentPath;
            pkg = OdfPackage.loadPackage(savedDocumentPath);
        } catch (Exception ex) {
            Assert.fail("The saved document '" + savedDocumentPath + "' could not be found!");
            Logger.getLogger(EditingRegressionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pkg == null || !pkg.contains(OdfOperationDocument.ORIGNAL_ODT_FILE)) {
            Assert.fail("The original document '" + OdfOperationDocument.ORIGNAL_ODT_FILE + "' could not be found in the saved document '" + savedDocumentPath + "'!");
        }

        String firstEditOperations2 = "["
            + "{\"start\":[0],\"name\":\"delete\"},"
            + "]";
        editOperations.clear();
        editOperations.add(firstEditOperations2);

        pkg = null;
        try {
            String previousOutputOdfPath = OUTPUT_DIRECTORY + SOURCE_FILE_NAME_TRUNC + ".odt" + HYPEN + getTestMethodName() + HYPEN + "OUT" + HYPEN + "org" + ".odt";
            Map<String, Object> configuration = new HashMap<String, Object>();
            configuration.put(DEBUG_OPERATIONS, Boolean.TRUE);
            OdfOperationDocument doc = new OdfOperationDocument(new FileInputStream(ResourceUtilities.newTestOutputFile(previousOutputOdfPath)), configuration);
            savedDocumentPath = super.roundtripOperationTest(doc, SOURCE_FILE_NAME_TRUNC, getTestMethodName() + HYPEN + "OUT" + HYPEN + "org", ".odt", editOperations, null, false, true, false, null, true);
            pkg = OdfPackage.loadPackage(ResourceUtilities.getTestOutputFolder() + savedDocumentPath);
        } catch (Exception ex) {
            Assert.fail("The saved document '" + savedDocumentPath + "' could not be found!");
            Logger.getLogger(EditingRegressionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pkg == null || !pkg.contains(OdfOperationDocument.ORIGNAL_ODT_FILE)) {
            Assert.fail("The original document '" + OdfOperationDocument.ORIGNAL_ODT_FILE + "' could not be found in the saved document '" + savedDocumentPath + "'!");
        }
        if (!pkg.contains(OdfOperationDocument.OPERATION_TEXT_FILE_PREFIX + "1.txt")) {
            Assert.fail("The original document '" + OdfOperationDocument.OPERATION_TEXT_FILE_PREFIX + "1.txt" + "' could not be found in the saved document '" + savedDocumentPath + "'!");
        }
        if (!pkg.contains(OdfOperationDocument.OPERATION_TEXT_FILE_PREFIX + "2.txt")) {
            Assert.fail("The original document '" + OdfOperationDocument.OPERATION_TEXT_FILE_PREFIX + "2.txt" + "' could not be found in the saved document '" + savedDocumentPath + "'!");
        }
        if (!pkg.contains(OdfOperationDocument.OPERATION_REVISON_FILE)) {
            Assert.fail("The original document '" + OdfOperationDocument.OPERATION_REVISON_FILE + "' could not be found in the saved document '" + savedDocumentPath + "'!");
        }
    }

    @Test
    /**
     * ODT: insert hyperlink via clipboard not saved
     *
     *
     * The width of an image should be limited to its page width (page width -
     * left&rigth margin) QUESTION: Is the height adapted to keep the ratio?
     */
    public void markHyperlinkAllTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"http://www.sueddeutsche.de/\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"styleName\":\"Hyperlink\",\"styleId\":\"Hyperlink\",\"attrs\":{\"character\":{\"color\":{\"value\":\"hyperlink\",\"fallbackValue\":\"0080C0\",\"type\":\"scheme\"},\"underline\":true}},\"name\":\"insertStyleSheet\",\"parent\":null,\"uiPriority\":99,\"type\":\"character\"},"
            + "{\"start\":[0,0],\"attrs\":{\"styleId\":\"Hyperlink\",\"character\":{\"url\":\"http://www.sueddeutsche.de/\"}},\"name\":\"setAttributes\",\"end\":[0,19]}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT: insert hyperlink via clipboard not saved
     *
     *
     * The width of an image should be limited to its page width (page width -
     * left&rigth margin) QUESTION: Is the height adapted to keep the ratio?
     */
    public void markHyperlinkFirstCharacterTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"http://www.sueddeutsche.de/\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"styleName\":\"Hyperlink\",\"styleId\":\"Hyperlink\",\"attrs\":{\"character\":{\"color\":{\"value\":\"hyperlink\",\"fallbackValue\":\"0080C0\",\"type\":\"scheme\"},\"underline\":true}},\"name\":\"insertStyleSheet\",\"parent\":null,\"uiPriority\":99,\"type\":\"character\"},"
            + "{\"start\":[0,0],\"attrs\":{\"character\":{\"style\":\"Hyperlink\",\"url\":\"http://www.sueddeutsche.de/\"}},\"name\":\"setAttributes\",\"end\":[0,0]}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    @Ignore // Have to disable the randomization of automatic styles first
    /**
     * ODT: Resized cell size not saved
     */
    public void changeCellSizeTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"styleName\":\"Table Grid\",\"styleId\":\"TableGrid\",\"attrs\":{\"wholeTable\":{\"paragraph\":{\"lineHeight\":{\"value\":100,\"type\":\"percent\"}},\"table\":{\"borderInsideVert\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderTop\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderInsideHor\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderBottom\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderRight\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderLeft\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17}}}},\"name\":\"insertStyleSheet\",\"uiPriority\":59,\"type\":\"table\"},"
            + "{\"start\":[1],\"attrs\":{\"table\":{\"style\":\"TableGrid\",\"width\":\"auto\",\"tableGrid\":[1000,1000,1000,1000,1000,1000],\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]}},\"name\":\"insertTable\"},"
            + "{\"count\":1,\"start\":[1,0],\"name\":\"insertRows\",\"insertDefaultCells\":true},"
            + "{\"start\":[1],\"attrs\":{\"table\":{\"width\":\"auto\",\"tableGrid\":[1002,1437,566,566,33,33]}},\"name\":\"setAttributes\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    @Ignore // API CHANGE - FIX ME
    /**
     */
    public void insertLargeImageTest() {
        final String SOURCE_FILE_NAME_TRUNC = "image-attributes";
        final String UID = "d03f7d7218eb";
        final String INTERNAL_IMAGE_PATH = "Pictures/uid" + UID + ".jpg";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[0,14],\"attrs\":{\"drawing\":{\"marginBottom\":317,\"height\":5982,\"marginLeft\":317,\"marginTop\":317,\"imageUrl\":\"" + INTERNAL_IMAGE_PATH + "\",\"marginRight\":317,\"width\":17013}},\"name\":\"insertDrawing\",\"type\":\"image\"}"
            + "]";

        editOperations.add(firstEditOperations);
        Map<Long, byte[]> resourceMap = new HashMap<Long, byte[]>();
        byte[] imageBytes = null;
        try {
            imageBytes = ResourceUtilities.loadFileAsBytes(ResourceUtilities.getReferenceFile("Herschel-Horsehead-Nebula.jpeg"));
        } catch (IOException ex) {
            Logger.getLogger(EditingRegressionTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail(ex.getMessage());
        }
        long uid = Long.parseLong(UID, 16);
        resourceMap.put(uid, imageBytes);
        String savedDocumentPath = super.roundtripRegressionWithResourcesTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations, resourceMap, true);
        OdfPackage pkg = null;
        try {
            // return the absolute path to the test directory
            savedDocumentPath = ResourceUtilities.getTestOutputFolder() + savedDocumentPath;
            pkg = OdfPackage.loadPackage(savedDocumentPath);
        } catch (Exception ex) {
            Assert.fail("The saved document '" + savedDocumentPath + "' could not be found!");
            Logger.getLogger(EditingRegressionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pkg == null || !pkg.contains(INTERNAL_IMAGE_PATH)) {
            Assert.fail("The image '" + INTERNAL_IMAGE_PATH + "' could not be found in the saved document '" + savedDocumentPath + "'!");
        }
    }

    @Test
    /**
     * ODT: Table borders expected to be on table style (extend ODF)
     */
    public void paddingRoundtripTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"attrs\":{\"document\":{\"defaultTabStop\":2401}},\"name\":\"setDocumentAttributes\"},{\"styleId\":\"default_paragraph_style\",\"default\":true,\"hidden\":true,\"attrs\":{\"character\":{\"fontSizeAsian\":12,\"fontName\":\"Times New Roman\",\"fontNameComplex\":\"Mangal\",\"language\":\"de-DE\",\"fontSize\":12,\"fontNameAsian\":\"SimSun\"}},\"name\":\"insertStyleSheet\",\"type\":\"paragraph\"},{\"styleId\":\"Standard\",\"attrs\":{},\"name\":\"insertStyleSheet\",\"parent\":\"default_paragraph_style\",\"type\":\"paragraph\"},{\"styleName\":\"Table Contents\",\"styleId\":\"Table_20_Contents\",\"attrs\":{},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},{\"styleName\":\"Text body\",\"styleId\":\"Text_20_body\",\"attrs\":{\"paragraph\":{\"marginBottom\":212,\"marginTop\":0}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},{\"styleId\":\"Caption\",\"attrs\":{\"paragraph\":{\"marginBottom\":212,\"marginTop\":212},\"character\":{\"fontSizeAsian\":12,\"italicComplex\":true,\"fontNameComplex\":\"Mangal1\",\"italicAsian\":true,\"italic\":true,\"fontSize\":12}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},{\"styleId\":\"Index\",\"attrs\":{\"character\":{\"fontNameComplex\":\"Mangal1\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},{\"styleId\":\"Heading\",\"attrs\":{\"paragraph\":{\"nextStyleId\":\"Text_20_body\",\"marginTop\":423,\"marginBottom\":212},\"character\":{\"fontSizeAsian\":14,\"fontNameComplex\":\"Mangal\",\"fontName\":\"Arial\",\"fontSize\":14,\"fontNameAsian\":\"Microsoft YaHei\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},{\"styleId\":\"List\",\"attrs\":{\"character\":{\"fontNameComplex\":\"Mangal1\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Text_20_body\",\"type\":\"paragraph\"},{\"attrs\":{\"page\":{\"printOrientation\":\"portrait\",\"marginBottom\":2000,\"indentRight\":2000,\"height\":29700,\"indentLeft\":2000,\"marginTop\":2000,\"marginLeft\":2000,\"numberFormat\":\"1\",\"width\":21001,\"marginRight\":2000}},\"name\":\"setDocumentAttributes\"},{\"fontName\":\"Mangal1\",\"attrs\":{\"family\":\"Mangal\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"Times New Roman\",\"attrs\":{\"family\":\"'Times New Roman'\",\"familyGeneric\":\"roman\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"Arial\",\"attrs\":{\"family\":\"Arial\",\"familyGeneric\":\"swiss\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"Mangal\",\"attrs\":{\"family\":\"Mangal\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"Microsoft YaHei\",\"attrs\":{\"family\":\"'Microsoft YaHei'\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"SimSun\",\"attrs\":{\"family\":\"SimSun\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"start\":[0],\"attrs\":{\"table\":{\"tableGrid\":[32767,32768]}},\"name\":\"insertTable\"},{\"start\":[0,0],\"name\":\"insertRows\"},{\"start\":[0,0,0],\"attrs\":{\"cell\":{\"paddingTop\":1300,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2,\"space\":1300},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2,\"space\":1401},\"borderRight\":{\"style\":\"none\",\"space\":1199},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2,\"space\":1101},\"paddingBottom\":1401,\"paddingLeft\":1101,\"paddingRight\":1199}},\"name\":\"insertCells\"},{\"start\":[0,0,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Table_20_Contents\"}},\"name\":\"insertParagraph\"},{\"text\":\"x\",\"start\":[0,0,0,0,0],\"name\":\"insertText\"},{\"start\":[0,0,1],\"attrs\":{\"cell\":{\"paddingTop\":1300,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"paddingBottom\":1401,\"paddingLeft\":1101,\"paddingRight\":1199}},\"name\":\"insertCells\"},{\"start\":[0,0,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Table_20_Contents\"}},\"name\":\"insertParagraph\"},{\"text\":\"y\",\"start\":[0,0,1,0,0],\"name\":\"insertText\"},{\"start\":[0,1],\"name\":\"insertRows\"},{\"start\":[0,1,0],\"attrs\":{\"cell\":{\"paddingTop\":1300,\"borderTop\":{\"style\":\"none\",\"space\":1300},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2,\"space\":1401},\"borderRight\":{\"style\":\"none\",\"space\":1199},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2,\"space\":1101},\"paddingBottom\":1401,\"paddingLeft\":1101,\"paddingRight\":1199}},\"name\":\"insertCells\"},{\"start\":[0,1,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Table_20_Contents\"}},\"name\":\"insertParagraph\"},{\"text\":\"z\",\"start\":[0,1,0,0,0],\"name\":\"insertText\"},{\"start\":[0,1,1],\"attrs\":{\"cell\":{\"paddingTop\":1300,\"borderTop\":{\"style\":\"none\",\"space\":1300},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2,\"space\":1401},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2,\"space\":1199},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2,\"space\":1101},\"paddingBottom\":1401,\"paddingLeft\":1101,\"paddingRight\":1199}},\"name\":\"insertCells\"},{\"start\":[0,1,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Table_20_Contents\"}},\"name\":\"insertParagraph\"},{\"text\":\"1\",\"start\":[0,1,1,0,0],\"name\":\"insertText\"},{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"Standard\"}},\"name\":\"insertParagraph\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT: Paragraph style 'List Paragraph' not saved
     */
    public void leftIndentStyleTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"List\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"styleName\":\"List Paragraph\",\"styleId\":\"ListParagraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\"},"
            + "{\"start\":[0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"nextStyleId\":null,\"borderBottom\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"showListLabel\":null,\"marginBottom\":null,\"tabStops\":null,\"listStartValue\":null,\"indentRight\":null,\"indentLeft\":null,\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"strike\":null,\"bold\":null,\"fontName\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"language\":null,\"italic\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT: Table border to close to the text
     */
    public void tableBorderTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"peng\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"borderTop\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17,\"space\":140},\"borderInside\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17,\"space\":140},\"borderRight\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17,\"space\":140},\"borderLeft\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17,\"space\":140}}},\"name\":\"setAttributes\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     */
    public void simpleList3CopyPasteTest() {
        final String SOURCE_FILE_NAME_TRUNC = "simpleList3";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[16,0],\"name\":\"splitParagraph\"},"
            + "{\"start\":[16],\"attrs\":{\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"nextStyleId\":null,\"listStyleId\":\"L1\",\"borderBottom\":null,\"listLevel\":0,\"contextualSpacing\":null,\"borderLeft\":null,\"showListLabel\":null,\"marginBottom\":null,\"tabStops\":null,\"listStartValue\":null,\"indentRight\":null,\"style\":\"Standard\",\"indentLeft\":null,\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"strike\":null,\"bold\":null,\"fontName\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"language\":null,\"italic\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\"},"
            + "{\"text\":\"First-followed-by-space\",\"start\":[16,0],\"attrs\":{\"character\":{\"vertAlign\":null,\"strike\":null,\"bold\":null,\"fontName\":null,\"style\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"language\":null,\"italic\":null,\"fontSize\":null,\"url\":null}},\"name\":\"insertText\"},"
            + "{\"start\":[17],\"attrs\":{\"paragraph\":{\"indentRight\":0,\"indentLeft\":2401,\"style\":\"Standard\",\"listStyleId\":\"L1\",\"indentFirstLine\":-635,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Second\",\"start\":[17,0],\"name\":\"insertText\"},"
            + "{\"start\":[18],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L1\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Third\",\"start\":[18,0],\"name\":\"insertText\"},"
            + "{\"start\":[19],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"start\":[20],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Cross-First-followed-by-space\",\"start\":[20,0],\"name\":\"insertText\"},"
            + "{\"start\":[21],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Cross-Second\",\"start\":[21,0],\"name\":\"insertText\"},"
            + "{\"start\":[22],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Cross-Third\",\"start\":[22,0],\"name\":\"insertText\"},"
            + "{\"start\":[23],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"start\":[24],\"attrs\":{\"paragraph\":{\"style\":\"Standard\"}},\"name\":\"insertParagraph\"},"
            + "{\"start\":[25],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L3\",\"listLevel\":0},\"character\":{\"underline\":false}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Star-First-followed-by-space\",\"start\":[25,0],\"name\":\"insertText\"},"
            + "{\"start\":[26],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L3\",\"listLevel\":0},\"character\":{\"underline\":false}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Star-Second\",\"start\":[26,0],\"name\":\"insertText\"},"
            + "{\"start\":[27],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L3\",\"listLevel\":0},\"character\":{\"underline\":false}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Star-Third\",\"start\":[27,0],\"name\":\"insertText\"},"
            + "{\"start\":[28],\"attrs\":{\"paragraph\":{\"style\":\"Standard\"},\"character\":{\"underline\":false}},\"name\":\"insertParagraph\"},"
            + "{\"start\":[29],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L4\",\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"LabelTest1\",\"start\":[29,0],\"name\":\"insertText\"},"
            + "{\"start\":[29,0],\"attrs\":{\"character\":{\"underline\":false}},\"name\":\"setAttributes\",\"end\":[29,9]},"
            + "{\"start\":[30],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L4\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"LabelTest2\",\"start\":[30,0],\"name\":\"insertText\"},"
            + "{\"start\":[30,0],\"attrs\":{\"character\":{\"underline\":false}},\"name\":\"setAttributes\",\"end\":[30,9]},"
            + "{\"start\":[31],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L4\",\"listLevel\":2}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"LabelTest3\",\"start\":[31,0],\"name\":\"insertText\"},"
            + "{\"start\":[31,0],\"attrs\":{\"character\":{\"underline\":false}},\"name\":\"setAttributes\",\"end\":[31,9]}"
            + "]";
        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * Moving root level images (usually page aligned) within a paragraph
     *
     */
    public void loadPageAlignedImageTest() {
        final String SOURCE_FILE_NAME_TRUNC = "odf-test-images";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "]";
        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT: insertTab not saved
     */
    public void insertTabTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"Hallo\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"start\":[0,0],\"name\":\"insertTab\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void simpleListDocTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty_as_can_be";
        String firstEditOperations = "["
            + "{\"attrs\":{\"document\":{\"defaultTabStop\":2401}},\"name\":\"setDocumentAttributes\"},"
            + "{\"styleId\":\"default_paragraph_style\",\"default\":true,\"hidden\":true,\"attrs\":{\"character\":{\"fontSizeAsian\":12,\"fontName\":\"Times New Roman\",\"fontNameComplex\":\"Mangal\",\"language\":\"de-DE\",\"fontSize\":12,\"fontNameAsian\":\"SimSun\"}},\"name\":\"insertStyleSheet\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"Standard\",\"attrs\":{},\"name\":\"insertStyleSheet\",\"parent\":\"default_paragraph_style\",\"type\":\"paragraph\"},"
            + "{\"styleName\":\"Text body\",\"styleId\":\"Text_20_body\",\"attrs\":{\"paragraph\":{\"marginBottom\":212,\"marginTop\":0}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"Caption\",\"attrs\":{\"paragraph\":{\"marginBottom\":212,\"marginTop\":212},\"character\":{\"fontSizeAsian\":12,\"italicComplex\":true,\"fontNameComplex\":\"Mangal1\",\"italicAsian\":true,\"italic\":true,\"fontSize\":12}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"Index\",\"attrs\":{\"character\":{\"fontNameComplex\":\"Mangal1\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"Heading\",\"attrs\":{\"paragraph\":{\"nextStyleId\":\"Text_20_body\",\"marginTop\":423,\"marginBottom\":212},\"character\":{\"fontSizeAsian\":14,\"fontNameComplex\":\"Mangal\",\"fontName\":\"Arial\",\"fontSize\":14,\"fontNameAsian\":\"Microsoft YaHei\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"List\",\"attrs\":{\"character\":{\"fontNameComplex\":\"Mangal1\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Text_20_body\",\"type\":\"paragraph\"},"
            + "{\"styleName\":\"Bullet Symbols\",\"styleId\":\"Bullet_20_Symbols\",\"attrs\":{\"character\":{\"fontNameAsian\":\"OpenSymbol\",\"fontNameComplex\":\"OpenSymbol\",\"fontName\":\"OpenSymbol\"}},\"name\":\"insertStyleSheet\",\"parent\":\"default_character_style\",\"type\":\"character\"},"
            + "{\"styleName\":\"Numbering Symbols\",\"styleId\":\"Numbering_20_Symbols\",\"attrs\":{},\"name\":\"insertStyleSheet\",\"parent\":\"default_character_style\",\"type\":\"character\"},"
            + "{\"attrs\":{\"page\":{\"printOrientation\":\"portrait\",\"marginBottom\":2000,\"indentRight\":2000,\"height\":29700,\"indentLeft\":2000,\"marginTop\":2000,\"marginLeft\":2000,\"numberFormat\":\"1\",\"width\":21001,\"marginRight\":2000}},\"name\":\"setDocumentAttributes\"},"
            + "{\"fontName\":\"StarSymbol\",\"attrs\":{\"family\":\"StarSymbol\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"Mangal1\",\"attrs\":{\"family\":\"Mangal\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"OpenSymbol\",\"attrs\":{\"family\":\"OpenSymbol\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"Times New Roman\",\"attrs\":{\"family\":\"'Times New Roman'\",\"familyGeneric\":\"roman\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"Arial\",\"attrs\":{\"family\":\"Arial\",\"familyGeneric\":\"swiss\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"Mangal\",\"attrs\":{\"family\":\"Mangal\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"Microsoft YaHei\",\"attrs\":{\"family\":\"'Microsoft YaHei'\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"SimSun\",\"attrs\":{\"family\":\"SimSun\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"listDefinition\":{\"listLevel8\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":6350,\"listStartValue\":\"7\",\"levelText\":\"%1.%2.%3.%4.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel9\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":6985,\"listStartValue\":\"7\",\"levelText\":\"%1.%2.%3.%4.%5.%6.%7.%8.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6985,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel6\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":5080,\"listStartValue\":\"7\",\"levelText\":\"%1.%2.%3.%4.%5.%6.%7.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel7\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":5715,\"listStartValue\":\"7\",\"levelText\":\"%1.%2.%3.%4.%5.%6.%7.%8.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5715,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel5\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":4445,\"listStartValue\":\"7\",\"levelText\":\"%1.%2.%3.%4.%5.%6.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":4445,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel4\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":3810,\"listStartValue\":\"7\",\"levelText\":\"%1.%2.%3.%4.%5.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel3\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":3175,\"listStartValue\":\"7\",\"levelText\":\"%1.%2.%3.%4.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3175,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel2\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":2540,\"listStartValue\":\"7\",\"levelText\":\"%1.%2.%3.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel1\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":1905,\"listStartValue\":\"7\",\"levelText\":\"b%1.%2a\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel0\":{\"styleId\":\"Numbering_20_Symbols\",\"listStartValue\":\"7\",\"levelText\":\"b%1a\",\"labelFollowedBy\":\"space\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"listStyleId\":\"L1\"},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list2505945715557893221\",\"style\":\"Standard\",\"listId\":\"list2505945715557893221\",\"listStyleId\":\"L1\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"First-followed-by-space\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list2505945715557893221\",\"indentRight\":0,\"style\":\"Standard\",\"indentLeft\":2401,\"listId\":\"list2505945715557893221\",\"marginLeft\":2401,\"marginRight\":0,\"listStyleId\":\"L1\",\"listStart\":true,\"listLevel\":0,\"indentFirstLine\":-635}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Second\",\"start\":[1,0],\"name\":\"insertText\"},"
            + "{\"start\":[2],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L1\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Third\",\"start\":[2,0],\"name\":\"insertText\"},"
            + "{\"listDefinition\":{\"listLevel8\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":6350,\"levelText\":\"✗\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel9\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":6985,\"levelText\":\"✗\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6985,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel6\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5080,\"levelText\":\"✗\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel7\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5715,\"levelText\":\"✗\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5715,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel5\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":4445,\"levelText\":\"✗\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":4445,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel4\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3810,\"levelText\":\"✗\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel3\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3175,\"levelText\":\"✗\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3175,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel2\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":2540,\"levelText\":\"✗\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel1\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":1905,\"levelText\":\"✗\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel0\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":1270,\"levelText\":\"✗\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"listStyleId\":\"L2\"},"
            + "{\"start\":[3],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list6626444102102110991\",\"listLabelHidden\":true,\"style\":\"Standard\",\"listId\":\"list6626444102102110991\",\"listStyleId\":\"L2\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"start\":[4],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list6626444102102110991\",\"style\":\"Standard\",\"listId\":\"list6626444102102110991\",\"listStyleId\":\"L2\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Cross-First-followed-by-space\",\"start\":[4,0],\"name\":\"insertText\"},"
            + "{\"start\":[5],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list6626444102102110991\",\"style\":\"Standard\",\"listId\":\"list6626444102102110991\",\"listStyleId\":\"L2\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Cross-Second\",\"start\":[5,0],\"name\":\"insertText\"},"
            + "{\"start\":[6],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list6626444102102110991\",\"style\":\"Standard\",\"listId\":\"list6626444102102110991\",\"listStyleId\":\"L2\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Cross-Third\",\"start\":[6,0],\"name\":\"insertText\"},"
            + "{\"start\":[7],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list6626444102102110991\",\"listLabelHidden\":true,\"style\":\"Standard\",\"listId\":\"list6626444102102110991\",\"listStyleId\":\"L2\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"start\":[8],\"attrs\":{\"paragraph\":{\"style\":\"Standard\"}},\"name\":\"insertParagraph\"},"
            + "{\"listDefinition\":{\"listLevel8\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":6350,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel9\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":6985,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6985,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel6\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5080,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel7\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":5715,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5715,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel5\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":4445,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":4445,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel4\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3810,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel3\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":3175,\"levelText\":\"•\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3175,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel2\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":2540,\"levelText\":\"▪\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel1\":{\"styleId\":\"Bullet_20_Symbols\",\"tabStopPosition\":1905,\"levelText\":\"◦\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel0\":{\"verticalRel\":\"line\",\"verticalPos\":\"middle\",\"tabStopPosition\":1270,\"height\":339,\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"width\":339,\"levelPicBulletUri\":\"Pictures/1000020000000010000000104191C4BD.gif\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"listStyleId\":\"L3\"},"
            + "{\"start\":[9],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list4610980543945444999\",\"style\":\"Standard\",\"listId\":\"list4610980543945444999\",\"listStyleId\":\"L3\",\"listStart\":true,\"listLevel\":0},\"character\":{\"underline\":false}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Star-First-followed-by-space\",\"start\":[9,0],\"name\":\"insertText\"},"
            + "{\"start\":[10],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list4610980543945444999\",\"style\":\"Standard\",\"listId\":\"list4610980543945444999\",\"listStyleId\":\"L3\",\"listStart\":true,\"listLevel\":0},\"character\":{\"underline\":false}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Star-Second\",\"start\":[10,0],\"name\":\"insertText\"},"
            + "{\"start\":[11],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list4610980543945444999\",\"style\":\"Standard\",\"listId\":\"list4610980543945444999\",\"listStyleId\":\"L3\",\"listStart\":true,\"listLevel\":0},\"character\":{\"underline\":false}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Star-Third\",\"start\":[11,0],\"name\":\"insertText\"},"
            + "{\"start\":[12],\"attrs\":{\"paragraph\":{\"style\":\"Standard\"},\"character\":{\"underline\":false}},\"name\":\"insertParagraph\"},"
            + "{\"listDefinition\":{\"listLevel8\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":6350,\"levelText\":\"(%1.%2.%3.%4.%5.%6.%7)\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel9\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":6985,\"levelText\":\"(%1.%2.%3.%4.%5.%6.%7)\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6985,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel6\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":5080,\"levelText\":\"(%1.%2.%3.%4.%5.%6.%7)\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel7\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":5715,\"levelText\":\"(%1.%2.%3.%4.%5.%6.%7)\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5715,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel5\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":4445,\"levelText\":\"(%1.%2.%3.%4.%5.%6)\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":4445,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel4\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":3810,\"levelText\":\"(%1.%2.%3.%4.%5)\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel3\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":3175,\"levelText\":\"(%1.%2.%3.%4)\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3175,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel2\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":2540,\"levelText\":\"(%1.%2.%3)\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel1\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":1905,\"levelText\":\"(%1.%2)\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel0\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":1270,\"levelText\":\"(%1)\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"listStyleId\":\"L4\"},"
            + "{\"start\":[13],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list4053051152566041198\",\"style\":\"Standard\",\"listId\":\"list4053051152566041198\",\"listStyleId\":\"L4\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"LabelTest1\",\"start\":[13,0],\"name\":\"insertText\"},"
            + "{\"start\":[13,0],\"attrs\":{\"character\":{\"underline\":false}},\"name\":\"setAttributes\",\"end\":[13,9]},"
            + "{\"start\":[14],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L4\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"LabelTest2\",\"start\":[14,0],\"name\":\"insertText\"},"
            + "{\"start\":[14,0],\"attrs\":{\"character\":{\"underline\":false}},\"name\":\"setAttributes\",\"end\":[14,9]},"
            + "{\"start\":[15],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"L4\",\"listLevel\":2}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"LabelTest3\",\"start\":[15,0],\"name\":\"insertText\"},"
            + "{\"start\":[15,0],\"attrs\":{\"character\":{\"underline\":false}},\"name\":\"setAttributes\",\"end\":[15,9]},"
            + "{\"start\":[16],\"attrs\":{\"paragraph\":{\"style\":\"Standard\"},\"character\":{\"underline\":false}},\"name\":\"insertParagraph\"}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    public void loadListStyleResolutionDocFullTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"attrs\":{\"document\":{\"defaultTabStop\":1251}},\"name\":\"setDocumentAttributes\"},"
            + "{\"styleId\":\"default_paragraph_style\",\"default\":true,\"hidden\":true,\"attrs\":{\"character\":{\"fontSizeAsian\":12,\"fontName\":\"Times New Roman\",\"fontNameComplex\":\"Mangal\",\"language\":\"de-DE\",\"fontSize\":12,\"fontNameAsian\":\"SimSun\"}},\"name\":\"insertStyleSheet\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"Standard\",\"attrs\":{},\"name\":\"insertStyleSheet\",\"parent\":\"default_paragraph_style\",\"type\":\"paragraph\"},"
            + "{\"styleName\":\"Text body\",\"styleId\":\"Text_20_body\",\"attrs\":{\"paragraph\":{\"fillColor\":{\"value\":\"ff0000\",\"type\":\"rgb\"},\"marginTop\":0,\"marginBottom\":212}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"Caption\",\"attrs\":{\"paragraph\":{\"marginBottom\":212,\"marginTop\":212},\"character\":{\"fontSizeAsian\":12,\"italicComplex\":true,\"fontNameComplex\":\"Mangal1\",\"italicAsian\":true,\"italic\":true,\"fontSize\":12}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"Index\",\"attrs\":{\"character\":{\"fontSizeAsian\":12,\"fontNameComplex\":\"Mangal1\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"Heading\",\"attrs\":{\"paragraph\":{\"nextStyleId\":\"Text_20_body\",\"marginTop\":423,\"marginBottom\":212},\"character\":{\"fontSizeAsian\":14,\"fontNameComplex\":\"Mangal\",\"fontName\":\"Arial\",\"fontSize\":14,\"fontNameAsian\":\"Microsoft YaHei\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleName\":\"Heading 2\",\"styleId\":\"Heading_20_2\",\"attrs\":{\"paragraph\":{\"nextStyleId\":\"Text_20_body\",\"outlineLevel\":1},\"character\":{\"italicAsian\":true,\"bold\":true,\"boldComplex\":true,\"fontSizeAsian\":14,\"italicComplex\":true,\"italic\":true,\"fontSize\":14,\"boldAsian\":true}},\"name\":\"insertStyleSheet\",\"parent\":\"Heading\",\"type\":\"paragraph\"},"
            + "{\"styleName\":\"Heading 1\",\"styleId\":\"Heading_20_1\",\"attrs\":{\"paragraph\":{\"nextStyleId\":\"Text_20_body\",\"fillColor\":{\"value\":\"ffff00\",\"type\":\"rgb\"},\"outlineLevel\":0},\"character\":{\"boldAsian\":true,\"bold\":true,\"boldComplex\":true}},\"name\":\"insertStyleSheet\",\"parent\":\"Heading\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"List\",\"attrs\":{\"character\":{\"fontSizeAsian\":12,\"fontNameComplex\":\"Mangal1\"}},\"name\":\"insertStyleSheet\",\"parent\":\"Text_20_body\",\"type\":\"paragraph\"},"
            + "{\"styleId\":\"Signature\",\"attrs\":{\"paragraph\":{\"fillColor\":{\"value\":\"3deb3d\",\"type\":\"rgb\"}}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"type\":\"paragraph\"},"
            + "{\"styleName\":\"Bullet Symbols\",\"styleId\":\"Bullet_20_Symbols\",\"attrs\":{\"character\":{\"fontNameAsian\":\"OpenSymbol\",\"fontNameComplex\":\"OpenSymbol\",\"fontName\":\"OpenSymbol\"}},\"name\":\"insertStyleSheet\",\"parent\":\"default_character_style\",\"type\":\"character\"},"
            + "{\"styleName\":\"Numbering Symbols\",\"styleId\":\"Numbering_20_Symbols\",\"attrs\":{},\"name\":\"insertStyleSheet\",\"parent\":\"default_character_style\",\"type\":\"character\"},"
            + "{\"attrs\":{\"page\":{\"printOrientation\":\"portrait\",\"marginBottom\":2000,\"indentRight\":2000,\"height\":29700,\"indentLeft\":2000,\"marginTop\":2000,\"marginLeft\":2000,\"numberFormat\":\"1\",\"width\":21001,\"marginRight\":2000}},\"name\":\"setDocumentAttributes\"},"
            + "{\"fontName\":\"Mangal1\",\"attrs\":{\"family\":\"Mangal\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"OpenSymbol\",\"attrs\":{\"family\":\"OpenSymbol\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"Times New Roman\",\"attrs\":{\"family\":\"'Times New Roman'\",\"familyGeneric\":\"roman\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"Arial\",\"attrs\":{\"family\":\"Arial\",\"familyGeneric\":\"swiss\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"Mangal\",\"attrs\":{\"family\":\"Mangal\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"Microsoft YaHei\",\"attrs\":{\"family\":\"'Microsoft YaHei'\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"fontName\":\"SimSun\",\"attrs\":{\"family\":\"SimSun\",\"familyGeneric\":\"system\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"Standard\"}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"This is a test for resolving the list style being used for a list label. \",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"listDefinition\":{\"listLevel8\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":6350,\"levelText\":\"%9.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6350,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel9\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":6985,\"levelText\":\"%10.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":6985,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel6\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":5080,\"levelText\":\"%7.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5080,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel7\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":5715,\"levelText\":\"%8.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":5715,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel5\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":4445,\"levelText\":\"%6.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":4445,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel4\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":3810,\"levelText\":\"%5.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3810,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel3\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":3175,\"levelText\":\"%4.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":3175,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel2\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":2540,\"levelText\":\"%3.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":2540,\"numberFormat\":\"decimal\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel1\":{\"verticalRel\":\"baseline\",\"verticalPos\":\"bottom\",\"tabStopPosition\":1905,\"height\":212,\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"width\":212,\"levelPicBulletUri\":\"Pictures/100002000000000A0000000A1F4FBE6C.gif\",\"indentFirstLine\":-635},\"listLevel0\":{\"verticalRel\":\"baseline\",\"verticalPos\":\"bottom\",\"tabStopPosition\":1270,\"height\":212,\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"bullet\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"width\":212,\"levelPicBulletUri\":\"Pictures/100002000000000A0000000A1F4FBE6C.gif\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"listStyleId\":\"ListStyle-ImageLabel\"},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list6357203421921001764\",\"listId\":\"list6357203421921001764\",\"listStyleId\":\"ListStyle-ImageLabel\",\"listItemXmlId\":\"listItem1\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"The first level list uses a upper case roman numbering.\",\"start\":[1,0],\"name\":\"insertText\"},"
            + "{\"start\":[2],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list6357203421921001764\",\"listId\":\"list6357203421921001764\",\"listStyleId\":\"ListStyle-ImageLabel\",\"listItemXmlId\":\"listItem2\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"The first level list-item overrides with a upper case alphabetic numbering\",\"start\":[2,0],\"name\":\"insertText\"},"
            + "{\"start\":[3],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list6357203421921001764\",\"listId\":\"list6357203421921001764\",\"listStyleId\":\"ListStyle-ImageLabel\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"The second level list has a lower case roman numbering\",\"start\":[3,0],\"name\":\"insertText\"},"
            + "{\"start\":[4],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list6357203421921001764\",\"listId\":\"list6357203421921001764\",\"listStyleId\":\"ListStyle-ImageLabel\",\"listItemXmlId\":\"listItem4\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"The second level list-item overrides with a lower-case alphabetic numbering\",\"start\":[4,0],\"name\":\"insertText\"},"
            + "{\"start\":[5],\"attrs\":{\"paragraph\":{\"listXmlId\":\"list6357203421921001764\",\"listId\":\"list6357203421921001764\",\"listStyleId\":\"ListStyle-ImageLabel\",\"listItemXmlId\":\"listItem5\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Finally the paragraph within the list item is referencing to a list label using an image\",\"start\":[5,0],\"name\":\"insertText\"},"
            + "{\"listDefinition\":{\"listLevel1\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":1905,\"levelText\":\"%2.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"upperLetter\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel0\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":1270,\"levelText\":\"%1.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"upperLetter\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"listStyleId\":\"ListStyle-LetterUpperCaseLabel\"},"
            + "{\"start\":[6],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-LetterUpperCaseLabel\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:3 Roman, Alpha\",\"start\":[6,0],\"name\":\"insertText\"},"
            + "{\"start\":[6,21],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: A.\",\"start\":[6,22],\"name\":\"insertText\"},"
            + "{\"start\":[6,9],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[6,13]},"
            + "{\"start\":[6,16],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[6,20]},"
            + "{\"listDefinition\":{\"listLevel1\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":1905,\"levelText\":\"%2.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"upperRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel0\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":1270,\"levelText\":\"%1.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"upperRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"listStyleId\":\"ListStyle-RomanUpperCaseLabel\"},"
            + "{\"start\":[7],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-RomanUpperCaseLabel\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:2 Roman, Alpha\",\"start\":[7,0],\"name\":\"insertText\"},"
            + "{\"start\":[7,21],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: I.\",\"start\":[7,22],\"name\":\"insertText\"},"
            + "{\"start\":[7,9],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[7,13]},"
            + "{\"start\":[8],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-LetterUpperCaseLabel\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:1 Roman, Alpha\",\"start\":[8,0],\"name\":\"insertText\"},"
            + "{\"start\":[8,21],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: A.\",\"start\":[8,22],\"name\":\"insertText\"},"
            + "{\"start\":[8,16],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[8,20]},"
            + "{\"start\":[9],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-ImageLabel\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:0 Roman, Alpha, but list style referenced by paragraph style\",\"start\":[9,0],\"name\":\"insertText\"},"
            + "{\"start\":[9,67],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: image label with red bullet\",\"start\":[9,68],\"name\":\"insertText\"},"
            + "{\"start\":[10],\"attrs\":{\"paragraph\":{\"listStyleId\":\"OX_DEFAULT_LIST\",\"listStart\":true,\"listLevel\":0}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:0 Roman, Alpha no paragraph style\",\"start\":[10,0],\"name\":\"insertText\"},"
            + "{\"start\":[10,40],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: Implementation defined - AO/LO: bullet, MSO: no list, Calligra: number\",\"start\":[10,41],\"name\":\"insertText\"},"
            + "{\"listDefinition\":{\"listLevel1\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":1905,\"levelText\":\"%2.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"lowerLetter\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel0\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":1270,\"levelText\":\"%1.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"lowerLetter\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"listStyleId\":\"ListStyle-LetterLowerCaseLabel\"},"
            + "{\"start\":[11],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-LetterLowerCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:15 Roman, Alpha, roman, alpha\",\"start\":[11,0],\"name\":\"insertText\"},"
            + "{\"start\":[11,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: a.\",\"start\":[11,37],\"name\":\"insertText\"},"
            + "{\"start\":[11,10],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[11,14]},"
            + "{\"start\":[11,17],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[11,21]},"
            + "{\"start\":[11,24],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[11,28]},"
            + "{\"start\":[11,31],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[11,35]},"
            + "{\"listDefinition\":{\"listLevel1\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":1905,\"levelText\":\"%2.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1905,\"numberFormat\":\"lowerRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635},\"listLevel0\":{\"styleId\":\"Numbering_20_Symbols\",\"tabStopPosition\":1270,\"levelText\":\"%1.\",\"labelFollowedBy\":\"listtab\",\"indentLeft\":1270,\"numberFormat\":\"lowerRoman\",\"listLevelPositionAndSpaceMode\":\"label-alignment\",\"indentFirstLine\":-635}},\"name\":\"insertListStyle\",\"listStyleId\":\"ListStyle-RomanLowerCaseLabel\"},"
            + "{\"start\":[12],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-RomanLowerCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:14 Roman, Alpha, roman, alpha\",\"start\":[12,0],\"name\":\"insertText\"},"
            + "{\"start\":[12,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: i.\",\"start\":[12,37],\"name\":\"insertText\"},"
            + "{\"start\":[12,10],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[12,14]},"
            + "{\"start\":[12,17],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[12,21]},"
            + "{\"start\":[12,24],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[12,28]},"
            + "{\"start\":[13],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-LetterLowerCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:13 Roman, Alpha, roman, alpha\",\"start\":[13,0],\"name\":\"insertText\"},"
            + "{\"start\":[13,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: a.\",\"start\":[13,37],\"name\":\"insertText\"},"
            + "{\"start\":[13,10],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[13,14]},"
            + "{\"start\":[13,17],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[13,21]},"
            + "{\"start\":[13,31],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[13,35]},"
            + "{\"start\":[14],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-LetterUpperCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:12 Roman, Alpha, roman, alpha\",\"start\":[14,0],\"name\":\"insertText\"},"
            + "{\"start\":[14,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: A.\",\"start\":[14,37],\"name\":\"insertText\"},"
            + "{\"start\":[14,10],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[14,14]},"
            + "{\"start\":[14,17],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[14,21]},"
            + "{\"start\":[15],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-LetterLowerCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:11 Roman, Alpha, roman, alpha\",\"start\":[15,0],\"name\":\"insertText\"},"
            + "{\"start\":[15,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: a.\",\"start\":[15,37],\"name\":\"insertText\"},"
            + "{\"start\":[15,10],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[15,14]},"
            + "{\"start\":[15,24],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[15,28]},"
            + "{\"start\":[15,31],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[15,35]},"
            + "{\"start\":[16],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-RomanLowerCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:10 Roman, Alpha, roman, alpha\",\"start\":[16,0],\"name\":\"insertText\"},"
            + "{\"start\":[16,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: i.\",\"start\":[16,37],\"name\":\"insertText\"},"
            + "{\"start\":[16,10],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[16,14]},"
            + "{\"start\":[16,24],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[16,28]},"
            + "{\"start\":[17],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-LetterLowerCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:09 Roman, Alpha, roman, alpha\",\"start\":[17,0],\"name\":\"insertText\"},"
            + "{\"start\":[17,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: a.\",\"start\":[17,37],\"name\":\"insertText\"},"
            + "{\"start\":[17,10],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[17,14]},"
            + "{\"start\":[17,31],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[17,35]},"
            + "{\"start\":[18],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-RomanUpperCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:08 Roman, Alpha, roman, alpha\",\"start\":[18,0],\"name\":\"insertText\"},"
            + "{\"start\":[18,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: I.\",\"start\":[18,37],\"name\":\"insertText\"},"
            + "{\"start\":[18,10],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[18,14]},"
            + "{\"start\":[19],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-LetterLowerCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:07 Roman, Alpha, roman, alpha\",\"start\":[19,0],\"name\":\"insertText\"},"
            + "{\"start\":[19,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: a.\",\"start\":[19,37],\"name\":\"insertText\"},"
            + "{\"start\":[19,17],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[19,21]},"
            + "{\"start\":[19,24],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[19,28]},"
            + "{\"start\":[19,31],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[19,35]},"
            + "{\"start\":[20],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-RomanLowerCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:06 Roman, Alpha, roman, alpha\",\"start\":[20,0],\"name\":\"insertText\"},"
            + "{\"start\":[20,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: i.\",\"start\":[20,37],\"name\":\"insertText\"},"
            + "{\"start\":[20,17],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[20,21]},"
            + "{\"start\":[20,24],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[20,28]},"
            + "{\"start\":[21],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-LetterLowerCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:05 Roman, Alpha, roman, alpha\",\"start\":[21,0],\"name\":\"insertText\"},"
            + "{\"start\":[21,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: a.\",\"start\":[21,37],\"name\":\"insertText\"},"
            + "{\"start\":[21,17],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[21,21]},"
            + "{\"start\":[21,31],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[21,35]},"
            + "{\"start\":[22],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-LetterUpperCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:04 Roman, Alpha, roman, alpha\",\"start\":[22,0],\"name\":\"insertText\"},"
            + "{\"start\":[22,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: A.\",\"start\":[22,37],\"name\":\"insertText\"},"
            + "{\"start\":[22,17],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[22,21]},"
            + "{\"start\":[23],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-LetterLowerCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:03 Roman, Alpha, roman, alpha\",\"start\":[23,0],\"name\":\"insertText\"},"
            + "{\"start\":[23,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: a.\",\"start\":[23,37],\"name\":\"insertText\"},"
            + "{\"start\":[23,24],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[23,28]},"
            + "{\"start\":[23,31],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[23,35]},"
            + "{\"start\":[24],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-RomanLowerCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:02 Roman, Alpha, roman, alpha\",\"start\":[24,0],\"name\":\"insertText\"},"
            + "{\"start\":[24,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: i.\",\"start\":[24,37],\"name\":\"insertText\"},"
            + "{\"start\":[24,24],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[24,28]},"
            + "{\"start\":[25],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-LetterLowerCaseLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:01 Roman, Alpha, roman, alpha\",\"start\":[25,0],\"name\":\"insertText\"},"
            + "{\"start\":[25,36],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: a.\",\"start\":[25,37],\"name\":\"insertText\"},"
            + "{\"start\":[25,31],\"attrs\":{\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"3deb3d\",\"type\":\"rgb\"},\"boldAsian\":true}},\"name\":\"setAttributes\",\"end\":[25,35]},"
            + "{\"start\":[26],\"attrs\":{\"paragraph\":{\"style\":\"Standard\",\"listStyleId\":\"ListStyle-ImageLabel\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:00 Roman, Alpha, roman, alpha, but image list style referenced by paragraph style\",\"start\":[26,0],\"name\":\"insertText\"},"
            + "{\"start\":[26,88],\"name\":\"insertHardBreak\"},"
            + "{\"text\":\"Expected: image label with red bullet\",\"start\":[26,89],\"name\":\"insertText\"},"
            + "{\"start\":[27],\"attrs\":{\"paragraph\":{\"listStyleId\":\"OX_DEFAULT_LIST\",\"listLevel\":1}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"binary:00 Roman, Alpha, roman, alpha\",\"start\":[27,0],\"name\":\"insertText\"},"
            + "{\"start\":[28],\"attrs\":{\"paragraph\":{\"listLabelHidden\":true,\"listStyleId\":\"OX_DEFAULT_LIST\",\"listLevel\":1}},\"name\":\"insertParagraph\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void newListDefinitionTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty_as_can_be";
        String firstEditOperations = "["
            + "{\"start\":[0],\"name\":\"insertParagraph\"},"
            + "{\"text\":\"1\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"start\":[1],\"name\":\"insertParagraph\"},"
            + "{\"text\":\"2\",\"start\":[1,0],\"name\":\"insertText\"},"
            + "{\"listDefinition\":"
            + "{\"listLevel8\":{\"listStartValue\":1,\"levelText\":\"%9.\",\"indentLeft\":11430,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel6\":{\"listStartValue\":1,\"levelText\":\"%7.\",\"indentLeft\":8890,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel7\":{\"listStartValue\":1,\"levelText\":\"%8.\",\"indentLeft\":10160,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel5\":{\"listStartValue\":1,\"levelText\":\"%6.\",\"indentLeft\":7620,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel4\":{\"listStartValue\":1,\"levelText\":\"myprefixN4%0%1%2%3.mysuffixN4\",\"indentLeft\":6350,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel3\":{\"listStartValue\":1,\"levelText\":\"%4.\",\"indentLeft\":5080,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel2\":{\"listStartValue\":1,\"levelText\":\"%3.\",\"indentLeft\":3810,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel1\":{\"listStartValue\":1,\"levelText\":\"%2.\",\"indentLeft\":2540,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel0\":{\"listStartValue\":1,\"levelText\":\"myprefixN0%0%1%2%3.mysuffixN0\",\"indentLeft\":1270,\"numberFormat\":\"lowerLetter\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"listStyleId\":\"L10000\"},"
            + "{\"listDefinition\":"
            + "{\"listLevel8\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"●suffixB8\",\"indentLeft\":11430,\"numberFormat\":\"bullet\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel6\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"●\",\"indentLeft\":8890,\"numberFormat\":\"bullet\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel7\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"●\",\"indentLeft\":10160,\"numberFormat\":\"bullet\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel5\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"●\",\"indentLeft\":7620,\"numberFormat\":\"bullet\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel4\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"●\",\"indentLeft\":6350,\"numberFormat\":\"bullet\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel3\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"●\",\"indentLeft\":5080,\"numberFormat\":\"bullet\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel2\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"●\",\"indentLeft\":3810,\"numberFormat\":\"bullet\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel1\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"●\",\"indentLeft\":2540,\"numberFormat\":\"bullet\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}"
            + ",\"listLevel0\":{\"listStartValue\":1,\"fontName\":\"Times New Roman\",\"levelText\":\"●suffixB1\",\"indentLeft\":1270,\"numberFormat\":\"bullet\",\"indentFirstLine\":-635,\"textAlign\":\"left\"}},\"name\":\"insertListStyle\",\"listStyleId\":\"L20000\"},"
            + "{\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"nextStyleId\":\"ListParagraph\",\"contextualSpacing\":true}},\"name\":\"insertStyleSheet\",\"parent\":\"Standard\",\"uiPriority\":34,\"type\":\"paragraph\"},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listXmlId\":\"someID1a\",\"listStyleId\":\"L1x000\""
            + ",\"listLevel\":0}},\"name\":\"setAttributes\"},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listXmlId\":\"someID1b\",\"listStyleId\":\"L10000\""
            + ",\"listLevel\":1}},\"name\":\"setAttributes\"},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listXmlId\":\"someID2a\",\"listStyleId\":\"L2x000\""
            + ",\"listLevel\":2}},\"name\":\"setAttributes\"},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"ListParagraph\",\"listXmlId\":\"someID2b\",\"listStyleId\":\"L20000\""
            + ",\"listLevel\":1}},\"name\":\"setAttributes\"},"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * When a listLevel is changed on a paragraph the ancestor elements
     * <text:list> and <text:list-item> have to be inserted/deleted.
     */
    public void listLevelTest() {
        final String SOURCE_FILE_NAME_TRUNC = "ListHeading";
        String firstEditOperations = "["
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"listLevel\":0}},\"name\":\"setAttributes\"},"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * When a listLevel is changed on a paragraph the ancestor elements
     * <text:list> and <text:list-item> have to be inserted/deleted.
     */
    public void listCreationTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty_as_can_be";
        String firstEditOperations = "["
            + "{\"start\":[0],\"name\":\"insertParagraph\"},"
            + "{\"text\":\"1\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"listLevel\":3}},\"name\":\"setAttributes\"},"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Text will be inserted to a paragraph component/element, which has
     * children, but none component child
     */
    public void insertToBoilerplateElementsOnly() {
        final String SOURCE_FILE_NAME_TRUNC = "boilerplate";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"Something!\",\"start\":[0,0],\"name\":\"insertText\"}"
            + "]";
        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * Split should delete as well boilerplate from the first part!
     */
    public void splitBeforeBoilerplateContent() {
        final String SOURCE_FILE_NAME_TRUNC = "footnote";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[1,22],\"name\":\"splitParagraph\"}"
            + "]";
        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void deleteListByDeletingAllParagraphsFromWithinTest() {
        final String SOURCE_FILE_NAME_TRUNC = "ListStyleResolution";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[28],\"name\":\"delete\"},"
            + "{\"start\":[27],\"name\":\"delete\"},"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     */
    public void TabsInHyperlinkTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"Empty Document\",\"start\":[0,0],\"name\":\"insertText\"},{\"start\":[0,7],\"name\":\"insertTab\"},{\"start\":[0,8],\"name\":\"insertTab\"},{\"styleName\":\"Hyperlink\",\"styleId\":\"Hyperlink\",\"attrs\":{\"character\":{\"color\":{\"value\":\"hyperlink\",\"fallbackValue\":\"0080C0\",\"type\":\"scheme\"},\"underline\":true}},\"name\":\"insertStyleSheet\",\"parent\":null,\"uiPriority\":99,\"type\":\"character\"},{\"start\":[0,6],\"attrs\":{\"character\":{\"style\":\"Hyperlink\",\"url\":\"http://www.heise.de\"}},\"name\":\"setAttributes\",\"end\":[0,9]}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     */
    public void textBeforeEmptyBookmarkTest() {
        final String SOURCE_FILE_NAME_TRUNC = "field";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"hello\",\"start\":[0,0],\"name\":\"insertText\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     */
    public void whitespaceAroundFieldTest() {
        final String SOURCE_FILE_NAME_TRUNC = "field";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[0,0],\"name\":\"insertTab\"},"
            + "{\"start\":[0,3],\"attrs\":{},\"name\":\"insertTab\"},"
            + "{\"text\":\"   \",\"start\":[0,4],\"name\":\"insertText\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     */
    public void moveComponentTest() {
        final String SOURCE_FILE_NAME_TRUNC = "image-attributes";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"to\":[2,0],\"start\":[1,0],\"name\":\"move\",\"end\":[1,0]}"
            + "]";

        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    @Ignore // API CHANGE - FIX ME
    /**
     */
    public void insertNewImageTest() {
        final String SOURCE_FILE_NAME_TRUNC = "image-attributes";
        final String UID = "d03f7d7218eb";
        final String INTERNAL_IMAGE_PATH = "Pictures/uid" + UID + ".jpg";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[0,14],\"attrs\":{\"drawing\":{\"marginBottom\":317,\"height\":5982,\"marginLeft\":317,\"marginTop\":317,\"imageUrl\":\"" + INTERNAL_IMAGE_PATH + "\",\"marginRight\":317,\"width\":17013}},\"name\":\"insertDrawing\",\"type\":\"image\"}"
            + "]";

        editOperations.add(firstEditOperations);
        Map<Long, byte[]> resourceMap = new HashMap<Long, byte[]>();
        byte[] imageBytes = null;
        try {
            imageBytes = ResourceUtilities.loadFileAsBytes(ResourceUtilities.getReferenceFile("testA.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(EditingRegressionTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail(ex.getMessage());
        }
        long uid = Long.parseLong(UID, 16);
        resourceMap.put(uid, imageBytes);
        String savedDocumentPath = super.roundtripRegressionWithResourcesTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations, resourceMap, true);
        OdfPackage pkg = null;
        try {
            // return the absolute path to the test directory
            savedDocumentPath = ResourceUtilities.getTestOutputFolder() + savedDocumentPath;
            pkg = OdfPackage.loadPackage(savedDocumentPath);
        } catch (Exception ex) {
            Assert.fail("The saved document '" + savedDocumentPath + "' could not be found!");
            Logger.getLogger(EditingRegressionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pkg == null || !pkg.contains(INTERNAL_IMAGE_PATH)) {
            Assert.fail("The image '" + INTERNAL_IMAGE_PATH + "' could not be found in the saved document '" + savedDocumentPath + "'!");
        }
    }

    @Test
    /**
     */
    public void exportImageAttributesTest() {
        final String SOURCE_FILE_NAME_TRUNC = "odt-images-linked";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[2,0],\"attrs\":{\"drawing\":{\"anchorHorOffset\":0,\"textWrapMode\":\"square\",\"anchorHorBase\":\"column\",\"inline\":false,\"anchorHorAlign\":\"left\",\"textWrapSide\":\"right\"}},\"name\":\"setAttributes\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     */
    public void changeListAlignmentTest() {
        final String SOURCE_FILE_NAME_TRUNC = "images";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[10,16],\"attrs\":{\"drawing\":{\"anchorHorBase\":\"column\",\"textWrapMode\":\"square\",\"anchorHorOffset\":0,\"inline\":false,\"anchorHorAlign\":\"left\",\"textWrapSide\":\"right\"}},\"name\":\"setAttributes\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void deleteUndoTest() {
        final String SOURCE_FILE_NAME_TRUNC = "odf-fields";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[1,17],\"name\":\"delete\",\"end\":[1,17]},"
            + "{\"start\":[1,17],\"attrs\":{\"character\":{\"vertAlign\":null,\"strike\":null,\"bold\":null,\"fontName\":null,\"style\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"language\":null,\"italic\":null,\"fontSize\":null,\"url\":null}},\"representation\":\"14:58:45\",\"name\":\"insertField\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void imageChangeHorizontalAlignTest() {
        final String SOURCE_FILE_NAME_TRUNC = "images";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            // Moving Charlie Brown from left alinged to right aligned
            + "{\"start\":[8,0],\"attrs\":{\"drawing\":{\"anchorHorBase\":\"column\",\"textWrapMode\":\"square\",\"anchorHorOffset\":0,\"inline\":false,\"anchorHorAlign\":\"right\",\"textWrapSide\":\"left\"}},\"name\":\"setAttributes\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void columnWidthTest() {
        final String SOURCE_FILE_NAME_TRUNC = "feature_attributes_tables";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"insertMode\":\"behind\",\"start\":[4],\"name\":\"insertColumn\",\"tableGrid\":[20,20,20,20,20],\"gridPosition\":2},"
            + "{\"start\":[4],\"attrs\":{\"table\":{\"tableGrid\":[20,20,20,20,20]}},\"name\":\"setAttributes\"},"
            + "{\"start\":[4,0,3,0],\"name\":\"insertParagraph\"},{\"text\":\"b\",\"start\":[4,0,3,0,0],\"name\":\"insertText\"},"
            + "{\"start\":[4,1,3,0],\"name\":\"insertParagraph\"},{\"text\":\"c\",\"start\":[4,1,3,0,0],\"name\":\"insertText\"},"
            + "{\"start\":[4,2,3,0],\"name\":\"insertParagraph\"},{\"text\":\"d\",\"start\":[4,2,3,0,0],\"name\":\"insertText\"},"
            + "{\"start\":[4,3,3,0],\"name\":\"insertParagraph\"},{\"text\":\"e\",\"start\":[4,3,3,0,0],\"name\":\"insertText\"},"
            + "{\"text\":\"b\",\"start\":[4,0,3,0,0],\"name\":\"insertText\"},"
            + "{\"insertMode\":\"behind\",\"start\":[4],\"name\":\"insertColumn\",\"tableGrid\":[17,17,17,17,17,17],\"gridPosition\":1},"
            + "{\"start\":[4],\"attrs\":{\"table\":{\"tableGrid\":[17,17,17,17,17,17]}},\"name\":\"setAttributes\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void hyperlinkInsertionTest() {

        final String SOURCE_FILE_NAME_TRUNC = "hyperlink_destination";
        String firstEditOperations = "["
            + "{\"name\":\"setAttributes\",\"start\":[0,2],\"end\":[0,2],\"attrs\":{\"character\":{\"color\":{\"value\":\"C00000\",\"type\":\"rgb\"},\"fillColor\":{\"value\":\"FFF000\",\"type\":\"rgb\"},\"url\":\"http://www.heise.de/\"}}},"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    public void insertSingleCharacterTest() {
        final String SOURCE_FILE_NAME_TRUNC = "feature_attributes_character_MSO15";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\" \",\"start\":[3,5],\"name\":\"insertText\"},"
            + "{\"text\":\"a\",\"start\":[3,6],\"name\":\"insertText\"},"
            + "{\"text\":\"b\",\"start\":[3,7],\"name\":\"insertText\"},"
            + "{\"text\":\"c\",\"start\":[3,8],\"name\":\"insertText\"},"
            + "{\"text\":\"d\",\"start\":[3,9],\"name\":\"insertText\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    @Ignore // as the file name is randomized so the reference will always fail
    public void imageBase64Test() {
        final String SOURCE_FILE_NAME_TRUNC = "image";

        List<String> editOperations = new ArrayList();
        String inputOpsFromFile = null;
        String referenceOpsTextFilePath = OUTPUT_DIRECTORY + "image-base64.ops";
        File referenceReloadedOpsFile = ResourceUtilities.getReferenceFile(referenceOpsTextFilePath);
        if (referenceReloadedOpsFile.exists()) {
            inputOpsFromFile = ResourceUtilities.loadFileAsString(referenceReloadedOpsFile);
        }
        editOperations.add(inputOpsFromFile);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     */
    public void insertColumnsWithStyleWithoutXmlId() {
        final String SOURCE_FILE_NAME_TRUNC = "feature_attributes_tables_FunnyTable_With_xmlid";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"insertMode\":\"behind\",\"start\":[2],\"name\":\"insertColumn\",\"tableGrid\":[20,20,20,20,20],\"gridPosition\":0},{\"start\":[2],\"attrs\":{\"table\":{\"tableGrid\":[20,20,20,20,20]}},\"name\":\"setAttributes\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     */
    public void insertColumnWithRepeatedCells() {
        final String SOURCE_FILE_NAME_TRUNC = "feature_attributes_tables";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"insertMode\":\"behind\",\"start\":[21],\"name\":\"insertColumn\",\"tableGrid\":[20,20,20,20,20],\"gridPosition\":0},"
            + "{\"start\":[21],\"attrs\":{\"table\":{\"tableGrid\":[20,20,20,20,20]}},\"name\":\"setAttributes\"}"
            + "]";

        editOperations.add(firstEditOperations);

        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    @Ignore // generated automatic styles differs from test to test, therefore the reference is not usable and test always fails..
    public void insertTableTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty_as_can_be";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"styleId\":\"TableGrid\",\"styleName\":\"Table Grid\",\"attrs\":{\"wholeTable\":{\"paragraph\":{\"lineHeight\":{\"value\":100,\"type\":\"percent\"}},\"table\":{\"borderInsideVert\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderTop\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderInsideHor\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderBottom\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderLeft\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderRight\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17}}},\"next\":null},\"name\":\"insertStyleSheet\",\"uiPriority\":59,\"type\":\"table\"},{\"start\":[0],\"attrs\":{\"table\":{\"style\":\"TableGrid\",\"tableGrid\":[1000,1000,1000,1000],\"width\":\"auto\",\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]}},\"name\":\"insertTable\"},{\"count\":2,\"start\":[0,0],\"name\":\"insertRows\",\"insertDefaultCells\":true}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     */
    public void insertRowAtSecondPosition() {
        final String SOURCE_FILE_NAME_TRUNC = "feature_attributes_tables-backgroundTableOnly";

        String firstEditOperations = "["
            + "{\"count\":1,\"start\":[4,1],\"name\":\"insertRows\",\"insertDefaultCells\":false,\"referenceRow\":0}"
            + "]";

        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    public void spanStyleInheritanceTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty_as_can_be";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"name\":\"insertStyleSheet\",\"styleId\":\"BLUE\",\"type\":\"character\",\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"0000FF\",\"type\":\"rgb\"}}},\"parent\":\"default_character_style\"},{\"name\":\"insertStyleSheet\",\"styleId\":\"ITALIC\",\"type\":\"character\",\"attrs\":{\"character\":{\"italic\":true,\"italicAsian\":true}},\"parent\":\"default_character_style\"},{\"name\":\"insertStyleSheet\",\"styleId\":\"BOLD\",\"type\":\"character\",\"attrs\":{\"character\":{\"boldAsian\":true,\"bold\":true}},\"parent\":\"default_character_style\"},{\"name\":\"insertStyleSheet\",\"styleId\":\"BACKGROUND_YELLOW\",\"type\":\"character\",\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"FFFF00\",\"type\":\"rgb\"}}},\"parent\":\"default_character_style\"},{\"name\":\"insertFontDescription\",\"fontName\":\"Courier\",\"attrs\":{\"family\":\"Courier\",\"familyGeneric\":\"modern\",\"pitch\":\"fixed\"}},{\"name\":\"insertParagraph\",\"start\":[0],\"attrs\":{\"paragraph\":{}}},{\"text\":\"TEST ALL INNER AND OUTER SPAN COMBINATIONS\",\"start\":[0,0],\"name\":\"insertText\"},{\"name\":\"insertParagraph\",\"start\":[1],\"attrs\":{\"paragraph\":{}}},{\"text\":\"There are the binar flags: OuterHasTemplate OuterHasAuto InnerHasTemplate InnerHasAuto\",\"start\":[1,0],\"name\":\"insertText\"},{\"name\":\"insertParagraph\",\"start\":[2],\"attrs\":{\"paragraph\":{}}},{\"text\":\"00: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[2,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[2,4],\"end\":[2,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[2,26],\"end\":[2,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[2,45],\"end\":[2,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[2,67],\"end\":[2,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[3],\"attrs\":{\"paragraph\":{}}},{\"text\":\" normal \",\"start\":[3,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[3,8]},{\"name\":\"insertParagraph\",\"start\":[4],\"attrs\":{\"paragraph\":{}}},{\"text\":\"01: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[4,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[4,4],\"end\":[4,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[4,26],\"end\":[4,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[4,45],\"end\":[4,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[4,67],\"end\":[4,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[5],\"attrs\":{\"paragraph\":{}}},{\"text\":\" red \",\"start\":[5,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[5,5]},{\"name\":\"setAttributes\",\"start\":[5,0],\"end\":[5,4],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[6],\"attrs\":{\"paragraph\":{}}},{\"text\":\"02: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[6,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[6,4],\"end\":[6,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[6,26],\"end\":[6,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[6,45],\"end\":[6,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[6,67],\"end\":[6,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[7],\"attrs\":{\"paragraph\":{}}},{\"text\":\" yellow background \",\"start\":[7,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[7,19]},{\"name\":\"setAttributes\",\"start\":[7,0],\"end\":[7,18],\"attrs\":{\"character\":{\"style\":\"BACKGROUND_YELLOW\"}}},{\"name\":\"insertParagraph\",\"start\":[8],\"attrs\":{\"paragraph\":{}}},{\"text\":\"03: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[8,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[8,4],\"end\":[8,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[8,26],\"end\":[8,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[8,45],\"end\":[8,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[8,67],\"end\":[8,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[9],\"attrs\":{\"paragraph\":{}}},{\"text\":\" yellow background and red color \",\"start\":[9,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[9,33]},{\"name\":\"setAttributes\",\"start\":[9,0],\"end\":[9,32],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"style\":\"BACKGROUND_YELLOW\"}}},{\"name\":\"insertParagraph\",\"start\":[10],\"attrs\":{\"paragraph\":{}}},{\"text\":\"04: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[10,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[10,4],\"end\":[10,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[10,26],\"end\":[10,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[10,45],\"end\":[10,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[10,67],\"end\":[10,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[11],\"attrs\":{\"paragraph\":{}}},{\"text\":\" super \",\"start\":[11,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[11,7]},{\"name\":\"setAttributes\",\"start\":[11,0],\"end\":[11,6],\"attrs\":{\"character\":{\"vertAlign\":\"super\"}}},{\"name\":\"insertParagraph\",\"start\":[12],\"attrs\":{\"paragraph\":{}}},{\"text\":\"05: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[12,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[12,4],\"end\":[12,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[12,26],\"end\":[12,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[12,45],\"end\":[12,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[12,67],\"end\":[12,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[13],\"attrs\":{\"paragraph\":{}}},{\"text\":\" super and red \",\"start\":[13,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[13,15]},{\"name\":\"setAttributes\",\"start\":[13,0],\"end\":[13,14],\"attrs\":{\"character\":{\"vertAlign\":\"super\",\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[14],\"attrs\":{\"paragraph\":{}}},{\"text\":\"06: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[14,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[14,4],\"end\":[14,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[14,26],\"end\":[14,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[14,45],\"end\":[14,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[14,67],\"end\":[14,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[15],\"attrs\":{\"paragraph\":{}}},{\"text\":\" super and yellow background \",\"start\":[15,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[15,29]},{\"name\":\"setAttributes\",\"start\":[15,0],\"end\":[15,28],\"attrs\":{\"character\":{\"vertAlign\":\"super\",\"style\":\"BACKGROUND_YELLOW\"}}},{\"name\":\"insertParagraph\",\"start\":[16],\"attrs\":{\"paragraph\":{}}},{\"text\":\"07: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[16,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[16,4],\"end\":[16,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[16,26],\"end\":[16,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[16,45],\"end\":[16,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[16,67],\"end\":[16,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[17],\"attrs\":{\"paragraph\":{}}},{\"text\":\" super, yellow background and red \",\"start\":[17,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[17,34]},{\"name\":\"setAttributes\",\"start\":[17,0],\"end\":[17,33],\"attrs\":{\"character\":{\"vertAlign\":\"super\",\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"style\":\"BACKGROUND_YELLOW\"}}},{\"name\":\"insertParagraph\",\"start\":[18],\"attrs\":{\"paragraph\":{}}},{\"text\":\"08: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[18,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[18,4],\"end\":[18,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[18,26],\"end\":[18,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[18,45],\"end\":[18,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[18,67],\"end\":[18,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[19],\"attrs\":{\"paragraph\":{}}},{\"text\":\" italic \",\"start\":[19,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[19,8]},{\"name\":\"setAttributes\",\"start\":[19,0],\"end\":[19,7],\"attrs\":{\"character\":{\"style\":\"ITALIC\"}}},{\"name\":\"insertParagraph\",\"start\":[20],\"attrs\":{\"paragraph\":{}}},{\"text\":\"09: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[20,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[20,4],\"end\":[20,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[20,26],\"end\":[20,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[20,45],\"end\":[20,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[20,67],\"end\":[20,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[21],\"attrs\":{\"paragraph\":{}}},{\"text\":\" italic and red \",\"start\":[21,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[21,16]},{\"name\":\"setAttributes\",\"start\":[21,0],\"end\":[21,15],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"style\":\"ITALIC\"}}},{\"name\":\"insertParagraph\",\"start\":[22],\"attrs\":{\"paragraph\":{}}},{\"text\":\"10: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[22,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[22,4],\"end\":[22,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[22,26],\"end\":[22,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[22,45],\"end\":[22,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[22,67],\"end\":[22,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[23],\"attrs\":{\"paragraph\":{}}},{\"text\":\" italic and yellow background \",\"start\":[23,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[23,30]},{\"name\":\"setAttributes\",\"start\":[23,0],\"end\":[23,29],\"attrs\":{\"character\":{\"style\":\"BACKGROUND_YELLOW\"}}},{\"name\":\"insertParagraph\",\"start\":[24],\"attrs\":{\"paragraph\":{}}},{\"text\":\"11: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[24,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[24,4],\"end\":[24,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[24,26],\"end\":[24,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[24,45],\"end\":[24,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[24,67],\"end\":[24,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[25],\"attrs\":{\"paragraph\":{}}},{\"text\":\" italic, yellow background and red \",\"start\":[25,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[25,35]},{\"name\":\"setAttributes\",\"start\":[25,0],\"end\":[25,34],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"style\":\"BACKGROUND_YELLOW\"}}},{\"name\":\"insertParagraph\",\"start\":[26],\"attrs\":{\"paragraph\":{}}},{\"text\":\"12: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[26,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[26,4],\"end\":[26,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[26,26],\"end\":[26,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[26,45],\"end\":[26,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[26,67],\"end\":[26,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[27],\"attrs\":{\"paragraph\":{}}},{\"text\":\" italic and super \",\"start\":[27,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[27,18]},{\"name\":\"setAttributes\",\"start\":[27,0],\"end\":[27,17],\"attrs\":{\"character\":{\"vertAlign\":\"super\",\"style\":\"ITALIC\"}}},{\"name\":\"insertParagraph\",\"start\":[28],\"attrs\":{\"paragraph\":{}}},{\"text\":\"13: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[28,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[28,4],\"end\":[28,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[28,26],\"end\":[28,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[28,45],\"end\":[28,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[28,67],\"end\":[28,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[29],\"attrs\":{\"paragraph\":{}}},{\"text\":\" italic, super and red \",\"start\":[29,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[29,23]},{\"name\":\"setAttributes\",\"start\":[29,0],\"end\":[29,22],\"attrs\":{\"character\":{\"vertAlign\":\"super\",\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[30],\"attrs\":{\"paragraph\":{}}},{\"text\":\"14: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[30,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[30,4],\"end\":[30,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[30,26],\"end\":[30,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[30,45],\"end\":[30,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[30,67],\"end\":[30,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[31],\"attrs\":{\"paragraph\":{}}},{\"text\":\" italic, super and yellow background \",\"start\":[31,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[31,37]},{\"name\":\"setAttributes\",\"start\":[31,0],\"end\":[31,36],\"attrs\":{\"character\":{\"vertAlign\":\"super\",\"style\":\"BACKGROUND_YELLOW\"}}},{\"name\":\"insertParagraph\",\"start\":[32],\"attrs\":{\"paragraph\":{}}},{\"text\":\"15: \\\"outer:parent italic\\\" \\\"outer:auto super\\\" \\\"inner:parent backg.\\\" \\\"inner:auto red\\\"\",\"start\":[32,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[32,4],\"end\":[32,24],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[32,26],\"end\":[32,43],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[32,45],\"end\":[32,65],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"setAttributes\",\"start\":[32,67],\"end\":[32,82],\"attrs\":{\"character\":{\"color\":{\"value\":\"00FF00\",\"type\":\"rgb\"}}}},{\"name\":\"insertParagraph\",\"start\":[33],\"attrs\":{\"paragraph\":{}}},{\"text\":\" italic, super, yellow background and red \",\"start\":[33,0],\"name\":\"insertText\"},{\"name\":\"insertHardBreak\",\"start\":[33,42]},{\"name\":\"setAttributes\",\"start\":[33,0],\"end\":[33,41],\"attrs\":{\"character\":{\"vertAlign\":\"super\",\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"style\":\"BACKGROUND_YELLOW\"}}},{\"name\":\"insertParagraph\",\"start\":[34],\"attrs\":{\"paragraph\":{}}},{\"text\":\"Three styles: Courier, sub, super (outer to inner):superscript\",\"start\":[34,0],\"name\":\"insertText\"},{\"name\":\"setAttributes\",\"start\":[34,51],\"end\":[34,61],\"attrs\":{\"character\":{\"vertAlign\":\"super\"}}}"
            + "]";
        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * Split position marks the first component of the second half, therefore
     * Character is Moving the unknown content DANGER: These tests are unable to
     * detect unknown content, like if it is in the first or second half!
     */
    public void markOverUnknownContentPlusSpaceElement() {

        final String SOURCE_FILE_NAME_TRUNC = "DUMMY";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "]";
        // {"start":[1,22],"name":"splitParagraph"},{"text":"Svante","start":[2,0],"name":"insertText"},{"start":[0,6],"attrs":{"character":{"color":{"value":"C00000","type":"rgb"}}},"name":"setAttributes","end":[0,22]},{"start":[1,0],"attrs":{"character":{"color":{"value":"C00000","type":"rgb"}}},"name":"setAttributes","end":[1,21]},{"start":[2,0],"attrs":{"character":{"color":{"value":"C00000","type":"rgb"}}},"name":"setAttributes","end":[2,4]},{"start":[2,0],"name":"delete","end":[2,3]},{"start":[1],"name":"delete"},{"start":[0,8],"name":"delete","end":[0,22]},{"start":[0],"name":"mergeParagraph"}
        String secondEditOperations = "["
            + "{\"start\":[1,22],\"name\":\"splitParagraph\"},"
            + "{\"text\":\"svante\",\"start\":[2,0],\"name\":\"insertText\"},"
            + "{\"start\":[0,6],\"attrs\":{\"character\":{\"color\":{\"value\":\"C00000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"end\":[0,22]},"
            + "{\"start\":[1,0],\"attrs\":{\"character\":{\"color\":{\"value\":\"C00000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"end\":[1,21]},"
            + "{\"start\":[2,0],\"attrs\":{\"character\":{\"color\":{\"value\":\"C00000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"end\":[2,4]}"
            + ","
            + "{\"start\":[0,6],\"name\":\"delete\",\"end\":[0,21]}"
            + "]";
        editOperations.add(firstEditOperations);
        editOperations.add(secondEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void mergeOverUnknownContent() {

        final String SOURCE_FILE_NAME_TRUNC = "footnote";

        String firstEditOperations = "["
            + " {\"start\":[1,22],\"name\":\"splitParagraph\"},"
            + "{\"text\":\"Svante\",\"start\":[2,0],\"name\":\"insertText\"},"
            + "{\"start\":[0,6],\"attrs\":{\"character\":{\"color\":{\"value\":\"C00000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"end\":[0,22]},"
            + "{\"start\":[1,0],\"attrs\":{\"character\":{\"color\":{\"value\":\"C00000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"end\":[1,21]},"
            + "{\"start\":[2,0],\"attrs\":{\"character\":{\"color\":{\"value\":\"C00000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"end\":[2,4]}"
            + ","
            + "{\"start\":[2,0],\"name\":\"delete\",\"end\":[2,3]},"
            + "{\"start\":[1],\"name\":\"delete\"},"
            + "{\"start\":[0,8],\"name\":\"delete\",\"end\":[0,22]}"
            + ","
            + "{\"start\":[0],\"name\":\"mergeParagraph\"}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    public void markOverUnknownContent() {

        final String SOURCE_FILE_NAME_TRUNC = "footnote";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "]";

        String secondEditOperations = "["
            + "{\"start\":[1,22],\"name\":\"splitParagraph\"},"
            + "{\"text\":\"Svante\",\"start\":[2,0],\"name\":\"insertText\"},"
            + "{\"start\":[0,6],\"attrs\":{\"character\":{\"color\":{\"value\":\"C00000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"end\":[0,22]},"
            + "{\"start\":[1,0],\"attrs\":{\"character\":{\"color\":{\"value\":\"C00000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"end\":[1,21]},"
            + "{\"start\":[2,0],\"attrs\":{\"character\":{\"color\":{\"value\":\"C00000\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"end\":[2,4]}"
            + "]";
        editOperations.add(firstEditOperations);
        editOperations.add(secondEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void deleteOverUnknownContent() {
        final String SOURCE_FILE_NAME_TRUNC = "footnote";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[1,22],\"name\":\"splitParagraph\"},"
            + "{\"text\":\"Something!\",\"start\":[2,0],\"name\":\"insertText\"}"
            + "]";
        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void hyperlinkTest() {
        final String SOURCE_FILE_NAME_TRUNC = "hyperlink";
        String firstEditOperations = "["
            + "{\"styleId\":\"Hyperlink\",\"styleName\":\"Hyperlink\",\"attrs\":{\"character\":{\"color\":{\"value\":\"hyperlink\",\"fallbackValue\":\"0080C0\",\"type\":\"scheme\"},\"underline\":true}},\"name\":\"insertStyleSheet\",\"parent\":null,\"uiPriority\":99,\"type\":\"character\"},{\"start\":[0,4],\"attrs\":{\"character\":{\"style\":\"Hyperlink\",\"url\":\"http://www.alternate.de\"}},\"name\":\"setAttributes\",\"end\":[0,4]}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    public void multipleSplitMergeTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty_as_can_be";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"heading1\"}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"first\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"heading1\"}},\"name\":\"insertParagraph\"},"
            + "{\"text\":\"second\",\"start\":[1,0],\"name\":\"insertText\"}"
            + "]";

        String secondEditOperations = "["
            + "{\"start\":[0],\"name\":\"mergeParagraph\"},"
            + "{\"start\":[0,5],\"name\":\"splitParagraph\"},"
            + "{\"start\":[0],\"name\":\"mergeParagraph\"},"
            + "{\"start\":[0,5],\"name\":\"splitParagraph\"},"
            + "{\"start\":[0],\"name\":\"mergeParagraph\"},"
            + "{\"start\":[0,5],\"name\":\"splitParagraph\"},"
            + "{\"start\":[0],\"name\":\"mergeParagraph\"},"
            + "{\"start\":[0,5],\"name\":\"splitParagraph\"},"
            + "{\"start\":[0],\"name\":\"mergeParagraph\"},"
            + "{\"start\":[0,5],\"name\":\"splitParagraph\"},"
            + "{\"start\":[0],\"name\":\"mergeParagraph\"},"
            + "{\"start\":[0,5],\"name\":\"splitParagraph\"},"
            + "{\"start\":[0],\"name\":\"mergeParagraph\"},"
            + "{\"start\":[0,5],\"name\":\"splitParagraph\"},"
            + "{\"start\":[0],\"name\":\"mergeParagraph\"},"
            + "{\"start\":[0,5],\"name\":\"splitParagraph\"},"
            + "{\"start\":[0],\"name\":\"mergeParagraph\"},"
            + "{\"start\":[0,5],\"name\":\"splitParagraph\"},"
            + "{\"start\":[0],\"name\":\"mergeParagraph\"},"
            + "{\"start\":[0,5],\"name\":\"splitParagraph\"},"
            + "{\"start\":[0],\"name\":\"mergeParagraph\"},"
            + "{\"start\":[0,5],\"name\":\"splitParagraph\"}"
            + "]";
        editOperations.add(firstEditOperations);
        editOperations.add(secondEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void MergeSplitOverUnknownContent() {
        final String SOURCE_FILE_NAME_TRUNC = "footnote";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "]";

        String secondEditOperations = "["
            + "{\"start\":[0,0],\"name\":\"splitParagraph\"},"
            + "{\"start\":[0],\"name\":\"mergeParagraph\"},"
            + "{\"start\":[1,22],\"name\":\"splitParagraph\"},"
            + "{\"text\":\"This text was lost!!\",\"start\":[2,0],\"name\":\"insertText\"}"
            + "]";
        editOperations.add(firstEditOperations);
        editOperations.add(secondEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void splitParagraphAtBeginTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty_as_can_be";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"name\":\"insertParagraph\",\"start\":[0],\"attrs\":{\"paragraph\":{}}},"
            + "{\"text\":\"heading\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"styleId\":\"heading1\",\"styleName\":\"heading 1\",\"attrs\":{\"paragraph\":{\"outlineLevel\":0},\"next\":\"default_paragraph_style\",\"character\":{\"bold\":true,\"color\":{\"value\":\"accent1\",\"fallbackValue\":\"376092\",\"type\":\"scheme\",\"transformations\":[{\"value\":74902,\"type\":\"shade\"}]},\"fontSize\":14}},\"name\":\"insertStyleSheet\",\"parent\":\"default_paragraph_style\",\"uiPriority\":9,\"type\":\"paragraph\"},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"listStyleId\":null,\"listLevel\":null,\"borderBottom\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"showListLabel\":null,\"marginBottom\":null,\"tabStops\":null,\"listStartValue\":null,\"indentRight\":null,\"indentLeft\":null,\"style\":\"heading1\",\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\"},"
            + "{\"start\":[0,0],\"name\":\"splitParagraph\"}"
            + "]";
        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void insertWithSpace() {
        final String SOURCE_FILE_NAME_TRUNC = "feature_attributes_character_MSO15";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\" svante\",\"start\":[5,5],\"name\":\"insertText\"}"
            + "]";
        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     */
    public void editingUsingMultipleSpans() {
        final String SOURCE_FILE_NAME_TRUNC = "empty_as_can_be";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"name\":\"insertParagraph\",\"start\":[0],\"attrs\":{\"paragraph\":{}}},"
            + "{\"text\":\"Courier\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"start\":[0,0],\"attrs\":{\"character\":{\"fontName\":\"Courier\"}},\"name\":\"setAttributes\",\"end\":[0,6]},"
            + "{\"text\":\" subscript\",\"start\":[0,7],\"name\":\"insertText\"},"
            + "{\"start\":[0,8],\"attrs\":{\"character\":{\"vertAlign\":\"sub\"}},\"name\":\"setAttributes\",\"end\":[0,16]},"
            + "{\"text\":\" \",\"start\":[0,17],\"name\":\"insertText\"},"
            + "{\"text\":\"superscript\",\"start\":[0,18],\"attrs\":{\"character\":{\"vertAlign\":\"super\"}},\"name\":\"insertText\"}"
            + "]";
        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    public void insertTextTest() {
        final String SOURCE_FILE_NAME_TRUNC = "ListStyleResolution";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"Svante\",\"start\":[11,7],\"name\":\"insertText\"},"
            + "]";
        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    // Verified
    public void splitSpaceElementTest() {
        final String SOURCE_FILE_NAME_TRUNC = "compdocfileformat_shortened";

        List<String> firstEditOperations = new ArrayList();
        String firstEditOperation = "["
            + "{\"name\":\"insertParagraph\",\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"Standard\"}}},"
            + "{\"text\":\"2  3 4 \",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"name\":\"setAttributes\",\"start\":[0,0],\"end\":[0,0],\"attrs\":{\"character\":{\"boldComplex\":true,\"boldAsian\":true,\"bold\":true,\"fillColor\":{\"value\":\"3deb3d\",\"type\":\"rgb\"}}}},"
            + "{\"name\":\"setAttributes\",\"start\":[0,2],\"end\":[0,2],\"attrs\":{\"character\":{\"boldComplex\":true,\"boldAsian\":true,\"bold\":true,\"fillColor\":{\"value\":\"3deb3d\",\"type\":\"rgb\"}}}},"
            + "]";

        firstEditOperations.add(firstEditOperation);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    public void deletionSpannedColumnTest() {
        final String SOURCE_FILE_NAME_TRUNC = "feature_attributes_tables_SMALL";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"startGrid\":2,\"start\":[2],\"name\":\"deleteColumns\",\"endGrid\":2},"
            + "{\"start\":[2],\"attrs\":{\"table\":{\"tableGrid\":[25,25,25]}},\"name\":\"setAttributes\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    // as below & above
    public void deletionColumnTest() {
        final String SOURCE_FILE_NAME_TRUNC = "feature_attributes_tables-backgroundTableOnly";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"startGrid\":0,\"start\":[4],\"name\":\"deleteColumns\",\"endGrid\":0},"
            + "{\"start\":[4],\"attrs\":{\"table\":{\"tableGrid\":[25,25,25]}},\"name\":\"setAttributes\"}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    public void columnDelete() {
        final String SOURCE_FILE_NAME_TRUNC = "empty_as_can_be";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"styleId\":\"default_paragraph_style\",\"default\":true,\"hidden\":true,\"attrs\":{\"paragraph\":{\"alignment\":\"left\",\"lineHeight\":{\"value\":\"100\",\"type\":\"percent\"},\"paddingTop\":0,\"fillColor\":{\"value\":\"transparent\",\"type\":\"auto\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"paddingLeft\":0,\"paddingBottom\":0,\"paddingRight\":0},\"character\":{\"fontNameComplex\":\"Times New Roman\",\"italicAsian\":false,\"fontName\":\"Cambria\",\"boldComplex\":false,\"fillColor\":{\"value\":\"transparent\",\"type\":\"auto\"},\"letterSpacing\":\"normal\",\"fontSizeAsian\":12,\"italicComplex\":false,\"italic\":false,\"fontSize\":12,\"vertAlign\":\"baseline\",\"bold\":false,\"language\":\"en-US\",\"boldAsian\":false,\"fontNameAsian\":\"MS Mincho\"}},\"name\":\"insertStyleSheet\",\"type\":\"paragraph\"},{\"styleName\":\"Normal\",\"styleId\":\"Normal\",\"attrs\":{},\"name\":\"insertStyleSheet\",\"parent\":\"default_paragraph_style\",\"type\":\"paragraph\"},{\"styleName\":\"Subtitle\",\"styleId\":\"Subtitle\",\"attrs\":{\"character\":{\"italicAsian\":true,\"fontName\":\"Calibri\",\"fontNameComplex\":\"Times New Roman\",\"letterSpacing\":26,\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"italicComplex\":true,\"italic\":true,\"fontNameAsian\":\"MS Gothic\"}},\"next\":\"Normal\",\"name\":\"insertStyleSheet\",\"parent\":\"Normal\",\"type\":\"paragraph\"},{\"styleName\":\"Heading 1\",\"styleId\":\"Heading1\",\"attrs\":{\"paragraph\":{\"marginTop\":847,\"outlineLevel\":0},\"character\":{\"fontNameComplex\":\"Times New Roman\",\"fontName\":\"Calibri\",\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"345A8A\",\"type\":\"rgb\"},\"fontSizeAsian\":16,\"fontSize\":16,\"fontNameAsian\":\"MS Gothic\",\"boldAsian\":true}},\"next\":\"Normal\",\"name\":\"insertStyleSheet\",\"parent\":\"Normal\",\"type\":\"paragraph\"},{\"styleName\":\"Title\",\"styleId\":\"Title\",\"attrs\":{\"paragraph\":{\"marginBottom\":529,\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":35},\"borderLeft\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"paddingLeft\":0,\"paddingBottom\":141,\"paddingRight\":0},\"character\":{\"fontName\":\"Calibri\",\"fontNameComplex\":\"Times New Roman\",\"letterSpacing\":9,\"color\":{\"value\":\"17365D\",\"type\":\"rgb\"},\"fontSizeAsian\":26,\"fontSize\":26,\"fontNameAsian\":\"MS Gothic\"}},\"next\":\"Normal\",\"name\":\"insertStyleSheet\",\"parent\":\"Normal\",\"type\":\"paragraph\"},{\"styleName\":\"Heading 2\",\"styleId\":\"Heading2\",\"attrs\":{\"paragraph\":{\"marginTop\":353,\"outlineLevel\":1},\"character\":{\"fontNameComplex\":\"Times New Roman\",\"fontName\":\"Calibri\",\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"fontSizeAsian\":13,\"fontSize\":13,\"fontNameAsian\":\"MS Gothic\",\"boldAsian\":true}},\"next\":\"Normal\",\"name\":\"insertStyleSheet\",\"parent\":\"Normal\",\"type\":\"paragraph\"},{\"styleName\":\"Default Paragraph Font\",\"styleId\":\"DefaultParagraphFont\",\"attrs\":{},\"name\":\"insertStyleSheet\",\"parent\":\"default_character_style\",\"type\":\"character\"},{\"styleName\":\"Heading 2 Char\",\"styleId\":\"Heading2Char\",\"attrs\":{\"character\":{\"fontNameComplex\":\"Times New Roman\",\"fontName\":\"Calibri\",\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"fontSizeAsian\":13,\"fontSize\":13,\"fontNameAsian\":\"MS Gothic\",\"boldAsian\":true}},\"name\":\"insertStyleSheet\",\"parent\":\"DefaultParagraphFont\",\"type\":\"character\"},{\"styleName\":\"Title Char\",\"styleId\":\"TitleChar\",\"attrs\":{\"character\":{\"fontName\":\"Calibri\",\"fontNameComplex\":\"Times New Roman\",\"letterSpacing\":9,\"color\":{\"value\":\"17365D\",\"type\":\"rgb\"},\"fontSizeAsian\":26,\"fontSize\":26,\"fontNameAsian\":\"MS Gothic\"}},\"name\":\"insertStyleSheet\",\"parent\":\"DefaultParagraphFont\",\"type\":\"character\"},{\"styleName\":\"Subtitle Char\",\"styleId\":\"SubtitleChar\",\"attrs\":{\"character\":{\"italicAsian\":true,\"fontName\":\"Calibri\",\"fontNameComplex\":\"Times New Roman\",\"letterSpacing\":26,\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"italicComplex\":true,\"italic\":true,\"fontNameAsian\":\"MS Gothic\"}},\"name\":\"insertStyleSheet\",\"parent\":\"DefaultParagraphFont\",\"type\":\"character\"},{\"styleName\":\"Heading 1 Char\",\"styleId\":\"Heading1Char\",\"attrs\":{\"character\":{\"fontNameComplex\":\"Times New Roman\",\"fontName\":\"Calibri\",\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"345A8A\",\"type\":\"rgb\"},\"fontSizeAsian\":16,\"fontSize\":16,\"fontNameAsian\":\"MS Gothic\",\"boldAsian\":true}},\"name\":\"insertStyleSheet\",\"parent\":\"DefaultParagraphFont\",\"type\":\"character\"},{\"fontName\":\"Cambria\",\"attrs\":{\"family\":\"Cambria\",\"panose1\":[2,4,5,3,5,4,6,3,2,4],\"familyGeneric\":\"roman\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"MS Mincho\",\"attrs\":{\"family\":\"MS Mincho\",\"panose1\":[2,2,6,9,4,2,5,8,3,4],\"familyGeneric\":\"modern\",\"pitch\":\"fixed\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"Times New Roman\",\"attrs\":{\"family\":\"Times New Roman\",\"panose1\":[2,2,6,3,5,4,5,2,3,4],\"familyGeneric\":\"roman\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"Calibri\",\"attrs\":{\"family\":\"Calibri\",\"panose1\":[2,15,5,2,2,2,4,3,2,4],\"familyGeneric\":\"swiss\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"MS Gothic\",\"attrs\":{\"family\":\"MS Gothic\",\"panose1\":[2,11,6,9,7,2,5,8,2,4],\"familyGeneric\":\"modern\",\"pitch\":\"fixed\"},\"name\":\"insertFontDescription\"},{\"fontName\":\"Adobe Caslon Pro\",\"attrs\":{\"family\":\"Adobe Caslon Pro\",\"panose1\":[0,0,0,0,0,0,0,0,0,0],\"familyGeneric\":\"roman\",\"pitch\":\"variable\"},\"name\":\"insertFontDescription\"},{\"attrs\":{\"page\":{\"printOrientation\":\"portrait\",\"marginBottom\":2000,\"indentRight\":2499,\"height\":29704,\"indentLeft\":2499,\"marginTop\":2499,\"marginLeft\":2499,\"numberFormat\":\"1\",\"width\":20990,\"marginRight\":2499}},\"name\":\"setDocumentAttributes\"},{\"start\":[0],\"attrs\":{\"paragraph\":{\"style\":\"Title\"}},\"name\":\"insertParagraph\"},{\"text\":\"Tables\",\"start\":[0,0],\"name\":\"insertText\"},{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"Subtitle\"}},\"name\":\"insertParagraph\"},{\"text\":\"Examples\",\"start\":[1,0],\"name\":\"insertText\"},{\"start\":[2],\"attrs\":{\"paragraph\":{\"style\":\"Heading2\",\"outlineLevel\":1}},\"name\":\"insertParagraph\"},{\"text\":\"Table with different background colors (hard coded)\",\"start\":[2,0],\"name\":\"insertText\"},{\"start\":[3],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"}},\"name\":\"insertParagraph\"},{\"start\":[4],\"attrs\":{\"table\":{\"indentLeft\":0,\"marginLeft\":0,\"tableGrid\":[25,25,25,25]}},\"name\":\"insertTable\"},{\"start\":[4,0],\"attrs\":{\"row\":{\"height\":1000}},\"name\":\"insertRows\"},{\"start\":[4,0,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFF00\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,0,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"1\",\"start\":[4,0,0,0,0],\"name\":\"insertText\"},{\"start\":[4,0,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFF00\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,0,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"2\",\"start\":[4,0,1,0,0],\"name\":\"insertText\"},{\"start\":[4,0,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFF00\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,0,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"3\",\"start\":[4,0,2,0,0],\"name\":\"insertText\"},{\"start\":[4,0,3],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFF00\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,0,3,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"4\",\"start\":[4,0,3,0,0],\"name\":\"insertText\"},{\"start\":[4,1],\"attrs\":{\"row\":{\"height\":2000}},\"name\":\"insertRows\"},{\"start\":[4,1,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,1,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"5\",\"start\":[4,1,0,0,0],\"name\":\"insertText\"},{\"start\":[4,1,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,1,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"6\",\"start\":[4,1,1,0,0],\"name\":\"insertText\"},{\"start\":[4,1,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFC000\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,1,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"7\",\"start\":[4,1,2,0,0],\"name\":\"insertText\"},{\"start\":[4,1,3],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,1,3,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"8\",\"start\":[4,1,3,0,0],\"name\":\"insertText\"},{\"start\":[4,2],\"attrs\":{\"row\":{\"height\":1000}},\"name\":\"insertRows\"},{\"start\":[4,2,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,2,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"9\",\"start\":[4,2,0,0,0],\"name\":\"insertText\"},{\"start\":[4,2,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"00B0F0\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,2,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"10\",\"start\":[4,2,1,0,0],\"name\":\"insertText\"},{\"start\":[4,2,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,2,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"11\",\"start\":[4,2,2,0,0],\"name\":\"insertText\"},{\"start\":[4,2,3],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,2,3,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"12\",\"start\":[4,2,3,0,0],\"name\":\"insertText\"},{\"start\":[4,3],\"attrs\":{\"row\":{\"height\":1000}},\"name\":\"insertRows\"},{\"start\":[4,3,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,3,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"13\",\"start\":[4,3,0,0,0],\"name\":\"insertText\"},{\"start\":[4,3,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,3,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"14\",\"start\":[4,3,1,0,0],\"name\":\"insertText\"},{\"start\":[4,3,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,3,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"15\",\"start\":[4,3,2,0,0],\"name\":\"insertText\"},{\"start\":[4,3,3],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"92D050\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[4,3,3,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"16\",\"start\":[4,3,3,0,0],\"name\":\"insertText\"},{\"start\":[5],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"}},\"name\":\"insertParagraph\"},{\"start\":[6],\"attrs\":{\"paragraph\":{\"style\":\"Heading2\",\"outlineLevel\":1}},\"name\":\"insertParagraph\"},{\"text\":\"Table with different background colors (theme, toggle background)\",\"start\":[6,0],\"name\":\"insertText\"},{\"start\":[7],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"}},\"name\":\"insertParagraph\"},{\"start\":[8],\"attrs\":{\"table\":{\"indentLeft\":0,\"marginLeft\":0,\"tableGrid\":[16,17,17,17,17,16]}},\"name\":\"insertTable\"},{\"start\":[8,0],\"attrs\":{\"row\":{\"height\":677}},\"name\":\"insertRows\"},{\"start\":[8,0,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFFFF\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":106},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,0,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"Point\",\"start\":[8,0,0,0,0],\"name\":\"insertText\"},{\"start\":[8,0,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFFFF\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":106},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,0,1,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"Point A\",\"start\":[8,0,1,0,0],\"name\":\"insertText\"},{\"start\":[8,0,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFFFF\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":106},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,0,2,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"Point B\",\"start\":[8,0,2,0,0],\"name\":\"insertText\"},{\"start\":[8,0,3],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFFFF\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":106},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,0,3,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"Point C\",\"start\":[8,0,3,0,0],\"name\":\"insertText\"},{\"start\":[8,0,4],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFFFF\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":106},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,0,4,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"Point D\",\"start\":[8,0,4,0,0],\"name\":\"insertText\"},{\"start\":[8,0,5],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFFFF\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":106},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,0,5,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"Point E\",\"start\":[8,0,5,0,0],\"name\":\"insertText\"},{\"start\":[8,1],\"attrs\":{\"row\":{\"height\":677}},\"name\":\"insertRows\"},{\"start\":[8,1,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFFFF\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":35},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,1,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"Point A\",\"start\":[8,1,0,0,0],\"name\":\"insertText\"},{\"start\":[8,1,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,1,1,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"—\",\"start\":[8,1,1,0,0],\"name\":\"insertText\"},{\"start\":[8,1,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,1,2,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"start\":[8,1,3],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,1,3,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"start\":[8,1,4],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,1,4,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"start\":[8,1,5],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":35},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,1,5,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"start\":[8,2],\"attrs\":{\"row\":{\"height\":677}},\"name\":\"insertRows\"},{\"start\":[8,2,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFFFF\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":35},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,2,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"Point B\",\"start\":[8,2,0,0,0],\"name\":\"insertText\"},{\"start\":[8,2,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,2,1,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"87\",\"start\":[8,2,1,0,0],\"name\":\"insertText\"},{\"start\":[8,2,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,2,2,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"—\",\"start\":[8,2,2,0,0],\"name\":\"insertText\"},{\"start\":[8,2,3],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,2,3,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"start\":[8,2,4],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,2,4,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"start\":[8,2,5],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":35},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,2,5,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"start\":[8,3],\"attrs\":{\"row\":{\"height\":677}},\"name\":\"insertRows\"},{\"start\":[8,3,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFFFF\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":35},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,3,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"Point C\",\"start\":[8,3,0,0,0],\"name\":\"insertText\"},{\"start\":[8,3,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,3,1,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"64\",\"start\":[8,3,1,0,0],\"name\":\"insertText\"},{\"start\":[8,3,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,3,2,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"56\",\"start\":[8,3,2,0,0],\"name\":\"insertText\"},{\"start\":[8,3,3],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,3,3,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"—\",\"start\":[8,3,3,0,0],\"name\":\"insertText\"},{\"start\":[8,3,4],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,3,4,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"start\":[8,3,5],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":35},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,3,5,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"start\":[8,4],\"attrs\":{\"row\":{\"height\":677}},\"name\":\"insertRows\"},{\"start\":[8,4,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFFFF\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":35},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,4,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"Point D\",\"start\":[8,4,0,0,0],\"name\":\"insertText\"},{\"start\":[8,4,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,4,1,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"37\",\"start\":[8,4,1,0,0],\"name\":\"insertText\"},{\"start\":[8,4,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,4,2,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"32\",\"start\":[8,4,2,0,0],\"name\":\"insertText\"},{\"start\":[8,4,3],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,4,3,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"91\",\"start\":[8,4,3,0,0],\"name\":\"insertText\"},{\"start\":[8,4,4],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,4,4,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"—\",\"start\":[8,4,4,0,0],\"name\":\"insertText\"},{\"start\":[8,4,5],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":35},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,4,5,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"start\":[8,5],\"attrs\":{\"row\":{\"height\":677}},\"name\":\"insertRows\"},{\"start\":[8,5,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"FFFFFF\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":35},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,5,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"Point E\",\"start\":[8,5,0,0,0],\"name\":\"insertText\"},{\"start\":[8,5,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,5,1,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"93\",\"start\":[8,5,1,0,0],\"name\":\"insertText\"},{\"start\":[8,5,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,5,2,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"35\",\"start\":[8,5,2,0,0],\"name\":\"insertText\"},{\"start\":[8,5,3],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,5,3,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"54\",\"start\":[8,5,3,0,0],\"name\":\"insertText\"},{\"start\":[8,5,4],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,5,4,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"43\",\"start\":[8,5,4,0,0],\"name\":\"insertText\"},{\"start\":[8,5,5],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"D3DFEE\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"4F81BD\",\"type\":\"rgb\"},\"width\":35},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[8,5,5,0],\"attrs\":{\"paragraph\":{\"alignment\":\"center\",\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11}},\"name\":\"insertParagraph\"},{\"text\":\"—\",\"start\":[8,5,5,0,0],\"name\":\"insertText\"},{\"start\":[9],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"}},\"name\":\"insertParagraph\"},{\"start\":[10],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"}},\"name\":\"insertParagraph\"},{\"start\":[11],\"attrs\":{\"paragraph\":{\"style\":\"Heading2\",\"outlineLevel\":1}},\"name\":\"insertParagraph\"},{\"text\":\"Thick table border, but cell can overrule table border\",\"start\":[11,0],\"name\":\"insertText\"},{\"start\":[12],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"}},\"name\":\"insertParagraph\"},{\"start\":[13],\"attrs\":{\"table\":{\"indentLeft\":0,\"marginLeft\":0,\"tableGrid\":[33,33,33]}},\"name\":\"insertTable\"},{\"start\":[13,0],\"attrs\":{\"row\":{\"height\":1000}},\"name\":\"insertRows\"},{\"start\":[13,0,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[13,0,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[13,0,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[13,0,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[13,0,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[13,0,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[13,1],\"attrs\":{\"row\":{\"height\":1000}},\"name\":\"insertRows\"},{\"start\":[13,1,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[13,1,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[13,1,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[13,1,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[13,1,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[13,1,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[13,2],\"attrs\":{\"row\":{\"height\":1000}},\"name\":\"insertRows\"},{\"start\":[13,2,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[13,2,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[13,2,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[13,2,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[13,2,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":26},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[13,2,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[14],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"}},\"name\":\"insertParagraph\"},{\"start\":[15],\"attrs\":{\"paragraph\":{\"style\":\"Heading2\",\"outlineLevel\":1}},\"name\":\"insertParagraph\"},{\"text\":\"Table with background color for top-left, top-right, bottom-left and bottom-right cell\",\"start\":[15,0],\"name\":\"insertText\"},{\"start\":[16],\"attrs\":{\"table\":{\"indentLeft\":0,\"marginLeft\":0,\"tableGrid\":[33,33,33]}},\"name\":\"insertTable\"},{\"start\":[16,0],\"attrs\":{\"row\":{\"height\":1501}},\"name\":\"insertRows\"},{\"start\":[16,0,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"548DD4\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":35},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":35},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[16,0,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"bold\":true,\"fontName\":\"Adobe Caslon Pro\",\"boldComplex\":true,\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"boldAsian\":true,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"Erste Ecke oben links\",\"start\":[16,0,0,0,0],\"name\":\"insertText\"},{\"start\":[16,0,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":35},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":35},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[16,0,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"boldAsian\":true,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"1\",\"start\":[16,0,1,0,0],\"name\":\"insertText\"},{\"start\":[16,0,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":35},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":35},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[16,0,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"boldAsian\":true,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"2\",\"start\":[16,0,2,0,0],\"name\":\"insertText\"},{\"start\":[16,1],\"attrs\":{\"row\":{\"height\":1501}},\"name\":\"insertRows\"},{\"start\":[16,1,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"C0C0C0\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[16,1,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"boldAsian\":true,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"Zweite Ecke oben links\",\"start\":[16,1,0,0,0],\"name\":\"insertText\"},{\"start\":[16,1,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"C0C0C0\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[16,1,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"3\",\"start\":[16,1,1,0,0],\"name\":\"insertText\"},{\"start\":[16,1,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"C0C0C0\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"none\"},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[16,1,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"4\",\"start\":[16,1,2,0,0],\"name\":\"insertText\"},{\"start\":[16,2],\"attrs\":{\"row\":{\"height\":1501}},\"name\":\"insertRows\"},{\"start\":[16,2,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":35},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[16,2,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"bold\":true,\"boldComplex\":true,\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"boldAsian\":true,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"Dritte Ecke oben links\",\"start\":[16,2,0,0,0],\"name\":\"insertText\"},{\"start\":[16,2,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":35},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[16,2,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"5\",\"start\":[16,2,1,0,0],\"name\":\"insertText\"},{\"start\":[16,2,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"none\"},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":35},\"borderRight\":{\"style\":\"none\"},\"borderLeft\":{\"style\":\"none\"},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[16,2,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"6\",\"start\":[16,2,2,0,0],\"name\":\"insertText\"},{\"start\":[17],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"}},\"name\":\"insertParagraph\"},{\"start\":[18],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"}},\"name\":\"insertParagraph\"},{\"start\":[19],\"attrs\":{\"paragraph\":{\"style\":\"Heading2\",\"outlineLevel\":1}},\"name\":\"insertParagraph\"},{\"text\":\"Funny Table\",\"start\":[19,0],\"name\":\"insertText\"},{\"start\":[20],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"}},\"name\":\"insertParagraph\"},{\"start\":[21],\"attrs\":{\"table\":{\"indentLeft\":0,\"marginLeft\":0,\"tableGrid\":[25,25,25,25]}},\"name\":\"insertTable\"},{\"start\":[21,0],\"attrs\":{\"row\":{\"height\":4001}},\"name\":\"insertRows\"},{\"start\":[21,0,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"columnSpan\":\"2\",\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,0,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"A big cell with thick border\",\"start\":[21,0,0,0,0],\"name\":\"insertText\"},{\"start\":[21,0,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,0,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fillColor\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"Para1\",\"start\":[21,0,1,0,0],\"name\":\"insertText\"},{\"start\":[21,0,1,1],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fillColor\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"Para2\",\"start\":[21,0,1,1,0],\"name\":\"insertText\"},{\"start\":[21,0,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,0,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"Hello world!\",\"start\":[21,0,2,0,0],\"name\":\"insertText\"},{\"start\":[21,1],\"attrs\":{\"row\":{\"height\":2501}},\"name\":\"insertRows\"},{\"start\":[21,1,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,1,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[21,1,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"00B050\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"columnSpan\":\"2\",\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,1,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"bold\":true,\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"boldAsian\":true,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"Cell background is green.\",\"start\":[21,1,1,0,0],\"name\":\"insertText\"},{\"start\":[21,1,1,1],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[21,1,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,1,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"bold\":true,\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"boldAsian\":true,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[21,2],\"name\":\"insertRows\"},{\"start\":[21,2,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"width\":212},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,2,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[21,2,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"width\":212},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"width\":212},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"width\":212},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"width\":212},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,2,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"bold\":true,\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"boldAsian\":true,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"Thick red border\",\"start\":[21,2,1,0,0],\"name\":\"insertText\"},{\"start\":[21,2,1,1],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"bold\":true,\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"boldAsian\":true,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[21,2,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"width\":212},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,2,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[21,2,3],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,2,3,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"bold\":true,\"fontSizeAsian\":11,\"language\":\"de-DE\",\"fontSize\":11,\"boldAsian\":true,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[21,3],\"name\":\"insertRows\"},{\"start\":[21,3,0],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,3,0,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"Thick border at bottom of 3 cells\",\"start\":[21,3,0,0,0],\"name\":\"insertText\"},{\"start\":[21,3,1],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"FF0000\",\"type\":\"rgb\"},\"width\":212},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,3,1,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[21,3,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":212},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,3,2,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"fontSizeAsian\":11,\"fontSize\":11,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"start\":[21,3,3],\"attrs\":{\"cell\":{\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":159},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\"},{\"start\":[21,3,3,0],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"},\"character\":{\"bold\":true,\"fontSizeAsian\":11,\"fontSize\":11,\"boldAsian\":true,\"fontNameAsian\":\"Cambria\"}},\"name\":\"insertParagraph\"},{\"text\":\"Normal border here...\",\"start\":[21,3,3,0,0],\"name\":\"insertText\"},{\"start\":[22],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"}},\"name\":\"insertParagraph\"},{\"start\":[23],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"}},\"name\":\"insertParagraph\"},{\"start\":[24],\"attrs\":{\"paragraph\":{\"style\":\"Normal\"}},\"name\":\"insertParagraph\"}"
            + "]";

        String changeOps = "["
            + "{\"startGrid\":0,\"start\":[4],\"name\":\"deleteColumns\",\"endGrid\":0},{\"start\":[4],\"attrs\":{\"table\":{\"tableGrid\":[25,25,25]}},\"name\":\"setAttributes\"}"
            + "]";

        editOperations.add(firstEditOperations);
        editOperations.add(changeOps);

        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    // Verfied: Does it works to delete a hard break within a text container?
    public void editingDeletionTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty_as_can_be";
        String firstEditOperations = "["
            + "{\"start\":[0],\"name\":\"insertParagraph\"},"
            + "{\"start\":[0],\"name\":\"delete\",\"end\":[0]},"
            + "{\"start\":[0],\"name\":\"insertParagraph\"},"
            + "{\"text\":\"heading1\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"styleId\":\"heading1\",\"styleName\":\"heading 1\",\"attrs\":{\"paragraph\":{\"outlineLevel\":0},\"next\":\"default_paragraph_style\",\"character\":{\"bold\":true,\"color\":{\"value\":\"accent1\",\"fallbackValue\":\"376092\",\"type\":\"scheme\",\"transformations\":[{\"value\":74902,\"type\":\"shade\"}]},\"fontSize\":14}},\"name\":\"insertStyleSheet\",\"parent\":\"default_paragraph_style\",\"uiPriority\":9,\"type\":\"paragraph\"},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"listStyleId\":null,\"listLevel\":null,\"borderBottom\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"showListLabel\":null,\"marginBottom\":null,\"tabStops\":null,\"listStartValue\":null,\"indentRight\":null,\"indentLeft\":null,\"style\":\"heading1\",\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\"},"
            + "{\"start\":[0,8],\"name\":\"splitParagraph\"},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"default_paragraph_style\"}},\"name\":\"setAttributes\"},"
            + "{\"text\":\"text\",\"start\":[1,0],\"name\":\"insertText\"}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    public void editingTextAfterHeadingTestTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty_as_can_be";
        String firstEditOperations = "["
            + "{\"start\":[0],\"name\":\"insertParagraph\"},"
            + "{\"text\":\"heading1\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"styleId\":\"heading1\",\"styleName\":\"heading 1\",\"attrs\":{\"paragraph\":{\"outlineLevel\":0},\"next\":\"default_paragraph_style\",\"character\":{\"bold\":true,\"color\":{\"value\":\"accent1\",\"fallbackValue\":\"376092\",\"type\":\"scheme\",\"transformations\":[{\"value\":74902,\"type\":\"shade\"}]},\"fontSize\":14}},\"name\":\"insertStyleSheet\",\"parent\":\"default_paragraph_style\",\"uiPriority\":9,\"type\":\"paragraph\"},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"listStyleId\":null,\"listLevel\":null,\"borderBottom\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"showListLabel\":null,\"marginBottom\":null,\"tabStops\":null,\"listStartValue\":null,\"indentRight\":null,\"indentLeft\":null,\"style\":\"heading1\",\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\"},"
            + "{\"start\":[0,8],\"name\":\"splitParagraph\"},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"default_paragraph_style\"}},\"name\":\"setAttributes\"},"
            + "{\"start\":[1,0],\"name\":\"splitParagraph\"},"
            + "{\"text\":\"text\",\"start\":[2,0],\"name\":\"insertText\"}]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    public void addingNullTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty_as_can_be";
        String firstEditOperations = "["
            + "{\"start\":[0],\"name\":\"insertParagraph\"},"
            + "{\"text\":\"Überschrift\",\"start\":[0,0],\"name\":\"insertText\"},"
            + "{\"styleId\":\"heading1\",\"styleName\":\"heading 1\",\"attrs\":{\"paragraph\":{\"outlineLevel\":0},\"next\":\"default_paragraph_style\",\"character\":{\"bold\":true,\"color\":{\"value\":\"accent1\",\"fallbackValue\":\"376092\",\"type\":\"scheme\",\"transformations\":[{\"value\":74902,\"type\":\"shade\"}]},\"fontSize\":14}},\"name\":\"insertStyleSheet\",\"parent\":\"default_paragraph_style\",\"uiPriority\":9,\"type\":\"paragraph\"},"
            + "{\"start\":[0],\"attrs\":{\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"listStyleId\":null,\"listLevel\":null,\"borderBottom\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"showListLabel\":null,\"marginBottom\":null,\"tabStops\":null,\"listStartValue\":null,\"indentRight\":null,\"indentLeft\":null,\"style\":\"heading1\",\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\"},"
            + "{\"start\":[0,11],\"name\":\"splitParagraph\"},"
            + "{\"start\":[1],\"attrs\":{\"paragraph\":{\"style\":\"default_paragraph_style\"}},\"name\":\"setAttributes\"},"
            + "{\"start\":[1,0],\"name\":\"splitParagraph\"},"
            + "{\"text\":\"Standard\",\"start\":[2,0],\"name\":\"insertText\"},"
            + "{\"start\":[2],\"attrs\":{\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"listStyleId\":null,\"listLevel\":null,\"borderBottom\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"showListLabel\":null,\"marginBottom\":null,\"tabStops\":null,\"listStartValue\":null,\"indentRight\":null,\"indentLeft\":null,\"style\":\"Standard\",\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\"},"
            + "{\"start\":[2,8],\"name\":\"splitParagraph\"},"
            + "{\"start\":[3,0],\"name\":\"splitParagraph\"},"
            + "{\"text\":\"Überschrift2\",\"start\":[4,0],\"name\":\"insertText\"},"
            + "{\"styleId\":\"heading2\",\"styleName\":\"heading 2\",\"attrs\":{\"paragraph\":{\"outlineLevel\":1},\"next\":\"default_paragraph_style\",\"character\":{\"bold\":true,\"color\":{\"value\":\"accent1\",\"fallbackValue\":\"4F81BD\",\"type\":\"scheme\"},\"fontSize\":13}},\"name\":\"insertStyleSheet\",\"parent\":\"default_paragraph_style\",\"uiPriority\":9,\"type\":\"paragraph\"},"
            + "{\"start\":[4],\"attrs\":{\"paragraph\":{\"alignment\":null,\"lineHeight\":null,\"fillColor\":null,\"marginTop\":null,\"outlineLevel\":null,\"listStyleId\":null,\"listLevel\":null,\"borderBottom\":null,\"contextualSpacing\":null,\"borderLeft\":null,\"showListLabel\":null,\"marginBottom\":null,\"tabStops\":null,\"listStartValue\":null,\"indentRight\":null,\"indentLeft\":null,\"style\":\"heading2\",\"borderTop\":null,\"borderInside\":null,\"indentFirstLine\":null,\"borderRight\":null},\"character\":{\"vertAlign\":null,\"fontName\":null,\"bold\":null,\"strike\":null,\"fillColor\":null,\"color\":null,\"underline\":null,\"italic\":null,\"language\":null,\"fontSize\":null,\"url\":null}},\"name\":\"setAttributes\"},"
            + "{\"text\":\"1\",\"start\":[0,11],\"name\":\"insertText\"}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    public void borderImportTest() {
        final String SOURCE_FILE_NAME_TRUNC = "Tabelle1";
        final String firstEditOperations
            = "["
            + "{\"name\":\"insertText\",\"text\":\"NewText\",\"start\":[0,0]},"
            + "{\"name\":\"setAttributes\",\"start\":[0],\"attrs\":{"
            + "\"paragraph\":{"
            + "\"borderLeft\":{\"style\":\"single\",\"width\":17,\"space\":140,\"color\":{\"type\":\"auto\"}},"
            + "\"borderRight\":{\"style\":\"single\",\"width\":17,\"space\":140,\"color\":{\"type\":\"auto\"}},"
            + "\"borderTop\":{\"style\":\"single\"},"
            + "\"borderBottom\":{\"style\":\"none\"},"
            + "\"borderInside\":{\"style\":\"none\"}},"
            + "\"character\":{"
            + "\"color\":{\"type\":\"auto\"},"
            + "\"fillColor\":{\"type\":\"rgb\",\"value\":\"FF00FF\"}}}"
            + "},"
            //				+ "{\"name\":\"setAttributes\",\"start\":[0],\"attrs\":{"
            //				+"\"character\":{"
            //					+"\"color\":{\"type\":\"rgb\",\"value\":\"000000\"}}}"
            //				+ "}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Applying optimal column width test
     */
    public void optimalColumnWidthTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"start\":[0,0],\"contents\":[[{\"ref\":null,\"attrs\":{\"cell\":{\"numberFormat\":{\"id\":0,\"code\":\"\"}}},\"value\":5}]],\"opl\":1,\"name\":\"setCellContents\",\"sheet\":0,\"osn\":9},"
            + "{\"visibleBorders\":null,\"start\":[0],\"attrs\":{\"cell\":null,\"row\":{\"customHeight\":false,\"height\":466,\"visible\":true},\"character\":null},\"opl\":1,\"name\":\"setRowAttributes\",\"rangeBorders\":null,\"sheet\":0,\"end\":null,\"osn\":10},"
            + "{\"visibleBorders\":null,\"start\":[0],\"attrs\":{\"cell\":null,\"column\":{\"customWidth\":false,\"visible\":true,\"width\":339},\"character\":null},\"opl\":1,\"name\":\"setColumnAttributes\",\"rangeBorders\":null,\"sheet\":0,\"end\":null,\"osn\":11}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Changing the font size in spreadsheet
     */
    public void formatFontTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"visibleBorders\":null,\"ref\":null,\"start\":[0,0],\"attrs\":{\"cell\":null,\"row\":null,\"character\":{\"fontSize\":26}},\"opl\":1,\"name\":\"fillCellRange\",\"rangeBorders\":null,\"parse\":null,\"sheet\":0,\"end\":null,\"osn\":9},"
            + "{\"visibleBorders\":null,\"start\":[0],\"attrs\":{\"cell\":null,\"row\":{\"customHeight\":false,\"height\":1037,\"visible\":true},\"character\":null},\"opl\":1,\"name\":\"setRowAttributes\",\"rangeBorders\":null,\"sheet\":0,\"end\":null,\"osn\":10},"
            + "{\"visibleBorders\":null,\"ref\":null,\"start\":[0,0],\"attrs\":{\"cell\":null,\"row\":null,\"character\":{\"fontSize\":26}},\"opl\":1,\"name\":\"fillCellRange\",\"rangeBorders\":null,\"parse\":null,\"sheet\":0,\"end\":null,\"osn\":11},"
            + "{\"visibleBorders\":null,\"start\":[0],\"attrs\":{\"cell\":null,\"row\":{\"customHeight\":false,\"height\":1037,\"visible\":true},\"character\":null},\"opl\":1,\"name\":\"setRowAttributes\",\"rangeBorders\":null,\"sheet\":0,\"end\":null,\"osn\":12},"
            + "{\"start\":[0,0],\"contents\":[[{\"ref\":null,\"attrs\":{\"cell\":{\"numberFormat\":{\"id\":0,\"code\":\"\"}}},\"value\":5}]],\"opl\":1,\"name\":\"setCellContents\",\"sheet\":0,\"osn\":13},"
            + "{\"visibleBorders\":null,\"start\":[0],\"attrs\":{\"cell\":null,\"row\":{\"customHeight\":false,\"height\":1058,\"visible\":true},\"character\":null},\"opl\":1,\"name\":\"setRowAttributes\",\"rangeBorders\":null,\"sheet\":0,\"end\":null,\"osn\":14}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore // 2DO Svante - Roundtrip broken as spreadsheet is always maximized, which causes problems. Perhaps always normalizing to full table in import or status attributes at table?
    /**
     * Adding red to first column and yellow to first row.
     */
    public void formatColumnAndRowTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"name\":\"setColumnAttributes\",\"visibleBorders\":null,\"start\":[0],\"attrs\":{\"cell\":{\"fillColor\":{\"value\":\"FF000A\",\"type\":\"rgb\"}},\"column\":{\"width\":1234},\"character\":null},\"opl\":1,\"rangeBorders\":null,\"sheet\":0,\"end\":null,\"osn\":9},"
            + "{\"name\":\"setColumnAttributes\",\"visibleBorders\":null,\"start\":[3],\"attrs\":{\"cell\":{\"fillColor\":{\"value\":\"FF000B\",\"type\":\"rgb\"}},\"column\":{\"width\":4321},\"character\":null},\"opl\":1,\"rangeBorders\":null,\"sheet\":0,\"end\":7,\"osn\":9},"
            + "{\"name\":\"setRowAttributes\",\"visibleBorders\":null,\"start\":[1],\"attrs\":{\"cell\":{\"fillColor\":{\"value\":\"FFC00C\",\"type\":\"rgb\"}},\"row\":{\"customHeight\":false,\"height\":1234,\"visible\":true},\"character\":null},\"opl\":1,\"rangeBorders\":null,\"sheet\":0,\"end\":null,\"osn\":10},"
            + "{\"name\":\"setRowAttributes\",\"visibleBorders\":null,\"start\":[3],\"attrs\":{\"cell\":{\"fillColor\":{\"value\":\"FFC00D\",\"type\":\"rgb\"}},\"row\":{\"customHeight\":false,\"height\":4321,\"visible\":true},\"character\":null},\"opl\":1,\"rangeBorders\":null,\"sheet\":0,\"end\":7,\"osn\":10}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore // Roundtrip broken as spreadsheet is always maximized, which causes problems. Perhaps always normalizing to full table in import or status attributes at table?
    /**
     * Format column/row width & color on a complex spreadsheet.
     */
    public void complexFormatTest() {
        final String SOURCE_FILE_NAME_TRUNC = "formula_import_xml_12_LO42";
        String firstEditOperations = "["
            + "{\"visibleBorders\":null,\"start\":22,\"attrs\":{\"cell\":{\"fillColor\":{\"value\":\"CCCCCC\",\"type\":\"rgb\"}},\"column\":null,\"character\":null},\"opl\":1,\"name\":\"setColumnAttributes\",\"rangeBorders\":null,\"sheet\":0,\"end\":null,\"osn\":467},"
            + "{\"start\":[0],\"attrs\":{\"cell\":{\"fillColor\":{\"value\":\"FFEE00\",\"type\":\"rgb\"}},\"row\":{\"customFormat\":true},\"character\":null},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":466},"
            + "{\"visibleBorders\":null,\"start\":2,\"attrs\":{\"cell\":{\"fillColor\":{\"value\":\"FF0000\",\"type\":\"rgb\"}},\"column\":null,\"character\":null},\"opl\":1,\"name\":\"setColumnAttributes\",\"rangeBorders\":null,\"sheet\":0,\"end\":null,\"osn\":467},"
            + "{\"visibleBorders\":null,\"start\":4,\"attrs\":{\"cell\":{\"fillColor\":{\"value\":\"FFFF00\",\"type\":\"rgb\"}},\"column\":null,\"character\":null},\"opl\":1,\"name\":\"setColumnAttributes\",\"rangeBorders\":null,\"sheet\":0,\"end\":null,\"osn\":468},"
            + "{\"visibleBorders\":null,\"start\":4,\"attrs\":{\"cell\":null,\"column\":{\"customWidth\":true,\"visible\":true,\"width\":2117},\"character\":null},\"opl\":1,\"name\":\"setColumnAttributes\",\"rangeBorders\":null,\"sheet\":0,\"end\":null,\"osn\":469},"
            + "{\"start\":[0],\"attrs\":{\"cell\":null,\"row\":{\"customHeight\":true,\"height\":1460,\"visible\":true},\"character\":null},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":470},"
            + "{\"start\":[5],\"attrs\":{\"row\":{\"visible\":false}},\"opl\":1,\"name\":\"setRowAttributes\",\"sheet\":0,\"osn\":470},"
            + "{\"start\":[5],\"attrs\":{\"column\":{\"visible\":false}},\"opl\":1,\"name\":\"setColumnAttributes\",\"sheet\":0,\"osn\":470},"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore // 2DO Svante: validation problem
    /**
     * Insert a row and column and undo them again.
     */
    public void deleteRowColumnUndoTest() {
        final String SOURCE_FILE_NAME_TRUNC = "simple-table";
        String firstEditOperations = "["
            + "{\"count\":1,\"start\":[0,1],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":false,\"referenceRow\":0,\"osn\":14},"
            + "{\"start\":[0,1,0,0],\"attrs\":{\"styleId\":\"Standard\",\"paragraph\":{\"marginBottom\":0,\"lineHeight\":{\"value\":100,\"type\":\"percent\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":15},"
            + "{\"insertMode\":\"behind\",\"start\":[0],\"name\":\"insertColumn\",\"tableGrid\":[50,50],\"opl\":1,\"gridPosition\":0,\"osn\":16},"
            + "{\"start\":[0],\"attrs\":{\"table\":{\"tableGrid\":[50,50]}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":17},"
            + "{\"start\":[0,0,1,0],\"attrs\":{\"styleId\":\"Standard\",\"paragraph\":{\"marginBottom\":0,\"lineHeight\":{\"value\":100,\"type\":\"percent\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":18},"
            + "{\"start\":[0,1,1,0],\"attrs\":{\"styleId\":\"Standard\",\"paragraph\":{\"marginBottom\":0,\"lineHeight\":{\"value\":100,\"type\":\"percent\"}}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":19},"
            + "{\"start\":[0,1,1,0],\"name\":\"delete\",\"opl\":1,\"osn\":20},"
            + "{\"start\":[0,0,1,0],\"name\":\"delete\",\"opl\":1,\"osn\":21},"
            + "{\"start\":[0],\"attrs\":{\"table\":{\"tableGrid\":[100]}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0],\"osn\":22},"
            + "{\"start\":[0,0,1],\"name\":\"delete\",\"opl\":1,\"osn\":23},"
            + "{\"start\":[0,1,1],\"name\":\"delete\",\"opl\":1,\"osn\":24},"
            + "{\"start\":[0,1,0,0],\"name\":\"delete\",\"opl\":1,\"osn\":25},"
            + "{\"start\":[0,1],\"name\":\"delete\",\"opl\":1,\"osn\":26}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Delete a column and restore it again.
     */
    public void insertColumnUndoTest() {
        final String SOURCE_FILE_NAME_TRUNC = "simple-table-with-lists";
        String firstEditOperations = "["
            + "{\"startGrid\":1,\"start\":[1],\"name\":\"deleteColumns\",\"opl\":1,\"endGrid\":1,\"osn\":51},"
            + "{\"start\":[1],\"attrs\":{\"table\":{\"tableGrid\":[1000,1000]}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":52},"
            + "{\"start\":[1],\"attrs\":{\"table\":{\"tableGrid\":[1000,1000,1000]}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1],\"osn\":53},"
            + "{\"count\":1,\"start\":[1,0,1],\"attrs\":{\"cell\":{\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":2}}},\"name\":\"insertCells\",\"opl\":1,\"osn\":54},"
            + "{\"start\":[1,0,1,0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":55},"
            + "{\"text\":\"ADSSAOUD\",\"start\":[1,0,1,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":56},"
            + "{\"start\":[1,0,1,0,0],\"attrs\":{\"character\":{\"fillColor\":{\"value\":\"00B050\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[1,0,1,0,7],\"osn\":57},"
            + "{\"start\":[1,0,1,1],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":58},"
            + "{\"text\":\"ASDASD\",\"start\":[1,0,1,1,0],\"opl\":1,\"name\":\"insertText\",\"osn\":59},"
            + "{\"start\":[1,0,1,2],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":60},"
            + "{\"text\":\"ASD\",\"start\":[1,0,1,2,0],\"opl\":1,\"name\":\"insertText\",\"osn\":61},"
            + "{\"start\":[1,0,1,3],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":62},"
            + "{\"text\":\"ASD\",\"start\":[1,0,1,3,0],\"opl\":1,\"name\":\"insertText\",\"osn\":63},"
            + "{\"start\":[1,0,1,4],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":64},"
            + "{\"text\":\"ASD\",\"start\":[1,0,1,4,0],\"opl\":1,\"name\":\"insertText\",\"osn\":65},"
            + "{\"start\":[1,0,1,5],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":66},"
            + "{\"text\":\"ASD\",\"start\":[1,0,1,5,0],\"opl\":1,\"name\":\"insertText\",\"osn\":67},"
            + "{\"start\":[1,0,1,6],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":68},"
            + "{\"text\":\"ASD\",\"start\":[1,0,1,6,0],\"opl\":1,\"name\":\"insertText\",\"osn\":69},"
            + "{\"start\":[1,0,1,7],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":70},"
            + "{\"text\":\"ASD\",\"start\":[1,0,1,7,0],\"opl\":1,\"name\":\"insertText\",\"osn\":71},"
            + "{\"start\":[1,0,1,8],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":72},"
            + "{\"text\":\"ASD\",\"start\":[1,0,1,8,0],\"opl\":1,\"name\":\"insertText\",\"osn\":73},"
            + "{\"start\":[1,0,1,9],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\",\"listLevel\":0}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":74}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Delete a column and restore it again.
     */
    public void deleteColumnUndoTest() {
        final String SOURCE_FILE_NAME_TRUNC = "coloredTable_MSO15";
        String firstEditOperations = "["
            + "{\"startGrid\":2,\"start\":[0],\"name\":\"deleteColumns\",\"opl\":1,\"endGrid\":2,\"osn\":28},"
            + "{\"start\":[0],\"attrs\":{\"table\":{\"tableGrid\":[5,23]}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":29},"
            + "{\"start\":[0],\"attrs\":{\"table\":{\"tableGrid\":[5,23,72]}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0],\"osn\":30},"
            + "{\"count\":1,\"start\":[0,0,2],\"attrs\":{\"cell\":{\"paddingTop\":0,\"fillColor\":{\"value\":\"ED7D31\",\"type\":\"rgb\"},\"borderTop\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderBottom\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderRight\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"borderLeft\":{\"style\":\"single\",\"color\":{\"value\":\"000000\",\"type\":\"rgb\"},\"width\":18},\"paddingBottom\":0,\"paddingLeft\":191,\"paddingRight\":191}},\"name\":\"insertCells\",\"opl\":1,\"osn\":31},"
            + "{\"start\":[0,0,2,0],\"attrs\":{\"styleId\":\"Normal\",\"paragraph\":{\"marginBottom\":0,\"lineHeight\":{\"value\":100,\"type\":\"percent\"}},\"character\":{\"color\":{\"value\":\"7030A0\",\"type\":\"rgb\"},\"underline\":true}},\"name\":\"insertParagraph\",\"opl\":1,\"osn\":32},"
            + "{\"text\":\"3\",\"start\":[0,0,2,0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":33}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Changing the column size of a text table.
     */
    public void changeColumnWidthTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"styleId\":\"TableGrid\",\"styleName\":\"Table Grid\",\"attrs\":{\"wholeTable\":{\"paragraph\":{\"lineHeight\":{\"value\":100,\"type\":\"percent\"}},\"table\":{\"borderInsideVert\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderInsideHor\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderBottom\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderLeft\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderRight\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingBottom\":0,\"paddingLeft\":190,\"paddingRight\":190}}},\"name\":\"insertStyleSheet\",\"opl\":1,\"uiPriority\":59,\"type\":\"table\",\"osn\":17},"
            + "{\"start\":[1],\"attrs\":{\"styleId\":\"TableGrid\",\"table\":{\"tableGrid\":[1000,1000,1000],\"width\":\"auto\",\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":18},"
            + "{\"count\":3,\"start\":[1,0],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":true,\"osn\":19},"
            + "{\"start\":[1],\"attrs\":{\"table\":{\"tableGrid\":[1002,1484,519],\"width\":\"auto\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":20}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Changing the text flow around an image.
     */
    public void changeImageTextFlowTest() {
        final String SOURCE_FILE_NAME_TRUNC = "imageWithTextFlow";
        String firstEditOperations = "["
            + "{\"start\":[0,537],\"attrs\":{\"drawing\":{\"anchorVertOffset\":0,\"anchorHorBase\":\"column\",\"anchorHorOffset\":0,\"textWrapMode\":\"square\",\"inline\":false,\"anchorHorAlign\":\"left\",\"textWrapSide\":\"right\",\"anchorVertBase\":\"paragraph\"}},\"name\":\"setAttributes\",\"opl\":1,\"osn\":21}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Coloring text with and without existing format properties.
     */
    public void listColoringTest() {
        final String SOURCE_FILE_NAME_TRUNC = "coloredParagraph";
        String firstEditOperations = "["
            + "{\"start\":[0,0],\"attrs\":{\"character\":{\"color\":{\"value\":\"7030A0\",\"type\":\"rgb\"}}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0,2],\"osn\":36},"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    public void splitParagraphWithPageStyle() {
        final String SOURCE_FILE_NAME_TRUNC = "paragraphWithPageStyle";

        String firstEditOperations = "["
            + "{\"name\":\"splitParagraph\",\"start\":[0,2],\"osn\":40,\"opl\":1}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);;
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * If no width is given for a table a default relative width of 100% should
     * be given for MSO15.
     */
    public void newTableWidthTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"styleId\":\"TableGrid\",\"styleName\":\"Table Grid\",\"attrs\":{\"wholeTable\":{\"paragraph\":{\"lineHeight\":{\"value\":100,\"type\":\"percent\"}},\"table\":{\"borderInsideVert\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingTop\":0,\"borderTop\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderInsideHor\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderBottom\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderLeft\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"borderRight\":{\"style\":\"single\",\"color\":{\"type\":\"auto\"},\"width\":17},\"paddingBottom\":0,\"paddingLeft\":190,\"paddingRight\":190}}},\"name\":\"insertStyleSheet\",\"opl\":1,\"uiPriority\":59,\"type\":\"table\",\"osn\":16},"
            + "{\"start\":[1],\"attrs\":{\"styleId\":\"TableGrid\",\"table\":{\"tableGrid\":[1000],\"width\":\"auto\",\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]}},\"name\":\"insertTable\",\"opl\":1,\"osn\":17},"
            + "{\"count\":1,\"start\":[1,0],\"name\":\"insertRows\",\"opl\":1,\"insertDefaultCells\":true,\"osn\":18}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * ODT - changing column width: after closing/re-edit the table
     * is set to 100%
     */
    public void setTableColumnWidthWithRelativeTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"name\":\"insertStyleSheet\",\"attrs\":{\"wholeTable\":{\"paragraph\":{\"lineHeight\":{\"type\":\"percent\",\"value\":100}},\"table\":{\"borderTop\":{\"color\":{\"type\":\"auto\"},\"width\":17,\"style\":\"single\"},\"borderBottom\":{\"color\":{\"type\":\"auto\"},\"width\":17,\"style\":\"single\"},\"borderInsideHor\":{\"color\":{\"type\":\"auto\"},\"width\":17,\"style\":\"single\"},\"borderInsideVert\":{\"color\":{\"type\":\"auto\"},\"width\":17,\"style\":\"single\"},\"borderLeft\":{\"color\":{\"type\":\"auto\"},\"width\":17,\"style\":\"single\"},\"borderRight\":{\"color\":{\"type\":\"auto\"},\"width\":17,\"style\":\"single\"},\"paddingBottom\":0,\"paddingTop\":0,\"paddingLeft\":190,\"paddingRight\":190}}},\"type\":\"table\",\"styleId\":\"TableGrid\",\"styleName\":\"Table Grid\",\"uiPriority\":59,\"osn\":16,\"opl\":1},"
            + "{\"name\":\"insertTable\",\"start\":[1],\"attrs\":{\"table\":{\"tableGrid\":[1000,1000],\"width\":\"auto\",\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]},\"styleId\":\"TableGrid\"},\"osn\":17,\"opl\":1},"
            + "{\"name\":\"insertRows\",\"start\":[1,0],\"count\":2,\"insertDefaultCells\":true,\"osn\":18,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"attrs\":{\"table\":{\"tableGrid\":[1324,680],\"width\":13309}},\"start\":[1],\"osn\":19,\"opl\":1}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Inserting columns
     */
    public void columnInsertionTest() {
        final String SOURCE_FILE_NAME_TRUNC = "Insert and Delete Columns_MSO15";
        String firstEditOperations = "["
            + "{\"start\":3,\"name\":\"insertColumns\",\"end\":5,\"sheet\":0,\"osn\":135},"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    /* Align an image to char:
     */
    public void imageAlignParagraph() {
        final String SOURCE_FILE_NAME_TRUNC = "imageAsChar";
        String firstEditOperations = "["
            + "{\"name\":\"setAttributes\",\"start\":[0,537],\"attrs\":{\"drawing\":{\"inline\":false,\"anchorHorBase\":\"column\",\"anchorHorAlign\":\"left\",\"anchorHorOffset\":0,\"textWrapMode\":\"square\",\"textWrapSide\":\"right\",\"anchorVertBase\":\"paragraph\",\"anchorVertOffset\":1799}},\"osn\":21,\"opl\":1},"
            + "]";
        List<String> editOperations = new ArrayList();
        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Inserting and Deleting rows and columns
     */
    public void rowInsertionATest() {
        final String SOURCE_FILE_NAME_TRUNC = "Insert and Delete Rows2";
        String firstEditOperations = "["
            + "{\"start\":12,\"name\":\"insertRows\",\"opl\":1,\"end\":14,\"sheet\":0,\"osn\":135},"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    /* Align an image to char:
     */
    public void imageAlignChar() {
        final String SOURCE_FILE_NAME_TRUNC = "imageAsChar";
        String firstEditOperations = "["
            + "{\"name\":\"setAttributes\",\"start\":[0,537],\"attrs\":{\"drawing\":{\"inline\":false,\"anchorHorBase\":\"column\",\"anchorHorAlign\":\"left\",\"anchorHorOffset\":0,\"textWrapMode\":\"square\",\"textWrapSide\":\"right\",\"anchorVertBase\":\"paragraph\",\"anchorVertOffset\":1799}},\"osn\":21,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[0,537],\"attrs\":{\"drawing\":{\"inline\":true,\"anchorHorBase\":null,\"anchorHorAlign\":null,\"anchorHorOffset\":null,\"anchorVertBase\":null,\"anchorVertAlign\":null,\"anchorVertOffset\":null,\"textWrapMode\":null,\"textWrapSide\":null}},\"osn\":22,\"opl\":1}"
            + "]";
        List<String> editOperations = new ArrayList();
        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * Removing one style property (font).
     */
    public void deleteHardFormattingFontTest() {
        final String SOURCE_FILE_NAME_TRUNC = "bigFont";
        String firstEditOperations = "["
            + "{\"start\":[0,3],\"attrs\":{\"character\":{\"fontSize\":null}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0,5],\"osn\":102}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Overriding hyperlinks partly (overlapping)
     */
    public void hyperlinkOverlappingOverridenByHyperlinkTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"text\":\"Empty Document\",\"start\":[0,0],\"opl\":1,\"name\":\"insertText\",\"osn\":21}"
            + ",{\"start\":[0,2],\"attrs\":{\"styleId\":\"Hyperlink\",\"character\":{\"url\":\"http://www.heise.de\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0,8],\"osn\":22}"
            + ",{\"start\":[0,3],\"attrs\":{\"styleId\":\"Hyperlink\",\"character\":{\"url\":\"http://www.sueddeutsche.de\"}},\"name\":\"setAttributes\",\"opl\":1,\"end\":[0,9],\"osn\":23}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * Adding a link to a part of an existing anchor and nested span.
     */
    public void newHyperlinkOnNestedSpansAndAnchor() {
        final String SOURCE_FILE_NAME_TRUNC = "_multipleSpansNested";
        String firstEditOperations = "["
            + "{\"name\":\"setAttributes\",\"attrs\":{\"styleId\":\"Hyperlink\",\"character\":{\"url\":\"http://www.sueddeutsche.de/\"}},\"start\":[0,2],\"end\":[0,3]}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Overriding hyperlinks partly (overlapping)
     */
    public void simpleFormatTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            + "{\"name\":\"setDocumentAttributes\",\"attrs\":{\"document\":{\"fileFormat\":\"odf\"}}},\n"
            + "{\"name\":\"insertFontDescription\",\"attrs\":{\"family\":\"Courier\",\"familyGeneric\":\"modern\",\"pitch\":\"fixed\"},\"fontName\":\"Courier\"},\n"
            + "{\"name\":\"insertParagraph\",\"start\":[0]},\n"
            + "{\"name\":\"insertText\",\"start\":[0,0],\"text\":\"Courier subscript\"},\n"
            + "{\"name\":\"setAttributes\",\"start\":[0,0],\"end\":[0,16],\"attrs\":{\"character\":{\"fontName\":\"Courier\"}}}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }

    @Test
    /**
     * A text style change over style borders mixes all styles. Formating full
     * text of a paragraph of first part already formatted will mix forma over
     * full selection.
     */
    public void textStyleChangeOverStyleBorders() {
        final String SOURCE_FILE_NAME_TRUNC = "coloredParagraph";
        String firstEditOperations = "["
            + "{\"name\":\"setAttributes\",\"start\":[0,0],\"attrs\":{\"character\":{\"underline\":true}},\"end\":[0,2]}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }


    @Ignore //no Spreadsheets for now..
    @Test
    /*
     * Inserting and Deleting rows and columns
     */
    public void rowInsertionBTest() {
        final String SOURCE_FILE_NAME_TRUNC = "Insert and Delete Rows3";
        String firstEditOperations = "["
            + "{\"start\":12,\"name\":\"insertRows\",\"opl\":1,\"end\":14,\"sheet\":0,\"osn\":135},"
            + "{\"start\":12,\"name\":\"insertRows\",\"opl\":1,\"end\":14,\"sheet\":1,\"osn\":135},"
            + "{\"start\":12,\"name\":\"insertRows\",\"opl\":1,\"end\":14,\"sheet\":2,\"osn\":135},"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * ODT - changing column width: after closing/re-edit the table
     * is set to 100%
     */
    public void setTableColumnWidthWithAlignMarginTest() {
        final String SOURCE_FILE_NAME_TRUNC = "tabelleAlignMargin";
        String firstEditOperations = "["
            + "{\"name\":\"setAttributes\",\"attrs\":{\"table\":{\"tableGrid\":[18773,18773,18773,9328],\"width\":14870}},\"start\":[0]}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Inserting and Deleting rows and columns
     */
    public void rowInsertionCTest() {
        final String SOURCE_FILE_NAME_TRUNC = "simple-styled";
        String firstEditOperations = "["
            + "{\"end\":null,\"name\":\"insertRows\",\"opl\":1,\"osn\":53,\"parse\":\"en_US\",\"sheet\":0,\"start\":9},"
            + "{\"attrs\":{\"sheet\":{\"activeCell\":[0,9],\"s5electedRanges\":[{\"end\":[1023,9],\"start\":[0,9]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":54,\"parse\":\"en_US\",\"sheet\":0}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore // Roundtrip broken as spreadsheet is always maximized, which causes problems. Perhaps always normalizing to full table in import or status attributes at table?
    /**
     * Insert row column within colored spreadsheet in the beginning and within.
     */
    public void insertRowColumnTest() {
        final String SOURCE_FILE_NAME_TRUNC = "coloredRowColumn";
        String firstEditOperations = "["
            + "{\"start\":[0],\"name\":\"insertRows\",\"sheet\":0},"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Adding a range of values to a table (spreadsheet)
     */
    public void cellRangeModificationAtBeginningTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"name\":\"setCellContents\",\"start\":[0,0],\"contents\":[[{\"value\":1},{\"value\":2},{\"value\":3}]],\"sheet\":0},"
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":4}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[3,0]},"
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":5}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[4,0]},"
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":2}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":3}]],\"name\":\"setCellContents\",\"parse\":\"en_US\",\"sheet\":0,\"start\":[0,1]},"
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":3}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":4}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[1,1]},"
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":4}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":5}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[2,1]},"
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":5}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":6}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[3,1]},"
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":6}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":7}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[4,1]},"
            + "{\"attrs\":{\"sheet\":{\"selectedRanges\":[{\"end\":[4,2],\"start\":[0,0]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":15,\"parse\":\"en_US\",\"sheet\":0}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Inserting and Deleting rows and columns
     */
    public void cellRangeModificationInMiddleTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":2}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":3}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":4}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":5}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":6}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":7}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":8}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":9}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":10}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":11}],[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":12}]],\"name\":\"setCellContents\",\"parse\":\"en_US\",\"sheet\":0,\"start\":[5,5]},"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Inserting a row in between two styled cells within a spreadsheet. New
     * rows with style!
     */
    public void rowInsertionWithCopyTest() {
        final String SOURCE_FILE_NAME_TRUNC = "Insert and Delete Rows4";
        String firstEditOperations = "["
            + "{\"start\":1,\"name\":\"insertRows\",\"end\":4,\"sheet\":0},"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Inserting a row in the beginning of the spreadsheet. New rows without
     * style!
     */
    public void rowInsertionWithOutCopyTest() {
        final String SOURCE_FILE_NAME_TRUNC = "Insert and Delete Rows4";
        String firstEditOperations = "["
            + "{\"start\":[0],\"name\":\"insertRows\",\"end\":4,\"sheet\":0},"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Sum via autosum - close/re-edit: sum is lost Inserting
     * integer numbers and a sum.
     */
    public void formulaSumTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":1}]],\"name\":\"setCellContents\",\"opl\":1,\"osn\":12,\"parse\":\"en_US\",\"sheet\":0,\"start\":[0,0]},"
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"value\":3}]],\"name\":\"setCellContents\",\"opl\":1,\"osn\":13,\"parse\":\"en_US\",\"sheet\":0,\"start\":[1,0]},"
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"numberFormat\":{\"code\":\"\",\"id\":0}}},\"result\":4,\"value\":\"=SUM(A1:B1)\"}]],\"name\":\"setCellContents\",\"opl\":1,\"osn\":14,\"parse\":\"en_US\",\"sheet\":0,\"start\":[2,0]},"
            + "{\"attrs\":{\"sheet\":{\"activeCell\":[2,1],\"selectedRanges\":[{\"end\":[2,1],\"start\":[2,1]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":15,\"parse\":\"en_US\",\"sheet\":0}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Set outer borders /set border attributes/close/re-edit:
     * left/right borders lost the attributes Setting a fat black table cell
     * border at B3, aka [1,2]
     */
    public void borderFullTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            // ** setting simple borders at B3 **
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"borderBottom\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},"
            + "                                        \"borderLeft\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},"
            + "                                        \"borderRight\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},"
            + "                                         \"borderTop\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26}}}}]],"
            + "                                         \"name\":\"setCellContents\",\"parse\":\"en_US\",\"sheet\":0,\"start\":[1,2]},"
            //
            //
            // ** Remove outer borders **
            // Remove right border on A3
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"borderRight\":null}}}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[0,2]},"
            // Remove left border on C3
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"borderLeft\":null}}}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[2,2]},"
            // Remove bottom border on B2
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"borderBottom\":null}}}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[1,1]},"
            // Remove top border on B4
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"borderTop\":null}}}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[1,3]},"
            //
            //
            // ** Cell row sequence, setting right & left fat border ** (upper and lower still 0.26 mm from initial format)
            + "{\"contents\":[["
            // A3 no right border
            + "{\"attrs\":{\"cell\":{\"borderRight\":null}}},"
            // B3 left and right fat border
            + "{\"attrs\":{\"cell\":{\"borderLeft\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":212},\"borderRight\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":212}}}},"
            // C3 no left border
            + "{\"attrs\":{\"cell\":{\"borderLeft\":null}}}]],"
            + "\"name\":\"setCellContents\",\"parse\":\"en_US\",\"sheet\":0,\"start\":[0,2]},"
            //
            //
            // ** Range change starting upper left corner at table cell B2 **
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"borderBottom\":null}}}],"
            // setting on table cell B3
            + "[{\"attrs\":{\"cell\":"
            + "{\"borderBottom\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":212},"
            + "\"borderTop\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":212}}}}],"
            // setting on table cell B4
            + "[{\"attrs\":{\"cell\":{\"borderTop\":null}}}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[1,1]},"
            // ** setting active cell **
            + "{\"attrs\":{\"sheet\":{\"activeCell\":[1,2],\"selectedRanges\":[{\"end\":[1,2],\"start\":[1,2]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":19,\"parse\":\"en_US\",\"sheet\":0}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Copy sheet - close/re-edit: copy sheet is lost Set "a" in
     * cell A1 and copy the sheet
     */
    public void sheetCopyTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"contents\":[[{\"value\":\"a\"}]],\"name\":\"setCellContents\",\"opl\":1,\"osn\":12,\"parse\":\"en_US\",\"sheet\":0,\"start\":[0,0]},"
            + "{\"name\":\"copySheet\",\"opl\":1,\"osn\":13,\"parse\":\"en_US\",\"sheet\":0,\"sheetName\":\"Sheet2\",\"to\":1},"
            + "{\"attrs\":{\"document\":{\"activeSheet\":1}},\"name\":\"setDocumentAttributes\",\"opl\":1,\"osn\":14,\"parse\":\"en_US\"},"
            + "{\"attrs\":{\"sheet\":{\"activeCell\":[0,1],\"selectedRanges\":[{\"end\":[0,1],\"start\":[0,1]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":15,\"sheet\":0},"
            + "{\"attrs\":{\"sheet\":{\"activeCell\":[0,1],\"selectedRanges\":[{\"end\":[0,1],\"start\":[0,1]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":16,\"sheet\":1}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Copy&paste into a cell with a formula: after closing re-edit
     * the pasted value is wrong
     */
    public void copyFormulaTest() {
        final String SOURCE_FILE_NAME_TRUNC = "simple-formula";
        String firstEditOperations = "["
            + "{\"contents\":[[{\"value\":3}]],\"end\":null,\"name\":\"setCellContents\",\"parse\":null,\"sheet\":0,\"start\":[0,0]}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * OOo Writer document with removed change-tracked content is
     * loaded incorrect Ignore deleted content.‚
     */
    public void ignoreDeletedChangedTrackParagraphTest() {
        final String SOURCE_FILE_NAME_TRUNC = "changeTracked";
        String firstEditOperations = "["
            + ""
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Creating a multi-row range with outerborders in an empty
     * document breaks Creating a 2 columns and 3 rows range starting at B1 with
     * surrounding border and copies the sheet! Expansion of table did not work
     * as at one point not the correct cell was returned.
     */
    public void expandAndFormatMultiRowRangeTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"contents\":["
            + "["
            // Starting with B1
            // 2nd Row:
            // B2 border left and top
            + "{\"attrs\":{\"cell\":{\"borderBottom\":null,\"borderLeft\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderRight\":null,\"borderTop\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26}}}},"
            // C2 border right and top
            + "{\"attrs\":{\"cell\":{\"borderBottom\":null,\"borderLeft\":null,\"borderRight\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderTop\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26}}}}"
            + "],"
            // 3rd Row:
            // B3 border left
            + "["
            + "{\"attrs\":{\"cell\":{\"borderBottom\":null,\"borderLeft\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderRight\":null,\"borderTop\":null}}},"
            // C3 border right
            + "{\"attrs\":{\"cell\":{\"borderBottom\":null,\"borderLeft\":null,\"borderRight\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderTop\":null}}}"
            + "],"
            // 4th Row:
            // B4 border left and bottom
            + "["
            + "{\"attrs\":{\"cell\":{\"borderBottom\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderLeft\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderRight\":null,\"borderTop\":null}}},"
            // B4 border right and bottom
            + "{\"attrs\":{\"cell\":{\"borderBottom\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderLeft\":null,\"borderRight\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderTop\":null}}}"
            + "]"
            + "],\"name\":\"setCellContents\",\"parse\":\"en_US\",\"sheet\":0,\"start\":[1,1]},"
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"borderRight\":null}}}],[{\"attrs\":{\"cell\":{\"borderRight\":null}}}],[{\"attrs\":{\"cell\":{\"borderRight\":null}}}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[0,1]},{\"contents\":[[{\"attrs\":{\"cell\":{\"borderLeft\":null}}}],[{\"attrs\":{\"cell\":{\"borderLeft\":null}}}],[{\"attrs\":{\"cell\":{\"borderLeft\":null}}}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[3,1]},{\"contents\":[[{\"attrs\":{\"cell\":{\"borderBottom\":null}}},{\"attrs\":{\"cell\":{\"borderBottom\":null}}}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[1,0]},"
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"borderTop\":null}}},{\"attrs\":{\"cell\":{\"borderTop\":null}}}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[1,4]},"
            + "{\"name\":\"copySheet\",\"opl\":1,\"osn\":17,\"parse\":\"en_US\",\"sheet\":0,\"sheetName\":\"Sheet2\",\"to\":1},{\"attrs\":{\"document\":{\"activeSheet\":1}},\"name\":\"setDocumentAttributes\",\"opl\":1,\"osn\":18,\"parse\":\"en_US\"},"
            + "{\"attrs\":{\"sheet\":{\"activeCell\":[4,2],\"selectedRanges\":[{\"end\":[4,2],\"start\":[4,2]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":19,\"sheet\":0},"
            + "{\"attrs\":{\"sheet\":{\"activeCell\":[4,2],\"selectedRanges\":[{\"end\":[4,2],\"start\":[4,2]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":20,\"sheet\":1}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Insert a rows/close/re-edit fails Insert a new first line
     */
    public void insertRowAtBeginningTest() {
        final String SOURCE_FILE_NAME_TRUNC = "single-row-spreadsheet";
        String firstEditOperations = "["
            + "{\"end\":null,\"name\":\"insertRows\",\"opl\":1,\"osn\":12,\"parse\":\"en_US\",\"sheet\":0,\"start\":[0]},"
            + "{\"attrs\":{\"sheet\":{\"selectedRanges\":[{\"end\":[1023,0],\"start\":[0,0]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":13,\"parse\":\"en_US\",\"sheet\":0}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Setting outer border over a cell range /close/re-edit: some
     * borders are lost Insert a range with outer border within yet not existing
     * space
     */
    public void insertRangeWithOuterBorderWithinEmptySpaceTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"contents\":[[{\"attrs\":{\"cell\":{\"borderBottom\":null,\"borderLeft\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderRight\":null,\"borderTop\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26}}}},{\"attrs\":{\"cell\":{\"borderBottom\":null,\"borderLeft\":null,\"borderRight\":null,\"borderTop\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26}}}},{\"attrs\":{\"cell\":{\"borderBottom\":null,\"borderLeft\":null,\"borderRight\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderTop\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26}}}}],[{\"attrs\":{\"cell\":{\"borderBottom\":null,\"borderLeft\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderRight\":null,\"borderTop\":null}}},{\"attrs\":{\"cell\":{\"borderBottom\":null,\"borderLeft\":null,\"borderRight\":null,\"borderTop\":null}}},{\"attrs\":{\"cell\":{\"borderBottom\":null,\"borderLeft\":null,\"borderRight\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderTop\":null}}}],[{\"attrs\":{\"cell\":{\"borderBottom\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderLeft\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderRight\":null,\"borderTop\":null}}},{\"attrs\":{\"cell\":{\"borderBottom\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderLeft\":null,\"borderRight\":null,\"borderTop\":null}}},{\"attrs\":{\"cell\":{\"borderBottom\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderLeft\":null,\"borderRight\":{\"color\":{\"type\":\"auto\"},\"style\":\"single\",\"width\":26},\"borderTop\":null}}}]],\"name\":\"setCellContents\",\"parse\":\"en_US\",\"sheet\":0,\"start\":[1,1]},{\"contents\":[[{\"attrs\":{\"cell\":{\"borderRight\":null}}}],[{\"attrs\":{\"cell\":{\"borderRight\":null}}}],[{\"attrs\":{\"cell\":{\"borderRight\":null}}}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[0,1]},{\"contents\":[[{\"attrs\":{\"cell\":{\"borderLeft\":null}}}],[{\"attrs\":{\"cell\":{\"borderLeft\":null}}}],[{\"attrs\":{\"cell\":{\"borderLeft\":null}}}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[4,1]},{\"contents\":[[{\"attrs\":{\"cell\":{\"borderBottom\":null}}},{\"attrs\":{\"cell\":{\"borderBottom\":null}}},{\"attrs\":{\"cell\":{\"borderBottom\":null}}}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[1,0]},{\"contents\":[[{\"attrs\":{\"cell\":{\"borderTop\":null}}},{\"attrs\":{\"cell\":{\"borderTop\":null}}},{\"attrs\":{\"cell\":{\"borderTop\":null}}}]],\"name\":\"setCellContents\",\"sheet\":0,\"start\":[1,4]},{\"attrs\":{\"sheet\":{\"activeCell\":[1,1],\"selectedRanges\":[{\"end\":[3,3],\"start\":[1,1]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":17,\"parse\":\"en_US\",\"sheet\":0}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Import a document (with a note) /type some
     * text/close/re-edit: text is lost
     */
    public void changeTextBehindNoteTest() {
        final String SOURCE_FILE_NAME_TRUNC = "ST_Note";
        String firstEditOperations = "["
            + "{\"contents\":[[{\"value\":\"abcd\"}]],\"name\":\"setCellContents\",\"opl\":1,\"osn\":1031,\"parse\":\"en_GB\",\"sheet\":0,\"start\":[1,6]},"
            + "{\"attrs\":{\"sheet\":{\"activeCell\":[1,6],\"selectedRanges\":[{\"end\":[1,6],\"start\":[1,6]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":1032,\"parse\":\"en_GB\",\"sheet\":0}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * There are two errors in the XML resulting in a fail of load.
     * 1) The font-size was given as integer without measurement, but is now
     * falling back to point 'pt'. 2) The XML namespace was given an explicit
     * attribute and resulted into a parsing error and as this attribute is
     * implicitly known in all XML is just being avoided.
     */
    public void loadInvalidODF() {
        final String SOURCE_FILE_NAME_TRUNC = "invalidODF";
        String firstEditOperations = "[]";
        List<String> editOperations = new ArrayList();
        editOperations.add(firstEditOperations);
        super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".ods", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * Loading/Saving various page break styles
     */
    public void loadPageBreakTest() {
        final String SOURCE_FILE_NAME_TRUNC = "pagebreaks";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), "[]");
    }

    @Test
    /**
     * Changed bullet symbols are not exported Switching bullet
     * symbols fails in different Level.
     */
    public void switchBulletSymbolsInLevelTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"name\":\"insertListStyle\",\"listStyleId\":\"L20004\",\"listDefinition\":{\"listLevel0\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":1270,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel1\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":2540,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel2\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":3810,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel3\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":5080,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel4\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":6350,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel5\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":7620,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel6\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":8890,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel7\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":10160,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel8\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":11430,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"}},\"osn\":16,\"opl\":1},"
            + "{\"name\":\"insertStyleSheet\",\"attrs\":{\"paragraph\":{\"indentLeft\":1270,\"contextualSpacing\":true,\"nextStyleId\":\"ListParagraph\"}},\"type\":\"paragraph\",\"styleId\":\"ListParagraph\",\"styleName\":\"List Paragraph\",\"parent\":\"Standard\",\"uiPriority\":34,\"osn\":17,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L20004\",\"listLevel\":0}},\"osn\":18,\"opl\":1},"
            + "{\"name\":\"insertText\",\"text\":\"a\",\"start\":[0,0],\"osn\":19,\"opl\":1},"
            + "{\"name\":\"splitParagraph\",\"start\":[0,1],\"osn\":20,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[1],\"attrs\":{\"paragraph\":{\"listLevel\":1}},\"osn\":21,\"opl\":1},"
            + "{\"name\":\"insertListStyle\",\"listStyleId\":\"L2\",\"listDefinition\":{\"listIdentifier\":\"L20004\",\"listLevels\":[{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":1270,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},"
            + "{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":2540,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},"
            + "{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":3810,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},"
            + "{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":5080,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},"
            + "{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":6350,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},"
            + "{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":7620,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},"
            + "{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":8890,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},"
            + "{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":10160,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},"
            + "{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":11430,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"}],\"listLevel0\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":1270,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel1\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":2540,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"○\",\"fontName\":\"Times New Roman\"},\"listLevel2\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":3810,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel3\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":5080,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel4\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":6350,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel5\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":7620,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel6\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":8890,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel7\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":10160,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"},\"listLevel8\":{\"numberFormat\":\"bullet\",\"listStartValue\":1,\"indentLeft\":11430,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"■\",\"fontName\":\"Times New Roman\"}},\"osn\":22,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\"}},\"osn\":23,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[1],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L2\"}},\"osn\":24,\"opl\":1},"
            + "{\"name\":\"insertText\",\"text\":\"b\",\"start\":[1,0],\"osn\":25,\"opl\":1}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Splitting a paragraph with page break inherited the page
     * break.
     */
    public void splitParagraphWithPageBreak() {
        final String SOURCE_FILE_NAME_TRUNC = "AB pageBreakBefore";
        String firstEditOperations = "["
            + "{\"name\":\"splitParagraph\",\"start\":[1,1],\"osn\":19,\"opl\":1},"
            + "{\"name\":\"insertText\",\"text\":\"C\",\"start\":[2,0],\"osn\":20,\"opl\":1}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Split ODT paragraph with Page Break is loosing it
     * TestDocument: A and B on two pages First: Adding new paragraph with C
     * behind B on second page Second: Adding page before C, so each letter is
     * on an own page.
     */
    public void pageBreakTest() {
        // A and B on two pages
        final String SOURCE_FILE_NAME_TRUNC = "pageBreakProblem";
        String firstEditOperations = "["
            + "{\"name\":\"splitParagraph\",\"start\":[1,1],\"osn\":16,\"opl\":1},"
            + "{\"name\":\"insertText\",\"text\":\"C\",\"start\":[2,0],\"osn\":17,\"opl\":1},"
            + "{\"name\":\"splitParagraph\",\"start\":[2,0],\"osn\":18,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[3],\"attrs\":{\"paragraph\":{\"pageBreakBefore\":true}},\"osn\":19,\"opl\":1}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Ignore //no Spreadsheets for now..
    @Test
    /**
     * Import document with formula: formulas does not worked (=>
     * oooc:=FALSE())
     */
    public void formulaPrefixUnknownTest() {
        super.roundtripRegressionSpreadsheetTest("ST_Formulas", getTestMethodName(), "[]");
    }

    @Test
    /**
     * Splitting a paragraph with page break inherited the page
     * break.
     */
    public void splitParagraphWithHeadings() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"name\":\"insertStyleSheet\",\"attrs\":{\"character\":{\"color\":{\"type\":\"scheme\",\"value\":\"accent1\",\"transformations\":[{\"type\":\"shade\",\"value\":74902}],\"fallbackValue\":\"376092\"},\"bold\":true,\"fontSize\":14},\"paragraph\":{\"outlineLevel\":0,\"nextStyleId\":\"Standard\"}},\"type\":\"paragraph\",\"styleId\":\"heading1\",\"styleName\":\"heading 1\",\"parent\":\"Standard\",\"uiPriority\":9,\"osn\":16,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[0],\"attrs\":{\"character\":{\"fontName\":null,\"fontSize\":null,\"bold\":null,\"italic\":null,\"underline\":null,\"strike\":null,\"vertAlign\":null,\"color\":null,\"fillColor\":null,\"language\":null,\"url\":null},\"changes\":{\"inserted\":null,\"removed\":null,\"modified\":null},\"paragraph\":{\"nextStyleId\":null,\"alignment\":null,\"fillColor\":null,\"lineHeight\":null,\"listLabelHidden\":null,\"listStartValue\":null,\"outlineLevel\":null,\"tabStops\":null,\"borderLeft\":null,\"borderRight\":null,\"borderTop\":null,\"borderBottom\":null,\"borderInside\":null,\"indentFirstLine\":null,\"indentLeft\":null,\"indentRight\":null,\"marginTop\":null,\"marginBottom\":null,\"contextualSpacing\":null,\"pageBreakBefore\":null,\"pageBreakAfter\":null},\"styleId\":\"heading1\"},\"osn\":17,\"opl\":1},"
            + "{\"name\":\"insertText\",\"text\":\"a\",\"start\":[0,0],\"osn\":18,\"opl\":1},"
            + "{\"name\":\"splitParagraph\",\"start\":[0,1],\"osn\":19,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[1],\"attrs\":{\"styleId\":\"Standard\"},\"osn\":20,\"opl\":1},{\"name\":\"insertStyleSheet\",\"attrs\":{\"character\":{\"color\":{\"type\":\"scheme\",\"value\":\"accent1\",\"fallbackValue\":\"4F81BD\"},\"bold\":true,\"fontSize\":13},\"paragraph\":{\"outlineLevel\":1,\"nextStyleId\":\"Standard\"}},\"type\":\"paragraph\",\"styleId\":\"heading2\",\"styleName\":\"heading 2\",\"parent\":\"Standard\",\"uiPriority\":9,\"osn\":21,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[1],\"attrs\":{\"character\":{\"fontName\":null,\"fontSize\":null,\"bold\":null,\"italic\":null,\"underline\":null,\"strike\":null,\"vertAlign\":null,\"color\":null,\"fillColor\":null,\"language\":null,\"url\":null},\"changes\":{\"inserted\":null,\"removed\":null,\"modified\":null},\"paragraph\":{\"nextStyleId\":null,\"alignment\":null,\"fillColor\":null,\"lineHeight\":null,\"listLabelHidden\":null,\"listStartValue\":null,\"outlineLevel\":null,\"tabStops\":null,\"borderLeft\":null,\"borderRight\":null,\"borderTop\":null,\"borderBottom\":null,\"borderInside\":null,\"indentFirstLine\":null,\"indentLeft\":null,\"indentRight\":null,\"marginTop\":null,\"marginBottom\":null,\"contextualSpacing\":null,\"pageBreakBefore\":null,\"pageBreakAfter\":null},\"styleId\":\"heading2\"},\"osn\":22,\"opl\":1},"
            + "{\"name\":\"insertText\",\"text\":\"b\",\"start\":[1,0],\"osn\":23,\"opl\":1},"
            + "{\"name\":\"splitParagraph\",\"start\":[1,1],\"osn\":24,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[2],\"attrs\":{\"styleId\":\"Standard\"},\"osn\":25,\"opl\":1}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Paragraph formatting/ return/ change/close/re-edit:
     * formatting is wrong
     */
    public void splitParagraphWithHeadings2() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";

        String firstEditOperations = "["
            + "{\"name\":\"insertStyleSheet\",\"attrs\":{\"character\":{\"color\":{\"type\":\"scheme\",\"value\":\"accent1\",\"transformations\":[{\"type\":\"shade\",\"value\":74902}],\"fallbackValue\":\"376092\"},\"bold\":true,\"fontSize\":14},\"paragraph\":{\"outlineLevel\":0,\"nextStyleId\":\"Standard\"}},\"type\":\"paragraph\",\"styleId\":\"heading1\",\"styleName\":\"heading 1\",\"parent\":\"Standard\",\"uiPriority\":9,\"osn\":16,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[0],\"attrs\":{\"character\":{\"fontName\":null,\"fontSize\":null,\"bold\":null,\"italic\":null,\"underline\":null,\"strike\":null,\"vertAlign\":null,\"color\":null,\"fillColor\":null,\"language\":null,\"url\":null},\"changes\":{\"inserted\":null,\"removed\":null,\"modified\":null},\"paragraph\":{\"nextStyleId\":null,\"alignment\":null,\"fillColor\":null,\"lineHeight\":null,\"listLabelHidden\":null,\"listStartValue\":null,\"outlineLevel\":null,\"tabStops\":null,\"borderLeft\":null,\"borderRight\":null,\"borderTop\":null,\"borderBottom\":null,\"borderInside\":null,\"indentFirstLine\":null,\"indentLeft\":null,\"indentRight\":null,\"marginTop\":null,\"marginBottom\":null,\"contextualSpacing\":null,\"pageBreakBefore\":null,\"pageBreakAfter\":null},\"styleId\":\"heading1\"},\"osn\":17,\"opl\":1},"
            + "{\"name\":\"insertText\",\"text\":\"123456\",\"start\":[0,0],\"osn\":18,\"opl\":6},"
            + "{\"name\":\"splitParagraph\",\"start\":[0,3],\"osn\":24,\"opl\":1},"
            + "{\"name\":\"insertStyleSheet\",\"attrs\":{\"character\":{\"color\":{\"type\":\"scheme\",\"value\":\"accent1\",\"fallbackValue\":\"4F81BD\"},\"bold\":true,\"fontSize\":13},\"paragraph\":{\"outlineLevel\":1,\"nextStyleId\":\"Standard\"}},\"type\":\"paragraph\",\"styleId\":\"heading2\",\"styleName\":\"heading 2\",\"parent\":\"Standard\",\"uiPriority\":9,\"osn\":25,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[1],\"attrs\":{"
            + "\"character\":{\"fontName\":null,\"fontSize\":null,\"bold\":null,\"italic\":null,\"underline\":null,\"strike\":null,\"vertAlign\":null,\"color\":null,\"fillColor\":null,\"language\":null,\"url\":null},"
            + "\"changes\":{\"inserted\":null,\"removed\":null,\"modified\":null},"
            + "\"paragraph\":{\"nextStyleId\":null,\"alignment\":null,\"fillColor\":null,\"lineHeight\":null,\"listLabelHidden\":null,\"listStartValue\":null,\"outlineLevel\":null,\"tabStops\":null,\"borderLeft\":null,\"borderRight\":null,\"borderTop\":null,\"borderBottom\":null,\"borderInside\":null,\"indentFirstLine\":null,\"indentLeft\":null,\"indentRight\":null,\"marginTop\":null,\"marginBottom\":null,\"contextualSpacing\":null,\"pageBreakBefore\":null,\"pageBreakAfter\":null},"
            + "\"styleId\":\"heading2\"},"
            + "\"osn\":26,"
            + "\"opl\":1}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Empty spans are being preserved to keep default character
     * format in a paragraph (both characters have to be bold)
     */
    public void splitParagraphFormatTest() {
        List<String> editOperations = new ArrayList();
        editOperations.add("["
            + "{\"name\":\"setAttributes\",\"start\":[0],\"attrs\":{\"character\":{\"bold\":false}}},"
            + "{\"name\":\"insertText\",\"text\":\"a\",\"start\":[0,0],\"attrs\":{\"character\":{\"bold\":true}}},"
            + "{\"name\":\"splitParagraph\",\"start\":[0,1]},"
            + "{\"name\":\"insertText\",\"text\":\"b\",\"start\":[1,0]},"
            + "]");
        super.roundtripRegressionTextTest("empty", getTestMethodName(), editOperations);
    }

    @Test
    /**
     * ODT: Resized table: Table size not loaded correctly
     */
    public void tableWidthChangeTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"name\":\"insertStyleSheet\",\"attrs\":{\"wholeTable\":{\"paragraph\":{\"lineHeight\":{\"type\":\"percent\",\"value\":100}},\"table\":{\"borderTop\":{\"color\":{\"type\":\"auto\"},\"width\":17,\"style\":\"single\"},\"borderBottom\":{\"color\":{\"type\":\"auto\"},\"width\":17,\"style\":\"single\"},\"borderInsideHor\":{\"color\":{\"type\":\"auto\"},\"width\":17,\"style\":\"single\"},\"borderInsideVert\":{\"color\":{\"type\":\"auto\"},\"width\":17,\"style\":\"single\"},\"borderLeft\":{\"color\":{\"type\":\"auto\"},\"width\":17,\"style\":\"single\"},\"borderRight\":{\"color\":{\"type\":\"auto\"},\"width\":17,\"style\":\"single\"},\"paddingBottom\":0,\"paddingTop\":0,\"paddingLeft\":190,\"paddingRight\":190}}},\"type\":\"table\",\"styleId\":\"TableGrid\",\"styleName\":\"Table Grid\",\"uiPriority\":59,\"osn\":16,\"opl\":1},"
            + "{\"name\":\"insertTable\",\"start\":[1],\"attrs\":{\"table\":{\"tableGrid\":[1000,1000],\"width\":\"auto\",\"exclude\":[\"lastRow\",\"lastCol\",\"bandsVert\"]},\"styleId\":\"TableGrid\"},\"osn\":17,\"opl\":1},"
            + "{\"name\":\"insertRows\",\"start\":[1,0],\"count\":2,\"insertDefaultCells\":true,\"osn\":18,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"attrs\":{\"table\":{\"tableGrid\":[1459,541],\"width\":12039}},\"start\":[1],\"osn\":19,\"opl\":1}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore // Ignore these big performance tests for now
    /**
     * The document can be round-tripped in a single test, but not with all the
     * other files on the disc (memory problem of test or toolkit?)
     */
    public void BigDocTest() {
        final String SOURCE_FILE_NAME_TRUNC = "performance/OpenDocument-v1.2-os-part1";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), "[]");
    }

    @Test
    /**
     * Bug 35330 - Delete a numbered list/undo/closed/re-edit: => one entry is
     * too much A text:list-item becomes a text:list-header, when there are
     * multiple paragraph in a list and the first paragraph (the one with the
     * list label) is being deleted and some of the following sibling are
     * remaining (without label). WYSIWYG.
     */
    public void insertDeleteListBlockTest() {
        final String SOURCE_FILE_NAME_TRUNC = "ST_Bullets&Numbering2";
        String firstEditOperations = "["
            + "{\"name\":\"delete\",\"start\":[45],\"opl\":1,\"osn\":110},\n"
            + "{\"name\":\"delete\",\"start\":[44],\"opl\":1,\"osn\":111},\n"
            + "{\"name\":\"delete\",\"start\":[43],\"opl\":1,\"osn\":112},\n"
            + "{\"name\":\"delete\",\"start\":[42],\"opl\":1,\"osn\":113},\n"
            + "{\"name\":\"delete\",\"start\":[41],\"opl\":1,\"osn\":114},\n"
            + "{\"name\":\"delete\",\"start\":[40],\"opl\":1,\"osn\":115},\n"
            + "{\"name\":\"delete\",\"start\":[39],\"opl\":1,\"osn\":116},\n"
            + "{\"name\":\"delete\",\"start\":[38],\"opl\":1,\"osn\":117},\n"
            + "{\"name\":\"insertParagraph\",\"start\":[38],\"attrs\":{\"styleId\":\"Standard\",\"character\":{\"bold\":true}},\"opl\":1,\"osn\":118},\n"
            + "{\"name\":\"insertParagraph\",\"start\":[39],\"attrs\":{\"styleId\":\"Standard\",\"character\":{\"bold\":true},\"paragraph\":{\"listLevel\":0,\"listStyleId\":\"L5\"}},\"opl\":1,\"osn\":119},\n"
            + "{\"name\":\"insertText\",\"start\":[39,0],\"opl\":1,\"osn\":120,\"text\":\"Smoke Testing\"},\n"
            + "{\"name\":\"insertParagraph\",\"start\":[40],\"attrs\":{\"styleId\":\"Standard\",\"character\":{\"bold\":true},\"paragraph\":{\"listLevel\":0,\"listStyleId\":\"L5\"}},\"opl\":1,\"osn\":121},\n"
            + "{\"name\":\"insertText\",\"start\":[40,0],\"opl\":1,\"osn\":122,\"text\":\"Functional Testing\"},\n"
            + "{\"name\":\"insertParagraph\",\"start\":[41],\"attrs\":{\"styleId\":\"Standard\",\"character\":{\"bold\":true},\"paragraph\":{\"listLevel\":0,\"listStyleId\":\"L5\"}},\"opl\":1,\"osn\":123},\n"
            + "{\"name\":\"insertText\",\"start\":[41,0],\"opl\":1,\"osn\":124,\"text\":\"Regression Testing\"},\n"
            + "{\"name\":\"insertParagraph\",\"start\":[42],\"attrs\":{\"styleId\":\"Standard\",\"character\":{\"bold\":true},\"paragraph\":{\"listLevel\":0,\"listStyleId\":\"L5\"}},\"opl\":1,\"osn\":125},\n"
            + "{\"name\":\"insertText\",\"start\":[42,0],\"opl\":1,\"osn\":126,\"text\":\"Installation Testing\"},\n"
            + "{\"name\":\"insertParagraph\",\"start\":[43],\"attrs\":{\"styleId\":\"Standard\",\"character\":{\"bold\":true},\"paragraph\":{\"listLevel\":0,\"listStyleId\":\"L5\"}},\"opl\":1,\"osn\":127},\n"
            + "{\"name\":\"insertText\",\"start\":[43,0],\"opl\":1,\"osn\":128,\"text\":\"Performance Testing\"},\n"
            + "{\"name\":\"insertParagraph\",\"start\":[44],\"attrs\":{\"styleId\":\"Standard\",\"character\":{\"bold\":true},\"paragraph\":{\"listLevel\":0,\"listStyleId\":\"L5\"}},\"opl\":1,\"osn\":129},\n"
            + "{\"name\":\"insertText\",\"start\":[44,0],\"opl\":1,\"osn\":130,\"text\":\"Command Line Testing\"},\n"
            + "{\"name\":\"insertParagraph\",\"start\":[45],\"attrs\":{\"styleId\":\"Standard\",\"character\":{\"bold\":true},\"paragraph\":{\"listLevel\":0,\"listStyleId\":\"L5\"}},\"opl\":1,\"osn\":131},\n"
            + "{\"name\":\"insertText\",\"start\":[45,0],\"opl\":1,\"osn\":132,\"text\":\"Reliability Testing\"}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..

    /**
     * A sheet with formula source will be renamed by OX Spreadsheet
     */
    public void renameSheetWithFormulaSourceTest() {
        final String SOURCE_FILE_NAME_TRUNC = "formulaCrossSheetSum";
        String firstEditOperations = "["
            + "{\"name\":\"setSheetName\",\"opl\":1,\"osn\":16,\"parse\":\"en_GB\",\"sheet\":0,\"sheetName\":\"wuff\"},"
            + "{\"name\":\"fillCellRange\",\"result\":2,\"sheet\":1,\"start\":[0,0],\"value\":\"=SUM([wuff.A1];[wuff.B1])\"},"
            + "{\"attrs\":{\"document\":{\"activeSheet\":1}},\"name\":\"setDocumentAttributes\",\"opl\":1,\"osn\":17,\"parse\":\"en_GB\"},"
            + "{\"attrs\":{\"sheet\":{\"activeCell\":[1,0],\"selectedRanges\":[{\"end\":[1,0],\"start\":[1,0]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":18,\"sheet\":0},"
            + "{\"attrs\":{\"sheet\":{\"activeCell\":[4,6],\"selectedRanges\":[{\"end\":[4,6],\"start\":[4,6]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":19,\"sheet\":1}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * A new list style name could not be set.
     */
    public void changeListDefaultStyleTest() {
        final String SOURCE_FILE_NAME_TRUNC = "listStyleId";
        String firstEditOperations = "["
            + "{\"name\":\"insertListStyle\",\"listStyleId\":\"L30001\",\"listDefinition\":{\"listLevel0\":{\"numberFormat\":\"decimal\",\"listStartValue\":1,\"indentLeft\":1270,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"%1.\"},\"listLevel1\":{\"numberFormat\":\"decimal\",\"listStartValue\":1,\"indentLeft\":2540,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"%2.\"},\"listLevel2\":{\"numberFormat\":\"decimal\",\"listStartValue\":1,\"indentLeft\":3810,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"%3.\"},\"listLevel3\":{\"numberFormat\":\"decimal\",\"listStartValue\":1,\"indentLeft\":5080,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"%4.\"},\"listLevel4\":{\"numberFormat\":\"decimal\",\"listStartValue\":1,\"indentLeft\":6350,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"%5.\"},\"listLevel5\":{\"numberFormat\":\"decimal\",\"listStartValue\":1,\"indentLeft\":7620,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"%6.\"},\"listLevel6\":{\"numberFormat\":\"decimal\",\"listStartValue\":1,\"indentLeft\":8890,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"%7.\"},\"listLevel7\":{\"numberFormat\":\"decimal\",\"listStartValue\":1,\"indentLeft\":10160,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"%8.\"},\"listLevel8\":{\"numberFormat\":\"decimal\",\"listStartValue\":1,\"indentLeft\":11430,\"indentFirstLine\":-635,\"textAlign\":\"left\",\"levelText\":\"%9.\"}},\"osn\":40,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[0],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L30001\",\"listLevel\":8}},\"osn\":41,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[1],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L30001\",\"listLevel\":8}},\"osn\":42,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[2],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L30001\",\"listLevel\":8}},\"osn\":43,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[3],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L30001\",\"listLevel\":8}},\"osn\":44,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[4],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L30001\",\"listLevel\":8}},\"osn\":45,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[5],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L30001\",\"listLevel\":8}},\"osn\":46,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[6],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L30001\",\"listLevel\":8}},\"osn\":47,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[7],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L30001\",\"listLevel\":8}},\"osn\":48,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[8],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L30001\",\"listLevel\":8}},\"osn\":49,\"opl\":1},"
            + "{\"name\":\"setAttributes\",\"start\":[9],\"attrs\":{\"styleId\":\"ListParagraph\",\"paragraph\":{\"listStyleId\":\"L30001\",\"listLevel\":8}},\"osn\":50,\"opl\":1}"
            + "]";
        List<String> editOperations = new ArrayList();
        editOperations.add(firstEditOperations);
        //  super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);

        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     * Invalid style names as '40% - Accent 1' where sometimes validated and
     * have thrown an exception. More validation was added and no exception is
     * thrown any longer instead SAX ErrorHandler mechanism is used.
     */
    public void overrideStyleTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty";
        String firstEditOperations = "["
            + "{\"attrs\":{\"cell\":{\"fillColor\":{\"fallbackValue\":\"bcd2f7\",\"transformations\":[{\"type\":\"tint\",\"value\":40001}],\"type\":\"scheme\",\"value\":\"accent1\"}},\"character\":{\"color\":{\"fallbackValue\":\"000000\",\"type\":\"scheme\",\"value\":\"dark1\"},\"fontName\":\"Arial\"}},\"name\":\"insertStyleSheet\",\"opl\":1,\"osn\":12,\"parse\":\"en_GB\",\"styleId\":\"40% - Accent 1\",\"styleName\":\"40% - Accent 1\",\"type\":\"cell\",\"uiPriority\":6},"
            + "{\"attrs\":{\"cell\":{\"alignHor\":null,\"alignVert\":null,\"borderBottom\":null,\"borderInsideHor\":null,\"borderInsideVert\":null,\"borderLeft\":null,\"borderRight\":null,\"borderTop\":null,\"fillColor\":null,\"hidden\":null,\"numberFormat\":null,\"unlocked\":null,\"wrapText\":null},\"character\":{\"bold\":null,\"color\":null,\"fontName\":null,\"fontSize\":null,\"italic\":null,\"strike\":null,\"underline\":null},\"styleId\":\"40% - Accent 1\"},\"name\":\"fillCellRange\",\"opl\":1,\"osn\":13,\"parse\":null,\"sheet\":0,\"start\":[0,0]},"
            + "{\"attrs\":{\"row\":{\"customHeight\":false,\"height\":503,\"visible\":true}},\"name\":\"setRowAttributes\",\"opl\":1,\"osn\":14,\"sheet\":0,\"start\":[0]},"
            + "{\"attrs\":{\"cell\":{\"alignHor\":null,\"alignVert\":null,\"borderBottom\":null,\"borderLeft\":null,\"borderRight\":null,\"borderTop\":null,\"fillColor\":null,\"hidden\":null,\"numberFormat\":null,\"unlocked\":null,\"vertAlign\":null,\"wrapText\":null},\"character\":{\"bold\":null,\"color\":null,\"fontName\":null,\"fontSize\":null,\"italic\":null,\"language\":null,\"strike\":null,\"underline\":null,\"url\":null},\"styleId\":null},\"end\":[2,2],\"name\":\"fillCellRange\",\"parse\":null,\"sheet\":0,\"start\":[2,2],\"value\":null},"
            + "{\"attrs\":{\"character\":{\"color\":{\"fallbackValue\":\"000000\",\"type\":\"scheme\",\"value\":\"dark1\"},\"fontName\":\"Arial\"},\"styleId\":\"40% - Accent 1\"},\"name\":\"fillCellRange\",\"sheet\":0,\"start\":[2,2]},"
            + "{\"attrs\":{\"character\":{\"color\":{\"fallbackValue\":\"000000\",\"type\":\"scheme\",\"value\":\"dark1\"},\"fontName\":\"Arial\"},\"styleId\":\"40% - Accent 1\"},\"end\":[2,2],\"name\":\"fillCellRange\",\"parse\":null,\"sheet\":0,\"start\":[2,2]},"
            + "{\"attrs\":{\"cell\":{\"fillColor\":{\"type\":\"rgb\",\"value\":\"FFC7CE\"}},\"character\":{\"color\":{\"type\":\"rgb\",\"value\":\"9C0006\"},\"fontName\":\"Arial\"}},\"name\":\"insertStyleSheet\",\"opl\":1,\"osn\":16,\"parse\":\"en_GB\",\"styleId\":\"Negative\",\"styleName\":\"Negative\",\"type\":\"cell\",\"uiPriority\":4},"
            + "{\"attrs\":{\"cell\":{\"alignHor\":null,\"alignVert\":null,\"borderBottom\":null,\"borderInsideHor\":null,\"borderInsideVert\":null,\"borderLeft\":null,\"borderRight\":null,\"borderTop\":null,\"fillColor\":null,\"hidden\":null,\"numberFormat\":null,\"unlocked\":null,\"wrapText\":null},\"character\":{\"bold\":null,\"color\":null,\"fontName\":null,\"fontSize\":null,\"italic\":null,\"strike\":null,\"underline\":null},\"styleId\":\"Negative\"},\"name\":\"fillCellRange\",\"opl\":1,\"osn\":17,\"parse\":null,\"sheet\":0,\"start\":[2,2]},"
            + "{\"attrs\":{\"sheet\":{\"activeCell\":[2,2],\"selectedRanges\":[{\"end\":[2,2],\"start\":[2,2]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":18,\"parse\":\"en_GB\",\"sheet\":0}"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    @Ignore //no Spreadsheets for now..
    /**
     *
     * Adding a new green cell format for column i. Insert row column within
     * colored spreadsheet in the beginning and within.
     */
    public void formatColumnTest() {
        final String SOURCE_FILE_NAME_TRUNC = "coloredRowColumn";
        String firstEditOperations = "["
            + "{\"attrs\":{\"cell\":{\"fillColor\":{\"type\":\"rgb\",\"value\":\"00B050\"}}},\"name\":\"setColumnAttributes\",\"opl\":1,\"osn\":9,\"parse\":\"en_GB\",\"sheet\":0,\"start\":[8]},"
            + "{\"attrs\":{\"cell\":{\"fillColor\":{\"type\":\"rgb\",\"value\":\"00B050\"}}},\"end\":[8,0],\"name\":\"fillCellRange\",\"sheet\":0,\"start\":[8,0]},"
            + "{\"attrs\":{\"cell\":{\"fillColor\":{\"type\":\"rgb\",\"value\":\"00B050\"}}},\"end\":[8,1500],\"name\":\"fillCellRange\",\"sheet\":0,\"start\":[8,31]},"
            + "{\"attrs\":{\"row\":{\"customHeight\":false,\"height\":503,\"visible\":true}},\"end\":50,\"name\":\"setRowAttributes\",\"opl\":1,\"osn\":10,\"sheet\":0,\"start\":[0]},"
            + "{\"attrs\":{\"sheet\":{\"activeCell\":[8,0],\"selectedRanges\":[{\"end\":[8,1048575],\"start\":[8,0]}]}},\"name\":\"setSheetAttributes\",\"opl\":1,\"osn\":11,\"parse\":\"en_GB\",\"sheet\":0},"
            + "{\"start\":[0],\"name\":\"insertColumns\",\"sheet\":0},"
            + "{\"start\":[0],\"name\":\"insertRows\",\"sheet\":0},"
            + "]";
        super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

    @Test
    /**
     * Deleting a column with merged cells within.
     */
    public void deleteColumnWithMergedCellsTest() {
        final String SOURCE_FILE_NAME_TRUNC = "mergedCells";
        String firstEditOperations = "["
            + "{\"name\":\"deleteColumns\",\"start\":[0],\"startGrid\":1,\"endGrid\":1,\"osn\":36,\"opl\":1},{\"name\":\"setAttributes\",\"attrs\":{\"table\":{\"tableGrid\":[21845,21845]}},\"start\":[0],\"osn\":37,\"opl\":1}"
            + "]";
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), firstEditOperations);
    }

	@Test
	public void setFontName() {
		final String SOURCE_FILE_NAME_TRUNC = "feature_attributes_character_MSO15";
		final String SOURCE_FILE_NAME_SUFFIX = ".odt";

		List<String> editOperations = new ArrayList();
		String firstEditOperations = "[{\"name\":\"setAttributes\",\"start\":[3,12],\"end\":[3,16],\"attrs\":{\"character\":{\"fontName\":\"Impact\"}}}]";
		String secondEditOperations = "[{\"text\":\"Svante\",\"start\":[3,9],\"name\":\"insertText\"}]";

		editOperations.add(firstEditOperations);
		editOperations.add(secondEditOperations);
		super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, SOURCE_FILE_NAME_SUFFIX, getTestMethodName(), editOperations);
	}
}
