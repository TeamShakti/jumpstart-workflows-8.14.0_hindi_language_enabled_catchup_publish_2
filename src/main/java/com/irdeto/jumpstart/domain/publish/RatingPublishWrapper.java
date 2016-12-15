package com.irdeto.jumpstart.domain.publish;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.Rating;
import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.relationship.Relationship;

public class RatingPublishWrapper extends AbstractPublishWrapper<Rating> {

	@Override
	public List<Relationship<?>> getRelationshipList() {
		// TODO Auto-generated method stub
		List<Relationship<?>> relationshipList = new ArrayList<>();
		return relationshipList;
	}

	@Override
	public boolean isValidTermMap(TermMap termMap) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	@Override
	@JsonIgnore
	public List<String> getDestinationProcessIdList() {
		List<String> destinationProcessIdList = new ArrayList<String>();
		destinationProcessIdList.add(JumpstartPropertyKey.PROCESS_ID_ACTIVEMQ_PUBLISH);
		return destinationProcessIdList;
	}
// change by nitin
	@Override
	@JsonIgnore
	public boolean isTermsRequired() {
		return false;
	}
	
	@Override
	public List<Relationship<?>> getTermRelationshipList() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public List<Base> getPrerequisites() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public Class<Rating> getEntityClass() {
		// TODO Auto-generated method stub
		return Rating.class;
	}

}