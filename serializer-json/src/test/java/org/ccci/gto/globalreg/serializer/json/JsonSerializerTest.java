package org.ccci.gto.globalreg.serializer.json;

import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.serializer.AbstractSerializerTest;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonSerializerTest extends AbstractSerializerTest {
    public JsonSerializerTest() {
        super(new JsonSerializer());
    }

    @Test
    public void testParseEntity() throws Exception {
        final JSONObject json = this.testParseEntity(new JSONObjectType("person"));
        assertEquals(5, json.getInt("id"));
        assertEquals("John", json.getString("first_name"));
        assertEquals("Doe", json.getString("last_name"));
        assertEquals("Ohio University", json.getString("campus"));
    }

    @Test
    public void testParseEntitiesList() throws Exception {
        final ResponseList<JSONObject> entities = this.testParseEntitiesList(new JSONObjectType("person"));

        // all records should have a last_name of Vellacott
        for(final JSONObject entity : entities) {
            assertEquals("Vellacott", entity.getString("last_name"));
        }
    }
}
