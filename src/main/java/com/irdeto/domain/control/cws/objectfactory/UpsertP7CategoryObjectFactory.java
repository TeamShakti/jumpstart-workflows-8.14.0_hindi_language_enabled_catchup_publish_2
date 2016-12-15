package com.irdeto.domain.control.cws.objectfactory;

import com.irdeto.domain.control.cws.P7Category;
import com.irdeto.domain.control.cws.SoapBody;
import com.irdeto.domain.control.cws.SoapEnvelope;
import com.irdeto.domain.control.cws.UpsertP7Category;
import com.irdeto.domain.control.cws.UpsertP7CategoryMessage;

public class UpsertP7CategoryObjectFactory extends AbstractCwsObjectFactory {
	public static SoapEnvelope createSoapEnvelope(String operation, String categoryId, String parentCategoryId, String name, String policyId, String description) {
		P7Category p7Category = new P7Category(categoryId, parentCategoryId, name, policyId, description);
		SoapBody body = new SoapBody();
		UpsertP7Category upsertP7Category = new UpsertP7Category();
		UpsertP7CategoryMessage upsertP7CategoryMessage = new UpsertP7CategoryMessage();
		upsertP7CategoryMessage.setUserCredential(createUserCredential());
		upsertP7CategoryMessage.setOperation(operation);
		upsertP7CategoryMessage.setP7Category(p7Category);
		upsertP7Category.setUpsertP7CategoryMessage(upsertP7CategoryMessage );
		body.setBodyElement(upsertP7Category);
		return new SoapEnvelope(body);
	}

	public static SoapEnvelope createSoapEnvelope(String operation, String categoryId) {
		return createSoapEnvelope(operation, categoryId, null, null, null, null);
	}

	public static SoapEnvelope createSoapEnvelope() {
		return createSoapEnvelope(null, null);
	}
}
