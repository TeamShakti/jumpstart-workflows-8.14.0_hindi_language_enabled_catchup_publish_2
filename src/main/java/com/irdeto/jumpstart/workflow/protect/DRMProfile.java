package com.irdeto.jumpstart.workflow.protect;

import java.util.List;
import java.util.Map;

import com.irdeto.domain.qts.response.QTSFileInfo;
import com.irdeto.jumpstart.workflow.FilenameSegments;

public interface DRMProfile {
	public String getProtectionWorkflow();
	public String getProfileName();
	public String getProtectProfileName();
	public String getVideoCdnPrefix();
	public String getCdnUrlSuffix();
	public boolean isEnableActiveCloak2();
	public String getSubcontentType();
	public String getCpsSourceURL(String masterSourceFilename, String d2gFilename,Integer policyGroupId);
	public String getSourceEncrypt(String cpsSourceURL, String m3u8Filename,String ismFilename,Integer policyGroupId);
	public String getTargetEncrypt(FilenameSegments filenameSegments,Integer policyGroupId);
	public String getSourceUploadAfterProtect(QTSFileInfo fileInfo);
	public String getTargetUploadAfterProtect(String masterSourceFilename);
	public List<String> getAdditionalSourceFiles(String filename);
	public boolean isManifestNeeded();
	public String getHlsManifestFilename(String masterSourceFilename,Integer policyGroupId);
	public boolean isDownloadProtectProfile();
	public String getSourceListFoldersForPublish(String protectedSourcePath);
	public String getDestinationCopyFolderToCDN(String publishFolder);
	public String getProtectedSourcePath(String cpsFilename);
	public Map<String, String> getLicenseUrlMap();
	public boolean getSecureHls();
	public List<String> getProtectionSystemList();
	public String getUSManifestFilename(String entityId, FilenameSegments segments);
	public boolean publishLicenseAcquisitionUrl();
	public int getOrder();
	public Map<String, String> getSkeLicenseUrlMap();
}
