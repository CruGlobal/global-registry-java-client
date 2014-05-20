package org.ccci.gto.globalreg.jaxrs;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.BaseGlobalRegistryClientIT;

public class JaxrsGlobalRegistryClientIT extends BaseGlobalRegistryClientIT {
    @Override
    protected BaseGlobalRegistryClient newClient() {
        return new JaxrsGlobalRegistryClient();
    }
}
