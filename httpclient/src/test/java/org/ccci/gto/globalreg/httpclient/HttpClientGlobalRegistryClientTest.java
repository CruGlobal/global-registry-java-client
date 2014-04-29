package org.ccci.gto.globalreg.httpclient;

import org.ccci.gto.globalreg.base.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.base.BaseGlobalRegistryClientTest;

public class HttpClientGlobalRegistryClientTest extends BaseGlobalRegistryClientTest {
    @Override
    protected BaseGlobalRegistryClient newClient() {
        return new HttpClientGlobalRegistryClient();
    }
}
