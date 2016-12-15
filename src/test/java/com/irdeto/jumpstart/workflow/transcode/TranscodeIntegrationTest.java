package com.irdeto.jumpstart.workflow.transcode;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.BaseProcessUnitTestWithSetup;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.transcode.Transcode")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/transcode/Transcode.bpmn",
	"com/irdeto/jumpstart/workflow/transcode/TranscodeElementalCloud.bpmn"})
public class TranscodeIntegrationTest extends BaseProcessUnitTestWithSetup {

	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<>();
		data.put("entityId", "1");
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
	}
}
