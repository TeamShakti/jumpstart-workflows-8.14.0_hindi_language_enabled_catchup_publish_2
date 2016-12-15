package com.irdeto.jumpstart.workflow.ingest;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.domain.mediamanager.command.MaintainRelationshipsCommand;
import com.irdeto.domain.mediamanager.command.MaintainRelationshipsResult;
import com.irdeto.domain.mediamanager.message.bulk.EntityData;
import com.irdeto.domain.mediamanager.message.response.bulk.Item;
import com.irdeto.domain.mediamanager.message.response.bulk.Response;
import com.irdeto.domain.operation.MM8Response;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.BaseMetadata;
import com.irdeto.jumpstart.domain.BaseMetadataWithContent;
import com.irdeto.jumpstart.domain.Content;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.SubtitleContent;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.ingest.AbstractEntityIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapperWithGenreAndRating;
import com.irdeto.jumpstart.domain.taskhandler.EntityIdTypeWrapper;
import com.irdeto.jumpstart.domain.taskhandler.QueryFilterWrapper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.jbpm.knowledge.ClassManager;
import com.irdeto.manager.jbpm.knowledge.ClassManagerImpl;
import com.irdeto.manager.mediamanager.MediaManagerBulkEntityInterface;
import com.irdeto.manager.task.BeanUtil;


public class DataIngestHelper extends WorkflowHelper {

	private static final String METHOD_NAME_GET_IMAGE_CONTENT = "getImageContent";
	private static final String METHOD_NAME_GET_VIDEO_CONTENT = "getVideoContent";
	private static final String METHOD_NAME_GET_SUBTITLE_CONTENT = "getSubtitleContent";
	private static final String SOURCE_URL = "sourceUrl";
	private static final String INGEST_WRAPPER_KEY_LIST_KEY = "ingestWrapperKeyList";
	private static final String QUERY_FILTER_PARAM_KEY = "queryFilterParameter";
	private static final String INGEST_WRAPPER_LIST_KEY = "ingestWrapperList";
	private static final String WRAPPER_KEY = "wrapper";
	private static final String INPUT_CONTENT_KEY = "inputContent";
	private static final String SCHEMA_STRING_LIST_KEY = "schemaStringList";
	private static final String CONTENT_TYPE_KEY = "contentType";
	private static final String CONTENT_LIST_KEY = "contentList";
	private static final String BULK_READ_RESULT_MAP_KEY = "bulkReadResultMap";
	private static final String QUERY_FILTER_WRAPPER_LIST_KEY = "queryFilterWrapperList";
	private static final String ENTITY_ID_TYPE_WRAPPER_LIST_KEY = "entityIdTypeWrapperList";
	private static final String APPROVED_ENTITY_MAP_KEY = "approvedEntityMap";
	private static final String UPDATE_ENTITY_DATA_MAP_KEY = "updateEntityDataMap";
	private static final String CREATE_ENTITY_DATA_MAP_KEY = "createEntityDataMap";
	private static final String MAINTAIN_RELATIONSHIPS_LIST_KEY = "maintainRelationshipsCommandsList";
	private static final String RATING_MAP_MAP_KEY = "ratingMapMap";
	private static final String RATING_MAINTAINED_ENTITY_LIST_KEY = "ratingMaintainedEntityList";
	private static final String RELATIONSHIP_MAINTAINED_ENTITY_LIST_KEY = "maintainRelationshipsResultObject";
	private static final String MODIFIED_ENTITY_LIST_KEY = "modifiedEntityList";
	private static final String VALIDATION_MESSAGE = "validationMessage";
	private static final String VALIDATION = "validation";

	private static final String BULK_CREATE_REQUEST_KEY = "bulkCreateRequest";
	private static final String BULK_UPDATE_REQUEST_KEY = "bulkUpdateRequest";

	private static final String BULK_CREATE_RESPONSE_KEY = "bulkCreateResponse";
	private static final String BULK_UPDATE_RESPONSE_KEY = "bulkUpdateResponse";

	private static final Logger logger = LoggerFactory.getLogger(DataIngestHelper.class);
	private static final String FILE_NAME_KEY = "fileName";
	private static final String FILE_PATH_KEY = "filePath";

	private static final ConcurrentHashMap<Class<?>, ConcurrentHashMap<String, Method>> reflectionCache = new ConcurrentHashMap<>();

	public static <T> void copyAllExceptIdAndList(T from, T to) {
		copyAllExceptIdAndList(from, to, true);
	}

	public static <T> void copyAllExceptIdAndList(T from, T to, boolean includeNulls) {
		for (Method method: findMethods(to.getClass()).values()) {
			String methodName = method.getName();
			if (methodName.startsWith("set")
					&& !"setId".equals(methodName)) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (parameterTypes == null || parameterTypes.length != 1) {
					continue;
				}
				if (!List.class.equals(parameterTypes[0])) {
					String getterMethodName = "get" + methodName.substring(3);
					Method getterMethod = null;
					try {
						getterMethod = findMethod(from.getClass(), getterMethodName);
						Object value = getterMethod.invoke(from);
						if (includeNulls || value != null) {
							method.invoke(to, value);
						}
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						logger.error("Metadata copy error: ", e);
					}
				}
			}
		}
	}

	protected static <T> void copyIfNull(T from, T to) {
		for (Method method: findMethods(to.getClass()).values()) {
			String methodName = method.getName();
			if (methodName.startsWith("set")) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (parameterTypes == null || parameterTypes.length != 1) {
					continue;
				}
				String getterMethodName = "get" + methodName.substring(3);
				Method getterMethod = null;
				try {
					getterMethod = findMethod(from.getClass(), getterMethodName);
					Object valueFrom = getterMethod.invoke(from);
					Object valueTo = getterMethod.invoke(to);
					if (valueTo == null || (valueTo instanceof List && ((List<?>) valueTo).isEmpty()))
						method.invoke(to, valueFrom);
				} catch (NoSuchMethodException
						| SecurityException
						| IllegalAccessException
						| IllegalArgumentException
						| InvocationTargetException e) {
					logger.error("Metadata copy error: ", e);
				}
			}
		}
	}

	public static <T extends VideoContent> List<T> mergeVideoContentList(List<T> from, List<T> to) {
		List<T> mergedList = new ArrayList<T>();
		if (from != null) {
			for (T fromContent : from) {
				boolean found = false;
				if (to != null) {
					for (T toContent : to) {
						if (fromContent.getUriId().equals(toContent.getUriId())) {
							copyAllExceptIdAndList(fromContent, toContent);
							if (fromContent.getSourceUrl() != null)
								toContent.setSourceUrl(fromContent.getSourceUrl());
							if (fromContent.getLanguage() != null)
								toContent.setLanguage(fromContent.getLanguage());
							if (fromContent.getSubtitleLanguage() != null)
								toContent.setSubtitleLanguage(fromContent.getSubtitleLanguage());
							if (fromContent.getDubbedLanguage() != null)
								toContent.setDubbedLanguage(fromContent.getDubbedLanguage());
							if (fromContent.getDuration() != null)
								toContent.setDuration(fromContent.getDuration());
							if (fromContent.getScreenFormat() != null)
								toContent.setScreenFormat(fromContent.getScreenFormat());
							if (fromContent.getCheckSum() != null)
								toContent.setCheckSum(fromContent.getCheckSum());
							if (fromContent.getFileSize() != null)
								toContent.setFileSize(fromContent.getFileSize());
							if (fromContent.getContentType() != null)
								toContent.setContentType(fromContent.getContentType());

							// merge Subtitles
							List<SubtitleContent> fromSubtitleContent = fromContent.getSubtitleContent();
							List<SubtitleContent> toSubtitleContent = toContent.getSubtitleContent();

							toContent.setSubtitleContent(mergeSubtitleContentList(fromSubtitleContent, toSubtitleContent));

							mergedList.add(toContent);

							found = true;
							break;
						}
					}
				}
				if (!found) {
					mergedList.add(fromContent);
				}
			}
		}
		if (to != null) {
			for (T toContent : to) {
				boolean found = false;
				for (T mergedListContent : mergedList) {
					if (mergedListContent.getUriId().equals(toContent.getUriId())) {
						found = true;
						break;
					}
				}
				if (!found) {
					mergedList.add(toContent);
				}
			}
		}
		return mergedList;
	}

	/**
	 * This method merges the Subtitles available in MediaManger to the re ingested XML for the same program video content
	 *
	 * @param from XML
	 * @param to   MM
	 * @return merged SubtitleContent
	 */
	public static <T extends SubtitleContent> List<T> mergeSubtitleContentList(List<T> from, List<T> to) {
		List<T> mergedList = new ArrayList<T>();
		if (from != null) {
			for (T fromContent : from) {
				boolean found = false;
				if (to != null) {
					for (T toContent : to) {
						if (fromContent.getUriId().equals(toContent.getUriId())) {
							copyAllExceptIdAndList(fromContent, toContent);
							if (fromContent.getSourceUrl() != null)
								toContent.setSourceUrl(fromContent.getSourceUrl());
							if (fromContent.getLanguage() != null)
								toContent.setLanguage(fromContent.getLanguage());
							if (fromContent.getCheckSum() != null)
								toContent.setCheckSum(fromContent.getCheckSum());
							if (fromContent.getFileSize() != null)
								toContent.setFileSize(fromContent.getFileSize());

							mergedList.add(toContent);
							found = true;
							break;
						}
					}
				}
				if (!found) {
					mergedList.add(fromContent);
				}
			}
		}
		if (to != null) {
			for (T toContent : to) {
				boolean found = false;
				for (T mergedListContent : mergedList) {
					if (mergedListContent.getUriId().equals(toContent.getUriId())) {
						found = true;
						break;
					}
				}
				if (!found) {
					mergedList.add(toContent);
				}
			}
		}
		return mergedList;
	}

	public static <T extends Content> List<T> mergeContentList(List<T> from, List<T> to) {
		List<T> mergedList = new ArrayList<T>();

		if (from != null) {
			for (T fromContent : from) {
				boolean found = false;
				if (to != null) {
					for (T toContent : to) {
						if (fromContent.getUriId().equals(toContent.getUriId())) {
							copyAllExceptIdAndList(fromContent, toContent);
							mergedList.add(toContent);
							found = true;
							break;
						}
					}
				}
				if (!found) {
					mergedList.add(fromContent);
				}
			}
		}

		if (to != null) {
			for (T toContent : to) {
				boolean found = false;
				for (T mergedListContent : mergedList) {
					if (mergedListContent.getUriId().equals(toContent.getUriId())) {
						found = true;
						break;
					}
				}
				if (!found) {
					mergedList.add(toContent);
				}
			}
		}
		return mergedList;
	}

	public static boolean schemaValidationRequired(ProcessContext kcontext) {
		String xmlInput = (String) kcontext.getVariable(INPUT_CONTENT_KEY);
		String fileName = (String) kcontext.getVariable(FILE_NAME_KEY);
		String filePath = (String) kcontext.getVariable(FILE_PATH_KEY);
		return VodIngestFactory.getInstance().getMapper(xmlInput, fileName, filePath).isSchemaValidationEnabled();
	}

	public static void entryXmlValidation(ProcessContext kcontext) {
		String xmlInput = (String) kcontext.getVariable(INPUT_CONTENT_KEY);
		String fileName = (String) kcontext.getVariable(FILE_NAME_KEY);
		String filePath = (String) kcontext.getVariable(FILE_PATH_KEY);
		String[] schemaFileNameArray = VodIngestFactory.getInstance().getMapper(xmlInput, fileName, filePath).getSchemaFileNames();
		ClassManager classManager = BeanUtil.getBean(ClassManagerImpl.class);
		kcontext.setVariable(SCHEMA_STRING_LIST_KEY, entryXmlValidation(schemaFileNameArray, classManager.getClassLoader()));
		kcontext.setVariable(INPUT_CONTENT_KEY, IngestFactory.stripUTF8ByteOrderMark(xmlInput));
	}

	public static void processValidationMessage(ProcessContext kcontext) {
		String validationMessage = (String) kcontext.getVariable(VALIDATION_MESSAGE);
		boolean validation = (boolean) kcontext.getVariable(VALIDATION);
		// Explicit passing the validation error for lang setting for 01 on Term 'suggested price'
		if (validation) {
			return;
		} else if (validationMessage.contains("is not a valid value of union type '#AnonType_lang'")) {
			kcontext.setVariable(VALIDATION, true);
		}
	}

	public static boolean hasMapper(ProcessContext kcontext) {
		String xmlInput = (String) kcontext.getVariable(INPUT_CONTENT_KEY);
		String fileName = (String) kcontext.getVariable(FILE_NAME_KEY);
		String filePath = (String) kcontext.getVariable(FILE_PATH_KEY);
		return VodIngestFactory.getInstance().getMapper(xmlInput, fileName, filePath) != null;
	}

	protected static List<String> entryXmlValidation(String[] schemaFileNameList, ClassLoader classLoader) {
		List<String> schemaStringList = new ArrayList<String>();
		for (String schemaFileName : schemaFileNameList) {
			try {
				InputStream inputStream = classLoader.getResourceAsStream("schema/" + schemaFileName);
				try (Scanner scanner = new Scanner(inputStream, "UTF-8")) {
					String schemaString = scanner.useDelimiter("\\A").next();
					schemaStringList.add(IngestFactory.stripUTF8ByteOrderMark(schemaString));
				}
			} catch (Throwable e) {
				logger.error("Error reading schema resource {}.", schemaFileName, e);
			}
		}
		return schemaStringList;
	}

	public static void entryContentLoop(ProcessContext kcontext) {
		@SuppressWarnings("unchecked")
		Map<String, String> contentListForIteration = (Map<String, String>) kcontext.getVariable(CONTENT_LIST_KEY);
		Entry<String, String> nextEntry = getNextEntry(contentListForIteration);
		kcontext.setVariable(CONTENT_TYPE_KEY, nextEntry.getValue());
		kcontext.setVariable(QUERY_FILTER_PARAM_KEY, getQueryFilterParameter(SOURCE_URL, nextEntry.getKey(), false));
	}

	public static void mapMetadata(ProcessContext kcontext) {
		String xmlInput = (String) kcontext.getVariable(INPUT_CONTENT_KEY);
		String fileName = (String) kcontext.getVariable(FILE_NAME_KEY);
		String filePath = (String) kcontext.getVariable(FILE_PATH_KEY);
		List<EntityIngestWrapper<?>> ingestWrapperList = mapMetadata(xmlInput, fileName, filePath);
		kcontext.setVariable(INGEST_WRAPPER_LIST_KEY, ingestWrapperList);
	}

	protected static List<EntityIngestWrapper<?>> mapMetadata(String xmlInput, String fileName, String filePath) {
		List<EntityIngestWrapper<?>> ingestWrapperList = new ArrayList<>();
		try {
			VodIngestMapper vodIngestMapper = VodIngestFactory.getInstance()
					.getMapper(xmlInput, fileName, filePath);
			if (vodIngestMapper != null) {
				ingestWrapperList.addAll(vodIngestMapper.findEntities());
			} else {
				logger.error("Unable to determine metadata ingest mechanism from input file.");
			}
		} catch (Exception e) {
			logger.error("An error occurred ingesting metadata.", e);
		}
		return ingestWrapperList;
	}

	public static void setupIngestWrapperKeyList(ProcessContext kcontext) {
		@SuppressWarnings("unchecked")
		List<AbstractEntityIngestWrapper<?>> ingestWrapperList = (List<AbstractEntityIngestWrapper<?>>) kcontext.getVariable(INGEST_WRAPPER_LIST_KEY);
		List<Integer> ingestWrapperKeyList = new ArrayList<>();

		for (int i = 0; i < ingestWrapperList.size(); i++) {
			ingestWrapperKeyList.add(new Integer(i));
		}
		kcontext.setVariable(INGEST_WRAPPER_KEY_LIST_KEY, ingestWrapperKeyList);
	}

	public static void setupIngestWrapperEternalWFKeyList(ProcessContext kcontext) {
		@SuppressWarnings("unchecked")
		List<AbstractEntityIngestWrapper<?>> ingestWrapperList = (List<AbstractEntityIngestWrapper<?>>) kcontext.getVariable(INGEST_WRAPPER_LIST_KEY);
		List<Integer> ingestWrapperKeyList = new ArrayList<>();

		for (int i = 0; i < ingestWrapperList.size(); i++) {
			if (ingestWrapperList.get(i).getEntity() instanceof Base) {
				ingestWrapperKeyList.add(new Integer(i));
			}
		}
		kcontext.setVariable(INGEST_WRAPPER_KEY_LIST_KEY, ingestWrapperKeyList);
	}

	public static void setupForIteration(ProcessContext kcontext) {
		AbstractEntityIngestWrapper<?> wrapper = (AbstractEntityIngestWrapper<?>) kcontext.getVariable(WRAPPER_KEY);
		kcontext.setVariable(CONTENT_LIST_KEY, setupForIteration(wrapper.getEntity()));
	}

	protected static Map<String, String> setupForIteration(BaseEntity entity) {
		Map<String, String> contentMap = new HashMap<>();

		for (String methodName : new String[]{METHOD_NAME_GET_IMAGE_CONTENT, METHOD_NAME_GET_VIDEO_CONTENT}) {
			try {
				Method getContentMethod = entity.getClass().getMethod(methodName);
				if (getContentMethod != null) {
					@SuppressWarnings("unchecked")
					List<? extends Content> contentList = (List<? extends Content>) getContentMethod.invoke(entity);
					for (Content content : contentList) {
						if (content.getId() == null && isNotBlank(content.getSourceUrl())) {
							contentMap.put(content.getSourceUrl(), WorkflowHelper.getEntityType(content));
						}
					}
				}
			} catch (NoSuchMethodException | SecurityException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
			}
		}
		return contentMap;
	}

	public static void mergeContentToEntity(ProcessContext kcontext) {
		MM8Response mm8Response = (MM8Response) kcontext.getVariable(MM8_RESPONSE_KEY);
		List<Content> mmContentList = getEntityList(Content.class, mm8Response);
		AbstractEntityIngestWrapper<?> wrapper = (AbstractEntityIngestWrapper<?>) kcontext.getVariable(WRAPPER_KEY);
		BaseEntity mergedEntity = wrapper.getEntity();
		mergeContentToEntity(mmContentList, mergedEntity);
	}

	protected static void mergeContentToEntity(List<Content> mmContentList, BaseEntity mergedEntity) {
		for (Content mmContent : mmContentList) {
			try {
				Method getContentMethod = null;
				if (mmContent instanceof VideoContent) {
					getContentMethod = findMethod(mergedEntity.getClass(), METHOD_NAME_GET_VIDEO_CONTENT);
				} else if (mmContent instanceof ImageContent) {
					getContentMethod = findMethod(mergedEntity.getClass(), METHOD_NAME_GET_IMAGE_CONTENT);
				} else if (mmContent instanceof SubtitleContent) {
					getContentMethod = findMethod(mergedEntity.getClass(), METHOD_NAME_GET_SUBTITLE_CONTENT);
				}

				if (getContentMethod != null) {
					@SuppressWarnings("unchecked")
					List<? extends Content> contentListFromMetadata = (List<? extends Content>) getContentMethod.invoke(mergedEntity);
					for (Content contentFromMetadata : contentListFromMetadata) {
						if (isNotBlank(contentFromMetadata.getSourceUrl())) {
							if (StringUtils.equalsIgnoreCase(contentFromMetadata.getSourceUrl(), mmContent.getSourceUrl())) {
								if (mergedEntity instanceof BaseMetadataWithContent) {
									((BaseMetadataWithContent) mergedEntity).setContentApproved(false);
									((BaseMetadataWithContent) mergedEntity).setLastModifiedDateTime(DateTime.now());
								}
								copyIfNull(mmContent, contentFromMetadata);
								contentFromMetadata.setId(mmContent.getId());
								break;
							}
						}
					}
				}
			} catch (NoSuchMethodException | SecurityException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void setupBulkReadEntities(ProcessContext kcontext) {
		List<EntityIngestWrapper<?>> ingestWrapperList = (List<EntityIngestWrapper<?>>) kcontext.getVariable(INGEST_WRAPPER_LIST_KEY);
		List<QueryFilterWrapper> queryFilterWrapperList = new ArrayList<>();
		for (EntityIngestWrapper<?> ingestWrapper : ingestWrapperList) {
			QueryFilterWrapper queryFilterWrapper = new QueryFilterWrapper();
			queryFilterWrapper.setEntityType(ingestWrapper.getEntityType());
			queryFilterWrapper.setQueryFilterParameter(ingestWrapper.getMatchingQueryFilter());
			queryFilterWrapperList.add(queryFilterWrapper);
		}

		kcontext.setVariable(QUERY_FILTER_WRAPPER_LIST_KEY, queryFilterWrapperList);
	}

	@SuppressWarnings("unchecked")
	public static void processBulkReadEntities(ProcessContext kcontext) {
		Map<Integer, List<BaseEntity>> resultMap = (Map<Integer, List<BaseEntity>>) kcontext.getVariable(BULK_READ_RESULT_MAP_KEY);
		List<EntityIngestWrapper<?>> ingestWrapperList = (List<EntityIngestWrapper<?>>) kcontext.getVariable(INGEST_WRAPPER_LIST_KEY);
		for (Entry<Integer, List<BaseEntity>> entry : resultMap.entrySet()) {
			EntityIngestWrapper<? extends BaseEntity> ingestWrapper = ingestWrapperList.get(entry.getKey());
			List<BaseEntity> baseEntityList = entry.getValue();
			if (baseEntityList != null && !baseEntityList.isEmpty()) {
				ingestWrapper.setStoredBaseEntity(baseEntityList.get(0));
			}
		}
		kcontext.setVariable(QUERY_FILTER_WRAPPER_LIST_KEY, null);
		kcontext.setVariable(BULK_READ_RESULT_MAP_KEY, null);
	}

	@SuppressWarnings("unchecked")
	public static void setupReadLatestApproved(ProcessContext kcontext) {
		List<EntityIngestWrapper<?>> ingestWrapperList = (List<EntityIngestWrapper<?>>) kcontext.getVariable(INGEST_WRAPPER_LIST_KEY);
		List<EntityIdTypeWrapper> entityIdTypeWrapperList = new ArrayList<>();
		for (EntityIngestWrapper<?> ingestWrapper : ingestWrapperList) {
			if (ingestWrapper.getStoredEntity() != null
					&& ingestWrapper.getStoredEntity() instanceof BaseMetadata) {
				EntityIdTypeWrapper entityIdTypeWrapper = new EntityIdTypeWrapper();
				entityIdTypeWrapper.setEntityId(ingestWrapper.getStoredEntity().getId());
				entityIdTypeWrapper.setEntityType(ingestWrapper.getStoredEntity().getType());
				entityIdTypeWrapperList.add(entityIdTypeWrapper);
			}
		}
		kcontext.setVariable(ENTITY_ID_TYPE_WRAPPER_LIST_KEY, entityIdTypeWrapperList);
	}

	@SuppressWarnings("unchecked")
	public static void processReadLatestApproved(ProcessContext kcontext) {
		List<EntityIngestWrapper<?>> ingestWrapperList = (List<EntityIngestWrapper<?>>) kcontext.getVariable(INGEST_WRAPPER_LIST_KEY);
		Map<Integer, BaseEntity> approvedEntityMap = (Map<Integer, BaseEntity>) kcontext.getVariable(APPROVED_ENTITY_MAP_KEY);
		List<EntityIdTypeWrapper> entityIdTypeWrapperList = (List<EntityIdTypeWrapper>) kcontext.getVariable(ENTITY_ID_TYPE_WRAPPER_LIST_KEY);
		for (Entry<Integer, BaseEntity> approvedEntityEntry : approvedEntityMap.entrySet()) {
			EntityIdTypeWrapper entityIdTypeWrapper = entityIdTypeWrapperList.get(approvedEntityEntry.getKey());
			BaseEntity approvedEntity = approvedEntityEntry.getValue();
			for (EntityIngestWrapper<?> ingestWrapper : ingestWrapperList) {
				if (ingestWrapper.getStoredEntity() != null && ingestWrapper.getStoredEntity().getId().equals(entityIdTypeWrapper.getEntityId())
						&& ingestWrapper.getStoredEntity().getType().equals(entityIdTypeWrapper.getEntityType())) {
					ingestWrapper.setApprovedBaseEntity(approvedEntity);
					break;
				}
			}
		}
		kcontext.setVariable(APPROVED_ENTITY_MAP_KEY, null);
		kcontext.setVariable(ENTITY_ID_TYPE_WRAPPER_LIST_KEY, null);
	}

	public static void prepareBulkRequest(ProcessContext kcontext) {
		List<AbstractEntityIngestWrapper> ingestWrapperList;
		ingestWrapperList = (List<AbstractEntityIngestWrapper>) kcontext
				.getVariable(INGEST_WRAPPER_LIST_KEY);

		Map<String, EntityData> insertEntitiesData = new HashMap<>();
		Map<String, EntityData> updateEntitiesData = new HashMap<>();

		for (EntityIngestWrapper wrapper : ingestWrapperList) {
			if (wrapper.getEntity() == null) {
				logger.debug("Entity of the wrapper with URI ID {} is null. Skipping.", wrapper.getUriId());
				continue;
			}

			if (wrapper.needUpdate()) {
				updateEntitiesData.put(
						wrapper.getUriId(),
						new EntityData(
								wrapper.getEntityType(),
								Integer.valueOf(wrapper.getEntity().getId()),
								wrapper.getEntity()
						)
				);
			} else {
				insertEntitiesData.put(
						wrapper.getUriId(),
						new EntityData(
								wrapper.getEntityType(),
								wrapper.getEntity()
						)
				);
			}
		}
		kcontext.setVariable(BULK_CREATE_REQUEST_KEY, insertEntitiesData);
		kcontext.setVariable(BULK_UPDATE_REQUEST_KEY, updateEntitiesData);
	}

	public static void processBulkResponse(
			Response bulkResponse,
			MediaManagerBulkEntityInterface.Operation operation,
			ProcessContext kcontext
	) {
		List<AbstractEntityIngestWrapper> ingestWrapperList;
		ingestWrapperList = (List<AbstractEntityIngestWrapper>) kcontext
				.getVariable(INGEST_WRAPPER_LIST_KEY);

		for (EntityIngestWrapper wrapper : ingestWrapperList) {
			Item bulkResponseItem = bulkResponse.get(wrapper.getUriId());

			if (bulkResponseItem == null) {
				logger.warn(
						"No {} bulk response item for wrapper {}. Skipping.",
						operation.getOperation(),
						wrapper.getUriId()
				);
				continue;
			}

			switch (operation) {
				case CREATE:
				case UPSERT:
					Map<String, Object> bulkItemData = bulkResponseItem.getData();
					String id;
					if (
							bulkItemData != null
							&& !bulkItemData.isEmpty()
							&& bulkItemData.get("id") != null
					) {
						id = String.valueOf(bulkItemData.get("id"));
					} else {
						logger.debug("No id for bulk response item {}. Skipping.", wrapper.getUriId());
						continue;
					}

					wrapper.getEntity().setId(id);
					List<MaintainRelationshipsCommand> maintainRelationshipsCommandList
							= wrapper.getMaintainRelationshipsList();
					for (MaintainRelationshipsCommand maintainRelationshipsCommand : maintainRelationshipsCommandList) {
						maintainRelationshipsCommand.setEntityId(id);
					}
					break;
				case UPDATE:
					break;
				default:
					logger.warn("Unsupported operation {}", operation.toString());
					break;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void setupMaintainRelationship(ProcessContext kcontext) {
		List<MaintainRelationshipsCommand> masterMaintainRelationshipsList = new ArrayList<>();
		Map<String, Map<String, String>> ratingMapMap = new HashMap<>();
		List<EntityIngestWrapper<?>> ingestWrapperList = (List<EntityIngestWrapper<?>>) kcontext.getVariable(INGEST_WRAPPER_LIST_KEY);
		for (EntityIngestWrapper<?> ingestWrapper : ingestWrapperList) {
			if (ingestWrapper.getEntity() instanceof BaseMetadata
					&& BaseMetadata.DataMaster.MEDIA_MANAGER.equals(((BaseMetadata) ingestWrapper.getEntity()).getDataMaster())) {
				// Ignore wrappers where data master is media manager.
				continue;
			}
			masterMaintainRelationshipsList.addAll(ingestWrapper.getMaintainRelationshipsList());
			if (ingestWrapper instanceof EntityIngestWrapperWithGenreAndRating<?>) {
				ratingMapMap.put(ingestWrapper.getEntityType() + ":" + ingestWrapper.getEntity().getId(), ((EntityIngestWrapperWithGenreAndRating<?>) ingestWrapper).getRatingMap());
			}
		}
		kcontext.setVariable(MAINTAIN_RELATIONSHIPS_LIST_KEY, masterMaintainRelationshipsList);
		kcontext.setVariable(RATING_MAP_MAP_KEY, ratingMapMap);
	}

	@SuppressWarnings("unchecked")
	public static void setupUpdateEntitiesWithChangedRelationships(ProcessContext kcontext) {//maintainRelationshipsResultObject
		MaintainRelationshipsResult maintainRelationshipsResult =
				(MaintainRelationshipsResult) kcontext.getVariable(RELATIONSHIP_MAINTAINED_ENTITY_LIST_KEY);
		List<String> ratingMaintainedEntityList =
				(List<String>) kcontext.getVariable(RATING_MAINTAINED_ENTITY_LIST_KEY);
		List<AbstractEntityIngestWrapper<?>> ingestWrapperList =
				(List<AbstractEntityIngestWrapper<?>>) kcontext.getVariable(INGEST_WRAPPER_LIST_KEY);
		List<BaseEntity> modifiedEntityList = new ArrayList<>();
		for (EntityIngestWrapper<?> ingestWrapper : ingestWrapperList) {
			if (ingestWrapper.getStoredEntity() == null) {
				// Ignore inserts.
				continue;
			}
			BaseEntity baseEntity = ingestWrapper.getEntity();
			for (MaintainRelationshipsResult.Details commandDetail : maintainRelationshipsResult.getDetails()) {
				// Update if statement to detect if command is realted to baseEntity by id and type and if it was changed
				if (commandDetail.isChanged()
						&& commandDetail.isEntity(ingestWrapper.getEntityType(), baseEntity.getId())
						&& !modifiedEntityList.contains(baseEntity)) {

					// Keep below as it is
					if (baseEntity instanceof BaseMetadata && BooleanUtils.isTrue(((BaseMetadata) baseEntity).getMetadataApproved())) {
						((BaseMetadata) baseEntity).setMetadataApproved(false);
						((BaseMetadata) baseEntity).setLastModifiedDateTime(DateTime.now());
						modifiedEntityList.add(baseEntity);
					} else if (!(baseEntity instanceof BaseMetadata) && baseEntity instanceof Base) {
						((Base) baseEntity).setLastModifiedDateTime(DateTime.now());
						modifiedEntityList.add(baseEntity);
					}
				}
			}
			// Do not touch. This functionality is unchanged.
			for (String entityKey : ratingMaintainedEntityList) {
				String[] splitEntityKey = entityKey.split(":");
				if (splitEntityKey.length >= 2
						&& ingestWrapper.getEntityType().equals(splitEntityKey[0])
						&& baseEntity.getId().equals(splitEntityKey[1])
						&& !modifiedEntityList.contains(baseEntity)) {
					baseEntity.setModifiedDate(DateTime.now());
					if (baseEntity instanceof BaseMetadata && BooleanUtils.isTrue(((BaseMetadata) baseEntity).getMetadataApproved())) {
						((BaseMetadata) baseEntity).setMetadataApproved(false);
						((BaseMetadata) baseEntity).setLastModifiedDateTime(DateTime.now());
						modifiedEntityList.add(baseEntity);
					} else if (!(baseEntity instanceof BaseMetadata) && baseEntity instanceof Base) {
						((Base) baseEntity).setLastModifiedDateTime(DateTime.now());
						modifiedEntityList.add(baseEntity);
					}
				}
			}
		}
		kcontext.setVariable(MODIFIED_ENTITY_LIST_KEY, modifiedEntityList);
	}

	private static ConcurrentHashMap<String, Method> findMethods(Class<?> clazz) {
		ConcurrentHashMap<String, Method> thisClassMethodCache = reflectionCache.get(clazz);
		if (thisClassMethodCache == null) {
			thisClassMethodCache = new ConcurrentHashMap<>();
			reflectionCache.put(clazz, thisClassMethodCache);
		}

		if (thisClassMethodCache.isEmpty()) {
			for (Method method : clazz.getMethods()) {
				thisClassMethodCache.put(method.getName(), method);
			}
		}

		return thisClassMethodCache;
	}

	private static Method findMethod(Class<?> clazz, String methodName)
			throws NoSuchMethodException {
		Method method = findMethods(clazz).get(methodName);
		if (method == null) {
			throw new NoSuchMethodException(clazz.getCanonicalName() + "." + methodName);
		}

		return method;
	}
}
