package com.irdeto.jumpstart.domain.taskhandler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.irdeto.manager.workflow.WorkItemManager;
import com.irdeto.taskhandler.SyncSubprocessTaskHandler;

@Component("SuppressingSyncSubprocessTaskHandler")
public class SuppressingSyncSubprocessTaskHandler extends SyncSubprocessTaskHandler {
	@Resource(name="workItemManager")
	private WorkItemManager workItemManager;

	public void handleSuccess(long workItemId, String workItemName, Map<String, Object> results) {
		// the actual suppressing bit
		workItemManager.cancelWorkItem(workItemId);
	}
}
