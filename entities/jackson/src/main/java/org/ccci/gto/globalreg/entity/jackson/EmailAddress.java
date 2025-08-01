
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
    "email",
    "parent_id",
    "client_integration_id",
    "client_updated_at"
})
public class EmailAddress {

    @JsonProperty("id")
    private String id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("parent_id")
    private String parentId;
    @JsonProperty("client_integration_id")
    private String clientIntegrationId;
    @JsonProperty("client_updated_at")
    private String clientUpdatedAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public EmailAddress() {
    }

    /**
     *
     * @param clientIntegrationId
     * @param id
     * @param clientUpdatedAt
     * @param email
     * @param parentId
     */
    public EmailAddress(String id, String email, String parentId, String clientIntegrationId, String clientUpdatedAt) {
        this.id = id;
        this.email = email;
        this.parentId = parentId;
        this.clientIntegrationId = clientIntegrationId;
        this.clientUpdatedAt = clientUpdatedAt;
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
     *     The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     *     The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
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
     *     The clientUpdatedAt
     */
    @JsonProperty("client_updated_at")
    public String getClientUpdatedAt() {
        return clientUpdatedAt;
    }

    /**
     *
     * @param clientUpdatedAt
     *     The client_updated_at
     */
    @JsonProperty("client_updated_at")
    public void setClientUpdatedAt(String clientUpdatedAt) {
        this.clientUpdatedAt = clientUpdatedAt;
    }

    @Override
    public String toString() {
        return new StringBuilder("EmailAddress{")
            .append("id='").append(id).append('\'')
            .append(", email='").append(email).append('\'')
            .append(", parentId='").append(parentId).append('\'')
            .append(", clientIntegrationId='").append(clientIntegrationId).append('\'')
            .append(", clientUpdatedAt='").append(clientUpdatedAt).append('\'')
            .append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddress that = (EmailAddress) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(clientIntegrationId, that.clientIntegrationId) &&
                Objects.equals(clientUpdatedAt, that.clientUpdatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, parentId, clientIntegrationId, clientUpdatedAt);
    }
}
