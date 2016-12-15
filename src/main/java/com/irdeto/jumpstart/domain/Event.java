
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
 * Event
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "eventBroadcastDate",
    "eventBroadcastEndDate",
    "screenFormat",
    "imageContent",
    "contributors",
    "_links"
})
public class Event
    extends BaseMetadataWithContent
    implements Serializable
{

    /**
     * Broadcast Start Date
     * <p>
     * 
     * 
     */
    @JsonProperty("eventBroadcastDate")
    private DateTime eventBroadcastDate;
    /**
     * Broadcast End Date
     * <p>
     * 
     * 
     */
    @JsonProperty("eventBroadcastEndDate")
    private DateTime eventBroadcastEndDate;
    /**
     * Screen Format
     * <p>
     * 
     * 
     */
    @JsonProperty("screenFormat")
    private Event.ScreenFormat screenFormat;
    @JsonProperty("imageContent")
    private List<ImageContent> imageContent = new ArrayList<ImageContent>();
    @JsonProperty("contributors")
    private List<Person> contributors = new ArrayList<Person>();
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 467222680727297371L;

    /**
     * Broadcast Start Date
     * <p>
     * 
     * 
     * @return
     *     The eventBroadcastDate
     */
    @JsonProperty("eventBroadcastDate")
    public DateTime getEventBroadcastDate() {
        return eventBroadcastDate;
    }

    /**
     * Broadcast Start Date
     * <p>
     * 
     * 
     * @param eventBroadcastDate
     *     The eventBroadcastDate
     */
    @JsonProperty("eventBroadcastDate")
    public void setEventBroadcastDate(DateTime eventBroadcastDate) {
        this.eventBroadcastDate = eventBroadcastDate;
    }

    public Event withEventBroadcastDate(DateTime eventBroadcastDate) {
        this.eventBroadcastDate = eventBroadcastDate;
        return this;
    }

    /**
     * Broadcast End Date
     * <p>
     * 
     * 
     * @return
     *     The eventBroadcastEndDate
     */
    @JsonProperty("eventBroadcastEndDate")
    public DateTime getEventBroadcastEndDate() {
        return eventBroadcastEndDate;
    }

    /**
     * Broadcast End Date
     * <p>
     * 
     * 
     * @param eventBroadcastEndDate
     *     The eventBroadcastEndDate
     */
    @JsonProperty("eventBroadcastEndDate")
    public void setEventBroadcastEndDate(DateTime eventBroadcastEndDate) {
        this.eventBroadcastEndDate = eventBroadcastEndDate;
    }

    public Event withEventBroadcastEndDate(DateTime eventBroadcastEndDate) {
        this.eventBroadcastEndDate = eventBroadcastEndDate;
        return this;
    }

    /**
     * Screen Format
     * <p>
     * 
     * 
     * @return
     *     The screenFormat
     */
    @JsonProperty("screenFormat")
    public Event.ScreenFormat getScreenFormat() {
        return screenFormat;
    }

    /**
     * Screen Format
     * <p>
     * 
     * 
     * @param screenFormat
     *     The screenFormat
     */
    @JsonProperty("screenFormat")
    public void setScreenFormat(Event.ScreenFormat screenFormat) {
        this.screenFormat = screenFormat;
    }

    public Event withScreenFormat(Event.ScreenFormat screenFormat) {
        this.screenFormat = screenFormat;
        return this;
    }

    /**
     * 
     * @return
     *     The imageContent
     */
    @JsonProperty("imageContent")
    public List<ImageContent> getImageContent() {
        return imageContent;
    }

    /**
     * 
     * @param imageContent
     *     The imageContent
     */
    @JsonProperty("imageContent")
    public void setImageContent(List<ImageContent> imageContent) {
        this.imageContent = imageContent;
    }

    public Event withImageContent(List<ImageContent> imageContent) {
        this.imageContent = imageContent;
        return this;
    }

    /**
     * 
     * @return
     *     The contributors
     */
    @JsonProperty("contributors")
    public List<Person> getContributors() {
        return contributors;
    }

    /**
     * 
     * @param contributors
     *     The contributors
     */
    @JsonProperty("contributors")
    public void setContributors(List<Person> contributors) {
        this.contributors = contributors;
    }

    public Event withContributors(List<Person> contributors) {
        this.contributors = contributors;
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

    public Event withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public Event withContentApproved(Boolean contentApproved) {
        super.withContentApproved(contentApproved);
        return this;
    }

    @Override
    public Event withTitleSortName(Locale titleSortName) {
        super.withTitleSortName(titleSortName);
        return this;
    }

    @Override
    public Event withTitleBrief(Locale titleBrief) {
        super.withTitleBrief(titleBrief);
        return this;
    }

    @Override
    public Event withTitleMedium(Locale titleMedium) {
        super.withTitleMedium(titleMedium);
        return this;
    }

    @Override
    public Event withTitleLong(Locale titleLong) {
        super.withTitleLong(titleLong);
        return this;
    }

    @Override
    public Event withSummaryShort(Locale summaryShort) {
        super.withSummaryShort(summaryShort);
        return this;
    }

    @Override
    public Event withSummaryMedium(Locale summaryMedium) {
        super.withSummaryMedium(summaryMedium);
        return this;
    }

    @Override
    public Event withSummaryLong(Locale summaryLong) {
        super.withSummaryLong(summaryLong);
        return this;
    }

    @Override
    public Event withCountryOfOrigin(List<Object> countryOfOrigin) {
        super.withCountryOfOrigin(countryOfOrigin);
        return this;
    }

    @Override
    public Event withShowType(com.irdeto.jumpstart.domain.BaseMetadataWithTitle.ShowType showType) {
        super.withShowType(showType);
        return this;
    }

    @Override
    public Event withKeywords(String keywords) {
        super.withKeywords(keywords);
        return this;
    }

    @Override
    public Event withMetadataApproved(Boolean metadataApproved) {
        super.withMetadataApproved(metadataApproved);
        return this;
    }

    @Override
    public Event withDataMaster(com.irdeto.jumpstart.domain.BaseMetadata.DataMaster dataMaster) {
        super.withDataMaster(dataMaster);
        return this;
    }

    @Override
    public Event withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public Event withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public Event withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public Event withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public Event withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public Event withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public Event withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public Event withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public Event withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public Event withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public Event withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public Event withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public Event withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public Event withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public Event withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public Event withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(eventBroadcastDate).append(eventBroadcastEndDate).append(screenFormat).append(imageContent).append(contributors).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Event) == false) {
            return false;
        }
        Event rhs = ((Event) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(eventBroadcastDate, rhs.eventBroadcastDate).append(eventBroadcastEndDate, rhs.eventBroadcastEndDate).append(screenFormat, rhs.screenFormat).append(imageContent, rhs.imageContent).append(contributors, rhs.contributors).append(links, rhs.links).isEquals();
    }

    public enum ScreenFormat {

        STANDARD("Standard"),
        WIDESCREEN("Widescreen"),
        LETTERBOX("Letterbox"),
        OAR("OAR");
        private final String value;
        private final static Map<String, Event.ScreenFormat> CONSTANTS = new HashMap<String, Event.ScreenFormat>();

        static {
            for (Event.ScreenFormat c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private ScreenFormat(String value) {
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
        public static Event.ScreenFormat fromValue(String value) {
            Event.ScreenFormat constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
