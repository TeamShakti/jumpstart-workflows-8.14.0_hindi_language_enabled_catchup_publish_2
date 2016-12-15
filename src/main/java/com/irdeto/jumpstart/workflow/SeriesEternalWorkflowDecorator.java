package com.irdeto.jumpstart.workflow;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.qa.SeriesContentQA;
import com.irdeto.jumpstart.domain.qa.SeriesMetadataQA;
import com.irdeto.jumpstart.domain.relationship.Relationship;

public class SeriesEternalWorkflowDecorator extends AbstractEternalWorkflowDecorator<Series> {

	@Override
	@JsonIgnore
	public QADecorator getContentQADecorator() {
		return new SeriesContentQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public QADecorator getMetadataQADecorator() {
		return new SeriesMetadataQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(new Relationship<Program>(WorkflowHelper.PROGRAM_RELATIONSHIP_NAME, Program.class));
		return relationshipList;
	}

	@Override
	@JsonIgnore
	public Class<Series> getEntityClass() {
		return Series.class;
	}

}
