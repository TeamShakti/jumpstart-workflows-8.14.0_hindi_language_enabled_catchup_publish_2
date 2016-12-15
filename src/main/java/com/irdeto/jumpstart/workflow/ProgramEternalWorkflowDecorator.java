package com.irdeto.jumpstart.workflow;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.qa.ProgramContentQA;
import com.irdeto.jumpstart.domain.qa.ProgramMetadataQA;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.domain.relationship.Relationship;

public class ProgramEternalWorkflowDecorator extends AbstractEternalWorkflowDecorator<Program>{
	private static final String PROGRAM_PROCESS_NAME = "Program";
	
	@Override
	@JsonIgnore
	public String getProcessId() {
		return WORKFLOW_PACKAGE_NAME + '.' + PROGRAM_PROCESS_NAME;
	}

	@Override
	@JsonIgnore
	public QADecorator getContentQADecorator() {
		return new ProgramContentQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public QADecorator getMetadataQADecorator() {
		return new ProgramMetadataQA(getEntityId(), getEntityType());
	}

	@Override
	@JsonIgnore
	public List<Relationship<?>> getRelationshipList() {
		return new ArrayList<>();
	}

	@Override
	@JsonIgnore
	public Class<Program> getEntityClass() {
		return Program.class;
	}
}
