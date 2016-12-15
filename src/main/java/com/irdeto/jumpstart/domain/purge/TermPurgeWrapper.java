package com.irdeto.jumpstart.domain.purge;

import java.util.ArrayList;
import java.util.List;

import com.irdeto.jumpstart.domain.Term;

public class TermPurgeWrapper extends AbstractPurgeWrapper<Term> {
	@Override
	public Class<Term> getEntityClass() {
		return Term.class;
	}

	@Override
	public List<String> getDestinationProcessIdList() {
		return new ArrayList<>();
	}
}
