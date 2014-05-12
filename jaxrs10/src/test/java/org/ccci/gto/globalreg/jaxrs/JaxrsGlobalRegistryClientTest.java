package org.ccci.gto.globalreg.jaxrs;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.BaseGlobalRegistryClientTest;

public class JaxrsGlobalRegistryClientTest extends BaseGlobalRegistryClientTest {
    @Override
    protected BaseGlobalRegistryClient newClient() {
        return new JaxrsGlobalRegistryClient();
    }
}
