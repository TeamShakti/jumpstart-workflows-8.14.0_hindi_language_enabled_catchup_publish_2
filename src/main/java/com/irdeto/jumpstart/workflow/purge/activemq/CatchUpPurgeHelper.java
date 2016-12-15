package com.irdeto.jumpstart.workflow.purge.activemq;

import static com.irdeto.jumpstart.workflow.WorkflowUtils.reportError;

import org.kie.api.runtime.process.ProcessContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.purge.PurgeWrapper;
import com.irdeto.jumpstart.domain.purge.ScheduleSlotPurgeWrapper;
import com.irdeto.jumpstart.workflow.CatchUpEndpoint;

public class CatchUpPurgeHelper {
	public static final String PURGE_WRAPPER_KEY = "wrapper";

	public static Boolean needCatchUpPurge(PurgeWrapper<ScheduleSlot> purgeWrapper) {
		ScheduleSlot entity = purgeWrapper.getEntity();

		return Boolean.TRUE.equals(entity.getCatchUp());
	}

	public static void prepareDocument(ProcessContext kcontext) throws JsonProcessingException {
		ScheduleSlotPurgeWrapper purgeWrapper =
				(ScheduleSlotPurgeWrapper) kcontext.getVariable(PURGE_WRAPPER_KEY);
		if (purgeWrapper == null) {
			reportError(kcontext, "Schedule slot is not found.");
			return;
		}
		ScheduleSlot entity = purgeWrapper.getEntity();
		if (entity == null) {
			reportError(kcontext, "Schedule slot is not found.");
			return;
		}
		// chnages by nitin
		kcontext.setVariable("document",
			CatchUpEndpoint.getDocument(
				null,
				entity.getUriId(),
				entity.getLinearBroadcastDate(),
				entity.getLinearBroadcastEndDate(),
				entity.getTitle(),
				entity.getSummary(),
				entity.getGenre(),
				entity.getEpisodeName(),
				entity.getEpisodeId(),
				false,
				false,
				false,
				entity.getRating()
				
			));
	}
}
