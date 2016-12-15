package com.irdeto.domain.control;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.control.ControlBatch;
import com.irdeto.manager.xml.XmlManager;
import com.irdeto.test.BaseSpringTest;

public class ControlRegisterContentSerializationIntegrationTest extends BaseSpringTest {
	
	@Test
	public void testSerialize() throws Exception {
		ControlBatch controlBatch = ControlRegisterContentDummyData.getControlBatch();
		XmlManager xmlManager = (XmlManager)context.getBean("xmlManager");
		String serializedMessage = xmlManager.serialize(controlBatch);
		Assert.assertNotNull(serializedMessage);
	}
}
