package com.irdeto.workflow;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.humantask")
@TestProcessFileNames("JumpstartHT.bpmn")
public class JumpstartHTIntegrationTest  extends BaseProcessUnitTest {

	
	/**
	 * provide input data map to the process
	 */
	@Override
	@Test(enabled=false)
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> signalDataHT = new HashMap<String, Object>();
		String mdIdentifier = "Media";
		signalDataHT.put("programEntityId", "10");
		signalDataHT.put("mdIdentifier", mdIdentifier);
		data.put("signalDataHT", signalDataHT);
		
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> results) {
	}

}
