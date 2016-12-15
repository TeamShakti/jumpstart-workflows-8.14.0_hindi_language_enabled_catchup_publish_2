package com.irdeto.workflow;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.EpgIngest")
@TestProcessFileNames({ "JumpstartEpgIngest.bpmn" })
public class JumpstartEpgIngestIntegrationTest extends BaseProcessUnitTest {
	@Test(enabled=false)
	@Override
	public void validateOutput(Map<String, Object> variables) {
		//System.out.println("ExitStatus = " + variables.get("ExitStatus"));
		//System.out.println("ExitMessage = " + variables.get("ExitMessage"));
	}

	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("mdIdentifier", "EPG");
		inputMap.put("fileName", "epg_default.xml");
		inputMap.put("filePath", "D:\\PodsWatch\\jumpstart\\EPG\\processed\\");
		inputMap.put("podServer", null);
		String xmlTv;
		try {
			xmlTv = WorkflowHelper.getXml("epg_default.xml");
			inputMap.put("inputContent", xmlTv);
		} catch (IOException e) {
		}
		return inputMap;
	}
}