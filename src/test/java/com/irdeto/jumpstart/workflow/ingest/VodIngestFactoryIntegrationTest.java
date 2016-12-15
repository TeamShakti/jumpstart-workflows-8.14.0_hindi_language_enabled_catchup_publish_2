package com.irdeto.jumpstart.workflow.ingest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.task.BeanUtil;
import com.irdeto.test.BaseSpringTest;

@Test
public class VodIngestFactoryIntegrationTest extends BaseSpringTest {
	public void getMapperTest() throws Exception {
//		String xml = WorkflowHelper.getXml("Subscription_Brand_Series_EZCooking_WithOffers_11.xml");
		String xml = WorkflowHelper.getXml("SingleMovie.xml");
		BeanUtil.setContext(context.getDependencyInjectionContext());
		VodIngestMapper mapper = VodIngestFactory.getInstance().getMapper(xml, "something.xml", "METADATA");
		Assert.assertNotNull(mapper);
	}
}
