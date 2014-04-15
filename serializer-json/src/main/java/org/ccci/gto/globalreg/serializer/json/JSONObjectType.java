package org.ccci.gto.globalreg.serializer.json;

import org.ccci.gto.globalreg.EntityType;
import org.json.JSONObject;

public final class JSONObjectType extends EntityType<JSONObject> {
    public JSONObjectType(final String type) {
        super(JSONObject.class, type);
    }
}
