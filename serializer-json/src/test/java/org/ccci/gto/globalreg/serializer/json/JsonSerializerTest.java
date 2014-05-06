package org.ccci.gto.globalreg.serializer.json;

import static org.junit.Assert.assertEquals;

import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.serializer.base.AbstractSerializerTest;
import org.json.JSONObject;
import org.junit.Test;

public class JsonSerializerTest extends AbstractSerializerTest {
    private static final JSONObjectType TYPE_PERSON = new JSONObjectType("person");

    public JsonSerializerTest() {
        super(new JsonSerializer());
    }

    @Test
    public void testDeserializeEntity() throws Exception {
        final JSONObject json = this.testDeserializeEntity(TYPE_PERSON);
        assertEquals("882ce1da-d556-11e3-bb64-12725f8f377c", json.getString("id"));
        assertEquals("John", json.getString("first_name"));
        assertEquals("Doe", json.getString("last_name"));
        assertEquals("Ohio University", json.getString("campus"));
    }

    @Test
    public void testDeserializeEntities() throws Exception {
        final ResponseList<JSONObject> entities = this.testDeserializeEntities(TYPE_PERSON);

        // all records should have a last_name of Vellacott
        for(final JSONObject entity : entities) {
            assertEquals("Vellacott", entity.getString("last_name"));
        }
    }

    @Test
    public void testSerializeEntity() throws Exception {
        final JSONObject entity = new JSONObject();
        entity.put("first_name", "Bobby");
        entity.put("last_name", "Tables");
        this.testSerializeEntity(TYPE_PERSON, entity);
    }
}
