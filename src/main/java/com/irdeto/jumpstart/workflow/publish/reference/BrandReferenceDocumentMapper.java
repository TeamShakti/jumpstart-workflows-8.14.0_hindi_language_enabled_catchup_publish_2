package com.irdeto.jumpstart.workflow.publish.reference;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.Brand;
import com.irdeto.jumpstart.domain.publish.EntityWithBrandListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithGenreListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithRatingsPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithSubscriptionPackageListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.EntityWithTvodCollectionListPublishWrapper;
import com.irdeto.jumpstart.domain.publish.TermWrapper;
import com.irdeto.jumpstart.domain.reference.ReferenceBrand;
import com.irdeto.jumpstart.domain.reference.ReferenceDocument;

public class BrandReferenceDocumentMapper extends AbstractReferenceDocumentMapper<Brand> {
	@Override
	@JsonIgnore
	public Class<Brand> getEntityClass() {
		return Brand.class;
	}

	@Override
	@JsonIgnore
	public ReferenceDocument getReferenceDocument() throws Exception {
		Brand brand = getPublishWrapper().getEntity();
		ReferenceBrand referenceBrand = mapBrand(brand, getPublishWrapper().getTermWrapperList());
		mapDocument(brand, referenceBrand);
		referenceBrand.getGenres().addAll(GenreReferenceDocumentMapper.mapGenreList((EntityWithGenreListPublishWrapper)getPublishWrapper()));
		referenceBrand.getRatings().addAll(RatingReferenceMapper.mapRatingList((EntityWithRatingsPublishWrapper)getPublishWrapper()));
		referenceBrand.getSubscriptionPackages().addAll(SubscriptionPackageReferenceDocumentMapper.mapSubscriptionPackageList((EntityWithSubscriptionPackageListPublishWrapper)getPublishWrapper()));
		referenceBrand.getTvodCollections().addAll(TvodCollectionReferenceDocumentMapper.mapTvodCollectionList((EntityWithTvodCollectionListPublishWrapper)getPublishWrapper()));
		return referenceBrand;
	}

	protected static List<ReferenceBrand> mapBrandList(EntityWithBrandListPublishWrapper publishWrapper) throws Exception {
		return mapBrandList(publishWrapper.getBrandList());
	}

	protected static List<ReferenceBrand> mapBrandList(List<Brand> brandList) throws Exception {
		List<ReferenceBrand> referenceBrandList = new ArrayList<>();
		if (brandList != null) {
			for (Brand brand: brandList) {
				referenceBrandList.add(mapBrand(brand));
			}
		}
		return referenceBrandList;
	}

	protected static ReferenceBrand mapBrand(Brand brand) throws Exception {
		return mapBrand(brand);
	}

	protected static ReferenceBrand mapBrand(EntityWithBrandListPublishWrapper publishWrapper) throws Exception {
		return mapBrand(publishWrapper.getBrand(), null);
	}

	protected static ReferenceBrand mapBrand(Brand brand, List<TermWrapper> termWrapperList) throws Exception {
		ReferenceBrand referenceBrand = null;
		if(brand != null) {
			referenceBrand = new ReferenceBrand();
			mapDocumentTitle(brand, termWrapperList, referenceBrand);
			mapDocumentEntitlement(brand, termWrapperList, referenceBrand);
			referenceBrand.setImageContents(mapImageContents(brand.getImageContent()));
		}
		return referenceBrand;
	}
}
