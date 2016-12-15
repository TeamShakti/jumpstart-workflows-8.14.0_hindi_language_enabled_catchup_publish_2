
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
 * Encode Profile
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "liveUri",
    "_links"
})
public class EncodeProfile
    extends BaseProtection
    implements Serializable
{

    /**
     * Encoder Live URI
     * <p>
     * 
     * 
     */
    @JsonProperty("liveUri")
    private String liveUri;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -6829025304824506089L;

    /**
     * Encoder Live URI
     * <p>
     * 
     * 
     * @return
     *     The liveUri
     */
    @JsonProperty("liveUri")
    public String getLiveUri() {
        return liveUri;
    }

    /**
     * Encoder Live URI
     * <p>
     * 
     * 
     * @param liveUri
     *     The liveUri
     */
    @JsonProperty("liveUri")
    public void setLiveUri(String liveUri) {
        this.liveUri = liveUri;
    }

    public EncodeProfile withLiveUri(String liveUri) {
        this.liveUri = liveUri;
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

    public EncodeProfile withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public EncodeProfile withName(String name) {
        super.withName(name);
        return this;
    }

    @Override
    public EncodeProfile withProtectionType(BaseProtection.ProtectionType protectionType) {
        super.withProtectionType(protectionType);
        return this;
    }

    @Override
    public EncodeProfile withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public EncodeProfile withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public EncodeProfile withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public EncodeProfile withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public EncodeProfile withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public EncodeProfile withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public EncodeProfile withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public EncodeProfile withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public EncodeProfile withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public EncodeProfile withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(liveUri).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EncodeProfile) == false) {
            return false;
        }
        EncodeProfile rhs = ((EncodeProfile) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(liveUri, rhs.liveUri).append(links, rhs.links).isEquals();
    }

}
