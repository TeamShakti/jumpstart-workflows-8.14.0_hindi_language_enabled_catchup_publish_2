
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
 * Protect Profile
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "required",
    "_links"
})
public class ProtectProfile
    extends BaseProtection
    implements Serializable
{

    /**
     * Required?
     * <p>
     * 
     * 
     */
    @JsonProperty("required")
    private Boolean required;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -5354375505448416854L;

    /**
     * Required?
     * <p>
     * 
     * 
     * @return
     *     The required
     */
    @JsonProperty("required")
    public Boolean getRequired() {
        return required;
    }

    /**
     * Required?
     * <p>
     * 
     * 
     * @param required
     *     The required
     */
    @JsonProperty("required")
    public void setRequired(Boolean required) {
        this.required = required;
    }

    public ProtectProfile withRequired(Boolean required) {
        this.required = required;
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

    public ProtectProfile withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public ProtectProfile withName(String name) {
        super.withName(name);
        return this;
    }

    @Override
    public ProtectProfile withProtectionType(BaseProtection.ProtectionType protectionType) {
        super.withProtectionType(protectionType);
        return this;
    }

    @Override
    public ProtectProfile withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public ProtectProfile withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public ProtectProfile withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public ProtectProfile withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public ProtectProfile withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public ProtectProfile withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public ProtectProfile withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public ProtectProfile withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public ProtectProfile withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public ProtectProfile withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(required).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ProtectProfile) == false) {
            return false;
        }
        ProtectProfile rhs = ((ProtectProfile) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(required, rhs.required).append(links, rhs.links).isEquals();
    }

}
