package com.irdeto.jumpstart.domain.publish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.irdeto.jumpstart.domain.*;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.config.TermMap;
import com.irdeto.jumpstart.domain.property.JumpstartPropertyKey;
import com.irdeto.jumpstart.domain.relationship.Relationship;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class ProgramPublishWrapper extends AbstractPublishWrapper<Program> implements EntityWithGenreListPublishWrapper, EntityWithBrandListPublishWrapper, EntityWithEventListPublishWrapper, EntityWithRatingsPublishWrapper, EntityWithScheduleSlotListPublishWrapper, EntityWithSeriesListPublishWrapper, EntityWithSubscriptionPackageListPublishWrapper, EntityWithTvodCollectionListPublishWrapper {
    public ProgramPublishWrapper() {
        super();
    }

    public ProgramPublishWrapper(Program approvedEntity) {
        super();
        setApprovedEntity(approvedEntity);
    }

    @Override
    public List<Relationship<?>> getRelationshipList() {
        List<Relationship<?>> relationshipList = new ArrayList<>();
        relationshipList.add(new Relationship<TvodCollection>(WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME, TvodCollection.class));
        relationshipList.add(new Relationship<SubscriptionPackage>(WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME, SubscriptionPackage.class));
        relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class));
        relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class, new Relationship<TvodCollection>(WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME, TvodCollection.class)));
        relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class, new Relationship<SubscriptionPackage>(WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME, SubscriptionPackage.class)));
        relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class, new Relationship<Brand>(WorkflowHelper.BRAND_RELATIONSHIP_NAME, Brand.class)));
        relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class, new Relationship<Brand>(WorkflowHelper.BRAND_RELATIONSHIP_NAME, Brand.class, new Relationship<TvodCollection>(WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME, TvodCollection.class))));
        relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class, new Relationship<Brand>(WorkflowHelper.BRAND_RELATIONSHIP_NAME, Brand.class, new Relationship<SubscriptionPackage>(WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME, SubscriptionPackage.class))));
       // relationshipList.add(new Relationship<Genre>(WorkflowHelper.GENRE_RELATIONSHIP_NAME, Genre.class));
        //relationshipList.add(new Relationship<Rating>(WorkflowHelper.RATING_RELATIONSHIP_NAME, Rating.class, new Relationship<RatingScheme>(WorkflowHelper.RATING_SCHEME_RELATIONSHIP_NAME, RatingScheme.class)));
        relationshipList.add(new Relationship<ScheduleSlot>(WorkflowHelper.SCHEDULE_SLOT_RELATIONSHIP_NAME, ScheduleSlot.class));
        relationshipList.add(new Relationship<Event>(WorkflowHelper.EVENT_RELATIONSHIP_NAME, Event.class));
        relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class, new Relationship<Genre>(WorkflowHelper.GENRE_RELATIONSHIP_NAME, Genre.class)));
        relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class, new Relationship<Brand>(WorkflowHelper.BRAND_RELATIONSHIP_NAME, Brand.class, new Relationship<Genre>(WorkflowHelper.GENRE_RELATIONSHIP_NAME, Genre.class))));
        relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class, new Relationship<Rating>(WorkflowHelper.RATING_RELATIONSHIP_NAME, Rating.class, new Relationship<RatingScheme>(WorkflowHelper.RATING_SCHEME_RELATIONSHIP_NAME, RatingScheme.class))));
        relationshipList.add(new Relationship<Series>(WorkflowHelper.SERIES_RELATIONSHIP_NAME, Series.class, new Relationship<Brand>(WorkflowHelper.BRAND_RELATIONSHIP_NAME, Brand.class, new Relationship<Rating>(WorkflowHelper.RATING_RELATIONSHIP_NAME, Rating.class, new Relationship<RatingScheme>(WorkflowHelper.RATING_SCHEME_RELATIONSHIP_NAME, RatingScheme.class)))));
        return relationshipList;
    }

    @Override
    @JsonIgnore
    public boolean hasVideoContent() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isValidTermMap(TermMap termMap) {
        return termMap.getContentTypeList().contains(ContentType.MOVIE.toString())
                || termMap.getContentTypeList().contains(ContentType.PREVIEW.toString())
                || termMap.getContentTypeList().contains(ContentType.BARKER.toString());
    }

    @Override
    @JsonIgnore
    public List<Relationship<?>> getTermRelationshipList() {
        return WorkflowHelper.getTermRelationshipList();
    }

    @Override
    @JsonIgnore
    public Class<Program> getEntityClass() {
        return Program.class;
    }

    @Override
    @JsonIgnore
    public List<String> getDestinationProcessIdList() {
        List<String> destinationProcessIdList = new ArrayList<String>();
        destinationProcessIdList.add(JumpstartPropertyKey.PROCESS_ID_CDN_PUBLISH);
        destinationProcessIdList.add(JumpstartPropertyKey.PROCESS_ID_CONTROL_PUBLISH);
        destinationProcessIdList.add(JumpstartPropertyKey.PROCESS_ID_ELASTICSEARCH_PUBLISH);
        destinationProcessIdList.add(JumpstartPropertyKey.PROCESS_ID_ACTIVEMQ_PUBLISH);
        return destinationProcessIdList;
    }

    @Override
    @JsonIgnore
    public List<Base> getPrerequisites() {
        List<Base> prerequisites = new ArrayList<>();
        prerequisites.addAll(getEntityListFromRelationshipMap(TvodCollection.class, WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME));
        prerequisites.addAll(getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME));
        prerequisites.addAll(getEntityListFromRelationshipMap(Series.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME));
        prerequisites.addAll(getEntityListFromRelationshipMap(TvodCollection.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME));
        prerequisites.addAll(getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME));
        prerequisites.addAll(getEntityListFromRelationshipMap(Brand.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.BRAND_RELATIONSHIP_NAME));
        prerequisites.addAll(getEntityListFromRelationshipMap(TvodCollection.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.BRAND_RELATIONSHIP_NAME, WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME));
        prerequisites.addAll(getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.BRAND_RELATIONSHIP_NAME, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME));
      // prerequisites.addAll(getEntityListFromRelationshipMap(Genre.class, WorkflowHelper.GENRE_RELATIONSHIP_NAME));
        return prerequisites;
    }

    @Override
    @JsonIgnore
    public List<Genre> getGenreList() {
        return getEntityListFromRelationshipMap(Genre.class, WorkflowHelper.GENRE_RELATIONSHIP_NAME);
    }

    @Override
    @JsonIgnore
    public List<Brand> getBrandList() {
        return (this.getBrand() != null) ? ImmutableList.of(this.getBrand()) : new ArrayList<Brand>();
    }

    @JsonIgnore
    public Brand getBrand() {
        return getEntityFromRelationshipMap(Brand.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.BRAND_RELATIONSHIP_NAME);
    }

    @Override
    @JsonIgnore
    public List<Series> getSeriesList() {
        return (this.getSeries() != null) ? ImmutableList.of(this.getSeries()) : new ArrayList<Series>();
    }

    @JsonIgnore
    public Series getSeries() {
        return getEntityFromRelationshipMap(Series.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME);
    }

    @JsonIgnore
    public List<SubscriptionPackage> getSubscriptionPackageList() {
        List<SubscriptionPackage> programSubscriptionPackageList = getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME);
        List<SubscriptionPackage> seriesSubscriptionPackageList = getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME);
        List<SubscriptionPackage> brandSubscriptionPackageList = getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.BRAND_RELATIONSHIP_NAME, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME);
        List<SubscriptionPackage> subscriptionPackageList = new ArrayList<>();
        subscriptionPackageList.addAll(programSubscriptionPackageList);
        subscriptionPackageList.addAll(seriesSubscriptionPackageList);
        subscriptionPackageList.addAll(brandSubscriptionPackageList);
        return subscriptionPackageList;
    }

    @JsonIgnore
    public List<TvodCollection> getTvodCollectionList() {
        List<TvodCollection> programTvodCollectionList = getEntityListFromRelationshipMap(TvodCollection.class, WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME);
        List<TvodCollection> seriesTvodCollectionList = getEntityListFromRelationshipMap(TvodCollection.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME);
        List<TvodCollection> brandTvodCollectionList = getEntityListFromRelationshipMap(TvodCollection.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.BRAND_RELATIONSHIP_NAME, WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME);
        List<TvodCollection> tvodCollectionList = new ArrayList<>();
        tvodCollectionList.addAll(programTvodCollectionList);
        tvodCollectionList.addAll(seriesTvodCollectionList);
        tvodCollectionList.addAll(brandTvodCollectionList);
        return tvodCollectionList;
    }

    @JsonIgnore
    public List<ScheduleSlot> getScheduleSlotList() {
        return getEntityListFromRelationshipMap(ScheduleSlot.class, WorkflowHelper.SCHEDULE_SLOT_RELATIONSHIP_NAME);
    }

    @JsonIgnore
    public List<Event> getEventList() {
        return getEntityListFromRelationshipMap(Event.class, WorkflowHelper.EVENT_RELATIONSHIP_NAME);
    }

    @JsonIgnore
    public Map<Rating, List<RatingScheme>> getRatingRatingSchemeListMap() {
        return getRatingRatingSchemeListMap(WorkflowHelper.RATING_RELATIONSHIP_NAME, WorkflowHelper.RATING_SCHEME_RELATIONSHIP_NAME);
    }

    @JsonIgnore
    public Map<Rating, List<RatingScheme>> getSeriesRatingRatingSchemeListMap() {
        return getRatingRatingSchemeListMap(WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.RATING_RELATIONSHIP_NAME, WorkflowHelper.RATING_SCHEME_RELATIONSHIP_NAME);
    }

    @JsonIgnore
    public Map<Rating, List<RatingScheme>> getBrandRatingRatingSchemeListMap() {
        return getRatingRatingSchemeListMap(WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.BRAND_RELATIONSHIP_NAME, WorkflowHelper.RATING_RELATIONSHIP_NAME, WorkflowHelper.RATING_SCHEME_RELATIONSHIP_NAME);
    }

    @JsonIgnore
    public List<Genre> getSeriesGenreList() {
        return getEntityListFromRelationshipMap(Genre.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.GENRE_RELATIONSHIP_NAME);
    }

    @JsonIgnore
    public List<Genre> getBrandGenreList() {
        return getEntityListFromRelationshipMap(Genre.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.BRAND_RELATIONSHIP_NAME, WorkflowHelper.GENRE_RELATIONSHIP_NAME);
    }

    @JsonIgnore
    public List<TvodCollection> getSeriesTvodCollectionList() {
        return getEntityListFromRelationshipMap(TvodCollection.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME);
    }

    @JsonIgnore
    public List<TvodCollection> getBrandTvodCollectionList() {
        return getEntityListFromRelationshipMap(TvodCollection.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.BRAND_RELATIONSHIP_NAME, WorkflowHelper.TVOD_COLLECTION_RELATIONSHIP_NAME);
    }

    @JsonIgnore
    public List<SubscriptionPackage> getSeriesSubscriptionPackageList() {
        return getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME);
    }

    @JsonIgnore
    public List<SubscriptionPackage> getBrandSubscriptionPackageList() {
        return getEntityListFromRelationshipMap(SubscriptionPackage.class, WorkflowHelper.SERIES_RELATIONSHIP_NAME, WorkflowHelper.BRAND_RELATIONSHIP_NAME, WorkflowHelper.SUBSCRIPTION_PACKAGE_RELATIONSHIP_NAME);
    }

    @Override
    @JsonIgnore
    public Map<RatingScheme, Rating> getRatings() {
        Map<RatingScheme, Rating> ratingsMap = new HashMap<>();
        for(Map.Entry<Rating, List<RatingScheme>> entry: getRatingRatingSchemeListMap().entrySet()) {
            ratingsMap.put(entry.getValue().get(0), entry.getKey());
        }
        return ratingsMap;
    }
}
