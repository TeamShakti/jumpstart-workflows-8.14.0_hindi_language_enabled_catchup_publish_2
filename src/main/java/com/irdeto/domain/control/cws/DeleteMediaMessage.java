package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;

public class DeleteMediaMessage extends BaseMessage {
	private String contentUniqueName;
	private String mediaUniqueName;
	
	public DeleteMediaMessage(Credential userCredential, String operation, String contentUniquename, String mediaUniqueName) {
		super(userCredential, operation);
		this.contentUniqueName = contentUniquename;
		this.mediaUniqueName = mediaUniqueName;
	}
	
    public DeleteMediaMessage() {
	}

	@XmlElement(name = "ContentUniqueName", required = true, namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
	public String getContentUniqueName() {
		return contentUniqueName;
	}
	public void setContentUniqueName(String contentUniqueName) {
		this.contentUniqueName = contentUniqueName;
	}
    @XmlElement(name = "MediaUniqueName", required = true, namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
	public String getMediaUniqueName() {
		return mediaUniqueName;
	}
	public void setMediaUniqueName(String mediaUniqueName) {
		this.mediaUniqueName = mediaUniqueName;
	}
	
}
