package com.irdeto.jumpstart.workflow.protect;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ProtectUSHelperTest {
	public void testGetSignedFileUrl() {
		String signedFileUrl = ProtectUSHelper.getSignedFileUrl(1412957325, "jumpstart-transcoded", "test.ismv", "XMTzA2Q0D71YOu0yJ14WPrmLTxCKGaoQ1VgIzFaO", "AKIAJNOLG6QPKWSI6F3A");
		Assert.assertEquals(signedFileUrl, "http://jumpstart-transcoded.s3.amazonaws.com/test.ismv?AWSAccessKeyId=AKIAJNOLG6QPKWSI6F3A&Expires=1412957325&Signature=qxv3iGjSKzNrwy%2BOqhq%2BZ3tP0J4%3D");
	}
	
	public void testGetCommand() {
		//		KEY_ID = 069296bfe9b9614987ad0adb6d917d9e
		//		CONTENT_ENCRYPTION_KEY = 8d98ebf12e6e377d16c93586b40bc013
		//		LICENSE_ACQUISITION_URL = http://multiscreen.dev.ott.irdeto.com/playready/rightsmanager.asmx?CrmId=multiscreen&AccountId=multiscreen&ContentId=1_live
		//		S3_FILES = movie-1-huy6gllp-m1_1500.mp4 movie-1-huy6gllp-m1_2000.mp4
		String command = ProtectUSHelper.getCommand("root@localhost", "/opt/data/mp4split-remote.sh");
		Assert.assertTrue(command.contains("ssh"));
	}
}
