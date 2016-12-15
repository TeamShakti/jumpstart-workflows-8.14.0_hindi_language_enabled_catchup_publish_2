package com.irdeto.jumpstart.domain.ingest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.jumpstart.domain.DeviceProfile;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.domain.TranscodeProfile;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

/**
 * File Name: ProtectProfileIngestWrapper.java
 * <p/>
 * Description: The wrapper class for ProtectProfile
 * <p/>
 * Developed by Tata Elxsi for Irdeto B.V.
 * <p/>
 * Creation Date: 15-Oct-2014
 */
public class ProtectProfileIngestWrapper extends AbstractEntityIngestWrapper<ProtectProfile> {
	public void addDeviceProfileNameList(List<String> nameList) {
		addRelationshipsList(
				WorkflowHelper.DEVICE_PROFILE_RELATIONSHIP_NAME,
				DeviceProfile.class,
				nameList
		);
	}

	public void addTranscodeProfileNameList(List<String> nameList) {
		addRelationshipsList(
				WorkflowHelper.TRANSCODE_PROFILE_RELATIONSHIP_NAME,
				TranscodeProfile.class,
				nameList
		);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@JsonIgnore
	@Override
	public QueryFilterParameter getMatchingQueryFilter() {
		return WorkflowHelper.getQueryFilterParameter(WorkflowHelper.ATTRIBUTE_NAME_NAME, getEntity().getName(), false);
	}

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(ProtectProfile entity) {
		return null;
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(ProtectProfile entity,
									   List<ImageContent> imageContentList) {
	}

	@Override
	@JsonIgnore
	public String getUriId() {
		return getEntity().getName();
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return new ArrayList<>();
	}

	protected void addRelationshipsList(String relName, Class rel, List<String> relList) {
		super.addRelationshipsList(
				ProtectProfile.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_NAME,
				relList
		);
	}
}

// End ProtectProfileIngestWrapper
