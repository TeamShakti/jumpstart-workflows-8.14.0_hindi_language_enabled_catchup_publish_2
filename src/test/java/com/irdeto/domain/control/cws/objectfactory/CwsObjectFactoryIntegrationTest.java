package com.irdeto.domain.control.cws.objectfactory;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.domain.control.cws.SoapEnvelope;
import com.irdeto.manager.jbpm.knowledge.ClassManager;
import com.irdeto.manager.properties.PropertiesManagerImpl;
import com.irdeto.manager.task.BeanUtil;
import com.irdeto.manager.xml.XmlManager;
import com.irdeto.test.BaseSpringTest;

public class CwsObjectFactoryIntegrationTest extends BaseSpringTest{
	private static final String NAME = "Namjun";
	private static final String POLICY_ID = "2";
	private static final String DESCRIPTION = "Description";
	private static final String CATEGORY_ID = "2";
	private static final String PARENT_CATEGORY_ID = "0";
	private static final String MEDIA_ID = "1_movie";

	@Test
	public void testCreateSoapEnvelope() throws Exception {
		BeanUtil.setContext(context.getDependencyInjectionContext());
		PropertiesManagerImpl propertiesManager = (PropertiesManagerImpl)context.getBean("propertiesManager");
		ClassManager classManager = (ClassManager) context.getBean("classManager");
		propertiesManager.secondStageSetup(classManager);
		XmlManager xmlManager = (XmlManager)context.getBean("xmlManager");
		
		SoapEnvelope soapEnvelope = UpsertP7CategoryObjectFactory.createSoapEnvelope("Insert", CATEGORY_ID, PARENT_CATEGORY_ID, NAME, POLICY_ID, DESCRIPTION);
		String httpBody = xmlManager.serialize(soapEnvelope, "com.irdeto.domain.control.cws");
		String expectedBody = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:arr=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" xmlns:con1=\"http://schemas.datacontract.org/2004/07/ConsoleService\" xmlns:con=\"http://ws.entriq.net/services/console\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"com.irdeto.domain.control.cws\"><SOAP-ENV:Body><con:UpsertP7Category><con:cMessage><con1:UserCredential><con1:AccountId>multiscreen</con1:AccountId><con1:Password>Qwk5TSOJJMR1TKs4</con1:Password><con1:UserName>admin@multiscreen.com</con1:UserName></con1:UserCredential><con1:Operation>Insert</con1:Operation><con1:Category><con1:CategoryId>2</con1:CategoryId><con1:Description>Description</con1:Description><con1:Name>Namjun</con1:Name><con1:ParentCategoryId>0</con1:ParentCategoryId><con1:PolicyId>2</con1:PolicyId></con1:Category></con:cMessage></con:UpsertP7Category></SOAP-ENV:Body></SOAP-ENV:Envelope>";
		Assert.assertEquals(httpBody, expectedBody);

		List<String> categoryIds = new ArrayList<String>();
		categoryIds.add(CATEGORY_ID);
		List<String> mediaIds = new ArrayList<String>();
		mediaIds.add(MEDIA_ID);
		soapEnvelope = UpsertP7MediaCategoryObjectFactory.createSoapEnvelope("Insert", categoryIds, mediaIds);
		httpBody = xmlManager.serialize(soapEnvelope, "com.irdeto.domain.control.cws");
		expectedBody = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:arr=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" xmlns:con1=\"http://schemas.datacontract.org/2004/07/ConsoleService\" xmlns:con=\"http://ws.entriq.net/services/console\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"com.irdeto.domain.control.cws\"><SOAP-ENV:Body><con:UpsertP7MediaCategory><con:cMessage><con1:UserCredential><con1:AccountId>multiscreen</con1:AccountId><con1:Password>Qwk5TSOJJMR1TKs4</con1:Password><con1:UserName>admin@multiscreen.com</con1:UserName></con1:UserCredential><con1:Operation>Insert</con1:Operation><con1:CategoryIds><arr:long>2</arr:long></con1:CategoryIds><con1:MediaIds><arr:string>1_movie</arr:string></con1:MediaIds></con:cMessage></con:UpsertP7MediaCategory></SOAP-ENV:Body></SOAP-ENV:Envelope>";
		Assert.assertEquals(httpBody, expectedBody);
		
		soapEnvelope = DeleteMediaObjectFactory.createSoapEnvelope(MEDIA_ID, MEDIA_ID);
		httpBody = xmlManager.serialize(soapEnvelope, "com.irdeto.domain.control.cws");
		expectedBody = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:arr=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\" xmlns:con1=\"http://schemas.datacontract.org/2004/07/ConsoleService\" xmlns:con=\"http://ws.entriq.net/services/console\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"com.irdeto.domain.control.cws\"><SOAP-ENV:Body><con:DeleteMedia><con:message><con1:UserCredential><con1:AccountId>multiscreen</con1:AccountId><con1:Password>Qwk5TSOJJMR1TKs4</con1:Password><con1:UserName>admin@multiscreen.com</con1:UserName></con1:UserCredential><con1:ContentUniqueName>1_movie</con1:ContentUniqueName><con1:MediaUniqueName>1_movie</con1:MediaUniqueName></con:message></con:DeleteMedia></SOAP-ENV:Body></SOAP-ENV:Envelope>";
		Assert.assertEquals(httpBody, expectedBody);
	}
}
