package com.irdeto.jumpstart.domain.ingest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class ScheduleSlotIngestWrapper extends AbstractEntityIngestWrapper<ScheduleSlot> {
	private String channelId;

	private String ingestGenre;

	private boolean catchUpChanged = false;
	private boolean blackOutChanged = false;

	@JsonIgnore
	@Override
	public void mergeEntity() {
		throw new NotImplementedException("Merge is currently done via EpgIngestHelper");
	}

	@JsonIgnore
	@Override
	public QueryFilterParameter getMatchingQueryFilter() {
		throw new NotImplementedException("Not to be read from MediaManager individually but only as part of ChannelDay entity");
	}

	public void addProgramUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.PROGRAM_RELATIONSHIP_NAME,
				Program.class,
				uriIdList
		);
	}

	public void addEventUriIdList(List<String> uriIdList) {
		addRelationshipsList(
				WorkflowHelper.EVENT_RELATIONSHIP_NAME,
				Event.class,
				uriIdList
		);
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelId() {
		return this.channelId;
	}

	public String getIngestGenre() {
		return ingestGenre;
	}

	public void setIngestGenre(String ingestGenre) {
		this.ingestGenre = ingestGenre;
	}

	public boolean isCatchUpChanged() {
		return catchUpChanged;
	}

	public void setCatchUpChanged(boolean catchupChanged) {
		this.catchUpChanged = catchupChanged;
	}

	public boolean isBlackOutChanged() {
		return blackOutChanged;
	}

	public void setBlackOutChanged(boolean blackOutChanged) {
		this.blackOutChanged = blackOutChanged;
	}

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(ScheduleSlot entity) {
		return null;
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(ScheduleSlot entity, List<ImageContent> imageContentList) {
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return new ArrayList<>();
	}

	protected void addRelationshipsList(String relName, Class rel, List<String> relList) {
		super.addRelationshipsList(
				ScheduleSlot.class,
				relName,
				rel,
				WorkflowHelper.ATTRIBUTE_NAME_URI_ID,
				relList
		);
	}
}
