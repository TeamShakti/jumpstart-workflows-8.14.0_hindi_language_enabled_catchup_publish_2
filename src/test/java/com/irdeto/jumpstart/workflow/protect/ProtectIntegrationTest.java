package com.irdeto.jumpstart.workflow.protect;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.BaseProcessUnitTestWithSetup;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.protect.Protect")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/protect/Protect.bpmn",
	"com/irdeto/jumpstart/workflow/protect/ProtectCPS.bpmn",
	"com/irdeto/jumpstart/workflow/protect/ProtectUS.bpmn"})
public class ProtectIntegrationTest extends BaseProcessUnitTestWithSetup {

	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<>();
		data.put("entityId", "117");
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
	}
}
