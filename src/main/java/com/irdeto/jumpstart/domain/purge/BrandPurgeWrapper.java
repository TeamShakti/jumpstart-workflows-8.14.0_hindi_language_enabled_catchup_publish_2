package com.irdeto.jumpstart.domain.purge;

import java.util.ArrayList;
import java.util.List;

import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;

public class BrandPurgeWrapper extends AbstractPurgeWrapper<Brand> {
	@Override
	public Class<Brand> getEntityClass() {
		return Brand.class;
	}

	@Override
	public List<String> getDestinationProcessIdList() {
		List<String> processIdList = new ArrayList<>();
		processIdList.add(JumpstartPropertyKey.PROCESS_ID_ACTIVEMQ_PUBLISH);
		processIdList.add(JumpstartPropertyKey.PROCESS_ID_ELASTICSEARCH_PUBLISH);
		return processIdList;
	}
}
