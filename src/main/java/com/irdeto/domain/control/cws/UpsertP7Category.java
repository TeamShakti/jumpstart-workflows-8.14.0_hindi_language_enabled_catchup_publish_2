package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;

public class UpsertP7Category {
	private UpsertP7CategoryMessage upsertP7CategoryMessage;

	public UpsertP7Category() {
	}

	public UpsertP7Category(UpsertP7CategoryMessage upsertP7CategoryMessage) {
		super();
		this.upsertP7CategoryMessage = upsertP7CategoryMessage;
	}

	@XmlElement(name = "cMessage", required = true, namespace = "http://ws.entriq.net/services/console")
	public UpsertP7CategoryMessage getUpsertP7CategoryMessage() {
		return upsertP7CategoryMessage;
	}

	public void setUpsertP7CategoryMessage(UpsertP7CategoryMessage upsertP7CategoryMessage) {
		this.upsertP7CategoryMessage = upsertP7CategoryMessage;
	}

}
