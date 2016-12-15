package com.irdeto.jumpstart.workflow.management;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.management.MemoryHelper;

@Test
public class MemoryHelperTest {
	public void memoryHelperTest() {
		String memoryState = MemoryHelper.getMemoryState();
		Assert.assertNotNull(memoryState);
	}
}
