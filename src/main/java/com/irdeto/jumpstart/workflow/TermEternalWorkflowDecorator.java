package com.irdeto.jumpstart.workflow;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Term;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.qa.TermMetadataQA;
import com.irdeto.jumpstart.domain.relationship.Relationship;


public class TermEternalWorkflowDecorator extends AbstractEternalWorkflowDecorator<Term> {

	@Override
	@JsonIgnore
	public Class<Term> getEntityClass() {
		return Term.class;
	}

	@Override
	@JsonIgnore
	public QADecorator getContentQADecorator() {
		return null;
	}

	@Override
	@JsonIgnore
	public QADecorator getMetadataQADecorator() {
		return new TermMetadataQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class));
		return relationshipList;
	}
}
