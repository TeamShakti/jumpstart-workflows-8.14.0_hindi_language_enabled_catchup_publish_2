package com.irdeto.jumpstart.workflow.protect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.workflow.FileHelper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.config.DeviceProfileHelper;

public class IrdetoSKEProfile extends AbstractCPSDRMProfile {

	private static final String SUBCONTENT_TYPE = "HLS_SKE";
    private static final String LICENSE_URL_SKE = "/HLS/GetEKF.aspx?CrmId=";

	@Override
	public String getProtectProfileName() {
		return ProtectProfile.ProtectionType.HTTP_LIVE_STREAMING_DRM.toString();
	}

	@Override
	public String getProfileName() {
		return ProtectProfile.ProtectionType.IRDETO_SKE.toString();
	}
	
	@Override
	public String getVideoCdnPrefix() {
		return DeviceProfileHelper.getCdnUrlPrefixHLS();
	}

	@Override
	public String getCdnUrlSuffix() {
		return "";
	}

	@Override
	public boolean isEnableActiveCloak2() {
		return false;
	}

	@Override
	public String getSubcontentType() {
		return SUBCONTENT_TYPE;
	}

	@Override
	public String getSourceEncrypt(String cpsSourceURL, String m3u8Filename,
			String ismFilename,Integer policyGroupId) {
		return cpsSourceURL + m3u8Filename;
	}

	@Override
	public List<String> getAdditionalSourceFiles(String filename) {
		return new ArrayList<>();
	}

	@Override
	public boolean isManifestNeeded() {
		return true;
	}

	@Override
	public boolean isDownloadProtectProfile() {
		return false;
	}

	@Override
	public String getProtectedSourcePath(String cpsFilename) {
		String buildUpHybridPath = FileHelper.buildUpHybridPath(getPath(), FileHelper.getLastFoldername(cpsFilename), FileHelper.getFilenameWithoutPath(cpsFilename));
		return FileHelper.addHybridBaseURLAndPath(buildUpHybridPath, WorkflowHelper.PROTECTED_TYPE);
	}

	@Override
	public Map<String, String> getLicenseUrlMap() {
		Map<String, String> licenseUrlMap = new HashMap<>();
		licenseUrlMap.putAll(getHlsLicenseUrlMap());
		licenseUrlMap.putAll(getSkeLicenseUrlMap());
		return licenseUrlMap;
	}

	@Override
	public boolean getSecureHls() {
		return true;
	}
	
	@Override
	public String getPath() {
		return PATH_HLS;
	}

	@Override
	public int getOrder() {
		return 5;
	}
	
	@Override
	public Map<String, String> getSkeLicenseUrlMap() {
		Map<String, String> licenseUrlMap = new HashMap<>();
		licenseUrlMap.put(LICENSE_URL_SKE_TYPE, DeviceProfileHelper.getLicenseServer() + LICENSE_URL_SKE + DeviceProfileHelper.getCrmId());
		return licenseUrlMap;
	}
}
