
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
 * Image
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "contentType",
    "xResolution",
    "yResolution",
    "subcontent",
    "_links"
})
public class ImageContent
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
    private ImageContent.ContentType contentType;
    /**
     * X Resolution
     * <p>
     * 
     * 
     */
    @JsonProperty("xResolution")
    private String xResolution;
    /**
     * Y Resolution
     * <p>
     * 
     * 
     */
    @JsonProperty("yResolution")
    private String yResolution;
    @JsonProperty("subcontent")
    private List<ImageSubcontent> subcontent = new ArrayList<ImageSubcontent>();
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 5764541812221433461L;

    /**
     * Content Type
     * <p>
     * 
     * 
     * @return
     *     The contentType
     */
    @JsonProperty("contentType")
    public ImageContent.ContentType getContentType() {
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
    public void setContentType(ImageContent.ContentType contentType) {
        this.contentType = contentType;
    }

    public ImageContent withContentType(ImageContent.ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * X Resolution
     * <p>
     * 
     * 
     * @return
     *     The xResolution
     */
    @JsonProperty("xResolution")
    public String getXResolution() {
        return xResolution;
    }

    /**
     * X Resolution
     * <p>
     * 
     * 
     * @param xResolution
     *     The xResolution
     */
    @JsonProperty("xResolution")
    public void setXResolution(String xResolution) {
        this.xResolution = xResolution;
    }

    public ImageContent withXResolution(String xResolution) {
        this.xResolution = xResolution;
        return this;
    }

    /**
     * Y Resolution
     * <p>
     * 
     * 
     * @return
     *     The yResolution
     */
    @JsonProperty("yResolution")
    public String getYResolution() {
        return yResolution;
    }

    /**
     * Y Resolution
     * <p>
     * 
     * 
     * @param yResolution
     *     The yResolution
     */
    @JsonProperty("yResolution")
    public void setYResolution(String yResolution) {
        this.yResolution = yResolution;
    }

    public ImageContent withYResolution(String yResolution) {
        this.yResolution = yResolution;
        return this;
    }

    /**
     * 
     * @return
     *     The subcontent
     */
    @JsonProperty("subcontent")
    public List<ImageSubcontent> getSubcontent() {
        return subcontent;
    }

    /**
     * 
     * @param subcontent
     *     The subcontent
     */
    @JsonProperty("subcontent")
    public void setSubcontent(List<ImageSubcontent> subcontent) {
        this.subcontent = subcontent;
    }

    public ImageContent withSubcontent(List<ImageSubcontent> subcontent) {
        this.subcontent = subcontent;
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

    public ImageContent withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public ImageContent withSourceUrl(String sourceUrl) {
        super.withSourceUrl(sourceUrl);
        return this;
    }

    @Override
    public ImageContent withFileSize(String fileSize) {
        super.withFileSize(fileSize);
        return this;
    }

    @Override
    public ImageContent withCheckSum(String checkSum) {
        super.withCheckSum(checkSum);
        return this;
    }

    @Override
    public ImageContent withSourceVersion(Integer sourceVersion) {
        super.withSourceVersion(sourceVersion);
        return this;
    }

    @Override
    public ImageContent withPublishVersion(Integer publishVersion) {
        super.withPublishVersion(publishVersion);
        return this;
    }

    @Override
    public ImageContent withIsPublished(Boolean isPublished) {
        super.withIsPublished(isPublished);
        return this;
    }

    @Override
    public ImageContent withPublishedDate(DateTime publishedDate) {
        super.withPublishedDate(publishedDate);
        return this;
    }

    @Override
    public ImageContent withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public ImageContent withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public ImageContent withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public ImageContent withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public ImageContent withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public ImageContent withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public ImageContent withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public ImageContent withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public ImageContent withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public ImageContent withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public ImageContent withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public ImageContent withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public ImageContent withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public ImageContent withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public ImageContent withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public ImageContent withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(contentType).append(xResolution).append(yResolution).append(subcontent).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ImageContent) == false) {
            return false;
        }
        ImageContent rhs = ((ImageContent) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(contentType, rhs.contentType).append(xResolution, rhs.xResolution).append(yResolution, rhs.yResolution).append(subcontent, rhs.subcontent).append(links, rhs.links).isEquals();
    }

    public enum ContentType {

        POSTER("poster"),
        BOX_COVER("boxCover"),
        THUMBNAIL("thumbnail");
        private final String value;
        private final static Map<String, ImageContent.ContentType> CONSTANTS = new HashMap<String, ImageContent.ContentType>();

        static {
            for (ImageContent.ContentType c: values()) {
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
        public static ImageContent.ContentType fromValue(String value) {
            ImageContent.ContentType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
