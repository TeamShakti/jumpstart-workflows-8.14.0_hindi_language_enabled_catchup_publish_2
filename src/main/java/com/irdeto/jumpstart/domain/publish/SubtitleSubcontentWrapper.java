package com.irdeto.jumpstart.domain.publish;

/**
 * File Name: SubtitleSubcontentWrapper.java
 * 
 * Description: The Wrapper class used for Subtitle Subcontent
 * 
 * Developed by Tata Elxsi for Irdeto B.V.
 * 
 * Creation Date: 07-Apr-2015
 *
 */
public class SubtitleSubcontentWrapper {

	private String subtitleSubcontentId;
	
	public SubtitleSubcontentWrapper() {
		super();
	}
	
	public SubtitleSubcontentWrapper(String subtitleSubcontentId) {
		super();
		setSubtitleSubcontentId(subtitleSubcontentId);
	}

	public String getSubtitleSubcontentId() {
		return subtitleSubcontentId;
	}

	public void setSubtitleSubcontentId(String subtitleSubcontentId) {
		this.subtitleSubcontentId = subtitleSubcontentId;
	}
	
}

//End SubtitleSubcontentWrapper