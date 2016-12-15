package com.irdeto.jumpstart.domain.publish;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.irdeto.jumpstart.domain.Content;

public abstract class AbstractContentWrapper<T extends Content> {
	private String contentId;
	private int publishVersion = 0;
	
	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contentId == null) ? 0 : contentId.hashCode());
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
		AbstractContentWrapper<?> other = (AbstractContentWrapper<?>) obj;
		if (contentId == null) {
			if (other.contentId != null)
				return false;
		} else if (!contentId.equals(other.contentId))
			return false;
		return true;
	}
	public int getPublishVersion() {
		return publishVersion;
	}
	public void setPublishVersion(int publishVersion) {
		this.publishVersion = publishVersion;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

}
