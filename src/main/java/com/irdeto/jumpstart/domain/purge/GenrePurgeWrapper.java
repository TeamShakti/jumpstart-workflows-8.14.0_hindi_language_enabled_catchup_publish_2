package com.irdeto.jumpstart.domain.purge;

import java.util.ArrayList;
import java.util.List;

import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class GenrePurgeWrapper extends AbstractPurgeWrapper<Genre> {
	@Override
	public Class<Genre> getEntityClass() {
		return Genre.class;
	}

	@Override
	public List<Relationship<? extends BaseEntity>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(new Relationship<Genre>(WorkflowHelper.PARENT_RELATIONSHIP_NAME, Genre.class));
		return relationshipList;
	}
}
