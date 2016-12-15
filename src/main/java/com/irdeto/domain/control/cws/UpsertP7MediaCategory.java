package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;

public class UpsertP7MediaCategory {
	private UpsertP7MediaCategoryMessage upsertP7MediaCategoryMessage;

	public UpsertP7MediaCategory(UpsertP7MediaCategoryMessage upsertP7MediaCategoryMessage) {
		super();
		this.upsertP7MediaCategoryMessage = upsertP7MediaCategoryMessage;
	}

	public UpsertP7MediaCategory() {
	}

	@XmlElement(name = "cMessage", required = true, namespace = "http://ws.entriq.net/services/console")
	public UpsertP7MediaCategoryMessage getUpsertP7MediaCategoryMessage() {
		return upsertP7MediaCategoryMessage;
	}

	public void setUpsertP7MediaCategoryMessage(
			UpsertP7MediaCategoryMessage upsertP7MediaCategoryMessage) {
		this.upsertP7MediaCategoryMessage = upsertP7MediaCategoryMessage;
	}


}
