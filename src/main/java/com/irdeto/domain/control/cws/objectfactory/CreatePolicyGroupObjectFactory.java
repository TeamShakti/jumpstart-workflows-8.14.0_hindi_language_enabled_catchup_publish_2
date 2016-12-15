package com.irdeto.domain.control.cws.objectfactory;

import java.util.List;

import com.irdeto.domain.control.cws.CMessage;
import com.irdeto.domain.control.cws.CreatePolicyGroup;
import com.irdeto.domain.control.cws.SoapBody;
import com.irdeto.domain.control.cws.SoapEnvelope;

public class CreatePolicyGroupObjectFactory extends AbstractCwsObjectFactory {

    private static final int START_INDEX = 0;
    private static final int MAX_RESULTS = 1000000;

    public static SoapEnvelope createSoapEnvelope(String id,String policyGroupName,String policyGroupDescription,List<PolicyObj> policies,List<String> blockedCountryList) {
        SoapBody body = new SoapBody();
		CreatePolicyGroup createPolicyGroup = new CreatePolicyGroup();
		body.setBodyElement(createPolicyGroup);
		
        CMessage message = new CMessage();
        createPolicyGroup.setMessage(message);
        message.setCredential(createUserCredential());
        message.setMaxResults(MAX_RESULTS);
        message.setSearchText(null);
        message.setStartIndex(START_INDEX);
        
        PolicyGroupObj policyGroup =  new PolicyGroupObj();
        policyGroup.setId(id);
        policyGroup.setName(policyGroupName);
        policyGroup.setDescription(policyGroupDescription);
        policyGroup.setPolicies(policies);
        policyGroup.setBlockedCountryList(blockedCountryList);
        message.setPolicyGroupProfile(policyGroup);
        
        return new SoapEnvelope(body);    
    }
}