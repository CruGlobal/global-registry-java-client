package org.ccci.gto.globalreg.jaxrs;

import org.ccci.gto.globalreg.AbstractGlobalRegistryClient;
import org.ccci.gto.globalreg.AbstractGlobalRegistryClientTest;

public class JaxrsGlobalRegistryClientTest extends AbstractGlobalRegistryClientTest {
    @Override
    protected AbstractGlobalRegistryClient newClient() {
        return new JaxrsGlobalRegistryClient();
    }
}
