package com.irdeto.jumpstart.workflow.taskhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.irdeto.domain.control.ControlRegisterContentDummyData;
import com.irdeto.domain.control.cws.objectfactory.DeleteMediaObjectFactory;
import com.irdeto.domain.control.cws.objectfactory.UpsertP7CategoryObjectFactory;
import com.irdeto.domain.control.cws.objectfactory.UpsertP7MediaCategoryObjectFactory;
import com.irdeto.manager.jbpm.knowledge.ClassManager;
import com.irdeto.manager.properties.PropertiesManagerImpl;
import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.TestContextObject;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.taskhandler.ControlCwsTest")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/taskhandler/ControlCwsTest.bpmn"})
public class ControlCwsWorkflowIntegrationTest extends BaseProcessUnitTest {
	private static final String NAME = "Namjun";
	private static final String POLICY_ID = "2";
	private static final String DESCRIPTION = "Description";
	private static final String CATEGORY_ID = "100";
	private static final String PARENT_CATEGORY_ID = "0";
	private static final String MEDIA_ID = "100_movie";

	@BeforeClass(groups={TestContextObject.INTEGRATION_TEST})
	public void setupSecondPropertiesManager() {
		ClassManager classManager = (ClassManager) context.getBean("classManager");
		((PropertiesManagerImpl)propertiesManager).secondStageSetup(classManager );
	}
	@Override
	@Test(enabled=false)
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data.put("upsertP7Category", UpsertP7CategoryObjectFactory.createSoapEnvelope("Insert", CATEGORY_ID, PARENT_CATEGORY_ID, NAME, POLICY_ID, DESCRIPTION));
			data.put("deleteP7Category", UpsertP7CategoryObjectFactory.createSoapEnvelope("Delete", CATEGORY_ID));
			List<String> categoryIds = new ArrayList<String>();
			categoryIds.add(CATEGORY_ID);
			List<String> mediaIds = new ArrayList<String>();
			mediaIds.add(MEDIA_ID);
			data.put("upsertP7MediaCategory", UpsertP7MediaCategoryObjectFactory.createSoapEnvelope("Insert", categoryIds, mediaIds));
			data.put("deleteP7MediaCategory", UpsertP7MediaCategoryObjectFactory.createSoapEnvelope("Delete", categoryIds, mediaIds));
			data.put("deleteMedia", DeleteMediaObjectFactory.createSoapEnvelope(CATEGORY_ID, MEDIA_ID));
			data.put("batch", ControlRegisterContentDummyData.getControlBatch());
		} catch (Exception e) {
		}
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> variables) {
	}

}
