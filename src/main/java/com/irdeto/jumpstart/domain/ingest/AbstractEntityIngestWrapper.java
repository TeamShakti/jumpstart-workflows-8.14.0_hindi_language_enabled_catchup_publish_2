package com.irdeto.jumpstart.domain.ingest;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.domain.mediamanager.command.MaintainRelationshipsCommand;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.BaseEntity;
import com.irdeto.jumpstart.domain.BaseMetadata;
import com.irdeto.jumpstart.domain.BaseMetadata.DataMaster;
import com.irdeto.jumpstart.domain.BaseMetadataWithContent;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.ingest.DataIngestHelper;
import com.irdeto.jumpstart.workflow.qa.ComparisonHelper;

public abstract class AbstractEntityIngestWrapper<T extends BaseEntity> implements EntityIngestWrapper<T> {

	private T entity;
	private T storedEntity;
	private T approvedEntity;
	private SourceInformation sourceInformation;

	private List<MaintainRelationshipsCommand> maintainRelationshipsList = new ArrayList<>();

	@JsonIgnore
	public String getEntityType() {
		return WorkflowHelper.getEntityType(getEntity());
	}

	@Override
	public T getEntity() {
		return entity;
	}

	@Override
	public void setEntity(T entity) {
		this.entity = entity;
		for (MaintainRelationshipsCommand maintainRelationship : getMaintainRelationshipsList()) {
			maintainRelationship.setEntityId(entity.getId());
		}
	}

	@JsonIgnore
	public String getUriId() {
		if (getEntity() instanceof Base) {
			return ((Base) getEntity()).getUriId();
		} else
			return null;
	}

	public List<MaintainRelationshipsCommand> getMaintainRelationshipsList() {
		return maintainRelationshipsList;
	}

	public void setMaintainRelationshipsList(
			List<MaintainRelationshipsCommand> maintainRelationshipsList) {
		this.maintainRelationshipsList = maintainRelationshipsList;
	}

	public boolean needUpdate() {
		return getEntity().getId() != null;
	}

	@SuppressWarnings("unchecked")
	@JsonIgnore
	public Class<T> getEntityClass() {
		return (Class<T>) getEntity().getClass();
	}

	@JsonIgnore
	@Override
	public QueryFilterParameter getMatchingQueryFilter() {
		return WorkflowHelper.getQueryFilterParameter(getUriId());

	}

	@JsonIgnore
	@Override
	public void mergeEntity() {
		if (getStoredEntity() != null) {
			// update
			if (getEntity() instanceof BaseMetadata) {
				DataMaster master = ((BaseMetadata) getStoredEntity()).getDataMaster();
				if (DataMaster.MEDIA_MANAGER.equals(master)) {
					setEntity(getStoredEntity());
					return;
				}
				((BaseMetadata) getEntity()).setDataMaster(DataMaster.INGEST);
			}
			T mergedEntity = getStoredEntity();
			DataIngestHelper.copyAllExceptIdAndList(getEntity(), mergedEntity);
			setImageContentList(mergedEntity, DataIngestHelper.mergeContentList(getImageContentList(getEntity()),
					(getImageContentList(mergedEntity))));
			setEntity(mergedEntity);

			if (getEntity() instanceof BaseMetadata) {
				if (!alwaysMetadataQA() || ComparisonHelper.areSameObjects(getStoredEntity(), getApprovedEntity(), getCompareSubcontent(), getCompareLinks())) {
					((BaseMetadata) getEntity()).setMetadataApproved(true);
				} else {
					((BaseMetadata) getEntity()).setMetadataApproved(false);

					//DataIngest - setting LastModifiedDateTime to 'now'.
					((BaseMetadata) getEntity()).setLastModifiedDateTime(DateTime.now());
				}
			}
		} else {
			// Insert
			if (getEntity() instanceof BaseMetadata) {
				((BaseMetadata) getEntity()).setMetadataApproved(!alwaysMetadataQA());
				((BaseMetadata) getEntity()).setDataMaster(DataMaster.INGEST);
			}
			if (getEntity() instanceof BaseMetadataWithContent) {
				((BaseMetadataWithContent) getEntity()).setContentApproved(!alwaysContentQA());
			}
		}
	}

	@JsonIgnore
	protected boolean getCompareSubcontent() {
		return false;
	}

	@JsonIgnore
	protected abstract List<String> getCompareLinks();

	@JsonIgnore
	protected abstract List<ImageContent> getImageContentList(T entity);

	@JsonIgnore
	protected abstract void setImageContentList(T entity, List<ImageContent> imageContentList);

	protected boolean alwaysMetadataQA() {
		return true;
	}

	protected boolean alwaysContentQA() {
		return true;
	}

	public SourceInformation getSourceInformation() {
		return sourceInformation;
	}

	public void setSourceInformation(SourceInformation sourceInformation) {
		this.sourceInformation = sourceInformation;
	}

	@Override
	public T getStoredEntity() {
		return storedEntity;
	}

	@Override
	public void setStoredEntity(T storedEntity) {
		this.storedEntity = storedEntity;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setStoredBaseEntity(BaseEntity storedEntity) {
		if (getEntityClass().equals(storedEntity.getClass())) {
			this.storedEntity = (T) storedEntity;
		}
	}

	@Override
	public T getApprovedEntity() {
		return approvedEntity;
	}

	@Override
	public void setApprovedEntity(T approvedEntity) {
		this.approvedEntity = approvedEntity;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setApprovedBaseEntity(BaseEntity approvedEntity) {
		if (getEntityClass().equals(approvedEntity.getClass())) {
			this.approvedEntity = (T) approvedEntity;
		}
	}

	protected void addRelationshipsList(Class source, String relName, Class rel, String attrName, List<String> relList) {
		getMaintainRelationshipsList().add(
				new MaintainRelationshipsCommand(
						WorkflowHelper.getEntityType(source),
						null,
						relName,
						WorkflowHelper.getEntityType(rel),
						attrName,
						relList
				)
		);
	}
}
