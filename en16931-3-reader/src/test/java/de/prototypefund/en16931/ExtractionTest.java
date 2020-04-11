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
 *  software distributed under the License isx distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 *********************************************************************** */
package de.prototypefund.en16931;

import org.junit.Test;
import org.slf4j.LoggerFactory;

public class ExtractionTest {

    /* Might be a syntax binding specification document as ODT or
         a directory containg several ODT specification documents.
        Path can be relative to working directory "en16831-3-reader" or
        to the classpath "target/test-classes", where files from "src/test/resources" will be copied to! */
    private static final String odtResource =   "./"; // or only a single document via "16931-3-3_example.odt";

    @Test
    public void collectSpecData() throws Exception {
        try {
            new OdtTableExtraction().collectSpecData(odtResource);
        } catch (Throwable t) {
            LoggerFactory.getLogger(ExtractionTest.class.getName()).error("ERROR: " + t.getMessage(), t);
        }
    }
}
