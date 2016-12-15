package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlType(propOrder = {"body"})
public class SoapEnvelope {
	private SoapBody body;

	public SoapEnvelope(){
	}

	public SoapEnvelope(SoapBody body) {
		super();
		this.body = body;
	}
	
    @XmlElement(name = "Body", required = true, namespace = "http://schemas.xmlsoap.org/soap/envelope/")
	public SoapBody getBody() {
		return body;
	}
	public void setBody(SoapBody body) {
		this.body = body;
	}
}
