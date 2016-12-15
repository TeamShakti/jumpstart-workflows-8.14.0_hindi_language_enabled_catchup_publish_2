package com.irdeto.jumpstart.domain.publish;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.collect.ImmutableList;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.ChannelDay;
import com.irdeto.jumpstart.domain.ContentType;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class ScheduleSlotPublishWrapper extends AbstractPublishWrapper<ScheduleSlot>
		implements EntityWithChannelListPublishWrapper, EntityWithEventListPublishWrapper, EntityWithProgramListPublishWrapper {
	public ScheduleSlotPublishWrapper() {
		super();
	}

	public ScheduleSlotPublishWrapper(ScheduleSlot approvedEntity) {
		super();
		setApprovedEntity(approvedEntity);
	}

	@Override
	public boolean publishRequired() {
		return this.getApprovedEntity().getLinearBroadcastDate().isAfterNow();
	}

	@Override
	public List<Relationship<?>> getRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(
				new Relationship<Program>(WorkflowHelper.PROGRAM_RELATIONSHIP_NAME, Program.class)
		);
		relationshipList.add(
				new Relationship<Event>(WorkflowHelper.EVENT_RELATIONSHIP_NAME, Event.class)
		);
		relationshipList.add(
				new Relationship<ChannelDay>(
						WorkflowHelper.SCHEDULE_SLOT_RELATIONSHIP_NAME, ChannelDay.class,
						new Relationship<Channel>(WorkflowHelper.CHANNEL_RELATIONSHIP_NAME, Channel.class)
				)
		);
		return relationshipList;
	}

	@Override
	@JsonIgnore
	public List<String> getDestinationProcessIdList() {
		List<String> destinationProcessIdList = new ArrayList<String>();
		destinationProcessIdList.add(JumpstartPropertyKey.PROCESS_ID_ACTIVEMQ_CATCH_UP_PUBLISH);
		// code by nitin
		destinationProcessIdList.add(JumpstartPropertyKey.PROCESS_ID_ACTIVEMQ_CATCH_UP_PUBLISH_ELEMENTAL_DELTA);
		return destinationProcessIdList;
	}

	@Override
	@JsonIgnore
	public int getDurationSeconds() {
		if (getApprovedEntity().getLinearBroadcastDate() != null && getApprovedEntity().getLinearBroadcastEndDate() != null) {
			Long durationMillis = getApprovedEntity().getLinearBroadcastEndDate().getMillis() - getApprovedEntity().getLinearBroadcastDate().getMillis();
			Long durationSeconds = durationMillis/1000;
			return durationSeconds.intValue();
		} else {
			return 0;
		}
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
	public Class<ScheduleSlot> getEntityClass() {
		return ScheduleSlot.class;
	}

	@Override
	@JsonIgnore
	public List<Base> getPrerequisites() {
		List<Base> prerequisites = new ArrayList<>();
		prerequisites.addAll(getEntityListFromRelationshipMap(Channel.class, WorkflowHelper.CHANNEL_RELATIONSHIP_NAME));
		prerequisites.addAll(getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.CHANNEL_RELATIONSHIP_NAME, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME));
		return prerequisites;
	}

	@JsonIgnore
	public List<Event> getEventList() {
		return getEntityListFromRelationshipMap(Event.class, WorkflowHelper.EVENT_RELATIONSHIP_NAME);
	}

	@JsonIgnore
	public List<Program> getProgramList() {
		return getEntityListFromRelationshipMap(Program.class, WorkflowHelper.PROGRAM_RELATIONSHIP_NAME);
	}

	@Override
	@JsonIgnore
	public boolean isTermsRequired() {
		return false;
	}

	@Override
	@JsonIgnore
	public List<Channel> getChannelList() {
		return (this.getChannel() != null) ? ImmutableList.of(this.getChannel()) : new ArrayList<Channel>();
	}

	@Override
	@JsonIgnore
	public Channel getChannel() {
		return getEntityFromRelationshipMap(Channel.class, WorkflowHelper.SCHEDULE_SLOT_RELATIONSHIP_NAME, WorkflowHelper.CHANNEL_RELATIONSHIP_NAME);
	}
}
