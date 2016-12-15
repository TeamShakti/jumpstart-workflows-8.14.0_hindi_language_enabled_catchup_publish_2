package com.irdeto.jumpstart.domain.taskhandler;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.irdeto.domain.constants.TaskHandlerConstants;
import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.domain.qts.request.SourceFiles;
import com.irdeto.domain.qts.request.SourceFolders;
import com.irdeto.domain.qts.request.SourceList;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.task.LongTermTaskHandler;
import com.irdeto.taskhandler.AbstractTaskHandler;
import com.irdeto.taskhandler.annotation.TaskConfig;
import com.irdeto.taskhandler.qts.QTSAmazonS3CopyTaskHandler;
import com.irdeto.taskhandler.qts.QTSAmazonS3GetTaskHandler;
import com.irdeto.taskhandler.qts.QTSAmazonS3PutTaskHandler;
import com.irdeto.taskhandler.qts.QTSFileCopyTaskHandler;
import com.irdeto.taskhandler.qts.SourceListAbleTaskHandler;

@Component("HybridCopy")
@TaskConfig(isAsynchronous=true)
public class HybridCopyTaskHandler extends AbstractTaskHandler implements SourceListAbleTaskHandler, LongTermTaskHandler{

	@TaskProperty(required=true, allowSpaces = false)
	public static final String ENTITY_ID_PARAMETER = "EntityId";
	@TaskProperty(required=true, allowSpaces = false)
	public static final String ENTITY_TYPE_PARAMETER = "EntityType";
	@TaskProperty(required=true, allowSpaces = true)
	public static final String ENTITY_TITLE_PARAMETER = "EntityTitle";
	@TaskProperty(required=false, allowSpaces = true)
	public static final String SOURCE_PARAMETER = "Source";
	@TaskProperty(required=true, allowSpaces = true)
	public static final String TARGET_PARAMETER = "Target";
	@TaskProperty(required=false, type=Integer.class)
	public static final String PRIORITY_PARAMETER = "QTSPriority";
	@TaskProperty(required=false, type=Boolean.class)
	public static final String FOLDER_MUST_EXIST_PARAMETER = "FolderMustExist";
	@TaskProperty
	public static final String VIEW_NAME_PARAMETER = "ViewName";

	private static final String ENCRYPTION_PARAMETER_AES256 = "AES256";

	@Resource
	private QTSAmazonS3GetTaskHandler qtsAmazonS3GetTaskHandler;
	@Resource
	private QTSAmazonS3PutTaskHandler qtsAmazonS3PutTaskHandler;
	@Resource
	private QTSAmazonS3CopyTaskHandler qtsAmazonS3CopyTaskHandler;
	@Resource
	private QTSFileCopyTaskHandler qtsFileCopyTaskHandler;
	@Resource(name="propertiesManager")
	private PropertiesManager propertiesManager;

	private static final Logger logger = LoggerFactory.getLogger(HybridCopyTaskHandler.class);

	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> params, Map<String, Object> results)
			throws Exception {
		String source = (String) params.get(SOURCE_PARAMETER);
		String target = (String) params.get(TARGET_PARAMETER);
		logger.debug("source:"+source);
		logger.debug("target:"+target);
		String s3SourceUrl = propertiesManager.getProperty(JumpstartPropertyKey.S3_SOURCE_URL_KEY);
		if (source.startsWith("\\\\")&&target.startsWith("\\\\")){
			processShare(workItemId, workItemName, params, results);
		} else if (source.startsWith(s3SourceUrl) && target.startsWith(s3SourceUrl)) {
			processS3Copy(workItemId, workItemName, params, results);
		} else if (source.startsWith(s3SourceUrl) && !target.startsWith(s3SourceUrl)) {
			processS3Get(workItemId, workItemName, params, results);
		} else if (!source.startsWith(s3SourceUrl) && target.startsWith(s3SourceUrl)) {
			processS3Put(workItemId, workItemName, params, results);
		} else {
			logger.error("Unidentified Source or Target");
            try {
                this.handleSuccess(workItemId, workItemName, results);
            } catch (Throwable e) {
            	logger.error(String.format("An exception occurred downstream of task %s: %s."
            			+ "Likely occurred in script task/gateway.",
            			this.getClass().getSimpleName(), workItemName), e);
            }
		}

	}

	private void processS3Put(long workItemId, String workItemName, Map<String, Object> params, Map<String, Object> results) throws Exception {
		Map<String, Object> s3TaskParams = new HashMap<>();
		URL s3URL = new URL((String) params.get(TARGET_PARAMETER));
		String urlPath = s3URL.getPath();
		String bucketName = urlPath.substring(1, urlPath.indexOf("/", 1));
		String objectName = urlPath.substring(urlPath.indexOf("/", 1)+1);
		s3TaskParams.put(QTSAmazonS3PutTaskHandler.SOURCE, params.get(SOURCE_PARAMETER));
		s3TaskParams.put(QTSAmazonS3PutTaskHandler.OBJECT_NAME_PARAMETER, objectName);
		s3TaskParams.put(QTSAmazonS3PutTaskHandler.BUCKET_NAME_PARAMETER, bucketName);
		s3TaskParams.put(QTSAmazonS3PutTaskHandler.ACCESS_KEY_PARAMETER, propertiesManager.getProperty(JumpstartPropertyKey.S3_ACCESS_KEY_KEY));
		s3TaskParams.put(QTSAmazonS3PutTaskHandler.SECRET_KEY_PARAMETER, propertiesManager.getProperty(JumpstartPropertyKey.S3_SECRET_KEY_KEY));
		s3TaskParams.put(QTSAmazonS3PutTaskHandler.ENTITY_ID_PARAMETER, params.get(ENTITY_ID_PARAMETER));
		s3TaskParams.put(QTSAmazonS3PutTaskHandler.ENTITY_TITLE_PARAMETER, params.get(ENTITY_TITLE_PARAMETER));
		s3TaskParams.put(QTSAmazonS3PutTaskHandler.ENTITY_TYPE_PARAMETER, params.get(ENTITY_TYPE_PARAMETER));
		s3TaskParams.put(QTSAmazonS3PutTaskHandler.VIEW_NAME_PARAMETER, params.get(VIEW_NAME_PARAMETER));
		s3TaskParams.put(TaskHandlerConstants.ERROR_EVENT_PROPERTY, params.get(TaskHandlerConstants.ERROR_EVENT_PROPERTY));
		s3TaskParams.put(QTSAmazonS3PutTaskHandler.ENCRYPTION_METHOD_PARAMETER, ENCRYPTION_PARAMETER_AES256);
		qtsAmazonS3PutTaskHandler.processTask(workItemId, workItemName, s3TaskParams, results);
	}

	private void processS3Get(long workItemId, String workItemName,	Map<String, Object> params, Map<String, Object> results) throws Exception {
		Map<String, Object> s3TaskParams = new HashMap<>();
		URL s3URL = new URL((String) params.get(SOURCE_PARAMETER));
		String urlPath = s3URL.getPath();
		String bucketName = urlPath.substring(1, urlPath.indexOf("/", 1));
		String objectName = urlPath.substring(urlPath.indexOf("/", 1)+1);
		s3TaskParams.put(QTSAmazonS3GetTaskHandler.TARGET_PARAMETER, params.get(TARGET_PARAMETER));
		s3TaskParams.put(QTSAmazonS3GetTaskHandler.OBJECT_NAME_PARAMETER, objectName);
		s3TaskParams.put(QTSAmazonS3GetTaskHandler.BUCKET_NAME_PARAMETER, bucketName);
		s3TaskParams.put(QTSAmazonS3GetTaskHandler.ACCESS_KEY_PARAMETER, propertiesManager.getProperty(JumpstartPropertyKey.S3_ACCESS_KEY_KEY));
		s3TaskParams.put(QTSAmazonS3GetTaskHandler.SECRET_KEY_PARAMETER, propertiesManager.getProperty(JumpstartPropertyKey.S3_SECRET_KEY_KEY));
		s3TaskParams.put(QTSAmazonS3GetTaskHandler.ENTITY_ID_PARAMETER, params.get(ENTITY_ID_PARAMETER));
		s3TaskParams.put(QTSAmazonS3GetTaskHandler.ENTITY_TITLE_PARAMETER, params.get(ENTITY_TITLE_PARAMETER));
		s3TaskParams.put(QTSAmazonS3GetTaskHandler.ENTITY_TYPE_PARAMETER, params.get(ENTITY_TYPE_PARAMETER));
		s3TaskParams.put(QTSAmazonS3GetTaskHandler.VIEW_NAME_PARAMETER, params.get(VIEW_NAME_PARAMETER));
		s3TaskParams.put(TaskHandlerConstants.ERROR_EVENT_PROPERTY, params.get(TaskHandlerConstants.ERROR_EVENT_PROPERTY));
		qtsAmazonS3GetTaskHandler.processTask(workItemId, workItemName, s3TaskParams, results);
	}

	@SuppressWarnings("unchecked")
	private void processS3Copy(long workItemId, String workItemName, Map<String, Object> params, Map<String, Object> results) throws Exception {
		Map<String, Object> s3TaskParams = new HashMap<>();
		URL s3TargetURL = new URL((String) params.get(TARGET_PARAMETER));
		String urlPath = s3TargetURL.getPath();
		String bucketName = urlPath.substring(1, urlPath.indexOf("/", 1));
		String objectName = urlPath.substring(urlPath.indexOf("/", 1)+1);
		s3TaskParams.put(QTSAmazonS3CopyTaskHandler.DESTINATION_BUCKET_NAME_PARAMETER, bucketName);
		s3TaskParams.put(QTSAmazonS3CopyTaskHandler.DESTINATION_OBJECT_NAME_PARAMETER, objectName);
		SourceList sourceList = new SourceList(new SourceFiles((List<String>) params.get(SOURCE_LIST_FILES_PARAMETER)),
				new SourceFolders((List<String>) params.get(SOURCE_LIST_FOLDERS_PARAMETER)));
		URL s3SourceURL = new URL((String) params.get(SOURCE_PARAMETER));
		urlPath = s3SourceURL.getPath();
		bucketName = urlPath.substring(1, urlPath.indexOf("/", 1));
		objectName = urlPath.substring(urlPath.indexOf("/", 1)+1);
		s3TaskParams.put(QTSAmazonS3CopyTaskHandler.SOURCE_BUCKET_NAME_PARAMETER, bucketName);
		s3TaskParams.put(QTSAmazonS3CopyTaskHandler.SOURCE_OBJECT_NAME_PARAMETER, objectName);
		if(!sourceList.isEmpty()){
			s3TaskParams.put(QTSAmazonS3CopyTaskHandler.SOURCE_LIST_FILES_PARAMETER, sourceList.getFiles());
			s3TaskParams.put(QTSAmazonS3CopyTaskHandler.SOURCE_LIST_FOLDERS_PARAMETER, sourceList.getFolders());
		}
		s3TaskParams.put(QTSAmazonS3CopyTaskHandler.ACCESS_KEY_PARAMETER, propertiesManager.getProperty(JumpstartPropertyKey.S3_ACCESS_KEY_KEY));
		s3TaskParams.put(QTSAmazonS3CopyTaskHandler.SECRET_KEY_PARAMETER, propertiesManager.getProperty(JumpstartPropertyKey.S3_SECRET_KEY_KEY));
		s3TaskParams.put(QTSAmazonS3CopyTaskHandler.ENTITY_ID_PARAMETER, params.get(ENTITY_ID_PARAMETER));
		s3TaskParams.put(QTSAmazonS3CopyTaskHandler.ENTITY_TITLE_PARAMETER, params.get(ENTITY_TITLE_PARAMETER));
		s3TaskParams.put(QTSAmazonS3CopyTaskHandler.ENTITY_TYPE_PARAMETER, params.get(ENTITY_TYPE_PARAMETER));
		s3TaskParams.put(QTSAmazonS3CopyTaskHandler.VIEW_NAME_PARAMETER, params.get(VIEW_NAME_PARAMETER));
		s3TaskParams.put(TaskHandlerConstants.ERROR_EVENT_PROPERTY, params.get(TaskHandlerConstants.ERROR_EVENT_PROPERTY));
		qtsAmazonS3CopyTaskHandler.processTask(workItemId, workItemName, s3TaskParams, results);
	}

	@SuppressWarnings("unchecked")
	private void processShare(long workItemId, String workItemName, Map<String, Object> params, Map<String, Object> results) throws Exception {
		Map<String, Object> shareTaskParams = new HashMap<>();

		// TODO: Currently we do not handle QTS sourceList XML structure, so we can only handle 1 file or folder.
		SourceList sourceList = new SourceList(new SourceFiles((List<String>) params.get(SOURCE_LIST_FILES_PARAMETER)),
				new SourceFolders((List<String>) params.get(SOURCE_LIST_FOLDERS_PARAMETER)));

		String source = (String) params.get(SOURCE_PARAMETER);
		String target = (String) params.get(TARGET_PARAMETER);
		if (sourceList.getFolders() != null && !sourceList.getFolders().isEmpty()) {
			source += sourceList.getFolders().get(0);
		} else if (sourceList.getFiles() != null && !sourceList.getFiles().isEmpty()) {
			source += sourceList.getFiles().get(0);
		}
		// Make sure no incorrect slashes are in path
		shareTaskParams.put(QTSFileCopyTaskHandler.SOURCE_PARAMETER, source.replace('/', '\\'));
		shareTaskParams.put(QTSFileCopyTaskHandler.TARGET_PARAMETER, target.replace('/', '\\'));
		shareTaskParams.put(QTSFileCopyTaskHandler.ENTITY_ID_PARAMETER, params.get(ENTITY_ID_PARAMETER));
		shareTaskParams.put(QTSFileCopyTaskHandler.ENTITY_TITLE_PARAMETER, params.get(ENTITY_TITLE_PARAMETER));
		shareTaskParams.put(QTSFileCopyTaskHandler.ENTITY_TYPE_PARAMETER, params.get(ENTITY_TYPE_PARAMETER));
		shareTaskParams.put(QTSFileCopyTaskHandler.VIEW_NAME_PARAMETER, params.get(VIEW_NAME_PARAMETER));
		shareTaskParams.put(QTSFileCopyTaskHandler.QTS_PRIORITY_PARAMETER, params.get(PRIORITY_PARAMETER));
		// TODO: Make this configurable via the Reference Workflow? Default QTS behavior is True, default Workflow-Engine behavior is False
		shareTaskParams.put(QTSFileCopyTaskHandler.FOLDER_MUST_EXIST_PARAMETER, false);
		shareTaskParams.put(TaskHandlerConstants.ERROR_EVENT_PROPERTY, params.get(TaskHandlerConstants.ERROR_EVENT_PROPERTY));
		qtsFileCopyTaskHandler.processTask(workItemId, workItemName, shareTaskParams, results);
	}

}
