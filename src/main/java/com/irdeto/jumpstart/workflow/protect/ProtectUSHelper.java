package com.irdeto.jumpstart.workflow.protect;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.kie.api.runtime.process.ProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irdeto.domain.qts.response.fileinfo.QTSHandleEventFileInfo;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.SubtitleContent;
import com.irdeto.jumpstart.domain.SubtitleSubcontent;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.domain.control.ControlBatch;
import com.irdeto.jumpstart.domain.control.ControlContent;
import com.irdeto.jumpstart.domain.control.ControlMedia;
import com.irdeto.jumpstart.domain.control.ControlSubContent;
import com.irdeto.jumpstart.domain.livedrm.GenerateKeysLiveDrmResponseProcessor;
import com.irdeto.jumpstart.domain.livedrm.GenerateKeysObjectFactory;
import com.irdeto.jumpstart.domain.livedrm.LiveDrmSoapEnvelope;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.protect.ProtectWrapper;
import com.irdeto.jumpstart.workflow.FileHelper;
import com.irdeto.jumpstart.workflow.FilenameSegments;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;

public class ProtectUSHelper extends WorkflowHelper {
	private static final String SUCCESS = "SUCCESS";
	private static final String EXIT_STATUS = "exitStatus";
	private static final String LIVE_DRM_SOAP_ENVELOPE_KEY = "liveDrmSoapEnvelope";
	private static final String LIVE_DRM_RESPONSE_PROCESSOR_KEY = "liveDrmResponseProcessor";
	private static final String SIGNATURE_KEY = "Signature";
	private static final String EXPIRES_KEY = "Expires";
	private static final String AWS_ACCESS_KEY_ID_KEY = "AWSAccessKeyId";
	private static final String S3_DOMAIN = "s3.amazonaws.com";
	private static final String HTTP = "http://";
	private static final String UTF_8 = "UTF-8";
	private static final String GET = "GET";
	private static final String HMAC_SHA1 = "HmacSHA1";
	private static final String VARIABLE_CALLBACK_URL = "CALLBACK_URL";
	private static final String VARIABLE_OUTPUT_FILENAME = "OUTPUT_FILENAME";
	private static final String VARIABLE_MP4SPLIT_PARAMETERS = "MP4SPLIT_PARAMETERS";
	private static final char EQUALS = '=';
	private static final char QUESTION_MARK = '?';
	private static final char AMPERSAND = '&';
	private static final char NEW_LINE = '\n';
	private static final char PERIOD = '.';
	private static final char SPACE = ' ';
	private static final String WORKING_DIRECTORY_KEY = "workingDirectory";
	private static final String CONTROL_BATCH_KEY = "controlBatch";
	private static final String COMMAND_KEY = "command";
	private static final String ENVIRONMENT_KEY = "environment";
	private static final String DRM_VALUES_KEY = "drmValues";
	private static final String ENTITY_ID_KEY = "entityId";
	private static final String PROTECT_WRAPPER_KEY = "protectWrapper";
	private static final String URI_ID_KEY = "uriId";
	private static final String SOURCE_SEGMENTS_KEY = "sourceSegments";
	private static final String MANIFEST_FILENAME_KEY = "manifestFilename";
	private static final String TARGET_LOCATION_KEY = "targetLocation";
	private static final String TARGET_SUBTITLE_LOCATION_KEY = "targetSubtitleLocation";
	private static final String SOURCE_SUBTITLE_LOCATION_KEY = "sourceSubtileLocation";
	private static final String SOURCE_LOCATION_KEY = "sourceLocation";
	private static final String GENERATE_KEYS_KEY = "generateKeys";
	private static final String VIDEO_ENTITY_ID = "videoEntityId";
	private static final String SUBTITLE_ENTITY_ID = "subtitleEntityId";
	private static final String SUBTITLE_SUBCONTENT_ENTITY_ID = "subtitleSubcontentEntityId";
	private static final String FILE_INFO_KEY = "fileInfo";
	private static final String ISMT_FILE_NAME = "ismtFileName";
	private static final String PROGRAM = "program";
	private static final String FILE_EXT_ISMT = ".ismt";
	private static final String FILE_EXT_SRT = ".srt";
	private static final String FILE_EXT_DFXP = ".dfhp";

	private static final Logger logger = LoggerFactory
			.getLogger(ProtectUSHelper.class);

	public static void setupVariables(ProcessContext kcontext) {
		// Input parameters
		ProtectWrapper protectWrapper = (ProtectWrapper) kcontext
				.getVariable(PROTECT_WRAPPER_KEY);
		String uriId = (String) kcontext.getVariable(URI_ID_KEY);
		String entityId = (String) kcontext.getVariable(ENTITY_ID_KEY);
		FilenameSegments segments = (FilenameSegments) kcontext
				.getVariable(SOURCE_SEGMENTS_KEY);
		setupControlRegisterContent(kcontext, protectWrapper, entityId, uriId,
				segments);
		setupGenerateProtectKeys(kcontext, protectWrapper, entityId, segments);
		String sourceLocationWithFilename = setupQTSFileCreate(kcontext,
				protectWrapper, entityId, segments);
		setupUploadProtectedFile(kcontext, protectWrapper,
				sourceLocationWithFilename);
		setupUploadSubtitleFile(kcontext);
		kcontext.setVariable(EXIT_STATUS, SUCCESS);
	}

	private static void setupControlRegisterContent(ProcessContext kcontext,
			ProtectWrapper protectWrapper, String entityId, String uriId,
			FilenameSegments segments) {
		String contentId = ProtectHelper.getControlAssetId(ENTITY_TYPE_PROGRAM,
				entityId, segments);
		String mediaId = ProtectHelper.getControlAssetId(ENTITY_TYPE_PROGRAM,
				entityId, segments);
		Integer policyId = protectWrapper.getPolicyGroupId();
		String subcontentType = ProtectHelper.getProfile(protectWrapper)
				.getSubcontentType();
		kcontext.setVariable(
				CONTROL_BATCH_KEY,
				getControlBatch(contentId, uriId, mediaId, policyId,
						subcontentType));
	}

	private static ControlBatch getControlBatch(String contentId,
			String programUriId, String mediaId, Integer policyId,
			String subcontentType) {
		try {
			List<ControlMedia> mediaList = new ArrayList<>();
			mediaList.add(getMedia(contentId, programUriId, mediaId, policyId,
					subcontentType));
			ControlBatch controlBatch = new ControlBatch();
			controlBatch.setMediaList(mediaList);
			return controlBatch;
		} catch (Exception e) {
		}
		return null;
	}

	private static ControlMedia getMedia(String contentId, String programUriId,
			String mediaId, Integer policyId, String subcontentType)
			throws PropertyException {
		ControlMedia media = new ControlMedia();
		String accountId = BeanUtil.propertiesManager
				.getProperty(JumpstartPropertyKey.CPS_ACCOUNT_ID_KEY);
		media.setAccountId(accountId);
		media.setMediaId(mediaId);
		media.setName(programUriId);
		List<ControlContent> contentList = new ArrayList<>();
		contentList.add(getContent(contentId, programUriId, mediaId, policyId,
				subcontentType));
		media.setContentList(contentList);
		return media;
	}

	private static ControlContent getContent(String contentId,
			String programUriId, String mediaId, Integer policyId,
			String subcontentType) throws PropertyException {
		ControlContent controlContent = new ControlContent();
		String accountId = BeanUtil.propertiesManager
				.getProperty(JumpstartPropertyKey.CPS_ACCOUNT_ID_KEY);
		controlContent.setAccountId(accountId);
		controlContent.setContentId(contentId);
		controlContent.setMediaId(mediaId);
		controlContent.setPolicyId(policyId.toString());

		List<ControlSubContent> subContentList = new ArrayList<>();
		subContentList.add(getSubContent(contentId, programUriId, mediaId,
				policyId, subcontentType));
		controlContent.setSubContentList(subContentList);

		return controlContent;
	}

	private static ControlSubContent getSubContent(String contentId,
			String programUriId, String mediaId, Integer policyId,
			String subcontentType) throws PropertyException {
		ControlSubContent subContent = new ControlSubContent();
		String accountId = BeanUtil.propertiesManager
				.getProperty(JumpstartPropertyKey.CPS_ACCOUNT_ID_KEY);
		subContent.setAccountId(accountId);
		subContent.setContentId(contentId);
		subContent.setMediaId(mediaId);
		subContent.setType(subcontentType);
		return subContent;
	}

	private static void setupGenerateProtectKeys(ProcessContext kcontext,
			ProtectWrapper protectWrapper, String entityId,
			FilenameSegments segments) {
		String contentId = ProtectHelper.getControlAssetId(ENTITY_TYPE_PROGRAM,
				entityId, segments);
		List<String> protectionSystemList = ProtectHelper.getProfile(
				protectWrapper).getProtectionSystemList();
		if (protectionSystemList == null || protectionSystemList.isEmpty()) {
			kcontext.setVariable(GENERATE_KEYS_KEY, false);
		} else {
			kcontext.setVariable(
					LIVE_DRM_SOAP_ENVELOPE_KEY,
					getGenerateKeysLiveDrmSoapEnvelope(protectWrapper,
							contentId));
			kcontext.setVariable(LIVE_DRM_RESPONSE_PROCESSOR_KEY,
					new GenerateKeysLiveDrmResponseProcessor());
			kcontext.setVariable(GENERATE_KEYS_KEY, true);
		}
	}

	private static LiveDrmSoapEnvelope getGenerateKeysLiveDrmSoapEnvelope(
			ProtectWrapper protectWrapper, String contentId) {
		GenerateKeysObjectFactory objectFactory = new GenerateKeysObjectFactory();
		Protect protect = protectWrapper.getRepresentativeProtect();
		DRMProfile drmProfile = ProtectHelper.getProfile(protect);
		objectFactory.setContentId(contentId);
		objectFactory.setProtectionSystemList(drmProfile
				.getProtectionSystemList());
		return objectFactory.createSoapMsgEnvelope();
	}

	public static void setupRunMp4SplitCommandForSubtitles(ProcessContext kcontext) {
		Program program =  (Program) kcontext.getVariable(PROGRAM);
		String videoEntityId = (String) kcontext.getVariable(VIDEO_ENTITY_ID);
		String subtitleEntityId = (String) kcontext.getVariable(SUBTITLE_ENTITY_ID);
		String subtitleSubcontentEntityId = (String) kcontext.getVariable(SUBTITLE_SUBCONTENT_ENTITY_ID);

		VideoContent videoContent = getEntityById(program.getVideoContent(),videoEntityId);
		SubtitleContent subtitleContent = getEntityById(videoContent.getSubtitleContent(), subtitleEntityId);
		SubtitleSubcontent subtitleSubcontent = getEntityById(subtitleContent.getSubcontent(), subtitleSubcontentEntityId);

		// to convert subtitles to .ismt files
		String subtitleInputFile = subtitleSubcontent.getSourcePath();
		String ismtFileName = FileHelper.getFilenameWithoutPathAndExt(subtitleInputFile)+FILE_EXT_ISMT;

		try {
			kcontext.setVariable(
					WORKING_DIRECTORY_KEY,
					BeanUtil.propertiesManager
							.getProperty(JumpstartPropertyKey.US_ROOT_DIRECTORY_KEY));
			kcontext.setVariable(
					ENVIRONMENT_KEY,
					getEnvironmentForSubtitles(subtitleInputFile, ismtFileName));
			kcontext.setVariable(
					COMMAND_KEY,
					getCommand(
							BeanUtil.propertiesManager
									.getProperty(JumpstartPropertyKey.US_SERVER_KEY),
							BeanUtil.propertiesManager
									.getProperty(JumpstartPropertyKey.MP4SPLIT_REMOTE_SUBTITLES_SCRIPT_LOCATION_KEY)));
			kcontext.setVariable(ISMT_FILE_NAME, ismtFileName);
		} catch (PropertyException e) {
		}
	}

	@SuppressWarnings("unchecked")
	public static void setupRunMp4SplitCommand(ProcessContext kcontext) {
		ProtectWrapper protectWrapper = (ProtectWrapper) kcontext
				.getVariable(PROTECT_WRAPPER_KEY);
		String manifestFilename = (String) kcontext
				.getVariable(MANIFEST_FILENAME_KEY);
		FilenameSegments segments = (FilenameSegments) kcontext
				.getVariable(SOURCE_SEGMENTS_KEY);
		Protect protect = protectWrapper.getRepresentativeProtect();
		DRMProfile drmProfile = ProtectHelper.getProfile(protect);
		List<Map<String, String>> drmValues = (List<Map<String, String>>) kcontext
				.getVariable(DRM_VALUES_KEY);
		String entityId = (String) kcontext.getVariable(ENTITY_ID_KEY);

		List<String> inputFiles = protectWrapper.getSourceFileList();
		try {
			kcontext.setVariable(
					WORKING_DIRECTORY_KEY,
					BeanUtil.propertiesManager
							.getProperty(JumpstartPropertyKey.US_ROOT_DIRECTORY_KEY));
			kcontext.setVariable(
					ENVIRONMENT_KEY,
					getEnvironment(drmValues, entityId, ENTITY_TYPE_PROGRAM,
							segments, inputFiles, drmProfile, manifestFilename,kcontext));
			kcontext.setVariable(
					COMMAND_KEY,
					getCommand(
							BeanUtil.propertiesManager
									.getProperty(JumpstartPropertyKey.US_SERVER_KEY),
							BeanUtil.propertiesManager
									.getProperty(JumpstartPropertyKey.MP4SPLIT_REMOTE_SCRIPT_LOCATION_KEY)));
		} catch (PropertyException e) {
		}
	}

	protected static String getCommand(String usServer, String script) {
		return "ssh -n -f " + usServer + " " + script;
	}

	protected static List<String> getEnvironment(
			List<Map<String, String>> drmValues, String entityId,
			String entityType, FilenameSegments segments,
			List<String> inputFiles, DRMProfile drmProfile,
			String manifestFilename,ProcessContext kcontext) {
		try {
			String signatureLifeTime = BeanUtil.propertiesManager
					.getProperty(JumpstartPropertyKey.S3_SIGNATURE_EXPIRY_KEY);
			String transcodedBucket = BeanUtil.propertiesManager
					.getProperty(JumpstartPropertyKey.S3_BUCKET_TRANSCODED_KEY);
			String sourceBucket = BeanUtil.propertiesManager
					.getProperty(JumpstartPropertyKey.S3_BUCKET_SOURCE_KEY);
			String inputSecret = BeanUtil.propertiesManager
					.getProperty(JumpstartPropertyKey.S3_SECRET_KEY_KEY);
			String inputKey = BeanUtil.propertiesManager
					.getProperty(JumpstartPropertyKey.S3_ACCESS_KEY_KEY);
			String storageType = BeanUtil.propertiesManager
					.getProperty(JumpstartPropertyKey.STORAGE_TYPE_KEY);
			List<String> environment = new ArrayList<>();

			environment
					.add(VARIABLE_CALLBACK_URL
							+ EQUALS
							+ BeanUtil.propertiesManager
									.getProperty(JumpstartPropertyKey.COMMAND_LINE_CALLBACK_URL_KEY));
			environment.add(VARIABLE_OUTPUT_FILENAME + EQUALS
					+ manifestFilename);
			if (!(drmProfile instanceof AbstractUSDRMProfile)) {
				logger.error(
						"DRM Profile is not for Unified Streaming.  DRM Profile is {}",
						drmProfile.getSubcontentType());
				return environment;
			}
			StringBuffer parameters = new StringBuffer();
			AbstractUSDRMProfile usDrmProfile = (AbstractUSDRMProfile) drmProfile;
			parameters.append(usDrmProfile.getMp4SplitParameters(drmValues,
				entityId, entityType, segments));
		    parameters.append(SPACE);

			// Add ismt files to the input file list for the mp4 split task handler
		    String videoEntityId = (String) kcontext.getVariable(VIDEO_ENTITY_ID);
			Program program = (Program) kcontext.getVariable("program");
			List<VideoContent> videoContentList =  program.getVideoContent();
			VideoContent videoContent = getEntityById(videoContentList, videoEntityId);
			List<SubtitleContent> subtitleContentList = videoContent.getSubtitleContent();
			List<String> inputSubtitleFiles = new ArrayList<>();

			for (SubtitleContent subtitleContent: subtitleContentList) {
				for (SubtitleSubcontent subtitleSubcontent : subtitleContent.getSubcontent()) {
					if (StringUtils.endsWith(subtitleSubcontent.getSourcePath(), FILE_EXT_ISMT))
						inputSubtitleFiles.add(subtitleSubcontent.getSourcePath());
				}
			}

			if (WorkflowHelper.S3_KEY.equals(storageType)) {
				parameters.append(getSignedFiles(inputFiles,
						Integer.parseInt(signatureLifeTime), transcodedBucket,
						inputSecret, inputKey));
			} else if (WorkflowHelper.VM_KEY.equals(storageType)) {
				String transcodedUrlRoot = BeanUtil.propertiesManager
						.getProperty(JumpstartPropertyKey.TRANSCODE_URL_ROOT);
				parameters.append(getFiles(inputFiles,transcodedUrlRoot));
			}

			if(!inputSubtitleFiles.isEmpty()){
				parameters.append(SPACE);
				if (WorkflowHelper.S3_KEY.equals(storageType)) {
					parameters.append(getSignedFiles(inputSubtitleFiles,
							Integer.parseInt(signatureLifeTime), sourceBucket,
							inputSecret, inputKey));
				} else if (WorkflowHelper.VM_KEY.equals(storageType)) {
					String sourceUrlRoot = BeanUtil.propertiesManager
							.getProperty(JumpstartPropertyKey.SOURCE_URL_ROOT);
					parameters.append(getFiles(inputSubtitleFiles,sourceUrlRoot));
				}
			}

			environment.add(VARIABLE_MP4SPLIT_PARAMETERS + EQUALS
					+ parameters.toString());
			return environment;
		} catch (PropertyException | NumberFormatException e) {
			return new ArrayList<>();
		}
	}

	protected static List<String> getEnvironmentForSubtitles(
			String subtitleInputFile,String ismtFilename) {
		try {
			String signatureLifeTime = BeanUtil.propertiesManager
					.getProperty(JumpstartPropertyKey.S3_SIGNATURE_EXPIRY_KEY);
			String sourceBucket = BeanUtil.propertiesManager
					.getProperty(JumpstartPropertyKey.S3_BUCKET_SOURCE_KEY);
			String inputSecret = BeanUtil.propertiesManager
					.getProperty(JumpstartPropertyKey.S3_SECRET_KEY_KEY);
			String inputKey = BeanUtil.propertiesManager
					.getProperty(JumpstartPropertyKey.S3_ACCESS_KEY_KEY);
			String storageType = BeanUtil.propertiesManager
					.getProperty(JumpstartPropertyKey.STORAGE_TYPE_KEY);
			String sourceUrlRoot = BeanUtil.propertiesManager
					.getProperty(JumpstartPropertyKey.SOURCE_URL_ROOT);
			List<String> environment = new ArrayList<>();

			environment
					.add(VARIABLE_CALLBACK_URL
							+ EQUALS
							+ BeanUtil.propertiesManager
									.getProperty(JumpstartPropertyKey.COMMAND_LINE_CALLBACK_URL_KEY));
			environment.add(VARIABLE_OUTPUT_FILENAME + EQUALS
					+ ismtFilename);

			StringBuffer parameters = new StringBuffer();

			if (WorkflowHelper.S3_KEY.equals(storageType)) {
				long currentTimeInSeconds = System.currentTimeMillis() / 1000;
				parameters.append(getSignedFile(subtitleInputFile,
						Integer.parseInt(signatureLifeTime), sourceBucket,
						inputSecret, inputKey,currentTimeInSeconds));
			} else if (WorkflowHelper.VM_KEY.equals(storageType)) {
				parameters.append(getFile(subtitleInputFile,sourceUrlRoot));
			}
			environment.add(VARIABLE_MP4SPLIT_PARAMETERS + EQUALS
					+ parameters.toString());
			return environment;
		} catch (PropertyException | NumberFormatException e) {
			return new ArrayList<>();
		}
	}


	private static String setupQTSFileCreate(ProcessContext kcontext,
			ProtectWrapper protectWrapper, String entityId,
			FilenameSegments segments) {
		String filename = getManifestFilename(protectWrapper, entityId,
				segments);
		kcontext.setVariable(MANIFEST_FILENAME_KEY, filename);
		String sourceLocation = getSourceLocation(protectWrapper, entityId,
				segments);
		kcontext.setVariable(SOURCE_LOCATION_KEY, sourceLocation);
		return sourceLocation + filename;
	}

	private static String getManifestFilename(ProtectWrapper protectWrapper,
			String entityId, FilenameSegments segments) {
		return ProtectHelper.getProfile(protectWrapper).getUSManifestFilename(
				entityId, segments);
	}

	private static String getSourceLocation(ProtectWrapper protectWrapper,
			String entityId, FilenameSegments segments) {
		return ProtectHelper.getProfile(protectWrapper).getTargetEncrypt(
				segments, protectWrapper.getPolicyGroupId());
	}

	protected static String getSignedFile(String file,int signatureLifeTime, String inputBucket, String inputSecret,
			String inputKey,long currentTimeInSeconds){
		long signatureExpiryTime = currentTimeInSeconds + signatureLifeTime;
			String shortFile = FileHelper.getFilenameWithoutPath(file);
			String signedFileUrl = getSignedFileUrl(signatureExpiryTime,
					inputBucket, shortFile, inputSecret, inputKey);
				return signedFileUrl;
	}

	protected static String getSignedFiles(List<String> inputFiles,
			int signatureLifeTime, String inputBucket, String inputSecret,
			String inputKey) {
		List<String> signedInputFiles = new ArrayList<>();
		long currentTimeInSeconds = System.currentTimeMillis() / 1000;
		for (String file : inputFiles) {
			String signedFileUrl = getSignedFile(file,signatureLifeTime,
					inputBucket, inputSecret, inputKey,currentTimeInSeconds);
			if (signedFileUrl != null) {
				signedInputFiles.add(signedFileUrl);
			}
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : signedInputFiles) {
			if (flag) {
				result.append(SPACE);
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	private static String getFile(String inputFile,String urlRoot) {
			if (!urlRoot.endsWith(SLASH)) {
				urlRoot = urlRoot + SLASH;
			}
			String shortFile = FileHelper.getFilenameWithoutPath(inputFile);
			return (urlRoot + shortFile);
	}

	protected static String getFiles(List<String> inputFiles,String urlRoot) {
		StringBuffer files = new StringBuffer();
			boolean flag = false;
			for (String file : inputFiles) {
				if (flag) {
					files.append(SPACE);
				} else {
					flag = true;
				}
				files.append(getFile(file,urlRoot));
			}
		return files.toString();
	}

	protected static String getSignedFileUrl(long signatureExpiryTime,
			String inputBucket, String file, String inputSecret, String inputKey) {
		StringBuffer string = new StringBuffer();
		string.append(GET).append(NEW_LINE).append(NEW_LINE).append(NEW_LINE)
				.append(String.valueOf(signatureExpiryTime)).append(NEW_LINE)
				.append(SLASH).append(inputBucket).append(SLASH).append(file);
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1);
			SecretKeySpec secret = new SecretKeySpec(inputSecret.getBytes(),
					HMAC_SHA1);
			mac.init(secret);
			byte[] signedString = mac.doFinal(string.toString().getBytes());
			byte[] base64EncodedSignedString = Base64
					.encodeBase64(signedString);

			String urlEncodedBase64EncodedSignedString = URLEncoder.encode(
					new String(base64EncodedSignedString, UTF_8), UTF_8);
			StringBuffer signedFileUrl = new StringBuffer();
			signedFileUrl.append(HTTP).append(inputBucket).append(PERIOD)
					.append(S3_DOMAIN).append(SLASH).append(file)
					.append(QUESTION_MARK).append(AWS_ACCESS_KEY_ID_KEY)
					.append(EQUALS).append(inputKey).append(AMPERSAND)
					.append(EXPIRES_KEY).append(EQUALS)
					.append(String.valueOf(signatureExpiryTime))
					.append(AMPERSAND).append(SIGNATURE_KEY).append(EQUALS)
					.append(urlEncodedBase64EncodedSignedString);
			return signedFileUrl.toString();
		} catch (NoSuchAlgorithmException | InvalidKeyException
				| UnsupportedEncodingException e) {
			logger.error("Unable to sign AWS S3 URL.", e);
		}
		return null;
	}

	private static void setupUploadProtectedFile(ProcessContext kcontext,
			ProtectWrapper protectWrapper, String sourceLocationWithFilename) {
		kcontext.setVariable(TARGET_LOCATION_KEY,
				getTargetLocation(protectWrapper, sourceLocationWithFilename));
	}

	private static void setupUploadSubtitleFile(ProcessContext kcontext) {
		String sourceLocation;
		try {
			sourceLocation = BeanUtil.propertiesManager
					.getProperty(JumpstartPropertyKey.US_PATH_ROOT_KEY);
			kcontext.setVariable(TARGET_SUBTITLE_LOCATION_KEY, FileHelper.addHybridBaseURLAndPath(null, WorkflowHelper.SOURCE_TYPE)+SLASH);
			kcontext.setVariable(SOURCE_SUBTITLE_LOCATION_KEY, sourceLocation);
		} catch (PropertyException e) {
		}

	}

	private static String getTargetLocation(ProtectWrapper protectWrapper,
			String sourceLocationWithFilename) {
		return ProtectHelper.getProfile(protectWrapper)
				.getTargetUploadAfterProtect(sourceLocationWithFilename)
				+ SLASH;
	}

	public static void setUpCopyFromOrigin(ProcessContext kcontext) {
		try {
			String usServer = BeanUtil.propertiesManager
			.getProperty(JumpstartPropertyKey.US_SERVER_KEY);
			String fileName = (String) kcontext.getVariable(ISMT_FILE_NAME);
			String command = "/usr/bin/scp "+usServer+":"+fileName+" .";
			kcontext.setVariable(COMMAND_KEY, command);
		} catch (PropertyException e) {
		}
	}

	public static void setUpDeleteFromOrigin(ProcessContext kcontext){
		try {
			String usServer = BeanUtil.propertiesManager
			.getProperty(JumpstartPropertyKey.US_SERVER_KEY);
			String fileName = (String) kcontext.getVariable(ISMT_FILE_NAME);
			String command = "/usr/bin/ssh "+usServer+" /bin/rm -f "+fileName;
			kcontext.setVariable(COMMAND_KEY, command);
		} catch (PropertyException e) {
		}
	}

	public static void setUpFTPSubtitles (ProcessContext kcontext){
		try {
		String ftpUsername = BeanUtil.propertiesManager
				.getProperty(JumpstartPropertyKey.CPS_FTP_USER_KEY);
		String ftpPassword = BeanUtil.propertiesManager
				.getProperty(JumpstartPropertyKey.CPS_FTP_PASSWORD_KEY);
		String ftpUrl = BeanUtil.propertiesManager
				.getProperty(JumpstartPropertyKey.CPS_FTP_URL_KEY);
		String ftpTarget = BeanUtil.propertiesManager
				.getProperty(JumpstartPropertyKey.CPS_FTP_PATH_KEY);

		 kcontext.setVariable("ftpUsername", ftpUsername);
		 kcontext.setVariable("ftpPassword", ftpPassword);
		 kcontext.setVariable("ftpUrl", ftpUrl);
		 kcontext.setVariable("ftpTarget", ftpTarget);
		} catch (PropertyException e) {
		}
	}


	public static void getNextSubtitleSubcontent(ProcessContext kcontext) {
		String videoEntityId = (String) kcontext.getVariable(VIDEO_ENTITY_ID);
		String subtitleEntityId = (String) kcontext
				.getVariable(SUBTITLE_ENTITY_ID);
		String subtitleSubcontentEntityId = (String) kcontext
				.getVariable(SUBTITLE_SUBCONTENT_ENTITY_ID);
		Program program = (Program) kcontext.getVariable("program");
		List<VideoContent> videoContentList = program.getVideoContent();
		VideoContent videoContent = getEntityById(videoContentList, videoEntityId);
		if (videoContent.getSubtitleContent() == null || videoContent.getSubtitleContent().isEmpty()) {
			// No subtitles.
			kcontext.setVariable(SUBTITLE_ENTITY_ID, null);
			kcontext.setVariable(SUBTITLE_SUBCONTENT_ENTITY_ID, null);
			return;
		}
		List<SubtitleContent> subtitleContentList = videoContent.getSubtitleContent();
		SubtitleContent subtitleContent = getEntityById(subtitleContentList, subtitleEntityId);
		if (subtitleContent == null) {
			// Maybe this is the first time looking, needs to set the first subtitleContent
			subtitleContent = getNextEntity(subtitleContentList, subtitleEntityId);
			subtitleSubcontentEntityId = null;
			if (subtitleContent == null) {
				// No more entities
				kcontext.setVariable(SUBTITLE_ENTITY_ID, null);
				kcontext.setVariable(SUBTITLE_SUBCONTENT_ENTITY_ID, null);
				return;
			} else {
				subtitleEntityId = subtitleContent.getId();
			}
		}

		while (true) {
			SubtitleSubcontent subtitleSubcontent = getNextEntity(subtitleContent.getSubcontent(), subtitleSubcontentEntityId);
			if (subtitleSubcontent == null) {
				// No more subtitle subcontent
				subtitleSubcontentEntityId = null;
				subtitleContent = getNextEntity(subtitleContentList, subtitleEntityId);
				if (subtitleContent == null) {
					// No more subtitle content
					kcontext.setVariable(SUBTITLE_ENTITY_ID, null);
					kcontext.setVariable(SUBTITLE_SUBCONTENT_ENTITY_ID, null);
					return;
				} else {
					//  There is more subtitle content
					subtitleEntityId = subtitleContent.getId();
					continue;
				}
			} else {
				// Has subtitle subcontent
				subtitleSubcontentEntityId = subtitleSubcontent.getId();
				boolean ismtFound = false;
				if(StringUtils.endsWithAny(subtitleSubcontent.getSourcePath(), FILE_EXT_SRT,FILE_EXT_DFXP)){
					for(SubtitleContent iterateSubtitleContent : videoContent.getSubtitleContent()){
						for(SubtitleSubcontent iterateSubtitleSubcontent : iterateSubtitleContent.getSubcontent()){
							if(StringUtils.endsWith(iterateSubtitleSubcontent.getSourcePath(), FILE_EXT_ISMT) && iterateSubtitleContent.getLanguage().equals(subtitleContent.getLanguage())){
								ismtFound = true;
								break;
							}
						}
						if (ismtFound){
							break;
						}
					}
					if(ismtFound) {
						continue;
					} else {
						kcontext.setVariable(SUBTITLE_ENTITY_ID, subtitleEntityId);
						kcontext.setVariable(SUBTITLE_SUBCONTENT_ENTITY_ID, subtitleSubcontentEntityId);
						return;
					}
				}
			}
		}
	}


	public static void logEntityIDs(ProcessContext kcontext){
		logger.debug("video entity id is...{}",kcontext.getVariable(VIDEO_ENTITY_ID));
		logger.debug("subtitle entity id is..{}.",kcontext.getVariable(SUBTITLE_ENTITY_ID));
		logger.debug("subtitle subcontent entity id is..{}.",kcontext.getVariable(SUBTITLE_SUBCONTENT_ENTITY_ID));
	}

	public static void createSubtitleSubcontent(ProcessContext kcontext){
		Program program =  (Program) kcontext.getVariable(PROGRAM);
		String videoEntityId = (String) kcontext.getVariable(VIDEO_ENTITY_ID);
		String subtitleEntityId = (String) kcontext.getVariable(SUBTITLE_ENTITY_ID);
		QTSHandleEventFileInfo fileInfo = (QTSHandleEventFileInfo) kcontext.getVariable(FILE_INFO_KEY);
		String subtitleLocation =  (String) kcontext.getVariable(TARGET_SUBTITLE_LOCATION_KEY);
		VideoContent videoContent = getEntityById(program.getVideoContent(),videoEntityId);
		SubtitleContent subtitleContent = getEntityById(videoContent.getSubtitleContent(), subtitleEntityId);
		SubtitleSubcontent newSubtileSubcontent = new SubtitleSubcontent();
		newSubtileSubcontent.setContentCheckSum(fileInfo.getHashCode());
		newSubtileSubcontent.setContentFileSize(fileInfo.getFileSize());
		newSubtileSubcontent.setSourcePath(subtitleLocation+FileHelper.getFilenameWithoutPath(fileInfo.getFile()));
		subtitleContent.getSubcontent().add(newSubtileSubcontent);
	}

	private static <T extends BaseEntity> T getNextEntity(List<T> entityList, String currentEntityId) {
		if(entityList == null || entityList.isEmpty()){
			return null;
		}
		Collections.sort(entityList, new BaseEntityComparator());
		if(currentEntityId == null){
		return entityList.get(0);
		}
		else {
			for (T entity : entityList){
				if(Integer.valueOf(entity.getId()) > Integer.valueOf(currentEntityId) ){
					return entity;
				}
			}
		}
		return null;
	}

	private static <T extends BaseEntity> T getEntityById(List<T> entityList,
			String id) {
		for (T itertorEntity : entityList) {
			if (itertorEntity.getId().equals(id)) {
				return itertorEntity;
			}
		}
		return null;
	}

	static class BaseEntityComparator implements Comparator<BaseEntity> {

		@Override
		public int compare(BaseEntity o1, BaseEntity o2) {
			return o1.getId().compareTo(o2.getId());
		}

	}
}
