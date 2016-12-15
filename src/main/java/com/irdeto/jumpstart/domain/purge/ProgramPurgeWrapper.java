package com.irdeto.jumpstart.domain.purge;

import java.util.ArrayList;
import java.util.List;

import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;

public class ProgramPurgeWrapper extends AbstractPurgeWrapper<Program> {
	@Override
	public Class<Program> getEntityClass() {
		return Program.class;
	}

	@Override
	public List<String> getDestinationProcessIdList() {
		List<String> processIdList = new ArrayList<>();
		processIdList.add(JumpstartPropertyKey.PROCESS_ID_ACTIVEMQ_PUBLISH);
		processIdList.add(JumpstartPropertyKey.PROCESS_ID_ELASTICSEARCH_PUBLISH);
		return processIdList;
	}
}
