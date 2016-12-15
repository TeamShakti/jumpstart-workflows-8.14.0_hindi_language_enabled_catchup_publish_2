package com.irdeto.jumpstart.workflow.ingest;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionType;
import org.joda.time.DateTime;
import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.irdeto.domain.mediamanager.command.ErrorLevel;
import com.irdeto.domain.mediamanager.command.MaintainRelationshipsCommand;
import com.irdeto.domain.mediamanager.command.ReadEntitiesSetCommand;
import com.irdeto.domain.mediamanager.command.ReadEntitiesSetResult;
import com.irdeto.domain.mediamanager.message.bulk.EntityData;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.ChannelDay;
import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.ingest.AbstractEntityIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.ChannelDayIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.ScheduleSlotIngestWrapper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.publish.activemq.CatchUpPublishHelper;

public class EpgIngestHelper extends WorkflowHelper {
	private static final Logger logger = LoggerFactory.getLogger(EpgIngestHelper.class);

	private static final String INPUT_CONTENT = "inputContent";
	private static final String FILE_NAME = "fileName";
	private static final String FILE_PATH = "filePath";

	private static final String INGEST_WRAPPER_LIST = "ingestWrapperList";
	private static final String INGEST_WRAPPER_KEY_LIST = "ingestWrapperKeyList";

	private static final String READ_ENTITIES_SET_COMMANDS_LIST = "readEntitiesSetCommandsList";
	private static final String READ_ENTITIES_RESPONSE = "readEntitiesResponse";

	private static final String BULK_CREATE_REQUEST = "bulkCreateRequest";
	private static final String BULK_UPDATE_REQUEST = "bulkUpdateRequest";

	private static final String MAINTAIN_RELATIONSHIPS_LIST = "maintainRelationshipsCommandsList";

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final CollectionType channelDayCollectionType
			= mapper.getTypeFactory().constructCollectionType(List.class, ChannelDay.class);
	private static final CollectionType genreCollectionType
			= mapper.getTypeFactory().constructCollectionType(List.class, Genre.class);

	public static void mapMetadata(ProcessContext kcontext) {
		String xmlInput = (String) kcontext.getVariable(INPUT_CONTENT);
		String fileName = (String) kcontext.getVariable(FILE_NAME);
		String filePath = (String) kcontext.getVariable(FILE_PATH);
		List<EntityIngestWrapper<?>> ingestWrapperList = mapMetadata(xmlInput, fileName, filePath);
		kcontext.setVariable(INGEST_WRAPPER_LIST, ingestWrapperList);
	}

	protected static List<EntityIngestWrapper<?>> mapMetadata(
			String xmlInput,
			String fileName,
			String filePath
	) {
		List<EntityIngestWrapper<?>> ingestWrapperList = new ArrayList<>();
		try {
			EpgIngestMapper epgIngestMapper = EpgIngestFactory.getInstance()
					.getMapper(xmlInput, fileName, filePath);
			if (epgIngestMapper != null) {
				ingestWrapperList.addAll(epgIngestMapper.findEntities());
			} else {
				logger.error("Unable to determine metadata ingest mechanism from input file.");
			}
		} catch (Exception e) {
			logger.error("An error occurred ingesting metadata.", e);
		}
		return ingestWrapperList;
	}

	public static boolean areSlotsInThePast(ProcessContext kcontext) {
		List<EntityIngestWrapper> ingestWrappersInThePast = new ArrayList<>();

		List<AbstractEntityIngestWrapper> ingestWrapperList;
		ingestWrapperList = (List<AbstractEntityIngestWrapper>) kcontext
				.getVariable(INGEST_WRAPPER_LIST);

		DateTime today = DateTime.now().withTimeAtStartOfDay();
		boolean inPast = ingestWrapperList.removeIf(wrapper -> {
			BaseEntity entity = wrapper.getEntity();
			if (entity == null) {
				return true;
			}

			DateTime epgDateTime = null;
			if (entity instanceof ChannelDay) {
				epgDateTime = ((ChannelDay) entity).getDate();
			} else if (entity instanceof ScheduleSlot) {
				epgDateTime = ((ScheduleSlot) entity).getLinearBroadcastDate();
			} else {
				return false;
			}

			return epgDateTime.isBefore(today);
		});

		kcontext.setVariable(INGEST_WRAPPER_LIST, ingestWrapperList);

		return inPast;
	}

	public static void prepareReadSlots(ProcessContext kcontext) {
		List<AbstractEntityIngestWrapper> ingestWrapperList;
		ingestWrapperList = (List<AbstractEntityIngestWrapper>) kcontext
				.getVariable(INGEST_WRAPPER_LIST);

		List<String> channelDayUriIdList = new ArrayList<>();
		for (EntityIngestWrapper wrapper : ingestWrapperList) {
			if (wrapper instanceof ChannelDayIngestWrapper && wrapper.getEntity() != null) {
				channelDayUriIdList.add(wrapper.getUriId());
			}
		}

		List<ReadEntitiesSetCommand> readCommandList;
		readCommandList = (List<ReadEntitiesSetCommand>) kcontext
				.getVariable(READ_ENTITIES_SET_COMMANDS_LIST);
		if (readCommandList == null) {
			readCommandList = new ArrayList<>();
		}

		readCommandList.add(
				new ReadEntitiesSetCommand(
						WorkflowHelper.getEntityType(ChannelDay.class),
						WorkflowHelper.ATTRIBUTE_NAME_URI_ID,
						channelDayUriIdList,
						ErrorLevel.ERROR,
						ErrorLevel.ERROR
				)
		);

		kcontext.setVariable(READ_ENTITIES_SET_COMMANDS_LIST, readCommandList);
	}

	public static void prepareReadGenres(ProcessContext kcontext) {
		List<AbstractEntityIngestWrapper> ingestWrapperList;
		ingestWrapperList = (List<AbstractEntityIngestWrapper>) kcontext
				.getVariable(INGEST_WRAPPER_LIST);

		List<String> genreUriIdList = ingestWrapperList.stream()
										.filter(Objects::nonNull)
										.filter(wrapper -> wrapper instanceof ScheduleSlotIngestWrapper)
										.map(wrapper -> ((ScheduleSlotIngestWrapper) wrapper).getIngestGenre())
										.collect(toList());

		List<ReadEntitiesSetCommand> readCommandList;
		readCommandList = (List<ReadEntitiesSetCommand>) kcontext
				.getVariable(READ_ENTITIES_SET_COMMANDS_LIST);
		if (readCommandList == null) {
			readCommandList = new ArrayList<>();
		}

		readCommandList.add(
				new ReadEntitiesSetCommand(
						WorkflowHelper.getEntityType(Genre.class),
						WorkflowHelper.ATTRIBUTE_NAME_INGEST_GENRE,
						genreUriIdList,
						ErrorLevel.ERROR,
						ErrorLevel.ERROR
				)
		);

		kcontext.setVariable(READ_ENTITIES_SET_COMMANDS_LIST, readCommandList);
	}

	public static void mergeEntities(ProcessContext kcontext) {
		ReadEntitiesSetResult readEntitiesResult = (ReadEntitiesSetResult) kcontext
				.getVariable(READ_ENTITIES_RESPONSE);

		final Map<String, ChannelDay> existingChannelDayMap
				= extractChannelDayEntities(readEntitiesResult);
		final Map<String, ScheduleSlot> existingScheduleSlotMap
				= extractScheduleSlotEntities(existingChannelDayMap.values());

		final Map<String, Genre> genreMap
				= extractGenreEntities(readEntitiesResult);

		List<AbstractEntityIngestWrapper> ingestWrapperList;
		ingestWrapperList = (List<AbstractEntityIngestWrapper>) kcontext
				.getVariable(INGEST_WRAPPER_LIST);

		Map<String, String> scheduleSlotIngestGenreMap = extractScheduleSlotIngestGenreMapping(
				ingestWrapperList
		);

		for (EntityIngestWrapper wrapper : ingestWrapperList) {
			if (wrapper.getEntity() == null) {
				logger.debug("Entity of the wrapper with URI ID {} is null. Skipping.", wrapper.getUriId());
				continue;
			}

			if (wrapper instanceof ChannelDayIngestWrapper) {
				mergeChannelDayIngestWrapperEntitiesIds(
						(ChannelDayIngestWrapper) wrapper,
						existingChannelDayMap.get(wrapper.getUriId()),
						scheduleSlotIngestGenreMap,
						genreMap
				);
			}
			if (wrapper instanceof ScheduleSlotIngestWrapper) {
				mergeScheduleSlotIngestWrapperEntityIds(
						(ScheduleSlotIngestWrapper) wrapper,
						existingScheduleSlotMap.get(wrapper.getUriId()),
						genreMap
				);
			}
		}

		kcontext.setVariable(INGEST_WRAPPER_LIST, ingestWrapperList);
	}

	public static void prepareBulkIngest(ProcessContext kcontext) {
		List<AbstractEntityIngestWrapper> ingestWrapperList;
		ingestWrapperList = (List<AbstractEntityIngestWrapper>) kcontext
				.getVariable(INGEST_WRAPPER_LIST);

		Map<String, EntityData> insertEntitiesData = new HashMap<>();
		Map<String, EntityData> updateEntitiesData = new HashMap<>();

		for (EntityIngestWrapper wrapper : ingestWrapperList) {
			if (wrapper.getEntity() == null) {
				logger.debug("Entity of the wrapper with URI ID {} is null. Skipping.", wrapper.getUriId());
				continue;
			}

			if (!ChannelDay.class.equals(wrapper.getEntityClass())) {
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

		kcontext.setVariable(BULK_CREATE_REQUEST, insertEntitiesData);
		kcontext.setVariable(BULK_UPDATE_REQUEST, updateEntitiesData);
	}

	public static void processUpdatedEntities(ProcessContext kcontext) {
		List<AbstractEntityIngestWrapper> ingestWrapperList;
		ingestWrapperList = (List<AbstractEntityIngestWrapper>) kcontext
				.getVariable(INGEST_WRAPPER_LIST);

		ReadEntitiesSetResult readEntitiesResult = (ReadEntitiesSetResult) kcontext
				.getVariable(READ_ENTITIES_RESPONSE);

		Map<String, ChannelDay> updatedChannelDayMap = extractChannelDayEntities(readEntitiesResult);
		Map<String, ScheduleSlot> updatedScheduleSlotMap = extractScheduleSlotEntities(updatedChannelDayMap.values());

		for (EntityIngestWrapper wrapper : ingestWrapperList) {
			if (wrapper.getEntity() == null) {
				logger.debug("Entity of the wrapper with URI ID {} is null. Skipping.", wrapper.getUriId());
				continue;
			}
			if (wrapper instanceof ChannelDayIngestWrapper) {
				ChannelDay updatedChannelDay = updatedChannelDayMap.get(wrapper.getUriId());
				if (updatedChannelDay != null) {
					((ChannelDayIngestWrapper) wrapper).setEntity(updatedChannelDay);
					@SuppressWarnings("unchecked")
					List<MaintainRelationshipsCommand> maintainRelationshipsCommandList
							= wrapper.getMaintainRelationshipsList();
					for (MaintainRelationshipsCommand maintainRelationshipsCommand : maintainRelationshipsCommandList) {
						maintainRelationshipsCommand.setEntityId(updatedChannelDay.getId());
					}
				}
			}
			if (wrapper instanceof ScheduleSlotIngestWrapper) {
				ScheduleSlot updatedScheduleSlot = updatedScheduleSlotMap.get(wrapper.getUriId());
				if (updatedScheduleSlot != null) {
					((ScheduleSlotIngestWrapper) wrapper).setEntity(updatedScheduleSlot);
				}
			}
		}

		kcontext.setVariable(INGEST_WRAPPER_LIST, ingestWrapperList);
	}

	public static void setupMaintainRelationship(ProcessContext kcontext) {
		List<EntityIngestWrapper<?>> ingestWrapperList;
		ingestWrapperList = (List<EntityIngestWrapper<?>>) kcontext
				.getVariable(INGEST_WRAPPER_LIST);

		List<MaintainRelationshipsCommand> masterMaintainRelationshipsList = new ArrayList<>();
		ingestWrapperList.stream().filter(wrapper -> wrapper instanceof ChannelDayIngestWrapper)
			.forEach(wrapper -> masterMaintainRelationshipsList.addAll(wrapper.getMaintainRelationshipsList()));
		kcontext.setVariable(MAINTAIN_RELATIONSHIPS_LIST, masterMaintainRelationshipsList);
	}

	public static void setupPublishLoop(ProcessContext kcontext) {
		List<AbstractEntityIngestWrapper<?>> ingestWrapperList;
		ingestWrapperList = (List<AbstractEntityIngestWrapper<?>>) kcontext
				.getVariable(INGEST_WRAPPER_LIST);

		List<Integer> ingestWrapperKeyList = new ArrayList<>();

		for (int i = 0; i < ingestWrapperList.size(); i++) {
			EntityIngestWrapper wrapper = ingestWrapperList.get(i);

			if (wrapper instanceof ChannelDayIngestWrapper) {
				ingestWrapperKeyList.add(i);
			}
			if (wrapper instanceof ScheduleSlotIngestWrapper) {
				ScheduleSlotIngestWrapper scheduleSlotWrapper
						= (ScheduleSlotIngestWrapper) wrapper;
				boolean needsPublish
						= scheduleSlotWrapper.isCatchUpChanged()
						|| scheduleSlotWrapper.isBlackOutChanged();
				if (needsPublish) {
					ingestWrapperKeyList.add(i);
				}
			}
		}
		kcontext.setVariable(INGEST_WRAPPER_KEY_LIST, ingestWrapperKeyList);
	}

	public static void prepareBulkUpdatePublishedDateRequest(ProcessContext kcontext) {
		List<AbstractEntityIngestWrapper> ingestWrapperList;
		ingestWrapperList = (List<AbstractEntityIngestWrapper>) kcontext
				.getVariable(INGEST_WRAPPER_LIST);

		Map<String, EntityData> updateEntitiesData = new HashMap<>();

		for (EntityIngestWrapper wrapper : ingestWrapperList) {
			if (wrapper.getEntity() == null) {
				logger.debug("Entity of the wrapper with URI ID {} is null. Skipping.", wrapper.getUriId());
				continue;
			}

			if (!ChannelDay.class.equals(wrapper.getEntityClass())) {
				continue;
			}

			DateTime now = new DateTime();
			ChannelDay channelDay = (ChannelDay) wrapper.getEntity();
			channelDay.setLastPublishDateTime(now);
			for (Base scheduleSlot : channelDay.getScheduleSlots()) {
				scheduleSlot.setLastPublishDateTime(now);
			}

			updateEntitiesData.put(
					wrapper.getUriId(),
					new EntityData(
							wrapper.getEntityType(),
							Integer.valueOf(wrapper.getEntity().getId()),
							wrapper.getEntity()
					)
			);
		}

		kcontext.setVariable(BULK_UPDATE_REQUEST, updateEntitiesData);
	}

	private static Map<String, ChannelDay> extractChannelDayEntities(ReadEntitiesSetResult result) {
		final Map<String, ChannelDay> channelDayMap = new HashMap<>();
		for (ReadEntitiesSetResult.Details details : result.getDetails()) {
			String entityType = probeEntityType(details);
			if (isBlank(entityType)) {
				continue;
			}

			if (WorkflowHelper.getEntityType(ChannelDay.class).equals(entityType)) {
				try {
					List<ChannelDay> channelDays = mapper.readValue(
							mapper.writeValueAsString(details.getData().getEntities()),
							channelDayCollectionType
					);

					channelDayMap.putAll(Maps.uniqueIndex(channelDays, Base::getUriId));
				} catch (IOException | IllegalArgumentException e) {
					logger.error("Failed to process {} from read entities set command response element {}",
						entityType, details.toString());
				}
			}
		}

		return channelDayMap;
	}

	private static Map<String, Genre> extractGenreEntities(ReadEntitiesSetResult result) {
		final Map<String, Genre> genresMap = new HashMap<>();
		for (ReadEntitiesSetResult.Details details : result.getDetails()) {
			String entityType = probeEntityType(details);
			if (isBlank(entityType)) {
				continue;
			}

			if (WorkflowHelper.getEntityType(Genre.class).equals(entityType)) {
				try {
					List<Genre> genres = mapper.readValue(
							mapper.writeValueAsString(details.getData().getEntities()),
							genreCollectionType
					);

					genresMap.putAll(Maps.uniqueIndex(genres, Genre::getIngestGenre));
				} catch (IOException | IllegalArgumentException e) {
					logger.error("Failed to process {} from read entities set command response element {}",
						entityType, details.toString());
				}
			}
		}

		return genresMap;
	}

	private static Map<String, ScheduleSlot> extractScheduleSlotEntities(
			Collection<ChannelDay> channelDays
	) {
		Map<String, ScheduleSlot> scheduleSlotMap = new HashMap<>();

		for (ChannelDay channelDay : channelDays) {
			scheduleSlotMap.putAll(
				Maps.uniqueIndex(
					channelDay.getScheduleSlots(),
					Base::getUriId
				)
			);
		}

		return scheduleSlotMap;
	}

	private static Map<String, String> extractScheduleSlotIngestGenreMapping(
			List<AbstractEntityIngestWrapper> ingestWrapperList
	) {
		Map<String, String> scheduleSlotIngestGenreMap = new HashMap<>();

		ingestWrapperList.stream()
			.filter(wrapper -> wrapper instanceof ScheduleSlotIngestWrapper)
			.forEach(wrapper -> {
				String ingestGenre = ((ScheduleSlotIngestWrapper) wrapper).getIngestGenre();
				if (isNotBlank(wrapper.getUriId()) && isNotBlank(ingestGenre))
					scheduleSlotIngestGenreMap.put(
						wrapper.getUriId(),
						((ScheduleSlotIngestWrapper) wrapper).getIngestGenre()
					);
			});

		return scheduleSlotIngestGenreMap;
	}

	private static void mergeChannelDayIngestWrapperEntitiesIds(
			ChannelDayIngestWrapper ingestWrapper,
			ChannelDay existingChannelDay,
			Map<String, String> scheduleSlotGenreMapping,
			Map<String, Genre> genreMap
	) {
		if (existingChannelDay != null) {
			ingestWrapper.getEntity().setId(existingChannelDay.getId());

			Map<String, ScheduleSlot> existingScheduleSlotsMap = Maps.uniqueIndex(
				existingChannelDay.getScheduleSlots(),
				Base::getUriId);

			for (ScheduleSlot scheduleSlot : ingestWrapper.getEntity().getScheduleSlots()) {
				ScheduleSlot existingScheduleSlot = existingScheduleSlotsMap.get(scheduleSlot.getUriId());
				if (existingScheduleSlot != null) {
					scheduleSlot.setId(existingScheduleSlot.getId());
				}

				String ingestGenre = scheduleSlotGenreMapping
						.get(scheduleSlot.getUriId());
				if (isNotBlank(ingestGenre)) {
					Genre genre = genreMap.get(ingestGenre);
					if (genre != null) {
						scheduleSlot.setGenre(genre.getTitle());
					}
				}
			}

			List<MaintainRelationshipsCommand> maintainRelationshipsCommandList
					= ingestWrapper.getMaintainRelationshipsList();
			for (MaintainRelationshipsCommand maintainRelationshipsCommand : maintainRelationshipsCommandList) {
				maintainRelationshipsCommand.setEntityId(existingChannelDay.getId());
			}
		}
	}

	private static void mergeScheduleSlotIngestWrapperEntityIds(
			ScheduleSlotIngestWrapper ingestWrapper,
			ScheduleSlot existingScheduleSlot,
			Map<String, Genre> genreMap
	) {
		ScheduleSlot currentScheduleSlot = ingestWrapper.getEntity();

		if (existingScheduleSlot != null) {
			ingestWrapper.getEntity().setId(existingScheduleSlot.getId());
		}

		Genre genre = genreMap.get(ingestWrapper.getIngestGenre());
		if (genre != null) {
			ingestWrapper.getEntity().setGenre(genre.getTitle());
		}

		ingestWrapper.setCatchUpChanged(
				CatchUpPublishHelper.needsCatchUpPublish(
						currentScheduleSlot,
						existingScheduleSlot
				)
		);
		// TODO: update the blackout flag accordingly when there is a workflow for that
	}

	private static String probeEntityType(ReadEntitiesSetResult.Details details) {
		if (details != null && details.getData() != null && details.getData().getEntities() != null) {
			List<Map<String, Object>> entities = details.getData().getEntities();
			if (!entities.isEmpty()) {
				return (String) entities.get(0).get(ATTRIBUTE_NAME_TYPE);
			}
		}
		return null;
	}
}
