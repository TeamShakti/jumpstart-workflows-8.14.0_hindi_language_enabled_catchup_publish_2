
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
 * Rating
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "ratingLabel",
    "minimumAge",
    "_links"
})
public class Rating
    extends Base
    implements Serializable
{

    /**
     * Rating
     * <p>
     * 
     * 
     */
    @JsonProperty("ratingLabel")
    private String ratingLabel;
    /**
     * Minimum Age
     * <p>
     * 
     * 
     */
    @JsonProperty("minimumAge")
    private Integer minimumAge;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -2863768868040982611L;

    /**
     * Rating
     * <p>
     * 
     * 
     * @return
     *     The ratingLabel
     */
    @JsonProperty("ratingLabel")
    public String getRatingLabel() {
        return ratingLabel;
    }

    /**
     * Rating
     * <p>
     * 
     * 
     * @param ratingLabel
     *     The ratingLabel
     */
    @JsonProperty("ratingLabel")
    public void setRatingLabel(String ratingLabel) {
        this.ratingLabel = ratingLabel;
    }

    public Rating withRatingLabel(String ratingLabel) {
        this.ratingLabel = ratingLabel;
        return this;
    }

    /**
     * Minimum Age
     * <p>
     * 
     * 
     * @return
     *     The minimumAge
     */
    @JsonProperty("minimumAge")
    public Integer getMinimumAge() {
        return minimumAge;
    }

    /**
     * Minimum Age
     * <p>
     * 
     * 
     * @param minimumAge
     *     The minimumAge
     */
    @JsonProperty("minimumAge")
    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Rating withMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
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

    public Rating withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public Rating withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public Rating withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public Rating withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public Rating withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public Rating withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public Rating withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public Rating withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public Rating withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public Rating withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public Rating withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public Rating withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public Rating withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public Rating withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public Rating withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public Rating withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public Rating withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(ratingLabel).append(minimumAge).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Rating) == false) {
            return false;
        }
        Rating rhs = ((Rating) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(ratingLabel, rhs.ratingLabel).append(minimumAge, rhs.minimumAge).append(links, rhs.links).isEquals();
    }

}
