package com.irdeto.jumpstart.domain.config;

import com.irdeto.jumpstart.domain.Base;
import com.irdeto.jumpstart.domain.Term;

public class AvailabilityWrapper implements Comparable<AvailabilityWrapper> {
	private Base entity;
	private Term term;
	private TermMap termMap;
	public Base getEntity() {
		return entity;
	}
	public void setEntity(Base entity) {
		this.entity = entity;
	}
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	public TermMap getTermMap() {
		return termMap;
	}
	public void setTermMap(TermMap termMap) {
		this.termMap = termMap;
	}
	@Override
	public int compareTo(AvailabilityWrapper o) {
		if (getTerm().getStartDateTime() == null) {
			return -1;
		}
		if (o.getTerm().getStartDateTime() == null) {
			return 1;
		}
		return getTerm().getStartDateTime().compareTo(o.getTerm().getStartDateTime());
	}
	
}
