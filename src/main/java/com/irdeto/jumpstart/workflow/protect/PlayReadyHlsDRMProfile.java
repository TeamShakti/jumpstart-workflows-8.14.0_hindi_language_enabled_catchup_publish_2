package com.irdeto.jumpstart.workflow.protect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.workflow.FileHelper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.config.DeviceProfileHelper;

public class PlayReadyHlsDRMProfile extends AbstractCPSDRMProfile {

	private static final String SUBCONTENT_TYPE = "HLS_PlayReady";

	@Override
	public String getProfileName() {
		return ProtectProfile.ProtectionType.PLAY_READY_HLS_DRM.toString();
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
		return getPlayReadyLicenseUrlMap();
	}
	
	@Override
	public String getPath() {
		return PATH_HLS;
	}

	@Override
	public int getOrder() {
		return 8;
	}
}
