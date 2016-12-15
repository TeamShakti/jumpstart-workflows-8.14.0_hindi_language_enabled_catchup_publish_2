package com.irdeto.jumpstart.domain.taskhandler;

import com.irdeto.domain.mediamanager.QueryFilterParameter;

public class QueryFilterWrapper {
	private String entityType;
	private QueryFilterParameter queryFilterParameter;
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public QueryFilterParameter getQueryFilterParameter() {
		return queryFilterParameter;
	}
	public void setQueryFilterParameter(
			QueryFilterParameter queryFilterParameter) {
		this.queryFilterParameter = queryFilterParameter;
	}
	
	@Override
	public String toString() {
		return "Query Filter Wrapper for entity " + getEntityType() + " with filter " + getQueryFilterParameter().toString();
	}
}
