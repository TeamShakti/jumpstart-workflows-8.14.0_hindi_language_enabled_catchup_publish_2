package com.irdeto.jumpstart.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.jumpstart.domain.control.ControlApiResult;
import com.irdeto.jumpstart.domain.control.ControlBatch;
import com.irdeto.jumpstart.domain.control.ControlIPList;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.web.WebManager;
import com.irdeto.manager.xml.XmlManager;

public class JumpstartControlManagerImpl implements JumpstartControlManager {
	private static final String TRUE = "true";

	private static final String CONTROL_DOMAIN_PACKAGE = "com.irdeto.jumpstart.domain.control";

	private static final Logger logger = LoggerFactory.getLogger(JumpstartControlManagerImpl.class);
	
	private PropertiesManager propertiesManager;
	private WebManager webManager;
	private XmlManager xmlManager;

	public JumpstartControlManagerImpl() {
		super();
	}
	
	public JumpstartControlManagerImpl(PropertiesManager propertiesManager, WebManager webManager, XmlManager xmlManager) {
		super();
		this.propertiesManager = propertiesManager;
		this.webManager = webManager;
		this.xmlManager = xmlManager;
	}
	
	@Override
	public void registerContent(ControlBatch batch) throws Exception {
		String responseString = "";
		String url = getControlRegisterContentUrl();
		try {
			Map<String, String> headers = getHeaders();
			String body = xmlManager.serialize(batch);
			logger.debug("Publishing to Control");
			logger.debug("URL: {}", url);
			for (Entry<String, String> entry: headers.entrySet()) {
				logger.debug("Header: {}: {}", entry.getKey(), entry.getValue());
			}
			logger.debug("Body: {}", body);
			responseString = webManager.callWebService(
					url,
					WebManager.REQUEST_METHOD_POST,
					body,
					WebManager.XMLUTF8_CONTENT_TYPE,
					headers,
					new Integer[] {200});
			ControlApiResult result = (ControlApiResult)xmlManager.deserializeObject(responseString, CONTROL_DOMAIN_PACKAGE);
			if (!TRUE.equals(result.getSuccess())) {
				logger.debug("Body:");
				logger.debug(body);
				throw new Exception(result.getError().getErrorCode() + ": " + result.getError().getErrorMessage());
			}
		} catch (Exception e) {
			logger.error("Control Register Content error.", e);
			logger.debug("Operation Result: {}", responseString);
			logger.debug("Request URL: {}", url);
			throw e;
		}
	}

	//http://multiscreen.dev.ott.irdeto.com/services/RegisterContent?Replace=true
	private String getControlRegisterContentUrl() throws PropertyException {
		return getBaseUrl() + "RegisterContent?Replace=true";
	}

	//http://multiscreen.dev.ott.irdeto.com/services/IPList?Action=Create&CrmId=demo&Replace=true
	private String getControlIPListUrl(String crmId) throws PropertyException {
		return getBaseUrl() + "IPList?Action=Create&CrmId=" + crmId + "&Replace=true";
	}

	private String getBaseUrl() throws PropertyException {
		return propertiesManager.getProperty(JumpstartPropertyKey.CPS_MAN_SERVICE_URL_KEY);
	}
	
	private Map<String, String> getHeaders() throws PropertyException {
		Map<String, String> headers = new HashMap<>();
		String userId = propertiesManager.getProperty(JumpstartPropertyKey.CPS_MAN_USER_KEY);
		headers.put("MAN-user-id", userId);
		String password = propertiesManager.getProperty(JumpstartPropertyKey.CPS_MAN_PASSWORD_KEY);
		headers.put("MAN-user-password", password);
		return headers;
	}
	
	private String getCrmId() throws PropertyException {
		String crmId = propertiesManager.getProperty(JumpstartPropertyKey.CPS_ACCOUNT_ID_KEY);
		return crmId;
	}

	@Override
	public void ipList(ControlIPList ipList) throws Exception {
		String responseString = "";
		String url = "";
		try {
			url = getControlIPListUrl(getCrmId());
			Map<String, String> headers = getHeaders();
			String body = xmlManager.serialize(ipList);
			responseString = webManager.callWebService(
					url,
					WebManager.REQUEST_METHOD_POST,
					body,
					WebManager.XMLUTF8_CONTENT_TYPE,
					headers,
					new Integer[] {200});
		} catch (Exception e) {
			logger.error("Control IP List error.", e);
			logger.debug("Operation Result: {}", responseString);
			logger.debug("Request URL: {}", url);
			throw e;
		}
	}


}
