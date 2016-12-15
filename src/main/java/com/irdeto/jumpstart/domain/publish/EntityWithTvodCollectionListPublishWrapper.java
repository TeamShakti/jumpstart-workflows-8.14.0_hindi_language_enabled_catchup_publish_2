package com.irdeto.jumpstart.domain.publish;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.TvodCollection;

public interface EntityWithTvodCollectionListPublishWrapper extends EntityWithTermWrapperListPublishWrapper{
	@JsonIgnore
	public List<TvodCollection> getTvodCollectionList();
}
