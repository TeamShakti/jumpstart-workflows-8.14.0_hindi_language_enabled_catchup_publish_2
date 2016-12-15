package com.irdeto.jumpstart.workflow.protect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.domain.livedrm.GenerateKeysObjectFactory;
import com.irdeto.jumpstart.workflow.FilenameSegments;

public class PlayReadyWidevineMultiUSDRMProfile extends AbstractUSDRMProfile {
	private static final Logger logger = LoggerFactory.getLogger(PlayReadyWidevineMultiUSDRMProfile.class);
	private static final String SUBCONTENT_TYPE = "US_PlayReady_Widevine";

	@Override
	public String getProfileName() {
		return ProtectProfile.ProtectionType.PLAY_READY_WIDEVINE_MULTI_US.toString();
	}
	
	@Override
	public String getSubcontentType() {
		return SUBCONTENT_TYPE;
	}

	@Override
	public Map<String, String> getLicenseUrlMap() {
		Map<String, String> licenseUrlMap = new HashMap<>();
		licenseUrlMap.putAll(getPlayReadyLicenseUrlMap());
		licenseUrlMap.putAll(getWidevineLicenseUrlMap());
		return licenseUrlMap;
	}

	@Override
	public List<String> getProtectionSystemList() {
		List<String> protectionSystemList = new ArrayList<>();
		protectionSystemList.add(GenerateKeysObjectFactory.PROTECTION_SYSTEM_PLAYREADY);
		protectionSystemList.add(GenerateKeysObjectFactory.PROTECTION_SYSTEM_WIDEVINE);
		return protectionSystemList;
	}
	
	@Override
	public String getMp4SplitParameters(List<Map<String, String>> drmValues,
			String entityId, String entityType, FilenameSegments segments) {
		// PLAYREADY
//				--iss.key_id=000102030405060708090a0b0c0d0e0f \
//				--iss.content_key=000102030405060708090a0b0c0d0e0f \
//				--iss.key_iv=0001020304050607 \
//				--iss.license_server_url=http://www.example.com/rightsmanager.asmx \
		// WIDEVINE
//				--widevine.key_id=$key_id
//				--widevine.content_key=$content_key
//				--widevine.license_server_url=$la_url
//				--widevine.drm_specific_data=$pssh
	
		StringBuffer parameters = new StringBuffer();
		if (drmValues == null || drmValues.size() != 2) {
			logger.error("Invalid number of keys retrieved for PlayReady and Widevine.");
			return parameters.toString();
		}
		Map<String, String> playReadyDrmValue = drmValues.get(0);
		parameters.append("--iss.key_id=").append(bigEndianKeyId(playReadyDrmValue.get("KeyId"))).append(SPACE);
		parameters.append("--iss.content_key=").append(base64EncodedContentKey(playReadyDrmValue.get("ContentKey"))).append(SPACE);
	   // parameters.append("--iss.key_iv=").append(playReadyDrmValue.get("IV")).append(SPACE);
		parameters.append("--iss.license_server_url=").append(playReadyDrmValue.get("PlayReadyLicenseURL")).append(SPACE);

		Map<String, String> widevineDrmValue = drmValues.get(1);
		parameters.append("--widevine.key_id=").append(bigEndianKeyId(widevineDrmValue.get("KeyId"))).append(SPACE);
		parameters.append("--widevine.content_key=").append(base64EncodedContentKey(widevineDrmValue.get("ContentKey"))).append(SPACE);
		parameters.append("--widevine.drm_specific_data=").append(widevineDrmValue.get("PSSH")).append(SPACE);
		parameters.append("--widevine.license_server_url=").append(getLicenseAcquisitionUrl(entityId, entityType, segments, LICENSE_URL_WIDEVINE_TYPE));
		return parameters.toString();
	}

	
	@Override
	public boolean publishLicenseAcquisitionUrl() {
		return true;
	}

	@Override
	public int getOrder() {
		return 12;
	}
}
