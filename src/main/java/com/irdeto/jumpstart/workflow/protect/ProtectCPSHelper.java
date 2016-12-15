package com.irdeto.jumpstart.workflow.protect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.BooleanUtils;
import org.kie.api.runtime.process.ProcessContext;

import com.irdeto.domain.qts.response.QTSFileInfo;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.protect.ProtectWrapper;
import com.irdeto.jumpstart.workflow.FileHelper;
import com.irdeto.jumpstart.workflow.FileHelper.FileProtectHelper;
import com.irdeto.jumpstart.workflow.FilenameSegments;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class ProtectCPSHelper extends WorkflowHelper {
	private static final String CONTENT_ID_KEY = "contentId";
	private static final String CPS_PATH_TARGET_KEY = "cpsPathTarget";
	private static final String CPS_SOURCE_URL_KEY = "cpsSourceUrl";
	private static final String DRM_ENCRYPTION_TYPE_KEY = "drmEncryptionType";
	private static final String ENABLE_ACTIVE_CLOAK2_KEY = "enableActiveCloak2";
	private static final String ENTITY_ID_KEY = "entityId";
	private static final String FETCH_FILE_LIST_KEY = "fetchFileList";
	private static final String FILE_INFO_KEY = "fileInfo";
	private static final String LICENSE_URL_KEY = "licenseUrl";
	private static final String M3U8_FILENAME_KEY = "m3u8Filename";
	private static final String M3U8INDEX_STRING_KEY = "m3u8indexString";
	private static final String MEDIA_ID_KEY = "mediaId";
	private static final String PROTECT_WRAPPER_KEY = "protectWrapper";
	private static final String SOURCE_ENCRYPT_KEY = "sourceEncrypt";
	private static final String SOURCE_FOR_DELETE_KEY = "sourceForDelete";
	private static final String SOURCE_UPLOAD_KEY = "sourceUpload";
	private static final String SUBCONTENT_TYPE_KEY = "subcontentType";
	private static final String TARGET_ENCRYPT_KEY = "targetEncrypt";
	private static final String TARGET_UPLOAD_KEY = "targetUpload";
	private static final String SECURE_HLS_KEY = "secureHLS";
	private static final String EKF_URL_KEY = "ekfUrl";
	private static final String SOURCE_SEGMENTS_KEY = "sourceSegments";
//	private static final String CPS_TARGET_KEY = "cpsTarget";

	public static void prepareVariables(ProcessContext kcontext) {
		SourceVideoSub sourceVideoSubcontent = (SourceVideoSub)kcontext.getVariable(SOURCE_SUBCONTENT_KEY);
		ProtectWrapper protectWrapper = (ProtectWrapper) kcontext.getVariable(PROTECT_WRAPPER_KEY);
		List<String> sourceFileListCopy = new ArrayList<String>(protectWrapper.getSourceFileList());
		kcontext.setVariable(FETCH_FILE_LIST_KEY, sourceFileListCopy);
		if (!sourceFileListCopy.isEmpty()) {
			String exampleFile = sourceFileListCopy.get(0);
			kcontext.setVariable(CPS_SOURCE_URL_KEY, ProtectHelper.getProfile(protectWrapper).getCpsSourceURL(sourceVideoSubcontent.getSourcePath(), exampleFile, protectWrapper.getPolicyGroupId()));
		}
		//Generates QTS File Create
		setupQTSFileCreate(kcontext);
	}

    public static boolean isManifestNeeded(ProtectWrapper protectWrapper) {
    	return ProtectHelper.getProfile(protectWrapper).isManifestNeeded();
    }

//	public static void entryCreateM3U8IndexFile(ProcessContext kcontext){
//		SourceVideoSub sourceVideoSubcontent = (SourceVideoSub)kcontext.getVariable(SOURCE_SUBCONTENT_KEY);
//		ProtectWrapper protectWrapper = (ProtectWrapper) kcontext.getVariable(PROTECT_WRAPPER_KEY);
//		String sourcePath = sourceVideoSubcontent.getSourcePath();
//		String m3u8indexString = FileProtectHelper.getHlsManifestContent(protectWrapper.getSourceFileList());
//		kcontext.setVariable(M3U8INDEX_STRING_KEY, m3u8indexString);
//		kcontext.setVariable(M3U8_FILENAME_KEY, ProtectHelper.getProfile(protectWrapper).getHlsManifestFilename(sourcePath,protectWrapper.getPolicyGroupId()));
//		kcontext.setVariable(CPS_FTP_TARGET_KEY, FileProtectHelper.getCpsFtpTarget(sourcePath, protectWrapper.getRepresentativeProtect().getProtectionType().toString(),protectWrapper.getPolicyGroupId()));
//	}

    private static void setupQTSFileCreate(ProcessContext kcontext) {
		SourceVideoSub sourceVideoSubcontent = (SourceVideoSub)kcontext.getVariable(SOURCE_SUBCONTENT_KEY);
		ProtectWrapper protectWrapper = (ProtectWrapper) kcontext.getVariable(PROTECT_WRAPPER_KEY);
		String sourcePath = sourceVideoSubcontent.getSourcePath();
	 	String m3u8indexString = FileProtectHelper.getHlsManifestContent(protectWrapper.getSourceFileList());
		kcontext.setVariable(M3U8INDEX_STRING_KEY, m3u8indexString);
	    kcontext.setVariable(M3U8_FILENAME_KEY, ProtectHelper.getProfile(protectWrapper).getHlsManifestFilename(sourcePath,protectWrapper.getPolicyGroupId()));
	   //cpsPathTarget
	    kcontext.setVariable(CPS_PATH_TARGET_KEY, FileProtectHelper.getCpsPathTarget(sourcePath, protectWrapper.getRepresentativeProtect().getProtectionType().toString(),protectWrapper.getPolicyGroupId()));
    }

	public static void entryEncrypt(ProcessContext kcontext){
		ProtectWrapper protectWrapper = (ProtectWrapper) kcontext.getVariable(PROTECT_WRAPPER_KEY);
		SourceVideoSub sourceVideoSubcontent = (SourceVideoSub) kcontext.getVariable(SOURCE_SUBCONTENT_KEY);
		FilenameSegments sourceSegments= (FilenameSegments) kcontext.getVariable(SOURCE_SEGMENTS_KEY);
		String programEntityId = (String) kcontext.getVariable(ENTITY_ID_KEY);
		String cpsSourceURL = (String) kcontext.getVariable(CPS_SOURCE_URL_KEY);
		String m3u8Filename = (String) kcontext.getVariable(M3U8_FILENAME_KEY);
		if (ProtectHelper.getProfile(protectWrapper).isEnableActiveCloak2()) {
			kcontext.setVariable(ENABLE_ACTIVE_CLOAK2_KEY, Boolean.TRUE.toString());
		} else {
			kcontext.setVariable(ENABLE_ACTIVE_CLOAK2_KEY, Boolean.FALSE.toString());
		}
		kcontext.setVariable(SUBCONTENT_TYPE_KEY, ProtectHelper.getProfile(protectWrapper).getSubcontentType());
		kcontext.setVariable(DRM_ENCRYPTION_TYPE_KEY, ProtectHelper.getProfile(protectWrapper).getProtectProfileName());
		kcontext.setVariable(SECURE_HLS_KEY, BooleanUtils.toString(ProtectHelper.getProfile(protectWrapper).getSecureHls(), "true", "false"));
		if (ProtectHelper.getProfile(protectWrapper) instanceof IrdetoSKEProfile) {
			kcontext.setVariable(LICENSE_URL_KEY, ProtectHelper.getProfile(protectWrapper).getLicenseUrlMap().get(AbstractDRMProfile.LICENSE_URL_HLS_TYPE));;
			kcontext.setVariable(EKF_URL_KEY, ProtectHelper.getProfile(protectWrapper).getLicenseUrlMap().get(AbstractDRMProfile.LICENSE_URL_SKE_TYPE));
		} else {
			Set<Entry<String, String>> entrySet = ProtectHelper.getProfile(protectWrapper).getLicenseUrlMap().entrySet();
			Entry<String, String> entry = entrySet.iterator().next();
			kcontext.setVariable(LICENSE_URL_KEY, entry.getValue());;
		}
		String masterSourceFilename = sourceVideoSubcontent.getSourcePath();
		String ismFilename = FileProtectHelper.getIsmFilename(masterSourceFilename);
		kcontext.setVariable(SOURCE_ENCRYPT_KEY, ProtectHelper.getProfile(protectWrapper).getSourceEncrypt(cpsSourceURL, m3u8Filename, ismFilename,protectWrapper.getPolicyGroupId()));
		kcontext.setVariable(TARGET_ENCRYPT_KEY, ProtectHelper.getProfile(protectWrapper).getTargetEncrypt(sourceSegments,protectWrapper.getPolicyGroupId()));

		kcontext.setVariable(CONTENT_ID_KEY, ProtectHelper.getControlAssetId(ENTITY_TYPE_PROGRAM, programEntityId, sourceSegments));
		kcontext.setVariable(MEDIA_ID_KEY, ProtectHelper.getControlAssetId(ENTITY_TYPE_PROGRAM, programEntityId, sourceSegments));
	}

	public static void entryUploadProtectedFile(ProcessContext kcontext){
		ProtectWrapper protectWrapper = (ProtectWrapper) kcontext.getVariable(PROTECT_WRAPPER_KEY);
		QTSFileInfo fileInfo = (QTSFileInfo) kcontext.getVariable(FILE_INFO_KEY);
		kcontext.setVariable(SOURCE_UPLOAD_KEY, ProtectHelper.getProfile(protectWrapper).getSourceUploadAfterProtect(fileInfo));
		kcontext.setVariable(TARGET_UPLOAD_KEY, ProtectHelper.getProfile(protectWrapper).getTargetUploadAfterProtect(fileInfo.getName()));
	}

	public static void entryDeleteCPSLocalFiles(ProcessContext kcontext){
		String sourceEncrypt = (String) kcontext.getVariable(SOURCE_ENCRYPT_KEY);
		kcontext.setVariable(SOURCE_FOR_DELETE_KEY, FileHelper.getFilePathWithoutFilename(sourceEncrypt));
	}
}
