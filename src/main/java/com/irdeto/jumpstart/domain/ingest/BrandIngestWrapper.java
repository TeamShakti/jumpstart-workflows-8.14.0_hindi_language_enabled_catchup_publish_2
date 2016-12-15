package com.irdeto.jumpstart.domain.ingest;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.TvodCollection;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class BrandIngestWrapper extends EntityIngestWrapperWithGenreAndRating<Brand> {
	public void addSeriesUriIdList(List<String> seriesUriIdList) {
		addRelationshipsList(
				WorkflowHelper.SERIES_RELATIONSHIP_NAME,
				Series.class,
				seriesUriIdList
		);
	}

	public void addOfferUriIdList(List<String> offerUriIdList) {
		addRelationshipsList(
				WorkflowHelper.OFFER_RELATIONSHIP_NAME,
				Offer.class,
				offerUriIdList
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

	@JsonIgnore
	@Override
	protected List<ImageContent> getImageContentList(Brand entity) {
		return entity.getImageContent();
	}

	@Override
	protected void setImageContentList(Brand entity,
									   List<ImageContent> imageContentList) {
		entity.setImageContent(imageContentList);
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return Arrays.asList(WorkflowHelper.OFFER_RELATIONSHIP_NAME,
				WorkflowHelper.GENRE_RELATIONSHIP_NAME,
				WorkflowHelper.RATING_RELATIONSHIP_NAME
		);
	}

	protected void addRelationshipsList(String relName, Class rel, List<String> relList) {
		super.addRelationshipsList(
				Brand.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_URI_ID,
				relList
		);
	}
}
