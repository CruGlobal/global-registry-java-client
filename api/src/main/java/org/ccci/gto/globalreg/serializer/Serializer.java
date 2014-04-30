package org.ccci.gto.globalreg.serializer;

import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.System;
import org.ccci.gto.globalreg.Type;

import java.util.List;

public interface Serializer {
    /* Entity serialization methods */

    <T> String serializeEntity(final Type<T> type, final T entity);

    <T> T deserializeEntity(final Type<T> type, final String raw) throws SerializerException;

    <T> ResponseList<T> deserializeEntities(final Type<T> type, final String raw) throws SerializerException;

    /* EntityType serialization methods */

    String serializeEntityType(final EntityType type);

    EntityType deserializeEntityType(final String raw);

    ResponseList<EntityType> deserializeEntityTypes(final String raw);

    /* System serialization methods */

    /**
     * Deserialize a JSON string to a System object.
     *
     * @param raw the raw System JSON
     * @return the deserialized System object
     * @throws SerializerException Thrown when there is an error deserializing the provided JSON
     */
    System deserializeSystem(final String raw) throws SerializerException;

    List<System> deserializeSystems(final String raw) throws SerializerException;
}
