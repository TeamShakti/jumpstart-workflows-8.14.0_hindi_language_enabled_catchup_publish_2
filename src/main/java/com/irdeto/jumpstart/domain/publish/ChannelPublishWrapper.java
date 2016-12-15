package com.irdeto.jumpstart.domain.publish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.ContentType;
import com.irdeto.jumpstart.domain.DeviceProfile;
import com.irdeto.jumpstart.domain.EncodeProfile;
import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.Term;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class ChannelPublishWrapper extends AbstractPublishWrapper<Channel> implements EntityWithGenreListPublishWrapper, EntityWithSubscriptionPackageListPublishWrapper, EntityWithEncodeProfilesPublishWrapper {
	public ChannelPublishWrapper() {
		super();
	}

	public ChannelPublishWrapper(Channel approvedEntity) {
		super();
		setApprovedEntity(approvedEntity);
	}

	@Override
	public boolean publishRequired() {
		return true;
	}

	@Override
	public List<Relationship<?>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(new Relationship<SubscriptionPackage>(WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME, SubscriptionPackage.class));
		relationshipList.add(new Relationship<EncodeProfile>(WorkflowHelper.ENCODE_PROFILE_RELATIONSHIP_NAME, EncodeProfile.class, new Relationship<DeviceProfile>(WorkflowHelper.DEVICE_PROFILE_RELATIONSHIP_NAME, DeviceProfile.class)));
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
	public boolean isValidTermMap(TermMap termMap) {
		return termMap.getContentTypeList().contains(ContentType.CHANNEL.toString());
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getTermRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(
				new Relationship<SubscriptionPackage>(WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME, SubscriptionPackage.class,
					new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
						new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class))));
		return relationshipList;
	}

	@Override
	@JsonIgnore
	public Class<Channel> getEntityClass() {
		return Channel.class;
	}

	@Override
	@JsonIgnore
	public List<Base> getPrerequisites() {
		List<Base> prerequisites = new ArrayList<>();
		prerequisites.addAll(getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME));
		return prerequisites;
	}

	@Override
	@JsonIgnore
	public List<Genre> getGenreList() {
		return getEntityListFromRelationshipMap(Genre.class, WorkflowHelper.GENRE_RELATIONSHIP_NAME);
	}

	@Override
	@JsonIgnore
	public List<SubscriptionPackage> getSubscriptionPackageList() {
		return getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME);
	}

	@Override
	@JsonIgnore
	public Map<DeviceProfile, EncodeProfile> getEncodeProfiles() {
		Map<DeviceProfile, EncodeProfile> encodeProfileMap = new HashMap<>();
		for(Map.Entry<EncodeProfile, List<DeviceProfile>> entry: getEncodeProfileDeviceProfileListMap().entrySet()) {
			encodeProfileMap.put(entry.getValue().get(0), entry.getKey());
		}
		return encodeProfileMap;
	}
}
