package com.irdeto.jumpstart.domain.taskhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

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
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.mediamanager.MediaManager;
import com.irdeto.manager.task.InvalidTaskParameterException;
import com.irdeto.taskhandler.AbstractTaskHandler;

@Component("CompoundRelationshipRead")
public class CompoundRelationshipReadTaskHandler extends AbstractTaskHandler {
	@TaskProperty(type=List.class)
	public static final String RELATIONSHIP_LIST_PROPERTY = "RelationshipList";
	@TaskProperty(type=BaseEntity.class, required=false)
	public static final String ENTITY_PROPERTY = "Entity";
	@TaskProperty(required=false)
	public static final String ENTITY_ID_PROPERTY = "EntityId";
	@TaskProperty(required=false)
	public static final String ENTITY_TYPE_PROPERTY = "EntityType";
	@TaskProperty(type=Boolean.class, required=false, defaultValue="false")
	public static final String APPROVED_ONLY_PROPERTY = "ApprovedOnly";
	@TaskResult
	public static final String RELATIONSHIP_MAP_PROPERTY = "RelationshipMap";

	protected static final String NEXT_RELATIONSHIP_KEY = "NextRelationship";

	@Resource(name="mediaManager8Rest")
	private MediaManager mediaManager;

	@Override
	protected void verifyParameters(Map<String, Object> parameters) throws InvalidTaskParameterException {
		if (parameters.get(ENTITY_PROPERTY) != null && !(parameters.get(ENTITY_PROPERTY) instanceof BaseEntity)) {
			throw new InvalidTaskParameterException("Either entity should be set or entityId and entityType should be set.");
		}
		BaseEntity entity = (BaseEntity) parameters.get(ENTITY_PROPERTY);
		String entityId = (String) parameters.get(ENTITY_ID_PROPERTY);
		String entityType = (String) parameters.get(ENTITY_TYPE_PROPERTY);
		if (entity == null) {
			if (StringUtils.isBlank(entityId) || StringUtils.isBlank(entityType)) {
				throw new InvalidTaskParameterException("Either Entity should be set or EntityId and EntityType should be set.");
			}
		}
		if (entity != null) {
			if (StringUtils.isNotEmpty(entityId) || StringUtils.isNotEmpty(entityType)) {
				throw new InvalidTaskParameterException("Either entity should be set or entityId and entityType should be set.");
			}
		}
	}

	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> verifiedParameters, Map<String, Object> results) throws Exception {
		final Map<String, Object> relationshipMap = new ConcurrentHashMap<>();

		@SuppressWarnings("unchecked")
		List<Relationship<?>> relationshipList = (List<Relationship<?>>) verifiedParameters.get(RELATIONSHIP_LIST_PROPERTY);
		BaseEntity inputEntity = (BaseEntity) verifiedParameters.get(ENTITY_PROPERTY);
		String entityId = (String) verifiedParameters.get(ENTITY_ID_PROPERTY);
		String entityType = (String) verifiedParameters.get(ENTITY_TYPE_PROPERTY);
		boolean approvedOnly = BooleanUtils.isTrue((Boolean)verifiedParameters.get(APPROVED_ONLY_PROPERTY));

		BaseEntity processEntity;

		if (inputEntity == null) {
			if (StringUtils.isNumeric(entityId)) {
				MM8Response response = mediaManager.readEntity(entityType, Integer.valueOf(entityId), null, null);
				Object responseObject = response.getResponseObject();
				if (responseObject != null && responseObject instanceof BaseEntity) {
					processEntity = (BaseEntity)responseObject;
				} else {
					processEntity = null;
				}
			} else {
				processEntity = null;
			}
		} else {
			processEntity = inputEntity;
		}

		if (processEntity != null) {
			ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap<>();
			dataMap.put(ENTITY_PROPERTY, processEntity);
			dataMap.put(APPROVED_ONLY_PROPERTY, approvedOnly);
			ConcurrentHashMap<Integer, Object> resultMap = new ConcurrentHashMap<>();
			ThreadPoolFactory.executeLoop(relationshipList, this, RelationshipProcessor.class, dataMap, resultMap);
			for (Entry<Integer, Object> entry: resultMap.entrySet()) {
				relationshipMap.put(relationshipList.get(entry.getKey()).toString(), entry.getValue());
			}
		}

		results.put(RELATIONSHIP_MAP_PROPERTY, relationshipMap);
	}

	class RelationshipProcessor extends ThreadPoolFactory.ItemRunnable<Relationship<?>> {
		@Override
		public void run() throws Throwable {
			String key = getItem().toString();
			Object value = getRelatedEntities((BaseEntity)getDataMap().get(ENTITY_PROPERTY), getItem(), (Boolean)getDataMap().get(APPROVED_ONLY_PROPERTY));
			if (key != null && value != null) {
				getResultMap().put(getPosition(), value);
			}
		}
	}

	@JsonIgnore
	private Object getRelatedEntities(BaseEntity entity, final Relationship<?> relationship, final boolean approvedOnly) throws Exception {
		Integer linkCount = WorkflowHelper.getLinkCount(entity, relationship.getRelationshipName());
		// In case of 0 - there is a relationship but no related entities.
		// In case of null - it may be an inverse embedded relationship
		// and we should try tackling it.
		if (new Integer(0).equals(linkCount)) {
			return new ArrayList<>();
		}

		try {
			MM8Response mm8Response = mediaManager.readRelationships(
					entity.getType(), Integer.valueOf(entity.getId()),
					relationship.getRelationshipName(), relationship.getRelationshipEntityType(),
					new HttpQueryFilterParameter(), null, null
			);
			List<BaseEntity> relatedEntityList = WorkflowHelper.getEntityList(BaseEntity.class, mm8Response);

			if (approvedOnly) {
				ConcurrentHashMap<Integer, Object> resultMap = new ConcurrentHashMap<>();
				ThreadPoolFactory.executeLoop(relatedEntityList, this, ApprovedEntityProcessor.class, resultMap);
				relatedEntityList = new ArrayList<BaseEntity>();
				for (Object object: resultMap.values()) {
					if (object instanceof BaseEntity) {
						relatedEntityList.add((BaseEntity)object);
					}
				}
			}
			if (relationship.getRelationship() != null) {
				Map<Object, Object> entityMap = new ConcurrentHashMap<>();
				ConcurrentHashMap<Integer, Object> resultMap = new ConcurrentHashMap<>();
				ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap<>();
				dataMap.put(ENTITY_PROPERTY, entity);
				dataMap.put(APPROVED_ONLY_PROPERTY, approvedOnly);
				dataMap.put(NEXT_RELATIONSHIP_KEY, relationship.getRelationship());
				ThreadPoolFactory.executeLoop(relatedEntityList, this, RelatedEntityProcessor.class, dataMap, resultMap);
				for (Entry<Integer, Object> entry: resultMap.entrySet()) {
					entityMap.put(relatedEntityList.get(entry.getKey()), entry.getValue());
				}
				return entityMap;
			} else {
				return relatedEntityList;
			}
		} catch (Exception e) {
		}
		return new ArrayList<>();
	}

	class ApprovedEntityProcessor extends ThreadPoolFactory.ItemRunnable<BaseEntity> {
		@Override
		public void run() throws Throwable {
			try {
				ApprovedEntityHelper approvedEntityHelper = new ApprovedEntityHelper();
				BaseEntity approvedRelatedEntity = approvedEntityHelper.getApprovedEntity(mediaManager, getItem(), true);
				if (approvedRelatedEntity != null) {
					getResultMap().put(getPosition(), approvedRelatedEntity);
				}
			} catch (Throwable e) {
			}
		}
	}

	class RelatedEntityProcessor extends ThreadPoolFactory.ItemRunnable<BaseEntity> {
		@Override
		public void run() throws Throwable {
			try {
				Object relatedEntities = getRelatedEntities(getItem(), (Relationship<?>)getDataMap().get(NEXT_RELATIONSHIP_KEY), (Boolean)getDataMap().get(APPROVED_ONLY_PROPERTY));
				if (relatedEntities != null) {
					getResultMap().put(getPosition(), relatedEntities);
				}
			} catch (Throwable e) {
			}
		}
	}

}
