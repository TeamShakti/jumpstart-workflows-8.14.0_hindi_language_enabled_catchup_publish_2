package com.irdeto.jumpstart.domain.control;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ApiResult")
public class ControlApiResult {
	private String success;
	private ControlError error;
	
	@XmlAttribute(name="Success", required=true)
	public String getSuccess() {
		return success;
	}
	
	public void setSuccess(String success) {
		this.success = success;
	}
	
	@XmlElement(name="Error", required=false)
	public ControlError getError() {
		return error;
	}
	
	public void setError(ControlError error) {
		this.error = error;
	}
}
