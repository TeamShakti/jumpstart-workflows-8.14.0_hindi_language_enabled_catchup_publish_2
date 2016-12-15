
package com.irdeto.jumpstart.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.annotate.JsonValue;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;


/**
 * Term Mapping
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "contractName",
    "policyGroupId",
    "policyType",
    "policyId",
    "contentType",
    "duration",
    "_links"
})
public class TermMapping
    extends BaseEntity
    implements Serializable
{

    /**
     * Contract Name
     * <p>
     * 
     * 
     */
    @JsonProperty("contractName")
    private String contractName;
    /**
     * Policy Group ID
     * <p>
     * 
     * 
     */
    @JsonProperty("policyGroupId")
    private Integer policyGroupId;
    /**
     * Policy Type
     * <p>
     * 
     * 
     */
    @JsonProperty("policyType")
    private TermMapping.PolicyType policyType;
    /**
     * Policy ID
     * <p>
     * 
     * 
     */
    @JsonProperty("policyId")
    private Integer policyId;
    /**
     * Content Type
     * <p>
     * 
     * 
     */
    @JsonProperty("contentType")
    private List<ContentType> contentType = new ArrayList<ContentType>();
    /**
     * Duration
     * <p>
     * 
     * 
     */
    @JsonProperty("duration")
    private Integer duration;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 3642540640306955446L;

    /**
     * Contract Name
     * <p>
     * 
     * 
     * @return
     *     The contractName
     */
    @JsonProperty("contractName")
    public String getContractName() {
        return contractName;
    }

    /**
     * Contract Name
     * <p>
     * 
     * 
     * @param contractName
     *     The contractName
     */
    @JsonProperty("contractName")
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public TermMapping withContractName(String contractName) {
        this.contractName = contractName;
        return this;
    }

    /**
     * Policy Group ID
     * <p>
     * 
     * 
     * @return
     *     The policyGroupId
     */
    @JsonProperty("policyGroupId")
    public Integer getPolicyGroupId() {
        return policyGroupId;
    }

    /**
     * Policy Group ID
     * <p>
     * 
     * 
     * @param policyGroupId
     *     The policyGroupId
     */
    @JsonProperty("policyGroupId")
    public void setPolicyGroupId(Integer policyGroupId) {
        this.policyGroupId = policyGroupId;
    }

    public TermMapping withPolicyGroupId(Integer policyGroupId) {
        this.policyGroupId = policyGroupId;
        return this;
    }

    /**
     * Policy Type
     * <p>
     * 
     * 
     * @return
     *     The policyType
     */
    @JsonProperty("policyType")
    public TermMapping.PolicyType getPolicyType() {
        return policyType;
    }

    /**
     * Policy Type
     * <p>
     * 
     * 
     * @param policyType
     *     The policyType
     */
    @JsonProperty("policyType")
    public void setPolicyType(TermMapping.PolicyType policyType) {
        this.policyType = policyType;
    }

    public TermMapping withPolicyType(TermMapping.PolicyType policyType) {
        this.policyType = policyType;
        return this;
    }

    /**
     * Policy ID
     * <p>
     * 
     * 
     * @return
     *     The policyId
     */
    @JsonProperty("policyId")
    public Integer getPolicyId() {
        return policyId;
    }

    /**
     * Policy ID
     * <p>
     * 
     * 
     * @param policyId
     *     The policyId
     */
    @JsonProperty("policyId")
    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public TermMapping withPolicyId(Integer policyId) {
        this.policyId = policyId;
        return this;
    }

    /**
     * Content Type
     * <p>
     * 
     * 
     * @return
     *     The contentType
     */
    @JsonProperty("contentType")
    public List<ContentType> getContentType() {
        return contentType;
    }

    /**
     * Content Type
     * <p>
     * 
     * 
     * @param contentType
     *     The contentType
     */
    @JsonProperty("contentType")
    public void setContentType(List<ContentType> contentType) {
        this.contentType = contentType;
    }

    public TermMapping withContentType(List<ContentType> contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * Duration
     * <p>
     * 
     * 
     * @return
     *     The duration
     */
    @JsonProperty("duration")
    public Integer getDuration() {
        return duration;
    }

    /**
     * Duration
     * <p>
     * 
     * 
     * @param duration
     *     The duration
     */
    @JsonProperty("duration")
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public TermMapping withDuration(Integer duration) {
        this.duration = duration;
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

    public TermMapping withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public TermMapping withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public TermMapping withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public TermMapping withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public TermMapping withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public TermMapping withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public TermMapping withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public TermMapping withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public TermMapping withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public TermMapping withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public TermMapping withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(contractName).append(policyGroupId).append(policyType).append(policyId).append(contentType).append(duration).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TermMapping) == false) {
            return false;
        }
        TermMapping rhs = ((TermMapping) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(contractName, rhs.contractName).append(policyGroupId, rhs.policyGroupId).append(policyType, rhs.policyType).append(policyId, rhs.policyId).append(contentType, rhs.contentType).append(duration, rhs.duration).append(links, rhs.links).isEquals();
    }

    public enum PolicyType {

        FREE("free"),
        TVOD("tvod"),
        SVOD("svod");
        private final String value;
        private final static Map<String, TermMapping.PolicyType> CONSTANTS = new HashMap<String, TermMapping.PolicyType>();

        static {
            for (TermMapping.PolicyType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private PolicyType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static TermMapping.PolicyType fromValue(String value) {
            TermMapping.PolicyType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
