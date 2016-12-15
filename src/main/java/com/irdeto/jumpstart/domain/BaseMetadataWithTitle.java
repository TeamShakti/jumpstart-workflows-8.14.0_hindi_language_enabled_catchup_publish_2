
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
 * baseMetadataWithTitle
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "titleSortName",
    "titleBrief",
    "titleMedium",
    "titleLong",
    "summaryShort",
    "summaryMedium",
    "summaryLong",
    "countryOfOrigin",
    "showType",
    "keywords",
    "_links"
})
public class BaseMetadataWithTitle
    extends BaseMetadata
    implements Serializable
{

    /**
     * Title (Sort Name)
     * <p>
     * 
     * 
     */
    @JsonProperty("titleSortName")
    private Locale titleSortName;
    /**
     * Title (Brief)
     * <p>
     * 
     * 
     */
    @JsonProperty("titleBrief")
    private Locale titleBrief;
    /**
     * Title (Medium)
     * <p>
     * 
     * 
     */
    @JsonProperty("titleMedium")
    private Locale titleMedium;
    /**
     * Title (Long)
     * <p>
     * 
     * 
     */
    @JsonProperty("titleLong")
    private Locale titleLong;
    /**
     * Summary (Short)
     * <p>
     * 
     * 
     */
    @JsonProperty("summaryShort")
    private Locale summaryShort;
    /**
     * Summary (Medium)
     * <p>
     * 
     * 
     */
    @JsonProperty("summaryMedium")
    private Locale summaryMedium;
    /**
     * Summary (Long)
     * <p>
     * 
     * 
     */
    @JsonProperty("summaryLong")
    private Locale summaryLong;
    /**
     * Country of Origin
     * <p>
     * 
     * 
     */
    @JsonProperty("countryOfOrigin")
    private List<Object> countryOfOrigin = new ArrayList<Object>();
    /**
     * Show Type
     * <p>
     * 
     * 
     */
    @JsonProperty("showType")
    private BaseMetadataWithTitle.ShowType showType;
    /**
     * Keywords
     * <p>
     * 
     * 
     */
    @JsonProperty("keywords")
    private String keywords;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = -2062920204909521573L;

    /**
     * Title (Sort Name)
     * <p>
     * 
     * 
     * @return
     *     The titleSortName
     */
    @JsonProperty("titleSortName")
    public Locale getTitleSortName() {
        return titleSortName;
    }

    /**
     * Title (Sort Name)
     * <p>
     * 
     * 
     * @param titleSortName
     *     The titleSortName
     */
    @JsonProperty("titleSortName")
    public void setTitleSortName(Locale titleSortName) {
        this.titleSortName = titleSortName;
    }

    public BaseMetadataWithTitle withTitleSortName(Locale titleSortName) {
        this.titleSortName = titleSortName;
        return this;
    }

    /**
     * Title (Brief)
     * <p>
     * 
     * 
     * @return
     *     The titleBrief
     */
    @JsonProperty("titleBrief")
    public Locale getTitleBrief() {
        return titleBrief;
    }

    /**
     * Title (Brief)
     * <p>
     * 
     * 
     * @param titleBrief
     *     The titleBrief
     */
    @JsonProperty("titleBrief")
    public void setTitleBrief(Locale titleBrief) {
        this.titleBrief = titleBrief;
    }

    public BaseMetadataWithTitle withTitleBrief(Locale titleBrief) {
        this.titleBrief = titleBrief;
        return this;
    }

    /**
     * Title (Medium)
     * <p>
     * 
     * 
     * @return
     *     The titleMedium
     */
    @JsonProperty("titleMedium")
    public Locale getTitleMedium() {
        return titleMedium;
    }

    /**
     * Title (Medium)
     * <p>
     * 
     * 
     * @param titleMedium
     *     The titleMedium
     */
    @JsonProperty("titleMedium")
    public void setTitleMedium(Locale titleMedium) {
        this.titleMedium = titleMedium;
    }

    public BaseMetadataWithTitle withTitleMedium(Locale titleMedium) {
        this.titleMedium = titleMedium;
        return this;
    }

    /**
     * Title (Long)
     * <p>
     * 
     * 
     * @return
     *     The titleLong
     */
    @JsonProperty("titleLong")
    public Locale getTitleLong() {
        return titleLong;
    }

    /**
     * Title (Long)
     * <p>
     * 
     * 
     * @param titleLong
     *     The titleLong
     */
    @JsonProperty("titleLong")
    public void setTitleLong(Locale titleLong) {
        this.titleLong = titleLong;
    }

    public BaseMetadataWithTitle withTitleLong(Locale titleLong) {
        this.titleLong = titleLong;
        return this;
    }

    /**
     * Summary (Short)
     * <p>
     * 
     * 
     * @return
     *     The summaryShort
     */
    @JsonProperty("summaryShort")
    public Locale getSummaryShort() {
        return summaryShort;
    }

    /**
     * Summary (Short)
     * <p>
     * 
     * 
     * @param summaryShort
     *     The summaryShort
     */
    @JsonProperty("summaryShort")
    public void setSummaryShort(Locale summaryShort) {
        this.summaryShort = summaryShort;
    }

    public BaseMetadataWithTitle withSummaryShort(Locale summaryShort) {
        this.summaryShort = summaryShort;
        return this;
    }

    /**
     * Summary (Medium)
     * <p>
     * 
     * 
     * @return
     *     The summaryMedium
     */
    @JsonProperty("summaryMedium")
    public Locale getSummaryMedium() {
        return summaryMedium;
    }

    /**
     * Summary (Medium)
     * <p>
     * 
     * 
     * @param summaryMedium
     *     The summaryMedium
     */
    @JsonProperty("summaryMedium")
    public void setSummaryMedium(Locale summaryMedium) {
        this.summaryMedium = summaryMedium;
    }

    public BaseMetadataWithTitle withSummaryMedium(Locale summaryMedium) {
        this.summaryMedium = summaryMedium;
        return this;
    }

    /**
     * Summary (Long)
     * <p>
     * 
     * 
     * @return
     *     The summaryLong
     */
    @JsonProperty("summaryLong")
    public Locale getSummaryLong() {
        return summaryLong;
    }

    /**
     * Summary (Long)
     * <p>
     * 
     * 
     * @param summaryLong
     *     The summaryLong
     */
    @JsonProperty("summaryLong")
    public void setSummaryLong(Locale summaryLong) {
        this.summaryLong = summaryLong;
    }

    public BaseMetadataWithTitle withSummaryLong(Locale summaryLong) {
        this.summaryLong = summaryLong;
        return this;
    }

    /**
     * Country of Origin
     * <p>
     * 
     * 
     * @return
     *     The countryOfOrigin
     */
    @JsonProperty("countryOfOrigin")
    public List<Object> getCountryOfOrigin() {
        return countryOfOrigin;
    }

    /**
     * Country of Origin
     * <p>
     * 
     * 
     * @param countryOfOrigin
     *     The countryOfOrigin
     */
    @JsonProperty("countryOfOrigin")
    public void setCountryOfOrigin(List<Object> countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public BaseMetadataWithTitle withCountryOfOrigin(List<Object> countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
        return this;
    }

    /**
     * Show Type
     * <p>
     * 
     * 
     * @return
     *     The showType
     */
    @JsonProperty("showType")
    public BaseMetadataWithTitle.ShowType getShowType() {
        return showType;
    }

    /**
     * Show Type
     * <p>
     * 
     * 
     * @param showType
     *     The showType
     */
    @JsonProperty("showType")
    public void setShowType(BaseMetadataWithTitle.ShowType showType) {
        this.showType = showType;
    }

    public BaseMetadataWithTitle withShowType(BaseMetadataWithTitle.ShowType showType) {
        this.showType = showType;
        return this;
    }

    /**
     * Keywords
     * <p>
     * 
     * 
     * @return
     *     The keywords
     */
    @JsonProperty("keywords")
    public String getKeywords() {
        return keywords;
    }

    /**
     * Keywords
     * <p>
     * 
     * 
     * @param keywords
     *     The keywords
     */
    @JsonProperty("keywords")
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public BaseMetadataWithTitle withKeywords(String keywords) {
        this.keywords = keywords;
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

    public BaseMetadataWithTitle withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public BaseMetadataWithTitle withMetadataApproved(Boolean metadataApproved) {
        super.withMetadataApproved(metadataApproved);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withDataMaster(BaseMetadata.DataMaster dataMaster) {
        super.withDataMaster(dataMaster);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withUriId(String uriId) {
        super.withUriId(uriId);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withStartDateTime(DateTime startDateTime) {
        super.withStartDateTime(startDateTime);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withEndDateTime(DateTime endDateTime) {
        super.withEndDateTime(endDateTime);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withProvider(String provider) {
        super.withProvider(provider);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withLastPublishDateTime(DateTime lastPublishDateTime) {
        super.withLastPublishDateTime(lastPublishDateTime);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withLastModifiedDateTime(DateTime lastModifiedDateTime) {
        super.withLastModifiedDateTime(lastModifiedDateTime);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public BaseMetadataWithTitle withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(titleSortName).append(titleBrief).append(titleMedium).append(titleLong).append(summaryShort).append(summaryMedium).append(summaryLong).append(countryOfOrigin).append(showType).append(keywords).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof BaseMetadataWithTitle) == false) {
            return false;
        }
        BaseMetadataWithTitle rhs = ((BaseMetadataWithTitle) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(titleSortName, rhs.titleSortName).append(titleBrief, rhs.titleBrief).append(titleMedium, rhs.titleMedium).append(titleLong, rhs.titleLong).append(summaryShort, rhs.summaryShort).append(summaryMedium, rhs.summaryMedium).append(summaryLong, rhs.summaryLong).append(countryOfOrigin, rhs.countryOfOrigin).append(showType, rhs.showType).append(keywords, rhs.keywords).append(links, rhs.links).isEquals();
    }

    public enum ShowType {

        SERIES("Series"),
        KIDS("Kids"),
        MOVIES("Movies"),
        SPORTS("Sports"),
        MUSIC("Music"),
        EVENTS("Events"),
        AD("Ad"),
        LIFESTYLE("Lifestyle"),
        COMMERCIAL("Commercial"),
        DOCUMENTARY("Documentary"),
        EDUCATIONAL("Educational"),
        ENTERTAINMENT("Entertainment"),
        NEWS("News"),
        RELIGIOUS("Religious"),
        OTHERS("Others");
        private final String value;
        private final static Map<String, BaseMetadataWithTitle.ShowType> CONSTANTS = new HashMap<String, BaseMetadataWithTitle.ShowType>();

        static {
            for (BaseMetadataWithTitle.ShowType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private ShowType(String value) {
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
        public static BaseMetadataWithTitle.ShowType fromValue(String value) {
            BaseMetadataWithTitle.ShowType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
