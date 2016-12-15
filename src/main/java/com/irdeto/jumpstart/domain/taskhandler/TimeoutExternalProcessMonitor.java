package com.irdeto.jumpstart.domain.taskhandler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.domain.constants.TaskHandlerConstants;

public class TimeoutExternalProcessMonitor extends AbstractProcessMonitor {
	private static final Logger logger = LoggerFactory.getLogger(TimeoutExternalProcessMonitor.class);

	@Override
	public void run() {
		if (isCancelled()) {
			cancel();
			return;
		}
		cancel();
		logger.error("Timeout while monitoring process.");
		Map<String, Object> resultsMap = new HashMap<>();
		resultsMap.put(TaskHandlerConstants.EXIT_STATUS_KEY, TaskHandlerConstants.FAILURE_STATUS);
		resultsMap.put(TaskHandlerConstants.EXIT_MESSAGE_KEY, "Timeout while monitoring process.");
		getWorkItemManager().completeWorkItem(getWorkItemId(), resultsMap, true);
	}
}
