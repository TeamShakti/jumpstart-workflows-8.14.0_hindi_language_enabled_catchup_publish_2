package com.irdeto.jumpstart.workflow.publish.control;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.ContentType;
import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.config.TermMap;

public class SubscriptionPackageControlMapper extends CategoryControlMapper<SubscriptionPackage>{

	@Override
	@JsonIgnore
	public Class<SubscriptionPackage> getEntityClass() {
		return SubscriptionPackage.class;
	}
	
	@Override
	@JsonIgnore
	protected boolean isMainContentType(TermMap termMap) {
		return termMap.getContentTypeList().contains(ContentType.CHANNEL.toString())
				|| termMap.getContentTypeList().contains(ContentType.MOVIE.toString())
				|| termMap.getContentTypeList().contains(ContentType.EVENT.toString());
	}

}
