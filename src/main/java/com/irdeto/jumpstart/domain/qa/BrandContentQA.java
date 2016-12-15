package com.irdeto.jumpstart.domain.qa;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.workflow.LocaleHelper;

public class BrandContentQA extends AbstractContentQADecorator<Brand> {
	
	public BrandContentQA() {
		super();
	}
	
	public BrandContentQA(String entityId, String entityType) {
		super();
		setEntityId(entityId);
		setEntityType(entityType);
	}
	
	@Override
	@JsonIgnore
	public String getQAView() {
		return "Content QA Brand View";
	}

	@Override
	@JsonIgnore
	public String getCorrectionView() {
		return "General Brand Task View";
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
		Brand brand = getEntity();
		StringBuffer messages = new StringBuffer();
		if (brand.getImageContent() == null || brand.getImageContent().isEmpty()) {
			messages.append("Brand must reference at least one image: ").append(getEntity().getUriId()).append("\n");
		} else {
			messages.append(getImageMetadataValidationMessages(brand, brand.getImageContent()));
		}
		return messages.toString();
	}

	@Override
	@JsonIgnore
	public ApprovalType getApprovalType() {
		return ApprovalType.CONTENT;
	}
}
