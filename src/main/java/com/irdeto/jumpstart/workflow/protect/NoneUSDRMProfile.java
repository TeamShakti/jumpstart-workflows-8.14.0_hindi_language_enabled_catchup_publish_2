package com.irdeto.jumpstart.workflow.protect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.workflow.FilenameSegments;

public class NoneUSDRMProfile extends AbstractUSDRMProfile {
	private static final String SUBCONTENT_TYPE = "None";

	@Override
	public String getProfileName() {
		return ProtectProfile.ProtectionType.NONE_US.toString();
	}

	@Override
	public String getSubcontentType() {
		return SUBCONTENT_TYPE;
	}

	@Override
	public Map<String, String> getLicenseUrlMap() {
		return new HashMap<>();
	}

	@Override
	public List<String> getProtectionSystemList() {
		return new ArrayList<>();
	}
	
	@Override
	public String getMp4SplitParameters(List<Map<String, String>> drmValues, String entityId, String entityType, FilenameSegments segments) {
		return "";
	}

	@Override
	public int getOrder() {
		return 6;
	}
}
