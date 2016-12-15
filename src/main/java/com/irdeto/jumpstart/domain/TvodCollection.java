
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
 * Collection
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "imageContent",
    "_links"
})
public class TvodCollection
    extends BaseMetadataWithContent
    implements Serializable
{

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
    private final static long serialVersionUID = -2028666294340894387L;

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

    public TvodCollection withImageContent(List<ImageContent> imageContent) {
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

    public TvodCollection withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public TvodCollection withContentApproved(Boolean contentApproved) {
        super.withContentApproved(contentApproved);
        return this;
    }

    @Override
    public TvodCollection withTitleSortName(Locale titleSortName) {
        super.withTitleSortName(titleSortName);
        return this;
    }

    @Override
    public TvodCollection withTitleBrief(Locale titleBrief) {
        super.withTitleBrief(titleBrief);
        return this;
    }

    @Override
    public TvodCollection withTitleMedium(Locale titleMedium) {
        super.withTitleMedium(titleMedium);
        return this;
    }

    @Override
    public TvodCollection withTitleLong(Locale titleLong) {
        super.withTitleLong(titleLong);
        return this;
    }

    @Override
    public TvodCollection withSummaryShort(Locale summaryShort) {
        super.withSummaryShort(summaryShort);
        return this;
    }

    @Override
    public TvodCollection withSummaryMedium(Locale summaryMedium) {
        super.withSummaryMedium(summaryMedium);
        return this;
    }

    @Override
    public TvodCollection withSummaryLong(Locale summaryLong) {
        super.withSummaryLong(summaryLong);
        return this;
    }

    @Override
    public TvodCollection withCountryOfOrigin(List<Object> countryOfOrigin) {
        super.withCountryOfOrigin(countryOfOrigin);
        return this;
    }

    @Override
    public TvodCollection withShowType(com.irdeto.jumpstart.domain.BaseMetadataWithTitle.ShowType showType) {
        super.withShowType(showType);
        return this;
    }

    @Override
    public TvodCollection withKeywords(String keywords) {
        super.withKeywords(keywords);
        return this;
    }

    @Override
    public TvodCollection withMetadataApproved(Boolean metadataApproved) {
        super.withMetadataApproved(metadataApproved);
        return this;
    }

    @Override
    public TvodCollection withDataMaster(com.irdeto.jumpstart.domain.BaseMetadata.DataMaster dataMaster) {
        super.withDataMaster(dataMaster);
        return this;
    }

    @Override
    public TvodCollection withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public TvodCollection withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public TvodCollection withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public TvodCollection withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public TvodCollection withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public TvodCollection withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public TvodCollection withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public TvodCollection withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public TvodCollection withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public TvodCollection withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public TvodCollection withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public TvodCollection withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public TvodCollection withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public TvodCollection withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public TvodCollection withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public TvodCollection withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(imageContent).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TvodCollection) == false) {
            return false;
        }
        TvodCollection rhs = ((TvodCollection) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(imageContent, rhs.imageContent).append(links, rhs.links).isEquals();
    }

}
