
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
 * Channel
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "channelId",
    "BroadcastServiceId",
    "channelPackager",
    "udpMulticastIP",
    "enabled",
    "allowedOnBrowsers",
    "freeChannel",
    "HDChannel",
    "catchupChannel",
    "displayOrder",
    "numberOfAudio",
    "liveWindowDuration",
    "imageContent",
    "_links"
})
public class Channel
    extends BaseMetadataWithContent
    implements Serializable
{

    /**
     * Channel ID
     * <p>
     * 
     * 
     */
    @JsonProperty("channelId")
    private String channelId;
    /**
     * Broadcast Service ID
     * <p>
     * 
     * 
     */
    @JsonProperty("BroadcastServiceId")
    private String broadcastServiceId;
    /**
     * Channel Packager
     * <p>
     * 
     * 
     */
    @JsonProperty("channelPackager")
    private String channelPackager;
    /**
     * UDP Multicast IP
     * <p>
     * 
     * 
     */
    @JsonProperty("udpMulticastIP")
    private String udpMulticastIP;
    /**
     * Enabled?
     * <p>
     * 
     * 
     */
    @JsonProperty("enabled")
    private Boolean enabled;
    /**
     * Allow on browsers?
     * <p>
     * 
     * 
     */
    @JsonProperty("allowedOnBrowsers")
    private Boolean allowedOnBrowsers;
    /**
     * Free Channel?
     * <p>
     * 
     * 
     */
    @JsonProperty("freeChannel")
    private Boolean freeChannel;
    /**
     * HD Channel?
     * <p>
     * 
     * 
     */
    @JsonProperty("HDChannel")
    private Boolean hDChannel;
    /**
     * Catchup Channel?
     * <p>
     * 
     * 
     */
    @JsonProperty("catchupChannel")
    private Boolean catchupChannel;
    /**
     * Display Order
     * <p>
     * 
     * 
     */
    @JsonProperty("displayOrder")
    private Integer displayOrder;
    /**
     * Number of Audios
     * <p>
     * 
     * 
     */
    @JsonProperty("numberOfAudio")
    private Integer numberOfAudio;
    /**
     * Live Window Duration (minutes)
     * <p>
     * 
     * 
     */
    @JsonProperty("liveWindowDuration")
    private Integer liveWindowDuration;
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
    private final static long serialVersionUID = 2940960996536412069L;

    /**
     * Channel ID
     * <p>
     * 
     * 
     * @return
     *     The channelId
     */
    @JsonProperty("channelId")
    public String getChannelId() {
        return channelId;
    }

    /**
     * Channel ID
     * <p>
     * 
     * 
     * @param channelId
     *     The channelId
     */
    @JsonProperty("channelId")
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Channel withChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    /**
     * Broadcast Service ID
     * <p>
     * 
     * 
     * @return
     *     The broadcastServiceId
     */
    @JsonProperty("BroadcastServiceId")
    public String getBroadcastServiceId() {
        return broadcastServiceId;
    }

    /**
     * Broadcast Service ID
     * <p>
     * 
     * 
     * @param broadcastServiceId
     *     The BroadcastServiceId
     */
    @JsonProperty("BroadcastServiceId")
    public void setBroadcastServiceId(String broadcastServiceId) {
        this.broadcastServiceId = broadcastServiceId;
    }

    public Channel withBroadcastServiceId(String broadcastServiceId) {
        this.broadcastServiceId = broadcastServiceId;
        return this;
    }

    /**
     * Channel Packager
     * <p>
     * 
     * 
     * @return
     *     The channelPackager
     */
    @JsonProperty("channelPackager")
    public String getChannelPackager() {
        return channelPackager;
    }

    /**
     * Channel Packager
     * <p>
     * 
     * 
     * @param channelPackager
     *     The channelPackager
     */
    @JsonProperty("channelPackager")
    public void setChannelPackager(String channelPackager) {
        this.channelPackager = channelPackager;
    }

    public Channel withChannelPackager(String channelPackager) {
        this.channelPackager = channelPackager;
        return this;
    }

    /**
     * UDP Multicast IP
     * <p>
     * 
     * 
     * @return
     *     The udpMulticastIP
     */
    @JsonProperty("udpMulticastIP")
    public String getUdpMulticastIP() {
        return udpMulticastIP;
    }

    /**
     * UDP Multicast IP
     * <p>
     * 
     * 
     * @param udpMulticastIP
     *     The udpMulticastIP
     */
    @JsonProperty("udpMulticastIP")
    public void setUdpMulticastIP(String udpMulticastIP) {
        this.udpMulticastIP = udpMulticastIP;
    }

    public Channel withUdpMulticastIP(String udpMulticastIP) {
        this.udpMulticastIP = udpMulticastIP;
        return this;
    }

    /**
     * Enabled?
     * <p>
     * 
     * 
     * @return
     *     The enabled
     */
    @JsonProperty("enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Enabled?
     * <p>
     * 
     * 
     * @param enabled
     *     The enabled
     */
    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Channel withEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * Allow on browsers?
     * <p>
     * 
     * 
     * @return
     *     The allowedOnBrowsers
     */
    @JsonProperty("allowedOnBrowsers")
    public Boolean getAllowedOnBrowsers() {
        return allowedOnBrowsers;
    }

    /**
     * Allow on browsers?
     * <p>
     * 
     * 
     * @param allowedOnBrowsers
     *     The allowedOnBrowsers
     */
    @JsonProperty("allowedOnBrowsers")
    public void setAllowedOnBrowsers(Boolean allowedOnBrowsers) {
        this.allowedOnBrowsers = allowedOnBrowsers;
    }

    public Channel withAllowedOnBrowsers(Boolean allowedOnBrowsers) {
        this.allowedOnBrowsers = allowedOnBrowsers;
        return this;
    }

    /**
     * Free Channel?
     * <p>
     * 
     * 
     * @return
     *     The freeChannel
     */
    @JsonProperty("freeChannel")
    public Boolean getFreeChannel() {
        return freeChannel;
    }

    /**
     * Free Channel?
     * <p>
     * 
     * 
     * @param freeChannel
     *     The freeChannel
     */
    @JsonProperty("freeChannel")
    public void setFreeChannel(Boolean freeChannel) {
        this.freeChannel = freeChannel;
    }

    public Channel withFreeChannel(Boolean freeChannel) {
        this.freeChannel = freeChannel;
        return this;
    }

    /**
     * HD Channel?
     * <p>
     * 
     * 
     * @return
     *     The hDChannel
     */
    @JsonProperty("HDChannel")
    public Boolean getHDChannel() {
        return hDChannel;
    }

    /**
     * HD Channel?
     * <p>
     * 
     * 
     * @param hDChannel
     *     The HDChannel
     */
    @JsonProperty("HDChannel")
    public void setHDChannel(Boolean hDChannel) {
        this.hDChannel = hDChannel;
    }

    public Channel withHDChannel(Boolean hDChannel) {
        this.hDChannel = hDChannel;
        return this;
    }

    /**
     * Catchup Channel?
     * <p>
     * 
     * 
     * @return
     *     The catchupChannel
     */
    @JsonProperty("catchupChannel")
    public Boolean getCatchupChannel() {
        return catchupChannel;
    }

    /**
     * Catchup Channel?
     * <p>
     * 
     * 
     * @param catchupChannel
     *     The catchupChannel
     */
    @JsonProperty("catchupChannel")
    public void setCatchupChannel(Boolean catchupChannel) {
        this.catchupChannel = catchupChannel;
    }

    public Channel withCatchupChannel(Boolean catchupChannel) {
        this.catchupChannel = catchupChannel;
        return this;
    }

    /**
     * Display Order
     * <p>
     * 
     * 
     * @return
     *     The displayOrder
     */
    @JsonProperty("displayOrder")
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    /**
     * Display Order
     * <p>
     * 
     * 
     * @param displayOrder
     *     The displayOrder
     */
    @JsonProperty("displayOrder")
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Channel withDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    /**
     * Number of Audios
     * <p>
     * 
     * 
     * @return
     *     The numberOfAudio
     */
    @JsonProperty("numberOfAudio")
    public Integer getNumberOfAudio() {
        return numberOfAudio;
    }

    /**
     * Number of Audios
     * <p>
     * 
     * 
     * @param numberOfAudio
     *     The numberOfAudio
     */
    @JsonProperty("numberOfAudio")
    public void setNumberOfAudio(Integer numberOfAudio) {
        this.numberOfAudio = numberOfAudio;
    }

    public Channel withNumberOfAudio(Integer numberOfAudio) {
        this.numberOfAudio = numberOfAudio;
        return this;
    }

    /**
     * Live Window Duration (minutes)
     * <p>
     * 
     * 
     * @return
     *     The liveWindowDuration
     */
    @JsonProperty("liveWindowDuration")
    public Integer getLiveWindowDuration() {
        return liveWindowDuration;
    }

    /**
     * Live Window Duration (minutes)
     * <p>
     * 
     * 
     * @param liveWindowDuration
     *     The liveWindowDuration
     */
    @JsonProperty("liveWindowDuration")
    public void setLiveWindowDuration(Integer liveWindowDuration) {
        this.liveWindowDuration = liveWindowDuration;
    }

    public Channel withLiveWindowDuration(Integer liveWindowDuration) {
        this.liveWindowDuration = liveWindowDuration;
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

    public Channel withImageContent(List<ImageContent> imageContent) {
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

    public Channel withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public Channel withContentApproved(Boolean contentApproved) {
        super.withContentApproved(contentApproved);
        return this;
    }

    @Override
    public Channel withTitleSortName(Locale titleSortName) {
        super.withTitleSortName(titleSortName);
        return this;
    }

    @Override
    public Channel withTitleBrief(Locale titleBrief) {
        super.withTitleBrief(titleBrief);
        return this;
    }

    @Override
    public Channel withTitleMedium(Locale titleMedium) {
        super.withTitleMedium(titleMedium);
        return this;
    }

    @Override
    public Channel withTitleLong(Locale titleLong) {
        super.withTitleLong(titleLong);
        return this;
    }

    @Override
    public Channel withSummaryShort(Locale summaryShort) {
        super.withSummaryShort(summaryShort);
        return this;
    }

    @Override
    public Channel withSummaryMedium(Locale summaryMedium) {
        super.withSummaryMedium(summaryMedium);
        return this;
    }

    @Override
    public Channel withSummaryLong(Locale summaryLong) {
        super.withSummaryLong(summaryLong);
        return this;
    }

    @Override
    public Channel withCountryOfOrigin(List<Object> countryOfOrigin) {
        super.withCountryOfOrigin(countryOfOrigin);
        return this;
    }

    @Override
    public Channel withShowType(com.irdeto.jumpstart.domain.BaseMetadataWithTitle.ShowType showType) {
        super.withShowType(showType);
        return this;
    }

    @Override
    public Channel withKeywords(String keywords) {
        super.withKeywords(keywords);
        return this;
    }

    @Override
    public Channel withMetadataApproved(Boolean metadataApproved) {
        super.withMetadataApproved(metadataApproved);
        return this;
    }

    @Override
    public Channel withDataMaster(com.irdeto.jumpstart.domain.BaseMetadata.DataMaster dataMaster) {
        super.withDataMaster(dataMaster);
        return this;
    }

    @Override
    public Channel withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public Channel withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public Channel withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public Channel withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public Channel withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public Channel withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public Channel withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public Channel withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public Channel withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public Channel withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public Channel withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public Channel withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public Channel withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public Channel withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public Channel withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public Channel withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(channelId).append(broadcastServiceId).append(channelPackager).append(udpMulticastIP).append(enabled).append(allowedOnBrowsers).append(freeChannel).append(hDChannel).append(catchupChannel).append(displayOrder).append(numberOfAudio).append(liveWindowDuration).append(imageContent).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Channel) == false) {
            return false;
        }
        Channel rhs = ((Channel) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(channelId, rhs.channelId).append(broadcastServiceId, rhs.broadcastServiceId).append(channelPackager, rhs.channelPackager).append(udpMulticastIP, rhs.udpMulticastIP).append(enabled, rhs.enabled).append(allowedOnBrowsers, rhs.allowedOnBrowsers).append(freeChannel, rhs.freeChannel).append(hDChannel, rhs.hDChannel).append(catchupChannel, rhs.catchupChannel).append(displayOrder, rhs.displayOrder).append(numberOfAudio, rhs.numberOfAudio).append(liveWindowDuration, rhs.liveWindowDuration).append(imageContent, rhs.imageContent).append(links, rhs.links).isEquals();
    }

}
