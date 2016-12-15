package com.irdeto.jumpstart.domain.ingest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.irdeto.jumpstart.domain.BaseMetadata.DataMaster;
import com.irdeto.jumpstart.domain.ImageContent;
import com.irdeto.jumpstart.domain.Offer;
import com.irdeto.jumpstart.domain.Term;
import com.irdeto.jumpstart.domain.TermMapping;
import com.irdeto.jumpstart.workflow.WorkflowHelper;

public class TermIngestWrapper extends AbstractEntityIngestWrapper<Term> {
	public void addTermMappingContractNameList(List<String> contractNameList) {
		super.addRelationshipsList(
				Term.class,
				WorkflowHelper.TERM_MAPPING_RELATIONSHIP_NAME,
				TermMapping.class,
				WorkflowHelper.ATTRIBUTE_NAME_CONTRACT_NAME,
				contractNameList
		);
	}

	public void addOfferUriIdList(List<String> offerUriIdList) {
		super.addRelationshipsList(
				Term.class,
				WorkflowHelper.OFFER_RELATIONSHIP_NAME,
				Offer.class,
				WorkflowHelper.ATTRIBUTE_NAME_URI_ID,
				offerUriIdList
		);
	}

	@Override
	@JsonIgnore
	public void mergeEntity() {
		if (getStoredEntity() != null) {
			//update
			if (DataMaster.MEDIA_MANAGER.equals(getStoredEntity().getDataMaster())) {
				setEntity(getStoredEntity());
				return;
			}
			Term mergedTerm = getStoredEntity();
			// Preserve the price map
			Map<String, Map<String, String>> price = mergedTerm.getPrice();
			mergedTerm.setPrice(price);
		}
		super.mergeEntity();
	}

	@Override
	@JsonIgnore
	protected List<ImageContent> getImageContentList(Term entity) {
		return null;
	}

	@Override
	@JsonIgnore
	protected void setImageContentList(
			Term entity,
			List<ImageContent> imageContentList
	) {
	}

	@Override
	@JsonIgnore
	protected List<String> getCompareLinks() {
		return new ArrayList<>();
	}
}
