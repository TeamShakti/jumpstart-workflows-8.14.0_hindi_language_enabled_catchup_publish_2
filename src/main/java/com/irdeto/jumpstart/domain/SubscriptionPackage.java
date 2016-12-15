
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
 * Subscription Package
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "smsPackageId",
    "alacarte",
    "imageContent",
    "_links"
})
public class SubscriptionPackage
    extends BaseMetadataWithContent
    implements Serializable
{

    /**
     * SMS Package ID
     * <p>
     * 
     * 
     */
    @JsonProperty("smsPackageId")
    private String smsPackageId;
    /**
     * A-la-carte?
     * <p>
     * 
     * 
     */
    @JsonProperty("alacarte")
    private Boolean alacarte;
    @JsonProperty("imageContent")
    private List<ImageContent> imageContent = new ArrayList<ImageContent>();
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -7165660421303439237L;

    /**
     * SMS Package ID
     * <p>
     * 
     * 
     * @return
     *     The smsPackageId
     */
    @JsonProperty("smsPackageId")
    public String getSmsPackageId() {
        return smsPackageId;
    }

    /**
     * SMS Package ID
     * <p>
     * 
     * 
     * @param smsPackageId
     *     The smsPackageId
     */
    @JsonProperty("smsPackageId")
    public void setSmsPackageId(String smsPackageId) {
        this.smsPackageId = smsPackageId;
    }

    public SubscriptionPackage withSmsPackageId(String smsPackageId) {
        this.smsPackageId = smsPackageId;
        return this;
    }

    /**
     * A-la-carte?
     * <p>
     * 
     * 
     * @return
     *     The alacarte
     */
    @JsonProperty("alacarte")
    public Boolean getAlacarte() {
        return alacarte;
    }

    /**
     * A-la-carte?
     * <p>
     * 
     * 
     * @param alacarte
     *     The alacarte
     */
    @JsonProperty("alacarte")
    public void setAlacarte(Boolean alacarte) {
        this.alacarte = alacarte;
    }

    public SubscriptionPackage withAlacarte(Boolean alacarte) {
        this.alacarte = alacarte;
        return this;
    }

    /**
     * 
     * @return
     *     The imageContent
     */
    @JsonProperty("imageContent")
    public List<ImageContent> getImageContent() {
        return imageContent;
    }

    /**
     * 
     * @param imageContent
     *     The imageContent
     */
    @JsonProperty("imageContent")
    public void setImageContent(List<ImageContent> imageContent) {
        this.imageContent = imageContent;
    }

    public SubscriptionPackage withImageContent(List<ImageContent> imageContent) {
        this.imageContent = imageContent;
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

    public SubscriptionPackage withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public SubscriptionPackage withContentApproved(Boolean contentApproved) {
        super.withContentApproved(contentApproved);
        return this;
    }

    @Override
    public SubscriptionPackage withTitleSortName(Locale titleSortName) {
        super.withTitleSortName(titleSortName);
        return this;
    }

    @Override
    public SubscriptionPackage withTitleBrief(Locale titleBrief) {
        super.withTitleBrief(titleBrief);
        return this;
    }

    @Override
    public SubscriptionPackage withTitleMedium(Locale titleMedium) {
        super.withTitleMedium(titleMedium);
        return this;
    }

    @Override
    public SubscriptionPackage withTitleLong(Locale titleLong) {
        super.withTitleLong(titleLong);
        return this;
    }

    @Override
    public SubscriptionPackage withSummaryShort(Locale summaryShort) {
        super.withSummaryShort(summaryShort);
        return this;
    }

    @Override
    public SubscriptionPackage withSummaryMedium(Locale summaryMedium) {
        super.withSummaryMedium(summaryMedium);
        return this;
    }

    @Override
    public SubscriptionPackage withSummaryLong(Locale summaryLong) {
        super.withSummaryLong(summaryLong);
        return this;
    }

    @Override
    public SubscriptionPackage withCountryOfOrigin(List<Object> countryOfOrigin) {
        super.withCountryOfOrigin(countryOfOrigin);
        return this;
    }

    @Override
    public SubscriptionPackage withShowType(com.irdeto.jumpstart.domain.BaseMetadataWithTitle.ShowType showType) {
        super.withShowType(showType);
        return this;
    }

    @Override
    public SubscriptionPackage withKeywords(String keywords) {
        super.withKeywords(keywords);
        return this;
    }

    @Override
    public SubscriptionPackage withMetadataApproved(Boolean metadataApproved) {
        super.withMetadataApproved(metadataApproved);
        return this;
    }

    @Override
    public SubscriptionPackage withDataMaster(com.irdeto.jumpstart.domain.BaseMetadata.DataMaster dataMaster) {
        super.withDataMaster(dataMaster);
        return this;
    }

    @Override
    public SubscriptionPackage withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public SubscriptionPackage withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public SubscriptionPackage withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public SubscriptionPackage withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public SubscriptionPackage withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public SubscriptionPackage withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public SubscriptionPackage withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public SubscriptionPackage withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public SubscriptionPackage withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public SubscriptionPackage withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public SubscriptionPackage withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public SubscriptionPackage withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public SubscriptionPackage withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public SubscriptionPackage withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public SubscriptionPackage withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public SubscriptionPackage withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(smsPackageId).append(alacarte).append(imageContent).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SubscriptionPackage) == false) {
            return false;
        }
        SubscriptionPackage rhs = ((SubscriptionPackage) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(smsPackageId, rhs.smsPackageId).append(alacarte, rhs.alacarte).append(imageContent, rhs.imageContent).append(links, rhs.links).isEquals();
    }

}
