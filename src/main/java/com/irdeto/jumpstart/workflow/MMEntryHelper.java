package com.irdeto.jumpstart.workflow;

import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.joda.time.DateTime;
import org.kie.api.runtime.process.ProcessContext;

import com.irdeto.domain.HTCommand.Action;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.BaseMetadata;
import com.irdeto.jumpstart.workflow.qa.ComparisonHelper;
import com.irdeto.manager.task.BeanUtil;

public class MMEntryHelper extends WorkflowHelper {

	private static final String ACTION_CREATE = "Insert";
	private static final String ACTION_UPDATE = "Update";
	private static final String ACTION_DELETE = "Delete";

	private static final String ACTION_RELATIONSHIP_ADD = "linkAdd";
	private static final String ACTION_RELATIONSHIP_DELETE = "linkDelete";


	private static final String INBOUND_ENTITY_TYPE_KEY = "EntityType";
	private static final String ENTITY_KEY = "entity";
	private static final String LAST_VERSION_ENTITY ="lastVersionEntity";
	private static final String APPROVED_ENTITY_KEY = "approvedEntity";
	private static final String ACTION_KEY = "Action";
	private static final String REQUESTOR_KEY = "Requestor";
	private static final String ENTITY_ID_KEY = "EntityID";
	private static final String ENTITY_TYPE_KEY = "EntityType";
	private static final String SOURCE_ENTITY_ID_KEY = "SourceEntityID";
	private static final String SOURCE_ENTITY_TYPE_KEY = "SourceEntityType";
	private static final String TARGET_ENTITY_ID_KEY = "TargetEntityID";
	private static final String TARGET_ENTITY_TYPE_KEY = "TargetEntityType";

	public static void entrySetPendingApproval(ProcessContext kcontext) {
		Object entity = kcontext.getVariable(ENTITY_KEY);

		if (entity instanceof BaseMetadata) {
			((BaseMetadata) entity).setMetadataApproved(false);

			//MMEntry - setting LastModifiedDateTime to 'now'.
			((BaseMetadata) entity).setLastModifiedDateTime(DateTime.now());
		}

	}

	public static boolean needToSetMetadataApprovedToFalse(ProcessContext kcontext) {
		Object entity = kcontext.getVariable(ENTITY_KEY);
		Object approvedEntity = kcontext.getVariable(APPROVED_ENTITY_KEY);

		if (entity == null){
			return false;
		} else if (!ComparisonHelper.areSameObjects(entity, approvedEntity) && (entity instanceof BaseMetadata) &&
				BooleanUtils.isTrue(((BaseMetadata)entity).getMetadataApproved())) {
			return  true;
		} else {
			return false;
		}
	}

	public static boolean isEternal(ProcessContext kcontext) {
		String entityType = (String)kcontext.getVariable(INBOUND_ENTITY_TYPE_KEY);
		return isIn(
				entityType,
				ENTITY_TYPE_PROGRAM,
				ENTITY_TYPE_SERIES,
				ENTITY_TYPE_BRAND,
				ENTITY_TYPE_CHANNEL,
				ENTITY_TYPE_EVENT,
				ENTITY_TYPE_OFFER,
				ENTITY_TYPE_TERM,
				ENTITY_TYPE_TVOD_COLLECTION,
				ENTITY_TYPE_SUBSCRIPTION_PACKAGE,
				ENTITY_TYPE_GENRE
		);
	}

	public static boolean isEPG(ProcessContext kcontext) {
		String entityType = (String)kcontext.getVariable(INBOUND_ENTITY_TYPE_KEY);
		return isIn(
				entityType,
				ENTITY_TYPE_SCHEDULE_SLOT,
				ENTITY_TYPE_CHANNEL_DAY
		);
	}

	public static boolean isRelationship(ProcessContext kcontext) {
		String action = (String)kcontext.getVariable(ACTION_KEY);
		return ACTION_RELATIONSHIP_ADD.equals(action)
				|| ACTION_RELATIONSHIP_DELETE.equals(action);
	}

	public static boolean isDelete(ProcessContext kcontext) {
		String action = (String)kcontext.getVariable(ACTION_KEY);
		return ACTION_DELETE.equals(action);
	}

	/**
	 * This method is added to decide which is  the master of data either data from Mediamanager or Metadata XML
	 *
	 * @param kcontext
	 */
	public static void setMaster(ProcessContext kcontext){
		boolean masterUpdated = false;
		Object currentVersionEntity = kcontext.getVariable(ENTITY_KEY);
		Object lastVersionEntity = kcontext.getVariable(LAST_VERSION_ENTITY);
		if((currentVersionEntity != null && currentVersionEntity instanceof BaseMetadata) && (lastVersionEntity != null && lastVersionEntity instanceof BaseMetadata)){
		BaseMetadata.DataMaster currentVerMaster = ((BaseMetadata)currentVersionEntity).getDataMaster();
		BaseMetadata.DataMaster lastVerMaster = ((BaseMetadata)lastVersionEntity).getDataMaster();
		  if(( !BaseMetadata.DataMaster.MEDIA_MANAGER.equals(currentVerMaster)) && !BaseMetadata.DataMaster.MEDIA_MANAGER.equals(lastVerMaster))
			{
				 ((BaseMetadata)currentVersionEntity).setDataMaster(BaseMetadata.DataMaster.MEDIA_MANAGER);
				 masterUpdated = true;
			}

		}
		kcontext.setVariable("masterUpdated",masterUpdated);
	}

	/**
	 * This method is used to get the last version from Media manager
	 *
	 * @return lastVersion
	 */
	public static void getLastVersion(ProcessContext kcontext) {
		Object entity = kcontext.getVariable(ENTITY_KEY);
		List<Object> versionList = null;
		if(entity instanceof BaseEntity){
			versionList = ((BaseEntity)entity).getVersions();
		}

		Integer maxVersion = null;
		Integer lastVersion = null;
		for (Object version : versionList) {
			if (version instanceof Integer) {
				if (maxVersion == null || (Integer) version > maxVersion) {
					maxVersion = (Integer) version;
				}
			}
		}
		if (null != maxVersion)
		{
			lastVersion = maxVersion - 1;

			if (!versionList.contains(lastVersion)){
				lastVersion = null;
			}
		}
		kcontext.setVariable("lastVersion",lastVersion);
	}

	public static void prepareVariables(ProcessContext kcontext) {
		String requestor = (String)kcontext.getVariable(REQUESTOR_KEY);
		String entityId = (String)kcontext.getVariable(ENTITY_ID_KEY);
		String entityType = (String)kcontext.getVariable(ENTITY_TYPE_KEY);
		String actionValue = (String)kcontext.getVariable(ACTION_KEY);
		Action action = Action.fromValue(actionValue);

		switch (action) {
		case LINK_ADD:
		case LINK_DELETE:
			String sourceEntityId = String.valueOf(kcontext.getVariable(SOURCE_ENTITY_ID_KEY));
			String sourceEntityType = String.valueOf(kcontext.getVariable(SOURCE_ENTITY_TYPE_KEY));
			String targetEntityId = String.valueOf(kcontext.getVariable(TARGET_ENTITY_ID_KEY));
			String targetEntityType = String.valueOf(kcontext.getVariable(TARGET_ENTITY_TYPE_KEY));

			BeanUtil.loggerManager.debug(kcontext, "\nExecuting MM Entry Workflow with Requestor({}), Action ({}) for relationship from SourceEntityType ({}), SourceEntityID ({}) to TargetEntityType ({}), TargetEntityID ({})", requestor, actionValue, sourceEntityType, sourceEntityId, targetEntityType, targetEntityId);
			if (WorkflowHelper.ENTITY_TYPE_GENRE.equals(sourceEntityType)) {
				kcontext.setVariable(ENTITY_ID_KEY, targetEntityId);
				kcontext.setVariable(ENTITY_TYPE_KEY, targetEntityType);
			} else {
				kcontext.setVariable(ENTITY_ID_KEY, sourceEntityId);
				kcontext.setVariable(ENTITY_TYPE_KEY, sourceEntityType);
			}
			break;
		case DELETE:
		case UPDATE:
		case INSERT:
		default:
			BeanUtil.loggerManager.debug(kcontext, "\nExecuting MM Entry Workflow with Requestor({}), Action ({}), EntityType ({}), EntityID ({})", requestor, actionValue, entityType, entityId);
			break;
		}
	}

}
