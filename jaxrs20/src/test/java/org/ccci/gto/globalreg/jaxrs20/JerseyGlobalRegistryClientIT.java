package org.ccci.gto.globalreg.jaxrs20;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.BaseGlobalRegistryClientIT;
import org.glassfish.jersey.client.JerseyClientBuilder;

public class JerseyGlobalRegistryClientIT extends BaseGlobalRegistryClientIT {

    @Override
    protected BaseGlobalRegistryClient newClient() {
        return new Jaxrs20GlobalRegistryClient(new JerseyClientBuilder().build());
    }
}
