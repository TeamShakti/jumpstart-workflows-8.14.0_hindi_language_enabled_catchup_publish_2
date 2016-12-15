package com.irdeto.jumpstart.domain.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Profile")
public class Profile {
	private List<Device> deviceList = new ArrayList<>();
	private List<Encode> encodeList = new ArrayList<>();
	private List<Transcode> transcodeList = new ArrayList<>();
	private List<Protect> protectList = new ArrayList<>();
	private List<TermMap> termMapList = new ArrayList<>();

	@XmlElement(name = "DeviceProfile")
	public List<Device> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}

	@XmlElement(name = "EncodeProfile")
	public List<Encode> getEncodeList() {
		return encodeList;
	}

	public void setEncodeList(List<Encode> encodeProfileList) {
		this.encodeList = encodeProfileList;
	}

	@XmlElement(name = "TranscodeProfile")
	public List<Transcode> getTranscodeList() {
		return transcodeList;
	}

	public void setTranscodeList(List<Transcode> transcodeProfileList) {
		this.transcodeList = transcodeProfileList;
	}

	@XmlElement(name = "ProtectProfile")
	public List<Protect> getProtectList() {
		return protectList;
	}

	public void setProtectList(List<Protect> protectList) {
		this.protectList = protectList;
	}

	@XmlElement(name = "TermMapping")
	public List<TermMap> getTermMapList() {
		return termMapList;
	}

	public void setTermMapList(List<TermMap> termMapList) {
		this.termMapList = termMapList;
	}
	
	@Override
	public String toString() {
		return getDeviceList().toString() + " "
				+ getEncodeList().toString() + " "
				+ getTranscodeList().toString() + " "
				+ getProtectList().toString() + " "
				+ getTermMapList().toString();
	}
}
