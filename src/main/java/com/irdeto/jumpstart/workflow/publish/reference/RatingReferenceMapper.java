package com.irdeto.jumpstart.workflow.publish.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.irdeto.jumpstart.domain.Rating;
import com.irdeto.jumpstart.domain.RatingScheme;
import com.irdeto.jumpstart.domain.publish.EntityWithRatingsPublishWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceRating;

class RatingReferenceMapper extends AbstractReferenceMapper {
	protected static List<ReferenceRating> mapRatingList(EntityWithRatingsPublishWrapper publishWrapper) {
		return mapRatingList(publishWrapper.getRatings());
	}

	protected static List<ReferenceRating> mapRatingList(Map<RatingScheme, Rating> ratingMap) {
		List<ReferenceRating> referenceRatingList = new ArrayList<>();
		if (ratingMap != null) {
			for (Entry<RatingScheme, Rating> ratingEntry: ratingMap.entrySet()) {
				referenceRatingList.add(mapRating(ratingEntry.getKey(), ratingEntry.getValue()));
			}
		}
		return referenceRatingList;
	}

	protected static ReferenceRating mapRating(RatingScheme ratingScheme, Rating rating) {
		ReferenceRating referenceRating = null;
		if (ratingScheme != null) {
			referenceRating = new ReferenceRating();
			referenceRating.setClassification(ratingScheme.getClassification());
			referenceRating.setCountries(stringifyList(ratingScheme.getCountriesIncluded()));
		}
		if (rating != null) {
			if (referenceRating == null) {
				referenceRating = new ReferenceRating();
			}
			referenceRating.setMinimumAge(rating.getMinimumAge());
			referenceRating.setRatingLabel(rating.getRatingLabel());
		}
		return referenceRating;
	}

}
