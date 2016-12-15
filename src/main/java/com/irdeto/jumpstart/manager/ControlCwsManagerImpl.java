package com.irdeto.jumpstart.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.domain.control.cws.SoapEnvelope;
import com.irdeto.helper.xmlmodel.JDOMDocumentHelper;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.web.WebManager;
import com.irdeto.manager.xml.XmlManager;

public class ControlCwsManagerImpl implements ControlCwsManager {
	private static final Logger logger = LoggerFactory.getLogger(JumpstartControlManagerImpl.class);

	private PropertiesManager propertiesManager;
	private WebManager webManager;
	private XmlManager xmlManager;

	public ControlCwsManagerImpl() {
		super();
	}
	
	public ControlCwsManagerImpl(PropertiesManager propertiesManager, WebManager webManager, XmlManager xmlManager) {
		super();
		this.propertiesManager = propertiesManager;
		this.webManager = webManager;
		this.xmlManager = xmlManager;
	}

	public String executeWebCall(SoapEnvelope soapEnvelope) throws Exception {
		String responseString = "";
		String url = getControlCwsUrl();
		if (StringUtils.isBlank(url)||soapEnvelope==null){
			throw new Exception("SOAP URL is blank or Envelop is null");
		}
		Map<String, String> headerMap = getHttpHeaders(soapEnvelope);
		String body = convertSoapEnvelopeToString(soapEnvelope);
		try {
			responseString = webManager.callWebService(
					url,
					WebManager.REQUEST_METHOD_POST,
					body,
					WebManager.XMLUTF8_CONTENT_TYPE,
					headerMap,
					new Integer[] {200});
	        if (responseString == null){
				throw new Exception("SOAP API result is null");
	        } else {
	    		processCwsSoapResponse(responseString);
	        }
			logger.debug("Operation Result: {}", responseString);
			logger.debug("Request URL: {}", url);
			for (Entry<String, String> headerEntry: headerMap.entrySet()) {
				logger.debug("Header: {}: {}", headerEntry.getKey(), headerEntry.getValue());
			}
			logger.debug("Body: {}", body);
			return responseString;
		} catch (Exception e) {
			logger.error("Control CWS Upsert Category error.", e);
			logger.debug("Operation Result: {}", responseString);
			logger.debug("Request URL: {}", url);
			for (Entry<String, String> headerEntry: headerMap.entrySet()) {
				logger.debug("Header: {}: {}", headerEntry.getKey(), headerEntry.getValue());
			}
			logger.debug("Body: {}", body);
			throw e;
		}
	}

	private String getControlCwsUrl() throws PropertyException{
		return propertiesManager.getProperty(JumpstartPropertyKey.CONTROL_CWS_URL_KEY);
	}
	
	private Map<String, String> getHttpHeaders(SoapEnvelope soapEnvelope) {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "text/xml");
		String command = soapEnvelope.getBody().getBodyElement().getClass().getSimpleName();
		headers.put("SOAPAction", "http://ws.entriq.net/services/console/IConsoleService/"+command );
		return headers;
	}

	private String convertSoapEnvelopeToString(SoapEnvelope soapEnvelope) throws Exception{
		String envelopeString = xmlManager.serialize(soapEnvelope, "com.irdeto.domain.control.cws");
		return xmlManager.stripXmlDirective(envelopeString);
	}

	private void processCwsSoapResponse(String responseString) throws Exception {
		Document document = JDOMDocumentHelper.buildDocument(responseString);
		XPathFactory xPathFactory = XPathFactory.instance();
		List<Namespace> namespaceList = new ArrayList<Namespace>();
		namespaceList.add(Namespace.getNamespace("s", "http://schemas.xmlsoap.org/soap/envelope/"));
		namespaceList.add(Namespace.getNamespace("a", "http://schemas.datacontract.org/2004/07/ConsoleService"));
		namespaceList.add(Namespace.getNamespace("b", "http://schemas.datacontract.org/2004/07/Entriq.Security.BusinessObject"));
		namespaceList.add(Namespace.getNamespace("i", "http://www.w3.org/2001/XMLSchema-instance"));
		String errorCode = xPathFactory.compile("//a:ErrorCode", Filters.element(), null, namespaceList).evaluate(document).get(0).getValue();
		String errorMessage = xPathFactory.compile("//a:ErrorMessage", Filters.element(), null, namespaceList).evaluate(document).get(0).getValue();
		String success = xPathFactory.compile("//a:Success", Filters.element(), null, namespaceList).evaluate(document).get(0).getValue();
		if (!"true".equals(success)){
			throw new Exception("SOAP API result is not success. ErrorCode: " + errorCode + ", ErrorMessage: " + errorMessage);
		} else {
			logger.debug("SOAP API is success");
		}
	}
}
