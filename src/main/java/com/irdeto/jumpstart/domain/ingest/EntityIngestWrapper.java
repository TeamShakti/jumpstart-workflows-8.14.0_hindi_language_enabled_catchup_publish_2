package com.irdeto.jumpstart.domain.ingest;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.domain.mediamanager.command.MaintainRelationshipsCommand;
import com.irdeto.jumpstart.domain.BaseEntity;

public interface EntityIngestWrapper<T extends BaseEntity> {

	public void mergeEntity();

	@JsonIgnore
	public String getEntityType();

	public T getEntity();

	public void setEntity(T entity);

	public T getStoredEntity();

	public void setStoredBaseEntity(BaseEntity entity);

	public void setStoredEntity(T entity);

	public T getApprovedEntity();

	public void setApprovedEntity(T entity);

	public void setApprovedBaseEntity(BaseEntity entity);

	@JsonIgnore
	public String getUriId();

	public List<MaintainRelationshipsCommand> getMaintainRelationshipsList();

	public void setMaintainRelationshipsList(
			List<MaintainRelationshipsCommand> maintainRelationshipsList);

	public boolean needUpdate();

	@JsonIgnore
	public Class<T> getEntityClass();

	@JsonIgnore
	public QueryFilterParameter getMatchingQueryFilter();
}
