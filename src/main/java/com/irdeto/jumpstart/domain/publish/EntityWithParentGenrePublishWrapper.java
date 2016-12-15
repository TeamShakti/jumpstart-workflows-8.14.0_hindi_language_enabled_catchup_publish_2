package com.irdeto.jumpstart.domain.publish;

import com.irdeto.jumpstart.domain.Genre;
import org.codehaus.jackson.annotate.JsonIgnore;
public interface EntityWithParentGenrePublishWrapper {
	@JsonIgnore
	public Genre getParentGenre();
}
