package com.irdeto.jumpstart.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.irdeto.jumpstart.domain.control.qsa.ControlQSAAuthorizationInfo;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.web.WebManager;
import com.irdeto.manager.xml.XmlManager;

public class ControlQuerySessionAuthorizationManagerImpl implements ControlQuerySessionAuthorizationManager {
	private PropertiesManager propertiesManager;
	private WebManager webManager;
	private XmlManager xmlManager;
	
	public ControlQuerySessionAuthorizationManagerImpl(
			PropertiesManager propertiesManager, WebManager webManager,
			XmlManager xmlManager) {
		super();
		this.propertiesManager = propertiesManager;
		this.webManager = webManager;
		this.xmlManager = xmlManager;
	}
	
	public ControlQuerySessionAuthorizationManagerImpl() {
		super();
	}

	@Override
	public List<ControlQSAAuthorizationInfo> getAuthorizationInfoList(List<String> contentIdList) throws Exception {
		List<ControlQSAAuthorizationInfo> controlAuthorizationInfoList = new ArrayList<>();
		for (String contentId: contentIdList) {
			ControlQSAAuthorizationInfo controlAuthorizationInfo = getAuthorizationInfo(contentId);
			if (controlAuthorizationInfo != null) {
				controlAuthorizationInfoList.add(controlAuthorizationInfo);
			}
		}
		return controlAuthorizationInfoList;
	}

	@Override
	public ControlQSAAuthorizationInfo getAuthorizationInfo(String contentId) throws Exception {
		try {
			String manServiceUrl = propertiesManager.getProperty(JumpstartPropertyKey.CPS_MAN_SERVICE_URL_KEY);
			String crmId = propertiesManager.getProperty(JumpstartPropertyKey.CONTROL_CRM_ID_KEY);
			String accountId = propertiesManager.getProperty(JumpstartPropertyKey.CONTROL_ACCOUNT_ID_KEY);
			String manUserId = propertiesManager.getProperty(JumpstartPropertyKey.CPS_MAN_USER_KEY);
			String manUserPassword = propertiesManager.getProperty(JumpstartPropertyKey.CPS_MAN_PASSWORD_KEY);
			
			if (!manServiceUrl.endsWith("/")) {
				manServiceUrl = manServiceUrl + '/';
			}
			StringBuffer qsaUrl = new StringBuffer(manServiceUrl);
			qsaUrl.append("QuerySessionAuthorization?CrmId=")
				.append(crmId)
				.append("&AccountId=")
				.append(accountId)
				.append("&ContentId=")
				.append(contentId)
				.append("&SessionId=0");
			
			Map<String, String> headers = new HashMap<>();
			headers.put("MAN-user-id", manUserId);
			headers.put("MAN-user-password", manUserPassword);
			Integer[] acceptableResponseCodes = new Integer[]{200};
			String response = webManager.callWebService(qsaUrl.toString(), WebManager.REQUEST_METHOD_GET, headers, acceptableResponseCodes);
			return deserializeResponse(response);
		} catch (PropertyException e) {
		}
		return null;
	}

	protected ControlQSAAuthorizationInfo deserializeResponse(String response) throws Exception {
		Object deserializedResponse = xmlManager.deserializeObject(response, "com.irdeto.jumpstart.domain.control.qsa");
		if (deserializedResponse != null && deserializedResponse instanceof ControlQSAAuthorizationInfo) {
			return (ControlQSAAuthorizationInfo)deserializedResponse;
		}
		return null;
	}
}
