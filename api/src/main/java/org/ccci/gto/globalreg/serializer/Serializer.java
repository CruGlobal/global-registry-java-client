package org.ccci.gto.globalreg.serializer;

import org.ccci.gto.globalreg.EntityClass;

public interface Serializer {
    <T> T toObject(final EntityClass<T> clazz, final String raw);

    <T> T toObject(final Class<T> clazz, final String raw);

    <T> String fromObject(final EntityClass<T> clazz, final T object);

    <T> String fromObject(final Class<T> clazz, final T object);
}
