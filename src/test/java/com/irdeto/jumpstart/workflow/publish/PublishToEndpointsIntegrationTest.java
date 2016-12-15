package com.irdeto.jumpstart.workflow.publish;

import static java.util.Collections.singletonMap;

import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.workflow.BaseProcessUnitTestWithSetup;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.publish.PublishToEndpoints")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/publish/PublishToEndPoints.bpmn",
	"com/irdeto/jumpstart/workflow/publish/cdn/CdnPublish.bpmn",
	"com/irdeto/jumpstart/workflow/publish/control/ControlPublish.bpmn"
	})
public class PublishToEndpointsIntegrationTest extends BaseProcessUnitTestWithSetup {

	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		return singletonMap("publishWrapper",
				new NonPublishingProgramPublishWrapper(new Program().withUriId("irdeto.com/program/123")));
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
	}

}
