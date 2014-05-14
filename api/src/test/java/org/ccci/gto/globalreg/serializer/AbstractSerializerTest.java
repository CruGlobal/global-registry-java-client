package org.ccci.gto.globalreg.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.common.io.CharStreams;
import com.jayway.restassured.path.json.JsonPath;
import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.Measurement;
import org.ccci.gto.globalreg.MeasurementType;
import org.ccci.gto.globalreg.RegisteredSystem;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.Type;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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
        assertEquals("97d1e40e-2557-11e3-8ea8-12725f8f377c", type.getId());
        assertEquals(EntityType.FieldType.STRING, type.getFieldType());
        assertEquals("favourite_colour", type.getName());

        // XXX: disable parent tests while there isn't a parent_id being returned
//        assertTrue(type.hasParent());
//        assertEquals(1, (long) type.getParentId());
    }

    @Test
    public void testDeserializeEntityTypes() throws Exception {
        final ResponseList<EntityType> types = this.serializer.deserializeEntityTypes(loadResource("entitytypes.json"));

        // validate list size & meta
        assertNotNull(types);
        final ResponseList.Meta meta = types.getMeta();
        assertEquals(3, types.size());
        assertEquals(10, meta.getTotal());
        assertEquals(7, meta.getFrom());
        assertEquals(9, meta.getTo());
        assertEquals(3, meta.getPage());
        assertEquals(4, meta.getTotalPages());

        // validate first entity_type (gender)
        final EntityType gender = types.get(0);
        assertEquals("6ee78926-d558-11e3-b071-12725f8f377c", gender.getId());
        assertEquals("gender", gender.getName());
        assertEquals(EntityType.FieldType.ENUM_VALUES, gender.getFieldType());
        assertEquals("Root level gender entity type to store enum values", gender.getDescription());
        assertEquals(0, gender.getFields().size());
        final List<String> genderValues = gender.getEnumValues();
        assertNotNull(genderValues);
        assertEquals(2, genderValues.size());
        assertTrue("gender doesn't contains Male", genderValues.contains("Male"));
        assertTrue("gender doesn't contains Female", genderValues.contains("Female"));
        assertFalse("gender contains Alien", genderValues.contains("Alien"));

        // validate second entity_type (person)
        final EntityType person = types.get(1);
        assertEquals("882af9d8-d556-11e3-9b12-12725f8f377c", person.getId());
        assertEquals("person", person.getName());
        assertEquals(EntityType.FieldType.ENTITY, person.getFieldType());
        assertEquals(43, person.getFields().size());
        final EntityType auth = person.getField("authentication");
        assertEquals("2c81cdea-d557-11e3-b111-12725f8f377c", auth.getId());
        assertEquals("882af9d8-d556-11e3-9b12-12725f8f377c", auth.getParentId());
        assertEquals(person, auth.getParent());
        assertEquals("authentication", auth.getName());
        assertEquals(EntityType.FieldType.ENTITY, auth.getFieldType());
        assertEquals(5, auth.getFields().size());

        // validate person relationship_type
        final List<EntityType.RelationshipType> relationships = person.getRelationshipTypes();
        assertNotNull(relationships);
        assertEquals("Invalid number of relationships for a person entity type", 2, relationships.size());
        {
            final EntityType.RelationshipType relationship = relationships.get(0);
            assertNotNull(relationship);
            assertEquals("7cd27938-d558-11e3-868a-12725f8f377c", relationship.getId());
            assertEquals("6c79a444-d558-11e3-908c-12725f8f377c", relationship.getEnumEntityTypeId());
            assertFalse(relationship.isReflexive());
            final EntityType.RelationshipType.Relationship local = relationship.getLocalRelationship();
            assertNotNull(local);
            assertEquals("person", local.getEntityType());
            assertEquals("person", local.getName());
            final EntityType.RelationshipType.Relationship target = relationship.getTargetRelationship();
            assertNotNull(target);
            assertEquals("ministry", target.getEntityType());
            assertEquals("ministry", target.getName());
        }
        {
            final EntityType.RelationshipType relationship = relationships.get(1);
            assertNotNull(relationship);
            assertEquals("7cf075aa-d558-11e3-b33d-12725f8f377c", relationship.getId());
            assertNull(relationship.getEnumEntityTypeId());
            assertTrue(relationship.isReflexive());

            //TODO: these can be in either order because it's reflexive
            final EntityType.RelationshipType.Relationship local = relationship.getLocalRelationship();
            assertNotNull(local);
            assertEquals("person", local.getEntityType());
            assertEquals("husband", local.getName());
            final EntityType.RelationshipType.Relationship target = relationship.getTargetRelationship();
            assertNotNull(target);
            assertEquals("person", target.getEntityType());
            assertEquals("wife", target.getName());
        }
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
            for (final String parentId : new String[]{null, "2c82fd82-d557-11e3-b6ec-12725f8f377c",
                    "6d591d90-d558-11e3-ab96-12725f8f377c"}) {
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
                        assertEquals(parentId, json.getString("parent_id"));
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
        assertEquals("a6c9c498-5687-11e3-a202-12725f8f377c", system.getId());
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
            assertEquals("a6c9c498-d554-11e3-2456-12725f8f377c", system.getId());
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
            assertEquals("a6c9cb96-4568-11e3-98ab-12725f8f377c", system.getId());
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
            assertEquals("a6c9cb96-d554-9999-98ab-12725f8f377c", system.getId());
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
        assertEquals("59ece820-d55a-11e3-1305-12725f8f377c", type.getId());
        assertEquals("Graduating on Mission", type.getName());
        assertEquals("Descript", type.getDescription());
        assertEquals(MeasurementType.Category.LMI, type.getCategory());
        assertEquals(MeasurementType.Frequency.MONTHLY, type.getFrequency());
        assertEquals("people", type.getUnit());
        assertEquals("59ece820-bbbb-aaaa-b305-12725f8f377c", type.getRelatedEntityType());

        // test deserialized measurements
        final List<Measurement> measurements = type.getMeasurements();
        assertEquals(7, measurements.size());
        {
            final Measurement measurement = measurements.get(0);
            assertEquals(type, measurement.getType());
            assertEquals(type.getId(), measurement.getTypeId());
            assertEquals("59ece820-d55a-11f3-b305-12725f8f377c", measurement.getId());
            assertNotNull(measurement.getPeriod());
            assertEquals(new DateTime().withZone(DateTimeZone.UTC).withDate(2006, 10, 1).withTimeAtStartOfDay(),
                    measurement.getPeriod().getStart());
            assertEquals(new DateTime().withZone(DateTimeZone.UTC).withDate(2006, 11, 1).withTimeAtStartOfDay(),
                    measurement.getPeriod().getEnd());
            assertEquals(1.0, measurement.getValue(), 0.001);
            assertEquals("59ece820-bbbb-11e3-b305-12725f8f377c", measurement.getRelatedEntityId());
        }
        {
            final Measurement measurement = measurements.get(3);
            assertEquals(type, measurement.getType());
            assertEquals(type.getId(), measurement.getTypeId());
            assertEquals("59ece820-d55a-11e3-b105-12725f8f377c", measurement.getId());
            assertNotNull(measurement.getPeriod());
            assertEquals(new DateTime().withZone(DateTimeZone.UTC).withDate(2007, 11, 1).withTimeAtStartOfDay(),
                    measurement.getPeriod().getStart());
            assertEquals(new DateTime().withZone(DateTimeZone.UTC).withDate(2007, 12, 1).withTimeAtStartOfDay(),
                    measurement.getPeriod().getEnd());
            assertEquals(3.0, measurement.getValue(), 0.001);
            assertEquals("59ece820-bbbb-11e3-b305-12725f8f377c", measurement.getRelatedEntityId());
        }
        {
            final Measurement measurement = measurements.get(6);
            assertEquals(type, measurement.getType());
            assertEquals(type.getId(), measurement.getTypeId());
            assertEquals("59ece820-123a-11e3-b305-12725f8f377c", measurement.getId());
            assertNotNull(measurement.getPeriod());
            assertEquals(new DateTime().withZone(DateTimeZone.UTC).withDate(2013, 4, 1).withTimeAtStartOfDay(),
                    measurement.getPeriod().getStart());
            assertEquals(new DateTime().withZone(DateTimeZone.UTC).withDate(2013, 5, 1).withTimeAtStartOfDay(),
                    measurement.getPeriod().getEnd());
            assertEquals(1.0, measurement.getValue(), 0.001);
            assertEquals("59ece820-bbbb-11e3-b305-12725f8f377c", measurement.getRelatedEntityId());
        }
    }

    @Test
    public void testDeserializeMeasurementTypes() throws Exception {
        final ResponseList<MeasurementType> types = this.serializer.deserializeMeasurementTypes(loadResource
                ("measurementtypes.json"));

        // validate list size & meta
        assertNotNull(types);
        final ResponseList.Meta meta = types.getMeta();
        assertEquals(5, types.size());
        assertEquals(43, meta.getTotal());
        assertEquals(16, meta.getFrom());
        assertEquals(20, meta.getTo());
        assertEquals(4, meta.getPage());
        assertEquals(9, meta.getTotalPages());

        // validate first measurement type
        {
            final MeasurementType type = types.get(0);
            assertEquals("59d002fa-d55a-11e3-8ffc-12725f8f377c", type.getId());
            assertEquals("Ministry-subsidy", type.getName());
            assertEquals("Percentage of income from subsidy", type.getDescription());
            assertEquals(MeasurementType.Category.MPD, type.getCategory());
            assertEquals(MeasurementType.Frequency.MONTHLY, type.getFrequency());
            assertEquals("%", type.getUnit());
            assertEquals("a5499c9a-d556-11e3-af5a-12725f8f377c", type.getRelatedEntityType());
        }

        // validate fourth measurement type
        {
            final MeasurementType type = types.get(3);
            assertEquals("5a2389ca-d55a-11e3-858c-12725f8f377c", type.getId());
            assertEquals("Media Exposures", type.getName());
            assertEquals("", type.getDescription());
            assertEquals(MeasurementType.Category.LMI, type.getCategory());
            assertEquals(MeasurementType.Frequency.MONTHLY, type.getFrequency());
            assertEquals("people", type.getUnit());
            assertEquals("a5499c9a-1234-11e3-af5a-12725f8f377c", type.getRelatedEntityType());
        }
    }
}
