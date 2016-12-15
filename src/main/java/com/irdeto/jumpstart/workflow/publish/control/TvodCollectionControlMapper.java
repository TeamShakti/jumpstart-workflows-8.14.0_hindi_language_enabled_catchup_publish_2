package com.irdeto.jumpstart.workflow.publish.control;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.ContentType;
import com.irdeto.jumpstart.domain.TvodCollection;
import com.irdeto.jumpstart.domain.config.TermMap;

public class TvodCollectionControlMapper extends CategoryControlMapper<TvodCollection>{

	@Override
	@JsonIgnore
	public Class<TvodCollection> getEntityClass() {
		return TvodCollection.class;
	}
	
	@Override
	@JsonIgnore
	protected boolean isMainContentType(TermMap termMap) {
		return termMap.getContentTypeList().contains(ContentType.MOVIE.toString())
				|| termMap.getContentTypeList().contains(ContentType.EVENT.toString());
	}

}
