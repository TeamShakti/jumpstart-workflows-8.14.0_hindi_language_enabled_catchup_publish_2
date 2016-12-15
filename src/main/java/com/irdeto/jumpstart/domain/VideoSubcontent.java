
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
 * videoSubcontent
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "resolution",
    "frameRate",
    "codec",
    "bitRate",
    "isHDContent",
    "protectPolicyGroupId",
    "_links"
})
public class VideoSubcontent
    extends Subcontent
    implements Serializable
{

    /**
     * Resolution
     * <p>
     * 
     * 
     */
    @JsonProperty("resolution")
    private VideoSubcontent.Resolution resolution;
    /**
     * Frame Rate
     * <p>
     * 
     * 
     */
    @JsonProperty("frameRate")
    private VideoSubcontent.FrameRate frameRate;
    /**
     * Codec
     * <p>
     * 
     * 
     */
    @JsonProperty("codec")
    private VideoSubcontent.Codec codec;
    /**
     * Bit Rate
     * <p>
     * 
     * 
     */
    @JsonProperty("bitRate")
    private Integer bitRate;
    /**
     * Is HD Content
     * <p>
     * 
     * 
     */
    @JsonProperty("isHDContent")
    private Boolean isHDContent;
    /**
     * Protect Policy Group ID
     * <p>
     * 
     * 
     */
    @JsonProperty("protectPolicyGroupId")
    private String protectPolicyGroupId;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -49700759957885003L;

    /**
     * Resolution
     * <p>
     * 
     * 
     * @return
     *     The resolution
     */
    @JsonProperty("resolution")
    public VideoSubcontent.Resolution getResolution() {
        return resolution;
    }

    /**
     * Resolution
     * <p>
     * 
     * 
     * @param resolution
     *     The resolution
     */
    @JsonProperty("resolution")
    public void setResolution(VideoSubcontent.Resolution resolution) {
        this.resolution = resolution;
    }

    public VideoSubcontent withResolution(VideoSubcontent.Resolution resolution) {
        this.resolution = resolution;
        return this;
    }

    /**
     * Frame Rate
     * <p>
     * 
     * 
     * @return
     *     The frameRate
     */
    @JsonProperty("frameRate")
    public VideoSubcontent.FrameRate getFrameRate() {
        return frameRate;
    }

    /**
     * Frame Rate
     * <p>
     * 
     * 
     * @param frameRate
     *     The frameRate
     */
    @JsonProperty("frameRate")
    public void setFrameRate(VideoSubcontent.FrameRate frameRate) {
        this.frameRate = frameRate;
    }

    public VideoSubcontent withFrameRate(VideoSubcontent.FrameRate frameRate) {
        this.frameRate = frameRate;
        return this;
    }

    /**
     * Codec
     * <p>
     * 
     * 
     * @return
     *     The codec
     */
    @JsonProperty("codec")
    public VideoSubcontent.Codec getCodec() {
        return codec;
    }

    /**
     * Codec
     * <p>
     * 
     * 
     * @param codec
     *     The codec
     */
    @JsonProperty("codec")
    public void setCodec(VideoSubcontent.Codec codec) {
        this.codec = codec;
    }

    public VideoSubcontent withCodec(VideoSubcontent.Codec codec) {
        this.codec = codec;
        return this;
    }

    /**
     * Bit Rate
     * <p>
     * 
     * 
     * @return
     *     The bitRate
     */
    @JsonProperty("bitRate")
    public Integer getBitRate() {
        return bitRate;
    }

    /**
     * Bit Rate
     * <p>
     * 
     * 
     * @param bitRate
     *     The bitRate
     */
    @JsonProperty("bitRate")
    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
    }

    public VideoSubcontent withBitRate(Integer bitRate) {
        this.bitRate = bitRate;
        return this;
    }

    /**
     * Is HD Content
     * <p>
     * 
     * 
     * @return
     *     The isHDContent
     */
    @JsonProperty("isHDContent")
    public Boolean getIsHDContent() {
        return isHDContent;
    }

    /**
     * Is HD Content
     * <p>
     * 
     * 
     * @param isHDContent
     *     The isHDContent
     */
    @JsonProperty("isHDContent")
    public void setIsHDContent(Boolean isHDContent) {
        this.isHDContent = isHDContent;
    }

    public VideoSubcontent withIsHDContent(Boolean isHDContent) {
        this.isHDContent = isHDContent;
        return this;
    }

    /**
     * Protect Policy Group ID
     * <p>
     * 
     * 
     * @return
     *     The protectPolicyGroupId
     */
    @JsonProperty("protectPolicyGroupId")
    public String getProtectPolicyGroupId() {
        return protectPolicyGroupId;
    }

    /**
     * Protect Policy Group ID
     * <p>
     * 
     * 
     * @param protectPolicyGroupId
     *     The protectPolicyGroupId
     */
    @JsonProperty("protectPolicyGroupId")
    public void setProtectPolicyGroupId(String protectPolicyGroupId) {
        this.protectPolicyGroupId = protectPolicyGroupId;
    }

    public VideoSubcontent withProtectPolicyGroupId(String protectPolicyGroupId) {
        this.protectPolicyGroupId = protectPolicyGroupId;
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

    public VideoSubcontent withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public VideoSubcontent withConsumerUrl(String consumerUrl) {
        super.withConsumerUrl(consumerUrl);
        return this;
    }

    @Override
    public VideoSubcontent withContentFileSize(String contentFileSize) {
        super.withContentFileSize(contentFileSize);
        return this;
    }

    @Override
    public VideoSubcontent withContentCheckSum(String contentCheckSum) {
        super.withContentCheckSum(contentCheckSum);
        return this;
    }

    @Override
    public VideoSubcontent withSourcePath(String sourcePath) {
        super.withSourcePath(sourcePath);
        return this;
    }

    @Override
    public VideoSubcontent withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public VideoSubcontent withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public VideoSubcontent withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public VideoSubcontent withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public VideoSubcontent withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public VideoSubcontent withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public VideoSubcontent withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public VideoSubcontent withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public VideoSubcontent withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public VideoSubcontent withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(resolution).append(frameRate).append(codec).append(bitRate).append(isHDContent).append(protectPolicyGroupId).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof VideoSubcontent) == false) {
            return false;
        }
        VideoSubcontent rhs = ((VideoSubcontent) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(resolution, rhs.resolution).append(frameRate, rhs.frameRate).append(codec, rhs.codec).append(bitRate, rhs.bitRate).append(isHDContent, rhs.isHDContent).append(protectPolicyGroupId, rhs.protectPolicyGroupId).append(links, rhs.links).isEquals();
    }

    public enum Codec {

        MPEG_2("MPEG2"),
        AVC_MP_L_30("AVC MP@L30"),
        AVC_MP_L_42("AVC MP@L42"),
        AVC_HP_L_30("AVC HP@L30"),
        AVC_HP_L_42("AVC HP@L42"),
        MPEG_4_MVC("MPEG4-MVC");
        private final String value;
        private final static Map<String, VideoSubcontent.Codec> CONSTANTS = new HashMap<String, VideoSubcontent.Codec>();

        static {
            for (VideoSubcontent.Codec c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Codec(String value) {
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
        public static VideoSubcontent.Codec fromValue(String value) {
            VideoSubcontent.Codec constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum FrameRate {

        _24("24"),
        _30("30"),
        _60("60");
        private final String value;
        private final static Map<String, VideoSubcontent.FrameRate> CONSTANTS = new HashMap<String, VideoSubcontent.FrameRate>();

        static {
            for (VideoSubcontent.FrameRate c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private FrameRate(String value) {
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
        public static VideoSubcontent.FrameRate fromValue(String value) {
            VideoSubcontent.FrameRate constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum Resolution {

        _480_I("480i"),
        _720_P("720p"),
        _1080_I("1080i"),
        _1080_P("1080p");
        private final String value;
        private final static Map<String, VideoSubcontent.Resolution> CONSTANTS = new HashMap<String, VideoSubcontent.Resolution>();

        static {
            for (VideoSubcontent.Resolution c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Resolution(String value) {
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
        public static VideoSubcontent.Resolution fromValue(String value) {
            VideoSubcontent.Resolution constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
