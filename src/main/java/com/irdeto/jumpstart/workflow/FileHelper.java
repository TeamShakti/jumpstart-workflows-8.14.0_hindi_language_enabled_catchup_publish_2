package com.irdeto.jumpstart.workflow;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.Content;
import com.irdeto.jumpstart.domain.ImageSubcontent;
import com.irdeto.jumpstart.domain.ProtectVideoSub;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.Subcontent;
import com.irdeto.jumpstart.domain.SubtitleSubcontent;
import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.workflow.config.DeviceProfileHelper;
import com.irdeto.jumpstart.workflow.protect.ProtectHelper;
import com.irdeto.manager.properties.PropertyException;
import com.irdeto.manager.task.BeanUtil;

public class FileHelper extends WorkflowHelper {
	private static final String PERCENT = "%";
	private static final String S3_PROTOCOL_PREFIX = "s3://";

	public static final String EMPTY_STRING = "";
	public static final String CRLF = "\r\n";
	public static final String SEPARATOR_PERIOD = ".";
	public static final String SEPARATOR_FILENAME_PART = "-";
	public static final String EXTENSION_SMOOTHSTREAM_ISM = "ism";
	public static final String EXTENSION_SMOOTHSTREAM_ISMC = "ismc";
	public static final String EXTENSION_MP4 = "mp4";
	public static final String EXTENSION_M3U8 = "m3u8";
	public static final String AC_D2G_KEY = "AC_D2G";
	public static final String PATH_HLS = "HLS";
	
	public static String getUniqueId(BaseEntity content) {
		if (content != null) {
			return getUniqueId(content.getCreatedDate());
		} else {
			return "";
		}
	}

	public static String getUniqueId(DateTime contentCreatedDate) {
		if (contentCreatedDate != null) {
			return Long.toString(contentCreatedDate.getMillis(), Character.MAX_RADIX);
		} else {
			return "";
		}
	}

	protected static String getExtension(String filename) {
		int periodPosition = StringUtils.lastIndexOf(filename, SEPARATOR_PERIOD);
		if (periodPosition > -1) {
			return StringUtils.substring(filename, periodPosition + 1);
		} else {
			return EMPTY_STRING;
		}
	}

	public static String getFilenameWithoutPathAndExt(String filename) {
		String filenameWithoutPath = getFilenameWithoutPath(filename);
		int periodPosition = StringUtils.lastIndexOf(filenameWithoutPath, SEPARATOR_PERIOD);
		if (periodPosition > -1) {
			return StringUtils.substring(filenameWithoutPath, 0, periodPosition);
		} else {
			return filenameWithoutPath;
		}
	}
	
	public static String getFilenameWithoutPath(String filename) {
		String filenameWithoutPath = filename;
		int pathSeparatorIndex = StringUtils.lastIndexOf(filename, SLASH);
		if (pathSeparatorIndex != -1) {
			filenameWithoutPath = StringUtils.substring(filename, pathSeparatorIndex + 1);
		}
		pathSeparatorIndex = StringUtils.lastIndexOf(filenameWithoutPath, BACKSLASH);
		if (pathSeparatorIndex != -1) {
			filenameWithoutPath = StringUtils.substring(filenameWithoutPath, pathSeparatorIndex + 1);
		}
		return filenameWithoutPath;
	}
	
	public static String getFilePathWithoutFilename(String filenameWithPath) {
		String filepathWithoutFilename = filenameWithPath;
		int pathSeparatorIndex = StringUtils.lastIndexOf(filenameWithPath, SLASH);
		if (pathSeparatorIndex != -1) {
			filepathWithoutFilename = StringUtils.substring(filenameWithPath, 0, pathSeparatorIndex);
		}
		pathSeparatorIndex = StringUtils.lastIndexOf(filepathWithoutFilename, BACKSLASH);
		if (pathSeparatorIndex != -1) {
			filepathWithoutFilename = StringUtils.substring(filepathWithoutFilename, 0, pathSeparatorIndex);
		}
		return filepathWithoutFilename;
	}
	
	public static String getFilenameRoot(String filename, int segmentsToRemove) {
		String filenameWithoutPath = getFilenameWithoutPath(filename);
		String basename = getFilenameWithoutPathAndExt(filenameWithoutPath);
		for (int i = 0; i < segmentsToRemove; i++) {
			int separatorIndex = basename.lastIndexOf(SEPARATOR_FILENAME_PART);
			if (separatorIndex > -1) {
				basename = basename.substring(0, separatorIndex);
			}
		}
		return basename;
	}
	public static String getFileBaseName(String path){
		return getFilenameRoot(path, 0);
	}

	public static String getFilenameRootWithPath(String filename, int segmentsToRemove) {
		String basename = getFilenameWithoutPathAndExt(filename);
		for (int i = 0; i < segmentsToRemove; i++) {
			int separatorIndex = basename.lastIndexOf(SEPARATOR_FILENAME_PART);
			if (separatorIndex > -1) {
				basename = basename.substring(0, separatorIndex);
			}
		}
		return basename;
	}
	public static String getLastFoldername(String filename) {
		String foldername = getFilePathWithoutFilename(filename);
		int pathSeparatorIndex = StringUtils.lastIndexOf(foldername, SLASH);
		if (pathSeparatorIndex != -1) {
			foldername = StringUtils.substring(foldername, pathSeparatorIndex+1);
		}
		pathSeparatorIndex = StringUtils.lastIndexOf(foldername, BACKSLASH);
		if (pathSeparatorIndex != -1) {
			foldername = StringUtils.substring(foldername, pathSeparatorIndex+1);
		}
		
		return foldername;
	}

	protected static boolean isD2GMp4VideoFile(String filename, String acD2GFileSuffix) {
		return StringUtils.endsWith(filename, SEPARATOR_FILENAME_PART + acD2GFileSuffix + SEPARATOR_PERIOD + EXTENSION_MP4);
	}
		
	public static String buildUpHybridPath(String...folderNames){
		String fullPath = EMPTY_STRING;
		try {
			String storageType = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.STORAGE_TYPE_KEY);
			if(storageType.equals(S3_KEY)){
				for(String folderName: folderNames){
					if (StringUtils.endsWith(folderName, SLASH) || StringUtils.endsWith(folderName, BACKSLASH)) {
						folderName = folderName.substring(0, folderName.length() - 1);
					}
					if(fullPath.isEmpty())
						fullPath = folderName;
					else
						fullPath = fullPath+SLASH+folderName;
				}
			}else if(storageType.equals(VM_KEY)){
				for(String folderName: folderNames){
					if (StringUtils.endsWith(folderName, SLASH) || StringUtils.endsWith(folderName, BACKSLASH)) {
						folderName = folderName.substring(0, folderName.length() - 1);
					}
					if(fullPath.isEmpty())
						fullPath = folderName;
					else
						fullPath = fullPath+BACKSLASH+folderName;
				}
			}
		} catch (PropertyException e) {
		}
		return fullPath;
	}
	public static String getBaseUrlbyType(String type){
		String baseURL = EMPTY_STRING;
		String filePath = EMPTY_STRING;
		try {
			String storageType = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.STORAGE_TYPE_KEY);
			if(storageType.equals(S3_KEY)){
				baseURL = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.S3_SOURCE_URL_KEY);
				filePath = EMPTY_STRING;
				if(type.equals(SOURCE_TYPE)){
					filePath = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.S3_BUCKET_SOURCE_KEY);
				}else if(type.equals(TRANSCODED_TYPE)){
					filePath = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.S3_BUCKET_TRANSCODED_KEY);
				}else if(type.equals(PROTECTED_TYPE)){
					filePath = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.S3_BUCKET_PROTECTED_KEY);
				}else if(type.equals(CDN_TYPE)){
					filePath = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.S3_BUCKET_CDN_KEY);
				}
			} else if(storageType.equals(VM_KEY)){
				baseURL = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.VM_BASE_URL_KEY);
				filePath = EMPTY_STRING;
				if(type.equals(SOURCE_TYPE)){
					filePath = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.VM_MEZZANINE_FOLDER_KEY);
				}else if(type.equals(TRANSCODED_TYPE)){
					filePath = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.VM_TRANSCODED_FOLDER_KEY);
				}else if(type.equals(PROTECTED_TYPE)){
					filePath = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.VM_PROTECTED_FOLDER_KEY);
				}else if(type.equals(CDN_TYPE)){
					filePath = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.VM_CDN_FOLDER_KEY);
				}
			}
		} catch (PropertyException e) {
		}
		return buildUpHybridPath(baseURL, filePath);
	}
	public static String addHybridBaseURLAndPath(String endPoint, String type) {
		if(StringUtils.isBlank(endPoint)){
			return buildUpHybridPath(getBaseUrlbyType(type));
		} else {
		return buildUpHybridPath(getBaseUrlbyType(type), endPoint);
		}
	}
	public static class FileIngestHelper {
		public static String getSourceFilename(Content content){
			return content.getType() + SEPARATOR_FILENAME_PART
					+ content.getId() + SEPARATOR_FILENAME_PART
					+ getUniqueId(content) + SEPARATOR_FILENAME_PART
					+ "m" + content.getSourceVersion()
					+ SEPARATOR_PERIOD + FilenameUtils.getExtension(content.getSourceUrl());
		}
		public static String getSourceFileUrl(Content content){
			return addHybridBaseURLAndPath(getSourceFilename(content), SOURCE_TYPE);
		}
		
		public static String addHybridBaseURLAndPathForIngest(String endPoint) {
			return addHybridBaseURLAndPath(endPoint, SOURCE_TYPE);
		}
		public static String getIngestSourceUrl(String filename) {
			return addHybridBaseURLAndPathForIngest(filename);
		}
		
		public static String getIngestFilenameWithVersion(String filename, Integer version) {
			return getFilenameWithoutPathAndExt(filename) + SEPARATOR_FILENAME_PART + String.valueOf(version) + SEPARATOR_PERIOD + getExtension(filename);
		}

		public static String getIngestEntityTypeFromFilename(String filename) {
			String entityType = null;
			if (StringUtils.containsIgnoreCase(filename, SEPARATOR_FILENAME_PART + ENTITY_TYPE_IMAGE_CONTENT + SEPARATOR_FILENAME_PART)) {
				entityType = ENTITY_TYPE_IMAGE_CONTENT;
			}
			if (StringUtils.containsIgnoreCase(filename, SEPARATOR_FILENAME_PART + ENTITY_TYPE_VIDEO_CONTENT + SEPARATOR_FILENAME_PART)) {
				entityType = ENTITY_TYPE_VIDEO_CONTENT;
			}
			return entityType;
		}
		
		public static String getIngestEntityTypeFromMdIdentifier(String mdIdentifier) {
			String entityType = null;
			if (StringUtils.containsIgnoreCase(mdIdentifier, ENTITY_TYPE_IMAGE_CONTENT)) {
				entityType = ENTITY_TYPE_IMAGE_CONTENT;
			}
			if (StringUtils.containsIgnoreCase(mdIdentifier, ENTITY_TYPE_VIDEO_CONTENT)) {
				entityType = ENTITY_TYPE_VIDEO_CONTENT;
			}
			if (StringUtils.containsIgnoreCase(mdIdentifier, ENTITY_TYPE_SUBTITLE_CONTENT)) {
				entityType = ENTITY_TYPE_SUBTITLE_CONTENT;
			}
			return entityType;
		}

		public static String getIngestFilenameCharactersStripped(String filename) {
			String basename = getFilenameWithoutPathAndExt(filename);
			String extension = getExtension(filename);
			String fixedBasename = basename.replaceAll(PERCENT, EMPTY_STRING);
			fixedBasename = fixedBasename.replace(SEPARATOR_PERIOD, EMPTY_STRING);
			return fixedBasename + SEPARATOR_PERIOD + extension;
		}
	}
		
	public static class FileTranscodeHelper {
		public static String addHybridBaseURLAndPathForTranscode(String endPoiint) {
			return addHybridBaseURLAndPath(endPoiint, TRANSCODED_TYPE);
		}
		public static String getTranscodeSourceUrl(String filename) {
			return addHybridBaseURLAndPathForTranscode(filename);
		}
		
		public static String getTranscodeSourceFilename(Subcontent sourceSubcontent) {
			String fullPath = EMPTY_STRING;
			String fileName = getFilenameWithoutPath(sourceSubcontent.getSourcePath());
			try {
				String storageType = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.STORAGE_TYPE_KEY);
				if(storageType.equals(S3_KEY)){
					String bucket = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.S3_BUCKET_SOURCE_KEY);
					fullPath = S3_PROTOCOL_PREFIX+bucket+SLASH+fileName;
				} else if(storageType.equals(VM_KEY)){
					String baseURL = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.VM_BASE_URL_KEY);
					String filePath = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.VM_MEZZANINE_FOLDER_KEY);
					fullPath = baseURL+BACKSLASH+filePath+BACKSLASH+fileName;
				}
			} catch (PropertyException e) {
			}
			return fullPath;
		}
		
		public static String getTranscodeTargetPath() {
			String fullPath = EMPTY_STRING;
			try {
				String storageType = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.STORAGE_TYPE_KEY);
				if(storageType.equals(S3_KEY)){
					String bucket = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.S3_BUCKET_TRANSCODED_KEY);
					fullPath = S3_PROTOCOL_PREFIX+bucket+SLASH;
				} else if(storageType.equals(VM_KEY)){
					String baseURL = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.VM_BASE_URL_KEY);
					String filePath = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.VM_TRANSCODED_FOLDER_KEY);
					fullPath = baseURL+BACKSLASH+filePath+BACKSLASH;
				}
			} catch (PropertyException e) {
			}
			return fullPath;
		}
		
		public static String convertPrefixOfTranscodedSourcePath(String filename){
			String fullPath = EMPTY_STRING;
			String fileName = getFilenameWithoutPath(filename);
			try {
				String storageType = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.STORAGE_TYPE_KEY);
				if(storageType.equals(S3_KEY)){
					String s3Root = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.S3_SOURCE_URL_KEY);
					String bucket = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.S3_BUCKET_TRANSCODED_KEY);
					fullPath = s3Root+SLASH+bucket+SLASH+fileName;
				} else if (storageType.equals(VM_KEY)) {
					String baseURL = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.VM_BASE_URL_KEY);
					String filePath = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.VM_TRANSCODED_FOLDER_KEY);
					fullPath = baseURL+BACKSLASH+filePath+BACKSLASH+fileName;
				}
			} catch (PropertyException e) {
			}
			return fullPath;
		}
	}
	
	public static class FileProtectHelper {
		public static String getIsmFilename(String filename) {
			return FileHelper.getFileBaseName(filename) + SEPARATOR_PERIOD + EXTENSION_SMOOTHSTREAM_ISM;
		}
		public static String getIsmcFilename(String filename) {
			return FileHelper.getFileBaseName(filename) + SEPARATOR_PERIOD + EXTENSION_SMOOTHSTREAM_ISMC;
		}

		/*public static String getHlsManifestFilename(String filename) {
			return getFileBaseName(filename) + SEPARATOR_PERIOD + EXTENSION_M3U8;
		}*/
		public static String getCpsPathTarget(String filename, String protectType,Integer policyGroupId) {
			String cpsPathTarget = EMPTY_STRING;
			try {
				String cpsPathRoot = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CPS_PATH_ROOT_KEY);
				cpsPathTarget = internalGetCpsPathTarget(filename, cpsPathRoot, protectType,policyGroupId);
			} catch (PropertyException e) {
			}
			return cpsPathTarget;
		}
		protected static String internalGetCpsPathTarget(String filename, String cpsPathRoot, String protectType,Integer policyGroupId) {
			return cpsPathRoot + getFileBaseName(filename)+ SEPARATOR_FILENAME_PART + String.valueOf(policyGroupId) + SEPARATOR_FILENAME_PART + protectType + SLASH;
		}
		

		public static String getHlsManifestContent(List<String> filenameListHLS) {
			String indexFileContent = EMPTY_STRING;

			for (Object filenameObject: filenameListHLS) {
				String filename = (String) filenameObject;
				indexFileContent += FileHelper.getFilenameWithoutPath(filename) + CRLF;
			}

			return indexFileContent;
		}
		public static String getCpsSourceFolder(String filename, String protectType,Integer policyGroupId) {
			String cpsSourceFolder = EMPTY_STRING;
			try {
				String cpsPathRoot = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CPS_PATH_ROOT_KEY);
				cpsSourceFolder = internalGetCpsSourceFolder(cpsPathRoot, filename, protectType,policyGroupId);
			} catch (PropertyException e) {
			}
			return cpsSourceFolder;
		}
	
		public static String getCpsTargetFolder(String cpsSourcePath, String masterSourceFilename){
			String filePath = FileHelper.getFilePathWithoutFilename(cpsSourcePath);
			return filePath+BACKSLASH+getLastFoldername(cpsSourcePath)+BACKSLASH;
		}

		protected static String internalGetCpsSourceFolder(String cpsPathRoot, String filename, String protectType,Integer policyGroupId) {
			return cpsPathRoot + getFileBaseName(filename)+ SEPARATOR_FILENAME_PART +String.valueOf(policyGroupId)+SEPARATOR_FILENAME_PART+ protectType + BACKSLASH;
		}
		protected static String internalGetD2gRenamedFilename(String sourceFileD2G) {
			return sourceFileD2G.replace(SEPARATOR_PERIOD + EXTENSION_MP4, "-" + AC_D2G_KEY + SEPARATOR_PERIOD + EXTENSION_MP4);
		}
		
		public static String getD2gRenamedFile(String filename) {
			String targetFilename = EMPTY_STRING;
			targetFilename = internalGetD2gRenamedFilename(filename);
			return targetFilename;
		}
		
		public static String getD2gFileURL(String masterSourceFilename, String d2gFilename, String protectProfile,Integer policyGroupId) {
			String cpsSourceFile = EMPTY_STRING;
			try {
				String cpsPathRoot = BeanUtil.propertiesManager.getProperty(JumpstartPropertyKey.CPS_PATH_ROOT_KEY);
				cpsSourceFile = internalGetCpsSourceFolder(cpsPathRoot, masterSourceFilename, protectProfile,policyGroupId) + getFilenameWithoutPath(d2gFilename);
			} catch (PropertyException e) {
			}
			return cpsSourceFile;
		}
	}

	public static class FilePublishHelper {
		public static String getConsumerUrl(Integer publishVersion, SourceVideoSub sourceVideoSubcontent, ProtectVideoSub protectedVideoSubcontent, Protect protect) {
			String baseUrl = ProtectHelper.getVideoCdnPrefix(protect);
			String cdnUrlSuffix = ProtectHelper.getCdnUrlSuffix(protect);
			String protectPath = FileHelper.getLastFoldername(protectedVideoSubcontent.getSourcePath());
			return getConsumerUrl(publishVersion, sourceVideoSubcontent, protectedVideoSubcontent, baseUrl, cdnUrlSuffix, protectPath);
		}

		public static String getConsumerUrl(Integer publishVersion, ImageSubcontent imageSubcontent) {
			String baseUrl = (String) DeviceProfileHelper.getImageCdnPrefix();
			return getConsumerUrl(publishVersion, imageSubcontent, imageSubcontent, baseUrl, null, null);
		}

		public static String getConsumerUrl(Integer publishVersion, SubtitleSubcontent subtitleSubcontent) {
			String baseUrl = (String) DeviceProfileHelper.getImageCdnPrefix();
			return getConsumerUrl(publishVersion, subtitleSubcontent, subtitleSubcontent, baseUrl, null, null);
		}
		
		private static String getConsumerUrl(Integer publishVersion, Subcontent sourceVideoSubcontent, Subcontent protectedVideoSubcontent, String baseUrl, String cdnUrlSuffix, String protectPath) {
			String publishPath = FileHelper.getFilenameRoot(sourceVideoSubcontent.getSourcePath(), 1) + "-v" + String.valueOf(publishVersion);
			String publishFilename = FileHelper.getFilenameWithoutPath(protectedVideoSubcontent.getSourcePath());
			
			StringBuffer consumerUrlBuffer = new StringBuffer();
			consumerUrlBuffer.append(baseUrl).append(SLASH);
			consumerUrlBuffer.append(publishPath).append(SLASH);
			if (protectPath != null) {
				consumerUrlBuffer.append(protectPath).append(SLASH);
			}
			consumerUrlBuffer.append(publishFilename);
			if (cdnUrlSuffix != null) {
				consumerUrlBuffer.append(cdnUrlSuffix);
			}
			return consumerUrlBuffer.toString();
		}

		public static String getPublishImageConsumerUrl(String sourcePath, Integer publishVersion) {
			StringBuffer consumerUrlBuffer = new StringBuffer();
			String baseURL = (String) DeviceProfileHelper.getImageCdnPrefix();
			String publishFilename = getFilenameWithoutPath(sourcePath);
			consumerUrlBuffer.append(baseURL).append(SLASH);
			consumerUrlBuffer.append(internalGetCdnFolder(sourcePath, publishVersion)).append(SLASH);
			consumerUrlBuffer.append(publishFilename);
			return consumerUrlBuffer.toString();
		}

		public static String getPublishImageTargetFolder(String sourcePath, Integer publishVersion) {
			return internalGetPublishImageTargetFolder(sourcePath, publishVersion);
		}

		protected static String internalGetPublishImageTargetFolder(String sourcePath, Integer publishVersion) {
			StringBuffer consumerUrlBuffer = new StringBuffer();
			String baseURL = addHybridBaseURLAndPath(PATH_HLS, WorkflowHelper.CDN_TYPE);
			consumerUrlBuffer.append(baseURL).append(SLASH);
			consumerUrlBuffer.append(internalGetCdnFolder(sourcePath, publishVersion)).append(SLASH);
			return consumerUrlBuffer.toString();
		}
		
		public static String internalGetCdnFolder(String sourcePath, Integer publishVersion) {
			return FileHelper.getFilenameRoot(sourcePath, 1) + "-v" + publishVersion;
		}
	}
}
