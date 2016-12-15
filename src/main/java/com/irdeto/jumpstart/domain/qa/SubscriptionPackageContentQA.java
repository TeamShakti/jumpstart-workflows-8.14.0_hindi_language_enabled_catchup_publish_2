package com.irdeto.jumpstart.domain.qa;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.workflow.LocaleHelper;

public class SubscriptionPackageContentQA extends AbstractContentQADecorator<SubscriptionPackage> {
	public SubscriptionPackageContentQA() {
		super();
	}
	
	public SubscriptionPackageContentQA(String entityId, String entityType) {
		super();
		setEntityId(entityId);
		setEntityType(entityType);
	}
	
	@Override
	@JsonIgnore
	public String getQAView() {
		return "Content QA Subscription Package View";
	}

	@Override
	@JsonIgnore
	public String getCorrectionView() {
		return "General Subscription Package Task View";
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
		SubscriptionPackage subscriptionPackage = getEntity();
		StringBuffer messages = new StringBuffer();
		if (subscriptionPackage.getImageContent() == null || subscriptionPackage.getImageContent().isEmpty()) {
			messages.append("Subscription package must reference at least one image: ").append(getEntity().getUriId()).append("\n");
		} else {
			messages.append(getImageMetadataValidationMessages(subscriptionPackage, subscriptionPackage.getImageContent()));
		}
		return messages.toString();
	}
	
	@Override
	@JsonIgnore
	public ApprovalType getApprovalType() {
		return ApprovalType.CONTENT;
	}

}
