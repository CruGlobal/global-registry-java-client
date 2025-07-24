package org.ccci.gto.globalreg.jaxrs;

import org.ccci.gto.globalreg.BaseGlobalRegistryClient;
import org.ccci.gto.globalreg.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Collection;
import java.util.Map;

public class JaxrsGlobalRegistryClient extends BaseGlobalRegistryClient {
    private static final Logger LOG = LoggerFactory.getLogger(JaxrsGlobalRegistryClient.class);

    @Nonnull
    @Override
    protected Response processRequest(final Request request) throws UnauthorizedException {
        // build the request uri
        final UriBuilder uriBuilder = UriBuilder.fromUri(this.apiUrl);
        for (final String path : request.path) {
            uriBuilder.path(path);
        }
        for (final String key : request.queryParams.keySet()) {
            final Collection<String> values = request.queryParams.get(key);
            uriBuilder.queryParam(key, (Object[]) values.toArray(new Object[values.size()]));
        }
        final URI uri = uriBuilder.build();

        // build & execute the request
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            conn.setRequestMethod(request.method);
            conn.setRequestProperty("Authorization", "Bearer " + this.accessToken);
            for (final Map.Entry<String, String> header : request.headers.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }

            // send content when necessary
            if (request.content != null) {
                conn.setRequestProperty("Content-Type", request.contentType);
                conn.setDoOutput(true);
                try (OutputStream raw = conn.getOutputStream(); OutputStreamWriter out = new OutputStreamWriter(raw)) {
                    out.write(request.content);
                } catch (final IOException e) {
                    LOG.debug("error writing data to connection", e);
                    throw new RuntimeException(e);
                }
            }

            // read & return response
            final int code = conn.getResponseCode();
            try (InputStream raw = conn.getInputStream(); InputStreamReader in = new InputStreamReader(raw)) {
                StringWriter writer = new StringWriter();
                in.transferTo(writer);
                return new Response(conn.getResponseCode(), writer.toString());
            } catch (final IOException e) {
                try (
                    InputStream rawError = conn.getErrorStream();
                    InputStreamReader errorReader = new InputStreamReader(rawError)
                ) {
                    StringWriter writer = new StringWriter();
                    errorReader.transferTo(writer);
                    return new Response(conn.getResponseCode(), writer.toString());
                } catch (final IOException e2) {
                    return new Response(code, "<error content not available>");
                }
            }
        } catch (final IOException e) {
            LOG.debug("error processing request: {}", uri, e);
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
