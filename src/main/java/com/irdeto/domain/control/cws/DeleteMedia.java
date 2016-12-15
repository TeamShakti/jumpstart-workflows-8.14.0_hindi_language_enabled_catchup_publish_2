package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;

public class DeleteMedia {
	private DeleteMediaMessage deleteMediaMessage;

	public DeleteMedia(DeleteMediaMessage deleteMediaMessage) {
		super();
		this.deleteMediaMessage = deleteMediaMessage;
	}

    public DeleteMedia() {
	}

	@XmlElement(name = "message", required = true, namespace = "http://ws.entriq.net/services/console")
	public DeleteMediaMessage getDeleteMediaMessage() {
		return deleteMediaMessage;
	}

	public void setDeleteMediaMessage(DeleteMediaMessage deleteMediaMessage) {
		this.deleteMediaMessage = deleteMediaMessage;
	}

}
