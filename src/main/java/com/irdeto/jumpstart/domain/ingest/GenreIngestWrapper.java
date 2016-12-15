package com.irdeto.jumpstart.domain.ingest;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.TvodCollection;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class GenreIngestWrapper extends AbstractEntityIngestWrapper<Genre> {
	public void addParentGenreUriIdList(List<String> parentGenreUriIdList) {
		addRelationshipsList(
				WorkflowHelper.PARENT_RELATIONSHIP_NAME,
				Genre.class,
				parentGenreUriIdList
		);
	}

	public void addProgramUriIdList(List<String> programUriIdList) {
		addRelationshipsList(
				WorkflowHelper.PROGRAM_RELATIONSHIP_NAME,
				Program.class,
				programUriIdList
		);
	}

	public void addSeriesUriIdList(List<String> seriesUriIdList) {
		addRelationshipsList(
				WorkflowHelper.SERIES_RELATIONSHIP_NAME,
				Series.class,
				seriesUriIdList
		);
	}

	public void addBrandUriIdList(List<String> brandUriIdList) {
		addRelationshipsList(
				WorkflowHelper.BRAND_RELATIONSHIP_NAME,
				Brand.class,
				brandUriIdList
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

	public void addScheduleSlotUriIdList(List<String> scheduleSlotUriIdList) {
		addRelationshipsList(
				WorkflowHelper.SCHEDULE_SLOT_RELATIONSHIP_NAME,
				ScheduleSlot.class,
				scheduleSlotUriIdList
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
		return "Genre ingest wrapper for id: " + getEntity().getId() + " URI ID:" + getUriId();
	}


	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(Genre entity) {
		return null;
	}


	@Override
	@JsonIgnore
	protected void setImageContentList(
			Genre entity,
			List<ImageContent> imageContentList
	) {

	}

	@Override
	protected boolean alwaysMetadataQA() {
		return false;
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return Arrays.asList(WorkflowHelper.PARENT_RELATIONSHIP_NAME);
	}

	protected void addRelationshipsList(String relName, Class rel, List<String> relList) {
		super.addRelationshipsList(
				Genre.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_URI_ID,
				relList
		);
	}
}
