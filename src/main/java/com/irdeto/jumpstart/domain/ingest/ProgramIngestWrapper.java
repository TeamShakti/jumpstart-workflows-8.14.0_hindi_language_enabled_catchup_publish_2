package com.irdeto.jumpstart.domain.ingest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.BaseMetadata.DataMaster;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Person;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.TvodCollection;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.ingest.DataIngestHelper;

public class ProgramIngestWrapper extends EntityIngestWrapperWithGenreAndRating<Program> {

	public void addEventUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.EVENT_RELATIONSHIP_NAME,
				Event.class,
				uriIdList
		);
	}

	public void addScheduleSlotUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.SCHEDULE_SLOT_RELATIONSHIP_NAME,
				ScheduleSlot.class,
				uriIdList
		);
	}

	public void addSubscriptionPackageUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME,
				SubscriptionPackage.class,
				uriIdList
		);
	}

	public void addTvodCollectionUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME,
				TvodCollection.class,
				uriIdList
		);
	}

	public void addSeriesUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.SERIES_RELATIONSHIP_NAME,
				Series.class,
				uriIdList
		);
	}

	public void addOfferUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.OFFER_RELATIONSHIP_NAME,
				Offer.class,
				uriIdList
		);
	}

	@Override
	@JsonIgnore
	public void mergeEntity() {
		if (getStoredEntity() != null) { // update entity
			if (DataMaster.MEDIA_MANAGER.equals(getStoredEntity().getDataMaster())) {
				setEntity(getStoredEntity());
				return;
			}
		}
		Program xmlProgram = getEntity();
		super.mergeEntity();
		if (getStoredEntity() != null) { // update entity
			getEntity().setCountryOfOrigin(xmlProgram.getCountryOfOrigin());
			List<VideoContent> videoContentList = DataIngestHelper
					.mergeVideoContentList(xmlProgram.getVideoContent(),
							getEntity().getVideoContent());
			getEntity().setVideoContent(videoContentList);
			if (xmlProgram.getContributors() != null) {
				getEntity().setContributors(xmlProgram.getContributors());
			} else {
				getEntity().setContributors(new ArrayList<Person>());
			}
		}

	}

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(Program entity) {
		return entity.getImageContent();
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(Program entity,
									   List<ImageContent> imageContentList) {
		entity.setImageContent(imageContentList);
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return Arrays.asList(WorkflowHelper.OFFER_RELATIONSHIP_NAME,
				//WorkflowHelper.GENRE_RELATIONSHIP_NAME,
				//WorkflowHelper.RATING_RELATIONSHIP_NAME,
				WorkflowHelper.EVENT_RELATIONSHIP_NAME,
				WorkflowHelper.SERIES_RELATIONSHIP_NAME,
				WorkflowHelper.SCHEDULE_SLOT_RELATIONSHIP_NAME);
	}

	protected void addRelationshipsList(String relName, Class rel, List<String> relList) {
		super.addRelationshipsList(
				Program.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_URI_ID,
				relList
		);
	}

}
