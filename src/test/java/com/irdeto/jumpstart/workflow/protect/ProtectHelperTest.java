package com.irdeto.jumpstart.workflow.protect;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ProtectHelperTest {
  @Test
  public void helperMethodTest() {
	  String sourcePath = "https://s3-us-west-2.amazonaws.com/jumpstart-protected/HLS/movie-1-hvgm1yd3-m2-ActiveCloak2GoDRM/movie-1-hvgm1yd3-m2-1500.mp4";
	  String vmsourcePath = "\\\\ShareURL\\jumpstart-protected\\HLS\\movie-1-hvgm1yd3-m2-ActiveCloak2GoDRM\\movie-1-hvgm1yd3-m2-1500.mp4";
	  String filePattern = "(.)*-ActiveCloak2GoDRM[/\\\\](.)*-1500\\.mp4$";
	  Assert.assertTrue(sourcePath.matches(filePattern ));
	  Assert.assertTrue(vmsourcePath.matches(filePattern ));
  }
}
