package com.irdeto.jumpstart.domain.ingest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.ChannelDay;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class ChannelDayIngestWrapper extends AbstractEntityIngestWrapper<ChannelDay> {
	private String channelId;

	@JsonIgnore
	public String getChannelId() {
		return channelId;
	}

	@JsonIgnore
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@JsonIgnore
	@Override
	public void mergeEntity() {
		throw new NotImplementedException("Merge is currently done via EpgIngestHelper");
	}

	public void addChannelIdList(List<String> channelIdList) {
		super.addRelationshipsList(
				ChannelDay.class,
				WorkflowHelper.CHANNEL_RELATIONSHIP_NAME,
				Channel.class,
				WorkflowHelper.ATTRIBUTE_NAME_CHANNEL_ID,
				channelIdList
		);
	}

	public void setScheduleSlots(List<ScheduleSlot> scheduleSlots) {
		this.getEntity().setScheduleSlots(scheduleSlots);
	}

	public void addScheduleSlot(ScheduleSlot scheduleSlot) {
		this.getEntity().getScheduleSlots().add(scheduleSlot);
	}

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(ChannelDay entity) {
		return null;
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(ChannelDay entity, List<ImageContent> imageContentList) {
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return new ArrayList<>();
	}
}
