package org.ccci.gto.globalreg;

public class EntityClass<T> {
    private final Class<T> clazz;
    private final String type;

    public EntityClass(final Class<T> clazz, final String type) {
        this.clazz = clazz;
        this.type = type;
    }

    public Class<T> getEntityClass() {
        return this.clazz;
    }

    public String getEntityType() {
        return this.type;
    }

    public String getType() {
        return this.type;
    }
}
