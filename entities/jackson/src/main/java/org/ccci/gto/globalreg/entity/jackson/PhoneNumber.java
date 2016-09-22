package org.ccci.gto.globalreg.entity.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

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
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("number", number)
                .add("location", location)
                .add("primary", primary)
                .add("parentId", parentId)
                .add("clientIntegrationId", clientIntegrationId)
                .toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (!(o instanceof PhoneNumber)) { return false; }
        final PhoneNumber that = (PhoneNumber) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(number, that.number) &&
                Objects.equal(location, that.location) &&
                Objects.equal(primary, that.primary) &&
                Objects.equal(parentId, that.parentId) &&
                Objects.equal(clientIntegrationId, that.clientIntegrationId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, number, location, primary, parentId, clientIntegrationId);
    }
}
