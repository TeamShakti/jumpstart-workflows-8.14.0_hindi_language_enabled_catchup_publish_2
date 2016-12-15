package com.irdeto.jumpstart.domain.livedrm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement
public class GenerateKeysLiveDrmSoapBody extends AbstractLiveDrmSoapBody {
	private GenerateKeysLiveDrmSoapBodyDetails details;
	
	@XmlElement(name = "GenerateKeys", namespace = "http://man.entriq.net/livedrmservice/")
	public GenerateKeysLiveDrmSoapBodyDetails getDetails() {
		return details;
	}

	public void setDetails(GenerateKeysLiveDrmSoapBodyDetails details) {
		this.details = details;
	}

	@Override
	@XmlTransient
	@JsonIgnore
	public String getCommand() {
		return "GenerateKeys";
	}
}
