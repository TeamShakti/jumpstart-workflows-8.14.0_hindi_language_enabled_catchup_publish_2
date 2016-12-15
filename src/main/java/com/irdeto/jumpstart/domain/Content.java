
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
 * content
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "sourceUrl",
    "fileSize",
    "checkSum",
    "sourceVersion",
    "publishVersion",
    "isPublished",
    "publishedDate",
    "_links"
})
public class Content
    extends Base
    implements Serializable
{

    /**
     * Source URL
     * <p>
     * 
     * 
     */
    @JsonProperty("sourceUrl")
    private String sourceUrl;
    /**
     * File Size
     * <p>
     * 
     * 
     */
    @JsonProperty("fileSize")
    private String fileSize;
    /**
     * Check Sum
     * <p>
     * 
     * 
     */
    @JsonProperty("checkSum")
    private String checkSum;
    /**
     * Source Version
     * <p>
     * 
     * 
     */
    @JsonProperty("sourceVersion")
    private Integer sourceVersion;
    /**
     * Publish Version
     * <p>
     * 
     * 
     */
    @JsonProperty("publishVersion")
    private Integer publishVersion;
    /**
     * This file is published.
     * <p>
     * 
     * 
     */
    @JsonProperty("isPublished")
    private Boolean isPublished;
    /**
     * Published date
     * <p>
     * 
     * 
     */
    @JsonProperty("publishedDate")
    private DateTime publishedDate;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -2541219121768816992L;

    /**
     * Source URL
     * <p>
     * 
     * 
     * @return
     *     The sourceUrl
     */
    @JsonProperty("sourceUrl")
    public String getSourceUrl() {
        return sourceUrl;
    }

    /**
     * Source URL
     * <p>
     * 
     * 
     * @param sourceUrl
     *     The sourceUrl
     */
    @JsonProperty("sourceUrl")
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Content withSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
        return this;
    }

    /**
     * File Size
     * <p>
     * 
     * 
     * @return
     *     The fileSize
     */
    @JsonProperty("fileSize")
    public String getFileSize() {
        return fileSize;
    }

    /**
     * File Size
     * <p>
     * 
     * 
     * @param fileSize
     *     The fileSize
     */
    @JsonProperty("fileSize")
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Content withFileSize(String fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    /**
     * Check Sum
     * <p>
     * 
     * 
     * @return
     *     The checkSum
     */
    @JsonProperty("checkSum")
    public String getCheckSum() {
        return checkSum;
    }

    /**
     * Check Sum
     * <p>
     * 
     * 
     * @param checkSum
     *     The checkSum
     */
    @JsonProperty("checkSum")
    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public Content withCheckSum(String checkSum) {
        this.checkSum = checkSum;
        return this;
    }

    /**
     * Source Version
     * <p>
     * 
     * 
     * @return
     *     The sourceVersion
     */
    @JsonProperty("sourceVersion")
    public Integer getSourceVersion() {
        return sourceVersion;
    }

    /**
     * Source Version
     * <p>
     * 
     * 
     * @param sourceVersion
     *     The sourceVersion
     */
    @JsonProperty("sourceVersion")
    public void setSourceVersion(Integer sourceVersion) {
        this.sourceVersion = sourceVersion;
    }

    public Content withSourceVersion(Integer sourceVersion) {
        this.sourceVersion = sourceVersion;
        return this;
    }

    /**
     * Publish Version
     * <p>
     * 
     * 
     * @return
     *     The publishVersion
     */
    @JsonProperty("publishVersion")
    public Integer getPublishVersion() {
        return publishVersion;
    }

    /**
     * Publish Version
     * <p>
     * 
     * 
     * @param publishVersion
     *     The publishVersion
     */
    @JsonProperty("publishVersion")
    public void setPublishVersion(Integer publishVersion) {
        this.publishVersion = publishVersion;
    }

    public Content withPublishVersion(Integer publishVersion) {
        this.publishVersion = publishVersion;
        return this;
    }

    /**
     * This file is published.
     * <p>
     * 
     * 
     * @return
     *     The isPublished
     */
    @JsonProperty("isPublished")
    public Boolean getIsPublished() {
        return isPublished;
    }

    /**
     * This file is published.
     * <p>
     * 
     * 
     * @param isPublished
     *     The isPublished
     */
    @JsonProperty("isPublished")
    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Content withIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
        return this;
    }

    /**
     * Published date
     * <p>
     * 
     * 
     * @return
     *     The publishedDate
     */
    @JsonProperty("publishedDate")
    public DateTime getPublishedDate() {
        return publishedDate;
    }

    /**
     * Published date
     * <p>
     * 
     * 
     * @param publishedDate
     *     The publishedDate
     */
    @JsonProperty("publishedDate")
    public void setPublishedDate(DateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Content withPublishedDate(DateTime publishedDate) {
        this.publishedDate = publishedDate;
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

    public Content withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public Content withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public Content withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public Content withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public Content withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public Content withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public Content withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public Content withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public Content withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public Content withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public Content withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public Content withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public Content withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public Content withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public Content withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public Content withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public Content withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(sourceUrl).append(fileSize).append(checkSum).append(sourceVersion).append(publishVersion).append(isPublished).append(publishedDate).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Content) == false) {
            return false;
        }
        Content rhs = ((Content) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(sourceUrl, rhs.sourceUrl).append(fileSize, rhs.fileSize).append(checkSum, rhs.checkSum).append(sourceVersion, rhs.sourceVersion).append(publishVersion, rhs.publishVersion).append(isPublished, rhs.isPublished).append(publishedDate, rhs.publishedDate).append(links, rhs.links).isEquals();
    }

}
