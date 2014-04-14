package org.ccci.gto.globalreg.serializer;

import org.ccci.gto.globalreg.EntityClass;

public abstract class AbstractSerializer implements Serializer {
    @Override
    public final <T> T toObject(final EntityClass<T> clazz, final String raw) {
        return this.toObject(clazz.getEntityClass(), raw);
    }

    @Override
    public final <T> String fromObject(EntityClass<T> clazz, T object) {
        return this.fromObject(clazz.getEntityClass(), object);
    }
}
