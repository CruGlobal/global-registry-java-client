
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
    "relay_guid",
    "key_guid",
    "parent_id",
    "client_updated_at"
})
public class Authentication {

    @JsonProperty("id")
    private String id;
    @JsonProperty("relay_guid")
    private String relayGuid;
    @JsonProperty("key_guid")
    private String keyGuid;
    @JsonProperty("parent_id")
    private String parentId;
    @JsonProperty("client_updated_at")
    private String clientUpdatedAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public Authentication() {
    }

    /**
     *
     * @param keyGuid
     * @param relayGuid
     * @param id
     * @param clientUpdatedAt
     * @param parentId
     */
    public Authentication(String id, String relayGuid, String keyGuid, String parentId, String clientUpdatedAt) {
        this.id = id;
        this.relayGuid = relayGuid;
        this.keyGuid = keyGuid;
        this.parentId = parentId;
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
     *     The relayGuid
     */
    @JsonProperty("relay_guid")
    public String getRelayGuid() {
        return relayGuid;
    }

    /**
     *
     * @param relayGuid
     *     The relay_guid
     */
    @JsonProperty("relay_guid")
    public void setRelayGuid(String relayGuid) {
        this.relayGuid = relayGuid;
    }

    /**
     *
     * @return
     *     The keyGuid
     */
    @JsonProperty("key_guid")
    public String getKeyGuid() {
        return keyGuid;
    }

    /**
     *
     * @param keyGuid
     *     The key_guid
     */
    @JsonProperty("key_guid")
    public void setKeyGuid(String keyGuid) {
        this.keyGuid = keyGuid;
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
        return new StringBuilder("Authentication{")
            .append("id='").append(id).append('\'')
            .append(", relayGuid='").append(relayGuid).append('\'')
            .append(", keyGuid='").append(keyGuid).append('\'')
            .append(", parentId='").append(parentId).append('\'')
            .append(", clientUpdatedAt='").append(clientUpdatedAt).append('\'')
            .append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authentication that = (Authentication) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(relayGuid, that.relayGuid) &&
                Objects.equals(keyGuid, that.keyGuid) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(clientUpdatedAt, that.clientUpdatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, relayGuid, keyGuid, parentId, clientUpdatedAt);
    }
}
