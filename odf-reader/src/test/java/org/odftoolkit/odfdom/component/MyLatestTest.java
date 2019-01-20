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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.odftoolkit.odfdom.component.RoundtripTestHelper.OUTPUT_DIRECTORY;
import org.odftoolkit.odfdom.utils.ResourceUtilities;

/**
 *
 * For developer: Temporary Test document for quick tests! :D
 * Changes not relevant, might be added to .git ignore list of project.
 *
 * Loads a document with tables and gathers its operations. Gathered operations
 * will be applied to an empty text document. The changed text document will be
 * saved and reloaded. New gathered operations will be compared with the
 * original ones, expected to be identical!
 *
 * @author svanteschubert
 */
public class MyLatestTest extends RoundtripTestHelper {

    private static final Logger LOG = Logger.getLogger(MyLatestTest.class.getName());

    public MyLatestTest() {
    }

    @BeforeClass
    @Ignore
    public static void setUpBeforeClass() throws Exception {
        // Creating the output directory for the tests
        File outputDir = ResourceUtilities.newTestOutputFile(OUTPUT_DIRECTORY);
        outputDir.mkdir();
    }

    @Test
    /**
     * Foreign ODF elements could not be cloned. Throwable are now caught and
     * rethrown as filterexception to be found by Admin logging -- adding
     * regression test references
     */
    public void testRDF() {
        final String SOURCE_FILE_NAME_TRUNC = "test_rdfmeta";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            //+ "{\"start\":[1,9,1,1,2],\"name\":\"splitParagraph\",\"opl\":1,\"osn\":495}"
            + "]";

        editOperations.add(firstEditOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, getTestMethodName(), editOperations);
    }


    @Test
    @Ignore
    public void myTest() {
        final String SOURCE_FILE_NAME_TRUNC = "empty.odt";
        List<String> editOperations = new ArrayList();
        String firstEditOperations = "["
            //				+ "{\"to\":[2,0],\"start\":[1,0],\"name\":\"move\",\"end\":[1,0]}"
            //				+ ",{\"start\":[2,0],\"attrs\":{\"drawing\":{\"anchorVertOffset\":2763,\"anchorHorBase\":\"column\",\"anchorHorOffset\":4710,\"anchorHorAlign\":\"offset\",\"anchorVertAlign\":\"offset\",\"anchorVertBase\":\"paragraph\"}},\"name\":\"setAttributes\"},{\"start\":[2,0],\"attrs\":{\"drawing\":{\"anchorVertOffset\":529,\"anchorHorBase\":\"column\",\"anchorHorOffset\":4763,\"anchorHorAlign\":\"offset\",\"anchorVertAlign\":\"offset\",\"anchorVertBase\":\"paragraph\"}},\"name\":\"setAttributes\"},{\"start\":[2,0],\"attrs\":{\"drawing\":{\"anchorVertOffset\":2487,\"anchorHorBase\":\"column\",\"anchorHorOffset\":5372,\"anchorHorAlign\":\"offset\",\"anchorVertAlign\":\"offset\",\"anchorVertBase\":\"paragraph\"}},\"name\":\"setAttributes\"},{\"start\":[2,0],\"attrs\":{\"drawing\":{\"inline\":true}},\"name\":\"setAttributes\"},{\"start\":[2,0],\"attrs\":{\"drawing\":{\"inline\":true}},\"name\":\"setAttributes\"},{\"start\":[2,0],\"attrs\":{\"drawing\":{\"anchorHorOffset\":0,\"textWrapMode\":\"topAndBottom\",\"anchorHorBase\":\"column\",\"inline\":false,\"anchorHorAlign\":\"center\"}},\"name\":\"setAttributes\"},{\"start\":[2,0],\"name\":\"delete\",\"end\":[2,0]},{\"start\":[5,0],\"attrs\":{\"drawing\":{\"anchorHorOffset\":0,\"textWrapMode\":\"square\",\"anchorHorBase\":\"column\",\"inline\":false,\"anchorHorAlign\":\"left\",\"textWrapSide\":\"right\"}},\"name\":\"setAttributes\"},{\"start\":[5,0],\"attrs\":{\"drawing\":{\"anchorHorOffset\":0,\"textWrapMode\":\"square\",\"anchorHorBase\":\"column\",\"inline\":false,\"anchorHorAlign\":\"right\",\"textWrapSide\":\"left\"}},\"name\":\"setAttributes\"},{\"start\":[5,0],\"attrs\":{\"drawing\":{\"inline\":true}},\"name\":\"setAttributes\"}"
            //				+ "{\"start\":[0,21],\"attrs\":{\"drawing\":{\"marginBottom\":317,\"height\":3307,\"marginLeft\":317,\"marginTop\":317,\"imageUrl\":\"Pictures/uid102dced7ccfc.jpg\",\"marginRight\":317,\"width\":3307}},\"name\":\"insertDrawing\",\"type\":\"image\"}"
            //+ "{\"start\":[28,0],\"name\":\"delete\",\"end\":[28,79]},"
            //				+ "{\"start\":[27],\"name\":\"delete\"},"
            //				+ "{\"start\":[26],\"name\":\"delete\"},"
            //				+ "{\"start\":[25],\"name\":\"delete\"},{\"start\":[24],\"name\":\"delete\"},{\"start\":[23],\"name\":\"delete\"},{\"start\":[22],\"name\":\"delete\"},{\"start\":[21],\"name\":\"delete\"},{\"start\":[20],\"name\":\"delete\"},{\"start\":[19],\"name\":\"delete\"},{\"start\":[18],\"name\":\"delete\"},{\"start\":[17],\"name\":\"delete\"},{\"start\":[16],\"name\":\"delete\"},{\"start\":[15],\"name\":\"delete\"},{\"start\":[14],\"name\":\"delete\"},{\"start\":[13],\"name\":\"delete\"},{\"start\":[12],\"name\":\"delete\"},{\"start\":[11],\"name\":\"delete\"},{\"start\":[10],\"name\":\"delete\"},{\"start\":[9],\"name\":\"delete\"},{\"start\":[8],\"name\":\"delete\"},{\"start\":[7],\"name\":\"delete\"},{\"start\":[6],\"name\":\"delete\"},{\"start\":[5],\"name\":\"delete\"},{\"start\":[4],\"name\":\"delete\"},{\"start\":[3],\"name\":\"delete\"},{\"start\":[2],\"name\":\"delete\"},{\"start\":[1],\"name\":\"delete\"},{\"start\":[0,0],\"name\":\"delete\",\"end\":[0,72]},{\"start\":[0],\"name\":\"mergeParagraph\"}"
            + "]";

        editOperations.add(firstEditOperations);
//		super.roundtripOnlyToEmptyDocRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt");
//      super.importOnlyRegressionTest(SOURCE_FILE_NAME_TRUNC, ".odt", getTestMethodName(), editOperations);
        super.roundtripRegressionTextTest(SOURCE_FILE_NAME_TRUNC, "", editOperations);
        //super.roundtripRegressionSpreadsheetTest(SOURCE_FILE_NAME_TRUNC, "", editOperations);
    }
}
