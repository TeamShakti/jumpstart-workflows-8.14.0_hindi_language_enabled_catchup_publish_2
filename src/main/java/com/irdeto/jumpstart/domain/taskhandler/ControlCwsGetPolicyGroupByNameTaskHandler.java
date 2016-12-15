package com.irdeto.jumpstart.domain.taskhandler;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.domain.constants.TaskResult;
import com.irdeto.domain.control.cws.PolicyGroupLite;
import com.irdeto.domain.control.cws.SoapEnvelope;
import com.irdeto.domain.control.cws.objectfactory.GetPolicyGroupListCwsResponseProcessor;
import com.irdeto.domain.control.cws.objectfactory.GetPolicyGroupListObjectFactory;
import com.irdeto.jumpstart.manager.ControlCwsManager;
import com.irdeto.jumpstart.manager.ControlCwsManagerImpl;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.web.WebManager;
import com.irdeto.manager.xml.XmlManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

public class ControlCwsGetPolicyGroupByNameTaskHandler extends AbstractTaskHandler {

	@TaskProperty
	private static final String POLICY_GROUP_NAME_PARAMETER = "PolicyGroupName";
	
	@Resource(name="propertiesManager")
	private PropertiesManager propertiesManager;

	@Resource(name="webManager")
	private WebManager webManager;

	@Resource(name="xmlManager")
	private XmlManager xmlManager;

	private ControlCwsManager cwsManager;
	
	@TaskResult
	private static final String POLICY_GROUP_LITE = "PolicyGroupLite";
	
	@PostConstruct
	protected void setupJumpstartControlCwsManager() {
		cwsManager = new ControlCwsManagerImpl(propertiesManager, webManager, xmlManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> params, Map<String, Object> results) throws Exception {
		String policyGroupName = (String)params.get(POLICY_GROUP_NAME_PARAMETER);
		SoapEnvelope getPolicyGroupListEnvelope = GetPolicyGroupListObjectFactory.createSoapEnvelope();
		String responseString = cwsManager.executeWebCall(getPolicyGroupListEnvelope);
		GetPolicyGroupListCwsResponseProcessor getPolicyGroupListCwsResponseProcessor = new  GetPolicyGroupListCwsResponseProcessor();
		Map<String,Object> policyGroupListMap = getPolicyGroupListCwsResponseProcessor.processResponse(responseString);
		List<PolicyGroupLite> policyGroupLiteList = (List<PolicyGroupLite>)policyGroupListMap.get(GetPolicyGroupListCwsResponseProcessor.POLICY_GROUP_LITE_LIST_KEY);
		for (PolicyGroupLite policyGroupLite:policyGroupLiteList) {
			if(policyGroupName.equals(policyGroupLite.getName())){
				results.put(POLICY_GROUP_LITE, policyGroupLite);
				break;
			}
		}
	}
}
