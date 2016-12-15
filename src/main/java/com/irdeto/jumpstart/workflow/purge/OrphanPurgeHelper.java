package com.irdeto.jumpstart.workflow.purge;

import java.util.ArrayList;
import java.util.List;

import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class OrphanPurgeHelper extends WorkflowHelper {
	public static List<String> getOrphanEntityTypeList() {
		List<String> orphanEntityTypeList = new ArrayList<>();
		orphanEntityTypeList.add(ENTITY_TYPE_IMAGE_CONTENT);
		orphanEntityTypeList.add(ENTITY_TYPE_VIDEO_CONTENT);
		orphanEntityTypeList.add(ENTITY_TYPE_PERSON);
		return orphanEntityTypeList;
	}
	
//	public static List<BaseEntity> processEntitiesToPurge(ProcessContext kcontext) {
//		List<BaseEntity> purgeEntityList = new ArrayList<>();
//		MM8Response mm8Response = (MM8Response)kcontext.getVariable(MM8_RESPONSE_KEY);
//		List<BaseEntity> baseEntityList = OrphanPurgeHelper.getEntityList(BaseEntity.class, mm8Response);
//		for (BaseEntity entity: baseEntityList) {
//			if (isReadyForPurge(entity)) {
//				purgeEntityList.add(entity);
//			}
//		}
//		
//		return purgeEntityList;
//	}

//	private static boolean isReadyForPurge(BaseEntity entity) {
//		try {
//			String purgeOrphanDuration = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.PURGE_ORPHAN_DURATION_KEY);
//			if (StringUtils.isNumeric(purgeOrphanDuration)) {
//				return PurgeHelper.isReadyForPurge(entity, Integer.valueOf(purgeOrphanDuration), PurgeWrapper.ACTION_DE_ORPHAN);
//			} else {
//				return false;
//			}
//		} catch (PropertyException e) {
//			return false;
//		}
//	}
}
