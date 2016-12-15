package com.irdeto.jumpstart.domain.ingest;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.EncodeProfile;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.TvodCollection;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class EventIngestWrapper extends EntityIngestWrapperWithGenreAndRating<Event> {
	public void addOfferUriIdList(List<String> offerUriIdList) {
		addRelationshipsList(
				WorkflowHelper.OFFER_RELATIONSHIP_NAME,
				Offer.class,
				offerUriIdList
		);
	}

	public void addProgramUriIdList(List<String> programUriIdList) {
		addRelationshipsList(
				WorkflowHelper.PROGRAM_RELATIONSHIP_NAME,
				Program.class,
				programUriIdList
		);
	}

	public void addScheduleSlotIdList(List<String> scheduleSlotIdList) {
		addRelationshipsList(
				WorkflowHelper.SCHEDULE_SLOT_RELATIONSHIP_NAME,
				ScheduleSlot.class,
				scheduleSlotIdList
		);
	}

	public void addEncodeProfileNameList(List<String> encodeProfileNameList) {
		super.addRelationshipsList(
				Event.class,
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

	public void addTvodCollectionUriIdList(List<String> tvodCollectionUriIdList) {
		addRelationshipsList(
				WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME,
				TvodCollection.class,
				tvodCollectionUriIdList
		);
	}

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(Event entity) {
		return entity.getImageContent();
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(
			Event entity,
			List<ImageContent> imageContentList
	) {
		entity.setImageContent(imageContentList);
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return Arrays.asList(WorkflowHelper.OFFER_RELATIONSHIP_NAME,
				WorkflowHelper.GENRE_RELATIONSHIP_NAME,
				WorkflowHelper.RATING_RELATIONSHIP_NAME,
				WorkflowHelper.ENCODE_PROFILE_RELATIONSHIP_NAME,
				WorkflowHelper.PROGRAM_RELATIONSHIP_NAME,
				WorkflowHelper.SCHEDULE_SLOT_RELATIONSHIP_NAME);
	}

	protected void addRelationshipsList(String relName, Class rel, List<String> relList) {
		super.addRelationshipsList(
				Event.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_URI_ID,
				relList
		);
	}
}
