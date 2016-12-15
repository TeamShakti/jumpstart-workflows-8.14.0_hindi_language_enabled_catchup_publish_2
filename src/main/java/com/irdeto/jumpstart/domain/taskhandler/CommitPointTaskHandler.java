package com.irdeto.jumpstart.domain.taskhandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.jbpm.workflow.instance.node.WorkItemNodeInstance;
import org.kie.api.event.process.ProcessCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.manager.workflow.DataEvent;
import com.irdeto.manager.workflow.DataEventListener;
import com.irdeto.manager.workflow.DispatchingEventListener;
import com.irdeto.manager.workflow.WorkItemManager;
import com.irdeto.taskhandler.AbstractTaskHandler;
import com.irdeto.taskhandler.annotation.TaskConfig;

@TaskConfig(isAsynchronous=true)
@Component("CommitPoint")
public class CommitPointTaskHandler extends AbstractTaskHandler {
	private static final Logger logger = LoggerFactory.getLogger(CommitPointTaskHandler.class);

	private static Map<Long, Map<Long, Integer>> batchCounterMap = new ConcurrentHashMap<>();

	@TaskProperty(type=Integer.class, required=false, defaultValue="1")
	public static final String BATCH_SIZE_PROPERTY = "BatchSize";

	@Resource(name="workItemManager")
	private WorkItemManager workItemManager;

	@Resource(name = "dispatchingEventListener")
	private DispatchingEventListener dispatchingEventListener;

	@PostConstruct
	public void setup() {
		dispatchingEventListener.addDataEventListener(new DataEventListener() {
			@Override
			public void eventFired(DataEvent eventType, Object event) {
				if (DataEvent.AFTER_PROCESS_COMPLETED.equals(eventType)
						&& event instanceof ProcessCompletedEvent) {
					Long processInstanceId = ((ProcessCompletedEvent) event).getProcessInstance().getId();
					batchCounterMap.remove(processInstanceId);
				}
			}
		});
	}

	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> verifiedParameters, Map<String, Object> results) throws Exception {
		int batchSize = (Integer) verifiedParameters.get(BATCH_SIZE_PROPERTY);

		if (batchSize > 1) {
			WorkItemNodeInstance currentNodeInstance = workItemManager.getNodeInstance(workItemManager.getWorkItem(workItemId));
			long currentNodeId = currentNodeInstance.getNodeId();
			long currentProcessInstanceId = currentNodeInstance.getProcessInstance().getId();

			Map<Long, Integer> nodeIdCounterMap = batchCounterMap.get(currentProcessInstanceId);
			if (nodeIdCounterMap == null) {
				nodeIdCounterMap = new HashMap<>();
				batchCounterMap.put(currentProcessInstanceId, nodeIdCounterMap);
			}

			Integer currentCount = nodeIdCounterMap.get(currentNodeId);
			currentCount = (currentCount == null)
					? 1
					: currentCount + 1;
			nodeIdCounterMap.put(currentNodeId, currentCount);

			if (currentCount < batchSize) {
				logger.debug(
						"{}:{} not entering wait state (batch size is {}, current is {})",
						currentProcessInstanceId,
						currentNodeId,
						batchSize,
						currentCount
				);
				workItemManager.completeWorkItem(workItemId, new HashMap<String, Object>(), true);
				return;
			}
		}

		logger.debug("Entering wait state");

		workItemManager.completeWorkItem(workItemId, new HashMap<String, Object>(), false);
	}

}
