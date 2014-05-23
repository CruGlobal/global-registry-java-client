package org.ccci.gto.globalreg.httpclient;

import static org.junit.Assert.assertEquals;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.BaseGlobalRegistryClientTest;
import org.junit.Test;

import java.net.URI;

public class HttpClientGlobalRegistryClientTest extends BaseGlobalRegistryClientTest {
    @Test
    public void testBuildUri() throws Exception {
        final HttpClientGlobalRegistryClient client = new HttpClientGlobalRegistryClient();

        // test api uri with & without trailing slash
        final BaseGlobalRegistryClient.Request request = this.emptyRequest();
        request.path = new String[]{"test"};
        for (final String apiUri : new String[]{"http://www.example.com/globalreg/",
                "http://www.example.com/globalreg"}) {
            client.setApiUrl(apiUri);
            assertEquals(URI.create("http://www.example.com/globalreg/test"), client.buildUri(request));
        }
        for (final String apiUri : new String[]{"http://www.example.com",
                "http://www.example.com/"}) {
            client.setApiUrl(apiUri);
            assertEquals(URI.create("http://www.example.com/test"), client.buildUri(request));
        }
    }
}
