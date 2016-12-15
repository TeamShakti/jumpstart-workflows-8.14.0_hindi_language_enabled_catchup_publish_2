package com.irdeto.jumpstart.domain.qa;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.workflow.LocaleHelper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class ProgramMetadataQA extends AbstractBaseMetadataQADecorator<Program> {
	public ProgramMetadataQA() {
		super();
	}
	
	public ProgramMetadataQA(String entityId, String entityType) {
		super();
		setEntityId(entityId);
		setEntityType(entityType);
	}
	
	@Override
	@JsonIgnore
	public String getQAView() {
		return "Metadata QA Program View";
	}
	
	@Override
	@JsonIgnore
	public String getCorrectionView() {
		return "Metadata Program Edit View";
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
		messages.append(testBlankOrWhitespace(getEntity().getUriId(), "Medium title", getEntity().getTitleMedium()));
		messages.append(testBlankOrWhitespace(getEntity().getUriId(), "Long summary", getEntity().getSummaryLong()));
		if (getEntity().getCountryOfOrigin()!=null && getEntity().getCountryOfOrigin().size()>1){
			messages.append("At most one country of origin can be set for entity: ").append(getEntity().getUriId()).append("\n");
		}
//		Integer genreLinkCount = WorkflowHelper.getLinkCount(getEntity(), WorkflowHelper.GENRE_RELATIONSHIP_NAME);
//		if (genreLinkCount == null || genreLinkCount == 0) {
//			messages.append("Entity must have at least one genre: ").append(getEntity().getUriId()).append("\n");
//		}
		messages.append(super.applyMetadataRules());
		return messages.toString();
	}
	
	@Override
	@JsonIgnore
	public ApprovalType getApprovalType() {
		return ApprovalType.METADATA;
	}

}
