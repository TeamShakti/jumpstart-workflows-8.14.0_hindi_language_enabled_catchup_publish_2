package com.irdeto.jumpstart.domain.ingest;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.TvodCollection;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class SeriesIngestWrapper extends EntityIngestWrapperWithGenreAndRating<Series> {
	public void addProgramUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.PROGRAM_RELATIONSHIP_NAME,
				Program.class,
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

	public void addOfferUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.OFFER_RELATIONSHIP_NAME,
				Series.class,
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

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(Series entity) {
		return getEntity().getImageContent();
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(Series entity,
									   List<ImageContent> imageContentList) {
		entity.setImageContent(imageContentList);
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return Arrays.asList(WorkflowHelper.OFFER_RELATIONSHIP_NAME,
				WorkflowHelper.GENRE_RELATIONSHIP_NAME,
				WorkflowHelper.RATING_RELATIONSHIP_NAME,
				WorkflowHelper.BRAND_RELATIONSHIP_NAME);
	}

	protected void addRelationshipsList(String relName, Class rel, List<String> relList) {
		super.addRelationshipsList(
				Series.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_URI_ID,
				relList
		);
	}
}
