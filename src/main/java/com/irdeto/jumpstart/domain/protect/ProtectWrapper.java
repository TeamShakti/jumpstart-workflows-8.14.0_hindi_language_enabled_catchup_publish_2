package com.irdeto.jumpstart.domain.protect;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.config.Protect;
import com.irdeto.jumpstart.domain.config.Transcode;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class ProtectWrapper {
	private List<String> sourceFileList = new ArrayList<>();
	private Integer policyGroupId;
	private Transcode transcode;
	private List<Protect> protectGroup = new ArrayList<>();
	public List<String> getSourceFileList() {
		return sourceFileList;
	}
	public void setSourceFileList(List<String> sourceFileList) {
		this.sourceFileList = sourceFileList;
	}
	public Integer getPolicyGroupId() {
		return policyGroupId;
	}
	public void setPolicyGroupId(Integer policyGroupId) {
		this.policyGroupId = policyGroupId;
	}
	public Transcode getTranscode() {
		return transcode;
	}
	public void setTranscode(
			Transcode transcode) {
		this.transcode = transcode;
	}
	public List<Protect> getProtectGroup() {
		return protectGroup;
	}
	public void setProtectGroup(List<Protect> protectGroup) {
		this.protectGroup = protectGroup;
	}
	
	@JsonIgnore
	public Protect getRepresentativeProtect() {
		if (getProtectGroup() == null || getProtectGroup().isEmpty()) {
			return null;
		} else {
			return getProtectGroup().get(0);
		}
	}
	@JsonIgnore
	public String getProtectKey() {
		return WorkflowHelper.getProtectKey(getRepresentativeProtect());
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((policyGroupId == null) ? 0 : policyGroupId.hashCode());
		result = prime
				* result
				+ ((getProtectKey() == null) ? 0 : getProtectKey().hashCode());
		result = prime * result
				+ ((getTranscode() == null) ? 0 : getTranscode().hashCode());
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
		ProtectWrapper other = (ProtectWrapper) obj;

		if (policyGroupId == null) {
			if (other.policyGroupId != null)
				return false;
		} else if (!policyGroupId.equals(other.policyGroupId))
			return false;

		if (getTranscode() == null) {
			if (other.getTranscode() != null)
				return false;
		} else if (!getTranscode().equals(other.getTranscode()))
			return false;

		if (getProtectKey() == null) {
			if (other.getProtectKey() != null)
				return false;
		} else if (!getProtectKey().equals(other.getProtectKey()))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
	}
}
