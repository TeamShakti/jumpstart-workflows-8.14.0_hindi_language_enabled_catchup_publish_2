package com.irdeto.jumpstart.domain.taskhandler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.domain.constants.TaskHandlerConstants;

public class PollingExternalProcessMonitor extends AbstractProcessMonitor {
	private static final Logger logger = LoggerFactory.getLogger(PollingExternalProcessMonitor.class);
	
	private int pid;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
	
	@Override
	public void run() {
		if (isCancelled()) {
			cancel();
			return;
		}
		try {
			Process process = Runtime.getRuntime().exec(getProcessMonitorConfiguration().getPollingCommand().replaceAll("{pid}", String.valueOf(getPid())));
			process.waitFor();
			if (process.exitValue() != 0) {
				// error
				cancel();
				logger.error("Failure while monitoring process {}", getPid());
				Map<String, Object> resultsMap = new HashMap<>();
				resultsMap.put(TaskHandlerConstants.EXIT_STATUS_KEY, TaskHandlerConstants.FAILURE_STATUS);
				resultsMap.put(TaskHandlerConstants.EXIT_MESSAGE_KEY, "Failure while monitoring process " + getPid());
				getWorkItemManager().completeWorkItem(getWorkItemId(), resultsMap, true);
			}
		} catch (Throwable e) {
			logger.error("Error monitoring process " + getPid() + ".", e);
		}
	}
}
