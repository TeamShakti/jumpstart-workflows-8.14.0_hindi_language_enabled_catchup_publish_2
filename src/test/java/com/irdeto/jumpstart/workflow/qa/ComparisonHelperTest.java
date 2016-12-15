package com.irdeto.jumpstart.workflow.qa;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.qa.ProgramMetadataQA;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.jumpstart.workflow.WorkflowHelperTest;

@Test
public class ComparisonHelperTest {
	public void testComparisonHelper() {
		Program program1 = WorkflowHelperTest.getDummyProgram(WorkflowHelper.SOURCE_TYPE);
		Program program2 = WorkflowHelperTest.getDummyProgram(WorkflowHelper.SOURCE_TYPE);
		Assert.assertFalse(program1 == program2);
		Assert.assertTrue(ComparisonHelper.areSameObjects(program1, program2));
		program2.getVideoContent().get(0).getSubcontent().get(0).setId("1000");
		Assert.assertTrue(ComparisonHelper.areSameObjects(program1, program2));
		program2.getVideoContent().get(0).getSubcontent().get(0).setBitRate(1);
		Assert.assertTrue(ComparisonHelper.areSameObjects(program1, program2));
		program2.getVideoContent().get(0).setCheckSum("somethingelse");
		Assert.assertFalse(ComparisonHelper.areSameObjects(program1, program2));
		
	}
	
	public void testAreSameObjToQAHT() {
		Program program1 = WorkflowHelperTest.getDummyProgram(WorkflowHelper.SOURCE_TYPE);
		Program program2 = WorkflowHelperTest.getDummyProgram(WorkflowHelper.SOURCE_TYPE);
		program2.getVideoContent().get(0).getSubcontent().get(0).setContentFileSize("15325");
		ProgramMetadataQA programQA = new ProgramMetadataQA();
		programQA.setEntity(program2);
		program2.setMetadataApproved(false);
		if(ComparisonHelper.areSameObjects(program1, program2)){
			program2.setMetadataApproved(true);
		}
		Assert.assertFalse(programQA.isQARequired());
		
		List<Object> versions = new ArrayList<Object>();
		versions.add(0);
		versions.add(1);
		versions.add(2);
		program2.setVersions(versions);
		
		if(ComparisonHelper.areSameObjects(program1, program2)){
			program2.setMetadataApproved(true);
		}
		
		Assert.assertFalse(programQA.isQARequired());
		
		boolean considerSubContent = true;
		Assert.assertFalse(ComparisonHelper.areSameObjects(program1, program2, considerSubContent, new ArrayList<String>()));
	}
}
	
