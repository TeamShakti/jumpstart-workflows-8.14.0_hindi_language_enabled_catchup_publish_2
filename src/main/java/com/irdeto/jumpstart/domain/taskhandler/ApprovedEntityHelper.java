package com.irdeto.jumpstart.domain.taskhandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.BooleanUtils;

import com.irdeto.domain.operation.MM8Response;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.BaseMetadata;
import com.irdeto.jumpstart.domain.BaseMetadataWithContent;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.mediamanager.MediaManager;

public class ApprovedEntityHelper {
	private static final String ENTITY_ID_KEY = "EntityId";
	private static final String ENTITY_TYPE_KEY = "EntityType";
	private static final String MEDIA_MANAGER_KEY = "MediaManager";
	private static final String APPROVED_ENTITY_MAP_KEY = "ApprovedEntityMap";
	
	public BaseEntity getApprovedEntity(MediaManager mediaManager, BaseEntity entity, Boolean considerLatestVersion) throws Exception {
		if (BooleanUtils.isTrue(considerLatestVersion) && isApproved(entity)) {
			return entity;
		} else if (entity != null) {
			List<Integer> previousVersionList = getPreviousVersionList(entity);
			int entityId = Integer.parseInt(entity.getId());
			String entityType = entity.getType();
			Map<Integer, BaseEntity> approvedEntityMap = new ConcurrentHashMap<>();
			ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap<>();
			dataMap.put(MEDIA_MANAGER_KEY, mediaManager);
			dataMap.put(ENTITY_ID_KEY, entityId);
			dataMap.put(ENTITY_TYPE_KEY, entityType);
			dataMap.put(APPROVED_ENTITY_MAP_KEY, approvedEntityMap);
			
			ThreadPoolFactory.executeLoop(previousVersionList, this, PreviousVersionProcessor.class, dataMap, null);
			
			int latestApprovedVersion = -1;
			BaseEntity latestApprovedEntityVersion = null;
			for (Entry<Integer, BaseEntity> approvedEntityEntry: approvedEntityMap.entrySet()) {
				if (approvedEntityEntry.getKey() > latestApprovedVersion) {
					latestApprovedVersion = approvedEntityEntry.getKey();
					latestApprovedEntityVersion = approvedEntityEntry.getValue();
				}
			}
			return latestApprovedEntityVersion;
		}
		return null;
	}
	
	class PreviousVersionProcessor extends ThreadPoolFactory.ItemRunnable<Integer> {
		@SuppressWarnings("unchecked")
		@Override
		public void run() throws Throwable {
			try {
				MediaManager mediaManager = (MediaManager)getDataMap().get(MEDIA_MANAGER_KEY);
				String entityType = (String)getDataMap().get(ENTITY_TYPE_KEY);
				Integer entityId = (Integer)getDataMap().get(ENTITY_ID_KEY);
				ConcurrentHashMap<Integer, BaseEntity> approvedEntityMap = (ConcurrentHashMap<Integer, BaseEntity>)getDataMap().get(APPROVED_ENTITY_MAP_KEY);
				MM8Response response = mediaManager.readEntity(entityType, entityId, getItem(), null);
				BaseEntity previousEntityVersion = WorkflowHelper.getEntity(BaseEntity.class, response);
				if (isApproved(previousEntityVersion)) {
					approvedEntityMap.put(getItem(), previousEntityVersion);
				}
			} catch (Throwable e) {
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean breakingCondition() {
			ConcurrentHashMap<Integer, BaseEntity> approvedEntityMap = (ConcurrentHashMap<Integer, BaseEntity>)getDataMap().get(APPROVED_ENTITY_MAP_KEY);
			return !approvedEntityMap.isEmpty();
		}
	}

	private static List<Integer> getPreviousVersionList(BaseEntity entity){
		List<Object> versions = ((Base)entity).getVersions();
		List<Integer> versionList = new ArrayList<Integer>(); 
		for (Object object: versions) {
			if (object instanceof Integer) {
				versionList.add((Integer)object);
			}
		}
		Collections.sort(versionList, Collections.reverseOrder());
		if (versionList.size() > 0) {
			versionList.remove(0);
		}
		return versionList;
	}
	
	private boolean isApproved(BaseEntity entity) {
		if (entity == null) {
			return false;
		}
		if (entity instanceof BaseMetadataWithContent) {
			BaseMetadataWithContent baseMetadataWithContent = (BaseMetadataWithContent)entity;
			if (BooleanUtils.isTrue(baseMetadataWithContent.getContentApproved())
					&& BooleanUtils.isTrue(baseMetadataWithContent.getMetadataApproved())) {
				return true;
			}
		} else if (entity instanceof BaseMetadata) {
			BaseMetadata baseMetadata = (BaseMetadata)entity;
			if (BooleanUtils.isTrue(baseMetadata.getMetadataApproved())) {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}
	
}
