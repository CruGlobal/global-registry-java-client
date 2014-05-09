package org.ccci.gto.globalreg;

import org.ccci.gto.globalreg.serializer.SerializerException;
import org.joda.time.ReadableInstant;

import java.util.List;

public interface GlobalRegistryClient {
    public static final String PATH_ENTITIES = "entities";
    public static final String PATH_ENTITY_TYPES = "entity_types";
    public static final String PATH_MEASUREMENT_TYPES = "measurement_types";
    public static final String PATH_SYSTEMS = "systems";
    public static final String PARAM_CREATED_BY = "created_by";
    public static final String PARAM_ENTITY_TYPE = "entity_type";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_FILTER = "filters";

    /* Entity Endpoints */

    /**
     * Retrieve an entity from the Global Registry
     *
     * @param type the type of entity to retrieve
     * @param id   the id of the entity being retrieved
     * @return The entity that is stored in the Global Registry
     * @throws UnauthorizedException Thrown when the request is unauthorized.
     * @throws SerializerException   Thrown when there was an exception with entity deserialization.
     */
    <T> T getEntity(Type<T> type, String id) throws GlobalRegistryException;

    /**
     * Retrieve an entity from the Global Registry
     *
     * @param type      the type of entity to retrieve
     * @param id        the id of the entity being retrieved
     * @param createdBy the system id the entity is being retrieved for
     * @return The entity that is stored in the Global Registry
     * @throws UnauthorizedException Thrown when the request is unauthorized.
     * @throws SerializerException   Thrown when there was an exception with entity deserialization.
     */
    <T> T getEntity(Type<T> type, String id, String createdBy) throws SerializerException, UnauthorizedException;

    <T> ResponseList<T> getEntities(Type<T> type, Filter... filters) throws UnauthorizedException, SerializerException;

    <T> ResponseList<T> getEntities(Type<T> type, int page, Filter... filters) throws UnauthorizedException, SerializerException;

    <T> ResponseList<T> getEntities(Type<T> type, String createdBy, Filter... filters) throws UnauthorizedException, SerializerException;

    <T> ResponseList<T> getEntities(Type<T> type, String createdBy, int page,
                                    Filter... filters) throws UnauthorizedException, SerializerException;

    /**
     * Store an entity in the Global Registry
     *
     * @param type   The type of entity to add
     * @param entity The actual entity to store in the Global Registry
     * @return the entity stored in the Global Registry
     * @throws UnauthorizedException Thrown when the request is unauthorized.
     */
    <T> T addEntity(Type<T> type, T entity) throws UnauthorizedException, SerializerException;

    <T> T updateEntity(Type<T> type, String id, T entity) throws UnauthorizedException, SerializerException;

    <T> void deleteEntity(Type<T> type, String id) throws UnauthorizedException;

    /* Entity Type Endpoints */

    ResponseList<EntityType> getEntityTypes(Filter... filters) throws UnauthorizedException, SerializerException;

    ResponseList<EntityType> getEntityTypes(int page, Filter... filters) throws UnauthorizedException, SerializerException;

    EntityType addEntityType(EntityType type) throws UnauthorizedException, SerializerException;

    /* System Endpoints */

    RegisteredSystem getSystem(String id) throws SerializerException, UnauthorizedException;

    List<RegisteredSystem> getSystems() throws UnauthorizedException, SerializerException;

    /* Measurement Endpoints */

    ResponseList<MeasurementType> getMeasurementTypes(Filter... filters) throws UnauthorizedException,
            SerializerException;

    ResponseList<MeasurementType> getMeasurementTypes(int page, Filter... filters) throws UnauthorizedException,
            SerializerException;

    MeasurementType getMeasurementType(String id, Filter... filters) throws UnauthorizedException, SerializerException;

    List<Measurement> getMeasurements(MeasurementType type, ReadableInstant from, ReadableInstant to,
                                      Filter... filters) throws UnauthorizedException, SerializerException;

    List<Measurement> getMeasurements(String type, ReadableInstant from, ReadableInstant to,
                                      Filter... filters) throws UnauthorizedException, SerializerException;

    List<Measurement> getMeasurements(MeasurementType type, Filter... filters) throws UnauthorizedException,
            SerializerException;

    List<Measurement> getMeasurements(String type, Filter... filters) throws UnauthorizedException, SerializerException;
}
