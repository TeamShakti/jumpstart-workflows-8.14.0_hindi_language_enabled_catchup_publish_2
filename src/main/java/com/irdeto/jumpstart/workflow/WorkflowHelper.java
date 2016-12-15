package com.irdeto.jumpstart.workflow;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.domain.mediamanager.filter.EqFilter;
import com.irdeto.domain.mediamanager.filter.Filter;
import com.irdeto.domain.mediamanager.filter.LikeFilter;
import com.irdeto.domain.mmentityapi.HttpQueryFilterParameter;
import com.irdeto.domain.operation.MM8Response;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.domain.Content;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ImageSubcontent;
import com.irdeto.jumpstart.domain.Link;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.domain.RatingScheme;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.Term;
import com.irdeto.jumpstart.domain.TranscodeProfile;
import com.irdeto.jumpstart.domain.TvodCollection;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.domain.config.Transcode;
import com.irdeto.jumpstart.domain.publish.TermWrapper;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.manager.jbpm.knowledge.ClassManager;
import com.irdeto.manager.task.BeanUtil;

public class WorkflowHelper {
	private static final int CONTENT_ID_SPACING_FACTOR = 100000000;
	private static final String RELATED_ENTITY_LIST_KEY = "relatedEntityList";
	public static final DateTime START_OF_TIME = new DateTime(2000, 1, 1, 0, 0);
	public static final DateTime END_OF_TIME = new DateTime(2100, 1, 1, 0, 0);
	private static final String DOMAIN_PACKAGE_NAME = "com.irdeto.jumpstart.domain";

	public static final String SOURCE_TYPE = "MEZZANINE";
	public static final String TRANSCODED_TYPE = "TRANSCODED";
	public static final String PROTECTED_TYPE = "PROTECTED";
	public static final String CDN_TYPE = "CDN";

	public static final String ENTITY_TYPE_GENRE = "genre";
	public static final String ENTITY_TYPE_SUBSCRIPTION_PACKAGE = "subscriptionPackage";
	public static final String ENTITY_TYPE_TVOD_COLLECTION = "tvodCollection";
	public static final String ENTITY_TYPE_CHANNEL_DAY = "channelDay";
	public static final String ENTITY_TYPE_SCHEDULE_SLOT = "scheduleSlot";
	public static final String ENTITY_TYPE_TERM = "term";
	public static final String ENTITY_TYPE_TERM_MAPPING = "termMapping";
	public static final String ENTITY_TYPE_DEVICE_PROFILE = "deviceProfile";
	public static final String ENTITY_TYPE_TRANSCODE_PROFILE = "transcodeProfile";
	public static final String ENTITY_TYPE_PROTECT_PROFILE = "protectProfile";
	public static final String ENTITY_TYPE_ENCODE_PROFILE = "encodeProfile";
	public static final String ENTITY_TYPE_OFFER = "offer";
	public static final String ENTITY_TYPE_PERSON = "person";
	public static final String ENTITY_TYPE_EVENT = "event";
	public static final String ENTITY_TYPE_CHANNEL = "channel";
	public static final String ENTITY_TYPE_BRAND = "brand";
	public static final String ENTITY_TYPE_SERIES = "series";
	public static final String ENTITY_TYPE_PROGRAM = "program";
	public static final String ENTITY_TYPE_IMAGE_CONTENT = "imageContent";
	public static final String ENTITY_TYPE_VIDEO_CONTENT = "videoContent";
	public static final String ENTITY_TYPE_SUBTITLE_CONTENT = "subtitleContent";
	public static final String ENTITY_TYPE_RATING = "rating";
	public static final String ENTITY_TYPE_RATING_SCHEME = "ratingScheme";

	public static final String CONTENT_BOX_COVER = "BoxCover";
	public static final String CONTENT_POSTER = "Poster";
	public static final String CONTENT_THUMBNAIL = "Thumbnail";
	public static final String CONTENT_BARKER = "Barker";
	public static final String CONTENT_MOVIE = "Movie";
	public static final String CONTENT_PREVIEW = "Preview";
	public static final String SUBCONTENT_BOX_COVER = "BoxCoverSubcontent";
	public static final String SUBCONTENT_POSTER = "PosterSubcontent";
	public static final String SUBCONTENT_THUMBNAIL = "ThumbnailSubcontent";
	public static final String SUBCONTENT_BARKER = "BarkerSubcontent";
	public static final String SUBCONTENT_MOVIE = "MovieSubcontent";
	public static final String SUBCONTENT_PREVIEW = "PreviewSubcontent";

	public static final String PROGRAM_KEY = "program";
	public static final String MM8_RESPONSE_KEY = "mm8Response";
	public static final String CONTENT_KEY = "content";
	public static final String SOURCE_SUBCONTENT_KEY = "sourceSubcontent";
	public static final String TRANSCODED_SUBCONTENT_LIST_KEY = "transcodedSubcontentList";
	private static final String RATING_SCHEME_ENTITY_ID_KEY = "ratingSchemeEntityId";
	protected static final String RELATIONSHIP_LIST_KEY = "relationshipList";
	protected static final String RELATIONSHIP_MAP_KEY = "relationshipMap";
	protected static final String TERM_WRAPPER_LIST_KEY = "termWrapperList";

	public static final String SLASH = "/";
	public static final String BACKSLASH = "\\";
	public static final String S3_KEY = "s3";
	public static final String VM_KEY = "vm";

	public static final String AVAILABLE_COUNTRY = "01";

	public static final String BRAND_RELATIONSHIP_NAME = "brands";
	public static final String CHANNEL_RELATIONSHIP_NAME = "channels";
	public static final String CHANNEL_DAY_RELATIONSHIP_NAME = "channelDays";
	public static final String EVENT_RELATIONSHIP_NAME = "events";
	public static final String OFFER_RELATIONSHIP_NAME = "offers";
	public static final String PROGRAM_RELATIONSHIP_NAME = "programs";
	public static final String SERIES_RELATIONSHIP_NAME = "seriess";
	public static final String SCHEDULE_SLOT_RELATIONSHIP_NAME = "scheduleSlots";
	public static final String SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME = "subscriptionPackages";
	public static final String TVOD_COLLECTION_RELATIONSHIP_NAME = "tvodCollections";
	public static final String TERM_RELATIONSHIP_NAME = "terms";
	public static final String GENRE_RELATIONSHIP_NAME = "genres";
	public static final String DEVICE_PROFILE_RELATIONSHIP_NAME = "deviceProfiles";
	public static final String TRANSCODE_PROFILE_RELATIONSHIP_NAME = "transcodeProfiles";
	public static final String ENCODE_PROFILE_RELATIONSHIP_NAME = "encodeProfiles";
	public static final String PROTECT_PROFILE_RELATIONSHIP_NAME = "protectProfiles";
	public static final String RATING_RELATIONSHIP_NAME = "ratings";
	public static final String RATING_SCHEME_RELATIONSHIP_NAME = "ratingSchemes";
	public static final String TRANSCODED_VIDEO_SUBCONTENT_RELATIONSHIP_NAME = "transSubs";
	public static final String PROTECTED_VIDEO_SUBCONTENT_RELATIONSHIP_NAME = "protectSubs";
	public static final String TERM_MAPPING_RELATIONSHIP_NAME = "termMappings";
	public static final String POLICY_GROUP_PROFILE_RELATIONSHIP_NAME = "policyGroupProfiles";
	public static final String POLICY_PROFILE_RELATIONSHIP_NAME = "policyProfiles";
	public static final String PARENT_RELATIONSHIP_NAME = "parent";
	public static final String CHILDREN_RELATIONSHIP_NAME = "children";

	protected static final String ATTRIBUTE_NAME_TYPE = "type";
	protected static final String ATTRIBUTE_NAME_ID = "id";
	public static final String ATTRIBUTE_NAME_URI_ID = "uriId";
	public static final String ATTRIBUTE_NAME_TITLE = "title";
	public static final String ATTRIBUTE_NAME_RATING = "rating";
	protected static final String ATTRIBUTE_NAME_SOURCE_PATH = "sourcePath";
	public static final String ATTRIBUTE_NAME_NAME = "name";
	public static final String ATTRIBUTE_NAME_CONTRACT_NAME = "contractName";
	public static final String ATTRIBUTE_CLASSIFICATION_SYS = "classificationSystem";
	public static final String ATTRIBUTE_NAME_CLASSIFICATION = "classification";
	public static final String ATTRIBUTE_MIN_AGE = "minimumAge";
	public static final String ATTRIBUTE_NAME_RATING_LABEL = "ratingLabel";
	public static final String ATTRIBUTE_NAME_CHANNEL_ID = "channelId";
	public static final String ATTRIBUTE_NAME_INGEST_GENRE = "ingestGenre";
	public static final String ATTRIBUTE_POLICY_NAME = "policyName";
	public static final String ATTRIBUTE_POLICY_GROUP_NAME = "policyGroupName";

	private static final Map<Class<? extends Base>, Integer> PARENT_CATEGORY_ID_MAP = new HashMap<>();

	public static final int CATEGORY_ID_EVERYTHING = 1;
	public static final int CATEGORY_ID_VOD = 2;
	public static final int CATEGORY_ID_TV_CHANNELS = 4;
	public static final int CATEGORY_ID_MOVIES = 5;
	public static final int CATEGORY_ID_CATCHUP = 7;
	public static final int CATEGORY_ID_EVENTS = 12;
	public static final int CATEGORY_ID_SCHEDULE_SLOTS = 13;

	static {
		PARENT_CATEGORY_ID_MAP.put(Program.class, 0);
		// 1 is everything
		// 2 is all VOD programs
		// 3 is unused
		PARENT_CATEGORY_ID_MAP.put(Channel.class, CATEGORY_ID_TV_CHANNELS);
		// 5 is movies
		PARENT_CATEGORY_ID_MAP.put(Series.class, 6);
		// 7 is catch-up
		PARENT_CATEGORY_ID_MAP.put(Genre.class, 8);
		PARENT_CATEGORY_ID_MAP.put(Brand.class, 9);
		PARENT_CATEGORY_ID_MAP.put(SubscriptionPackage.class, 10);
		PARENT_CATEGORY_ID_MAP.put(TvodCollection.class, 11);
		PARENT_CATEGORY_ID_MAP.put(Event.class, CATEGORY_ID_EVENTS);
		PARENT_CATEGORY_ID_MAP.put(ScheduleSlot.class, CATEGORY_ID_SCHEDULE_SLOTS);
	}

	private static Logger logger = LoggerFactory.getLogger(WorkflowHelper.class);

	public static Integer getParentCategoryId(Base entity) {
		return getParentCategoryId(entity.getClass());
	}

	public static Integer getParentCategoryId(Class<? extends Base> clazz) {
		Integer parentCategoryId = PARENT_CATEGORY_ID_MAP.get(clazz);
		if (parentCategoryId != null) {
			return parentCategoryId;
		} else {
			return 0;
		}
	}

	public static Integer getCategoryId(Base entity) {
		return getCategoryId(entity.getClass(), entity.getId());
	}

	public static Integer getCategoryId(String entityType, String entityId) {
		return getCategoryId(getClassFromType(entityType), entityId);
	}

	@SuppressWarnings("unchecked")
	private static Class<? extends Base> getClassFromType(String entityType) {
		ClassLoader classLoader = ((ClassManager) BeanUtil.getBean(ClassManager.class)).getClassLoader();
		String className = DOMAIN_PACKAGE_NAME + "." + StringUtils.capitalize(entityType);
		try {
			return (Class<? extends Base>) Class.forName(className, true, classLoader);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static Integer getCategoryId(Class<? extends Base> clazz, String entityId) {
		Integer parentCategoryId = getParentCategoryId(clazz);
		Integer integerEntityId = Integer.valueOf(entityId);
		return parentCategoryId * CONTENT_ID_SPACING_FACTOR + integerEntityId;
	}

	public static String getProgramTitle(ProcessContext kcontext) {
		Object programObject = (Program) kcontext.getVariable(PROGRAM_KEY);
		if (programObject == null || !(programObject instanceof Program)) {
			return "";
		}
		Program program = (Program) programObject;
		return getProgramTitle(program);
	}

	public static String getProgramTitle(Program program) {
		return LocaleHelper.getStringValueForDefaultLanguage(program.getTitleMedium());
	}

	@SuppressWarnings("unchecked")
	protected static <T extends Content> T findContentByEntityId(Program program, Class<T> contentClass, String contentId) {
		List<T> upToDateContentList = null;
		if (VideoContent.class.equals(contentClass)) {
			upToDateContentList = (List<T>) program.getVideoContent();
		} else if (ImageContent.class.equals(contentClass)) {
			upToDateContentList = (List<T>) program.getImageContent();
		}
		return getEntityFromListById(upToDateContentList, contentId);
	}

	protected static SourceVideoSub findSourceVideoSubcontentByEntityId(Program program, String videoContentId, String subcontentId) {
		VideoContent upToDateContent = findContentByEntityId(program, VideoContent.class, videoContentId);
		return getEntityFromListById(upToDateContent.getSubcontent(), subcontentId);
	}

	protected static <T extends BaseEntity> T getEntityFromListById(List<? extends T> baseEntityList, String id) {
		if (baseEntityList == null || id == null) {
			return null;
		}
		for (T baseEntityIterator : baseEntityList) {
			if (id.equals(baseEntityIterator.getId())) {
				return baseEntityIterator;
			}
		}
		return null;
	}

	protected static Integer getVersion(List<Object> versionList) {
		Integer maxVersion = null;
		for (Object version : versionList) {
			if (version instanceof Integer) {
				if (maxVersion == null || (Integer) version > maxVersion) {
					maxVersion = (Integer) version;
				}
			}
		}
		return maxVersion;
	}

	public static <T extends BaseEntity> T getEntity(Class<T> entityClass, MM8Response response) {
		List<T> entityList = getEntityList(entityClass, response);
		if (entityList != null && !entityList.isEmpty()) {
			return entityList.get(0);
		} else {
			return null;
		}
	}

	public static <T extends BaseEntity> List<T> getEntityList(Class<T> entityClass, MM8Response response) {
		List<T> entityList = new ArrayList<T>();
		if (response == null || response.getResponseObject() == null) {
			return entityList;
		}
		Object result = response.getResponseObject();
		if (result instanceof List) {
			List<?> list = (List<?>) result;
			if (list.size() > 0) {
				for (Object object : list) {
					if (isSuperclass(entityClass, object.getClass())) {
						@SuppressWarnings("unchecked")
						T entity = (T) object;
						entityList.add(entity);
					}
				}
			}
		} else if (isSuperclass(entityClass, result.getClass())) {
			@SuppressWarnings("unchecked")
			T entity = (T) result;
			entityList.add(entity);
		}
		return entityList;
	}

	protected static boolean isSuperclass(Class<?> entityClass, Class<?> testedObjectClass) {
		if (testedObjectClass == null || entityClass == null) {
			return false;
		}
		if (testedObjectClass.equals(entityClass)) {
			return true;
		}
		if (Object.class.equals(testedObjectClass)) {
			return false;
		}
		return isSuperclass(entityClass, testedObjectClass.getSuperclass());
	}

	public static <S, T> Entry<S, T> getNextEntry(Map<S, T> map) {
		Entry<S, T> returnedEntry = null;
		for (Entry<S, T> entry : map.entrySet()) {
			returnedEntry = entry;
			break;
		}
		if (returnedEntry != null) {
			map.remove(returnedEntry.getKey());
		}
		return returnedEntry;
	}

	public static void foundRatingSchemeFromMMResponse(ProcessContext kcontext) {
		MM8Response response = (MM8Response) kcontext.getVariable(MM8_RESPONSE_KEY);
		logger.debug("MM8Response from rating scheme search contains: {}", response.getResponseData());

		RatingScheme ratingScheme = getEntity(RatingScheme.class, response);
		if (ratingScheme != null) {
			kcontext.setVariable(RATING_SCHEME_ENTITY_ID_KEY, ratingScheme.getId());
			logger.debug("Found rating scheme: " + ratingScheme.getClassification());
		} else {
			logger.debug("Not found matching Rating Scheme");
			kcontext.setVariable(RATING_SCHEME_ENTITY_ID_KEY, null);
		}
	}

	@SuppressWarnings("unchecked")
	public static Integer getLinkCount(Object entity, String key) {
		if (entity == null) {
			return null;
		}

		if (entity instanceof BaseEntity) {
			List<Link> links = ((BaseEntity) entity).getLinks();
			if (links != null) {
				for (Link link : links) {
					if (key.equals(link.getKey())) {
						return link.getCount();
					}
				}
			}
		}
		return null;
	}

	public static String getXml(String filename) throws IOException {
		String xml = null;
		InputStream is = WorkflowHelper.class.getClassLoader().getResourceAsStream(filename);
		StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer);
		xml = writer.toString();
		return xml;
	}

	public static boolean isIn(Object item, Object... objects) {
		if (item == null) {
			return false;
		}
		for (Object object : objects) {
			if (item.equals(object)) {
				return true;
			}
		}
		return false;
	}

	public static String getEntityType(Object entity) {
		String className = null;
		if (entity instanceof Class<?>) {
			className = ((Class<?>) entity).getSimpleName();
		} else {
			Class<?> entityClass = entity.getClass();
			className = entityClass.getSimpleName();
		}
		return StringUtils.uncapitalize(className);
	}

	public static String getEntityType(Class entity) {
		String className;
		className = entity.getSimpleName();
		return StringUtils.uncapitalize(className);
	}

	public static SourceVideoSub getLatestSourceVideoSubcontent(VideoContent content) {
		if (content == null) {
			return null;
		}
		List<SourceVideoSub> sourceVideoSubcontentList = content.getSubcontent();
		if (sourceVideoSubcontentList == null || sourceVideoSubcontentList.isEmpty()) {
			return null;
		}
		Collections.sort(sourceVideoSubcontentList, new Comparator<SourceVideoSub>() {
			@Override
			public int compare(SourceVideoSub o1, SourceVideoSub o2) {
				if (o1.getCreatedDate() == null && o2.getCreatedDate() == null) {
					return 0;
				} else if (o1.getCreatedDate() == null) {
					return 1;
				} else {
					return o2.getCreatedDate().compareTo(o1.getCreatedDate());
				}
			}
		});
		return sourceVideoSubcontentList.get(0);
	}


	public static Collection<ImageSubcontent> getLatestImageSubcontentCollection(ImageContent content) {
		List<ImageSubcontent> imageSubcontentList = content.getSubcontent();
		if (imageSubcontentList == null || imageSubcontentList.isEmpty()) {
			return new ArrayList<>();
		}
		Collections.sort(imageSubcontentList, new Comparator<ImageSubcontent>() {
			@Override
			public int compare(ImageSubcontent o1, ImageSubcontent o2) {
				int compareResolutions = o1.getYResolution().compareTo(o2.getYResolution());
				if (compareResolutions != 0) {
					return compareResolutions;
				} else {
					return o1.getCreatedDate().compareTo(o2.getCreatedDate());
				}
			}
		});
		Map<Integer, ImageSubcontent> imageSubcontentMap = new HashMap<>();
		for (ImageSubcontent imageSubcontent : imageSubcontentList) {
			imageSubcontentMap.put(imageSubcontent.getYResolution(), imageSubcontent);
		}
		return imageSubcontentMap.values();
	}

	public static void setupTermRelationshipList(ProcessContext kcontext) {
		List<Relationship<?>> relationshipList = getTermRelationshipList();
		kcontext.setVariable(RELATIONSHIP_LIST_KEY, relationshipList);
	}

	public static List<Relationship<?>> getTermRelationshipList() {
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(
				new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
						new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class)));
		relationshipList.add(
				new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class,
						new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
								new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class))));
		relationshipList.add(
				new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class,
						new Relationship<Brand>(WorkflowHelper.BRAND_RELATIONSHIP_NAME, Brand.class,
								new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
										new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class)))));
		relationshipList.add(
				new Relationship<SubscriptionPackage>(WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME, SubscriptionPackage.class,
						new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
								new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class))));
		relationshipList.add(
				new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class,
						new Relationship<SubscriptionPackage>(WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME, SubscriptionPackage.class,
								new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
										new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class)))));
		relationshipList.add(
				new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class,
						new Relationship<Brand>(WorkflowHelper.BRAND_RELATIONSHIP_NAME, Brand.class,
								new Relationship<SubscriptionPackage>(WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME, SubscriptionPackage.class,
										new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
												new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class))))));
		relationshipList.add(
				new Relationship<TvodCollection>(WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME, TvodCollection.class,
						new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
								new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class))));
		relationshipList.add(
				new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class,
						new Relationship<TvodCollection>(WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME, TvodCollection.class,
								new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
										new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class)))));
		relationshipList.add(
				new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class,
						new Relationship<Brand>(WorkflowHelper.BRAND_RELATIONSHIP_NAME, Brand.class,
								new Relationship<TvodCollection>(WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME, TvodCollection.class,
										new Relationship<Offer>(WorkflowHelper.OFFER_RELATIONSHIP_NAME, Offer.class,
												new Relationship<Term>(WorkflowHelper.TERM_RELATIONSHIP_NAME, Term.class))))));
		return relationshipList;
	}

	public static void processTermRelationshipMap(ProcessContext kcontext, Base relatedEntity) {
		@SuppressWarnings("unchecked")
		Map<String, Object> relationshipMap = (Map<String, Object>) kcontext.getVariable(RELATIONSHIP_MAP_KEY);
		kcontext.setVariable(RELATIONSHIP_MAP_KEY, null);
		List<TermWrapper> termWrapperList = new ArrayList<>();

		for (Object value : relationshipMap.values()) {
			if (value instanceof Map) {
				DateTime startDateTime = START_OF_TIME;
				if (relatedEntity.getStartDateTime() != null) {
					startDateTime = relatedEntity.getStartDateTime();
				}
				DateTime endDateTime = END_OF_TIME;
				if (relatedEntity.getEndDateTime() != null) {
					endDateTime = relatedEntity.getEndDateTime();
				}
				List<TermWrapper> validTermWrapperList = getValidTermWrapperList((Map<?, ?>) value, startDateTime, endDateTime, relatedEntity);
				for (TermWrapper termWrapper : validTermWrapperList) {
					if (!termWrapperList.contains(termWrapper)) {
						termWrapperList.add(termWrapper);
					}
				}
			}
		}
		kcontext.setVariable(TERM_WRAPPER_LIST_KEY, termWrapperList);
	}

	private static List<TermWrapper> getValidTermWrapperList(Map<?, ?> map, DateTime startDateTime, DateTime endDateTime, Base offeredEntity) {
		List<TermWrapper> termWrapperList = new ArrayList<>();
		for (Entry<?, ?> entry : map.entrySet()) {
			if (entry.getKey() instanceof Base) {
				Base entity = (Base) entry.getKey();
				if (entity.getStartDateTime() != null && entity.getStartDateTime().isAfter(startDateTime)) {
					startDateTime = entity.getStartDateTime();
				}
				if (entity.getEndDateTime() != null && entity.getEndDateTime().isBefore(endDateTime)) {
					endDateTime = entity.getEndDateTime();
				}
				if (!(entity instanceof Offer)) {
					offeredEntity = entity;
				}
				if (entry.getValue() instanceof Map) {
					termWrapperList.addAll(getValidTermWrapperList((Map<?, ?>) entry.getValue(), startDateTime, endDateTime, offeredEntity));
				} else if (entry.getKey() instanceof Offer && entry.getValue() instanceof List) {
					Offer offer = (Offer) entry.getKey();
					for (Object object : (List<?>) entry.getValue()) {
						if (object instanceof Term) {
							Term term = (Term) object;
							if (term.getStartDateTime() != null && term.getStartDateTime().isAfter(startDateTime)) {
								startDateTime = term.getStartDateTime();
							}
							if (term.getEndDateTime() != null && term.getEndDateTime().isBefore(endDateTime)) {
								endDateTime = term.getEndDateTime();
							}
							DateTime now = DateTime.now();
							if (startDateTime.isBefore(endDateTime) && endDateTime.isAfter(now)) {
								termWrapperList.add(new TermWrapper(term, offer, offeredEntity, startDateTime, endDateTime));
							}
						}
					}
				}
			}
		}
		return termWrapperList;
	}

	public static String getTranscodeKey(Transcode transcode) {
		return transcode.getTranscoderProfile() + "~" + transcode.getTranscoderUri() + "~" + transcode.getTranscoderWorkflow();
	}

	public static String getTranscodeKey(TranscodeProfile transcodeProfile) {
		return transcodeProfile.getTranscoderProfile() + "~" + transcodeProfile.getTranscoderUri() + "~" + transcodeProfile.getTranscoderWorkflow();
	}

	public static String getProtectKey(Protect protect) {
		return protect.getProtectionType();
	}

	public static String getProtectKey(ProtectProfile protectProfile) {
		return protectProfile.getProtectionType().toString();
	}

	protected static List<Protect> intersect(List<Protect> set1, List<Protect> set2) {
		List<Protect> intersectList = new ArrayList<>();
		if (set1 == null || set2 == null) {
			return intersectList;
		}
		for (Protect protect : set1) {
			if (set2.contains(protect)) {
				intersectList.add(protect);
			}
		}
		return intersectList;
	}

	@SuppressWarnings("unchecked")
	public static void getEntityListFromMap(ProcessContext kcontext) {
		Map<String, Object> relationshipMap = (Map<String, Object>) kcontext.getVariable(RELATIONSHIP_MAP_KEY);
		List<Base> relatedEntityList = new ArrayList<>();
		for (Object value : relationshipMap.values()) {
			relatedEntityList.addAll(getRelatedEntities(value));
		}
		kcontext.setVariable(RELATED_ENTITY_LIST_KEY, relatedEntityList);
		kcontext.setVariable(RELATIONSHIP_MAP_KEY, null);
	}

	@SuppressWarnings({"unchecked"})
	private static List<Base> getRelatedEntities(Object value) {
		List<Base> relatedEntityList = new ArrayList<>();
		if (value instanceof Base) {
			relatedEntityList.add((Base) value);
		} else if (value instanceof List) {
			List<Object> list = (List<Object>) value;
			for (Object entity : list) {
				relatedEntityList.addAll(getRelatedEntities(entity));
			}
		} else if (value instanceof Map) {
			Map<Object, Object> relatedEntityMap = (Map<Object, Object>) value;
			for (Object relatedEntityMapValue : relatedEntityMap.values()) {
				relatedEntityList.addAll(getRelatedEntities(relatedEntityMapValue));
			}
		}
		return relatedEntityList;
	}

	public static String getControlAssetId(String entityType, String entityId, DateTime contentCreatedDate) {
		Integer categoryId = WorkflowHelper.getCategoryId(entityType, entityId);
		return getControlAssetId(categoryId, contentCreatedDate, (VideoContent) null);
	}

	public static String getControlAssetId(String entityType, String entityId, DateTime contentCreatedDate, VideoContent videoContent) {
		Integer categoryId = WorkflowHelper.getCategoryId(entityType, entityId);
		return getControlAssetId(categoryId, contentCreatedDate, videoContent);
	}

	public static String getControlAssetId(String entityType, String entityId, FilenameSegments segments) {
		Integer categoryId = WorkflowHelper.getCategoryId(entityType, entityId);
		return getControlAssetId(categoryId, segments.getDateHash(), segments.getContentId());
	}

	public static String getControlAssetId(String entityType, String entityId, DateTime contentCreatedDate, String videoContentId) {
		Integer categoryId = WorkflowHelper.getCategoryId(entityType, entityId);
		return getControlAssetId(categoryId, contentCreatedDate, videoContentId);
	}

	public static String getControlAssetId(Base entity) {
		return getControlAssetId(entity, null);
	}

	public static String getControlAssetId(String entityId, String entityType) {
		return getControlAssetId(entityId, entityType, (DateTime) null);
	}

	public static String getControlAssetId(Base entity, VideoContent videoContent) {
		Integer categoryId = WorkflowHelper.getCategoryId(entity);
		return getControlAssetId(categoryId, entity.getCreatedDate(), videoContent);
	}

	public static String getControlAssetId(Integer categoryId, DateTime contentCreatedDate, VideoContent videoContent) {
		if (videoContent != null) {
			return getControlAssetId(categoryId, contentCreatedDate, videoContent.getId());
		} else {
			return getControlAssetId(categoryId, contentCreatedDate, "");
		}
	}

	public static String getControlAssetId(Integer categoryId, DateTime contentCreatedDate, String videoContentId) {
		return getControlAssetId(categoryId, FileHelper.getUniqueId(contentCreatedDate), videoContentId);
	}

	public static String getControlAssetId(Integer categoryId, String createdDateHash, String videoContentId) {
		if (StringUtils.isNotBlank(videoContentId)) {
			return categoryId.toString() + "_" + videoContentId;
		} else {
			return categoryId.toString();
		}
	}

	public static QueryFilterParameter getQueryFilterParameter(String value) {
		return getQueryFilterParameter(ATTRIBUTE_NAME_URI_ID, value, false);
	}

	public static QueryFilterParameter getQueryFilterParameter(String key, String value, boolean likeMatch) {
		HttpQueryFilterParameter queryFilterParameter = new HttpQueryFilterParameter();
		if (likeMatch) {
			Filter likeFilter = new LikeFilter(key, value);
			queryFilterParameter.addFilter(likeFilter);
		} else {
			Filter filter = new EqFilter(key, value);
			queryFilterParameter.addFilter(filter);
		}
		return queryFilterParameter;
	}
}
