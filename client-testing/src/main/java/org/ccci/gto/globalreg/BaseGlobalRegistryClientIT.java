package org.ccci.gto.globalreg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;

import com.google.common.collect.ImmutableSet;
import org.ccci.gto.globalreg.serializer.json.JSONObjectType;
import org.ccci.gto.globalreg.serializer.json.JsonSerializer;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class BaseGlobalRegistryClientIT {

    private static final Logger LOG = LoggerFactory.getLogger(BaseGlobalRegistryClientIT.class);

    private static final String ACCESS_TOKEN;
    static {
        String key = "accessToken";
        ACCESS_TOKEN = System.getProperty(key);
        if (ACCESS_TOKEN == null) {
            LOG.warn( "{} is not available as a system property", key);
        }
    }

    private static final Random RAND = new SecureRandom();

    protected static final JSONObjectType TYPE_PERSON = new JSONObjectType("person");

    private BaseGlobalRegistryClient client;

    protected abstract BaseGlobalRegistryClient newClient();

    @Before
    public void initClient() {
        // dont create client if we don't have an access token
        if (ACCESS_TOKEN == null || "".equals(ACCESS_TOKEN)) {
            client = null;
            return;
        }

        client = this.newClient();
        client.setApiUrl("https://stage-api.global-registry.org");
        client.setAccessToken(ACCESS_TOKEN);
        client.setSerializer(new JsonSerializer());
        client.setFullResponsesFromUpdates(true);
    }

    @After
    public void closeClient() throws Exception {
        if (client != null) {
            client.close();
        }
    }

    @Test
    public void testGetEntity() throws Exception {
        assumeNotNull(client);

        final JSONObject entity = client.getEntity(TYPE_PERSON, "ed4442da-00ca-11e4-9830-12725f8f377c",
                "4e9541b8-e02a-11e3-a00d-12725f8f377c", Collections.singleton("last_name"));

        assertNotNull(entity);
        assertEquals("ed4442da-00ca-11e4-9830-12725f8f377c", entity.getString("id"));
        assertEquals("Vellacott", entity.getString("last_name"));
        assertNull(entity.optString("first_name", null));
    }

    @Test
    public void testGetEntities() throws Exception {
        assumeNotNull(client);

        final ResponseList<JSONObject> entities = client.getEntities(TYPE_PERSON,
                "4e9541b8-e02a-11e3-a00d-12725f8f377c", Collections.singleton("last_name"),
                new Filter().path("last_name").values("Vellacott"));

        assertEquals(1, entities.getMeta().getPage());
        assertTrue(entities.size() > 0);

        for (final JSONObject entity : entities) {
            assertEquals("Vellacott", entity.getString("last_name"));
        }
    }

    @Test
    public void testCreateUpdateDeleteEntity() throws Exception {
        assumeNotNull(client);

        final JSONObject baseEntity = new JSONObject();
        baseEntity.put("client_integration_id", RAND.nextLong());
        baseEntity.put("first_name", "Test User");
        final String newId = client.addEntity(TYPE_PERSON, baseEntity).getString("id");
        final JSONObject newEntity = client.getEntity(TYPE_PERSON, newId, "4e9541b8-e02a-11e3-a00d-12725f8f377c",
                Collections.singleton("*"));

        assertNotNull(newEntity);
        assertEquals("Test User", newEntity.getString("first_name"));
        assertEquals(null, newEntity.optString("last_name", null));

        final JSONObject tmp = new JSONObject(newEntity.toString());
        tmp.put("first_name", "Updated Name");
        tmp.put("last_name", "Last");
        final JSONObject updatedEntity = client.updateEntity(TYPE_PERSON, newEntity.getString("id"), tmp,
                ImmutableSet.of("first_name", "last_name"), true);

        assertNotNull(updatedEntity);
        assertEquals("Updated Name", updatedEntity.getString("first_name"));
        assertEquals("Last", updatedEntity.getString("last_name"));
        assertEquals(newId, updatedEntity.getString("id"));
        assertEquals(newId, newEntity.getString("id"));

        client.deleteEntity(newId);
    }

    @Test
    public void testGetEntityTypes() throws Exception {
        assumeNotNull(client);

        final ResponseList<EntityType> types = client.getEntityTypes(new Filter().path("name").values("person"));

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
        assumeNotNull(client);
        client.setAccessToken(client.accessToken + "_invalid");

        try {
            client.getEntities(TYPE_PERSON);
            fail("Expected UnauthorizedException not thrown");
        } catch (final UnauthorizedException expected) {
        }
    }
}
