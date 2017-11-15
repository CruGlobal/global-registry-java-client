package org.ccci.gto.globalreg.jaxrs20;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.BaseGlobalRegistryClientIT;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

public class ResteasyGlobalRegistryClientIT extends BaseGlobalRegistryClientIT {

    @Override
    protected BaseGlobalRegistryClient newClient() {
        return new Jaxrs20GlobalRegistryClient(new ResteasyClientBuilder());
    }
}
