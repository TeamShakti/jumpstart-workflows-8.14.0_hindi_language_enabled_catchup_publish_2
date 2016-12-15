package com.irdeto.jumpstart.manager;

import java.util.List;

import com.irdeto.jumpstart.domain.control.qsa.ControlQSAAuthorizationInfo;

public interface ControlQuerySessionAuthorizationManager {
	public ControlQSAAuthorizationInfo getAuthorizationInfo(String contentId) throws Exception;
	public List<ControlQSAAuthorizationInfo> getAuthorizationInfoList(List<String> contentIdList) throws Exception;
}
