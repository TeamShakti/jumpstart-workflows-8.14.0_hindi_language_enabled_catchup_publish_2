package com.irdeto.jumpstart.domain.ingest;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class MaintainEntities {
	private Class<? extends BaseEntity> entityClass;
	private List<? extends BaseEntity> entityList;
	private String logicalKeyField;

	public MaintainEntities() {
		super();
	}

	public MaintainEntities(
			Class<? extends BaseEntity> entityClass,
			String logicalKeyField,
			List<? extends BaseEntity> entityList
	) {
		super();
		this.entityClass = entityClass;
		this.logicalKeyField = logicalKeyField;
		this.entityList = entityList;
	}

	@JsonIgnore
	public String getEntityType() {
		return WorkflowHelper.getEntityType(getEntityClass());
	}

	public Class<? extends BaseEntity> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<? extends BaseEntity> entityClass) {
		this.entityClass = entityClass;
	}

	public List<? extends BaseEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<? extends BaseEntity> entityList) {
		this.entityList = entityList;
	}

	public String getLogicalKeyField() {
		return logicalKeyField;
	}

	public void setLogicalKeyField(String logicalKeyField) {
		this.logicalKeyField = logicalKeyField;
	}


}
