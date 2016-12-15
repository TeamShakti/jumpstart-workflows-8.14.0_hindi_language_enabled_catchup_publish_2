package com.irdeto.jumpstart.workflow.ingest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.manager.xml.XmlManager;
import com.irdeto.manager.xml.XmlManagerImpl;

public class XmlValidateTest {
	private String expectedSerializedDataWrapperMulti;
	private String expectedSerializedDataWrapperXmlTv;
	private String expectedValidationSchemaXml;
	private String expectedValidationSchemaCore;
	private String expectedValidationSchemaContent;
	private String expectedValidationSchemaTitle;
	private String expectedValidationSchemaOffer;
	private String expectedValidationSchemaTerms;
	private String expectedValidationSchemaIrdeto;
	private String expectedValidationSchemaIrdetoTypes;
	private String expectedValidationSchemaXmlTv;
	
	public XmlValidateTest(){
		try {
			this.expectedSerializedDataWrapperMulti = FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/TVseries-TrueBlood_Season5_Episode1.xml")));
			this.expectedSerializedDataWrapperXmlTv = FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/channel-12702-epg_03112014.xml")));
			this.expectedValidationSchemaXml = FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/schema/xml.xsd")));
			this.expectedValidationSchemaCore = FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/schema/MD-SP-CORE-I02.xsd")));
			this.expectedValidationSchemaIrdetoTypes = FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/schema/Irdeto-ADI-3_0-types-extension.xsd")));
			this.expectedValidationSchemaContent = FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/schema/MD-SP-CONTENT-I02.xsd")));
			this.expectedValidationSchemaTitle = FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/schema/MD-SP-TITLE-I02.xsd")));
			this.expectedValidationSchemaOffer = FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/schema/MD-SP-OFFER-I02.xsd")));
			this.expectedValidationSchemaTerms = FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/schema/MD-SP-TERMS-I02.xsd")));
			this.expectedValidationSchemaIrdeto = FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/schema/Irdeto-ADI-3_0-extension.xsd")));
			this.expectedValidationSchemaXmlTv = FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/schema/epg.xsd")));
		} catch (IOException e) {
		}
	}
	
	@Test
	public void testValidateWithMultipleXsd() {
		XmlManager xmlManager = new XmlManagerImpl();
		List<String> schemaList = new ArrayList<String>();
		
		schemaList.add(expectedValidationSchemaXml);
		schemaList.add(expectedValidationSchemaCore);
		schemaList.add(expectedValidationSchemaIrdetoTypes);
		schemaList.add(expectedValidationSchemaContent);
		schemaList.add(expectedValidationSchemaTitle);
		schemaList.add(expectedValidationSchemaOffer);
		schemaList.add(expectedValidationSchemaTerms);
		schemaList.add(expectedValidationSchemaIrdeto);
	
		if (!xmlManager.isValid(expectedSerializedDataWrapperMulti, schemaList)){
			Assert.fail("Validation test failed!");
		}
	}

	@Test
	public void testValidateEPG() {
		XmlManager xmlManager = new XmlManagerImpl();
		List<String> schemaList = new ArrayList<String>();
		schemaList.add(expectedValidationSchemaXml);
		schemaList.add(expectedValidationSchemaXmlTv);
		if (!xmlManager.isValid(expectedSerializedDataWrapperXmlTv, schemaList)){
			Assert.fail("Validation test failed!");
		}
	}
}
