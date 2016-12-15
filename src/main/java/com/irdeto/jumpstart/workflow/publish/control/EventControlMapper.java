package com.irdeto.jumpstart.workflow.publish.control;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.ContentType;
import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.workflow.publish.PublishHelper;

public class EventControlMapper extends ContentControlMapper<Event>{

	@Override
	@JsonIgnore
	public Class<Event> getEntityClass() {
		return Event.class;
	}

	@Override
	@JsonIgnore
	public List<String> getMediaIdList() {
		// Find videos to link
		List<String> mediaIdList = new ArrayList<>();
		if (!getPublishWrapper().getTermMapList().isEmpty()
				&& !getPublishWrapper().getTermWrapperList().isEmpty()) {
			mediaIdList.add(PublishHelper.getControlAssetId(getPublishWrapper().getApprovedEntity()));
		}
		return mediaIdList;
	}

	@Override
	@JsonIgnore
	protected boolean isMainContentType(TermMap termMap) {
		return termMap.getContentTypeList().contains(ContentType.EVENT.toString());
	}
}
