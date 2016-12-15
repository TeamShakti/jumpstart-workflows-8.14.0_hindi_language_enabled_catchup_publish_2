package com.irdeto.jumpstart.domain.publish;

import java.util.List;

import com.irdeto.jumpstart.domain.Event;

public interface EntityWithEventListPublishWrapper extends EntityWithTermWrapperListPublishWrapper{
	public List<Event> getEventList();
}
