package com.irdeto.domain.control.cws.objectfactory;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.domain.control.cws.Price;
import com.irdeto.domain.control.cws.SoapEnvelope;
import com.irdeto.jumpstart.workflow.BaseSpringTestWithSetup;

@Test
public class CreatePolicyIntegrationTest extends BaseSpringTestWithSetup{

	@SuppressWarnings("unused")
	public void testProcessResponse() throws Exception {
		CreatePolicyObjectFactory createPolicy = new CreatePolicyObjectFactory();
		Price priceTable = new Price();
		priceTable.setCountry("01");
		priceTable.setAmount("100");
		SoapEnvelope soapEnv = CreatePolicyObjectFactory.createSoapEnvelope("1", "policyName", "description", "1", "1", priceTable);
		
		Assert.assertNotNull("");
	}
}
