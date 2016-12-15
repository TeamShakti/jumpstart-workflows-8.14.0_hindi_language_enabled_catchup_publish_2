
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
 * baseProtection
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "name",
    "protectionType",
    "_links"
})
public class BaseProtection
    extends BaseEntity
    implements Serializable
{

    /**
     * Name
     * <p>
     * 
     * 
     */
    @JsonProperty("name")
    private String name;
    /**
     * Protection Type
     * <p>
     * 
     * 
     */
    @JsonProperty("protectionType")
    private BaseProtection.ProtectionType protectionType;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 8428292891669568635L;

    /**
     * Name
     * <p>
     * 
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Name
     * <p>
     * 
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public BaseProtection withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Protection Type
     * <p>
     * 
     * 
     * @return
     *     The protectionType
     */
    @JsonProperty("protectionType")
    public BaseProtection.ProtectionType getProtectionType() {
        return protectionType;
    }

    /**
     * Protection Type
     * <p>
     * 
     * 
     * @param protectionType
     *     The protectionType
     */
    @JsonProperty("protectionType")
    public void setProtectionType(BaseProtection.ProtectionType protectionType) {
        this.protectionType = protectionType;
    }

    public BaseProtection withProtectionType(BaseProtection.ProtectionType protectionType) {
        this.protectionType = protectionType;
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

    public BaseProtection withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public BaseProtection withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public BaseProtection withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public BaseProtection withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public BaseProtection withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public BaseProtection withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public BaseProtection withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public BaseProtection withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public BaseProtection withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public BaseProtection withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public BaseProtection withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(name).append(protectionType).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BaseProtection) == false) {
            return false;
        }
        BaseProtection rhs = ((BaseProtection) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(name, rhs.name).append(protectionType, rhs.protectionType).append(links, rhs.links).isEquals();
    }

    public enum ProtectionType {

        ACTIVE_CLOAK("ActiveCloak"),
        ACTIVE_CLOAK_2_GO_DRM("ActiveCloak2GoDRM"),
        PLAY_READY_DRM("PlayReadyDRM"),
        HTTP_LIVE_STREAMING_DRM("HttpLiveStreamingDRM"),
        IRDETO_SKE("IrdetoSKE"),
        PLAY_READY_HLS_DRM("PlayReadyHlsDRM"),
        PLAY_READY_US("PlayReadyUS"),
        SKEUS("SKEUS"),
        PLAY_READY_WIDEVINE_MULTI_US("PlayReadyWidevineMultiUS"),
        WIDEVINE_US("WidevineUS"),
        NONE_US("NoneUS");
        private final String value;
        private final static Map<String, BaseProtection.ProtectionType> CONSTANTS = new HashMap<String, BaseProtection.ProtectionType>();

        static {
            for (BaseProtection.ProtectionType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private ProtectionType(String value) {
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
        public static BaseProtection.ProtectionType fromValue(String value) {
            BaseProtection.ProtectionType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
