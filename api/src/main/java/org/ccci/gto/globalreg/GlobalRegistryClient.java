package org.ccci.gto.globalreg;

public interface GlobalRegistryClient {
    public static final String PATH_ENTITIES = "entities";
    public static final String PATH_ENTITY_TYPES = "entity_types";
    public static final String PARAM_CREATED_BY = "created_by";
    public static final String PARAM_ENTITY_TYPE = "entity_type";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_FILTER = "filters";

    /* Entity Endpoints */

    <T> T getEntity(Type<T> type, int id) throws UnauthorizedException;

    <T> T getEntity(Type<T> type, int id, String createdBy) throws UnauthorizedException;

    <T> ResponseList<T> getEntities(Type<T> type, Filter... filters) throws UnauthorizedException;

    <T> ResponseList<T> getEntities(Type<T> type, int page, Filter... filters) throws UnauthorizedException;

    <T> ResponseList<T> getEntities(Type<T> type, String createdBy, Filter... filters) throws UnauthorizedException;

    <T> ResponseList<T> getEntities(Type<T> type, String createdBy, int page,
                                    Filter... filters) throws UnauthorizedException;

    <T> T addEntity(Type<T> type, T entity) throws UnauthorizedException;

    <T> T updateEntity(Type<T> type, int id, T entity) throws UnauthorizedException;

    <T> void deleteEntity(Type<T> type, int id) throws UnauthorizedException;

    /* Entity Type Endpoints */

    ResponseList<EntityType> getEntityTypes(Filter... filters) throws UnauthorizedException;

    ResponseList<EntityType> getEntityTypes(int page, Filter... filters) throws UnauthorizedException;

    EntityType addEntityType(EntityType type) throws UnauthorizedException;
}
