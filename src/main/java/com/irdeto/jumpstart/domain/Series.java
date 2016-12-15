
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
 * Series
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "season",
    "programCount",
    "imageContent",
    "_links"
})
public class Series
    extends BaseMetadataWithContent
    implements Serializable
{

    /**
     * Season
     * <p>
     * 
     * 
     */
    @JsonProperty("season")
    private Integer season;
    /**
     * Program Count
     * <p>
     * 
     * 
     */
    @JsonProperty("programCount")
    private Integer programCount;
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
    private final static long serialVersionUID = -8561811428626718919L;

    /**
     * Season
     * <p>
     * 
     * 
     * @return
     *     The season
     */
    @JsonProperty("season")
    public Integer getSeason() {
        return season;
    }

    /**
     * Season
     * <p>
     * 
     * 
     * @param season
     *     The season
     */
    @JsonProperty("season")
    public void setSeason(Integer season) {
        this.season = season;
    }

    public Series withSeason(Integer season) {
        this.season = season;
        return this;
    }

    /**
     * Program Count
     * <p>
     * 
     * 
     * @return
     *     The programCount
     */
    @JsonProperty("programCount")
    public Integer getProgramCount() {
        return programCount;
    }

    /**
     * Program Count
     * <p>
     * 
     * 
     * @param programCount
     *     The programCount
     */
    @JsonProperty("programCount")
    public void setProgramCount(Integer programCount) {
        this.programCount = programCount;
    }

    public Series withProgramCount(Integer programCount) {
        this.programCount = programCount;
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

    public Series withImageContent(List<ImageContent> imageContent) {
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

    public Series withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public Series withContentApproved(Boolean contentApproved) {
        super.withContentApproved(contentApproved);
        return this;
    }

    @Override
    public Series withTitleSortName(Locale titleSortName) {
        super.withTitleSortName(titleSortName);
        return this;
    }

    @Override
    public Series withTitleBrief(Locale titleBrief) {
        super.withTitleBrief(titleBrief);
        return this;
    }

    @Override
    public Series withTitleMedium(Locale titleMedium) {
        super.withTitleMedium(titleMedium);
        return this;
    }

    @Override
    public Series withTitleLong(Locale titleLong) {
        super.withTitleLong(titleLong);
        return this;
    }

    @Override
    public Series withSummaryShort(Locale summaryShort) {
        super.withSummaryShort(summaryShort);
        return this;
    }

    @Override
    public Series withSummaryMedium(Locale summaryMedium) {
        super.withSummaryMedium(summaryMedium);
        return this;
    }

    @Override
    public Series withSummaryLong(Locale summaryLong) {
        super.withSummaryLong(summaryLong);
        return this;
    }

    @Override
    public Series withCountryOfOrigin(List<Object> countryOfOrigin) {
        super.withCountryOfOrigin(countryOfOrigin);
        return this;
    }

    @Override
    public Series withShowType(com.irdeto.jumpstart.domain.BaseMetadataWithTitle.ShowType showType) {
        super.withShowType(showType);
        return this;
    }

    @Override
    public Series withKeywords(String keywords) {
        super.withKeywords(keywords);
        return this;
    }

    @Override
    public Series withMetadataApproved(Boolean metadataApproved) {
        super.withMetadataApproved(metadataApproved);
        return this;
    }

    @Override
    public Series withDataMaster(com.irdeto.jumpstart.domain.BaseMetadata.DataMaster dataMaster) {
        super.withDataMaster(dataMaster);
        return this;
    }

    @Override
    public Series withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public Series withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public Series withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public Series withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public Series withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public Series withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public Series withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public Series withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public Series withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public Series withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public Series withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public Series withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public Series withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public Series withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public Series withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public Series withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(season).append(programCount).append(imageContent).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Series) == false) {
            return false;
        }
        Series rhs = ((Series) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(season, rhs.season).append(programCount, rhs.programCount).append(imageContent, rhs.imageContent).append(links, rhs.links).isEquals();
    }

}
