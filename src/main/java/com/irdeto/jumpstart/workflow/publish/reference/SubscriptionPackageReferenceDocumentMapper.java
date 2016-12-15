package com.irdeto.jumpstart.workflow.publish.reference;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.SubscriptionPackage;
import com.irdeto.jumpstart.domain.publish.EntityWithSubscriptionPackageListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.TermWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;
import com.irdeto.jumpstart.domain.reference.ReferenceSubscriptionPackage;

public class SubscriptionPackageReferenceDocumentMapper extends AbstractReferenceDocumentMapper<SubscriptionPackage> {
	@Override
	@JsonIgnore
	public Class<SubscriptionPackage> getEntityClass() {
		return SubscriptionPackage.class;
	}

	@Override
	@JsonIgnore
	public ReferenceDocument getReferenceDocument() throws Exception {
		SubscriptionPackage subscriptionPackage = getPublishWrapper().getApprovedEntity();
		ReferenceSubscriptionPackage referenceSubscriptionPackage = mapSubscriptionPackage(subscriptionPackage, getPublishWrapper().getTermWrapperList());
		mapDocument(subscriptionPackage, referenceSubscriptionPackage);
		return referenceSubscriptionPackage;
	}

	protected static List<ReferenceSubscriptionPackage> mapSubscriptionPackageList(EntityWithSubscriptionPackageListPublishWrapper publishWrapper) throws Exception {
		return mapSubscriptionPackageList(publishWrapper.getSubscriptionPackageList());
	}

	protected static List<ReferenceSubscriptionPackage> mapSubscriptionPackageList(List<SubscriptionPackage> subscriptionPackageList) throws Exception {
		List<ReferenceSubscriptionPackage> referenceSubscriptionPackageList = new ArrayList<>();
		if (subscriptionPackageList != null) {
			for (SubscriptionPackage subscriptionPackage: subscriptionPackageList) {
				referenceSubscriptionPackageList.add(mapSubscriptionPackage(subscriptionPackage));
			}
		}
		return referenceSubscriptionPackageList;
	}

	protected static ReferenceSubscriptionPackage mapSubscriptionPackage(SubscriptionPackage subscriptionPackage) throws Exception {
		return mapSubscriptionPackage(subscriptionPackage, null);
	}

	protected static ReferenceSubscriptionPackage mapSubscriptionPackage(SubscriptionPackage subscriptionPackage, List<TermWrapper> termWrapperList) throws Exception {
		ReferenceSubscriptionPackage referenceSubscriptionPackage = null;
		if (subscriptionPackage != null) {
			referenceSubscriptionPackage = new ReferenceSubscriptionPackage();
			mapDocumentTitle(subscriptionPackage, termWrapperList, referenceSubscriptionPackage);
			mapDocumentEntitlement(subscriptionPackage, termWrapperList, referenceSubscriptionPackage);
			referenceSubscriptionPackage.setAlacarte(subscriptionPackage.getAlacarte());
			referenceSubscriptionPackage.setSmsPackageId(subscriptionPackage.getSmsPackageId());
			referenceSubscriptionPackage.setImageContents(mapImageContents(subscriptionPackage.getImageContent()));
		}
		return referenceSubscriptionPackage;
	}
}
