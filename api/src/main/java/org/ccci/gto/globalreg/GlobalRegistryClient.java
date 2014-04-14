package org.ccci.gto.globalreg;

public interface GlobalRegistryClient {
    <T> ResponseList<T> findEntities(EntityClass<T> clazz, Filter... filters);

    <T> ResponseList<T> findEntities(EntityClass<T> clazz, int page, Filter... filters);

    <T> ResponseList<T> findEntities(EntityClass<T> clazz, String createdBy, Filter... filters);

    <T> ResponseList<T> findEntities(EntityClass<T> clazz, String createdBy, int page, Filter... filters);

    <T> T getEntity(EntityClass<T> clazz, int id);
}
