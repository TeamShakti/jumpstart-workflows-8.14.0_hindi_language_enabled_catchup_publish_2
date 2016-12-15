package com.irdeto.jumpstart.domain.qa;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.workflow.LocaleHelper;

public class GenreMetadataQA extends AbstractBaseMetadataQADecorator<Genre> {
	public GenreMetadataQA() {
		super();
	}
	
	public GenreMetadataQA(String entityId, String entityType) {
		super();
		setEntityId(entityId);
		setEntityType(entityType);
	}
	
	@Override
	@JsonIgnore
	public String getQAView() {
		return "Metadata QA Genre View";
	}
	
	@Override
	@JsonIgnore
	public String getCorrectionView() {
		return "Metadata Genre Edit View";
	}
	
	@Override
	@JsonIgnore
	public String getEntityTitle() {
        String title= LocaleHelper.getStringValueForDefaultLanguage(getEntity().getTitle());
        return StringUtils.isEmpty(title)?getEntity().getId():title;
	}
	
	@Override
	@JsonIgnore
	public String getQAOperation() {
		return "Metadata QA on " + getEntityTitle();
	}

	@Override
	@JsonIgnore
	public String getCorrectOperation() {
		return "Rejected Metadata QA on " + getEntityTitle();
	}

	@Override
	@JsonIgnore
	public String getAutomatedPreValidationMessages() {
		return applyMetadataRules();
	}

	@Override
	@JsonIgnore
	public String getAutomatedPostValidationMessages() {
		return applyMetadataRules();
	}

	@Override
	protected String applyMetadataRules() {
		StringBuffer messages = new StringBuffer();
		messages.append(testBlankOrWhitespace(getEntity().getId(), "Title", getEntity().getTitle()));
		messages.append(super.applyMetadataRules());
		return messages.toString();
	}
	
	@Override
	@JsonIgnore
	public ApprovalType getApprovalType() {
		return ApprovalType.METADATA;
	}

}
