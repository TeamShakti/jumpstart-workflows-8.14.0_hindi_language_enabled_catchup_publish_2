package com.irdeto.jumpstart.domain.livedrm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://man.entriq.net/livedrmservice/")
public class LiveDrmSoapHeader {
	private LiveDrmServiceHeader liveDrmServiceHeader;

	public LiveDrmSoapHeader() {
	}

	public LiveDrmSoapHeader(LiveDrmServiceHeader liveDrmServiceHeader) {
		super();
		this.liveDrmServiceHeader = liveDrmServiceHeader;
	}

	@XmlElement(name = "LiveDrmServiceHeader", required = true)
	public LiveDrmServiceHeader getLiveDrmServiceHeader() {
		return liveDrmServiceHeader;
	}

	public void setLiveDrmServiceHeader(LiveDrmServiceHeader liveDrmServiceHeader) {
		this.liveDrmServiceHeader = liveDrmServiceHeader;
	}

}
