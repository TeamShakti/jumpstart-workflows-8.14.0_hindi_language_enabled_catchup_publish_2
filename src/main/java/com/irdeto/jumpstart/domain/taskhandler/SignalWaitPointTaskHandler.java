package com.irdeto.jumpstart.domain.taskhandler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.manager.jbpm.session.SessionManager;
import com.irdeto.manager.workflow.WorkItemManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("SignalWaitPoint")
public class SignalWaitPointTaskHandler extends AbstractTaskHandler {

	private static final String WAIT_POINT_MAP_KEY = "waitPointMap";

	@Resource(name="workItemManager")
	private WorkItemManager workItemManager;

	@Resource(name="sessionManager")
	protected SessionManager sessionManager;

	@TaskProperty(allowSpaces = true)
	public static final String SIGNAL_PROPERTY = "Signal";

	@SuppressWarnings("unchecked")
	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> verifiedParameters, Map<String, Object> results) throws Exception {
		String signal = (String)verifiedParameters.get(SIGNAL_PROPERTY);

		WorkItem workItem = workItemManager.getWorkItem(workItemId);
		if (workItem == null) {
			throw new Exception("Work item is null in signal wait point task handler.");
		}

		ProcessInstance processInstance = sessionManager.getSession().getProcessInstance(workItem.getProcessInstanceId());
		if (!(processInstance instanceof WorkflowProcessInstance)) {
			throw new Exception ("ProcessInstance is not WorkflowProcessInstance in signal wait point task handler.");
		}
		WorkflowProcessInstance workflowProcessInstance = (WorkflowProcessInstance)processInstance;
		Object waitPointMapObject = workflowProcessInstance.getVariable(WAIT_POINT_MAP_KEY);
		if (waitPointMapObject == null) {
			waitPointMapObject = new HashMap<String, Long>();
		}

		Map<String, Long> waitPointMap = (Map<String, Long>)waitPointMapObject;
		Long existingWaitPointWorkItemId = waitPointMap.get(signal);
		if (existingWaitPointWorkItemId != null) {
			// something is waiting for this signal
			workItemManager.completeWorkItem(existingWaitPointWorkItemId, new HashMap<String, Object>(), false);
			waitPointMap.remove(signal);
			workflowProcessInstance.setVariable(WAIT_POINT_MAP_KEY, waitPointMapObject);
		}
	}

}
