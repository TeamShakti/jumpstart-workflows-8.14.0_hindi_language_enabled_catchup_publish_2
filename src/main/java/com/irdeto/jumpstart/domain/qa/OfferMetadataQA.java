package com.irdeto.jumpstart.domain.qa;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Offer;

public class OfferMetadataQA extends AbstractBaseMetadataQADecorator<Offer> {
	public OfferMetadataQA() {
		super();
	}
	
	public OfferMetadataQA(String entityId, String entityType) {
		super();
		setEntityId(entityId);
		setEntityType(entityType);
	}

	@Override
	@JsonIgnore
	public String getQAView() {
		return "Metadata QA Offer View";
	}

	@Override
	@JsonIgnore
	public String getCorrectionView() {
		return "Metadata Offer Edit View";
	}

	@Override
	@JsonIgnore
	public String getEntityTitle() {
		return getEntity().getBillingId();
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
		messages.append(testBlankOrWhitespace(getEntity().getUriId(), "Billing ID", getEntity().getBillingId()));
		messages.append(super.applyMetadataRules());
		return messages.toString();
	}
	
	@Override
	@JsonIgnore
	public ApprovalType getApprovalType() {
		return ApprovalType.METADATA;
	}

}
