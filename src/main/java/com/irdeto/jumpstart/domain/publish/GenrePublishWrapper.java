package com.irdeto.jumpstart.domain.publish;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class GenrePublishWrapper extends AbstractPublishWrapper<Genre> implements EntityWithParentGenrePublishWrapper {
	public GenrePublishWrapper() {
		super();
	}
	
	public GenrePublishWrapper(Genre approvedEntity) {
		super();
		setApprovedEntity(approvedEntity);
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(new Relationship<Genre>(WorkflowHelper.PARENT_RELATIONSHIP_NAME, Genre.class));
		return relationshipList;
	}

	@Override
	@JsonIgnore
	public List<String> getDestinationProcessIdList() {
		List<String> destinationProcessIdList = new ArrayList<String>();
		destinationProcessIdList.add(JumpstartPropertyKey.PROCESS_ID_ACTIVEMQ_PUBLISH);
		return destinationProcessIdList;
	}
	
	@Override
	@JsonIgnore
	public boolean isValidTermMap(TermMap termMap) {
		return true;
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getTermRelationshipList() {
		return new ArrayList<>();
	}

	@Override
	@JsonIgnore
	public Class<Genre> getEntityClass() {
		return Genre.class;
	}

	@Override
	@JsonIgnore
	public boolean isTermsRequired() {
		return false;
	}

	@Override
	@JsonIgnore
	public List<Base> getPrerequisites() {
		return new ArrayList<>();
	}
	
	@JsonIgnore
	public Genre getParentGenre() {
		return getEntityFromRelationshipMap(Genre.class, WorkflowHelper.PARENT_RELATIONSHIP_NAME);
	}
}
