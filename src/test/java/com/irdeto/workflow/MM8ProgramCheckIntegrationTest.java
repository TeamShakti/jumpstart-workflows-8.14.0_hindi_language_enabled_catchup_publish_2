package com.irdeto.workflow;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.MM8ProgramCheck")
@TestProcessFileNames("JumpstartMM8ProgramCheck.bpmn")
public class MM8ProgramCheckIntegrationTest extends BaseProcessUnitTest {

	@Override
	@Test(enabled=false)
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<>();
		String programId = "irdeto.com/ContentGroup/IRDET201300000000228";
		data.put("programId", programId);
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> variables) {
		// TODO Auto-generated method stub
		
	}

}
