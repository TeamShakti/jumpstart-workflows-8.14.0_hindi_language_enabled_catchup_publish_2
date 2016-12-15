package com.irdeto.jumpstart.workflow.ingest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.domain.operation.MM8Response;
import com.irdeto.domain.qts.response.QTSHandleEvent;
import com.irdeto.domain.qts.response.fileinfo.QTSHandleEventFileInfo;
import com.irdeto.domain.qts.response.imageinfo.QTSDataImageInfo;
import com.irdeto.jumpstart.domain.Content;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ImageSubcontent;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.SubtitleContent;
import com.irdeto.jumpstart.domain.SubtitleSubcontent;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.VideoSubcontent.FrameRate;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.workflow.FileHelper.FileIngestHelper;
import com.irdeto.jumpstart.workflow.LocaleHelper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;

public class MediaIngestHelper extends WorkflowHelper {
	private static final String URI_ID_FIELD_NAME = "uriId";
	private static final String SOURCE_URL_FIELD_NAME = "sourceUrl";
	private static final String MM_FILTER_LIST_KEY = "mmFilterList";
	private static final Logger logger = LoggerFactory.getLogger(MediaIngestHelper.class);

	protected static final String MDIDENTIFER_KEY = "mdIdentifier";
	protected static final String URI_ID_KEY = URI_ID_FIELD_NAME;

	private static final String INGESTED_MEDIA_DATA_MAP_KEY = "ingestMediaDataMap";
	protected static final String ENTITY_TYPE_KEY = "entityType";
	private static final String ENTITY_ID_KEY = "entityId";
	protected static final String PODSERVER_KEY = "podServer";
	protected static final String FILENAME_KEY = "fileName";
	protected static final String FILEPATH_KEY = "filePath";
	private static final String HANDLE_EVENT_KEY = "handleEvent";
	private static final String SOURCE_KEY = "source";
	private static final String TARGET_KEY = "target";
	private static final String SOURCE_VERSION_KEY = "sourceVersion";

	public static void handleActivation(ProcessContext kcontext){
		String mdIdentifier = (String)kcontext.getVariable(MDIDENTIFER_KEY);
		String fileName = (String)kcontext.getVariable(FILENAME_KEY);
		String filePath = (String)kcontext.getVariable(FILEPATH_KEY);
		String podServer = (String)kcontext.getVariable(PODSERVER_KEY);
		String entityType = FileIngestHelper.getIngestEntityTypeFromMdIdentifier(mdIdentifier);
		kcontext.setVariable(ENTITY_TYPE_KEY, entityType);
		logger.debug("mdIdentifier: {}", mdIdentifier );
		logger.debug("fileName: {}", fileName );
		logger.debug("filePath: {}", filePath );
		logger.debug("podServer: {}", podServer );
		logger.debug("entityType: {}", entityType );

		Map<String, Object> ingestedData = new HashMap<>();
		ingestedData.put(MDIDENTIFER_KEY, mdIdentifier);
		ingestedData.put(FILENAME_KEY, fileName);
		ingestedData.put(FILEPATH_KEY, filePath);
		ingestedData.put(PODSERVER_KEY, podServer);
		ingestedData.put(ENTITY_TYPE_KEY, entityType);
		kcontext.setVariable(INGESTED_MEDIA_DATA_MAP_KEY, ingestedData);
	}

	/**
	 * prepares a filter query from the given data and sets it as the variable mmFilterList on the context.
	 * @param mediaData
	 * @param kcontext
	 */
	@SuppressWarnings("unchecked")
	public static void prepareFilterData(ProcessContext kcontext){
		Map<String, Object> mapMediaData = (Map<String,Object>) kcontext.getVariable(INGESTED_MEDIA_DATA_MAP_KEY);
		String fileName = MapUtils.getString(mapMediaData, FILENAME_KEY);
		kcontext.setVariable(MM_FILTER_LIST_KEY, getQueryFilterParameter(SOURCE_URL_FIELD_NAME, fileName, false));
	}

	public static boolean assetFoundInMM(ProcessContext kcontext){
		MM8Response mm8Response = (MM8Response)kcontext.getVariable(MM8_RESPONSE_KEY);
		return assetFoundInMM(mm8Response);
	}

	protected static boolean assetFoundInMM(MM8Response mm8Response){
		boolean found = false;
		String type = mm8Response.getResponseObjectType();
		logger.debug("Asset found in MM: " + type);
		@SuppressWarnings("unchecked")
		ArrayList<Object> results = (ArrayList<Object>)mm8Response.getResponseObject();
		if (results.size() > 0){
			found = true;
			Object asset = results.get(0);
			logger.debug("Found asset: " + asset.toString());
		}
		return found;
	}

	public static void createEntity(ProcessContext kcontext) {
		@SuppressWarnings("unchecked")
		Map<String,Object> ingestData = (Map<String, Object>)kcontext.getVariable(INGESTED_MEDIA_DATA_MAP_KEY);
		String entityType = (String)kcontext.getVariable(ENTITY_TYPE_KEY);
		kcontext.setVariable(CONTENT_KEY, createEntity(ingestData, entityType));
	}

	protected static Content createEntity(Map<String,Object> ingestData, String entityType) {
		Content item = null;
		if (ENTITY_TYPE_IMAGE_CONTENT.equals(entityType)) {
			item = new ImageContent();
			item.setSourceUrl(MapUtils.getString(ingestData, FILENAME_KEY));
			item.setUriId(MapUtils.getString(ingestData, FILENAME_KEY));
		} else if (ENTITY_TYPE_VIDEO_CONTENT.equals(entityType)) {
			item = new VideoContent();
			item.setSourceUrl(MapUtils.getString(ingestData, FILENAME_KEY));
			item.setUriId(MapUtils.getString(ingestData, FILENAME_KEY));
		} else if (ENTITY_TYPE_SUBTITLE_CONTENT.equals(entityType)) {
			item = new SubtitleContent();
			item.setSourceUrl(MapUtils.getString(ingestData, FILENAME_KEY));
			item.setUriId(MapUtils.getString(ingestData, FILENAME_KEY));
		}
		else {
			logger.error("Unable to create entity for unknown entity type: " + entityType);
		}
		return item;
	}

	public static boolean isVideo(ProcessContext kcontext){
		String entityType = (String) kcontext.getVariable(ENTITY_TYPE_KEY);
		return ENTITY_TYPE_VIDEO_CONTENT.equals(entityType);
	}

	public static boolean isSubtitle(ProcessContext kcontext){
		String entityType = (String) kcontext.getVariable(ENTITY_TYPE_KEY);
		return ENTITY_TYPE_SUBTITLE_CONTENT.equals(entityType);
	}

	public static void entryMoveContentToMezzanine(ProcessContext kcontext){
		// Note that this version of the Content does not get saved back to MM.  The setupAssetUpdateForMM method
		// is responsible.
		MM8Response response = (MM8Response)kcontext.getVariable(MM8_RESPONSE_KEY);
		Content content = getEntity(Content.class, response);
		@SuppressWarnings("unchecked")
		Map<String,Object> ingestData = (Map<String, Object>)kcontext.getVariable(INGESTED_MEDIA_DATA_MAP_KEY);
		String filename = (String) ingestData.get(FILENAME_KEY);
		String filePath = (String) ingestData.get(FILEPATH_KEY);

		kcontext.setVariable(ENTITY_ID_KEY, content.getId());
		Integer sourceVersion;
		if(content.getSourceVersion() == null) {
			sourceVersion = new Integer(1);
		} else {
			sourceVersion = new Integer(content.getSourceVersion()+1);
		}
		kcontext.setVariable(SOURCE_VERSION_KEY, sourceVersion);
		// As above, content is not updated in the DB in this method.
		content.setSourceVersion(sourceVersion);
		String sourceFileUrl = FileIngestHelper.getSourceFileUrl(content);
		kcontext.setVariable(SOURCE_KEY, filePath+filename);
		kcontext.setVariable(TARGET_KEY, sourceFileUrl);
	}

	/**
	 * extract found domain object from mm8Response context variable
	 * read it and then update it with data from ingestData
	 * store it in kcontext under createEntity key.
	 * also store program uriid if exists in kcontext under key "programURIID"
	 * @param kcontext
	 */
	public static void setupAssetUpdateForMM(ProcessContext kcontext){
		Content content = (Content)kcontext.getVariable(CONTENT_KEY);
		String sourceFileUrl = (String)kcontext.getVariable(TARGET_KEY);
		Integer sourceVersion = (Integer)kcontext.getVariable(SOURCE_VERSION_KEY);
		content.setSourceVersion(sourceVersion);
		if (content instanceof VideoContent) {
			QTSHandleEventFileInfo fileInfo = (QTSHandleEventFileInfo) kcontext.getVariable(HANDLE_EVENT_KEY);

			SourceVideoSub subcontent = new SourceVideoSub();
			try {
				subcontent.setFrameRate(FrameRate.fromValue(fileInfo.getFps()));
			} catch (Exception e) {
			}
			if (StringUtils.isNotEmpty(fileInfo.getFileSize())) {
				subcontent.setContentFileSize(fileInfo.getFileSize());
			}
			QTSHandleEventFileInfo handleEvent = (QTSHandleEventFileInfo) fileInfo;
			subcontent.setSourcePath(sourceFileUrl);
			subcontent.setContentCheckSum(handleEvent.getHashCode().replaceAll("[-]", ""));
			subcontent.setContentFileSize(handleEvent.getFileSize());
			subcontent.setBitRate(Integer.getInteger(handleEvent.getVideoBitRate()));

			((VideoContent) content).getSubcontent().add(subcontent);

		}  else if (content instanceof SubtitleContent) {
			QTSHandleEventFileInfo fileInfo = (QTSHandleEventFileInfo) kcontext.getVariable(HANDLE_EVENT_KEY);

			SubtitleSubcontent subtitleSubcontent = new SubtitleSubcontent();

			if (StringUtils.isNotEmpty(fileInfo.getFileSize())) {
				subtitleSubcontent.setContentFileSize(fileInfo.getFileSize());
			}
			QTSHandleEventFileInfo handleEvent = (QTSHandleEventFileInfo) fileInfo;
			subtitleSubcontent.setSourcePath(sourceFileUrl); // Check the Target value for Subtitle content
			subtitleSubcontent.setContentCheckSum(handleEvent.getHashCode().replaceAll("[-]", ""));
			subtitleSubcontent.setContentFileSize(handleEvent.getFileSize());

			((SubtitleContent) content).getSubcontent().add(subtitleSubcontent);
		}  else if (content instanceof ImageContent) {
			@SuppressWarnings("unchecked")
			QTSHandleEvent<QTSDataImageInfo> handleEvent = (QTSHandleEvent<QTSDataImageInfo>) kcontext.getVariable(HANDLE_EVENT_KEY);
			String fileSize = handleEvent.getEvent().getData().getSize().toString();
			Integer width = handleEvent.getEvent().getData().getWidth();
			Integer height = handleEvent.getEvent().getData().getHeight();
			ImageSubcontent subcontent = new ImageSubcontent();
			subcontent.setSourcePath(sourceFileUrl);
			if (StringUtils.isNotEmpty(fileSize)) {
				subcontent.setContentFileSize(fileSize);
			}

			subcontent.setLanguage(LocaleHelper.INGEST_DEFAULT_LOCALE);
			subcontent.setXResolution(width);
			subcontent.setYResolution(height);

			((ImageContent) content).getSubcontent().add(subcontent);
		}
	}

	public static boolean removeFromProcessedFolder(ProcessContext kcontext) {
		try {
			String removeFromProcessed = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.REMOVE_FROM_PROCESSED_FOLDER_KEY);
			return BooleanUtils.toBoolean(removeFromProcessed);
		} catch (PropertyException e) {
		}
		return false;
	}
}
