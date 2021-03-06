package com.irdeto.jumpstart.domain.ingest;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.Term;
import com.irdeto.jumpstart.domain.TvodCollection;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class OfferIngestWrapper extends AbstractEntityIngestWrapper<Offer> {
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

	public void addTermUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.TERM_RELATIONSHIP_NAME,
				Term.class,
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

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(Offer entity) {
		return null;
	}

	@Override
	protected void setImageContentList(
			Offer entity,
			List<ImageContent> imageContentList
	) {

	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return Arrays.asList(WorkflowHelper.TERM_RELATIONSHIP_NAME);
	}

	protected void addRelationshipsList(String relName, Class rel, List<String> relList) {
		super.addRelationshipsList(
				Offer.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_URI_ID,
				relList
		);
	}
}
