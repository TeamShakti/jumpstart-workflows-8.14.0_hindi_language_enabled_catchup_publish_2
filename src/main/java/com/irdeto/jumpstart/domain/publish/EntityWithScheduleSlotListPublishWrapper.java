package com.irdeto.jumpstart.domain.publish;

import java.util.List;

import com.irdeto.jumpstart.domain.ScheduleSlot;

public interface EntityWithScheduleSlotListPublishWrapper extends EntityWithTermWrapperListPublishWrapper {
	public List<ScheduleSlot> getScheduleSlotList();
}
