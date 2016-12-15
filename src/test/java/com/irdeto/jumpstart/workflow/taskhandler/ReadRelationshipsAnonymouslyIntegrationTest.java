package com.irdeto.jumpstart.workflow.taskhandler;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.taskhandler.ReadRelationshipsAnonymouslyTest")
@TestProcessFileNames({
	"com/irdeto/jumpstart/workflow/taskhandler/ReadRelationshipsAnonymouslyTest.bpmn"
	})
public class ReadRelationshipsAnonymouslyIntegrationTest extends BaseProcessUnitTest {

	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("entityId", "11");
		data.put("relationship", "videoContent");
		data.put("entityType", "videoContent");
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
	}
}
