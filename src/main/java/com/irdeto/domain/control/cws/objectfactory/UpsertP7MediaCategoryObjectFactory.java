package com.irdeto.domain.control.cws.objectfactory;

import java.util.List;

import com.irdeto.domain.control.cws.SoapBody;
import com.irdeto.domain.control.cws.SoapEnvelope;
import com.irdeto.domain.control.cws.UpsertP7MediaCategory;
import com.irdeto.domain.control.cws.UpsertP7MediaCategoryMessage;

public class UpsertP7MediaCategoryObjectFactory extends AbstractCwsObjectFactory {
	public static SoapEnvelope createSoapEnvelope(String operation, List<String> categoryIds, List<String> mediaIds) {
		SoapBody body = new SoapBody();
		UpsertP7MediaCategory upsertP7MediaCategory = new UpsertP7MediaCategory();
		UpsertP7MediaCategoryMessage upsertP7MediaCategoryMessage = new UpsertP7MediaCategoryMessage();
		upsertP7MediaCategoryMessage.setUserCredential(createUserCredential());
		upsertP7MediaCategoryMessage.setOperation(operation);
		upsertP7MediaCategoryMessage.setCategoryIds(categoryIds);
		upsertP7MediaCategoryMessage.setMediaIds(mediaIds);
		upsertP7MediaCategory.setUpsertP7MediaCategoryMessage(upsertP7MediaCategoryMessage );
		body.setBodyElement(upsertP7MediaCategory);
		return new SoapEnvelope(body);
	}

}
