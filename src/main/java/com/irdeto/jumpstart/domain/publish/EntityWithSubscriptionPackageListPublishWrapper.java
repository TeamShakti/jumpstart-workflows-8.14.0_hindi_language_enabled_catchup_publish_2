package com.irdeto.jumpstart.domain.publish;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.SubscriptionPackage;

public interface EntityWithSubscriptionPackageListPublishWrapper extends EntityWithTermWrapperListPublishWrapper{
	@JsonIgnore
	public List<SubscriptionPackage> getSubscriptionPackageList();
}
