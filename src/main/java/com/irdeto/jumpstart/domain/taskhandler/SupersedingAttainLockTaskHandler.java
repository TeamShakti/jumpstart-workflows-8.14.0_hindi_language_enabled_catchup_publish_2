package com.irdeto.jumpstart.domain.taskhandler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.manager.lock.LockManager;
import com.irdeto.manager.workflow.DispatchingEventListener;
import com.irdeto.manager.workflow.WorkItemManager;
import com.irdeto.taskhandler.AbstractTaskHandler;
import com.irdeto.taskhandler.annotation.TaskConfig;

@Component("SupersedingAttainLock")
@TaskConfig(isAsynchronous = true)
public class SupersedingAttainLockTaskHandler extends AbstractTaskHandler {
	@TaskProperty(allowSpaces = true)
	public static final String LOCK_ID = "LockID";

	@Resource(name = "lockManager")
	private LockManager lockManager;

	@Resource(name = "dispatchingEventListener")
	private DispatchingEventListener dispatchingEventListener;

	@Resource(name = "workItemManager")
	private WorkItemManager workItemManager;

	@Override
	public void processTask(
			final long workItemId, String workItemName,
			Map<String, Object> params, Map<String, Object> results)
			throws Exception {
		String lockId = (String) params.get(LOCK_ID);
		this.attainLock(lockId, workItemId, results);
	}

	private void attainLock(
			final String lockId,
			final long workItemId, Map<String, Object> results) {
		if (lockManager.attainLock(lockId)) {
			workItemManager.completeWorkItem(
					workItemId, results,
					true
			);
		} else {
			dispatchingEventListener.addDataEventListener(
					new SupersedingLockReleasedEventListener(
							lockId,
							workItemId, results,
							lockManager,
							workItemManager,
							dispatchingEventListener
					)
			);
		}
	}
}
