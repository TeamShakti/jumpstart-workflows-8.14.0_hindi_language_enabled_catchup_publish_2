package com.irdeto.jumpstart.domain.qa;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseMetadata;
import com.irdeto.jumpstart.domain.BaseMetadataWithContent;
import com.irdeto.jumpstart.domain.Content;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.ImageSubcontent;
import com.irdeto.jumpstart.domain.Locale;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.SubtitleContent;
import com.irdeto.jumpstart.domain.SubtitleSubcontent;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.workflow.LocaleHelper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public abstract class AbstractQADecorator<T> implements QADecorator {
	private T entity;
	private String entityId;
	private String entityType;

	public T getEntity() {
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setEntity(Object entity) {
		this.entity = (T)entity;
	}

	@Override
	public String getEntityId() {
		return entityId;
	}

	@Override
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + getEntityId();
	}
	
	@Override
	public boolean equals(Object other) {
		return this.toString().equals(other.toString()); 
	}

	@JsonIgnore
	protected Boolean isListDifferent(List<?> list1, List<?> list2) {
		if (list1 == null && list2 == null) {
			return false;
		}
		if (list1 == null && list2 != null) {
			return true;
		}
		if (list1 != null && list2 == null) {
			return true;
		}
		if (list1.size() != list2.size()) {
			return true;
		}
		return null;
	}
	
	@JsonIgnore
	protected <T1 extends Content> Boolean isContentListDifferent(List<T1> list1, List<T1> list2) {
		Boolean isListDifferent = isListDifferent(list1, list2);
		if (isListDifferent != null) {
			return isListDifferent;
		}
		for (T1 item1: list1) {
			boolean foundMatch = false;
			for (T1 item2: list2) {
				if (item1.getId() != null && item1.getId().equals(item2.getId())) {
					foundMatch = true;
				} else {
					continue;
				}
				Boolean isVersionListDifferent = isListDifferent(item1.getVersions(), item2.getVersions());
				if (BooleanUtils.isTrue(isVersionListDifferent)) {
					return true;
				}
			}
			if (!foundMatch) {
				return true;
			}
		}
		return null;
	}
	
	@JsonIgnore
	@Override
	public void setApproved(Boolean approved) {
		switch (getApprovalType()) {
		case METADATA:
			if (getEntity() != null && getEntity() instanceof BaseMetadata) {
				((BaseMetadata)getEntity()).setMetadataApproved(approved);
			}
			break;
		case CONTENT:
			if (getEntity() != null && getEntity() instanceof BaseMetadataWithContent) {
				((BaseMetadataWithContent)getEntity()).setContentApproved(approved);
			}
			break;
		}
	}

	@JsonIgnore
	@Override
	public Boolean getApproved() {
		if (getEntity() == null) {
			return null;
		}
		if (ApprovalType.METADATA.equals(getApprovalType())
				&& getEntity() instanceof BaseMetadata) {
			return ((BaseMetadata)getEntity()).getMetadataApproved();
		} else if (ApprovalType.CONTENT.equals(getApprovalType())
				&& getEntity() instanceof BaseMetadataWithContent) {
			return ((BaseMetadataWithContent)getEntity()).getContentApproved();
		}
		return null;
	}
	
	@JsonIgnore
	public abstract ApprovalType getApprovalType();

	@JsonIgnore
	public String testBlankOrWhitespace(String entityId, String fieldName, Locale locale) {
		StringBuffer messages = new StringBuffer();
		for (String language: LocaleHelper.getLanguages()) {
			messages.append(testBlankOrWhitespace(entityId, fieldName, language, LocaleHelper.getStringValueForLanguage(locale, language)));
		}
		return messages.toString();
	}

	@JsonIgnore
	public String testBlankOrWhitespace(String entityId, String fieldName, String value) {
		return testBlankOrWhitespace(entityId, fieldName, null, value).toString();
	}

	@JsonIgnore
	private StringBuffer testBlankOrWhitespace(String entityId, String fieldName, String language, String value) {
		StringBuffer messages = new StringBuffer();
		if (StringUtils.isBlank(value)) {
			messages.append(fieldName);
			if (!StringUtils.isBlank(language)) {
				messages.append(" in language ").append(language);
			}
			messages.append(" is not set, for entity: ").append(entityId).append("\n");
		}
		if (StringUtils.startsWithAny(value, " ", "\t")) {
			messages.append(fieldName);
			if (!StringUtils.isBlank(language)) {
				messages.append(" in language ").append(language);
			}
			messages.append(" must not start with whitespace, for entity: ").append(entityId).append("\n");
		}
		if (StringUtils.endsWithAny(value, " ", "\t")) {
			messages.append(fieldName);
			if (!StringUtils.isBlank(language)) {
				messages.append(" in language ").append(language);
			}
			messages.append(" must not end with whitespace, for entity: ").append(entityId).append("\n");
		}
		return messages;
	}

	@Override
	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	
	@JsonIgnore
	protected String getImageMetadataValidationMessages(Base entity, List<ImageContent> imageContentList) {
		StringBuffer messages = new StringBuffer();
		if (imageContentList != null) {
			for (ImageContent imageContent: imageContentList) {
				Collection<ImageSubcontent> imgSubContentList = WorkflowHelper.getLatestImageSubcontentCollection(imageContent);
				for (ImageSubcontent imgSubContentElement: imgSubContentList) {
					Integer subConXResolution = imgSubContentElement.getXResolution();
					Integer subConYResolution = imgSubContentElement.getYResolution();
					if(StringUtils.isNumeric(imageContent.getXResolution())
							&& subConXResolution != null
							&& subConXResolution != 0
							&& !Integer.valueOf(imageContent.getXResolution()).equals(subConXResolution)){
						messages.append(entity.getType()).append(" ").append(entity.getUriId()).append(" has image content ").append(imageContent.getUriId()).append(" with a mismatching X resolution.").append("\n");
					}
					if(StringUtils.isNumeric(imageContent.getYResolution())
							&& subConYResolution != null
							&& subConYResolution != 0
							&& !Integer.valueOf(imageContent.getYResolution()).equals(subConYResolution)){
						messages.append(entity.getType()).append(" ").append(entity.getUriId()).append(" has image content ").append(imageContent.getUriId()).append(" with a mismatching Y resolution.").append("\n");
					}
					String subContentFileSize = imgSubContentElement.getContentFileSize();
					String subContentCheckSum = imgSubContentElement.getContentCheckSum();
					if(StringUtils.isNotBlank(imageContent.getFileSize())
							&& StringUtils.isNotBlank(subContentFileSize)
							&& !imageContent.getFileSize().equals(subContentFileSize)){
						messages.append(entity.getType()).append(" ").append(entity.getUriId()).append(" has image content ").append(imageContent.getUriId()).append(" with a mismatching file size.").append("\n");
					}
					if(StringUtils.isNotBlank(imageContent.getCheckSum())
							&& StringUtils.isNotBlank(subContentCheckSum)
							&& !imageContent.getCheckSum().equalsIgnoreCase(subContentCheckSum)){
						messages.append(entity.getType()).append(" ").append(entity.getUriId()).append(" has image content ").append(imageContent.getUriId()).append(" with a mismatching check sum.").append("\n");
					}
				}
			}
		}
		return messages.toString();
	}
	
	@JsonIgnore
	protected String getVideoMetadataValidationMessages(Base entity, List<VideoContent> videoContentList) {
		StringBuffer messages = new StringBuffer();
		if (videoContentList != null) {
			for (VideoContent videoContent: videoContentList) {
				SourceVideoSub sourceVideoSubcontent = WorkflowHelper.getLatestSourceVideoSubcontent(videoContent);
				List<SubtitleContent> subtitleContentList = videoContent.getSubtitleContent();
				if (sourceVideoSubcontent == null) {
					return "";
				}
				
				String subContentFileSize = sourceVideoSubcontent.getContentFileSize();
				String subContentCheckSum = sourceVideoSubcontent.getContentCheckSum();
				if(StringUtils.isNotBlank(videoContent.getFileSize())
						&& StringUtils.isNotBlank(subContentFileSize)
						&& !videoContent.getFileSize().equals(subContentFileSize)){
					messages.append(entity.getType()).append(" ").append(entity.getUriId()).append(" has video content ").append(videoContent.getUriId()).append(" with a mismatching file size.").append("\n");
				}
				if(StringUtils.isNotBlank(videoContent.getCheckSum())
						&& StringUtils.isNotBlank(subContentCheckSum)
						&& !videoContent.getCheckSum().equalsIgnoreCase((subContentCheckSum))){
					messages.append(entity.getType()).append(" ").append(entity.getUriId()).append(" has video content ").append(videoContent.getUriId()).append(" with a mismatching check sum.").append("\n");
				}
				
				// Validate Filesize and checksum for Subtitles of video contents
				if(subtitleContentList != null){
				for (SubtitleContent subtitleContent: subtitleContentList) {
					boolean matchFound = false;
					for (SubtitleSubcontent subtitleSubContent : subtitleContent.getSubcontent()){
					//SubtitleSubcontent subtitleSubContent = WorkflowHelper.getLatestSubtitleSubContent(subtitleContent); 
						if (subtitleSubContent == null) {
							return "";
						}
						String subtitleSubContentFileSize = subtitleSubContent.getContentFileSize();
						String subtitleSubContentCheckSum = subtitleSubContent.getContentCheckSum();
							if((StringUtils.isBlank(subtitleContent.getFileSize())
									|| StringUtils.isBlank(subtitleSubContentFileSize)
									|| subtitleContent.getFileSize().equals(subtitleSubContentFileSize))
									&& (StringUtils.isBlank(subtitleContent.getCheckSum())
									|| StringUtils.isBlank(subtitleSubContentCheckSum)
									|| subtitleContent.getCheckSum().equalsIgnoreCase((subtitleSubContentCheckSum)))){
								matchFound = true;
								break;
							}
						}
						if(!matchFound){
							messages.append(entity.getType()).append(" ").append(entity.getUriId()).append(" has subtitle content ").append(subtitleContent.getUriId()).append(" with a mismatching check sum or file size.").append("\n");
						}
					}
				}
			}
		}
		return messages.toString();
	}
	
	public enum ApprovalType {
		METADATA, CONTENT
	}
}
