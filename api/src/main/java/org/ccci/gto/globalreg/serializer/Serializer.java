package org.ccci.gto.globalreg.serializer;

import org.ccci.gto.globalreg.EntityClass;

public interface Serializer {
    <T> T toObject(final EntityClass<T> clazz, final String raw);

    <T> String fromObject(final EntityClass<T> clazz, final T object);
}
