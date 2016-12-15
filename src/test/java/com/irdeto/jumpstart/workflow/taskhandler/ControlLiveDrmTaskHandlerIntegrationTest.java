package com.irdeto.jumpstart.workflow.taskhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.livedrm.GenerateKeysObjectFactory;
import com.irdeto.manager.jbpm.knowledge.ClassManager;
import com.irdeto.manager.properties.PropertiesManagerImpl;
import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.TestContextObject;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.taskhandler.ControlLiveDrmTest")
@TestProcessFileNames({
	"com/irdeto/jumpstart/workflow/taskhandler/ControlLiveDrmTest.bpmn"
	})
public class ControlLiveDrmTaskHandlerIntegrationTest extends BaseProcessUnitTest {

	private static final String CONTENT_ID = "1_live";

	@BeforeClass(groups={TestContextObject.INTEGRATION_TEST})
	public void setupSecondPropertiesManager() {
		ClassManager classManager = (ClassManager) context.getBean("classManager");
		((PropertiesManagerImpl)propertiesManager).secondStageSetup(classManager );
	}

	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		GenerateKeysObjectFactory objectFactory;
		try {
			objectFactory = new GenerateKeysObjectFactory();
			objectFactory.setContentId(CONTENT_ID);
			List<String> protectionSystemList = new ArrayList<>();
			protectionSystemList.add(GenerateKeysObjectFactory.PROTECTION_SYSTEM_PLAYREADY);
			objectFactory.setProtectionSystemList(protectionSystemList);
			data.put("liveDrmSoapEnvelope", objectFactory.createSoapMsgEnvelope());
		} catch (Exception e) {
		}
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
	}
}
