
package org.ccci.gto.globalreg.serializer.entity.person;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "number",
    "location",
    "primary",
    "parent_id",
    "client_integration_id"
})
public class PhoneNumber {

    @JsonProperty("id")
    private String id;
    @JsonProperty("number")
    private String number;
    @JsonProperty("location")
    private String location;
    @JsonProperty("primary")
    private Boolean primary;
    @JsonProperty("parent_id")
    private String parentId;
    @JsonProperty("client_integration_id")
    private String clientIntegrationId;

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The number
     */
    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 
     * @return
     *     The location
     */
    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    /**
     * 
     * @param location
     *     The location
     */
    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 
     * @return
     *     The primary
     */
    @JsonProperty("primary")
    public Boolean getPrimary() {
        return primary;
    }

    /**
     * 
     * @param primary
     *     The primary
     */
    @JsonProperty("primary")
    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    /**
     * 
     * @return
     *     The parentId
     */
    @JsonProperty("parent_id")
    public String getParentId() {
        return parentId;
    }

    /**
     * 
     * @param parentId
     *     The parent_id
     */
    @JsonProperty("parent_id")
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 
     * @return
     *     The clientIntegrationId
     */
    @JsonProperty("client_integration_id")
    public String getClientIntegrationId() {
        return clientIntegrationId;
    }

    /**
     * 
     * @param clientIntegrationId
     *     The client_integration_id
     */
    @JsonProperty("client_integration_id")
    public void setClientIntegrationId(String clientIntegrationId) {
        this.clientIntegrationId = clientIntegrationId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(number).append(location).append(primary).append(parentId).append(clientIntegrationId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PhoneNumber) == false) {
            return false;
        }
        PhoneNumber rhs = ((PhoneNumber) other);
        return new EqualsBuilder().append(id, rhs.id).append(number, rhs.number).append(location, rhs.location).append(primary, rhs.primary).append(parentId, rhs.parentId).append(clientIntegrationId, rhs.clientIntegrationId).isEquals();
    }

}
