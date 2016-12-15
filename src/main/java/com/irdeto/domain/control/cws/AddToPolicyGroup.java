package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
public class AddToPolicyGroup {

	private CMessage message;

	@XmlElement(name = "cMessage", namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
	public CMessage getMessage() {
		return message;
	}

	public void setMessage(CMessage message) {
		this.message = message;
	}
}
