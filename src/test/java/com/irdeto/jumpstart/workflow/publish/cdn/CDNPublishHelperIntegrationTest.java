package com.irdeto.jumpstart.workflow.publish.cdn;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.BaseProtection.ProtectionType;
import com.irdeto.jumpstart.domain.SourceVideoSub;
import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.workflow.BaseSpringTestWithSetup;

@Test
public class CDNPublishHelperIntegrationTest extends BaseSpringTestWithSetup {
	public void testGetDestinationCopyCDN() {
		SourceVideoSub sourceVideoSubcontent = new SourceVideoSub();
		sourceVideoSubcontent.setSourcePath("this/that/other.txt");
		Protect protect = new Protect();
		protect.setProtectionType(ProtectionType.PLAY_READY_US.toString());
		String target = CdnPublishHelper.getDestinationCopyCDN(sourceVideoSubcontent, 1, protect);
		Assert.assertNotNull(target);
	}

	public void testSourceListFoldersForPublish() {
		String sourcePath = "this/that/other.txt";
		Protect protect = new Protect();
		protect.setProtectionType(ProtectionType.ACTIVE_CLOAK_2_GO_DRM.toString());
		List<String> sourceList = CdnPublishHelper.getSourceListFoldersForPublish(sourcePath, protect);
		Assert.assertNotNull(sourceList);
	}

}
