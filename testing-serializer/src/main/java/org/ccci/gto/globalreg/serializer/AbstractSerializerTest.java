package org.ccci.gto.globalreg.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.TestUtils;

public abstract class AbstractSerializerTest {
    protected final Serializer serializer;

    protected AbstractSerializerTest(final Serializer serializer) {
        this.serializer = serializer;
    }

    protected <T> T testParseEntity(final EntityType<T> type) throws Exception {
        final T entity = this.serializer.parseEntity(type, TestUtils.loadResource(AbstractSerializerTest.class,
                "entity.json"));
        assertNotNull(entity);
        return entity;
    }

    protected <T> ResponseList<T> testParseEntitiesList(final EntityType<T> type) throws Exception {
        final ResponseList<T> entities = this.serializer.parseEntitiesList(type,
                TestUtils.loadResource(AbstractSerializerTest.class, "entitieslist.json"));

        // validate meta-data
        final ResponseList.Meta meta = entities.getMeta();
        assertEquals(6, entities.size());
        assertEquals(60, meta.getTotal());
        assertEquals(7, meta.getFrom());
        assertEquals(12, meta.getTo());
        assertEquals(10, meta.getTotalPages());

        // return the parsed entities list
        return entities;
    }
}
