package com.irdeto.jumpstart.workflow.taskhandler;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.BaseProcessUnitTestWithSetup;
import com.irdeto.manager.jbpm.knowledge.ClassManager;
import com.irdeto.manager.properties.PropertiesManagerImpl;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.taskhandler.ProfileReadTest")
@TestProcessFileNames({
	"com/irdeto/jumpstart/workflow/taskhandler/ProfileReadTest.bpmn"
	})
public class ProfileReadTaskHandlerIntegrationTest extends BaseProcessUnitTestWithSetup {

	@BeforeClass
	public void setupJumpstartProperties() {
		PropertiesManagerImpl propertiesManager = (PropertiesManagerImpl)context.getBean("propertiesManager");
		ClassManager classManager = (ClassManager)context.getBean("classManager");
		propertiesManager.secondStageSetup(classManager);
	}
	
	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
	}
}
