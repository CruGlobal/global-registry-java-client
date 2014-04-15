package org.ccci.gto.globalreg.serializer;

import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.ResponseList;

public interface Serializer {
    <T> T parseEntity(final EntityType<T> type, final String raw);

    <T> ResponseList<T> parseEntitiesList(final EntityType<T> type, final String raw);

    <T> String fromObject(final EntityType<T> type, final T object);
}
