package org.ccci.gto.globalreg.httpclient;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.BaseGlobalRegistryClientTest;

public class HttpClientGlobalRegistryClientTest extends BaseGlobalRegistryClientTest {
    @Override
    protected BaseGlobalRegistryClient newClient() {
        return new HttpClientGlobalRegistryClient();
    }
}
