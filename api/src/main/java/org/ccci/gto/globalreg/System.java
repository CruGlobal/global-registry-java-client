package org.ccci.gto.globalreg;

public class System {
    private Long id;
    private String name;
    private Boolean root;
    private Boolean trusted;
    private String accessToken;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
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
}
