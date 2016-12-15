package com.irdeto.jumpstart.workflow.ingest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.BaseProcessUnitTestWithSetup;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.ingest.DataIngest")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/ingest/DataIngest.bpmn"})
public class DataIngestIntegrationTest extends BaseProcessUnitTestWithSetup {

	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<>();
		try {
			String inputContent = WorkflowHelper.getXml("OceansEleven.xml");
//			String inputContent = WorkflowHelper.getXml("genres.xml");
//			String inputContent = WorkflowHelper.getXml("ibcxmltvfeed.xml");
//			String inputContent = WorkflowHelper.getXml("20150109_Storm Surfers.xml");
//			String inputContent = WorkflowHelper.getXml("data/deviceProfiles.xml");
			//String inputContent = WorkflowHelper.getXml("data/ratings.xml");
			data.put("inputContent", inputContent);
			data.put("filePath", "METADATA");
		} catch (IOException e) {
		}
		return data;
	}

	@Test(enabled=false)
	@Override
	public void validateOutput(Map<String, Object> variables) {
	}

}
