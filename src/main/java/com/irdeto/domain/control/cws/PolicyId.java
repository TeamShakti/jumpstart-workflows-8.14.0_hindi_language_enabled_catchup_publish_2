package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
public class PolicyId {

	private Integer longVal;

	@XmlElement(name = "long", namespace="http://schemas.microsoft.com/2003/10/Serialization/Arrays")
	public Integer getLongVal() {
		return longVal;
	}

	public void setLongVal(Integer longVal) {
		this.longVal = longVal;
	}
	
	
}
