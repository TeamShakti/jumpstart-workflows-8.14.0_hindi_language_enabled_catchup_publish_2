package com.irdeto.domain.control.qsa;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.control.qsa.ControlQSAAuthorizationInfo;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.xml.XmlManager;
import com.irdeto.test.BaseSpringTest;

@Test
public class ControlQSADeserializationIntegrationTest extends BaseSpringTest {
	public void testDeserialization() throws Exception {
		String xml = WorkflowHelper.getXml("ControlQSAResponse.xml");
		XmlManager xmlManager = (XmlManager)context.getBean("xmlManager");
		Object object = xmlManager.deserializeObject(xml, "com.irdeto.jumpstart.domain.control.qsa");
		Assert.assertTrue(object instanceof ControlQSAAuthorizationInfo);
	}
}
