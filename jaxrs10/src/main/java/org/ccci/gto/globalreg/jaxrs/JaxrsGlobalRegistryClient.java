package org.ccci.gto.globalreg.jaxrs;

import com.google.common.base.Throwables;
import com.google.common.io.CharStreams;
import com.google.common.net.HttpHeaders;
import org.ccci.gto.globalreg.AbstractGlobalRegistryClient;
import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.Filter;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Collection;

public class JaxrsGlobalRegistryClient extends AbstractGlobalRegistryClient {
    private static final Logger LOG = LoggerFactory.getLogger(JaxrsGlobalRegistryClient.class);

    private UriBuilder getApiUriBuilder() {
        return UriBuilder.fromUri(this.apiUrl);
    }

    private HttpURLConnection prepareRequest(final HttpURLConnection conn) {
        conn.setRequestProperty(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
        conn.setRequestProperty(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken);
        return conn;
    }

    private HttpURLConnection sendData(final HttpURLConnection conn, final String data) {
        conn.setDoOutput(true);
        try (OutputStream raw = conn.getOutputStream(); OutputStreamWriter out = new OutputStreamWriter(raw)) {
            out.write(data);
        } catch (final IOException e) {
            LOG.debug("error writing data to connection", e);
            throw Throwables.propagate(e);
        }
        return conn;
    }

    @Override
    public <T> T addEntity(final Type<T> type, final T entity) {
        // build the request uri
        final UriBuilder uri = this.getApiUriBuilder().path(PATH_ENTITIES);

        // build & execute the request
        HttpURLConnection conn = null;
        try {
            conn = this.prepareRequest((HttpURLConnection) uri.build().toURL().openConnection());
            conn.setRequestMethod("POST");
            conn.setRequestProperty(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            this.sendData(conn, this.serializer.serializeEntity(type, entity));

            // parse the stored entity on successful creation/update
            // 200: existing entity updated
            // 201: new entity created
            final int code = conn.getResponseCode();
            if (code == 200 || code == 201) {
                try (final InputStreamReader in = new InputStreamReader(conn.getInputStream())) {
                    return this.serializer.deserializeEntity(type, CharStreams.toString(in));
                }
            }
        } catch (final IOException e) {
            LOG.debug("error storing new entity", e);
            throw Throwables.propagate(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

    @Override
    public <T> T updateEntity(final Type<T> type, final int id, final T entity) {
        // build the request uri
        final UriBuilder uri = this.getApiUriBuilder().path(PATH_ENTITIES).path(Integer.toString(id));

        // build & execute the request
        HttpURLConnection conn = null;
        try {
            conn = this.prepareRequest((HttpURLConnection) uri.build().toURL().openConnection());
            conn.setRequestMethod("PUT");
            conn.setRequestProperty(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            this.sendData(conn, this.serializer.serializeEntity(type, entity));

            // parse the stored entity on successful update
            // 200: successful update
            final int code = conn.getResponseCode();
            if (code == 200) {
                try (final InputStreamReader in = new InputStreamReader(conn.getInputStream())) {
                    return this.serializer.deserializeEntity(type, CharStreams.toString(in));
                }
            }
        } catch (final IOException e) {
            LOG.debug("error updating entity", e);
            throw Throwables.propagate(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

    @Override
    public <T> void deleteEntity(final Type<T> type, final int id) {
        // build the request uri
        final UriBuilder uri = this.getApiUriBuilder().path(PATH_ENTITIES).path(Integer.toString(id));

        // build & execute the request
        HttpURLConnection conn = null;
        try {
            conn = this.prepareRequest((HttpURLConnection) uri.build().toURL().openConnection());
            conn.setRequestMethod("DELETE");


        } catch (final IOException e) {
            LOG.debug("error deleting entity", e);
            throw Throwables.propagate(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @Override
    public ResponseList<EntityType> getEntityTypes(final int page, final Filter... filters) {
        // build the request uri
        final UriBuilder uri = this.getApiUriBuilder().path(PATH_ENTITY_TYPES);
        uri.queryParam(PARAM_PAGE, page);
        for (final Filter filter : filters) {
            uri.queryParam(this.buildFilterParamName(filter), filter.getValue());
        }

        // build & execute the request
        HttpURLConnection conn = null;
        try {
            conn = this.prepareRequest((HttpURLConnection) uri.build().toURL().openConnection());

            if (conn.getResponseCode() == 200) {
                try (final InputStreamReader in = new InputStreamReader(conn.getInputStream())) {
                    return this.serializer.deserializeEntityTypes(CharStreams.toString(in));
                }
            }
        } catch (final IOException e) {
            LOG.debug("error retrieving entity types", e);
            throw Throwables.propagate(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

    @Override
    protected Response processRequest(final Request request) {
        // build the request uri
        final UriBuilder uriBuilder = this.getApiUriBuilder();
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
            conn.setRequestMethod(request.method);
            conn.setRequestProperty(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
            conn.setRequestProperty(HttpHeaders.AUTHORIZATION, "Bearer " + this.accessToken);

            // read & return response
            try (InputStream raw = conn.getInputStream(); InputStreamReader in = new InputStreamReader(raw)) {
                return new Response(conn.getResponseCode(), CharStreams.toString(in));
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
