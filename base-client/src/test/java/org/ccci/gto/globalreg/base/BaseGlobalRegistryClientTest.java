package org.ccci.gto.globalreg.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;

import org.ccci.gto.globalreg.EntityType;
import org.ccci.gto.globalreg.Filter;
import org.ccci.gto.globalreg.GlobalRegistryClient;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.System;
import org.ccci.gto.globalreg.UnauthorizedException;
import org.ccci.gto.globalreg.serializer.json.JSONObjectType;
import org.ccci.gto.globalreg.serializer.json.JsonSerializer;
import org.json.JSONObject;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class BaseGlobalRegistryClientTest {
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

        final JSONObject entity = client.getEntity(TYPE_PERSON, 7178366, "4");

        assertEquals(7178366, entity.getInt("id"));
        assertEquals("Vellacott", entity.getString("last_name"));
    }

    @Test
    public void testGetEntities() throws Exception {
        final GlobalRegistryClient client = this.getClient();
        assumeNotNull(client);

        final ResponseList<JSONObject> entities = client.getEntities(TYPE_PERSON, "4",
                new Filter().path("last_name").value("Vellacott"));

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

        final JSONObject newEntity = client.addEntity(TYPE_PERSON, new JSONObject(Collections.singletonMap
                ("first_name", "Test User")));

        assertNotNull(newEntity);
        assertEquals("Test User", newEntity.getString("first_name"));
        assertEquals(null, newEntity.optString("last_name", null));

        final JSONObject tmp = new JSONObject(newEntity.toString());
        tmp.put("first_name", "Updated Name");
        tmp.put("last_name", "Last");
        final JSONObject updatedEntity = client.updateEntity(TYPE_PERSON, newEntity.getInt("id"), tmp);

        assertNotNull(updatedEntity);
        assertEquals("Updated Name", updatedEntity.getString("first_name"));
        assertEquals("Last", updatedEntity.getString("last_name"));
        assertEquals(newEntity.getInt("id"), updatedEntity.getInt("id"));

        client.deleteEntity(TYPE_PERSON, newEntity.getInt("id"));
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
        assertNull(person.getFieldType());
        final EntityType firstName = person.getField("first_name");
        assertNotNull(firstName);
        assertEquals("first_name", firstName.getName());
        assertEquals(EntityType.FieldType.STRING, firstName.getFieldType());
    }

    @Test
    public void testGetSystems() throws Exception {
        final GlobalRegistryClient client = this.getClient();
        assumeNotNull(client);

        final List<System> systems = client.getSystems();

        assertNotNull(systems);
        assertTrue(systems.size() >= 1);

        {
            // randomly select one of the returned systems
            final System expected = systems.get(RAND.nextInt(systems.size()));
            assertNotNull(expected);
            assertNotNull(expected.getId());

            // fetch the same system directly from the GR
            final System system = client.getSystem(expected.getId());
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
