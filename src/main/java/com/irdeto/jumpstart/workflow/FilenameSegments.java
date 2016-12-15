package com.irdeto.jumpstart.workflow;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

public class FilenameSegments {
	private static final String SEPARATOR_FILENAME_PART = "-";
	private String contentType;
	private String contentId;
	private String dateHash;
	private String sourceFileVersion;

	public FilenameSegments() {
		super();
	}
	
	public FilenameSegments(String filename) {
		super();
		String filenameWithoutPathAndExtension = FileHelper.getFilenameWithoutPathAndExt(filename);
		if (StringUtils.isNotBlank(filenameWithoutPathAndExtension)) { 
			String[] segments = filenameWithoutPathAndExtension.split(SEPARATOR_FILENAME_PART);
			if (segments.length >= 4) {
				this.contentType = segments[0];
				this.contentId = segments[1];
				this.dateHash = segments[2];
				this.sourceFileVersion = segments[3];
			}
		}
	}
	
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getDateHash() {
		return dateHash;
	}
	public void setDateHash(String dateHash) {
		this.dateHash = dateHash;
	}
	public String getSourceFileVersion() {
		return sourceFileVersion;
	}
	public void setSourceFileVersion(String sourceFileVersion) {
		this.sourceFileVersion = sourceFileVersion;
	}
	
    @Override
    public String toString() {
        return contentType + SEPARATOR_FILENAME_PART + contentId + SEPARATOR_FILENAME_PART + dateHash + SEPARATOR_FILENAME_PART + sourceFileVersion;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
	@Override
	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}
}
