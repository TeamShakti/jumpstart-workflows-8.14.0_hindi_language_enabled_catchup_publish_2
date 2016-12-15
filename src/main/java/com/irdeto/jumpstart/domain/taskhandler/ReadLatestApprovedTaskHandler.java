package com.irdeto.jumpstart.domain.taskhandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.domain.constants.TaskResult;
import com.irdeto.domain.operation.MM8Response;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.mediamanager.MediaManager;
import com.irdeto.manager.task.InvalidTaskParameterException;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("ReadLatestApproved")
public class ReadLatestApprovedTaskHandler extends AbstractTaskHandler {
	@TaskProperty(type=BaseEntity.class, required = false)
	public static final String CURRENT_ENTITY_PROPERTY = "CurrentEntity";
	@TaskProperty(type=Integer.class, required = false)
	public static final String ENTITY_ID_PROPERTY = "EntityId";
	@TaskProperty(required = false)
	public static final String ENTITY_TYPE_PROPERTY = "EntityType";
	@TaskProperty(type=List.class, required = false)
	public static final String ENTITY_ID_TYPE_WRAPPER_LIST_PROPERTY = "EntityIdTypeWrapperList";

	@TaskResult
	public static final String ENTITY_PROPERTY = "Entity";
	@TaskResult
	public static final String ENTITY_MAP_PROPERTY = "EntityMap";
	@TaskProperty(allowSpaces = false,defaultValue = "true", required = false,type = Boolean.class)
	public static final String CONSIDER_LATEST_VERSION = "ConsiderLatestVersion";
	
	@Resource(name="mediaManager8Rest")
	private MediaManager mediaManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> verifiedParameters, Map<String, Object> results) throws Exception {
		Integer entityId = (Integer)verifiedParameters.get(ENTITY_ID_PROPERTY);
		String entityType = (String)verifiedParameters.get(ENTITY_TYPE_PROPERTY);
		BaseEntity currentEntity = (BaseEntity)verifiedParameters.get(CURRENT_ENTITY_PROPERTY);
		List<EntityIdTypeWrapper> entityIdTypeList = (List<EntityIdTypeWrapper>)verifiedParameters.get(ENTITY_ID_TYPE_WRAPPER_LIST_PROPERTY);
		Boolean considerLatestVersion = (Boolean) verifiedParameters.get(CONSIDER_LATEST_VERSION);
		if (entityIdTypeList != null) {
			ConcurrentHashMap<Integer, Object> approvedEntityMap = new ConcurrentHashMap<>();
			ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap<>();
			dataMap.put(CONSIDER_LATEST_VERSION, considerLatestVersion);
			ThreadPoolFactory.executeLoop(entityIdTypeList, this, ReadApprovedEntity.class, dataMap, approvedEntityMap);
			results.put(ENTITY_MAP_PROPERTY, approvedEntityMap);
		} else {
			if (currentEntity == null && entityId != null && StringUtils.isNotBlank(entityType)) {
				MM8Response response = mediaManager.readEntity(entityType, entityId, null, null);
				currentEntity = WorkflowHelper.getEntity(BaseEntity.class, response);
			}
			ApprovedEntityHelper approvedEntityHelper = new ApprovedEntityHelper();
			results.put(ENTITY_PROPERTY, approvedEntityHelper.getApprovedEntity(mediaManager, currentEntity, considerLatestVersion));
		}
	}
	
	class ReadApprovedEntity extends ThreadPoolFactory.ItemRunnable<EntityIdTypeWrapper> {
		@Override
		public void run() throws Throwable {
			EntityIdTypeWrapper entityIdTypeWrapper = getItem();
			MM8Response response = mediaManager.readEntity(entityIdTypeWrapper.getEntityType(), Integer.valueOf(entityIdTypeWrapper.getEntityId()), null, null);
			BaseEntity entity = WorkflowHelper.getEntity(BaseEntity.class, response);
			ApprovedEntityHelper approvedEntityHelper = new ApprovedEntityHelper();
			BaseEntity approvedEntity = approvedEntityHelper.getApprovedEntity(mediaManager, entity, (Boolean)getDataMap().get(CONSIDER_LATEST_VERSION));
			if (approvedEntity != null) {
				getResultMap().put(getPosition(), approvedEntity);
			}
		}
	} 
	
	@SuppressWarnings("unchecked")
	protected void verifyParameters(Map<String, Object> parameters)
			throws InvalidTaskParameterException {
		Integer entityId = (Integer)parameters.get(ENTITY_ID_PROPERTY);
		String entityType = (String)parameters.get(ENTITY_TYPE_PROPERTY);
		BaseEntity currentEntity = (BaseEntity)parameters.get(CURRENT_ENTITY_PROPERTY);
		List<EntityIdTypeWrapper> entityIdTypeWrapperList = (List<EntityIdTypeWrapper>)parameters.get(ENTITY_ID_TYPE_WRAPPER_LIST_PROPERTY);
		if (entityId == null && StringUtils.isBlank(entityType) && currentEntity == null && entityIdTypeWrapperList == null) {
			throw new InvalidTaskParameterException("You must set " + CURRENT_ENTITY_PROPERTY + " or " + ENTITY_ID_TYPE_WRAPPER_LIST_PROPERTY + " or both of " + ENTITY_ID_PROPERTY + " and " + ENTITY_TYPE_PROPERTY);
		}
		if (entityId != null && StringUtils.isBlank(entityType)) {
			throw new InvalidTaskParameterException("You must set both of " + ENTITY_ID_PROPERTY + " and " + ENTITY_TYPE_PROPERTY);
		}
		if (entityId == null && StringUtils.isNotBlank(entityType)) {
			throw new InvalidTaskParameterException("You must set both of " + ENTITY_ID_PROPERTY + " and " + ENTITY_TYPE_PROPERTY);
		}

	};

}
