package com.irdeto.jumpstart.workflow;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.TvodCollection;
import com.irdeto.jumpstart.domain.qa.OfferMetadataQA;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.relationship.Relationship;


public class OfferEternalWorkflowDecorator extends AbstractEternalWorkflowDecorator<Offer> {

	@Override
	@JsonIgnore
	public QADecorator getContentQADecorator() {
		return null;
	}

	@Override
	@JsonIgnore
	public QADecorator getMetadataQADecorator() {
		return new OfferMetadataQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(new Relationship<SubscriptionPackage>(WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME, SubscriptionPackage.class));
		relationshipList.add(new Relationship<TvodCollection>(WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME, TvodCollection.class));
		relationshipList.add(new Relationship<Event>(WorkflowHelper.EVENT_RELATIONSHIP_NAME, Event.class));
		relationshipList.add(new Relationship<Brand>(WorkflowHelper.BRAND_RELATIONSHIP_NAME, Brand.class));
		relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class));
		relationshipList.add(new Relationship<Program>(WorkflowHelper.PROGRAM_RELATIONSHIP_NAME, Program.class));
		return relationshipList;
	}

	@Override
	@JsonIgnore
	public Class<Offer> getEntityClass() {
		return Offer.class;
	}
}
