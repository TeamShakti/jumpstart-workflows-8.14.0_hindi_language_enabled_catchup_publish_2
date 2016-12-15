package com.irdeto.jumpstart.workflow;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.qa.SubscriptionPackageContentQA;
import com.irdeto.jumpstart.domain.qa.SubscriptionPackageMetadataQA;
import com.irdeto.jumpstart.domain.relationship.Relationship;


public class SubscriptionPackageEternalWorkflowDecorator extends AbstractEternalWorkflowDecorator<SubscriptionPackage> {

	@Override
	@JsonIgnore
	public QADecorator getContentQADecorator() {
		return new SubscriptionPackageContentQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public QADecorator getMetadataQADecorator() {
		return new SubscriptionPackageMetadataQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(new Relationship<Event>(WorkflowHelper.EVENT_RELATIONSHIP_NAME, Event.class));
		relationshipList.add(new Relationship<Brand>(WorkflowHelper.BRAND_RELATIONSHIP_NAME, Brand.class));
		relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class));
		relationshipList.add(new Relationship<Program>(WorkflowHelper.PROGRAM_RELATIONSHIP_NAME, Program.class));
		relationshipList.add(new Relationship<Channel>(WorkflowHelper.CHANNEL_RELATIONSHIP_NAME, Channel.class));
		return relationshipList;
	}

	@Override
	@JsonIgnore
	public Class<SubscriptionPackage> getEntityClass() {
		return SubscriptionPackage.class;
	}
}
