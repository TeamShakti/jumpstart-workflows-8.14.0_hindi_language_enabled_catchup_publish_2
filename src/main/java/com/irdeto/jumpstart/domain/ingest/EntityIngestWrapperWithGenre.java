package com.irdeto.jumpstart.domain.ingest;

import java.util.List;

import com.irdeto.domain.mediamanager.command.MaintainRelationshipsCommand;
import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public abstract class EntityIngestWrapperWithGenre<T extends Base> extends AbstractEntityIngestWrapper<T> {
	public void addGenreList(List<String> genreList) {
		getMaintainRelationshipsList().add(
				new MaintainRelationshipsCommand(
						WorkflowHelper.getEntityType(getEntityClass()),
						null,
						WorkflowHelper.GENRE_RELATIONSHIP_NAME,
						WorkflowHelper.getEntityType(Genre.class),
						WorkflowHelper.ATTRIBUTE_NAME_INGEST_GENRE,
						genreList,
						true
				)
		);
	}
}
