package com.irdeto.jumpstart.domain.taskhandler;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.irdeto.manager.lock.LockManager;
import com.irdeto.manager.task.BeanUtil;
import com.irdeto.manager.workflow.DataEvent;
import com.irdeto.manager.workflow.DataEventListener;
import com.irdeto.manager.workflow.DispatchingEventListener;
import com.irdeto.manager.workflow.WorkItemManager;
import com.irdeto.manager.workflow.eventlistener.listener.PersistentEventListener;

public class SupersedingLockReleasedEventListener implements DataEventListener, PersistentEventListener,
ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(SupersedingLockReleasedEventListener.class);

	private String lockId;
	private long workItemId;
	private Map<String, Object> results;

	@JsonIgnore
	private LockManager lockManager;
	@JsonIgnore
	private WorkItemManager workItemManager;
	@JsonIgnore
	private DispatchingEventListener dispatchingEventListener;

	public SupersedingLockReleasedEventListener() {}
	public SupersedingLockReleasedEventListener(
			String lockId, long workItemId, Map<String, Object> results,
			LockManager lockManager,
			WorkItemManager workItemManager,
			DispatchingEventListener dispatchingEventListener) {
		this.lockId = lockId;
		this.workItemId = workItemId;
		this.results = results;

		this.lockManager = lockManager;
		this.workItemManager = workItemManager;

		this.dispatchingEventListener = dispatchingEventListener;

	}

	@Override
	public void eventFired(DataEvent eventType, Object event) {
		DataEvent expectedEventType = DataEvent.AFTER_LOCK_REMOVED;
		if (expectedEventType.equals(eventType) && getLockId().equals(event)) {
			LockManager lockManager = BeanUtil.getBean(LockManager.class);
			logger.debug("Event {} fired for lock {} (work item {})",
					DataEvent.AFTER_LOCK_REMOVED,
					getLockId(),
					getWorkItemId());

			DispatchingEventListener dispatchingEventListener = BeanUtil.getBean(DispatchingEventListener.class);
			dispatchingEventListener.removeDataEventListener(this);
			WorkItemManager workItemManager = BeanUtil.getBean(WorkItemManager.class);
			if (lockManager.attainLock(getLockId())) {
				workItemManager.completeWorkItem(getWorkItemId(), getResults(),	false);
			} else {
				workItemManager.cancelWorkItem(getWorkItemId());
			}
		}
	}

	@Override
	@JsonIgnore
	public DataEvent getDataEvent() {
		return DataEvent.AFTER_LOCK_REMOVED;
	}

	@Override
	public long getWorkItemId() {
		return this.workItemId;
	}

	public String getLockId() {
		return lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}

	public void setWorkItemId(long workItemId) {
		this.workItemId = workItemId;
	}

	public Map<String, Object> getResults() {
		return results;
	}

	public void setResults(Map<String, Object> results) {
		this.results = results;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.workItemManager = applicationContext.getBean(WorkItemManager.class);
		this.lockManager = applicationContext.getBean(LockManager.class);
		this.dispatchingEventListener = applicationContext.getBean(DispatchingEventListener.class);
	}
}
