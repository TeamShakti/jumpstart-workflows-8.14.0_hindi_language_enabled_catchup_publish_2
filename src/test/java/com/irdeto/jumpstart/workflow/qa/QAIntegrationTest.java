package com.irdeto.jumpstart.workflow.qa;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.qa.ProgramMetadataQA;
import com.irdeto.jumpstart.domain.qa.QADecorator;
import com.irdeto.jumpstart.workflow.BaseProcessUnitTestWithSetup;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.qa.QA")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/qa/QA.bpmn"})
public class QAIntegrationTest extends BaseProcessUnitTestWithSetup {
	@Override
	@Test(enabled=false)
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> qaData = new HashMap<String, Object>();
		QADecorator qaDecorator = new ProgramMetadataQA("13", "program");
		qaData.put(QADecorator.QA_ENTITY_KEY, qaDecorator);
		data.put(QADecorator.QA_DATA_KEY, qaData);
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> variables) {
	}

}
