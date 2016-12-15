
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
 * Policy Group Profile
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "policyGroupName",
    "policyGroupDescription",
    "licenseProfileReference",
    "_links"
})
public class PolicyGroupProfile
    extends BaseEntity
    implements Serializable
{

    /**
     * Policy Group Name 
     * <p>
     * 
     * 
     */
    @JsonProperty("policyGroupName")
    private String policyGroupName;
    /**
     * Policy Group Description  
     * <p>
     * 
     * 
     */
    @JsonProperty("policyGroupDescription")
    private String policyGroupDescription;
    /**
     * License Profile Reference
     * <p>
     * 
     * 
     */
    @JsonProperty("licenseProfileReference")
    private Integer licenseProfileReference;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -8916488629897582338L;

    /**
     * Policy Group Name 
     * <p>
     * 
     * 
     * @return
     *     The policyGroupName
     */
    @JsonProperty("policyGroupName")
    public String getPolicyGroupName() {
        return policyGroupName;
    }

    /**
     * Policy Group Name 
     * <p>
     * 
     * 
     * @param policyGroupName
     *     The policyGroupName
     */
    @JsonProperty("policyGroupName")
    public void setPolicyGroupName(String policyGroupName) {
        this.policyGroupName = policyGroupName;
    }

    public PolicyGroupProfile withPolicyGroupName(String policyGroupName) {
        this.policyGroupName = policyGroupName;
        return this;
    }

    /**
     * Policy Group Description  
     * <p>
     * 
     * 
     * @return
     *     The policyGroupDescription
     */
    @JsonProperty("policyGroupDescription")
    public String getPolicyGroupDescription() {
        return policyGroupDescription;
    }

    /**
     * Policy Group Description  
     * <p>
     * 
     * 
     * @param policyGroupDescription
     *     The policyGroupDescription
     */
    @JsonProperty("policyGroupDescription")
    public void setPolicyGroupDescription(String policyGroupDescription) {
        this.policyGroupDescription = policyGroupDescription;
    }

    public PolicyGroupProfile withPolicyGroupDescription(String policyGroupDescription) {
        this.policyGroupDescription = policyGroupDescription;
        return this;
    }

    /**
     * License Profile Reference
     * <p>
     * 
     * 
     * @return
     *     The licenseProfileReference
     */
    @JsonProperty("licenseProfileReference")
    public Integer getLicenseProfileReference() {
        return licenseProfileReference;
    }

    /**
     * License Profile Reference
     * <p>
     * 
     * 
     * @param licenseProfileReference
     *     The licenseProfileReference
     */
    @JsonProperty("licenseProfileReference")
    public void setLicenseProfileReference(Integer licenseProfileReference) {
        this.licenseProfileReference = licenseProfileReference;
    }

    public PolicyGroupProfile withLicenseProfileReference(Integer licenseProfileReference) {
        this.licenseProfileReference = licenseProfileReference;
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

    public PolicyGroupProfile withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public PolicyGroupProfile withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public PolicyGroupProfile withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public PolicyGroupProfile withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public PolicyGroupProfile withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public PolicyGroupProfile withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public PolicyGroupProfile withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public PolicyGroupProfile withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public PolicyGroupProfile withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public PolicyGroupProfile withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public PolicyGroupProfile withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(policyGroupName).append(policyGroupDescription).append(licenseProfileReference).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PolicyGroupProfile) == false) {
            return false;
        }
        PolicyGroupProfile rhs = ((PolicyGroupProfile) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(policyGroupName, rhs.policyGroupName).append(policyGroupDescription, rhs.policyGroupDescription).append(licenseProfileReference, rhs.licenseProfileReference).append(links, rhs.links).isEquals();
    }

}
