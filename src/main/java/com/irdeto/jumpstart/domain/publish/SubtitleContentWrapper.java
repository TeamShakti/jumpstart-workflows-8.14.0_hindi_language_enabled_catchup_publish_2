package com.irdeto.jumpstart.domain.publish;

import java.util.ArrayList;
import java.util.List;

import com.irdeto.jumpstart.domain.SubtitleContent;

/**
 * File Name: SubtitleContentWrapper.java
 * 
 * Description: The Wrapper class used for Subtitle content
 * 
 * Developed by Tata Elxsi for Irdeto B.V.
 * 
 * Creation Date: 07-Apr-2015
 *
 */
public class SubtitleContentWrapper  extends AbstractContentWrapper<SubtitleContent> {
	
	private List<String> subtitleSubcontentIdList = new ArrayList<>();
	private List<String> unpublishedSubtitleSubcontentIdList = new ArrayList<>();
	
	public SubtitleContentWrapper() {
		super();
	}
	
	public SubtitleContentWrapper(String subtitleContentId) {
		super();
		setContentId(subtitleContentId);
	}

	public List<String> getSubtitleSubcontentIdList() {
		return subtitleSubcontentIdList;
	}

	public void setSubtitleSubcontentIdList(List<String> subtitleSubcontentIdList) {
		this.subtitleSubcontentIdList = subtitleSubcontentIdList;
	}

	public List<String> getUnpublishedSubtitleSubcontentIdList() {
		return unpublishedSubtitleSubcontentIdList;
	}

	public void setUnpublishedSubtitleSubcontentIdList(
			List<String> unpublishedSubtitleSubcontentIdList) {
		this.unpublishedSubtitleSubcontentIdList = unpublishedSubtitleSubcontentIdList;
	}

}

//End SubtitleContentWrapper
