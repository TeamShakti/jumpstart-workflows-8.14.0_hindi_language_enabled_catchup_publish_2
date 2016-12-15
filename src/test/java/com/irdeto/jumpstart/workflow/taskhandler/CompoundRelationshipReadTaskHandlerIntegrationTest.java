package com.irdeto.jumpstart.workflow.taskhandler;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.process.WorkItemHandler;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.irdeto.manager.jbpm.knowledge.ClassManager;
import com.irdeto.manager.properties.PropertiesManagerImpl;
import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.taskhandler.CompoundRelationshipRead")
@TestProcessFileNames({
	"com/irdeto/jumpstart/workflow/taskhandler/CompoundReadRelationshipsTest.bpmn"
	})
public class CompoundRelationshipReadTaskHandlerIntegrationTest extends BaseProcessUnitTest {
	@BeforeClass
	public void setupPropertiesManagerPhase2() {
		PropertiesManagerImpl propertiesManager = (PropertiesManagerImpl)context.getBean("propertiesManager");
		ClassManager classManager = (ClassManager)context.getBean("classManager");
		propertiesManager.secondStageSetup(classManager);
	}

	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, WorkItemHandler> beanMap = context.getDependencyInjectionContext().getBeansOfType(WorkItemHandler.class);
		for (Map.Entry<String, WorkItemHandler> entry: beanMap.entrySet()) {
			logger.debug("Registering bean: {}", entry.getKey());
			sessionManager.getSession().getWorkItemManager().registerWorkItemHandler(
					entry.getKey(),
					entry.getValue());
		}
		Map<String, Object> data = new HashMap<String, Object>();
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
	}
}
