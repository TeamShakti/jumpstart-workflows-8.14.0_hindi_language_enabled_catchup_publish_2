
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
 * Subtitle
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "language",
    "subcontent",
    "_links"
})
public class SubtitleContent
    extends Content
    implements Serializable
{

    /**
     * Language
     * <p>
     * 
     * 
     */
    @JsonProperty("language")
    private String language;
    @JsonProperty("subcontent")
    private List<SubtitleSubcontent> subcontent = new ArrayList<SubtitleSubcontent>();
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 434889140275710290L;

    /**
     * Language
     * <p>
     * 
     * 
     * @return
     *     The language
     */
    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    /**
     * Language
     * <p>
     * 
     * 
     * @param language
     *     The language
     */
    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    public SubtitleContent withLanguage(String language) {
        this.language = language;
        return this;
    }

    /**
     * 
     * @return
     *     The subcontent
     */
    @JsonProperty("subcontent")
    public List<SubtitleSubcontent> getSubcontent() {
        return subcontent;
    }

    /**
     * 
     * @param subcontent
     *     The subcontent
     */
    @JsonProperty("subcontent")
    public void setSubcontent(List<SubtitleSubcontent> subcontent) {
        this.subcontent = subcontent;
    }

    public SubtitleContent withSubcontent(List<SubtitleSubcontent> subcontent) {
        this.subcontent = subcontent;
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

    public SubtitleContent withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public SubtitleContent withSourceUrl(String sourceUrl) {
        super.withSourceUrl(sourceUrl);
        return this;
    }

    @Override
    public SubtitleContent withFileSize(String fileSize) {
        super.withFileSize(fileSize);
        return this;
    }

    @Override
    public SubtitleContent withCheckSum(String checkSum) {
        super.withCheckSum(checkSum);
        return this;
    }

    @Override
    public SubtitleContent withSourceVersion(Integer sourceVersion) {
        super.withSourceVersion(sourceVersion);
        return this;
    }

    @Override
    public SubtitleContent withPublishVersion(Integer publishVersion) {
        super.withPublishVersion(publishVersion);
        return this;
    }

    @Override
    public SubtitleContent withIsPublished(Boolean isPublished) {
        super.withIsPublished(isPublished);
        return this;
    }

    @Override
    public SubtitleContent withPublishedDate(DateTime publishedDate) {
        super.withPublishedDate(publishedDate);
        return this;
    }

    @Override
    public SubtitleContent withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public SubtitleContent withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public SubtitleContent withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public SubtitleContent withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public SubtitleContent withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public SubtitleContent withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public SubtitleContent withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public SubtitleContent withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public SubtitleContent withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public SubtitleContent withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public SubtitleContent withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public SubtitleContent withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public SubtitleContent withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public SubtitleContent withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public SubtitleContent withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public SubtitleContent withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(language).append(subcontent).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SubtitleContent) == false) {
            return false;
        }
        SubtitleContent rhs = ((SubtitleContent) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(language, rhs.language).append(subcontent, rhs.subcontent).append(links, rhs.links).isEquals();
    }

}
