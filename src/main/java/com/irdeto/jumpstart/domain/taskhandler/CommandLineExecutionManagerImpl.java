package com.irdeto.jumpstart.domain.taskhandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskHandlerConstants;

@Component("commandLineExecutionManager")
public class CommandLineExecutionManagerImpl implements CommandLineExecutionManager,ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(CommandLineExecutionManagerImpl.class);
	
	//@Resource(name = "externalProcessMonitorManager")
	private ExternalProcessMonitorManager externalProcessMonitorManager;
	
	private ApplicationContext applicationContext;
	
	@PostConstruct
	public void setup() {
		this.externalProcessMonitorManager = SpringHelper.getBean(ExternalProcessMonitorManagerImpl.class,applicationContext);
		
	}
	
	@Override
	public Map<String, Object> executeProcess(String command, List<String> environment, String directory, boolean isAmqUsed, ExternalProcessMonitorConfiguration externalProcessMonitorConfiguration, long workItemId) throws Exception {
		logger.debug("Command: {}", command);
		String[] splitCmd = command.trim().split("\\s+");
		String[] environmentArray = null;
		if (environment == null) {
			environmentArray = new String[0];
		} else {
			environmentArray = environment.toArray(new String[0]);
		}
		Process process = null;
		if (StringUtils.isNotBlank(directory)) {
			File workingDirectory = new File(directory);
			process = Runtime.getRuntime().exec(splitCmd, environmentArray, workingDirectory);
		} else {
			process = Runtime.getRuntime().exec(splitCmd, environmentArray);
		}
		if (isAmqUsed) {
			externalProcessMonitorManager.monitorProcess(process, externalProcessMonitorConfiguration, workItemId);
		}
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        // read the output from the command
        StringBuffer output = new StringBuffer();
        logger.debug("Standard output of the command:\n");
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            logger.debug(s);
            output.append(s).append('\n');
        }
        // read any errors from the attempted command
        logger.debug("Standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
        	logger.error(s);
            output.append(s).append('\n');
        }
	    process.waitFor();
		Map<String, Object> resultsMap = new HashMap<>();
		if (process.exitValue() == 0) {
			resultsMap.put(TaskHandlerConstants.EXIT_STATUS_KEY, TaskHandlerConstants.SUCCESS_STATUS);
		} else {
			resultsMap.put(TaskHandlerConstants.EXIT_STATUS_KEY, TaskHandlerConstants.FAILURE_STATUS);
			resultsMap.put(TaskHandlerConstants.EXIT_MESSAGE_KEY, output);
		}
		return resultsMap;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		 this.applicationContext = applicationContext;
	}
}
