
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
 * Daily Schedule Slots
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "date",
    "scheduleSlots",
    "_links"
})
public class ChannelDay
    extends Base
    implements Serializable
{

    /**
     * Slots Date
     * <p>
     * 
     * 
     */
    @JsonProperty("date")
    private DateTime date;
    @JsonProperty("scheduleSlots")
    private List<ScheduleSlot> scheduleSlots = new ArrayList<ScheduleSlot>();
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 79318608287295575L;

    /**
     * Slots Date
     * <p>
     * 
     * 
     * @return
     *     The date
     */
    @JsonProperty("date")
    public DateTime getDate() {
        return date;
    }

    /**
     * Slots Date
     * <p>
     * 
     * 
     * @param date
     *     The date
     */
    @JsonProperty("date")
    public void setDate(DateTime date) {
        this.date = date;
    }

    public ChannelDay withDate(DateTime date) {
        this.date = date;
        return this;
    }

    /**
     * 
     * @return
     *     The scheduleSlots
     */
    @JsonProperty("scheduleSlots")
    public List<ScheduleSlot> getScheduleSlots() {
        return scheduleSlots;
    }

    /**
     * 
     * @param scheduleSlots
     *     The scheduleSlots
     */
    @JsonProperty("scheduleSlots")
    public void setScheduleSlots(List<ScheduleSlot> scheduleSlots) {
        this.scheduleSlots = scheduleSlots;
    }

    public ChannelDay withScheduleSlots(List<ScheduleSlot> scheduleSlots) {
        this.scheduleSlots = scheduleSlots;
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

    public ChannelDay withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public ChannelDay withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public ChannelDay withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public ChannelDay withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public ChannelDay withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public ChannelDay withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public ChannelDay withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public ChannelDay withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public ChannelDay withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public ChannelDay withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public ChannelDay withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public ChannelDay withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public ChannelDay withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public ChannelDay withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public ChannelDay withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public ChannelDay withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public ChannelDay withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(date).append(scheduleSlots).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ChannelDay) == false) {
            return false;
        }
        ChannelDay rhs = ((ChannelDay) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(date, rhs.date).append(scheduleSlots, rhs.scheduleSlots).append(links, rhs.links).isEquals();
    }

}
