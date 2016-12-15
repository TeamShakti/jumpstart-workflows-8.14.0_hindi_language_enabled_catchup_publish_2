package com.irdeto.jumpstart.domain.publish;

import java.util.List;

import com.irdeto.jumpstart.domain.Brand;

public interface EntityWithBrandListPublishWrapper extends EntityWithTermWrapperListPublishWrapper {
	public List<Brand> getBrandList();
	public Brand getBrand();
}
