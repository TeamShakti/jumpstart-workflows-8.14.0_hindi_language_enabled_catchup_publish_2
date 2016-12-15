package com.irdeto.jumpstart.workflow;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.relationship.Relationship;


public class ScheduleSlotEternalWorkflowDecorator extends AbstractEternalWorkflowDecorator<ScheduleSlot> {

	@Override
	@JsonIgnore
	public QADecorator getContentQADecorator() {
		return null;
	}

	@Override
	@JsonIgnore
	public QADecorator getMetadataQADecorator() {
		return null;
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getRelationshipList() {
		return new ArrayList<>();
	}

	@Override
	@JsonIgnore
	public Class<ScheduleSlot> getEntityClass() {
		return ScheduleSlot.class;
	}
}
