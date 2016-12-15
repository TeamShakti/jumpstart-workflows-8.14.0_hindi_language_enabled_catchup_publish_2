
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
 * Device Profile
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "deviceClass",
    "name",
    "enabled",
    "packagingType",
    "_links"
})
public class DeviceProfile
    extends BaseEntity
    implements Serializable
{

    /**
     * Device Class
     * <p>
     * 
     * 
     */
    @JsonProperty("deviceClass")
    private String deviceClass;
    /**
     * Name
     * <p>
     * 
     * 
     */
    @JsonProperty("name")
    private String name;
    /**
     * Enabled?
     * <p>
     * 
     * 
     */
    @JsonProperty("enabled")
    private Boolean enabled;
    /**
     * Packaging Type
     * <p>
     * 
     * 
     */
    @JsonProperty("packagingType")
    private DeviceProfile.PackagingType packagingType;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -5455112212206533703L;

    /**
     * Device Class
     * <p>
     * 
     * 
     * @return
     *     The deviceClass
     */
    @JsonProperty("deviceClass")
    public String getDeviceClass() {
        return deviceClass;
    }

    /**
     * Device Class
     * <p>
     * 
     * 
     * @param deviceClass
     *     The deviceClass
     */
    @JsonProperty("deviceClass")
    public void setDeviceClass(String deviceClass) {
        this.deviceClass = deviceClass;
    }

    public DeviceProfile withDeviceClass(String deviceClass) {
        this.deviceClass = deviceClass;
        return this;
    }

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

    public DeviceProfile withName(String name) {
        this.name = name;
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

    public DeviceProfile withEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * Packaging Type
     * <p>
     * 
     * 
     * @return
     *     The packagingType
     */
    @JsonProperty("packagingType")
    public DeviceProfile.PackagingType getPackagingType() {
        return packagingType;
    }

    /**
     * Packaging Type
     * <p>
     * 
     * 
     * @param packagingType
     *     The packagingType
     */
    @JsonProperty("packagingType")
    public void setPackagingType(DeviceProfile.PackagingType packagingType) {
        this.packagingType = packagingType;
    }

    public DeviceProfile withPackagingType(DeviceProfile.PackagingType packagingType) {
        this.packagingType = packagingType;
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

    public DeviceProfile withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public DeviceProfile withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public DeviceProfile withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public DeviceProfile withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public DeviceProfile withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public DeviceProfile withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public DeviceProfile withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public DeviceProfile withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public DeviceProfile withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public DeviceProfile withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public DeviceProfile withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(deviceClass).append(name).append(enabled).append(packagingType).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DeviceProfile) == false) {
            return false;
        }
        DeviceProfile rhs = ((DeviceProfile) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(deviceClass, rhs.deviceClass).append(name, rhs.name).append(enabled, rhs.enabled).append(packagingType, rhs.packagingType).append(links, rhs.links).isEquals();
    }

    public enum PackagingType {

        NA("NA"),
        DASH("DASH"),
        HLS("HLS"),
        SMOOTH_STREAM("Smooth Stream");
        private final String value;
        private final static Map<String, DeviceProfile.PackagingType> CONSTANTS = new HashMap<String, DeviceProfile.PackagingType>();

        static {
            for (DeviceProfile.PackagingType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private PackagingType(String value) {
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
        public static DeviceProfile.PackagingType fromValue(String value) {
            DeviceProfile.PackagingType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
