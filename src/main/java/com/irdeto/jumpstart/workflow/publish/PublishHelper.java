package com.irdeto.jumpstart.workflow.publish;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.Content;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ImageSubcontent;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.domain.ProtectVideoSub;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.SubtitleContent;
import com.irdeto.jumpstart.domain.SubtitleSubcontent;
import com.irdeto.jumpstart.domain.TranscodeProfile;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.config.Device;
import com.irdeto.jumpstart.domain.config.Profile;
import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.config.Transcode;
import com.irdeto.jumpstart.domain.publish.ImageContentWrapper;
import com.irdeto.jumpstart.domain.publish.PublishWrapper;
import com.irdeto.jumpstart.domain.publish.SubtitleContentWrapper;
import com.irdeto.jumpstart.domain.publish.TermWrapper;
import com.irdeto.jumpstart.domain.publish.VideoContentWrapper;
import com.irdeto.jumpstart.domain.publish.VideoSubcontentWrapper;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.jumpstart.factory.WrapperWithDestinations;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.properties.PropertyException;

import static com.irdeto.manager.task.BeanUtil.propertiesManager;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class PublishHelper extends WorkflowHelper {
	private static final String ENABLED = "enabled";
	private static final String HAS_MISSING_PROTECTED_CONTENT_KEY = "hasMissingProtectedContent";
	private static final String HAS_MISSING_REQUIRED_PROTECTED_CONTENT_KEY = "hasMissingRequiredProtectedContent";
	private static final String PUBLISH_WRAPPER_KEY = "publishWrapper";
	private static final String RELATIONSHIP_LIST_KEY = "relationshipList";
	private static final String CURRENT_ENTITY_KEY = "currentEntity";
	private static final String VIDEO_CONTENT_LIST_KEY = "videoContentList";
	private static final String VIDEO_CONTENT_KEY = "videoContent";
	private static final String PROFILE_KEY = "profile";
	private static final String PROTECTED_VIDEO_SUBCONTENT_LIST_KEY = "protectedVideoSubcontentList";
	private static final String SOURCE_VIDEO_SUBCONTENT_KEY = "sourceVideoSubcontent";
	private static final String PROTECTED_VIDEO_SUBCONTENT_KEY = "protectedVideoSubcontent";
	private static final String TERM_MAP_LIST_KEY = "termMapList";
	private static final String ALL_IMAGE_MEDIA_PRESENT_KEY = "allImageMediaPresent";
	private static final String ALL_SUBTITLE_MEDIA_PRESENT_KEY = "allSubtitleMediaPresent";
	private static final String METHOD_NAME_GET_VIDEO_CONTENT = "getVideoContent";
	private static final String METHOD_NAME_GET_IMAGE_CONTENT = "getImageContent";
	private static final String VIDEO_HAS_SUBTITLE = "videoHasSubtitles";

	private static final Logger logger = LoggerFactory.getLogger(PublishHelper.class);
	public static final String DESTINATION_PROCESS_ID_LIST = "destinationProcessIdList";

	public static void determineVideoContentList(ProcessContext kcontext) {
		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		Base currentEntity = (Base)kcontext.getVariable(CURRENT_ENTITY_KEY);
		Profile profile = (Profile)kcontext.getVariable(PROFILE_KEY);
		List<VideoContent> approvedVideoContentList = getContentList(publishWrapper.getApprovedEntity(), METHOD_NAME_GET_VIDEO_CONTENT, VideoContent.class);
		List<VideoContent> currentVideoContentList = getContentList(currentEntity, METHOD_NAME_GET_VIDEO_CONTENT, VideoContent.class);
		List<VideoContent> videoContentList = new ArrayList<>();

		if (profile != null && profile.getTermMapList() != null) {
		for (VideoContent currentVideoContent: currentVideoContentList) {
			if (contains(approvedVideoContentList, currentVideoContent)) {
				boolean hasRelevantTermMap = false;
					for (TermMap termMap: profile.getTermMapList()) {
						if (termMap.getContentTypeList().contains(currentVideoContent.getContentType().toString())) {
							for (Device device: termMap.getDeviceList()) {
								if (!device.getProtectList().isEmpty()) {
									hasRelevantTermMap = true;
									break;
								}
							}
							if (hasRelevantTermMap) {
								break;
							}
						}
					}

				if (hasRelevantTermMap) {
					videoContentList.add(currentVideoContent);
				}
			}
		}
	}
		kcontext.setVariable(VIDEO_CONTENT_LIST_KEY, videoContentList);
	}

	private static <T extends BaseEntity> boolean contains(List<T> baseEntityList, T baseEntity) {
		if (baseEntityList == null || baseEntity == null) {
			return false;
		}
		for (BaseEntity baseEntityFromList: baseEntityList) {
			if (baseEntityFromList.getId() != null && baseEntityFromList.getId().equals(baseEntity.getId())) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static void prepareSubcontentProfileRelationshipList(ProcessContext kcontext) {
		List<ProtectVideoSub> protectedVideoSubcontentList = (List<ProtectVideoSub>)kcontext.getVariable(PROTECTED_VIDEO_SUBCONTENT_LIST_KEY);
		kcontext.setVariable(PROTECTED_VIDEO_SUBCONTENT_KEY, protectedVideoSubcontentList.remove(0));
		List<Relationship<?>> relationshipList = new ArrayList<>();
		relationshipList.add(new Relationship<TranscodeProfile>(TRANSCODE_PROFILE_RELATIONSHIP_NAME, TranscodeProfile.class));
		relationshipList.add(new Relationship<ProtectProfile>(PROTECT_PROFILE_RELATIONSHIP_NAME, ProtectProfile.class));
		kcontext.setVariable(RELATIONSHIP_LIST_KEY, relationshipList);
	}

	public static void processVideoContentWrapper(ProcessContext kcontext) {
		List<VideoContent> videoContentList = (List<VideoContent>)kcontext.getVariable(VIDEO_CONTENT_LIST_KEY);
		Profile profile = (Profile)kcontext.getVariable(PROFILE_KEY);
		VideoContent videoContent = videoContentList.remove(0);
		SourceVideoSub sourceVideoSubcontent = getLatestSourceVideoSubcontent(videoContent);
		if (sourceVideoSubcontent != null) {
			List<ProtectVideoSub> protectedVideoSubcontentList = new ArrayList<>(sourceVideoSubcontent.getProtectSubs());
			kcontext.setVariable(PROTECTED_VIDEO_SUBCONTENT_LIST_KEY, protectedVideoSubcontentList);
			kcontext.setVariable(SOURCE_VIDEO_SUBCONTENT_KEY, sourceVideoSubcontent);
			kcontext.setVariable(TERM_MAP_LIST_KEY, new ArrayList<>(profile.getTermMapList()));
			kcontext.setVariable(VIDEO_CONTENT_KEY, videoContent);
		} else {
			kcontext.setVariable(PROTECTED_VIDEO_SUBCONTENT_LIST_KEY, new ArrayList<>());
			kcontext.setVariable(SOURCE_VIDEO_SUBCONTENT_KEY, null);
			kcontext.setVariable(TERM_MAP_LIST_KEY, new ArrayList<>(profile.getTermMapList()));
			kcontext.setVariable(VIDEO_CONTENT_KEY, videoContent);
		}

		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		VideoContentWrapper videoContentWrapper = null;
		for (VideoContentWrapper videoContentWrapperIterator: publishWrapper.getVideoContentWrapperList()) {
			if (videoContentWrapperIterator.getContentId().equals(videoContent.getId())) {
				videoContentWrapper = videoContentWrapperIterator;
				break;
			}
		}
		if (videoContentWrapper == null) {
			videoContentWrapper = new VideoContentWrapper(videoContent.getId());
			if (videoContent.getPublishVersion() == null) {
				videoContentWrapper.setPublishVersion(1);
			} else {
				videoContentWrapper.setPublishVersion(videoContent.getPublishVersion() + 1);
			}
			publishWrapper.getVideoContentWrapperList().add(videoContentWrapper);
		}

		if ( null != videoContent.getSubtitleContent() && videoContent.getSubtitleContent().size() > 0){
			kcontext.setVariable(VIDEO_HAS_SUBTITLE, "true");
		}
	}

	@SuppressWarnings("unchecked")
	public static void processVideoSubContentWrapper(ProcessContext kcontext) {
		Map<String, Object> relationshipMap = (Map<String, Object>)kcontext.getVariable(RELATIONSHIP_MAP_KEY);
		List<TranscodeProfile> transcodeProfileList = (List<TranscodeProfile>)relationshipMap.get(TRANSCODE_PROFILE_RELATIONSHIP_NAME);
		List<ProtectProfile> protectProfileList = (List<ProtectProfile>)relationshipMap.get(PROTECT_PROFILE_RELATIONSHIP_NAME);
		SourceVideoSub sourceVideoSubcontent = (SourceVideoSub)kcontext.getVariable(SOURCE_VIDEO_SUBCONTENT_KEY);
		ProtectVideoSub protectedVideoSubcontent = (ProtectVideoSub)kcontext.getVariable(PROTECTED_VIDEO_SUBCONTENT_KEY);
		VideoContent videoContent = (VideoContent)kcontext.getVariable(VIDEO_CONTENT_KEY);
		List<TermMap> termMapList = (List<TermMap>)kcontext.getVariable(TERM_MAP_LIST_KEY);
		List<TermWrapper> termWrapperList = (List<TermWrapper>)kcontext.getVariable(TERM_WRAPPER_LIST_KEY);

		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		VideoContentWrapper videoContentWrapper = null;
		for (VideoContentWrapper videoContentWrapperIterator: publishWrapper.getVideoContentWrapperList()) {
			if (videoContentWrapperIterator.getContentId().equals(videoContent.getId())) {
				videoContentWrapper = videoContentWrapperIterator;
				break;
			}
		}

		VideoSubcontentWrapper videoSubcontentWrapper = null;
		for (VideoSubcontentWrapper videoSubcontentWrapperIterator: videoContentWrapper.getVideoSubcontentWrapperList()) {
			if (videoSubcontentWrapperIterator.getProtectedVideoSubcontentId().equals(protectedVideoSubcontent.getId())) {
				videoSubcontentWrapper = videoSubcontentWrapperIterator;
				break;
			}
		}
		if (videoSubcontentWrapper == null && sourceVideoSubcontent != null) {
			videoSubcontentWrapper = new VideoSubcontentWrapper(sourceVideoSubcontent.getId(), protectedVideoSubcontent.getId());
			videoContentWrapper.getVideoSubcontentWrapperList().add(videoSubcontentWrapper);
			if (StringUtils.isBlank(protectedVideoSubcontent.getConsumerUrl())) {
				videoContentWrapper.getUnpublishedVideoSubcontentWrapperList().add(videoSubcontentWrapper);
			}
		}

		for (TermMap termMap: termMapList) {
			for (Device device: termMap.getDeviceList()) {
				List<Transcode> transcodeMatchList = new ArrayList<>();
				List<Protect> protectMatchList = new ArrayList<>();
				if (transcodeProfileList != null && device.getProtectList() != null && protectProfileList != null) {
					for (Transcode transcode: device.getTranscodeList()) {
						for (TranscodeProfile transcodeProfile: transcodeProfileList) {
							if (transcodeProfile.getId().equals(transcode.getTranscodeProfileId())) {
								for (Protect protect: device.getProtectList()) {
									for (Protect transcodeProtect: transcode.getProtectList()) {
										if (transcodeProtect.getProtectProfileId().equals(protect.getProtectProfileId())) {
											transcodeMatchList.add(transcode);
										}
									}
									for (ProtectProfile protectProfile: protectProfileList) {
										if (protectProfile.getId().equals(protect.getProtectProfileId())) {
											protectMatchList.add(protect);
										}
									}
								}
							}
						}
					}
				}

				if (!transcodeMatchList.isEmpty() && !protectMatchList.isEmpty()
						&& protectedVideoSubcontent.getProtectPolicyGroupId().equals(termMap.getPolicyGroupId().toString())) {
					for (TermWrapper termWrapper: termWrapperList) {
						if (termWrapper.getTerm().getContractName().equals(termMap.getContractName())) {
							if (!videoContentWrapper.getTermWrapperList().contains(termWrapper)) {
								videoContentWrapper.getTermWrapperList().add(termWrapper);
							}
							if (videoSubcontentWrapper != null
									&& !videoSubcontentWrapper.getTermWrapperList().contains(termWrapper)) {
								videoSubcontentWrapper.getTermWrapperList().add(termWrapper);
							}
						}
					}
					if (!videoContentWrapper.getDeviceList().contains(device)) {
						videoContentWrapper.getDeviceList().add(device);
					}
					if (!videoContentWrapper.getTermMapList().contains(termMap)) {
						videoContentWrapper.getTermMapList().add(termMap);
					}

					if (videoSubcontentWrapper != null) {
						if (!videoSubcontentWrapper.getDeviceList().contains(device)) {
							videoSubcontentWrapper.getDeviceList().add(device);
						}
						if (!videoSubcontentWrapper.getTermMapList().contains(termMap)) {
							videoSubcontentWrapper.getTermMapList().add(termMap);
						}
						for (Transcode transcodeMatch : transcodeMatchList) {
							if (!videoSubcontentWrapper.getTranscodeList().contains(transcodeMatch)) {
								videoSubcontentWrapper.getTranscodeList().add(transcodeMatch);
							}
						}
						for (Protect protectMatch : protectMatchList) {
							if (!videoSubcontentWrapper.getProtectList().contains(protectMatch)) {
								videoSubcontentWrapper.getProtectList().add(protectMatch);
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void processTermToWrapper(ProcessContext kcontext) {
		List<TermWrapper> termWrapperList = (List<TermWrapper>)kcontext.getVariable(TERM_WRAPPER_LIST_KEY);
		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		Profile profile = (Profile)kcontext.getVariable(PROFILE_KEY);

		for (TermWrapper termWrapper: termWrapperList) {
			for (TermMap termMap: profile.getTermMapList()) {
				if (publishWrapper.isValidTermMap(termMap)) {
					if (termWrapper.getTerm().getContractName().equals(termMap.getContractName())) {
						if (!publishWrapper.getTermWrapperList().contains(termWrapper)) {
							publishWrapper.getTermWrapperList().add(termWrapper);
						}
						if (!publishWrapper.getTermMapList().contains(termMap)) {
							publishWrapper.getTermMapList().add(termMap);
						}
						for (Device device: termMap.getDeviceList()) {
							if (!publishWrapper.getDeviceList().contains(device)) {
								publishWrapper.getDeviceList().add(device);
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void checkMissingProtectedContent(ProcessContext kcontext) {
		List<TermMap> termMapList = (List<TermMap>)kcontext.getVariable(TERM_MAP_LIST_KEY);
		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		VideoContent videoContent = (VideoContent) kcontext.getVariable(VIDEO_CONTENT_KEY);

		VideoContentWrapper videoContentWrapper = null;
		for (VideoContentWrapper videoContentWrapperIterator: publishWrapper.getVideoContentWrapperList()) {
			if (videoContentWrapperIterator.getContentId().equals(videoContent.getId())) {
				videoContentWrapper = videoContentWrapperIterator;
				break;
			}
		}

		List<VideoSubcontentWrapper> videoSubcontentWrapperList =
			videoContentWrapper == null ? emptyList(): videoContentWrapper.getVideoSubcontentWrapperList();
		for (TermMap termMap: termMapList) {
			if (termMap.getContentTypeList().contains(videoContent.getContentType().toString())) {
				// Does the videoContentWrapper's subcontent satisfy the term map?
				for (Device device: termMap.getDeviceList()) {
					for (Transcode transcode: device.getTranscodeList()) {
						List<Protect> protectList = intersect(device.getProtectList(), transcode.getProtectList());
						if (!protectList.isEmpty()) {
							for (Protect protect: protectList) {
								// Does this transcode and protect exist as a videosubcontent for this videocontent?
								boolean found = false;
								for (VideoSubcontentWrapper videoSubcontentWrapper: videoSubcontentWrapperList) {
									ProtectVideoSub protectedVideoSubcontent = publishWrapper.getProtectedVideoSubcontentById(videoSubcontentWrapper.getProtectedVideoSubcontentId());
									if (protectedVideoSubcontent != null
											&& videoSubcontentWrapper.getProtectList().contains(protect)
											&& videoSubcontentWrapper.getTranscodeList().contains(transcode)
											&& protectedVideoSubcontent.getProtectPolicyGroupId().equals(termMap.getPolicyGroupId().toString())) {
										found = true;
										break;
									}
								}
								if (!found) {
									if (BooleanUtils.isTrue(protect.getRequired())) {
										logger.warn("No protected content for device {} matches transcode profile {}, protect profile {} , and policy group ID {}.  Protected content is required.", device.getName(), transcode.getName(), protect.getName(), termMap.getPolicyGroupId().toString());
										kcontext.setVariable(HAS_MISSING_REQUIRED_PROTECTED_CONTENT_KEY, true);
									} else {
										logger.warn("No protected content for device {} matches transcode profile {}, protect profile {} , and policy group ID {}.  Protected content is not required, publish continuing.", device.getName(), transcode.getName(), protect.getName(), termMap.getPolicyGroupId().toString());
										kcontext.setVariable(HAS_MISSING_PROTECTED_CONTENT_KEY, true);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static void prepareImagePublish(ProcessContext kcontext) {
		Base currentEntity = (Base)kcontext.getVariable(CURRENT_ENTITY_KEY);
		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		List<ImageContent> approvedImageContentList = getContentList(publishWrapper.getApprovedEntity(), METHOD_NAME_GET_IMAGE_CONTENT, ImageContent.class);
		List<ImageContent> currentImageContentList = getContentList(currentEntity, METHOD_NAME_GET_IMAGE_CONTENT, ImageContent.class);
		publishWrapper.getImageContentWrapperList().clear();
		boolean allImageMediaPresent = true;
		for (ImageContent currentImageContent: currentImageContentList) {
			if (contains(approvedImageContentList, currentImageContent)) {
				ImageContentWrapper imageContentWrapper = new ImageContentWrapper(currentImageContent.getId());
				publishWrapper.getImageContentWrapperList().add(imageContentWrapper);
				if (currentImageContent.getPublishVersion() == null) {
					imageContentWrapper.setPublishVersion(1);
				} else {
					imageContentWrapper.setPublishVersion(currentImageContent.getPublishVersion() + 1);
				}

				ImageContent approvedImageContent = WorkflowHelper.getEntityFromListById(approvedImageContentList, currentImageContent.getId());
				Collection<ImageSubcontent> imageSubcontentList = WorkflowHelper.getLatestImageSubcontentCollection(approvedImageContent);
				if (!imageSubcontentList.isEmpty()) {
					for (ImageSubcontent imageSubcontent: imageSubcontentList) {
						imageContentWrapper.getImageSubcontentIdList().add(imageSubcontent.getId());
						if (StringUtils.isBlank(imageSubcontent.getConsumerUrl())) {
							imageContentWrapper.getUnpublishedImageSubcontentIdList().add(imageSubcontent.getId());
						}
					}
				} else {
					allImageMediaPresent = false;
				}
			}
		}
		if (!allImageMediaPresent){
			logger.warn("One of image media is missing for {} id {}.", currentEntity.getType(), currentEntity.getId());
		}
		kcontext.setVariable(ALL_IMAGE_MEDIA_PRESENT_KEY, allImageMediaPresent);
	}

	public static void prepareSubtitlePublish(ProcessContext kcontext) {
		Base currentEntity = (Base)kcontext.getVariable(CURRENT_ENTITY_KEY);
		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		List<VideoContentWrapper> videoContentWrapperList = publishWrapper.getVideoContentWrapperList();
		boolean allSubtitleMediaPresent = true;
		for (VideoContentWrapper videoContentWrapper: videoContentWrapperList) {
			VideoContent videoContent = publishWrapper.getContentById(videoContentWrapper);
			List<SubtitleContent> currentSubtitleContentList = videoContent.getSubtitleContent();
			publishWrapper.getSubtitleContentWrapperList().clear();
			videoContentWrapper.getSubtitleContentWrapperList().clear();
			for (SubtitleContent currentSubtitleContent: currentSubtitleContentList) {
					SubtitleContentWrapper subtitleContentWrapper = new SubtitleContentWrapper(currentSubtitleContent.getId());
					videoContentWrapper.getSubtitleContentWrapperList().add(subtitleContentWrapper);
					publishWrapper.getSubtitleContentWrapperList().add(subtitleContentWrapper);
					if (currentSubtitleContent.getPublishVersion() == null) {
						subtitleContentWrapper.setPublishVersion(1);
					} else {
						subtitleContentWrapper.setPublishVersion(currentSubtitleContent.getPublishVersion() + 1);
					}
					if(null == currentSubtitleContent.getSubcontent() || currentSubtitleContent.getSubcontent().isEmpty()) {
						allSubtitleMediaPresent = false;
					}
					for (SubtitleSubcontent subtitleSubcontent : currentSubtitleContent.getSubcontent()){
								subtitleContentWrapper.getSubtitleSubcontentIdList().add(subtitleSubcontent.getId());
								if (StringUtils.isBlank(subtitleSubcontent.getConsumerUrl())) {
									subtitleContentWrapper.getUnpublishedSubtitleSubcontentIdList().add(subtitleSubcontent.getId());
								}
					}
			}
		}
		if (!allSubtitleMediaPresent){
			logger.warn("One of subtitle media is missing for {} id {}.", currentEntity.getType(), currentEntity.getId());
		}
		kcontext.setVariable(ALL_SUBTITLE_MEDIA_PRESENT_KEY, allSubtitleMediaPresent);
	}

	@SuppressWarnings("unchecked")
	private static <T extends Content> List<T> getContentList(Base entity, String contentGetterMethodName, Class<T> contentClass) {
		try {
			Method contentGetterMethod = entity.getClass().getMethod(contentGetterMethodName);
			return (List<T>)contentGetterMethod.invoke(entity);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
		}
		return new ArrayList<>();
	}

	public static boolean readSchedule(ProcessContext kcontext) {
		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		return publishWrapper.getApprovedEntity() instanceof ScheduleSlot;
	}

	public static boolean readContent(ProcessContext kcontext) {
		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		return publishWrapper.getApprovedEntity() instanceof Event || publishWrapper.getApprovedEntity() instanceof Program;
	}

	/**
	 * @deprecated use {@link #populateEnabledEndpoints(ProcessContext, String)} instead.
	 */
	public static void configureEndpoints(ProcessContext kcontext) {
		populateEnabledEndpoints(kcontext, PUBLISH_WRAPPER_KEY);
	}

	/**
	 * Filters out the endpoints, which are not enabled.
	 * @param kcontext context to be used
	 * @param wrapperKey key to retrieve the wrapper instance.
	 */
	public static void populateEnabledEndpoints(ProcessContext kcontext, String wrapperKey) {
		WrapperWithDestinations<?> wrapper = (WrapperWithDestinations<?>) kcontext.getVariable(wrapperKey);
		List<String> destinationProcessIdList = wrapper.getDestinationProcessIdList();
		if (destinationProcessIdList == null || destinationProcessIdList.isEmpty()) {
			kcontext.setVariable(DESTINATION_PROCESS_ID_LIST, emptyList());
		} else {
			List<String> enabledProcessIds = destinationProcessIdList.stream().filter(processId -> {
				try {
					return StringUtils.equalsIgnoreCase(propertiesManager.getProperty(processId), ENABLED);
				} catch (PropertyException e) {
					return false;
				}
			}).collect(toList());
			kcontext.setVariable(DESTINATION_PROCESS_ID_LIST, enabledProcessIds);
		}
	}


}
