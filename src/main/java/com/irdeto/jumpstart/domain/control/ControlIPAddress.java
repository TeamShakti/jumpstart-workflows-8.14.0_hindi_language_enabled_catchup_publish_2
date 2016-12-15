package com.irdeto.jumpstart.domain.control;

import javax.xml.bind.annotation.XmlAttribute;

public class ControlIPAddress {
	private String value;
	private String type;
	private String comment;
	
	@XmlAttribute(name="Value")
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@XmlAttribute(name="Type")
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlAttribute(name="Comment")
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
}
