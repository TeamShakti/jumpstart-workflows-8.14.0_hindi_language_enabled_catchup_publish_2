package com.irdeto.jumpstart.workflow.publish.reference;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.TvodCollection;
import com.irdeto.jumpstart.domain.publish.EntityWithTvodCollectionListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.TermWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;
import com.irdeto.jumpstart.domain.reference.ReferenceTvodCollection;

public class TvodCollectionReferenceDocumentMapper extends AbstractReferenceDocumentMapper<TvodCollection> {
	@Override
	@JsonIgnore
	public Class<TvodCollection> getEntityClass() {
		return TvodCollection.class;
	}

	@Override
	@JsonIgnore
	public ReferenceDocument getReferenceDocument() throws Exception {
		TvodCollection tvodCollection = getPublishWrapper().getApprovedEntity();
		ReferenceTvodCollection referenceTvodCollection = mapTvodCollection(tvodCollection, getPublishWrapper().getTermWrapperList());
		mapDocument(tvodCollection, referenceTvodCollection);
		return referenceTvodCollection;
	}

	protected static List<ReferenceTvodCollection> mapTvodCollectionList(EntityWithTvodCollectionListPublishWrapper publishWrapper) throws Exception {
		return mapTvodCollectionList(publishWrapper.getTvodCollectionList());
	}

	protected static List<ReferenceTvodCollection> mapTvodCollectionList(List<TvodCollection> tvodCollectionList) throws Exception {
		List<ReferenceTvodCollection> referenceTvodCollectionList = new ArrayList<>();
		if (tvodCollectionList != null) {
			for (TvodCollection tvodCollection: tvodCollectionList) {
				referenceTvodCollectionList.add(mapTvodCollection(tvodCollection));
			}
		}
		return referenceTvodCollectionList;
	}

	protected static ReferenceTvodCollection mapTvodCollection(TvodCollection tvodCollection) throws Exception {
		return mapTvodCollection(tvodCollection, null);
	}

	protected static ReferenceTvodCollection mapTvodCollection(TvodCollection tvodCollection, List<TermWrapper> termWrapperList) throws Exception {
		ReferenceTvodCollection referenceTvodCollection = null;
		if (tvodCollection != null) {
			referenceTvodCollection = new ReferenceTvodCollection();
			mapDocumentTitle(tvodCollection, termWrapperList, referenceTvodCollection);
			mapDocumentEntitlement(tvodCollection, termWrapperList, referenceTvodCollection);
			referenceTvodCollection.setImageContents(mapImageContents(tvodCollection.getImageContent()));
		}
		return referenceTvodCollection;
	}
}
