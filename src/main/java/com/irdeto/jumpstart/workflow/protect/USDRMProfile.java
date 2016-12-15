package com.irdeto.jumpstart.workflow.protect;

import java.util.List;
import java.util.Map;

import com.irdeto.jumpstart.workflow.FilenameSegments;

public interface USDRMProfile extends DRMProfile {
	public String getMp4SplitParameters(List<Map<String, String>> drmValues, String entityId, String entityType, FilenameSegments segments);
	public String getLicenseAcquisitionUrl(String entityId, String entityType, String type);
	public String getLicenseAcquisitionUrl(String entityId, String entityType, FilenameSegments segments, String type);
}
