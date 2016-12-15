
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
 * Video
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "contentType",
    "duration",
    "screenFormat",
    "language",
    "subtitleLanguage",
    "dubbedLanguage",
    "subcontent",
    "subtitleContent",
    "_links"
})
public class VideoContent
    extends Content
    implements Serializable
{

    /**
     * Content Type
     * <p>
     * 
     * 
     */
    @JsonProperty("contentType")
    private VideoContent.ContentType contentType;
    /**
     * Duration
     * <p>
     * 
     * 
     */
    @JsonProperty("duration")
    private String duration;
    /**
     * Screen Format
     * <p>
     * 
     * 
     */
    @JsonProperty("screenFormat")
    private VideoContent.ScreenFormat screenFormat;
    /**
     * Language
     * <p>
     * 
     * 
     */
    @JsonProperty("language")
    private List<Object> language = new ArrayList<Object>();
    /**
     * Subtitle Langauge
     * <p>
     * 
     * 
     */
    @JsonProperty("subtitleLanguage")
    private List<Object> subtitleLanguage = new ArrayList<Object>();
    /**
     * Dubbed Language
     * <p>
     * 
     * 
     */
    @JsonProperty("dubbedLanguage")
    private List<Object> dubbedLanguage = new ArrayList<Object>();
    @JsonProperty("subcontent")
    private List<SourceVideoSub> subcontent = new ArrayList<SourceVideoSub>();
    @JsonProperty("subtitleContent")
    private List<SubtitleContent> subtitleContent = new ArrayList<SubtitleContent>();
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -5862566590890428628L;

    /**
     * Content Type
     * <p>
     * 
     * 
     * @return
     *     The contentType
     */
    @JsonProperty("contentType")
    public VideoContent.ContentType getContentType() {
        return contentType;
    }

    /**
     * Content Type
     * <p>
     * 
     * 
     * @param contentType
     *     The contentType
     */
    @JsonProperty("contentType")
    public void setContentType(VideoContent.ContentType contentType) {
        this.contentType = contentType;
    }

    public VideoContent withContentType(VideoContent.ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * Duration
     * <p>
     * 
     * 
     * @return
     *     The duration
     */
    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    /**
     * Duration
     * <p>
     * 
     * 
     * @param duration
     *     The duration
     */
    @JsonProperty("duration")
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public VideoContent withDuration(String duration) {
        this.duration = duration;
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
    public VideoContent.ScreenFormat getScreenFormat() {
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
    public void setScreenFormat(VideoContent.ScreenFormat screenFormat) {
        this.screenFormat = screenFormat;
    }

    public VideoContent withScreenFormat(VideoContent.ScreenFormat screenFormat) {
        this.screenFormat = screenFormat;
        return this;
    }

    /**
     * Language
     * <p>
     * 
     * 
     * @return
     *     The language
     */
    @JsonProperty("language")
    public List<Object> getLanguage() {
        return language;
    }

    /**
     * Language
     * <p>
     * 
     * 
     * @param language
     *     The language
     */
    @JsonProperty("language")
    public void setLanguage(List<Object> language) {
        this.language = language;
    }

    public VideoContent withLanguage(List<Object> language) {
        this.language = language;
        return this;
    }

    /**
     * Subtitle Langauge
     * <p>
     * 
     * 
     * @return
     *     The subtitleLanguage
     */
    @JsonProperty("subtitleLanguage")
    public List<Object> getSubtitleLanguage() {
        return subtitleLanguage;
    }

    /**
     * Subtitle Langauge
     * <p>
     * 
     * 
     * @param subtitleLanguage
     *     The subtitleLanguage
     */
    @JsonProperty("subtitleLanguage")
    public void setSubtitleLanguage(List<Object> subtitleLanguage) {
        this.subtitleLanguage = subtitleLanguage;
    }

    public VideoContent withSubtitleLanguage(List<Object> subtitleLanguage) {
        this.subtitleLanguage = subtitleLanguage;
        return this;
    }

    /**
     * Dubbed Language
     * <p>
     * 
     * 
     * @return
     *     The dubbedLanguage
     */
    @JsonProperty("dubbedLanguage")
    public List<Object> getDubbedLanguage() {
        return dubbedLanguage;
    }

    /**
     * Dubbed Language
     * <p>
     * 
     * 
     * @param dubbedLanguage
     *     The dubbedLanguage
     */
    @JsonProperty("dubbedLanguage")
    public void setDubbedLanguage(List<Object> dubbedLanguage) {
        this.dubbedLanguage = dubbedLanguage;
    }

    public VideoContent withDubbedLanguage(List<Object> dubbedLanguage) {
        this.dubbedLanguage = dubbedLanguage;
        return this;
    }

    /**
     * 
     * @return
     *     The subcontent
     */
    @JsonProperty("subcontent")
    public List<SourceVideoSub> getSubcontent() {
        return subcontent;
    }

    /**
     * 
     * @param subcontent
     *     The subcontent
     */
    @JsonProperty("subcontent")
    public void setSubcontent(List<SourceVideoSub> subcontent) {
        this.subcontent = subcontent;
    }

    public VideoContent withSubcontent(List<SourceVideoSub> subcontent) {
        this.subcontent = subcontent;
        return this;
    }

    /**
     * 
     * @return
     *     The subtitleContent
     */
    @JsonProperty("subtitleContent")
    public List<SubtitleContent> getSubtitleContent() {
        return subtitleContent;
    }

    /**
     * 
     * @param subtitleContent
     *     The subtitleContent
     */
    @JsonProperty("subtitleContent")
    public void setSubtitleContent(List<SubtitleContent> subtitleContent) {
        this.subtitleContent = subtitleContent;
    }

    public VideoContent withSubtitleContent(List<SubtitleContent> subtitleContent) {
        this.subtitleContent = subtitleContent;
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

    public VideoContent withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public VideoContent withSourceUrl(String sourceUrl) {
        super.withSourceUrl(sourceUrl);
        return this;
    }

    @Override
    public VideoContent withFileSize(String fileSize) {
        super.withFileSize(fileSize);
        return this;
    }

    @Override
    public VideoContent withCheckSum(String checkSum) {
        super.withCheckSum(checkSum);
        return this;
    }

    @Override
    public VideoContent withSourceVersion(Integer sourceVersion) {
        super.withSourceVersion(sourceVersion);
        return this;
    }

    @Override
    public VideoContent withPublishVersion(Integer publishVersion) {
        super.withPublishVersion(publishVersion);
        return this;
    }

    @Override
    public VideoContent withIsPublished(Boolean isPublished) {
        super.withIsPublished(isPublished);
        return this;
    }

    @Override
    public VideoContent withPublishedDate(DateTime publishedDate) {
        super.withPublishedDate(publishedDate);
        return this;
    }

    @Override
    public VideoContent withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public VideoContent withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public VideoContent withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public VideoContent withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public VideoContent withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public VideoContent withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public VideoContent withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public VideoContent withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public VideoContent withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public VideoContent withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public VideoContent withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public VideoContent withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public VideoContent withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public VideoContent withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public VideoContent withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public VideoContent withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(contentType).append(duration).append(screenFormat).append(language).append(subtitleLanguage).append(dubbedLanguage).append(subcontent).append(subtitleContent).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof VideoContent) == false) {
            return false;
        }
        VideoContent rhs = ((VideoContent) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(contentType, rhs.contentType).append(duration, rhs.duration).append(screenFormat, rhs.screenFormat).append(language, rhs.language).append(subtitleLanguage, rhs.subtitleLanguage).append(dubbedLanguage, rhs.dubbedLanguage).append(subcontent, rhs.subcontent).append(subtitleContent, rhs.subtitleContent).append(links, rhs.links).isEquals();
    }

    public enum ContentType {

        MOVIE("movie"),
        PREVIEW("preview"),
        BARKER("barker");
        private final String value;
        private final static Map<String, VideoContent.ContentType> CONSTANTS = new HashMap<String, VideoContent.ContentType>();

        static {
            for (VideoContent.ContentType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private ContentType(String value) {
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
        public static VideoContent.ContentType fromValue(String value) {
            VideoContent.ContentType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ScreenFormat {

        STANDARD("Standard"),
        WIDESCREEN("Widescreen"),
        LETTERBOX("Letterbox"),
        OAR("OAR");
        private final String value;
        private final static Map<String, VideoContent.ScreenFormat> CONSTANTS = new HashMap<String, VideoContent.ScreenFormat>();

        static {
            for (VideoContent.ScreenFormat c: values()) {
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
        public static VideoContent.ScreenFormat fromValue(String value) {
            VideoContent.ScreenFormat constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
