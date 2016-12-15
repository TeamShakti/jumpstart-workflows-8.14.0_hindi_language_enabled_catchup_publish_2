package com.irdeto.jumpstart.manager;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.irdeto.jumpstart.domain.livedrm.GenerateKeysLiveDrmResponseProcessor;

public class ControlLiveDrmManagerTest {
  private static final Object CONTENT_KEY = "ContentKey";
  private static final Object DRM_VALUES_KEY = "DrmValues";

@SuppressWarnings("unchecked")
@Test
  public void processLiveDrmSoapResponseTest() throws Exception {
	  GenerateKeysLiveDrmResponseProcessor responseProcessor = new GenerateKeysLiveDrmResponseProcessor();
	  Map<String, Object> validResults = responseProcessor.processResponse("<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><GenerateKeysResponse xmlns=\"http://man.entriq.net/livedrmservice/\"><GenerateKeysResult>&lt;?xml version=\"1.0\" encoding=\"UTF-8\" ?&gt;&lt;Key ContentKey=\"BHTqID/b41TDdExyhCkaqA==\" Version=\"2\" AccountId=\"multiscreen\" ContentId=\"1_live\" KeyId=\"321328ae-ee74-46c0-9e23-7275797a514d\" Key=\"eR+SlB0yQV9txNjGGjC26BFa3/OaJ5tQ1jp7ZQTZtZeTBUcTCbDfaQ==\" SubContentType=\"Default\" KeyType=\"PlayReady\"/&gt;</GenerateKeysResult></GenerateKeysResponse></soap:Body></soap:Envelope>");
	  Assert.assertEquals((String)((List<Map<String, String>>)validResults.get(DRM_VALUES_KEY)).get(0).get(CONTENT_KEY), "BHTqID/b41TDdExyhCkaqA==");
	  Exception exception = null;
	  try {
		  responseProcessor.processResponse("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><soap:Fault><faultcode>soap:Server</faultcode><faultstring>Server was unable to process request. ---&gt; Failed to Register Keys:&lt;?xml version=\"1.0\" encoding=\"utf-8\"?&gt;&lt;ApiResult xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" Success=\"false\" xmlns=\"http://entriq.com/control/2008-10-10\"&gt;&lt;Error ErrorCode=\"1105\"&gt;Cannot register key because content is not found: accountid=[multiscreen] contentid=[fdsafdasad]&lt;/Error&gt;&lt;/ApiResult&gt;</faultstring><detail /></soap:Fault></soap:Body></soap:Envelope>");
	  } catch (Exception e) {
		  exception = e;
	  }
	  Assert.assertNotNull(exception);
  }
}
