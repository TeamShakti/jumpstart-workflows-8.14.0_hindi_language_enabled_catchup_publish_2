package com.irdeto.jumpstart.domain.ingest;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.jumpstart.domain.BaseMetadata.DataMaster;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.EncodeProfile;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.ingest.JDOMXmlTvMapper;

public class ChannelIngestWrapper extends AbstractEntityIngestWrapper<Channel> {

	public void addEncodeProfileNameList(List<String> encodeProfileNameList) {
		super.addRelationshipsList(
				Channel.class,
				WorkflowHelper.ENCODE_PROFILE_RELATIONSHIP_NAME,
				EncodeProfile.class,
				WorkflowHelper.ATTRIBUTE_NAME_NAME,
				encodeProfileNameList
		);
	}

	public void addSubscriptionPackageUriIdList(List<String> subscriptionPackageUriIdList) {
		addRelationshipsList(
				WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME,
				SubscriptionPackage.class,
				subscriptionPackageUriIdList
		);
	}

	public void addScheduleSlotUriIdList(List<String> scheduleSlotUriIdList) {
		addRelationshipsList(
				WorkflowHelper.SCHEDULE_SLOT_RELATIONSHIP_NAME,
				ScheduleSlot.class,
				scheduleSlotUriIdList
		);
	}

	@JsonIgnore
	@Override
	public QueryFilterParameter getMatchingQueryFilter() {
		return WorkflowHelper.getQueryFilterParameter(WorkflowHelper.ATTRIBUTE_NAME_CHANNEL_ID, getEntity().getChannelId(), false);
	}

	@Override
	@JsonIgnore
	public void mergeEntity() {
		boolean isUpdate = (getStoredEntity() != null);
		if (isUpdate && DataMaster.MEDIA_MANAGER.equals(getStoredEntity().getDataMaster())) {
			setEntity(getStoredEntity());
			return;
		}
		boolean enabled = false;
		String mmChannelUriId = null;
		List<ImageContent> imageContent = null;
		// Stash the values from Media Manager prior to merging.
		if (isUpdate) {
			enabled = BooleanUtils.isTrue(getStoredEntity().getEnabled());
		}

		if (isUpdate && getSourceInformation() != null && JDOMXmlTvMapper.class.getCanonicalName().equals(getSourceInformation().getMapper())) {
			mmChannelUriId = getStoredEntity().getUriId();
			imageContent = getStoredEntity().getImageContent();
		}

		super.mergeEntity();
		// Re-apply the values from Media Manager after merging.
		if (isUpdate && getSourceInformation() != null && JDOMXmlTvMapper.class.getCanonicalName().equals(getSourceInformation().getMapper())) {
			getEntity().setUriId(mmChannelUriId);
			getEntity().setImageContent(imageContent);
		}
		getEntity().setEnabled(enabled);
	}

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(Channel entity) {
		return entity.getImageContent();
	}

	@Override
	protected void setImageContentList(Channel entity,
									   List<ImageContent> imageContentList) {
		entity.setImageContent(imageContentList);
	}

	@Override
	@JsonIgnore
	protected boolean getCompareSubcontent() {
		return true;
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return Arrays.asList(WorkflowHelper.ENCODE_PROFILE_RELATIONSHIP_NAME);
	}

	protected void addRelationshipsList(String relName, Class rel, List<String> relList) {
		super.addRelationshipsList(
				Channel.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_URI_ID,
				relList
		);
	}
}
