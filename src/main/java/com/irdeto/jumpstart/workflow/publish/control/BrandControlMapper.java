package com.irdeto.jumpstart.workflow.publish.control;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.ContentType;
import com.irdeto.jumpstart.domain.config.TermMap;

public class BrandControlMapper extends CategoryControlMapper<Brand>{

	@Override
	@JsonIgnore
	public Class<Brand> getEntityClass() {
		return Brand.class;
	}

	@Override
	@JsonIgnore
	protected boolean isMainContentType(TermMap termMap) {
		return termMap.getContentTypeList().contains(ContentType.MOVIE.toString());
	}
}
