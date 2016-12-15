@XmlSchema(
		elementFormDefault = XmlNsForm.QUALIFIED,
		xmlns={
				@XmlNs(prefix="soapenv", namespaceURI="http://schemas.xmlsoap.org/soap/envelope/"),
				@XmlNs(prefix="liv", namespaceURI="http://man.entriq.net/livedrmservice/")
				})  

package com.irdeto.jumpstart.domain.livedrm;
import javax.xml.bind.annotation.*;