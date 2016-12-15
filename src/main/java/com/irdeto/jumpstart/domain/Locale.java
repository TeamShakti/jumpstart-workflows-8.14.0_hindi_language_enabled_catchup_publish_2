
package com.irdeto.jumpstart.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * Title (Sort Name)
 * <p>
 * 
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "en_US",
    "hi_IN"
})
public class Locale implements Serializable
{

    /**
     * en_US
     * <p>
     * 
     * 
     */
    @JsonProperty("en_US")
    private String enUS;
    /**
     * hi_IN
     * <p>
     * 
     * 
     */
    @JsonProperty("hi_IN")
    private String hiIN;
    private final static long serialVersionUID = -8093374901719377362L;

    /**
     * en_US
     * <p>
     * 
     * 
     * @return
     *     The enUS
     */
    @JsonProperty("en_US")
    public String getEnUS() {
        return enUS;
    }

    /**
     * en_US
     * <p>
     * 
     * 
     * @param enUS
     *     The en_US
     */
    @JsonProperty("en_US")
    public void setEnUS(String enUS) {
        this.enUS = enUS;
    }

    public Locale withEnUS(String enUS) {
        this.enUS = enUS;
        return this;
    }

    /**
     * hi_IN
     * <p>
     * 
     * 
     * @return
     *     The hiIN
     */
    @JsonProperty("hi_IN")
    public String getHiIN() {
        return hiIN;
    }

    /**
     * hi_IN
     * <p>
     * 
     * 
     * @param hiIN
     *     The hi_IN
     */
    @JsonProperty("hi_IN")
    public void setHiIN(String hiIN) {
        this.hiIN = hiIN;
    }

    public Locale withHiIN(String hiIN) {
        this.hiIN = hiIN;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enUS).append(hiIN).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Locale) == false) {
            return false;
        }
        Locale rhs = ((Locale) other);
        return new EqualsBuilder().append(enUS, rhs.enUS).append(hiIN, rhs.hiIN).isEquals();
    }

}
