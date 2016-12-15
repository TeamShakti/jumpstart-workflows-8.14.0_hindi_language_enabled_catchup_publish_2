package com.irdeto.jumpstart.domain.purge;

import java.util.ArrayList;
import java.util.List;

import com.irdeto.jumpstart.domain.Offer;

public class OfferPurgeWrapper extends AbstractPurgeWrapper<Offer> {
	@Override
	public Class<Offer> getEntityClass() {
		return Offer.class;
	}

	@Override
	public List<String> getDestinationProcessIdList() {
		return new ArrayList<>();
	}
}
