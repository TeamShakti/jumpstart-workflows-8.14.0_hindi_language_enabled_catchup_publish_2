package com.irdeto.jumpstart.workflow.publish.activemq;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.publish.EntityWithChannelListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.PublishWrapper;
import com.irdeto.jumpstart.workflow.CatchUpEndpoint;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.publish.reference.ScheduleSlotReferenceDocumentMapper;

public class CatchUpPublishHelper extends WorkflowHelper {
	private static final Logger logger = LoggerFactory.getLogger(CatchUpPublishHelper.class);

	public static boolean needVersionRead(PublishWrapper<ScheduleSlot> publishWrapper) {
		List<Object> versions = getEntity(publishWrapper).getVersions();
		return versions.isEmpty() || versions.size() > 1;
	}

	public static Integer getPreviousVersion(PublishWrapper<ScheduleSlot> publishWrapper) {
		List<Object> versions = getEntity(publishWrapper).getVersions();
		try {
			return versions.size() <= 1
					? 1
					: Integer.valueOf((versions.get(versions.size() - 2).toString()));
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			logger.warn("Failed to get previous version number of {}", getEntity(publishWrapper).toString());
			return 0;
		}
	}

	public static boolean needsCatchUpPublish(
			ScheduleSlot currentVersion ,
			ScheduleSlot previousVersion
	) {
		if (currentVersion == null) {
			return false;
		}
		if (currentVersion.equals(previousVersion)) {
			return false;
		}
		//changes by nitin
		if (previousVersion == null) {
			return (currentVersion.getCatchUp()&&currentVersion.getSTBEnabled());
		}

		// change by nitin
		boolean stbenabledChanged
		= currentVersion.getSTBEnabled() != previousVersion.getSTBEnabled()
		&& currentVersion.getLinearBroadcastDate() != null
		&& currentVersion.getLinearBroadcastEndDate() != null;
		
		boolean catchupChanged
				= currentVersion.getCatchUp() != previousVersion.getCatchUp()
				&& currentVersion.getLinearBroadcastDate() != null
				&& currentVersion.getLinearBroadcastEndDate() != null;
		boolean startChanged
				= currentVersion.getLinearBroadcastDate() != null
				&& previousVersion.getLinearBroadcastDate() != null
				&& currentVersion.getLinearBroadcastDate().getMillis()
				!= previousVersion.getLinearBroadcastDate().getMillis();
		boolean endChanged
				= currentVersion.getLinearBroadcastEndDate() != null
				&& previousVersion.getLinearBroadcastEndDate() != null
				&& currentVersion.getLinearBroadcastEndDate().getMillis()
				!= previousVersion.getLinearBroadcastEndDate().getMillis();

		return stbenabledChanged || (currentVersion.getSTBEnabled() && (startChanged || endChanged));
	}

	public static String prepareDocument(PublishWrapper<ScheduleSlot> publishWrapper) throws JsonProcessingException {
		ScheduleSlot scheduleSlot = getEntity(publishWrapper);
		Channel channel = null;
		if (publishWrapper instanceof EntityWithChannelListPublishWrapper) {
			channel = ((EntityWithChannelListPublishWrapper) publishWrapper).getChannel();
		}
// changes by nitin
		return CatchUpEndpoint.getDocument(
				channel != null ? channel.getChannelId() : null,
				scheduleSlot.getUriId(),

				scheduleSlot.getLinearBroadcastDate(),
				scheduleSlot.getLinearBroadcastEndDate(),

				scheduleSlot.getTitle(),
				scheduleSlot.getSummary(),
				scheduleSlot.getGenre(),
				scheduleSlot.getEpisodeName(),
				scheduleSlot.getEpisodeId(),
				scheduleSlot.getCatchUp(),
				scheduleSlot.getDownloadable(),
				scheduleSlot.getSTBEnabled(),
				scheduleSlot.getRating()
				
		);
		
		
	}

	private static ScheduleSlot getEntity(PublishWrapper<ScheduleSlot> publishWrapper) {
		return publishWrapper.getEntity();
	}
}
