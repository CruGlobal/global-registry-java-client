
package org.ccci.gto.globalreg.entity.jackson;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Objects;

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
        return new StringBuilder("PhoneNumber{")
            .append("id='").append(id).append('\'')
            .append(", primary=").append(primary)
            .append(", location='").append(location).append('\'')
            .append(", number='").append(number).append('\'')
            .append(", parentId='").append(parentId).append('\'')
            .append(", clientIntegrationId='").append(clientIntegrationId).append('\'')
            .append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(primary, that.primary) &&
                Objects.equals(location, that.location) &&
                Objects.equals(number, that.number) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(clientIntegrationId, that.clientIntegrationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, primary, location, number, parentId, clientIntegrationId);
    }
}
