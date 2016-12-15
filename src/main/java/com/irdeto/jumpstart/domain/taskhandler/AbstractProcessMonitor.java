package com.irdeto.jumpstart.domain.taskhandler;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import com.irdeto.manager.workflow.WorkItemManager;

public abstract class AbstractProcessMonitor implements Runnable {
	private WorkItemManager workItemManager;
	private ExternalProcessMonitorConfiguration processMonitorConfiguration;
	private long workItemId;
	private Map<Long, ? extends AbstractProcessMonitor> processMonitorMap;
	private boolean cancelled = false;
	
	private ScheduledFuture<?> scheduledFuture;
	
	public WorkItemManager getWorkItemManager() {
		return workItemManager;
	}

	public void setWorkItemManager(WorkItemManager workItemManager) {
		this.workItemManager = workItemManager;
	}

	public ExternalProcessMonitorConfiguration getProcessMonitorConfiguration() {
		return processMonitorConfiguration;
	}

	public void setProcessMonitorConfiguration(
			ExternalProcessMonitorConfiguration processMonitorConfiguration) {
		this.processMonitorConfiguration = processMonitorConfiguration;
	}

	public long getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(long workItemId) {
		this.workItemId = workItemId;
	}

	public ScheduledFuture<?> getScheduledFuture() {
		return scheduledFuture;
	}

	public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
		this.scheduledFuture = scheduledFuture;
	}

	public Map<Long, ? extends AbstractProcessMonitor> getProcessMonitorMap() {
		return processMonitorMap;
	}

	public void setProcessMonitorMap(Map<Long, ? extends AbstractProcessMonitor> processMonitorMap) {
		this.processMonitorMap = processMonitorMap;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public synchronized AbstractProcessMonitor cancel() {
		setCancelled(true);
		if (getScheduledFuture() != null) {
			getScheduledFuture().cancel(false);
		}
		getProcessMonitorMap().remove(getWorkItemId());
		return this;
	}
}
