package com.irdeto.jumpstart.manager;

import com.irdeto.domain.control.cws.SoapEnvelope;

public interface ControlCwsManager {
	
	public String executeWebCall(SoapEnvelope soapEnvelope) throws Exception;
}
