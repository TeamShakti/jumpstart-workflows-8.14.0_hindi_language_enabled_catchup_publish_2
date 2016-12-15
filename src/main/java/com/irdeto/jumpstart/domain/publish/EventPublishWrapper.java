package com.irdeto.jumpstart.domain.publish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.ContentType;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.Rating;
import com.irdeto.jumpstart.domain.RatingScheme;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.Term;
import com.irdeto.jumpstart.domain.TvodCollection;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class EventPublishWrapper extends AbstractPublishWrapper<Event> implements EntityWithGenreListPublishWrapper, EntityWithProgramListPublishWrapper, EntityWithRatingsPublishWrapper, EntityWithScheduleSlotListPublishWrapper, EntityWithSubscriptionPackageListPublishWrapper, EntityWithTvodCollectionListPublishWrapper {
	public EventPublishWrapper() {
		super();
	}

	public EventPublishWrapper(Event approvedEntity) {
		super();
		setApprovedEntity(approvedEntity);
	}

	@Override
	public List<Relationship<?>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(new Relationship<TvodCollection>(WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME, TvodCollection.class));
		relationshipList.add(new Relationship<SubscriptionPackage>(WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME, SubscriptionPackage.class));
		relationshipList.add(new Relationship<Genre>(WorkflowHelper.GENRE_RELATIONSHIP_NAME, Genre.class));
		relationshipList.add(new Relationship<Program>(WorkflowHelper.PROGRAM_RELATIONSHIP_NAME, Program.class));
		relationshipList.add(new Relationship<ScheduleSlot>(WorkflowHelper.SCHEDULE_SLOT_RELATIONSHIP_NAME, ScheduleSlot.class));
		relationshipList.add(new Relationship<Rating>(WorkflowHelper.RATING_RELATIONSHIP_NAME, Rating.class, new Relationship<RatingScheme>(WorkflowHelper.RATING_SCHEME_RELATIONSHIP_NAME, RatingScheme.class)));
		return relationshipList;
	}

	@Override
	@JsonIgnore
	public boolean isValidTermMap(TermMap termMap) {
		return termMap.getContentTypeList().contains(ContentType.EVENT.toString());
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getTermRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(
				new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
					new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class)));
		relationshipList.add(
				new Relationship<SubscriptionPackage>(WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME, SubscriptionPackage.class,
					new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
						new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class))));
		relationshipList.add(
				new Relationship<TvodCollection>(WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME, TvodCollection.class,
					new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
						new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class))));
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
	public Class<Event> getEntityClass() {
		return Event.class;
	}

	@Override
	@JsonIgnore
	public List<Base> getPrerequisites() {
		List<Base> prerequisites = new ArrayList<>();
		prerequisites.addAll(getEntityListFromRelationshipMap(TvodCollection.class, WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME));
		prerequisites.addAll(getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME));
		prerequisites.addAll(getEntityListFromRelationshipMap(Genre.class, WorkflowHelper.GENRE_RELATIONSHIP_NAME));
		return prerequisites;
	}

	@Override
	@JsonIgnore
	public List<Genre> getGenreList() {
		return getEntityListFromRelationshipMap(Genre.class, WorkflowHelper.GENRE_RELATIONSHIP_NAME);
	}

	@JsonIgnore
	public List<SubscriptionPackage> getSubscriptionPackageList() {
		return getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME);
	}

	@JsonIgnore
	public List<TvodCollection> getTvodCollectionList() {
		return getEntityListFromRelationshipMap(TvodCollection.class, WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME);
	}

	@JsonIgnore
	public List<Program> getProgramList() {
		return getEntityListFromRelationshipMap(Program.class, WorkflowHelper.PROGRAM_RELATIONSHIP_NAME);
	}

	@JsonIgnore
	public List<ScheduleSlot> getScheduleSlotList() {
		return getEntityListFromRelationshipMap(ScheduleSlot.class, WorkflowHelper.SCHEDULE_SLOT_RELATIONSHIP_NAME);
	}

	@JsonIgnore
	public Map<Rating, List<RatingScheme>> getRatingRatingSchemeListMap() {
		return getRatingRatingSchemeListMap(WorkflowHelper.RATING_RELATIONSHIP_NAME, WorkflowHelper.RATING_SCHEME_RELATIONSHIP_NAME);
	}

	@Override
	@JsonIgnore
	public Map<RatingScheme, Rating> getRatings() {
		Map<RatingScheme, Rating> ratingsMap = new HashMap<>();
		for(Map.Entry<Rating, List<RatingScheme>> entry: getRatingRatingSchemeListMap().entrySet()) {
			ratingsMap.put(entry.getValue().get(0), entry.getKey());
		}
		return ratingsMap;
	}
}
