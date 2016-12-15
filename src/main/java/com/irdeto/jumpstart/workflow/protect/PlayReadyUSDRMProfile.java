package com.irdeto.jumpstart.workflow.protect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.domain.livedrm.GenerateKeysObjectFactory;
import com.irdeto.jumpstart.workflow.FilenameSegments;

public class PlayReadyUSDRMProfile extends AbstractUSDRMProfile {
	private static final Logger logger = LoggerFactory.getLogger(PlayReadyUSDRMProfile.class);

	private static final String SUBCONTENT_TYPE = "US_PlayReady";

	@Override
	public String getProfileName() {
		return ProtectProfile.ProtectionType.PLAY_READY_US.toString();
	}

	@Override
	public String getSubcontentType() {
		return SUBCONTENT_TYPE;
	}

	@Override
	public Map<String, String> getLicenseUrlMap() {
		return getPlayReadyLicenseUrlMap();
	}

	@Override
	public List<String> getProtectionSystemList() {
		List<String> protectionSystemList = new ArrayList<>();
		protectionSystemList.add(GenerateKeysObjectFactory.PROTECTION_SYSTEM_PLAYREADY);
		return protectionSystemList;
	}
	
	@Override
	public String getMp4SplitParameters(List<Map<String, String>> drmValues, String entityId, String entityType, FilenameSegments segments) {
//		  --iss.key_id=000102030405060708090a0b0c0d0e0f \
//		  --iss.content_key=000102030405060708090a0b0c0d0e0f \
//		  --iss.key_iv=0001020304050607 \
//		  --iss.license_server_url=http://www.example.com/rightsmanager.asmx \
		StringBuffer parameters = new StringBuffer();
		if (drmValues == null || drmValues.size() != 1) {
			logger.error("Invalid number of keys retrieved for PlayReady.");
			return parameters.toString();
		}
		Map<String, String> drmValue = drmValues.get(0);
		parameters.append("--iss.key_id=").append(bigEndianKeyId(drmValue.get("KeyId"))).append(SPACE);
		parameters.append("--iss.content_key=").append(base64EncodedContentKey(drmValue.get("ContentKey"))).append(SPACE);
	//	parameters.append("--iss.key_iv=").append(drmValue.get("IV")).append(SPACE);
		parameters.append("--iss.license_server_url=").append(drmValue.get("PlayReadyLicenseURL"));

		return parameters.toString();
	}

	@Override
	public int getOrder() {
		return 9;
	}
}
