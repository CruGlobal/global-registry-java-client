package org.ccci.gto.globalreg.jaxrs;

import com.google.common.base.Throwables;
import com.google.common.io.CharStreams;
import com.google.common.net.HttpHeaders;
import org.ccci.gto.globalreg.AbstractGlobalRegistryClient;
import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.Filter;
import org.ccci.gto.globalreg.ResponseList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

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

    @Override
    public <T> ResponseList<T> findEntities(final EntityType<T> type, final String createdBy, final int page,
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
                    return this.serializer.parseEntitiesList(type, CharStreams.toString(in));
                }
            }
        } catch (final MalformedURLException e) {
            throw Throwables.propagate(e);
        } catch (final IOException e) {
            LOG.debug("error searching for entities", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

    @Override
    public <T> T getEntity(final EntityType<T> type, final int id, final String createdBy) {
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
                    return this.serializer.parseEntity(type, CharStreams.toString(in));
                }
            }
        } catch (final MalformedURLException e) {
            throw Throwables.propagate(e);
        } catch (final IOException e) {
            LOG.debug("error retrieving entity", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }
}
