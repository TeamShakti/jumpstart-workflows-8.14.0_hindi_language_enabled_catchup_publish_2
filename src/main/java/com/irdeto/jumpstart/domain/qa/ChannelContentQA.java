package com.irdeto.jumpstart.domain.qa;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Channel;
import com.irdeto.jumpstart.workflow.LocaleHelper;

public class ChannelContentQA extends AbstractContentQADecorator<Channel> {
	
	public ChannelContentQA() {
		super();
	}

	public ChannelContentQA(String entityId, String entityType) {
		super();
		setEntityId(entityId);
		setEntityType(entityType);
	}
	
	@Override
	@JsonIgnore
	public String getQAView() {
		return "Content QA Channel View";
	}

	@Override
	@JsonIgnore
	public String getCorrectionView() {
		return "General Channel Task View";
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
		Channel channel = getEntity();
		StringBuffer messages = new StringBuffer();
		if (channel.getImageContent() == null || channel.getImageContent().isEmpty()) {
			messages.append("Channel must reference at least one image: ").append(getEntity().getUriId()).append("\n");
		} else {
			messages.append(getImageMetadataValidationMessages(channel, channel.getImageContent()));
		}
		return messages.toString();
	}

	@Override
	@JsonIgnore
	public ApprovalType getApprovalType() {
		return ApprovalType.CONTENT;
	}
}
