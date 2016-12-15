package com.irdeto.jumpstart.workflow.purge;

import java.util.List;

import org.kie.api.runtime.process.ProcessContext;

import com.irdeto.domain.operation.MM8Response;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class ScheduledPurgeHelper extends WorkflowHelper {

	private static final String PROGRAM_LIST_KEY = "programList";

	public static void exitReadAllProgramEntity(ProcessContext kcontext){

		MM8Response response = (MM8Response) kcontext.getVariable(MM8_RESPONSE_KEY);
		List<Program> programList = getEntityList(Program.class, response);
		kcontext.setVariable(PROGRAM_LIST_KEY, programList);
	}

	public static void entryCallEntityPurge(ProcessContext kcontext){
		@SuppressWarnings("unchecked")
		List<Program> programList = (List<Program>) kcontext.getVariable(PROGRAM_LIST_KEY);
		Program program = programList.remove(0);
		kcontext.setVariable("PurgeOnExpiry", "true");
		kcontext.setVariable("EntityId", program.getId());
	}

}
