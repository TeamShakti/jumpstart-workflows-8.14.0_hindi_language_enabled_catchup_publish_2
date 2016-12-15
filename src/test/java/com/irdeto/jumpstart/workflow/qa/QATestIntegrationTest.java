package com.irdeto.jumpstart.workflow.qa;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.qa.QATest")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/qa/QATest.bpmn"})
public class QATestIntegrationTest extends BaseProcessUnitTest {
	@Override
	@Test(enabled=false)
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("programEntityId", "4");
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> variables) {
	}

}
