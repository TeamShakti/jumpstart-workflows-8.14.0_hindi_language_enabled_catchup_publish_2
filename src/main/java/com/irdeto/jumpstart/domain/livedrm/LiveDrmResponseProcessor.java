package com.irdeto.jumpstart.domain.livedrm;

import java.util.Map;

public interface LiveDrmResponseProcessor {
	public Map<String, Object> processResponse(String xmlResponse) throws Exception;
}
