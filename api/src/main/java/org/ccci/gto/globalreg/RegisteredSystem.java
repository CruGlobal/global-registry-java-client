package org.ccci.gto.globalreg;

import org.ccci.gto.globalreg.util.Objects;

public final class RegisteredSystem {
    private String id;
    private String name;
    private String contactName;
    private String contactEmail;
    private Boolean root;
    private Boolean trusted;
    private String accessToken;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(final String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public void setContactEmail(final String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Boolean getRoot() {
        return this.root;
    }

    public void setRoot(final Boolean root) {
        this.root = root;
    }

    public boolean isRoot() {
        return this.root != null && this.root;
    }

    public Boolean getTrusted() {
        return this.trusted;
    }

    public void setTrusted(final Boolean trusted) {
        this.trusted = trusted;
    }

    public boolean isTrusted() {
        return this.trusted != null && this.trusted;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegisteredSystem that = (RegisteredSystem) o;
        return Objects.equals(this.id, that.id) && Objects.equals(this.name, that.name) && Objects.equals(this.root,
                that.root) && Objects.equals(this.trusted, that.trusted) && Objects.equals(this.accessToken,
                that.accessToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.root, this.trusted, this.accessToken);
    }
}
