package com.irdeto.jumpstart.manager;

import java.util.Map;

import com.irdeto.jumpstart.domain.livedrm.LiveDrmResponseProcessor;
import com.irdeto.jumpstart.domain.livedrm.LiveDrmSoapEnvelope;

public interface ControlLiveDrmManager {

	public Map<String, Object> executeWebCall(
			LiveDrmSoapEnvelope liveDrmSoapEnvelope, LiveDrmResponseProcessor responseProcessor) throws Exception;

}