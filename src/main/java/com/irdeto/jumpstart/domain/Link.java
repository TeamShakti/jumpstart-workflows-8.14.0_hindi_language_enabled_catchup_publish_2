
package com.irdeto.jumpstart.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * link
 * <p>
 * 
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "href",
    "title",
    "key",
    "count"
})
public class Link implements Serializable
{

    /**
     * Link HREF
     * <p>
     * 
     * 
     */
    @JsonProperty("href")
    private String href;
    /**
     * Human-readable label
     * <p>
     * 
     * 
     */
    @JsonProperty("title")
    private String title;
    /**
     * Relationship key
     * <p>
     * 
     * 
     */
    @JsonProperty("key")
    private String key;
    /**
     * Number of related entities
     * <p>
     * 
     * 
     */
    @JsonProperty("count")
    private Integer count;
    private final static long serialVersionUID = 5538262727268712483L;

    /**
     * Link HREF
     * <p>
     * 
     * 
     * @return
     *     The href
     */
    @JsonProperty("href")
    public String getHref() {
        return href;
    }

    /**
     * Link HREF
     * <p>
     * 
     * 
     * @param href
     *     The href
     */
    @JsonProperty("href")
    public void setHref(String href) {
        this.href = href;
    }

    public Link withHref(String href) {
        this.href = href;
        return this;
    }

    /**
     * Human-readable label
     * <p>
     * 
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * Human-readable label
     * <p>
     * 
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public Link withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Relationship key
     * <p>
     * 
     * 
     * @return
     *     The key
     */
    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    /**
     * Relationship key
     * <p>
     * 
     * 
     * @param key
     *     The key
     */
    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    public Link withKey(String key) {
        this.key = key;
        return this;
    }

    /**
     * Number of related entities
     * <p>
     * 
     * 
     * @return
     *     The count
     */
    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    /**
     * Number of related entities
     * <p>
     * 
     * 
     * @param count
     *     The count
     */
    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    public Link withCount(Integer count) {
        this.count = count;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(href).append(title).append(key).append(count).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Link) == false) {
            return false;
        }
        Link rhs = ((Link) other);
        return new EqualsBuilder().append(href, rhs.href).append(title, rhs.title).append(key, rhs.key).append(count, rhs.count).isEquals();
    }

}
