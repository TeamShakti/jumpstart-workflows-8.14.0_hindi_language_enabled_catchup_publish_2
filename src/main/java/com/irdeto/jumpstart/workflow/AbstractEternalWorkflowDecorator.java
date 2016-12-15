package com.irdeto.jumpstart.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.relationship.Relationship;

public abstract class AbstractEternalWorkflowDecorator<T extends Base> implements EternalWorkflowDecorator<T> {
	private static final String ETERNAL_WORKFLOW_PROCESS_NAME = "EternalWorkflow";
	protected static final String QA_ENTITY_KEY = "qaEntity";
	protected static final String APPROVED_ENTITY_KEY = "approvedEntity";

	protected static final String ENTITY_ID_KEY = "entityId";
	protected static final String WORKFLOW_PACKAGE_NAME = "com.irdeto.jumpstart.workflow";

	private String entityId;
	private String uriId;

	@Override
	@JsonIgnore
	public String getProcessId() {
		return WORKFLOW_PACKAGE_NAME + '.' + ETERNAL_WORKFLOW_PROCESS_NAME;
	}
	@Override
	@JsonIgnore
	public String getEntityType() {
		return StringUtils.uncapitalize(getEntityClass().getSimpleName());
	}

	@Override
	@JsonIgnore
	public abstract QADecorator getContentQADecorator();

	@Override
	@JsonIgnore
	public abstract QADecorator getMetadataQADecorator();

	@Override
	@JsonIgnore
	public abstract List<Relationship<?>> getRelationshipList();
	
	@Override
	@JsonIgnore
	public Map<String, Object> getQADataMap(QADecorator qaDecorator) {
		Map<String, Object> qaDataMap = new HashMap<String, Object>();
		qaDataMap.put(QA_ENTITY_KEY, qaDecorator);
		return qaDataMap;
	}
	
	@Override
	public String getEntityId() {
		return entityId;
	}

	@Override
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	@Override
	public String getUriId() {
		return uriId;
	}
	
	@Override
	public void setUriId(String uriId) {
		this.uriId = uriId;
	}
	
	@Override
	public String toString() {
		return "Eternal workflow decorator for id: " + getEntityId() + " URI ID: " + getUriId();
	}
}
