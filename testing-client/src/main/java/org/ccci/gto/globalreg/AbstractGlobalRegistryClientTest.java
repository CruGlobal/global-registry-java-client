package org.ccci.gto.globalreg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

import org.ccci.gto.globalreg.serializer.json.JSONObjectType;
import org.ccci.gto.globalreg.serializer.json.JsonSerializer;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Collections;

public abstract class AbstractGlobalRegistryClientTest {
    private static final String ACCESS_TOKEN = "";

    protected static final JSONObjectType TYPE_PERSON = new JSONObjectType("person");

    protected abstract AbstractGlobalRegistryClient newClient();

    protected AbstractGlobalRegistryClient getClient() {
        // dont create client if we don't have an access token
        if(ACCESS_TOKEN == null || "".equals(ACCESS_TOKEN)) {
            return null;
        }

        final AbstractGlobalRegistryClient client = this.newClient();
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
    public void testFindEntities() throws Exception {
        final GlobalRegistryClient client = this.getClient();
        assumeNotNull(client);

        final ResponseList<JSONObject> entities = client.findEntities(TYPE_PERSON, "4",
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
}