package com.irdeto.jumpstart.workflow.qa;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.BaseProcessUnitTestWithSetup;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.qa.HumanTaskTest")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/qa/HumanTaskTest.bpmn"})
public class HumanTaskIntegrationTest extends BaseProcessUnitTestWithSetup {
	@Override
	@Test(enabled=false)
	public Map<String, Object> getInput() {
		return new HashMap<>();
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> variables) {
	}

}
