
package com.irdeto.jumpstart.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * wrapper
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "_link",
    "baseEntity",
    "base",
    "baseMetadata",
    "baseMetadataWithTitle",
    "baseMetadataWithContent",
    "genre",
    "program",
    "series",
    "brand",
    "channel",
    "scheduleSlot",
    "channelDay",
    "event",
    "subscriptionPackage",
    "tvodCollection",
    "offer",
    "term",
    "person",
    "content",
    "videoContent",
    "imageContent",
    "subcontent",
    "subtitleContent",
    "subtitleSubcontent",
    "videoSubcontent",
    "sourceVideoSub",
    "transVideoSub",
    "protectVideoSub",
    "imageSubcontent",
    "termMapping",
    "policyGroupProfile",
    "policyProfile",
    "deviceProfile",
    "baseProtection",
    "encodeProfile",
    "transcodeProfile",
    "protectProfile",
    "ratingScheme",
    "rating"
})
public class MM8DomainObject implements Serializable
{

    /**
     * link
     * <p>
     * 
     * 
     */
    @JsonProperty("_link")
    private Link link;
    /**
     * baseEntity
     * 
     */
    @JsonProperty("baseEntity")
    private BaseEntity baseEntity;
    /**
     * base
     * 
     */
    @JsonProperty("base")
    private Base base;
    /**
     * baseMetadata
     * 
     */
    @JsonProperty("baseMetadata")
    private BaseMetadata baseMetadata;
    /**
     * baseMetadataWithTitle
     * 
     */
    @JsonProperty("baseMetadataWithTitle")
    private BaseMetadataWithTitle baseMetadataWithTitle;
    /**
     * baseMetadataWithContent
     * 
     */
    @JsonProperty("baseMetadataWithContent")
    private BaseMetadataWithContent baseMetadataWithContent;
    /**
     * Genre
     * 
     */
    @JsonProperty("genre")
    private Genre genre;
    /**
     * Program
     * 
     */
    @JsonProperty("program")
    private Program program;
    /**
     * Series
     * 
     */
    @JsonProperty("series")
    private Series series;
    /**
     * Brand
     * 
     */
    @JsonProperty("brand")
    private Brand brand;
    /**
     * Channel
     * 
     */
    @JsonProperty("channel")
    private Channel channel;
    /**
     * Schedule Slot
     * 
     */
    @JsonProperty("scheduleSlot")
    private ScheduleSlot scheduleSlot;
    /**
     * Daily Schedule Slots
     * 
     */
    @JsonProperty("channelDay")
    private ChannelDay channelDay;
    /**
     * Event
     * 
     */
    @JsonProperty("event")
    private Event event;
    /**
     * Subscription Package
     * 
     */
    @JsonProperty("subscriptionPackage")
    private SubscriptionPackage subscriptionPackage;
    /**
     * Collection
     * 
     */
    @JsonProperty("tvodCollection")
    private TvodCollection tvodCollection;
    /**
     * Offer
     * 
     */
    @JsonProperty("offer")
    private Offer offer;
    /**
     * Term
     * 
     */
    @JsonProperty("term")
    private Term term;
    /**
     * Person
     * 
     */
    @JsonProperty("person")
    private Person person;
    /**
     * content
     * 
     */
    @JsonProperty("content")
    private Content content;
    /**
     * Video
     * 
     */
    @JsonProperty("videoContent")
    private VideoContent videoContent;
    /**
     * Image
     * 
     */
    @JsonProperty("imageContent")
    private ImageContent imageContent;
    /**
     * subcontent
     * 
     */
    @JsonProperty("subcontent")
    private Subcontent subcontent;
    /**
     * Subtitle
     * 
     */
    @JsonProperty("subtitleContent")
    private SubtitleContent subtitleContent;
    /**
     * Subtitle Subcontent
     * 
     */
    @JsonProperty("subtitleSubcontent")
    private SubtitleSubcontent subtitleSubcontent;
    /**
     * videoSubcontent
     * 
     */
    @JsonProperty("videoSubcontent")
    private VideoSubcontent videoSubcontent;
    /**
     * Source Video Subcontent
     * 
     */
    @JsonProperty("sourceVideoSub")
    private SourceVideoSub sourceVideoSub;
    /**
     * Transcoded Video Subcontent
     * 
     */
    @JsonProperty("transVideoSub")
    private TransVideoSub transVideoSub;
    /**
     * Protected Video Subcontent
     * 
     */
    @JsonProperty("protectVideoSub")
    private ProtectVideoSub protectVideoSub;
    /**
     * Image Subcontent
     * 
     */
    @JsonProperty("imageSubcontent")
    private ImageSubcontent imageSubcontent;
    /**
     * Term Mapping
     * 
     */
    @JsonProperty("termMapping")
    private TermMapping termMapping;
    /**
     * Policy Group Profile
     * 
     */
    @JsonProperty("policyGroupProfile")
    private PolicyGroupProfile policyGroupProfile;
    /**
     * Policy Profile
     * 
     */
    @JsonProperty("policyProfile")
    private PolicyProfile policyProfile;
    /**
     * Device Profile
     * 
     */
    @JsonProperty("deviceProfile")
    private DeviceProfile deviceProfile;
    /**
     * baseProtection
     * 
     */
    @JsonProperty("baseProtection")
    private BaseProtection baseProtection;
    /**
     * Encode Profile
     * 
     */
    @JsonProperty("encodeProfile")
    private EncodeProfile encodeProfile;
    /**
     * Transcode Profile
     * 
     */
    @JsonProperty("transcodeProfile")
    private TranscodeProfile transcodeProfile;
    /**
     * Protect Profile
     * 
     */
    @JsonProperty("protectProfile")
    private ProtectProfile protectProfile;
    /**
     * Rating Scheme
     * 
     */
    @JsonProperty("ratingScheme")
    private RatingScheme ratingScheme;
    /**
     * Rating
     * 
     */
    @JsonProperty("rating")
    private Rating rating;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -8405910174300935656L;

    /**
     * link
     * <p>
     * 
     * 
     * @return
     *     The link
     */
    @JsonProperty("_link")
    public Link getLink() {
        return link;
    }

    /**
     * link
     * <p>
     * 
     * 
     * @param link
     *     The _link
     */
    @JsonProperty("_link")
    public void setLink(Link link) {
        this.link = link;
    }

    public MM8DomainObject withLink(Link link) {
        this.link = link;
        return this;
    }

    /**
     * baseEntity
     * 
     * @return
     *     The baseEntity
     */
    @JsonProperty("baseEntity")
    public BaseEntity getBaseEntity() {
        return baseEntity;
    }

    /**
     * baseEntity
     * 
     * @param baseEntity
     *     The baseEntity
     */
    @JsonProperty("baseEntity")
    public void setBaseEntity(BaseEntity baseEntity) {
        this.baseEntity = baseEntity;
    }

    public MM8DomainObject withBaseEntity(BaseEntity baseEntity) {
        this.baseEntity = baseEntity;
        return this;
    }

    /**
     * base
     * 
     * @return
     *     The base
     */
    @JsonProperty("base")
    public Base getBase() {
        return base;
    }

    /**
     * base
     * 
     * @param base
     *     The base
     */
    @JsonProperty("base")
    public void setBase(Base base) {
        this.base = base;
    }

    public MM8DomainObject withBase(Base base) {
        this.base = base;
        return this;
    }

    /**
     * baseMetadata
     * 
     * @return
     *     The baseMetadata
     */
    @JsonProperty("baseMetadata")
    public BaseMetadata getBaseMetadata() {
        return baseMetadata;
    }

    /**
     * baseMetadata
     * 
     * @param baseMetadata
     *     The baseMetadata
     */
    @JsonProperty("baseMetadata")
    public void setBaseMetadata(BaseMetadata baseMetadata) {
        this.baseMetadata = baseMetadata;
    }

    public MM8DomainObject withBaseMetadata(BaseMetadata baseMetadata) {
        this.baseMetadata = baseMetadata;
        return this;
    }

    /**
     * baseMetadataWithTitle
     * 
     * @return
     *     The baseMetadataWithTitle
     */
    @JsonProperty("baseMetadataWithTitle")
    public BaseMetadataWithTitle getBaseMetadataWithTitle() {
        return baseMetadataWithTitle;
    }

    /**
     * baseMetadataWithTitle
     * 
     * @param baseMetadataWithTitle
     *     The baseMetadataWithTitle
     */
    @JsonProperty("baseMetadataWithTitle")
    public void setBaseMetadataWithTitle(BaseMetadataWithTitle baseMetadataWithTitle) {
        this.baseMetadataWithTitle = baseMetadataWithTitle;
    }

    public MM8DomainObject withBaseMetadataWithTitle(BaseMetadataWithTitle baseMetadataWithTitle) {
        this.baseMetadataWithTitle = baseMetadataWithTitle;
        return this;
    }

    /**
     * baseMetadataWithContent
     * 
     * @return
     *     The baseMetadataWithContent
     */
    @JsonProperty("baseMetadataWithContent")
    public BaseMetadataWithContent getBaseMetadataWithContent() {
        return baseMetadataWithContent;
    }

    /**
     * baseMetadataWithContent
     * 
     * @param baseMetadataWithContent
     *     The baseMetadataWithContent
     */
    @JsonProperty("baseMetadataWithContent")
    public void setBaseMetadataWithContent(BaseMetadataWithContent baseMetadataWithContent) {
        this.baseMetadataWithContent = baseMetadataWithContent;
    }

    public MM8DomainObject withBaseMetadataWithContent(BaseMetadataWithContent baseMetadataWithContent) {
        this.baseMetadataWithContent = baseMetadataWithContent;
        return this;
    }

    /**
     * Genre
     * 
     * @return
     *     The genre
     */
    @JsonProperty("genre")
    public Genre getGenre() {
        return genre;
    }

    /**
     * Genre
     * 
     * @param genre
     *     The genre
     */
    @JsonProperty("genre")
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public MM8DomainObject withGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    /**
     * Program
     * 
     * @return
     *     The program
     */
    @JsonProperty("program")
    public Program getProgram() {
        return program;
    }

    /**
     * Program
     * 
     * @param program
     *     The program
     */
    @JsonProperty("program")
    public void setProgram(Program program) {
        this.program = program;
    }

    public MM8DomainObject withProgram(Program program) {
        this.program = program;
        return this;
    }

    /**
     * Series
     * 
     * @return
     *     The series
     */
    @JsonProperty("series")
    public Series getSeries() {
        return series;
    }

    /**
     * Series
     * 
     * @param series
     *     The series
     */
    @JsonProperty("series")
    public void setSeries(Series series) {
        this.series = series;
    }

    public MM8DomainObject withSeries(Series series) {
        this.series = series;
        return this;
    }

    /**
     * Brand
     * 
     * @return
     *     The brand
     */
    @JsonProperty("brand")
    public Brand getBrand() {
        return brand;
    }

    /**
     * Brand
     * 
     * @param brand
     *     The brand
     */
    @JsonProperty("brand")
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public MM8DomainObject withBrand(Brand brand) {
        this.brand = brand;
        return this;
    }

    /**
     * Channel
     * 
     * @return
     *     The channel
     */
    @JsonProperty("channel")
    public Channel getChannel() {
        return channel;
    }

    /**
     * Channel
     * 
     * @param channel
     *     The channel
     */
    @JsonProperty("channel")
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public MM8DomainObject withChannel(Channel channel) {
        this.channel = channel;
        return this;
    }

    /**
     * Schedule Slot
     * 
     * @return
     *     The scheduleSlot
     */
    @JsonProperty("scheduleSlot")
    public ScheduleSlot getScheduleSlot() {
        return scheduleSlot;
    }

    /**
     * Schedule Slot
     * 
     * @param scheduleSlot
     *     The scheduleSlot
     */
    @JsonProperty("scheduleSlot")
    public void setScheduleSlot(ScheduleSlot scheduleSlot) {
        this.scheduleSlot = scheduleSlot;
    }

    public MM8DomainObject withScheduleSlot(ScheduleSlot scheduleSlot) {
        this.scheduleSlot = scheduleSlot;
        return this;
    }

    /**
     * Daily Schedule Slots
     * 
     * @return
     *     The channelDay
     */
    @JsonProperty("channelDay")
    public ChannelDay getChannelDay() {
        return channelDay;
    }

    /**
     * Daily Schedule Slots
     * 
     * @param channelDay
     *     The channelDay
     */
    @JsonProperty("channelDay")
    public void setChannelDay(ChannelDay channelDay) {
        this.channelDay = channelDay;
    }

    public MM8DomainObject withChannelDay(ChannelDay channelDay) {
        this.channelDay = channelDay;
        return this;
    }

    /**
     * Event
     * 
     * @return
     *     The event
     */
    @JsonProperty("event")
    public Event getEvent() {
        return event;
    }

    /**
     * Event
     * 
     * @param event
     *     The event
     */
    @JsonProperty("event")
    public void setEvent(Event event) {
        this.event = event;
    }

    public MM8DomainObject withEvent(Event event) {
        this.event = event;
        return this;
    }

    /**
     * Subscription Package
     * 
     * @return
     *     The subscriptionPackage
     */
    @JsonProperty("subscriptionPackage")
    public SubscriptionPackage getSubscriptionPackage() {
        return subscriptionPackage;
    }

    /**
     * Subscription Package
     * 
     * @param subscriptionPackage
     *     The subscriptionPackage
     */
    @JsonProperty("subscriptionPackage")
    public void setSubscriptionPackage(SubscriptionPackage subscriptionPackage) {
        this.subscriptionPackage = subscriptionPackage;
    }

    public MM8DomainObject withSubscriptionPackage(SubscriptionPackage subscriptionPackage) {
        this.subscriptionPackage = subscriptionPackage;
        return this;
    }

    /**
     * Collection
     * 
     * @return
     *     The tvodCollection
     */
    @JsonProperty("tvodCollection")
    public TvodCollection getTvodCollection() {
        return tvodCollection;
    }

    /**
     * Collection
     * 
     * @param tvodCollection
     *     The tvodCollection
     */
    @JsonProperty("tvodCollection")
    public void setTvodCollection(TvodCollection tvodCollection) {
        this.tvodCollection = tvodCollection;
    }

    public MM8DomainObject withTvodCollection(TvodCollection tvodCollection) {
        this.tvodCollection = tvodCollection;
        return this;
    }

    /**
     * Offer
     * 
     * @return
     *     The offer
     */
    @JsonProperty("offer")
    public Offer getOffer() {
        return offer;
    }

    /**
     * Offer
     * 
     * @param offer
     *     The offer
     */
    @JsonProperty("offer")
    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public MM8DomainObject withOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    /**
     * Term
     * 
     * @return
     *     The term
     */
    @JsonProperty("term")
    public Term getTerm() {
        return term;
    }

    /**
     * Term
     * 
     * @param term
     *     The term
     */
    @JsonProperty("term")
    public void setTerm(Term term) {
        this.term = term;
    }

    public MM8DomainObject withTerm(Term term) {
        this.term = term;
        return this;
    }

    /**
     * Person
     * 
     * @return
     *     The person
     */
    @JsonProperty("person")
    public Person getPerson() {
        return person;
    }

    /**
     * Person
     * 
     * @param person
     *     The person
     */
    @JsonProperty("person")
    public void setPerson(Person person) {
        this.person = person;
    }

    public MM8DomainObject withPerson(Person person) {
        this.person = person;
        return this;
    }

    /**
     * content
     * 
     * @return
     *     The content
     */
    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    /**
     * content
     * 
     * @param content
     *     The content
     */
    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
    }

    public MM8DomainObject withContent(Content content) {
        this.content = content;
        return this;
    }

    /**
     * Video
     * 
     * @return
     *     The videoContent
     */
    @JsonProperty("videoContent")
    public VideoContent getVideoContent() {
        return videoContent;
    }

    /**
     * Video
     * 
     * @param videoContent
     *     The videoContent
     */
    @JsonProperty("videoContent")
    public void setVideoContent(VideoContent videoContent) {
        this.videoContent = videoContent;
    }

    public MM8DomainObject withVideoContent(VideoContent videoContent) {
        this.videoContent = videoContent;
        return this;
    }

    /**
     * Image
     * 
     * @return
     *     The imageContent
     */
    @JsonProperty("imageContent")
    public ImageContent getImageContent() {
        return imageContent;
    }

    /**
     * Image
     * 
     * @param imageContent
     *     The imageContent
     */
    @JsonProperty("imageContent")
    public void setImageContent(ImageContent imageContent) {
        this.imageContent = imageContent;
    }

    public MM8DomainObject withImageContent(ImageContent imageContent) {
        this.imageContent = imageContent;
        return this;
    }

    /**
     * subcontent
     * 
     * @return
     *     The subcontent
     */
    @JsonProperty("subcontent")
    public Subcontent getSubcontent() {
        return subcontent;
    }

    /**
     * subcontent
     * 
     * @param subcontent
     *     The subcontent
     */
    @JsonProperty("subcontent")
    public void setSubcontent(Subcontent subcontent) {
        this.subcontent = subcontent;
    }

    public MM8DomainObject withSubcontent(Subcontent subcontent) {
        this.subcontent = subcontent;
        return this;
    }

    /**
     * Subtitle
     * 
     * @return
     *     The subtitleContent
     */
    @JsonProperty("subtitleContent")
    public SubtitleContent getSubtitleContent() {
        return subtitleContent;
    }

    /**
     * Subtitle
     * 
     * @param subtitleContent
     *     The subtitleContent
     */
    @JsonProperty("subtitleContent")
    public void setSubtitleContent(SubtitleContent subtitleContent) {
        this.subtitleContent = subtitleContent;
    }

    public MM8DomainObject withSubtitleContent(SubtitleContent subtitleContent) {
        this.subtitleContent = subtitleContent;
        return this;
    }

    /**
     * Subtitle Subcontent
     * 
     * @return
     *     The subtitleSubcontent
     */
    @JsonProperty("subtitleSubcontent")
    public SubtitleSubcontent getSubtitleSubcontent() {
        return subtitleSubcontent;
    }

    /**
     * Subtitle Subcontent
     * 
     * @param subtitleSubcontent
     *     The subtitleSubcontent
     */
    @JsonProperty("subtitleSubcontent")
    public void setSubtitleSubcontent(SubtitleSubcontent subtitleSubcontent) {
        this.subtitleSubcontent = subtitleSubcontent;
    }

    public MM8DomainObject withSubtitleSubcontent(SubtitleSubcontent subtitleSubcontent) {
        this.subtitleSubcontent = subtitleSubcontent;
        return this;
    }

    /**
     * videoSubcontent
     * 
     * @return
     *     The videoSubcontent
     */
    @JsonProperty("videoSubcontent")
    public VideoSubcontent getVideoSubcontent() {
        return videoSubcontent;
    }

    /**
     * videoSubcontent
     * 
     * @param videoSubcontent
     *     The videoSubcontent
     */
    @JsonProperty("videoSubcontent")
    public void setVideoSubcontent(VideoSubcontent videoSubcontent) {
        this.videoSubcontent = videoSubcontent;
    }

    public MM8DomainObject withVideoSubcontent(VideoSubcontent videoSubcontent) {
        this.videoSubcontent = videoSubcontent;
        return this;
    }

    /**
     * Source Video Subcontent
     * 
     * @return
     *     The sourceVideoSub
     */
    @JsonProperty("sourceVideoSub")
    public SourceVideoSub getSourceVideoSub() {
        return sourceVideoSub;
    }

    /**
     * Source Video Subcontent
     * 
     * @param sourceVideoSub
     *     The sourceVideoSub
     */
    @JsonProperty("sourceVideoSub")
    public void setSourceVideoSub(SourceVideoSub sourceVideoSub) {
        this.sourceVideoSub = sourceVideoSub;
    }

    public MM8DomainObject withSourceVideoSub(SourceVideoSub sourceVideoSub) {
        this.sourceVideoSub = sourceVideoSub;
        return this;
    }

    /**
     * Transcoded Video Subcontent
     * 
     * @return
     *     The transVideoSub
     */
    @JsonProperty("transVideoSub")
    public TransVideoSub getTransVideoSub() {
        return transVideoSub;
    }

    /**
     * Transcoded Video Subcontent
     * 
     * @param transVideoSub
     *     The transVideoSub
     */
    @JsonProperty("transVideoSub")
    public void setTransVideoSub(TransVideoSub transVideoSub) {
        this.transVideoSub = transVideoSub;
    }

    public MM8DomainObject withTransVideoSub(TransVideoSub transVideoSub) {
        this.transVideoSub = transVideoSub;
        return this;
    }

    /**
     * Protected Video Subcontent
     * 
     * @return
     *     The protectVideoSub
     */
    @JsonProperty("protectVideoSub")
    public ProtectVideoSub getProtectVideoSub() {
        return protectVideoSub;
    }

    /**
     * Protected Video Subcontent
     * 
     * @param protectVideoSub
     *     The protectVideoSub
     */
    @JsonProperty("protectVideoSub")
    public void setProtectVideoSub(ProtectVideoSub protectVideoSub) {
        this.protectVideoSub = protectVideoSub;
    }

    public MM8DomainObject withProtectVideoSub(ProtectVideoSub protectVideoSub) {
        this.protectVideoSub = protectVideoSub;
        return this;
    }

    /**
     * Image Subcontent
     * 
     * @return
     *     The imageSubcontent
     */
    @JsonProperty("imageSubcontent")
    public ImageSubcontent getImageSubcontent() {
        return imageSubcontent;
    }

    /**
     * Image Subcontent
     * 
     * @param imageSubcontent
     *     The imageSubcontent
     */
    @JsonProperty("imageSubcontent")
    public void setImageSubcontent(ImageSubcontent imageSubcontent) {
        this.imageSubcontent = imageSubcontent;
    }

    public MM8DomainObject withImageSubcontent(ImageSubcontent imageSubcontent) {
        this.imageSubcontent = imageSubcontent;
        return this;
    }

    /**
     * Term Mapping
     * 
     * @return
     *     The termMapping
     */
    @JsonProperty("termMapping")
    public TermMapping getTermMapping() {
        return termMapping;
    }

    /**
     * Term Mapping
     * 
     * @param termMapping
     *     The termMapping
     */
    @JsonProperty("termMapping")
    public void setTermMapping(TermMapping termMapping) {
        this.termMapping = termMapping;
    }

    public MM8DomainObject withTermMapping(TermMapping termMapping) {
        this.termMapping = termMapping;
        return this;
    }

    /**
     * Policy Group Profile
     * 
     * @return
     *     The policyGroupProfile
     */
    @JsonProperty("policyGroupProfile")
    public PolicyGroupProfile getPolicyGroupProfile() {
        return policyGroupProfile;
    }

    /**
     * Policy Group Profile
     * 
     * @param policyGroupProfile
     *     The policyGroupProfile
     */
    @JsonProperty("policyGroupProfile")
    public void setPolicyGroupProfile(PolicyGroupProfile policyGroupProfile) {
        this.policyGroupProfile = policyGroupProfile;
    }

    public MM8DomainObject withPolicyGroupProfile(PolicyGroupProfile policyGroupProfile) {
        this.policyGroupProfile = policyGroupProfile;
        return this;
    }

    /**
     * Policy Profile
     * 
     * @return
     *     The policyProfile
     */
    @JsonProperty("policyProfile")
    public PolicyProfile getPolicyProfile() {
        return policyProfile;
    }

    /**
     * Policy Profile
     * 
     * @param policyProfile
     *     The policyProfile
     */
    @JsonProperty("policyProfile")
    public void setPolicyProfile(PolicyProfile policyProfile) {
        this.policyProfile = policyProfile;
    }

    public MM8DomainObject withPolicyProfile(PolicyProfile policyProfile) {
        this.policyProfile = policyProfile;
        return this;
    }

    /**
     * Device Profile
     * 
     * @return
     *     The deviceProfile
     */
    @JsonProperty("deviceProfile")
    public DeviceProfile getDeviceProfile() {
        return deviceProfile;
    }

    /**
     * Device Profile
     * 
     * @param deviceProfile
     *     The deviceProfile
     */
    @JsonProperty("deviceProfile")
    public void setDeviceProfile(DeviceProfile deviceProfile) {
        this.deviceProfile = deviceProfile;
    }

    public MM8DomainObject withDeviceProfile(DeviceProfile deviceProfile) {
        this.deviceProfile = deviceProfile;
        return this;
    }

    /**
     * baseProtection
     * 
     * @return
     *     The baseProtection
     */
    @JsonProperty("baseProtection")
    public BaseProtection getBaseProtection() {
        return baseProtection;
    }

    /**
     * baseProtection
     * 
     * @param baseProtection
     *     The baseProtection
     */
    @JsonProperty("baseProtection")
    public void setBaseProtection(BaseProtection baseProtection) {
        this.baseProtection = baseProtection;
    }

    public MM8DomainObject withBaseProtection(BaseProtection baseProtection) {
        this.baseProtection = baseProtection;
        return this;
    }

    /**
     * Encode Profile
     * 
     * @return
     *     The encodeProfile
     */
    @JsonProperty("encodeProfile")
    public EncodeProfile getEncodeProfile() {
        return encodeProfile;
    }

    /**
     * Encode Profile
     * 
     * @param encodeProfile
     *     The encodeProfile
     */
    @JsonProperty("encodeProfile")
    public void setEncodeProfile(EncodeProfile encodeProfile) {
        this.encodeProfile = encodeProfile;
    }

    public MM8DomainObject withEncodeProfile(EncodeProfile encodeProfile) {
        this.encodeProfile = encodeProfile;
        return this;
    }

    /**
     * Transcode Profile
     * 
     * @return
     *     The transcodeProfile
     */
    @JsonProperty("transcodeProfile")
    public TranscodeProfile getTranscodeProfile() {
        return transcodeProfile;
    }

    /**
     * Transcode Profile
     * 
     * @param transcodeProfile
     *     The transcodeProfile
     */
    @JsonProperty("transcodeProfile")
    public void setTranscodeProfile(TranscodeProfile transcodeProfile) {
        this.transcodeProfile = transcodeProfile;
    }

    public MM8DomainObject withTranscodeProfile(TranscodeProfile transcodeProfile) {
        this.transcodeProfile = transcodeProfile;
        return this;
    }

    /**
     * Protect Profile
     * 
     * @return
     *     The protectProfile
     */
    @JsonProperty("protectProfile")
    public ProtectProfile getProtectProfile() {
        return protectProfile;
    }

    /**
     * Protect Profile
     * 
     * @param protectProfile
     *     The protectProfile
     */
    @JsonProperty("protectProfile")
    public void setProtectProfile(ProtectProfile protectProfile) {
        this.protectProfile = protectProfile;
    }

    public MM8DomainObject withProtectProfile(ProtectProfile protectProfile) {
        this.protectProfile = protectProfile;
        return this;
    }

    /**
     * Rating Scheme
     * 
     * @return
     *     The ratingScheme
     */
    @JsonProperty("ratingScheme")
    public RatingScheme getRatingScheme() {
        return ratingScheme;
    }

    /**
     * Rating Scheme
     * 
     * @param ratingScheme
     *     The ratingScheme
     */
    @JsonProperty("ratingScheme")
    public void setRatingScheme(RatingScheme ratingScheme) {
        this.ratingScheme = ratingScheme;
    }

    public MM8DomainObject withRatingScheme(RatingScheme ratingScheme) {
        this.ratingScheme = ratingScheme;
        return this;
    }

    /**
     * Rating
     * 
     * @return
     *     The rating
     */
    @JsonProperty("rating")
    public Rating getRating() {
        return rating;
    }

    /**
     * Rating
     * 
     * @param rating
     *     The rating
     */
    @JsonProperty("rating")
    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public MM8DomainObject withRating(Rating rating) {
        this.rating = rating;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public MM8DomainObject withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(link).append(baseEntity).append(base).append(baseMetadata).append(baseMetadataWithTitle).append(baseMetadataWithContent).append(genre).append(program).append(series).append(brand).append(channel).append(scheduleSlot).append(channelDay).append(event).append(subscriptionPackage).append(tvodCollection).append(offer).append(term).append(person).append(content).append(videoContent).append(imageContent).append(subcontent).append(subtitleContent).append(subtitleSubcontent).append(videoSubcontent).append(sourceVideoSub).append(transVideoSub).append(protectVideoSub).append(imageSubcontent).append(termMapping).append(policyGroupProfile).append(policyProfile).append(deviceProfile).append(baseProtection).append(encodeProfile).append(transcodeProfile).append(protectProfile).append(ratingScheme).append(rating).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MM8DomainObject) == false) {
            return false;
        }
        MM8DomainObject rhs = ((MM8DomainObject) other);
        return new EqualsBuilder().append(link, rhs.link).append(baseEntity, rhs.baseEntity).append(base, rhs.base).append(baseMetadata, rhs.baseMetadata).append(baseMetadataWithTitle, rhs.baseMetadataWithTitle).append(baseMetadataWithContent, rhs.baseMetadataWithContent).append(genre, rhs.genre).append(program, rhs.program).append(series, rhs.series).append(brand, rhs.brand).append(channel, rhs.channel).append(scheduleSlot, rhs.scheduleSlot).append(channelDay, rhs.channelDay).append(event, rhs.event).append(subscriptionPackage, rhs.subscriptionPackage).append(tvodCollection, rhs.tvodCollection).append(offer, rhs.offer).append(term, rhs.term).append(person, rhs.person).append(content, rhs.content).append(videoContent, rhs.videoContent).append(imageContent, rhs.imageContent).append(subcontent, rhs.subcontent).append(subtitleContent, rhs.subtitleContent).append(subtitleSubcontent, rhs.subtitleSubcontent).append(videoSubcontent, rhs.videoSubcontent).append(sourceVideoSub, rhs.sourceVideoSub).append(transVideoSub, rhs.transVideoSub).append(protectVideoSub, rhs.protectVideoSub).append(imageSubcontent, rhs.imageSubcontent).append(termMapping, rhs.termMapping).append(policyGroupProfile, rhs.policyGroupProfile).append(policyProfile, rhs.policyProfile).append(deviceProfile, rhs.deviceProfile).append(baseProtection, rhs.baseProtection).append(encodeProfile, rhs.encodeProfile).append(transcodeProfile, rhs.transcodeProfile).append(protectProfile, rhs.protectProfile).append(ratingScheme, rhs.ratingScheme).append(rating, rhs.rating).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
