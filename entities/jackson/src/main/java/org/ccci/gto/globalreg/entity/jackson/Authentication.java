
package org.ccci.gto.globalreg.entity.jackson;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

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
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("relayGuid", relayGuid)
                .add("keyGuid", keyGuid)
                .add("parentId", parentId)
                .add("clientUpdatedAt", clientUpdatedAt)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authentication)) return false;
        Authentication that = (Authentication) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(relayGuid, that.relayGuid) &&
                Objects.equal(keyGuid, that.keyGuid) &&
                Objects.equal(parentId, that.parentId) &&
                Objects.equal(clientUpdatedAt, that.clientUpdatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, relayGuid, keyGuid, parentId, clientUpdatedAt);
    }
}
