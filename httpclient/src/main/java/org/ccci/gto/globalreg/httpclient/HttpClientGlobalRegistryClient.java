package org.ccci.gto.globalreg.httpclient;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.common.net.HttpHeaders;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.UnauthorizedException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class HttpClientGlobalRegistryClient extends BaseGlobalRegistryClient {
    private final static Joiner JOINER_PATH = Joiner.on("/").skipNulls();

    private final static ResponseHandler<Response> RESPONSE_HANDLER = new ResponseHandler<Response>() {
        @Override
        public Response handleResponse(final HttpResponse response) throws IOException {
            final HttpEntity entity = response.getEntity();
            return new Response(response.getStatusLine().getStatusCode(), entity != null ? EntityUtils.toString
                    (entity) : null);
        }
    };

    @Override
    protected Response processRequest(final Request request) throws UnauthorizedException {
        try {
            // build the request uri
            final URI uri = this.buildUri(request);

            // build the base request
            HttpRequestBase req = null;
            if (request.method != null) {
                switch (request.method) {
                    case "GET":
                        req = new HttpGet(uri);
                        break;
                    case "POST":
                        req = new HttpPost(uri);
                        break;
                    case "PUT":
                        req = new HttpPut(uri);
                        break;
                    case "DELETE":
                        req = new HttpDelete(uri);
                        break;
                }
            }
            if (req == null) {
                throw new IllegalArgumentException("Request specifies unsupported method: " + request.method);
            }
            req.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken);
            for (final Map.Entry<String, String> header : request.headers.entrySet()) {
                req.addHeader(header.getKey(), header.getValue());
            }

            // set connect & read timeouts
            final RequestConfig oldConfig = req.getConfig();
            final RequestConfig.Builder config = oldConfig != null ? RequestConfig.copy(oldConfig) : RequestConfig
                    .custom();
            config.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).setSocketTimeout
                    (readTimeout);
            req.setConfig(config.build());

            // send content when necessary
            if (request.content != null && req instanceof HttpEntityEnclosingRequest) {
                ((HttpEntityEnclosingRequest) req).setEntity(new StringEntity(request.content,
                        ContentType.create(request.contentType, Charsets.UTF_8)));
            }

            // execute request & return response
            CloseableHttpClient client = HttpClients.createDefault();
            try {
                return client.execute(req, RESPONSE_HANDLER);
            } finally {
                client.close();
            }
        } catch (final IOException | URISyntaxException e) {
            throw Throwables.propagate(e);
        }
    }

    URI buildUri(final Request request) throws URISyntaxException {
        // build the request uri
        final URIBuilder builder = new URIBuilder(this.apiUrl);
        final String path = builder.getPath();
        builder.setPath(path + JOINER_PATH.join(request.path));
        for (final Map.Entry<String, String> param : request.queryParams.entries()) {
            builder.addParameter(param.getKey(), param.getValue());
        }
        return builder.build();
    }
}
