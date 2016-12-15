package com.irdeto.jumpstart.domain.taskhandler;

public interface ExternalProcessMonitorManager {

	public void monitorProcess(Process process,
			ExternalProcessMonitorConfiguration processMonitorConfiguration,
			long workItemId);

	public void destroy();

	public void setup();

	public void cancel(long workItemId);
	
}
