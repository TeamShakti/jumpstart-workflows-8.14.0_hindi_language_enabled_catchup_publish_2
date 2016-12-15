package com.irdeto.jumpstart.domain.qa;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.workflow.LocaleHelper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class ChannelMetadataQA extends AbstractBaseMetadataQADecorator<Channel> {
	public ChannelMetadataQA() {
		super();
	}
	
	public ChannelMetadataQA(String entityId, String entityType) {
		super();
		setEntityId(entityId);
		setEntityType(entityType);
	}
	
	@Override
	@JsonIgnore
	public String getQAView() {
		return "Metadata QA Channel View";
	}
	
	@Override
	@JsonIgnore
	public String getCorrectionView() {
		return "Metadata Channel Edit View";
	}
	
	@Override
	@JsonIgnore
	public String getEntityTitle() {
		return LocaleHelper.getStringValueForDefaultLanguage(getEntity().getTitleLong());
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
		messages.append(testBlankOrWhitespace(getEntity().getId(), "URI ID", getEntity().getUriId()));
		messages.append(testBlankOrWhitespace(getEntity().getUriId(), "Long title", getEntity().getTitleLong()));
		Integer encodeProfileLinkCount = WorkflowHelper.getLinkCount(getEntity(), WorkflowHelper.ENCODE_PROFILE_RELATIONSHIP_NAME);
		if (encodeProfileLinkCount == null || encodeProfileLinkCount == 0) {
			messages.append("Entity must have at least one encode profile: ").append(getEntity().getUriId()).append("\n");
		}
		
		if (getEntity().getImageContent() == null || getEntity().getImageContent().isEmpty()) {
			messages.append("Channel must reference at least one image: ").append(getEntity().getUriId()).append("\n");
		}
		messages.append(super.applyMetadataRules());
		return messages.toString();
	}
	
	@Override
	@JsonIgnore
	public ApprovalType getApprovalType() {
		return ApprovalType.METADATA;
	}
}
