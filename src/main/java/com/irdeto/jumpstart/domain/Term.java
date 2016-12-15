
package com.irdeto.jumpstart.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;


/**
 * Term
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "contractName",
    "suggestedPrice",
    "price",
    "_links"
})
public class Term
    extends BaseMetadata
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
     * Suggested Price
     * <p>
     * 
     * 
     */
    @JsonProperty("suggestedPrice")
    private Map<String, Map<String, String>> suggestedPrice;
    /**
     * Price
     * <p>
     * 
     * 
     */
    @JsonProperty("price")
    private Map<String, Map<String, String>> price;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 8137463187064578754L;

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

    public Term withContractName(String contractName) {
        this.contractName = contractName;
        return this;
    }

    /**
     * Suggested Price
     * <p>
     * 
     * 
     * @return
     *     The suggestedPrice
     */
    @JsonProperty("suggestedPrice")
    public Map<String, Map<String, String>> getSuggestedPrice() {
        return suggestedPrice;
    }

    /**
     * Suggested Price
     * <p>
     * 
     * 
     * @param suggestedPrice
     *     The suggestedPrice
     */
    @JsonProperty("suggestedPrice")
    public void setSuggestedPrice(Map<String, Map<String, String>> suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public Term withSuggestedPrice(Map<String, Map<String, String>> suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
        return this;
    }

    /**
     * Price
     * <p>
     * 
     * 
     * @return
     *     The price
     */
    @JsonProperty("price")
    public Map<String, Map<String, String>> getPrice() {
        return price;
    }

    /**
     * Price
     * <p>
     * 
     * 
     * @param price
     *     The price
     */
    @JsonProperty("price")
    public void setPrice(Map<String, Map<String, String>> price) {
        this.price = price;
    }

    public Term withPrice(Map<String, Map<String, String>> price) {
        this.price = price;
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

    public Term withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public Term withMetadataApproved(Boolean metadataApproved) {
        super.withMetadataApproved(metadataApproved);
        return this;
    }

    @Override
    public Term withDataMaster(BaseMetadata.DataMaster dataMaster) {
        super.withDataMaster(dataMaster);
        return this;
    }

    @Override
    public Term withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public Term withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public Term withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public Term withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public Term withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public Term withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public Term withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public Term withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public Term withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public Term withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public Term withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public Term withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public Term withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public Term withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public Term withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public Term withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(contractName).append(suggestedPrice).append(price).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Term) == false) {
            return false;
        }
        Term rhs = ((Term) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(contractName, rhs.contractName).append(suggestedPrice, rhs.suggestedPrice).append(price, rhs.price).append(links, rhs.links).isEquals();
    }

}
