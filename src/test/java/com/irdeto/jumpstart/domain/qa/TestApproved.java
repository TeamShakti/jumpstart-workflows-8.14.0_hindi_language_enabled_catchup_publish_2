package com.irdeto.jumpstart.domain.qa;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.Brand;

@Test
public class TestApproved {

	public void setMetadataQATest() {
		Brand brand = new Brand();
		BrandMetadataQA qa = new BrandMetadataQA();
		qa.setEntity(brand);
		qa.setEntityId("1");
		qa.setApproved(true);
		Assert.assertTrue(brand.getMetadataApproved());
		qa.setApproved(false);
		Assert.assertFalse(brand.getMetadataApproved());
	}

	public void getMetadataQATest() {
		Brand brand = new Brand();
		BrandMetadataQA qa = new BrandMetadataQA();
		qa.setEntity(brand);
		qa.setEntityId("1");
		brand.setMetadataApproved(true);
		Assert.assertTrue(qa.getApproved());
		brand.setMetadataApproved(false);
		Assert.assertFalse(qa.getApproved());
	}

	public void setContentQATest() {
		Brand brand = new Brand();
		BrandContentQA qa = new BrandContentQA();
		qa.setEntity(brand);
		qa.setEntityId("1");
		qa.setApproved(true);
		Assert.assertTrue(brand.getContentApproved());
		qa.setApproved(false);
		Assert.assertFalse(brand.getContentApproved());
	}

	public void getContentQATest() {
		Brand brand = new Brand();
		BrandContentQA qa = new BrandContentQA();
		qa.setEntity(brand);
		qa.setEntityId("1");
		brand.setContentApproved(true);
		Assert.assertTrue(qa.getApproved());
		brand.setContentApproved(false);
		Assert.assertFalse(qa.getApproved());
	}
}
