package com.irdeto.jumpstart.workflow;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.qa.BrandContentQA;
import com.irdeto.jumpstart.domain.qa.BrandMetadataQA;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.relationship.Relationship;

public class BrandEternalWorkflowDecorator extends AbstractEternalWorkflowDecorator<Brand> {
	@Override
	@JsonIgnore
	public List<Relationship<?>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class));
		return relationshipList;
	}
	
	@Override
	@JsonIgnore
	public QADecorator getContentQADecorator() {
		return new BrandContentQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public QADecorator getMetadataQADecorator() {
		return new BrandMetadataQA(getEntityId(), getEntityType());
	}
	
	@Override
	@JsonIgnore
	public Class<Brand> getEntityClass() {
		return Brand.class;
	}
}
