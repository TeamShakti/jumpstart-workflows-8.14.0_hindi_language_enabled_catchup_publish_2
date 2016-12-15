
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
 * Protected Video Subcontent
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "_links"
})
public class ProtectVideoSub
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
    private final static long serialVersionUID = 6294665216224079807L;

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

    public ProtectVideoSub withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public ProtectVideoSub withResolution(VideoSubcontent.Resolution resolution) {
        super.withResolution(resolution);
        return this;
    }

    @Override
    public ProtectVideoSub withFrameRate(VideoSubcontent.FrameRate frameRate) {
        super.withFrameRate(frameRate);
        return this;
    }

    @Override
    public ProtectVideoSub withCodec(VideoSubcontent.Codec codec) {
        super.withCodec(codec);
        return this;
    }

    @Override
    public ProtectVideoSub withBitRate(Integer bitRate) {
        super.withBitRate(bitRate);
        return this;
    }

    @Override
    public ProtectVideoSub withIsHDContent(Boolean isHDContent) {
        super.withIsHDContent(isHDContent);
        return this;
    }

    @Override
    public ProtectVideoSub withProtectPolicyGroupId(String protectPolicyGroupId) {
        super.withProtectPolicyGroupId(protectPolicyGroupId);
        return this;
    }

    @Override
    public ProtectVideoSub withConsumerUrl(String consumerUrl) {
        super.withConsumerUrl(consumerUrl);
        return this;
    }

    @Override
    public ProtectVideoSub withContentFileSize(String contentFileSize) {
        super.withContentFileSize(contentFileSize);
        return this;
    }

    @Override
    public ProtectVideoSub withContentCheckSum(String contentCheckSum) {
        super.withContentCheckSum(contentCheckSum);
        return this;
    }

    @Override
    public ProtectVideoSub withSourcePath(String sourcePath) {
        super.withSourcePath(sourcePath);
        return this;
    }

    @Override
    public ProtectVideoSub withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public ProtectVideoSub withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public ProtectVideoSub withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public ProtectVideoSub withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public ProtectVideoSub withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public ProtectVideoSub withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public ProtectVideoSub withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public ProtectVideoSub withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public ProtectVideoSub withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public ProtectVideoSub withAdditionalProperties(Object additionalProperties) {
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
        if ((other instanceof ProtectVideoSub) == false) {
            return false;
        }
        ProtectVideoSub rhs = ((ProtectVideoSub) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(links, rhs.links).isEquals();
    }

}
