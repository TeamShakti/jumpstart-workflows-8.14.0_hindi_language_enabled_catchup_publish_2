package com.irdeto.jumpstart.workflow.publish.reference;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Event;
import com.irdeto.jumpstart.domain.publish.EntityWithEventListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithGenreListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithProgramListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithRatingsPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithScheduleSlotListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithSubscriptionPackageListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithTvodCollectionListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.TermWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;
import com.irdeto.jumpstart.domain.reference.ReferenceEvent;
import com.irdeto.jumpstart.domain.reference.ReferenceVideoContent;

public class EventReferenceDocumentMapper extends AbstractReferenceDocumentMapper<Event> {
	@Override
	@JsonIgnore
	public Class<Event> getEntityClass() {
		return Event.class;
	}

	@Override
	@JsonIgnore
	public ReferenceDocument getReferenceDocument() throws Exception {
		Event event = getPublishWrapper().getApprovedEntity();
		List<ReferenceVideoContent> videoContentList = mapEncodeProfileRenditions();
		ReferenceEvent referenceEvent = mapEvent(event, getPublishWrapper().getTermWrapperList(), videoContentList);
		mapDocument(event, referenceEvent);
		referenceEvent.getGenres().addAll(GenreReferenceDocumentMapper.mapGenreList((EntityWithGenreListPublishWrapper)getPublishWrapper()));
		referenceEvent.getScheduleSlots().addAll(ScheduleSlotReferenceDocumentMapper.mapScheduleSlotList((EntityWithScheduleSlotListPublishWrapper)getPublishWrapper()));
		referenceEvent.getPrograms().addAll(ProgramReferenceDocumentMapper.mapProgramList((EntityWithProgramListPublishWrapper)getPublishWrapper()));
		referenceEvent.getSubscriptionPackages().addAll(SubscriptionPackageReferenceDocumentMapper.mapSubscriptionPackageList((EntityWithSubscriptionPackageListPublishWrapper)getPublishWrapper()));
		referenceEvent.getTvodCollections().addAll(TvodCollectionReferenceDocumentMapper.mapTvodCollectionList((EntityWithTvodCollectionListPublishWrapper)getPublishWrapper()));
		referenceEvent.getRatings().addAll(RatingReferenceMapper.mapRatingList((EntityWithRatingsPublishWrapper)getPublishWrapper()));
		return referenceEvent;
	}

	protected static List<ReferenceEvent> mapEventList(EntityWithEventListPublishWrapper publishWrapper) throws Exception {
		return mapEventList(publishWrapper.getEventList());
	}

	protected static List<ReferenceEvent> mapEventList(List<Event> events) throws Exception {
		List<ReferenceEvent> referenceEventList = new ArrayList<>();
		if (events != null) {
			for (Event event: events) {
				referenceEventList.add(mapEvent(event));
			}
		}
		return referenceEventList;
	}

	protected static ReferenceEvent mapEvent(Event event) throws Exception {
		return mapEvent(event, null, null);
	}

	protected static ReferenceEvent mapEvent(Event event, List<TermWrapper> termWrapperList, List<ReferenceVideoContent> videoContentList) throws Exception {
		ReferenceEvent referenceEvent = null;
		if (event != null) {
			referenceEvent = new ReferenceEvent();
			mapDocumentTitle(event, termWrapperList, referenceEvent);
			mapDocumentEntitlement(event, termWrapperList, videoContentList, referenceEvent);
			referenceEvent.setScreenFormat(stringify(event.getScreenFormat()));
			referenceEvent.setEventBroadcastDate(event.getEventBroadcastDate());
			referenceEvent.setEventBroadcastEndDate(event.getEventBroadcastEndDate());
			referenceEvent.setContributors(ContributorReferenceMapper.mapContributorList(event.getContributors()));
			referenceEvent.setImageContents(mapImageContents(event.getImageContent()));
		}
		return referenceEvent;
	}
}
