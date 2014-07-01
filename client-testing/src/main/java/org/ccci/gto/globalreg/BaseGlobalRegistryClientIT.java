package org.ccci.gto.globalreg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;

import org.ccci.gto.globalreg.serializer.json.JSONObjectType;
import org.ccci.gto.globalreg.serializer.json.JsonSerializer;
import org.json.JSONObject;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public abstract class BaseGlobalRegistryClientIT {
    private static final String ACCESS_TOKEN = "";

    private static final Random RAND = new SecureRandom();

    protected static final JSONObjectType TYPE_PERSON = new JSONObjectType("person");

    protected abstract BaseGlobalRegistryClient newClient();

    protected BaseGlobalRegistryClient getClient() {
        // dont create client if we don't have an access token
        if (ACCESS_TOKEN == null || "".equals(ACCESS_TOKEN)) {
            return null;
        }

        final BaseGlobalRegistryClient client = this.newClient();
        client.setApiUrl("http://gr.stage.uscm.org");
        client.setAccessToken(ACCESS_TOKEN);
        client.setSerializer(new JsonSerializer());
        return client;
    }

    @Test
    public void testGetEntity() throws Exception {
        final GlobalRegistryClient client = this.getClient();
        assumeNotNull(client);

        final JSONObject entity = client.getEntity(TYPE_PERSON, "ed4442da-00ca-11e4-9830-12725f8f377c",
                "4e9541b8-e02a-11e3-a00d-12725f8f377c");

        assertNotNull(entity);
        assertEquals("ed4442da-00ca-11e4-9830-12725f8f377c", entity.getString("id"));
        assertEquals("Vellacott", entity.getString("last_name"));
    }

    @Test
    public void testGetEntities() throws Exception {
        final GlobalRegistryClient client = this.getClient();
        assumeNotNull(client);

        final ResponseList<JSONObject> entities = client.getEntities(TYPE_PERSON,
                "4e9541b8-e02a-11e3-a00d-12725f8f377c", new Filter().path("last_name").values("Vellacott"));

        assertEquals(1, entities.getMeta().getPage());
        assertTrue(entities.getMeta().getTotal() > 0);

        for (final JSONObject entity : entities) {
            assertEquals("Vellacott", entity.getString("last_name"));
        }
    }

    @Test
    public void testCreateUpdateDeleteEntity() throws Exception {
        final GlobalRegistryClient client = this.getClient();
        assumeNotNull(client);

        final JSONObject baseEntity = new JSONObject();
        baseEntity.put("client_integration_id", RAND.nextLong());
        baseEntity.put("first_name", "Test User");
        final String newId = client.addEntity(TYPE_PERSON, baseEntity).getString("id");
        final JSONObject newEntity = client.getEntity(TYPE_PERSON, newId, "4e9541b8-e02a-11e3-a00d-12725f8f377c");

        assertNotNull(newEntity);
        assertEquals("Test User", newEntity.getString("first_name"));
        assertEquals(null, newEntity.optString("last_name", null));

        final JSONObject tmp = new JSONObject(newEntity.toString());
        tmp.put("first_name", "Updated Name");
        tmp.put("last_name", "Last");
        final String updatedId = client.updateEntity(TYPE_PERSON, newEntity.getString("id"), tmp).getString("id");
        final JSONObject updatedEntity = client.getEntity(TYPE_PERSON, updatedId, "4e9541b8-e02a-11e3-a00d-12725f8f377c");

        assertNotNull(updatedEntity);
        assertEquals("Updated Name", updatedEntity.getString("first_name"));
        assertEquals("Last", updatedEntity.getString("last_name"));
        assertEquals(newId, updatedId);

        client.deleteEntity(newEntity.getString("id"));
    }

    @Test
    public void testGetEntityTypes() throws Exception {
        final GlobalRegistryClient client = this.getClient();
        assumeNotNull(client);

        final ResponseList<EntityType> types = client.getEntityTypes(new Filter().path("name").value("person"));

        assertNotNull(types);
        assertEquals(1, types.size());

        // check the person entity
        final EntityType person = types.get(0);
        assertEquals("person", person.getName());
        assertEquals(EntityType.FieldType.ENTITY, person.getFieldType());
        final EntityType firstName = person.getField("first_name");
        assertNotNull(firstName);
        assertEquals("first_name", firstName.getName());
        assertEquals(EntityType.FieldType.STRING, firstName.getFieldType());
    }

    @Test
    public void testGetSystems() throws Exception {
        final GlobalRegistryClient client = this.getClient();
        assumeNotNull(client);

        final List<RegisteredSystem> systems = client.getSystems();

        assertNotNull(systems);
        assertTrue(systems.size() >= 1);

        {
            // randomly select one of the returned systems
            final RegisteredSystem expected = systems.get(RAND.nextInt(systems.size()));
            assertNotNull(expected);
            assertNotNull(expected.getId());

            // fetch the same system directly from the GR
            final RegisteredSystem system = client.getSystem(expected.getId());
            assertNotNull(system);
            assertEquals(expected, system);
        }

    }

    @Test
    public void testInvalidAccessToken() throws Exception {
        final BaseGlobalRegistryClient client = this.getClient();
        assumeNotNull(client);
        client.setAccessToken(client.accessToken + "_invalid");

        try {
            client.getEntities(TYPE_PERSON);
            fail("Expected UnauthorizedException not thrown");
        } catch (final UnauthorizedException expected) {
        }
    }
}
