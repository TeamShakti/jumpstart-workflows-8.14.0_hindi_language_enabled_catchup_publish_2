package com.irdeto.jumpstart.domain.taskhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskHandlerConstants;
import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.manager.json.JsonManager;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.workflow.WorkItemManager;
import com.irdeto.taskhandler.AbstractTaskHandler;
import com.irdeto.taskhandler.annotation.TaskConfig;


@TaskConfig(isAsynchronous=true)
@Component("CommandLine")
public class CommandLineTaskHandler extends AbstractTaskHandler implements ApplicationContextAware {
	@TaskProperty(required=true, allowSpaces = true)
	private static final String COMMAND_PARAMETER = "Command";
	@TaskProperty(type=Boolean.class, required=false, defaultValue="true")
	private static final String USE_AMQ_PARAMETER = "UseAMQ";
	@TaskProperty(required=false)
	private static final String DIRECTORY_PARAMETER = "Directory";
	@TaskProperty(type=String[].class, required=false)
	private static final String ENVIRONMENT_PARAMETER = "Environment";
	@TaskProperty(type=Boolean.class, required=false, defaultValue="false")
	private static final String POLLING_ENABLED_PARAMETER = "PollingEnabled";
	@TaskProperty(required=false)
	private static final String POLLING_COMMAND_PARAMETER = "PollingCommand";
	@TaskProperty(type=Integer.class, required=false, defaultValue="30")
	private static final String POLLING_PERIOD_PARAMETER = "PollingPeriod";
	@TaskProperty(type=Boolean.class, required=false, defaultValue="false")
	private static final String TIMEOUT_ENABLED_PARAMETER = "TimeoutEnabled";
	@TaskProperty(type=Integer.class, required=false, defaultValue="600")
	private static final String TIMEOUT_PERIOD_PARAMETER = "TimeoutPeriod";

	@Resource(name="workItemManager")
	private WorkItemManager workItemManager;

	@Resource(name="propertiesManager")
	private PropertiesManager propertiesManager;

	@Resource(name="jsonManager")
	private JsonManager jsonManager;

	@Resource(name="connectionFactory")
	private ConnectionFactory connectionFactory;

	//@Resource(name="externalProcessMessageConsumerManager")
	@SuppressWarnings("unused")
	private ExternalProcessMessageConsumerManager externalProcessMessageConsumerManager;

	//@Resource(name="commandLineExecutionManager")
	private CommandLineExecutionManager commandLineExecutionManager;

	private ApplicationContext applicationContext;

	@PostConstruct
	public void setup() {

		this.externalProcessMessageConsumerManager = SpringHelper.getBean(ExternalProcessMessageConsumerManager.class,applicationContext);
		this.commandLineExecutionManager = SpringHelper.getBean(CommandLineExecutionManagerImpl.class,applicationContext);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> verifiedParameters, Map<String, Object> results) throws Exception {
		String command = (String)verifiedParameters.get(COMMAND_PARAMETER);
		String useAMQ = (String)verifiedParameters.get(USE_AMQ_PARAMETER);
		String directory = (String)verifiedParameters.get(DIRECTORY_PARAMETER);
		List<String> environment = (List<String>)verifiedParameters.get(ENVIRONMENT_PARAMETER);

		boolean isAmqUsed = BooleanUtils.toBoolean(useAMQ);
		if (isAmqUsed) {
			if (environment == null) {
				environment = new ArrayList<>();
				environment.add("WORK_ITEM_ID=" + String.valueOf(workItemId));
			} else {
				environment.add("WORK_ITEM_ID=" + String.valueOf(workItemId));
			}
		}
		boolean pollingEnabled = BooleanUtils.toBoolean((String)verifiedParameters.get(POLLING_ENABLED_PARAMETER));
		String pollingCommand = (String)verifiedParameters.get(POLLING_COMMAND_PARAMETER);
		Integer pollingPeriod = null;
		if (StringUtils.isNumeric((String)verifiedParameters.get(POLLING_PERIOD_PARAMETER))) {
			pollingPeriod = Integer.valueOf((String)verifiedParameters.get(POLLING_PERIOD_PARAMETER));
		}
		boolean timeoutEnabled = BooleanUtils.toBoolean((String)verifiedParameters.get(TIMEOUT_ENABLED_PARAMETER));
		Integer timeoutPeriod = null;
		if (StringUtils.isNumeric((String)verifiedParameters.get(TIMEOUT_PERIOD_PARAMETER))) {
			timeoutPeriod = Integer.valueOf((String)verifiedParameters.get(TIMEOUT_PERIOD_PARAMETER));
		}

		ExternalProcessMonitorConfiguration processMonitorConfiguration = new ExternalProcessMonitorConfiguration(pollingEnabled, pollingCommand, pollingPeriod, timeoutEnabled, timeoutPeriod);
		Map<String, Object> resultsMap = commandLineExecutionManager.executeProcess(command, environment, directory, isAmqUsed, processMonitorConfiguration, workItemId);
		if (!isAmqUsed || TaskHandlerConstants.FAILURE_STATUS.equals(resultsMap.get(TaskHandlerConstants.EXIT_STATUS_KEY))) {
			workItemManager.completeWorkItem(workItemId, resultsMap, false);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
