package com.irdeto.jumpstart.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.jumpstart.domain.livedrm.LiveDrmResponseProcessor;
import com.irdeto.jumpstart.domain.livedrm.LiveDrmSoapEnvelope;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.web.WebManager;
import com.irdeto.manager.xml.XmlManager;

public class ControlLiveDrmManagerImpl implements ControlLiveDrmManager {
	private static final Logger logger = LoggerFactory.getLogger(JumpstartControlManagerImpl.class);

	private PropertiesManager propertiesManager;
	private WebManager webManager;
	private XmlManager xmlManager;

	public ControlLiveDrmManagerImpl(PropertiesManager propertiesManager, WebManager webManager, XmlManager xmlManager) {
		super();
		this.propertiesManager = propertiesManager;
		this.webManager = webManager;
		this.xmlManager = xmlManager;
	}

	public ControlLiveDrmManagerImpl() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.irdeto.jumpstart.manager.ControlLiveDrmManager#executeWebCall(com.irdeto.jumpstart.domain.livedrm.LiveDrmSoapEnvelope)
	 */
	@Override
	public Map<String, Object> executeWebCall(LiveDrmSoapEnvelope liveDrmSoapEnvelope, LiveDrmResponseProcessor responseProcessor) throws Exception {
		Map<String, Object> results = new HashMap<>();
		String responseString = null;
		String url = getControlLiveDrmUrl();
		Map<String, String> headers = getHttpHeaders(liveDrmSoapEnvelope);
		try {
			responseString = webManager.callWebService(
					url,
					WebManager.REQUEST_METHOD_POST,
					convertSoapEnvelopeToString(liveDrmSoapEnvelope),
					WebManager.XMLUTF8_CONTENT_TYPE,
					headers,
					new Integer[] {200});
	        if (responseString == null){
				throw new Exception("SOAP API result is null");
	        } else {
	        	results = responseProcessor.processResponse(responseString);
	        }
		} catch (Exception e) {
			logger.error("Control LiveDRM API error.", e);
			logger.debug("Request URL: {}", url);
			for (Entry<String, String> header: headers.entrySet()) {
				logger.debug("Header: {}:{}", header.getKey(), header.getValue());
			}
			logger.debug("Request body: {}", convertSoapEnvelopeToString(liveDrmSoapEnvelope) );
			if (responseString != null) {
				logger.debug("Response: {}", responseString);
			}
			throw e;
		}
		return results;
	}

	private String getControlLiveDrmUrl() throws PropertyException{
		return propertiesManager.getProperty(JumpstartPropertyKey.CONTROL_LIVE_DRM_URL_KEY);
	}
	
	private Map<String, String> getHttpHeaders(LiveDrmSoapEnvelope liveDrmSoapEnvelope) {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "text/xml");
		String command = liveDrmSoapEnvelope.getBody().getCommand();
		headers.put("SOAPAction", "http://man.entriq.net/livedrmservice/" + command );
		return headers;
	}

	private String convertSoapEnvelopeToString(LiveDrmSoapEnvelope liveDrmSoapEnvelope) throws Exception{
		String envelopeString = xmlManager.serialize(liveDrmSoapEnvelope, "com.irdeto.jumpstart.domain.livedrm");
		return xmlManager.stripXmlDirective(envelopeString);
	}

}
