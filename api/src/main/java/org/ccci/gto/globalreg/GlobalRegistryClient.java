package org.ccci.gto.globalreg;

import org.ccci.gto.globalreg.serializer.SerializerException;
import org.joda.time.ReadableInstant;

import java.util.List;

public interface GlobalRegistryClient {
    public static final String PATH_ENTITIES = "entities";
    public static final String PATH_ENTITY_TYPES = "entity_types";
    public static final String PATH_MEASUREMENT_TYPES = "measurement_types";
    public static final String PATH_SYSTEMS = "systems";
    public static final String PARAM_ENTITY_TYPE = "entity_type";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_PER_PAGE = "per_page";
    public static final String PARAM_FILTER = "filters";

    /* Entity Endpoints */

    /**
     * Retrieve an entity from the Global Registry
     *
     * @param type    the type of entity to retrieve
     * @param id      the id of the entity being retrieved
     * @param ownedBy the system id the entity is being retrieved for
     * @param filters any filters for the data being returned
     * @return The entity that is stored in the Global Registry, or null if it doesn't exist
     * @throws GlobalRegistryException Thrown when there is an error retrieving the entity.
     * @throws UnauthorizedException   Thrown when the request is unauthorized.
     * @throws SerializerException     Thrown when there was an exception with entity deserialization.
     */
    <T> T getEntity(Type<T> type, String id, String ownedBy, Filter... filters) throws GlobalRegistryException;

    /**
     * Retrieve an entity from the Global Registry
     *
     * @param type    the type of entity to retrieve
     * @param id      the id of the entity being retrieved
     * @param filters any filters for the data being returned
     * @return The entity that is stored in the Global Registry, or null if it doesn't exist
     * @throws GlobalRegistryException Thrown when there is an error retrieving the entity.
     * @throws UnauthorizedException   Thrown when the request is unauthorized.
     * @throws SerializerException     Thrown when there was an exception with entity deserialization.
     */
    <T> T getEntity(Type<T> type, String id, Filter... filters) throws GlobalRegistryException;

    <T> ResponseList<T> getEntities(Type<T> type, String ownedBy, Filter... filters) throws GlobalRegistryException;

    <T> ResponseList<T> getEntities(Type<T> type, String ownedBy, int page,
                                    Filter... filters) throws GlobalRegistryException;

    <T> ResponseList<T> getEntities(Type<T> type, String ownedBy, int page, int perPage,
                                    Filter... filters) throws GlobalRegistryException;

    <T> ResponseList<T> getEntities(Type<T> type, Filter... filters) throws GlobalRegistryException;

    <T> ResponseList<T> getEntities(Type<T> type, int page, Filter... filters) throws GlobalRegistryException;

    <T> ResponseList<T> getEntities(Type<T> type, int page, int perPage,
                                    Filter... filters) throws GlobalRegistryException;

    /**
     * Store an entity in the Global Registry
     *
     * @param type   The type of entity to add
     * @param entity The actual entity to store in the Global Registry
     * @return the entity stored in the Global Registry
     * @throws GlobalRegistryException Thrown when there is an error adding the entity.
     * @throws UnauthorizedException   Thrown when the request is unauthorized.
     */
    <T> T addEntity(Type<T> type, T entity) throws GlobalRegistryException;

    <T> T updateEntity(Type<T> type, String id, T entity) throws GlobalRegistryException;

    void deleteEntity(String id) throws GlobalRegistryException;

    @Deprecated
    <T> void deleteEntity(Type<T> type, String id) throws GlobalRegistryException;

    /* Entity Type Endpoints */

    ResponseList<EntityType> getEntityTypes(Filter... filters) throws GlobalRegistryException;

    ResponseList<EntityType> getEntityTypes(int page, Filter... filters) throws GlobalRegistryException;

    EntityType addEntityType(EntityType type) throws GlobalRegistryException;

    /* System Endpoints */

    RegisteredSystem getSystem(String id) throws GlobalRegistryException;

    List<RegisteredSystem> getSystems() throws GlobalRegistryException;

    /* Measurement Endpoints */

    ResponseList<MeasurementType> getMeasurementTypes(Filter... filters) throws GlobalRegistryException;

    ResponseList<MeasurementType> getMeasurementTypes(int page, Filter... filters) throws GlobalRegistryException;

    MeasurementType getMeasurementType(String id, Filter... filters) throws GlobalRegistryException;

    List<Measurement> getMeasurements(MeasurementType type, ReadableInstant from, ReadableInstant to,
                                      Filter... filters) throws GlobalRegistryException;

    List<Measurement> getMeasurements(String type, ReadableInstant from, ReadableInstant to,
                                      Filter... filters) throws GlobalRegistryException;

    List<Measurement> getMeasurements(MeasurementType type, Filter... filters) throws GlobalRegistryException;

    List<Measurement> getMeasurements(String type, Filter... filters) throws GlobalRegistryException;
}
