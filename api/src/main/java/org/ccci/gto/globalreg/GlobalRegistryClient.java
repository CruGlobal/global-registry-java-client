package org.ccci.gto.globalreg;

public interface GlobalRegistryClient {
    public static final String PATH_ENTITIES = "entities";
    public static final String PARAM_CREATED_BY = "created_by";
    public static final String PARAM_ENTITY_TYPE = "entity_type";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_FILTER = "filters";

    <T> ResponseList<T> findEntities(EntityType<T> type, Filter... filters);

    <T> ResponseList<T> findEntities(EntityType<T> type, int page, Filter... filters);

    <T> ResponseList<T> findEntities(EntityType<T> type, String createdBy, Filter... filters);

    <T> ResponseList<T> findEntities(EntityType<T> type, String createdBy, int page, Filter... filters);

    <T> T getEntity(EntityType<T> type, int id);

    <T> T getEntity(EntityType<T> type, int id, String createdBy);
}
