package com.irdeto.domain.control.cws.objectfactory;

import com.irdeto.domain.control.cws.AddToPolicyGroup;
import com.irdeto.domain.control.cws.CMessage;
import com.irdeto.domain.control.cws.PolicyId;
import com.irdeto.domain.control.cws.SoapBody;
import com.irdeto.domain.control.cws.SoapEnvelope;

public class AddToPolicyGroupObjectFactory extends AbstractCwsObjectFactory {
	
	private static final int INDEX = 0;
	//private static final int POLICY_GROUP_ID = 1000000;
	//private static final int LONGVAL = 0;

	public static SoapEnvelope createSoapEnvelope(int policyGroupId,int policyId) {
		SoapBody body = new SoapBody();
		AddToPolicyGroup addToPolicyGroup = new AddToPolicyGroup();
		body.setBodyElement(addToPolicyGroup);
		CMessage message = new CMessage();
		addToPolicyGroup.setMessage(message);
		message.setCredential(createUserCredential());
		message.setIndex(INDEX);
		message.setPolicyGroupId(policyGroupId);
		PolicyId policyIds = new PolicyId();
		policyIds.setLongVal(policyId);
		message.setPolicyIds(policyIds);
		return new SoapEnvelope(body);	
	}
}
