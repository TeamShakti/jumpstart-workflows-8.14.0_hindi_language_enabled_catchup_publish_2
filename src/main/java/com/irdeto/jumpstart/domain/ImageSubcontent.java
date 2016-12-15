
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
 * Image Subcontent
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "xResolution",
    "yResolution",
    "language",
    "_links"
})
public class ImageSubcontent
    extends Subcontent
    implements Serializable
{

    /**
     * X-Resolution
     * <p>
     * 
     * 
     */
    @JsonProperty("xResolution")
    private Integer xResolution;
    /**
     * Y-Resolution
     * <p>
     * 
     * 
     */
    @JsonProperty("yResolution")
    private Integer yResolution;
    /**
     * Language
     * <p>
     * 
     * 
     */
    @JsonProperty("language")
    private String language;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 7417519094797121853L;

    /**
     * X-Resolution
     * <p>
     * 
     * 
     * @return
     *     The xResolution
     */
    @JsonProperty("xResolution")
    public Integer getXResolution() {
        return xResolution;
    }

    /**
     * X-Resolution
     * <p>
     * 
     * 
     * @param xResolution
     *     The xResolution
     */
    @JsonProperty("xResolution")
    public void setXResolution(Integer xResolution) {
        this.xResolution = xResolution;
    }

    public ImageSubcontent withXResolution(Integer xResolution) {
        this.xResolution = xResolution;
        return this;
    }

    /**
     * Y-Resolution
     * <p>
     * 
     * 
     * @return
     *     The yResolution
     */
    @JsonProperty("yResolution")
    public Integer getYResolution() {
        return yResolution;
    }

    /**
     * Y-Resolution
     * <p>
     * 
     * 
     * @param yResolution
     *     The yResolution
     */
    @JsonProperty("yResolution")
    public void setYResolution(Integer yResolution) {
        this.yResolution = yResolution;
    }

    public ImageSubcontent withYResolution(Integer yResolution) {
        this.yResolution = yResolution;
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
    public String getLanguage() {
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
    public void setLanguage(String language) {
        this.language = language;
    }

    public ImageSubcontent withLanguage(String language) {
        this.language = language;
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

    public ImageSubcontent withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public ImageSubcontent withConsumerUrl(String consumerUrl) {
        super.withConsumerUrl(consumerUrl);
        return this;
    }

    @Override
    public ImageSubcontent withContentFileSize(String contentFileSize) {
        super.withContentFileSize(contentFileSize);
        return this;
    }

    @Override
    public ImageSubcontent withContentCheckSum(String contentCheckSum) {
        super.withContentCheckSum(contentCheckSum);
        return this;
    }

    @Override
    public ImageSubcontent withSourcePath(String sourcePath) {
        super.withSourcePath(sourcePath);
        return this;
    }

    @Override
    public ImageSubcontent withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public ImageSubcontent withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public ImageSubcontent withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public ImageSubcontent withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public ImageSubcontent withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public ImageSubcontent withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public ImageSubcontent withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public ImageSubcontent withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public ImageSubcontent withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public ImageSubcontent withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(xResolution).append(yResolution).append(language).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ImageSubcontent) == false) {
            return false;
        }
        ImageSubcontent rhs = ((ImageSubcontent) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(xResolution, rhs.xResolution).append(yResolution, rhs.yResolution).append(language, rhs.language).append(links, rhs.links).isEquals();
    }

}
