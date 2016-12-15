package com.irdeto.jumpstart.workflow.publish.control;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.ContentType;
import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.config.TermMap;

public class SeriesControlMapper extends CategoryControlMapper<Series>{

	@Override
	@JsonIgnore
	public Class<Series> getEntityClass() {
		return Series.class;
	}
	
	@Override
	@JsonIgnore
	protected boolean isMainContentType(TermMap termMap) {
		return termMap.getContentTypeList().contains(ContentType.MOVIE.toString());
	}

}
