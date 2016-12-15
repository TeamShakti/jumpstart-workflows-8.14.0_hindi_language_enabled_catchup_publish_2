package com.irdeto.jumpstart.domain.control;

import javax.xml.bind.annotation.XmlAttribute;

public class ControlKey {
	private String accountId;
	private String contentId;
	private String keyId;
	private String key;
	private Boolean base64;
	private String keyType;
	private String subContentType;
	private String contentKey;
	
	@XmlAttribute(name="AccountId", required=true)
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@XmlAttribute(name="ContentId", required=true)
	public String getContentId() {
		return contentId;
	}
	
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	
	@XmlAttribute(name="KeyId", required=true)
	public String getKeyId() {
		return keyId;
	}
	
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	
	@XmlAttribute(name="Key", required=true)
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	@XmlAttribute(name="base64", required=false)
	public Boolean getBase64() {
		return base64;
	}
	
	public void setBase64(Boolean base64) {
		this.base64 = base64;
	}
	
	@XmlAttribute(name="KeyType", required=false)
	public String getKeyType() {
		return keyType;
	}
	
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	
	@XmlAttribute(name="SubContentType", required=false)
	public String getSubContentType() {
		return subContentType;
	}
	
	public void setSubContentType(String subContentType) {
		this.subContentType = subContentType;
	}
	
	@XmlAttribute(name="ContentKey", required=false)
	public String getContentKey() {
		return contentKey;
	}
	
	public void setContentKey(String contentKey) {
		this.contentKey = contentKey;
	}
}
