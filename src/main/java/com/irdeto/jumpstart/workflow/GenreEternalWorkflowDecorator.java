package com.irdeto.jumpstart.workflow;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.domain.qa.GenreMetadataQA;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.relationship.Relationship;


public class GenreEternalWorkflowDecorator extends AbstractEternalWorkflowDecorator<Genre> {

	@Override
	@JsonIgnore
	public QADecorator getContentQADecorator() {
		return null;
	}

	@Override
	@JsonIgnore
	public QADecorator getMetadataQADecorator() {
		return new GenreMetadataQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getRelationshipList() {
		return new ArrayList<>();
	}

	@Override
	@JsonIgnore
	public Class<Genre> getEntityClass() {
		return Genre.class;
	}
}
