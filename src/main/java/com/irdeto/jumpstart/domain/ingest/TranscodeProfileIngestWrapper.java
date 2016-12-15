package com.irdeto.jumpstart.domain.ingest;

import java.util.Arrays;
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
 * File Name: TranscodeProfileIngestWrapper.java
 * <p/>
 * Description: The wrapper class for TranscodeProfile
 * <p/>
 * Developed by Tata Elxsi for Irdeto B.V.
 * <p/>
 * Creation Date: 15-Oct-2014
 */
public class TranscodeProfileIngestWrapper extends AbstractEntityIngestWrapper<TranscodeProfile> {

	/**
	 * This method will add the relationship between TranscodeProfile and ProtectProfile entities
	 *
	 * @param protectProfileNameList
	 * @return none
	 */
	public void addProtectProfileNameList(List<String> protectProfileNameList) {
		addRelationshipsList(
				WorkflowHelper.PROTECT_PROFILE_RELATIONSHIP_NAME,
				ProtectProfile.class,
				protectProfileNameList
		);
	}

	public void addDeviceProfileNameList(List<String> deviceProfileNameList) {
		addRelationshipsList(
				WorkflowHelper.DEVICE_PROFILE_RELATIONSHIP_NAME,
				DeviceProfile.class,
				deviceProfileNameList
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
	protected List<ImageContent> getImageContentList(TranscodeProfile entity) {
		return null;
	}


	@Override
	@JsonIgnore
	protected void setImageContentList(TranscodeProfile entity,
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
		return Arrays.asList(WorkflowHelper.PROTECT_PROFILE_RELATIONSHIP_NAME);
	}

	protected void addRelationshipsList(String relName, Class rel, List<String> relList) {
		super.addRelationshipsList(
				TranscodeProfile.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_NAME,
				relList
		);
	}
}

// End TranscodeProfileIngestWrapper
