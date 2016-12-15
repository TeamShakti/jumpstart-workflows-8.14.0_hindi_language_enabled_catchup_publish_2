package com.irdeto.jumpstart.workflow.protect;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.workflow.FileHelper;
import com.irdeto.jumpstart.workflow.FilenameSegments;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.config.DeviceProfileHelper;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;

public abstract class AbstractUSDRMProfile extends AbstractDRMProfile implements USDRMProfile {

	private static final char[] hexArray = "0123456789abcdef".toCharArray();
	private static final String CONTENT_ID_KEY = "ContentId";
	private static final String ACCOUNT_ID_KEY = "AccountId";
	private static final char AMPERSAND = '&';
	private static final char EQUALS = '=';
	protected static final char SPACE = ' ';
	protected static final String PATH_US = "US";

	@Override
	public String getPathRoot() {
		try {
			return BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.US_PATH_ROOT_KEY);
		} catch (PropertyException e) {
		}
		return "";
	}

	@Override
	public String getVideoCdnPrefix() {
		return DeviceProfileHelper.getCdnUrlPrefixUS();
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
	public String getSourceEncrypt(String cpsSourceURL, String m3u8Filename,
			String ismFilename,Integer policyGroupId) {
		return null;
	}
	
	@Override
	public String getCpsSourceURL(String masterSourceFilename, String d2gFilename,Integer policyGroupId) {
		return null;
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
		return false;
	}

	@Override
	public String getProtectedSourcePath(String cpsFilename) {
		String buildUpHybridPath = FileHelper.buildUpHybridPath(getPath(), FileHelper.getLastFoldername(cpsFilename), FileHelper.getFilenameWithoutPath(cpsFilename));
		return FileHelper.addHybridBaseURLAndPath(buildUpHybridPath, WorkflowHelper.PROTECTED_TYPE);
	}

	@Override
	public String getProtectionWorkflow() {
		return PROTECTION_WORKFLOW_US;
	}

	@Override
	public String getUSManifestFilename(String entityId,
			FilenameSegments segments) {
		StringBuffer manifestFilename = new StringBuffer();
		manifestFilename.append(entityId)
			.append(DASH)
			.append(segments.getContentId())
			.append(DASH)
			.append(segments.getSourceFileVersion())
			.append(DASH)
			.append(segments.getDateHash())
			.append(DASH)
			.append(getSubcontentType())
			.append(".ism");
		return manifestFilename.toString();
	}
	
	@Override
	public String getPath() {
		return PATH_US;
	}
		
	public String getLicenseAcquisitionUrl(String entityId, String entityType, String type) {
		return getLicenseAcquisitionUrl(entityId, entityType, null, type);
	}
	public String getLicenseAcquisitionUrl(String entityId, String entityType, FilenameSegments segments, String type) {
		try {
			String accountId = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CPS_ACCOUNT_ID_KEY);
			String controlAssetId = null;
			if (segments != null) {
				controlAssetId = ProtectHelper.getControlAssetId(entityType, entityId, segments);
			} else {
				controlAssetId = ProtectHelper.getControlAssetId(entityType, entityId);
			}
			StringBuffer licenseAcquisitionUrl = new StringBuffer();
			licenseAcquisitionUrl.append(getLicenseUrlMap().get(type))
				.append(AMPERSAND).append(ACCOUNT_ID_KEY).append(EQUALS).append(accountId)
				.append(AMPERSAND).append(CONTENT_ID_KEY).append(EQUALS).append(controlAssetId);
			return licenseAcquisitionUrl.toString();
		} catch (PropertyException e) {
			return null;
		}
	}

	protected String bigEndianKeyId(String keyId){
		if(keyId.length()!=36){
			return null;
		}
		keyId = keyId.replaceAll("" + DASH, "");
		char[] bigEndianKeyId = keyId.toCharArray();
		bigEndianKeyId[0] = keyId.charAt(6);
		bigEndianKeyId[1] = keyId.charAt(7);
		bigEndianKeyId[2] = keyId.charAt(4);
		bigEndianKeyId[3] = keyId.charAt(5);
		bigEndianKeyId[4] = keyId.charAt(2);
		bigEndianKeyId[5] = keyId.charAt(3);
		bigEndianKeyId[6] = keyId.charAt(0);
		bigEndianKeyId[7] = keyId.charAt(1);
		
		bigEndianKeyId[8] = keyId.charAt(10);
		bigEndianKeyId[9] = keyId.charAt(11);
		bigEndianKeyId[10] = keyId.charAt(8);
		bigEndianKeyId[11] = keyId.charAt(9);

		bigEndianKeyId[12] = keyId.charAt(14);
		bigEndianKeyId[13] = keyId.charAt(15);
		bigEndianKeyId[14] = keyId.charAt(12);
		bigEndianKeyId[15] = keyId.charAt(13);
		
		return new String(bigEndianKeyId);
	}

	protected String base64EncodedContentKey(String contentKey) {
		byte[] decodedByte = DatatypeConverter.parseBase64Binary(contentKey);
		return bytesToHex(decodedByte);
	}
	
	protected String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	@Override
	public boolean publishLicenseAcquisitionUrl() {
		return false;
	}
}
