package com.irdeto.jumpstart.domain.publish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Term;
import com.irdeto.jumpstart.domain.config.TermMap;

public class TermWrapper {
	private Term term;
	private Offer offer;
	private Base offeredEntity;
	private DateTime startDateTime;
	private DateTime endDateTime;
	private List<TermMap> termMapList = new ArrayList<>();
	
	public TermWrapper() {
		super();
	}
	
	public TermWrapper(Term term, Offer offer, Base offeredEntity, DateTime startDateTime, DateTime endDateTime) {
		super();
		setTerm(term);
		setOffer(offer);
		setOfferedEntity(offeredEntity);
		setStartDateTime(startDateTime);
		setEndDateTime(endDateTime);
	}
	
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}

	public Base getOfferedEntity() {
		return offeredEntity;
	}

	public void setOfferedEntity(Base offeredEntity) {
		this.offeredEntity = offeredEntity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((offeredEntity == null) ? 0 : offeredEntity.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TermWrapper other = (TermWrapper) obj;
		if (offeredEntity == null) {
			if (other.offeredEntity != null)
				return false;
		} else if (!offeredEntity.equals(other.offeredEntity))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}

	public DateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(DateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public DateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(DateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public List<TermMap> getTermMapList() {
		return termMapList;
	}

	public void setTermMapList(List<TermMap> termMapList) {
		this.termMapList = termMapList;
	}
	
	@Override
	public String toString() {
		String value = "TermWrapper for term " + getTerm().getId();
		if (getOfferedEntity() != null) {
			value = value + " and entity " + getOfferedEntity().getId();
		}
		return value;
	}

	@JsonIgnore
	public HashMap<String, Map<String, String>> getMergedCurrencyMap() {
		HashMap<String, Map<String, String>> mergedPriceMap = new HashMap<>();
		mergedPriceMap.putAll(getTerm().getSuggestedPrice());
		mergedPriceMap.putAll(getTerm().getPrice());
		return mergedPriceMap;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

}
