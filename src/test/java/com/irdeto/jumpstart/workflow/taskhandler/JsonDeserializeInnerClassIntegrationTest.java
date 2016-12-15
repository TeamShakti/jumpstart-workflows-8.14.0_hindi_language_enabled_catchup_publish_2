package com.irdeto.jumpstart.workflow.taskhandler;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.manager.json.JsonManager;
import com.irdeto.test.BaseSpringTest;

public class JsonDeserializeInnerClassIntegrationTest extends BaseSpringTest{
	@Resource(name="jsonManager")
	private JsonManager jsonManager;
	
  @Test
  public void deserializeTest() {
	  JsonManager jsonManager = (JsonManager)context.getBean("jsonManager");
	  //String text = "{\"workItemId\":\"FAILURE\",\"status\":\"FAILURE\"}";
	  String text = "{ \"work_item_id\":\"1111\", \"status\":\"FAILURE\" }";
		try {
			CommandLineMessage commandLineMessage = jsonManager.deserializeObject(text, CommandLineMessage.class);
			Assert.assertEquals(commandLineMessage.getWork_item_id(), "1111");
			Assert.assertEquals(commandLineMessage.getStatus(), "FAILURE");
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
  }
  static class CommandLineMessage {
		private String work_item_id;
		private String status;

		public String getWork_item_id() {
			return work_item_id;
		}
		public void setWork_item_id(String work_item_id) {
			this.work_item_id = work_item_id;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}

	}
 }
