package org.ccci.gto.globalreg.resteasy;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.BaseGlobalRegistryClientIT;

public class ResteasyGlobalRegistryClientIT extends BaseGlobalRegistryClientIT {
    @Override
    protected BaseGlobalRegistryClient newClient() {
        return new ResteasyGlobalRegistryClient();
    }
}
