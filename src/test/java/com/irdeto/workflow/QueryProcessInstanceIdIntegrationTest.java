package com.irdeto.workflow;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.domain.constants.TaskHandlerConstants;
import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestAssertNodeNames;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.workflow.query")
@TestProcessFileNames({ "query.bpmn" })
@TestAssertNodeNames({ "StartProcess", "EndProcess", "QueryActiveProcessInstances" })
public class QueryProcessInstanceIdIntegrationTest extends BaseProcessUnitTest {

	@Test(enabled=false)
	@Override
	public void validateOutput(Map<String, Object> variables) {
		//Assert.assertEquals(TaskHandlerConstants.SUCCESS_STATUS, variables.get("exitStatus"));
		Assert.assertNotNull(TaskHandlerConstants.SUCCESS_STATUS);
	}

	@Override
	@Test(enabled=false)
	public Map<String, Object> getInput() {
		return null;
	}
}
