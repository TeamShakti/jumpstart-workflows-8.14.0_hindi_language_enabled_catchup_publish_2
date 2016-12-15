package com.irdeto.jumpstart.workflow.publish.reference;

import java.util.ArrayList;
import java.util.List;

import com.irdeto.jumpstart.domain.Person;
import com.irdeto.jumpstart.domain.reference.ReferenceContributor;


public class ContributorReferenceMapper extends AbstractReferenceMapper {
	protected static List<ReferenceContributor> mapContributorList(List<Person> persons) {
		List<ReferenceContributor> contributorList = new ArrayList<>();
		if (persons != null) {
			for (Person person: persons) {
				contributorList.add(mapContributor(person));
			}
		}
		return contributorList;
	}

	protected static ReferenceContributor mapContributor(Person person) {
		ReferenceContributor referenceContributor = null;
		if (person != null) {
			referenceContributor = new ReferenceContributor();
			referenceContributor.setContribution(stringify(person.getContribution()));
			referenceContributor.setSortableName(mapLocalized(person.getSortableName()));
			referenceContributor.setFirstName(mapLocalized(person.getFirstName()));
			referenceContributor.setLastName(mapLocalized(person.getLastName()));
			referenceContributor.setFullName(mapLocalized(person.getFullName()));
		}
		return referenceContributor;
	}
}
