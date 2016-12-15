package com.irdeto.jumpstart.domain.qa;


public interface QADecorator {
	String ENTITY_ID_KEY = "entityId";
	String QA_DATA_KEY = "qaData";
	String QA_ENTITY_KEY = "qaEntity";
	
	String getEntityType();
	String getQAView();
	String getCorrectionView();
	String getEntityTitle();
	String getQAOperation();
	String getCorrectOperation();
	String getAutomatedPreValidationMessages();
	String getAutomatedPostValidationMessages();
	void setEntity(Object entity);
	String getEntityId();
	void setEntityId(String id);
	boolean isQARequired();
	Boolean getApproved();
	void setApproved(Boolean approved);
}
