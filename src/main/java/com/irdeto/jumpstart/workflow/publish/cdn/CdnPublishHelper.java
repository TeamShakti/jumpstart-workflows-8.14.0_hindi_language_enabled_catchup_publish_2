package com.irdeto.jumpstart.workflow.publish.cdn;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.jumpstart.domain.Content;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ImageSubcontent;
import com.irdeto.jumpstart.domain.ProtectVideoSub;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.Subcontent;
import com.irdeto.jumpstart.domain.SubtitleSubcontent;
import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.publish.AbstractContentWrapper;
import com.irdeto.jumpstart.domain.publish.ImageContentWrapper;
import com.irdeto.jumpstart.domain.publish.PublishWrapper;
import com.irdeto.jumpstart.domain.publish.SubtitleContentWrapper;
import com.irdeto.jumpstart.domain.publish.VideoContentWrapper;
import com.irdeto.jumpstart.domain.publish.VideoSubcontentWrapper;
import com.irdeto.jumpstart.workflow.FileHelper;
import com.irdeto.jumpstart.workflow.FileHelper.FilePublishHelper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.protect.AbstractDRMProfile;
import com.irdeto.jumpstart.workflow.protect.DRMProfile;
import com.irdeto.jumpstart.workflow.protect.ProtectHelper;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;

public class CdnPublishHelper extends WorkflowHelper {
	private static final String DEFAULT_VIEWNAME_KEY = "defaultViewname";
	private static final String PUBLISH_WRAPPER_KEY = "wrapper";
	private static final String CONTENT_ID_KEY = "contentId";
	private static final String CONTENT_TYPE_KEY = "contentType";
	private static final String SOURCE_LIST_FOLDERS_KEY = "sourceListFolders";
	private static final String SOURCE_KEY = "source";
	private static final String TARGET_KEY = "target";
	private static final String VIDEO_CONTENT_WRAPPER_POSITION_LIST_KEY = "videoContentWrapperPositionList";
	private static final String IMAGE_CONTENT_WRAPPER_POSITION_LIST_KEY = "imageContentWrapperPositionList";
	private static final String SUBTITLE_CONTENT_WRAPPER_POSITION_LIST_KEY = "subtitleContentWrapperPositionList";
	private static final String VIDEO_CONTENT_WRAPPER_POSITION_KEY = "videoContentWrapperPosition";
	private static final String IMAGE_CONTENT_WRAPPER_POSITION_KEY = "imageContentWrapperPosition";
	private static final String SUBTITLE_CONTENT_WRAPPER_POSITION_KEY = "subtitleContentWrapperPosition";
	private static final String SUBCONTENT_KEY = "subcontent";
	private static final String CURRENT_CONTENT_KEY = "currentContent";

	private static final Logger logger = LoggerFactory.getLogger(CdnPublishHelper.class);

	public static void setupPublish(ProcessContext kcontext) {
		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		List<Integer> videoContentWrapperList = new ArrayList<>();
		for (int i = 0; i < publishWrapper.getVideoContentWrapperList().size(); i++) {
			videoContentWrapperList.add(i);
		}
		List<Integer> imageContentWrapperList = new ArrayList<>();
		for (int i = 0; i < publishWrapper.getImageContentWrapperList().size(); i++) {
			imageContentWrapperList.add(i);
		}
		List<Integer> subtitleContentWrapperList =  new ArrayList<>();
		for (int i = 0; i < publishWrapper.getSubtitleContentWrapperList().size(); i++) {
			subtitleContentWrapperList.add(i);
		}

		kcontext.setVariable(VIDEO_CONTENT_WRAPPER_POSITION_LIST_KEY, videoContentWrapperList);
		kcontext.setVariable(IMAGE_CONTENT_WRAPPER_POSITION_LIST_KEY, imageContentWrapperList);
		kcontext.setVariable(SUBTITLE_CONTENT_WRAPPER_POSITION_LIST_KEY, subtitleContentWrapperList);
	}

	@SuppressWarnings("unchecked")
	public static void prepareContentPublish(ProcessContext kcontext) {
		List<Integer> videoContentWrapperList = (List<Integer>)kcontext.getVariable(VIDEO_CONTENT_WRAPPER_POSITION_LIST_KEY);
		List<Integer> imageContentWrapperList = (List<Integer>)kcontext.getVariable(IMAGE_CONTENT_WRAPPER_POSITION_LIST_KEY);
		List<Integer> subtitleContentWrapperList = (List<Integer>)kcontext.getVariable(SUBTITLE_CONTENT_WRAPPER_POSITION_LIST_KEY);

		if (!subtitleContentWrapperList.isEmpty()) {
			Integer subtitleContentWrapperPosition = subtitleContentWrapperList.remove(0);
			kcontext.setVariable(SUBTITLE_CONTENT_WRAPPER_POSITION_KEY, subtitleContentWrapperPosition);
			kcontext.setVariable(VIDEO_CONTENT_WRAPPER_POSITION_KEY, null);
			kcontext.setVariable(IMAGE_CONTENT_WRAPPER_POSITION_KEY, null);
		}
		else if (!videoContentWrapperList.isEmpty()) {
			Integer videoContentWrapperPosition = videoContentWrapperList.remove(0);
			kcontext.setVariable(VIDEO_CONTENT_WRAPPER_POSITION_KEY, videoContentWrapperPosition);
			kcontext.setVariable(IMAGE_CONTENT_WRAPPER_POSITION_KEY, null);
			kcontext.setVariable(SUBTITLE_CONTENT_WRAPPER_POSITION_KEY, null);
		} else if (!imageContentWrapperList.isEmpty()) {
			Integer imageContentWrapperPosition = imageContentWrapperList.remove(0);
			kcontext.setVariable(VIDEO_CONTENT_WRAPPER_POSITION_KEY, null);
			kcontext.setVariable(IMAGE_CONTENT_WRAPPER_POSITION_KEY, imageContentWrapperPosition);
			kcontext.setVariable(SUBTITLE_CONTENT_WRAPPER_POSITION_KEY, null);
		}
	}

	public static void setupSubcontentCopy(ProcessContext kcontext) {
		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		Integer videoContentWrapperPosition = (Integer)kcontext.getVariable(VIDEO_CONTENT_WRAPPER_POSITION_KEY);
		Integer imageContentWrapperPosition = (Integer)kcontext.getVariable(IMAGE_CONTENT_WRAPPER_POSITION_KEY);
		Integer subtitleContentWrapperPosition = (Integer)kcontext.getVariable(SUBTITLE_CONTENT_WRAPPER_POSITION_KEY);

		kcontext.setVariable(SOURCE_LIST_FOLDERS_KEY, null);
		kcontext.setVariable(SOURCE_KEY, null);
		kcontext.setVariable(TARGET_KEY, null);
		kcontext.setVariable(SUBCONTENT_KEY, null);

		if(subtitleContentWrapperPosition != null){
		SubtitleContentWrapper subtitleContentWrapper =	publishWrapper.getSubtitleContentWrapperList().get(subtitleContentWrapperPosition);
			SubtitleSubcontent subtitleSubcontent = publishWrapper.getSubtitleSubcontentById(subtitleContentWrapper.getUnpublishedSubtitleSubcontentIdList().remove(0));
			if(null != subtitleSubcontent){
					subtitleSubcontent.setConsumerUrl(FilePublishHelper.getConsumerUrl(subtitleContentWrapper.getPublishVersion(), subtitleSubcontent));
			}
			kcontext.setVariable(SUBCONTENT_KEY, subtitleSubcontent);
			kcontext.setVariable(SOURCE_LIST_FOLDERS_KEY, null);
			kcontext.setVariable(SOURCE_KEY, subtitleSubcontent.getSourcePath());
			kcontext.setVariable(TARGET_KEY, FilePublishHelper.getPublishImageTargetFolder(subtitleSubcontent.getSourcePath(), subtitleContentWrapper.getPublishVersion()));
		}
		else if (videoContentWrapperPosition != null) {
			VideoContentWrapper videoContentWrapper = publishWrapper.getVideoContentWrapperList().get(videoContentWrapperPosition);
			if (videoContentWrapper == null) {
				logger.error("Unable to process video content position {}", videoContentWrapperPosition);
				return;
			}

			if(videoContentWrapper.getUnpublishedVideoSubcontentWrapperList().size() > 0){
			VideoSubcontentWrapper videoSubcontentWrapper = (VideoSubcontentWrapper)videoContentWrapper.getUnpublishedVideoSubcontentWrapperList().remove(0);

			SourceVideoSub sourceVideoSubcontent = publishWrapper.getSourceVideoSubcontentById(videoSubcontentWrapper.getSourceVideoSubcontentId());
			if (sourceVideoSubcontent == null) {
				logger.error("Unable to process subcontent ID {}", videoSubcontentWrapper.getSourceVideoSubcontentId());
				return;
			}

			ProtectVideoSub protectedVideoSubcontent = publishWrapper.getProtectedVideoSubcontentById(videoSubcontentWrapper.getProtectedVideoSubcontentId());
			Protect protect = videoSubcontentWrapper.getProtectList().get(0);
			protectedVideoSubcontent.setConsumerUrl(FilePublishHelper.getConsumerUrl(videoContentWrapper.getPublishVersion(), sourceVideoSubcontent, protectedVideoSubcontent, protect));
			kcontext.setVariable(SOURCE_LIST_FOLDERS_KEY, getSourceListFoldersForPublish(protectedVideoSubcontent.getSourcePath(), protect));
			kcontext.setVariable(SOURCE_KEY, FileHelper.addHybridBaseURLAndPath("", WorkflowHelper.PROTECTED_TYPE)+SLASH);
			kcontext.setVariable(TARGET_KEY, getDestinationCopyCDN(sourceVideoSubcontent, videoContentWrapper.getPublishVersion(), protect));
			kcontext.setVariable(SUBCONTENT_KEY, protectedVideoSubcontent);
			}

		} else if (imageContentWrapperPosition != null) {
			ImageContentWrapper imageContentWrapper = publishWrapper.getImageContentWrapperList().get(imageContentWrapperPosition);
			Content content = publishWrapper.getContentById(imageContentWrapper);
			ImageContent imageContent = (ImageContent)content;
			ImageSubcontent imageSubcontent = publishWrapper.getImageSubcontentById(imageContentWrapper.getUnpublishedImageSubcontentIdList().remove(0));
			// TODO is really for loop needed ?
			for (ImageSubcontent imageContentSubcontent: imageContent.getSubcontent()) {
				if (imageContentSubcontent.getId().equals(imageSubcontent.getId())) {
					imageContentSubcontent.setConsumerUrl(FilePublishHelper.getConsumerUrl(imageContentWrapper.getPublishVersion(), imageSubcontent));
					break;
				}
			}
			imageSubcontent.setConsumerUrl(FilePublishHelper.getConsumerUrl(imageContentWrapper.getPublishVersion(), imageSubcontent));
			kcontext.setVariable(SUBCONTENT_KEY, imageSubcontent);
			kcontext.setVariable(SOURCE_LIST_FOLDERS_KEY, null);
			kcontext.setVariable(SOURCE_KEY, imageSubcontent.getSourcePath());
			kcontext.setVariable(TARGET_KEY, FilePublishHelper.getPublishImageTargetFolder(imageSubcontent.getSourcePath(), imageContentWrapper.getPublishVersion()));
		}
		try {
			kcontext.setVariable(DEFAULT_VIEWNAME_KEY, BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.DEFAULT_VIEWNAME_KEY));
		} catch (PropertyException e) {
		}
	}

	public static boolean needCopy(ProcessContext kcontext){
		String source = (String) kcontext.getVariable(SOURCE_KEY);
		String target = (String) kcontext.getVariable(TARGET_KEY);
		Subcontent subcontent = (Subcontent) kcontext.getVariable(SUBCONTENT_KEY);
		return !StringUtils.isBlank(source) && !StringUtils.isBlank(target) && subcontent != null;
	}

	public static String getDestinationCopyCDN(SourceVideoSub sourceVideoSubcontent, Integer publishVersion, Protect protect) {
		String publishFolder = FileHelper.getFilenameRoot(sourceVideoSubcontent.getSourcePath(), 1) + "-v" + publishVersion;
		return ProtectHelper.getProfile(protect).getDestinationCopyFolderToCDN(publishFolder);
	}

	protected static List<String> getSourceListFoldersForPublish(String protectedSourcePath, Protect protect) {
		List<String> sourceListFolders = new ArrayList<>();
		DRMProfile drmProfile = ProtectHelper.getProfile(protect);
		if (drmProfile instanceof AbstractDRMProfile) {
			String sourceListFolder = ProtectHelper.getProfile(protect).getSourceListFoldersForPublish(protectedSourcePath);
			sourceListFolders.add(sourceListFolder);
		}
		return sourceListFolders;
	}

	@SuppressWarnings("unchecked")
	public static boolean moreContent(ProcessContext kcontext) {
		List<Integer> videoContentWrapperPositionList = (List<Integer>)kcontext.getVariable(VIDEO_CONTENT_WRAPPER_POSITION_LIST_KEY);
		List<Integer> imageContentWrapperPositionList = (List<Integer>)kcontext.getVariable(IMAGE_CONTENT_WRAPPER_POSITION_LIST_KEY);
		List<Integer> subtitleContentWrapperPositionList = (List<Integer>)kcontext.getVariable(SUBTITLE_CONTENT_WRAPPER_POSITION_LIST_KEY);
		return !videoContentWrapperPositionList.isEmpty() || !imageContentWrapperPositionList.isEmpty() || !subtitleContentWrapperPositionList.isEmpty();
	}

	public static boolean moreSubcontent(ProcessContext kcontext) {
		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		Integer videoContentWrapperPosition = (Integer)kcontext.getVariable(VIDEO_CONTENT_WRAPPER_POSITION_KEY);
		Integer imageContentWrapperPosition = (Integer)kcontext.getVariable(IMAGE_CONTENT_WRAPPER_POSITION_KEY);
		Integer subtitleContentWrapperPosition = (Integer)kcontext.getVariable(SUBTITLE_CONTENT_WRAPPER_POSITION_KEY);
		if (subtitleContentWrapperPosition != null) {
			SubtitleContentWrapper subtitleContentWrapper = publishWrapper.getSubtitleContentWrapperList().get(subtitleContentWrapperPosition);
			return !subtitleContentWrapper.getUnpublishedSubtitleSubcontentIdList().isEmpty();
		} else if (videoContentWrapperPosition != null) {
			VideoContentWrapper videoContentWrapper = publishWrapper.getVideoContentWrapperList().get(videoContentWrapperPosition);
			return !videoContentWrapper.getUnpublishedVideoSubcontentWrapperList().isEmpty();
		} else if (imageContentWrapperPosition != null) {
			ImageContentWrapper imageContentWrapper = publishWrapper.getImageContentWrapperList().get(imageContentWrapperPosition);
			return !imageContentWrapper.getUnpublishedImageSubcontentIdList().isEmpty();
		}  else {
			return false;
		}
	}

	public static void setupCurrentContentRead(ProcessContext kcontext) {
		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		Integer videoContentWrapperPosition = (Integer)kcontext.getVariable(VIDEO_CONTENT_WRAPPER_POSITION_KEY);
		Integer imageContentWrapperPosition = (Integer)kcontext.getVariable(IMAGE_CONTENT_WRAPPER_POSITION_KEY);
		Integer subtitleContentWrapperPosition = (Integer)kcontext.getVariable(SUBTITLE_CONTENT_WRAPPER_POSITION_KEY);

		AbstractContentWrapper<?> contentWrapper = null;
		if (subtitleContentWrapperPosition != null) {
			contentWrapper = publishWrapper.getSubtitleContentWrapperList().get(subtitleContentWrapperPosition);
		}
		else if (videoContentWrapperPosition != null) {
			contentWrapper = publishWrapper.getVideoContentWrapperList().get(videoContentWrapperPosition);

		} else if (imageContentWrapperPosition != null) {
			contentWrapper = publishWrapper.getImageContentWrapperList().get(imageContentWrapperPosition);
		}
		Content content = publishWrapper.getContentById(contentWrapper);
		if (contentWrapper != null) {
			kcontext.setVariable(CONTENT_ID_KEY, content.getId());
			kcontext.setVariable(CONTENT_TYPE_KEY, content.getType());
		}
	}

	public static void setContent(ProcessContext kcontext) {
		PublishWrapper<?> publishWrapper = (PublishWrapper<?>)kcontext.getVariable(PUBLISH_WRAPPER_KEY);
		Integer videoContentWrapperPosition = (Integer)kcontext.getVariable(VIDEO_CONTENT_WRAPPER_POSITION_KEY);
		Integer imageContentWrapperPosition = (Integer)kcontext.getVariable(IMAGE_CONTENT_WRAPPER_POSITION_KEY);
		Integer subtitleContentWrapperPosition = (Integer)kcontext.getVariable(SUBTITLE_CONTENT_WRAPPER_POSITION_KEY);

		Content currentContent = (Content)kcontext.getVariable(CURRENT_CONTENT_KEY);
		AbstractContentWrapper<?> contentWrapper = null;

		if (subtitleContentWrapperPosition != null) {
			contentWrapper = publishWrapper.getSubtitleContentWrapperList().get(subtitleContentWrapperPosition);
		}
		else if (videoContentWrapperPosition != null) {
			contentWrapper = publishWrapper.getVideoContentWrapperList().get(videoContentWrapperPosition);

		} else if (imageContentWrapperPosition != null) {
			contentWrapper = publishWrapper.getImageContentWrapperList().get(imageContentWrapperPosition);
		}
		if (contentWrapper != null) {
			currentContent.setPublishVersion(contentWrapper.getPublishVersion());
		}
		currentContent.setIsPublished(true);
	}
}
