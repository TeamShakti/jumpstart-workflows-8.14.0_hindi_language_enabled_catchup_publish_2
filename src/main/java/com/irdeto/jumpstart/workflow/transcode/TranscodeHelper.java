package com.irdeto.jumpstart.workflow.transcode;

import java.util.ArrayList;
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
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.TransVideoSub;
import com.irdeto.jumpstart.domain.TranscodeProfile;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.VideoSubcontent.FrameRate;
import com.irdeto.jumpstart.domain.config.Profile;
import com.irdeto.jumpstart.domain.config.Transcode;
import com.irdeto.jumpstart.domain.transcode.TranscodeWrapper;
import com.irdeto.jumpstart.workflow.FileHelper.FileTranscodeHelper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class TranscodeHelper extends WorkflowHelper {
	private static final String PROGRAM_TITLE_KEY = "programTitle";
	private static final String SOURCE_FILE_KEY = "sourceFile";
	private static final String TARGET_PATH_KEY = "targetPath";
	private static final String TRANSCODED_FILE_INFO_LIST_KEY = "transcodedFileInfoList";
	private static final String MAINTAIN_RELATIONSHIPS_LIST_KEY = "maintainRelationshipsCommandsList";
	private static final String TRANSCODED_SUBCONTENT_KEY = "transcodedSubcontent";
	private static final String PROCESSED_VIDEO_CONTENT_ID_LIST_KEY = "processedVideoContentIdList";
	private static final String PROFILE_KEY = "profile";
	private static final String TRANSCODE_WRAPPER_LIST_KEY = "transcodeWrapperList";
	private static final String TRANSCODE_WRAPPER_KEY = "transcodeWrapper";
	private static final String MAINTAIN_RELATIONSHIPS_KEY = "maintainRelationshipsCommand";
	private static final String VIDEO_CONTENT_ID_KEY = "videoContentId";
	private static final String SOURCE_PATH_KEY = "sourcePath";
	private static final String PROCESS_INSTANCE_ID_LIST_KEY = "processInstanceIdList";
	private static final String CONTENT_TYPE_KEY = "contentType";

	private static final Logger logger = LoggerFactory.getLogger(TranscodeHelper.class);

	public static void prepareTranscode(ProcessContext kcontext) {
		kcontext.setVariable(PROGRAM_TITLE_KEY, TranscodeHelper.getProgramTitle(kcontext));
		kcontext.setVariable(PROCESSED_VIDEO_CONTENT_ID_LIST_KEY, new ArrayList<>());
	}

	@SuppressWarnings("unchecked")
	public static void determineExistingTranscodeProfileList(ProcessContext kcontext) {
		Profile profile = (Profile) kcontext.getVariable(PROFILE_KEY);
		List<TranscodeWrapper> transcodeWrapperList = (List<TranscodeWrapper>) kcontext.getVariable(TRANSCODE_WRAPPER_LIST_KEY);

		TransVideoSub transcodedSubcontent = (TransVideoSub) kcontext.getVariable(TRANSCODED_SUBCONTENT_KEY);
		MM8Response response = (MM8Response) kcontext.getVariable(MM8_RESPONSE_KEY);
		List<TranscodeProfile> existingTranscodeProfileList = (List<TranscodeProfile>) TranscodeHelper.getEntityList(TranscodeProfile.class, response);
		List<Transcode> existingTranscodeList = new ArrayList<>();

		kcontext.setVariable(MAINTAIN_RELATIONSHIPS_KEY, processExistingRenditions(transcodedSubcontent, existingTranscodeProfileList, profile.getTranscodeList(), existingTranscodeList));
		removeCompletedTranscodeWrappers(transcodeWrapperList, existingTranscodeList);
	}

	private static void removeCompletedTranscodeWrappers(
			List<TranscodeWrapper> transcodeWrapperList,
			List<Transcode> existingTranscodeList) {
		// For each existing transcode profile, look through the master transcode profile list and determine if the group is already transcoded.  If so, create any
		// missing relationships, and remove the group from the master list copy.
		List<TranscodeWrapper> processedTranscodeWrapperList = new ArrayList<>();
		Iterator<TranscodeWrapper> transcodeWrapperIterator = transcodeWrapperList.iterator();
		while (transcodeWrapperIterator.hasNext()) {
			TranscodeWrapper transcodeWrapper = transcodeWrapperIterator.next();
			boolean transcodeWrapperIsRemoved = false;
			for (Transcode existingTranscode : existingTranscodeList) {
				if (transcodeWrapper.getTranscodeKey().equals(getTranscodeKey(existingTranscode))) {
					// Content has already been transcoded in the appropriate way.  Remove this transcode profile from the wrapper, add the wrapper to the processed
					// wrapper list.
					if (!transcodeWrapperIsRemoved) {
						transcodeWrapperIterator.remove();
						transcodeWrapperIsRemoved = true;
					}
					Iterator<Transcode> transcodeIterator = transcodeWrapper.getTranscodeGroup().iterator();
					while (transcodeIterator.hasNext()) {
						Transcode transcode = transcodeIterator.next();
						if (transcode.getTranscodeProfileId().equals(existingTranscode.getTranscodeProfileId())) {
							transcodeIterator.remove();
						}
					}
					if (!processedTranscodeWrapperList.contains(transcodeWrapper)) {
						processedTranscodeWrapperList.add(transcodeWrapper);
					}
				}
			}
		}
	}

	private static MaintainRelationshipsCommand processExistingRenditions(TransVideoSub transcodedSubcontent, List<TranscodeProfile> existingTranscodeProfileList, List<Transcode> allTranscodeList, List<Transcode> existingTranscodeList) {
		List<String> missingTranscodeProfileIdList = new ArrayList<>();

		for (Transcode transcode : allTranscodeList) {
			if (transcodedSubcontent.getSourcePath().matches(transcode.getTranscodedFilePattern())) {
				boolean exists = false;
				for (TranscodeProfile transcodeProfile : existingTranscodeProfileList) {
					if (transcodeProfile.getId().equals(transcode.getTranscodeProfileId())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					missingTranscodeProfileIdList.add(transcode.getTranscodeProfileId());
				}
				existingTranscodeList.add(transcode);
			}
		}

		return new MaintainRelationshipsCommand(
				WorkflowHelper.getEntityType(TransVideoSub.class),
				transcodedSubcontent.getId(),
				TRANSCODE_PROFILE_RELATIONSHIP_NAME,
				WorkflowHelper.getEntityType(TranscodeProfile.class),
				ATTRIBUTE_NAME_ID,
				missingTranscodeProfileIdList,
				MaintainRelationshipsCommand.Action.ADD
		);
	}

	public static void setupTranscodeWrapperList(ProcessContext kcontext) {
		Profile profile = (Profile) kcontext.getVariable(PROFILE_KEY);

		Map<String, TranscodeWrapper> transcodeWrapperMap = new HashMap<>();

		for (Transcode transcode : profile.getTranscodeList()) {
			String transcodeKey = getTranscodeKey(transcode);
			TranscodeWrapper transcodeWrapper = transcodeWrapperMap.get(transcodeKey);
			if (transcodeWrapper == null) {
				transcodeWrapper = new TranscodeWrapper();
			}
			if (!transcodeWrapper.getTranscodeGroup().contains(transcode)) {
				transcodeWrapper.getTranscodeGroup().add(transcode);
				transcodeWrapperMap.put(transcodeKey, transcodeWrapper);
			}
		}

		kcontext.setVariable(TRANSCODE_WRAPPER_LIST_KEY, new ArrayList<>(transcodeWrapperMap.values()));
	}

	@SuppressWarnings("unchecked")
	public static void prepareTranscodeVariables(ProcessContext kcontext) {
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
			kcontext.setVariable(CONTENT_TYPE_KEY, videoContent.getContentType().toString());
			SourceVideoSub sourceVideoSubcontent = getLatestSourceVideoSubcontent(videoContent);
			kcontext.setVariable(SOURCE_SUBCONTENT_KEY, sourceVideoSubcontent);
			if (sourceVideoSubcontent != null && StringUtils.isNotBlank(sourceVideoSubcontent.getSourcePath()) && !StringUtils.endsWith(sourceVideoSubcontent.getSourcePath(), SLASH)) {
				kcontext.setVariable(SOURCE_PATH_KEY, sourceVideoSubcontent.getSourcePath());

				List<Integer> transcodedVideoSubcontentList = new ArrayList<>();
				for (int i = 0; i < sourceVideoSubcontent.getTransSubs().size(); i++) {
					TransVideoSub transcodedVideoSubcontent = sourceVideoSubcontent.getTransSubs().get(i);
					if (transcodedVideoSubcontent.getId() != null) {
						transcodedVideoSubcontentList.add(i);
					}
				}
				kcontext.setVariable(TRANSCODED_SUBCONTENT_LIST_KEY, transcodedVideoSubcontentList);
				kcontext.setVariable(SOURCE_FILE_KEY, FileTranscodeHelper.getTranscodeSourceFilename(sourceVideoSubcontent));
				kcontext.setVariable(TARGET_PATH_KEY, FileTranscodeHelper.getTranscodeTargetPath());
			} else {
				if (sourceVideoSubcontent != null &&
						(StringUtils.isBlank(sourceVideoSubcontent.getSourcePath()) || StringUtils.endsWith(sourceVideoSubcontent.getSourcePath(), SLASH))) {
					logger.warn("Skipping video content {} - no source subcontent/invalid source path.", videoContent.getId());
				}
				kcontext.setVariable(SOURCE_SUBCONTENT_KEY, null);
				kcontext.setVariable(SOURCE_PATH_KEY, null);
				kcontext.setVariable(CONTENT_TYPE_KEY, null);
			}
		} else {
			kcontext.setVariable(SOURCE_SUBCONTENT_KEY, null);
			kcontext.setVariable(SOURCE_PATH_KEY, null);
			kcontext.setVariable(CONTENT_TYPE_KEY, null);
		}
	}

	@SuppressWarnings("unchecked")
	public static void updateProgramTranscoded(ProcessContext kcontext) {
		List<QTSFileInfo> transcodedFileInfoList = (List<QTSFileInfo>) kcontext.getVariable(TRANSCODED_FILE_INFO_LIST_KEY);
		TranscodeWrapper transcodeWrapper = (TranscodeWrapper) kcontext.getVariable(TRANSCODE_WRAPPER_KEY);
		Program program = (Program) kcontext.getVariable(PROGRAM_KEY);
		SourceVideoSub sourceSubcontent = (SourceVideoSub) kcontext.getVariable(SOURCE_SUBCONTENT_KEY);
		String videoContentId = (String) kcontext.getVariable(VIDEO_CONTENT_ID_KEY);
		processFileInfoToSubcontent(transcodedFileInfoList, program, sourceSubcontent, videoContentId);

		List<MaintainRelationshipsCommand> transcodeProfileRelationshipsList = getTranscodeProfileRelationshipsList(
				transcodedFileInfoList,
				transcodeWrapper
		);
		kcontext.setVariable(MAINTAIN_RELATIONSHIPS_LIST_KEY, transcodeProfileRelationshipsList);
	}

	private static void processFileInfoToSubcontent(
			List<QTSFileInfo> transcodedFileInfoList, Program program,
			SourceVideoSub sourceSubcontent, String videoContentId) {
		SourceVideoSub upToDateSourceVideoSubcontent = findSourceVideoSubcontentByEntityId(program, videoContentId, sourceSubcontent.getId());
		for (QTSFileInfo fileInfo : transcodedFileInfoList) {

			TransVideoSub transcodedVideoSubcontent = new TransVideoSub();
			try {
				transcodedVideoSubcontent.setFrameRate(FrameRate.fromValue(fileInfo.getFps()));
			} catch (Exception e) {
			}
			if (StringUtils.isNotEmpty(fileInfo.getFileSize())) {
				transcodedVideoSubcontent.setContentFileSize(fileInfo.getFileSize());
			}
			String sourcePath = FileTranscodeHelper.convertPrefixOfTranscodedSourcePath(fileInfo.getName());
			transcodedVideoSubcontent.setSourcePath(sourcePath);
			upToDateSourceVideoSubcontent.getTransSubs().add(transcodedVideoSubcontent);
		}
	}

	protected static List<MaintainRelationshipsCommand> getTranscodeProfileRelationshipsList(
			List<QTSFileInfo> transcodedFileInfoList,
			TranscodeWrapper transcodeWrapper) {
		List<MaintainRelationshipsCommand> transcodeProfileRelationshipsList = new ArrayList<>();
		for (Transcode transcode : transcodeWrapper.getTranscodeGroup()) {
			if (transcode.getTranscodedFilePattern() == null) {
				continue;
			}
			List<String> sourcePathList = new ArrayList<>();

			for (QTSFileInfo fileInfo : transcodedFileInfoList) {
				if (fileInfo.getName() != null && fileInfo.getName().matches(transcode.getTranscodedFilePattern())) {
					String sourcePath = FileTranscodeHelper.convertPrefixOfTranscodedSourcePath(fileInfo.getName());
					sourcePathList.add(sourcePath);
				}
			}
			if (!sourcePathList.isEmpty()) {
				MaintainRelationshipsCommand transcodeProfileRelationships =
						new MaintainRelationshipsCommand(
								WorkflowHelper.getEntityType(TranscodeProfile.class),
								transcode.getTranscodeProfileId(),
								WorkflowHelper.TRANSCODED_VIDEO_SUBCONTENT_RELATIONSHIP_NAME,
								WorkflowHelper.getEntityType(TransVideoSub.class),
								ATTRIBUTE_NAME_SOURCE_PATH,
								sourcePathList,
								MaintainRelationshipsCommand.Action.ADD
						);
				transcodeProfileRelationshipsList.add(transcodeProfileRelationships);
			}
		}
		return transcodeProfileRelationshipsList;
	}


	@SuppressWarnings("unchecked")
	public static boolean isAlreadyTranscoding(ProcessContext kcontext) {
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
	public static void setProcessed(ProcessContext kcontext) {
		String videoContentId = (String) kcontext.getVariable(VIDEO_CONTENT_ID_KEY);
		List<String> processedVideoContentIdList = (List<String>) kcontext.getVariable(PROCESSED_VIDEO_CONTENT_ID_LIST_KEY);
		if (!processedVideoContentIdList.contains(videoContentId)) {
			processedVideoContentIdList.add(videoContentId);
		}
	}

	@SuppressWarnings("unchecked")
	public static boolean moreToTranscode(ProcessContext kcontext) {
		Program program = (Program) kcontext.getVariable(PROGRAM_KEY);
		List<String> processedVideoContentIdList = (List<String>) kcontext.getVariable(PROCESSED_VIDEO_CONTENT_ID_LIST_KEY);
		for (VideoContent videoContent : program.getVideoContent()) {
			if (!processedVideoContentIdList.contains(videoContent.getId())) {
				return true;
			}
		}
		return false;
	}
}
