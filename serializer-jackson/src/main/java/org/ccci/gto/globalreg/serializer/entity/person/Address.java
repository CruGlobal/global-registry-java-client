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
    "active",
    "address_type",
    "start_date",
    "line1",
    "city",
    "state",
    "postal_code",
    "country",
    "parent_id",
    "client_integration_id",
    "current_address"
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
    @JsonProperty("current_address")
    private Boolean currentAddress;

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

    /**
     * 
     * @return
     *     The currentAddress
     */
    @JsonProperty("current_address")
    public Boolean getCurrentAddress() {
        return currentAddress;
    }

    /**
     * 
     * @param currentAddress
     *     The current_address
     */
    @JsonProperty("current_address")
    public void setCurrentAddress(Boolean currentAddress) {
        this.currentAddress = currentAddress;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(active).append(addressType).append(startDate).append(line1).append(city).append(state).append(postalCode).append(country).append(parentId).append(clientIntegrationId).append(currentAddress).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Address) == false) {
            return false;
        }
        Address rhs = ((Address) other);
        return new EqualsBuilder().append(id, rhs.id).append(active, rhs.active).append(addressType, rhs.addressType).append(startDate, rhs.startDate).append(line1, rhs.line1).append(city, rhs.city).append(state, rhs.state).append(postalCode, rhs.postalCode).append(country, rhs.country).append(parentId, rhs.parentId).append(clientIntegrationId, rhs.clientIntegrationId).append(currentAddress, rhs.currentAddress).isEquals();
    }

}
