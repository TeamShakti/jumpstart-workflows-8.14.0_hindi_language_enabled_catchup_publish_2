package com.irdeto.jumpstart.workflow.taskhandler;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.BaseProcessUnitTestWithSetup;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.SuppressingSyncSubprocessTest")
@TestProcessFileNames({
	"com/irdeto/jumpstart/workflow/SuppressingSyncSubprocessTest.bpmn",
	"com/irdeto/jumpstart/workflow/WaitProcess.bpmn"
	})
public class SuppressingSyncSubprocessIntegrationTest extends BaseProcessUnitTestWithSetup {
	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
	}
}
