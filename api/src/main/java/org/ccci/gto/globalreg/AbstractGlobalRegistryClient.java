package org.ccci.gto.globalreg;

import org.ccci.gto.globalreg.serializer.Serializer;

public abstract class AbstractGlobalRegistryClient implements GlobalRegistryClient {
    public static final String DEFAULT_CREATED_BY = null;
    public static final int DEFAULT_PAGE = 1;

    protected Serializer serializer;

    public void setSerializer(final Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public final <T> ResponseList<T> findEntities(final EntityClass<T> clazz, final Filter... filters) {
        return this.findEntities(clazz, DEFAULT_CREATED_BY, DEFAULT_PAGE, filters);
    }

    @Override
    public final <T> ResponseList<T> findEntities(final EntityClass<T> clazz, final int page, final Filter... filters) {
        return this.findEntities(clazz, DEFAULT_CREATED_BY, page, filters);
    }

    @Override
    public final <T> ResponseList<T> findEntities(final EntityClass<T> clazz, final String createdBy, final Filter... filters) {
        return this.findEntities(clazz, createdBy, DEFAULT_PAGE, filters);
    }
}
