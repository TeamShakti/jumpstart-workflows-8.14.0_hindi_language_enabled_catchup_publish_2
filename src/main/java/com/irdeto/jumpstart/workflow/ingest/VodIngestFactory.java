package com.irdeto.jumpstart.workflow.ingest;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.irdeto.jumpstart.factory.FactoryHelper;

public class VodIngestFactory extends IngestFactory<VodIngestMapper> {
	private static final List<Class<?>> vodIngestMapperClassList
			= FactoryHelper.streamClasses(VodIngestMapper.class).collect(toList());

	private static final IngestFactory INSTANCE = new VodIngestFactory();

	public static IngestFactory<VodIngestMapper> getInstance() {
		return INSTANCE;
	}

	@Override
	protected List<Class<VodIngestMapper>> getIngestMapperClassList() {
		return (List<Class<VodIngestMapper>>) (List<? extends Object>) vodIngestMapperClassList;
	}
}
