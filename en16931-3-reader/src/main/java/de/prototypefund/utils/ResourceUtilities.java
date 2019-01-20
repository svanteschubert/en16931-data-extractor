/**
 * **********************************************************************
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 *
 * Copyright 2008, 2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Use is subject to license terms.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0. You can also
 * obtain a copy of the License at http://odftoolkit.org/docs/license.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ***********************************************************************
 */
package de.prototypefund.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test utility class providing resources for the test in- and output
 */
public final class ResourceUtilities {

    private static final String PATH_FROM_OUTPUT_TO_INPUT = "../../src/test/resources/";

    private ResourceUtilities() {
    }

    /**
     * The relative path of the test file will be resolved and the absolute will
     * be returned
     *
     * @param relativeFilePath Path of the test resource relative to
     * <code>src/test/resource/</code>.
     * @return the absolute path of the test file
     * @throws FileNotFoundException If the file could not be found
     */
    public static String getAbsolutePath(String relativeFilePath) throws FileNotFoundException {
        URI uri = null;
        try {
            uri = ResourceUtilities.class.getClassLoader().getResource(relativeFilePath).toURI();
        } catch (URISyntaxException ex) {
            Logger.getLogger(ResourceUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (uri == null) {
            throw new FileNotFoundException("Could not find the file '" + relativeFilePath + "'!");
        }
        return uri.getPath();
    }

    /**
     * The relative path of the test file will be resolved and the absolute will
     * be returned
     *
     * @param relativeFilePath Path of the test resource relative to
     * <code>src/test/resource/</code>.
     * @return the URI created based on the relativeFilePath
     * @throws URISyntaxException if no URI could be created from the given
     * relative path
     */
    public static URI getURI(String relativeFilePath) throws URISyntaxException {
        String filePath = "file:" + ResourceUtilities.class.getClassLoader().getResource(relativeFilePath).getPath();
        return new URI(filePath);
        //return ResourceUtilities.class.getClassLoader().getResource(relativeFilePath).toURI();
    }

    /**
     * The relative path of the test file will be used to determine an absolute
     * path to a temporary directory in the output directory.
     *
     * @param relativeFilePath Path of the test resource relative to
     * <code>src/test/resource/</code>.
     * @return absolute path to a test output
     * @throws IOException if no absolute Path could be created.
     */
    public static String getTestOutput(String relativeFilePath) throws IOException {
        return File.createTempFile(relativeFilePath, null).getAbsolutePath();
    }

    /**
     * The Input of the test file will be resolved and the absolute will be
     * returned
     *
     * @param relativeFilePath Path of the test resource relative to
     * <code>src/test/resource/</code>.
     * @return the absolute path of the test file
     */
    public static InputStream getTestResourceAsStream(String relativeFilePath) {
        return ResourceUtilities.class.getClassLoader().getResourceAsStream(relativeFilePath);
    }

    /**
     * Relative to the test output directory (ie. "target/test-classes") a test
     * file will be returned dependent on the relativeFilePath provided.
     *
     * @param relativeFilePath Path of the test output resource relative to
     * <code>target/test-classes/</code>.
     * @return the empty <code>File</code> of the test output (to be filled)
     */
    public static File newTestOutputFile(String relativeFilePath) {
        String filepath = null;
        try {
            filepath = ResourceUtilities.class.getClassLoader().getResource("").toURI().getPath() + relativeFilePath;
        } catch (URISyntaxException ex) {
            Logger.getLogger(ResourceUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new File(filepath);
    }

    /**
     * Relative to the test input directory (ie. "src/test/resources") a test
     * file will be returned dependent on the relativeFilePath provided.
     *
     * @param relativeFilePath Path of the test output resource relative to
     * <code>target/test-classes/</code>.
     * @return the empty <code>File</code> of the test output (to be filled)
     */
    public static File getReferenceFile(String relativeFilePath) {
        String filepath = null;
        try {
            filepath = ResourceUtilities.class.getClassLoader().getResource("").toURI().getPath() + PATH_FROM_OUTPUT_TO_INPUT + relativeFilePath;
        } catch (URISyntaxException ex) {
            Logger.getLogger(ResourceUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new File(filepath);
    }

    /**
     * @return the absolute path of the test output folder, which is usually
     * <code>target/test-classes/</code>.
     */
    public static String getTestOutputFolder() {
        String testFolder = null;
        try {
            testFolder = ResourceUtilities.class.getClassLoader().getResource("").toURI().getPath();
        } catch (URISyntaxException ex) {
            Logger.getLogger(ResourceUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return testFolder;
    }

    /**
     * Relative to the test input directory (ie. "src/test/resources") a test
     * file will be returned dependent on the relativeFilePath provided.
     *
     * @param relativeFilePath Path of the test output resource relative to
     * <code>target/test-classes/</code>.
     * @return the empty <code>File</code> of the test output (to be filled)
     */
    public static File getTestReferenceFolder() {
        String filepath = null;
        try {
            filepath = ResourceUtilities.class.getClassLoader().getResource("").toURI().getPath() + PATH_FROM_OUTPUT_TO_INPUT;
        } catch (URISyntaxException ex) {
            Logger.getLogger(ResourceUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new File(filepath);
    }

    /*
	 * @param file the file to be saved, when creating a test file, you might use <code>newTestOutputFile(String relativeFilePath)</code>.
	 * @param inputData the data to be written into the file
     * @return absolute file path of new output file
     */
    public static String saveStringToFile(File file, String data) {
        return saveStringToFile(file, Charset.forName("UTF-8"), data);
    }

    /**
     * @param file the file to be saved, when creating a test file, you might
     * use <code>newTestOutputFile(String relativeFilePath)</code>.
     * @param charset the character encoding
     * @param inputData the data to be written into the file
     * @return absolute file path of new output file
     */
    public static String saveStringToFile(File file, Charset charset, String inputData) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
            //out = new BufferedWriter(new FileWriter(file));
            out.write(inputData);
        } catch (IOException ex) {
            Logger.getLogger(ResourceUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ResourceUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return file.getAbsolutePath();
    }

    /**
     * @param file the file to be loaded, when accessing a test file, you might
     * use <code>newTestOutputFile(String relativeFilePath)</code>.
     * @return the data from the given file	as a String
     */
    public static String loadFileAsString(File file) {
        FileInputStream input = null;
        String result = null;
        try {
            input = new FileInputStream(file);
            byte[] fileData = new byte[input.available()];
            input.read(fileData);
            input.close();
            result = new String(fileData, "UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ResourceUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ResourceUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(ResourceUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    /**
     * @param file the file to be loaded, when accessing a test file, you might
     * use <code>newTestOutputFile(String relativeFilePath)</code>.
     * @return the data from the given file	as a byte array
     */
    public static byte[] loadFileAsBytes(File file) throws IOException {
        // Open the file
        RandomAccessFile f = new RandomAccessFile(file, "r");
        try {
            // check length
            long longlength = f.length();
            int length = (int) longlength;
            if (length != longlength) {
                throw new IOException("File size >= 2 GB");
            }
            // Read file and return data
            byte[] data = new byte[length];
            f.readFully(data);
            return data;
        } finally {
            f.close();
        }
    }

    /**
     * Returns the JSONObject as String with all characters over 127 encoded as
     * escaped unicode, e.g. as "\u00AB"
     */
    public static String encodeInAscii(String encodedString) {
        StringBuilder output = new StringBuilder(encodedString.length());
        char[] charArray = encodedString.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if ((int) c > 127) {
                encodedString = "000" + Integer.toHexString((int) c).toUpperCase();
                output.append("\\u").append(encodedString.substring(encodedString.length() - 4));
            } else {
                output.append(c);
            }
        }
        return output.toString();
    }

    static final String INITIAL_OPS_SUFFIX = "-initial_ops.txt";
    static final String RELOADED_OPS_SUFFIX = "-reloaded_ops.txt";
    static final String OUTPUT_DIRECTORY = "regression-tests" + File.separatorChar;
    static final String HYPEN = "-";
    static final String ODT_SUFFIX = ".odt";
    static final String ODS_SUFFIX = ".ods";
    static final List<String> NO_OPERATIONS = new ArrayList<String>(0);
    static final String NO_METHOD_NAME = "";
    // the smallest possible test document of this ODF type. Edited manually and proofed valid by Apache ODF Validator.
    static final String EMPTY_AS_CAN_BE = "empty_as_can_be";
//
//    public static void main(String[] args) {
//        normalizeFilesfromFolderToFolder(ResourceUtilities.getReferenceFile("regression-tests" + File.separatorChar + "2BeTransformed"), ResourceUtilities.getReferenceFile("regression-tests" + File.separatorChar + "2BeTransformed" + File.separatorChar + "Normalized"));
//    }
//
//    public static void normalizeFilesfromFolderToFolder(final File sourceFolder, final File targetFolder) {
//        String jsonFileAsString = null;
//        for (final File fileEntry : sourceFolder.listFiles()) {
//            if (fileEntry.isDirectory()) {
//                System.err.println("Opening Subdirectory: " + fileEntry.getName());
//            } else if (fileEntry.getName().endsWith(".txt")) {
//                System.err.println("Normalizing file: " + fileEntry.getName());
//                jsonFileAsString = ResourceUtilities.loadFileAsString(fileEntry);
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(new JSONTokener(jsonFileAsString));
//                    ResourceUtilities.saveStringToFile(ResourceUtilities.newRefOutputFile(targetFolder, fileEntry.getName()), JsonOperationNormalizer.asString(jsonObject).replace(",{\"name\"", ",\n{\"name\""));
//                } catch (JSONException ex) {
//                    System.err.println("Problem file: " + fileEntry.getName());
//                    Logger.getLogger(ResourceUtilities.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//    }

    /**
     * Relative to the test output directory (ie. "target/test-classes") a test
     * file will be returned dependent on the relativeFilePath provided.
     *
     * @param targetFolder
     * @param relativeFilePath Path of the test output resource relative to
     * <code>target/test-classes/</code>.
     * @return the empty <code>File</code> of the test output (to be filled)
     */
    public static File newRefOutputFile(File targetFolder, String relativeFilePath) {
        String filepath = null;
        filepath = targetFolder.getAbsolutePath() + File.separatorChar + relativeFilePath;
        return new File(filepath);
    }

    /**
     * Escape a string for use inside as XML single-quoted attributes. This
     * escapes less-than, single-quote, ampersand, and (not strictly necessary)
     * newlines.
     */
    public static String xmlSingleQuotedEscape(String s) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        switch (c) {
          case '\'': sb.append("&quot;"); break;
          case '&': sb.append("&amp;"); break;
          case '<': sb.append("&lt;"); break;
          case '\n': sb.append("&#xA;"); break;
          case '"': sb.append("&#xA;"); break;

          case '\000': case '\001': case '\002': case '\003': case '\004':
          case '\005': case '\006': case '\007': case '\010': case '\013':
          case '\014': case '\016': case '\017': case '\020': case '\021':
          case '\022': case '\023': case '\024': case '\025': case '\026':
          case '\027': case '\030': case '\031': case '\032': case '\033':
          case '\034': case '\035': case '\036': case '\037':
            // do nothing, these are disallowed characters
            break;
          default:   sb.append(c);
        }
      }
      return sb.toString();
    }
}
