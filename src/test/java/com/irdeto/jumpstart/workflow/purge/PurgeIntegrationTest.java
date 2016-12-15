package com.irdeto.jumpstart.workflow.purge;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.BaseProcessUnitTestWithSetup;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.purge.Purge")
@TestProcessFileNames({"com/irdeto/jumpstart/workflow/purge/Purge.bpmn",
		"com/irdeto/jumpstart/workflow/purge/PurgeFromEndpoints.bpmn",
		"com/irdeto/jumpstart/workflow/purge/activemq/CatchUpPurge.bpmn",
})
public class PurgeIntegrationTest extends BaseProcessUnitTestWithSetup {
	@Test(enabled = true)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<>();
		data.put("entityId", "46983");
		data.put("action", "Delete");
		data.put("entityType", "scheduleSlot");
		return data;
	}

	@Override
	@Test(enabled = false)
	public void validateOutput(Map<String, Object> arg0) {
	}
}
