package org.ccci.gto.globalreg.httpclient;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.BaseGlobalRegistryClientIT;

public class HttpClientGlobalRegistryClientIT extends BaseGlobalRegistryClientIT {
    @Override
    protected BaseGlobalRegistryClient newClient() {
        return new HttpClientGlobalRegistryClient();
    }
}
