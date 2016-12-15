package com.irdeto.jumpstart.workflow.publish.reference;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.publish.EntityWithChannelListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithEventListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithProgramListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithScheduleSlotListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.PublishWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;
import com.irdeto.jumpstart.domain.reference.ReferenceScheduleSlot;
import com.irdeto.jumpstart.workflow.LocaleHelper;

public class ScheduleSlotReferenceDocumentMapper extends AbstractReferenceDocumentMapper<ScheduleSlot> {
	@JsonIgnore
	@Override
	public Class<ScheduleSlot> getEntityClass() {
		return ScheduleSlot.class;
	}

	@JsonIgnore
	@Override
	public ReferenceDocument getReferenceDocument() throws Exception {
		ScheduleSlot scheduleSlot = getPublishWrapper().getApprovedEntity();

		ReferenceScheduleSlot referenceScheduleSlot = mapScheduleSlot(scheduleSlot);

		mapDocument(scheduleSlot, referenceScheduleSlot);
		referenceScheduleSlot.getEvents().addAll(
				EventReferenceDocumentMapper.mapEventList(
						(EntityWithEventListPublishWrapper)getPublishWrapper()
				)
		);
		referenceScheduleSlot.getPrograms().addAll(
				ProgramReferenceDocumentMapper.mapProgramList(
						(EntityWithProgramListPublishWrapper)getPublishWrapper()
				)
		);

		return referenceScheduleSlot;
	}

	protected static List<ReferenceScheduleSlot> mapScheduleSlotList(EntityWithScheduleSlotListPublishWrapper publishWrapper) throws Exception {
		return mapScheduleSlotList(publishWrapper.getScheduleSlotList());
	}

	protected static List<ReferenceScheduleSlot> mapScheduleSlotList(List<ScheduleSlot> scheduleSlotList) throws Exception {
		List<ReferenceScheduleSlot> referenceScheduleSlotList = new ArrayList<>();
		if (scheduleSlotList != null) {
			for (ScheduleSlot scheduleSlot: scheduleSlotList) {
				referenceScheduleSlotList.add(mapScheduleSlot(scheduleSlot));
			}
		}
		return referenceScheduleSlotList;
	}
// change by nitin earlier this method was protected
	protected static ReferenceScheduleSlot mapScheduleSlot(ScheduleSlot scheduleSlot) throws Exception {
		ReferenceScheduleSlot referenceScheduleSlot = null;
		if (scheduleSlot != null) {
			referenceScheduleSlot = new ReferenceScheduleSlot();
			mapEntity(scheduleSlot, referenceScheduleSlot);
			referenceScheduleSlot.setLinearBroadcastDate(scheduleSlot.getLinearBroadcastDate());
			referenceScheduleSlot.setLinearBroadcastEndDate(scheduleSlot.getLinearBroadcastEndDate());
			referenceScheduleSlot.setStartDateTime(scheduleSlot.getStartDateTime());
			referenceScheduleSlot.setEndDateTime(scheduleSlot.getEndDateTime());
			referenceScheduleSlot.setTitleBrief(mapLocalized(scheduleSlot.getTitle(), 19));
			referenceScheduleSlot.setTitleMedium(mapLocalized(scheduleSlot.getTitle(), 35));
			referenceScheduleSlot.setTitleLong(mapLocalized(scheduleSlot.getTitle(), 128));
			referenceScheduleSlot.setSummaryShort(mapLocalized(scheduleSlot.getSummary(), 256));
			referenceScheduleSlot.setSummaryMedium(mapLocalized(scheduleSlot.getSummary(), 1024));
			referenceScheduleSlot.setSummaryLong(mapLocalized(scheduleSlot.getSummary(), 4096));
			referenceScheduleSlot.setGenre(mapLocalized(scheduleSlot.getGenre()));
			referenceScheduleSlot.setCountryOfOrigin(stringifyList(scheduleSlot.getCountryOfOrigin()));
			referenceScheduleSlot.setEpisodeName(LocaleHelper.getStringValueForDefaultLanguage(scheduleSlot.getEpisodeName()));
			referenceScheduleSlot.setEpisodeId(scheduleSlot.getEpisodeId());
			// code by nitin
						referenceScheduleSlot.setCatchUp(scheduleSlot.getCatchUp());
						
						referenceScheduleSlot.setDownloadable(scheduleSlot.getDownloadable());
						referenceScheduleSlot.setSTBEnabled(scheduleSlot.getSTBEnabled());
			referenceScheduleSlot.setImageUrl(scheduleSlot.getImageUrl());
			referenceScheduleSlot.setScreenFormat(stringify(scheduleSlot.getScreenFormat()));
			referenceScheduleSlot.setShowType(stringify(scheduleSlot.getShowType()));
			referenceScheduleSlot.setRating(scheduleSlot.getRating());
		}
		return referenceScheduleSlot;
	}
}
