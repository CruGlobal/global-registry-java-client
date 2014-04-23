package org.ccci.gto.globalreg;

public abstract class AbstractGlobalRegistryClientTest {
    private static final String ACCESS_TOKEN = "";

    protected abstract AbstractGlobalRegistryClient newClient();

    protected AbstractGlobalRegistryClient getClient() {
        // dont create client if we don't have an access token
        if(ACCESS_TOKEN == null || "".equals(ACCESS_TOKEN)) {
            return null;
        }

        final AbstractGlobalRegistryClient client = this.newClient();
        client.setApiUrl("http://gr.stage.uscm.org");
        client.setAccessToken(ACCESS_TOKEN);
        return client;
    }
}
