package org.ccci.gto.globalreg.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.jayway.restassured.path.json.JsonPath;
import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.TestUtils;
import org.ccci.gto.globalreg.Type;
import org.ccci.gto.globalreg.util.ArrayUtil;
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
        final ResponseList<T> entities = this.serializer.deserializeEntities(type,
                TestUtils.loadResource(AbstractSerializerTest.class, "entities.json"));

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
        assertEquals(392, (int) ministry.getId());
        assertEquals("ministry_scope", ministry.getName());
        assertEquals(EntityType.FieldType.ENUM_VALUES, ministry.getFieldType());
        assertEquals("Root level ministry scope entity type to store enum values", ministry.getDescription());
        assertEquals(0, ministry.getFields().size());

        // validate second entity_type (person)
        final EntityType person = types.get(1);
        assertEquals(299, (int) person.getId());
        assertEquals("person", person.getName());
        assertNull(person.getFieldType());
        assertEquals(42, person.getFields().size());
        final EntityType auth = person.getField("authentication");
        assertEquals(345, (int) auth.getId());
        assertEquals(299, (int) auth.getParentId());
        assertEquals(person, auth.getParent());
        assertEquals("authentication", auth.getName());
        assertNull(person.getFieldType());
        assertEquals(4, auth.getFields().size());
    }

    protected <T> void testSerializeEntity(final Type<T> type, final T entity) throws Exception {
        final String raw = this.serializer.serializeEntity(type, entity);
        final JsonPath json = new JsonPath(raw);
        json.setRoot("entity." + type.getEntityType());
        assertEquals("Bobby", json.getString("first_name"));
        assertEquals("Tables", json.getString("last_name"));
    }

    @Test
    public void testSerializeEntityType() throws Exception {
        for (final EntityType.FieldType fieldType : ArrayUtil.merge(EntityType.FieldType.values(),
                (EntityType.FieldType) null)) {
            for (final Integer parentId : new Integer[]{null, 1}) {
                for (final String name : new String[]{"test_name"}) {
                    // create test entity type
                    final EntityType type = new EntityType();
                    type.setParentId(parentId);
                    type.setFieldType(fieldType);
                    type.setName(name);
                    type.setDescription("test_description");

                    // test entity type serialization
                    final String raw = this.serializer.serializeEntityType(type);
                    final JsonPath json = new JsonPath(raw);
                    json.setRoot("entity_type");
                    assertEquals(name, json.getString("name"));
                    assertEquals("test_description", json.getString("description"));
                    if (parentId != null) {
                        assertEquals((int) parentId, json.getInt("parent_id"));
                    } else {
                        assertNull(json.get("parent_id"));
                    }
                    if (fieldType != null) {
                        assertEquals(fieldType.toString(), json.getString("field_type"));
                    } else {
                        assertNull(json.get("field_type"));
                    }
                }
            }
        }
    }
}
