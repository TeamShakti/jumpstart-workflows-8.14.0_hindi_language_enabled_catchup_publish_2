package com.irdeto.jumpstart.workflow.publish.reference;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.ChannelDay;
import com.irdeto.jumpstart.domain.publish.EntityWithChannelListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithScheduleSlotListPublishWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceChannelDay;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;

public class ChannelDayReferenceDocumentMapper extends AbstractReferenceDocumentMapper<ChannelDay> {
	@JsonIgnore
	@Override
	public Class<ChannelDay> getEntityClass() {
		return ChannelDay.class;
	}

	@JsonIgnore
	@Override
	public ReferenceDocument getReferenceDocument() throws Exception {
		ChannelDay ChannelDay = getPublishWrapper().getApprovedEntity();

		ReferenceChannelDay referenceChannelDay = new ReferenceChannelDay();
		mapDocument(ChannelDay, referenceChannelDay);

		referenceChannelDay.setDate(ChannelDay.getDate());
		Channel channel = ((EntityWithChannelListPublishWrapper) getPublishWrapper()).getChannel();
		referenceChannelDay.setChannelId(channel != null ? channel.getChannelId() : null);

		referenceChannelDay.setScheduleSlotList(
				ScheduleSlotReferenceDocumentMapper.mapScheduleSlotList(
						(EntityWithScheduleSlotListPublishWrapper) getPublishWrapper()
				)
		);
		referenceChannelDay.setChannel(
				ChannelReferenceDocumentMapper.mapChannel(
						(EntityWithChannelListPublishWrapper) getPublishWrapper()
				)
		);

		return referenceChannelDay;
	}
}
