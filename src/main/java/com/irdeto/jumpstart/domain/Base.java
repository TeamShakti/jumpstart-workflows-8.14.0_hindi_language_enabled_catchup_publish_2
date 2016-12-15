
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
 * base
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "uriId",
    "startDateTime",
    "endDateTime",
    "provider",
    "lastPublishDateTime",
    "lastModifiedDateTime",
    "_links"
})
public class Base
    extends BaseEntity
    implements Serializable
{

    /**
     * URI ID
     * <p>
     * 
     * 
     */
    @JsonProperty("uriId")
    private String uriId;
    /**
     * Start Date/Time
     * <p>
     * 
     * 
     */
    @JsonProperty("startDateTime")
    private DateTime startDateTime;
    /**
     * End Date/Time
     * <p>
     * 
     * 
     */
    @JsonProperty("endDateTime")
    private DateTime endDateTime;
    /**
     * Provider
     * <p>
     * 
     * 
     */
    @JsonProperty("provider")
    private String provider;
    /**
     * Last Publish Date/Time
     * <p>
     * 
     * 
     */
    @JsonProperty("lastPublishDateTime")
    private DateTime lastPublishDateTime;
    /**
     * Last Modified Date/Time
     * <p>
     * 
     * 
     */
    @JsonProperty("lastModifiedDateTime")
    private DateTime lastModifiedDateTime;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -9153406196287590993L;

    /**
     * URI ID
     * <p>
     * 
     * 
     * @return
     *     The uriId
     */
    @JsonProperty("uriId")
    public String getUriId() {
        return uriId;
    }

    /**
     * URI ID
     * <p>
     * 
     * 
     * @param uriId
     *     The uriId
     */
    @JsonProperty("uriId")
    public void setUriId(String uriId) {
        this.uriId = uriId;
    }

    public Base withUriId(String uriId) {
        this.uriId = uriId;
        return this;
    }

    /**
     * Start Date/Time
     * <p>
     * 
     * 
     * @return
     *     The startDateTime
     */
    @JsonProperty("startDateTime")
    public DateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Start Date/Time
     * <p>
     * 
     * 
     * @param startDateTime
     *     The startDateTime
     */
    @JsonProperty("startDateTime")
    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Base withStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    /**
     * End Date/Time
     * <p>
     * 
     * 
     * @return
     *     The endDateTime
     */
    @JsonProperty("endDateTime")
    public DateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * End Date/Time
     * <p>
     * 
     * 
     * @param endDateTime
     *     The endDateTime
     */
    @JsonProperty("endDateTime")
    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Base withEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    /**
     * Provider
     * <p>
     * 
     * 
     * @return
     *     The provider
     */
    @JsonProperty("provider")
    public String getProvider() {
        return provider;
    }

    /**
     * Provider
     * <p>
     * 
     * 
     * @param provider
     *     The provider
     */
    @JsonProperty("provider")
    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Base withProvider(String provider) {
        this.provider = provider;
        return this;
    }

    /**
     * Last Publish Date/Time
     * <p>
     * 
     * 
     * @return
     *     The lastPublishDateTime
     */
    @JsonProperty("lastPublishDateTime")
    public DateTime getLastPublishDateTime() {
        return lastPublishDateTime;
    }

    /**
     * Last Publish Date/Time
     * <p>
     * 
     * 
     * @param lastPublishDateTime
     *     The lastPublishDateTime
     */
    @JsonProperty("lastPublishDateTime")
    public void setLastPublishDateTime(DateTime lastPublishDateTime) {
        this.lastPublishDateTime = lastPublishDateTime;
    }

    public Base withLastPublishDateTime(DateTime lastPublishDateTime) {
        this.lastPublishDateTime = lastPublishDateTime;
        return this;
    }

    /**
     * Last Modified Date/Time
     * <p>
     * 
     * 
     * @return
     *     The lastModifiedDateTime
     */
    @JsonProperty("lastModifiedDateTime")
    public DateTime getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    /**
     * Last Modified Date/Time
     * <p>
     * 
     * 
     * @param lastModifiedDateTime
     *     The lastModifiedDateTime
     */
    @JsonProperty("lastModifiedDateTime")
    public void setLastModifiedDateTime(DateTime lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public Base withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
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

    public Base withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public Base withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public Base withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public Base withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public Base withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public Base withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public Base withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public Base withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public Base withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public Base withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public Base withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(uriId).append(startDateTime).append(endDateTime).append(provider).append(lastPublishDateTime).append(lastModifiedDateTime).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Base) == false) {
            return false;
        }
        Base rhs = ((Base) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(uriId, rhs.uriId).append(startDateTime, rhs.startDateTime).append(endDateTime, rhs.endDateTime).append(provider, rhs.provider).append(lastPublishDateTime, rhs.lastPublishDateTime).append(lastModifiedDateTime, rhs.lastModifiedDateTime).append(links, rhs.links).isEquals();
    }

}
