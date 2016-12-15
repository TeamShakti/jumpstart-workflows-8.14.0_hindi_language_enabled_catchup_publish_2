package com.irdeto.jumpstart.domain.taskhandler;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.domain.constants.TaskResult;
import com.irdeto.domain.control.cws.SoapEnvelope;
import com.irdeto.domain.control.cws.objectfactory.GetPolicyGroupByIDCwsResponseProcessor;
import com.irdeto.domain.control.cws.objectfactory.GetPolicyGroupByIDObjectFactory;
import com.irdeto.jumpstart.domain.PolicyGroupProfile;
import com.irdeto.jumpstart.domain.PolicyProfile;
import com.irdeto.jumpstart.manager.ControlCwsManager;
import com.irdeto.jumpstart.manager.ControlCwsManagerImpl;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.web.WebManager;
import com.irdeto.manager.xml.XmlManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

public class ControlCwsGetPolicyGroupByIdTaskHandler extends AbstractTaskHandler {
	@TaskProperty
	private static final String POLICY_GROUP_ID_PARAMETER = "PolicyGroupId";
	
	@TaskProperty
	private static final String POLICY_ID_PARAMETER = "PolicyId";
	
	@Resource(name="propertiesManager")
	private PropertiesManager propertiesManager;

	@Resource(name="webManager")
	private WebManager webManager;

	@Resource(name="xmlManager")
	private XmlManager xmlManager;

	private ControlCwsManager cwsManager;
	
	@TaskResult
	private static final String POLICY_GROUP = "PolicyGroup";
	
	@TaskResult
	private static final String POLICY = "Policy";
	
	@PostConstruct
	protected void setupJumpstartControlCwsManager() {
		cwsManager = new ControlCwsManagerImpl(propertiesManager, webManager, xmlManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> params, Map<String, Object> results) throws Exception {
		String policyGroupId = (String)params.get(POLICY_GROUP_ID_PARAMETER);
		String policyId = (String)params.get(POLICY_ID_PARAMETER);
		
		SoapEnvelope getPolicyGroupByIdEnvelope = GetPolicyGroupByIDObjectFactory.createSoapEnvelope();
		String responseString = cwsManager.executeWebCall(getPolicyGroupByIdEnvelope);
		GetPolicyGroupByIDCwsResponseProcessor getPolicyGroupbyIDCwsResponseProcessor = new GetPolicyGroupByIDCwsResponseProcessor();
		Map<String,Object> policyGroupListMap = getPolicyGroupbyIDCwsResponseProcessor.processResponse(responseString);
		List<PolicyGroupProfile> policyGroupList = (List<PolicyGroupProfile>)policyGroupListMap.get(GetPolicyGroupByIDCwsResponseProcessor.POLICY_GROUP_LIST_KEY);
		List<PolicyProfile> policyList = (List<PolicyProfile>)policyGroupListMap.get(GetPolicyGroupByIDCwsResponseProcessor.POLICY_LIST_KEY);
		for (PolicyGroupProfile policyGroup:policyGroupList) {
			if(policyGroupId.equals(policyGroup.getId())){
				results.put(POLICY_GROUP, policyGroup);
				break;
			}
		}
		for (PolicyProfile policy:policyList) {
			if(policyId.equals(policy.getId())){
				results.put(POLICY, policy);
				break;
			}
		}
	}
}
