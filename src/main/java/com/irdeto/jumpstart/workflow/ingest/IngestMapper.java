package com.irdeto.jumpstart.workflow.ingest;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.ingest.EntityIngestWrapper;

public interface IngestMapper {

	@JsonIgnore
	public boolean isApplicableMapper();
	public List<EntityIngestWrapper<?>> findEntities() throws Exception;
	public String getInputString();
	public void setInputString(String inputString);
	public void setFileName(String fileName);
	public void setFilePath(String filePath);
	@JsonIgnore
	public boolean isSchemaValidationEnabled();
	@JsonIgnore
	public String[] getSchemaFileNames();
}
