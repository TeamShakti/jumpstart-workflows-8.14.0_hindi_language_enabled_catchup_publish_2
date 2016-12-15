
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
 * Policy Profile
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "policyName",
    "policyDescription",
    "policyType",
    "entitlementStartMode",
    "entitlementDuration",
    "RequireSubscription",
    "subscriptionReference",
    "licenseProfileReference",
    "requireAuthentication",
    "priceTable",
    "subscriptionBillingInterval",
    "subscriptionGroupId",
    "subscriptionId",
    "subscriptionReleaseDate",
    "subscriptionReleaseEnd",
    "subscriptionMinimumBillingInterval",
    "subscriptionBillingEndDate",
    "numberOfDevices",
    "productTaxType",
    "billImmediately",
    "syndicateAccountId",
    "_links"
})
public class PolicyProfile
    extends BaseEntity
    implements Serializable
{

    /**
     * Policy Name
     * <p>
     * 
     * 
     */
    @JsonProperty("policyName")
    private String policyName;
    /**
     * Policy Description
     * <p>
     * 
     * 
     */
    @JsonProperty("policyDescription")
    private String policyDescription;
    /**
     * Policy Type
     * <p>
     * 
     * 
     */
    @JsonProperty("policyType")
    private PolicyProfile.PolicyType policyType;
    /**
     * Entitlement Start Mode
     * <p>
     * 
     * 
     */
    @JsonProperty("entitlementStartMode")
    private String entitlementStartMode;
    /**
     * Entitlement Duration
     * <p>
     * 
     * 
     */
    @JsonProperty("entitlementDuration")
    private String entitlementDuration;
    /**
     * Require Subscription?
     * <p>
     * 
     * 
     */
    @JsonProperty("RequireSubscription")
    private Boolean requireSubscription;
    /**
     * Subscription Reference
     * <p>
     * 
     * 
     */
    @JsonProperty("subscriptionReference")
    private Integer subscriptionReference;
    /**
     * License Profile Reference
     * <p>
     * 
     * 
     */
    @JsonProperty("licenseProfileReference")
    private Integer licenseProfileReference;
    /**
     * Require Authentication?
     * <p>
     * 
     * 
     */
    @JsonProperty("requireAuthentication")
    private Boolean requireAuthentication;
    /**
     * Price Table
     * <p>
     * 
     * 
     */
    @JsonProperty("priceTable")
    private Map<String, Map<String, String>> priceTable;
    /**
     * Subscription Billing Interval 
     * <p>
     * 
     * 
     */
    @JsonProperty("subscriptionBillingInterval")
    private Integer subscriptionBillingInterval;
    /**
     * Subscription GroupId
     * <p>
     * 
     * 
     */
    @JsonProperty("subscriptionGroupId")
    private Integer subscriptionGroupId;
    /**
     * Subscription Id
     * <p>
     * 
     * 
     */
    @JsonProperty("subscriptionId")
    private Integer subscriptionId;
    /**
     * Subscription Release Date
     * <p>
     * 
     * 
     */
    @JsonProperty("subscriptionReleaseDate")
    private DateTime subscriptionReleaseDate;
    /**
     * Subscription Release End
     * <p>
     * 
     * 
     */
    @JsonProperty("subscriptionReleaseEnd")
    private DateTime subscriptionReleaseEnd;
    /**
     * Subscription Minimum Billing Interval
     * <p>
     * 
     * 
     */
    @JsonProperty("subscriptionMinimumBillingInterval")
    private Integer subscriptionMinimumBillingInterval;
    /**
     * Subscription Billing EndDate
     * <p>
     * 
     * 
     */
    @JsonProperty("subscriptionBillingEndDate")
    private DateTime subscriptionBillingEndDate;
    /**
     * Number Of Devices
     * <p>
     * 
     * 
     */
    @JsonProperty("numberOfDevices")
    private Integer numberOfDevices;
    /**
     * Product Tax Type
     * <p>
     * 
     * 
     */
    @JsonProperty("productTaxType")
    private String productTaxType;
    /**
     * Bill Immediately?
     * <p>
     * 
     * 
     */
    @JsonProperty("billImmediately")
    private Boolean billImmediately;
    /**
     * Syndicate AccountId 
     * <p>
     * 
     * 
     */
    @JsonProperty("syndicateAccountId")
    private String syndicateAccountId;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 5021945505371669785L;

    /**
     * Policy Name
     * <p>
     * 
     * 
     * @return
     *     The policyName
     */
    @JsonProperty("policyName")
    public String getPolicyName() {
        return policyName;
    }

    /**
     * Policy Name
     * <p>
     * 
     * 
     * @param policyName
     *     The policyName
     */
    @JsonProperty("policyName")
    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public PolicyProfile withPolicyName(String policyName) {
        this.policyName = policyName;
        return this;
    }

    /**
     * Policy Description
     * <p>
     * 
     * 
     * @return
     *     The policyDescription
     */
    @JsonProperty("policyDescription")
    public String getPolicyDescription() {
        return policyDescription;
    }

    /**
     * Policy Description
     * <p>
     * 
     * 
     * @param policyDescription
     *     The policyDescription
     */
    @JsonProperty("policyDescription")
    public void setPolicyDescription(String policyDescription) {
        this.policyDescription = policyDescription;
    }

    public PolicyProfile withPolicyDescription(String policyDescription) {
        this.policyDescription = policyDescription;
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
    public PolicyProfile.PolicyType getPolicyType() {
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
    public void setPolicyType(PolicyProfile.PolicyType policyType) {
        this.policyType = policyType;
    }

    public PolicyProfile withPolicyType(PolicyProfile.PolicyType policyType) {
        this.policyType = policyType;
        return this;
    }

    /**
     * Entitlement Start Mode
     * <p>
     * 
     * 
     * @return
     *     The entitlementStartMode
     */
    @JsonProperty("entitlementStartMode")
    public String getEntitlementStartMode() {
        return entitlementStartMode;
    }

    /**
     * Entitlement Start Mode
     * <p>
     * 
     * 
     * @param entitlementStartMode
     *     The entitlementStartMode
     */
    @JsonProperty("entitlementStartMode")
    public void setEntitlementStartMode(String entitlementStartMode) {
        this.entitlementStartMode = entitlementStartMode;
    }

    public PolicyProfile withEntitlementStartMode(String entitlementStartMode) {
        this.entitlementStartMode = entitlementStartMode;
        return this;
    }

    /**
     * Entitlement Duration
     * <p>
     * 
     * 
     * @return
     *     The entitlementDuration
     */
    @JsonProperty("entitlementDuration")
    public String getEntitlementDuration() {
        return entitlementDuration;
    }

    /**
     * Entitlement Duration
     * <p>
     * 
     * 
     * @param entitlementDuration
     *     The entitlementDuration
     */
    @JsonProperty("entitlementDuration")
    public void setEntitlementDuration(String entitlementDuration) {
        this.entitlementDuration = entitlementDuration;
    }

    public PolicyProfile withEntitlementDuration(String entitlementDuration) {
        this.entitlementDuration = entitlementDuration;
        return this;
    }

    /**
     * Require Subscription?
     * <p>
     * 
     * 
     * @return
     *     The requireSubscription
     */
    @JsonProperty("RequireSubscription")
    public Boolean getRequireSubscription() {
        return requireSubscription;
    }

    /**
     * Require Subscription?
     * <p>
     * 
     * 
     * @param requireSubscription
     *     The RequireSubscription
     */
    @JsonProperty("RequireSubscription")
    public void setRequireSubscription(Boolean requireSubscription) {
        this.requireSubscription = requireSubscription;
    }

    public PolicyProfile withRequireSubscription(Boolean requireSubscription) {
        this.requireSubscription = requireSubscription;
        return this;
    }

    /**
     * Subscription Reference
     * <p>
     * 
     * 
     * @return
     *     The subscriptionReference
     */
    @JsonProperty("subscriptionReference")
    public Integer getSubscriptionReference() {
        return subscriptionReference;
    }

    /**
     * Subscription Reference
     * <p>
     * 
     * 
     * @param subscriptionReference
     *     The subscriptionReference
     */
    @JsonProperty("subscriptionReference")
    public void setSubscriptionReference(Integer subscriptionReference) {
        this.subscriptionReference = subscriptionReference;
    }

    public PolicyProfile withSubscriptionReference(Integer subscriptionReference) {
        this.subscriptionReference = subscriptionReference;
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

    public PolicyProfile withLicenseProfileReference(Integer licenseProfileReference) {
        this.licenseProfileReference = licenseProfileReference;
        return this;
    }

    /**
     * Require Authentication?
     * <p>
     * 
     * 
     * @return
     *     The requireAuthentication
     */
    @JsonProperty("requireAuthentication")
    public Boolean getRequireAuthentication() {
        return requireAuthentication;
    }

    /**
     * Require Authentication?
     * <p>
     * 
     * 
     * @param requireAuthentication
     *     The requireAuthentication
     */
    @JsonProperty("requireAuthentication")
    public void setRequireAuthentication(Boolean requireAuthentication) {
        this.requireAuthentication = requireAuthentication;
    }

    public PolicyProfile withRequireAuthentication(Boolean requireAuthentication) {
        this.requireAuthentication = requireAuthentication;
        return this;
    }

    /**
     * Price Table
     * <p>
     * 
     * 
     * @return
     *     The priceTable
     */
    @JsonProperty("priceTable")
    public Map<String, Map<String, String>> getPriceTable() {
        return priceTable;
    }

    /**
     * Price Table
     * <p>
     * 
     * 
     * @param priceTable
     *     The priceTable
     */
    @JsonProperty("priceTable")
    public void setPriceTable(Map<String, Map<String, String>> priceTable) {
        this.priceTable = priceTable;
    }

    public PolicyProfile withPriceTable(Map<String, Map<String, String>> priceTable) {
        this.priceTable = priceTable;
        return this;
    }

    /**
     * Subscription Billing Interval 
     * <p>
     * 
     * 
     * @return
     *     The subscriptionBillingInterval
     */
    @JsonProperty("subscriptionBillingInterval")
    public Integer getSubscriptionBillingInterval() {
        return subscriptionBillingInterval;
    }

    /**
     * Subscription Billing Interval 
     * <p>
     * 
     * 
     * @param subscriptionBillingInterval
     *     The subscriptionBillingInterval
     */
    @JsonProperty("subscriptionBillingInterval")
    public void setSubscriptionBillingInterval(Integer subscriptionBillingInterval) {
        this.subscriptionBillingInterval = subscriptionBillingInterval;
    }

    public PolicyProfile withSubscriptionBillingInterval(Integer subscriptionBillingInterval) {
        this.subscriptionBillingInterval = subscriptionBillingInterval;
        return this;
    }

    /**
     * Subscription GroupId
     * <p>
     * 
     * 
     * @return
     *     The subscriptionGroupId
     */
    @JsonProperty("subscriptionGroupId")
    public Integer getSubscriptionGroupId() {
        return subscriptionGroupId;
    }

    /**
     * Subscription GroupId
     * <p>
     * 
     * 
     * @param subscriptionGroupId
     *     The subscriptionGroupId
     */
    @JsonProperty("subscriptionGroupId")
    public void setSubscriptionGroupId(Integer subscriptionGroupId) {
        this.subscriptionGroupId = subscriptionGroupId;
    }

    public PolicyProfile withSubscriptionGroupId(Integer subscriptionGroupId) {
        this.subscriptionGroupId = subscriptionGroupId;
        return this;
    }

    /**
     * Subscription Id
     * <p>
     * 
     * 
     * @return
     *     The subscriptionId
     */
    @JsonProperty("subscriptionId")
    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * Subscription Id
     * <p>
     * 
     * 
     * @param subscriptionId
     *     The subscriptionId
     */
    @JsonProperty("subscriptionId")
    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public PolicyProfile withSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    /**
     * Subscription Release Date
     * <p>
     * 
     * 
     * @return
     *     The subscriptionReleaseDate
     */
    @JsonProperty("subscriptionReleaseDate")
    public DateTime getSubscriptionReleaseDate() {
        return subscriptionReleaseDate;
    }

    /**
     * Subscription Release Date
     * <p>
     * 
     * 
     * @param subscriptionReleaseDate
     *     The subscriptionReleaseDate
     */
    @JsonProperty("subscriptionReleaseDate")
    public void setSubscriptionReleaseDate(DateTime subscriptionReleaseDate) {
        this.subscriptionReleaseDate = subscriptionReleaseDate;
    }

    public PolicyProfile withSubscriptionReleaseDate(DateTime subscriptionReleaseDate) {
        this.subscriptionReleaseDate = subscriptionReleaseDate;
        return this;
    }

    /**
     * Subscription Release End
     * <p>
     * 
     * 
     * @return
     *     The subscriptionReleaseEnd
     */
    @JsonProperty("subscriptionReleaseEnd")
    public DateTime getSubscriptionReleaseEnd() {
        return subscriptionReleaseEnd;
    }

    /**
     * Subscription Release End
     * <p>
     * 
     * 
     * @param subscriptionReleaseEnd
     *     The subscriptionReleaseEnd
     */
    @JsonProperty("subscriptionReleaseEnd")
    public void setSubscriptionReleaseEnd(DateTime subscriptionReleaseEnd) {
        this.subscriptionReleaseEnd = subscriptionReleaseEnd;
    }

    public PolicyProfile withSubscriptionReleaseEnd(DateTime subscriptionReleaseEnd) {
        this.subscriptionReleaseEnd = subscriptionReleaseEnd;
        return this;
    }

    /**
     * Subscription Minimum Billing Interval
     * <p>
     * 
     * 
     * @return
     *     The subscriptionMinimumBillingInterval
     */
    @JsonProperty("subscriptionMinimumBillingInterval")
    public Integer getSubscriptionMinimumBillingInterval() {
        return subscriptionMinimumBillingInterval;
    }

    /**
     * Subscription Minimum Billing Interval
     * <p>
     * 
     * 
     * @param subscriptionMinimumBillingInterval
     *     The subscriptionMinimumBillingInterval
     */
    @JsonProperty("subscriptionMinimumBillingInterval")
    public void setSubscriptionMinimumBillingInterval(Integer subscriptionMinimumBillingInterval) {
        this.subscriptionMinimumBillingInterval = subscriptionMinimumBillingInterval;
    }

    public PolicyProfile withSubscriptionMinimumBillingInterval(Integer subscriptionMinimumBillingInterval) {
        this.subscriptionMinimumBillingInterval = subscriptionMinimumBillingInterval;
        return this;
    }

    /**
     * Subscription Billing EndDate
     * <p>
     * 
     * 
     * @return
     *     The subscriptionBillingEndDate
     */
    @JsonProperty("subscriptionBillingEndDate")
    public DateTime getSubscriptionBillingEndDate() {
        return subscriptionBillingEndDate;
    }

    /**
     * Subscription Billing EndDate
     * <p>
     * 
     * 
     * @param subscriptionBillingEndDate
     *     The subscriptionBillingEndDate
     */
    @JsonProperty("subscriptionBillingEndDate")
    public void setSubscriptionBillingEndDate(DateTime subscriptionBillingEndDate) {
        this.subscriptionBillingEndDate = subscriptionBillingEndDate;
    }

    public PolicyProfile withSubscriptionBillingEndDate(DateTime subscriptionBillingEndDate) {
        this.subscriptionBillingEndDate = subscriptionBillingEndDate;
        return this;
    }

    /**
     * Number Of Devices
     * <p>
     * 
     * 
     * @return
     *     The numberOfDevices
     */
    @JsonProperty("numberOfDevices")
    public Integer getNumberOfDevices() {
        return numberOfDevices;
    }

    /**
     * Number Of Devices
     * <p>
     * 
     * 
     * @param numberOfDevices
     *     The numberOfDevices
     */
    @JsonProperty("numberOfDevices")
    public void setNumberOfDevices(Integer numberOfDevices) {
        this.numberOfDevices = numberOfDevices;
    }

    public PolicyProfile withNumberOfDevices(Integer numberOfDevices) {
        this.numberOfDevices = numberOfDevices;
        return this;
    }

    /**
     * Product Tax Type
     * <p>
     * 
     * 
     * @return
     *     The productTaxType
     */
    @JsonProperty("productTaxType")
    public String getProductTaxType() {
        return productTaxType;
    }

    /**
     * Product Tax Type
     * <p>
     * 
     * 
     * @param productTaxType
     *     The productTaxType
     */
    @JsonProperty("productTaxType")
    public void setProductTaxType(String productTaxType) {
        this.productTaxType = productTaxType;
    }

    public PolicyProfile withProductTaxType(String productTaxType) {
        this.productTaxType = productTaxType;
        return this;
    }

    /**
     * Bill Immediately?
     * <p>
     * 
     * 
     * @return
     *     The billImmediately
     */
    @JsonProperty("billImmediately")
    public Boolean getBillImmediately() {
        return billImmediately;
    }

    /**
     * Bill Immediately?
     * <p>
     * 
     * 
     * @param billImmediately
     *     The billImmediately
     */
    @JsonProperty("billImmediately")
    public void setBillImmediately(Boolean billImmediately) {
        this.billImmediately = billImmediately;
    }

    public PolicyProfile withBillImmediately(Boolean billImmediately) {
        this.billImmediately = billImmediately;
        return this;
    }

    /**
     * Syndicate AccountId 
     * <p>
     * 
     * 
     * @return
     *     The syndicateAccountId
     */
    @JsonProperty("syndicateAccountId")
    public String getSyndicateAccountId() {
        return syndicateAccountId;
    }

    /**
     * Syndicate AccountId 
     * <p>
     * 
     * 
     * @param syndicateAccountId
     *     The syndicateAccountId
     */
    @JsonProperty("syndicateAccountId")
    public void setSyndicateAccountId(String syndicateAccountId) {
        this.syndicateAccountId = syndicateAccountId;
    }

    public PolicyProfile withSyndicateAccountId(String syndicateAccountId) {
        this.syndicateAccountId = syndicateAccountId;
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

    public PolicyProfile withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public PolicyProfile withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public PolicyProfile withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public PolicyProfile withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public PolicyProfile withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public PolicyProfile withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public PolicyProfile withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public PolicyProfile withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public PolicyProfile withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public PolicyProfile withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public PolicyProfile withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(policyName).append(policyDescription).append(policyType).append(entitlementStartMode).append(entitlementDuration).append(requireSubscription).append(subscriptionReference).append(licenseProfileReference).append(requireAuthentication).append(priceTable).append(subscriptionBillingInterval).append(subscriptionGroupId).append(subscriptionId).append(subscriptionReleaseDate).append(subscriptionReleaseEnd).append(subscriptionMinimumBillingInterval).append(subscriptionBillingEndDate).append(numberOfDevices).append(productTaxType).append(billImmediately).append(syndicateAccountId).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PolicyProfile) == false) {
            return false;
        }
        PolicyProfile rhs = ((PolicyProfile) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(policyName, rhs.policyName).append(policyDescription, rhs.policyDescription).append(policyType, rhs.policyType).append(entitlementStartMode, rhs.entitlementStartMode).append(entitlementDuration, rhs.entitlementDuration).append(requireSubscription, rhs.requireSubscription).append(subscriptionReference, rhs.subscriptionReference).append(licenseProfileReference, rhs.licenseProfileReference).append(requireAuthentication, rhs.requireAuthentication).append(priceTable, rhs.priceTable).append(subscriptionBillingInterval, rhs.subscriptionBillingInterval).append(subscriptionGroupId, rhs.subscriptionGroupId).append(subscriptionId, rhs.subscriptionId).append(subscriptionReleaseDate, rhs.subscriptionReleaseDate).append(subscriptionReleaseEnd, rhs.subscriptionReleaseEnd).append(subscriptionMinimumBillingInterval, rhs.subscriptionMinimumBillingInterval).append(subscriptionBillingEndDate, rhs.subscriptionBillingEndDate).append(numberOfDevices, rhs.numberOfDevices).append(productTaxType, rhs.productTaxType).append(billImmediately, rhs.billImmediately).append(syndicateAccountId, rhs.syndicateAccountId).append(links, rhs.links).isEquals();
    }

    public enum PolicyType {

        FREE("Free"),
        SUBSCRIPTION("Subscription"),
        RENTAL("Rental");
        private final String value;
        private final static Map<String, PolicyProfile.PolicyType> CONSTANTS = new HashMap<String, PolicyProfile.PolicyType>();

        static {
            for (PolicyProfile.PolicyType c: values()) {
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
        public static PolicyProfile.PolicyType fromValue(String value) {
            PolicyProfile.PolicyType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
