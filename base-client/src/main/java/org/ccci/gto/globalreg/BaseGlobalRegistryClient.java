package org.ccci.gto.globalreg;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import org.ccci.gto.globalreg.serializer.Serializer;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseGlobalRegistryClient extends AbstractGlobalRegistryClient {
    private static final MediaType APPLICATION_JSON = MediaType.create("application", "json");

    protected String apiUrl;
    protected String accessToken;
    protected Serializer serializer;

    public void setApiUrl(final String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public void setSerializer(final Serializer serializer) {
        this.serializer = serializer;
    }

    protected final String buildFilterParamName(final Filter filter) {
        if (filter != null && filter.isValid()) {
            final StringBuilder sb = new StringBuilder(PARAM_FILTER);
            for (final String field : filter.getPath()) {
                sb.append("[").append(field).append("]");
            }
            return sb.toString();
        }

        return null;
    }

    protected abstract Response processRequest(Request request);

    @Override
    public <T> T getEntity(final Type<T> type, final int id, final String createdBy) {
        // build the request
        final Request request = new Request();
        request.path = new String[]{PATH_ENTITIES, Integer.toString(id)};
        request.queryParams.put(PARAM_ENTITY_TYPE, type.getEntityType());
        if (createdBy != null) {
            request.queryParams.put(PARAM_CREATED_BY, createdBy);
        }

        // process request
        final Response response = this.processRequest(request);
        if (response.code == 200) {
            return this.serializer.deserializeEntity(type, response.content);
        }

        return null;
    }

    @Override
    public <T> ResponseList<T> getEntities(final Type<T> type, final String createdBy, final int page,
                                           final Filter... filters) {
        // build request
        final Request request = new Request();
        request.path = new String[]{PATH_ENTITIES};
        request.queryParams.put(PARAM_ENTITY_TYPE, type.getEntityType());
        request.queryParams.put(PARAM_PAGE, Integer.toString(page));
        if (createdBy != null) {
            request.queryParams.put(PARAM_CREATED_BY, createdBy);
        }
        for (final Filter filter : filters) {
            request.queryParams.put(this.buildFilterParamName(filter), filter.getValue());
        }

        // execute request
        final Response response = this.processRequest(request);

        // process response
        if (response.code == 200) {
            return this.serializer.deserializeEntities(type, response.content);
        }

        return null;
    }

    @Override
    public <T> T addEntity(final Type<T> type, final T entity) {
        // build request
        final Request request = new Request();
        request.method = "POST";
        request.path = new String[]{PATH_ENTITIES};
        request.contentType = APPLICATION_JSON.toString();
        request.content = this.serializer.serializeEntity(type, entity);

        // execute request
        final Response response = this.processRequest(request);

        // process response
        // 200: existing entity updated
        // 201: new entity created
        if (response.code == 200 || response.code == 201) {
            return this.serializer.deserializeEntity(type, response.content);
        }

        return null;
    }

    @Override
    public <T> T updateEntity(final Type<T> type, final int id, final T entity) {
        // build the request
        final Request request = new Request();
        request.method = "PUT";
        request.path = new String[]{PATH_ENTITIES, Integer.toString(id)};
        request.contentType = APPLICATION_JSON.toString();
        request.content = this.serializer.serializeEntity(type, entity);

        // execute request
        final Response response = this.processRequest(request);

        // process response
        // 200: successful update
        if (response.code == 200) {
            return this.serializer.deserializeEntity(type, response.content);
        }

        return null;
    }

    @Override
    public <T> void deleteEntity(Type<T> type, int id) {
        // build the request
        final Request request = new Request();
        request.method = "DELETE";
        request.path = new String[]{PATH_ENTITIES, Integer.toString(id)};

        // execute request
        final Response response = this.processRequest(request);
    }

    @Override
    public ResponseList<EntityType> getEntityTypes(final int page, final Filter... filters) {
        // build request
        final Request request = new Request();
        request.path = new String[]{PATH_ENTITY_TYPES};
        request.queryParams.put(PARAM_PAGE, Integer.toString(page));
        for (final Filter filter : filters) {
            request.queryParams.put(this.buildFilterParamName(filter), filter.getValue());
        }

        // execute request
        final Response response = this.processRequest(request);

        // process response
        if (response.code == 200) {
            return this.serializer.deserializeEntityTypes(response.content);
        }

        return null;
    }

    protected final static class Request {
        public String method = "GET";
        public String[] path = new String[0];
        public final Map<String, String> headers = new HashMap<>();
        public final Multimap<String, String> queryParams = HashMultimap.create();
        public String contentType = null;
        public String content = null;

        public Request() {
            this.headers.put(HttpHeaders.ACCEPT, APPLICATION_JSON.toString());
        }
    }

    protected final static class Response {
        public final int code;
        public final String content;

        public Response(final int code, final String content) {
            this.code = code;
            this.content = content;
        }
    }
}
