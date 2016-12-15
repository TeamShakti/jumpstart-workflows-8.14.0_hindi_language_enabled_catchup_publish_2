package com.irdeto.jumpstart.workflow.publish.reference;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Series;
import com.irdeto.jumpstart.domain.publish.EntityWithBrandListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithGenreListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithRatingsPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithSeriesListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithSubscriptionPackageListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithTvodCollectionListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.TermWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;
import com.irdeto.jumpstart.domain.reference.ReferenceSeries;

public class SeriesReferenceDocumentMapper extends AbstractReferenceDocumentMapper<Series> {
	@Override
	@JsonIgnore
	public Class<Series> getEntityClass() {
		return Series.class;
	}

	@Override
	@JsonIgnore
	public ReferenceDocument getReferenceDocument() throws Exception {
		Series series = getPublishWrapper().getApprovedEntity();
		ReferenceSeries referenceSeries = mapSeries(series, getPublishWrapper().getTermWrapperList());
		mapDocument(series, referenceSeries);
		referenceSeries.getGenres().addAll(GenreReferenceDocumentMapper.mapGenreList((EntityWithGenreListPublishWrapper)getPublishWrapper()));
		referenceSeries.getRatings().addAll(RatingReferenceMapper.mapRatingList((EntityWithRatingsPublishWrapper)getPublishWrapper()));
		referenceSeries.getSubscriptionPackages().addAll(SubscriptionPackageReferenceDocumentMapper.mapSubscriptionPackageList((EntityWithSubscriptionPackageListPublishWrapper)getPublishWrapper()));
		referenceSeries.getTvodCollections().addAll(TvodCollectionReferenceDocumentMapper.mapTvodCollectionList((EntityWithTvodCollectionListPublishWrapper)getPublishWrapper()));
		referenceSeries.setBrand(BrandReferenceDocumentMapper.mapBrand((EntityWithBrandListPublishWrapper)getPublishWrapper()));
		return referenceSeries;
	}

	protected static ReferenceSeries mapSeries(EntityWithSeriesListPublishWrapper publishWrapper) throws Exception {
		return mapSeries(publishWrapper.getSeries());
	}

	protected static ReferenceSeries mapSeries(Series series) throws Exception {
		return mapSeries(series, null);
	}

	protected static ReferenceSeries mapSeries(Series series, List<TermWrapper> termWrapperList) throws Exception {
		ReferenceSeries referenceSeries = null;
		if (series != null) {
			referenceSeries = new ReferenceSeries();
			mapDocumentTitle(series, termWrapperList, referenceSeries);
			mapDocumentEntitlement(series, termWrapperList, referenceSeries);
			referenceSeries.setSeason(String.valueOf(series.getSeason()));
			referenceSeries.setImageContents(mapImageContents(series.getImageContent()));
		}
		return referenceSeries;
	}
}
