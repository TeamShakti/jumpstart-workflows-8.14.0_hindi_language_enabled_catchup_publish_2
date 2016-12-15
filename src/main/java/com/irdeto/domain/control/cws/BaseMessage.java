package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"userCredential", "operation"})
public class BaseMessage {
	private Credential userCredential;
	private String operation;
    
	public BaseMessage(){
	}
	public BaseMessage(Credential userCredential, String operation) {
		super();
		this.userCredential = userCredential;
		this.operation = operation;
	}
    @XmlElement(name = "UserCredential", required = true, namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
	public Credential getUserCredential() {
		return userCredential;
	}
	public void setUserCredential(Credential userCredential) {
		this.userCredential = userCredential;
	}
    @XmlElement(name = "Operation", required = true, namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}

}
