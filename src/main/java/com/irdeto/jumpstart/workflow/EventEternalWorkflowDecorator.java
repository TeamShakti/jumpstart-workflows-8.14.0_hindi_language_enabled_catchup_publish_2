package com.irdeto.jumpstart.workflow;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.qa.EventContentQA;
import com.irdeto.jumpstart.domain.qa.EventMetadataQA;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.relationship.Relationship;

public class EventEternalWorkflowDecorator extends AbstractEternalWorkflowDecorator<Event> {
	@Override
	@JsonIgnore
	public List<Relationship<?>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(new Relationship<Program>(WorkflowHelper.PROGRAM_RELATIONSHIP_NAME, Program.class));
		return relationshipList;
	}
	
	@Override
	@JsonIgnore
	public QADecorator getContentQADecorator() {
		return new EventContentQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public QADecorator getMetadataQADecorator() {
		return new EventMetadataQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public Class<Event> getEntityClass() {
		return Event.class;
	}
}
