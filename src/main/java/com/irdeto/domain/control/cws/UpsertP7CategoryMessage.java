package com.irdeto.domain.control.cws;

import javax.xml.bind.annotation.XmlElement;

public class UpsertP7CategoryMessage extends BaseMessage {
	private P7Category p7Category;
	
	public UpsertP7CategoryMessage(){
	}

	public UpsertP7CategoryMessage(Credential userCredential, String Operation, P7Category p7Category) {
		super(userCredential, Operation);
		this.p7Category = p7Category;
	}

	@XmlElement(name = "Category", required = true, namespace = "http://schemas.datacontract.org/2004/07/ConsoleService")
	public P7Category getP7Category() {
		return p7Category;
	}

	public void setP7Category(P7Category p7Category) {
		this.p7Category = p7Category;
	}

}
