package com.irdeto.jumpstart.manager;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.livedrm.GenerateKeysLiveDrmResponseProcessor;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

@Test
public class LiveDrmGenerateKeysResponseTest {
	public void testResponseProcessor() throws Exception {
		String xml = WorkflowHelper.getXml("liveDrmGenerateKeysResponse.xml");
		GenerateKeysLiveDrmResponseProcessor responseProcessor = new GenerateKeysLiveDrmResponseProcessor();
		Map<String, Object> results = responseProcessor.processResponse(xml);
		Assert.assertNotNull(results);
	}
}
