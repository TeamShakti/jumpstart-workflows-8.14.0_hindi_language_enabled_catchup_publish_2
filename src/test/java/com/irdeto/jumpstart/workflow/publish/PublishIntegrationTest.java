package com.irdeto.jumpstart.workflow.publish;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.BaseProcessUnitTestWithSetup;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.publish.Publish")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/publish/Publish.bpmn",
	"com/irdeto/jumpstart/workflow/publish/PublishToEndpoints.bpmn",
	"com/irdeto/jumpstart/workflow/publish/cdn/CdnPublish.bpmn",
	"com/irdeto/jumpstart/workflow/publish/activemq/CatchUpPublish.bpmn",
	"com/irdeto/jumpstart/workflow/publish/control/ControlPublish.bpmn"
	})
public class PublishIntegrationTest extends BaseProcessUnitTestWithSetup {

	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<>();
		data.put("entityId", "46983");
		data.put("entityType", "scheduleSlot");
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
	}
}
