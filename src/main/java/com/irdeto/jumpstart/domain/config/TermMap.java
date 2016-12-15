package com.irdeto.jumpstart.domain.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;


public class TermMap {
	private String termMappingId;
    private String contractName;
    private Integer policyGroupId;
    private String policyType;
    private Integer policyId;
    private Integer duration;
    private List<String> deviceProfileNameList = new ArrayList<>();
    private List<String> contentTypeList = new ArrayList<>();
    private List<Device> deviceList = new ArrayList<>();
    private List<String> policyGroupNameList =  new ArrayList<>();
    private List<String> policyNameList = new ArrayList<>();
    private List<PolicyGroup> policyGroupList = new ArrayList<>();
    private List<Policy> policyList = new ArrayList<>();
    
    @XmlAttribute
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
    @XmlAttribute
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
    @XmlAttribute
	public Integer getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
	@XmlElement(name="DeviceProfile")
	public List<String> getDeviceProfileNameList() {
		return deviceProfileNameList;
	}
	public void setDeviceProfileNameList(List<String> deviceProfileNameList) {
		this.deviceProfileNameList = deviceProfileNameList;
	}
    @XmlAttribute
	public Integer getPolicyGroupId() {
		return policyGroupId;
	}
	public void setPolicyGroupId(Integer policyGroupId) {
		this.policyGroupId = policyGroupId;
	}
	@XmlElement(name="ContentType")
	public List<String> getContentTypeList() {
		return contentTypeList;
	}
	public void setContentTypeList(List<String> contentTypeList) {
		this.contentTypeList = contentTypeList;
	}
	@XmlTransient
	public List<Device> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}
	@XmlTransient
	public String getTermMappingId() {
		return termMappingId;
	}
	public void setTermMappingId(String termMappingId) {
		this.termMappingId = termMappingId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((termMappingId == null) ? 0 : termMappingId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TermMap other = (TermMap) obj;
		if (termMappingId == null) {
			if (other.termMappingId != null)
				return false;
		} else if (!termMappingId.equals(other.termMappingId))
			return false;
		return true;
	}
    @XmlAttribute
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	@XmlElement(name="PolicyGroup")
	public List<String> getPolicyGroupNameList() {
		return policyGroupNameList;
	}
	public void setPolicyGroupNameList(List<String> policyGroupNameList) {
		this.policyGroupNameList = policyGroupNameList;
	}
	
	@XmlElement(name="Policy")
	public List<String> getPolicyNameList() {
		return policyNameList;
	}
	public void setPolicyNameList(List<String> policyNameList) {
		this.policyNameList = policyNameList;
	}
	public List<PolicyGroup> getPolicyGroupList() {
		return policyGroupList;
	}
	public void setPolicyGroupList(List<PolicyGroup> policyGroupList) {
		this.policyGroupList = policyGroupList;
	}
	public List<Policy> getPolicyList() {
		return policyList;
	}
	public void setPolicyList(List<Policy> policyList) {
		this.policyList = policyList;
	}
	
}
