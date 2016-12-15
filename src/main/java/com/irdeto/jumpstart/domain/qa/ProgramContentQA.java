package com.irdeto.jumpstart.domain.qa;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.jdom2.Element;

import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.workflow.LocaleHelper;

public class ProgramContentQA extends AbstractContentQADecorator<Program> {
	public ProgramContentQA() {
		super();
	}
	
	public ProgramContentQA(String entityId, String entityType) {
		super();
		setEntityId(entityId);
		setEntityType(entityType);
	}
	
	@Override
	@JsonIgnore
	public String getQAView() {
		return "Content QA Program View";
	}

	@Override
	@JsonIgnore
	public String getCorrectionView() {
		return "General Program Task View";
	}

	
	@Override
	@JsonIgnore
	public String getEntityTitle() {
        String titleBrief = LocaleHelper.getStringValueForDefaultLanguage(getEntity().getTitleBrief());
        return StringUtils.isEmpty(titleBrief)?getEntity().getUriId():titleBrief;
	}
	
	@Override
	@JsonIgnore
	public String getQAOperation() {
		return "Content QA on " + getEntityTitle();
	}

	@Override
	@JsonIgnore
	public String getCorrectOperation() {
		return "Rejected Content QA on " + getEntityTitle();
	}

	@Override
	@JsonIgnore
	public String getAutomatedPreValidationMessages() {
		StringBuffer messages = new StringBuffer();
		messages.append(checkContentReferences());
		return messages.toString();
	}

	@Override
	@JsonIgnore
	public String getAutomatedPostValidationMessages() {
		StringBuffer messages = new StringBuffer();
		messages.append(checkContentReferences());
		return messages.toString();
	}
	
	private String checkContentReferences() {
		Program program = getEntity();
		StringBuffer messages = new StringBuffer();
		boolean found = false;
		if (program.getVideoContent() != null && !program.getVideoContent().isEmpty()) {
			for (VideoContent videoContent: program.getVideoContent()) {
				if (VideoContent.ContentType.MOVIE.equals(videoContent.getContentType())) {
					found = true;
					break;
				}
			}
		}
		if (!found) {
			messages.append("Program must reference at least one movie: ").append(getEntity().getUriId()).append("\n");
		}
		
		boolean foundPoster = false;
		boolean foundBoxCover = false;
		if (program.getImageContent() != null && !program.getImageContent().isEmpty()) {
			for (ImageContent imageContent: program.getImageContent()) {
				if (ImageContent.ContentType.POSTER.equals(imageContent.getContentType())) {
					foundPoster = true;
				}
				if (ImageContent.ContentType.BOX_COVER.equals(imageContent.getContentType())) {
					foundBoxCover = true;
				}
				if (foundPoster && foundBoxCover) {
					break;
				}
			}
		}
		if (!foundBoxCover) {
			messages.append("Program must reference at least one box cover: ").append(getEntity().getUriId()).append("\n");
		}
		if (!foundPoster) {
			messages.append("Program must reference at least one poster: ").append(getEntity().getUriId()).append("\n");
		}
		
		messages.append(getImageMetadataValidationMessages(program, program.getImageContent()));
		messages.append(getVideoMetadataValidationMessages(program, program.getVideoContent()));
		return messages.toString();
	}
	
	@JsonIgnore
	 private List<String> getProfileElementListValues(List<Element> elementList) {
	  List<String> profileElementListValues = new ArrayList<>();
	  for (Element element: elementList) {
	   String value = element.getValue();
	   profileElementListValues.add(value);
	  } 
	  return profileElementListValues;
	 }
	
	
	@Override
	@JsonIgnore
	public ApprovalType getApprovalType() {
		return ApprovalType.CONTENT;
	}
}
