package com.irdeto.jumpstart.workflow.ingest;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.ingest.DeviceProfileIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.json.JsonManager;
import com.irdeto.manager.locale.LocaleManager;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.secondstagebean.SecondStageBeanManager;
import com.irdeto.manager.task.BeanUtil;
import com.irdeto.test.TestContextObject;

public class JDOMIrdetoDeviceProfileMapperIntegrationTest {
	private ApplicationContext context = null;

	@BeforeClass(groups={TestContextObject.INTEGRATION_TEST})
	public void setupJsonManager() {
		context = new ClassPathXmlApplicationContext("/testApplicationContext.xml");
		BeanUtil.propertiesManager = context.getBean(PropertiesManager.class);
		BeanUtil.localeManager = context.getBean(LocaleManager.class);
		BeanUtil.jsonManager = context.getBean(JsonManager.class);
		context.getBean(SecondStageBeanManager.class);
	}

	@Test
	public void testFindDeviceProfiles(){
		try {
			String xml = WorkflowHelper.getXml("data/deviceProfiles.xml");
			List<EntityIngestWrapper<?>> ingestWrapperList = VodIngestFactory.getInstance().getMapper(xml, "something.xml", "METADATA").findEntities();
			int wrapperCount= 0;
			for (EntityIngestWrapper<?> ingestWrapper: ingestWrapperList) {
				if (ingestWrapper instanceof DeviceProfileIngestWrapper) {
					wrapperCount++;
				}
			}
			Assert.assertEquals(wrapperCount, 7);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
