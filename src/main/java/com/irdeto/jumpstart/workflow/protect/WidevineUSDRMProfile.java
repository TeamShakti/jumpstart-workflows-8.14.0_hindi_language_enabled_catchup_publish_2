package com.irdeto.jumpstart.workflow.protect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.domain.livedrm.GenerateKeysObjectFactory;
import com.irdeto.jumpstart.workflow.FilenameSegments;

public class WidevineUSDRMProfile extends AbstractUSDRMProfile {
	private static final Logger logger = LoggerFactory.getLogger(WidevineUSDRMProfile.class);
	private static final String SUBCONTENT_TYPE = "US_Widevine";

	@Override
	public String getProfileName() {
		return ProtectProfile.ProtectionType.WIDEVINE_US.toString();
	}
	
	@Override
	public String getSubcontentType() {
		return SUBCONTENT_TYPE;
	}

	@Override
	public List<String> getProtectionSystemList() {
		List<String> protectionSystemList = new ArrayList<>();
		protectionSystemList.add(GenerateKeysObjectFactory.PROTECTION_SYSTEM_WIDEVINE);
		return protectionSystemList;
	}
	
	@Override
	public String getMp4SplitParameters(List<Map<String, String>> drmValues, String entityId, String entityType, FilenameSegments segments) {
		// WIDEVINE
//				--widevine.key_id=$key_id
//				--widevine.content_key=$content_key
//				--widevine.license_server_url=$la_url
//				--widevine.drm_specific_data=$pssh
	
		StringBuffer parameters = new StringBuffer();
		if (drmValues == null || drmValues.size() != 1) {
			logger.error("Invalid number of keys retrieved for Widevine.");
			return parameters.toString();
		}
		Map<String, String> widevineDrmValue = drmValues.get(0);
//		parameters.append("--widevine.key_id=").append(bigEndianKeyId(widevineDrmValue.get("KeyId"))).append(SPACE);
//		Do not big endian the standalone widevine US key ID
		String keyId = widevineDrmValue.get("KeyId");
		parameters.append("--widevine.key_id=").append(keyId.replaceAll("" + DASH, "")).append(SPACE);
		parameters.append("--widevine.content_key=").append(base64EncodedContentKey(widevineDrmValue.get("ContentKey"))).append(SPACE);
		parameters.append("--widevine.drm_specific_data=").append(widevineDrmValue.get("PSSH")).append(SPACE);
		parameters.append("--widevine.license_server_url=").append(getLicenseAcquisitionUrl(entityId, entityType, segments, LICENSE_URL_WIDEVINE_TYPE));
		return parameters.toString();
	}

	@Override
	public Map<String, String> getLicenseUrlMap() {
		return getWidevineLicenseUrlMap();
	}
	
	@Override
	public boolean publishLicenseAcquisitionUrl() {
		return true;
	}

	@Override
	public int getOrder() {
		return 11;
	}
}
