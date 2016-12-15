package com.irdeto.workflow;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.Program;
import com.irdeto.manager.file.FileManager;
import com.irdeto.manager.json.JsonManager;
import com.irdeto.test.BaseSpringTest;

@Test
public class MM8ResponseDeserializationIntegrationTest extends BaseSpringTest {


	private JsonManager jsonManager;
	private FileManager fileManager;

	@Test
	public void testDeserialization() throws Exception {
		jsonManager = (JsonManager)context.getBean("jsonManager");
		fileManager = (FileManager)context.getBean("fileManager");
		String jsonString = fileManager.readFile("src/test/resources/mm8response.json");
		Program program = jsonManager.deserializeEntity(jsonString, Program.class);
		Assert.assertNotNull(program);
	}

}
