package org.ccci.gto.globalreg.jaxrs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

import org.ccci.gto.globalreg.AbstractGlobalRegistryClient;
import org.ccci.gto.globalreg.AbstractGlobalRegistryClientTest;
import org.ccci.gto.globalreg.Filter;
import org.ccci.gto.globalreg.GlobalRegistryClient;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.serializer.json.JSONObjectType;
import org.ccci.gto.globalreg.serializer.json.JsonSerializer;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Collections;

public class JaxrsGlobalRegistryClientTest extends AbstractGlobalRegistryClientTest {
    final JSONObjectType personType = new JSONObjectType("person");

    @Override
    protected AbstractGlobalRegistryClient newClient() {
        final JaxrsGlobalRegistryClient client = new JaxrsGlobalRegistryClient();
        client.setSerializer(new JsonSerializer());
        return client;
    }

    @Test
    public void testGetEntity() throws Exception {
        final GlobalRegistryClient client = this.getClient();
        assumeNotNull(client);

        final JSONObject entity = client.getEntity(personType, 7178366, "4");

        assertEquals(7178366, entity.getInt("id"));
        assertEquals("Vellacott", entity.getString("last_name"));
    }

    @Test
    public void testFindEntities() throws Exception {
        final GlobalRegistryClient client = this.getClient();
        assumeNotNull(client);

        final ResponseList<JSONObject> entities = client.findEntities(personType, "4",
                new Filter().path("last_name").value("Vellacott"));

        assertEquals(1, entities.getMeta().getPage());
        assertTrue(entities.getMeta().getTotal() > 0);

        for(final JSONObject entity : entities) {
            assertEquals("Vellacott", entity.getString("last_name"));
        }
    }
}
