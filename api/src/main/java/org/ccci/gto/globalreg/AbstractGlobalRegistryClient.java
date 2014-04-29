package org.ccci.gto.globalreg;

public abstract class AbstractGlobalRegistryClient implements GlobalRegistryClient {
    public static final String DEFAULT_CREATED_BY = null;
    public static final int DEFAULT_PAGE = 1;

    @Override
    public final <T> T getEntity(final Type<T> type, final int id) throws UnauthorizedException {
        return this.getEntity(type, id, DEFAULT_CREATED_BY);
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final Filter... filters) throws
            UnauthorizedException {
        return this.getEntities(type, DEFAULT_CREATED_BY, DEFAULT_PAGE, filters);
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final int page,
                                                 final Filter... filters) throws UnauthorizedException {
        return this.getEntities(type, DEFAULT_CREATED_BY, page, filters);
    }

    @Override
    public final <T> ResponseList<T> getEntities(final Type<T> type, final String createdBy, final Filter... filters) throws UnauthorizedException {
        return this.getEntities(type, createdBy, DEFAULT_PAGE, filters);
    }

    @Override
    public final ResponseList<EntityType> getEntityTypes(final Filter... filters) throws UnauthorizedException {
        return this.getEntityTypes(1, filters);
    }
}
