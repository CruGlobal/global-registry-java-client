package org.ccci.gto.globalreg.jaxrs20;

import org.apache.cxf.jaxrs.client.spec.ClientBuilderImpl;
import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.BaseGlobalRegistryClientIT;

public class CxfGlobalRegistryClientIT extends BaseGlobalRegistryClientIT {

    @Override
    protected BaseGlobalRegistryClient newClient() {
        return new Jaxrs20GlobalRegistryClient(new ClientBuilderImpl());
    }
}
