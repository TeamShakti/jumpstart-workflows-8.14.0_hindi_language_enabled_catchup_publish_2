package com.irdeto.jumpstart.domain.qa;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.workflow.LocaleHelper;

public class SeriesContentQA extends AbstractContentQADecorator<Series> {
	public SeriesContentQA() {
		super();
	}
	
	public SeriesContentQA(String entityId, String entityType) {
		super();
		setEntityId(entityId);
		setEntityType(entityType);
	}
	
	@Override
	@JsonIgnore
	public String getQAView() {
		return "Content QA Series View";
	}

	@Override
	@JsonIgnore
	public String getCorrectionView() {
		return "General Series Task View";
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
		Series series = getEntity();
		StringBuffer messages = new StringBuffer();
		if (series.getImageContent() == null || series.getImageContent().isEmpty()) {
			messages.append("Series must reference at least one image: ").append(getEntity().getUriId()).append("\n");
		} else {
			messages.append(getImageMetadataValidationMessages(series, series.getImageContent()));
		}
		return messages.toString();
	}
	
	@Override
	@JsonIgnore
	public ApprovalType getApprovalType() {
		return ApprovalType.CONTENT;
	}

}
