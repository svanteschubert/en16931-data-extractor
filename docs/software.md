# Software Developer's View

## Building
1. git clone https://github.com/svanteschubert/en16931-data-extractor.git
2. Call 'mvn install' in the root directory of the project once, to build the [ODF reader (ODFDOM library)](https://github.com/svanteschubert/odftoolkit/tree/odf-changes/odfdom)</br>
   NOTE: This ODF library will become the 1.0.0 version of the [ODF Toolkit from "The Document Foundation"](https://github.com/tdf/odftoolkit), but copied as yet no Maven artifacts are available for download.

## Running as Test (e.g. debug)
1. Create a test document by saving either the UBL or UN/CEFACT 16931-3 specification from original DOCX to OpenDocument Text format (ODT) format into the folder en16931-3-reader/src/test/resources.
(e.g. using LibreOffice 6.2.5.2 on Ubuntu 19.04 (disco) via commandline:
'libreoffice --headless --convert-to odt *.docx')
2. Provide its name in the test file: en16931-3-reader/src/test/java/de/prototypefund/en16931/ExtractionTest.java
3. Call via command line 'mvn install' in the 'en16931-3-reader' (or root) folder (or use an IDE like [Netbeans](https://netbeans.apache.org/download/), [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)).</br>
   You may also find the new JAR in the "target" directory and use it as explained to the "technical writer" before.
   After the JUnit tests there will be a test log file "target/test.log".

## Architecture
The Data Extractor for the European e-Invoice Specification (en16931) consists of two modules:
1. The [ODFDOM library](https://tdf.github.io/odftoolkit/docs/odfdom/), which unzips the OpenDocument Text document, access the XML, i.e. the content.xml file.
2. The EN16931 reader, which uses ODFDOM to find the tables an get accesss to the data of every cell of each table row.
You may find an overview over the Java classes in the [JavaDoc](docs/apidocs/index.html).

## Deployment
1. The revision number - usually the date - is being set in the root [pom.xml](https://github.com/svanteschubert/en16931-data-extractor/blob/master/pom.xml).
2. [JavaDoc](https://svanteschubert.github.io/en16931-data-extractor/docs/apidocs/) and JAR are being copied from Maven's 'target' build directory into GitHubs 'docs' directory and become downloadable from the [project's GitHub website](https://svanteschubert.github.io/en16931-data-extractor/docs/)
3. Documentation files are in the [Maven specific src/site directory from the base project](https://github.com/svanteschubert/en16931-data-extractor/tree/master/src/site). The actual version number and JAR naming are being exchanged automatically during copy via Maven's resouce plugin.


