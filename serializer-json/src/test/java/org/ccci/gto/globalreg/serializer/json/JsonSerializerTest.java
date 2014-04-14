package org.ccci.gto.globalreg.serializer.json;

import org.ccci.gto.globalreg.TestUtils;
import org.ccci.gto.globalreg.serializer.AbstractSerializerTest;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonSerializerTest extends AbstractSerializerTest {
    public JsonSerializerTest() {
        super(new JsonSerializer());
    }

    @Test
    public void testToObject() throws Exception {
        final JSONObject json = this.serializer.toObject(new JSONObjectClass("person"), TestUtils.loadResource(AbstractSerializerTest.class, "person.json"));

        assertEquals(5, json.optInt("id"));
        assertEquals("John", json.optString("first_name"));
        assertEquals("Doe", json.optString("last_name"));
        assertEquals("Ohio University", json.optString("campus"));
    }
}
