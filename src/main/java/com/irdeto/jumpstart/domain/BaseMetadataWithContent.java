
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
 * baseMetadataWithContent
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "contentApproved",
    "_links"
})
public class BaseMetadataWithContent
    extends BaseMetadataWithTitle
    implements Serializable
{

    /**
     * Content Approved?
     * <p>
     * 
     * 
     */
    @JsonProperty("contentApproved")
    private Boolean contentApproved;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -1196497917644308337L;

    /**
     * Content Approved?
     * <p>
     * 
     * 
     * @return
     *     The contentApproved
     */
    @JsonProperty("contentApproved")
    public Boolean getContentApproved() {
        return contentApproved;
    }

    /**
     * Content Approved?
     * <p>
     * 
     * 
     * @param contentApproved
     *     The contentApproved
     */
    @JsonProperty("contentApproved")
    public void setContentApproved(Boolean contentApproved) {
        this.contentApproved = contentApproved;
    }

    public BaseMetadataWithContent withContentApproved(Boolean contentApproved) {
        this.contentApproved = contentApproved;
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

    public BaseMetadataWithContent withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public BaseMetadataWithContent withTitleSortName(Locale titleSortName) {
        super.withTitleSortName(titleSortName);
        return this;
    }

    @Override
    public BaseMetadataWithContent withTitleBrief(Locale titleBrief) {
        super.withTitleBrief(titleBrief);
        return this;
    }

    @Override
    public BaseMetadataWithContent withTitleMedium(Locale titleMedium) {
        super.withTitleMedium(titleMedium);
        return this;
    }

    @Override
    public BaseMetadataWithContent withTitleLong(Locale titleLong) {
        super.withTitleLong(titleLong);
        return this;
    }

    @Override
    public BaseMetadataWithContent withSummaryShort(Locale summaryShort) {
        super.withSummaryShort(summaryShort);
        return this;
    }

    @Override
    public BaseMetadataWithContent withSummaryMedium(Locale summaryMedium) {
        super.withSummaryMedium(summaryMedium);
        return this;
    }

    @Override
    public BaseMetadataWithContent withSummaryLong(Locale summaryLong) {
        super.withSummaryLong(summaryLong);
        return this;
    }

    @Override
    public BaseMetadataWithContent withCountryOfOrigin(List<Object> countryOfOrigin) {
        super.withCountryOfOrigin(countryOfOrigin);
        return this;
    }

    @Override
    public BaseMetadataWithContent withShowType(BaseMetadataWithTitle.ShowType showType) {
        super.withShowType(showType);
        return this;
    }

    @Override
    public BaseMetadataWithContent withKeywords(String keywords) {
        super.withKeywords(keywords);
        return this;
    }

    @Override
    public BaseMetadataWithContent withMetadataApproved(Boolean metadataApproved) {
        super.withMetadataApproved(metadataApproved);
        return this;
    }

    @Override
    public BaseMetadataWithContent withDataMaster(com.irdeto.jumpstart.domain.BaseMetadata.DataMaster dataMaster) {
        super.withDataMaster(dataMaster);
        return this;
    }

    @Override
    public BaseMetadataWithContent withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public BaseMetadataWithContent withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public BaseMetadataWithContent withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public BaseMetadataWithContent withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public BaseMetadataWithContent withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public BaseMetadataWithContent withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public BaseMetadataWithContent withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public BaseMetadataWithContent withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public BaseMetadataWithContent withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public BaseMetadataWithContent withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public BaseMetadataWithContent withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public BaseMetadataWithContent withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public BaseMetadataWithContent withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public BaseMetadataWithContent withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public BaseMetadataWithContent withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public BaseMetadataWithContent withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(contentApproved).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BaseMetadataWithContent) == false) {
            return false;
        }
        BaseMetadataWithContent rhs = ((BaseMetadataWithContent) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(contentApproved, rhs.contentApproved).append(links, rhs.links).isEquals();
    }

}
