package org.ccci.gto.globalreg;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.ccci.gto.globalreg.serializer.Serializer;

public abstract class AbstractGlobalRegistryClient implements GlobalRegistryClient {
    public static final String DEFAULT_CREATED_BY = null;
    public static final int DEFAULT_PAGE = 1;

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
    public final <T> T getEntity(Type<T> type, int id) {
        return this.getEntity(type, id, DEFAULT_CREATED_BY);
    }

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
    public final <T> ResponseList<T> getEntities(final Type<T> type, final Filter... filters) {
        return this.getEntities(type, DEFAULT_CREATED_BY, DEFAULT_PAGE, filters);
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final int page, final Filter... filters) {
        return this.getEntities(type, DEFAULT_CREATED_BY, page, filters);
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final String createdBy, final Filter... filters) {
        return this.getEntities(type, createdBy, DEFAULT_PAGE, filters);
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
    public final ResponseList<EntityType> getEntityTypes(final Filter... filters) {
        return this.getEntityTypes(1, filters);
    }

    protected final static class Request {
        public String method = "GET";
        public String[] path = new String[0];
        public final Multimap<String, String> queryParams = HashMultimap.create();
        public String content = null;

        public Request() {}
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
