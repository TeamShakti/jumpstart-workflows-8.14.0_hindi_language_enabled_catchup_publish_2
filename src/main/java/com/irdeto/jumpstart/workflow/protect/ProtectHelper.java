package com.irdeto.jumpstart.workflow.protect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.domain.mediamanager.command.MaintainRelationshipsCommand;
import com.irdeto.domain.operation.MM8Response;
import com.irdeto.domain.qts.response.QTSFileInfo;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.ProtectProfile;
import com.irdeto.jumpstart.domain.ProtectVideoSub;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.TransVideoSub;
import com.irdeto.jumpstart.domain.TranscodeProfile;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.VideoSubcontent.FrameRate;
import com.irdeto.jumpstart.domain.config.Device;
import com.irdeto.jumpstart.domain.config.Encode;
import com.irdeto.jumpstart.domain.config.Profile;
import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.config.Transcode;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.protect.ProtectWrapper;
import com.irdeto.jumpstart.factory.FactoryHelper;
import com.irdeto.jumpstart.workflow.FilenameSegments;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;

public class ProtectHelper extends WorkflowHelper {

	private static final String PROGRAM_TITLE_KEY = "programTitle";
	private static final String PROTECT_WRAPPER_LIST_KEY = "protectWrapperList";
	private static final String PROTECTED_SUBCONTENT_KEY = "protectedSubcontent";
	private static final String TRANSCODED_SUBCONTENT_KEY = "transcodedSubcontent";
	private static final String TRANSCODE_PROFILE_LIST_KEY = "transcodeProfileList";
	private static final String MAINTAIN_RELATIONSHIPS_LIST_KEY = "maintainRelationshipsCommandsList";
	private static final String PROTECT_WRAPPER_KEY = "protectWrapper";
	private static final String FILE_INFO_KEY = "fileInfo";
	private static final String PROFILE_KEY = "profile";
	private static final String PROTECTED_SUBCONTENT_LIST_KEY = "protectedSubcontentList";
	private static final String PROCESSED_VIDEO_CONTENT_ID_LIST_KEY = "processedVideoContentIdList";
	private static final String VIDEO_CONTENT_ID_KEY = "videoContentId";
	private static final String PROCESS_INSTANCE_ID_LIST_KEY = "processInstanceIdList";
	private static final String CONTENT_TYPE_KEY = "contentType";
	private static final String SOURCE_SEGMENTS_KEY = "sourceSegments";
	private static final String URI_ID_KEY = "uriId";
	private static final String SOURCE_VERSION_KEY = "sourceVersion";

	private static final Logger logger = LoggerFactory.getLogger(ProtectHelper.class);

	private static final Map<String, DRMProfile> profileMap = new HashMap<>();

	static {
		FactoryHelper.streamClasses(DRMProfile.class).forEach(clazz ->{
			try {
				DRMProfile profile = (DRMProfile) clazz.getConstructor().newInstance();
				logger.debug("Adding protect profile {}.", clazz.getCanonicalName());
				profileMap.put(profile.getProfileName(), profile);
			} catch (NoSuchMethodException | SecurityException
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				logger.error("Unable to add profile {}", clazz.getCanonicalName());
			}
		});
	}

	public static DRMProfile getProfile(String profileName) {
		DRMProfile profile = profileMap.get(profileName);
		if (profile == null) {
			logger.error("Profile name: {} is not in the profile map in ProtectHelper.", profileName);
		}
		return profile;
	}

	public static void protectPrepare(ProcessContext kcontext) {
		kcontext.setVariable(PROGRAM_TITLE_KEY, ProtectHelper.getProgramTitle(kcontext));
		kcontext.setVariable(PROCESSED_VIDEO_CONTENT_ID_LIST_KEY, new ArrayList<>());
		Program program = (Program) kcontext.getVariable(PROGRAM_KEY);
		kcontext.setVariable(URI_ID_KEY, program.getUriId());
	}

	public static DRMProfile getProfile(ProtectWrapper protectWrapper) {
		return getProfile(protectWrapper.getRepresentativeProtect().getProtectionType());
	}

	public static DRMProfile getProfile(Protect protect) {
		return getProfile(protect.getProtectionType());
	}

	public static DRMProfile getProfileList(Encode encode) {
		return getProfile(encode.getProtectionType());
	}

	public static void setupProtectWrapperList(ProcessContext kcontext) {
		Profile profile = (Profile) kcontext.getVariable(PROFILE_KEY);
		kcontext.setVariable(PROFILE_KEY, null);
		List<ProtectWrapper> protectWrapperList = new ArrayList<>();

		for (TermMap termMap : profile.getTermMapList()) {
			for (Device device : termMap.getDeviceList()) {
				for (Transcode transcode : device.getTranscodeList()) {
					List<Protect> protectList = intersect(device.getProtectList(), transcode.getProtectList());
					if (protectList.isEmpty()) {
						continue;
					}
					for (Protect protect : protectList) {
						if (getProfile(protect) != null) {
							String protectKey = getProtectKey(protect);
							ProtectWrapper matchingProtectWrapper = null;
							for (ProtectWrapper protectWrapper : protectWrapperList) {
								if (transcode.equals(protectWrapper.getTranscode())
										&& protectKey.equals(protectWrapper.getProtectKey())
										&& termMap.getPolicyGroupId().equals(protectWrapper.getPolicyGroupId())) {
									matchingProtectWrapper = protectWrapper;
									break;
								}
							}
							if (matchingProtectWrapper == null) {
								matchingProtectWrapper = new ProtectWrapper();
								matchingProtectWrapper.setPolicyGroupId(termMap.getPolicyGroupId());
								matchingProtectWrapper.setTranscode(transcode);
								protectWrapperList.add(matchingProtectWrapper);
							}
							if (!matchingProtectWrapper.getProtectGroup().contains(protect)) {
								matchingProtectWrapper.getProtectGroup().add(protect);
							}
						}
					}
				}
			}
		}

		kcontext.setVariable(PROTECT_WRAPPER_LIST_KEY, protectWrapperList);

	}

	@SuppressWarnings("unchecked")
	public static void prepareProtectVariables(ProcessContext kcontext) {
		Program program = (Program) kcontext.getVariable(PROGRAM_KEY);
		List<String> processedVideoContentIdList = (List<String>) kcontext.getVariable(PROCESSED_VIDEO_CONTENT_ID_LIST_KEY);
		VideoContent videoContent = null;
		for (VideoContent iteratingVideoContent : program.getVideoContent()) {
			if (!processedVideoContentIdList.contains(iteratingVideoContent.getId())) {
				videoContent = iteratingVideoContent;
				break;
			}
		}

		if (videoContent != null) {
			kcontext.setVariable(VIDEO_CONTENT_ID_KEY, videoContent.getId());
			kcontext.setVariable(SOURCE_VERSION_KEY, videoContent.getSourceVersion());
			kcontext.setVariable(CONTENT_TYPE_KEY, videoContent.getContentType().toString());
			SourceVideoSub sourceVideoSubcontent = getLatestSourceVideoSubcontent(videoContent);
			kcontext.setVariable(SOURCE_SUBCONTENT_KEY, sourceVideoSubcontent);

			if (sourceVideoSubcontent != null) {
				kcontext.setVariable(SOURCE_SEGMENTS_KEY, new FilenameSegments(sourceVideoSubcontent.getSourcePath()));
				List<Integer> transcodedVideoSubcontentList = new ArrayList<>();
				for (int i = 0; i < sourceVideoSubcontent.getTransSubs().size(); i++) {
					TransVideoSub transcodedVideoSubcontent = sourceVideoSubcontent.getTransSubs().get(i);
					if (transcodedVideoSubcontent.getId() != null) {
						transcodedVideoSubcontentList.add(i);
					}
				}
				kcontext.setVariable(TRANSCODED_SUBCONTENT_LIST_KEY, transcodedVideoSubcontentList);

				List<Integer> protectedVideoSubcontentList = new ArrayList<>();
				for (int i = 0; i < sourceVideoSubcontent.getProtectSubs().size(); i++) {
					ProtectVideoSub protectedVideoSubcontent = sourceVideoSubcontent.getProtectSubs().get(i);
					if (protectedVideoSubcontent.getId() != null) {
						protectedVideoSubcontentList.add(i);
					}
				}
				kcontext.setVariable(PROTECTED_SUBCONTENT_LIST_KEY, protectedVideoSubcontentList);
			} else {
				kcontext.setVariable(SOURCE_SEGMENTS_KEY, null);
				kcontext.setVariable(SOURCE_SUBCONTENT_KEY, null);
				kcontext.setVariable(TRANSCODED_SUBCONTENT_LIST_KEY, null);
				kcontext.setVariable(PROTECTED_SUBCONTENT_LIST_KEY, null);
				kcontext.setVariable(CONTENT_TYPE_KEY, null);
			}
		} else {
			kcontext.setVariable(SOURCE_SEGMENTS_KEY, null);
			kcontext.setVariable(SOURCE_SUBCONTENT_KEY, null);
			kcontext.setVariable(TRANSCODED_SUBCONTENT_LIST_KEY, null);
			kcontext.setVariable(PROTECTED_SUBCONTENT_LIST_KEY, null);
			kcontext.setVariable(CONTENT_TYPE_KEY, null);
		}
	}

	public static boolean isDownloadProtectProfile(Protect protect) {
		return getProfile(protect).isDownloadProtectProfile();
	}

	@SuppressWarnings("unchecked")
	public static boolean isAlreadyProtecting(ProcessContext kcontext) {
		List<String> processInstanceIdList = (List<String>) kcontext.getVariable(PROCESS_INSTANCE_ID_LIST_KEY);
		boolean inProgress = false;
		if (processInstanceIdList != null && !processInstanceIdList.isEmpty()) {
			for (String processInstanceId : processInstanceIdList) {
				if (!processInstanceId.equals(String.valueOf(kcontext.getProcessInstance().getId()))) {
					inProgress = true;
				}
			}
		}
		return inProgress;
	}

	@SuppressWarnings("unchecked")
	public static void setSourceFileList(ProcessContext kcontext) {
		MM8Response response = (MM8Response) kcontext.getVariable(MM8_RESPONSE_KEY);
		List<TranscodeProfile> transcodeProfileList = (List<TranscodeProfile>) getEntityList(TranscodeProfile.class, response);
		TransVideoSub transcodedSubcontent = (TransVideoSub) kcontext.getVariable(TRANSCODED_SUBCONTENT_KEY);

		List<ProtectWrapper> protectWrapperList = (List<ProtectWrapper>) kcontext.getVariable(PROTECT_WRAPPER_LIST_KEY);
		for (ProtectWrapper protectWrapper : protectWrapperList) {
			boolean found = false;
			for (TranscodeProfile transcodeProfile : transcodeProfileList) {
				if (transcodeProfile.getId().equals(protectWrapper.getTranscode().getTranscodeProfileId())) {
					found = true;
					break;
				}
			}
			if (found) {
				if (!protectWrapper.getSourceFileList().contains(transcodedSubcontent.getSourcePath())) {
					protectWrapper.getSourceFileList().add(transcodedSubcontent.getSourcePath());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void setAdditionalSourceFiles(ProcessContext kcontext) {
		List<ProtectWrapper> protectWrapperList = (List<ProtectWrapper>) kcontext.getVariable(PROTECT_WRAPPER_LIST_KEY);
		SourceVideoSub sourceVideoSubcontent = (SourceVideoSub) kcontext.getVariable(SOURCE_SUBCONTENT_KEY);
		String sourcePath = sourceVideoSubcontent.getSourcePath();
		// E.g. ism and ismc
		for (ProtectWrapper protectWrapper : protectWrapperList) {
			List<String> list = protectWrapper.getSourceFileList();
			if (!list.isEmpty()) {
				list.addAll(getProfile(protectWrapper).getAdditionalSourceFiles(sourcePath));
			}
		}

	}

	@SuppressWarnings("unchecked")
	public static void removeUnneededProtectWrappers(ProcessContext kcontext) {
		List<ProtectWrapper> protectWrapperList = (List<ProtectWrapper>) kcontext.getVariable(PROTECT_WRAPPER_LIST_KEY);
		Iterator<ProtectWrapper> protectWrapperIterator = protectWrapperList.iterator();
		while (protectWrapperIterator.hasNext()) {
			ProtectWrapper protectWrapper = protectWrapperIterator.next();
			if (protectWrapper.getSourceFileList().isEmpty()
					|| protectWrapper.getPolicyGroupId() == null
					|| protectWrapper.getProtectGroup().isEmpty()
					|| protectWrapper.getTranscode() == null) {
				protectWrapperIterator.remove();

			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void determineProtectProfileSubcontentMap(ProcessContext kcontext) {
		List<ProtectWrapper> protectWrapperList = (List<ProtectWrapper>) kcontext.getVariable(PROTECT_WRAPPER_LIST_KEY);
		List<TranscodeProfile> pTranscodeProfileList =
				(List<TranscodeProfile>) kcontext.getVariable(TRANSCODE_PROFILE_LIST_KEY);
		MM8Response response = (MM8Response) kcontext.getVariable(MM8_RESPONSE_KEY);
		List<ProtectProfile> pProtectProfileList = getEntityList(ProtectProfile.class, response);
		ProtectVideoSub protectedVideoSubcontent = (ProtectVideoSub) kcontext.getVariable(PROTECTED_SUBCONTENT_KEY);

		// remove any protect wrappers with nothing to do
		// remove any protect wrappers where all we have to do is add relationships
		// add relationships
		// set the new arraylist of files into protect onto the protectwrappers in the protect wrapper list
		List<ProtectWrapper> processedProtectWrapperList = new ArrayList<>();
		Iterator<ProtectWrapper> protectWrapperIterator = protectWrapperList.iterator();
		while (protectWrapperIterator.hasNext()) {
			ProtectWrapper protectWrapper = protectWrapperIterator.next();
			// There is no matching transcoded subcontent, so nothing to do for this protectWrapper
			if (protectWrapper.getSourceFileList() == null || protectWrapper.getSourceFileList().isEmpty()) {
				protectWrapperIterator.remove();
				continue;
			}
			if (!protectWrapper.getPolicyGroupId().toString()
					.equals(protectedVideoSubcontent.getProtectPolicyGroupId())) {
				continue;
			}
			// If this protected content has a matching transcode profile for this protectWrapper,
			// and contains all the protect profile relationships, then nothing to do for this protectWrapper
			boolean removeFlag = false;
			for (TranscodeProfile existingTranscodeProfile : pTranscodeProfileList) {
				if (!existingTranscodeProfile.getId().equals(protectWrapper.getTranscode().getTranscodeProfileId())) {
					continue;
				}
				for (ProtectProfile existingProtectProfile : pProtectProfileList) {
					if (getProtectKey(existingProtectProfile).equals(protectWrapper.getProtectKey())) {
						protectWrapperIterator.remove();
						Iterator<Protect> protectIterator = protectWrapper.getProtectGroup().iterator();
						while (protectIterator.hasNext()) {
							Protect protect = protectIterator.next();
							if (protect.getProtectProfileId().equals(existingProtectProfile.getId())) {
								protectIterator.remove();
								continue;
							}
							// Discard Protectwrappers with insufficient numbers of source files
							// according transcoded file count.
							Integer count = protectWrapper.getTranscode().getTranscodedFileCount();
							if (count != null) {
								if (count > protectWrapper.getSourceFileList().size()) {
									protectIterator.remove();
								}
							}
						}
						if (!processedProtectWrapperList.contains(protectWrapper)) {
							processedProtectWrapperList.add(protectWrapper);
						}
						removeFlag = true;
						break;
					}
				}
				if (removeFlag) break;
			}
		}

		List<String> missingProtectProfileIdList = new ArrayList<>();
		for (ProtectWrapper protectWrapper : processedProtectWrapperList) {
			for (Protect protect : protectWrapper.getProtectGroup()) {
				missingProtectProfileIdList.add(protect.getProtectProfileId());
			}
		}

		List<MaintainRelationshipsCommand> maintainRelationshipsList = new ArrayList<>();
		if (!missingProtectProfileIdList.isEmpty()) {
			maintainRelationshipsList.add(
					new MaintainRelationshipsCommand(
							WorkflowHelper.getEntityType(ProtectVideoSub.class),
							protectedVideoSubcontent.getId(),
							PROTECT_PROFILE_RELATIONSHIP_NAME,
							WorkflowHelper.getEntityType(ProtectProfile.class),
							ATTRIBUTE_NAME_ID,
							missingProtectProfileIdList,
							MaintainRelationshipsCommand.Action.ADD
					)
			);
		}
		kcontext.setVariable(MAINTAIN_RELATIONSHIPS_LIST_KEY, maintainRelationshipsList);
	}

	public static void updateProgramProtected(ProcessContext kcontext) {
		Program program = (Program) kcontext.getVariable(PROGRAM_KEY);
		String videoContentId = (String) kcontext.getVariable(VIDEO_CONTENT_ID_KEY);
		ProtectWrapper protectWrapper = (ProtectWrapper) kcontext.getVariable(PROTECT_WRAPPER_KEY);
		SourceVideoSub sourceVideoSubcontent = (SourceVideoSub) kcontext.getVariable(SOURCE_SUBCONTENT_KEY);
		QTSFileInfo fileInfo = (QTSFileInfo) kcontext.getVariable(FILE_INFO_KEY);

		SourceVideoSub upToDateSourceVideoSubcontent = findSourceVideoSubcontentByEntityId(program, videoContentId,
				sourceVideoSubcontent.getId());
		List<MaintainRelationshipsCommand> maintainRelationshipsList = new ArrayList<>();
		if (upToDateSourceVideoSubcontent != null) {
			ProtectVideoSub subcontent = new ProtectVideoSub();
			try {
				subcontent.setFrameRate(FrameRate.fromValue(fileInfo.getFps()));
			} catch (Exception e) {
			}
			if (StringUtils.isNotEmpty(fileInfo.getFileSize())) {
				subcontent.setContentFileSize(fileInfo.getFileSize());
			}
			String sourcePath = getProfile(protectWrapper).getProtectedSourcePath(fileInfo.getName());
			subcontent.setSourcePath(sourcePath);
			subcontent.setProtectPolicyGroupId(String.valueOf(protectWrapper.getPolicyGroupId()));
			upToDateSourceVideoSubcontent.getProtectSubs().add(subcontent);

			List<String> sourcePathList = new ArrayList<>();
			sourcePathList.add(sourcePath);
			maintainRelationshipsList.add(
					new MaintainRelationshipsCommand(
							WorkflowHelper.getEntityType(TranscodeProfile.class),
							protectWrapper.getTranscode().getTranscodeProfileId(),
							PROTECTED_VIDEO_SUBCONTENT_RELATIONSHIP_NAME,
							WorkflowHelper.getEntityType(ProtectVideoSub.class),
							ATTRIBUTE_NAME_SOURCE_PATH,
							sourcePathList,
							MaintainRelationshipsCommand.Action.ADD
					)
			);
			for (Protect protect : protectWrapper.getProtectGroup()) {
				maintainRelationshipsList.add(
						new MaintainRelationshipsCommand(
								WorkflowHelper.getEntityType(ProtectProfile.class),
								protect.getProtectProfileId(),
								PROTECTED_VIDEO_SUBCONTENT_RELATIONSHIP_NAME,
								WorkflowHelper.getEntityType(ProtectVideoSub.class),
								ATTRIBUTE_NAME_SOURCE_PATH,
								sourcePathList,
								MaintainRelationshipsCommand.Action.ADD
						)
				);
			}
		}
		kcontext.setVariable(MAINTAIN_RELATIONSHIPS_LIST_KEY, maintainRelationshipsList);
	}

	public static String getImageCdnPrefix() {
		try {
			return BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CDN_HLS_PREFIX);
		} catch (PropertyException e) {
		}
		return null;
	}

	public static String getVideoCdnPrefix(Protect protect) {
		return getProfile(protect).getVideoCdnPrefix();
	}

	public static String getCdnUrlSuffix(Protect protect) {
		return getProfile(protect).getCdnUrlSuffix();
	}

	@SuppressWarnings("unchecked")
	public static void setProcessed(ProcessContext kcontext) {
		String videoContentId = (String) kcontext.getVariable(VIDEO_CONTENT_ID_KEY);
		List<String> processedVideoContentIdList = (List<String>) kcontext.getVariable(PROCESSED_VIDEO_CONTENT_ID_LIST_KEY);
		if (!processedVideoContentIdList.contains(videoContentId)) {
			processedVideoContentIdList.add(videoContentId);
		}
	}

	@SuppressWarnings("unchecked")
	public static boolean moreToProtect(ProcessContext kcontext) {
		Program program = (Program) kcontext.getVariable(PROGRAM_KEY);
		List<String> processedVideoContentIdList = (List<String>) kcontext.getVariable(PROCESSED_VIDEO_CONTENT_ID_LIST_KEY);
		for (VideoContent videoContent : program.getVideoContent()) {
			if (!processedVideoContentIdList.contains(videoContent.getId())) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static void sortProtectWrappers(ProcessContext kcontext) {
		List<ProtectWrapper> protectWrapperList = (List<ProtectWrapper>) kcontext.getVariable(PROTECT_WRAPPER_LIST_KEY);
		Collections.sort(protectWrapperList, (o1, o2) -> getProfile(o1).getOrder() - getProfile(o2).getOrder());
	}
}
