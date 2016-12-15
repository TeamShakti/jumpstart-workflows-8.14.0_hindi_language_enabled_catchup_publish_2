package com.irdeto.jumpstart.domain.reference;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceSubscriptionPackage extends ReferenceDocumentWithEntitlementAndTitle {
	@Override
	@JsonIgnore
	public String getType() {
		return "subscriptionPackage";
	}
	
	// code by nitin
	private String smsPackageId;
	private Boolean alacarte;
    private List<ReferenceImageContent> imageContents = new ArrayList<>();

    @JsonProperty("imageContents")
    public List<ReferenceImageContent> getImageContents() {
        return imageContents;
    }
    public void setImageContents(List<ReferenceImageContent> imageContents) {
        this.imageContents = imageContents;
    }
    
    //code by nitin
    @JsonProperty("smsPackageId")
    public String getSmsPackageId() {
        return smsPackageId;
    }
    public void setSmsPackageId(String smsPackageId) {
        this.smsPackageId = smsPackageId;
    }
    
    @JsonProperty("alacarte")
    public Boolean getAlacarte() {
        return alacarte;
    }
    public void setAlacarte(Boolean alacarte) {
        this.alacarte = alacarte;
    }
}
