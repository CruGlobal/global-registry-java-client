package org.ccci.gto.globalreg.serializer;

import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.Type;

public interface Serializer {
    <T> T parseEntity(final Type<T> type, final String raw);

    <T> ResponseList<T> parseEntitiesList(final Type<T> type, final String raw);

    <T> String fromObject(final Type<T> type, final T object);
}
