package com.irdeto.jumpstart.workflow;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.irdeto.jumpstart.domain.Locale;

import static com.irdeto.jumpstart.workflow.WorkflowUtils.JSON_MAPPER;

public class CatchUpEndpoint {
// change by nitin
	public static String getDocument(
			String channelId,
			String scheduleSlotId,
			DateTime linearBroadcastDate,
			DateTime linearBroadcastEndDate,
			Locale title,
			Locale summary,
			Locale genre,
			Locale episodename,
			String episodeid,
			Boolean catchUp,
			Boolean downloadable,
			Boolean stbenabled,
			String rating
	) throws JsonProcessingException {
		Map<String, Object> message = new HashMap<>();
		message.put("ChannelId", channelId);
		message.put("ScheduleSlotId", scheduleSlotId);
		message.put("LinearBroadcastDate", linearBroadcastDate);
		message.put("LinearBroadcastEndDate", linearBroadcastEndDate);
		message.put("Title", title);
		message.put("Summary", summary);
		message.put("Genre", genre);
		message.put("EpisodeName", episodename);
		message.put("EpisodeId", episodeid);
		message.put("CatchUp", catchUp);
		message.put("Downloadable", downloadable);
		message.put("STBEnabled", stbenabled);
		message.put("Rating", rating);

		return JSON_MAPPER.writeValueAsString(message);
	}

}
