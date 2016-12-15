
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
 * baseMetadata
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "metadataApproved",
    "dataMaster",
    "_links"
})
public class BaseMetadata
    extends Base
    implements Serializable
{

    /**
     * Metadata Approved?
     * <p>
     * 
     * 
     */
    @JsonProperty("metadataApproved")
    private Boolean metadataApproved;
    /**
     * Data Master
     * <p>
     * 
     * 
     */
    @JsonProperty("dataMaster")
    private BaseMetadata.DataMaster dataMaster;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -473918991839994732L;

    /**
     * Metadata Approved?
     * <p>
     * 
     * 
     * @return
     *     The metadataApproved
     */
    @JsonProperty("metadataApproved")
    public Boolean getMetadataApproved() {
        return metadataApproved;
    }

    /**
     * Metadata Approved?
     * <p>
     * 
     * 
     * @param metadataApproved
     *     The metadataApproved
     */
    @JsonProperty("metadataApproved")
    public void setMetadataApproved(Boolean metadataApproved) {
        this.metadataApproved = metadataApproved;
    }

    public BaseMetadata withMetadataApproved(Boolean metadataApproved) {
        this.metadataApproved = metadataApproved;
        return this;
    }

    /**
     * Data Master
     * <p>
     * 
     * 
     * @return
     *     The dataMaster
     */
    @JsonProperty("dataMaster")
    public BaseMetadata.DataMaster getDataMaster() {
        return dataMaster;
    }

    /**
     * Data Master
     * <p>
     * 
     * 
     * @param dataMaster
     *     The dataMaster
     */
    @JsonProperty("dataMaster")
    public void setDataMaster(BaseMetadata.DataMaster dataMaster) {
        this.dataMaster = dataMaster;
    }

    public BaseMetadata withDataMaster(BaseMetadata.DataMaster dataMaster) {
        this.dataMaster = dataMaster;
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

    public BaseMetadata withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public BaseMetadata withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public BaseMetadata withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public BaseMetadata withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public BaseMetadata withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public BaseMetadata withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public BaseMetadata withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public BaseMetadata withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public BaseMetadata withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public BaseMetadata withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public BaseMetadata withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public BaseMetadata withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public BaseMetadata withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public BaseMetadata withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public BaseMetadata withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public BaseMetadata withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public BaseMetadata withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(metadataApproved).append(dataMaster).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BaseMetadata) == false) {
            return false;
        }
        BaseMetadata rhs = ((BaseMetadata) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(metadataApproved, rhs.metadataApproved).append(dataMaster, rhs.dataMaster).append(links, rhs.links).isEquals();
    }

    public enum DataMaster {

        INGEST("Ingest"),
        MEDIA_MANAGER("Media Manager");
        private final String value;
        private final static Map<String, BaseMetadata.DataMaster> CONSTANTS = new HashMap<String, BaseMetadata.DataMaster>();

        static {
            for (BaseMetadata.DataMaster c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private DataMaster(String value) {
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
        public static BaseMetadata.DataMaster fromValue(String value) {
            BaseMetadata.DataMaster constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
