package com.irdeto.domain.control.cws.objectfactory;

import com.irdeto.domain.control.cws.CMessage;

import com.irdeto.domain.control.cws.GetPolicyGroupByID;
import com.irdeto.domain.control.cws.SoapBody;
import com.irdeto.domain.control.cws.SoapEnvelope;

/**
 * File Name: GetPolicyGroupByIDObjectFactory.java
 * 
 * Description: The Object Factory for  class for GetPolicyGroupByID
 * 
 * Developed by Tata Elxsi for Irdeto B.V.
 * 
 * Creation Date: 30-Jan-2015
 *
 */
public class GetPolicyGroupByIDObjectFactory extends AbstractCwsObjectFactory {

	public static SoapEnvelope createSoapEnvelope() {
		SoapBody body = new SoapBody();
		GetPolicyGroupByID getPolicyGroupByID = new GetPolicyGroupByID();
		body.setBodyElement(getPolicyGroupByID);
		CMessage message = new CMessage();
		getPolicyGroupByID.setMessage(message);
		message.setCredential(createUserCredential());
		return new SoapEnvelope(body);	
	}
}
