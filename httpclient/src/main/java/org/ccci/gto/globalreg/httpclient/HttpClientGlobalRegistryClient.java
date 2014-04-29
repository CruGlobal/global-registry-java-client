package org.ccci.gto.globalreg.httpclient;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.common.net.HttpHeaders;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.ccci.gto.globalreg.UnauthorizedException;
import org.ccci.gto.globalreg.base.BaseGlobalRegistryClient;

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
            final URIBuilder builder = new URIBuilder(this.apiUrl);
            final String path = builder.getPath();
            builder.setPath(JOINER_PATH.join(path, null, request.path));
            for (final Map.Entry<String, String> param : request.queryParams.entries()) {
                builder.addParameter(param.getKey(), param.getValue());
            }
            final URI uri = builder.build();

            // build the base request
            HttpUriRequest req = null;
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

            // send content when necessary
            if (request.content != null && req instanceof HttpEntityEnclosingRequest) {
                ((HttpEntityEnclosingRequest) req).setEntity(new StringEntity(request.content,
                        ContentType.create(request.contentType, Charsets.UTF_8)));
            }

            // execute request & return response
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                final Response response = client.execute(req, RESPONSE_HANDLER);

                // check to see if there was an unauthorized response
                if (response.code == 401) {
                    throw new UnauthorizedException();
                }

                // return the response
                return response;
            }
        } catch (final IOException | URISyntaxException e) {
            throw Throwables.propagate(e);
        }
    }
}
