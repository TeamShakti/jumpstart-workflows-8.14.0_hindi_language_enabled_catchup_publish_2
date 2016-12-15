package com.irdeto.jumpstart.domain.taskhandler;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.irdeto.dao.task.TaskDAO;
import com.irdeto.domain.constants.TaskHandlerConstants;
import com.irdeto.domain.constants.TaskProperty;
import com.irdeto.factory.TaskFactory;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.task.LongTermTaskHandler;
import com.irdeto.taskhandler.AbstractTaskHandler;
import com.irdeto.taskhandler.annotation.TaskConfig;
import com.irdeto.taskhandler.qts.QTSAmazonS3DeleteFileTaskHandler;
import com.irdeto.taskhandler.qts.QTSFileDeleteTaskHandler;

@Component("HybridDelete")
@TaskConfig(isAsynchronous=true)
public class HybridDeleteTaskHandler extends AbstractTaskHandler implements LongTermTaskHandler{

	@TaskProperty(required=true, allowSpaces = false)
	public static final String ENTITY_ID_PARAMETER = "EntityId";
	@TaskProperty(required=true, allowSpaces = false)
	public static final String ENTITY_TYPE_PARAMETER = "EntityType";
	@TaskProperty(required=true, allowSpaces = true)
	public static final String ENTITY_TITLE_PARAMETER = "EntityTitle";
	@TaskProperty(required=true, allowSpaces = true)
	public static final String SOURCE_PARAMETER = "Source";
	@TaskProperty
	public static final String VIEW_NAME_PARAMETER = "ViewName";
	
	@Resource
	private QTSAmazonS3DeleteFileTaskHandler qtsAmazonS3DeleteFileTaskHandler;
	@Resource
	private QTSFileDeleteTaskHandler qtsFileDeleteTaskHandler;
	@Resource(name="propertiesManager")
	private PropertiesManager propertiesManager;
	@Resource(name="taskFactory")
	protected TaskFactory taskFactory;
	@Resource(name="taskDao")
	protected TaskDAO taskDao;

	private static final Logger logger = LoggerFactory.getLogger(HybridDeleteTaskHandler.class);
		
	@Override
	public void processTask(long workItemId, String workItemName, Map<String, Object> params, Map<String, Object> results)
			throws Exception {
		String source = (String) params.get(SOURCE_PARAMETER);
		logger.debug("source:"+source);
		if (source.startsWith("\\\\")){
			processShare(workItemId, workItemName, params, results);
		} else if (source.startsWith(propertiesManager.getProperty(JumpstartPropertyKey.S3_SOURCE_URL_KEY))){
			processS3(workItemId, workItemName, params, results);
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

	private void processShare(long workItemId, String workItemName, Map<String, Object> params, Map<String, Object> results) throws Exception {
		Map<String, Object> shareTaskParams = new HashMap<>();
		shareTaskParams.put(QTSFileDeleteTaskHandler.SOURCE_PARAMETER, params.get(SOURCE_PARAMETER));
		shareTaskParams.put(QTSFileDeleteTaskHandler.ENTITY_ID_PARAMETER, params.get(ENTITY_ID_PARAMETER));
		shareTaskParams.put(QTSFileDeleteTaskHandler.ENTITY_TITLE_PARAMETER, params.get(ENTITY_TITLE_PARAMETER));
		shareTaskParams.put(QTSFileDeleteTaskHandler.ENTITY_TYPE_PARAMETER, params.get(ENTITY_TYPE_PARAMETER));
		shareTaskParams.put(QTSFileDeleteTaskHandler.VIEW_NAME_PARAMETER, params.get(VIEW_NAME_PARAMETER));
		shareTaskParams.put(TaskHandlerConstants.ERROR_EVENT_PROPERTY, params.get(TaskHandlerConstants.ERROR_EVENT_PROPERTY));
		qtsFileDeleteTaskHandler.processTask(workItemId, workItemName, shareTaskParams, results);
	}

	private void processS3(long workItemId, String workItemName, Map<String, Object> params, Map<String, Object> results) throws Exception {
		Map<String, Object> s3TaskParams = new HashMap<>();
		URL s3URL = new URL((String) params.get(SOURCE_PARAMETER));
		String urlPath = s3URL.getPath();
		String bucketName = urlPath.substring(1, urlPath.indexOf("/", 1));
		String objectName = urlPath.substring(urlPath.indexOf("/", 1)+1);
		s3TaskParams.put(QTSAmazonS3DeleteFileTaskHandler.OBJECT_NAME_PARAMETER, objectName);
		s3TaskParams.put(QTSAmazonS3DeleteFileTaskHandler.BUCKET_NAME_PARAMETER, bucketName);
		s3TaskParams.put(QTSAmazonS3DeleteFileTaskHandler.ACCESS_KEY_PARAMETER, propertiesManager.getProperty(JumpstartPropertyKey.S3_ACCESS_KEY_KEY));
		s3TaskParams.put(QTSAmazonS3DeleteFileTaskHandler.SECRET_KEY_PARAMETER, propertiesManager.getProperty(JumpstartPropertyKey.S3_SECRET_KEY_KEY));
		s3TaskParams.put(QTSAmazonS3DeleteFileTaskHandler.ENTITY_ID_PARAMETER, params.get(ENTITY_ID_PARAMETER));
		s3TaskParams.put(QTSAmazonS3DeleteFileTaskHandler.ENTITY_TITLE_PARAMETER, params.get(ENTITY_TITLE_PARAMETER));
		s3TaskParams.put(QTSAmazonS3DeleteFileTaskHandler.ENTITY_TYPE_PARAMETER, params.get(ENTITY_TYPE_PARAMETER));
		s3TaskParams.put(QTSAmazonS3DeleteFileTaskHandler.VIEW_NAME_PARAMETER, params.get(VIEW_NAME_PARAMETER));
		s3TaskParams.put(TaskHandlerConstants.ERROR_EVENT_PROPERTY, params.get(TaskHandlerConstants.ERROR_EVENT_PROPERTY));
		qtsAmazonS3DeleteFileTaskHandler.processTask(workItemId, workItemName, s3TaskParams, results);
	}


}
