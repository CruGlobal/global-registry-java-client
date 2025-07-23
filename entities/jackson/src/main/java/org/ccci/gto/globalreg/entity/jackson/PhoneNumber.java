
package org.ccci.gto.globalreg.entity.jackson;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "primary",
    "location",
    "number",
    "parent_id",
    "client_integration_id"
})
public class PhoneNumber {

    @JsonProperty("id")
    private String id;
    @JsonProperty("primary")
    private Boolean primary;
    @JsonProperty("location")
    private String location;
    @JsonProperty("number")
    private String number;
    @JsonProperty("parent_id")
    private String parentId;
    @JsonProperty("client_integration_id")
    private String clientIntegrationId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PhoneNumber() {
    }

    /**
     * 
     * @param clientIntegrationId
     * @param number
     * @param location
     * @param id
     * @param parentId
     * @param primary
     */
    public PhoneNumber(String id, Boolean primary, String location, String number, String parentId, String clientIntegrationId) {
        this.id = id;
        this.primary = primary;
        this.location = location;
        this.number = number;
        this.parentId = parentId;
        this.clientIntegrationId = clientIntegrationId;
    }

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
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("primary", primary)
                .add("location", location)
                .add("number", number)
                .add("parentId", parentId)
                .add("clientIntegrationId", clientIntegrationId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber)) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(primary, that.primary) &&
                Objects.equal(location, that.location) &&
                Objects.equal(number, that.number) &&
                Objects.equal(parentId, that.parentId) &&
                Objects.equal(clientIntegrationId, that.clientIntegrationId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, primary, location, number, parentId, clientIntegrationId);
    }
}
