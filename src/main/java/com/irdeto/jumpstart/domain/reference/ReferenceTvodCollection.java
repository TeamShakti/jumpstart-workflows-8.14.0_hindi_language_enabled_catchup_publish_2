package com.irdeto.jumpstart.domain.reference;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceTvodCollection extends ReferenceDocumentWithEntitlementAndTitle {
	@Override
	@JsonIgnore
	public String getType() {
		return "tvodCollection";
	}

    private List<ReferenceImageContent> imageContents = new ArrayList<>();

    @JsonProperty("imageContents")
    public List<ReferenceImageContent> getImageContents() {
        return imageContents;
    }
    public void setImageContents(List<ReferenceImageContent> imageContents) {
        this.imageContents = imageContents;
    }
}
