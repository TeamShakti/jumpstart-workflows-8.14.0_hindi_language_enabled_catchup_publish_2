package com.irdeto.jumpstart.manager;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.control.qsa.ControlQSAAuthorizationInfo;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.xml.XmlManager;
import com.irdeto.test.BaseSpringTest;

@Test
public class AuthorizationInfoDeserializationIntegrationTest extends BaseSpringTest {
	public void testDeserialization() throws Exception {
		String xml = WorkflowHelper.getXml("AuthorizationInfo.xml");
		ControlQuerySessionAuthorizationManagerImpl manager = new ControlQuerySessionAuthorizationManagerImpl(null, null, (XmlManager)context.getBean("xmlManager"));
		ControlQSAAuthorizationInfo authorizationInfo = manager.deserializeResponse(xml);
		Assert.assertNotNull(authorizationInfo);
	}
}
