package com.irdeto.jumpstart.domain.ingest;

import java.util.HashMap;
import java.util.Map;

import com.irdeto.jumpstart.domain.Base;

public abstract class EntityIngestWrapperWithGenreAndRating<T extends Base> extends EntityIngestWrapperWithGenre<T> {
	private Map<String, String> ratingMap = new HashMap<>();

	public Map<String, String> getRatingMap() {
		return ratingMap;
	}

	public void setRatingMap(Map<String, String> ratingMap) {
		this.ratingMap = ratingMap;
	}
}
