package com.irdeto.jumpstart.domain.qa;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.workflow.LocaleHelper;

public class SeriesMetadataQA extends AbstractBaseMetadataQADecorator<Series> {
	public SeriesMetadataQA() {
		super();
	}
	
	public SeriesMetadataQA(String entityId, String entityType) {
		super();
		setEntityId(entityId);
		setEntityType(entityType);
	}
	
	@Override
	@JsonIgnore
	public String getQAView() {
		return "Metadata QA Series View";
	}
	
	@Override
	@JsonIgnore
	public String getCorrectionView() {
		return "Metadata Series Edit View";
	}
	
	@Override
	@JsonIgnore
	public String getEntityTitle() {
        String titleMedium = LocaleHelper.getStringValueForDefaultLanguage(getEntity().getTitleMedium());
        return StringUtils.isEmpty(titleMedium)?getEntity().getUriId():titleMedium;
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
		messages.append(testBlankOrWhitespace(getEntity().getUriId(), "Long title", getEntity().getTitleLong()));
		messages.append(super.applyMetadataRules());
		return messages.toString();
	}
	
	@Override
	@JsonIgnore
	public ApprovalType getApprovalType() {
		return ApprovalType.METADATA;
	}

}
