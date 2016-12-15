package com.irdeto.jumpstart.domain.ingest;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.jumpstart.domain.DeviceProfile;
import com.irdeto.jumpstart.domain.EncodeProfile;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.domain.TermMapping;
import com.irdeto.jumpstart.domain.TranscodeProfile;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

/**
 * File Name: DeviceProfileIngestWrapper.java
 *
 * Description: The wrapper class for DeviceProfile
 *
 * Developed by Tata Elxsi for Irdeto B.V.
 *
 * Creation Date: 15-Oct-2014
 *
 */
public class DeviceProfileIngestWrapper extends AbstractEntityIngestWrapper<DeviceProfile> {

	/**
	 * This method will add the relationship between DeviceProfile and EncodeProfile entities
	 *
	 * @param encodeProfileNameList
	 * @return none
	 */
	public void addEncodeProfileNameList(List<String> encodeProfileNameList) {
		addRelationshipsList(
				WorkflowHelper.ENCODE_PROFILE_RELATIONSHIP_NAME,
				EncodeProfile.class,
				encodeProfileNameList
		);
	}

	/**
	 * This method will add the relationship between DeviceProfile and TranscodeProfile entities
	 *
	 * @param transcodeProfileNameList
	 * @return none
	 */
	public void addTranscodeProfileNameList(List<String> transcodeProfileNameList) {
		addRelationshipsList(
				WorkflowHelper.TRANSCODE_PROFILE_RELATIONSHIP_NAME,
				TranscodeProfile.class,
				transcodeProfileNameList
		);
	}

	/**
	 * This method will add the relationship between DeviceProfile and ProtectProfile entities
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

	public void addTermMappingNameList(List<String> termMappingNameList) {
		addRelationshipsList(
				WorkflowHelper.PROTECT_PROFILE_RELATIONSHIP_NAME,
				TermMapping.class,
				termMappingNameList
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
	protected List<ImageContent> getImageContentList(DeviceProfile entity) {
		return null;
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(DeviceProfile entity,
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
		return Arrays.asList(WorkflowHelper.ENCODE_PROFILE_RELATIONSHIP_NAME,
				WorkflowHelper.PROTECT_PROFILE_RELATIONSHIP_NAME,
				WorkflowHelper.TRANSCODE_PROFILE_RELATIONSHIP_NAME);
	}

	protected void addRelationshipsList(String relName, Class rel, List<String> relList) {
		super.addRelationshipsList(
				DeviceProfile.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_NAME,
				relList
		);
	}
}

// End DeviceProfileIngestWrapper
