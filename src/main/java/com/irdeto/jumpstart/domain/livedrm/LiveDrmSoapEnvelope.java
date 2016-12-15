package com.irdeto.jumpstart.domain.livedrm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlType(propOrder = {"header", "body"})
public class LiveDrmSoapEnvelope {
	private LiveDrmSoapHeader header;
	private AbstractLiveDrmSoapBody body;

	public LiveDrmSoapEnvelope(){
	}

	public LiveDrmSoapEnvelope(LiveDrmSoapHeader header, AbstractLiveDrmSoapBody body) {
		super();
		this.setHeader(header);
		this.body = body;
	}
	
	@XmlElement(name = "Header", required = true, namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    public LiveDrmSoapHeader getHeader() {
		return header;
	}

	public void setHeader(LiveDrmSoapHeader header) {
		this.header = header;
	}

	@XmlElement(name = "Body", required = true, namespace = "http://schemas.xmlsoap.org/soap/envelope/")
	public AbstractLiveDrmSoapBody getBody() {
		return body;
	}
	public void setBody(AbstractLiveDrmSoapBody body) {
		this.body = body;
	}
}
