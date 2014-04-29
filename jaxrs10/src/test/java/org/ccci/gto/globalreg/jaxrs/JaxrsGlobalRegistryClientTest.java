package org.ccci.gto.globalreg.jaxrs;

import org.ccci.gto.globalreg.base.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.base.BaseGlobalRegistryClientTest;

public class JaxrsGlobalRegistryClientTest extends BaseGlobalRegistryClientTest {
    @Override
    protected BaseGlobalRegistryClient newClient() {
        return new JaxrsGlobalRegistryClient();
    }
}
