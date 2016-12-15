package com.irdeto.workflow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.JumpstartProtect")
@TestProcessFileNames({ "JumpstartProtect.bpmn" })
public class JumpstartProtectIntegrationTest extends BaseProcessUnitTest {
	@Test(enabled = false)
	@Override
	public void validateOutput(Map<String, Object> variables) {
	}

	@Test(enabled = false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("programEntityId", "3");
		inputMap.put("programEntityTitle", "Mad Max");
		inputMap.put("movieURIID", "irdeto.com/ContentGroup/IRDE2012000000000DDD");

		List<String> inputList= new ArrayList<String>();
		inputList.add("s3://jumpstart-transcoded/NJirdeto%2Ecom%2FContentGroup%2FIRDE2012000000000BBB-PC-movie-1-en_USA-256.mp4");
		inputList.add("s3://jumpstart-transcoded/NJirdeto%2Ecom%2FContentGroup%2FIRDE2012000000000BBB-PC-movie-1-en_USA-512.mp4");
		inputList.add("s3://jumpstart-transcoded/NJirdeto%2Ecom%2FContentGroup%2FIRDE2012000000000BBB-PC-movie-1-en_USA-1024.mp4");
		inputList.add("s3://jumpstart-transcoded/NJirdeto%2Ecom%2FContentGroup%2FIRDE2012000000000BBB-PC-movie-1-en_USA-1500.mp4");
		inputList.add("s3://jumpstart-transcoded/NJirdeto%2Ecom%2FContentGroup%2FIRDE2012000000000BBB-PC-movie-1-en_USA-2048.mp4");
		inputMap.put("inputList", inputList);
		return inputMap;
	}
}
