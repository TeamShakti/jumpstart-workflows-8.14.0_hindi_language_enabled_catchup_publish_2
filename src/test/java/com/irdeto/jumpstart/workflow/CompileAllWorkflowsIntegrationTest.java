package com.irdeto.jumpstart.workflow;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.protect.Protect")
@TestProcessFileNames({
	"com/irdeto/jumpstart/workflow/CascadeEntities.bpmn",
	"com/irdeto/jumpstart/workflow/EternalWorkflow.bpmn",
	"com/irdeto/jumpstart/workflow/MMEntry.bpmn",
	"com/irdeto/jumpstart/workflow/Program.bpmn",
	"com/irdeto/jumpstart/workflow/config/ExtractDeviceProfile.bpmn",
	"com/irdeto/jumpstart/workflow/config/ExtractRatingSchemeRating.bpmn",
	"com/irdeto/jumpstart/workflow/ingest/DataIngest.bpmn",
	"com/irdeto/jumpstart/workflow/ingest/MediaIngest.bpmn",
	"com/irdeto/jumpstart/workflow/protect/Protect.bpmn",
	"com/irdeto/jumpstart/workflow/protect/ProtectCPS.bpmn",
	"com/irdeto/jumpstart/workflow/protect/ProtectUS.bpmn",
	"com/irdeto/jumpstart/workflow/publish/Publish.bpmn",
	"com/irdeto/jumpstart/workflow/publish/PublishToEndpoints.bpmn",
	"com/irdeto/jumpstart/workflow/publish/cdn/CdnPublish.bpmn",
	"com/irdeto/jumpstart/workflow/publish/control/ControlPublish.bpmn",
	"com/irdeto/jumpstart/workflow/purge/CancelTasks.bpmn",
	"com/irdeto/jumpstart/workflow/purge/CascadePurge.bpmn",
	"com/irdeto/jumpstart/workflow/purge/Purge.bpmn",
	"com/irdeto/jumpstart/workflow/purge/PurgeFromEndpoints.bpmn",
	"com/irdeto/jumpstart/workflow/qa/QA.bpmn",
	"com/irdeto/jumpstart/workflow/transcode/Transcode.bpmn",
	"com/irdeto/jumpstart/workflow/transcode/TranscodeElementalCloud.bpmn"
	})
public class CompileAllWorkflowsIntegrationTest extends BaseProcessUnitTestWithSetup {

	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put("Action", "Update");
			data.put("EntityID", "1");
			data.put("EntityType", MMEntryHelper.PROGRAM_KEY);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
	}
}
