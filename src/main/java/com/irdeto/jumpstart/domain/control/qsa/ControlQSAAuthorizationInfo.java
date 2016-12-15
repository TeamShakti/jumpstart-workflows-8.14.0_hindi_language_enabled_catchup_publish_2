package com.irdeto.jumpstart.domain.control.qsa;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "AuthorizationInfo")
public class ControlQSAAuthorizationInfo {
	private List<ControlQSACategory> categoryList = new ArrayList<>();
	private ControlQSAItem item;
	private String version;
	private String authorized;
	private String errorCode;
	private String errorCodes;
	private String errorMessage;
	private ControlQSAMedia media;
	private ControlQSAPolicy policy;
	private ControlQSASubPolicy subPolicy;
	private List<ControlQSASubPolicy> subPolicyList = new ArrayList<>();
	private ControlQSASession session;

	@XmlElementWrapper(name = "CategoryList")
	@XmlElement(name = "Category")
	public List<ControlQSACategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<ControlQSACategory> categoryList) {
		this.categoryList = categoryList;
	}

	@XmlAttribute(name = "Version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@XmlAttribute(name = "Authorized")
	public String getAuthorized() {
		return authorized;
	}

	public void setAuthorized(String authorized) {
		this.authorized = authorized;
	}

	@XmlAttribute(name = "ErrorCode")
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@XmlAttribute(name = "ErrorCodes")
	public String getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(String errorCodes) {
		this.errorCodes = errorCodes;
	}

	@XmlAttribute(name = "ErrorMessage")
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@XmlElement(name = "Item")
	public ControlQSAItem getItem() {
		return item;
	}

	public void setItem(ControlQSAItem item) {
		this.item = item;
	}
	
	@XmlElement(name = "Media")
	public ControlQSAMedia getMedia() {
		return media;
	}
	public void setMedia(ControlQSAMedia media) {
		this.media = media;
	}
	@XmlElement(name = "Policy")
	public ControlQSAPolicy getPolicy() {
		return policy;
	}
	public void setPolicy(ControlQSAPolicy policy) {
		this.policy = policy;
	}
	@XmlElement(name = "SubPolicy")
	public ControlQSASubPolicy getSubPolicy() {
		return subPolicy;
	}
	public void setSubPolicy(ControlQSASubPolicy subPolicy) {
		this.subPolicy = subPolicy;
	}
	@XmlElementWrapper(name = "SubPolicyList")
	@XmlElement(name = "SubPolicy")
	public List<ControlQSASubPolicy> getSubPolicyList() {
		return subPolicyList;
	}
	public void setSubPolicyList(List<ControlQSASubPolicy> subPolicyList) {
		this.subPolicyList = subPolicyList;
	}

	@XmlElement(name = "Session")
	public ControlQSASession getSession() {
		return session;
	}

	public void setSession(ControlQSASession session) {
		this.session = session;
	}
	
}
