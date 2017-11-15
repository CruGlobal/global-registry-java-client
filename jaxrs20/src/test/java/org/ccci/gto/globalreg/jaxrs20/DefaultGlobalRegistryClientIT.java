package org.ccci.gto.globalreg.jaxrs20;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.BaseGlobalRegistryClientIT;

public class DefaultGlobalRegistryClientIT extends BaseGlobalRegistryClientIT {

    @Override
    protected BaseGlobalRegistryClient newClient() {
        return new Jaxrs20GlobalRegistryClient();
    }
}
