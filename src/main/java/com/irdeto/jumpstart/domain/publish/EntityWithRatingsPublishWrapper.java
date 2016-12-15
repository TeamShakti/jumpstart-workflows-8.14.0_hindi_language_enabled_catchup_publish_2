package com.irdeto.jumpstart.domain.publish;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Rating;
import com.irdeto.jumpstart.domain.RatingScheme;

public interface EntityWithRatingsPublishWrapper extends EntityWithTermWrapperListPublishWrapper{
	@JsonIgnore
	public Map<RatingScheme, Rating> getRatings();
}
