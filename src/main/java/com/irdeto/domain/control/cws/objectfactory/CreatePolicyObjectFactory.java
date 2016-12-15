package com.irdeto.domain.control.cws.objectfactory;

import com.irdeto.domain.control.cws.CMessage;
import com.irdeto.domain.control.cws.CreatePolicy;
import com.irdeto.domain.control.cws.Price;
import com.irdeto.domain.control.cws.SoapBody;
import com.irdeto.domain.control.cws.SoapEnvelope;

public class CreatePolicyObjectFactory extends AbstractCwsObjectFactory {

	private static final int NO_DEVICES = 0;
	
	public static SoapEnvelope createSoapEnvelope(String id,String name,String description,String subscriptionRef,String licenseProfileRef,Price priceTable) {
		SoapBody body = new SoapBody();
		CreatePolicy createPolicy = new CreatePolicy();
		body.setBodyElement(createPolicy);
		CMessage message = new CMessage();
		createPolicy.setMessage(message);
		message.setCredential(createUserCredential());
		
		PolicyObj policy = new PolicyObj();
		policy.setId(id);
		policy.setName(name);
		policy.setDescription(description);
		policy.setEntitlementStartMode("");
		policy.setEntitlementDuration("");
		policy.setSubscriptionReference(Integer.parseInt(subscriptionRef));
		policy.setLicenseProfileReference(Integer.parseInt(licenseProfileRef));
		policy.setRequireAuthentication(false);
		policy.setPrice(priceTable);
		policy.setNumberOfDevices(NO_DEVICES);
		policy.setProductTaxType("");
		policy.setBillImmediately(false);
		policy.setSyndicateAccountId("");
		
		message.setPolicyProfile(policy);
		
		return new SoapEnvelope(body);	
	}

}
