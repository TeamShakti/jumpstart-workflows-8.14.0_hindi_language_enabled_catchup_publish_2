package com.irdeto.jumpstart.workflow;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DateHelperTest {

	@Test
	public void testConvertCablelabsDurationToSeconds() {
		int seconds = DateHelper.convertCablelabsDurationToSeconds("P4563Y2M13DT12H8M10S");
		Assert.assertEquals(seconds, 1166890);
		seconds = DateHelper.convertCablelabsDurationToSeconds("8M10S");
		Assert.assertEquals(seconds, 0);
		seconds = DateHelper.convertCablelabsDurationToSeconds("PT8M10S");
		Assert.assertEquals(seconds, 490);
		seconds = DateHelper.convertCablelabsDurationToSeconds("P10DT8M10S");
		Assert.assertEquals(seconds, 864490);
	}
	
	@Test
	public void testGetGregorianXMLDate() throws Exception {
		Assert.assertNotNull(DateHelper.convertStringToXmlGregorian("20130920070000 +0200", "yyyyMMddHHmmss Z"));
	}
}
