package com.irdeto.jumpstart.workflow;

import java.util.Map;

import org.kie.api.runtime.process.WorkItemHandler;
import org.testng.annotations.BeforeClass;

import com.irdeto.manager.jbpm.knowledge.ClassManager;
import com.irdeto.manager.properties.PropertiesManagerImpl;
import com.irdeto.test.BaseProcessUnitTest;

public abstract class BaseProcessUnitTestWithSetup extends BaseProcessUnitTest {
	@BeforeClass
	public void setupPropertiesManagerAndTaskHandlers() {
		PropertiesManagerImpl propertiesManager = (PropertiesManagerImpl)context.getBean("propertiesManager");
		ClassManager classManager = (ClassManager)context.getBean("classManager");
		propertiesManager.secondStageSetup(classManager);
		Map<String, WorkItemHandler> beanMap = context.getDependencyInjectionContext().getBeansOfType(WorkItemHandler.class);
		for (Map.Entry<String, WorkItemHandler> entry: beanMap.entrySet()) {
			logger.debug("Registering bean: {}", entry.getKey());
			sessionManager.getSession().getWorkItemManager().registerWorkItemHandler(
					entry.getKey(),
					entry.getValue());
		}
	}

}
