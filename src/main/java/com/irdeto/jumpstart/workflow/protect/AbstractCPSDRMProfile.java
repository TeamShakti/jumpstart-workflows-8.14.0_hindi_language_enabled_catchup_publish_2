package com.irdeto.jumpstart.workflow.protect;

import java.util.ArrayList;
import java.util.List;

import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.workflow.FileHelper.FileProtectHelper;
import com.irdeto.jumpstart.workflow.FilenameSegments;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;

public abstract class AbstractCPSDRMProfile extends AbstractDRMProfile implements CPSDRMProfile {

	protected static final String SLASH = "/";
	protected static final String MANIFEST_SUFFIX = "manifest";
	protected static final String PATH_IIS = "IIS";
	protected static final String PATH_HLS = "HLS";
	protected static final String PATH_FLASHACCESS = "FlashAccess";

	@Override
	public String getPathRoot() {
		try {
			String pathRoot = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CPS_PATH_ROOT_KEY);
			return pathRoot;
		} catch (PropertyException e) {
		}
		return "";
	}
	
	@Override
	public String getProtectionWorkflow() {
		return PROTECTION_WORKFLOW_CPS;
	}
	
	@Override
	public List<String> getProtectionSystemList() {
		return new ArrayList<>();
	}
	@Override
	public String getUSManifestFilename(String entityId, FilenameSegments segments) {
		return null;
	}
	
	@Override
	public boolean publishLicenseAcquisitionUrl() {
		return false;
	}

	@Override
	public String getCpsSourceURL(String masterSourceFilename, String exampleFilename,Integer policyGroupId) {
		return FileProtectHelper.getCpsSourceFolder(masterSourceFilename, getProfileName(),policyGroupId);
	}
}
