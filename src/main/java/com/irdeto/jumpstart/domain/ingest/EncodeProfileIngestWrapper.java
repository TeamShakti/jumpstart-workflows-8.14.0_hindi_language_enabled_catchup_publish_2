package com.irdeto.jumpstart.domain.ingest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.DeviceProfile;
import com.irdeto.jumpstart.domain.EncodeProfile;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

/**
 * File Name: EncodeProfileIngestWrapper.java
 *
 * Description: The wrapper class for EncodeProfile
 *
 * Developed by Tata Elxsi for Irdeto B.V.
 *
 * Creation Date: 15-Oct-2014
 *
 */
public class EncodeProfileIngestWrapper extends AbstractEntityIngestWrapper<EncodeProfile> {
	public void addDeviceProfileNameList(List<String> deviceProfileNameList) {
		addRelationshipsList(
				WorkflowHelper.DEVICE_PROFILE_RELATIONSHIP_NAME,
				DeviceProfile.class,
				deviceProfileNameList
		);
	}

	public void addChannelUriIdList(List<String> channelUriIdList) {
		addRelationshipsList(
				WorkflowHelper.CHANNEL_RELATIONSHIP_NAME,
				Channel.class,
				channelUriIdList
		);
	}

	public void addEventUriIdList(List<String> eventUriIdList) {
		addRelationshipsList(
				WorkflowHelper.EVENT_RELATIONSHIP_NAME,
				Event.class,
				eventUriIdList
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
	protected List<ImageContent> getImageContentList(EncodeProfile entity) {
		return null;
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(EncodeProfile entity,
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
				EncodeProfile.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_NAME,
				relList
		);
	}
}

//End EncodeProfileIngestWrapper
