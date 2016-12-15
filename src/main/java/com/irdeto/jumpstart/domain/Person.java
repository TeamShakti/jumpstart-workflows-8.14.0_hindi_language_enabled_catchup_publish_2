
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
 * Person
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "contribution",
    "firstName",
    "lastName",
    "sortableName",
    "fullName",
    "_links"
})
public class Person
    extends BaseEntity
    implements Serializable
{

    /**
     * Contribution
     * <p>
     * 
     * 
     */
    @JsonProperty("contribution")
    private Person.Contribution contribution;
    /**
     * First Name
     * <p>
     * 
     * 
     */
    @JsonProperty("firstName")
    private Locale firstName;
    /**
     * Last Name
     * <p>
     * 
     * 
     */
    @JsonProperty("lastName")
    private Locale lastName;
    /**
     * Sortable Name
     * <p>
     * 
     * 
     */
    @JsonProperty("sortableName")
    private Locale sortableName;
    /**
     * Full Name
     * <p>
     * 
     * 
     */
    @JsonProperty("fullName")
    private Locale fullName;
    /**
     * Links
     * <p>
     * 
     * 
     */
    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();
    private final static long serialVersionUID = 932390545282197825L;

    /**
     * Contribution
     * <p>
     * 
     * 
     * @return
     *     The contribution
     */
    @JsonProperty("contribution")
    public Person.Contribution getContribution() {
        return contribution;
    }

    /**
     * Contribution
     * <p>
     * 
     * 
     * @param contribution
     *     The contribution
     */
    @JsonProperty("contribution")
    public void setContribution(Person.Contribution contribution) {
        this.contribution = contribution;
    }

    public Person withContribution(Person.Contribution contribution) {
        this.contribution = contribution;
        return this;
    }

    /**
     * First Name
     * <p>
     * 
     * 
     * @return
     *     The firstName
     */
    @JsonProperty("firstName")
    public Locale getFirstName() {
        return firstName;
    }

    /**
     * First Name
     * <p>
     * 
     * 
     * @param firstName
     *     The firstName
     */
    @JsonProperty("firstName")
    public void setFirstName(Locale firstName) {
        this.firstName = firstName;
    }

    public Person withFirstName(Locale firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Last Name
     * <p>
     * 
     * 
     * @return
     *     The lastName
     */
    @JsonProperty("lastName")
    public Locale getLastName() {
        return lastName;
    }

    /**
     * Last Name
     * <p>
     * 
     * 
     * @param lastName
     *     The lastName
     */
    @JsonProperty("lastName")
    public void setLastName(Locale lastName) {
        this.lastName = lastName;
    }

    public Person withLastName(Locale lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Sortable Name
     * <p>
     * 
     * 
     * @return
     *     The sortableName
     */
    @JsonProperty("sortableName")
    public Locale getSortableName() {
        return sortableName;
    }

    /**
     * Sortable Name
     * <p>
     * 
     * 
     * @param sortableName
     *     The sortableName
     */
    @JsonProperty("sortableName")
    public void setSortableName(Locale sortableName) {
        this.sortableName = sortableName;
    }

    public Person withSortableName(Locale sortableName) {
        this.sortableName = sortableName;
        return this;
    }

    /**
     * Full Name
     * <p>
     * 
     * 
     * @return
     *     The fullName
     */
    @JsonProperty("fullName")
    public Locale getFullName() {
        return fullName;
    }

    /**
     * Full Name
     * <p>
     * 
     * 
     * @param fullName
     *     The fullName
     */
    @JsonProperty("fullName")
    public void setFullName(Locale fullName) {
        this.fullName = fullName;
    }

    public Person withFullName(Locale fullName) {
        this.fullName = fullName;
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

    public Person withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Override
    public Person withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public Person withType(String type) {
        super.withType(type);
        return this;
    }

    @Override
    public Person withCreatedDate(DateTime createdDate) {
        super.withCreatedDate(createdDate);
        return this;
    }

    @Override
    public Person withCreatedBy(Integer createdBy) {
        super.withCreatedBy(createdBy);
        return this;
    }

    @Override
    public Person withModifiedDate(DateTime modifiedDate) {
        super.withModifiedDate(modifiedDate);
        return this;
    }

    @Override
    public Person withModifiedBy(Integer modifiedBy) {
        super.withModifiedBy(modifiedBy);
        return this;
    }

    @Override
    public Person withDeletedDate(DateTime deletedDate) {
        super.withDeletedDate(deletedDate);
        return this;
    }

    @Override
    public Person withDeletedBy(Integer deletedBy) {
        super.withDeletedBy(deletedBy);
        return this;
    }

    @Override
    public Person withVersions(List<Object> versions) {
        super.withVersions(versions);
        return this;
    }

    @Override
    public Person withAdditionalProperties(Object additionalProperties) {
        super.withAdditionalProperties(additionalProperties);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(contribution).append(firstName).append(lastName).append(sortableName).append(fullName).append(links).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Person) == false) {
            return false;
        }
        Person rhs = ((Person) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(contribution, rhs.contribution).append(firstName, rhs.firstName).append(lastName, rhs.lastName).append(sortableName, rhs.sortableName).append(fullName, rhs.fullName).append(links, rhs.links).isEquals();
    }

    public enum Contribution {

        ACTOR("actor"),
        DIRECTOR("director"),
        WRITER("writer"),
        PRODUCER("producer");
        private final String value;
        private final static Map<String, Person.Contribution> CONSTANTS = new HashMap<String, Person.Contribution>();

        static {
            for (Person.Contribution c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Contribution(String value) {
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
        public static Person.Contribution fromValue(String value) {
            Person.Contribution constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
