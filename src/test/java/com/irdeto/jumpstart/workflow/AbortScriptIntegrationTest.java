package com.irdeto.jumpstart.workflow;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.AbortScriptTest")
@TestProcessFileNames({
	"com/irdeto/jumpstart/workflow/AbortScriptTest.bpmn"})
public class AbortScriptIntegrationTest extends BaseProcessUnitTest {

	@Override
	protected Map<String, Object> getInput() {
		return new HashMap<>();
	}

	@Override
	protected void validateOutput(Map<String, Object> variables) {
	}

}
