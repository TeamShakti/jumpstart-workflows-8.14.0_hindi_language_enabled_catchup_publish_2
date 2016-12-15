
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
 * Schedule Slot
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "linearBroadcastDate",
    "linearBroadcastEndDate",
    "title",
    "summary",
    "genre",
    "rating",
    "countryOfOrigin",
    "episodeName",
    "episodeId",
    "screenFormat",
    "imageUrl",
    "showType",
    "catchUp",
    "STBEnabled",
    "Downloadable",
    "blackOut",
    "_links"
})
public class ScheduleSlot
    extends Base
    implements Serializable
{

    /**
     * Broadcast Start Date
     * <p>
     * 
     * 
     */
    @JsonProperty("linearBroadcastDate")
    private DateTime linearBroadcastDate;
    /**
     * Broadcast End Date
     * <p>
     * 
     * 
     */
    @JsonProperty("linearBroadcastEndDate")
    private DateTime linearBroadcastEndDate;
    /**
     * Title
     * <p>
     * 
     * 
     */
    @JsonProperty("title")
    private Locale title;
    /**
     * Summary
     * <p>
     * 
     * 
     */
    @JsonProperty("summary")
    private Locale summary;
    /**
     * Genre
     * <p>
     * 
     * 
     */
    @JsonProperty("genre")
    private Locale genre;
    /**
     * Rating
     * <p>
     * 
     * 
     */
    @JsonProperty("rating")
    private String rating;
    /**
     * Country of Origin
     * <p>
     * 
     * 
     */
    @JsonProperty("countryOfOrigin")
    private List<Object> countryOfOrigin = new ArrayList<Object>();
    /**
     * Episode Name
     * <p>
     * 
     * 
     */
    @JsonProperty("episodeName")
    private Locale episodeName;
    /**
     * Episode ID
     * <p>
     * 
     * 
     */
    @JsonProperty("episodeId")
    private String episodeId;
    /**
     * Screen Format
     * <p>
     * 
     * 
     */
    @JsonProperty("screenFormat")
    private ScheduleSlot.ScreenFormat screenFormat;
    /**
     * Image URL
     * <p>
     * 
     * 
     */
    @JsonProperty("imageUrl")
    private String imageUrl;
    /**
     * Show Type
     * <p>
     * 
     * 
     */
    @JsonProperty("showType")
    private ScheduleSlot.ShowType showType;
    /**
     * Catch Up
     * <p>
     * 
     * 
     */
    @JsonProperty("catchUp")
    private Boolean catchUp;
    /**
     * STB Enabled
     * <p>
     * 
     * 
     */
    @JsonProperty("STBEnabled")
    private Boolean sTBEnabled;
    /**
     * Downloadable
     * <p>
     * 
     * 
     */
    @JsonProperty("Downloadable")
    private Boolean downloadable;
    /**
     * Black Out
     * <p>
     * 
     * 
     */
    @JsonProperty("blackOut")
    private Boolean blackOut;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 9198892456665734345L;

    /**
     * Broadcast Start Date
     * <p>
     * 
     * 
     * @return
     *     The linearBroadcastDate
     */
    @JsonProperty("linearBroadcastDate")
    public DateTime getLinearBroadcastDate() {
        return linearBroadcastDate;
    }

    /**
     * Broadcast Start Date
     * <p>
     * 
     * 
     * @param linearBroadcastDate
     *     The linearBroadcastDate
     */
    @JsonProperty("linearBroadcastDate")
    public void setLinearBroadcastDate(DateTime linearBroadcastDate) {
        this.linearBroadcastDate = linearBroadcastDate;
    }

    public ScheduleSlot withLinearBroadcastDate(DateTime linearBroadcastDate) {
        this.linearBroadcastDate = linearBroadcastDate;
        return this;
    }

    /**
     * Broadcast End Date
     * <p>
     * 
     * 
     * @return
     *     The linearBroadcastEndDate
     */
    @JsonProperty("linearBroadcastEndDate")
    public DateTime getLinearBroadcastEndDate() {
        return linearBroadcastEndDate;
    }

    /**
     * Broadcast End Date
     * <p>
     * 
     * 
     * @param linearBroadcastEndDate
     *     The linearBroadcastEndDate
     */
    @JsonProperty("linearBroadcastEndDate")
    public void setLinearBroadcastEndDate(DateTime linearBroadcastEndDate) {
        this.linearBroadcastEndDate = linearBroadcastEndDate;
    }

    public ScheduleSlot withLinearBroadcastEndDate(DateTime linearBroadcastEndDate) {
        this.linearBroadcastEndDate = linearBroadcastEndDate;
        return this;
    }

    /**
     * Title
     * <p>
     * 
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public Locale getTitle() {
        return title;
    }

    /**
     * Title
     * <p>
     * 
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(Locale title) {
        this.title = title;
    }

    public ScheduleSlot withTitle(Locale title) {
        this.title = title;
        return this;
    }

    /**
     * Summary
     * <p>
     * 
     * 
     * @return
     *     The summary
     */
    @JsonProperty("summary")
    public Locale getSummary() {
        return summary;
    }

    /**
     * Summary
     * <p>
     * 
     * 
     * @param summary
     *     The summary
     */
    @JsonProperty("summary")
    public void setSummary(Locale summary) {
        this.summary = summary;
    }

    public ScheduleSlot withSummary(Locale summary) {
        this.summary = summary;
        return this;
    }

    /**
     * Genre
     * <p>
     * 
     * 
     * @return
     *     The genre
     */
    @JsonProperty("genre")
    public Locale getGenre() {
        return genre;
    }

    /**
     * Genre
     * <p>
     * 
     * 
     * @param genre
     *     The genre
     */
    @JsonProperty("genre")
    public void setGenre(Locale genre) {
        this.genre = genre;
    }

    public ScheduleSlot withGenre(Locale genre) {
        this.genre = genre;
        return this;
    }

    /**
     * Rating
     * <p>
     * 
     * 
     * @return
     *     The rating
     */
    @JsonProperty("rating")
    public String getRating() {
        return rating;
    }

    /**
     * Rating
     * <p>
     * 
     * 
     * @param rating
     *     The rating
     */
    @JsonProperty("rating")
    public void setRating(String rating) {
        this.rating = rating;
    }

    public ScheduleSlot withRating(String rating) {
        this.rating = rating;
        return this;
    }

    /**
     * Country of Origin
     * <p>
     * 
     * 
     * @return
     *     The countryOfOrigin
     */
    @JsonProperty("countryOfOrigin")
    public List<Object> getCountryOfOrigin() {
        return countryOfOrigin;
    }

    /**
     * Country of Origin
     * <p>
     * 
     * 
     * @param countryOfOrigin
     *     The countryOfOrigin
     */
    @JsonProperty("countryOfOrigin")
    public void setCountryOfOrigin(List<Object> countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public ScheduleSlot withCountryOfOrigin(List<Object> countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
        return this;
    }

    /**
     * Episode Name
     * <p>
     * 
     * 
     * @return
     *     The episodeName
     */
    @JsonProperty("episodeName")
    public Locale getEpisodeName() {
        return episodeName;
    }

    /**
     * Episode Name
     * <p>
     * 
     * 
     * @param episodeName
     *     The episodeName
     */
    @JsonProperty("episodeName")
    public void setEpisodeName(Locale episodeName) {
        this.episodeName = episodeName;
    }

    public ScheduleSlot withEpisodeName(Locale episodeName) {
        this.episodeName = episodeName;
        return this;
    }

    /**
     * Episode ID
     * <p>
     * 
     * 
     * @return
     *     The episodeId
     */
    @JsonProperty("episodeId")
    public String getEpisodeId() {
        return episodeId;
    }

    /**
     * Episode ID
     * <p>
     * 
     * 
     * @param episodeId
     *     The episodeId
     */
    @JsonProperty("episodeId")
    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public ScheduleSlot withEpisodeId(String episodeId) {
        this.episodeId = episodeId;
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
    public ScheduleSlot.ScreenFormat getScreenFormat() {
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
    public void setScreenFormat(ScheduleSlot.ScreenFormat screenFormat) {
        this.screenFormat = screenFormat;
    }

    public ScheduleSlot withScreenFormat(ScheduleSlot.ScreenFormat screenFormat) {
        this.screenFormat = screenFormat;
        return this;
    }

    /**
     * Image URL
     * <p>
     * 
     * 
     * @return
     *     The imageUrl
     */
    @JsonProperty("imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Image URL
     * <p>
     * 
     * 
     * @param imageUrl
     *     The imageUrl
     */
    @JsonProperty("imageUrl")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ScheduleSlot withImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    /**
     * Show Type
     * <p>
     * 
     * 
     * @return
     *     The showType
     */
    @JsonProperty("showType")
    public ScheduleSlot.ShowType getShowType() {
        return showType;
    }

    /**
     * Show Type
     * <p>
     * 
     * 
     * @param showType
     *     The showType
     */
    @JsonProperty("showType")
    public void setShowType(ScheduleSlot.ShowType showType) {
        this.showType = showType;
    }

    public ScheduleSlot withShowType(ScheduleSlot.ShowType showType) {
        this.showType = showType;
        return this;
    }

    /**
     * Catch Up
     * <p>
     * 
     * 
     * @return
     *     The catchUp
     */
    @JsonProperty("catchUp")
    public Boolean getCatchUp() {
        return catchUp;
    }

    /**
     * Catch Up
     * <p>
     * 
     * 
     * @param catchUp
     *     The catchUp
     */
    @JsonProperty("catchUp")
    public void setCatchUp(Boolean catchUp) {
        this.catchUp = catchUp;
    }

    public ScheduleSlot withCatchUp(Boolean catchUp) {
        this.catchUp = catchUp;
        return this;
    }

    /**
     * STB Enabled
     * <p>
     * 
     * 
     * @return
     *     The sTBEnabled
     */
    @JsonProperty("STBEnabled")
    public Boolean getSTBEnabled() {
        return sTBEnabled;
    }

    /**
     * STB Enabled
     * <p>
     * 
     * 
     * @param sTBEnabled
     *     The STBEnabled
     */
    @JsonProperty("STBEnabled")
    public void setSTBEnabled(Boolean sTBEnabled) {
        this.sTBEnabled = sTBEnabled;
    }

    public ScheduleSlot withSTBEnabled(Boolean sTBEnabled) {
        this.sTBEnabled = sTBEnabled;
        return this;
    }

    /**
     * Downloadable
     * <p>
     * 
     * 
     * @return
     *     The downloadable
     */
    @JsonProperty("Downloadable")
    public Boolean getDownloadable() {
        return downloadable;
    }

    /**
     * Downloadable
     * <p>
     * 
     * 
     * @param downloadable
     *     The Downloadable
     */
    @JsonProperty("Downloadable")
    public void setDownloadable(Boolean downloadable) {
        this.downloadable = downloadable;
    }

    public ScheduleSlot withDownloadable(Boolean downloadable) {
        this.downloadable = downloadable;
        return this;
    }

    /**
     * Black Out
     * <p>
     * 
     * 
     * @return
     *     The blackOut
     */
    @JsonProperty("blackOut")
    public Boolean getBlackOut() {
        return blackOut;
    }

    /**
     * Black Out
     * <p>
     * 
     * 
     * @param blackOut
     *     The blackOut
     */
    @JsonProperty("blackOut")
    public void setBlackOut(Boolean blackOut) {
        this.blackOut = blackOut;
    }

    public ScheduleSlot withBlackOut(Boolean blackOut) {
        this.blackOut = blackOut;
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

    public ScheduleSlot withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public ScheduleSlot withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public ScheduleSlot withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public ScheduleSlot withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public ScheduleSlot withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public ScheduleSlot withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public ScheduleSlot withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public ScheduleSlot withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public ScheduleSlot withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public ScheduleSlot withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public ScheduleSlot withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public ScheduleSlot withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public ScheduleSlot withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public ScheduleSlot withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public ScheduleSlot withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public ScheduleSlot withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public ScheduleSlot withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(linearBroadcastDate).append(linearBroadcastEndDate).append(title).append(summary).append(genre).append(rating).append(countryOfOrigin).append(episodeName).append(episodeId).append(screenFormat).append(imageUrl).append(showType).append(catchUp).append(sTBEnabled).append(downloadable).append(blackOut).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ScheduleSlot) == false) {
            return false;
        }
        ScheduleSlot rhs = ((ScheduleSlot) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(linearBroadcastDate, rhs.linearBroadcastDate).append(linearBroadcastEndDate, rhs.linearBroadcastEndDate).append(title, rhs.title).append(summary, rhs.summary).append(genre, rhs.genre).append(rating, rhs.rating).append(countryOfOrigin, rhs.countryOfOrigin).append(episodeName, rhs.episodeName).append(episodeId, rhs.episodeId).append(screenFormat, rhs.screenFormat).append(imageUrl, rhs.imageUrl).append(showType, rhs.showType).append(catchUp, rhs.catchUp).append(sTBEnabled, rhs.sTBEnabled).append(downloadable, rhs.downloadable).append(blackOut, rhs.blackOut).append(links, rhs.links).isEquals();
    }

    public enum ScreenFormat {

        STANDARD("Standard"),
        WIDESCREEN("Widescreen"),
        LETTERBOX("Letterbox"),
        OAR("OAR");
        private final String value;
        private final static Map<String, ScheduleSlot.ScreenFormat> CONSTANTS = new HashMap<String, ScheduleSlot.ScreenFormat>();

        static {
            for (ScheduleSlot.ScreenFormat c: values()) {
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
        public static ScheduleSlot.ScreenFormat fromValue(String value) {
            ScheduleSlot.ScreenFormat constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ShowType {

        SERIES("Series"),
        KIDS("Kids"),
        MOVIES("Movies"),
        SPORTS("Sports"),
        MUSIC("Music"),
        EVENTS("Events"),
        AD("Ad"),
        LIFESTYLE("Lifestyle"),
        COMMERCIAL("Commercial"),
        DOCUMENTARY("Documentary"),
        EDUCATIONAL("Educational"),
        ENTERTAINMENT("Entertainment"),
        NEWS("News"),
        RELIGIOUS("Religious"),
        OTHERS("Others");
        private final String value;
        private final static Map<String, ScheduleSlot.ShowType> CONSTANTS = new HashMap<String, ScheduleSlot.ShowType>();

        static {
            for (ScheduleSlot.ShowType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private ShowType(String value) {
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
        public static ScheduleSlot.ShowType fromValue(String value) {
            ScheduleSlot.ShowType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
