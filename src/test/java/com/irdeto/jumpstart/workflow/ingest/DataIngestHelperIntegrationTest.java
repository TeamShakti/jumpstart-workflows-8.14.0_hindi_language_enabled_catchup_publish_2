package com.irdeto.jumpstart.workflow.ingest;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.task.BeanUtil;
import com.irdeto.test.BaseSpringTest;

public class DataIngestHelperIntegrationTest extends BaseSpringTest {
	@BeforeClass
	public void setupBeanUtil() {
		BeanUtil.setContext(context.getDependencyInjectionContext());
	}
	
	@Test
	public void testIngestXMLTV() throws Exception {
		String xml = WorkflowHelper.getXml("ibcxmltvfeed.xml");
		List<EntityIngestWrapper<?>> entityIngestWrapperList = DataIngestHelper.mapMetadata(xml, "something.xml", "METADATA");
		Assert.assertNotNull(entityIngestWrapperList);
	}

	@Test
	public void testIngestGenres() throws Exception {
		String xml = WorkflowHelper.getXml("genres.xml");
		List<EntityIngestWrapper<?>> entityIngestWrapperList = DataIngestHelper.mapMetadata(xml, "something.xml", "METADATA");
		Assert.assertNotNull(entityIngestWrapperList);
	}

	@Test
	public void testIngestCablelabsChannel() throws Exception {
		String xml = WorkflowHelper.getXml("cablelabsEvent.xml");
		List<EntityIngestWrapper<?>> entityIngestWrapperList = DataIngestHelper.mapMetadata(xml, "cablelabsEvent.xml", "METADATA");
		Assert.assertNotNull(entityIngestWrapperList);
	}

}
