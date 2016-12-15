package com.irdeto.domain.control.cws.objectfactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.domain.control.cws.SoapEnvelope;
import com.irdeto.jumpstart.workflow.BaseSpringTestWithSetup;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

@Test
public class CreatePolicyGroupIntegrationTest extends BaseSpringTestWithSetup {
	public void testCreatePolicyGroup() throws Exception {
		@SuppressWarnings("unused")
		CreatePolicyGroupObjectFactory createPolicyGroup = new CreatePolicyGroupObjectFactory();
		List<PolicyObj> policies =  new ArrayList<>();
		PolicyObj policyObj = new PolicyObj();
		policyObj.setId("1");
		policies.add(policyObj);
		List<String> blockedCountryList = new ArrayList<>();
		@SuppressWarnings("unused")
		SoapEnvelope soapEnv = CreatePolicyGroupObjectFactory.createSoapEnvelope("1", "policyName", "description",policies,blockedCountryList);
		
		Assert.assertNotNull("");
    }
	
    public void testCreatePolicyGroupResponse() throws Exception {
        String xml = WorkflowHelper.getXml("CreatePolicyGroupResponse.xml");
        CwsResponseProcessor responseProcessor = new CreatePolicyGroupCwsResponseProcessor();
        Map<String, Object> results = responseProcessor.processResponse(xml);
        Assert.assertNotNull(results);
    }
}
