package com.irdeto.jumpstart.workflow.ingest;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.Genre;
import com.irdeto.jumpstart.domain.Locale;
import com.irdeto.jumpstart.domain.ingest.BrandIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.EventIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.OfferIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.ProgramIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.SeriesIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.SubscriptionPackageIngestWrapper;
import com.irdeto.jumpstart.domain.ingest.TermIngestWrapper;
import com.irdeto.jumpstart.workflow.LocaleHelper;
import com.irdeto.jumpstart.workflow.WorkflowHelper;
import com.irdeto.manager.json.JsonManager;
import com.irdeto.manager.locale.LocaleManager;
import com.irdeto.manager.properties.PropertiesManager;
import com.irdeto.manager.secondstagebean.SecondStageBeanManager;
import com.irdeto.manager.task.BeanUtil;
import com.irdeto.test.TestContextObject;

public class JDOMCablelabsMapperIntegrationTest {
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
	public void testFindEvents(){
		try {
			String xml = WorkflowHelper.getXml("Raccoon OceansEleven.xml");
			List<EntityIngestWrapper<?>> ingestWrapperList = VodIngestFactory.getInstance().getMapper(xml, "something.xml", "METADATA").findEntities();
			int eventWrapperCount= 0;
			for (EntityIngestWrapper<?> ingestWrapper: ingestWrapperList) {
				if (ingestWrapper instanceof EventIngestWrapper) {
					eventWrapperCount++;
				}
			}
			Assert.assertEquals(eventWrapperCount, 1);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindProgramsForMovie(){
		try {
			String xml = WorkflowHelper.getXml("SingleMovie.xml");
			List<EntityIngestWrapper<?>> ingestWrapperList = VodIngestFactory.getInstance().getMapper(xml, "something.xml", "METADATA").findEntities();
			int programWrapperCount= 0;
			for (EntityIngestWrapper<?> ingestWrapper: ingestWrapperList) {
				if (ingestWrapper instanceof ProgramIngestWrapper) {
					programWrapperCount++;
				}
			}
			Assert.assertEquals(programWrapperCount, 1);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}


	@Test
	public void testFindProgramsForSeries(){
		try {
			String xml = WorkflowHelper.getXml("Series-12-programs.xml");
			List<EntityIngestWrapper<?>> ingestWrapperList = VodIngestFactory.getInstance().getMapper(xml, "something.xml", "METADATA").findEntities();
			int programWrapperCount= 0;
			for (EntityIngestWrapper<?> ingestWrapper: ingestWrapperList) {
				if (ingestWrapper instanceof ProgramIngestWrapper) {
					programWrapperCount++;
				}
			}
			Assert.assertEquals(programWrapperCount, 12);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindSeries(){
		try {
			String xml = WorkflowHelper.getXml("Series-12-programs.xml");
			List<EntityIngestWrapper<?>> ingestWrapperList = VodIngestFactory.getInstance().getMapper(xml, "something.xml", "METADATA").findEntities();
			int seriesWrapperCount= 0;
			for (EntityIngestWrapper<?> ingestWrapper: ingestWrapperList) {
				if (ingestWrapper instanceof SeriesIngestWrapper) {
					seriesWrapperCount++;
				}
			}
			Assert.assertEquals(seriesWrapperCount, 1);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindBrand(){
		try {
			String xml = WorkflowHelper.getXml("TVseries-TrueBlood_Season5_Episode1.xml");
			List<EntityIngestWrapper<?>> ingestWrapperList = VodIngestFactory.getInstance().getMapper(xml, "something.xml", "METADATA").findEntities();
			int brandWrapperCount= 0;
			for (EntityIngestWrapper<?> ingestWrapper: ingestWrapperList) {
				if (ingestWrapper instanceof BrandIngestWrapper) {
					brandWrapperCount++;
				}
			}
			Assert.assertEquals(brandWrapperCount, 1);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testSerializeGenre() throws Exception {
		Locale locale = new Locale();
		LocaleHelper.setStringValueForDefaultLanguage(locale, "Comedy");
		Genre genre = new Genre();
		genre.setTitle(locale);
		String serialized = BeanUtil.jsonManager.serialize(genre);
		Assert.assertNotNull(serialized);
		Assert.assertEquals(serialized.length(), 83);
	}

	@Test
	public void testGetOffersTermsSubscriptionPackages()throws Exception {
		try {
			String xml = WorkflowHelper.getXml("SubPackage_Brand_Movie_channel1.xml");
			List<EntityIngestWrapper<?>> ingestWrapperList = VodIngestFactory.getInstance().getMapper(xml, "something.xml", "METADATA").findEntities();
			int subscriptionPackageIngestWrapperCount = 0;
			int termIngestWrapperCount = 0;
			int offerIngestWrapperCount = 0;
			for (EntityIngestWrapper<?> ingestWrapper: ingestWrapperList) {
				if (ingestWrapper instanceof SubscriptionPackageIngestWrapper) {
					subscriptionPackageIngestWrapperCount++;
				} else if (ingestWrapper instanceof TermIngestWrapper) {
					termIngestWrapperCount++;
				} else if (ingestWrapper instanceof OfferIngestWrapper) {
					offerIngestWrapperCount++;
				}
			}
			Assert.assertEquals(subscriptionPackageIngestWrapperCount, 1);
			Assert.assertEquals(termIngestWrapperCount, 1);
			Assert.assertEquals(offerIngestWrapperCount, 1);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testReadDataMCA() throws Exception {
		try {
			String xml = WorkflowHelper.getXml("IXS_Cablelabs_test1_MCA.xml");
			List<EntityIngestWrapper<?>> ingestWrapperList = VodIngestFactory.getInstance().getMapper(xml, "something.xml", "METADATA").findEntities();
			Assert.assertNotNull(ingestWrapperList);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
