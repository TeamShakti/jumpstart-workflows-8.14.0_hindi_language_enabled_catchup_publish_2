package com.irdeto.jumpstart.domain.taskhandler;

import java.util.List;
import java.util.Map;

public interface CommandLineExecutionManager {

	public Map<String, Object> executeProcess(
			String command,
			List<String> environment,
			String directory,
			boolean isAmqUsed,
			ExternalProcessMonitorConfiguration externalProcessMonitorConfiguration,
			long workItemId) throws Exception;

}
