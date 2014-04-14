package org.ccci.gto.globalreg.serializer.json;

import org.ccci.gto.globalreg.EntityClass;
import org.json.JSONObject;

public final class JSONObjectClass extends EntityClass<JSONObject> {
    public JSONObjectClass(final String type) {
        super(JSONObject.class, type);
    }
}
