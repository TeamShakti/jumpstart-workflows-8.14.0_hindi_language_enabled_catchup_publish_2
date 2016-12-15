package com.irdeto.jumpstart.domain.control;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlRootElement(name="Batch")
public class ControlBatch {
	private List<ControlMedia> mediaList = new ArrayList<>();
	private List<ControlContent> contentList = new ArrayList<>();
	private List<ControlSubContent> subContentList = new ArrayList<>();
	private List<ControlKey> keyList = new ArrayList<>();
	
	@XmlElement(name="Media", required=false)
	public List<ControlMedia> getMediaList() {
		return mediaList;
	}
	public void setMediaList(List<ControlMedia> mediaList) {
		this.mediaList = mediaList;
	}
	@XmlElement(name="Content", required=false)
	public List<ControlContent> getContentList() {
		return contentList;
	}
	public void setContentList(List<ControlContent> contentList) {
		this.contentList = contentList;
	}
	@XmlElement(name="SubContent", required=false)
	public List<ControlSubContent> getSubContentList() {
		return subContentList;
	}
	public void setSubContentList(List<ControlSubContent> subContentList) {
		this.subContentList = subContentList;
	}
	@XmlElement(name="Key", required=false)
	public List<ControlKey> getKeyList() {
		return keyList;
	}
	public void setKeyList(List<ControlKey> keyList) {
		this.keyList = keyList;
	}
	
	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
	}
}
