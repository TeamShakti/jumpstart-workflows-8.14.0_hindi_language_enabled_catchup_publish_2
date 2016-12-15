package com.irdeto.jumpstart.domain.purge;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.ScheduleSlot;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;

public class ScheduleSlotPurgeWrapper extends AbstractPurgeWrapper<ScheduleSlot> {
	@Override
	@JsonIgnore
	public Class<ScheduleSlot> getEntityClass() {
		return ScheduleSlot.class;
	}

	@Override
	@JsonIgnore
	public List<String> getDestinationProcessIdList() {
		List<String> processIdList = new ArrayList<>();
		processIdList.add(JumpstartPropertyKey.PROCESS_ID_ACTIVEMQ_CATCH_UP_PURGE);
		return processIdList;
	}
}
