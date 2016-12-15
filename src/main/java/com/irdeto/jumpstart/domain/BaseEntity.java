
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
 * baseEntity
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type",
    "createdDate",
    "createdBy",
    "modifiedDate",
    "modifiedBy",
    "deletedDate",
    "deletedBy",
    "versions",
    "additionalProperties",
    "_links"
})
public class BaseEntity implements Serializable
{

    /**
     * id
     * <p>
     * 
     * 
     */
    @JsonProperty("id")
    private String id;
    /**
     * type
     * <p>
     * 
     * 
     */
    @JsonProperty("type")
    private String type;
    /**
     * createdDate
     * <p>
     * 
     * 
     */
    @JsonProperty("createdDate")
    private DateTime createdDate;
    /**
     * createdBy
     * <p>
     * 
     * 
     */
    @JsonProperty("createdBy")
    private Integer createdBy;
    /**
     * modifiedDate
     * <p>
     * 
     * 
     */
    @JsonProperty("modifiedDate")
    private DateTime modifiedDate;
    /**
     * modifiedBy
     * <p>
     * 
     * 
     */
    @JsonProperty("modifiedBy")
    private Integer modifiedBy;
    /**
     * deletedDate
     * <p>
     * 
     * 
     */
    @JsonProperty("deletedDate")
    private DateTime deletedDate;
    /**
     * deletedBy
     * <p>
     * 
     * 
     */
    @JsonProperty("deletedBy")
    private Integer deletedBy;
    /**
     * versions
     * <p>
     * 
     * 
     */
    @JsonProperty("versions")
    private List<Object> versions = new ArrayList<Object>();
    @JsonProperty("additionalProperties")
    private Object additionalProperties;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 8504154871555444043L;

    /**
     * id
     * <p>
     * 
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * id
     * <p>
     * 
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public BaseEntity withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * type
     * <p>
     * 
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * type
     * <p>
     * 
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public BaseEntity withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * createdDate
     * <p>
     * 
     * 
     * @return
     *     The createdDate
     */
    @JsonProperty("createdDate")
    public DateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * createdDate
     * <p>
     * 
     * 
     * @param createdDate
     *     The createdDate
     */
    @JsonProperty("createdDate")
    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    public BaseEntity withCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    /**
     * createdBy
     * <p>
     * 
     * 
     * @return
     *     The createdBy
     */
    @JsonProperty("createdBy")
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * createdBy
     * <p>
     * 
     * 
     * @param createdBy
     *     The createdBy
     */
    @JsonProperty("createdBy")
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public BaseEntity withCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * modifiedDate
     * <p>
     * 
     * 
     * @return
     *     The modifiedDate
     */
    @JsonProperty("modifiedDate")
    public DateTime getModifiedDate() {
        return modifiedDate;
    }

    /**
     * modifiedDate
     * <p>
     * 
     * 
     * @param modifiedDate
     *     The modifiedDate
     */
    @JsonProperty("modifiedDate")
    public void setModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public BaseEntity withModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    /**
     * modifiedBy
     * <p>
     * 
     * 
     * @return
     *     The modifiedBy
     */
    @JsonProperty("modifiedBy")
    public Integer getModifiedBy() {
        return modifiedBy;
    }

    /**
     * modifiedBy
     * <p>
     * 
     * 
     * @param modifiedBy
     *     The modifiedBy
     */
    @JsonProperty("modifiedBy")
    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public BaseEntity withModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    /**
     * deletedDate
     * <p>
     * 
     * 
     * @return
     *     The deletedDate
     */
    @JsonProperty("deletedDate")
    public DateTime getDeletedDate() {
        return deletedDate;
    }

    /**
     * deletedDate
     * <p>
     * 
     * 
     * @param deletedDate
     *     The deletedDate
     */
    @JsonProperty("deletedDate")
    public void setDeletedDate(DateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    public BaseEntity withDeletedDate(DateTime deletedDate) {
        this.deletedDate = deletedDate;
        return this;
    }

    /**
     * deletedBy
     * <p>
     * 
     * 
     * @return
     *     The deletedBy
     */
    @JsonProperty("deletedBy")
    public Integer getDeletedBy() {
        return deletedBy;
    }

    /**
     * deletedBy
     * <p>
     * 
     * 
     * @param deletedBy
     *     The deletedBy
     */
    @JsonProperty("deletedBy")
    public void setDeletedBy(Integer deletedBy) {
        this.deletedBy = deletedBy;
    }

    public BaseEntity withDeletedBy(Integer deletedBy) {
        this.deletedBy = deletedBy;
        return this;
    }

    /**
     * versions
     * <p>
     * 
     * 
     * @return
     *     The versions
     */
    @JsonProperty("versions")
    public List<Object> getVersions() {
        return versions;
    }

    /**
     * versions
     * <p>
     * 
     * 
     * @param versions
     *     The versions
     */
    @JsonProperty("versions")
    public void setVersions(List<Object> versions) {
        this.versions = versions;
    }

    public BaseEntity withVersions(List<Object> versions) {
        this.versions = versions;
        return this;
    }

    /**
     * 
     * @return
     *     The additionalProperties
     */
    @JsonProperty("additionalProperties")
    public Object getAdditionalProperties() {
        return additionalProperties;
    }

    /**
     * 
     * @param additionalProperties
     *     The additionalProperties
     */
    @JsonProperty("additionalProperties")
    public void setAdditionalProperties(Object additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public BaseEntity withAdditionalProperties(Object additionalProperties) {
        this.additionalProperties = additionalProperties;
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

    public BaseEntity withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(type).append(createdDate).append(createdBy).append(modifiedDate).append(modifiedBy).append(deletedDate).append(deletedBy).append(versions).append(additionalProperties).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BaseEntity) == false) {
            return false;
        }
        BaseEntity rhs = ((BaseEntity) other);
        return new EqualsBuilder().append(id, rhs.id).append(type, rhs.type).append(createdDate, rhs.createdDate).append(createdBy, rhs.createdBy).append(modifiedDate, rhs.modifiedDate).append(modifiedBy, rhs.modifiedBy).append(deletedDate, rhs.deletedDate).append(deletedBy, rhs.deletedBy).append(versions, rhs.versions).append(additionalProperties, rhs.additionalProperties).append(links, rhs.links).isEquals();
    }

}
