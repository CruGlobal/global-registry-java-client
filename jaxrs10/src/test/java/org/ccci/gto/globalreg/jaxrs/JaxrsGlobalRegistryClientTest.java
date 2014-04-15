package org.ccci.gto.globalreg.jaxrs;

import org.ccci.gto.globalreg.*;
import org.ccci.gto.globalreg.serializer.json.JSONObjectType;
import org.ccci.gto.globalreg.serializer.json.JsonSerializer;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JaxrsGlobalRegistryClientTest extends AbstractGlobalRegistryClientTest {
    @Override
    protected AbstractGlobalRegistryClient newClient() {
        final JaxrsGlobalRegistryClient client = new JaxrsGlobalRegistryClient();
        client.setSerializer(new JsonSerializer());
        return client;
    }

    @Test
    public void testGetEntity() throws Exception {
        final GlobalRegistryClient client = this.getClient();

        final JSONObject entity = client.getEntity(new JSONObjectType("person"), 7178366, "4");

        assertEquals(7178366, entity.getInt("id"));
        assertEquals("Vellacott", entity.getString("last_name"));
    }

    @Test
    public void testFindEntities() throws Exception {
        final GlobalRegistryClient client = this.getClient();

        final ResponseList<JSONObject> entities = client.findEntities(new JSONObjectType("person"), "4",
                new Filter().path("last_name").value("Vellacott"));

        assertEquals(1, entities.getMeta().getPage());
        assertTrue(entities.getMeta().getTotal() > 0);

        for(final JSONObject entity : entities) {
            assertEquals("Vellacott", entity.getString("last_name"));
        }
    }
}
