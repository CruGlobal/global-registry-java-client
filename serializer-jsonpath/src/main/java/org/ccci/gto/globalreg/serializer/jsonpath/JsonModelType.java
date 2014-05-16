package org.ccci.gto.globalreg.serializer.jsonpath;

import com.jayway.jsonpath.JsonModel;
import org.ccci.gto.globalreg.Type;

public class JsonModelType extends Type<JsonModel> {
    public JsonModelType(final String type) {
        super(JsonModel.class, type);
    }
}
