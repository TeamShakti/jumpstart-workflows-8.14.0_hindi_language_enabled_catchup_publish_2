package com.irdeto.workflow;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.domain.ccspods.DataWrapper;
import com.irdeto.domain.transfer.NameValueObject;
import com.irdeto.manager.datawrapper.DataWrapperSerializer;
import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

@Test
@TestProcessId("com.irdeto.jumpstart.workflow.DataIngest")
@TestProcessFileNames({ "JumpstartDataIngest.bpmn", "JumpstartProgram.bpmn", "ProcessMetaData.bpmn",
	"JumpstartElementalTranscode.bpmn", "GenericQTSElementalTranscode.bpmn", "query.bpmn",
	"JumpstartMM8ProgramCheck.bpmn", "JumpstartValidate.bpmn", "JumpstartPublish.bpmn"})
public class JumpstartDataIngestIntegrationTest extends BaseProcessUnitTest {

	DataWrapperSerializer dataWrapperSerializer;

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
					.deserialize(getExampleMessage("exampleWrappedMessage.xml"));
			List<NameValueObject> params = wrapper.getParams();
			for (NameValueObject nvp : params) {
				data.put(nvp.getName(), nvp.getValue());
			}
			data.put("inputContent", wrapper.getContent());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		return data;
	}

	@Override
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

}
