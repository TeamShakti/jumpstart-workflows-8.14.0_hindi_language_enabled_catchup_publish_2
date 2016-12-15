
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
 * Rating Scheme
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "classification",
    "countryOfSystem",
    "countriesIncluded",
    "_links"
})
public class RatingScheme
    extends BaseEntity
    implements Serializable
{

    /**
     * Classification System
     * <p>
     * 
     * 
     */
    @JsonProperty("classification")
    private String classification;
    /**
     * Parental Control Slider Countries
     * <p>
     * 
     * 
     */
    @JsonProperty("countryOfSystem")
    private List<Object> countryOfSystem = new ArrayList<Object>();
    /**
     * Classification Countries
     * <p>
     * 
     * 
     */
    @JsonProperty("countriesIncluded")
    private List<Object> countriesIncluded = new ArrayList<Object>();
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 1080051210864030841L;

    /**
     * Classification System
     * <p>
     * 
     * 
     * @return
     *     The classification
     */
    @JsonProperty("classification")
    public String getClassification() {
        return classification;
    }

    /**
     * Classification System
     * <p>
     * 
     * 
     * @param classification
     *     The classification
     */
    @JsonProperty("classification")
    public void setClassification(String classification) {
        this.classification = classification;
    }

    public RatingScheme withClassification(String classification) {
        this.classification = classification;
        return this;
    }

    /**
     * Parental Control Slider Countries
     * <p>
     * 
     * 
     * @return
     *     The countryOfSystem
     */
    @JsonProperty("countryOfSystem")
    public List<Object> getCountryOfSystem() {
        return countryOfSystem;
    }

    /**
     * Parental Control Slider Countries
     * <p>
     * 
     * 
     * @param countryOfSystem
     *     The countryOfSystem
     */
    @JsonProperty("countryOfSystem")
    public void setCountryOfSystem(List<Object> countryOfSystem) {
        this.countryOfSystem = countryOfSystem;
    }

    public RatingScheme withCountryOfSystem(List<Object> countryOfSystem) {
        this.countryOfSystem = countryOfSystem;
        return this;
    }

    /**
     * Classification Countries
     * <p>
     * 
     * 
     * @return
     *     The countriesIncluded
     */
    @JsonProperty("countriesIncluded")
    public List<Object> getCountriesIncluded() {
        return countriesIncluded;
    }

    /**
     * Classification Countries
     * <p>
     * 
     * 
     * @param countriesIncluded
     *     The countriesIncluded
     */
    @JsonProperty("countriesIncluded")
    public void setCountriesIncluded(List<Object> countriesIncluded) {
        this.countriesIncluded = countriesIncluded;
    }

    public RatingScheme withCountriesIncluded(List<Object> countriesIncluded) {
        this.countriesIncluded = countriesIncluded;
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

    public RatingScheme withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public RatingScheme withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public RatingScheme withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public RatingScheme withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public RatingScheme withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public RatingScheme withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public RatingScheme withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public RatingScheme withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public RatingScheme withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public RatingScheme withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public RatingScheme withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(classification).append(countryOfSystem).append(countriesIncluded).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RatingScheme) == false) {
            return false;
        }
        RatingScheme rhs = ((RatingScheme) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(classification, rhs.classification).append(countryOfSystem, rhs.countryOfSystem).append(countriesIncluded, rhs.countriesIncluded).append(links, rhs.links).isEquals();
    }

}
