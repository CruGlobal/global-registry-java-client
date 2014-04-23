package org.ccci.gto.globalreg;

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
    public final <T> T getEntity(Type<T> type, int id) {
        return this.getEntity(type, id, DEFAULT_CREATED_BY);
    }
}
