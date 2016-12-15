package com.irdeto.jumpstart.workflow.ingest;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.irdeto.jumpstart.factory.FactoryHelper;

public class EpgIngestFactory extends IngestFactory<EpgIngestMapper> {
	private static final List<Class<?>> epgIngestMapperClassList
			= FactoryHelper.streamClasses(EpgIngestMapper.class).collect(toList());

	private static final IngestFactory INSTANCE = new EpgIngestFactory();

	public static IngestFactory<EpgIngestMapper> getInstance() {
		return INSTANCE;
	}

	@Override
	protected List<Class<EpgIngestMapper>> getIngestMapperClassList() {
		return (List<Class<EpgIngestMapper>>) (List<? extends Object>) epgIngestMapperClassList;
	}
}
