
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
 * Transcoded Video Subcontent
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "_links"
})
public class TransVideoSub
    extends VideoSubcontent
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
    private final static long serialVersionUID = 4690753505059541553L;

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

    public TransVideoSub withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public TransVideoSub withResolution(VideoSubcontent.Resolution resolution) {
        super.withResolution(resolution);
        return this;
    }

    @Override
    public TransVideoSub withFrameRate(VideoSubcontent.FrameRate frameRate) {
        super.withFrameRate(frameRate);
        return this;
    }

    @Override
    public TransVideoSub withCodec(VideoSubcontent.Codec codec) {
        super.withCodec(codec);
        return this;
    }

    @Override
    public TransVideoSub withBitRate(Integer bitRate) {
        super.withBitRate(bitRate);
        return this;
    }

    @Override
    public TransVideoSub withIsHDContent(Boolean isHDContent) {
        super.withIsHDContent(isHDContent);
        return this;
    }

    @Override
    public TransVideoSub withProtectPolicyGroupId(String protectPolicyGroupId) {
        super.withProtectPolicyGroupId(protectPolicyGroupId);
        return this;
    }

    @Override
    public TransVideoSub withConsumerUrl(String consumerUrl) {
        super.withConsumerUrl(consumerUrl);
        return this;
    }

    @Override
    public TransVideoSub withContentFileSize(String contentFileSize) {
        super.withContentFileSize(contentFileSize);
        return this;
    }

    @Override
    public TransVideoSub withContentCheckSum(String contentCheckSum) {
        super.withContentCheckSum(contentCheckSum);
        return this;
    }

    @Override
    public TransVideoSub withSourcePath(String sourcePath) {
        super.withSourcePath(sourcePath);
        return this;
    }

    @Override
    public TransVideoSub withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public TransVideoSub withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public TransVideoSub withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public TransVideoSub withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public TransVideoSub withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public TransVideoSub withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public TransVideoSub withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public TransVideoSub withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public TransVideoSub withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public TransVideoSub withAdditionalProperties(Object additionalProperties) {
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
        if ((other instanceof TransVideoSub) == false) {
            return false;
        }
        TransVideoSub rhs = ((TransVideoSub) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(links, rhs.links).isEquals();
    }

}
