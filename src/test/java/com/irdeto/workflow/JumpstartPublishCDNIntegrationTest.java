package com.irdeto.workflow;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.Publish")
@TestProcessFileNames({ "JumpstartPublish.bpmn" })
public class JumpstartPublishCDNIntegrationTest extends BaseProcessUnitTest {
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
		inputMap.put("mdIdentifier", "movie");
		//inputMap.put("fileName", "epg_default.xml");
		//inputMap.put("filePath", "D:\\PodsWatch\\jumpstart\\EPG\\processed\\");
		//inputMap.put("podServer", null);
				
		return inputMap;
	}
}