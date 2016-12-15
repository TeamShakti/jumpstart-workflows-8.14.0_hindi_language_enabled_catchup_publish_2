
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
 * Program
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "episodeName",
    "episodeId",
    "NDSOfferID",
    "isClosedCaptioning",
    "isDownloadable",
    "downloadExpiry",
    "ndsPrice",
    "maxconcurrentstream",
    "MSORating",
    "displayRunTime",
    "yearOfRelease",
    "genre",
    "rating",
    "isSeasonPremier",
    "isSeasonFinale",
    "linearBroadcastDate",
    "contributors",
    "videoContent",
    "imageContent",
    "_links"
})
public class Program
    extends BaseMetadataWithContent
    implements Serializable
{

    /**
     * Episode Name
     * <p>
     * 
     * 
     */
    @JsonProperty("episodeName")
    private String episodeName;
    /**
     * Episode ID
     * <p>
     * 
     * 
     */
    @JsonProperty("episodeId")
    private String episodeId;
    /**
     * NDS Offer ID
     * <p>
     * 
     * 
     */
    @JsonProperty("NDSOfferID")
    private String nDSOfferID;
    /**
     * Closed Captioning?
     * <p>
     * 
     * 
     */
    @JsonProperty("isClosedCaptioning")
    private Boolean isClosedCaptioning;
    /**
     * Downloadable?
     * <p>
     * 
     * 
     */
    @JsonProperty("isDownloadable")
    private Boolean isDownloadable;
    /**
     * Download Expiry in days
     * <p>
     * 
     * 
     */
    @JsonProperty("downloadExpiry")
    private Integer downloadExpiry;
    /**
     * NDS Price
     * <p>
     * 
     * 
     */
    @JsonProperty("ndsPrice")
    private Integer ndsPrice;
    /**
     * Maximum Concurrent Stream
     * <p>
     * 
     * 
     */
    @JsonProperty("maxconcurrentstream")
    private Integer maxconcurrentstream;
    /**
     * MSO Rating
     * <p>
     * 
     * 
     */
    @JsonProperty("MSORating")
    private Integer mSORating;
    /**
     * Display Run Time
     * <p>
     * 
     * 
     */
    @JsonProperty("displayRunTime")
    private String displayRunTime;
    /**
     * Year of Release
     * <p>
     * 
     * 
     */
    @JsonProperty("yearOfRelease")
    private Integer yearOfRelease;
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
     * Season Premier?
     * <p>
     * 
     * 
     */
    @JsonProperty("isSeasonPremier")
    private Boolean isSeasonPremier;
    /**
     * Season Finale?
     * <p>
     * 
     * 
     */
    @JsonProperty("isSeasonFinale")
    private Boolean isSeasonFinale;
    /**
     * Last Broadcast Date
     * <p>
     * 
     * 
     */
    @JsonProperty("linearBroadcastDate")
    private DateTime linearBroadcastDate;
    @JsonProperty("contributors")
    private List<Person> contributors = new ArrayList<Person>();
    @JsonProperty("videoContent")
    private List<VideoContent> videoContent = new ArrayList<VideoContent>();
    @JsonProperty("imageContent")
    private List<ImageContent> imageContent = new ArrayList<ImageContent>();
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -8704864648822528110L;

    /**
     * Episode Name
     * <p>
     * 
     * 
     * @return
     *     The episodeName
     */
    @JsonProperty("episodeName")
    public String getEpisodeName() {
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
    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public Program withEpisodeName(String episodeName) {
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

    public Program withEpisodeId(String episodeId) {
        this.episodeId = episodeId;
        return this;
    }

    /**
     * NDS Offer ID
     * <p>
     * 
     * 
     * @return
     *     The nDSOfferID
     */
    @JsonProperty("NDSOfferID")
    public String getNDSOfferID() {
        return nDSOfferID;
    }

    /**
     * NDS Offer ID
     * <p>
     * 
     * 
     * @param nDSOfferID
     *     The NDSOfferID
     */
    @JsonProperty("NDSOfferID")
    public void setNDSOfferID(String nDSOfferID) {
        this.nDSOfferID = nDSOfferID;
    }

    public Program withNDSOfferID(String nDSOfferID) {
        this.nDSOfferID = nDSOfferID;
        return this;
    }

    /**
     * Closed Captioning?
     * <p>
     * 
     * 
     * @return
     *     The isClosedCaptioning
     */
    @JsonProperty("isClosedCaptioning")
    public Boolean getIsClosedCaptioning() {
        return isClosedCaptioning;
    }

    /**
     * Closed Captioning?
     * <p>
     * 
     * 
     * @param isClosedCaptioning
     *     The isClosedCaptioning
     */
    @JsonProperty("isClosedCaptioning")
    public void setIsClosedCaptioning(Boolean isClosedCaptioning) {
        this.isClosedCaptioning = isClosedCaptioning;
    }

    public Program withIsClosedCaptioning(Boolean isClosedCaptioning) {
        this.isClosedCaptioning = isClosedCaptioning;
        return this;
    }

    /**
     * Downloadable?
     * <p>
     * 
     * 
     * @return
     *     The isDownloadable
     */
    @JsonProperty("isDownloadable")
    public Boolean getIsDownloadable() {
        return isDownloadable;
    }

    /**
     * Downloadable?
     * <p>
     * 
     * 
     * @param isDownloadable
     *     The isDownloadable
     */
    @JsonProperty("isDownloadable")
    public void setIsDownloadable(Boolean isDownloadable) {
        this.isDownloadable = isDownloadable;
    }

    public Program withIsDownloadable(Boolean isDownloadable) {
        this.isDownloadable = isDownloadable;
        return this;
    }

    /**
     * Download Expiry in days
     * <p>
     * 
     * 
     * @return
     *     The downloadExpiry
     */
    @JsonProperty("downloadExpiry")
    public Integer getDownloadExpiry() {
        return downloadExpiry;
    }

    /**
     * Download Expiry in days
     * <p>
     * 
     * 
     * @param downloadExpiry
     *     The downloadExpiry
     */
    @JsonProperty("downloadExpiry")
    public void setDownloadExpiry(Integer downloadExpiry) {
        this.downloadExpiry = downloadExpiry;
    }

    public Program withDownloadExpiry(Integer downloadExpiry) {
        this.downloadExpiry = downloadExpiry;
        return this;
    }

    /**
     * NDS Price
     * <p>
     * 
     * 
     * @return
     *     The ndsPrice
     */
    @JsonProperty("ndsPrice")
    public Integer getNdsPrice() {
        return ndsPrice;
    }

    /**
     * NDS Price
     * <p>
     * 
     * 
     * @param ndsPrice
     *     The ndsPrice
     */
    @JsonProperty("ndsPrice")
    public void setNdsPrice(Integer ndsPrice) {
        this.ndsPrice = ndsPrice;
    }

    public Program withNdsPrice(Integer ndsPrice) {
        this.ndsPrice = ndsPrice;
        return this;
    }

    /**
     * Maximum Concurrent Stream
     * <p>
     * 
     * 
     * @return
     *     The maxconcurrentstream
     */
    @JsonProperty("maxconcurrentstream")
    public Integer getMaxconcurrentstream() {
        return maxconcurrentstream;
    }

    /**
     * Maximum Concurrent Stream
     * <p>
     * 
     * 
     * @param maxconcurrentstream
     *     The maxconcurrentstream
     */
    @JsonProperty("maxconcurrentstream")
    public void setMaxconcurrentstream(Integer maxconcurrentstream) {
        this.maxconcurrentstream = maxconcurrentstream;
    }

    public Program withMaxconcurrentstream(Integer maxconcurrentstream) {
        this.maxconcurrentstream = maxconcurrentstream;
        return this;
    }

    /**
     * MSO Rating
     * <p>
     * 
     * 
     * @return
     *     The mSORating
     */
    @JsonProperty("MSORating")
    public Integer getMSORating() {
        return mSORating;
    }

    /**
     * MSO Rating
     * <p>
     * 
     * 
     * @param mSORating
     *     The MSORating
     */
    @JsonProperty("MSORating")
    public void setMSORating(Integer mSORating) {
        this.mSORating = mSORating;
    }

    public Program withMSORating(Integer mSORating) {
        this.mSORating = mSORating;
        return this;
    }

    /**
     * Display Run Time
     * <p>
     * 
     * 
     * @return
     *     The displayRunTime
     */
    @JsonProperty("displayRunTime")
    public String getDisplayRunTime() {
        return displayRunTime;
    }

    /**
     * Display Run Time
     * <p>
     * 
     * 
     * @param displayRunTime
     *     The displayRunTime
     */
    @JsonProperty("displayRunTime")
    public void setDisplayRunTime(String displayRunTime) {
        this.displayRunTime = displayRunTime;
    }

    public Program withDisplayRunTime(String displayRunTime) {
        this.displayRunTime = displayRunTime;
        return this;
    }

    /**
     * Year of Release
     * <p>
     * 
     * 
     * @return
     *     The yearOfRelease
     */
    @JsonProperty("yearOfRelease")
    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    /**
     * Year of Release
     * <p>
     * 
     * 
     * @param yearOfRelease
     *     The yearOfRelease
     */
    @JsonProperty("yearOfRelease")
    public void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Program withYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
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

    public Program withGenre(Locale genre) {
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

    public Program withRating(String rating) {
        this.rating = rating;
        return this;
    }

    /**
     * Season Premier?
     * <p>
     * 
     * 
     * @return
     *     The isSeasonPremier
     */
    @JsonProperty("isSeasonPremier")
    public Boolean getIsSeasonPremier() {
        return isSeasonPremier;
    }

    /**
     * Season Premier?
     * <p>
     * 
     * 
     * @param isSeasonPremier
     *     The isSeasonPremier
     */
    @JsonProperty("isSeasonPremier")
    public void setIsSeasonPremier(Boolean isSeasonPremier) {
        this.isSeasonPremier = isSeasonPremier;
    }

    public Program withIsSeasonPremier(Boolean isSeasonPremier) {
        this.isSeasonPremier = isSeasonPremier;
        return this;
    }

    /**
     * Season Finale?
     * <p>
     * 
     * 
     * @return
     *     The isSeasonFinale
     */
    @JsonProperty("isSeasonFinale")
    public Boolean getIsSeasonFinale() {
        return isSeasonFinale;
    }

    /**
     * Season Finale?
     * <p>
     * 
     * 
     * @param isSeasonFinale
     *     The isSeasonFinale
     */
    @JsonProperty("isSeasonFinale")
    public void setIsSeasonFinale(Boolean isSeasonFinale) {
        this.isSeasonFinale = isSeasonFinale;
    }

    public Program withIsSeasonFinale(Boolean isSeasonFinale) {
        this.isSeasonFinale = isSeasonFinale;
        return this;
    }

    /**
     * Last Broadcast Date
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
     * Last Broadcast Date
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

    public Program withLinearBroadcastDate(DateTime linearBroadcastDate) {
        this.linearBroadcastDate = linearBroadcastDate;
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

    public Program withContributors(List<Person> contributors) {
        this.contributors = contributors;
        return this;
    }

    /**
     * 
     * @return
     *     The videoContent
     */
    @JsonProperty("videoContent")
    public List<VideoContent> getVideoContent() {
        return videoContent;
    }

    /**
     * 
     * @param videoContent
     *     The videoContent
     */
    @JsonProperty("videoContent")
    public void setVideoContent(List<VideoContent> videoContent) {
        this.videoContent = videoContent;
    }

    public Program withVideoContent(List<VideoContent> videoContent) {
        this.videoContent = videoContent;
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

    public Program withImageContent(List<ImageContent> imageContent) {
        this.imageContent = imageContent;
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

    public Program withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public Program withContentApproved(Boolean contentApproved) {
        super.withContentApproved(contentApproved);
        return this;
    }

    @Override
    public Program withTitleSortName(Locale titleSortName) {
        super.withTitleSortName(titleSortName);
        return this;
    }

    @Override
    public Program withTitleBrief(Locale titleBrief) {
        super.withTitleBrief(titleBrief);
        return this;
    }

    @Override
    public Program withTitleMedium(Locale titleMedium) {
        super.withTitleMedium(titleMedium);
        return this;
    }

    @Override
    public Program withTitleLong(Locale titleLong) {
        super.withTitleLong(titleLong);
        return this;
    }

    @Override
    public Program withSummaryShort(Locale summaryShort) {
        super.withSummaryShort(summaryShort);
        return this;
    }

    @Override
    public Program withSummaryMedium(Locale summaryMedium) {
        super.withSummaryMedium(summaryMedium);
        return this;
    }

    @Override
    public Program withSummaryLong(Locale summaryLong) {
        super.withSummaryLong(summaryLong);
        return this;
    }

    @Override
    public Program withCountryOfOrigin(List<Object> countryOfOrigin) {
        super.withCountryOfOrigin(countryOfOrigin);
        return this;
    }

    @Override
    public Program withShowType(com.irdeto.jumpstart.domain.BaseMetadataWithTitle.ShowType showType) {
        super.withShowType(showType);
        return this;
    }

    @Override
    public Program withKeywords(String keywords) {
        super.withKeywords(keywords);
        return this;
    }

    @Override
    public Program withMetadataApproved(Boolean metadataApproved) {
        super.withMetadataApproved(metadataApproved);
        return this;
    }

    @Override
    public Program withDataMaster(com.irdeto.jumpstart.domain.BaseMetadata.DataMaster dataMaster) {
        super.withDataMaster(dataMaster);
        return this;
    }

    @Override
    public Program withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public Program withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public Program withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public Program withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public Program withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public Program withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public Program withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public Program withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public Program withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public Program withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public Program withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public Program withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public Program withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public Program withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public Program withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public Program withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(episodeName).append(episodeId).append(nDSOfferID).append(isClosedCaptioning).append(isDownloadable).append(downloadExpiry).append(ndsPrice).append(maxconcurrentstream).append(mSORating).append(displayRunTime).append(yearOfRelease).append(genre).append(rating).append(isSeasonPremier).append(isSeasonFinale).append(linearBroadcastDate).append(contributors).append(videoContent).append(imageContent).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Program) == false) {
            return false;
        }
        Program rhs = ((Program) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(episodeName, rhs.episodeName).append(episodeId, rhs.episodeId).append(nDSOfferID, rhs.nDSOfferID).append(isClosedCaptioning, rhs.isClosedCaptioning).append(isDownloadable, rhs.isDownloadable).append(downloadExpiry, rhs.downloadExpiry).append(ndsPrice, rhs.ndsPrice).append(maxconcurrentstream, rhs.maxconcurrentstream).append(mSORating, rhs.mSORating).append(displayRunTime, rhs.displayRunTime).append(yearOfRelease, rhs.yearOfRelease).append(genre, rhs.genre).append(rating, rhs.rating).append(isSeasonPremier, rhs.isSeasonPremier).append(isSeasonFinale, rhs.isSeasonFinale).append(linearBroadcastDate, rhs.linearBroadcastDate).append(contributors, rhs.contributors).append(videoContent, rhs.videoContent).append(imageContent, rhs.imageContent).append(links, rhs.links).isEquals();
    }

}
