package com.irdeto.jumpstart.domain.ingest;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class SubscriptionPackageIngestWrapper extends EntityIngestWrapperWithGenre<SubscriptionPackage> {
	public void addProgramUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.PROGRAM_RELATIONSHIP_NAME,
				Program.class,
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

	public void addBrandUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.BRAND_RELATIONSHIP_NAME,
				Brand.class,
				uriIdList
		);
	}

	public void addChannelUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.CHANNEL_RELATIONSHIP_NAME,
				Channel.class,
				uriIdList
		);
	}

	public void addEventUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.EVENT_RELATIONSHIP_NAME,
				Event.class,
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
	protected List<ImageContent> getImageContentList(SubscriptionPackage entity) {
		return getEntity().getImageContent();
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(
			SubscriptionPackage entity,
			List<ImageContent> imageContentList
	) {
		entity.setImageContent(imageContentList);
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return Arrays.asList(WorkflowHelper.OFFER_RELATIONSHIP_NAME,
				WorkflowHelper.GENRE_RELATIONSHIP_NAME,
				WorkflowHelper.BRAND_RELATIONSHIP_NAME,
				WorkflowHelper.CHANNEL_RELATIONSHIP_NAME,
				WorkflowHelper.EVENT_RELATIONSHIP_NAME,
				WorkflowHelper.PROGRAM_RELATIONSHIP_NAME,
				WorkflowHelper.SERIES_RELATIONSHIP_NAME);
	}

	protected void addRelationshipsList(String relName, Class rel, List<String> relList) {
		super.addRelationshipsList(
				SubscriptionPackage.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_URI_ID,
				relList
		);
	}
}
