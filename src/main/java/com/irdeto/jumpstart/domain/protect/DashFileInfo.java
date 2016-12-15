package com.irdeto.jumpstart.domain.protect;

import com.irdeto.domain.qts.response.QTSFileInfo;

public class DashFileInfo implements QTSFileInfo {
	private String fileSize;
	private String creationDate;
	private String fps;
	private String name;
	private String videoBitRate;
	private String duration;

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public void setFps(String fps) {
		this.fps = fps;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVideoBitRate(String videoBitRate) {
		this.videoBitRate = videoBitRate;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public String getFileSize() {
		return fileSize;
	}

	@Override
	public String getCreationDate() {
		return creationDate;
	}

	@Override
	public String getFps() {
		return fps;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getVideoBitRate() {
		return videoBitRate;
	}

	@Override
	public String getDuration() {
		return duration;
	}

}
