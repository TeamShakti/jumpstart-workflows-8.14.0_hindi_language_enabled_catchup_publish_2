package com.irdeto.jumpstart.domain.taskhandler;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.irdeto.dao.task.TaskDAO;
import com.irdeto.dao.task.TaskInfoDAO;
import com.irdeto.dao.task.TaskPermissionException;
import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.domain.taskinfo.TaskInfo;
import com.irdeto.manager.workflow.WorkItemManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("CancelTask")
public class CancelTaskTaskHandler extends AbstractTaskHandler {
	private static final Logger logger = LoggerFactory.getLogger(CancelTaskTaskHandler.class);

	@Resource(name="taskInfoDAO")
	private TaskInfoDAO taskInfoDAO;

	@Resource(name="taskDao")
	private TaskDAO taskDAO;

	@Resource(name="workItemManager")
	private WorkItemManager workItemManager;

	@TaskProperty(allowSpaces = true)
	public static final String USER_ID_PROPERTY = "UserId";
	@TaskProperty(required = false, allowSpaces = true, defaultValue = "")
	public static final String CONTEXT_PROPERTY = "Context";
	@TaskProperty(type = Boolean.class, required = false, defaultValue = "true")
	public static final String SPECIFIC_PROCESS_INSTANCE_PROPERTY = "SpecificProcessInstance";
	@TaskProperty(type = Boolean.class, required = false, defaultValue = "false")
	public static final String FORCE = "Force";
	@TaskProperty
	public static final String ENTITY_ID_PROPERTY = "EntityId";
	@TaskProperty
	public static final String ENTITY_TYPE_PROPERTY = "EntityType";

	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> verifiedParameters, Map<String, Object> results) throws Exception {
		String entityType = (String) verifiedParameters.get(ENTITY_TYPE_PROPERTY);
		String entityId = (String) verifiedParameters.get(ENTITY_ID_PROPERTY);
		String userId = (String) verifiedParameters.get(USER_ID_PROPERTY);
		String context = (String) verifiedParameters.get(CONTEXT_PROPERTY);

		boolean onlyCurrentProcessInstance = BooleanUtils.isTrue(
				(Boolean) verifiedParameters.get(SPECIFIC_PROCESS_INSTANCE_PROPERTY)
		);
		boolean force = BooleanUtils.isTrue(
				(Boolean) verifiedParameters.get(FORCE)
		);

		List<Status> statusList = Arrays.asList(
				Status.Created,
				Status.Ready,
				Status.InProgress,
				Status.Reserved,
				Status.Suspended
		);

		logger.debug(
				"Cancelling (skipping) {}:{} tasks{} assigned to {} with context '{}' and statuses {}",
				entityType,
				entityId,
				onlyCurrentProcessInstance
						? " from the current process instance"
						: "",
				userId,
				context != null ? context : "",
				Arrays.toString(statusList.toArray())
		);

		WorkItem currentWorkItem = workItemManager.getWorkItem(workItemId);
		if (currentWorkItem == null) {
			return;
		}
		Long currentProcessInstanceId = currentWorkItem.getProcessInstanceId();

		List<TaskInfo> taskList = taskInfoDAO.getPotentialTaskInfoList(userId, statusList, 0, Integer.MAX_VALUE);
		for (TaskInfo taskInfo: taskList) {
			logger.trace("Checking task info {}", taskInfo.getId());

			Long taskWorkItemId = taskInfo.getWorkItemId();
			Long taskProcessInstanceId = taskInfo.getProcessInstanceId();

			logger.trace("Task info work item {}, process instance {}", taskWorkItemId, taskProcessInstanceId);

			boolean entityMatches = entityType.equals(taskInfo.getEntityType())
					&& entityId.equals(taskInfo.getEntityId());
			boolean processInstanceMatches = !onlyCurrentProcessInstance
					|| (onlyCurrentProcessInstance && taskProcessInstanceId.equals(currentProcessInstanceId));
			boolean contextMatches = isBlank(context) || context.equals(taskInfo.getContext());

			if (taskInfo.getTaskId() == null) {
				logger.trace("No task linked with the task info {}", taskInfo.getId());
				continue;
			}

			Task task = taskDAO.getTaskById(taskInfo.getTaskId());
			if (entityMatches && processInstanceMatches &&  contextMatches) {
				try {
					logger.debug("Skipping task {}", task.getId());
					taskDAO.skipTask(task);
				} catch (TaskPermissionException e) {
					logger.warn("Failed to skip task " + task.getId(), e);
					if (force) {
						logger.info("Attempting to exit task " + task.getId());
						taskDAO.exitTask(task);
					}
				} finally {
					workItemManager.cancelWorkItem(taskWorkItemId);
				}
			}
		}
	}
}
