package com.irdeto.jumpstart.domain.publish;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.Term;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class SubscriptionPackagePublishWrapper extends AbstractPublishWrapper<SubscriptionPackage> implements EntityWithGenreListPublishWrapper {
	public SubscriptionPackagePublishWrapper() {
		super();
	}
	
	public SubscriptionPackagePublishWrapper(SubscriptionPackage approvedEntity) {
		super();
		setApprovedEntity(approvedEntity);
	}

	@Override
	public List<Relationship<?>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(new Relationship<Genre>(WorkflowHelper.GENRE_RELATIONSHIP_NAME, Genre.class));
		return relationshipList;
	}
	
	@Override
	@JsonIgnore
	public boolean isValidTermMap(TermMap termMap) {
		return true;
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getTermRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(
				new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
					new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class)));
		return relationshipList;
	}

	@Override
	@JsonIgnore
	public List<String> getDestinationProcessIdList() {
		List<String> destinationProcessIdList = new ArrayList<String>();
		destinationProcessIdList.add(JumpstartPropertyKey.PROCESS_ID_CDN_PUBLISH);
		destinationProcessIdList.add(JumpstartPropertyKey.PROCESS_ID_CONTROL_PUBLISH);
		destinationProcessIdList.add(JumpstartPropertyKey.PROCESS_ID_ELASTICSEARCH_PUBLISH);
		destinationProcessIdList.add(JumpstartPropertyKey.PROCESS_ID_ACTIVEMQ_PUBLISH);
		return destinationProcessIdList;
	}
	
	@Override
	@JsonIgnore
	public Class<SubscriptionPackage> getEntityClass() {
		return SubscriptionPackage.class;
	}
	
	@Override
	@JsonIgnore
	public List<Base> getPrerequisites() {
		List<Base> prerequisites = new ArrayList<>();
		prerequisites.addAll(getEntityListFromRelationshipMap(Genre.class, WorkflowHelper.GENRE_RELATIONSHIP_NAME));
		return prerequisites;
	}

	@Override
	@JsonIgnore
	public List<Genre> getGenreList() {
		return getEntityListFromRelationshipMap(Genre.class, WorkflowHelper.GENRE_RELATIONSHIP_NAME);
	}
}
