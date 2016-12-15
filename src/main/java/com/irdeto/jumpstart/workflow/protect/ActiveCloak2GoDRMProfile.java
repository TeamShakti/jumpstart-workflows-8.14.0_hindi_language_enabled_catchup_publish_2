package com.irdeto.jumpstart.workflow.protect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.workflow.FileHelper;
import com.irdeto.jumpstart.workflow.FileHelper.FileProtectHelper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.config.DeviceProfileHelper;

public class ActiveCloak2GoDRMProfile extends AbstractCPSDRMProfile {

	private static final String SUBCONTENT_TYPE = "AC_D2G";

	@Override
	public String getProfileName() {
		return ProtectProfile.ProtectionType.ACTIVE_CLOAK_2_GO_DRM.toString();
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
		return true;
	}

	@Override
	public String getSubcontentType() {
		return SUBCONTENT_TYPE;
	}

	@Override
	public String getSourceEncrypt(String cpsSourceURL, String m3u8Filename,
			String ismFilename,Integer policyGroupId) {
		return cpsSourceURL;
	}

	@Override
	public String getCpsSourceURL(String masterSourceFilename, String d2gFilename,Integer policyGroupId) {
		return FileProtectHelper.getD2gFileURL(masterSourceFilename,d2gFilename,getProfileName(),policyGroupId);
	}
	
	@Override
	public List<String> getAdditionalSourceFiles(String filename) {
		return new ArrayList<>();
	}
	
	@Override
	public boolean isManifestNeeded() {
		return false;
	}

	@Override
	public boolean isDownloadProtectProfile() {
		return true;
	}

/*	@Override
	public String getSourceUploadAfterProtect(QTSFileInfo fileInfo) {
		return fileInfo.getName();
	}*/

	@Override
	public String getTargetUploadAfterProtect(String masterSourceFilename) {
		String basePath = FileHelper.addHybridBaseURLAndPath(getPath(), WorkflowHelper.PROTECTED_TYPE);
		return basePath + SLASH;
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
		return 1;
	}
}
