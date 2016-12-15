package com.irdeto.domain.control.cws.objectfactory;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.WorkflowHelper;

@Test
public class GetPolicyGroupByIDTest {
	public void testProcessResponse() throws Exception {
		String xml = WorkflowHelper.getXml("GetPolicyGroupByIDResponse.xml");
		CwsResponseProcessor responseProcessor = new GetPolicyGroupByIDCwsResponseProcessor();
		Map<String, Object> results = responseProcessor.processResponse(xml);
		Assert.assertNotNull(results);
	}
}
