package com.irdeto.jumpstart.domain.publish;

import java.util.List;

import com.irdeto.jumpstart.domain.Series;

public interface EntityWithSeriesListPublishWrapper extends EntityWithTermWrapperListPublishWrapper{
	public List<Series> getSeriesList();
	public Series getSeries();
}
