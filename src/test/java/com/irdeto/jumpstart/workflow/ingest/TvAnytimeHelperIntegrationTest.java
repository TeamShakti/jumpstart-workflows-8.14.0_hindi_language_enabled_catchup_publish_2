package com.irdeto.jumpstart.workflow.ingest;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.ingest.BrandIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.ProgramIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.SeriesIngestWrapper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.task.BeanUtil;
import com.irdeto.test.BaseSpringTest;

public class TvAnytimeHelperIntegrationTest extends BaseSpringTest {
	@BeforeClass
	public void setupBeanUtil() {
		BeanUtil.setContext(context.getDependencyInjectionContext());
	}

	@Test
	public void findProgramTest() throws Exception {
		String xmlInput = WorkflowHelper.getXml("20140430_daserste_71_0001.xml");
		List<EntityIngestWrapper<?>> ingestWrapperList = VodIngestFactory.getInstance().getMapper(xmlInput, "something.xml", "METADATA").findEntities();
		int programWrapperCount = 0;
		for (EntityIngestWrapper<?> ingestWrapper: ingestWrapperList) {
			if (ingestWrapper instanceof ProgramIngestWrapper) {
				programWrapperCount++;
			}
		}
		Assert.assertEquals(programWrapperCount, 37);
	}

	@Test
	public void findSeriesTest() throws Exception {
		String xmlInput = WorkflowHelper.getXml("20140430_daserste_71_0001.xml");
		List<EntityIngestWrapper<?>> ingestWrapperList = VodIngestFactory.getInstance().getMapper(xmlInput, "something.xml", "METADATA").findEntities();
		int seriesWrapperCount = 0;
		for (EntityIngestWrapper<?> ingestWrapper: ingestWrapperList) {
			if (ingestWrapper instanceof SeriesIngestWrapper) {
				seriesWrapperCount++;
			}
		}
		Assert.assertEquals(seriesWrapperCount, 37);
	}

	@Test
	public void findBrandTest() throws Exception {
		String xmlInput = WorkflowHelper.getXml("20140430_daserste_71_0001.xml");
		List<EntityIngestWrapper<?>> ingestWrapperList = VodIngestFactory.getInstance().getMapper(xmlInput, "something.xml", "METADATA").findEntities();
		int brandWrapperCount = 0;
		for (EntityIngestWrapper<?> ingestWrapper: ingestWrapperList) {
			if (ingestWrapper instanceof BrandIngestWrapper) {
				brandWrapperCount++;
			}
		}
		Assert.assertEquals(brandWrapperCount, 37);
	}

}
