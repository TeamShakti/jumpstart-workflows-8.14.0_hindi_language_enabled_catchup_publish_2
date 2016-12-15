package com.irdeto.domain.control.cws.objectfactory;

import com.irdeto.domain.control.cws.CMessage;
import com.irdeto.domain.control.cws.GetPolicyGroupList;
import com.irdeto.domain.control.cws.SoapBody;
import com.irdeto.domain.control.cws.SoapEnvelope;

public class GetPolicyGroupListObjectFactory extends AbstractCwsObjectFactory {
	private static final int START_INDEX = 0;
	private static final int MAX_RESULTS = 1000000;

	public static SoapEnvelope createSoapEnvelope() {
		SoapBody body = new SoapBody();
		GetPolicyGroupList getPolicyGroupList = new GetPolicyGroupList();
		body.setBodyElement(getPolicyGroupList);
		CMessage message = new CMessage();
		getPolicyGroupList.setMessage(message);
		message.setCredential(createUserCredential());
		message.setMaxResults(MAX_RESULTS);
		message.setSearchText(null);
		message.setStartIndex(START_INDEX);
		return new SoapEnvelope(body);	
	}
}
