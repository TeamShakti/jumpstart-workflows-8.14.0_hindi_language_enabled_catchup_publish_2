package com.irdeto.workflow;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.domain.operation.MM8Response;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.manager.datawrapper.DataWrapperSerializer;
import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.ReadMetaDataTest")
@TestProcessFileNames("ReadMetaDataTest.bpmn")
public class ReadMetaDataIntegrationTest extends BaseProcessUnitTest {

	DataWrapperSerializer dataWrapperSerializer;

	/**
	 * provide intput data map to the process
	 */
	@Override
	@Test(enabled=false)
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> results) {
		MM8Response response = (MM8Response)results.get("response");
		Assert.assertNotNull(response.getResponseObject());
		Program program = (Program)response.getResponseObject();
		String uriId = program.getId();
		System.out.println("Found program with uriId: "+ uriId);
		System.out.println("Program title: "+ program.getTitleSortName());
	}
}
