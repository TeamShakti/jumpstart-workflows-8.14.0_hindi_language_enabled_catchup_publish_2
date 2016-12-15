package com.irdeto.jumpstart.domain.taskhandler;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.kie.api.runtime.manager.audit.ProcessInstanceLog;
import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.manager.workflow.ProcessAuditManager;
import com.irdeto.manager.workflow.ProcessExecutionManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("SignalWorkflow")
public class SignalWorkflowTaskHandler extends AbstractTaskHandler {
	@TaskProperty
	public static final String PROCESS_ID = "ProcessId";
	@TaskProperty
	public static final String VARIABLE_TO_MATCH = "VariableToMatch";
	@TaskProperty(allowSpaces = true)
	public static final String VALUE_TO_MATCH = "ValueToMatch";
	@TaskProperty(allowSpaces = true)
	public static final String EVENT_TYPE_PROPERTY = "EventType";

	@Resource(name="processAuditManager")
	private ProcessAuditManager processAuditManager;

	@Resource(name="processExecutionManager")
	private ProcessExecutionManager processExecutionManager;

	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> verifiedParameters, Map<String, Object> results) throws Exception {
		List<ProcessInstanceLog> processInstances = processAuditManager
				.getActiveProcessInstancesUsingCriteria(
						(String) verifiedParameters.get(PROCESS_ID),
						(String) verifiedParameters.get(VARIABLE_TO_MATCH),
						(String) verifiedParameters.get(VALUE_TO_MATCH));
		for (ProcessInstanceLog processInstanceLog : processInstances) {
			processExecutionManager.signalProcessInstance(
					processInstanceLog.getProcessInstanceId(),
					(String)verifiedParameters.get(EVENT_TYPE_PROPERTY),
					verifiedParameters);
		}
	}

}
