package com.irdeto.jumpstart.workflow;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.TvodCollection;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.qa.TvodCollectionContentQA;
import com.irdeto.jumpstart.domain.qa.TvodCollectionMetadataQA;
import com.irdeto.jumpstart.domain.relationship.Relationship;


public class TvodCollectionEternalWorkflowDecorator extends AbstractEternalWorkflowDecorator<TvodCollection> {

	@Override
	@JsonIgnore
	public Class<TvodCollection> getEntityClass() {
		return TvodCollection.class;
	}

	@Override
	@JsonIgnore
	public QADecorator getContentQADecorator() {
		return new TvodCollectionContentQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public QADecorator getMetadataQADecorator() {
		return new TvodCollectionMetadataQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(new Relationship<Event>(WorkflowHelper.EVENT_RELATIONSHIP_NAME, Event.class));
		relationshipList.add(new Relationship<Brand>(WorkflowHelper.BRAND_RELATIONSHIP_NAME, Brand.class));
		relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class));
		relationshipList.add(new Relationship<Program>(WorkflowHelper.PROGRAM_RELATIONSHIP_NAME, Program.class));
		return relationshipList;
	}
}
