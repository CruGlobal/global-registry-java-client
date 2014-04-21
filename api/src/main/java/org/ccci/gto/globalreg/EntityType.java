package org.ccci.gto.globalreg;

public class EntityType<T> {
    private final Class<? extends T> clazz;
    private final String type;

    public EntityType(final Class<? extends T> clazz, final String type) {
        this.clazz = clazz;
        this.type = type;
    }

    public Class<? extends T> getEntityClass() {
        return this.clazz;
    }

    public String getEntityType() {
        return this.type;
    }
}
