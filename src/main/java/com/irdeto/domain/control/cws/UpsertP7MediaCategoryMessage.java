package com.irdeto.domain.control.cws;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class UpsertP7MediaCategoryMessage extends BaseMessage {
	private List<String> categoryIds;
	private List<String> mediaIds;

	public UpsertP7MediaCategoryMessage(Credential userCredential, String operation, List<String> categoryIds, List<String> mediaIds) {
		super(userCredential, operation);
		this.categoryIds = categoryIds;
		this.mediaIds = mediaIds;
	}

    public UpsertP7MediaCategoryMessage() {
	}

	@XmlElementWrapper(name = "CategoryIds", required = true, namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
	@XmlElement(name="long", required = true, namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays")
	public List<String> getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(List<String> categoryIds) {
		this.categoryIds = categoryIds;
	}
    @XmlElementWrapper(name = "MediaIds", required = true, namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
	@XmlElement(name="string", required = true, namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays")
	public List<String> getMediaIds() {
		return mediaIds;
	}
	public void setMediaIds(List<String> mediaIds) {
		this.mediaIds = mediaIds;
	}


}
