# Data extractor for the european e-invoice specifcation (en16931)
Open sourced for the creators of the e-invoice specification to allow easier sanity checks of the data within the tables to improve the quality of the specification.
Build as part of the [PrototypeFund project "paperless"](https://prototypefund.de/project/papierloser-alltag/) to generate larger parts of the software implementing the [European e-invoice specifcation (en16931)](https://invoice.fans/en/en16931-en/).

## Usage
1) Call 'mvn install' in the root directory of the project once, to build the ODF reader (ODFDOM library)
2) Create a test document by saving either the UBL or UN/CEFACT 16931-3 specification from DOCX as OpenDocument Text format (ODT) in the folder en16931-3-reader/src/test/resources
3) Provide its name in the test en16931-3-reader/src/test/java/de/prototypefund/en16931/ExtractionTest.java
4) Call 'mvn install' in the 'en16931-3-reader' folder
5) The extracted data can be found as XML files in the folder en16931-3-reader/target/test-classes

## Future features
a) Support of the Edifact format from 16931-3-4
b) Providing a JAR to be called by command-line taking the ODT spec document path as argument
c) Further documentation..