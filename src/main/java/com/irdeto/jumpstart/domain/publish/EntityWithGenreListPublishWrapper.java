package com.irdeto.jumpstart.domain.publish;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Genre;

public interface EntityWithGenreListPublishWrapper {
	@JsonIgnore
	public List<Genre> getGenreList();
}
