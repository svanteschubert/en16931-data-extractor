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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import static org.odftoolkit.odfdom.component.OperationConstants.DEBUG_OPERATIONS;
import static org.odftoolkit.odfdom.component.OperationConstants.MAX_SHEETS;
import static org.odftoolkit.odfdom.component.OperationConstants.MAX_TABLE_CELLS;
import static org.odftoolkit.odfdom.component.OperationConstants.MAX_TABLE_COLUMNS;
import static org.odftoolkit.odfdom.component.OperationConstants.MAX_TABLE_ROWS;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.OdfSchemaDocument.OdfXMLFile;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * This is a prototype of a new high level view text document. Methods might be
 * better bIt will be added
 *
 * @author svante.schubertATgmail.com
 */
public class OdfOperationDocument {

    private static final Logger LOG = LoggerFactory.getLogger(OdfOperationDocument.class);
//	private static final Logger LOG = Logger.getLogger(OdfOperationDocument.class.getName());
    // not private as used as well by tests
    static final String OPERATION_REVISON_FILE = "debug/revision.txt";
    static final String OPERATION_TEXT_FILE_PREFIX = "debug/operationUpdates_";
    static final String ORIGNAL_ODT_FILE = "debug/original.odt";



    /**
     * Only being used when a certain test (my workbench test, which is not part
     * of the regression testing) is being used
     */
    private static final String OPERATION_DEBUG_OUTPUT_FILE = System.getProperty("java.io.tmpdir") + File.separatorChar +  "odf-operations.txt";
    private OdfDocument mDocument;
    private OdfPackage mPackage;
    private Map<Long, byte[]> mResourceMap;
    private boolean mSaveDebugOperations;
    private int mMaxTableColumnCount;
    private int mMaxTableRowCount;
    private int mMaxTableCellCount;
    private int mMaxSheetCount;
    private boolean isMetadataUpdated = false;
    private int successfulAppliedOperations = 0;

    /**
     * Creates an empty ODF document.
     */
    private OdfOperationDocument() {
    }

    // to be ... depending to the target the owner document might be also the StylesDom
    public OdfFileDom getOwnerDocument()
    	throws SAXException, IOException {

    	return getDocument().getContentDom();
    }

    /**
     * Creates a new ODT document from the default template
     */
    public static OdfOperationDocument newTextOperationDocument() throws Exception {
        OdfOperationDocument odt = new OdfOperationDocument();
        odt.mDocument = OdfTextDocument.newTextDocument();
        return odt;
    }

    /**
     * Creates a new ODS document from the default template
     */
    public static OdfOperationDocument newSpreadsheetOperationDocument() throws Exception {
        OdfOperationDocument ods = new OdfOperationDocument();
        ods.mDocument = OdfSpreadsheetDocument.newSpreadsheetDocument();
        return ods;
    }

    /**
     * Creates an OdfOperationDocument from the OpenDocument provided by a
     * resource Stream.
     *
     * <p>
     * Since an InputStream does not provide the arbitrary (non sequential) read
     * access needed by OdfOperationDocument, the InputStream is cached. This
     * usually takes more time compared to the other createInternalDocument
     * methods. An advantage of caching is that there are no problems
     * overwriting an input file.</p>
     *
     * @param inputStream - the InputStream of the ODF text document.
     * @return the text document created from the given InputStream
     */
    public OdfOperationDocument(InputStream aInputDocumentStm) throws Exception {
        mDocument = OdfDocument.loadDocument(aInputDocumentStm);
    }

    /**
     * Creates an OdfOperationDocument from the OpenDocument provided by a
     * resource Stream.
     *
     * <p>
     * Since an InputStream does not provide the arbitrary (non sequential) read
     * access needed by OdfOperationDocument, the InputStream is cached. This
     * usually takes more time compared to the other createInternalDocument
     * methods. An advantage of caching is that there are no problems
     * overwriting an input file.</p>
     *
     * @param configuration - key/value pairs of user given run-time settings
     * (configuration)
     *
     *
     * @param documentStream - the InputStream of the ODF text document.
     */
    public OdfOperationDocument(InputStream documentStream, Map<String, Object> configuration) throws Exception {
        this(documentStream, null, configuration);
    }

    /**
     * Creates an OdfOperationDocument from the OpenDocument provided by a
     * resource Stream.
     *
     * <p>
     * Since an InputStream does not provide the arbitrary (non sequential) read
     * access needed by OdfOperationDocument, the InputStream is cached. This
     * usually takes more time compared to the other createInternalDocument
     * methods. An advantage of caching is that there are no problems
     * overwriting an input file.</p>
     *
     * @param inputStream - the InputStream of the ODF text document.
     * @param resourceMap - the bytes of new resources can be accessed by an ID.
     * @param configuration - key/value pairs of user given run-time settings
     * (configuration)
     * @throws java.lang.Exception document could not be opened
     */
    public OdfOperationDocument(InputStream inputStream, Map<Long, byte[]> resourceManager, Map<String, Object> configuration) throws Exception {
        mDocument = OdfDocument.loadDocument(inputStream, configuration);
        mPackage = getDocument().getPackage();
        mResourceMap = resourceManager;
        if (configuration != null) {
            if (configuration.containsKey(DEBUG_OPERATIONS)) {
                mSaveDebugOperations = (Boolean) configuration.get(DEBUG_OPERATIONS);
            }
            if (configuration.containsKey(MAX_TABLE_COLUMNS)) {
                mMaxTableColumnCount = (Integer) configuration.get(MAX_TABLE_COLUMNS);
            }
            if (configuration.containsKey(MAX_TABLE_ROWS)) {
                mMaxTableRowCount = (Integer) configuration.get(MAX_TABLE_ROWS);
            }
            if (configuration.containsKey(MAX_TABLE_CELLS)) {
                mMaxTableCellCount = (Integer) configuration.get(MAX_TABLE_CELLS);
            }
            if (configuration.containsKey(MAX_SHEETS)) {
                mMaxSheetCount = (Integer) configuration.get(MAX_SHEETS);
            }
        }
    }

    public int getMaxTableColumnsCount() {
        return mMaxTableColumnCount;
    }

    public int getMaxTableRowsCount() {
        return mMaxTableRowCount;
    }

    public int getMaxTableCellCount() {
        return mMaxTableCellCount;
    }

    public int getMaxSheetCount() {
        return mMaxSheetCount;
    }


    /**
     * Receives the a map with new resources for the Document
     *
     * @return the operations as JSON
     */
    public Map<Long, byte[]> getResourceMap() {
    	if(mResourceMap!=null) {
    		return mResourceMap;
    	}
    	return null;
    }

    /**
     * Receives the (known) operations of the ODF text document
     *
     * @return the operations as JSON
     */
    public JSONObject getOperations()
    	throws SAXException, JSONException, IOException {

    	JSONObject ops = mDocument.getOperations(this);
        if (ops != null && ops.length() > 0) {
            LOG.debug("\n\n*** ALL OPERATIONS:\n{0}", ops.toString());
        } else {
            LOG.debug("\n\n*** ALL OPERATIONS:\nNo Operation have been extracted!");
        }
        return ops;
    }

	    /**
     * Applies the (known) operations to upon the latest state of the ODF text
     * document
     *
     * @param operationString ODF operations as String
     * @return the number of operations being accepted
     */
    public int applyOperations(String operationString) throws Exception {
        JSONObject operations = new JSONObject();
        try {
            operations.put("operations", new JSONArray(new JSONTokener(operationString)));
        } catch (Exception ex) {
            LOG.error(null, ex);
        }
        int operationsCount = this.applyOperations(operations);
        // if the document was altered
        if (operationsCount > 0) {
            // remove the cached view
            removeCachedView();
        }
        return operationsCount;
    }

    /**
     * Applies the (known) operations to upon the latest state of the ODF text
     * document
     *
     * @param operations ODF operations as JSONArray within an JSONObject with
     * "operations" key.
     * @return the number of operations being accepted
     */
    public int applyOperations(JSONObject operations) throws Exception {
        LOG.debug("\n*** EDIT OPERATIONS:\n{0}", operations.toString());
//      System.err.println("\n*** EDIT OPERATIONS:\n" + operations.toString());
        final JSONArray ops = operations.getJSONArray("operations");
        if (mSaveDebugOperations) {
            addOriginalOdfAsDebug();
            addOperationFileAsDebug(ops);
        }
        saveLocalDebug(ops);
        final int operationCount = getDocument().applyOperations(this, ops);
        if(operationCount > 0) {
	        // remove the cached view
	        removeCachedView();
	        if (!isMetadataUpdated) {
	            mDocument.updateMetaData();
	            isMetadataUpdated = true;
	        }
        }
        return operationCount;
    }

    private static void saveLocalDebug(JSONArray ops) {
        // only meant for local testing
        String unitTest = System.getProperty("test");
        if (unitTest != null && unitTest.equals("org.odftoolkit.odfdom.component.LatestOngoingTest")) {
            saveOperationAsDebugFile(ops, OPERATION_DEBUG_OUTPUT_FILE);
        }
    }

    private void removeCachedView() {
        if (mPackage == null) {
            mPackage = getDocument().getPackage();
        }
        // removes the LO/AO view caching
        mPackage.remove("Thumbnails/thumbnail.png");
    }

    private void addOriginalOdfAsDebug()
    		throws SAXException {
        OdfPackage pkg = mDocument.getPackage();
        // if there is not already an orignal file being stored
        if (!pkg.contains(ORIGNAL_ODT_FILE)) {
            LOG.debug("Adding original ODT document as debug within the zip at " + ORIGNAL_ODT_FILE);
            try {
                // ..from the ODF ZIP
                pkg.insert(pkg.getInputStream(), ORIGNAL_ODT_FILE, "application/vnd.oasis.opendocument.text");
            } catch (IOException ex) {
                LOG.error(null, ex);
            }
        }
    }

    /*
     * @param file the file to be saved, when creating a test file, you might use <code>newTestOutputFile(String relativeFilePath)</code>.
     * @param inputData the data to be written into the file
     */
    private static void saveStringToFile(File file, String data) {
        saveStringToFile(file, Charset.forName("UTF-8"), data);
    }

    /**
     * @param file the file to be saved, when creating a test file, you might
     * use <code>newTestOutputFile(String relativeFilePath)</code>.
     * @param charset the character encoding
     * @param inputData the data to be written into the file
     */
    private static void saveStringToFile(File file, Charset charset, String inputData) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
            //out = new BufferedWriter(new FileWriter(file));
            out.write(inputData);
            out.close();
        } catch (IOException ex) {
            LOG.error(null, ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                LOG.error(null, ex);
            }
        }
    }

    private static void saveOperationAsDebugFile(JSONArray operations, String debugFilePath) {
        // serialize the operations as String (using ascii characters only) and indent a line for every new operations (heuristic: every array item will be split into new line)
        if (debugFilePath != null && !debugFilePath.isEmpty()) {
            saveStringToFile(new File(debugFilePath), operations.toString());
        }
    }

    private void addOperationFileAsDebug(JSONArray operations) {
        // serialize the operations as String (using ascii characters only) and indent a line for every new operations (heuristic: every array item will be split into new line)
        try {
            OdfPackage pkg = mDocument.getPackage();
            // start with zero to always increment (either read a default by file or new)
            int revisionNo = 0;
            // if there was already a revision, get it..
            if (pkg.contains(OPERATION_REVISON_FILE)) {
                // ..from the ODF ZIP
                byte[] revisionByteArray = pkg.getBytes(OPERATION_REVISON_FILE);
                if (revisionByteArray != null && revisionByteArray.length != 0) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(revisionByteArray)));
                    // read the first line of the file, only containing one number
                    String firstLine = reader.readLine();
                    // map it to a number
                    revisionNo = Integer.parseInt(firstLine);
                    LOG.debug("Found an existing revision number:{0}", revisionNo);
                }
            } else {
                LOG.debug("Created a new revision number: 1");
            }
            // always increment, so even a new file starts with the revision number "1"
            revisionNo++;
            pkg.insert(operations.toString().getBytes(), OPERATION_TEXT_FILE_PREFIX + revisionNo + ".txt", "text/plain");
            pkg.insert(Integer.toString(revisionNo).getBytes(), OPERATION_REVISON_FILE, "text/plain");
        } catch (Exception ex) {
            LOG.error(null, ex);
        }
    }

    public long getContentSize() {
        if (mPackage == null) {
            if (mDocument != null) {
                mPackage = mDocument.getPackage();
            }
        }
        if (mPackage != null) {
            return mPackage.getSize(OdfXMLFile.CONTENT.getFileName());
        } else {
            return 0;
        }
    }

    /**
     * Returns the OdfTextDocument encapsulating the DOM view
     *
     * @return ODF text document
     */
    public OdfDocument getDocument() {
        return mDocument;
    }

    /**
     * Returns the OdfPackage
     *
     * @return ODF Package
     */
    public OdfPackage getPackage() {
        if(mPackage == null && mDocument != null) {
            mPackage = mDocument.getPackage();
        }
        return mPackage;
    }

    /**
     * Close the OdfPackage and release all temporary created data. After
     * execution of this method, this class is no longer usable. Do this as the
     * last action to free resources. Closing an already closed document has no
     * effect.
     */
    public void close() {
        mDocument.close();
    }

    void setSuccessfulAppliedOperations(int opCount) {
		successfulAppliedOperations = opCount;
	}

    /** @return number of correct applied operations */
    public int getSuccessfulAppliedOperations() {
		return successfulAppliedOperations;
	}
}
