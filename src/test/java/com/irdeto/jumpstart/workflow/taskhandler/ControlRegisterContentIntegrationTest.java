package com.irdeto.jumpstart.workflow.taskhandler;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.irdeto.domain.control.ControlRegisterContentDummyData;
import com.irdeto.manager.jbpm.knowledge.ClassManager;
import com.irdeto.manager.properties.PropertiesManagerImpl;
import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.TestContextObject;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.taskhandler.ControlRegisterContentTest")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/taskhandler/ControlRegisterContentTest.bpmn"})
public class ControlRegisterContentIntegrationTest extends BaseProcessUnitTest {
	@BeforeClass(groups={TestContextObject.INTEGRATION_TEST})
	public void setupSecondPropertiesManager() {
		ClassManager classManager = (ClassManager) context.getBean("classManager");
		((PropertiesManagerImpl)propertiesManager).secondStageSetup(classManager );
	}
	@Override
	@Test(enabled=false)
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("batch", ControlRegisterContentDummyData.getControlBatch());
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> variables) {
	}

}
