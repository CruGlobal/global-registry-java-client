package org.ccci.gto.globalreg.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.TestUtils;
import org.ccci.gto.globalreg.Type;
import org.junit.Test;

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

    @Test
    public void testDeserializeEntityTypes() throws Exception {
        final ResponseList<EntityType> types = this.serializer.deserializeEntityTypes(TestUtils.loadResource
                (AbstractSerializerTest.class, "entitytypes.json"));

        // validate list size & meta
        assertNotNull(types);
        final ResponseList.Meta meta = types.getMeta();
        assertEquals(3, types.size());
        assertEquals(16, meta.getTotal());
        assertEquals(7, meta.getFrom());
        assertEquals(9, meta.getTo());
        assertEquals(3, meta.getPage());
        assertEquals(6, meta.getTotalPages());

        // validate first entity_type (ministry_scope)
        final EntityType ministry = types.get(0);
        assertEquals(392, ministry.getId());
        assertEquals("ministry_scope", ministry.getName());
        assertEquals(EntityType.FieldType.ENUM_VALUES, ministry.getFieldType());
        assertEquals("Root level ministry scope entity type to store enum values", ministry.getDescription());
        assertEquals(0, ministry.getFields().size());

        // validate second entity_type (person)
        final EntityType person = types.get(1);
        assertEquals(299, person.getId());
        assertEquals("person", person.getName());
        assertNull(person.getFieldType());
        assertEquals(42, person.getFields().size());
        final EntityType auth = person.getFields().get(5);
        assertEquals(345, auth.getId());
        assertEquals("authentication", auth.getName());
        assertNull(person.getFieldType());
        assertEquals(4, auth.getFields().size());
    }
}
