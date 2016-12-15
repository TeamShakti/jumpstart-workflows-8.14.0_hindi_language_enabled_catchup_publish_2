package com.irdeto.jumpstart.domain;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.json.JsonManager;
import com.irdeto.test.BaseSpringTest;

@Test
public class TermSerializationIntegrationTest extends BaseSpringTest {
	private JsonManager jsonManager;

	@BeforeClass
	public void setupJsonManager() {
		jsonManager = (JsonManager)context.getBean("jsonManager");
	}

	public void testTermSerialization() throws Exception {
		Term term = new Term();
		term.setContractName("Test");
		term.setId("1");
		term.setStartDateTime(WorkflowHelper.START_OF_TIME);
		Map<String, Map<String, String>> map = new HashMap<>();
		term.setSuggestedPrice(map);
		String serializedTerm = jsonManager.serialize(term);
		Assert.assertNotNull(serializedTerm);
	}
}
