package com.irdeto.workflow;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.jumpstartPublishToSAndD")
@TestProcessFileNames({ "JumpstartPublishToSAndD.bpmn", "GenericPublishToSAndD.bpmn" })
public class JumpstartPublishToSAndDIntegrationTest extends BaseProcessUnitTest{

	private static final String TEST_PROGRAM_ENTITY_ID = "3";

	/**
	 * provide intput data map to the process
	 */
	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put("programEntityId", TEST_PROGRAM_ENTITY_ID);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
		// TODO Auto-generated method stub

	}

}
