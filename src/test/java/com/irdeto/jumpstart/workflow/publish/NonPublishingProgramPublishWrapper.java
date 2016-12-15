package com.irdeto.jumpstart.workflow.publish;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.publish.ProgramPublishWrapper;

public class NonPublishingProgramPublishWrapper extends ProgramPublishWrapper {
	public NonPublishingProgramPublishWrapper() {
		super();
	}
	
	public NonPublishingProgramPublishWrapper(Program program) {
		super(program);
	}
	
	@Override
	@JsonIgnore
	public List<String> getDestinationProcessIdList() {
		return new ArrayList<>();
	}
}
