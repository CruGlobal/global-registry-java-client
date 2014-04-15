package org.ccci.gto.globalreg;

public abstract class AbstractGlobalRegistryClientTest {

    protected abstract AbstractGlobalRegistryClient newClient();

    protected AbstractGlobalRegistryClient getClient() {
        final AbstractGlobalRegistryClient client = this.newClient();
        client.setApiUrl("http://gr.stage.uscm.org");
        client.setAccessToken("");
        return client;
    }
}
