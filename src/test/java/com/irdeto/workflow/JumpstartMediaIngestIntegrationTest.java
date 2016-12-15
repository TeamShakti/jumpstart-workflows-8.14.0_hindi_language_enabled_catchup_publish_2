package com.irdeto.workflow;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.domain.ccspods.DataWrapper;
import com.irdeto.domain.transfer.NameValueObject;
import com.irdeto.manager.datawrapper.DataWrapperSerializer;
import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;
import com.irdeto.test.annotation.TestWaitForNode;

@Test
@TestWaitForNode("Termination")
@TestProcessId("com.irdeto.jumpstart.workflow.dataingest.DataIngest")
@TestProcessFileNames({ "com/irdeto/jumpstart/workflow/dataingest/DataIngest.bpmn",
	"com/irdeto/jumpstart/workflow/Program.bpmn",
	"com/irdeto/jumpstart/workflow/transcode/Transcode.bpmn",
//	"com/irdeto/jumpstart/workflow/protect/GenericProtect.bpmn",
//	"com/irdeto/jumpstart/workflow/protect/Protect.bpmn",
	"com/irdeto/jumpstart/workflow/qa/QA.bpmn",
	"com/irdeto/jumpstart/workflow/dataingest/MediaIngest.bpmn",
	"com/irdeto/jumpstart/workflow/MMEntry.bpmn"})
public class JumpstartMediaIngestIntegrationTest extends BaseProcessUnitTest {

	DataWrapperSerializer dataWrapperSerializer;

	private Logger logger = LoggerFactory.getLogger(JumpstartMediaIngestIntegrationTest.class);

	/**
	 * provide intput data map to the process
	 */
	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			dataWrapperSerializer = (DataWrapperSerializer)context.getBean("dataWrapperSerializer");
			DataWrapper wrapper = dataWrapperSerializer
					.deserialize(getExampleMessage("wrappedMediaMessage.xml"));
			List<NameValueObject> params = wrapper.getParams();
			for (NameValueObject nvp : params) {
				logger.debug("VNP: " + nvp.getName() + "/" + nvp.getValue());
				data.put(nvp.getName(), nvp.getValue());
			}
			if (StringUtils.isNotEmpty(wrapper.getContent())){
				data.put("inputContent", wrapper.getContent());
			}
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

	private String getExampleMessage(String filename) throws IOException {
		String inputContent = null;
		InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream(filename);
		StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer);
		inputContent = writer.toString();
		return inputContent;
	}
//
//	 @AfterMethod
//	    public void testTimeout() throws Exception {
//	        Thread.sleep(600*1000);
//	    }
}
