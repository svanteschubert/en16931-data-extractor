/************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0. You can also
 * obtain a copy of the License at http://odftoolkit.org/docs/license.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ************************************************************************/
package de.prototypefund;

import de.prototypefund.en16931.OdtTableExtraction;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides functionality by calling the JAR file.
 * This class adds metadata the the JAR manifest.
 * Metadata such as the libraries build date, version number and its
 * main() method as starting method of the library.
 * Providing all info by calling the JAR, e.g. via command line:
 * "java -jar en16931-data-extractor-<VERSION_INFO>-jar-with-dependencies.jar".
 * Triggering the data extraction by:
 * "java -jar en16931-data-extractor-<VERSION_INFO>-jar-with-dependencies.jar <DIRECTORY> | <SPECIFICATIONFILE>".
 */
public class JarFunctionality {

    private static final String CURRENT_CLASS_RESOURCE_PATH = "de/prototypefund/JarFunctionality.class";
    private static final String INNER_JAR_MANIFEST_PATH = "META-INF/MANIFEST.MF";
    private static String PROJECT_NAME;
    private static String PROJECT_VERSION;
    private static String PROJECT_WEBSITE;
    private static String PROJECT_BUILD_DATE;

    static {
        try {
            Manifest manifest = new Manifest(getManifestAsStream());
            Attributes attr = manifest.getEntries().get("en16931-data-extractor");
            // value is given from JAR's /META-INF/manifest.ml, which was filled
            // via Maven with the pom.xml variable,
            // e.g. <Project-Name>en16931-data-extractor</Project-Name>
            PROJECT_NAME = attr.getValue("Project-Name");
            PROJECT_VERSION = attr.getValue("Project-Version");
            PROJECT_WEBSITE = attr.getValue("Project-Website");
            PROJECT_BUILD_DATE = attr.getValue("Project-Built-Date");

        } catch (Exception e) {
            Logger.getLogger(JarFunctionality.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /** The problem is that in the test environment the class is NOT within the JAR, but in a class directory where no MANIFEST.MF exists..*/
    private static InputStream getManifestAsStream() {
        String versionRef = JarFunctionality.class.getClassLoader().getResource(CURRENT_CLASS_RESOURCE_PATH).toString();
        String manifestRef = versionRef.substring(0, versionRef.lastIndexOf(CURRENT_CLASS_RESOURCE_PATH)) + INNER_JAR_MANIFEST_PATH;
        URL manifestURL = null;
        InputStream in = null;
        try {
            manifestURL = new URL(manifestRef);
        } catch (MalformedURLException ex) {
            Logger.getLogger(JarFunctionality.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            in = manifestURL.openStream();
        } catch (IOException ex) {
            Logger.getLogger(JarFunctionality.class.getName()).log(Level.SEVERE, null, ex);
        }
        return in;
    }

    private JarFunctionality() {
    }

	/**
	 * The main method is meant to be called when the JAR is being executed,
	 * e.g. "java -jar en16931-data-extractor-<VERSION_INFO>-jar-with-dependencies.jar" and provides versioning information:
	 *
     *  en16931-data-extractor 1.0.0-SNAPSHOT (build 2019-01-20T19:29:17)
     * from https://github.com/svanteschubert/en16931-data-extractor
	 *
	 * Allowing version access from the JAR without the need to unzip the JAR nor naming the JAR
	 * (requiring the change of classpath for every version due to JAR naming change).
	 */
    public static void main(String[] args) {
        if(args == null || args.length == 0){
            System.out.println(getProjectTitle() + " (build " + getProjectBuildDate() + ')' + "\nfrom " + getProjectWebsite());
        }else if(args.length == 1){
            try {
                new OdtTableExtraction().collectSpecData(args[0]);
            } catch (Exception ex) {
                Logger.getLogger(JarFunctionality.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {
            for(String path : args){
                try {
                    new OdtTableExtraction().collectSpecData(path);
                } catch (Exception ex) {
                    Logger.getLogger(JarFunctionality.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


    /**
     * Return the project name
     * @return the project name
     */
    public static String getProjectName() {
        return PROJECT_NAME;
    }

    /**
     * Returns the ODFDOM library title
     *
     * @return A string containing both the name and the version of the ODFDOM library.
     */
    public static String getProjectTitle() {
        return getProjectName() + ' ' + getProjectVersion();
    }

    /**
     * Return the version of the library
     * @return the ODFDOM library version
     */
    public static String getProjectVersion() {
        return PROJECT_VERSION;
    }

    /**
     * Return the website of the library
     * @return the library website
     */
    public static String getProjectWebsite() {
        return PROJECT_WEBSITE;
    }

    /**
     * Return the date when the project had been build
     * @return the date of the build formatted as "yyyy-MM-dd'T'HH:mm:ss".
     */
    public static String getProjectBuildDate() {
        return PROJECT_BUILD_DATE;
    }

}
