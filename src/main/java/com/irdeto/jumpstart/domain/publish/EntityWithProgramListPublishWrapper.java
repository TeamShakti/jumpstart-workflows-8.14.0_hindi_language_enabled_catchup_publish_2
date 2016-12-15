package com.irdeto.jumpstart.domain.publish;

import java.util.List;

import com.irdeto.jumpstart.domain.Program;

public interface EntityWithProgramListPublishWrapper extends EntityWithTermWrapperListPublishWrapper{
	public List<Program> getProgramList();
}
