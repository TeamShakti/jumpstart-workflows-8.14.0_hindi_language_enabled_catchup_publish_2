package com.irdeto.jumpstart.domain.publish;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

import com.google.common.collect.ImmutableList;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.ChannelDay;
import com.irdeto.jumpstart.domain.ContentType;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class ChannelDayPublishWrapper extends AbstractPublishWrapper<ChannelDay>
		implements EntityWithScheduleSlotListPublishWrapper, EntityWithChannelListPublishWrapper {
	public ChannelDayPublishWrapper() {
		super();
	}

	public ChannelDayPublishWrapper(ChannelDay approvedEntity) {
		super();
		setApprovedEntity(approvedEntity);
	}

	@Override
	@JsonIgnore
	public List<ScheduleSlot> getScheduleSlotList() {
		return this.getApprovedEntity().getScheduleSlots();
	}

	@Override
	@JsonIgnore
	public boolean publishRequired() {
		return !this.getApprovedEntity().getDate()
				.isBefore(DateTime.now().withTimeAtStartOfDay());
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(
				new Relationship<Channel>(WorkflowHelper.CHANNEL_RELATIONSHIP_NAME, Channel.class));
		relationshipList.add(
				new Relationship<Channel>(
						WorkflowHelper.CHANNEL_RELATIONSHIP_NAME, Channel.class,
						new Relationship<SubscriptionPackage>(WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME, SubscriptionPackage.class)
				)
		);
		return relationshipList;
	}

	@Override
	@JsonIgnore
	public List<String> getDestinationProcessIdList() {
		List<String> destinationProcessIdList = new ArrayList<String>();
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
		return new ArrayList<>();
	}

	@Override
	@JsonIgnore
	public List<Base> getPrerequisites() {
		List<Base> prerequisites = new ArrayList<>();
		prerequisites.addAll(getEntityListFromRelationshipMap(Channel.class, WorkflowHelper.CHANNEL_RELATIONSHIP_NAME));
		prerequisites.addAll(getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.CHANNEL_RELATIONSHIP_NAME, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME));
		return prerequisites;
	}

	@Override
	@JsonIgnore
	public Class<ChannelDay> getEntityClass() {
		return ChannelDay.class;
	}

	@JsonIgnore
	public List<SubscriptionPackage> getSubscriptionPackageList() {
		return getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.CHANNEL_RELATIONSHIP_NAME, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME);
	}

	@Override
	@JsonIgnore
	public List<Channel> getChannelList() {
		return (this.getChannel() != null) ? ImmutableList.of(this.getChannel()) : new ArrayList<Channel>();
	}

	@Override
	@JsonIgnore
	public Channel getChannel() {
		return getEntityFromRelationshipMap(Channel.class, WorkflowHelper.CHANNEL_RELATIONSHIP_NAME);
	}

	@Override
	@JsonIgnore
	public boolean isTermsRequired() {
		return false;
	}
}
