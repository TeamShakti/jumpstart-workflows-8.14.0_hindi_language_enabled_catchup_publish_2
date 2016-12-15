package com.irdeto.workflow;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.domain.mediamanager.QueryFilterParameter;
import com.irdeto.domain.operation.MM8Response;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.datawrapper.DataWrapperSerializer;
import com.irdeto.test.BaseProcessUnitTest;
import com.irdeto.test.annotation.TestProcessFileNames;
import com.irdeto.test.annotation.TestProcessId;

/**
 * http://54.214.14.58/api/types/program/entities?filter=uriId%20eq%20irdeto.com%2FContentGroup%2FIRDE2012000000000001
 * @author c-peter.courcoux
 *
 */
@Test
@TestProcessId("com.irdeto.jumpstart.test.workflow.FilteredMM8Read")
@TestProcessFileNames("FilteredReadMM8workflowTest.bpmn")
public class FilteredReadMetaDataIntegrationTest extends BaseProcessUnitTest {

	DataWrapperSerializer dataWrapperSerializer;

	/**
	 * provide input data map to the process
	 */
	@Test(enabled=false)
	@Override
	public Map<String, Object> getInput() {
		Map<String, Object> data = new HashMap<>();
		QueryFilterParameter queryFilterParameter =
				WorkflowHelper.getQueryFilterParameter("irdeto.com/ContentGroup/IRDET201300000000228");
		data.put("QueryFilterList", queryFilterParameter);
		return data;
	}

	@Override
	@Test(enabled=false)
	public void validateOutput(Map<String, Object> results) {
		MM8Response response = (MM8Response)results.get("response");
		Assert.assertNotNull(response.getResponseObject());
		Program program = (Program)response.getResponseObject();
		String uriId = program.getId();
		System.out.println("Found program with uriId: "+ uriId);
		System.out.println("Program title: "+ program.getTitleSortName());
	}

}
