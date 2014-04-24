package org.ccci.gto.globalreg.jaxrs;

import com.google.common.base.Throwables;
import com.google.common.io.CharStreams;
import com.google.common.net.HttpHeaders;
import org.ccci.gto.globalreg.AbstractGlobalRegistryClient;
import org.ccci.gto.globalreg.Filter;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

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
    public <T> ResponseList<T> getEntities(final Type<T> type, final String createdBy, final int page,
                                           final Filter... filters) {
        // build the request uri
        final UriBuilder uri = this.getApiUriBuilder().path(PATH_ENTITIES);
        uri.queryParam(PARAM_ENTITY_TYPE, type.getEntityType());
        uri.queryParam(PARAM_PAGE, page);
        if (createdBy != null) {
            uri.queryParam(PARAM_CREATED_BY, createdBy);
        }
        for (final Filter filter : filters) {
            uri.queryParam(this.buildFilterParamName(filter), filter.getValue());
        }

        // build & execute the request
        HttpURLConnection conn = null;
        try {
            conn = this.prepareRequest((HttpURLConnection) uri.build().toURL().openConnection());

            if (conn.getResponseCode() == 200) {
                try (final InputStreamReader in = new InputStreamReader(conn.getInputStream())) {
                    return this.serializer.deserializeEntities(type, CharStreams.toString(in));
                }
            }
        } catch (final IOException e) {
            LOG.debug("error searching for entities", e);
            throw Throwables.propagate(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

    @Override
    public <T> T getEntity(final Type<T> type, final int id, final String createdBy) {
        // build the request uri
        final UriBuilder uri = this.getApiUriBuilder().path(PATH_ENTITIES).path(Integer.toString(id));
        uri.queryParam(PARAM_ENTITY_TYPE, type.getEntityType());
        if (createdBy != null) {
            uri.queryParam(PARAM_CREATED_BY, createdBy);
        }

        // build & execute the request
        HttpURLConnection conn = null;
        try {
            conn = this.prepareRequest((HttpURLConnection) uri.build().toURL().openConnection());

            if (conn.getResponseCode() == 200) {
                try (final InputStreamReader in = new InputStreamReader(conn.getInputStream())) {
                    return this.serializer.deserializeEntity(type, CharStreams.toString(in));
                }
            }
        } catch (final IOException e) {
            LOG.debug("error retrieving entity", e);
            throw Throwables.propagate(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
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
}
