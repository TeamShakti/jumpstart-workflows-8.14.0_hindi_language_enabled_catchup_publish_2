package com.irdeto.jumpstart.domain.reference;

import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder({
		"channelId",
		"date",
		"channel",
		"scheduleSlots"
})
public class ReferenceChannelDay extends ReferenceDocument {
	@JsonIgnore
	@Override
	public String getType() {
		return "channelDay";
	}

	@JsonProperty("channelId")
	private String channelId;

	@JsonProperty("date")
	private DateTime date;

	@JsonProperty("channel")
	private ReferenceChannel channel;

	@JsonProperty("scheduleSlots")
	private List<ReferenceScheduleSlot> scheduleSlotList;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@JsonProperty("date")
	public DateTime getDate() {
		return date;
	}

	@JsonProperty("date")
	public void setDate(DateTime date) {
		this.date = date;
	}

	@JsonProperty("channel")
	public ReferenceChannel getChannel() {
		return channel;
	}

	@JsonProperty("channel")
	public void setChannel(ReferenceChannel channel) {
		this.channel = channel;
	}

	@JsonProperty("scheduleSlots")
	public List<ReferenceScheduleSlot> getScheduleSlotList() {
		return scheduleSlotList;
	}

	@JsonProperty("scheduleSlots")
	public void setScheduleSlotList(List<ReferenceScheduleSlot> scheduleSlotList) {
		this.scheduleSlotList = scheduleSlotList;
	}
}
