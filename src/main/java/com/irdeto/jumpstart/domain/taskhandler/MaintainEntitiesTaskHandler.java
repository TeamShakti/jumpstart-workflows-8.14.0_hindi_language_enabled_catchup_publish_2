package com.irdeto.jumpstart.domain.taskhandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.domain.constants.TaskResult;
import com.irdeto.domain.mmentityapi.HttpQueryFilterParameter;
import com.irdeto.domain.operation.MM8Response;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.ingest.MaintainEntities;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.mediamanager.MediaManager;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("MaintainEntities")
public class MaintainEntitiesTaskHandler extends AbstractTaskHandler {
	@TaskProperty(type=MaintainEntities.class)
	public static final String MAINTAIN_ENTITIES_PROPERTY = "MaintainEntities";
	@TaskProperty(type=Boolean.class, required=false, defaultValue="true")
	public static final String DELETE_PROPERTY = "Delete";
	@TaskResult
	public static final String MAINTAINED_ENTITY_LIST_PROPERTY = "MaintainedEntityList";
	@TaskResult
	public static final String MAINTAINED_ENTITY_PROPERTY = "MaintainedEntity";

	@Resource(name="mediaManager8Rest")
	private MediaManager mediaManager;

	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> verifiedParameters, Map<String, Object> results) throws Exception {
		MaintainEntities maintainEntities = (MaintainEntities) verifiedParameters.get(MAINTAIN_ENTITIES_PROPERTY);
		Boolean delete = (Boolean) verifiedParameters.get(DELETE_PROPERTY);
		// Read existing entities
		MM8Response readResponse = mediaManager.readEntity(maintainEntities.getEntityType(), new HttpQueryFilterParameter(), null, null);
		if (BooleanUtils.isTrue(delete)) {
			List<String> deleteEntityIdList = getEntities(maintainEntities, readResponse);
			
			// Delete unnecessary entities
			for (String entityId: deleteEntityIdList) {
				mediaManager.deleteEntity(maintainEntities.getEntityType(), Integer.valueOf(entityId));
			}
		}
		
		List<BaseEntity> maintainedEntityList = new ArrayList<>();
		// Insert/update entities
		for (BaseEntity entity: maintainEntities.getEntityList()) {
			if (!(entity instanceof BaseEntity)) {
				continue;
			}
			String id = ((BaseEntity)entity).getId();
			if (id != null) {
				// Update
				mediaManager.updateEntity(maintainEntities.getEntityType(), Integer.valueOf(id), entity);
				maintainedEntityList.add(entity);
			} else {
				// Create
				MM8Response response = mediaManager.createEntity(maintainEntities.getEntityType(), entity);
				BaseEntity createdEntity = WorkflowHelper.getEntity(maintainEntities.getEntityClass(), response);
				maintainedEntityList.add(createdEntity);
			}
		}
		results.put(MAINTAINED_ENTITY_LIST_PROPERTY, maintainedEntityList);
		if (maintainedEntityList != null && !maintainedEntityList.isEmpty()) {
			results.put(MAINTAINED_ENTITY_PROPERTY, maintainedEntityList.get(0));
		}
	}
	
	@JsonIgnore
	private List<String> getEntities(MaintainEntities maintainEntities, MM8Response response) {
		List<BaseEntity> existingEntityList = WorkflowHelper.getEntityList(BaseEntity.class, response);
		List<?> entityList = maintainEntities.getEntityList();
		List<String> deletedEntityIdList = new ArrayList<>();
		
		for (BaseEntity existingEntity: existingEntityList) {
			try {
				String existingId = existingEntity.getId();
				Method existingLogicalKeyMethod = existingEntity.getClass().getMethod("get" + StringUtils.capitalize(maintainEntities.getLogicalKeyField()));
				String existingLogicalKey = (String)existingLogicalKeyMethod.invoke(existingEntity);
				boolean found = false;
				for (Object entity: entityList) {
					Method logicalKeyMethod = entity.getClass().getMethod("get" + StringUtils.capitalize(maintainEntities.getLogicalKeyField()));
					String logicalKey = (String)logicalKeyMethod.invoke(entity);
					if (existingLogicalKey.equals(logicalKey)) {
						Method idMethod = entity.getClass().getMethod("setId", String.class);
						idMethod.invoke(entity, existingId);
						found = true;
					}
				}
				if (!found) {
					deletedEntityIdList.add(existingId);
				}
			} catch (NoSuchMethodException | SecurityException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
			}
		}
		return deletedEntityIdList;
	}
	
}
