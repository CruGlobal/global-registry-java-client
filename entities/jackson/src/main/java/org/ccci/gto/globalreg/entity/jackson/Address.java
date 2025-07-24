
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
    "active",
    "address_type",
    "start_date",
    "line1",
    "city",
    "state",
    "postal_code",
    "country",
    "parent_id",
    "client_integration_id"
})
public class Address {

    @JsonProperty("id")
    private String id;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("address_type")
    private String addressType;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("line1")
    private String line1;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("country")
    private String country;
    @JsonProperty("parent_id")
    private String parentId;
    @JsonProperty("client_integration_id")
    private String clientIntegrationId;

    /**
     * No args constructor for use in serialization
     *
     */
    public Address() {
    }

    /**
     *
     * @param clientIntegrationId
     * @param country
     * @param city
     * @param addressType
     * @param postalCode
     * @param active
     * @param id
     * @param state
     * @param startDate
     * @param line1
     * @param parentId
     */
    public Address(String id, Boolean active, String addressType, String startDate, String line1, String city, String state, String postalCode, String country, String parentId, String clientIntegrationId) {
        this.id = id;
        this.active = active;
        this.addressType = addressType;
        this.startDate = startDate;
        this.line1 = line1;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
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
     *     The active
     */
    @JsonProperty("active")
    public Boolean getActive() {
        return active;
    }

    /**
     *
     * @param active
     *     The active
     */
    @JsonProperty("active")
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     *
     * @return
     *     The addressType
     */
    @JsonProperty("address_type")
    public String getAddressType() {
        return addressType;
    }

    /**
     *
     * @param addressType
     *     The address_type
     */
    @JsonProperty("address_type")
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    /**
     *
     * @return
     *     The startDate
     */
    @JsonProperty("start_date")
    public String getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     *     The start_date
     */
    @JsonProperty("start_date")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     *     The line1
     */
    @JsonProperty("line1")
    public String getLine1() {
        return line1;
    }

    /**
     *
     * @param line1
     *     The line1
     */
    @JsonProperty("line1")
    public void setLine1(String line1) {
        this.line1 = line1;
    }

    /**
     *
     * @return
     *     The city
     */
    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     *     The city
     */
    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     *     The state
     */
    @JsonProperty("state")
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     *     The state
     */
    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     *     The postalCode
     */
    @JsonProperty("postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @param postalCode
     *     The postal_code
     */
    @JsonProperty("postal_code")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     * @return
     *     The country
     */
    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     *     The country
     */
    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
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
        return new StringBuilder("Address{")
            .append("id='").append(id).append('\'')
            .append(", active=").append(active)
            .append(", addressType='").append(addressType).append('\'')
            .append(", startDate='").append(startDate).append('\'')
            .append(", line1='").append(line1).append('\'')
            .append(", city='").append(city).append('\'')
            .append(", state='").append(state).append('\'')
            .append(", postalCode='").append(postalCode).append('\'')
            .append(", country='").append(country).append('\'')
            .append(", parentId='").append(parentId).append('\'')
            .append(", clientIntegrationId='").append(clientIntegrationId).append('\'')
            .append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) &&
                Objects.equals(active, address.active) &&
                Objects.equals(addressType, address.addressType) &&
                Objects.equals(startDate, address.startDate) &&
                Objects.equals(line1, address.line1) &&
                Objects.equals(city, address.city) &&
                Objects.equals(state, address.state) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(country, address.country) &&
                Objects.equals(parentId, address.parentId) &&
                Objects.equals(clientIntegrationId, address.clientIntegrationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active, addressType, startDate, line1, city, state, postalCode, country, parentId, clientIntegrationId);
    }
}
