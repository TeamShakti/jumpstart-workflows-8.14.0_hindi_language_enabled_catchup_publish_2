package com.irdeto.domain.control.cws;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.testng.annotations.Test;

public class ConsoleWebServiceTest {
  @Test
  public void upsertCategoryTest() {
      try{
		  UpsertP7Category upsertP7Category = new UpsertP7Category();
		  UpsertP7CategoryMessage upsertP7CategoryMessage = new UpsertP7CategoryMessage();
		  Credential userCredential = new Credential();
		  userCredential.setAccountId("1");
		  userCredential.setSessionId("test");
		  userCredential.setUserName("test");
		  userCredential.setPassword("pw");
		  upsertP7CategoryMessage.setUserCredential(userCredential );
		  upsertP7CategoryMessage.setOperation("Insert");
		  P7Category p7Category = new P7Category();
		  p7Category.setCategoryId("2");
		  p7Category.setParentCategoryId("0");
		  p7Category.setDescription("test");
		  p7Category.setName("category");
		  p7Category.setPolicyId("3");
		  upsertP7CategoryMessage.setP7Category(p7Category);
		  upsertP7Category.setUpsertP7CategoryMessage(upsertP7CategoryMessage );
		
		  SoapEnvelope envelope = new SoapEnvelope();
		  SoapBody body = new SoapBody();
		  body.setBodyElement(upsertP7Category);
		  envelope.setBody(body);

		  ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		  Marshaller marshaller = JAXBContext.newInstance(SoapEnvelope.class).createMarshaller();
		  marshaller.marshal(envelope, outputStream);
		  outputStream.writeTo(System.out);
		  System.out.println("\nSOAP msg created");
       }catch(Exception e){
       }
  }
}
