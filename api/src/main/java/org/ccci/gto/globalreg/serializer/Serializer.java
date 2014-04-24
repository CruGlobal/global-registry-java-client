package org.ccci.gto.globalreg.serializer;

import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.Type;

public interface Serializer {
    <T> String serializeEntity(final Type<T> type, final T entity);

    <T> T deserializeEntity(final Type<T> type, final String raw);

    <T> ResponseList<T> deserializeEntities(final Type<T> type, final String raw);

    ResponseList<EntityType> deserializeEntityTypes(final String raw);
}
