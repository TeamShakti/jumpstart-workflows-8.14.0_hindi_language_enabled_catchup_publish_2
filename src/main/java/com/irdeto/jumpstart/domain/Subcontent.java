
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
 * subcontent
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "consumerUrl",
    "contentFileSize",
    "contentCheckSum",
    "sourcePath",
    "_links"
})
public class Subcontent
    extends BaseEntity
    implements Serializable
{

    /**
     * Consumer URL
     * <p>
     * 
     * 
     */
    @JsonProperty("consumerUrl")
    private String consumerUrl;
    /**
     * Content File Size
     * <p>
     * 
     * 
     */
    @JsonProperty("contentFileSize")
    private String contentFileSize;
    /**
     * Content MD5 Checksum
     * <p>
     * 
     * 
     */
    @JsonProperty("contentCheckSum")
    private String contentCheckSum;
    /**
     * Source Path
     * <p>
     * 
     * 
     */
    @JsonProperty("sourcePath")
    private String sourcePath;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -926138359167021450L;

    /**
     * Consumer URL
     * <p>
     * 
     * 
     * @return
     *     The consumerUrl
     */
    @JsonProperty("consumerUrl")
    public String getConsumerUrl() {
        return consumerUrl;
    }

    /**
     * Consumer URL
     * <p>
     * 
     * 
     * @param consumerUrl
     *     The consumerUrl
     */
    @JsonProperty("consumerUrl")
    public void setConsumerUrl(String consumerUrl) {
        this.consumerUrl = consumerUrl;
    }

    public Subcontent withConsumerUrl(String consumerUrl) {
        this.consumerUrl = consumerUrl;
        return this;
    }

    /**
     * Content File Size
     * <p>
     * 
     * 
     * @return
     *     The contentFileSize
     */
    @JsonProperty("contentFileSize")
    public String getContentFileSize() {
        return contentFileSize;
    }

    /**
     * Content File Size
     * <p>
     * 
     * 
     * @param contentFileSize
     *     The contentFileSize
     */
    @JsonProperty("contentFileSize")
    public void setContentFileSize(String contentFileSize) {
        this.contentFileSize = contentFileSize;
    }

    public Subcontent withContentFileSize(String contentFileSize) {
        this.contentFileSize = contentFileSize;
        return this;
    }

    /**
     * Content MD5 Checksum
     * <p>
     * 
     * 
     * @return
     *     The contentCheckSum
     */
    @JsonProperty("contentCheckSum")
    public String getContentCheckSum() {
        return contentCheckSum;
    }

    /**
     * Content MD5 Checksum
     * <p>
     * 
     * 
     * @param contentCheckSum
     *     The contentCheckSum
     */
    @JsonProperty("contentCheckSum")
    public void setContentCheckSum(String contentCheckSum) {
        this.contentCheckSum = contentCheckSum;
    }

    public Subcontent withContentCheckSum(String contentCheckSum) {
        this.contentCheckSum = contentCheckSum;
        return this;
    }

    /**
     * Source Path
     * <p>
     * 
     * 
     * @return
     *     The sourcePath
     */
    @JsonProperty("sourcePath")
    public String getSourcePath() {
        return sourcePath;
    }

    /**
     * Source Path
     * <p>
     * 
     * 
     * @param sourcePath
     *     The sourcePath
     */
    @JsonProperty("sourcePath")
    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public Subcontent withSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
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

    public Subcontent withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public Subcontent withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public Subcontent withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public Subcontent withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public Subcontent withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public Subcontent withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public Subcontent withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public Subcontent withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public Subcontent withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public Subcontent withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public Subcontent withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(consumerUrl).append(contentFileSize).append(contentCheckSum).append(sourcePath).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Subcontent) == false) {
            return false;
        }
        Subcontent rhs = ((Subcontent) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(consumerUrl, rhs.consumerUrl).append(contentFileSize, rhs.contentFileSize).append(contentCheckSum, rhs.contentCheckSum).append(sourcePath, rhs.sourcePath).append(links, rhs.links).isEquals();
    }

}
