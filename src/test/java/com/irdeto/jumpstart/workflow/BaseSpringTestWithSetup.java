package com.irdeto.jumpstart.workflow;

import org.testng.annotations.BeforeClass;

import com.irdeto.manager.jbpm.knowledge.ClassManager;
import com.irdeto.manager.properties.PropertiesManagerImpl;
import com.irdeto.manager.task.BeanUtil;
import com.irdeto.test.BaseSpringTest;

public abstract class BaseSpringTestWithSetup extends BaseSpringTest {
	@BeforeClass
	public void setupPropertiesManagerAndTaskHandlers() {
		PropertiesManagerImpl propertiesManager = (PropertiesManagerImpl)context.getBean("propertiesManager");
		ClassManager classManager = (ClassManager)context.getBean("classManager");
		propertiesManager.secondStageSetup(classManager);
		BeanUtil.setContext(context.getDependencyInjectionContext());
	}

}
