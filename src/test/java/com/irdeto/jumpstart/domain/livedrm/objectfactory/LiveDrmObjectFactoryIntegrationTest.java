package com.irdeto.jumpstart.domain.livedrm.objectfactory;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.livedrm.GenerateKeysObjectFactory;
import com.irdeto.jumpstart.domain.livedrm.LiveDrmSoapEnvelope;
import com.irdeto.jumpstart.workflow.BaseSpringTestWithSetup;
import com.irdeto.manager.xml.XmlManager;

public class LiveDrmObjectFactoryIntegrationTest extends BaseSpringTestWithSetup {
	@Test
	public void testCreateLiveDrmEnvelope() throws Exception {
		XmlManager xmlManager = (XmlManager)context.getBean("xmlManager");
		
		GenerateKeysObjectFactory liveDrmObjectFactory = new GenerateKeysObjectFactory();
		liveDrmObjectFactory.setContentId("1_movie");
		LiveDrmSoapEnvelope liveDrmEnvelope = liveDrmObjectFactory.createSoapMsgEnvelope();

		String httpBody = xmlManager.serialize(liveDrmEnvelope, "com.irdeto.jumpstart.domain.livedrm");
		String expectedBody = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><soapenv:Envelope xmlns:liv=\"http://man.entriq.net/livedrmservice/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"com.irdeto.jumpstart.domain.livedrm\"><soapenv:Header><liv:LiveDrmServiceHeader m_sPassword=\"Qwk5TSOJJMR1TKs4\" m_sUsername=\"admin@multiscreen.com\"/></soapenv:Header><soapenv:Body><liv:GeneratePlayreadyKeys><liv:sAccountID>multiscreen</liv:sAccountID><liv:sContentId>1_movie</liv:sContentId></liv:GeneratePlayreadyKeys></soapenv:Body></soapenv:Envelope>";
		Assert.assertEquals(httpBody, expectedBody);

	}
}
