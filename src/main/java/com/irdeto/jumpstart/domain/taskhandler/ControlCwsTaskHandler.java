package com.irdeto.jumpstart.domain.taskhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.domain.control.cws.SoapEnvelope;
import com.irdeto.domain.control.cws.objectfactory.CwsResponseProcessor;
import com.irdeto.jumpstart.manager.ControlCwsManager;
import com.irdeto.jumpstart.manager.ControlCwsManagerImpl;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.web.WebManager;
import com.irdeto.manager.xml.XmlManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("ControlCws")
public class ControlCwsTaskHandler extends AbstractTaskHandler {

	@TaskProperty(required=false, allowSpaces = true)
	private static final String SOAP_ENVELOPE_PARAMETER = "SoapEnvelope";

	@TaskProperty(required=false, allowSpaces = true)
	private static final String SOAP_ENVELOPE_LIST_PARAMETER = "SoapEnvelopeList";

	@TaskProperty(required=false, type=CwsResponseProcessor.class)
	private static final String CWS_RESPONSE_PROCESSOR_PARAMETER = "CwsResponseProcessor";

	@Resource(name="propertiesManager")
	private PropertiesManager propertiesManager;

	@Resource(name="webManager")
	private WebManager webManager;

	@Resource(name="xmlManager")
	private XmlManager xmlManager;

	private ControlCwsManager cwsManager;
	
	@PostConstruct
	protected void setupJumpstartControlCwsManager() {
		cwsManager = new ControlCwsManagerImpl(propertiesManager, webManager, xmlManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> params, Map<String, Object> results) throws Exception {
		SoapEnvelope soapEnvelope = (SoapEnvelope)params.get(SOAP_ENVELOPE_PARAMETER);
		List<SoapEnvelope> soapEnvelopeList = (List<SoapEnvelope>)params.get(SOAP_ENVELOPE_LIST_PARAMETER);
		CwsResponseProcessor cwsResponseProcessor = (CwsResponseProcessor)params.get(CWS_RESPONSE_PROCESSOR_PARAMETER);
		if (soapEnvelopeList == null) {
			soapEnvelopeList = new ArrayList<>();
		}
		if (soapEnvelope != null) {
			soapEnvelopeList.add(soapEnvelope);
		}
		for (SoapEnvelope iteratingSoapEnvelope: soapEnvelopeList) {
			String responseString = cwsManager.executeWebCall(iteratingSoapEnvelope);
			if (cwsResponseProcessor != null) {
				results.putAll(cwsResponseProcessor.processResponse(responseString));
			}
		}
	}

}
