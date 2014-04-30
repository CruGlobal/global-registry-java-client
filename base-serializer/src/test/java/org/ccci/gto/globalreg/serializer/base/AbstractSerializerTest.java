package org.ccci.gto.globalreg.serializer.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.common.io.CharStreams;
import com.jayway.restassured.path.json.JsonPath;
import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.MeasurementType;
import org.ccci.gto.globalreg.RegisteredSystem;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.Type;
import org.ccci.gto.globalreg.serializer.Serializer;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public abstract class AbstractSerializerTest {
    protected final Serializer serializer;

    protected AbstractSerializerTest(final Serializer serializer) {
        this.serializer = serializer;
    }

    private String loadResource(final String name) throws IOException {
        try (final InputStreamReader in = new InputStreamReader(AbstractSerializerTest.class.getResourceAsStream
                (name), StandardCharsets.UTF_8)) {
            return CharStreams.toString(in);
        }
    }

    protected <T> T testDeserializeEntity(final Type<T> type) throws Exception {
        final T entity = this.serializer.deserializeEntity(type, loadResource("entity.json"));
        assertNotNull(entity);
        return entity;
    }

    protected <T> ResponseList<T> testDeserializeEntities(final Type<T> type) throws Exception {
        final ResponseList<T> entities = this.serializer.deserializeEntities(type, loadResource("entities.json"));

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
    public void testDeserializeEntityType() throws Exception {
        final EntityType type = this.serializer.deserializeEntityType(loadResource("entitytype.json"));

        // validate returned EntityType
        assertNotNull(type);
        assertNull(type.getId());
        assertEquals(EntityType.FieldType.STRING, type.getFieldType());
        assertEquals("favourite_colour", type.getName());
        assertTrue(type.hasParent());
        assertEquals(1, (int) type.getParentId());
    }

    @Test
    public void testDeserializeEntityTypes() throws Exception {
        final ResponseList<EntityType> types = this.serializer.deserializeEntityTypes(loadResource("entitytypes.json"));

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
        assertEquals(EntityType.FieldType.ENTITY, person.getFieldType());
        assertEquals(42, person.getFields().size());
        final EntityType auth = person.getField("authentication");
        assertEquals(345, (int) auth.getId());
        assertEquals(299, (int) auth.getParentId());
        assertEquals(person, auth.getParent());
        assertEquals("authentication", auth.getName());
        assertEquals(EntityType.FieldType.ENTITY, auth.getFieldType());
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
        for (final EntityType.FieldType fieldType : EntityType.FieldType.values()) {
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
                    if (fieldType != EntityType.FieldType.UNKNOWN && fieldType != EntityType.FieldType.NONE) {
                        assertEquals(fieldType.toString(), json.getString("field_type"));
                    } else {
                        assertNull(json.get("field_type"));
                    }
                }
            }
        }
    }

    @Test
    public void testDeserializeSystem() throws Exception {
        final RegisteredSystem system = this.serializer.deserializeSystem(loadResource("system.json"));

        // validate system object
        assertNotNull(system);
        assertEquals(7, (long) system.getId());
        assertEquals("System 1", system.getName());
        assertEquals(Boolean.TRUE, system.getRoot());
        assertTrue(system.isRoot());
        assertEquals(Boolean.FALSE, system.getTrusted());
        assertFalse(system.isTrusted());
        assertEquals("accessToken", system.getAccessToken());
    }

    @Test
    public void testDeserializeSystems() throws Exception {
        final List<RegisteredSystem> systems = this.serializer.deserializeSystems(loadResource("systems.json"));

        assertNotNull(systems);
        assertEquals(3, systems.size());
        {
            final RegisteredSystem system = systems.get(0);
            assertNotNull(system);
            assertEquals(1, (long) system.getId());
            assertEquals("System 1", system.getName());
            assertEquals(Boolean.FALSE, system.getRoot());
            assertFalse(system.isRoot());
            assertEquals(Boolean.FALSE, system.getTrusted());
            assertFalse(system.isTrusted());
            assertEquals("token_1", system.getAccessToken());
        }
        {
            final RegisteredSystem system = systems.get(1);
            assertNotNull(system);
            assertEquals(2, (long) system.getId());
            assertEquals("System 2", system.getName());
            assertEquals(Boolean.TRUE, system.getRoot());
            assertTrue(system.isRoot());
            assertEquals(Boolean.TRUE, system.getTrusted());
            assertTrue(system.isTrusted());
            assertEquals("token_2", system.getAccessToken());
        }
        {
            final RegisteredSystem system = systems.get(2);
            assertNotNull(system);
            assertEquals(10, (long) system.getId());
            assertEquals("System 10", system.getName());
            assertNull(system.getRoot());
            assertFalse(system.isRoot());
            assertNull(system.getTrusted());
            assertFalse(system.isTrusted());
            assertNull(system.getAccessToken());
        }
    }

    @Test
    public void testDeserializeMeasurementType() throws Exception {
        final MeasurementType type = this.serializer.deserializeMeasurementType(loadResource("measurementtype.json"));

        assertNotNull(type);
        assertEquals(5, (long) type.getId());
        assertEquals("Expenses", type.getName());
        assertEquals("Expenses (turnover)", type.getDescription());
        assertEquals(MeasurementType.Category.FINANCE, type.getCategory());
        assertEquals(MeasurementType.Frequency.MONTHLY, type.getFrequency());
        assertEquals("Percentage", type.getUnit());
        assertEquals(299, (long) type.getRelatedEntityType());
    }
}
