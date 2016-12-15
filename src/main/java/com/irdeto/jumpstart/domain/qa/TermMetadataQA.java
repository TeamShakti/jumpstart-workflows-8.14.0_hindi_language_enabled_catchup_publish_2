package com.irdeto.jumpstart.domain.qa;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Term;

public class TermMetadataQA extends AbstractBaseMetadataQADecorator<Term> {
	public TermMetadataQA() {
		super();
	}
	
	public TermMetadataQA(String entityId, String entityType) {
		super();
		setEntityId(entityId);
		setEntityType(entityType);
	}
	
	@Override
	@JsonIgnore
	public String getQAView() {
		return "Metadata QA Term View";
	}

	@Override
	@JsonIgnore
	public String getCorrectionView() {
		return "Metadata Term Edit View";
	}

	@Override
	@JsonIgnore
	public String getEntityTitle() {
		if (getEntity().getContractName()!=null&&!getEntity().getContractName().isEmpty()){
			return getEntity().getContractName();
		} else {
			return getEntity().getUriId();
		}
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
		messages.append(testBlankOrWhitespace(getEntity().getUriId(), "Contract name", getEntity().getContractName()));
		messages.append(super.applyMetadataRules());
		return messages.toString();
	}
	
	@Override
	@JsonIgnore
	public ApprovalType getApprovalType() {
		return ApprovalType.METADATA;
	}

}
