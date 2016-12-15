package com.irdeto.jumpstart.workflow;

import java.util.ArrayList;
import java.util.List;

import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.domain.Rating;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.relationship.Relationship;

public class RatingEternalWorkflowDecorator extends AbstractEternalWorkflowDecorator<Rating> {

	@Override
	public Class<Rating> getEntityClass() {
		// TODO Auto-generated method stub
		return Rating.class;
	}

	@Override
	public QADecorator getContentQADecorator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QADecorator getMetadataQADecorator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Relationship<?>> getRelationshipList() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

}