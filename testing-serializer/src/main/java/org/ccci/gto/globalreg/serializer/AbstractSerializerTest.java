package org.ccci.gto.globalreg.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.TestUtils;
import org.ccci.gto.globalreg.Type;

public abstract class AbstractSerializerTest {
    protected final Serializer serializer;

    protected AbstractSerializerTest(final Serializer serializer) {
        this.serializer = serializer;
    }

    protected <T> T testDeserializeEntity(final Type<T> type) throws Exception {
        final T entity = this.serializer.deserializeEntity(type, TestUtils.loadResource(AbstractSerializerTest.class,
                "entity.json"));
        assertNotNull(entity);
        return entity;
    }

    protected <T> ResponseList<T> testDeserializeEntities(final Type<T> type) throws Exception {
        final ResponseList<T> entities = this.serializer.deserializeEntities(type, TestUtils.loadResource
                (AbstractSerializerTest.class, "entitieslist.json"));

        // validate meta-data
        final ResponseList.Meta meta = entities.getMeta();
        assertEquals(6, entities.size());
        assertEquals(60, meta.getTotal());
        assertEquals(7, meta.getFrom());
        assertEquals(12, meta.getTo());
        assertEquals(2, meta.getPage());
        assertEquals(10, meta.getTotalPages());

        // return the parsed entities list
        return entities;
    }
}
