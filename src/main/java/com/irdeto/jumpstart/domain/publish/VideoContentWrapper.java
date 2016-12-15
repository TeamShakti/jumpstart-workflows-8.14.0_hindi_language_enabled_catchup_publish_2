package com.irdeto.jumpstart.domain.publish;

import java.util.ArrayList;
import java.util.List;

import com.irdeto.jumpstart.domain.VideoContent;
import com.irdeto.jumpstart.domain.config.Device;
import com.irdeto.jumpstart.domain.config.TermMap;

public class VideoContentWrapper extends AbstractContentWrapper<VideoContent> {
	private List<VideoSubcontentWrapper> videoSubcontentWrapperList = new ArrayList<>();
	private List<VideoSubcontentWrapper> unpublishedVideoSubcontentWrapperList = new ArrayList<>();
	private List<TermMap> termMapList = new ArrayList<>();
	private List<TermWrapper> termWrapperList = new ArrayList<>();
	private List<Device> deviceList = new ArrayList<>();
	private List<SubtitleContentWrapper> subtitleContentWrapperList  = new ArrayList<>();;
	private List<SubtitleContentWrapper> unpublishedSubtitleContentWrapperList = new ArrayList<>();
	
	public VideoContentWrapper() {
		super();
	}
	
	public VideoContentWrapper(String videoContentId) {
		super();
		setContentId(videoContentId);
	}
	
	public List<VideoSubcontentWrapper> getVideoSubcontentWrapperList() {
		return videoSubcontentWrapperList;
	}
	public void setVideoSubcontentWrapperList(List<VideoSubcontentWrapper> videoSubcontentWrapperList) {
		this.videoSubcontentWrapperList = videoSubcontentWrapperList;
	}
	public List<VideoSubcontentWrapper> getUnpublishedVideoSubcontentWrapperList() {
		return unpublishedVideoSubcontentWrapperList;
	}
	public void setUnpublishedVideoSubcontentWrapperList(
			List<VideoSubcontentWrapper> unpublishedVideoSubcontentWrapperList) {
		this.unpublishedVideoSubcontentWrapperList = unpublishedVideoSubcontentWrapperList;
	}
	public List<Device> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}
	public List<TermMap> getTermMapList() {
		return termMapList;
	}
	public void setTermMapList(List<TermMap> termMapList) {
		this.termMapList = termMapList;
	}
	public List<TermWrapper> getTermWrapperList() {
		return termWrapperList;
	}
	public void setTermWrapperList(List<TermWrapper> termWrapperList) {
		this.termWrapperList = termWrapperList;
	}

	public List<SubtitleContentWrapper> getSubtitleContentWrapperList() {
		return subtitleContentWrapperList;
	}

	public void setSubtitleContentWrapperList(
			List<SubtitleContentWrapper> subtitleContentWrapperList) {
		this.subtitleContentWrapperList = subtitleContentWrapperList;
	}

	public List<SubtitleContentWrapper> getUnpublishedSubtitleContentWrapperList() {
		return unpublishedSubtitleContentWrapperList;
	}

	public void setUnpublishedSubtitleContentWrapperList(
			List<SubtitleContentWrapper> unpublishedSubtitleContentWrapperList) {
		this.unpublishedSubtitleContentWrapperList = unpublishedSubtitleContentWrapperList;
	}
	
}
