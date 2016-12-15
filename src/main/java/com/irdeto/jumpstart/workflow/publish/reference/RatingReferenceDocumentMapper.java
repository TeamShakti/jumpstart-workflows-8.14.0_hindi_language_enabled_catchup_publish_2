package com.irdeto.jumpstart.workflow.publish.reference;

import com.irdeto.jumpstart.domain.Rating;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;
import com.irdeto.jumpstart.domain.reference.ReferenceRating;

public class RatingReferenceDocumentMapper extends AbstractReferenceDocumentMapper<Rating>{

	@Override
	public ReferenceDocument getReferenceDocument() throws Exception {
		Rating rating =getPublishWrapper().getEntity();
		ReferenceRating referencerating = maprating(rating);
		return referencerating;
	}

	private static ReferenceRating maprating(Rating rating){
		ReferenceRating referencerating = null;
		if(rating!=null){
			referencerating = new ReferenceRating();
			referencerating.setId(rating.getUriId());
			referencerating.setMinimumAge(rating.getMinimumAge());
			referencerating.setRatingLabel(rating.getRatingLabel());
		}
		return referencerating;
	}
	@Override
	public Class<Rating> getEntityClass() {
		// TODO Auto-generated method stub
		return Rating.class;
	}

}
