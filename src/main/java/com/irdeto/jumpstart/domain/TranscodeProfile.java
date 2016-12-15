
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
 * Transcode Profile
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "name",
    "transcoderProfile",
    "transcoderUri",
    "transcodedFilePattern",
    "transcodedFileCount",
    "transcoderWorkflow",
    "_links"
})
public class TranscodeProfile
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
     * Transcoder Profile
     * <p>
     * 
     * 
     */
    @JsonProperty("transcoderProfile")
    private String transcoderProfile;
    /**
     * Transcoder URI
     * <p>
     * 
     * 
     */
    @JsonProperty("transcoderUri")
    private String transcoderUri;
    /**
     * Transcoded File Pattern
     * <p>
     * 
     * 
     */
    @JsonProperty("transcodedFilePattern")
    private String transcodedFilePattern;
    /**
     * Transcoded File Count
     * <p>
     * 
     * 
     */
    @JsonProperty("transcodedFileCount")
    private Integer transcodedFileCount;
    /**
     * Transcoder Workflow
     * <p>
     * 
     * 
     */
    @JsonProperty("transcoderWorkflow")
    private TranscodeProfile.TranscoderWorkflow transcoderWorkflow;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -499488216730982632L;

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

    public TranscodeProfile withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Transcoder Profile
     * <p>
     * 
     * 
     * @return
     *     The transcoderProfile
     */
    @JsonProperty("transcoderProfile")
    public String getTranscoderProfile() {
        return transcoderProfile;
    }

    /**
     * Transcoder Profile
     * <p>
     * 
     * 
     * @param transcoderProfile
     *     The transcoderProfile
     */
    @JsonProperty("transcoderProfile")
    public void setTranscoderProfile(String transcoderProfile) {
        this.transcoderProfile = transcoderProfile;
    }

    public TranscodeProfile withTranscoderProfile(String transcoderProfile) {
        this.transcoderProfile = transcoderProfile;
        return this;
    }

    /**
     * Transcoder URI
     * <p>
     * 
     * 
     * @return
     *     The transcoderUri
     */
    @JsonProperty("transcoderUri")
    public String getTranscoderUri() {
        return transcoderUri;
    }

    /**
     * Transcoder URI
     * <p>
     * 
     * 
     * @param transcoderUri
     *     The transcoderUri
     */
    @JsonProperty("transcoderUri")
    public void setTranscoderUri(String transcoderUri) {
        this.transcoderUri = transcoderUri;
    }

    public TranscodeProfile withTranscoderUri(String transcoderUri) {
        this.transcoderUri = transcoderUri;
        return this;
    }

    /**
     * Transcoded File Pattern
     * <p>
     * 
     * 
     * @return
     *     The transcodedFilePattern
     */
    @JsonProperty("transcodedFilePattern")
    public String getTranscodedFilePattern() {
        return transcodedFilePattern;
    }

    /**
     * Transcoded File Pattern
     * <p>
     * 
     * 
     * @param transcodedFilePattern
     *     The transcodedFilePattern
     */
    @JsonProperty("transcodedFilePattern")
    public void setTranscodedFilePattern(String transcodedFilePattern) {
        this.transcodedFilePattern = transcodedFilePattern;
    }

    public TranscodeProfile withTranscodedFilePattern(String transcodedFilePattern) {
        this.transcodedFilePattern = transcodedFilePattern;
        return this;
    }

    /**
     * Transcoded File Count
     * <p>
     * 
     * 
     * @return
     *     The transcodedFileCount
     */
    @JsonProperty("transcodedFileCount")
    public Integer getTranscodedFileCount() {
        return transcodedFileCount;
    }

    /**
     * Transcoded File Count
     * <p>
     * 
     * 
     * @param transcodedFileCount
     *     The transcodedFileCount
     */
    @JsonProperty("transcodedFileCount")
    public void setTranscodedFileCount(Integer transcodedFileCount) {
        this.transcodedFileCount = transcodedFileCount;
    }

    public TranscodeProfile withTranscodedFileCount(Integer transcodedFileCount) {
        this.transcodedFileCount = transcodedFileCount;
        return this;
    }

    /**
     * Transcoder Workflow
     * <p>
     * 
     * 
     * @return
     *     The transcoderWorkflow
     */
    @JsonProperty("transcoderWorkflow")
    public TranscodeProfile.TranscoderWorkflow getTranscoderWorkflow() {
        return transcoderWorkflow;
    }

    /**
     * Transcoder Workflow
     * <p>
     * 
     * 
     * @param transcoderWorkflow
     *     The transcoderWorkflow
     */
    @JsonProperty("transcoderWorkflow")
    public void setTranscoderWorkflow(TranscodeProfile.TranscoderWorkflow transcoderWorkflow) {
        this.transcoderWorkflow = transcoderWorkflow;
    }

    public TranscodeProfile withTranscoderWorkflow(TranscodeProfile.TranscoderWorkflow transcoderWorkflow) {
        this.transcoderWorkflow = transcoderWorkflow;
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

    public TranscodeProfile withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public TranscodeProfile withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public TranscodeProfile withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public TranscodeProfile withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public TranscodeProfile withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public TranscodeProfile withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public TranscodeProfile withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public TranscodeProfile withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public TranscodeProfile withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public TranscodeProfile withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public TranscodeProfile withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(name).append(transcoderProfile).append(transcoderUri).append(transcodedFilePattern).append(transcodedFileCount).append(transcoderWorkflow).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TranscodeProfile) == false) {
            return false;
        }
        TranscodeProfile rhs = ((TranscodeProfile) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(name, rhs.name).append(transcoderProfile, rhs.transcoderProfile).append(transcoderUri, rhs.transcoderUri).append(transcodedFilePattern, rhs.transcodedFilePattern).append(transcodedFileCount, rhs.transcodedFileCount).append(transcoderWorkflow, rhs.transcoderWorkflow).append(links, rhs.links).isEquals();
    }

    public enum TranscoderWorkflow {

        TRANSCODE_ELEMENTAL_CLOUD("TranscodeElementalCloud");
        private final String value;
        private final static Map<String, TranscodeProfile.TranscoderWorkflow> CONSTANTS = new HashMap<String, TranscodeProfile.TranscoderWorkflow>();

        static {
            for (TranscodeProfile.TranscoderWorkflow c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private TranscoderWorkflow(String value) {
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
        public static TranscodeProfile.TranscoderWorkflow fromValue(String value) {
            TranscodeProfile.TranscoderWorkflow constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
