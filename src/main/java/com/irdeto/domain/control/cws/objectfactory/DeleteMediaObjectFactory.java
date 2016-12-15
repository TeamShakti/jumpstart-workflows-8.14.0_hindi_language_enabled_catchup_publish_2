package com.irdeto.domain.control.cws.objectfactory;

import com.irdeto.domain.control.cws.DeleteMedia;
import com.irdeto.domain.control.cws.DeleteMediaMessage;
import com.irdeto.domain.control.cws.SoapBody;
import com.irdeto.domain.control.cws.SoapEnvelope;

public class DeleteMediaObjectFactory extends AbstractCwsObjectFactory {
	public static SoapEnvelope createSoapEnvelope(String contentUniqueName, String mediaUniqueName) {
		SoapBody body = new SoapBody();
		DeleteMedia deleteMedia = new DeleteMedia();
		DeleteMediaMessage deleteMediaMessage = new DeleteMediaMessage();
		deleteMediaMessage.setUserCredential(createUserCredential());
		deleteMediaMessage.setContentUniqueName(contentUniqueName);
		deleteMediaMessage.setMediaUniqueName(mediaUniqueName);
		deleteMedia.setDeleteMediaMessage(deleteMediaMessage);
		body.setBodyElement(deleteMedia);
		return new SoapEnvelope(body);	
	}
}
