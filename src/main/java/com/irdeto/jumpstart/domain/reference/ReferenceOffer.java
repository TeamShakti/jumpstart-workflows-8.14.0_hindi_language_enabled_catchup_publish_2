package com.irdeto.jumpstart.domain.reference;

import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class ReferenceOffer extends ReferenceEntity {
	// code by nitin
	 private String offerpackageid;
	 private String offertype;
	 
	 @JsonProperty("offertype")
	    public String getOffertype() {
	        return offertype;
	    }
	 public void setOffertype(String offertype) {
	        this.offertype = offertype;
	    }
	 
	 @JsonProperty("offerpackageid")
	    public String getOfferpackageid() {
	        return offerpackageid;
	    }
	 public void setOfferpackageid(String offerpackageid) {
	        this.offerpackageid = offerpackageid;
	    }
	 
}
