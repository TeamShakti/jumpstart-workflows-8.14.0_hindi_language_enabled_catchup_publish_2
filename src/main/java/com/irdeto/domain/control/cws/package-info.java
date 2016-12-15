@XmlSchema(
		elementFormDefault = XmlNsForm.QUALIFIED,
		xmlns={
				@XmlNs(prefix="SOAP-ENV", namespaceURI="http://schemas.xmlsoap.org/soap/envelope/"),
				@XmlNs(prefix="con", namespaceURI="http://ws.entriq.net/services/console"),
				@XmlNs(prefix="con1", namespaceURI="http://schemas.datacontract.org/2004/07/ConsoleService"),
				@XmlNs(prefix="arr", namespaceURI="http://schemas.microsoft.com/2003/10/Serialization/Arrays")
				})  

package com.irdeto.domain.control.cws;
import javax.xml.bind.annotation.*;