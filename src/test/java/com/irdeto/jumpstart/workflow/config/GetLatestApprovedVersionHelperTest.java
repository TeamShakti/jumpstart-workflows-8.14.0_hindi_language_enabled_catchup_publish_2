package com.irdeto.jumpstart.workflow.config;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Program;
import com.irdeto.jumpstart.domain.VideoContent;

public class GetLatestApprovedVersionHelperTest {
	
	@Test
	public void testHasApprovedField(){
		Program program = new Program();
		Assert.assertTrue(GetLatestApprovedVersionHelper.hasApprovedField(program));
		
		Offer offer = new Offer();
		Assert.assertTrue(GetLatestApprovedVersionHelper.hasApprovedField(offer));
		
		VideoContent movie = new VideoContent();
		Assert.assertFalse(GetLatestApprovedVersionHelper.hasApprovedField(movie));
	}

  @Test
  public void testIsApproved() {
	  Program program = new Program();
	  program.setContentApproved(false);
	  program.setMetadataApproved(false);
	  Assert.assertFalse(GetLatestApprovedVersionHelper.isApproved(program));

	  program.setContentApproved(false);
	  program.setMetadataApproved(true);
	  Assert.assertFalse(GetLatestApprovedVersionHelper.isApproved(program));

	  program.setContentApproved(true);
	  program.setMetadataApproved(false);
	  Assert.assertFalse(GetLatestApprovedVersionHelper.isApproved(program));

	  program.setContentApproved(true);
	  program.setMetadataApproved(true);
	  Assert.assertTrue(GetLatestApprovedVersionHelper.isApproved(program));
	  
	  Offer offer = new Offer();
	  offer.setMetadataApproved(false);
	  Assert.assertFalse(GetLatestApprovedVersionHelper.isApproved(offer));

	  offer.setMetadataApproved(true);
	  Assert.assertTrue(GetLatestApprovedVersionHelper.isApproved(offer));

  }
  
}
