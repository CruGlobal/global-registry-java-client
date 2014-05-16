package org.ccci.gto.globalreg.serializer.jsonpath;

import static org.junit.Assert.assertEquals;

import com.jayway.jsonpath.JsonModel;
import org.ccci.gto.globalreg.ResponseList;
import org.ccci.gto.globalreg.serializer.AbstractSerializerTest;
import org.junit.Test;

public class JsonPathSerializerTest extends AbstractSerializerTest {
    private static final JsonModelType TYPE_PERSON = new JsonModelType("person");

    public JsonPathSerializerTest() {
        super(new JsonPathSerializer());
    }

    @Test
    public void testDeserializeEntity() throws Exception {
        final JsonModel json = this.testDeserializeEntity(TYPE_PERSON);
        final JsonModel.ObjectOps objOps = json.opsForObject();
        assertEquals("882ce1da-d556-11e3-bb64-12725f8f377c", objOps.getString("id"));
        assertEquals("John", objOps.getString("first_name"));
        assertEquals("Doe", objOps.getString("last_name"));
        assertEquals("Ohio University", objOps.getString("campus"));
    }

    @Test
    public void testDeserializeEntities() throws Exception {
        final ResponseList<JsonModel> entities = this.testDeserializeEntities(TYPE_PERSON);

        // all records should have a last_name of Vellacott
        for (final JsonModel entity : entities) {
            assertEquals("Vellacott", entity.opsForObject().getString("last_name"));
        }
    }

    @Test
    public void testSerializeEntity() throws Exception {
        final JsonModel entity = JsonModel.model("{}");
        entity.opsForObject().put("first_name", "Bobby");
        entity.opsForObject().put("last_name", "Tables");
        this.testSerializeEntity(TYPE_PERSON, entity);
    }
}
