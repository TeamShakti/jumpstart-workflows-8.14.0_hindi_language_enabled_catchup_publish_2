
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
 * Genre
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "title",
    "isDisplayGenre",
    "isEnabled",
    "ingestGenre",
    "children",
    "ancestors",
    "parent",
    "_links"
})
public class Genre
    extends BaseMetadata
    implements Serializable
{

    /**
     * Title
     * <p>
     * 
     * 
     */
    @JsonProperty("title")
    private Locale title;
    /**
     * Visible in the UI?
     * <p>
     * 
     * 
     */
    @JsonProperty("isDisplayGenre")
    private Boolean isDisplayGenre;
    /**
     * Is genre enabled?
     * <p>
     * 
     * 
     */
    @JsonProperty("isEnabled")
    private Boolean isEnabled;
    /**
     * Ingest Genre
     * <p>
     * 
     * 
     */
    @JsonProperty("ingestGenre")
    private String ingestGenre;
    @JsonProperty("children")
    private List<Genre> children = new ArrayList<Genre>();
    @JsonProperty("ancestors")
    private List<Genre> ancestors = new ArrayList<Genre>();
    /**
     * Genre
     * 
     */
    @JsonProperty("parent")
    private Genre parent;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -6575681995982173654L;

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

    public Genre withTitle(Locale title) {
        this.title = title;
        return this;
    }

    /**
     * Visible in the UI?
     * <p>
     * 
     * 
     * @return
     *     The isDisplayGenre
     */
    @JsonProperty("isDisplayGenre")
    public Boolean getIsDisplayGenre() {
        return isDisplayGenre;
    }

    /**
     * Visible in the UI?
     * <p>
     * 
     * 
     * @param isDisplayGenre
     *     The isDisplayGenre
     */
    @JsonProperty("isDisplayGenre")
    public void setIsDisplayGenre(Boolean isDisplayGenre) {
        this.isDisplayGenre = isDisplayGenre;
    }

    public Genre withIsDisplayGenre(Boolean isDisplayGenre) {
        this.isDisplayGenre = isDisplayGenre;
        return this;
    }

    /**
     * Is genre enabled?
     * <p>
     * 
     * 
     * @return
     *     The isEnabled
     */
    @JsonProperty("isEnabled")
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * Is genre enabled?
     * <p>
     * 
     * 
     * @param isEnabled
     *     The isEnabled
     */
    @JsonProperty("isEnabled")
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Genre withIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    /**
     * Ingest Genre
     * <p>
     * 
     * 
     * @return
     *     The ingestGenre
     */
    @JsonProperty("ingestGenre")
    public String getIngestGenre() {
        return ingestGenre;
    }

    /**
     * Ingest Genre
     * <p>
     * 
     * 
     * @param ingestGenre
     *     The ingestGenre
     */
    @JsonProperty("ingestGenre")
    public void setIngestGenre(String ingestGenre) {
        this.ingestGenre = ingestGenre;
    }

    public Genre withIngestGenre(String ingestGenre) {
        this.ingestGenre = ingestGenre;
        return this;
    }

    /**
     * 
     * @return
     *     The children
     */
    @JsonProperty("children")
    public List<Genre> getChildren() {
        return children;
    }

    /**
     * 
     * @param children
     *     The children
     */
    @JsonProperty("children")
    public void setChildren(List<Genre> children) {
        this.children = children;
    }

    public Genre withChildren(List<Genre> children) {
        this.children = children;
        return this;
    }

    /**
     * 
     * @return
     *     The ancestors
     */
    @JsonProperty("ancestors")
    public List<Genre> getAncestors() {
        return ancestors;
    }

    /**
     * 
     * @param ancestors
     *     The ancestors
     */
    @JsonProperty("ancestors")
    public void setAncestors(List<Genre> ancestors) {
        this.ancestors = ancestors;
    }

    public Genre withAncestors(List<Genre> ancestors) {
        this.ancestors = ancestors;
        return this;
    }

    /**
     * Genre
     * 
     * @return
     *     The parent
     */
    @JsonProperty("parent")
    public Genre getParent() {
        return parent;
    }

    /**
     * Genre
     * 
     * @param parent
     *     The parent
     */
    @JsonProperty("parent")
    public void setParent(Genre parent) {
        this.parent = parent;
    }

    public Genre withParent(Genre parent) {
        this.parent = parent;
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

    public Genre withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public Genre withMetadataApproved(Boolean metadataApproved) {
        super.withMetadataApproved(metadataApproved);
        return this;
    }

    @Override
    public Genre withDataMaster(BaseMetadata.DataMaster dataMaster) {
        super.withDataMaster(dataMaster);
        return this;
    }

    @Override
    public Genre withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public Genre withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public Genre withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public Genre withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public Genre withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public Genre withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public Genre withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public Genre withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public Genre withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public Genre withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public Genre withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public Genre withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public Genre withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public Genre withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public Genre withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public Genre withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(title).append(isDisplayGenre).append(isEnabled).append(ingestGenre).append(children).append(ancestors).append(parent).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Genre) == false) {
            return false;
        }
        Genre rhs = ((Genre) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(title, rhs.title).append(isDisplayGenre, rhs.isDisplayGenre).append(isEnabled, rhs.isEnabled).append(ingestGenre, rhs.ingestGenre).append(children, rhs.children).append(ancestors, rhs.ancestors).append(parent, rhs.parent).append(links, rhs.links).isEquals();
    }

}
