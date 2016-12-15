package com.irdeto.jumpstart.domain.transcode;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.config.Transcode;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class TranscodeWrapper {
	private List<Transcode> transcodeGroup = new ArrayList<>();

	public List<Transcode> getTranscodeGroup() {
		return transcodeGroup;
	}

	public void setTranscodeGroup(List<Transcode> transcodeGroup) {
		this.transcodeGroup = transcodeGroup;
	}

	@JsonIgnore
	public Transcode getRepresentativeTranscode() {
		if (getTranscodeGroup() == null || getTranscodeGroup().isEmpty()) {
			return null;
		} else {
			return getTranscodeGroup().get(0);
		}
	}
	@JsonIgnore
	public String getTranscodeKey() {
		return WorkflowHelper.getTranscodeKey(getRepresentativeTranscode());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((getTranscodeKey() == null) ? 0 : getTranscodeKey()
						.hashCode());
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
		TranscodeWrapper other = (TranscodeWrapper) obj;
		if (getTranscodeKey() == null) {
			if (other.getTranscodeKey() != null)
				return false;
		} else if (!getTranscodeKey().equals(other.getTranscodeKey()))
			return false;
		return true;
	}

}
