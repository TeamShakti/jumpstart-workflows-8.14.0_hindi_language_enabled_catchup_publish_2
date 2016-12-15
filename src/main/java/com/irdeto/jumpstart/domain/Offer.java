
package com.irdeto.jumpstart.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;


/**
 * Offer
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "billingId",
    "offertype",
    "offerpackageid",
    "_links"
})
public class Offer
    extends BaseMetadata
    implements Serializable
{

    /**
     * Billing ID
     * <p>
     * 
     * 
     */
    @JsonProperty("billingId")
    private String billingId;
    /**
     * Offer Type
     * <p>
     * 
     * 
     */
    @JsonProperty("offertype")
    private String offertype;
    /**
     * Offer Package ID
     * <p>
     * 
     * 
     */
    @JsonProperty("offerpackageid")
    private String offerpackageid;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -9104197374659649886L;

    /**
     * Billing ID
     * <p>
     * 
     * 
     * @return
     *     The billingId
     */
    @JsonProperty("billingId")
    public String getBillingId() {
        return billingId;
    }

    /**
     * Billing ID
     * <p>
     * 
     * 
     * @param billingId
     *     The billingId
     */
    @JsonProperty("billingId")
    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

    public Offer withBillingId(String billingId) {
        this.billingId = billingId;
        return this;
    }

    /**
     * Offer Type
     * <p>
     * 
     * 
     * @return
     *     The offertype
     */
    @JsonProperty("offertype")
    public String getOffertype() {
        return offertype;
    }

    /**
     * Offer Type
     * <p>
     * 
     * 
     * @param offertype
     *     The offertype
     */
    @JsonProperty("offertype")
    public void setOffertype(String offertype) {
        this.offertype = offertype;
    }

    public Offer withOffertype(String offertype) {
        this.offertype = offertype;
        return this;
    }

    /**
     * Offer Package ID
     * <p>
     * 
     * 
     * @return
     *     The offerpackageid
     */
    @JsonProperty("offerpackageid")
    public String getOfferpackageid() {
        return offerpackageid;
    }

    /**
     * Offer Package ID
     * <p>
     * 
     * 
     * @param offerpackageid
     *     The offerpackageid
     */
    @JsonProperty("offerpackageid")
    public void setOfferpackageid(String offerpackageid) {
        this.offerpackageid = offerpackageid;
    }

    public Offer withOfferpackageid(String offerpackageid) {
        this.offerpackageid = offerpackageid;
        return this;
    }

    /**
     * Links
     * <p>
     * 
     * 
     * @return
     *     The links
     */
    @JsonProperty("_links")
    public List<Link> getLinks() {
        return links;
    }

    /**
     * Links
     * <p>
     * 
     * 
     * @param links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Offer withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public Offer withMetadataApproved(Boolean metadataApproved) {
        super.withMetadataApproved(metadataApproved);
        return this;
    }

    @Override
    public Offer withDataMaster(BaseMetadata.DataMaster dataMaster) {
        super.withDataMaster(dataMaster);
        return this;
    }

    @Override
    public Offer withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public Offer withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public Offer withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public Offer withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public Offer withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public Offer withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public Offer withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public Offer withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public Offer withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public Offer withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public Offer withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public Offer withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public Offer withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public Offer withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public Offer withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public Offer withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(billingId).append(offertype).append(offerpackageid).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Offer) == false) {
            return false;
        }
        Offer rhs = ((Offer) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(billingId, rhs.billingId).append(offertype, rhs.offertype).append(offerpackageid, rhs.offerpackageid).append(links, rhs.links).isEquals();
    }

}
