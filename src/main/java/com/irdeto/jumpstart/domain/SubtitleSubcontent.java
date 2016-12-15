
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
 * Subtitle Subcontent
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "_links"
})
public class SubtitleSubcontent
    extends Subcontent
    implements Serializable
{

    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 3590214707918135242L;

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

    public SubtitleSubcontent withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public SubtitleSubcontent withConsumerUrl(String consumerUrl) {
        super.withConsumerUrl(consumerUrl);
        return this;
    }

    @Override
    public SubtitleSubcontent withContentFileSize(String contentFileSize) {
        super.withContentFileSize(contentFileSize);
        return this;
    }

    @Override
    public SubtitleSubcontent withContentCheckSum(String contentCheckSum) {
        super.withContentCheckSum(contentCheckSum);
        return this;
    }

    @Override
    public SubtitleSubcontent withSourcePath(String sourcePath) {
        super.withSourcePath(sourcePath);
        return this;
    }

    @Override
    public SubtitleSubcontent withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public SubtitleSubcontent withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public SubtitleSubcontent withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public SubtitleSubcontent withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public SubtitleSubcontent withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public SubtitleSubcontent withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public SubtitleSubcontent withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public SubtitleSubcontent withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public SubtitleSubcontent withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public SubtitleSubcontent withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SubtitleSubcontent) == false) {
            return false;
        }
        SubtitleSubcontent rhs = ((SubtitleSubcontent) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(links, rhs.links).isEquals();
    }

}
