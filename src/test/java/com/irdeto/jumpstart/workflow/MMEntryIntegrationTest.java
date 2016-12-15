package com.irdeto.jumpstart.workflow;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;
import com.irdeto.test.annotation.TestWaitForNode;

@Test
@TestWaitForNode("Termination")
@TestProcessId("com.irdeto.jumpstart.workflow.MMEntry")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/MMEntry.bpmn",
	"com/irdeto/jumpstart/workflow/Program.bpmn",
	"com/irdeto/jumpstart/workflow/transcode/Transcode.bpmn",
	"com/irdeto/jumpstart/workflow/qa/QA.bpmn",
	"com/irdeto/jumpstart/workflow/dataingest/MediaIngest.bpmn",
	"com/irdeto/jumpstart/workflow/MMEntry.bpmn"})
public class MMEntryIntegrationTest extends BaseProcessUnitTest {


//	private Logger logger = LoggerFactory.getLogger(MMEntryIntegrationTest.class);

	/**
	 * provide input data map to the process
	 */
	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			//String serializedMessage = getExampleMessage("ExampleMMEntryMessage.json");
			data.put("Action", "Update");
			data.put("EntityID", "1");
			data.put("EntityType", MMEntryHelper.PROGRAM_KEY);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return data;
	}

	//@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> arg0) {
		// TODO Auto-generated method stub

	}

//	private String getExampleMessage(String filename) throws IOException {
//		String inputContent = null;
//		InputStream is = this.getClass().getClassLoader()
//				.getResourceAsStream(filename);
//		StringWriter writer = new StringWriter();
//		IOUtils.copy(is, writer);
//		inputContent = writer.toString();
//		return inputContent;
//	}

}
