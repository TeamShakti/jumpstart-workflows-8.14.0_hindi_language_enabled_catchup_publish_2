package com.irdeto.jumpstart.workflow;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.jumpstart.factory.AbstractInstance;

public interface EternalWorkflowDecorator<T extends Base> extends AbstractInstance<T> {
	@JsonIgnore
	public String getProcessId();

	@JsonIgnore
	public String getEntityType();

	@JsonIgnore
	public QADecorator getContentQADecorator();

	@JsonIgnore
	public QADecorator getMetadataQADecorator();

	@JsonIgnore
	public List<Relationship<?>> getRelationshipList();

	@JsonIgnore
	public Map<String, Object> getQADataMap(QADecorator qaDecorator);

	public String getEntityId();
	public void setEntityId(String entityId);

	public String getUriId();
	public void setUriId(String uriId);
}