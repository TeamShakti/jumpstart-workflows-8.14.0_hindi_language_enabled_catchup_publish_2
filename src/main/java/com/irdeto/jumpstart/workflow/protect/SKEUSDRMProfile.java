package com.irdeto.jumpstart.workflow.protect;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.domain.livedrm.GenerateKeysObjectFactory;
import com.irdeto.jumpstart.workflow.FilenameSegments;

public class SKEUSDRMProfile extends AbstractUSDRMProfile {
	private static final Logger logger = LoggerFactory.getLogger(SKEUSDRMProfile.class);
	private static final String SUBCONTENT_TYPE = "US_SKE";
	private static final String UTF_8 = "UTF-8";
	private static final String EMPTY_STRING = "";

	@Override
	public String getProfileName() {
		return ProtectProfile.ProtectionType.SKEUS.toString();
	}
	
	@Override
	public String getSubcontentType() {
		return SUBCONTENT_TYPE;
	}

	@Override
	public List<String> getProtectionSystemList() {
		List<String> protectionSystemList = new ArrayList<>();
		protectionSystemList.add(GenerateKeysObjectFactory.PROTECTION_SYSTEM_SKE);
		return protectionSystemList;
	}
	
	@Override
	public Map<String, String> getLicenseUrlMap() {
		Map<String, String> licenseUrlMap = new HashMap<>();
		licenseUrlMap.putAll(getHlsLicenseUrlMap());
		licenseUrlMap.putAll(getSkeLicenseUrlMap());
		return licenseUrlMap;
	}

	@Override
	public String getMp4SplitParameters(List<Map<String, String>> drmValues,
			String entityId, String entityType, FilenameSegments segments) {
		// SKE
//				--irdeto.content_key=$(cek)
//				--irdeto.license_server_url=$(la_url)
//				--irdeto.key_id=$(kid)
//				--irdeto.key_iv=$(iv)
	
		StringBuffer parameters = new StringBuffer();
		if (drmValues == null || drmValues.size() != 1) {
			logger.error("Invalid number of keys retrieved for SKE.");
			return parameters.toString();
		}
		Map<String, String> skeDrmValue = drmValues.get(0);
		

		String licenseServerURL = skeDrmValue.get("HLS_URI");
		String ekf = skeDrmValue.get("EKF");
		String urlEncodeEKF = EMPTY_STRING;
		try {
			urlEncodeEKF = URLEncoder.encode(ekf, UTF_8);
		} catch (UnsupportedEncodingException e) {
			logger.error("Unable to do URL encode on SKE EKF.", e);
		}
		int EKFindex = licenseServerURL.indexOf(ekf); 
		String finalLAURL = licenseServerURL.substring(0, EKFindex)+(urlEncodeEKF); 
		parameters.append("--irdeto.key_id=").append(bigEndianKeyId(skeDrmValue.get("KeyId"))).append(SPACE);
		parameters.append("--irdeto.content_key=").append(base64EncodedContentKey(skeDrmValue.get("ContentKey"))).append(SPACE);
//		parameters.append("--irdeto.key_iv=").append(skeDrmValue.get("HLS_EXT-X-KEY").substring(skeDrmValue.get("HLS_EXT-X-KEY").length()-32)).append(SPACE);
		parameters.append("--irdeto.license_server_url=").append(finalLAURL);
		return parameters.toString();
	}

	@Override
	public int getOrder() {
		return 10;
	}
}
