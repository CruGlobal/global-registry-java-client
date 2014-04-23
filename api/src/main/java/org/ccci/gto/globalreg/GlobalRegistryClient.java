package org.ccci.gto.globalreg;

public interface GlobalRegistryClient {
    public static final String PATH_ENTITIES = "entities";
    public static final String PATH_ENTITY_TYPES = "entity_types";
    public static final String PARAM_CREATED_BY = "created_by";
    public static final String PARAM_ENTITY_TYPE = "entity_type";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_FILTER = "filters";

    <T> T getEntity(Type<T> type, int id);

    <T> T getEntity(Type<T> type, int id, String createdBy);

    <T> ResponseList<T> getEntities(Type<T> type, Filter... filters);

    <T> ResponseList<T> getEntities(Type<T> type, int page, Filter... filters);

    <T> ResponseList<T> getEntities(Type<T> type, String createdBy, Filter... filters);

    <T> ResponseList<T> getEntities(Type<T> type, String createdBy, int page, Filter... filters);

    <T> T addEntity(Type<T> type, T entity);

    <T> T updateEntity(Type<T> type, int id, T entity);

    <T> void deleteEntity(Type<T> type, int id);
}
