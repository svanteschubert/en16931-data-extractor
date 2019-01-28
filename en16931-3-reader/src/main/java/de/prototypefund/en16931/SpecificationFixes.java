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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In order to be able to generate software from the data model, there have to
 * be no ambiguities in the model. In a such a model there are two restrictions:
 * <ul>
 * <li>Every semantic node has its own unique syntax nodes. (Or context
 * information are being kept.)</li>
 * <li>Every semantic node is unique in the Semantic Data Model. (Or context
 * information are being kept.)</li>
 * </ul>
 * In cases, the specification does not provide this condition, it will be
 * updated by this class.
 */
class SpecificationFixes {

    private static final Logger LOG = LoggerFactory.getLogger(SpecificationFixes.class.getName());
    // Every table has its own fixes
    static Map<String, Map<String, String>> mAllFixes = new HashMap<>(1);
    private static Map<String, String> mFixes_By_Table = new HashMap<>(1);
    // The context of fixes is its table defined by its heading prior to the table!
    private static final String TABLE_NO2_HEADER_CII_NORMATIVE = "Table 2—Semantic model to UN/CEFACT syntax elements mapping (normative)";
    private static final String TABLE_NO3_HEADER_CII_INFORMATIVE = "Table 3—UN/CEFACT syntax elements to semantic model mapping (informative)";
    private static final String TABLE_NO2_HEADER_CII_NORMATIVE_DE = "Table 2—Semantic model to UN/CEFACT syntax elements mapping (normative)";
    private static final String TABLE_NO3_HEADER_CII_INFORMATIVE_DE = "Table 3—UN/CEFACT syntax elements to semantic model mapping (informative)";
    private static final String TABLE_NO3_HEADER_UBL_INVOICE_NORMATIVE = "Table 3 — Semantic model to UBL invoice syntax elements mapping (normative)";
    private static final String TABLE_NO5_HEADER_UBL_INVOICE_INFORMATIVE = "Table 5 — Semantic model to UBL credit note syntax elements mapping (normative)";
    private static final String TABLE_NO5_HEADER_UBL_CREDIT_NOTE_NORMATIVE_DE = "Tabelle 5 — Mapping des Semantischen Modells auf die UBL Gutschriftsyntaxelemente (normativ)";
    private static final String TABLE_NO5_HEADER_UBL_CREDIT_NOTE_INFORMATIVE_DE = "Tabelle 6 — Mapping der UBL Gutschriftsyntaxelemente auf das Semantische Modell";
    private static final String TABLE_HEADER_UBL_MAPPING_DES_RECHNUNGMODELLS_DE = "Mapping des Rechnungsmodells";
    private static final String TABLE_HEADER_UBL_MAPPING_DES_GUTSCHRIFTENMODELLS_DE = "Mapping des Gutschriftenmodells";
    private static List<NodeSyntax> mXmlNodes = new ArrayList<NodeSyntax>(1);
    private static List<NodeSemantic> mSemanticNodes = new ArrayList<NodeSemantic>(1);
    static Boolean hasError = Boolean.FALSE;
    static final String ID_ERROR_MIDSTRING = "_DUPLICATED_ID_#";
    static Map<String, Integer> mDoubleIdCounter = new HashMap<>();

    static {

        // **** FIXES ****
        // 16931-3-3 - Duplicate semantic ID
        // Seller has one ID, but buyer two for the similar XML nodes!
        //	BG-5 /rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty
        //	BG-5 /rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:PostalTradeAddress
        //
        //	BG-7 /rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty
        //	BG-8 /rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:PostalTradeAddress
        mFixes_By_Table.put("BG-5", "BG-4");
        mAllFixes.put(TABLE_NO2_HEADER_CII_NORMATIVE, mFixes_By_Table); // Every table has its own fixes!
        mAllFixes.put(TABLE_NO3_HEADER_CII_INFORMATIVE, mFixes_By_Table); // Every table has its own fixes!
        mFixes_By_Table.put("BG-5", "BG-4");
        mAllFixes.put(TABLE_NO2_HEADER_CII_NORMATIVE_DE, mFixes_By_Table); // Every table has its own fixes - here for German translation
        mAllFixes.put(TABLE_NO3_HEADER_CII_INFORMATIVE_DE, mFixes_By_Table); // Every table has its own fixes - here for German translation

        // **** FIXES ****
        // 16931-3-2 - Duplicate semantic ID
        // SEPA might belong to seller or payer. Depends on its context.
        // Unique banking reference identifier of the Payee or Seller assigned by the Payee or Seller bank.
        //	BT-90  /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID
        //	BT-90  /Invoice/cac:AccountingSupplierParty/cac:Party/cac:PartyIdentification/cbc:ID
        //
        mFixes_By_Table.put("BT-90", "BT-90-Payee");
        mAllFixes.put(TABLE_NO3_HEADER_UBL_INVOICE_NORMATIVE, mFixes_By_Table); // Every table has its own fixes!
        mAllFixes.put(TABLE_NO5_HEADER_UBL_INVOICE_INFORMATIVE, mFixes_By_Table); // Every table has its own fixes!
        mAllFixes.put(TABLE_NO5_HEADER_UBL_CREDIT_NOTE_NORMATIVE_DE, mFixes_By_Table); // Every table has its own fixes - here for German translation
        mAllFixes.put(TABLE_NO5_HEADER_UBL_CREDIT_NOTE_INFORMATIVE_DE, mFixes_By_Table); // Every table has its own fixes - here for German translation
        mAllFixes.put(TABLE_HEADER_UBL_MAPPING_DES_RECHNUNGMODELLS_DE, mFixes_By_Table); // Every table has its own fixes - here for German translation
        mAllFixes.put(TABLE_HEADER_UBL_MAPPING_DES_GUTSCHRIFTENMODELLS_DE, mFixes_By_Table); // Every table has its own fixes - here for German translation
    }

    static void flushErrors(NodeSemantic s) {
        duplicatedIdErrors(s);

        clear();
    }

    static private void duplicatedIdErrors(NodeSemantic s) {
        //
        if (s.mWARNING_FixUnavailable || s.mWARNING_FixAlreadyTaken) {
            LOG.info("WARNING: Duplicated SemanticNode ID: '" + s.getId() + "'\n");
            LOG.info("       Semantic Node ID: '" + s.getId() + "'\n");
            LOG.info("       Business Term: '" + s.getBusinessTerm() + "'\n\n");

        }
    }

    static String getAlternativeID(String doubledId) {
        String alternativeId = null;
        Integer idCounter = mDoubleIdCounter.get(doubledId);
        if (idCounter == null) {
            idCounter = 1;
        }
        // start with 2 and keep the first ID without number..
        idCounter++;
        mDoubleIdCounter.put(doubledId, idCounter);
        alternativeId = doubledId + ID_ERROR_MIDSTRING + String.valueOf(idCounter);
        return alternativeId;
    }

    static private void clear() {
        mXmlNodes.clear();
        mSemanticNodes.clear();
        hasError = Boolean.FALSE;
    }
}
