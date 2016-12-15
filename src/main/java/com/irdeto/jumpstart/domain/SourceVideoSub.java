
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
 * Source Video Subcontent
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "transSubs",
    "protectSubs",
    "_links"
})
public class SourceVideoSub
    extends VideoSubcontent
    implements Serializable
{

    @JsonProperty("transSubs")
    private List<TransVideoSub> transSubs = new ArrayList<TransVideoSub>();
    @JsonProperty("protectSubs")
    private List<ProtectVideoSub> protectSubs = new ArrayList<ProtectVideoSub>();
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 3915271027838093124L;

    /**
     * 
     * @return
     *     The transSubs
     */
    @JsonProperty("transSubs")
    public List<TransVideoSub> getTransSubs() {
        return transSubs;
    }

    /**
     * 
     * @param transSubs
     *     The transSubs
     */
    @JsonProperty("transSubs")
    public void setTransSubs(List<TransVideoSub> transSubs) {
        this.transSubs = transSubs;
    }

    public SourceVideoSub withTransSubs(List<TransVideoSub> transSubs) {
        this.transSubs = transSubs;
        return this;
    }

    /**
     * 
     * @return
     *     The protectSubs
     */
    @JsonProperty("protectSubs")
    public List<ProtectVideoSub> getProtectSubs() {
        return protectSubs;
    }

    /**
     * 
     * @param protectSubs
     *     The protectSubs
     */
    @JsonProperty("protectSubs")
    public void setProtectSubs(List<ProtectVideoSub> protectSubs) {
        this.protectSubs = protectSubs;
    }

    public SourceVideoSub withProtectSubs(List<ProtectVideoSub> protectSubs) {
        this.protectSubs = protectSubs;
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

    public SourceVideoSub withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public SourceVideoSub withResolution(VideoSubcontent.Resolution resolution) {
        super.withResolution(resolution);
        return this;
    }

    @Override
    public SourceVideoSub withFrameRate(VideoSubcontent.FrameRate frameRate) {
        super.withFrameRate(frameRate);
        return this;
    }

    @Override
    public SourceVideoSub withCodec(VideoSubcontent.Codec codec) {
        super.withCodec(codec);
        return this;
    }

    @Override
    public SourceVideoSub withBitRate(Integer bitRate) {
        super.withBitRate(bitRate);
        return this;
    }

    @Override
    public SourceVideoSub withIsHDContent(Boolean isHDContent) {
        super.withIsHDContent(isHDContent);
        return this;
    }

    @Override
    public SourceVideoSub withProtectPolicyGroupId(String protectPolicyGroupId) {
        super.withProtectPolicyGroupId(protectPolicyGroupId);
        return this;
    }

    @Override
    public SourceVideoSub withConsumerUrl(String consumerUrl) {
        super.withConsumerUrl(consumerUrl);
        return this;
    }

    @Override
    public SourceVideoSub withContentFileSize(String contentFileSize) {
        super.withContentFileSize(contentFileSize);
        return this;
    }

    @Override
    public SourceVideoSub withContentCheckSum(String contentCheckSum) {
        super.withContentCheckSum(contentCheckSum);
        return this;
    }

    @Override
    public SourceVideoSub withSourcePath(String sourcePath) {
        super.withSourcePath(sourcePath);
        return this;
    }

    @Override
    public SourceVideoSub withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public SourceVideoSub withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public SourceVideoSub withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public SourceVideoSub withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public SourceVideoSub withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public SourceVideoSub withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public SourceVideoSub withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public SourceVideoSub withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public SourceVideoSub withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public SourceVideoSub withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(transSubs).append(protectSubs).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SourceVideoSub) == false) {
            return false;
        }
        SourceVideoSub rhs = ((SourceVideoSub) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(transSubs, rhs.transSubs).append(protectSubs, rhs.protectSubs).append(links, rhs.links).isEquals();
    }

}
