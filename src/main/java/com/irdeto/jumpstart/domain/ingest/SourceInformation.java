package com.irdeto.jumpstart.domain.ingest;

import com.irdeto.jumpstart.workflow.ingest.IngestMapper;

public class SourceInformation {
	private String fileName;
	private String filePath;
	private String mapper;

	public SourceInformation() {
		super();
	}

	public SourceInformation(String fileName, String filePath, Class<? extends IngestMapper> mapper) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.mapper = mapper.getCanonicalName();
	}

	public SourceInformation(String fileName, String filePath, String mapper) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.mapper = mapper;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getMapper() {
		return mapper;
	}

	public void setMapper(String mapper) {
		this.mapper = mapper;
	}
}
