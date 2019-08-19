# Data Extractor for the European e-Invoice Specification (en16931)
## Summary
Open sourced for the creators of the EU e-invoice specification to allow easier sanity checks of the data within the tables to improve the quality of the specification.
Build as part of the [PrototypeFund project "paperless"](https://prototypefund.de/project/papierloser-alltag/) to generate larger parts of the software implementing the [European e-invoice specifcation (en16931)](https://invoice.fans/en/en16931-en/).

## Background on the EU e-Invoice Specification (en16931)
There is [a wonderful online introduction for en16931](https://ec.europa.eu/cefdigital/wiki/display/CEFDIGITAL/Compliance+with+eInvoicing+standard) given by the EU.

## Reason
The goal of this tool is to extract the syntax-binding (mapping) between the XML formats and the Semantic Data Model. The bindings are required to generate source code for a software allowing to load/save both XML formats. In addition the software shall modify/create XML by an API based on the Semantic Data Model of en16931.
Some reasons behind from a bird perspective:
<br/>There are three [graphs](https://en.wikipedia.org/wiki/Seven_Bridges_of_K%C3%B6nigsberg) within the EU specification:
1. graph: Semantic Data Model (describes the user semantic both XML are being mapped upon)
2. graph: UBL XML W3C XML grammar (describes all allowed XML files of UBL)
3. graph: UN/CEFACT W3C XML grammar (describes all allowed XML files of UN/CEFACT)

The syntax bindings - this tool is extracting - are contecting "graph 2" with "graph 1" and "graph 2" with "graph 3".
In addition, aside of the grammar there are more restrictions upon the XML that W3C Schema is not able to express ([given by ISO Schematron constraints](https://github.com/CenPC434/validation)), for instance an order date has to be earlier than the pay date.
These schematron restrictions can be seen as additional relations upon the XML grammar graphs (graph 2 & graph 3).
It is planned to map those XML constraints later to the "Semantic Data model" level (graph 1). By this it could be validated if there are constraints only for UBL or UN/CEFACT and missing for the other XML.
Also the same restrictions on the semantic graph might be reused for other older e-invoice formats to be mapped to the Semantic Data Model, in other words the validation artefacts could be easier reused.

## XML Syntax Binding of en16931
The EU e-invoice specification demands the support of two XML file formats [OASIS UBL 1.2](http://docs.oasis-open.org/ubl/UBL-2.1.html) and [UN/CEFACT XML Industry Invoice D16B](https://www.unece.org/cefact/xml_schemas/index).
In its 3rd, part the EU specification binds the XML syntax to the EU e-invoice semantic. For each syntax exist a document with at least two mapping table.
The first normative table (see "Table 2" below) describe the syntax binding from the semantic (light grey) to XML (dark grey), the second informative table (see Table 3 below) describes it the other way around from XML (dark grey) to semantic (light grey).
![Two example tables for UN/CEFACT](docs/resources/3-3-both-tables.png)
The informative table does not add any new information. It starts with the XML part (dark grey), but uses only two of the five XML attributes from the prior normative table.
In theory, the comparison of both tables should provide the same data.

## en16931 Data Reader
The data of the above mapping tables is being read from the tables.
The XML part (dark grey) is loaded as XMLNode object.
The semantic part (light grey) is loaded as SemanticNode object.
The data extractor saves the model of each table in its own XML file to ease reading the data.
The own XML file consists of a sequence of semantic elements containing the XML syntax elements.
The normative tables is being saved twice, once with all information and a second time as subset equal to the informative table, making file comparison easier.
The name of our own XML files is a combination of: "specification document name" + "table name" + ".xml"
More about the used class representation in the [JavaDoc](docs/apidocs/index.html).
After the JUnit tests there will be a test log file "target/test.log".

## Software Prerequisites
1. [>=JDK 9](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
2. [Apache Maven](https://maven.apache.org/download.cgi?Preferred=ftp://mirror.reverse.net/pub/apache/)

## Data Extraction by Technical Writer
1. Download the JAR with all dependencies includes [en16931-data-extractor-20190817-jar-with-dependencies.jar](docs/en16931-data-extractor-20190817-jar-with-dependencies.jar)
2. To see version information via command-line call:<br/>"java -jar [en16931-data-extractor-20190817-jar-with-dependencies.jar](docs/en16931-data-extractor-20190817-jar-with-dependencies.jar)"
3. Safe the DOCX documents of en16931-3 to ODT
4. To extract data from the specification via command-line call:<br/>"java -jar [en16931-data-extractor-20190817-jar-with-dependencies.jar](docs/en16931-data-extractor-20190817-jar-with-dependencies.jar) your-specification.odt or your-directory"

## Data Extraction by Software Developer
1. Call 'mvn install' in the root directory of the project once, to build the [ODF reader (ODFDOM library)](https://github.com/svanteschubert/odftoolkit/tree/odf-changes/odfdom) NOTE: This might take about 15min.</br>
   NOTE: This ODF library will become the 1.0.0 version of the [ODF Toolkit from "The Document Foundation"](https://github.com/tdf/odftoolkit), but yet no Maven artifacts are available for download.
2. Create a test document by saving either the UBL or UN/CEFACT 16931-3 specification from original DOCX to OpenDocument Text format (ODT) format into the folder en16931-3-reader/src/test/resources
3. Provide its name in the test file: en16931-3-reader/src/test/java/de/prototypefund/en16931/ExtractionTest.java
4. Call via command line 'mvn install' in the 'en16931-3-reader' folder (or use an IDE like [Netbeans](https://netbeans.apache.org/download/nb90/), [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)).</br>
   You may also find the new JAR in the "target" directory and use it as explained to the "technical writer" before.

## Data Analysis
5. The extracted data can be found as XML files aside the input documents (the example ODT is in the folder en16931-3-reader/target/test-classes).
6. Use a text comparing tool like [Total Commander on Windows](https://www.ghisler.com/download.htm) to find any differences between the ["informative"](docs/resources/16931-3-3_example_informative.xml) and the ["normative SUBSET"](docs/resources/16931-3-3_example_SUBSETnormative.xml) XML files.
![In our example only the title is different between the two tables](docs/resources/TotalCommanderComparison.png)
