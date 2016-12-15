package com.irdeto.jumpstart.workflow.config;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.config.ExtractDeviceProfile")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/config/ExtractDeviceProfile.bpmn"})

public class ExtractDeviceProfileIntegrationTest extends BaseProcessUnitTest {

	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<>();
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
	}
}
