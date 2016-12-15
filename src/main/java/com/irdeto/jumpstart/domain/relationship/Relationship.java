package com.irdeto.jumpstart.domain.relationship;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class Relationship<T extends BaseEntity> {
	private String relationshipName;
	private Class<T> relatedEntityClass;
	private Relationship<?> relationship;
	
	public Relationship() {
		super();
	}
	
	public Relationship(String relationshipName, Class<T> relatedEntityClass) {
		super();
		this.relationshipName = relationshipName;
		this.relatedEntityClass = relatedEntityClass;
	}

	public Relationship(String relationshipName, Class<T> relatedEntityClass, Relationship<?> relationship) {
		this(relationshipName, relatedEntityClass);
		this.relationship = relationship;
	}

	public String getRelationshipName() {
		return relationshipName;
	}
	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}
	@JsonIgnore
	public String getRelationshipEntityType() {
		return WorkflowHelper.getEntityType(getRelatedEntityClass());
	}
	public Class<T> getRelatedEntityClass() {
		return relatedEntityClass;
	}
	public void setRelatedEntityClass(Class<T> relatedEntityClass) {
		this.relatedEntityClass = relatedEntityClass;
	}

	public Relationship<?> getRelationship() {
		return relationship;
	}

	public void setRelationship(Relationship<?> relationship) {
		this.relationship = relationship;
	}
	
	@Override
	public String toString() {
		if (getRelationship() == null) {
			return getRelationshipName();
		} else {
			return getRelationshipName() + "," + getRelationship().toString();
		}
	}
}