package com.irdeto.jumpstart.workflow.protect;

import java.util.HashMap;
import java.util.Map;

import com.irdeto.domain.qts.response.QTSFileInfo;
import com.irdeto.jumpstart.workflow.FileHelper;
import com.irdeto.jumpstart.workflow.FilenameSegments;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.config.DeviceProfileHelper;

public abstract class AbstractDRMProfile implements DRMProfile {
	protected static final String LICENSE_URL_FLASH_ACCESS = "/flashaccess/license/v3";
	protected static final String LICENSE_URL_FLASH_ACCESS_TYPE = "flashaccess";
	protected static final String LICENSE_URL_HLS = "/HLS/GetKey.aspx?CrmId=";
	protected static final String LICENSE_URL_HLS_TYPE = "hls";
	protected static final String LICENSE_URL_PLAY_READY = "/playready/rightsmanager.asmx?CrmId=";
	protected static final String LICENSE_URL_PLAY_READY_TYPE = "playready";
	protected static final String LICENSE_URL_SKE = "/HLS/GetKey.aspx?CrmId=";
	protected static final String LICENSE_URL_SKE_TYPE = "ske";
	protected static final String LICENSE_URL_WIDEVINE = "/Widevine/getlicense?CrmId=";
	protected static final String LICENSE_URL_WIDEVINE_TYPE = "widevine";
	
	protected static final String PROTECTION_WORKFLOW_US = "ProtectUS";
	protected static final String PROTECTION_WORKFLOW_CPS = "ProtectCPS";
	
	protected static final char BACKSLASH = '\\';
	protected static final char DASH = '-';
	
	public abstract String getPathRoot();
	
	@Override
	 public String getTargetEncrypt(FilenameSegments segments,Integer policyGroupId) {
		String pathRoot = getPathRoot();
		StringBuffer target = new StringBuffer();
		target.append(pathRoot);
		if (target.charAt(pathRoot.length() - 1) != BACKSLASH) {
			target.append(BACKSLASH);
		}
		target.append(segments.toString())
			.append(DASH)
			.append(String.valueOf(policyGroupId))
			.append(DASH)
			.append(getProfileName())
			.append(BACKSLASH)
			.append(segments.toString())
			.append(DASH)
			.append(String.valueOf(policyGroupId))
			.append(DASH)
			.append(getProfileName())
			.append(BACKSLASH);
		return target.toString();
	}

	@Override
	public boolean getSecureHls() {
		return false;
	}

	@Override
	public String getProtectProfileName() {
		return getProfileName();
	}
	
	@Override
	public String getHlsManifestFilename(String masterSourceFilename, Integer policyGroupId) {
		return FileHelper.getFileBaseName(masterSourceFilename) + FileHelper.SEPARATOR_FILENAME_PART + String.valueOf(policyGroupId)+ FileHelper.SEPARATOR_FILENAME_PART + getProfileName() + FileHelper.SEPARATOR_PERIOD + FileHelper.EXTENSION_M3U8;
	}
	
	public abstract String getPath();
	
	@Override
	public String getTargetUploadAfterProtect(String masterSourceFilename) {
		String basePath = FileHelper.addHybridBaseURLAndPath(getPath(), WorkflowHelper.PROTECTED_TYPE);
//		String lastFoldername = FileHelper.getLastFoldername(masterSourceFilename);
//		String filename = FileHelper.getFilenameWithoutPath(masterSourceFilename);
//		String absolutePath = FileHelper.buildUpHybridPath(basePath, lastFoldername, filename);
//		return absolutePath;
		return basePath;
	}
	
	@Override
	public String getSourceUploadAfterProtect(QTSFileInfo fileInfo) {
		return FileHelper.getFilePathWithoutFilename(fileInfo.getName());
	}
	
	protected Map<String, String> getPlayReadyLicenseUrlMap() {
		Map<String, String> licenseUrlMap = new HashMap<>();
		licenseUrlMap.put(LICENSE_URL_PLAY_READY_TYPE, DeviceProfileHelper.getLicenseServer() + LICENSE_URL_PLAY_READY + DeviceProfileHelper.getCrmId());
		return licenseUrlMap;
	}
	
	protected Map<String, String> getFlashReadyLicenseUrlMap() {
		Map<String, String> licenseUrlMap = new HashMap<>();
		licenseUrlMap.put(LICENSE_URL_FLASH_ACCESS_TYPE, DeviceProfileHelper.getLicenseServer() + LICENSE_URL_FLASH_ACCESS + DeviceProfileHelper.getCrmId());
		return licenseUrlMap;
	}

	protected Map<String, String> getHlsLicenseUrlMap() {
		Map<String, String> licenseUrlMap = new HashMap<>();
		licenseUrlMap.put(LICENSE_URL_HLS_TYPE, DeviceProfileHelper.getLicenseServer() + LICENSE_URL_HLS + DeviceProfileHelper.getCrmId());
		return licenseUrlMap;
	}

	public Map<String, String> getSkeLicenseUrlMap() {
		Map<String, String> licenseUrlMap = new HashMap<>();
		licenseUrlMap.put(LICENSE_URL_SKE_TYPE, DeviceProfileHelper.getLicenseServer() + LICENSE_URL_SKE + DeviceProfileHelper.getCrmId());
		return licenseUrlMap;
	}

	protected Map<String, String> getWidevineLicenseUrlMap() {
		Map<String, String> licenseUrlMap = new HashMap<>();
		licenseUrlMap.put(LICENSE_URL_WIDEVINE_TYPE, DeviceProfileHelper.getLicenseServer() + LICENSE_URL_WIDEVINE + DeviceProfileHelper.getCrmId());
		return licenseUrlMap;
	}
	
	@Override
	public String getDestinationCopyFolderToCDN(String publishFolder) {
		return FileHelper.buildUpHybridPath(FileHelper.addHybridBaseURLAndPath(getPath(), WorkflowHelper.CDN_TYPE), publishFolder);
	}
	
	@Override
	public String getSourceListFoldersForPublish(String protectedSourcePath) {
		return FileHelper.buildUpHybridPath(getPath(), FileHelper.getLastFoldername(protectedSourcePath));
	}
}
