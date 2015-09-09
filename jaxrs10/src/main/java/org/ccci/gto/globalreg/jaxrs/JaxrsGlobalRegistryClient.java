package org.ccci.gto.globalreg.jaxrs;

import com.google.common.base.Throwables;
import com.google.common.io.CharStreams;
import com.google.common.net.HttpHeaders;
import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Collection;
import java.util.Map;

public class JaxrsGlobalRegistryClient extends BaseGlobalRegistryClient {
    private static final Logger LOG = LoggerFactory.getLogger(JaxrsGlobalRegistryClient.class);

    @Override
    protected Response processRequest(final Request request) throws UnauthorizedException {
        // build the request uri
        final UriBuilder uriBuilder = UriBuilder.fromUri(this.apiUrl);
        for (final String path : request.path) {
            uriBuilder.path(path);
        }
        for (final String key : request.queryParams.keySet()) {
            final Collection<String> values = request.queryParams.get(key);
            uriBuilder.queryParam(key, values.toArray(new String[values.size()]));
        }
        final URI uri = uriBuilder.build();

        // build & execute the request
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            conn.setRequestMethod(request.method);
            conn.setRequestProperty(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken);
            for (final Map.Entry<String, String> header : request.headers.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }

            // send content when necessary
            if (request.content != null) {
                conn.setRequestProperty(HttpHeaders.CONTENT_TYPE, request.contentType);
                conn.setDoOutput(true);
                OutputStream raw = null;
                OutputStreamWriter out = null;
                try {
                    raw = conn.getOutputStream();
                    out = new OutputStreamWriter(raw);
                    out.write(request.content);
                } catch (final IOException e) {
                    LOG.debug("error writing data to connection", e);
                    throw Throwables.propagate(e);
                } finally {
                    if(raw != null) raw.close();
                    if(out != null) out.close();
                }
            }

            // read & return response
            final int code = conn.getResponseCode();
            InputStream raw = null;
            InputStreamReader in = null;
            try {
                raw = conn.getInputStream();
                in = new InputStreamReader(raw);
                return new Response(conn.getResponseCode(), CharStreams.toString(in));
            } catch (final IOException e) {
                // XXX: this probably isn't right for 200 & 201 responses
                return new Response(code, "");
            } finally {
                if(raw != null) raw.close();
                if(in != null) in.close();
            }
        } catch (final IOException e) {
            LOG.debug("error processing request: {}", uri, e);
            throw Throwables.propagate(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
