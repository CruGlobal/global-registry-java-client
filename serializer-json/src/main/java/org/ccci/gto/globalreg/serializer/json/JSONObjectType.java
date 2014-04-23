package org.ccci.gto.globalreg.serializer.json;

import org.ccci.gto.globalreg.Type;
import org.json.JSONObject;

public final class JSONObjectType extends Type<JSONObject> {
    public JSONObjectType(final String type) {
        super(JSONObject.class, type);
    }
}
