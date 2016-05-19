package org.ccci.gto.globalreg.serializer;

import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.MeasurementType;
import org.ccci.gto.globalreg.RegisteredSystem;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.Type;

import javax.annotation.Nonnull;
import java.util.List;

public interface Serializer {
    /* Entity serialization methods */

    <T> String serializeEntity(final Type<T> type, final T entity) throws SerializerException;

    <T> T deserializeEntity(@Nonnull final Type<T> type, final String raw) throws SerializerException;

    @Nonnull
    <T> ResponseList<T> deserializeEntities(final Type<T> type, final String raw) throws SerializerException;

    /* EntityType serialization methods */

    String serializeEntityType(@Nonnull final EntityType type) throws SerializerException;

    @Nonnull
    EntityType deserializeEntityType(final String raw) throws SerializerException;

    @Nonnull
    ResponseList<EntityType> deserializeEntityTypes(final String raw) throws SerializerException;

    /* System serialization methods */

    /**
     * Deserialize a JSON string to a System object.
     *
     * @param raw the raw System JSON
     * @return the deserialized System object
     * @throws SerializerException Thrown when there is an error deserializing the provided JSON
     */
    @Nonnull
    RegisteredSystem deserializeSystem(final String raw) throws SerializerException;

    @Nonnull
    List<RegisteredSystem> deserializeSystems(final String raw) throws SerializerException;

    /* Measurement serialization methods */

    MeasurementType deserializeMeasurementType(final String raw) throws SerializerException;

    ResponseList<MeasurementType> deserializeMeasurementTypes(final String raw) throws SerializerException;
}
