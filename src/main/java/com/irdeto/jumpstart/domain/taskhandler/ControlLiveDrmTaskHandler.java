package com.irdeto.jumpstart.domain.taskhandler;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.jumpstart.domain.livedrm.LiveDrmResponseProcessor;
import com.irdeto.jumpstart.domain.livedrm.LiveDrmSoapEnvelope;
import com.irdeto.jumpstart.manager.ControlLiveDrmManager;
import com.irdeto.jumpstart.manager.ControlLiveDrmManagerImpl;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.web.WebManager;
import com.irdeto.manager.xml.XmlManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("ControlLiveDrm")
public class ControlLiveDrmTaskHandler extends AbstractTaskHandler {

	@TaskProperty(required=true, type = LiveDrmSoapEnvelope.class)
	private static final Object LIVE_DRM_SOAP_ENVELOPE_PARAMETER = "LiveDrmSoapEnvelope";
	@TaskProperty(required=true, type = LiveDrmResponseProcessor.class)
	private static final Object LIVE_DRM_RESPONSE_PROCESSOR_PARAMETER = "LiveDrmResponseProcessor";
	
	@Resource(name="propertiesManager")
	private PropertiesManager propertiesManager;

	@Resource(name="webManager")
	private WebManager webManager;

	@Resource(name="xmlManager")
	private XmlManager xmlManager;

	private ControlLiveDrmManager controlLiveDrmManager;
	
	@PostConstruct
	protected void setupControlLiveDrmManager() {
		controlLiveDrmManager = new ControlLiveDrmManagerImpl(propertiesManager, webManager, xmlManager);
	}
	
	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> params, Map<String, Object> results) throws Exception {
		LiveDrmSoapEnvelope liveDrmSoapEnvelope = (LiveDrmSoapEnvelope)params.get(LIVE_DRM_SOAP_ENVELOPE_PARAMETER);
		LiveDrmResponseProcessor liveDrmResponseProcessor = (LiveDrmResponseProcessor)params.get(LIVE_DRM_RESPONSE_PROCESSOR_PARAMETER);
		results.putAll(controlLiveDrmManager.executeWebCall(liveDrmSoapEnvelope, liveDrmResponseProcessor));
	}

}
