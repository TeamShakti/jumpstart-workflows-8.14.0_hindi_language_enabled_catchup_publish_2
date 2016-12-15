package com.irdeto.jumpstart.domain.taskhandler;

import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.BaseSpringTestWithSetup;
import com.irdeto.manager.task.BeanUtil;

@Test
public class CommandLineIntegrationTest extends BaseSpringTestWithSetup {
	public void testCommandLine() throws Exception {
		CommandLineExecutionManager commandLineExecutionManager = BeanUtil.getBean(CommandLineExecutionManager.class);
		ExternalProcessMonitorConfiguration externalProcessMonitorConfiguration = new ExternalProcessMonitorConfiguration();
		externalProcessMonitorConfiguration.setPollingEnabled(false);
		externalProcessMonitorConfiguration.setTimeoutEnabled(false);
		commandLineExecutionManager.executeProcess("c:/temp/tim.bat", null, "c:/temp", true, externalProcessMonitorConfiguration, 0);
	}
}
