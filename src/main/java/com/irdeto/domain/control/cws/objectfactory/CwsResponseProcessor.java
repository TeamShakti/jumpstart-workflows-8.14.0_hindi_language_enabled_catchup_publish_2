package com.irdeto.domain.control.cws.objectfactory;

import java.util.Map;

public interface CwsResponseProcessor {
	public Map<String, Object> processResponse(String xmlResponse) throws Exception;
}
